package uk.ac.ncl.safecap.misc.core;

import java.util.Collection;

import safecap.Extensible;
import safecap.derived.MergedPoint;
import safecap.model.Ambit;
import safecap.model.Point;
import safecap.schema.Segment;

public class DiagramError {

	/**
	 * Returns attached validation messages
	 * 
	 * @param element extensible element
	 * @param kind    one of error, warning or info
	 * @return
	 */
	public static Collection<String> getValidationRecords(Extensible element, String kind) {
		return ExtensionUtil.getStringValues(element, SafecapConstants.EXT_SAFECAP_VALIDATION, kind);
	}

	/**
	 * Adds a new validation marker to a diagram element
	 * 
	 * @param element extensible element
	 * @param kind    one of error, warning or info
	 * @param message free-form text message
	 */
	public static void addValidationRecord(Extensible element, String kind, String message) {
		element.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_SAFECAP_VALIDATION, kind, message));
	}

	/**
	 * Delete all validation records for the given element
	 * 
	 * @param element
	 * @param kind
	 */
	public static void dropValidationRecords(Extensible element, String kind) {
		ExtensionUtil.delete(element, SafecapConstants.EXT_SAFECAP_VALIDATION, kind);
		if (element instanceof Ambit) {
			final Ambit ambit = (Ambit) element;
			for (final Segment s : ambit.getSegments()) {
				ExtensionUtil.delete(s, SafecapConstants.EXT_SAFECAP_VALIDATION, kind);
			}
		} else if (element instanceof MergedPoint) {
			final MergedPoint point = (MergedPoint) element;
			for (final Point p : point.getPoints()) {
				ExtensionUtil.delete(p.getNode(), SafecapConstants.EXT_SAFECAP_VALIDATION, kind);
			}
		} else if (element instanceof Point) {
			final Point point = (Point) element;
			ExtensionUtil.delete(point.getNode(), SafecapConstants.EXT_SAFECAP_VALIDATION, kind);
		}
	}

}
