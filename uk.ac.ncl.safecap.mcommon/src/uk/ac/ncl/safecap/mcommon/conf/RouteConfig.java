package uk.ac.ncl.safecap.mcommon.conf;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.dialogs.MessageDialog;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

import safecap.Extensible;
import safecap.Labeled;
import safecap.Orientation;
import safecap.Project;
import safecap.config.ConfigFactory;
import safecap.config.RouteBuilderConfiguration;
import safecap.derived.DerivedFactory;
import safecap.derived.MergedAmbit;
import safecap.derived.MergedJunction;
import safecap.derived.MergedPoint;
import safecap.extension.ExtAttribute;
import safecap.extension.ExtAttributeB;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.model.ModelFactory;
import safecap.model.Point;
import safecap.model.Route;
import safecap.schema.Node;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.LeftSignal;
import safecap.trackside.RightSignal;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.misc.core.AmbitPath;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.PointUtil;
import uk.ac.ncl.safecap.misc.core.ProjectTransient;
import uk.ac.ncl.safecap.misc.core.RouteUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.misc.core.SegmentPath;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;
import uk.ac.ncl.safecap.misc.core.SignalUtil;
import uk.ac.ncl.safecap.misc.core.SubOverlap;
import uk.ac.ncl.safecap.misc.core.SubOverlapUtil;
import uk.ac.ncl.safecap.misc.core.TextDialog;

public class RouteConfig implements IConfigError {
	private final StringBuffer errors;
	private final Project project;
	private final IGraphicalEditPart projectPart;
	private Map<Segment, Ambit> _map;
	private final Map<SegmentPath, AmbitPath> pathCache;

	public RouteConfig(Project project, IGraphicalEditPart projectPart) {
		this.project = project;
		errors = new StringBuffer();
		this.projectPart = projectPart;
		pathCache = new HashMap<>();

		ProjectTransient.setValue(project, ProjectTransient.SEGMENT_AMBIT_MAP, null);
	}

	public String getErrors() {
		return errors.toString();
	}

	private Map<Segment, Ambit> getMap() {
		if (_map == null) {
			_map = AmbitUtil.getSegmentAmbitMap(project);
		}
		return _map;
	}

	public AbstractTransactionalCommand getCommandBuildNonInteractive(final Reader in) {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(project);
		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "BuildNonInteractive", null) {
			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				process(in);
				return CommandResult.newOKCommandResult();
			}
		};
		return command;
	}

	public AbstractTransactionalCommand getCommandBuildInteractive(final Reader in) {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(project);
		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "BuildInteractive", null) {
			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				process(in);
				if (errors.length() > 0) {
					final TextDialog td = new TextDialog(null, errors.toString());
					td.open();
					final boolean commit = MessageDialog.openConfirm(null, "Route construction",
							"Commit these configuration despite errors?");
					if (!commit) {
						return CommandResult.newErrorCommandResult("Route construction aborted due to errors");
					}
				}
				return CommandResult.newOKCommandResult();
			}
		};
		return command;
	}

	public void process(Reader in) {
		int line = 0;
		final RouteBuilderConfiguration config = ConfigFactory.eINSTANCE.createRouteBuilderConfiguration();
		project.setRoutebuilder(config);
		// clear_routes();

		// clear forced sub routes
		for (final Ambit ambit : project.getAmbits()) {
			ExtensionUtil.delete(ambit, SafecapConstants.EXT_SUBROUTE_FORCED);
		}

		try {
			String[] parts;
			final CSVParser parser = new CSVParser(CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, '\0',
					CSVParser.DEFAULT_STRICT_QUOTES);
			final CSVReader reader = new CSVReader(in, 0, parser);
			while ((parts = reader.readNext()) != null) {
				try {
					parts = CSVUtil.trim(parts);
					processLine(line, parts);
				} catch (final Throwable e) {
					if (e.getMessage() != null) {
						error("line " + line + ": " + e.getMessage());
					}
				}
				line++;
			}
			ProjectTransient.resetProjectLevel(project);
			reader.close();
		} catch (final Throwable e) {
			error("I/O error: " + e.getMessage());
		}
	}

	public void processLine(int line, String... parts) throws RoutePathException {
		if (parts.length > 0) {
			final String kind = CSVUtil.unquote(parts[0]);
			if ("comment".equals(kind)) {
				// do nothing
			} else if ("clear".equals(kind)) {
				for (int i = 1; i < parts.length; i++) {
					final String what = CSVUtil.unquote(parts[i]);
					if ("routes".equals(what)) {
						clear_routes();
					} else if ("subroutes".equals(what)) {
						// clear forced sub routes
						clear_subroutes();
					} else if ("suboverlaps".equals(what)) {
						// clear forced sub routes
						clear_suboverlaps();
					} else if ("overlaps".equals(what)) {
						// clear forced sub routes
						clear_overlaps();
					} else if ("markers".equals(what)) {
						clear_markers();
					} else if ("virtual".equals(what)) {
						clear_virtual();
					} else if ("mergedpoints".equals(what)) {
						clear_merged_points();
					} else if ("mergedambits".equals(what)) {
						clear_merged_ambits();
					} else if ("all".equals(what)) {
						clear_routes();
						clear_subroutes();
						clear_suboverlaps();
						clear_overlaps();
						clear_markers();
						clear_virtual();
						clear_merged_points();
						clear_merged_ambits();
					} else {
						error("clear: invalid argument " + what);
					}
				}
			} else if ("flag".equals(kind)) {
				if (parts.length == 3) {
					final String flag = CSVUtil.unquote(parts[1]);
					final boolean value = CSVUtil.unquote(parts[2]).equals("true");
					ExtensionUtil.setFlag(project, flag, value);
				}
			} else if ("suboverlap".equals(kind)) {
				if (parts.length == 4) {
					final String ambit = CSVUtil.unquote(parts[1]);
					final String direction = CSVUtil.unquote(parts[2]);
					final String name = CSVUtil.unquote(parts[3]);
					
					createCustomSubOverlap(ambit, direction, name);
				}
			} else if ("delete-suboverlap".equals(kind)) {
				if (parts.length > 1) {
					for(int i = 1; i < parts.length; i++)
					deleteSubOverlap(CSVUtil.unquote(parts[i]));
				}
			} else if ("overlap".equals(kind)) {
				if (parts.length > 3) {
					final String signal = CSVUtil.unquote(parts[1]);
					final String type = CSVUtil.unquote(parts[2]);
					List<String> suboverlaps = new ArrayList<>();
					for(int i = 3; i < parts.length; i++)
						suboverlaps.add(parts[i]);
					
					createCustomOverlap(signal, type, suboverlaps);
				}
			} else if ("mergedpoint".equals(kind)) {
				if (parts.length < 4) {
					error("mergedpoint: need at least 3 arguments");
				} else {
					final List<String> points = new ArrayList<>();
					for (int i = 2; i < parts.length; i++) {
						points.add(CSVUtil.unquote(parts[i]));
					}
					mergedpoint(CSVUtil.unquote(parts[1]), points);
					ProjectTransient.resetProjectLevel(project);
				}
			} else if ("mergedambit".equals(kind)) {
				if (parts.length < 4) {
					error("mergedambit: need at least 3 arguments");
				} else {
					final List<String> ambits = new ArrayList<>();
					for (int i = 2; i < parts.length; i++) {
						ambits.add(CSVUtil.unquote(parts[i]));
					}
					mergedambit(CSVUtil.unquote(parts[1]), ambits, false, false);
					ProjectTransient.resetProjectLevel(project);
					_map = null;
				}
			} else if ("mergedambit-disjoint".equals(kind)) {
				if (parts.length < 4) {
					error("mergedambit-disjoint: need at least 3 arguments");
				} else {
					final List<String> ambits = new ArrayList<>();
					for (int i = 2; i < parts.length; i++) {
						ambits.add(CSVUtil.unquote(parts[i]));
					}
					mergedambit(CSVUtil.unquote(parts[1]), ambits, false, true);
					ProjectTransient.resetProjectLevel(project);
					_map = null;
				}
			} else if ("composedambit".equals(kind)) {
				if (parts.length < 4) {
					error("composedambit: need at least 3 arguments");
				} else {
					final List<String> ambits = new ArrayList<>();
					for (int i = 2; i < parts.length; i++) {
						ambits.add(CSVUtil.unquote(parts[i]));
					}
					mergedambit(CSVUtil.unquote(parts[1]), ambits, true, false);
					ProjectTransient.resetProjectLevel(project);
					_map = null;
				}
			} else if ("subroutes".equals(kind)) {
				for (int i = 1; i < parts.length; i++) {
					forceSubRoute(CSVUtil.unquote(parts[i]));
				}
				ProjectTransient.resetProjectLevel(project);
			} else if ("subroute-alias".equals(kind) && parts.length > 2) {
				aliasSubRoute(CSVUtil.unquote(parts[1]), CSVUtil.unquote(parts[2]));
				ProjectTransient.resetProjectLevel(project);
			} else if ("auto".equals(kind)) {
				final String name = CSVUtil.unquote(parts[1]);
				final Collection<String> ignoredSignals = new ArrayList<>();
				for (int i = 2; i < parts.length; i++) {
					ignoredSignals.add(CSVUtil.unquote(parts[i]));
				}
				makeAutoRoute(name, ignoredSignals);
			} else if ("route".equals(kind)) {
				final String name = CSVUtil.unquote(parts[1]);
				final String entry = CSVUtil.unquote(parts[2]);
				final String exit = CSVUtil.unquote(parts[3]);
				final Collection<String> ignoredSignals = new ArrayList<>();
				final Collection<String> pointStates = new ArrayList<>();
				for (int i = 4; i < parts.length; i++) {
					final String value = CSVUtil.unquote(parts[i]);

					if (value.startsWith("P")) {
						pointStates.add(value);
					} else if (value.startsWith("S")) {
						ignoredSignals.add(value);
					} else {
						final Signal s = SignalUtil.getByLabel(project, value);
						final Point p = PointUtil.getByLabel(project, value.substring(0, value.length() - 1));
						final char last = value.charAt(value.length() - 1);
						if (s != null) {
							ignoredSignals.add(value);
						} else if (p != null && (last == 'N' || last == 'P')) {
							pointStates.add(value);
						} else {
							error("route command: " + value + " is neither signal nor point state");
						}
					}
				}
				routeBuilderEntry(name, entry, exit, ignoredSignals, pointStates);
			} else if ("detectmergedpoints".equals(kind)) {
				final String name = CSVUtil.unquote(parts[1]);
				final String delimiter = CSVUtil.unquote(parts[2]);
				final String code = CSVUtil.unquote(parts[3]);
				PointUtil.buildMergedPoints(project, name, delimiter, code);
				final String ss = SchemaConfig.checkMergedPoints(project);
				if (ss.length() > 0) {
					error("Detecting merged points:\n" + ss);
				}
				ProjectTransient.resetProjectLevel(project);
			} else if ("detectmergedambits".equals(kind)) {
				final String name = CSVUtil.unquote(parts[1]);
				final String delimiter = CSVUtil.unquote(parts[2]);
				final String code = CSVUtil.unquote(parts[3]);
				AmbitUtil.buildMergedAmbits(project, name, delimiter, code, false, this);
				final String ss = SchemaConfig.checkMergedAmbits(project);
				if (ss.length() > 0) {
					error("Detecting merged track:\n" + ss);
				}
				ProjectTransient.resetProjectLevel(project);
				_map = null;
			} else if ("pointmap".equals(kind)) {
				final String pattern = CSVUtil.unquote(parts[1]);
				final String template = CSVUtil.unquote(parts[2]);
				PointUtil.renamePoints(project, pattern, template);
				ProjectTransient.resetProjectLevel(project);
			} else if ("trackmap".equals(kind)) {
				final String pattern = CSVUtil.unquote(parts[1]);
				final String template = CSVUtil.unquote(parts[2]);
				AmbitUtil.renameAmbits(project, pattern, template);
				_map = null;
			} else if ("section-virtual".equals(kind)) {
				final String pattern = CSVUtil.unquote(parts[1]);
				final String template = CSVUtil.unquote(parts[2]);
				AmbitUtil.declareVirtualAmbits(project, pattern, template);
				_map = null;
			} else if ("signalmap".equals(kind)) {
				final String pattern = CSVUtil.unquote(parts[1]);
				final String template = CSVUtil.unquote(parts[2]);
				SignalUtil.renameSignals(project, pattern, template);
				ProjectTransient.resetProjectLevel(project);
			} else if (kind.length() > 0) {
				error("line " + line + ": " + "unknown command " + kind);
			}
		}
	}

	private void deleteSubOverlap(String name) {
		SubOverlap so = SubOverlapUtil.getSubOverlapFromName(project, name);
		SubOverlapBuilder.deleteSubOverlap(so);
	}

	private void createCustomOverlap(String signal, String type, List<String> suboverlaps) {
		if (suboverlaps.isEmpty()) {
			error("command overlap: require at least one sub overlap");
			return;	
		}
		
		int kind;
		if ("full".equals(type))
			kind = 1;
		else if ("reduced".equals(type))
			kind = 0;
		else {
			error("command overlap: invalid overlap type; possible values are {full, reduced}");
			return;	
		}
		
		Signal sig = SignalUtil.getByLabel(project, signal);
		if (sig == null) {
			error("command overlap: invalid signal name " + signal);
			return;	
		}
		
		List<SubOverlap> resolved = new ArrayList<>(suboverlaps.size());
		SubOverlap p = null;
		for(String so: suboverlaps) {
			SubOverlap r = SubOverlapUtil.getSubOverlapFromName(project, so);
			if (r == null) {
				error("command overlap: invalid sub overlap name " + so);
				return;	
			}
			
			if (!r.isValid() || r.getLength() == 0 || r.getNodePath() == null) {
				error("command overlap: sub overlap " + so + " does not exist");
				return;	
			}
			
			if (p != null) {
				Node p_last = p.getNodePath().get(p.getNodePath().size() - 1);
				Node r_first = r.getNodePath().get(0);
				if (p_last != r_first) {
					error("command overlap: sub overlaps " + p.toString() + " and " + r.toString() + " are not adjacent");
					return;	
				}
			}
			
			resolved.add(r);
		}
		
		SubOverlapBuilder.commitOverlap(sig, resolved, kind, true);
		
	}

	private void createCustomSubOverlap(String ambit, String direction, String name) {
		Ambit _ambit = AmbitUtil.getByLabel(project, ambit);
		if (_ambit == null) {
			error("command suboverlap: invalid ambit name " + ambit);
			return;
		}
		
		if (!AmbitUtil.getAllAmbitSubRouteDirections(_ambit).contains(direction)) {
			error("command suboverlap: invalid sub overlap direction " + direction + "; possibe values are " + AmbitUtil.getAllAmbitSubRouteDirections(_ambit));
			return;
		}
		
		if (!name.startsWith("O")) {
			error("command suboverlap: invalid sub overlap name - must start with O");
			return;			
		}
		
		SubOverlap so = SubOverlapUtil.getSubOverlapFromName(project, name);
		if (so != null && so.isValid()) {
			error("command suboverlap: sub overlap " + name + " is already defined on ambit " + so.getAmbit().getLabel());
			return;						
		}
		
		
		SubOverlapBuilder.setSuboverlap(_ambit, direction, name);
	}

	private void clear_merged_ambits() {
		final Collection<MergedAmbit> allMerged = AmbitUtil.getMergedAmbits(project);
		for (final MergedAmbit merged : allMerged) {
			AmbitUtil.disbandMergedAmbit(project, merged);
		}

		ProjectTransient.resetProjectLevel(project);
		_map = null;
	}

	private void clear_merged_points() {
		project.getMergedpoints().clear();
		rebuildJunctionPointAssociation(project);
		ProjectTransient.resetProjectLevel(project);
	}

	private void clear_virtual() {
		final List<Labeled> toRemove = new ArrayList<>();

		for (final Labeled l : project.getDarkmatter()) {
			if (l instanceof Extensible && ExtensionUtil.isVirtual((Extensible) l)) {
				toRemove.add(l);
			}
		}

		project.getDarkmatter().removeAll(toRemove);
	}

	private void clear_markers() {
		for (final Node node : project.getNodes()) {
			ExtensionUtil.delete(node, SafecapConstants.EXT_NODE_TAG_PROTECT);
		}
		ProjectTransient.resetProjectLevel(project);
	}

	private void clear_subroutes() {
		for (final Ambit ambit : project.getAmbits()) {
			ExtensionUtil.delete(ambit, SafecapConstants.EXT_SUBROUTE_FORCED);
			ExtensionUtil.delete(ambit, SafecapConstants.EXT_SUBROUTE_RENAMED);
		}
		ProjectTransient.resetProjectLevel(project);
	}
	
	private void clear_suboverlaps() {
		for (final Ambit ambit : project.getAmbits()) {
			ExtensionUtil.delete(ambit, SafecapConstants.EXT_SUBOVERLAP_PLUS);
		}
		ProjectTransient.resetProjectLevel(project);
	}	
	
	private void clear_overlaps() {
		for (final Equipment s : project.getEquipment()) {
			if (s instanceof Signal) {
				Signal signal = (Signal) s;
				for(ExtAttribute name: ExtensionUtil.getAll(signal, SafecapConstants.EXT_OVERLAP_CUSTOM)) {
					if (name instanceof ExtAttributeB) {
						ExtAttributeB name_b = (ExtAttributeB) name;
						if (name_b.isValue()) {
							SubOverlapBuilder.deleteOverlapDefinition(signal, name_b.getLabel());
						}
					}
				}
			}
		}
		ProjectTransient.resetProjectLevel(project);
	}	

	private void clear_routes() {
		ProjectTransient.resetProjectLevel(project);
		for (final Route route : project.getRoutes()) {
			ExtensionUtil.delete(route, SafecapConstants.EXT_ROUTE);
		}
		project.getRoutes().clear();
	}

	private void aliasSubRoute(String subrouteName, String alias) {

		if (alias.length() < 2 || alias.charAt(0) != 'U') {
			error("command subroute-alias: invalid alias " + alias);
			return;
		}

		final String[] parts = parseSubRoute("subroute-alias", subrouteName);
		if (parts == null) {
			return;
		}

		final String ambitName = parts[0];
		final String direction = parts[1];

		final Ambit ambit = AmbitUtil.getByLabel(project, ambitName);
		assert ambit != null;

		final boolean isForced = ExtensionUtil.getBool(ambit, SafecapConstants.EXT_SUBROUTE_FORCED, direction);

		if (!isForced) {
			ambit.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_SUBROUTE_FORCED, direction, true));
		}

		ambit.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_SUBROUTE_RENAMED, direction, alias));
	}

	private void mergedpoint(String label, List<String> points) {
		if (PointUtil.getByLabel(project, label) != null) {
			error("command mergedpoint: new point name " + label + " is already used");
			return;
		}

		final List<Point> resolved = new ArrayList<>(points.size());
		for (final String p : points) {
			final Point rp = PointUtil.getByLabel(project, p);
			if (rp == null) {
				error("command mergedpoint: invalid point name " + p);
				return;
			}
			if (rp instanceof MergedPoint) {
				error("command mergedpoint: point end " + p + " is a merged point itself");
				return;
			}
			final MergedPoint mp = PointUtil.getMergedByPoint(project, rp);
			if (mp != null) {
				error("command mergedpoint: point name " + p + " is already a part of merged point " + mp.getLabel());
				return;
			}

			resolved.add(rp);
		}

		final MergedPoint mpoints = DerivedFactory.eINSTANCE.createMergedPoint();
		mpoints.setLabel(label);
		for (int i = 0; i < resolved.size(); i++) {
			mpoints.getCodes().add(String.valueOf((char) ('A' + i)));
			mpoints.getPoints().add(resolved.get(i));
		}
		project.getMergedpoints().add(mpoints);
	}

	private void mergedambit(String label, List<String> ambits, boolean composed, boolean disjoint) {
		final String myname = composed ? "composedambit" : disjoint ? "mergedambit-disjoint" : "mergedambit";
		if (AmbitUtil.getByLabel(project, label) != null) {
			error("command " + myname + ": new ambit name " + label + " is already used");
			return;
		}

		boolean isJunction = false;

		final List<Ambit> resolved = new ArrayList<>(ambits.size());
		for (final String a : ambits) {
			final Ambit ra = AmbitUtil.getByLabel(project, a);
			if (ra == null) {
				error("command " + myname + ": invalid ambit name " + a);
				return;
			}
			if (ra instanceof MergedAmbit) {
				error("command " + myname + ": ambit " + a + " is a merged ambit itself");
				return;
			}

			if (!composed) {
				final MergedAmbit ma = AmbitUtil.getMergedByAmbit(project, ra);
				if (ma != null) {
					error("command " + myname + ": ambit name " + a + " is already a part of merged ambit " + ma.getLabel());
					return;
				}
			}
			isJunction |= ra instanceof Junction;
			resolved.add(ra);
		}

		final MergedAmbit mambit = isJunction ? DerivedFactory.eINSTANCE.createMergedJunction()
				: DerivedFactory.eINSTANCE.createMergedSection();
		mambit.setLabel(label);
		mambit.setComposed(composed);
		mambit.setDisjoint(disjoint);
		for (int i = 0; i < resolved.size(); i++) {
			mambit.getCodes().add(String.valueOf(i));
			mambit.getAmbits().add(resolved.get(i));
		}

		for (final Ambit ambit : mambit.getAmbits()) {
			mambit.getSegments().addAll(ambit.getSegments());
			if (isJunction && ambit instanceof Junction) {
				final Junction j = (Junction) ambit;
				final MergedJunction mj = (MergedJunction) mambit;
				mj.getPoints().addAll(j.getPoints());
			}
		}

		project.getAmbits().add(mambit);

		if (!composed) {
			for (final Ambit a : resolved) {
				ExtensionUtil.setVirtual(a);
			}

			project.getDarkmatter().addAll(resolved);
			project.getAmbits().removeAll(resolved);
		}
	}

	private String[] parseSubRoute(String command, String subrouteName) {
		if (subrouteName == null) {
			error("command " + command + " missing sub route name");
			return null;
		}

		final int pos = subrouteName.indexOf('-');
		if (subrouteName.length() < 5 || pos == -1 || subrouteName.length() - pos != 3 || subrouteName.charAt(0) != 'U') {
			error("command  " + command + "  - invalid sub route name " + subrouteName);
			return null;
		}

		final String ambitName = subrouteName.substring(1, pos);
		final String direction = subrouteName.substring(pos + 1);
		Ambit ambit = AmbitUtil.getByLabel(project, ambitName);
		if (ambit == null) {
			ambit = AmbitUtil.getByLabel(project, "T" + ambitName);
		}
		if (ambit == null) {
			error("command  " + command + "  - invalid ambit name " + ambitName);
			return null;
		}

		final Collection<String> all = AmbitUtil.getAllAmbitSubRouteDirections(ambit);
		if (!all.isEmpty() && !all.contains(direction)) {
			error("command subroutes - invalid sub route direction " + direction);
			return null;
		}

//
//		if (direction.length() != 2 || direction.charAt(0) == direction.charAt(1) ||
//				!Character.isUpperCase(direction.charAt(0)) ||
//				!Character.isUpperCase(direction.charAt(1))) {
//			error("command  " + command + "  - subroute direction " + direction);
//			return null;
//		}		

		return new String[] { "T" + ambitName, direction };
	}

	private void forceSubRoute(String subrouteName) {
		final String[] parts = parseSubRoute("subroutes", subrouteName);
		if (parts == null) {
			return;
		}

		final String ambitName = parts[0];
		final String direction = parts[1];

		final Ambit ambit = AmbitUtil.getByLabel(project, ambitName);
		if (ambit == null) {
			error("Failed forcing sub route " + subrouteName + ": cannot locate section " + ambitName);
			return;
		}

		ambit.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_SUBROUTE_FORCED, direction, true));

	}

	private void routeBuilderEntry(String name, String entry, String exit, Collection<String> ignoredSignals,
			Collection<String> pointStates) throws RoutePathException {
		final Signal fromSignal = SignalUtil.getByLabel(project, entry);

		try {
			if (exit == null && fromSignal != null) {
				routeBuilder(name, entry, ignoredSignals, pointStates);
				return;
			} else {
				routeBuilder(name, entry, exit, ignoredSignals, pointStates);
				return;
			}
		} catch (final RoutePathException e) {
			throw new RoutePathException("Route " + name + ": " + e.getMessage());
		}

	}

	public void routeBuilder(String name, String from, Collection<String> ignoredSignals, Collection<String> pointStates)
			throws RoutePathException {
		AmbitPath ambitPath;
		try {

			pointStates = normalisePointStates(pointStates);

			final Signal fromSignal = SignalUtil.getByLabel(project, from);
			if (fromSignal == null) {
				error("Invalid route start \"" + from + "\" for route " + name);
				return;
			}

			final SegmentPath path = walk(fromSignal, ignoredSignals, pointStates);
			if (path.getPath().size() < 1) {

				error("Failed building route " + name + ": no segments on paths");
				return;
			}

			ambitPath = getAmbitPath(path);
			addNewRoute(name, path, ambitPath);
		} catch (final RoutePathException e) {
			throw new RoutePathException("Route " + name + ": " + e.getMessage());
		}

		// System.out.println("Added route " + name + " with path " +
		// ambitPath.getPath());

	}

	public SegmentPath routePath(String name, String from, String to, Collection<String> ignoredSignals, Collection<String> pointStates)
			throws RoutePathException {
		final Node fromNode = NodeUtil.getByLabel(project, from);
		final Node toNode = NodeUtil.getByLabel(project, to);
		final Signal fromSignal = SignalUtil.getByLabel(project, from);
		final Signal toSignal = SignalUtil.getByLabel(project, to);

		// System.out.println("Processing route command " + name + " from " + from + "
		// to " + to);

		if (fromNode == null && fromSignal == null) {
			error("Invalid route start \"" + from + "\" for route " + name);
			return null;
		}

		if (toNode == null && toSignal == null) {
			error("Invalid route end \"" + to + "\" for route " + name);
			return null;
		}

		SegmentPath path = null;

		try {
			if (fromNode != null) {
				if (toNode != null) {
					error("Invalid route definition - neither end nor start are signals for route " + name);
					return null;
				} else {
					path = walk(fromNode, toSignal, ignoredSignals, pointStates);
				}
			} else {
				if (toNode != null) {
					path = walk(fromSignal, toNode, ignoredSignals, pointStates);
				} else {
					path = walk(fromSignal, toSignal, ignoredSignals, pointStates);
				}
			}
		} catch (final Throwable e) {
			error("Failed building route " + name + ": " + e.getMessage() + "; ignored signals = " + ignoredSignals
					+ "; prohibited point states = " + pointStates);
			// e.printStackTrace();
			return null;
		}

		if (path.getPath().size() < 1) {
			error("Failed building route " + name + ": no segments on paths");
			return null;
		}

		return path;
	}

	public void routeBuilder(String name, String from, String to, Collection<String> ignoredSignals, Collection<String> pointStates)
			throws RoutePathException {

		pointStates = normalisePointStates(pointStates);

		final SegmentPath path = routePath(name, from, to, ignoredSignals, pointStates);
		if (path != null) {
			final AmbitPath ambitPath = getAmbitPath(path);
			addNewRoute(name, path, ambitPath);
			// System.out.println("Added route " + name + " with path " +
			// ambitPath.getPath());
		}
	}

	private Collection<String> normalisePointStates(Collection<String> pointStates) {
		if (pointStates.isEmpty()) {
			return pointStates;
		}

		final Collection<String> result = new HashSet<>();

		for (final String ps : pointStates) {
			final String pointname = ps.substring(0, ps.length() - 1);
			final String state = ps.substring(ps.length() - 1);
			final MergedPoint mp = PointUtil.getMergedByLabel(project, pointname);
			if (mp != null) {
				for (final Point p : mp.getPoints()) {
					result.add(p.getNode().getLabel() + state);
				}
			} else {
				result.add(ps);
			}
		}
		return result;
	}

	public static RouteNameDescriber parseRouteName(String name) {
		final Matcher matcher = RouteUtil.routePattern.matcher(name);
		if (!matcher.matches() || matcher.groupCount() < 3) {
			return null;
		}

		return new RouteNameDescriber(matcher.group("prefix"), matcher.group("signal"), matcher.group("path"), matcher.group("pathcode"),
				matcher.group("class") != null ? matcher.group("class") : "M", matcher.group("subclass"), matcher.group("suffix"));
	}

	public SegmentPath makeAutoRoutePath(String name, Collection<String> ignoredSignals) throws RoutePathException {
		final String[] ignored = ignoredSignals.toArray(new String[ignoredSignals.size()]);
		final Matcher matcher = RouteUtil.routePattern.matcher(name);
		if (!matcher.matches() || matcher.groupCount() < 3) {
			error("Invalid route name " + name + " for auto construct route. Expect " + RouteUtil.routePattern.toString() + " pattern");
			return null;
		}

		final String routePrefix = matcher.group("prefix");
		final String signalCode = matcher.group("signal");
		final String pathName = matcher.group("path");
		final String pathId = matcher.group("pathcode");
		final String routeClass = matcher.group("class") != null ? matcher.group("class") : "M";

		if (pathId != null) {
			error("Cannot automatically determine path for indexed route " + name + "; use route command instead");
			return null;
		}

		final Signal signal = SignalUtil.getByNumberCode(project, signalCode, routePrefix);
		if (signal == null) {
			error("Invalid signal ?" + signalCode + " for auto construct route " + name);
			return null;
		}
		final List<SegmentPath> paths = SignalUtil.getRouteSegmentPaths(signal, ignored);
		filterByAmbitPath(paths, routeClass);
		if (paths.size() == 0) {
			error("No viable paths for auto construct route " + name);
			return null;
		} else if (paths.size() == 1 && pathName != null) {
			final char code = pathName.toCharArray()[0];
			if (code != 'A') {
				error("Only one viable path for auto construct route " + name + " - " + paths.get(0) + " - but path code " + pathName
						+ " is used");
				return null;
			}
			return paths.get(0);
		} else if (paths.size() > 1 && pathName == null) {
			error("Multiple viable paths for auto construct route " + name + " but no path code defined");
			return null;
		} else if (paths.size() == 1 && pathName == null) {
			return paths.get(0);
		} else if (paths.size() > 1 && pathName != null) {
			final char code = pathName.toCharArray()[0];
			final Map<Character, SegmentPath> smap = getOrderedPaths(paths);
			if (!smap.containsKey(code)) {
				error("Invalid path code " + pathName + "  for route " + name + "; valid paths are " + smap.keySet());
				return null;
			}

			final SegmentPath path = smap.get(code);
			return path;
		} else {
			return null;
		}
	}

	public static SegmentPath pathBuilder(Node fromNode, Node toNode, Orientation orientation) {
		try {
			return walk(fromNode, toNode, orientation, null, null);
		} catch (final Throwable e) {
			return null;
		}
	}

	protected void makeAutoRoute(String name, Collection<String> ignoredSignals) throws RoutePathException {
		final SegmentPath path = makeAutoRoutePath(name, ignoredSignals);
		if (path != null) {
			final AmbitPath ambitPath = getAmbitPath(path);
			addNewRoute(name, path, ambitPath);
			// System.out.println("Added route " + name + " with path " +
			// ambitPath.getPath() + " / " + path);
		}
	}

	public AmbitPath makeAutoRouteAmbitPath(String name, Collection<String> ignoredSignals) {
		try {
			final SegmentPath path = makeAutoRoutePath(name, ignoredSignals);
			if (path != null) {
				return getAmbitPath(path);
			}
		} catch (final RoutePathException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	private AmbitPath getAmbitPath(SegmentPath path) {
		if (!pathCache.containsKey(path)) {
			final AmbitPath apath = new AmbitPath(path, getMap(), AmbitUtil.getAmbitToComposedMap(project));
			pathCache.put(path, apath);
			return apath;
		}

		return pathCache.get(path);
	}

	private void filterByAmbitPath(List<SegmentPath> paths, String routeClass) {
		final Collection<SegmentPath> toRemove = new HashSet<>();

		for (final SegmentPath sp : paths) {
			// if (routeClass.equals("M") && !sp.endsWithSignal()) {
			// toRemove.add(sp);
			// } else {
			for (final SegmentPath tp : paths) {
				if (sp != tp && getAmbitPath(sp).getLast().equals(getAmbitPath(tp).getLast())) {
					toRemove.add(sp.getPath().size() < tp.getPath().size() ? tp : sp);
					// }
				}
			}
		}

		paths.removeAll(toRemove);
	}

	private Map<Character, SegmentPath> getOrderedPaths(List<SegmentPath> paths) throws RoutePathException {
		if (paths == null || paths.size() < 2) {
			throw new RoutePathException("Invalid path set");
		}

		final Orientation orientation = paths.get(0).getOrientation();
		if (orientation == Orientation.LEFT) {
			paths.sort(new Comparator<SegmentPath>() {
				@Override
				public int compare(SegmentPath o1, SegmentPath o2) {
					final Segment aLast = o1.getPath().get(o1.getPath().size() - 1);
					final Segment bLast = o2.getPath().get(o2.getPath().size() - 1);
					final Node a = aLast.getTo();
					final Node b = bLast.getTo();
					final Signal aSignal = SignalUtil.getLeftSignalOn(aLast);
					final Signal bSignal = SignalUtil.getLeftSignalOn(bLast);
					final Rectangle aBounds = aSignal == null ? getNodeBounds(a) : getSignalBounds(aSignal);
					final Rectangle bBounds = bSignal == null ? getNodeBounds(b) : getSignalBounds(bSignal);
					if (Math.abs(aBounds.y - bBounds.y) < 10) {
						o1.setAmbiguous(true);
						o2.setAmbiguous(true);
					}
					return aBounds.y - bBounds.y;
				}

			});
		} else if (orientation == Orientation.RIGHT) {
			paths.sort(new Comparator<SegmentPath>() {

				@Override
				public int compare(SegmentPath o1, SegmentPath o2) {
					final Segment aLast = o1.getPath().get(o1.getPath().size() - 1);
					final Segment bLast = o2.getPath().get(o2.getPath().size() - 1);
					final Node a = aLast.getFrom();
					final Node b = bLast.getFrom();
					final Signal aSignal = SignalUtil.getRightSignalOn(aLast);
					final Signal bSignal = SignalUtil.getRightSignalOn(bLast);
					final Rectangle aBounds = aSignal == null ? getNodeBounds(a) : getSignalBounds(aSignal);
					final Rectangle bBounds = bSignal == null ? getNodeBounds(b) : getSignalBounds(bSignal);
					if (Math.abs(aBounds.y - bBounds.y) < 10) {
						o1.setAmbiguous(true);
						o2.setAmbiguous(true);
					}
					return bBounds.y - aBounds.y;
				}
			});
		} else {
			throw new RoutePathException("Invalid path orientation");
		}

		final Map<Character, SegmentPath> smap = new HashMap<>();
		for (int i = 0; i < paths.size(); i++) {
			smap.put((char) ('A' + i), paths.get(i));
		}

		return smap;
	}

	private void addNewRoute(String name, SegmentPath path, AmbitPath ambitPath) {
		final Route newr = ModelFactory.eINSTANCE.createRoute();
		newr.setLabel(name);
		newr.getAmbits().addAll(ambitPath.getPath());
		newr.getSegments().addAll(path.getPath());
		newr.setOrientation(path.getOrientation());
		project.getRoutes().add(newr);
	}

	private static void buildRightPath(Segment start, Segment end, SegmentPath path, Collection<String> ignoredSignals,
			Collection<String> pointStates) throws RoutePathException {
		boolean hasPath = false;
		for (final Segment s : SegmentUtil.getRightSegments(start)) {
			if (!containsPointState(pointStates, start, s) && hasRightPath(s, end)) {
				if (hasPath) {

					throw new RoutePathException(
							"Ambiguous path from segment " + s.getLabel() + "; disambiguate on " + s.getFrom().getLabel());
				}
				path.getPath().add(s);
				final Signal signal = SignalUtil.getLeftSignalOn(s);
				if (signal != null && !SignalUtil.isDistantOrRepeater(signal) && !containsIgnoredSignal(ignoredSignals, signal)) {
					if (end == null || end == s) {
						return;
					} else {
						throw new RoutePathException("Unexpected signal " + signal.getLabel() + " on the path");
					}
				}

				if (end == s) {
					return;
				}

				buildRightPath(s, end, path, ignoredSignals, pointStates);
				hasPath = true;
			}
		}

		if (!hasPath) {
			throw new RoutePathException("Unable to find path from " + start.getLabel() + " to " + end.getLabel());
		}
	}

	private static boolean containsIgnoredSignal(Collection<String> ignoredSignals, Signal signal) {
		return ignoredSignals != null && ignoredSignals.contains(signal.getLabel());
	}

	private static boolean hasRightPath(Segment start, Segment end) {
		if (end == null || start == end) {
			return true;
		}

		for (final Segment s : SegmentUtil.getRightSegments(start)) {
			if (hasRightPath(s, end)) {
				return true;
			}
		}

		return false;
	}

	private static void buildLeftPath(Segment start, Segment end, SegmentPath path, Collection<String> ignoredSignals,
			Collection<String> pointStates) throws RoutePathException {
		boolean hasPath = false;
		for (final Segment s : SegmentUtil.getLeftSegments(start)) {
			if (!containsPointState(pointStates, start, s) && hasLeftPath(s, end)) {
				if (hasPath) {
					throw new RoutePathException(
							"Ambiguous path from segment " + s.getLabel() + "; disambiguate on " + s.getTo().getLabel());
				}
				path.getPath().add(s);
				final Signal signal = SignalUtil.getRightSignalOn(s);
				if (signal != null && !SignalUtil.isDistantOrRepeater(signal) && !containsIgnoredSignal(ignoredSignals, signal)) {
					if (end == null || end == s) {
						return;
					} else {
						throw new RoutePathException("Unexpected signal " + signal.getLabel() + " on the path");
					}
				}

				if (end == s) {
					return;
				}

				buildLeftPath(s, end, path, ignoredSignals, pointStates);
				hasPath = true;
			}
		}

		if (!hasPath) {
			throw new RoutePathException("Unable to find path from " + start.getLabel() + " to " + end.getLabel());
		}
	}

	private static boolean containsPointState(Collection<String> pointStates, Segment from, Segment to) {
		if (pointStates != null && !pointStates.isEmpty()) {
			final String pointState = PointUtil.getPointState(from, to);
			return pointStates.contains(pointState);
		} else {
			return false;
		}
	}

	private static boolean hasLeftPath(Segment start, Segment end) {
		if (end == null || start == end) {
			return true;
		}

		for (final Segment s : SegmentUtil.getLeftSegments(start)) {
			if (hasLeftPath(s, end)) {
				return true;
			}
		}

		return false;
	}

	public SegmentPath walk(Signal fromSignal, Collection<String> ignoredSignals, Collection<String> pointStates)
			throws RoutePathException {
		final Segment start = SignalUtil.getSignalSegment(fromSignal);
		if (fromSignal instanceof LeftSignal) {
			final SegmentPath path = new SegmentPath(Orientation.LEFT, start);
			buildRightPath(start, null, path, ignoredSignals, pointStates);
			return path;
		} else if (fromSignal instanceof RightSignal) {
			final SegmentPath path = new SegmentPath(Orientation.RIGHT, start);
			buildLeftPath(start, null, path, ignoredSignals, pointStates);
			return path;
		} else {
			return null;
		}
	}

	public SegmentPath walk(Signal fromSignal, Signal toSignal, Collection<String> ignoredSignals, Collection<String> pointStates)
			throws RoutePathException {
		final Segment start = SignalUtil.getSignalSegment(fromSignal);
		final Segment end = SignalUtil.getSignalSegment(toSignal);
		if (start == null) {
			throw new RoutePathException("Cannot find position of entrance signal " + fromSignal);
		} else if (end == null) {
			throw new RoutePathException("Cannot find position of exit signal " + toSignal);
		}

		if (fromSignal instanceof LeftSignal && toSignal instanceof LeftSignal) {
			final SegmentPath path = new SegmentPath(Orientation.LEFT, start);
			buildRightPath(start, end, path, ignoredSignals, pointStates);
			return path;
		} else if (fromSignal instanceof RightSignal && toSignal instanceof RightSignal) {
			final SegmentPath path = new SegmentPath(Orientation.RIGHT, start);
			buildLeftPath(start, end, path, ignoredSignals, pointStates);
			return path;
		} else {
			return null;
		}
	}

	public SegmentPath walk(Signal fromSignal, Node toNode, Collection<String> ignoredSignals, Collection<String> pointStates)
			throws RoutePathException {
		final Segment start = SignalUtil.getSignalSegment(fromSignal);
		if (fromSignal instanceof LeftSignal) {
			final List<Segment> _end = NodeUtil.getIncomingSegments(toNode);
			if (_end.size() != 1) {
				throw new RoutePathException("Invalid end node");
			}
			final Segment end = _end.get(0);
			final SegmentPath path = new SegmentPath(Orientation.LEFT, start);
			buildRightPath(start, end, path, ignoredSignals, pointStates);
			return path;
		} else if (fromSignal instanceof RightSignal) {
			final List<Segment> _end = NodeUtil.getOutgoingSegments(toNode);
			if (_end.size() != 1) {
				throw new RoutePathException("Invalid end node");
			}
			final Segment end = _end.get(0);
			final SegmentPath path = new SegmentPath(Orientation.RIGHT, start);
			buildLeftPath(start, end, path, ignoredSignals, pointStates);
			return path;
		} else {
			return null;
		}
	}

	private SegmentPath walk(Node fromNode, Signal toSignal, Collection<String> ignoredSignals, Collection<String> pointStates)
			throws RoutePathException {

		final Segment end = SignalUtil.getSignalSegment(toSignal);
		if (toSignal instanceof LeftSignal) {
			final List<Segment> _start = NodeUtil.getIncomingSegments(fromNode);
			if (_start.size() != 1) {
				throw new RoutePathException("Invalid start node");
			}
			final Segment start = _start.get(0);
			final SegmentPath path = new SegmentPath(Orientation.LEFT, start);
			buildRightPath(start, end, path, ignoredSignals, pointStates);
			return path;
		} else if (toSignal instanceof RightSignal) {
			final List<Segment> _start = NodeUtil.getOutgoingSegments(fromNode);
			if (_start.size() != 1) {
				throw new RoutePathException("Invalid start node");
			}
			final Segment start = _start.get(0);
			final SegmentPath path = new SegmentPath(Orientation.RIGHT, start);
			buildLeftPath(start, end, path, ignoredSignals, pointStates);
			return path;
		} else {
			return null;
		}
	}

	public static SegmentPath walk(Node fromNode, Node toNode, Orientation orientation, Collection<String> ignoredSignals,
			Collection<String> pointStates) throws RoutePathException {

		final List<Segment> _start = NodeUtil.getIncomingSegments(fromNode);
		if (_start.size() != 1) {
			throw new RoutePathException("Invalid start node");
		}
		final Segment start = _start.get(0);
		final List<Segment> _end = NodeUtil.getIncomingSegments(toNode);
		if (_end.size() != 1) {
			throw new RoutePathException("Invalid end node");
		}
		final Segment end = _end.get(0);
		if (orientation == Orientation.LEFT) {
			final SegmentPath path = new SegmentPath(Orientation.LEFT, start);
			buildRightPath(start, end, path, ignoredSignals, pointStates);
			return path;
		} else if (orientation == Orientation.RIGHT) {
			final SegmentPath path = new SegmentPath(Orientation.RIGHT, start);
			buildLeftPath(start, end, path, ignoredSignals, pointStates);
			return path;
		} else {
			return null;
		}
	}

	/**
	 * Builds a segment path from a node to any first signal in a given direction
	 * 
	 * @param fromNode    starting node
	 * @param orientation , path orientation, left or right
	 * @return segment path, if found, or null
	 * @throws RoutePathException
	 */
	public static SegmentPath walk(Node fromNode, Orientation orientation, Collection<String> ignoredSignals,
			Collection<String> pointStates) throws RoutePathException {

		final List<Segment> _start = NodeUtil.getIncomingSegments(fromNode);
		if (_start.size() != 1) {
			throw new RoutePathException("Invalid start node");
		}
		final Segment start = _start.get(0);
		if (orientation == Orientation.LEFT) {
			final SegmentPath path = new SegmentPath(Orientation.LEFT, start);
			buildRightPath(start, null, path, ignoredSignals, pointStates);
			return path;
		} else if (orientation == Orientation.RIGHT) {
			final SegmentPath path = new SegmentPath(Orientation.RIGHT, start);
			buildLeftPath(start, null, path, ignoredSignals, pointStates);
			return path;
		} else {
			return null;
		}
	}

	private Rectangle getNodeBounds(safecap.schema.Node node) {
		if (node != null) {
			final IGraphicalEditPart ep = (IGraphicalEditPart) projectPart.findEditPart(projectPart, node);
			return ep.getFigure().getBounds();
		} else {
			return null;
		}
	}

	private Rectangle getSignalBounds(Signal signal) {
		if (signal != null) {
			final IGraphicalEditPart ep = (IGraphicalEditPart) projectPart.findEditPart(projectPart, signal);
			return ep.getFigure().getBounds();
		} else {
			return null;
		}
	}

	@Override
	public void error(String message) {
		errors.append(message);
		errors.append("\n");
	}

	public static void weakNormalise(Project project, Labeled element) {
		String id = element.getLabel();
		if (id != null && SchemaConfig.isApplicable(project, element)) {
			final int bracket_open = id.indexOf('(');
			final int bracket_close = id.indexOf(')');
			if (bracket_open > 0 && bracket_open < bracket_close) {
				final String flag = id.substring(bracket_open + 1, bracket_close);
				id = id.substring(0, bracket_open);
				element.setLabel(id);
				if (element instanceof Ambit && flag.equals("X")) {
					final Ambit ambit = (Ambit) element;
					ExtensionUtil.delete(ambit, SafecapConstants.EXT_AMBIT, SafecapConstants.TRACK_AXLE_COUNTER);
					ambit.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_AMBIT, SafecapConstants.TRACK_AXLE_COUNTER, true));
				} else if (element instanceof Signal && flag.equals("W")) {
					final Signal signal = (Signal) element;
					ExtensionUtil.delete(signal, SafecapConstants.EXT_SIGNAL, SafecapConstants.SIGNAL_WARNER);
					signal.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_SIGNAL, SafecapConstants.SIGNAL_WARNER, true));
				}
			}
		}
	}

	public static void rebuildJunctionPointAssociation(Project root) {
		for (final Ambit ambit : root.getAmbits()) {
			if (ambit instanceof Junction) {
				final Junction junction = (Junction) ambit;
				processJunctionPointAssociation(junction);
			}
		}
	}

	private static void processJunctionPointAssociation(Junction junction) {
		final List<Node> point_nodes = new ArrayList<>();

		for (final Segment segment : junction.getSegments()) {
			final Node left = segment.getFrom();
			final Node right = segment.getTo();

			if (left != null && !point_nodes.contains(left) && NodeUtil.isJunctionNode(left)) {
				point_nodes.add(left);
			}

			if (right != null && !point_nodes.contains(right) && NodeUtil.isJunctionNode(right)) {
				point_nodes.add(right);
			}
		}

		final List<Point> toremove = new ArrayList<>();

		for (final Point point : junction.getPoints()) {
			if (point.getNode() == null) {
				toremove.add(point);
			} else {
				point_nodes.remove(point.getNode());
			}
		}

		junction.getPoints().removeAll(toremove);

		// create new points
		for (final Node node : point_nodes) {
			final Point point = ModelFactory.eINSTANCE.createPoint();
			point.setNode(node);
			junction.getPoints().add(point);
		}
	}

}
