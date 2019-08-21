package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import safecap.Project;
import safecap.model.Ambit;
import safecap.schema.Node;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.Signal;

public class OverlapUtil {

	public static Set<Overlap> getAllOverlaps(Project root) {
		final Set<Overlap> result = new HashSet<>();
		for (final Equipment s : root.getEquipment()) {
			if (s instanceof Signal) {
				final Signal signal = (Signal) s;
				result.addAll(getOverlaps(signal));
			}
		}
		return result;
	}

	public static Set<Overlap> getAllOverlapsForNode(Project root, Node node) {
		final Set<Overlap> result = new HashSet<>();
		for (final Overlap overlap : OverlapUtil.getAllOverlaps(root)) {
			final List<Node> zlist = OverlapUtil.getNodePath(overlap);
			if (zlist != null) {
				final Node last = zlist.get(zlist.size() - 1);
				if (last == node) {
					result.add(overlap);
				}
			}
		}

		return result;
	}

	public static Set<Overlap> getAllOverlapsForNode(Signal signal, Project root, Node node) {
		final Set<Overlap> result = new HashSet<>();
		for (final Overlap overlap : OverlapUtil.getAllOverlaps(root)) {
			final List<Node> zlist = OverlapUtil.getNodePath(overlap);
			if (zlist != null) {
				final Node last = zlist.get(zlist.size() - 1);
				if (last == node && overlap.getSignal() == signal) {
					result.add(overlap);
				}
			}
		}

		return result;
	}

	public static Set<Ambit> getOverlapAmbits(Project root, Overlap overlap) {
		final Set<Ambit> result = new HashSet<>();
		final Map<Segment, Ambit> samap = AmbitUtil.getSegmentAmbitMap(root);
		final List<Node> path = getNodePath(overlap);
		if (path != null) {
			for (int i = 1; i < path.size(); i++) {
				final Segment s = SegmentUtil.getByNodePair(root, path.get(i - 1), path.get(i));
				if (s != null) {
					final Ambit a = samap.get(s);
					if (a != null) {
						result.add(a);
					}
				}
			}
		}

		return result;
	}

	public static List<SubOverlap> getOverlapSubOverlaps(Project root, Overlap overlap) {
		final List<SubOverlap> result = new ArrayList<>();
		String name = overlap.getName();
		if (name.endsWith("-R") || name.endsWith("-F"))
			name = name.substring(0, name.length() - 2);
		final String parts[] = name.substring(4).split(";");
		for (final String p : parts) {
			final SubOverlap so = SubOverlapUtil.getSubOverlapFromName(root, p);
			if (so == null) {
				return null;
			}
			result.add(SubOverlapUtil.getSubOverlapFromName(root, p));
		}

		return result;
	}

	public static Set<Overlap> getOverlaps(Signal signal) {
		final Set<Overlap> result = new HashSet<>();
		for (final String k : ExtensionUtil.getKeys(signal, SafecapConstants.EXT_OVERLAP_PATH)) {
			result.add(new Overlap(signal, k));
		}

		return result;
	}

	public static Set<Overlap> getFullOverlaps(Signal signal) {
		final Set<Overlap> result = new HashSet<>();
		for (final String k : ExtensionUtil.getKeys(signal, SafecapConstants.EXT_OVERLAP_PATH)) {
			final Overlap ovl = new Overlap(signal, k);
			if (ovl.isFull()) {
				result.add(ovl);
			}
		}

		return result;
	}

	public static Set<Overlap> getReducedOverlaps(Signal signal) {
		final Set<Overlap> result = new HashSet<>();
		for (final String k : ExtensionUtil.getKeys(signal, SafecapConstants.EXT_OVERLAP_PATH)) {
			final Overlap ovl = new Overlap(signal, k);
			if (ovl.isReduced()) {
				result.add(ovl);
			}
		}

		return result;
	}

	public static List<Node> getNodePath(Overlap overlap) {
		final Project root = EmfUtil.getProject(overlap.getSignal());
		final String path = ExtensionUtil.getString(overlap.getSignal(), SafecapConstants.EXT_OVERLAP_PATH, overlap.getName());

		if (path != null) {
			final List<Node> result = new ArrayList<>();
			final String[] parts = path.split(";");
			for (final String p : parts) {
				Node n = NodeUtil.getByLabel(root, p);
				if (n == null) {
					n = NodeUtil.getByLabel(root, "P" + p);
				}
				if (n == null) {
					return null;
				}
				result.add(n);
			}

			return result;
		}

		return null;
	}

	public static Overlap getOverlapByName(Project root, String s) {
		for (final Overlap overlap : getAllOverlaps(root)) {
			if (s.equals(overlap.getName())) {
				return overlap;
			}
		}
		return null;
	}
}
