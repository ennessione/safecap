package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import safecap.Labeled;
import safecap.Orientation;
import safecap.Project;
import safecap.schema.Node;
import safecap.schema.Segment;

public class SegmentPath {
	private final List<Segment> path;
	private Labeled from, to;
	private Node from_node, to_node;
	private Orientation orientation;
	private boolean ambiguous = false;
	private long length = Long.MIN_VALUE;

	public SegmentPath(Orientation orientation, Labeled from) {
		path = new ArrayList<>();
		this.from = from;
		to = from;
		this.orientation = orientation;
	}

	public SegmentPath(List<Node> nodes, Orientation o) {
		path = new ArrayList<>();

		if (nodes != null && !nodes.isEmpty()) {
			final Project root = EmfUtil.getProject(nodes.iterator().next());
			for (int i = 1; i < nodes.size(); i++) {
				path.add(SegmentUtil.getByNodePair(root, nodes.get(i - 1), nodes.get(i)));
			}

			from = nodes.get(0);
			to = nodes.get(nodes.size() - 1);
			orientation = o;
		}
	}

	public SegmentPath(SegmentPath path2, Segment s) {
		path = new ArrayList<>(path2.size() + 1);
		path.addAll(path2.path);
		path.add(s);
		orientation = path2.orientation;
	}

	Collection<Node> getNodes() {
		final List<Node> nodes = new ArrayList<>(path.size() + 1);

		nodes.add(path.get(0).getFrom());
		for (int i = 0; i < path.size(); i++) {
			nodes.add(path.get(i).getTo());
		}

		return nodes;

	}

	public Segment getLast() {
		return path.get(path.size() - 1);
	}

	public Segment getFirst() {
		return path.get(0);
	}

	public Node getLastNode() {
		if (orientation == Orientation.LEFT) {
			return path.get(path.size() - 1).getTo();
		} else {
			return path.get(path.size() - 1).getFrom();
		}
	}

	public Node getFirstNode() {
		if (orientation == Orientation.LEFT) {
			return path.get(path.size() - 1).getFrom();
		} else {
			return path.get(path.size() - 1).getTo();
		}
	}

	public static SegmentPath getLongest(Collection<SegmentPath> paths) {
		SegmentPath r = null;
		long lmax = Long.MIN_VALUE;
		for (final SegmentPath sp : paths) {
			if (sp.getLength() > lmax) {
				lmax = sp.getLength();
				r = sp;
			}
		}

		return r;
	}

	public static SegmentPath getShortest(Collection<SegmentPath> paths) {
		SegmentPath r = null;
		long lmin = Long.MAX_VALUE;
		for (final SegmentPath sp : paths) {
			if (sp.getLength() < lmin) {
				lmin = sp.getLength();
				r = sp;
			}
		}

		return r;
	}

	public boolean endsWithSignal() {
		if (orientation == Orientation.LEFT) {
			return SignalUtil.getLeftSignalOn(path.get(path.size() - 1)) != null;
		} else {
			return SignalUtil.getRightSignalOn(path.get(path.size() - 1)) != null;
		}
	}

	public long getLength() {
		if (length == Long.MIN_VALUE) {
			long l = 0;
			for (final Segment s : path) {
				l += s.getLength();
			}

			length = l;
		}
		return length;
	}

	public boolean isAmbiguous() {
		return ambiguous;
	}

	public void setAmbiguous(boolean ambiguous) {
		this.ambiguous = ambiguous;
	}

	public void prepend(Segment s) {
		path.add(0, s);
		from = s;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public String getCanonicalName() {
		final StringBuffer name = new StringBuffer();

		Labeled f = from, t = to;

		if (f.getLabel() == null && from_node != null) {
			f = from_node;
		}

		if (t.getLabel() == null && to_node != null) {
			t = to_node;
		}

		if (f.getLabel() != null) {
			name.append(f.getLabel());
		} else {
			name.append("X");
		}

		if (to != null && t.getLabel() != null) {
			name.append("_");
			name.append(t.getLabel());
		}

		String nm = name.toString();

		if (nm.length() == 0 || nm.startsWith("_") || nm.endsWith("_")) {
			final String base = nm.length() > 1 ? nm : "path";
			nm = ScopeB.getUniqueName(EmfUtil.getProject(from), base);
		}

		return nm;
	}

	public Labeled getTo() {
		return to;
	}

	public void setTo(Labeled to) {
		this.to = to;
	}

	public List<Segment> getPath() {
		return path;
	}

	public Labeled getFrom() {
		return from;
	}

	public Node getFromNode() {
		return from_node;
	}

	public void setFromNode(Node from_node) {
		this.from_node = from_node;
	}

	public Node getToNode() {
		return to_node;
	}

	public void setToNode(Node to_node) {
		this.to_node = to_node;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (path == null ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SegmentPath other = (SegmentPath) obj;
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!path.equals(other.path)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return path.toString();
	}

	public int size() {
		return path.size();
	}

	public Segment get(int i) {
		return path.get(i);
	}

}
