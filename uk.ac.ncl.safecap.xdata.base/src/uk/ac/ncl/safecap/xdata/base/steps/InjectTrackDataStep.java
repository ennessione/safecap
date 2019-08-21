package uk.ac.ncl.safecap.xdata.base.steps;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.xml.sax.SAXException;

import safecap.Orientation;
import safecap.Project;
import safecap.derived.MergedAmbit;
import safecap.derived.MergedPoint;
import safecap.extension.ExtAttribute;
import safecap.extension.ExtAttributeStr;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.model.Point;
import safecap.model.Route;
import safecap.model.Rule;
import safecap.model.TimedOccupationRule;
import safecap.schema.Node;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.GenericLocatedEquipment;
import safecap.trackside.LeftSignal;
import safecap.trackside.RightSignal;
import safecap.trackside.Signal;
import uk.ac.ncl.prime.sim.sets.BMap;
import uk.ac.ncl.safecap.mcommon.conf.RouteConfig;
import uk.ac.ncl.safecap.misc.core.AmbitPath;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.ISegmentWalkerFilter;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.Overlap;
import uk.ac.ncl.safecap.misc.core.OverlapUtil;
import uk.ac.ncl.safecap.misc.core.PointConf;
import uk.ac.ncl.safecap.misc.core.PointUtil;
import uk.ac.ncl.safecap.misc.core.RouteUtil;
import uk.ac.ncl.safecap.misc.core.RuleUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.misc.core.SegmentPath;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;
import uk.ac.ncl.safecap.misc.core.SignalUtil;
import uk.ac.ncl.safecap.misc.core.SubOverlap;
import uk.ac.ncl.safecap.misc.core.SubOverlapUtil;
import uk.ac.ncl.safecap.misc.core.SubRoute;
import uk.ac.ncl.safecap.misc.core.SubRouteUtil;
import uk.ac.ncl.safecap.xmldata.Block;
import uk.ac.ncl.safecap.xmldata.DataConfigurationException;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataException;
import uk.ac.ncl.safecap.xmldata.DataNamespace;
import uk.ac.ncl.safecap.xmldata.ValueList;

public class InjectTrackDataStep {

	public static void execute(DataContext data, safecap.Project prj)
			throws DataException, DataConfigurationException, SAXException, IOException, ParserConfigurationException {

		if (data == null || prj == null) {
			return;
		}

		injectRouteInfo(data, prj);
		injectPointInfo(data, prj);
		injectSignalInfo(data, prj);
		injectAmbitInfo(data, prj);
		injectSubRouteInfo(data, prj);
		injectEquipmentInfo(data, prj);
		// injectControlTableInfo(data, prj);

	}

	private static void injectEquipmentInfo(DataContext context, Project prj) {
		final DataNamespace safecap = new DataNamespace("safecap.equipment.generic", "safecap.equipment.generic");
		for (final Equipment eqp : prj.getEquipment()) {
			if (eqp instanceof GenericLocatedEquipment) {
				injectGenericLocatedEquipmentInfo(safecap, (GenericLocatedEquipment) eqp);
			}
		}

		context.addNamespace(safecap);
	}

	private static void injectGenericLocatedEquipmentInfo(DataNamespace safecap, GenericLocatedEquipment eqp) {

		final Block block = safecap.getBlockByNameOrNew("generic", eqp.getLabel());

		block.enterAttribute("type");
		block.enterValue(eqp.getType());

		block.enterAttribute("offset");
		block.enterValue(eqp.getOffset());

		block.enterAttribute("dir");
		block.enterValue(eqp.getOrientation().getName());

		final Segment segment = eqp.getSegment();

		if (segment != null) {

			if (eqp.getOrientation() == Orientation.RIGHT) {
				if (segment.getFrom().getLabel() != null) {
					block.enterAttribute("joint");
					block.enterValue(segment.getFrom().getLabel());
				}
			} else {
				if (segment.getTo().getLabel() != null) {
					block.enterAttribute("joint");
					block.enterValue(segment.getTo().getLabel());
				}

			}

			final Ambit ambit = SegmentUtil.getSegmentAmbit(eqp.getSegment());

			if (ambit != null) {
				block.enterAttribute("track");
				block.enterValue(ambit.getLabel());
			}
		}

	}

	public static void injectControlTableInfo(DataContext context, Project prj) {
		final DataNamespace safecap = new DataNamespace("safecap.control", "safecap.control");
		for (final Route route : prj.getRoutes()) {
			injectCTRouteInfo(safecap, route);
		}

		injectCTPointInfo(safecap, prj);

		context.addNamespace(safecap);

	}

	private static void injectCTPointInfo(DataNamespace safecap, Project prj) {

		for (final Rule rule : RuleUtil.getAllPointRules(prj)) {
			final String pointLabel = ((Point) rule.eContainer()).getNode().getLabel();
			final Block block = safecap.getBlockByNameOrNew("point", pointLabel);
			for (final Ambit ambit : rule.getClear()) {
				block.enterAttribute("ct:point:clear");
				block.enterValue(ambit.getLabel());
			}
		}
	}

	private static void injectCTRouteInfo(DataNamespace safecap, Route route) {
		if (RuleUtil.getDefaultLogic(route).getRule() != null) {
			final List<Rule> rules = RuleUtil.getRouteAspectRules(route);
			if (!rules.isEmpty()) {
				final Rule main = rules.get(0);
				final Block block = safecap.getBlockByNameOrNew("route", route.getLabel());
				for (final Ambit ambit : main.getClear()) {
					block.enterAttribute("ct:route:clear");
					block.enterValue(ambit.getLabel());
				}
				for (final Point point : main.getNormal()) {
					block.enterAttribute("ct:route:normal");
					block.enterValue(point.getNode().getLabel());
				}
				for (final Point point : main.getReverse()) {
					block.enterAttribute("ct:route:reverse");
					block.enterValue(point.getNode().getLabel());
				}
				for (final TimedOccupationRule tambit : main.getOccupied()) {
					block.enterAttribute("ct:route:occ");
					block.enterValue(tambit.getAmbit().getLabel());
				}
			}
		}
	}

	private static void injectAmbitInfo(DataContext context, Project prj) {
		final DataNamespace safecap = new DataNamespace("safecap.track.derived", "safecap.track.derived");
		for (final Ambit ambit : prj.getAmbits()) {
			injectAmbitInfo(safecap, ambit);
		}

		for (final MergedAmbit mambit : AmbitUtil.getMergedAmbits(prj)) {
			injectMergedAmbitInfo(safecap, mambit);
			if (mambit.isComposed()) {
				injectComposedAmbitInfo(safecap, mambit);
			}
			if (mambit.isDisjoint()) {
				injectDisjointAmbitInfo(safecap, mambit);
			}
		}

		context.addNamespace(safecap);
	}

	private static void injectComposedAmbitInfo(DataNamespace safecap, MergedAmbit mambit) {
		final Block block = safecap.getBlockByNameOrNew("Track", mambit.getLabel());
		block.enterAttribute("track:composed");
		block.enterValue("true");
	}

	private static void injectDisjointAmbitInfo(DataNamespace safecap, MergedAmbit mambit) {
		final Block block = safecap.getBlockByNameOrNew("Track", mambit.getLabel());
		block.enterAttribute("track:disjoint");
		block.enterValue("true");
	}

	private static void injectSubRouteInfo(DataContext data, Project project) {
		final DataNamespace safecap = new DataNamespace("safecap.subroute.derived", "safecap.subroute.derived");
		final Set<String> handled = new HashSet<>();
		final Set<String> orientations = new HashSet<>();
		for (final Ambit ambit : project.getAmbits()) {
			final Collection<ExtAttribute> allAttributes = ExtensionUtil.getAll(ambit, SafecapConstants.EXT_ORIENTATION_DOMAIN);
			for (final ExtAttribute attr : allAttributes) {
				if (attr instanceof ExtAttributeStr) {
					final String subroute = "U" + ambit.getLabel() + "-" + ((ExtAttributeStr) attr).getValue();
					if (!handled.contains(subroute)) {
						handled.add(subroute);
						orientations.add(((ExtAttributeStr) attr).getValue());
					}
				}
			}
		}

		for (final String s : orientations) {
			final Block block = safecap.getBlockByNameOrNew("SubRouteOrientation", s);
			block.enterAttribute("subroute:opposite");
			block.enterValue(new StringBuilder(s).reverse().toString());
		}

		final Map<SubRoute, Set<SubRoute>> subroute_graph = SubRouteUtil.getSubRouteGraph(project);
		final Collection<SubRoute> all_subroutes = new HashSet<>();
		all_subroutes.addAll(subroute_graph.keySet());
		for (final SubRoute subroute : subroute_graph.keySet()) {
			final Block block = safecap.getBlockByNameOrNew("SubRouteGraph", subroute.toString());
			all_subroutes.addAll(subroute_graph.get(subroute));
			for (final SubRoute next : subroute_graph.get(subroute)) {
				block.enterAttribute("subroute:next");
				block.enterValue(next.toString());
			}

		}

		for (final SubRoute subroute : all_subroutes) {
			Block block = safecap.getBlockByNameOrNew("SubRouteMap", subroute.toString());
			block.enterAttribute("subroute:track");
			block.enterValue(subroute.getAmbit().getLabel());
			block.enterAttribute("subroute:path");
			block.enterValue(subroute.getDirection());
			block.enterAttribute("subroute:len");
			block.enterValue(subroute.getLength());
			block.enterAttribute("subroute:from");
			block.enterValue(subroute.getFirstNode().getLabel());
			block.enterAttribute("subroute:to");
			block.enterValue(subroute.getLastNode().getLabel());
			block.enterAttribute("subroute:direction");
			block.enterValue(subroute.getOrientation().getName());

			final List<Node> spath = SubRouteUtil.getNodePath(subroute);

			PointConf conf = null;
			if (spath != null) {
				conf = new PointConf(project, SubRouteUtil.getNodePath(subroute));
				for (final Point point : conf.getNormal()) {
					block.enterAttribute("subroute:pointnormal");
					block.enterValue(point.getNode().getLabel());
				}

				for (final Point point : conf.getReverse()) {
					block.enterAttribute("subroute:pointreverse");
					block.enterValue(point.getNode().getLabel());
				}
			} else {
				System.err.println("Suspicious subroute " + subroute.toString());
			}

			if (SubOverlapUtil.hasSubOverlaps(subroute)) {
				for (final SubOverlap suboverlap : SubOverlapUtil.getSubOverlap(subroute)) {
					block = safecap.getBlockByNameOrNew("SubOverlapMap", suboverlap.toString());
					block.enterAttribute("suboverlap:track");
					block.enterValue(subroute.getAmbit().getLabel());
					block.enterAttribute("suboverlap:subroute");
					block.enterValue(subroute.toString());
					block.enterAttribute("suboverlap:path");
					block.enterValue(subroute.getDirection());
					block.enterAttribute("suboverlap:len");
					block.enterValue(subroute.getLength());
					block.enterAttribute("suboverlap:from");
					block.enterValue(subroute.getFirstNode().getLabel());
					block.enterAttribute("suboverlap:to");
					block.enterValue(subroute.getLastNode().getLabel());
					block.enterAttribute("suboverlap:direction");
					block.enterValue(subroute.getOrientation().getName());

					if (conf != null) {
						for (final Point point : conf.getNormal()) {
							block.enterAttribute("suboverlap:pointnormal");
							block.enterValue(point.getNode().getLabel());
						}

						for (final Point point : conf.getReverse()) {
							block.enterAttribute("suboverlap:pointreverse");
							block.enterValue(point.getNode().getLabel());
						}
					}
				}
			}
		}

		for (final Equipment s : project.getEquipment()) {
			if (s instanceof Signal) {
				final Signal signal = (Signal) s;
				if (signal.getLabel() != null) {
					final Block block = safecap.getBlockByNameOrNew("Signal", signal.getLabel());
					int k = 0;
					try {
						for (final Overlap overlap : OverlapUtil.getOverlaps(signal)) {
							final List<SubOverlap> so_list = OverlapUtil.getOverlapSubOverlaps(project, overlap);
							if (so_list == null) {
								System.err.println("Overlap " + overlap.toString() + " is broken");
								continue;
							}
							block.enterAttribute("signal:suboverlaps");
							final ValueList vl = new ValueList(so_list.size(), true);
							for (final SubOverlap so : so_list) {
								vl.add(so.toString());
							}
							block.enterValue(new ValueList(k, vl));

							for (final SubOverlap so : so_list) {
								block.enterAttribute("signal:suboverlapset");
								block.enterValue(so.toString());
							}

							block.enterAttribute("signal:overlaps");
							block.enterValue(overlap.getName());

							if (so_list.size() > 1) {
								for (int i = 1; i < so_list.size(); i++) {
									final Block block2 = safecap.getBlockByNameOrNew("SubOverlapMap", so_list.get(i).toString());
									block2.enterAttribute("suboverlap:prev");
									block2.enterValue(so_list.get(i - 1).toString());
								}
							}
							final Block block2 = safecap.getBlockByNameOrNew("SubOverlapMap", so_list.get(0).toString());
							block2.enterAttribute("suboverlap:signal");
							block2.enterValue(signal.getLabel());
							k++;
						}
					} catch (final Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		for (final Overlap overlap : OverlapUtil.getAllOverlaps(project)) {
			final Block block = safecap.getBlockByNameOrNew("overlap", overlap.getName());
			block.enterAttribute("overlap:len");
			block.enterValue(overlap.getLength());
			block.enterAttribute("overlap:full");
			block.enterValue(overlap.isFull());
		}

		for (final Overlap overlap : OverlapUtil.getAllOverlaps(project)) {
			final List<SubOverlap> so_list = OverlapUtil.getOverlapSubOverlaps(project, overlap);
			if (so_list != null) {
				int q = 0;
				for (final SubOverlap so : so_list) {
					if (so != null) {
						final Block block = safecap.getBlockByNameOrNew("overlap", overlap.getName());
						block.enterAttribute("overlap:suboverlapset");
						block.enterValue(so.toString());
						block.enterAttribute("overlap:suboverlaps");
						block.enterValue(new ValueList(q, so.toString()));
						q++;
					}
				}
			}
		}

		data.addNamespace(safecap);
	}

	private static void injectMergedAmbitInfo(DataNamespace safecap, MergedAmbit mambit) {
		final Block block = safecap.getBlockByNameOrNew("Track", mambit.getLabel());
		for (final Ambit ambit : mambit.getAmbits()) {
			block.enterAttribute("track:merged");
			block.enterValue(ambit.getLabel());
		}
	}

	private static void injectAmbitInfo(DataNamespace safecap, Ambit ambit) {
		injectAmbitNextPrev(safecap, ambit);
		injectAmbitFlags(safecap, ambit);
	}

	private static void injectAmbitNextPrev(DataNamespace safecap, Ambit ambit) {
		final Block block = safecap.getBlockByNameOrNew("Track", ambit.getLabel());
		for (final Ambit next : AmbitUtil.getNextAmbitsExclusive(ambit)) {
			block.enterAttribute("track:next");
			block.enterValue(next.getLabel());
		}
	}

	private static void injectAmbitFlags(DataNamespace safecap, Ambit ambit) {
		final Block block = safecap.getBlockByNameOrNew("Track", ambit.getLabel());
		if (AmbitUtil.isAxleCounter(ambit)) {
			block.enterAttribute("track:axlecounter");
			block.enterValue(true);
		}
	}

	private static void injectSignalInfo(DataContext context, Project prj) {
		final Map<Segment, Ambit> map = AmbitUtil.getSegmentAmbitMap(prj);
		final DataNamespace safecap = new DataNamespace("safecap.signal.derived", "safecap.signal.derived");
		for (final Equipment eq : prj.getEquipment()) {
			if (eq instanceof Signal) {
				final Signal signal = (Signal) eq;
				if (signal.getLabel() != null) {
					injectSignalInfo(prj, safecap, signal, map);
				}
			}
		}

		context.addNamespace(safecap);
	}

	private static void injectSignalInfo(Project prj, DataNamespace safecap, Signal signal, Map<Segment, Ambit> map) {
		injectSignalProtectedRoutes(safecap, signal);
		injectSignalPosition(prj, safecap, signal, map);
		injectSignalDirection(safecap, signal);
		injectSignalType(safecap, signal);
	}

	private static void injectSignalType(DataNamespace safecap, Signal signal) {
		final Block block = safecap.getBlockByNameOrNew("Signal", signal.getLabel());
		block.enterAttribute("signal:type");
		block.enterValue("ST_" + signal.getType().toString());
	}

	private static void injectSignalDirection(DataNamespace safecap, Signal signal) {
		final Block block = safecap.getBlockByNameOrNew("Signal", signal.getLabel());
		block.enterAttribute("signal:direction");

		if (signal instanceof LeftSignal) {
			block.enterValue("Left");
		} else if (signal instanceof RightSignal) {
			block.enterValue("Right");
		}
	}

	private static void injectSignalProtectedRoutes(DataNamespace safecap, Signal signal) {
		final Block block = safecap.getBlockByNameOrNew("Signal", signal.getLabel());
		for (final Route route : SignalUtil.getRoutesProtectedBy(signal)) {
			block.enterAttribute("signal:routes");
			block.enterValue(route.getLabel());
		}
	}

	private static void injectSignalPosition(Project root, DataNamespace safecap, Signal signal, Map<Segment, Ambit> map) {
		final Segment segment = SignalUtil.getSignalSegment(signal);
		if (segment != null && map.containsKey(segment)) {
			final Block block = safecap.getBlockByNameOrNew("Signal", signal.getLabel());
			block.enterAttribute("signal:track");
			block.enterValue(map.get(segment).getLabel());
			block.enterAttribute("signal:offset");
			block.enterValue(SignalUtil.getSignalOffsetFromJoint(root, signal));
			block.enterAttribute("signal:joint");
			block.enterValue(SignalUtil.getSignalNode(signal).getLabel());
		}
	}

	private static void injectPointInfo(DataContext context, Project prj) {
		final DataNamespace safecap = new DataNamespace("safecap.point.derived", "safecap.point.derived");
		for (final Ambit ambit : prj.getAmbits()) {
			if (ambit instanceof Junction) {
				final Junction junction = (Junction) ambit;
				for (final Point point : junction.getPoints()) {
					injectPointInfo(safecap, point);
				}
			}
		}

		for (final MergedPoint mpoint : prj.getMergedpoints()) {
			injectMergedPointInfo(prj, safecap, mpoint);
		}

		context.addNamespace(safecap);
	}

	private static void injectMergedPointInfo(Project prj, DataNamespace safecap, MergedPoint mpoint) {
		// injectMergedPointClearTracks(prj, safecap, mpoint);
		injectMergedPointDefinition(prj, safecap, mpoint);
	}

	private static void injectMergedPointClearTracks(Project prj, DataNamespace safecap, MergedPoint point) {
		final Block block = safecap.getBlockByNameOrNew("Point", point.getLabel());

		for (final Point p : point.getPoints()) {
			if (p.eContainer() instanceof Junction) {
				final Junction junction = (Junction) p.eContainer();
				block.enterAttribute("point:merged:tracks");
				block.enterValue(junction.getLabel());
			}
		}
	}

	private static void injectMergedPointDefinition(Project prj, DataNamespace safecap, MergedPoint point) {
		final Block block = safecap.getBlockByNameOrNew("Point", point.getLabel());

		for (final Point p : point.getPoints()) {
			block.enterAttribute("point:merged");
			block.enterValue(p.getNode().getLabel());
		}
	}

	private static void injectPointInfo(DataNamespace safecap, Point point) {
		injectPointClearTracks(safecap, point);
		injectPointFouling(safecap, point);
		injectPointTrap(safecap, point);
		injectPointOrientation(safecap, point);
		injectPointJointOffsets(safecap, point);
	}

	private static void injectPointJointOffsets(DataNamespace safecap, Point point) {
		final Block block = safecap.getBlockByNameOrNew("Point", point.getNode().getLabel());
		if (point.eContainer() instanceof Junction) {
			final Junction junction = (Junction) point.eContainer();

			final Node node = point.getNode();
			final Collection<SegmentPath> paths = NodeUtil.buildPaths(node, Orientation.ANY, new ISegmentWalkerFilter() {
				@Override
				public boolean accept(Segment prev, Segment current) {
					return junction.getSegments().contains(current);
				}
			});

			for (final SegmentPath sp : paths) {
				block.enterAttribute("point:offset");
				block.enterValue(new ValueList(sp.getLastNode().getLabel(), sp.getLength()));
			}
		}
	}

	private static void injectPointTrap(DataNamespace safecap, Point point) {
		if (PointUtil.isTrap(point)) {
			final Block block = safecap.getBlockByNameOrNew("Point", point.getNode().getLabel());
			block.enterAttribute("point:trap");
			block.enterValue(true);
		}
	}

	private static void injectPointOrientation(DataNamespace safecap, Point point) {
		final Block block = safecap.getBlockByNameOrNew("Point", point.getNode().getLabel());
		block.enterAttribute("point:direction");
		block.enterValue(PointUtil.getPointOrientation(point).getName());
	}

	private static void injectPointFouling(DataNamespace safecap, Point point) {
		final Block block = safecap.getBlockByNameOrNew("Point", point.getNode().getLabel());

		if (point.eContainer() instanceof Junction) {
			final Junction junction = (Junction) point.eContainer();
			final List<Segment> segments = junction.getSegments();

			final Segment normal = PointUtil.getNormalBranch(point, segments);
			final Segment reverse = PointUtil.getReverseBranch(point, segments);

			if (normal != null && reverse != null) {
				final Orientation orientation = normal.getFrom() == point.getNode() ? Orientation.LEFT : Orientation.RIGHT;

				final ISegmentWalkerFilter filter = new ISegmentWalkerFilter() {
					@Override
					public boolean accept(Segment prev, Segment current) {
						return segments.contains(current);
					}

				};

				final SegmentPath normalToCicruit = SegmentPath.getShortest(SegmentUtil.buildPaths(normal, orientation, filter));
				final SegmentPath reverseToCicruit = SegmentPath.getShortest(SegmentUtil.buildPaths(reverse, orientation, filter));

				final int minLength = (int) Math.min(normalToCicruit.getLength(), reverseToCicruit.getLength());
				block.enterAttribute("point:fouling");
				block.enterValue(minLength);
			} else {
				block.enterAttribute("point:malformed");
				block.enterValue(true);
			}
		}
	}

	private static void injectPointClearTracks(DataNamespace safecap, Point point) {
		final Block block = safecap.getBlockByNameOrNew("Point", point.getNode().getLabel());

		if (point.eContainer() instanceof Junction) {
			final Junction junction = (Junction) point.eContainer();
			block.enterAttribute("point:tracks");
			block.enterValue(junction.getLabel());
		}
	}

	private static void injectRouteInfo(DataContext context, Project prj) {
		final DataNamespace safecap = new DataNamespace("safecap.route.derived", "safecap.route.derived");
		for (final Route route : prj.getRoutes()) {
			injectRouteInfo(safecap, route, prj);
		}

		context.addNamespace(safecap);
	}

	private static void injectRouteInfo(DataNamespace safecap, Route route, Project prj) {
		injectRoutePathPoints(safecap, route);
		injectRouteClearTracks(safecap, route);
		injectRouteFirst(safecap, route);
		injectRouteLast(safecap, route);
		injectRouteOrientation(safecap, route);
		injectRouteEntry(prj, safecap, route);
		injectRouteExit(prj, safecap, route);
		final BMap<Collection<Route>, Collection<Route>> conf_opp = RouteUtil.getConflictingOpposingRoutes(prj, route);
		injectConflictingRoutes(safecap, route, conf_opp.prj1());
		injectOpposingRoutes(safecap, route, conf_opp.prj2());
		injectFlankPoints(prj, safecap, route);
		injectSubRoutes(prj, safecap, route);
		injectRouteOverlaps(prj, safecap, route);
		injectRouteSummary(prj, safecap, route);

	}

	private static void injectRouteSummary(Project prj, DataNamespace safecap, Route route) {
		final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());
		block.enterAttribute("route:len");
		block.enterValue(RouteUtil.getLength(route));
		block.enterAttribute("route:ntracks");
		block.enterValue(route.getAmbits().size());
	}

	private static void injectRouteOverlaps(Project prj, DataNamespace safecap, Route route) {
		final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());

		int i = 0;
		for (final Overlap ovl : RouteUtil.getRouteOverlaps(route)) {
			block.enterAttribute("route:overlaps");
			block.enterValue(ovl.getName());
			i++;
		}
		block.enterAttribute("route:noverlaps");
		block.enterValue(i);
	}

	private static void injectRouteDecodeInfo(Project prj, DataNamespace safecap, Route route) {
		final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());
		final String info = ExtensionUtil.getString(route, SafecapConstants.EXT_LDL_GEN, "DECODING_ERROR");
		if (info != null) {
			block.enterAttribute("route:decoding");
			block.enterValue(info);
		}

	}

	private static void injectSubRoutes(Project prj, DataNamespace safecap, Route route) {
		final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());
		int i = 0;
		for (final Ambit ambit : route.getAmbits()) {
			final String orientation = ExtensionUtil.getString(ambit, SafecapConstants.EXT_ORIENTATION_DOMAIN, route.getLabel());
			block.enterAttribute("route:subroutes");
			final ValueList vl = new ValueList(2, true);
			vl.add(i++);

			final SubRoute sr = new SubRoute(ambit, orientation);
			vl.add(sr.toString());
			block.enterValue(vl);
			block.enterAttribute("route:subrouteset");
			block.enterValue(sr.toString());
		}

		block.enterAttribute("route:nsubroutes");
		block.enterValue(i);

	}

	private static void injectRouteDecoding(Project prj, DataNamespace safecap, Route route, DiagramEditPart editPart) {
		final RouteConfig rimporting = new RouteConfig(prj, editPart);
		final AmbitPath path = rimporting.makeAutoRouteAmbitPath(route.getLabel(), Collections.EMPTY_SET);
		final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());
		if (path != null) {
			for (final Ambit a : path.getPath()) {
				block.enterAttribute("route:decoded");
				block.enterValue(a.getLabel());
			}

			// if (path.isAmbiguous()) {
			block.enterAttribute("route:ambiguous");
			block.enterValue(path.isAmbiguous());
			// }
		} else {
			block.enterAttribute("route:undecoded");
			block.enterValue(true);
			// System.out.println("#### route decode errors:" +
			// rimporting.getErrors());
		}

	}

	private static void injectFlankPoints(Project prj, DataNamespace safecap, Route route) {
		final Collection<Point> normal = new HashSet<>();
		final Collection<Point> reverse = new HashSet<>();

		RouteUtil.getFlankPoints(prj, route, normal, reverse);

		if (!normal.isEmpty()) {
			final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());
			for (final Point p : normal) {
				block.enterAttribute("route:flanknormal");
				block.enterValue(p.getNode().getLabel());
			}
		}

		if (!reverse.isEmpty()) {
			final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());
			for (final Point p : reverse) {
				block.enterAttribute("route:flankreverse");
				block.enterValue(p.getNode().getLabel());
			}
		}

	}

	private static void injectConflictingRoutes(DataNamespace safecap, Route route, Collection<Route> set) {
		if (!set.isEmpty()) {
			final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());
			for (final Route r : set) {
				block.enterAttribute("route:conflicting");
				block.enterValue(r.getLabel());
			}
		}
	}

	private static void injectOpposingRoutes(DataNamespace safecap, Route route, Collection<Route> set) {
		if (!set.isEmpty()) {
			final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());
			for (final Route r : set) {
				block.enterAttribute("route:opposing");
				block.enterValue(r.getLabel());
			}
		}
	}

	private static void injectRouteEntry(Project prj, DataNamespace safecap, Route route) {
		if (route.getAmbits().size() > 0) {
			final Signal entry = RouteUtil.getEntrySignal(prj, route);
			if (entry != null) {
				final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());
				block.enterAttribute("route:entry");
				block.enterValue(entry.getLabel());
			}
		}
	}

	private static void injectRouteExit(Project prj, DataNamespace safecap, Route route) {
		if (route.getAmbits().size() > 0) {
			final Signal exit = RouteUtil.getExitSignal(prj, route);
			if (exit != null) {
				final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());
				block.enterAttribute("route:exit");
				block.enterValue(exit.getLabel());
			}
		}
	}

	private static void injectRouteFirst(DataNamespace safecap, Route route) {
		if (route.getAmbits().size() > 0) {
			final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());
			block.enterAttribute("route:first");

			block.enterValue(route.getAmbits().get(0).getLabel());
		}
	}

	private static void injectRouteLast(DataNamespace safecap, Route route) {
		if (route.getAmbits().size() > 0) {
			final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());
			block.enterAttribute("route:last");
			block.enterValue(route.getAmbits().get(route.getAmbits().size() - 1).getLabel());
		}
	}

	private static void injectRouteOrientation(DataNamespace safecap, Route route) {
		if (route.getAmbits().size() > 0) {
			final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());
			block.enterAttribute("route:direction");
			block.enterValue(route.getOrientation().getName());
		}
	}

	private static void injectRouteClearTracks(DataNamespace safecap, Route route) {
		final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());

		int i = 0;
		for (final Ambit ambit : route.getAmbits()) {
			block.enterAttribute("route:tracks");
			block.enterValue(new ValueList(i, ambit.getLabel()));
			block.enterAttribute("route:trackset");
			block.enterValue(ambit.getLabel());
			i++;
		}
	}

	private static void injectRoutePathPoints(DataNamespace safecap, Route route) {
		final PointConf conf = new PointConf(route);
		final Block block = safecap.getBlockByNameOrNew("Route", route.getLabel());

		int i = 0;
		for (final Point point : conf.getNormal()) {
			block.enterAttribute("route:normalpoints");
			block.enterValue(point.getNode().getLabel());
			i++;
		}
		block.enterAttribute("route:nnormalpoints");
		block.enterValue(i);

		i = 0;
		for (final Point point : conf.getReverse()) {
			block.enterAttribute("route:reversepoints");
			block.enterValue(point.getNode().getLabel());
			i++;
		}
		block.enterAttribute("route:nreversepoints");
		block.enterValue(i);

	}

}
