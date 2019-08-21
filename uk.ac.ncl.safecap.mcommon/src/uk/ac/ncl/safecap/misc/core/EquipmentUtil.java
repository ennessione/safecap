package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.List;

import safecap.Project;
import safecap.trackside.Equipment;
import safecap.trackside.GenericLocatedEquipment;
import safecap.trackside.Wire;

public class EquipmentUtil {
	public static List<Equipment> getEquipmentByOrigin(Project project, String origin) {
		final List<Equipment> result = new ArrayList<>(10);
		if (origin == null || origin.length() == 0) {
			return result;
		}

		for (final Equipment e : project.getEquipment()) {
			if (origin.equals(e.getOrigin())) {
				result.add(e);
			}
		}

		return result;
	}

	public static Wire getWire(Equipment eq) {
		final Project root = EmfUtil.getProject(eq);
		for (final Wire w : root.getWires()) {
			if (w.getFrom() == eq) {
				return w;
			}
		}

		return null;
	}

	/**
	 * Removes equipment with the wires
	 * 
	 * @param equip
	 */
	public static void removeEquipment(List<Equipment> equip) {
		if (equip == null || equip.size() == 0) {
			return;
		}

		final Project root = EmfUtil.getProject(equip.get(0));

		final List<Wire> toremove = new ArrayList<>(equip.size());

		for (final Equipment e : equip) {
			final Wire w = getWire(e);
			if (w != null) {
				toremove.add(w);
			}
		}

		root.getWires().removeAll(toremove);
		root.getEquipment().removeAll(equip);
	}

	public static GenericLocatedEquipment getGenEqpByLabel(Project root, String s) {
		for (final Equipment eqp : root.getEquipment()) {
			if (eqp instanceof GenericLocatedEquipment) {
				final GenericLocatedEquipment gleqp = (GenericLocatedEquipment) eqp;
				if (s.equals(gleqp.getLabel())) {
					return gleqp;
				}
			}
		}
		return null;
	}
}
