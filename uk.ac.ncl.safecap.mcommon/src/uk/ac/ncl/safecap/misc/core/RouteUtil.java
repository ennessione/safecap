package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

import safecap.Orientation;
import safecap.Project;
import safecap.model.Ambit;
import safecap.model.ControlLogic;
import safecap.model.Line;
import safecap.model.Point;
import safecap.model.Route;
import safecap.schema.Node;
import safecap.schema.PointRole;
import safecap.schema.Segment;
import safecap.trackside.Signal;
import uk.ac.ncl.prime.sim.sets.BMap;

public class RouteUtil {
	public static Pattern routePattern = Pattern.compile(
			"(?<prefix>[A-Z]*)(?<signal>[0-9]+)(?<path>[A-Z])?(\\-(?<pathcode>[0-9]))?(\\(?(?<class>M|W|C|S|P|NP|PS|PSC|PSM)\\)?)??(\\(?(?<subclass>NP|P)\\)?)?(-(?<suffix>.+))?");

	public static Collection<String> getInterlockings(Route route) {
		final Collection<String> interlockings = new HashSet<>();
		for (final Ambit a : route.getAmbits()) {
			final String interlocking = AmbitUtil.getInterlocking(a);
			if (interlocking != null) {
				interlockings.add(interlocking);
			}
		}

		return interlockings;
	}

	public static String getRouteClass(Route route) {
		final String _class = ExtensionUtil.getString(route, SafecapConstants.EXT_ROUTE, SafecapConstants.EXT_ROUTE_CLASS);
		if (_class != null) {
			return _class;
		}

		buildRouteAttributes(route);
		return ExtensionUtil.getString(route, SafecapConstants.EXT_ROUTE, SafecapConstants.EXT_ROUTE_CLASS);
	}

	public static String getRouteSubClass(Route route) {
		final String _subclass = ExtensionUtil.getString(route, SafecapConstants.EXT_ROUTE, SafecapConstants.EXT_ROUTE_SUBCLASS);
		if (_subclass != null) {
			return _subclass;
		}

		buildRouteAttributes(route);
		return ExtensionUtil.getString(route, SafecapConstants.EXT_ROUTE, SafecapConstants.EXT_ROUTE_SUBCLASS);
	}

	public static boolean isRoutePreset(Route route) {
		if (!ExtensionUtil.hasValue(route, SafecapConstants.EXT_ROUTE, SafecapConstants.EXT_ROUTE_PRESET)) {
			buildRouteAttributes(route);
		}

		return ExtensionUtil.getBool(route, SafecapConstants.EXT_ROUTE, SafecapConstants.EXT_ROUTE_PRESET);
	}

	public static boolean isFullOverlapClass(Route route) {
		final String cl = getRouteClass(route);
		final String scl = getRouteSubClass(route);

		return "M".equals(cl) || "S".equals(cl) && "NP".equals(scl);
	}

	public static boolean isReducedOverlapClass(Route route) {
		final String cl = getRouteClass(route);
		return "W".equals(cl);
	}

	public static boolean isOverlapClass(Route route) {
		return isFullOverlapClass(route) || isFullOverlapClass(route);
	}

	private static synchronized void buildRouteAttributes(final Route route) {
		try {
			final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(route);
			if (domain instanceof InternalTransactionalEditingDomain) {
				final InternalTransactionalEditingDomain ed = (InternalTransactionalEditingDomain) domain;
				if (ed.getActiveTransaction() != null) {
					if (ed.getActiveTransaction().isReadOnly()) {
						return;
					}
					_buildRouteAttributes(route);
					return;
				}
			}
			if (!domain.isReadOnly(route.eResource())) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {

					@Override
					protected void doExecute() {
						_buildRouteAttributes(route);
					}
				});
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	private static void _buildRouteAttributes(Route route) {
		final Matcher matcher = RouteUtil.routePattern.matcher(route.getLabel());
		if (!matcher.matches() || matcher.groupCount() < 3) {
			// assert false;
			return;
		}

		String mClass = matcher.group("class");
		final String mSubClass = matcher.group("subclass");

		if (mClass == null) {
			mClass = "";
		}

		String routeClass = "M";
		String routeSubClass = mSubClass;
		boolean isPreset = false;

		if (mClass.equals("M")) {
			routeClass = "M";
		} else if (mClass.equals("W")) {
			routeClass = "W";
		} else if (mClass.equals("C")) {
			routeClass = "C";
		} else if (mClass.equals("S")) {
			routeClass = "S";
		} else if (mClass.equals("P")) {
			routeClass = "P";
		} else if (mClass.equals("NP")) {
			routeClass = "S";
			routeSubClass = "NP";
		} else if (mClass.equals("PS")) {
			routeClass = "S";
			routeSubClass = "P";
		} else if (mClass.equals("PSC")) {
			routeClass = "C";
			isPreset = true;
		} else if (mClass.equals("PSM")) {
			routeClass = "M";
			isPreset = true;
		} else {
			routeClass = "M";
			//assert false;
		}

		assert routeSubClass == null || routeSubClass.equals("P") || routeSubClass.equals("NP");

		ExtensionUtil.delete(route, SafecapConstants.EXT_ROUTE);

		route.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_ROUTE, SafecapConstants.EXT_ROUTE_CLASS, routeClass));
		if (routeSubClass != null) {
			route.getAttributes()
					.add(ExtensionUtil.mkString(SafecapConstants.EXT_ROUTE, SafecapConstants.EXT_ROUTE_SUBCLASS, routeSubClass));
		}
		route.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_ROUTE, SafecapConstants.EXT_ROUTE_PRESET, isPreset));
	}

	public static void getFlankPoints(Project root, Route route, Collection<Point> normal, Collection<Point> reverse) {

		// route path
		PointConf conf = new PointConf(route);
		processPointConfig(root, route, normal, reverse, conf);

		// route overlaps
		final List<SegmentPath> overlaps = getRouteOverlapPaths(route);
		for (final SegmentPath sp : overlaps) {
			conf = new PointConf(sp.getPath());
			processPointConfig(root, route, normal, reverse, conf);
		}

		// remove ambivalent points
		final Collection<Point> neutral = new ArrayList<>(normal);
		neutral.retainAll(reverse);
		normal.removeAll(neutral);
		reverse.removeAll(neutral);

		// remove points set to enable the path
		conf = new PointConf(route, true);
		removeStates(normal, reverse, conf);

		// remove points set to enable any overlap
		for (final SegmentPath sp : overlaps) {
			conf = new PointConf(sp.getPath());
			removeStates(normal, reverse, conf);
		}

	}

	private static void removeStates(Collection<Point> normal, Collection<Point> reverse, PointConf conf) {
		normal.removeAll(conf.getNormal());
		normal.removeAll(conf.getReverse());
		reverse.removeAll(conf.getNormal());
		reverse.removeAll(conf.getReverse());
	}

	private static void processPointConfig(Project root, Route route, Collection<Point> normal, Collection<Point> reverse, PointConf conf) {
		for (final Point p : conf.getNormal()) {
			final Segment seed = PointUtil.getReverseBranch(p, root.getSegments());
			Orientation or = route.getOrientation();
			if (conf.getTrailing().contains(p)) {
				or = inverseOrientation(or);
			}
			getFlankPointsFrom(root, normal, reverse, seed, or);
		}

		for (final Point p : conf.getReverse()) {
			final Segment seed = PointUtil.getNormalBranch(p, root.getSegments());
			Orientation or = route.getOrientation();
			if (conf.getTrailing().contains(p)) {
				or = inverseOrientation(or);
			}
			getFlankPointsFrom(root, normal, reverse, seed, or);
		}
	}

	public static Orientation inverseOrientation(Orientation orientation) {
		if (orientation == Orientation.LEFT) {
			return Orientation.RIGHT;
		} else if (orientation == Orientation.RIGHT) {
			return Orientation.LEFT;
		} else {
			return orientation;
		}
	}

	private static void getFlankPointsFrom(Project root, Collection<Point> normal, Collection<Point> reverse, Segment seed,
			Orientation orientation) {
		final Collection<SegmentPath> paths = SegmentUtil.buildPaths(Collections.singleton(seed), orientation,
				new FilterPoints(orientation, 2));
		processPathList(root, normal, reverse, paths, orientation);
	}

	private static void processPathList(Project root, Collection<Point> normal, Collection<Point> reverse, Collection<SegmentPath> paths1,
			Orientation orientation) {
		for (final SegmentPath p : paths1) {
			if (orientation == Orientation.RIGHT && NodeUtil.isJunctionNode(p.getLast().getFrom())) {
				final Point q = PointUtil.getPointFor(p.getLast().getFrom());
				if (q != null) {
					if (p.getLast().getPointrole() == PointRole.NORMAL) {
						reverse.addAll(PointUtil.getAllSiblings(root, q));
					} else if (p.getLast().getPointrole() == PointRole.REVERSE) {
						normal.addAll(PointUtil.getAllSiblings(root, q));
					}
				}
			} else if (orientation == Orientation.LEFT && NodeUtil.isJunctionNode(p.getLast().getTo())) {
				final Point q = PointUtil.getPointFor(p.getLast().getTo());
				if (q != null) {
					if (p.getLast().getPointrole() == PointRole.NORMAL) {
						reverse.addAll(PointUtil.getAllSiblings(root, q));
					} else if (p.getLast().getPointrole() == PointRole.REVERSE) {
						normal.addAll(PointUtil.getAllSiblings(root, q));
					}
				}
			}
		}
	}

	public static class FilterPoints implements ISegmentWalkerFilter {
		private final Orientation orientation;
		private int remainingHops;

		private FilterPoints(Orientation orientation, int maxHops) {
			remainingHops = maxHops;
			this.orientation = orientation;
		}

		@Override
		public boolean accept(Segment prev, Segment current) {
			if (prev == null) {
				return true;
			}

			if (orientation == Orientation.LEFT) {
				if (SignalUtil.getRightSignalOn(current) != null) {
					return false;
				}

				if (NodeUtil.isJunctionNode(prev.getTo())) {
					if (prev.getPointrole() == PointRole.NONE) {
						remainingHops--;
						return remainingHops > 0;
					}
					return false;
				}
			} else if (orientation == Orientation.RIGHT) {
				if (SignalUtil.getLeftSignalOn(current) != null) {
					return false;
				}

				if (NodeUtil.isJunctionNode(prev.getFrom())) {
					if (prev.getPointrole() == PointRole.NONE) {
						remainingHops--;
						return remainingHops > 0;
					}
					return false;
				}
			} else {
				return false;
			}

			return true;
		}
	}

	public static List<SegmentPath> getRouteOverlapPaths(Route route) {
		final Project root = EmfUtil.getProject(route);
		final Signal exit = RouteUtil.getExitSignal(root, route);
		if (exit == null) {
			return Collections.emptyList();
		}

		final List<SegmentPath> result = new ArrayList<>();

		final Orientation o = SignalUtil.getSignalOrientation(exit);

		if (RouteUtil.isFullOverlapClass(route)) {
			for (final Overlap overlap : OverlapUtil.getFullOverlaps(exit)) {
				final SegmentPath sp = new SegmentPath(OverlapUtil.getNodePath(overlap), o);
				if (sp.getLength() != 0)
						result.add(sp);
			}
		} else if (RouteUtil.isReducedOverlapClass(route)) {
			for (final Overlap overlap : OverlapUtil.getReducedOverlaps(exit)) {
				final SegmentPath sp = new SegmentPath(OverlapUtil.getNodePath(overlap), o);
				if (sp.getLength() != 0)
					result.add(sp);
			}

		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static Collection<Overlap> getRouteOverlaps(Route route) {
		final Project root = EmfUtil.getProject(route);
		final Signal exit = RouteUtil.getExitSignal(root, route);
		if (exit == null) {
			return Collections.emptyList();
		}

		if (RouteUtil.isFullOverlapClass(route)) {
			return OverlapUtil.getFullOverlaps(exit);
		} else if (RouteUtil.isReducedOverlapClass(route)) {
			return OverlapUtil.getReducedOverlaps(exit);
		}

		return Collections.EMPTY_LIST;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static BMap<Collection<Route>, Collection<Route>> getConflictingOpposingRoutes(Project root, Route route) {
		final Collection<Route> overlapping = getOverlappingRoutes(root, route);
		final Collection<Route> conflicting = new HashSet<>();
		final Collection<Route> opposing = new HashSet<>();

		final PointConf main = new PointConf(route);

		for (final Route r : overlapping) {
			final PointConf rconf = new PointConf(r);
			if (conflictingPointStates(main, rconf)) {
				conflicting.add(r);
			} else if (r.getOrientation() != route.getOrientation()) {
				opposing.add(r);
			}
		}

		return new BMap(conflicting, opposing);
	}

	private static boolean conflictingPointStates(PointConf main, PointConf rconf) {
		for (final Point normal : main.getNormal()) {
			if (rconf.getReverse().contains(normal)) {
				return true;
			}
		}

		for (final Point normal : main.getReverse()) {
			if (rconf.getNormal().contains(normal)) {
				return true;
			}
		}

		return false;
	}

	public static Collection<Route> getOverlappingRoutes(Project root, Route route) {
		final Collection<Route> result = new HashSet<>();
		for (final Route r : root.getRoutes()) {
			if (r != route && !r.getLabel().equals(route.getLabel()) && haveCommongSegments(r, route)) {
				result.add(r);
			}
		}

		return result;
	}

	private static boolean haveCommongSegments(Route r, Route route) {
		for (final Segment s : r.getSegments()) {
			if (route.getSegments().contains(s)) {
				return true;
			}
		}

		return false;
	}

	public static Signal getEntrySignal(Project root, Route route) {
		final Node entry = getEntryNode(route);

		if (route.getOrientation() == Orientation.LEFT) {
			final List<Segment> segs = NodeUtil.getIncomingSegments(entry);
			if (segs.size() == 1) {
				return SignalUtil.getLeftSignalOn(segs.get(0));
			}
			return null;
		} else if (route.getOrientation() == Orientation.RIGHT) {
			final List<Segment> segs = NodeUtil.getOutgoingSegments(entry);
			if (segs.size() == 1) {
				return SignalUtil.getRightSignalOn(segs.get(0));
			}
			return null;
		}

		return null;
	}

	public static Signal getExitSignal(Project root, Route route) {
		final Ambit last = route.getAmbits().get(route.getAmbits().size() - 1);

		for (final Segment s : last.getSegments()) {
			final Signal signal = route.getOrientation() == Orientation.LEFT ? SignalUtil.getLeftSignalOn(s)
					: SignalUtil.getRightSignalOn(s);
			if (signal != null) {
				return signal;
			}
		}
		return null;
	}

	public static class FilterSignal implements ISegmentWalkerFilter {
		private final Orientation or;

		public FilterSignal(Orientation or) {
			this.or = or;
		}

		@Override
		public boolean accept(Segment prev, Segment current) {
			return prev == null || SignalUtil.getSignalOn(prev, or) == null;
		}
	}

	public static class FilterLeftSignal implements ISegmentWalkerFilter {
		public static final FilterLeftSignal INSTANCE = new FilterLeftSignal();

		private FilterLeftSignal() {
		}

		@Override
		public boolean accept(Segment prev, Segment current) {
			return prev == null || SignalUtil.getLeftSignalOn(prev) == null;
		}
	}

	public static class FilterRightSignal implements ISegmentWalkerFilter {
		public static final FilterRightSignal INSTANCE = new FilterRightSignal();

		private FilterRightSignal() {
		}

		@Override
		public boolean accept(Segment prev, Segment current) {
			return prev == null || SignalUtil.getRightSignalOn(prev) == null;
		}
	}

	public static Node getEntryNode(Route route) {
		if (route.getOrientation() == Orientation.LEFT) {
			return route.getSegments().get(0).getFrom();
		} else {
			return route.getSegments().get(0).getTo();
		}
	}

	public static Node getExitNode(Route route) {
		if (route.getOrientation() == Orientation.LEFT) {
			return route.getSegments().get(route.getSegments().size() - 1).getTo();
		} else {
			return route.getSegments().get(route.getSegments().size() - 1).getFrom();
		}
	}

	public static List<ControlLogic> getAllControlLogic(Route route) {
		final List<ControlLogic> list = new ArrayList<>();
		list.add(RuleUtil.getDefaultLogic(route));
		list.addAll(route.getLogic());
		return list;
	}

	public static List<ControlLogic> getAllControlLogicSafe(Route route) {
		final List<ControlLogic> list = new ArrayList<>();
		list.add(RuleUtil.getDefaultLogicSafe(route));
		list.addAll(route.getLogic());
		return list;
	}

	public static int getLength(Route route) {
		int length = 0;
		for (final Segment s : route.getSegments()) {
			length += s.getLength();
		}

		return length;
	}

	public static double getMovementAuthority(Route route, TrainLine line, int afterRoutes) {
		double len = getLength(route);

		Route next = line.getNextRoute(route);
		Route prev = route;

		while (next != null && afterRoutes > 1) {
			len += getLength(next);
			prev = next;
			next = line.getNextRoute(next);
			afterRoutes--;
		}

		if (next == null || prev == null) {
			return 100000;
		}

		// for the last route we take into consideration the signal position
		final Signal lst_grd = line.getGuardian(prev);
		if (lst_grd != null) {
			final double spos = line.getSignalPosition(lst_grd);
			final double fpos = line.getAmbitOffset(prev.getAmbits().get(0)); // the
			// position
			// of
			// last
			// route
			// start
			final double sig_offset = fpos - spos; // the 'lost' track position due to
													// forward signal position
			len -= sig_offset;
		}

		return len;
	}

	public static Route getByLabel(Project root, String label) {
		if (label == null) {
			return null;
		}

		for (final Route r : root.getRoutes()) {
			if (label.equals(r.getLabel())) {
				return r;
			}
		}

		return null;
	}

	public static List<Route> getByPrefix(Project root, String label) {
		final List<Route> result = new ArrayList<>();

		if (label == null) {
			return null;
		}

		for (final Route r : root.getRoutes()) {
			if (r.getLabel() != null && r.getLabel().startsWith(label)) {
				result.add(r);
			}
		}

		return result;
	}

	public static String getAspectLabel(int aspects, int aspect) {
		if (aspect < aspects - 1) {
			switch (aspect) {
			case 0:
				return "R";
			case 1:
				return "Y";
			case 2:
				return "YY";
			case 3:
				return "YYY";
			default:
				return "Y" + aspect;
			}
		} else if (aspect == aspects - 1) {
			return "G";
		} else {
			return "unused";
		}
	}

	public static String getAspectLabel(ControlLogic logic, int aspect) {
		return getAspectLabel(logic.getAspects(), aspect);
	}

	public static int parseAspectLabel(int aspects, String label) {
		if (label.equals("R")) {
			return 0;
		} else if (label.equals("G")) {
			return aspects - 1;
		} else if (label.equals("Y")) {
			return 1;
		} else if (label.equals("YY")) {
			return 2;
		} else if (label.equals("YYY")) {
			return 3;
		} else if (label.length() > 1 && label.charAt(0) == 'Y' && Character.isDigit(label.charAt(1))) {
			try {
				return Integer.parseInt(label.substring(1));
			} catch (final NumberFormatException e) {
				return 1;
			}
		}

		return 0;
	}

	public static int parseAspectLabel(ControlLogic logic, String label) {
		return parseAspectLabel(logic.getAspects(), label);
	}

	public static List<Line> getAllContainingLines(Route route) {
		final List<Line> lines = new ArrayList<>();
		final Project project = EmfUtil.getProject(route);
		for (final Line line : project.getLines()) {
			if (line.getRoutes().contains(route)) {
				lines.add(line);
			}
		}
		return lines;
	}
}
