package uk.ac.ncl.safecap.misc.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import safecap.Project;
import safecap.derived.MergedAmbit;
import safecap.model.Ambit;

public class SubOverlapUtil {
	public static Collection<SubOverlap> getSubOverlap(SubRoute subroute) {
		if (ExtensionUtil.getBool(subroute.getAmbit(), SafecapConstants.EXT_SUBOVERLAP, subroute.getDirection())) {
			return getSubOverlaps(subroute.getAmbit(), subroute.getDirection());
			//return new SubOverlap(subroute.getAmbit(), subroute.getDirection());
		}

		if (AmbitUtil.isDisjoint(subroute.getAmbit())) {
			final MergedAmbit ma = (MergedAmbit) subroute.getAmbit();
			for (final Ambit a : ma.getAmbits()) {
				// final Collection<ExtAttribute> vals = ExtensionUtil.getAll(a,
				// SafecapConstants.EXT_SUBOVERLAP);
				if (ExtensionUtil.getBool(a, SafecapConstants.EXT_SUBOVERLAP, subroute.getDirection())) {
					return getSubOverlaps(a, subroute.getDirection());
				}
			}
		}

		return Collections.emptySet();
	}

	public static SubOverlap getSubOverlapFromName(Project root, String name) {
		final int i = name.indexOf('-');
		if (i > 0) {
			String ambitName = name.substring(1, i);
			final int i2 = name.indexOf('-', i + 1);
			final String orientation = i2 == -1 ? name.substring(i + 1) : name.substring(i + 1, i2);

			final boolean normalised = ExtensionUtil.getFlag(root, "normalised");
			if (normalised) {
				ambitName = "T" + ambitName;
			}

			final Ambit ambit = AmbitUtil.getByLabel(root, ambitName);
			if (ambit != null && orientation.length() == 2) {
				SubOverlap so = new SubOverlap(ambit, orientation);

				return so;
			} else if (ambit != null) {
				return getNamedSubOverlap(ambit, name);
			}
		}
		return null;
	}

	public static Set<SubOverlap> getSubOverlaps(Ambit ambit) {
		final Set<SubOverlap> result = new HashSet<>();
		for (final String k : ExtensionUtil.getKeys(ambit, SafecapConstants.EXT_SUBROUTE))
			result.addAll(getSubOverlaps(ambit, k));

		return result;
	}

	public static Set<SubOverlap> getSubOverlaps(Ambit ambit, String direction) {
		final Set<SubOverlap> result = new HashSet<>();
		if (ExtensionUtil.getBool(ambit, SafecapConstants.EXT_SUBOVERLAP, direction)) {
			for (String s : ExtensionUtil.getStringValues(ambit, SafecapConstants.EXT_SUBOVERLAP_PLUS, direction)) {
				result.add(new SubOverlap(ambit, direction, s));
			}
			result.add(new SubOverlap(ambit, direction));
		}

		return result;
	}

	public static SubOverlap getNamedSubOverlap(Ambit ambit, String name) {
		for (final String k : ExtensionUtil.getKeys(ambit, SafecapConstants.EXT_SUBROUTE)) {
			if (ExtensionUtil.getBool(ambit, SafecapConstants.EXT_SUBOVERLAP, k)) {
				for (String s : ExtensionUtil.getStringValues(ambit, SafecapConstants.EXT_SUBOVERLAP_PLUS, k))
					if (s.equals(name))
						return new SubOverlap(ambit, k, s);
			}
		}

		return null;
	}

	public static boolean hasSubOverlaps(Ambit ambit) {
		return ExtensionUtil.hasKey(ambit, SafecapConstants.EXT_SUBOVERLAP);
	}

	public static boolean hasSubOverlaps(SubRoute subroute) {
		return ExtensionUtil.getBool(subroute.getAmbit(), SafecapConstants.EXT_SUBOVERLAP, subroute.getDirection());
	}

}
