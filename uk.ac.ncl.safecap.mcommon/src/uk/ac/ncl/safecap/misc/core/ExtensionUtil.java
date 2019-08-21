package uk.ac.ncl.safecap.misc.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import safecap.Extensible;
import safecap.Project;
import safecap.extension.ExtAttribute;
import safecap.extension.ExtAttributeB;
import safecap.extension.ExtAttributeDbl;
import safecap.extension.ExtAttributeInt;
import safecap.extension.ExtAttributeStr;

public class ExtensionUtil {
	public static final String FILTER_SIGNAL = "filter.signal";
	public static final String FILTER_SPEEDLIMIT = "filter.speedlimit";
	public static final String FILTER_PLATFORMS = "filter.platforms";
	public static final String FILTER_TRACK_LABEL = "filter.track.label";
	public static final String FILTER_TRACK_SU = "filter.track.su"; // sub route markers
	public static final String FILTER_NODE_LABEL = "filter.node.label";
	public static final String FILTER_MARKER = "filter.marker";

	public static boolean getFlag(Extensible element, String flag) {
		return ExtensionUtil.getBool(element, SafecapConstants.EXT_SCHEMA_FLAG, flag);
	}

	public static boolean isVirtual(Extensible element) {
		return ExtensionUtil.getBool(element, SafecapConstants.EXT_SCHEMA_FLAG, SafecapConstants.EXT_VIRTUAL, false);
	}

	public static void setVirtual(Extensible element) {
		setFlag(element, SafecapConstants.EXT_VIRTUAL, true);
	}

	public static void clearVirtual(Extensible element) {
		removeFlag(element, SafecapConstants.EXT_VIRTUAL);
	}

	public static void setFlag(Extensible element, String flag, boolean value) {
		ExtensionUtil.delete(element, SafecapConstants.EXT_SCHEMA_FLAG, flag);
		element.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_SCHEMA_FLAG, flag, value));
	}

	public static void removeFlag(Extensible element, String flag) {
		ExtensionUtil.delete(element, SafecapConstants.EXT_SCHEMA_FLAG, flag);
	}

	private static boolean isElementVisible(EObject obj, String flag) {
		if (obj != null) {
			final Project prj = EmfUtil.getProject(obj);
			if (prj != null) {
				return !ExtensionUtil.getBool(prj, SafecapConstants.EXT_SCHEMA_FLAG, flag, false);
			}
		}

		return true;
	}

	public static boolean isSignalsVisible(EObject obj) {
		return isElementVisible(obj, ExtensionUtil.FILTER_SIGNAL);
	}

	public static boolean isTrackLabelsVisible(EObject obj) {
		return isElementVisible(obj, ExtensionUtil.FILTER_TRACK_LABEL);
	}

	public static boolean isSpeedLimitsVisible(EObject obj) {
		return isElementVisible(obj, ExtensionUtil.FILTER_SPEEDLIMIT);
	}

	public static boolean isPlatformsVisible(EObject obj) {
		return isElementVisible(obj, ExtensionUtil.FILTER_PLATFORMS);
	}

	public static boolean isNodesVisible(EObject obj) {
		return isElementVisible(obj, ExtensionUtil.FILTER_NODE_LABEL);
	}

	public static boolean isTrackSubRouteMarkersVisible(EObject obj) {
		return isElementVisible(obj, ExtensionUtil.FILTER_TRACK_SU);
	}

	public static boolean isMarkerVisible(EObject obj) {
		return isElementVisible(obj, ExtensionUtil.FILTER_MARKER);
	}

	/**
	 * Creates a new string attribute
	 * 
	 * @param domain attribute domain (must be unique to each extension)
	 * @param key    attribute key (for later retrieval)
	 * @param value  attribute value
	 * @return attribute object
	 */
	public static ExtAttributeStr mkString(String domain, String key, String value) {
		final ExtAttributeStr ext = safecap.extension.ExtensionFactory.eINSTANCE.createExtAttributeStr();
		ext.setDomain(domain);
		ext.setLabel(key);
		ext.setValue(value);
		return ext;
	}

	/**
	 * Creates a new boolean attribute
	 * 
	 * @param domain attribute domain (must be unique to each extension)
	 * @param key    attribute key (for later retrieval)
	 * @param value  attribute value
	 * @return attribute object
	 */
	public static ExtAttributeB mkBool(String domain, String key, Boolean value) {
		final ExtAttributeB ext = safecap.extension.ExtensionFactory.eINSTANCE.createExtAttributeB();
		ext.setDomain(domain);
		ext.setLabel(key);
		ext.setValue(value);
		return ext;
	}

	/**
	 * Creates a new integer attribute
	 * 
	 * @param domain attribute domain (must be unique to each extension)
	 * @param key    attribute key (for later retrieval)
	 * @param value  attribute value
	 * @return attribute object
	 */
	public static ExtAttributeInt mkInt(String domain, String key, Integer value) {
		final ExtAttributeInt ext = safecap.extension.ExtensionFactory.eINSTANCE.createExtAttributeInt();
		ext.setDomain(domain);
		ext.setLabel(key);
		ext.setValue(value);
		return ext;
	}

	/**
	 * Creates a new double attribute
	 * 
	 * @param domain attribute domain (must be unique to each extension)
	 * @param key    attribute key (for later retrieval)
	 * @param value  attribute value
	 * @return attribute object
	 */
	public static ExtAttributeDbl mkDouble(String domain, String key, Double value) {
		final ExtAttributeDbl ext = safecap.extension.ExtensionFactory.eINSTANCE.createExtAttributeDbl();
		ext.setDomain(domain);
		ext.setLabel(key);
		ext.setValue(value);
		return ext;
	}

	/**
	 * Returns an extension attribute value
	 * 
	 * @param element an extensible element
	 * @param domain  the domain of the extension
	 * @param key     the key value
	 * @return string value if the attribute is present, null otherwise
	 */
	public static String getString(Extensible element, String domain, String key) {
		for (final ExtAttribute attr : element.getAttributes()) {
			if (attr instanceof ExtAttributeStr && attr.getDomain().equals(domain) && attr.getLabel().equals(key)) {
				return ((ExtAttributeStr) attr).getValue();
			}
		}
		return null;
	}

	public static String getString(Extensible element, String domain, String key, String defaultValue) {
		final String val = getString(element, domain, key);
		if (val == null) {
			return defaultValue;
		}
		return val;
	}

	public static Collection<ExtAttribute> getAll(Extensible element, String domain) {
		final HashSet<ExtAttribute> result = new HashSet<>();
		for (final ExtAttribute attr : element.getAttributes()) {
			if (attr instanceof ExtAttributeStr && attr.getDomain().equals(domain)) {
				result.add(attr);
			}
		}

		return result;
	}

	/**
	 * Returns an extension attribute value
	 * 
	 * @param element an extensible element
	 * @param domain  the domain of the extension
	 * @param key     the key value
	 * @return boolean value if the attribute is present, null otherwise
	 */
	public static boolean getBool(Extensible element, String domain, String key) {
		return getBool(element, domain, key, false);
	}

	public static boolean getBool(Extensible element, String domain, String key, boolean def) {
		for (final ExtAttribute attr : element.getAttributes()) {
			if (attr instanceof ExtAttributeB && attr.getDomain().equals(domain) && attr.getLabel().equals(key)) {
				return ((ExtAttributeB) attr).isValue();
			}
		}
		return def;
	}

	/**
	 * Returns an extension attribute value
	 * 
	 * @param element an extensible element
	 * @param domain  the domain of the extension
	 * @param key     the key value
	 * @return integer value if the attribute is present, null otherwise
	 */
	public static Integer getInt(Extensible element, String domain, String key) {
		for (final ExtAttribute attr : element.getAttributes()) {
			if (attr instanceof ExtAttributeInt && attr.getDomain().equals(domain) && attr.getLabel().equals(key)) {
				return ((ExtAttributeInt) attr).getValue();
			}
		}
		return null;
	}

	/**
	 * Returns an extension attribute value
	 * 
	 * @param element an extensible element
	 * @param domain  the domain of the extension
	 * @param key     the key value
	 * @return double value if the attribute is present, null otherwise
	 */
	public static Double getDouble(Extensible element, String domain, String key) {
		for (final ExtAttribute attr : element.getAttributes()) {
			if (attr instanceof ExtAttributeDbl && attr.getDomain().equals(domain) && attr.getLabel().equals(key)) {
				return ((ExtAttributeDbl) attr).getValue();
			}
		}
		return null;
	}

	public static void delete(Extensible element, String domain, String key) {
		final Set<ExtAttribute> toDelete = new HashSet<>();
		for (final ExtAttribute attr : element.getAttributes()) {
			if (attr.getDomain().equals(domain) && attr.getLabel().equals(key)) {
				toDelete.add(attr);
			}
		}

		element.getAttributes().removeAll(toDelete);
	}

	public static void delete(Extensible element, String domain) {
		final Set<ExtAttribute> toDelete = new HashSet<>();
		for (final ExtAttribute attr : element.getAttributes()) {
			if (attr.getDomain().equals(domain)) {
				toDelete.add(attr);
			}
		}

		element.getAttributes().removeAll(toDelete);
	}

	public static String findKey(Extensible element, String domain, String value) {
		for (final ExtAttribute attr : element.getAttributes()) {
			if (attr instanceof ExtAttributeStr && attr.getDomain().equals(domain) && ((ExtAttributeStr) attr).getValue().equals(value)) {
				return attr.getLabel();
			}
		}

		return null;
	}

	public static Collection<String> getKeys(Extensible element, String domain) {
		final Collection<String> result = new HashSet<>();
		for (final ExtAttribute attr : element.getAttributes()) {
			if (attr.getDomain().equals(domain)) {
				result.add(attr.getLabel());
			}
		}

		return result;
	}

	public static boolean hasKey(Extensible element, String domain) {
		for (final ExtAttribute attr : element.getAttributes()) {
			if (attr.getDomain().equals(domain)) {
				return true;
			}
		}

		return false;
	}

	public static Collection<String> getStringValues(Extensible element, String domain, String label) {
		final Collection<String> result = new HashSet<>();
		for (final ExtAttribute attr : element.getAttributes()) {
			if (attr.getDomain().equals(domain) && attr.getLabel().equals(label) && attr instanceof ExtAttributeStr) {
				result.add(((ExtAttributeStr) attr).getValue());
			}
		}

		return result;
	}

	public static boolean hasValue(Extensible element, String domain, String key) {
		for (final ExtAttribute attr : element.getAttributes()) {
			if (attr.getDomain().equals(domain) && attr.getLabel().equals(key)) {
				return true;
			}
		}

		return false;
	}
}
