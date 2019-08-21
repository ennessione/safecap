/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import safecap.SafecapPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see safecap.schema.SchemaFactory
 * @model kind="package"
 * @generated
 */
public interface SchemaPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "schema";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "uk.ac.ncl.safecap.emf.schema";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "uk.ac.ncl.safecap.emf.schema";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	SchemaPackage eINSTANCE = safecap.schema.impl.SchemaPackageImpl.init();

	/**
	 * The meta object id for the '{@link safecap.schema.impl.SchemaImpl
	 * <em>Schema</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.schema.impl.SchemaImpl
	 * @see safecap.schema.impl.SchemaPackageImpl#getSchema()
	 * @generated
	 */
	int SCHEMA = 0;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCHEMA__NODES = 0;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCHEMA__SEGMENTS = 1;

	/**
	 * The number of structural features of the '<em>Schema</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCHEMA_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link safecap.schema.impl.SegmentImpl
	 * <em>Segment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.schema.impl.SegmentImpl
	 * @see safecap.schema.impl.SchemaPackageImpl#getSegment()
	 * @generated
	 */
	int SEGMENT = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__LABEL = SafecapPackage.LABELED__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__VISIBLE = SafecapPackage.LABELED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__STYLE = SafecapPackage.LABELED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__INTERVALS = SafecapPackage.LABELED_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__MARKERS = SafecapPackage.LABELED_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__RUNTIME_ATTRIBUTES = SafecapPackage.LABELED_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__ATTRIBUTES = SafecapPackage.LABELED_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__FROM = SafecapPackage.LABELED_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__TO = SafecapPackage.LABELED_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Heightmap</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__HEIGHTMAP = SafecapPackage.LABELED_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Role</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__ROLE = SafecapPackage.LABELED_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Pointrole</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__POINTROLE = SafecapPackage.LABELED_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__LENGTH = SafecapPackage.LABELED_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Speed</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__SPEED = SafecapPackage.LABELED_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Gradient</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__GRADIENT = SafecapPackage.LABELED_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Speedlimit</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__SPEEDLIMIT = SafecapPackage.LABELED_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Orientation</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__ORIENTATION = SafecapPackage.LABELED_FEATURE_COUNT + 15;

	/**
	 * The number of structural features of the '<em>Segment</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT_FEATURE_COUNT = SafecapPackage.LABELED_FEATURE_COUNT + 16;

	/**
	 * The meta object id for the '{@link safecap.schema.impl.NodeImpl
	 * <em>Node</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.schema.impl.NodeImpl
	 * @see safecap.schema.impl.SchemaPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__LABEL = SafecapPackage.LABELED__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__VISIBLE = SafecapPackage.LABELED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__STYLE = SafecapPackage.LABELED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__INTERVALS = SafecapPackage.LABELED_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__MARKERS = SafecapPackage.LABELED_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__RUNTIME_ATTRIBUTES = SafecapPackage.LABELED_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__ATTRIBUTES = SafecapPackage.LABELED_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Altitude</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__ALTITUDE = SafecapPackage.LABELED_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__KIND = SafecapPackage.LABELED_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Roles</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__ROLES = SafecapPackage.LABELED_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Speedlimit</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__SPEEDLIMIT = SafecapPackage.LABELED_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Node</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = SafecapPackage.LABELED_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link safecap.schema.impl.TerminalNodeImpl
	 * <em>Terminal Node</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.schema.impl.TerminalNodeImpl
	 * @see safecap.schema.impl.SchemaPackageImpl#getTerminalNode()
	 * @generated
	 */
	int TERMINAL_NODE = 3;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TERMINAL_NODE__LABEL = NODE__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TERMINAL_NODE__VISIBLE = NODE__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TERMINAL_NODE__STYLE = NODE__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TERMINAL_NODE__INTERVALS = NODE__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TERMINAL_NODE__MARKERS = NODE__MARKERS;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TERMINAL_NODE__RUNTIME_ATTRIBUTES = NODE__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TERMINAL_NODE__ATTRIBUTES = NODE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Altitude</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TERMINAL_NODE__ALTITUDE = NODE__ALTITUDE;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TERMINAL_NODE__KIND = NODE__KIND;

	/**
	 * The feature id for the '<em><b>Roles</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TERMINAL_NODE__ROLES = NODE__ROLES;

	/**
	 * The feature id for the '<em><b>Speedlimit</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TERMINAL_NODE__SPEEDLIMIT = NODE__SPEEDLIMIT;

	/**
	 * The number of structural features of the '<em>Terminal Node</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TERMINAL_NODE_FEATURE_COUNT = NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link safecap.schema.impl.HeightMapImpl
	 * <em>Height Map</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.schema.impl.HeightMapImpl
	 * @see safecap.schema.impl.SchemaPackageImpl#getHeightMap()
	 * @generated
	 */
	int HEIGHT_MAP = 4;

	/**
	 * The feature id for the '<em><b>Points</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HEIGHT_MAP__POINTS = 0;

	/**
	 * The number of structural features of the '<em>Height Map</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HEIGHT_MAP_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link safecap.schema.impl.HeightPointImpl
	 * <em>Height Point</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.schema.impl.HeightPointImpl
	 * @see safecap.schema.impl.SchemaPackageImpl#getHeightPoint()
	 * @generated
	 */
	int HEIGHT_POINT = 5;

	/**
	 * The feature id for the '<em><b>Altitude</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HEIGHT_POINT__ALTITUDE = 0;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HEIGHT_POINT__POSITION = 1;

	/**
	 * The number of structural features of the '<em>Height Point</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HEIGHT_POINT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link safecap.schema.impl.RouteSpeedImpl
	 * <em>Route Speed</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.schema.impl.RouteSpeedImpl
	 * @see safecap.schema.impl.SchemaPackageImpl#getRouteSpeed()
	 * @generated
	 */
	int ROUTE_SPEED = 6;

	/**
	 * The feature id for the '<em><b>Route</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_SPEED__ROUTE = 0;

	/**
	 * The feature id for the '<em><b>Speed</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_SPEED__SPEED = 1;

	/**
	 * The number of structural features of the '<em>Route Speed</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_SPEED_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link safecap.schema.impl.SubSchemaNodeImpl
	 * <em>Sub Schema Node</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.schema.impl.SubSchemaNodeImpl
	 * @see safecap.schema.impl.SchemaPackageImpl#getSubSchemaNode()
	 * @generated
	 */
	int SUB_SCHEMA_NODE = 7;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_NODE__LABEL = NODE__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_NODE__VISIBLE = NODE__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_NODE__STYLE = NODE__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_NODE__INTERVALS = NODE__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_NODE__MARKERS = NODE__MARKERS;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_NODE__RUNTIME_ATTRIBUTES = NODE__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_NODE__ATTRIBUTES = NODE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Altitude</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_NODE__ALTITUDE = NODE__ALTITUDE;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_NODE__KIND = NODE__KIND;

	/**
	 * The feature id for the '<em><b>Roles</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_NODE__ROLES = NODE__ROLES;

	/**
	 * The feature id for the '<em><b>Speedlimit</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_NODE__SPEEDLIMIT = NODE__SPEEDLIMIT;

	/**
	 * The feature id for the '<em><b>Paths</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_NODE__PATHS = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Children</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_NODE__CHILDREN = NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Sub Schema Node</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_NODE_FEATURE_COUNT = NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link safecap.schema.impl.SubSchemaPathImpl
	 * <em>Sub Schema Path</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.schema.impl.SubSchemaPathImpl
	 * @see safecap.schema.impl.SchemaPackageImpl#getSubSchemaPath()
	 * @generated
	 */
	int SUB_SCHEMA_PATH = 8;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_PATH__LENGTH = 0;

	/**
	 * The feature id for the '<em><b>From</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_PATH__FROM = 1;

	/**
	 * The feature id for the '<em><b>To</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_PATH__TO = 2;

	/**
	 * The feature id for the '<em><b>Pnormal</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_PATH__PNORMAL = 3;

	/**
	 * The feature id for the '<em><b>Preverse</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_PATH__PREVERSE = 4;

	/**
	 * The number of structural features of the '<em>Sub Schema Path</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_SCHEMA_PATH_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link safecap.schema.NodeKind <em>Node
	 * Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.schema.NodeKind
	 * @see safecap.schema.impl.SchemaPackageImpl#getNodeKind()
	 * @generated
	 */
	int NODE_KIND = 9;

	/**
	 * The meta object id for the '{@link safecap.schema.NodeRole <em>Node
	 * Role</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.schema.NodeRole
	 * @see safecap.schema.impl.SchemaPackageImpl#getNodeRole()
	 * @generated
	 */
	int NODE_ROLE = 10;

	/**
	 * The meta object id for the '{@link safecap.schema.SegmentRole <em>Segment
	 * Role</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.schema.SegmentRole
	 * @see safecap.schema.impl.SchemaPackageImpl#getSegmentRole()
	 * @generated
	 */
	int SEGMENT_ROLE = 11;

	/**
	 * The meta object id for the '{@link safecap.schema.PointRole <em>Point
	 * Role</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.schema.PointRole
	 * @see safecap.schema.impl.SchemaPackageImpl#getPointRole()
	 * @generated
	 */
	int POINT_ROLE = 12;

	/**
	 * Returns the meta object for class '{@link safecap.schema.Schema
	 * <em>Schema</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Schema</em>'.
	 * @see safecap.schema.Schema
	 * @generated
	 */
	EClass getSchema();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.schema.Schema#getNodes <em>Nodes</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see safecap.schema.Schema#getNodes()
	 * @see #getSchema()
	 * @generated
	 */
	EReference getSchema_Nodes();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.schema.Schema#getSegments <em>Segments</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Segments</em>'.
	 * @see safecap.schema.Schema#getSegments()
	 * @see #getSchema()
	 * @generated
	 */
	EReference getSchema_Segments();

	/**
	 * Returns the meta object for class '{@link safecap.schema.Segment
	 * <em>Segment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Segment</em>'.
	 * @see safecap.schema.Segment
	 * @generated
	 */
	EClass getSegment();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.schema.Segment#getFrom <em>From</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see safecap.schema.Segment#getFrom()
	 * @see #getSegment()
	 * @generated
	 */
	EReference getSegment_From();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.schema.Segment#getTo <em>To</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see safecap.schema.Segment#getTo()
	 * @see #getSegment()
	 * @generated
	 */
	EReference getSegment_To();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link safecap.schema.Segment#getHeightmap <em>Heightmap</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Heightmap</em>'.
	 * @see safecap.schema.Segment#getHeightmap()
	 * @see #getSegment()
	 * @generated
	 */
	EReference getSegment_Heightmap();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.Segment#getRole <em>Role</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Role</em>'.
	 * @see safecap.schema.Segment#getRole()
	 * @see #getSegment()
	 * @generated
	 */
	EAttribute getSegment_Role();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.Segment#getPointrole <em>Pointrole</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Pointrole</em>'.
	 * @see safecap.schema.Segment#getPointrole()
	 * @see #getSegment()
	 * @generated
	 */
	EAttribute getSegment_Pointrole();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.Segment#getLength <em>Length</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see safecap.schema.Segment#getLength()
	 * @see #getSegment()
	 * @generated
	 */
	EAttribute getSegment_Length();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.schema.Segment#getSpeed <em>Speed</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Speed</em>'.
	 * @see safecap.schema.Segment#getSpeed()
	 * @see #getSegment()
	 * @generated
	 */
	EReference getSegment_Speed();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.Segment#getGradient <em>Gradient</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Gradient</em>'.
	 * @see safecap.schema.Segment#getGradient()
	 * @see #getSegment()
	 * @generated
	 */
	EAttribute getSegment_Gradient();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.Segment#getSpeedlimit <em>Speedlimit</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Speedlimit</em>'.
	 * @see safecap.schema.Segment#getSpeedlimit()
	 * @see #getSegment()
	 * @generated
	 */
	EAttribute getSegment_Speedlimit();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.Segment#getOrientation <em>Orientation</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Orientation</em>'.
	 * @see safecap.schema.Segment#getOrientation()
	 * @see #getSegment()
	 * @generated
	 */
	EAttribute getSegment_Orientation();

	/**
	 * Returns the meta object for class '{@link safecap.schema.Node
	 * <em>Node</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Node</em>'.
	 * @see safecap.schema.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.Node#getAltitude <em>Altitude</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Altitude</em>'.
	 * @see safecap.schema.Node#getAltitude()
	 * @see #getNode()
	 * @generated
	 */
	EAttribute getNode_Altitude();

	/**
	 * Returns the meta object for the attribute '{@link safecap.schema.Node#getKind
	 * <em>Kind</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see safecap.schema.Node#getKind()
	 * @see #getNode()
	 * @generated
	 */
	EAttribute getNode_Kind();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.Node#getRoles <em>Roles</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Roles</em>'.
	 * @see safecap.schema.Node#getRoles()
	 * @see #getNode()
	 * @generated
	 */
	EAttribute getNode_Roles();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.Node#getSpeedlimit <em>Speedlimit</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Speedlimit</em>'.
	 * @see safecap.schema.Node#getSpeedlimit()
	 * @see #getNode()
	 * @generated
	 */
	EAttribute getNode_Speedlimit();

	/**
	 * Returns the meta object for class '{@link safecap.schema.TerminalNode
	 * <em>Terminal Node</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Terminal Node</em>'.
	 * @see safecap.schema.TerminalNode
	 * @generated
	 */
	EClass getTerminalNode();

	/**
	 * Returns the meta object for class '{@link safecap.schema.HeightMap <em>Height
	 * Map</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Height Map</em>'.
	 * @see safecap.schema.HeightMap
	 * @generated
	 */
	EClass getHeightMap();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.schema.HeightMap#getPoints <em>Points</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Points</em>'.
	 * @see safecap.schema.HeightMap#getPoints()
	 * @see #getHeightMap()
	 * @generated
	 */
	EReference getHeightMap_Points();

	/**
	 * Returns the meta object for class '{@link safecap.schema.HeightPoint
	 * <em>Height Point</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Height Point</em>'.
	 * @see safecap.schema.HeightPoint
	 * @generated
	 */
	EClass getHeightPoint();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.HeightPoint#getAltitude <em>Altitude</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Altitude</em>'.
	 * @see safecap.schema.HeightPoint#getAltitude()
	 * @see #getHeightPoint()
	 * @generated
	 */
	EAttribute getHeightPoint_Altitude();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.HeightPoint#getPosition <em>Position</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Position</em>'.
	 * @see safecap.schema.HeightPoint#getPosition()
	 * @see #getHeightPoint()
	 * @generated
	 */
	EAttribute getHeightPoint_Position();

	/**
	 * Returns the meta object for class '{@link safecap.schema.RouteSpeed <em>Route
	 * Speed</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Route Speed</em>'.
	 * @see safecap.schema.RouteSpeed
	 * @generated
	 */
	EClass getRouteSpeed();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.schema.RouteSpeed#getRoute <em>Route</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Route</em>'.
	 * @see safecap.schema.RouteSpeed#getRoute()
	 * @see #getRouteSpeed()
	 * @generated
	 */
	EReference getRouteSpeed_Route();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.RouteSpeed#getSpeed <em>Speed</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Speed</em>'.
	 * @see safecap.schema.RouteSpeed#getSpeed()
	 * @see #getRouteSpeed()
	 * @generated
	 */
	EAttribute getRouteSpeed_Speed();

	/**
	 * Returns the meta object for class '{@link safecap.schema.SubSchemaNode
	 * <em>Sub Schema Node</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Sub Schema Node</em>'.
	 * @see safecap.schema.SubSchemaNode
	 * @generated
	 */
	EClass getSubSchemaNode();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.schema.SubSchemaNode#getPaths <em>Paths</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Paths</em>'.
	 * @see safecap.schema.SubSchemaNode#getPaths()
	 * @see #getSubSchemaNode()
	 * @generated
	 */
	EReference getSubSchemaNode_Paths();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link safecap.schema.SubSchemaNode#getChildren <em>Children</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Children</em>'.
	 * @see safecap.schema.SubSchemaNode#getChildren()
	 * @see #getSubSchemaNode()
	 * @generated
	 */
	EAttribute getSubSchemaNode_Children();

	/**
	 * Returns the meta object for class '{@link safecap.schema.SubSchemaPath
	 * <em>Sub Schema Path</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Sub Schema Path</em>'.
	 * @see safecap.schema.SubSchemaPath
	 * @generated
	 */
	EClass getSubSchemaPath();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.SubSchemaPath#getFrom <em>From</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>From</em>'.
	 * @see safecap.schema.SubSchemaPath#getFrom()
	 * @see #getSubSchemaPath()
	 * @generated
	 */
	EAttribute getSubSchemaPath_From();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.SubSchemaPath#getTo <em>To</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>To</em>'.
	 * @see safecap.schema.SubSchemaPath#getTo()
	 * @see #getSubSchemaPath()
	 * @generated
	 */
	EAttribute getSubSchemaPath_To();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.SubSchemaPath#getPnormal <em>Pnormal</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Pnormal</em>'.
	 * @see safecap.schema.SubSchemaPath#getPnormal()
	 * @see #getSubSchemaPath()
	 * @generated
	 */
	EAttribute getSubSchemaPath_Pnormal();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.SubSchemaPath#getPreverse <em>Preverse</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Preverse</em>'.
	 * @see safecap.schema.SubSchemaPath#getPreverse()
	 * @see #getSubSchemaPath()
	 * @generated
	 */
	EAttribute getSubSchemaPath_Preverse();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.schema.SubSchemaPath#getLength <em>Length</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see safecap.schema.SubSchemaPath#getLength()
	 * @see #getSubSchemaPath()
	 * @generated
	 */
	EAttribute getSubSchemaPath_Length();

	/**
	 * Returns the meta object for enum '{@link safecap.schema.NodeKind <em>Node
	 * Kind</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Node Kind</em>'.
	 * @see safecap.schema.NodeKind
	 * @generated
	 */
	EEnum getNodeKind();

	/**
	 * Returns the meta object for enum '{@link safecap.schema.NodeRole <em>Node
	 * Role</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Node Role</em>'.
	 * @see safecap.schema.NodeRole
	 * @generated
	 */
	EEnum getNodeRole();

	/**
	 * Returns the meta object for enum '{@link safecap.schema.SegmentRole
	 * <em>Segment Role</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Segment Role</em>'.
	 * @see safecap.schema.SegmentRole
	 * @generated
	 */
	EEnum getSegmentRole();

	/**
	 * Returns the meta object for enum '{@link safecap.schema.PointRole <em>Point
	 * Role</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Point Role</em>'.
	 * @see safecap.schema.PointRole
	 * @generated
	 */
	EEnum getPointRole();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SchemaFactory getSchemaFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link safecap.schema.impl.SchemaImpl
		 * <em>Schema</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.schema.impl.SchemaImpl
		 * @see safecap.schema.impl.SchemaPackageImpl#getSchema()
		 * @generated
		 */
		EClass SCHEMA = eINSTANCE.getSchema();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SCHEMA__NODES = eINSTANCE.getSchema_Nodes();

		/**
		 * The meta object literal for the '<em><b>Segments</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SCHEMA__SEGMENTS = eINSTANCE.getSchema_Segments();

		/**
		 * The meta object literal for the '{@link safecap.schema.impl.SegmentImpl
		 * <em>Segment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.schema.impl.SegmentImpl
		 * @see safecap.schema.impl.SchemaPackageImpl#getSegment()
		 * @generated
		 */
		EClass SEGMENT = eINSTANCE.getSegment();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SEGMENT__FROM = eINSTANCE.getSegment_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SEGMENT__TO = eINSTANCE.getSegment_To();

		/**
		 * The meta object literal for the '<em><b>Heightmap</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SEGMENT__HEIGHTMAP = eINSTANCE.getSegment_Heightmap();

		/**
		 * The meta object literal for the '<em><b>Role</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SEGMENT__ROLE = eINSTANCE.getSegment_Role();

		/**
		 * The meta object literal for the '<em><b>Pointrole</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SEGMENT__POINTROLE = eINSTANCE.getSegment_Pointrole();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SEGMENT__LENGTH = eINSTANCE.getSegment_Length();

		/**
		 * The meta object literal for the '<em><b>Speed</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SEGMENT__SPEED = eINSTANCE.getSegment_Speed();

		/**
		 * The meta object literal for the '<em><b>Gradient</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SEGMENT__GRADIENT = eINSTANCE.getSegment_Gradient();

		/**
		 * The meta object literal for the '<em><b>Speedlimit</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SEGMENT__SPEEDLIMIT = eINSTANCE.getSegment_Speedlimit();

		/**
		 * The meta object literal for the '<em><b>Orientation</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SEGMENT__ORIENTATION = eINSTANCE.getSegment_Orientation();

		/**
		 * The meta object literal for the '{@link safecap.schema.impl.NodeImpl
		 * <em>Node</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.schema.impl.NodeImpl
		 * @see safecap.schema.impl.SchemaPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '<em><b>Altitude</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute NODE__ALTITUDE = eINSTANCE.getNode_Altitude();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute NODE__KIND = eINSTANCE.getNode_Kind();

		/**
		 * The meta object literal for the '<em><b>Roles</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute NODE__ROLES = eINSTANCE.getNode_Roles();

		/**
		 * The meta object literal for the '<em><b>Speedlimit</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute NODE__SPEEDLIMIT = eINSTANCE.getNode_Speedlimit();

		/**
		 * The meta object literal for the '{@link safecap.schema.impl.TerminalNodeImpl
		 * <em>Terminal Node</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.schema.impl.TerminalNodeImpl
		 * @see safecap.schema.impl.SchemaPackageImpl#getTerminalNode()
		 * @generated
		 */
		EClass TERMINAL_NODE = eINSTANCE.getTerminalNode();

		/**
		 * The meta object literal for the '{@link safecap.schema.impl.HeightMapImpl
		 * <em>Height Map</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.schema.impl.HeightMapImpl
		 * @see safecap.schema.impl.SchemaPackageImpl#getHeightMap()
		 * @generated
		 */
		EClass HEIGHT_MAP = eINSTANCE.getHeightMap();

		/**
		 * The meta object literal for the '<em><b>Points</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference HEIGHT_MAP__POINTS = eINSTANCE.getHeightMap_Points();

		/**
		 * The meta object literal for the '{@link safecap.schema.impl.HeightPointImpl
		 * <em>Height Point</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.schema.impl.HeightPointImpl
		 * @see safecap.schema.impl.SchemaPackageImpl#getHeightPoint()
		 * @generated
		 */
		EClass HEIGHT_POINT = eINSTANCE.getHeightPoint();

		/**
		 * The meta object literal for the '<em><b>Altitude</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute HEIGHT_POINT__ALTITUDE = eINSTANCE.getHeightPoint_Altitude();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute HEIGHT_POINT__POSITION = eINSTANCE.getHeightPoint_Position();

		/**
		 * The meta object literal for the '{@link safecap.schema.impl.RouteSpeedImpl
		 * <em>Route Speed</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.schema.impl.RouteSpeedImpl
		 * @see safecap.schema.impl.SchemaPackageImpl#getRouteSpeed()
		 * @generated
		 */
		EClass ROUTE_SPEED = eINSTANCE.getRouteSpeed();

		/**
		 * The meta object literal for the '<em><b>Route</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROUTE_SPEED__ROUTE = eINSTANCE.getRouteSpeed_Route();

		/**
		 * The meta object literal for the '<em><b>Speed</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ROUTE_SPEED__SPEED = eINSTANCE.getRouteSpeed_Speed();

		/**
		 * The meta object literal for the '{@link safecap.schema.impl.SubSchemaNodeImpl
		 * <em>Sub Schema Node</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see safecap.schema.impl.SubSchemaNodeImpl
		 * @see safecap.schema.impl.SchemaPackageImpl#getSubSchemaNode()
		 * @generated
		 */
		EClass SUB_SCHEMA_NODE = eINSTANCE.getSubSchemaNode();

		/**
		 * The meta object literal for the '<em><b>Paths</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SUB_SCHEMA_NODE__PATHS = eINSTANCE.getSubSchemaNode_Paths();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' attribute list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SUB_SCHEMA_NODE__CHILDREN = eINSTANCE.getSubSchemaNode_Children();

		/**
		 * The meta object literal for the '{@link safecap.schema.impl.SubSchemaPathImpl
		 * <em>Sub Schema Path</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see safecap.schema.impl.SubSchemaPathImpl
		 * @see safecap.schema.impl.SchemaPackageImpl#getSubSchemaPath()
		 * @generated
		 */
		EClass SUB_SCHEMA_PATH = eINSTANCE.getSubSchemaPath();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SUB_SCHEMA_PATH__FROM = eINSTANCE.getSubSchemaPath_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SUB_SCHEMA_PATH__TO = eINSTANCE.getSubSchemaPath_To();

		/**
		 * The meta object literal for the '<em><b>Pnormal</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SUB_SCHEMA_PATH__PNORMAL = eINSTANCE.getSubSchemaPath_Pnormal();

		/**
		 * The meta object literal for the '<em><b>Preverse</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SUB_SCHEMA_PATH__PREVERSE = eINSTANCE.getSubSchemaPath_Preverse();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SUB_SCHEMA_PATH__LENGTH = eINSTANCE.getSubSchemaPath_Length();

		/**
		 * The meta object literal for the '{@link safecap.schema.NodeKind <em>Node
		 * Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.schema.NodeKind
		 * @see safecap.schema.impl.SchemaPackageImpl#getNodeKind()
		 * @generated
		 */
		EEnum NODE_KIND = eINSTANCE.getNodeKind();

		/**
		 * The meta object literal for the '{@link safecap.schema.NodeRole <em>Node
		 * Role</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.schema.NodeRole
		 * @see safecap.schema.impl.SchemaPackageImpl#getNodeRole()
		 * @generated
		 */
		EEnum NODE_ROLE = eINSTANCE.getNodeRole();

		/**
		 * The meta object literal for the '{@link safecap.schema.SegmentRole
		 * <em>Segment Role</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.schema.SegmentRole
		 * @see safecap.schema.impl.SchemaPackageImpl#getSegmentRole()
		 * @generated
		 */
		EEnum SEGMENT_ROLE = eINSTANCE.getSegmentRole();

		/**
		 * The meta object literal for the '{@link safecap.schema.PointRole <em>Point
		 * Role</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.schema.PointRole
		 * @see safecap.schema.impl.SchemaPackageImpl#getPointRole()
		 * @generated
		 */
		EEnum POINT_ROLE = eINSTANCE.getPointRole();

	}

} // SchemaPackage
