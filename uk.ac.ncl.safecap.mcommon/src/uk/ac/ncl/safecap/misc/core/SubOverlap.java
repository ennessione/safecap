package uk.ac.ncl.safecap.misc.core;

import java.util.List;

import safecap.Orientation;
import safecap.model.Ambit;
import safecap.schema.Node;

public class SubOverlap {
	private final Ambit ambit;
	private final String direction;
	private int length;
	private String name;

	public SubOverlap(Ambit ambit, String direction) {
		this.ambit = ambit;
		this.direction = direction;
		length = getMyLength();
	}
	
	private int getMyLength() {
//		if (ambit instanceof MergedAmbit) {
//			MergedAmbit ma = (MergedAmbit) ambit;
//			for(Ambit a: ma.getAmbits()) {
//				Integer l = getMyLength(a);
//				if (l != null)
//					return l;
//			}
//			return 0;
//		} else {
			return getMyLength(ambit);
//		}
	}
	
	
	private Integer getMyLength(Ambit a) {
		Integer len = ExtensionUtil.getInt(a, SafecapConstants.EXT_SUBROUTE_LENGTH, direction);
		if (len != null) {
			return len;
		} else {
			len = ExtensionUtil.getInt(a, SafecapConstants.EXT_SUBROUTE_LENGTH, getOppositeDirection());
			if (len != null) {
				return len;
			}
		}
		
		return null;
	}	
	
	public SubOverlap(Ambit ambit, String direction, String name) {
		this(ambit, direction);
		this.name = name;
	}	

	public boolean isValid() {
		return getAmbit() != null && getDirection() != null && ExtensionUtil.getBool(getAmbit(), SafecapConstants.EXT_SUBOVERLAP, getDirection());
	}
	
	public Orientation getOrientation() {
		if (direction != null && direction.length() == 2) {
			final char f = direction.charAt(0);
			final char s = direction.charAt(1);
			assert f != s;
			if (f > s) {
				return Orientation.LEFT;
			} else {
				return Orientation.RIGHT;
			}
		}

		return Orientation.ANY;
	}

	public String getOppositeDirection() {
		if (direction != null && direction.length() == 2) {
			final char f = direction.charAt(0);
			final char s = direction.charAt(1);
			return "" + s + "" + f;
		}

		return null;
	}

	public Ambit getAmbit() {
		return ambit;
	}

	public String getDirection() {
		return direction;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ambit == null ? 0 : ambit.hashCode());
		result = prime * result + (direction == null ? 0 : direction.hashCode());
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
		final SubOverlap other = (SubOverlap) obj;
		if (ambit == null) {
			if (other.ambit != null) {
				return false;
			}
		} else if (!ambit.equals(other.ambit)) {
			return false;
		}
		if (direction == null) {
			if (other.direction != null) {
				return false;
			}
		} else if (!direction.equals(other.direction)) {
			return false;
		}
		return true;
	}

	public String getCustomName() {
		return name;
	}

	@Override
	public String toString() {
		if (name != null) {
			return name;
		} else {
			final boolean normalised = ExtensionUtil.getFlag(EmfUtil.getProject(ambit), "normalised");
			if (normalised && ambit.getLabel().length() > 1) {
				return "O" + ambit.getLabel().substring(1) + "-" + direction;
			} else {
				return "O" + ambit.getLabel() + "-" + direction;
			}
		}
	}

	public List<Node> getNodePath() {
//		if (ambit instanceof MergedAmbit) {
//			MergedAmbit ma = (MergedAmbit) ambit;
//			for(Ambit a: ma.getAmbits()) {
//				List<Node> l = getAmbitNodePath(a);
//				if (l != null)
//					return l;
//			}
//			return null;
//		} else {
			return getAmbitNodePath(ambit);
//		}
	}	
	

	public List<Node> getAmbitNodePath(Ambit a) {
		final SubRoute subroute = new SubRoute(a, direction);
		return SubRouteUtil.getNodePath(subroute);
	}
}
