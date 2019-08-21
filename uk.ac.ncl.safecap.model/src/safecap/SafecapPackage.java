/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see safecap.SafecapFactory
 * @model kind="package"
 * @generated
 */
public interface SafecapPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "safecap";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "uk.ac.ncl.safecap.emf";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "uk.ac.ncl.safecap.emf";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	SafecapPackage eINSTANCE = safecap.impl.SafecapPackageImpl.init();

	/**
	 * The meta object id for the '{@link safecap.impl.LabeledImpl
	 * <em>Labeled</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.impl.LabeledImpl
	 * @see safecap.impl.SafecapPackageImpl#getLabeled()
	 * @generated
	 */
	int LABELED = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LABELED__LABEL = 0;

	/**
	 * The number of structural features of the '<em>Labeled</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LABELED_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link safecap.impl.ProjectImpl
	 * <em>Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.impl.ProjectImpl
	 * @see safecap.impl.SafecapPackageImpl#getProject()
	 * @generated
	 */
	int PROJECT = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__LABEL = LABELED__LABEL;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__NODES = LABELED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__SEGMENTS = LABELED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Lines</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__LINES = LABELED_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Routes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__ROUTES = LABELED_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Ambits</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__AMBITS = LABELED_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Equipment</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__EQUIPMENT = LABELED_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Wires</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__WIRES = LABELED_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Nodewires</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__NODEWIRES = LABELED_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Subwires</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__SUBWIRES = LABELED_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Subequipment</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__SUBEQUIPMENT = LABELED_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__COMMENTS = LABELED_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__RUNTIME_ATTRIBUTES = LABELED_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__ATTRIBUTES = LABELED_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Mergedpoints</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__MERGEDPOINTS = LABELED_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__ORIGIN = LABELED_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__AUTHOR = LABELED_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__VERSION = LABELED_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__DATE = LABELED_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__CONFIGURATION = LABELED_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Routebuilder</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__ROUTEBUILDER = LABELED_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>Darkmatter</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT__DARKMATTER = LABELED_FEATURE_COUNT + 20;

	/**
	 * The number of structural features of the '<em>Project</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROJECT_FEATURE_COUNT = LABELED_FEATURE_COUNT + 21;

	/**
	 * The meta object id for the '{@link safecap.impl.VisualImpl <em>Visual</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.impl.VisualImpl
	 * @see safecap.impl.SafecapPackageImpl#getVisual()
	 * @generated
	 */
	int VISUAL = 2;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VISUAL__VISIBLE = 0;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VISUAL__STYLE = 1;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VISUAL__INTERVALS = 2;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VISUAL__MARKERS = 3;

	/**
	 * The number of structural features of the '<em>Visual</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VISUAL_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link safecap.impl.StyleImpl <em>Style</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.impl.StyleImpl
	 * @see safecap.impl.SafecapPackageImpl#getStyle()
	 * @generated
	 */
	int STYLE = 3;

	/**
	 * The feature id for the '<em><b>Foreground</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STYLE__FOREGROUND = 0;

	/**
	 * The feature id for the '<em><b>Background</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STYLE__BACKGROUND = 1;

	/**
	 * The feature id for the '<em><b>Linewidth</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STYLE__LINEWIDTH = 2;

	/**
	 * The feature id for the '<em><b>Linestyle</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STYLE__LINESTYLE = 3;

	/**
	 * The number of structural features of the '<em>Style</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STYLE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link safecap.impl.ProvenanceImpl
	 * <em>Provenance</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.impl.ProvenanceImpl
	 * @see safecap.impl.SafecapPackageImpl#getProvenance()
	 * @generated
	 */
	int PROVENANCE = 4;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROVENANCE__ORIGIN = 0;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROVENANCE__AUTHOR = 1;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROVENANCE__VERSION = 2;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROVENANCE__DATE = 3;

	/**
	 * The number of structural features of the '<em>Provenance</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROVENANCE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link safecap.impl.TransientValuesImpl
	 * <em>Transient Values</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.impl.TransientValuesImpl
	 * @see safecap.impl.SafecapPackageImpl#getTransientValues()
	 * @generated
	 */
	int TRANSIENT_VALUES = 8;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRANSIENT_VALUES__RUNTIME_ATTRIBUTES = 0;

	/**
	 * The number of structural features of the '<em>Transient Values</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRANSIENT_VALUES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link safecap.impl.ExtensibleImpl
	 * <em>Extensible</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.impl.ExtensibleImpl
	 * @see safecap.impl.SafecapPackageImpl#getExtensible()
	 * @generated
	 */
	int EXTENSIBLE = 5;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXTENSIBLE__RUNTIME_ATTRIBUTES = TRANSIENT_VALUES__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXTENSIBLE__ATTRIBUTES = TRANSIENT_VALUES_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Extensible</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXTENSIBLE_FEATURE_COUNT = TRANSIENT_VALUES_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link safecap.impl.HighlightedIntervalImpl
	 * <em>Highlighted Interval</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see safecap.impl.HighlightedIntervalImpl
	 * @see safecap.impl.SafecapPackageImpl#getHighlightedInterval()
	 * @generated
	 */
	int HIGHLIGHTED_INTERVAL = 6;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HIGHLIGHTED_INTERVAL__STYLE = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HIGHLIGHTED_INTERVAL__LABEL = 1;

	/**
	 * The feature id for the '<em><b>From</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HIGHLIGHTED_INTERVAL__FROM = 2;

	/**
	 * The feature id for the '<em><b>To</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HIGHLIGHTED_INTERVAL__TO = 3;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HIGHLIGHTED_INTERVAL__OWNER = 4;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HIGHLIGHTED_INTERVAL__INDEX = 5;

	/**
	 * The feature id for the '<em><b>Voffset</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HIGHLIGHTED_INTERVAL__VOFFSET = 6;

	/**
	 * The number of structural features of the '<em>Highlighted Interval</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HIGHLIGHTED_INTERVAL_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link safecap.impl.VisualMarkerImpl <em>Visual
	 * Marker</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.impl.VisualMarkerImpl
	 * @see safecap.impl.SafecapPackageImpl#getVisualMarker()
	 * @generated
	 */
	int VISUAL_MARKER = 7;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VISUAL_MARKER__STYLE = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VISUAL_MARKER__LABEL = 1;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VISUAL_MARKER__POSITION = 2;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VISUAL_MARKER__OWNER = 3;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VISUAL_MARKER__INDEX = 4;

	/**
	 * The feature id for the '<em><b>Offset Label</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VISUAL_MARKER__OFFSET_LABEL = 5;

	/**
	 * The number of structural features of the '<em>Visual Marker</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VISUAL_MARKER_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link safecap.Orientation <em>Orientation</em>}'
	 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.Orientation
	 * @see safecap.impl.SafecapPackageImpl#getOrientation()
	 * @generated
	 */
	int ORIENTATION = 9;

	/**
	 * Returns the meta object for class '{@link safecap.Labeled <em>Labeled</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Labeled</em>'.
	 * @see safecap.Labeled
	 * @generated
	 */
	EClass getLabeled();

	/**
	 * Returns the meta object for the attribute '{@link safecap.Labeled#getLabel
	 * <em>Label</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see safecap.Labeled#getLabel()
	 * @see #getLabeled()
	 * @generated
	 */
	EAttribute getLabeled_Label();

	/**
	 * Returns the meta object for class '{@link safecap.Project <em>Project</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Project</em>'.
	 * @see safecap.Project
	 * @generated
	 */
	EClass getProject();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link safecap.Project#getConfiguration <em>Configuration</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference
	 *         '<em>Configuration</em>'.
	 * @see safecap.Project#getConfiguration()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Configuration();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link safecap.Project#getRoutebuilder <em>Routebuilder</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference
	 *         '<em>Routebuilder</em>'.
	 * @see safecap.Project#getRoutebuilder()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Routebuilder();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.Project#getDarkmatter <em>Darkmatter</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Darkmatter</em>'.
	 * @see safecap.Project#getDarkmatter()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Darkmatter();

	/**
	 * Returns the meta object for class '{@link safecap.Visual <em>Visual</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Visual</em>'.
	 * @see safecap.Visual
	 * @generated
	 */
	EClass getVisual();

	/**
	 * Returns the meta object for the attribute '{@link safecap.Visual#isVisible
	 * <em>Visible</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Visible</em>'.
	 * @see safecap.Visual#isVisible()
	 * @see #getVisual()
	 * @generated
	 */
	EAttribute getVisual_Visible();

	/**
	 * Returns the meta object for the reference '{@link safecap.Visual#getStyle
	 * <em>Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Style</em>'.
	 * @see safecap.Visual#getStyle()
	 * @see #getVisual()
	 * @generated
	 */
	EReference getVisual_Style();

	/**
	 * Returns the meta object for the reference list
	 * '{@link safecap.Visual#getIntervals <em>Intervals</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Intervals</em>'.
	 * @see safecap.Visual#getIntervals()
	 * @see #getVisual()
	 * @generated
	 */
	EReference getVisual_Intervals();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.Visual#getMarkers <em>Markers</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Markers</em>'.
	 * @see safecap.Visual#getMarkers()
	 * @see #getVisual()
	 * @generated
	 */
	EReference getVisual_Markers();

	/**
	 * Returns the meta object for class '{@link safecap.Style <em>Style</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Style</em>'.
	 * @see safecap.Style
	 * @generated
	 */
	EClass getStyle();

	/**
	 * Returns the meta object for the attribute '{@link safecap.Style#getForeground
	 * <em>Foreground</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Foreground</em>'.
	 * @see safecap.Style#getForeground()
	 * @see #getStyle()
	 * @generated
	 */
	EAttribute getStyle_Foreground();

	/**
	 * Returns the meta object for the attribute '{@link safecap.Style#getBackground
	 * <em>Background</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Background</em>'.
	 * @see safecap.Style#getBackground()
	 * @see #getStyle()
	 * @generated
	 */
	EAttribute getStyle_Background();

	/**
	 * Returns the meta object for the attribute '{@link safecap.Style#getLinewidth
	 * <em>Linewidth</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Linewidth</em>'.
	 * @see safecap.Style#getLinewidth()
	 * @see #getStyle()
	 * @generated
	 */
	EAttribute getStyle_Linewidth();

	/**
	 * Returns the meta object for the attribute '{@link safecap.Style#getLinestyle
	 * <em>Linestyle</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Linestyle</em>'.
	 * @see safecap.Style#getLinestyle()
	 * @see #getStyle()
	 * @generated
	 */
	EAttribute getStyle_Linestyle();

	/**
	 * Returns the meta object for class '{@link safecap.Provenance
	 * <em>Provenance</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Provenance</em>'.
	 * @see safecap.Provenance
	 * @generated
	 */
	EClass getProvenance();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.Provenance#getOrigin <em>Origin</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Origin</em>'.
	 * @see safecap.Provenance#getOrigin()
	 * @see #getProvenance()
	 * @generated
	 */
	EAttribute getProvenance_Origin();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.Provenance#getAuthor <em>Author</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see safecap.Provenance#getAuthor()
	 * @see #getProvenance()
	 * @generated
	 */
	EAttribute getProvenance_Author();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.Provenance#getVersion <em>Version</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see safecap.Provenance#getVersion()
	 * @see #getProvenance()
	 * @generated
	 */
	EAttribute getProvenance_Version();

	/**
	 * Returns the meta object for the attribute '{@link safecap.Provenance#getDate
	 * <em>Date</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see safecap.Provenance#getDate()
	 * @see #getProvenance()
	 * @generated
	 */
	EAttribute getProvenance_Date();

	/**
	 * Returns the meta object for class '{@link safecap.Extensible
	 * <em>Extensible</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Extensible</em>'.
	 * @see safecap.Extensible
	 * @generated
	 */
	EClass getExtensible();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.Extensible#getAttributes <em>Attributes</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Attributes</em>'.
	 * @see safecap.Extensible#getAttributes()
	 * @see #getExtensible()
	 * @generated
	 */
	EReference getExtensible_Attributes();

	/**
	 * Returns the meta object for class '{@link safecap.HighlightedInterval
	 * <em>Highlighted Interval</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Highlighted Interval</em>'.
	 * @see safecap.HighlightedInterval
	 * @generated
	 */
	EClass getHighlightedInterval();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.HighlightedInterval#getStyle <em>Style</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Style</em>'.
	 * @see safecap.HighlightedInterval#getStyle()
	 * @see #getHighlightedInterval()
	 * @generated
	 */
	EReference getHighlightedInterval_Style();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.HighlightedInterval#getLabel <em>Label</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see safecap.HighlightedInterval#getLabel()
	 * @see #getHighlightedInterval()
	 * @generated
	 */
	EAttribute getHighlightedInterval_Label();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.HighlightedInterval#getFrom <em>From</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>From</em>'.
	 * @see safecap.HighlightedInterval#getFrom()
	 * @see #getHighlightedInterval()
	 * @generated
	 */
	EAttribute getHighlightedInterval_From();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.HighlightedInterval#getTo <em>To</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>To</em>'.
	 * @see safecap.HighlightedInterval#getTo()
	 * @see #getHighlightedInterval()
	 * @generated
	 */
	EAttribute getHighlightedInterval_To();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.HighlightedInterval#getOwner <em>Owner</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Owner</em>'.
	 * @see safecap.HighlightedInterval#getOwner()
	 * @see #getHighlightedInterval()
	 * @generated
	 */
	EAttribute getHighlightedInterval_Owner();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.HighlightedInterval#getIndex <em>Index</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see safecap.HighlightedInterval#getIndex()
	 * @see #getHighlightedInterval()
	 * @generated
	 */
	EAttribute getHighlightedInterval_Index();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.HighlightedInterval#getVoffset <em>Voffset</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Voffset</em>'.
	 * @see safecap.HighlightedInterval#getVoffset()
	 * @see #getHighlightedInterval()
	 * @generated
	 */
	EAttribute getHighlightedInterval_Voffset();

	/**
	 * Returns the meta object for class '{@link safecap.VisualMarker <em>Visual
	 * Marker</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Visual Marker</em>'.
	 * @see safecap.VisualMarker
	 * @generated
	 */
	EClass getVisualMarker();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.VisualMarker#getStyle <em>Style</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Style</em>'.
	 * @see safecap.VisualMarker#getStyle()
	 * @see #getVisualMarker()
	 * @generated
	 */
	EReference getVisualMarker_Style();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.VisualMarker#getLabel <em>Label</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see safecap.VisualMarker#getLabel()
	 * @see #getVisualMarker()
	 * @generated
	 */
	EAttribute getVisualMarker_Label();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.VisualMarker#getPosition <em>Position</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Position</em>'.
	 * @see safecap.VisualMarker#getPosition()
	 * @see #getVisualMarker()
	 * @generated
	 */
	EAttribute getVisualMarker_Position();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.VisualMarker#getOwner <em>Owner</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Owner</em>'.
	 * @see safecap.VisualMarker#getOwner()
	 * @see #getVisualMarker()
	 * @generated
	 */
	EAttribute getVisualMarker_Owner();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.VisualMarker#getIndex <em>Index</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see safecap.VisualMarker#getIndex()
	 * @see #getVisualMarker()
	 * @generated
	 */
	EAttribute getVisualMarker_Index();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.VisualMarker#getOffsetLabel <em>Offset Label</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Offset Label</em>'.
	 * @see safecap.VisualMarker#getOffsetLabel()
	 * @see #getVisualMarker()
	 * @generated
	 */
	EAttribute getVisualMarker_OffsetLabel();

	/**
	 * Returns the meta object for class '{@link safecap.TransientValues
	 * <em>Transient Values</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Transient Values</em>'.
	 * @see safecap.TransientValues
	 * @generated
	 */
	EClass getTransientValues();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.TransientValues#getRuntimeAttributes <em>Runtime
	 * Attributes</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Runtime Attributes</em>'.
	 * @see safecap.TransientValues#getRuntimeAttributes()
	 * @see #getTransientValues()
	 * @generated
	 */
	EAttribute getTransientValues_RuntimeAttributes();

	/**
	 * Returns the meta object for enum '{@link safecap.Orientation
	 * <em>Orientation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Orientation</em>'.
	 * @see safecap.Orientation
	 * @generated
	 */
	EEnum getOrientation();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SafecapFactory getSafecapFactory();

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
		 * The meta object literal for the '{@link safecap.impl.LabeledImpl
		 * <em>Labeled</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.impl.LabeledImpl
		 * @see safecap.impl.SafecapPackageImpl#getLabeled()
		 * @generated
		 */
		EClass LABELED = eINSTANCE.getLabeled();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LABELED__LABEL = eINSTANCE.getLabeled_Label();

		/**
		 * The meta object literal for the '{@link safecap.impl.ProjectImpl
		 * <em>Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.impl.ProjectImpl
		 * @see safecap.impl.SafecapPackageImpl#getProject()
		 * @generated
		 */
		EClass PROJECT = eINSTANCE.getProject();

		/**
		 * The meta object literal for the '<em><b>Configuration</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROJECT__CONFIGURATION = eINSTANCE.getProject_Configuration();

		/**
		 * The meta object literal for the '<em><b>Routebuilder</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROJECT__ROUTEBUILDER = eINSTANCE.getProject_Routebuilder();

		/**
		 * The meta object literal for the '<em><b>Darkmatter</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROJECT__DARKMATTER = eINSTANCE.getProject_Darkmatter();

		/**
		 * The meta object literal for the '{@link safecap.impl.VisualImpl
		 * <em>Visual</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.impl.VisualImpl
		 * @see safecap.impl.SafecapPackageImpl#getVisual()
		 * @generated
		 */
		EClass VISUAL = eINSTANCE.getVisual();

		/**
		 * The meta object literal for the '<em><b>Visible</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VISUAL__VISIBLE = eINSTANCE.getVisual_Visible();

		/**
		 * The meta object literal for the '<em><b>Style</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VISUAL__STYLE = eINSTANCE.getVisual_Style();

		/**
		 * The meta object literal for the '<em><b>Intervals</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VISUAL__INTERVALS = eINSTANCE.getVisual_Intervals();

		/**
		 * The meta object literal for the '<em><b>Markers</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VISUAL__MARKERS = eINSTANCE.getVisual_Markers();

		/**
		 * The meta object literal for the '{@link safecap.impl.StyleImpl
		 * <em>Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.impl.StyleImpl
		 * @see safecap.impl.SafecapPackageImpl#getStyle()
		 * @generated
		 */
		EClass STYLE = eINSTANCE.getStyle();

		/**
		 * The meta object literal for the '<em><b>Foreground</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STYLE__FOREGROUND = eINSTANCE.getStyle_Foreground();

		/**
		 * The meta object literal for the '<em><b>Background</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STYLE__BACKGROUND = eINSTANCE.getStyle_Background();

		/**
		 * The meta object literal for the '<em><b>Linewidth</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STYLE__LINEWIDTH = eINSTANCE.getStyle_Linewidth();

		/**
		 * The meta object literal for the '<em><b>Linestyle</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STYLE__LINESTYLE = eINSTANCE.getStyle_Linestyle();

		/**
		 * The meta object literal for the '{@link safecap.impl.ProvenanceImpl
		 * <em>Provenance</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.impl.ProvenanceImpl
		 * @see safecap.impl.SafecapPackageImpl#getProvenance()
		 * @generated
		 */
		EClass PROVENANCE = eINSTANCE.getProvenance();

		/**
		 * The meta object literal for the '<em><b>Origin</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROVENANCE__ORIGIN = eINSTANCE.getProvenance_Origin();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROVENANCE__AUTHOR = eINSTANCE.getProvenance_Author();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROVENANCE__VERSION = eINSTANCE.getProvenance_Version();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROVENANCE__DATE = eINSTANCE.getProvenance_Date();

		/**
		 * The meta object literal for the '{@link safecap.impl.ExtensibleImpl
		 * <em>Extensible</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.impl.ExtensibleImpl
		 * @see safecap.impl.SafecapPackageImpl#getExtensible()
		 * @generated
		 */
		EClass EXTENSIBLE = eINSTANCE.getExtensible();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXTENSIBLE__ATTRIBUTES = eINSTANCE.getExtensible_Attributes();

		/**
		 * The meta object literal for the '{@link safecap.impl.HighlightedIntervalImpl
		 * <em>Highlighted Interval</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see safecap.impl.HighlightedIntervalImpl
		 * @see safecap.impl.SafecapPackageImpl#getHighlightedInterval()
		 * @generated
		 */
		EClass HIGHLIGHTED_INTERVAL = eINSTANCE.getHighlightedInterval();

		/**
		 * The meta object literal for the '<em><b>Style</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference HIGHLIGHTED_INTERVAL__STYLE = eINSTANCE.getHighlightedInterval_Style();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute HIGHLIGHTED_INTERVAL__LABEL = eINSTANCE.getHighlightedInterval_Label();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute HIGHLIGHTED_INTERVAL__FROM = eINSTANCE.getHighlightedInterval_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute HIGHLIGHTED_INTERVAL__TO = eINSTANCE.getHighlightedInterval_To();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute HIGHLIGHTED_INTERVAL__OWNER = eINSTANCE.getHighlightedInterval_Owner();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute HIGHLIGHTED_INTERVAL__INDEX = eINSTANCE.getHighlightedInterval_Index();

		/**
		 * The meta object literal for the '<em><b>Voffset</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute HIGHLIGHTED_INTERVAL__VOFFSET = eINSTANCE.getHighlightedInterval_Voffset();

		/**
		 * The meta object literal for the '{@link safecap.impl.VisualMarkerImpl
		 * <em>Visual Marker</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.impl.VisualMarkerImpl
		 * @see safecap.impl.SafecapPackageImpl#getVisualMarker()
		 * @generated
		 */
		EClass VISUAL_MARKER = eINSTANCE.getVisualMarker();

		/**
		 * The meta object literal for the '<em><b>Style</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VISUAL_MARKER__STYLE = eINSTANCE.getVisualMarker_Style();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VISUAL_MARKER__LABEL = eINSTANCE.getVisualMarker_Label();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VISUAL_MARKER__POSITION = eINSTANCE.getVisualMarker_Position();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VISUAL_MARKER__OWNER = eINSTANCE.getVisualMarker_Owner();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VISUAL_MARKER__INDEX = eINSTANCE.getVisualMarker_Index();

		/**
		 * The meta object literal for the '<em><b>Offset Label</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VISUAL_MARKER__OFFSET_LABEL = eINSTANCE.getVisualMarker_OffsetLabel();

		/**
		 * The meta object literal for the '{@link safecap.impl.TransientValuesImpl
		 * <em>Transient Values</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see safecap.impl.TransientValuesImpl
		 * @see safecap.impl.SafecapPackageImpl#getTransientValues()
		 * @generated
		 */
		EClass TRANSIENT_VALUES = eINSTANCE.getTransientValues();

		/**
		 * The meta object literal for the '<em><b>Runtime Attributes</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRANSIENT_VALUES__RUNTIME_ATTRIBUTES = eINSTANCE.getTransientValues_RuntimeAttributes();

		/**
		 * The meta object literal for the '{@link safecap.Orientation
		 * <em>Orientation</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.Orientation
		 * @see safecap.impl.SafecapPackageImpl#getOrientation()
		 * @generated
		 */
		EEnum ORIENTATION = eINSTANCE.getOrientation();

	}

} // SafecapPackage
