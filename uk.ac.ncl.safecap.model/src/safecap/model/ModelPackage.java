/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see safecap.model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "uk.ac.ncl.safecap.emf.model";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "uk.ac.ncl.safecap.emf.model";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	ModelPackage eINSTANCE = safecap.model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link safecap.model.impl.ModelImpl
	 * <em>Model</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.model.impl.ModelImpl
	 * @see safecap.model.impl.ModelPackageImpl#getModel()
	 * @generated
	 */
	int MODEL = 0;

	/**
	 * The feature id for the '<em><b>Lines</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL__LINES = 0;

	/**
	 * The feature id for the '<em><b>Routes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL__ROUTES = 1;

	/**
	 * The feature id for the '<em><b>Ambits</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL__AMBITS = 2;

	/**
	 * The number of structural features of the '<em>Model</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link safecap.model.impl.LineImpl
	 * <em>Line</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.model.impl.LineImpl
	 * @see safecap.model.impl.ModelPackageImpl#getLine()
	 * @generated
	 */
	int LINE = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINE__LABEL = SafecapPackage.LABELED__LABEL;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINE__RUNTIME_ATTRIBUTES = SafecapPackage.LABELED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINE__ATTRIBUTES = SafecapPackage.LABELED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINE__VISIBLE = SafecapPackage.LABELED_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINE__STYLE = SafecapPackage.LABELED_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINE__INTERVALS = SafecapPackage.LABELED_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINE__MARKERS = SafecapPackage.LABELED_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Routes</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINE__ROUTES = SafecapPackage.LABELED_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINE__COLOR = SafecapPackage.LABELED_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Orientation</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINE__ORIENTATION = SafecapPackage.LABELED_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Trafficmix</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINE__TRAFFICMIX = SafecapPackage.LABELED_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Line</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINE_FEATURE_COUNT = SafecapPackage.LABELED_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link safecap.model.impl.RouteImpl
	 * <em>Route</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.model.impl.RouteImpl
	 * @see safecap.model.impl.ModelPackageImpl#getRoute()
	 * @generated
	 */
	int ROUTE = 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE__LABEL = SafecapPackage.LABELED__LABEL;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE__RUNTIME_ATTRIBUTES = SafecapPackage.LABELED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE__ATTRIBUTES = SafecapPackage.LABELED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ambits</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE__AMBITS = SafecapPackage.LABELED_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE__SEGMENTS = SafecapPackage.LABELED_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Overlap</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE__OVERLAP = SafecapPackage.LABELED_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE__RULE = SafecapPackage.LABELED_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Aspects</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE__ASPECTS = SafecapPackage.LABELED_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Orientation</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE__ORIENTATION = SafecapPackage.LABELED_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Logic</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE__LOGIC = SafecapPackage.LABELED_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Defaultlogic</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE__DEFAULTLOGIC = SafecapPackage.LABELED_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Route</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_FEATURE_COUNT = SafecapPackage.LABELED_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link safecap.model.impl.AmbitImpl
	 * <em>Ambit</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.model.impl.AmbitImpl
	 * @see safecap.model.impl.ModelPackageImpl#getAmbit()
	 * @generated
	 */
	int AMBIT = 3;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AMBIT__LABEL = SafecapPackage.LABELED__LABEL;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AMBIT__RUNTIME_ATTRIBUTES = SafecapPackage.LABELED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AMBIT__ATTRIBUTES = SafecapPackage.LABELED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AMBIT__SEGMENTS = SafecapPackage.LABELED_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Ambit</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AMBIT_FEATURE_COUNT = SafecapPackage.LABELED_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link safecap.model.impl.SectionImpl
	 * <em>Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.model.impl.SectionImpl
	 * @see safecap.model.impl.ModelPackageImpl#getSection()
	 * @generated
	 */
	int SECTION = 4;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SECTION__LABEL = AMBIT__LABEL;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SECTION__RUNTIME_ATTRIBUTES = AMBIT__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SECTION__ATTRIBUTES = AMBIT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SECTION__SEGMENTS = AMBIT__SEGMENTS;

	/**
	 * The number of structural features of the '<em>Section</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SECTION_FEATURE_COUNT = AMBIT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link safecap.model.impl.JunctionImpl
	 * <em>Junction</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.model.impl.JunctionImpl
	 * @see safecap.model.impl.ModelPackageImpl#getJunction()
	 * @generated
	 */
	int JUNCTION = 5;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int JUNCTION__LABEL = AMBIT__LABEL;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int JUNCTION__RUNTIME_ATTRIBUTES = AMBIT__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int JUNCTION__ATTRIBUTES = AMBIT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int JUNCTION__SEGMENTS = AMBIT__SEGMENTS;

	/**
	 * The feature id for the '<em><b>Points</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int JUNCTION__POINTS = AMBIT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Junction</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int JUNCTION_FEATURE_COUNT = AMBIT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link safecap.model.impl.PointImpl
	 * <em>Point</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.model.impl.PointImpl
	 * @see safecap.model.impl.ModelPackageImpl#getPoint()
	 * @generated
	 */
	int POINT = 6;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POINT__RUNTIME_ATTRIBUTES = SafecapPackage.EXTENSIBLE__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POINT__ATTRIBUTES = SafecapPackage.EXTENSIBLE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POINT__NODE = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POINT__RULE = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Point</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POINT_FEATURE_COUNT = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link safecap.model.impl.RuleImpl
	 * <em>Rule</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.model.impl.RuleImpl
	 * @see safecap.model.impl.ModelPackageImpl#getRule()
	 * @generated
	 */
	int RULE = 7;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RULE__RUNTIME_ATTRIBUTES = SafecapPackage.EXTENSIBLE__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RULE__ATTRIBUTES = SafecapPackage.EXTENSIBLE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Clear</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RULE__CLEAR = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Occupied</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RULE__OCCUPIED = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Normal</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RULE__NORMAL = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Reverse</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RULE__REVERSE = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Route State</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RULE__ROUTE_STATE = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Rule</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RULE_FEATURE_COUNT = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link safecap.model.impl.TimedOccupationRuleImpl
	 * <em>Timed Occupation Rule</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see safecap.model.impl.TimedOccupationRuleImpl
	 * @see safecap.model.impl.ModelPackageImpl#getTimedOccupationRule()
	 * @generated
	 */
	int TIMED_OCCUPATION_RULE = 8;

	/**
	 * The feature id for the '<em><b>Ambit</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TIMED_OCCUPATION_RULE__AMBIT = 0;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TIMED_OCCUPATION_RULE__TIME = 1;

	/**
	 * The number of structural features of the '<em>Timed Occupation Rule</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TIMED_OCCUPATION_RULE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link safecap.model.impl.RouteStateRuleImpl
	 * <em>Route State Rule</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.model.impl.RouteStateRuleImpl
	 * @see safecap.model.impl.ModelPackageImpl#getRouteStateRule()
	 * @generated
	 */
	int ROUTE_STATE_RULE = 9;

	/**
	 * The feature id for the '<em><b>Route</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_STATE_RULE__ROUTE = 0;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_STATE_RULE__STATE = 1;

	/**
	 * The number of structural features of the '<em>Route State Rule</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_STATE_RULE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link safecap.model.impl.ControlLogicImpl
	 * <em>Control Logic</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.model.impl.ControlLogicImpl
	 * @see safecap.model.impl.ModelPackageImpl#getControlLogic()
	 * @generated
	 */
	int CONTROL_LOGIC = 10;

	/**
	 * The feature id for the '<em><b>Line</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL_LOGIC__LINE = 0;

	/**
	 * The feature id for the '<em><b>Aspects</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL_LOGIC__ASPECTS = 1;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL_LOGIC__RULE = 2;

	/**
	 * The feature id for the '<em><b>Normal</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL_LOGIC__NORMAL = 3;

	/**
	 * The feature id for the '<em><b>Reverse</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL_LOGIC__REVERSE = 4;

	/**
	 * The number of structural features of the '<em>Control Logic</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL_LOGIC_FEATURE_COUNT = 5;

	/**
	 * Returns the meta object for class '{@link safecap.model.Model
	 * <em>Model</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Model</em>'.
	 * @see safecap.model.Model
	 * @generated
	 */
	EClass getModel();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.model.Model#getLines <em>Lines</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Lines</em>'.
	 * @see safecap.model.Model#getLines()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_Lines();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.model.Model#getRoutes <em>Routes</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Routes</em>'.
	 * @see safecap.model.Model#getRoutes()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_Routes();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.model.Model#getAmbits <em>Ambits</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Ambits</em>'.
	 * @see safecap.model.Model#getAmbits()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_Ambits();

	/**
	 * Returns the meta object for class '{@link safecap.model.Line <em>Line</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Line</em>'.
	 * @see safecap.model.Line
	 * @generated
	 */
	EClass getLine();

	/**
	 * Returns the meta object for the reference list
	 * '{@link safecap.model.Line#getRoutes <em>Routes</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Routes</em>'.
	 * @see safecap.model.Line#getRoutes()
	 * @see #getLine()
	 * @generated
	 */
	EReference getLine_Routes();

	/**
	 * Returns the meta object for the attribute '{@link safecap.model.Line#getColor
	 * <em>Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Color</em>'.
	 * @see safecap.model.Line#getColor()
	 * @see #getLine()
	 * @generated
	 */
	EAttribute getLine_Color();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.model.Line#getOrientation <em>Orientation</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Orientation</em>'.
	 * @see safecap.model.Line#getOrientation()
	 * @see #getLine()
	 * @generated
	 */
	EAttribute getLine_Orientation();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link safecap.model.Line#getTrafficmix <em>Trafficmix</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Trafficmix</em>'.
	 * @see safecap.model.Line#getTrafficmix()
	 * @see #getLine()
	 * @generated
	 */
	EAttribute getLine_Trafficmix();

	/**
	 * Returns the meta object for class '{@link safecap.model.Route
	 * <em>Route</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Route</em>'.
	 * @see safecap.model.Route
	 * @generated
	 */
	EClass getRoute();

	/**
	 * Returns the meta object for the reference list
	 * '{@link safecap.model.Route#getAmbits <em>Ambits</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Ambits</em>'.
	 * @see safecap.model.Route#getAmbits()
	 * @see #getRoute()
	 * @generated
	 */
	EReference getRoute_Ambits();

	/**
	 * Returns the meta object for the reference list
	 * '{@link safecap.model.Route#getSegments <em>Segments</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Segments</em>'.
	 * @see safecap.model.Route#getSegments()
	 * @see #getRoute()
	 * @generated
	 */
	EReference getRoute_Segments();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.model.Route#getOverlap <em>Overlap</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Overlap</em>'.
	 * @see safecap.model.Route#getOverlap()
	 * @see #getRoute()
	 * @generated
	 */
	EReference getRoute_Overlap();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.model.Route#getRule <em>Rule</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Rule</em>'.
	 * @see safecap.model.Route#getRule()
	 * @see #getRoute()
	 * @generated
	 */
	EReference getRoute_Rule();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.model.Route#getAspects <em>Aspects</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Aspects</em>'.
	 * @see safecap.model.Route#getAspects()
	 * @see #getRoute()
	 * @generated
	 */
	EAttribute getRoute_Aspects();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.model.Route#getOrientation <em>Orientation</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Orientation</em>'.
	 * @see safecap.model.Route#getOrientation()
	 * @see #getRoute()
	 * @generated
	 */
	EAttribute getRoute_Orientation();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.model.Route#getLogic <em>Logic</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Logic</em>'.
	 * @see safecap.model.Route#getLogic()
	 * @see #getRoute()
	 * @generated
	 */
	EReference getRoute_Logic();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link safecap.model.Route#getDefaultlogic <em>Defaultlogic</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference
	 *         '<em>Defaultlogic</em>'.
	 * @see safecap.model.Route#getDefaultlogic()
	 * @see #getRoute()
	 * @generated
	 */
	EReference getRoute_Defaultlogic();

	/**
	 * Returns the meta object for class '{@link safecap.model.Ambit
	 * <em>Ambit</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Ambit</em>'.
	 * @see safecap.model.Ambit
	 * @generated
	 */
	EClass getAmbit();

	/**
	 * Returns the meta object for the reference list
	 * '{@link safecap.model.Ambit#getSegments <em>Segments</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Segments</em>'.
	 * @see safecap.model.Ambit#getSegments()
	 * @see #getAmbit()
	 * @generated
	 */
	EReference getAmbit_Segments();

	/**
	 * Returns the meta object for class '{@link safecap.model.Section
	 * <em>Section</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Section</em>'.
	 * @see safecap.model.Section
	 * @generated
	 */
	EClass getSection();

	/**
	 * Returns the meta object for class '{@link safecap.model.Junction
	 * <em>Junction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Junction</em>'.
	 * @see safecap.model.Junction
	 * @generated
	 */
	EClass getJunction();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.model.Junction#getPoints <em>Points</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Points</em>'.
	 * @see safecap.model.Junction#getPoints()
	 * @see #getJunction()
	 * @generated
	 */
	EReference getJunction_Points();

	/**
	 * Returns the meta object for class '{@link safecap.model.Point
	 * <em>Point</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Point</em>'.
	 * @see safecap.model.Point
	 * @generated
	 */
	EClass getPoint();

	/**
	 * Returns the meta object for the reference '{@link safecap.model.Point#getNode
	 * <em>Node</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see safecap.model.Point#getNode()
	 * @see #getPoint()
	 * @generated
	 */
	EReference getPoint_Node();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link safecap.model.Point#getRule <em>Rule</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Rule</em>'.
	 * @see safecap.model.Point#getRule()
	 * @see #getPoint()
	 * @generated
	 */
	EReference getPoint_Rule();

	/**
	 * Returns the meta object for class '{@link safecap.model.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Rule</em>'.
	 * @see safecap.model.Rule
	 * @generated
	 */
	EClass getRule();

	/**
	 * Returns the meta object for the reference list
	 * '{@link safecap.model.Rule#getClear <em>Clear</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Clear</em>'.
	 * @see safecap.model.Rule#getClear()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Clear();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.model.Rule#getOccupied <em>Occupied</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Occupied</em>'.
	 * @see safecap.model.Rule#getOccupied()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Occupied();

	/**
	 * Returns the meta object for the reference list
	 * '{@link safecap.model.Rule#getNormal <em>Normal</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Normal</em>'.
	 * @see safecap.model.Rule#getNormal()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Normal();

	/**
	 * Returns the meta object for the reference list
	 * '{@link safecap.model.Rule#getReverse <em>Reverse</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Reverse</em>'.
	 * @see safecap.model.Rule#getReverse()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Reverse();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.model.Rule#getRouteState <em>Route State</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Route
	 *         State</em>'.
	 * @see safecap.model.Rule#getRouteState()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_RouteState();

	/**
	 * Returns the meta object for class '{@link safecap.model.TimedOccupationRule
	 * <em>Timed Occupation Rule</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Timed Occupation Rule</em>'.
	 * @see safecap.model.TimedOccupationRule
	 * @generated
	 */
	EClass getTimedOccupationRule();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.model.TimedOccupationRule#getAmbit <em>Ambit</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Ambit</em>'.
	 * @see safecap.model.TimedOccupationRule#getAmbit()
	 * @see #getTimedOccupationRule()
	 * @generated
	 */
	EReference getTimedOccupationRule_Ambit();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.model.TimedOccupationRule#getTime <em>Time</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see safecap.model.TimedOccupationRule#getTime()
	 * @see #getTimedOccupationRule()
	 * @generated
	 */
	EAttribute getTimedOccupationRule_Time();

	/**
	 * Returns the meta object for class '{@link safecap.model.RouteStateRule
	 * <em>Route State Rule</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Route State Rule</em>'.
	 * @see safecap.model.RouteStateRule
	 * @generated
	 */
	EClass getRouteStateRule();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.model.RouteStateRule#getRoute <em>Route</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Route</em>'.
	 * @see safecap.model.RouteStateRule#getRoute()
	 * @see #getRouteStateRule()
	 * @generated
	 */
	EReference getRouteStateRule_Route();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.model.RouteStateRule#getState <em>State</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see safecap.model.RouteStateRule#getState()
	 * @see #getRouteStateRule()
	 * @generated
	 */
	EAttribute getRouteStateRule_State();

	/**
	 * Returns the meta object for class '{@link safecap.model.ControlLogic
	 * <em>Control Logic</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Control Logic</em>'.
	 * @see safecap.model.ControlLogic
	 * @generated
	 */
	EClass getControlLogic();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.model.ControlLogic#getLine <em>Line</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Line</em>'.
	 * @see safecap.model.ControlLogic#getLine()
	 * @see #getControlLogic()
	 * @generated
	 */
	EReference getControlLogic_Line();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.model.ControlLogic#getAspects <em>Aspects</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Aspects</em>'.
	 * @see safecap.model.ControlLogic#getAspects()
	 * @see #getControlLogic()
	 * @generated
	 */
	EAttribute getControlLogic_Aspects();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.model.ControlLogic#getRule <em>Rule</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Rule</em>'.
	 * @see safecap.model.ControlLogic#getRule()
	 * @see #getControlLogic()
	 * @generated
	 */
	EReference getControlLogic_Rule();

	/**
	 * Returns the meta object for the reference list
	 * '{@link safecap.model.ControlLogic#getNormal <em>Normal</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Normal</em>'.
	 * @see safecap.model.ControlLogic#getNormal()
	 * @see #getControlLogic()
	 * @generated
	 */
	EReference getControlLogic_Normal();

	/**
	 * Returns the meta object for the reference list
	 * '{@link safecap.model.ControlLogic#getReverse <em>Reverse</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Reverse</em>'.
	 * @see safecap.model.ControlLogic#getReverse()
	 * @see #getControlLogic()
	 * @generated
	 */
	EReference getControlLogic_Reverse();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

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
		 * The meta object literal for the '{@link safecap.model.impl.ModelImpl
		 * <em>Model</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.model.impl.ModelImpl
		 * @see safecap.model.impl.ModelPackageImpl#getModel()
		 * @generated
		 */
		EClass MODEL = eINSTANCE.getModel();

		/**
		 * The meta object literal for the '<em><b>Lines</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODEL__LINES = eINSTANCE.getModel_Lines();

		/**
		 * The meta object literal for the '<em><b>Routes</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODEL__ROUTES = eINSTANCE.getModel_Routes();

		/**
		 * The meta object literal for the '<em><b>Ambits</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODEL__AMBITS = eINSTANCE.getModel_Ambits();

		/**
		 * The meta object literal for the '{@link safecap.model.impl.LineImpl
		 * <em>Line</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.model.impl.LineImpl
		 * @see safecap.model.impl.ModelPackageImpl#getLine()
		 * @generated
		 */
		EClass LINE = eINSTANCE.getLine();

		/**
		 * The meta object literal for the '<em><b>Routes</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LINE__ROUTES = eINSTANCE.getLine_Routes();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LINE__COLOR = eINSTANCE.getLine_Color();

		/**
		 * The meta object literal for the '<em><b>Orientation</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LINE__ORIENTATION = eINSTANCE.getLine_Orientation();

		/**
		 * The meta object literal for the '<em><b>Trafficmix</b></em>' attribute list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LINE__TRAFFICMIX = eINSTANCE.getLine_Trafficmix();

		/**
		 * The meta object literal for the '{@link safecap.model.impl.RouteImpl
		 * <em>Route</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.model.impl.RouteImpl
		 * @see safecap.model.impl.ModelPackageImpl#getRoute()
		 * @generated
		 */
		EClass ROUTE = eINSTANCE.getRoute();

		/**
		 * The meta object literal for the '<em><b>Ambits</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROUTE__AMBITS = eINSTANCE.getRoute_Ambits();

		/**
		 * The meta object literal for the '<em><b>Segments</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROUTE__SEGMENTS = eINSTANCE.getRoute_Segments();

		/**
		 * The meta object literal for the '<em><b>Overlap</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROUTE__OVERLAP = eINSTANCE.getRoute_Overlap();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROUTE__RULE = eINSTANCE.getRoute_Rule();

		/**
		 * The meta object literal for the '<em><b>Aspects</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ROUTE__ASPECTS = eINSTANCE.getRoute_Aspects();

		/**
		 * The meta object literal for the '<em><b>Orientation</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ROUTE__ORIENTATION = eINSTANCE.getRoute_Orientation();

		/**
		 * The meta object literal for the '<em><b>Logic</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROUTE__LOGIC = eINSTANCE.getRoute_Logic();

		/**
		 * The meta object literal for the '<em><b>Defaultlogic</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROUTE__DEFAULTLOGIC = eINSTANCE.getRoute_Defaultlogic();

		/**
		 * The meta object literal for the '{@link safecap.model.impl.AmbitImpl
		 * <em>Ambit</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.model.impl.AmbitImpl
		 * @see safecap.model.impl.ModelPackageImpl#getAmbit()
		 * @generated
		 */
		EClass AMBIT = eINSTANCE.getAmbit();

		/**
		 * The meta object literal for the '<em><b>Segments</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference AMBIT__SEGMENTS = eINSTANCE.getAmbit_Segments();

		/**
		 * The meta object literal for the '{@link safecap.model.impl.SectionImpl
		 * <em>Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.model.impl.SectionImpl
		 * @see safecap.model.impl.ModelPackageImpl#getSection()
		 * @generated
		 */
		EClass SECTION = eINSTANCE.getSection();

		/**
		 * The meta object literal for the '{@link safecap.model.impl.JunctionImpl
		 * <em>Junction</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.model.impl.JunctionImpl
		 * @see safecap.model.impl.ModelPackageImpl#getJunction()
		 * @generated
		 */
		EClass JUNCTION = eINSTANCE.getJunction();

		/**
		 * The meta object literal for the '<em><b>Points</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference JUNCTION__POINTS = eINSTANCE.getJunction_Points();

		/**
		 * The meta object literal for the '{@link safecap.model.impl.PointImpl
		 * <em>Point</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.model.impl.PointImpl
		 * @see safecap.model.impl.ModelPackageImpl#getPoint()
		 * @generated
		 */
		EClass POINT = eINSTANCE.getPoint();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference POINT__NODE = eINSTANCE.getPoint_Node();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference POINT__RULE = eINSTANCE.getPoint_Rule();

		/**
		 * The meta object literal for the '{@link safecap.model.impl.RuleImpl
		 * <em>Rule</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.model.impl.RuleImpl
		 * @see safecap.model.impl.ModelPackageImpl#getRule()
		 * @generated
		 */
		EClass RULE = eINSTANCE.getRule();

		/**
		 * The meta object literal for the '<em><b>Clear</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RULE__CLEAR = eINSTANCE.getRule_Clear();

		/**
		 * The meta object literal for the '<em><b>Occupied</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RULE__OCCUPIED = eINSTANCE.getRule_Occupied();

		/**
		 * The meta object literal for the '<em><b>Normal</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RULE__NORMAL = eINSTANCE.getRule_Normal();

		/**
		 * The meta object literal for the '<em><b>Reverse</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RULE__REVERSE = eINSTANCE.getRule_Reverse();

		/**
		 * The meta object literal for the '<em><b>Route State</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RULE__ROUTE_STATE = eINSTANCE.getRule_RouteState();

		/**
		 * The meta object literal for the
		 * '{@link safecap.model.impl.TimedOccupationRuleImpl <em>Timed Occupation
		 * Rule</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.model.impl.TimedOccupationRuleImpl
		 * @see safecap.model.impl.ModelPackageImpl#getTimedOccupationRule()
		 * @generated
		 */
		EClass TIMED_OCCUPATION_RULE = eINSTANCE.getTimedOccupationRule();

		/**
		 * The meta object literal for the '<em><b>Ambit</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TIMED_OCCUPATION_RULE__AMBIT = eINSTANCE.getTimedOccupationRule_Ambit();

		/**
		 * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TIMED_OCCUPATION_RULE__TIME = eINSTANCE.getTimedOccupationRule_Time();

		/**
		 * The meta object literal for the '{@link safecap.model.impl.RouteStateRuleImpl
		 * <em>Route State Rule</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see safecap.model.impl.RouteStateRuleImpl
		 * @see safecap.model.impl.ModelPackageImpl#getRouteStateRule()
		 * @generated
		 */
		EClass ROUTE_STATE_RULE = eINSTANCE.getRouteStateRule();

		/**
		 * The meta object literal for the '<em><b>Route</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROUTE_STATE_RULE__ROUTE = eINSTANCE.getRouteStateRule_Route();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ROUTE_STATE_RULE__STATE = eINSTANCE.getRouteStateRule_State();

		/**
		 * The meta object literal for the '{@link safecap.model.impl.ControlLogicImpl
		 * <em>Control Logic</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.model.impl.ControlLogicImpl
		 * @see safecap.model.impl.ModelPackageImpl#getControlLogic()
		 * @generated
		 */
		EClass CONTROL_LOGIC = eINSTANCE.getControlLogic();

		/**
		 * The meta object literal for the '<em><b>Line</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTROL_LOGIC__LINE = eINSTANCE.getControlLogic_Line();

		/**
		 * The meta object literal for the '<em><b>Aspects</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONTROL_LOGIC__ASPECTS = eINSTANCE.getControlLogic_Aspects();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTROL_LOGIC__RULE = eINSTANCE.getControlLogic_Rule();

		/**
		 * The meta object literal for the '<em><b>Normal</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTROL_LOGIC__NORMAL = eINSTANCE.getControlLogic_Normal();

		/**
		 * The meta object literal for the '<em><b>Reverse</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTROL_LOGIC__REVERSE = eINSTANCE.getControlLogic_Reverse();

	}

} // ModelPackage
