package uk.ac.ncl.safecap.misc.core;

import java.util.List;

import safecap.Orientation;
import safecap.model.Ambit;
import safecap.schema.Node;

public class SubRoute {
	private final Ambit ambit;
	private final String direction;
	private int length;
	private List<Node> nodePath;

	private final String alias; // sub route alias enforced by renamed tag

	public SubRoute(Ambit ambit, String direction) {
		this.ambit = ambit;
		this.direction = direction;
		// assert direction.length() == 2 && direction.charAt(0) != direction.charAt(1);
		Integer len = ExtensionUtil.getInt(ambit, SafecapConstants.EXT_SUBROUTE_LENGTH, direction);
		if (len != null) {
			length = len;
		} else {
			len = ExtensionUtil.getInt(ambit, SafecapConstants.EXT_SUBROUTE_LENGTH, getOppositeDirection());
			if (len != null) {
				length = len;
			}
		}

		alias = ExtensionUtil.getString(ambit, SafecapConstants.EXT_SUBROUTE_RENAMED, direction);
	}

	public Node getFirstNode() {
		if (nodePath == null) {
			nodePath = SubRouteUtil.getNodePath(this);
		}

		if (nodePath != null) {
			return nodePath.get(0);
		} else {
			return null;
		}
	}

	public Node getLastNode() {
		if (nodePath == null) {
			nodePath = SubRouteUtil.getNodePath(this);
		}

		if (nodePath != null) {
			return nodePath.get(nodePath.size() - 1);
		} else {
			return null;
		}
	}

	public List<Node> getNodePath() {
		if (nodePath == null) {
			nodePath = SubRouteUtil.getNodePath(this);
		}
		return nodePath;
	}

	public Orientation getOrientation() {
		if (direction != null && direction.length() == 2) {
			final char f = direction.charAt(0);
			final char s = direction.charAt(1);
			// assert f != s;
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
		final SubRoute other = (SubRoute) obj;
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

	/**
	 * Returns sub route canonical name given ambit and direction
	 */
	public static String getCanonicalName(Ambit ambit, String dir) {
		if (ambit.getLabel().startsWith("T")) {
			String name = ambit.getLabel().substring(1);
			if (name.indexOf('-') > 0) {
				name = name.substring(0, name.indexOf('-'));
			}
			return "U" + name + "-" + dir;
		} else {
			return "U" + ambit.getLabel() + "-" + dir;
		}
	}

	@Override
	public String toString() {
		if (alias == null) {
			return getCanonicalName(ambit, direction);
		} else {
			return alias;
		}
	}

}
