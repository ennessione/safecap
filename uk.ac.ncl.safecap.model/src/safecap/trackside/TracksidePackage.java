/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside;

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
 * @see safecap.trackside.TracksideFactory
 * @model kind="package"
 * @generated
 */
public interface TracksidePackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "trackside";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "uk.ac.ncl.safecap.emf.trackside";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "uk.ac.ncl.safecap.emf.trackside";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	TracksidePackage eINSTANCE = safecap.trackside.impl.TracksidePackageImpl.init();

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.TracksideImpl
	 * <em>Trackside</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.impl.TracksideImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getTrackside()
	 * @generated
	 */
	int TRACKSIDE = 0;

	/**
	 * The feature id for the '<em><b>Equipment</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACKSIDE__EQUIPMENT = 0;

	/**
	 * The feature id for the '<em><b>Wires</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACKSIDE__WIRES = 1;

	/**
	 * The feature id for the '<em><b>Nodewires</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACKSIDE__NODEWIRES = 2;

	/**
	 * The feature id for the '<em><b>Subwires</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACKSIDE__SUBWIRES = 3;

	/**
	 * The feature id for the '<em><b>Subequipment</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACKSIDE__SUBEQUIPMENT = 4;

	/**
	 * The number of structural features of the '<em>Trackside</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACKSIDE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.EquipmentImpl
	 * <em>Equipment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.impl.EquipmentImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getEquipment()
	 * @generated
	 */
	int EQUIPMENT = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUIPMENT__LABEL = SafecapPackage.LABELED__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUIPMENT__VISIBLE = SafecapPackage.LABELED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUIPMENT__STYLE = SafecapPackage.LABELED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUIPMENT__INTERVALS = SafecapPackage.LABELED_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUIPMENT__MARKERS = SafecapPackage.LABELED_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUIPMENT__ORIGIN = SafecapPackage.LABELED_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUIPMENT__AUTHOR = SafecapPackage.LABELED_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUIPMENT__VERSION = SafecapPackage.LABELED_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUIPMENT__DATE = SafecapPackage.LABELED_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUIPMENT__RUNTIME_ATTRIBUTES = SafecapPackage.LABELED_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUIPMENT__ATTRIBUTES = SafecapPackage.LABELED_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Equipment</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUIPMENT_FEATURE_COUNT = SafecapPackage.LABELED_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.SignalImpl
	 * <em>Signal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.impl.SignalImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getSignal()
	 * @generated
	 */
	int SIGNAL = 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__LABEL = EQUIPMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__VISIBLE = EQUIPMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__STYLE = EQUIPMENT__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__INTERVALS = EQUIPMENT__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__MARKERS = EQUIPMENT__MARKERS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__ORIGIN = EQUIPMENT__ORIGIN;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__AUTHOR = EQUIPMENT__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__VERSION = EQUIPMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__DATE = EQUIPMENT__DATE;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__RUNTIME_ATTRIBUTES = EQUIPMENT__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__ATTRIBUTES = EQUIPMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__TYPE = EQUIPMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Signal</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_FEATURE_COUNT = EQUIPMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.SpeedLimitImpl
	 * <em>Speed Limit</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.impl.SpeedLimitImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getSpeedLimit()
	 * @generated
	 */
	int SPEED_LIMIT = 3;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SPEED_LIMIT__LABEL = EQUIPMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SPEED_LIMIT__VISIBLE = EQUIPMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SPEED_LIMIT__STYLE = EQUIPMENT__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SPEED_LIMIT__INTERVALS = EQUIPMENT__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SPEED_LIMIT__MARKERS = EQUIPMENT__MARKERS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SPEED_LIMIT__ORIGIN = EQUIPMENT__ORIGIN;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SPEED_LIMIT__AUTHOR = EQUIPMENT__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SPEED_LIMIT__VERSION = EQUIPMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SPEED_LIMIT__DATE = EQUIPMENT__DATE;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SPEED_LIMIT__RUNTIME_ATTRIBUTES = EQUIPMENT__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SPEED_LIMIT__ATTRIBUTES = EQUIPMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Line</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SPEED_LIMIT__LINE = EQUIPMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Limit</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SPEED_LIMIT__LIMIT = EQUIPMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Speed Limit</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SPEED_LIMIT_FEATURE_COUNT = EQUIPMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.LeftSignalImpl
	 * <em>Left Signal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.impl.LeftSignalImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getLeftSignal()
	 * @generated
	 */
	int LEFT_SIGNAL = 4;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SIGNAL__LABEL = SIGNAL__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SIGNAL__VISIBLE = SIGNAL__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SIGNAL__STYLE = SIGNAL__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SIGNAL__INTERVALS = SIGNAL__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SIGNAL__MARKERS = SIGNAL__MARKERS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SIGNAL__ORIGIN = SIGNAL__ORIGIN;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SIGNAL__AUTHOR = SIGNAL__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SIGNAL__VERSION = SIGNAL__VERSION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SIGNAL__DATE = SIGNAL__DATE;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SIGNAL__RUNTIME_ATTRIBUTES = SIGNAL__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SIGNAL__ATTRIBUTES = SIGNAL__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SIGNAL__TYPE = SIGNAL__TYPE;

	/**
	 * The number of structural features of the '<em>Left Signal</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SIGNAL_FEATURE_COUNT = SIGNAL_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.RightSignalImpl
	 * <em>Right Signal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.impl.RightSignalImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getRightSignal()
	 * @generated
	 */
	int RIGHT_SIGNAL = 5;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SIGNAL__LABEL = SIGNAL__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SIGNAL__VISIBLE = SIGNAL__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SIGNAL__STYLE = SIGNAL__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SIGNAL__INTERVALS = SIGNAL__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SIGNAL__MARKERS = SIGNAL__MARKERS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SIGNAL__ORIGIN = SIGNAL__ORIGIN;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SIGNAL__AUTHOR = SIGNAL__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SIGNAL__VERSION = SIGNAL__VERSION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SIGNAL__DATE = SIGNAL__DATE;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SIGNAL__RUNTIME_ATTRIBUTES = SIGNAL__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SIGNAL__ATTRIBUTES = SIGNAL__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SIGNAL__TYPE = SIGNAL__TYPE;

	/**
	 * The number of structural features of the '<em>Right Signal</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SIGNAL_FEATURE_COUNT = SIGNAL_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.LeftSpeedLimitImpl
	 * <em>Left Speed Limit</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.trackside.impl.LeftSpeedLimitImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getLeftSpeedLimit()
	 * @generated
	 */
	int LEFT_SPEED_LIMIT = 6;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SPEED_LIMIT__LABEL = SPEED_LIMIT__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SPEED_LIMIT__VISIBLE = SPEED_LIMIT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SPEED_LIMIT__STYLE = SPEED_LIMIT__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SPEED_LIMIT__INTERVALS = SPEED_LIMIT__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SPEED_LIMIT__MARKERS = SPEED_LIMIT__MARKERS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SPEED_LIMIT__ORIGIN = SPEED_LIMIT__ORIGIN;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SPEED_LIMIT__AUTHOR = SPEED_LIMIT__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SPEED_LIMIT__VERSION = SPEED_LIMIT__VERSION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SPEED_LIMIT__DATE = SPEED_LIMIT__DATE;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SPEED_LIMIT__RUNTIME_ATTRIBUTES = SPEED_LIMIT__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SPEED_LIMIT__ATTRIBUTES = SPEED_LIMIT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Line</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SPEED_LIMIT__LINE = SPEED_LIMIT__LINE;

	/**
	 * The feature id for the '<em><b>Limit</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SPEED_LIMIT__LIMIT = SPEED_LIMIT__LIMIT;

	/**
	 * The number of structural features of the '<em>Left Speed Limit</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SPEED_LIMIT_FEATURE_COUNT = SPEED_LIMIT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.RightSpeedLimitImpl
	 * <em>Right Speed Limit</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.trackside.impl.RightSpeedLimitImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getRightSpeedLimit()
	 * @generated
	 */
	int RIGHT_SPEED_LIMIT = 7;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SPEED_LIMIT__LABEL = SPEED_LIMIT__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SPEED_LIMIT__VISIBLE = SPEED_LIMIT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SPEED_LIMIT__STYLE = SPEED_LIMIT__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SPEED_LIMIT__INTERVALS = SPEED_LIMIT__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SPEED_LIMIT__MARKERS = SPEED_LIMIT__MARKERS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SPEED_LIMIT__ORIGIN = SPEED_LIMIT__ORIGIN;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SPEED_LIMIT__AUTHOR = SPEED_LIMIT__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SPEED_LIMIT__VERSION = SPEED_LIMIT__VERSION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SPEED_LIMIT__DATE = SPEED_LIMIT__DATE;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SPEED_LIMIT__RUNTIME_ATTRIBUTES = SPEED_LIMIT__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SPEED_LIMIT__ATTRIBUTES = SPEED_LIMIT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Line</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SPEED_LIMIT__LINE = SPEED_LIMIT__LINE;

	/**
	 * The feature id for the '<em><b>Limit</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SPEED_LIMIT__LIMIT = SPEED_LIMIT__LIMIT;

	/**
	 * The number of structural features of the '<em>Right Speed Limit</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SPEED_LIMIT_FEATURE_COUNT = SPEED_LIMIT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.WireImpl
	 * <em>Wire</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.impl.WireImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getWire()
	 * @generated
	 */
	int WIRE = 8;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WIRE__RUNTIME_ATTRIBUTES = SafecapPackage.EXTENSIBLE__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WIRE__ATTRIBUTES = SafecapPackage.EXTENSIBLE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WIRE__VISIBLE = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WIRE__STYLE = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WIRE__INTERVALS = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WIRE__MARKERS = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WIRE__FROM = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WIRE__TO = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WIRE__OFFSET = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Wire</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WIRE_FEATURE_COUNT = SafecapPackage.EXTENSIBLE_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.RedLeftSignalImpl
	 * <em>Red Left Signal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.trackside.impl.RedLeftSignalImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getRedLeftSignal()
	 * @generated
	 */
	int RED_LEFT_SIGNAL = 9;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_LEFT_SIGNAL__LABEL = SIGNAL__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_LEFT_SIGNAL__VISIBLE = SIGNAL__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_LEFT_SIGNAL__STYLE = SIGNAL__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_LEFT_SIGNAL__INTERVALS = SIGNAL__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_LEFT_SIGNAL__MARKERS = SIGNAL__MARKERS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_LEFT_SIGNAL__ORIGIN = SIGNAL__ORIGIN;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_LEFT_SIGNAL__AUTHOR = SIGNAL__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_LEFT_SIGNAL__VERSION = SIGNAL__VERSION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_LEFT_SIGNAL__DATE = SIGNAL__DATE;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_LEFT_SIGNAL__RUNTIME_ATTRIBUTES = SIGNAL__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_LEFT_SIGNAL__ATTRIBUTES = SIGNAL__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_LEFT_SIGNAL__TYPE = SIGNAL__TYPE;

	/**
	 * The number of structural features of the '<em>Red Left Signal</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_LEFT_SIGNAL_FEATURE_COUNT = SIGNAL_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.RedRightSignalImpl
	 * <em>Red Right Signal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.trackside.impl.RedRightSignalImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getRedRightSignal()
	 * @generated
	 */
	int RED_RIGHT_SIGNAL = 10;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_RIGHT_SIGNAL__LABEL = SIGNAL__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_RIGHT_SIGNAL__VISIBLE = SIGNAL__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_RIGHT_SIGNAL__STYLE = SIGNAL__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_RIGHT_SIGNAL__INTERVALS = SIGNAL__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_RIGHT_SIGNAL__MARKERS = SIGNAL__MARKERS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_RIGHT_SIGNAL__ORIGIN = SIGNAL__ORIGIN;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_RIGHT_SIGNAL__AUTHOR = SIGNAL__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_RIGHT_SIGNAL__VERSION = SIGNAL__VERSION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_RIGHT_SIGNAL__DATE = SIGNAL__DATE;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_RIGHT_SIGNAL__RUNTIME_ATTRIBUTES = SIGNAL__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_RIGHT_SIGNAL__ATTRIBUTES = SIGNAL__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_RIGHT_SIGNAL__TYPE = SIGNAL__TYPE;

	/**
	 * The number of structural features of the '<em>Red Right Signal</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RED_RIGHT_SIGNAL_FEATURE_COUNT = SIGNAL_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link safecap.trackside.LeftSide <em>Left
	 * Side</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.LeftSide
	 * @see safecap.trackside.impl.TracksidePackageImpl#getLeftSide()
	 * @generated
	 */
	int LEFT_SIDE = 11;

	/**
	 * The number of structural features of the '<em>Left Side</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_SIDE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link safecap.trackside.RightSide <em>Right
	 * Side</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.RightSide
	 * @see safecap.trackside.impl.TracksidePackageImpl#getRightSide()
	 * @generated
	 */
	int RIGHT_SIDE = 12;

	/**
	 * The number of structural features of the '<em>Right Side</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_SIDE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.StopAndGoImpl
	 * <em>Stop And Go</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.impl.StopAndGoImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getStopAndGo()
	 * @generated
	 */
	int STOP_AND_GO = 13;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO__LABEL = EQUIPMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO__VISIBLE = EQUIPMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO__STYLE = EQUIPMENT__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO__INTERVALS = EQUIPMENT__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO__MARKERS = EQUIPMENT__MARKERS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO__ORIGIN = EQUIPMENT__ORIGIN;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO__AUTHOR = EQUIPMENT__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO__VERSION = EQUIPMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO__DATE = EQUIPMENT__DATE;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO__RUNTIME_ATTRIBUTES = EQUIPMENT__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO__ATTRIBUTES = EQUIPMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Delay</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO__DELAY = EQUIPMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Line</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO__LINE = EQUIPMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Release Route</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO__RELEASE_ROUTE = EQUIPMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Stop And Go</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STOP_AND_GO_FEATURE_COUNT = EQUIPMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.LeftStopAndGoImpl
	 * <em>Left Stop And Go</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.trackside.impl.LeftStopAndGoImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getLeftStopAndGo()
	 * @generated
	 */
	int LEFT_STOP_AND_GO = 14;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO__LABEL = STOP_AND_GO__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO__VISIBLE = STOP_AND_GO__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO__STYLE = STOP_AND_GO__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO__INTERVALS = STOP_AND_GO__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO__MARKERS = STOP_AND_GO__MARKERS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO__ORIGIN = STOP_AND_GO__ORIGIN;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO__AUTHOR = STOP_AND_GO__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO__VERSION = STOP_AND_GO__VERSION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO__DATE = STOP_AND_GO__DATE;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO__RUNTIME_ATTRIBUTES = STOP_AND_GO__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO__ATTRIBUTES = STOP_AND_GO__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Delay</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO__DELAY = STOP_AND_GO__DELAY;

	/**
	 * The feature id for the '<em><b>Line</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO__LINE = STOP_AND_GO__LINE;

	/**
	 * The feature id for the '<em><b>Release Route</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO__RELEASE_ROUTE = STOP_AND_GO__RELEASE_ROUTE;

	/**
	 * The number of structural features of the '<em>Left Stop And Go</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEFT_STOP_AND_GO_FEATURE_COUNT = STOP_AND_GO_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.RightStopAndGoImpl
	 * <em>Right Stop And Go</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.trackside.impl.RightStopAndGoImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getRightStopAndGo()
	 * @generated
	 */
	int RIGHT_STOP_AND_GO = 15;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO__LABEL = STOP_AND_GO__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO__VISIBLE = STOP_AND_GO__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO__STYLE = STOP_AND_GO__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO__INTERVALS = STOP_AND_GO__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO__MARKERS = STOP_AND_GO__MARKERS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO__ORIGIN = STOP_AND_GO__ORIGIN;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO__AUTHOR = STOP_AND_GO__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO__VERSION = STOP_AND_GO__VERSION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO__DATE = STOP_AND_GO__DATE;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO__RUNTIME_ATTRIBUTES = STOP_AND_GO__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO__ATTRIBUTES = STOP_AND_GO__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Delay</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO__DELAY = STOP_AND_GO__DELAY;

	/**
	 * The feature id for the '<em><b>Line</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO__LINE = STOP_AND_GO__LINE;

	/**
	 * The feature id for the '<em><b>Release Route</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO__RELEASE_ROUTE = STOP_AND_GO__RELEASE_ROUTE;

	/**
	 * The number of structural features of the '<em>Right Stop And Go</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RIGHT_STOP_AND_GO_FEATURE_COUNT = STOP_AND_GO_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.SubEquipmentImpl
	 * <em>Sub Equipment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.impl.SubEquipmentImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getSubEquipment()
	 * @generated
	 */
	int SUB_EQUIPMENT = 16;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_EQUIPMENT__LABEL = EQUIPMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_EQUIPMENT__VISIBLE = EQUIPMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_EQUIPMENT__STYLE = EQUIPMENT__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_EQUIPMENT__INTERVALS = EQUIPMENT__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_EQUIPMENT__MARKERS = EQUIPMENT__MARKERS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_EQUIPMENT__ORIGIN = EQUIPMENT__ORIGIN;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_EQUIPMENT__AUTHOR = EQUIPMENT__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_EQUIPMENT__VERSION = EQUIPMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_EQUIPMENT__DATE = EQUIPMENT__DATE;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_EQUIPMENT__RUNTIME_ATTRIBUTES = EQUIPMENT__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_EQUIPMENT__ATTRIBUTES = EQUIPMENT__ATTRIBUTES;

	/**
	 * The number of structural features of the '<em>Sub Equipment</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_EQUIPMENT_FEATURE_COUNT = EQUIPMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.SubWireImpl <em>Sub
	 * Wire</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.impl.SubWireImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getSubWire()
	 * @generated
	 */
	int SUB_WIRE = 17;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_WIRE__FROM = 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_WIRE__TO = 1;

	/**
	 * The number of structural features of the '<em>Sub Wire</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_WIRE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link safecap.trackside.impl.NodeWireImpl
	 * <em>Node Wire</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.impl.NodeWireImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getNodeWire()
	 * @generated
	 */
	int NODE_WIRE = 18;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE_WIRE__FROM = 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE_WIRE__TO = 1;

	/**
	 * The number of structural features of the '<em>Node Wire</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE_WIRE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the
	 * '{@link safecap.trackside.impl.GenericLocatedEquipmentImpl <em>Generic
	 * Located Equipment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.impl.GenericLocatedEquipmentImpl
	 * @see safecap.trackside.impl.TracksidePackageImpl#getGenericLocatedEquipment()
	 * @generated
	 */
	int GENERIC_LOCATED_EQUIPMENT = 19;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__LABEL = EQUIPMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__VISIBLE = EQUIPMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__STYLE = EQUIPMENT__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__INTERVALS = EQUIPMENT__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__MARKERS = EQUIPMENT__MARKERS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__ORIGIN = EQUIPMENT__ORIGIN;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__AUTHOR = EQUIPMENT__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__VERSION = EQUIPMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__DATE = EQUIPMENT__DATE;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__RUNTIME_ATTRIBUTES = EQUIPMENT__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__ATTRIBUTES = EQUIPMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__OFFSET = EQUIPMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__TYPE = EQUIPMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Segment</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__SEGMENT = EQUIPMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Orientation</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT__ORIENTATION = EQUIPMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Generic Located Equipment</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERIC_LOCATED_EQUIPMENT_FEATURE_COUNT = EQUIPMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link safecap.trackside.SignalType <em>Signal
	 * Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.trackside.SignalType
	 * @see safecap.trackside.impl.TracksidePackageImpl#getSignalType()
	 * @generated
	 */
	int SIGNAL_TYPE = 20;

	/**
	 * Returns the meta object for class '{@link safecap.trackside.Trackside
	 * <em>Trackside</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Trackside</em>'.
	 * @see safecap.trackside.Trackside
	 * @generated
	 */
	EClass getTrackside();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.trackside.Trackside#getEquipment <em>Equipment</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Equipment</em>'.
	 * @see safecap.trackside.Trackside#getEquipment()
	 * @see #getTrackside()
	 * @generated
	 */
	EReference getTrackside_Equipment();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.trackside.Trackside#getWires <em>Wires</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Wires</em>'.
	 * @see safecap.trackside.Trackside#getWires()
	 * @see #getTrackside()
	 * @generated
	 */
	EReference getTrackside_Wires();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.trackside.Trackside#getNodewires <em>Nodewires</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Nodewires</em>'.
	 * @see safecap.trackside.Trackside#getNodewires()
	 * @see #getTrackside()
	 * @generated
	 */
	EReference getTrackside_Nodewires();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.trackside.Trackside#getSubwires <em>Subwires</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Subwires</em>'.
	 * @see safecap.trackside.Trackside#getSubwires()
	 * @see #getTrackside()
	 * @generated
	 */
	EReference getTrackside_Subwires();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.trackside.Trackside#getSubequipment <em>Subequipment</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Subequipment</em>'.
	 * @see safecap.trackside.Trackside#getSubequipment()
	 * @see #getTrackside()
	 * @generated
	 */
	EReference getTrackside_Subequipment();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.Equipment
	 * <em>Equipment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Equipment</em>'.
	 * @see safecap.trackside.Equipment
	 * @generated
	 */
	EClass getEquipment();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.Signal
	 * <em>Signal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Signal</em>'.
	 * @see safecap.trackside.Signal
	 * @generated
	 */
	EClass getSignal();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.trackside.Signal#getType <em>Type</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see safecap.trackside.Signal#getType()
	 * @see #getSignal()
	 * @generated
	 */
	EAttribute getSignal_Type();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.SpeedLimit
	 * <em>Speed Limit</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Speed Limit</em>'.
	 * @see safecap.trackside.SpeedLimit
	 * @generated
	 */
	EClass getSpeedLimit();

	/**
	 * Returns the meta object for the reference list
	 * '{@link safecap.trackside.SpeedLimit#getLine <em>Line</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Line</em>'.
	 * @see safecap.trackside.SpeedLimit#getLine()
	 * @see #getSpeedLimit()
	 * @generated
	 */
	EReference getSpeedLimit_Line();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.trackside.SpeedLimit#getLimit <em>Limit</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Limit</em>'.
	 * @see safecap.trackside.SpeedLimit#getLimit()
	 * @see #getSpeedLimit()
	 * @generated
	 */
	EAttribute getSpeedLimit_Limit();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.LeftSignal
	 * <em>Left Signal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Left Signal</em>'.
	 * @see safecap.trackside.LeftSignal
	 * @generated
	 */
	EClass getLeftSignal();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.RightSignal
	 * <em>Right Signal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Right Signal</em>'.
	 * @see safecap.trackside.RightSignal
	 * @generated
	 */
	EClass getRightSignal();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.LeftSpeedLimit
	 * <em>Left Speed Limit</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Left Speed Limit</em>'.
	 * @see safecap.trackside.LeftSpeedLimit
	 * @generated
	 */
	EClass getLeftSpeedLimit();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.RightSpeedLimit
	 * <em>Right Speed Limit</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Right Speed Limit</em>'.
	 * @see safecap.trackside.RightSpeedLimit
	 * @generated
	 */
	EClass getRightSpeedLimit();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.Wire
	 * <em>Wire</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Wire</em>'.
	 * @see safecap.trackside.Wire
	 * @generated
	 */
	EClass getWire();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.trackside.Wire#getFrom <em>From</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see safecap.trackside.Wire#getFrom()
	 * @see #getWire()
	 * @generated
	 */
	EReference getWire_From();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.trackside.Wire#getTo <em>To</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see safecap.trackside.Wire#getTo()
	 * @see #getWire()
	 * @generated
	 */
	EReference getWire_To();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.trackside.Wire#getOffset <em>Offset</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Offset</em>'.
	 * @see safecap.trackside.Wire#getOffset()
	 * @see #getWire()
	 * @generated
	 */
	EAttribute getWire_Offset();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.RedLeftSignal
	 * <em>Red Left Signal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Red Left Signal</em>'.
	 * @see safecap.trackside.RedLeftSignal
	 * @generated
	 */
	EClass getRedLeftSignal();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.RedRightSignal
	 * <em>Red Right Signal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Red Right Signal</em>'.
	 * @see safecap.trackside.RedRightSignal
	 * @generated
	 */
	EClass getRedRightSignal();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.LeftSide <em>Left
	 * Side</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Left Side</em>'.
	 * @see safecap.trackside.LeftSide
	 * @generated
	 */
	EClass getLeftSide();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.RightSide
	 * <em>Right Side</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Right Side</em>'.
	 * @see safecap.trackside.RightSide
	 * @generated
	 */
	EClass getRightSide();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.StopAndGo
	 * <em>Stop And Go</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Stop And Go</em>'.
	 * @see safecap.trackside.StopAndGo
	 * @generated
	 */
	EClass getStopAndGo();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.trackside.StopAndGo#getDelay <em>Delay</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Delay</em>'.
	 * @see safecap.trackside.StopAndGo#getDelay()
	 * @see #getStopAndGo()
	 * @generated
	 */
	EAttribute getStopAndGo_Delay();

	/**
	 * Returns the meta object for the reference list
	 * '{@link safecap.trackside.StopAndGo#getLine <em>Line</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Line</em>'.
	 * @see safecap.trackside.StopAndGo#getLine()
	 * @see #getStopAndGo()
	 * @generated
	 */
	EReference getStopAndGo_Line();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.trackside.StopAndGo#getReleaseRoute <em>Release Route</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Release Route</em>'.
	 * @see safecap.trackside.StopAndGo#getReleaseRoute()
	 * @see #getStopAndGo()
	 * @generated
	 */
	EReference getStopAndGo_ReleaseRoute();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.LeftStopAndGo
	 * <em>Left Stop And Go</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Left Stop And Go</em>'.
	 * @see safecap.trackside.LeftStopAndGo
	 * @generated
	 */
	EClass getLeftStopAndGo();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.RightStopAndGo
	 * <em>Right Stop And Go</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Right Stop And Go</em>'.
	 * @see safecap.trackside.RightStopAndGo
	 * @generated
	 */
	EClass getRightStopAndGo();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.SubEquipment
	 * <em>Sub Equipment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Sub Equipment</em>'.
	 * @see safecap.trackside.SubEquipment
	 * @generated
	 */
	EClass getSubEquipment();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.SubWire <em>Sub
	 * Wire</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Sub Wire</em>'.
	 * @see safecap.trackside.SubWire
	 * @generated
	 */
	EClass getSubWire();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.trackside.SubWire#getFrom <em>From</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see safecap.trackside.SubWire#getFrom()
	 * @see #getSubWire()
	 * @generated
	 */
	EReference getSubWire_From();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.trackside.SubWire#getTo <em>To</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see safecap.trackside.SubWire#getTo()
	 * @see #getSubWire()
	 * @generated
	 */
	EReference getSubWire_To();

	/**
	 * Returns the meta object for class '{@link safecap.trackside.NodeWire <em>Node
	 * Wire</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Node Wire</em>'.
	 * @see safecap.trackside.NodeWire
	 * @generated
	 */
	EClass getNodeWire();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.trackside.NodeWire#getFrom <em>From</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see safecap.trackside.NodeWire#getFrom()
	 * @see #getNodeWire()
	 * @generated
	 */
	EReference getNodeWire_From();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.trackside.NodeWire#getTo <em>To</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see safecap.trackside.NodeWire#getTo()
	 * @see #getNodeWire()
	 * @generated
	 */
	EReference getNodeWire_To();

	/**
	 * Returns the meta object for class
	 * '{@link safecap.trackside.GenericLocatedEquipment <em>Generic Located
	 * Equipment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Generic Located Equipment</em>'.
	 * @see safecap.trackside.GenericLocatedEquipment
	 * @generated
	 */
	EClass getGenericLocatedEquipment();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.trackside.GenericLocatedEquipment#getOffset
	 * <em>Offset</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Offset</em>'.
	 * @see safecap.trackside.GenericLocatedEquipment#getOffset()
	 * @see #getGenericLocatedEquipment()
	 * @generated
	 */
	EAttribute getGenericLocatedEquipment_Offset();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.trackside.GenericLocatedEquipment#getType <em>Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see safecap.trackside.GenericLocatedEquipment#getType()
	 * @see #getGenericLocatedEquipment()
	 * @generated
	 */
	EAttribute getGenericLocatedEquipment_Type();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.trackside.GenericLocatedEquipment#getSegment
	 * <em>Segment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Segment</em>'.
	 * @see safecap.trackside.GenericLocatedEquipment#getSegment()
	 * @see #getGenericLocatedEquipment()
	 * @generated
	 */
	EReference getGenericLocatedEquipment_Segment();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.trackside.GenericLocatedEquipment#getOrientation
	 * <em>Orientation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Orientation</em>'.
	 * @see safecap.trackside.GenericLocatedEquipment#getOrientation()
	 * @see #getGenericLocatedEquipment()
	 * @generated
	 */
	EAttribute getGenericLocatedEquipment_Orientation();

	/**
	 * Returns the meta object for enum '{@link safecap.trackside.SignalType
	 * <em>Signal Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Signal Type</em>'.
	 * @see safecap.trackside.SignalType
	 * @generated
	 */
	EEnum getSignalType();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TracksideFactory getTracksideFactory();

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
		 * The meta object literal for the '{@link safecap.trackside.impl.TracksideImpl
		 * <em>Trackside</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.TracksideImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getTrackside()
		 * @generated
		 */
		EClass TRACKSIDE = eINSTANCE.getTrackside();

		/**
		 * The meta object literal for the '<em><b>Equipment</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACKSIDE__EQUIPMENT = eINSTANCE.getTrackside_Equipment();

		/**
		 * The meta object literal for the '<em><b>Wires</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACKSIDE__WIRES = eINSTANCE.getTrackside_Wires();

		/**
		 * The meta object literal for the '<em><b>Nodewires</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACKSIDE__NODEWIRES = eINSTANCE.getTrackside_Nodewires();

		/**
		 * The meta object literal for the '<em><b>Subwires</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACKSIDE__SUBWIRES = eINSTANCE.getTrackside_Subwires();

		/**
		 * The meta object literal for the '<em><b>Subequipment</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACKSIDE__SUBEQUIPMENT = eINSTANCE.getTrackside_Subequipment();

		/**
		 * The meta object literal for the '{@link safecap.trackside.impl.EquipmentImpl
		 * <em>Equipment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.EquipmentImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getEquipment()
		 * @generated
		 */
		EClass EQUIPMENT = eINSTANCE.getEquipment();

		/**
		 * The meta object literal for the '{@link safecap.trackside.impl.SignalImpl
		 * <em>Signal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.SignalImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getSignal()
		 * @generated
		 */
		EClass SIGNAL = eINSTANCE.getSignal();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SIGNAL__TYPE = eINSTANCE.getSignal_Type();

		/**
		 * The meta object literal for the '{@link safecap.trackside.impl.SpeedLimitImpl
		 * <em>Speed Limit</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.SpeedLimitImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getSpeedLimit()
		 * @generated
		 */
		EClass SPEED_LIMIT = eINSTANCE.getSpeedLimit();

		/**
		 * The meta object literal for the '<em><b>Line</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SPEED_LIMIT__LINE = eINSTANCE.getSpeedLimit_Line();

		/**
		 * The meta object literal for the '<em><b>Limit</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SPEED_LIMIT__LIMIT = eINSTANCE.getSpeedLimit_Limit();

		/**
		 * The meta object literal for the '{@link safecap.trackside.impl.LeftSignalImpl
		 * <em>Left Signal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.LeftSignalImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getLeftSignal()
		 * @generated
		 */
		EClass LEFT_SIGNAL = eINSTANCE.getLeftSignal();

		/**
		 * The meta object literal for the
		 * '{@link safecap.trackside.impl.RightSignalImpl <em>Right Signal</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.RightSignalImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getRightSignal()
		 * @generated
		 */
		EClass RIGHT_SIGNAL = eINSTANCE.getRightSignal();

		/**
		 * The meta object literal for the
		 * '{@link safecap.trackside.impl.LeftSpeedLimitImpl <em>Left Speed Limit</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.LeftSpeedLimitImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getLeftSpeedLimit()
		 * @generated
		 */
		EClass LEFT_SPEED_LIMIT = eINSTANCE.getLeftSpeedLimit();

		/**
		 * The meta object literal for the
		 * '{@link safecap.trackside.impl.RightSpeedLimitImpl <em>Right Speed
		 * Limit</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.RightSpeedLimitImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getRightSpeedLimit()
		 * @generated
		 */
		EClass RIGHT_SPEED_LIMIT = eINSTANCE.getRightSpeedLimit();

		/**
		 * The meta object literal for the '{@link safecap.trackside.impl.WireImpl
		 * <em>Wire</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.WireImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getWire()
		 * @generated
		 */
		EClass WIRE = eINSTANCE.getWire();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference WIRE__FROM = eINSTANCE.getWire_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference WIRE__TO = eINSTANCE.getWire_To();

		/**
		 * The meta object literal for the '<em><b>Offset</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute WIRE__OFFSET = eINSTANCE.getWire_Offset();

		/**
		 * The meta object literal for the
		 * '{@link safecap.trackside.impl.RedLeftSignalImpl <em>Red Left Signal</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.RedLeftSignalImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getRedLeftSignal()
		 * @generated
		 */
		EClass RED_LEFT_SIGNAL = eINSTANCE.getRedLeftSignal();

		/**
		 * The meta object literal for the
		 * '{@link safecap.trackside.impl.RedRightSignalImpl <em>Red Right Signal</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.RedRightSignalImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getRedRightSignal()
		 * @generated
		 */
		EClass RED_RIGHT_SIGNAL = eINSTANCE.getRedRightSignal();

		/**
		 * The meta object literal for the '{@link safecap.trackside.LeftSide <em>Left
		 * Side</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.LeftSide
		 * @see safecap.trackside.impl.TracksidePackageImpl#getLeftSide()
		 * @generated
		 */
		EClass LEFT_SIDE = eINSTANCE.getLeftSide();

		/**
		 * The meta object literal for the '{@link safecap.trackside.RightSide <em>Right
		 * Side</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.RightSide
		 * @see safecap.trackside.impl.TracksidePackageImpl#getRightSide()
		 * @generated
		 */
		EClass RIGHT_SIDE = eINSTANCE.getRightSide();

		/**
		 * The meta object literal for the '{@link safecap.trackside.impl.StopAndGoImpl
		 * <em>Stop And Go</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.StopAndGoImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getStopAndGo()
		 * @generated
		 */
		EClass STOP_AND_GO = eINSTANCE.getStopAndGo();

		/**
		 * The meta object literal for the '<em><b>Delay</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STOP_AND_GO__DELAY = eINSTANCE.getStopAndGo_Delay();

		/**
		 * The meta object literal for the '<em><b>Line</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STOP_AND_GO__LINE = eINSTANCE.getStopAndGo_Line();

		/**
		 * The meta object literal for the '<em><b>Release Route</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STOP_AND_GO__RELEASE_ROUTE = eINSTANCE.getStopAndGo_ReleaseRoute();

		/**
		 * The meta object literal for the
		 * '{@link safecap.trackside.impl.LeftStopAndGoImpl <em>Left Stop And Go</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.LeftStopAndGoImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getLeftStopAndGo()
		 * @generated
		 */
		EClass LEFT_STOP_AND_GO = eINSTANCE.getLeftStopAndGo();

		/**
		 * The meta object literal for the
		 * '{@link safecap.trackside.impl.RightStopAndGoImpl <em>Right Stop And
		 * Go</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.RightStopAndGoImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getRightStopAndGo()
		 * @generated
		 */
		EClass RIGHT_STOP_AND_GO = eINSTANCE.getRightStopAndGo();

		/**
		 * The meta object literal for the
		 * '{@link safecap.trackside.impl.SubEquipmentImpl <em>Sub Equipment</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.SubEquipmentImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getSubEquipment()
		 * @generated
		 */
		EClass SUB_EQUIPMENT = eINSTANCE.getSubEquipment();

		/**
		 * The meta object literal for the '{@link safecap.trackside.impl.SubWireImpl
		 * <em>Sub Wire</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.SubWireImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getSubWire()
		 * @generated
		 */
		EClass SUB_WIRE = eINSTANCE.getSubWire();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SUB_WIRE__FROM = eINSTANCE.getSubWire_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SUB_WIRE__TO = eINSTANCE.getSubWire_To();

		/**
		 * The meta object literal for the '{@link safecap.trackside.impl.NodeWireImpl
		 * <em>Node Wire</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.NodeWireImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getNodeWire()
		 * @generated
		 */
		EClass NODE_WIRE = eINSTANCE.getNodeWire();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference NODE_WIRE__FROM = eINSTANCE.getNodeWire_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference NODE_WIRE__TO = eINSTANCE.getNodeWire_To();

		/**
		 * The meta object literal for the
		 * '{@link safecap.trackside.impl.GenericLocatedEquipmentImpl <em>Generic
		 * Located Equipment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.impl.GenericLocatedEquipmentImpl
		 * @see safecap.trackside.impl.TracksidePackageImpl#getGenericLocatedEquipment()
		 * @generated
		 */
		EClass GENERIC_LOCATED_EQUIPMENT = eINSTANCE.getGenericLocatedEquipment();

		/**
		 * The meta object literal for the '<em><b>Offset</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute GENERIC_LOCATED_EQUIPMENT__OFFSET = eINSTANCE.getGenericLocatedEquipment_Offset();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute GENERIC_LOCATED_EQUIPMENT__TYPE = eINSTANCE.getGenericLocatedEquipment_Type();

		/**
		 * The meta object literal for the '<em><b>Segment</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GENERIC_LOCATED_EQUIPMENT__SEGMENT = eINSTANCE.getGenericLocatedEquipment_Segment();

		/**
		 * The meta object literal for the '<em><b>Orientation</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute GENERIC_LOCATED_EQUIPMENT__ORIENTATION = eINSTANCE.getGenericLocatedEquipment_Orientation();

		/**
		 * The meta object literal for the '{@link safecap.trackside.SignalType
		 * <em>Signal Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.trackside.SignalType
		 * @see safecap.trackside.impl.TracksidePackageImpl#getSignalType()
		 * @generated
		 */
		EEnum SIGNAL_TYPE = eINSTANCE.getSignalType();

	}

} // TracksidePackage
