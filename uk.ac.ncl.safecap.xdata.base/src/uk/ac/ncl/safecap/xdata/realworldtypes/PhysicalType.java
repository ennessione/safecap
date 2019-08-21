package uk.ac.ncl.safecap.xdata.realworldtypes;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlElement;

public class PhysicalType {
	private TreeMap<PhysicalUnit, Integer> map;
	private String worldType; // Additional qualifier to distinguish between compatible types

	public PhysicalType() {
		map = new TreeMap<>();
	}

	public PhysicalType(String worldType) {
		map = new TreeMap<>();
		this.worldType = worldType;
	}

	public static PhysicalType parse(String string) {
		if (string == null || string.length() == 0) {
			return null;
		}

		String wtype = null;

		final String sections[] = string.split(":");
		if (sections.length == 2) {
			string = sections[0];
			wtype = sections[1];
		} else if (sections.length != 1) {
			return null;
		}

		final String parts[] = string.split("\\*");

		final PhysicalType ptype = new PhysicalType(wtype);

		for (final String p : parts) {
			if (!ptype.parseAndInject(p)) {
				return null;
			}
		}

		return ptype;
	}

	private boolean parseAndInject(String part) {
		final String parts[] = part.split("\\^");
		int power = 1;
		PhysicalUnit unit = null;
		if (parts.length == 2) {
			try {
				power = Integer.parseInt(parts[1]);
				if (power == 0) {
					return false;
				}
			} catch (final NumberFormatException e) {
				return false;
			}
			unit = parseUnit(parts[0]);
		} else if (parts.length == 1) {
			unit = parseUnit(parts[0]);
		} else {
			return false;
		}

		map.put(unit, power);

		return true;
	}

	public static PhysicalUnit parseUnit(String unit) {
		if (unit == null || unit.length() == 0) {
			return null;
		}

		if (unit.equals(PTAmpere.INSTANCE.getTag())) {
			return PTAmpere.INSTANCE;
		} else if (unit.equals(PTCandela.INSTANCE.getTag())) {
			return PTCandela.INSTANCE;
		} else if (unit.equals(PTKelvin.INSTANCE.getTag())) {
			return PTKelvin.INSTANCE;
		} else if (unit.equals(PTKilogram.INSTANCE.getTag())) {
			return PTKilogram.INSTANCE;
		} else if (unit.equals(PTMetre.INSTANCE.getTag())) {
			return PTMetre.INSTANCE;
		} else if (unit.equals(PTMole.INSTANCE.getTag())) {
			return PTMole.INSTANCE;
		} else if (unit.equals(PTSecond.INSTANCE.getTag())) {
			return PTSecond.INSTANCE;
		} else {
			return null;
		}
	}

	@XmlElement
	public SortedMap<PhysicalUnit, Integer> getMap() {
		return map;
	}

	public void setMap(TreeMap<PhysicalUnit, Integer> map) {
		this.map = map;
	}

	@XmlElement
	public String getWorldType() {
		return worldType;
	}

	public void setWorldType(String type) {
		worldType = type;
	}

	public boolean hasWorldType() {
		return worldType != null;
	}

	public static PhysicalType multiply(PhysicalType a, PhysicalType b) {
		final PhysicalType c = new PhysicalType();
		c.map.putAll(a.map);

		for (final PhysicalUnit t : b.map.keySet()) {
			c.insert(t, b.map.get(t));
		}

		return c;
	}

	public static PhysicalType divide(PhysicalType a, PhysicalType b) {
		final PhysicalType c = new PhysicalType();
		c.map.putAll(a.map);

		for (final PhysicalUnit t : b.map.keySet()) {
			c.insert(t, -b.map.get(t));
		}

		return c;
	}

	public void insert(PhysicalUnit type, int power) {
		int type_power;
		if (map.containsKey(type)) {
			type_power = map.get(type);
		} else {
			type_power = 0;
		}

		map.put(type, type_power + power);
	}

	public int power(PhysicalUnit type) {
		if (map.containsKey(type)) {
			return map.get(type);
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		boolean first = true;
		for (final PhysicalUnit pt : map.keySet()) {
			if (!first) {
				sb.append("*");
			} else {
				first = false;
			}
			sb.append(pt.getTag());
			if (power(pt) != 1) {
				sb.append("^");
				sb.append(power(pt));
			}
		}

		return sb.toString();
	}

	public String toStringLong() {
		final StringBuilder sb = new StringBuilder();

		boolean first = true;
		for (final PhysicalUnit pt : map.keySet()) {
			if (!first) {
				sb.append("*");
			} else {
				first = false;
			}
			sb.append(pt.getName());
			if (power(pt) != 1) {
				sb.append("^");
				sb.append(power(pt));
			}
		}

		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (map == null ? 0 : map.hashCode());
		result = prime * result + (worldType == null ? 0 : worldType.hashCode());
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
		final PhysicalType other = (PhysicalType) obj;
		if (map == null) {
			if (other.map != null) {
				return false;
			}
		} else if (!map.equals(other.map)) {
			return false;
		}
		if (worldType == null) {
			if (other.worldType != null) {
				return false;
			}
		} else if (!worldType.equals(other.worldType)) {
			return false;
		}
		return true;
	}

}
