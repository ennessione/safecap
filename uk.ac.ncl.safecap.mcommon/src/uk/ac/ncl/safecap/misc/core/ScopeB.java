package uk.ac.ncl.safecap.misc.core;

import org.eclipse.emf.ecore.EObject;

import safecap.Labeled;
import safecap.Project;
import safecap.schema.Node;
import safecap.trackside.Equipment;
import safecap.trackside.Signal;

/**
 * Scope B is the naming scope of nodes and signals
 * 
 * @author alex
 *
 */
public class ScopeB {
	public static String getUniqueName(Project root, String base) {
		int index = 1;
		String candidate = base;

		while (getByLabel(root, candidate) != null) {
			candidate = base + index++;
		}

		return candidate;
	}

	public static EObject getByLabel(Project root, String label) {
		if (label == null) {
			return null;
		}

		for (final Equipment e : root.getEquipment()) {
			if (label.equals(e.getLabel())) {
				return e;
			}
		}

		for (final Node n : root.getNodes()) {
			if (label.equals(n.getLabel())) {
				return n;
			}
		}

		return null;
	}

	public static boolean isUniqueLabel(Labeled object, String label) {
		final Project root = EmfUtil.getProject(object);
		for (final Equipment e : root.getEquipment()) {
			if (e != object && label.equals(e.getLabel()) && e instanceof Signal) {
				return false;
			}
		}

		for (final Node n : root.getNodes()) {
			if (n != object && label.equals(n.getLabel())) {
				return false;
			}
		}

		return true;
	}
}
