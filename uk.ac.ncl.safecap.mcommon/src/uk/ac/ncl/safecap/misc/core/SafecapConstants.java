package uk.ac.ncl.safecap.misc.core;

import org.eclipse.swt.SWT;

public class SafecapConstants {
	public static final String SDA_PROFILE_FOLDER = "Profiles";
	public static final String SDA_PROJECT_FOLDER = "Projects";
	public static final String SDA_SHARE_PROPERY = "sdafolder";

	// The navigator view ID
	public static final String NAVIGATOR_ID = "uk.ac.ncl.safecap.navigator.main"; //$NON-NLS-1$

	// Capacity file extension
	public static final String CAPACITY_LOG_EXTENSION = "capacity"; //$NON-NLS-1$

	// Verification file extension
	public static final String VERIFICATION_LOG_EXTENSION = "verification"; //$NON-NLS-1$

	// Schema file extension
	public static final String SAFECAP_FILE_EXTENSION = "safecap"; //$NON-NLS-1$

	public static final String TRANSITIONS_EXTENSION = "transitions"; //$NON-NLS-1$
	public static final String VCATALOG_EXTENSION = "vcatalog"; //$NON-NLS-1$
	public static final String XMLDATA_EXTENSION = "xmldata"; //$NON-NLS-1$
	public static final String S2_EXTENSION = "s2system"; //$NON-NLS-1$
	public static final String S2INS_EXTENSION = "s2ins"; //$NON-NLS-1$
	public static final String CSV_EXTENSION = "s2ins"; //$NON-NLS-1$
	public static final double DEFAULT_SPEED_LIMIT = 120;
	public static final String CONTRACT_EXTENSION = "contract";
	public static final String DEFINITION_EXTENSION = "definition";
	public static final String CONFIGURATION_EXTENSION = "configuration";
	public static final String EXTRACTOR_EXTENSION = "extractor";

	public static final String EXT_LDL_GEN = "uk.ac.ncl.safecap.ldl";
	public static final String EXT_ORIENTATION_DOMAIN = "ext.orientation";
	public static final String EXT_SUBROUTE_PATH = "ext.subroute.path";
	public static final String EXT_SUBROUTE_LENGTH = "ext.subroute.len";
	public static final String EXT_SUBOVERLAP = "ext.suboverlap";
	public static final String EXT_SUBOVERLAP_PLUS = "ext.suboverlap.plus";

	public static final String EXT_SUBROUTE = "ext.subroute";
	public static final String EXT_SUBROUTE_FORCED = "ext.subroute.forced";
	public static final String EXT_SUBROUTE_RENAMED = "ext.subroute.renamed";

	public static final String EXT_NODE_TAG = "ssi.node.tag";
	public static final String EXT_NODE_TAG_PROTECT = "ssi.node.tag.protect";
	public static final String EXT_SIGNAL = "ext.signal";
	public static final String EXT_AMBIT = "ext.ambit";
	public static final String EXT_OVERLAP_PATH = "ext.overlap.path";
	public static final String EXT_OVERLAP_LENGTH = "ext.overlap.len";
	public static final String EXT_OVERLAP_KIND = "ext.overlap.full";
	public static final String EXT_OVERLAP_CUSTOM = "ext.overlap.custom";
	public static final String EXT_POINT = "ext.point";
	public static final String EXT_ROUTE = "ext.route";
	public static final String EXT_ROUTE_CLASS = "class";
	public static final String EXT_ROUTE_SUBCLASS = "subclass";
	public static final String EXT_ROUTE_PRESET = "preset";
	public static final String EXT_POINT_IS_TRAP = "trap";
	public static final String EXT_PORT = "segment.port";
	public static final String EXT_SCHEMA_FLAG = "schema.flag";
	public static final String EXT_SAFECAP_VALIDATION = "safecap.validation";
	public static final String EXT_VIRTUAL = "ext.virtual";

	public static final String EXT_PROJECT_FINGERPRINT = "ext.project.fingerprint";

	public static final String EXT_OVERLAP_END_NODE = "ext.overlap.endnode";

	public static final int FULL_OVERLAP_LENGTH = 183;
	public static final int FULL_OVERLAP_LENGTH_MIN = 173;
	public static final int FULL_OVERLAP_LENGTH_MAX = 193;

	public static final String TRACK_AXLE_COUNTER = "axlecounter";
	public static final String SIGNAL_WARNER = "warner";
	public static final String EXT_SSI = "ext.ssi";
	public static final String EXT_SSI_INTERLOCKING = "interlocking";
	public static final String EXT_SSI_IDENTITIES = "identities";

	public static final int GET_EDITOR_CONTROL_KEY() {
		return SWT.CTRL;
	}

}
