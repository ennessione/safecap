package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import safecap.Labeled;
import safecap.Orientation;
import safecap.derived.MergedAmbit;
import safecap.model.Ambit;
import safecap.schema.Segment;

public class AmbitPath {
	private final List<Ambit> path;
	private final SegmentPath segmentPath;
	private int version = 0;
	private boolean ambiguous = false;

	public AmbitPath(SegmentPath segmentPath, Map<Segment, Ambit> map, Map<Ambit, Collection<MergedAmbit>> composedMap) {
		path = new ArrayList<>();
		this.segmentPath = segmentPath;
		ambiguous = segmentPath.isAmbiguous();
		segmentPathToAmbitPath(map, composedMap);
	}

	public Ambit getLast() {
		return path.get(path.size() - 1);
	}

	public boolean isAmbiguous() {
		return ambiguous;
	}

	private void segmentPathToAmbitPath(Map<Segment, Ambit> map, Map<Ambit, Collection<MergedAmbit>> composedMap) {
		for (final Segment s : segmentPath.getPath()) {
			final Ambit a = map.get(s);
			assert a != null;
			if (path.size() == 0 || path.get(path.size() - 1) != a) {
				path.add(a);
			}
		}
	}

	public Orientation getOrientation() {
		return segmentPath.getOrientation();
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getVersion() {
		return version;
	}

	public String getCanonicalName() {
		return segmentPath.getCanonicalName() + (version > 0 ? "_" + version : "");
	}

	public String getBaseName() {
		return segmentPath.getCanonicalName();
	}

	public Labeled getTo() {
		return segmentPath.getTo();
	}

	public List<Ambit> getPath() {
		return path;
	}

	public List<Segment> getSegmentPath() {
		return segmentPath.getPath();
	}

	public Labeled getFrom() {
		return segmentPath.getFrom();
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
		final AmbitPath other = (AmbitPath) obj;
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

}
