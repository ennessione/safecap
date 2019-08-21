package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import safecap.Orientation;
import safecap.Project;
import safecap.model.Ambit;
import safecap.schema.Node;
import safecap.schema.Segment;
import safecap.trackside.LeftSignal;
import safecap.trackside.Signal;

public class SubRouteUtil {

	public static List<SubRoute> getSubRoutePath(SegmentPath segmentPath) {
		if (segmentPath.size() == 0) {
			return Collections.emptyList();
		}

		final Segment s = segmentPath.get(0);
		final Project root = EmfUtil.getProject(s);

		final Collection<Node> nodes = segmentPath.getNodes();
		final Map<SubRoute, Set<SubRoute>> graph = getSubRouteGraph(root);
		final Map<Segment, Ambit> samap = AmbitUtil.getSegmentAmbitMap(root);
		final Ambit startAmbit = samap.get(s);
		final List<SubRoute> result = new ArrayList<>();

		Collection<SubRoute> currentSet = filterByNodes(getSubRoutes(startAmbit, s, segmentPath.getOrientation()), nodes);

		while (currentSet != null) {
			if (currentSet.size() != 1) {
				return Collections.emptyList();
			}
			final SubRoute current = currentSet.iterator().next();
			result.add(current);
			currentSet = filterByNodes(graph.get(current), nodes);
		}
		return result;
	}

	public static List<SubRoute> getSubRoutePath(Signal signal, Node end) {
		if (signal == null || end == null) {
			return Collections.emptyList();
		}
		final Node startNode = SignalUtil.getSignalNode(signal);

		if (startNode != null) {
			final List<SubRoute> path = getSubRoutePath(startNode, end,
					signal instanceof LeftSignal ? Orientation.LEFT : Orientation.RIGHT);
			if (path != null) {
				return path;
			}
		}

		return Collections.emptyList();
	}

	public static List<SubRoute> getSubRoutePath(Node start, Node end, Orientation orientation) {
		// Project root = EmfUtil.getProject(start);
		// Map<SubRoute, Set<SubRoute>> graph = getSubRouteGraph(root);
		// Map<Segment, Ambit> samap = SignalUtil.getSegmentAmbitMap(root);
		final Set<SubRoute> startSubRoutes = getSubRoutesStartingAt(start);
		for (final SubRoute sr : startSubRoutes) {
			final List<SubRoute> path = getSubRoutePath(sr, end, orientation);
			if (path != null) {
				return path;
			}
		}

		return null;
	}

	public static List<SubRoute> getSubRoutePath(SubRoute start, Node end, Orientation orientation) {
		if (start.getNodePath().get(start.getNodePath().size() - 1) == end) {
			final List<SubRoute> result = new ArrayList<>(1);
			result.add(start);
			return result;
		}

		final Project root = EmfUtil.getProject(end);
		final Map<SubRoute, Set<SubRoute>> graph = getSubRouteGraph(root);
		final Collection<SubRoute> next = graph.get(start);
		if (next == null) {
			return null;
		}

		for (final SubRoute n : next) {
			final List<SubRoute> p = getSubRoutePath(n, end, orientation);
			if (p != null) {
				p.add(0, start);
				return p;
			}
		}

		return null;
	}

	private static Set<SubRoute> getSubRoutesStartingAt(Node start) {
		final Project root = EmfUtil.getProject(start);
		final Set<SubRoute> result = new HashSet<>();
		for (final SubRoute subroute : getSubRoutes(root)) {
			if (subroute.getNodePath() != null && subroute.getNodePath().get(0) == start) {
				result.add(subroute);
			}
		}

//		if (result.isEmpty()) {
//			System.out.println("No sub routes at node " + start);
//		}

		return result;
	}

	private static Collection<SubRoute> filterByNodes(Set<SubRoute> subRoutes, Collection<Node> nodes) {
		final List<SubRoute> result = new ArrayList<>();

		outer: for (final SubRoute sr : subRoutes) {
			for (final Node n : getNodePath(sr)) {
				if (!nodes.contains(n)) {
					continue outer;
				}
			}
			result.add(sr);
		}

		return result;
	}

	public static SubRoute getSubRouteFromName(Project root, String name) {
		final int i = name.indexOf('-');
		if (i > 0) {
			String ambitName = name.substring(1, i);
			final String orientation = name.substring(i + 1);
			final boolean normalised = ExtensionUtil.getFlag(root, "normalised");
			if (normalised) {
				ambitName = "T" + ambitName;
			}

			final Ambit ambit = AmbitUtil.getByLabel(root, ambitName);
			if (ambit != null && orientation.length() == 2) {
				return new SubRoute(ambit, orientation);
			} else {
				return null;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Map<SubRoute, Set<SubRoute>> getSubRouteGraph(Project root) {
		Map<SubRoute, Set<SubRoute>> result = (Map<SubRoute, Set<SubRoute>>) ProjectTransient.getValue(root,
				ProjectTransient.SUBROUTE_GRAPH);
		if (result == null) {
			result = new HashMap<>();

			for (final Ambit ambit : root.getAmbits()) {
				for (final SubRoute subroute : getSubRoutes(ambit)) {
					final Set<SubRoute> next = getNext(subroute);
					if (next != null) {
						result.put(subroute, next);
					}
				}
			}

			ProjectTransient.setValue(root, ProjectTransient.SUBROUTE_GRAPH, result);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getSubRouteRenameMap(Project root) {
		Map<String, String> result = (Map<String, String>) ProjectTransient.getValue(root, ProjectTransient.SUBROUTE_RENAMED_MAP);
		if (result == null) {
			result = buildSubRouteRenameMap(root);
			ProjectTransient.setValue(root, ProjectTransient.SUBROUTE_RENAMED_MAP, result);
		}
		return result;
	}

	private static Map<String, String> buildSubRouteRenameMap(Project root) {
		final Map<String, String> map = new HashMap<>();
		for (final Ambit a : root.getAmbits()) {
			for (final String direction : AmbitUtil.getAllAmbitSubRouteDirections(a)) {
				final String alias = ExtensionUtil.getString(a, SafecapConstants.EXT_SUBROUTE_RENAMED, direction);
				if (alias != null) {
					map.put(alias, SubRoute.getCanonicalName(a, direction));
				}

			}

		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static Collection<SubRoute> getSubRoutes(Project root) {
		Collection<SubRoute> result = (Collection<SubRoute>) ProjectTransient.getValue(root, ProjectTransient.SUBROUTE_SET);
		if (result == null) {
			result = new HashSet<>();

			for (final Ambit ambit : root.getAmbits()) {
				result.addAll(getSubRoutes(ambit));
			}

			ProjectTransient.setValue(root, ProjectTransient.SUBROUTE_SET, result);
		}
		return result;
	}

	public static boolean hasSubRoutes(Ambit ambit) {
		return ExtensionUtil.hasKey(ambit, SafecapConstants.EXT_SUBROUTE);
	}

	public static Set<SubRoute> getSubRoutes(Ambit ambit) {
		final Set<SubRoute> result = new HashSet<>();
		for (final String k : ExtensionUtil.getKeys(ambit, SafecapConstants.EXT_SUBROUTE)) {
			result.add(new SubRoute(ambit, k));
		}

		return result;
	}



	public static SubRoute getSubRoute(SubOverlap suboverlap) {
		// Collection<ExtAttribute> vals = ExtensionUtil.getAll(suboverlap.getAmbit(),
		// SafecapConstants.EXT_SUBOVERLAP);

		if (ExtensionUtil.getBool(suboverlap.getAmbit(), SafecapConstants.EXT_SUBOVERLAP, suboverlap.getDirection())) {
			return new SubRoute(suboverlap.getAmbit(), suboverlap.getDirection());
		}

		return null;
	}

	public static Set<SubRoute> getSubRoutes(Ambit ambit, Segment segment, Orientation orientation) {
		final Set<SubRoute> result = new HashSet<>();
		for (final String k : ExtensionUtil.getKeys(ambit, SafecapConstants.EXT_SUBROUTE)) {
			final SubRoute sr = new SubRoute(ambit, k);
			final List<Node> path = getNodePath(sr);
			for (int i = 1; i < path.size(); i++) {
				if (orientation == Orientation.LEFT && path.get(i - 1) == segment.getFrom() && path.get(i) == segment.getTo()) {
					result.add(sr);
				} else if (orientation == Orientation.RIGHT && path.get(i - 1) == segment.getTo() && path.get(i) == segment.getFrom()) {
					result.add(sr);
				}
			}
		}

		return result;
	}

	public static Set<SubRoute> getSubRoutes(Ambit ambit, Node start) {
		final Set<SubRoute> result = new HashSet<>();
		for (final String k : ExtensionUtil.getKeys(ambit, SafecapConstants.EXT_SUBROUTE)) {
			final SubRoute sr = new SubRoute(ambit, k);
			final List<Node> path = getNodePath(sr);
			if (path.get(0) == start) {
				result.add(sr);
			}
		}

		return result;
	}

	public static Set<SubRoute> getSubRoutesEnding(Ambit ambit, Node end) {
		final Set<SubRoute> result = new HashSet<>();
		for (final String k : ExtensionUtil.getKeys(ambit, SafecapConstants.EXT_SUBROUTE)) {
			final SubRoute sr = new SubRoute(ambit, k);
			final List<Node> path = getNodePath(sr);
			if (path != null && path.get(path.size() - 1) == end) {
				result.add(sr);
			}
		}

		return result;
	}

	public static Set<SubRoute> getNext(SubRoute subroute) {
		final Set<SubRoute> result = new HashSet<>();
		final Ambit current = subroute.getAmbit();
		final Collection<Ambit> next = new HashSet<>(AmbitUtil.getNextAmbits(current));
		next.addAll(AmbitUtil.getPrevAmbits(current));
//		if (subroute.getOrientation() == Orientation.LEFT) {
//			next = AmbitUtil.getNextAmbits(current);
//		} else if (subroute.getOrientation() == Orientation.RIGHT) {
//			next = AmbitUtil.getPrevAmbits(current);
//		}

		final Node last = getLastNode(subroute);
		for (final Ambit na : next) {
			for (final SubRoute sr : getSubroutesStartingAt(na, last)) {
				if (!sr.toString().equals(subroute.toString())) {
					result.add(sr);
				}
			}
		}

		return result;

	}

	public static Collection<SubRoute> getSubroutesStartingAt(Ambit ambit, Node node) {
		final List<SubRoute> result = new ArrayList<>();

		for (final String k : ExtensionUtil.getKeys(ambit, SafecapConstants.EXT_SUBROUTE)) {
			final SubRoute s = new SubRoute(ambit, k);
			if (getFirstNode(s) == node) {
				result.add(s);
			}
		}

		return result;
	}

	public static Node getFirstNode(SubRoute subroute) {
		final List<Node> list = subroute.getNodePath();
		if (list != null) {
			return list.get(0);
		}

		return null;
	}

	public static Node getLastNode(SubRoute subroute) {
		final List<Node> list = subroute.getNodePath();
		if (list != null) {
			return list.get(list.size() - 1);
		}

		return null;
	}

	public static List<Node> getNodePath(SubRoute subroute) {
		try {
			final List<Node> list = getNodePathLeft(subroute, subroute.getDirection());
			if (list == null) {
				final List<Node> list2 = getNodePathLeft(subroute, subroute.getOppositeDirection());
				if (list2 == null) {
					return null;
				}
				Collections.reverse(list2);
				return list2;
			} else {
				return list;
			}
		} catch (final Exception e) {
			return null;
		}
	}

	private static List<Node> getNodePathLeft(SubRoute subroute, String dir) {
		final Project root = EmfUtil.getProject(subroute.getAmbit());
		final String path = ExtensionUtil.getString(subroute.getAmbit(), SafecapConstants.EXT_SUBROUTE_PATH, dir);
		//ExtensionUtil.getAll(subroute.getAmbit(), SafecapConstants.EXT_SUBROUTE_PATH);
		if (path != null) {
			final List<Node> result = new ArrayList<>();
			final String[] parts = path.split(";");
			for (final String p : parts) {
				final Node n = NodeUtil.getByLabel(root, p);
				if (n == null) {
					return null;
				}
				result.add(n);
			}

			return result;
		}

		return null;
	}
}
