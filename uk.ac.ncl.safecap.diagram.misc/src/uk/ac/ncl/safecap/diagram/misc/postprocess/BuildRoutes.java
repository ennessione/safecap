package uk.ac.ncl.safecap.diagram.misc.postprocess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import safecap.Labeled;
import safecap.Orientation;
import safecap.Project;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.model.Line;
import safecap.model.ModelFactory;
import safecap.model.Point;
import safecap.model.Route;
import safecap.schema.Node;
import safecap.schema.Segment;
import safecap.trackside.Signal;
import safecap.trackside.SpeedLimit;
import safecap.trackside.TracksideFactory;
import safecap.trackside.Wire;
import uk.ac.ncl.safecap.misc.core.AmbitPath;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.EquipmentUtil;
import uk.ac.ncl.safecap.misc.core.LineUtil;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.PointConf;
import uk.ac.ncl.safecap.misc.core.RouteUtil;
import uk.ac.ncl.safecap.misc.core.ScopeB;
import uk.ac.ncl.safecap.misc.core.SegmentPath;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;
import uk.ac.ncl.safecap.misc.core.SignalUtil;
import uk.ac.ncl.safecap.misc.core.SpeedLimitUtil;

@SuppressWarnings("unused")
public class BuildRoutes {
	private final Project root;
	private final IGraphicalEditPart projectPart;
	private static final String PROVENANCE = "core.ROUTE_BUILDER";
	private static final int MIN_ROUTE_LENGTH = 500; // meters
	public BuildRoutes(IGraphicalEditPart projectPart, Project root) {
		this.root = root;
		this.projectPart = projectPart;
	}

	public static void doAutoRoutes(IGraphicalEditPart projectPart, Project root, IProgressMonitor monitor) {
		final BuildRoutes cmd = new BuildRoutes(projectPart, root);
		cmd.runBuildAutoRoutes(monitor);
	}

	public static void doSignalRoutes(Project root, IProgressMonitor monitor) {
		final BuildRoutes cmd = new BuildRoutes(null, root);
		cmd.buildSignalRoutes(monitor);
	}

	public void runBuildAutoRoutes(IProgressMonitor monitor) {
		final CompositeCommand compc = new CompositeCommand("BuildAutoRoutes");
		compc.add(getBuildAutoRoutesCommand());
		if (projectPart != null) {
			compc.add(PositionGeneratedEquipment.getPositionGeneratedEquipmentCommand(projectPart, root, PROVENANCE));
		}
		try {
			compc.execute(monitor, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	public void buildSignalRoutes(IProgressMonitor monitor) {
		try {
			getBuildSignalRoutesCommand().execute(monitor, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getBuildAutoRoutesCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		// System.out.println("BuildRoutes: Domain for " + root + " is " + domain);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "BuildAutoRoutes", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				buildAutoRoutes();
				buildSignalRoutes();
				return CommandResult.newOKCommandResult();
			}

		};

		return command;
	}

	private AbstractTransactionalCommand getBuildSignalRoutesCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		// System.out.println("BuildRoutes: Domain for " + root + " is " + domain);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "BuildSignalRoutes", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				buildSignalRoutes();
				return CommandResult.newOKCommandResult();
			}

		};

		return command;
	}

	protected void buildAutoRoutes() {
		// remove all routes
		root.getRoutes().clear();

		// remove all lines
		root.getLines().clear();

		// remove all generated trackside equipment
		EquipmentUtil.removeEquipment(EquipmentUtil.getEquipmentByOrigin(root, PROVENANCE));

		// clear overlap flags
//		for (Node n : root.getNodes())
//			n.setRoles(n.getRoles() & ~(NodeRole.LEFT_OVERLAP_VALUE | NodeRole.RIGHT_OVERLAP_VALUE));

		computeRoutes();

		placeSignals();

		// placeSpeedLimits();
	}

	private void placeSignals() {

		// clean up lines with stale routes
		final List<Line> toremove_lines = new ArrayList<>();
		for (final Line l : root.getLines()) {
			for (final Route r : l.getRoutes()) {
				if (r == null || !root.getRoutes().contains(r)) {
					toremove_lines.add(l);
				}
			}
		}
		root.getLines().removeAll(toremove_lines);

		for (final Line l : root.getLines()) {
			for (int i = 0; i < l.getRoutes().size() - 1; i++) {
				final Route current = l.getRoutes().get(i);
				final Route next = l.getRoutes().get(i + 1);
				final Segment c_last = current.getSegments().get(current.getSegments().size() - 1);
				if (l.getOrientation() == Orientation.LEFT) {
					Signal signal = SignalUtil.getLeftSignalOn(c_last);
					if (signal == null) {
						signal = TracksideFactory.eINSTANCE.createLeftSignal();
						signal.setLabel(ScopeB.getUniqueName(root, "S" + c_last.getTo().getLabel()));
						signal.setOrigin(PROVENANCE);
						final Wire wire = TracksideFactory.eINSTANCE.createWire();
						wire.setFrom(signal);
						wire.setTo(c_last);
						root.getEquipment().add(signal);
						root.getWires().add(wire);
					}
				} else {
					Signal signal = SignalUtil.getRightSignalOn(c_last);
					if (signal == null) {
						signal = TracksideFactory.eINSTANCE.createRightSignal();
						signal.setLabel(ScopeB.getUniqueName(root, "S" + c_last.getFrom().getLabel()));
						signal.setOrigin(PROVENANCE);
						final Wire wire = TracksideFactory.eINSTANCE.createWire();
						wire.setFrom(signal);
						wire.setTo(c_last);
						root.getEquipment().add(signal);
						root.getWires().add(wire);
					}
				}
			}
		}
	}

//	private void placeSpeedLimits() {
//		for (final Line l : root.getLines()) {
//			Ambit prev_not_junction = null;
//			for (final Route r : l.getRoutes()) {
//				boolean is_under_limit = false;
//				final PointConf conf = new PointConf(r);
//				for (final Ambit a : r.getAmbits()) {
//					boolean this_has_limit = false;
//
//					// speed limit starts
//					if (a instanceof Junction && prev_not_junction != null && !is_under_limit) {
//						final Junction j = (Junction) a;
//						boolean has_reverse = false;
//						for (final Point p : j.getPoints()) {
//							if (conf.getReverse().contains(p)) {
//								has_reverse = true;
//								break;
//							}
//						}
//
//						if (has_reverse) {
//							placeJunctionSpeedLimit(l, prev_not_junction.getSegments().get(prev_not_junction.getSegments().size() - 1),
//									JUNCTION_SPEED_LIMIT);
//							is_under_limit = true;
//							this_has_limit = true;
//						}
//
//					}
//
//					// speed limit ends
//					if (is_under_limit && !this_has_limit && !(a instanceof Junction)) {
//						placeJunctionSpeedLimit(l, a.getSegments().get(a.getSegments().size() - 1), LINE_SPEED_LIMIT);
//						is_under_limit = false;
//					}
//
//					if (!(a instanceof Junction)) {
//						prev_not_junction = a;
//					}
//				}
//			}
//		}
//	}

	private void placeJunctionSpeedLimit(Line line, Segment segment, int speed) {
		SpeedLimit limit = null;

		if (line.getOrientation() == Orientation.LEFT) {
			limit = SpeedLimitUtil.getLeftSpeedLimitOn(segment);
		} else {
			limit = SpeedLimitUtil.getRightSpeedLimitOn(segment);
		}

		if (limit == null || limit.getLimit() != speed || !PROVENANCE.equals(limit.getOrigin())) {
			if (line.getOrientation() == Orientation.LEFT) {
				limit = TracksideFactory.eINSTANCE.createLeftSpeedLimit();
			} else {
				limit = TracksideFactory.eINSTANCE.createRightSpeedLimit();
			}
			limit.getLine().add(line);
			limit.setLimit(speed);
			limit.setOrigin(PROVENANCE);
			final Wire wire = TracksideFactory.eINSTANCE.createWire();
			wire.setFrom(limit);
			wire.setTo(segment);
			root.getEquipment().add(limit);
			root.getWires().add(wire);
		} else {
			limit.getLine().add(line);
		}
	}

	private void computeRoutes() {
		final List<Node> entries = NodeUtil.getSourceNodes(root);
		for (final Node e : entries) {
			computeNodeRoutes(e);
		}
	}

	private void computeNodeRoutes(Node entry) {

		final List<SegmentPath> paths = SignalUtil.getRouteSegmentPaths(entry, false);
		for (final SegmentPath path : paths) {
			if (path.getTo() instanceof Node) {
				final Node boundary = (Node) path.getTo();
				if (NodeUtil.isSink(boundary)) {
					computePathRoutes(path);
				}
			}
		}

		for (final SegmentPath path : paths) {
			final Line l = buildLineFor(path);
			if (l != null) {
				root.getLines().add(l);
			}
		}
	}

	private Node getOrientedEnd(Orientation orientation, Segment segment) {
		if (orientation == Orientation.LEFT) {
			return segment.getTo();
		} else {
			return segment.getFrom();
		}
	}

	private void computePathRoutes(SegmentPath path) {
		final List<SegmentPath> routes = new ArrayList<>();

		SegmentPath current = new SegmentPath(path.getOrientation(), path.getFrom());

		int sections_to_add = 1;
		int current_length = 0;

		for (final Segment s : path.getPath()) {
			current_length += s.getLength();

			// do not end a route on junction
			if (current_length >= MIN_ROUTE_LENGTH && !SegmentUtil.isJunctionPart(s)) {
				sections_to_add--;
			}

			if (sections_to_add == 0 && current_length >= MIN_ROUTE_LENGTH) {
				current.getPath().add(s);

				final Node to = getOrientedEnd(path.getOrientation(), s);

				current.setTo(to);

				fillRouteNames(current);

				routes.add(current);
				sections_to_add = 1;
				current_length = 0;
				current = new SegmentPath(path.getOrientation(), to);
			} else {
				current.getPath().add(s);
			}
		}

		if (current.getPath().size() > 0) {
			final Segment last = path.getPath().get(path.getPath().size() - 1);
			final Node to = getOrientedEnd(path.getOrientation(), last);
			current.setTo(to);
			routes.add(current);
		}

		final List<Route> rr = mkRoutes(routes);

		// makeNewLine(rr, path);
	}

	Set<Segment> route_boundary;

	/**
	 * Detect and split routes that do not run to signal boundaries
	 */
	private void splitPatalogicRoutes() {
		route_boundary = new HashSet<>();
		for (final Route r : root.getRoutes()) {
			route_boundary.add(r.getSegments().get(0));
			route_boundary.add(r.getSegments().get(r.getSegments().size() - 1));
		}

		for (final Route r : root.getRoutes()) {
			splitPatalogicRoute(r);
		}
	}

	private void splitPatalogicRoute(Route r) {
		for (int i = 1; i < r.getSegments().size() - 2; i++) {
			final Segment s = r.getSegments().get(i);
			if (route_boundary.contains(s)) {

			}
		}
	}

	private List<Route> mkRoutes(List<SegmentPath> routes) {
		final Map<Segment, Ambit> map = AmbitUtil.getSegmentAmbitMap(root);
		final List<Route> result = new ArrayList<>(routes.size());

		for (final SegmentPath r : routes) {
			final AmbitPath ambit_path = new AmbitPath(r, map, AmbitUtil.getAmbitToComposedMap(root));
			final String name = r.getCanonicalName();
			final Route existing = RouteUtil.getByLabel(root, name);
			if (existing != null && existing.getSegments().equals(r.getPath())) {
				result.add(existing);
			} else {
				final Route new_route = createNewRoute(ambit_path, r.getCanonicalName());
				result.add(new_route);

			}

		}

		return result;
	}

	private void makeNewLine(List<Route> routes, SegmentPath path) {
		final Line line = ModelFactory.eINSTANCE.createLine();
		final String name = LineUtil.getUniqueName(root, path.getCanonicalName());
		line.setLabel(name);
		line.getRoutes().addAll(routes);
		line.setOrientation(path.getOrientation());
		root.getLines().add(line);
	}

	private void fillRouteNames(SegmentPath current) {
		final Labeled a = current.getFrom();
		final Labeled b = current.getTo();

		if (a.getLabel() == null || a.getLabel().length() == 0) {
			a.setLabel(ScopeB.getUniqueName(root, "N"));
		}

		if (b.getLabel() == null || b.getLabel().length() == 0) {
			b.setLabel(ScopeB.getUniqueName(root, "N"));
		}
	}

	private void buildSignalRoutes() {
		final List<AmbitPath> _routes = SignalUtil.getAllRoutes(root);
		final List<Route> actual = new ArrayList<>(root.getRoutes().size());

		final List<AmbitPath> routes = new ArrayList<>();

		// compute route name version
		for (final AmbitPath path : _routes) {
			int max_version = -1;
			for (final AmbitPath p : routes) {
				if (path.getBaseName().equals(p.getBaseName())) {
					max_version = p.getVersion();
				}
			}

			path.setVersion(max_version + 1);
			routes.add(path);
		}

		// System.out.println("All routes: " + routes);

		for (final AmbitPath path : routes) {

			final String name = path.getCanonicalName();

			final Route r = RouteUtil.getByLabel(root, name);
			if (r != null) {
				if (r.getAmbits().equals(path.getPath()) && r.getSegments().equals(path.getSegmentPath())
						&& r.getOrientation() == path.getOrientation()) {
					// System.out.println("Skipping route " + r.getLabel() + " " + r.getAmbits());
					actual.add(r);
					continue;
				} else {
					root.getRoutes().remove(r);
				}
			}

			// create new route
			final Route newr = createNewRoute(path, name);
			// System.out.println("Adding route " + newr.getLabel() + " " +
			// newr.getAmbits());
			actual.add(newr);
		}

		root.getRoutes().retainAll(actual);

		refreshLines();
	}

	public void refreshLines() {
		final List<Node> entries = NodeUtil.getSourceNodes(root);

		for (final Node entry : entries) {
			// System.out.println("## node " + entry.getLabel());
			final List<SegmentPath> paths = SignalUtil.getRouteSegmentPaths(entry, true);
			for (final SegmentPath path : paths) {
				if (path.getTo() instanceof Node) {
					final Node boundary = (Node) path.getTo();
					if (NodeUtil.isSink(boundary)) {
						final Line l = buildLineFor(path);
						if (l != null && !hasMatchingLine(l)) {
							root.getLines().add(l);
							// System.out.println("Added line " + l.getLabel());
						}
					}
				}
			}
		}
	}

	private boolean hasMatchingLine(Line line) {
		assert line != null;

		for (final Line l : root.getLines()) {
			if ((l.getLabel().startsWith(line.getLabel()) || line.getLabel().startsWith(l.getLabel()))
					&& l.getRoutes().equals(line.getRoutes()) && l.getOrientation() == line.getOrientation()) {
				return true;
			}
		}

		return false;
	}

	private Line buildLineFor(SegmentPath path) {
		int i = 0;
		final Line line = ModelFactory.eINSTANCE.createLine();
		final String name = LineUtil.getUniqueName(root, path.getCanonicalName());
		line.setLabel(name);
		line.setOrientation(path.getOrientation());
		while (i < path.getPath().size()) {
			final Route route = getMatchingRoute(path.getOrientation(), path.getPath(), i); // BUG!!
			if (route == null) {
				return null;
			}
			i += route.getSegments().size();
			line.getRoutes().add(route);
		}

		return line;
	}

	private Route getMatchingRoute(Orientation orientation, List<Segment> path, int i) {
		for (final Route r : root.getRoutes()) {
			if (r.getOrientation() == orientation && r.getSegments().size() <= path.size() - i) {
				boolean matches = true;
				for (int j = 0; j < r.getSegments().size(); j++) {
					if (r.getSegments().get(j) != path.get(i + j)) {
						matches = false;
						break;
					}
				}

				if (matches) {
					return r;
				}
			}
		}
		return null;
	}

	private Route createNewRoute(AmbitPath path, String name) {
		final String base = name;
		int index = 1;
		while (RouteUtil.getByLabel(root, name) != null) {
			name = base + "(" + index++ + ")";
		}

		final Route newr = ModelFactory.eINSTANCE.createRoute();
		newr.setLabel(name);
		newr.getAmbits().addAll(path.getPath());
		newr.getSegments().addAll(path.getSegmentPath());
		newr.setOrientation(path.getOrientation());
		root.getRoutes().add(newr);
		// System.out.println("Added route " + newr.getLabel() + " " + newr.getAmbits()
		// + " " + newr.getSegments() + " ovr:" + newr.getOverlap());
		return newr;
	}
}
