package uk.ac.ncl.safecap.misc.core;

import org.eclipse.emf.ecore.EObject;

import safecap.Project;
import safecap.model.Ambit;
import safecap.schema.Segment;

/**
 * Scope C is the naming scope of segments
 * 
 * @author alex
 *
 */
public class ScopeC {
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

		for (final Segment s : root.getSegments()) {
			if (label.equals(s.getLabel())) {
				return s;
			}
		}

		return null;
	}

	public static boolean isUniqueLabel(Segment segment, String label) {
		final Project root = EmfUtil.getProject(segment);
		final Ambit ambit = SegmentUtil.getSegmentAmbit(segment);

		for (final Segment s : root.getSegments()) {
			if (s != segment && label.equals(s.getLabel())) {
				final Ambit a = SegmentUtil.getSegmentAmbit(s);
				if (ambit != a) {
					return false;
				}
			}
		}

		return true;
	}
}
