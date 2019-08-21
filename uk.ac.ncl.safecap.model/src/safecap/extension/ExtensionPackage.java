/**
 */
package safecap.extension;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import safecap.trackside.TracksidePackage;

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
 * @see safecap.extension.ExtensionFactory
 * @model kind="package"
 * @generated
 */
public interface ExtensionPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "extension";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "uk.ac.ncl.safecap.emf.extension";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "uk.ac.ncl.safecap.emf.extension";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	ExtensionPackage eINSTANCE = safecap.extension.impl.ExtensionPackageImpl.init();

	/**
	 * The meta object id for the '{@link safecap.extension.impl.ExtAttributeImpl
	 * <em>Ext Attribute</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.extension.impl.ExtAttributeImpl
	 * @see safecap.extension.impl.ExtensionPackageImpl#getExtAttribute()
	 * @generated
	 */
	int EXT_ATTRIBUTE = 0;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE__DOMAIN = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE__LABEL = 1;

	/**
	 * The number of structural features of the '<em>Ext Attribute</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link safecap.extension.impl.ExtAttributeIntImpl
	 * <em>Ext Attribute Int</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.extension.impl.ExtAttributeIntImpl
	 * @see safecap.extension.impl.ExtensionPackageImpl#getExtAttributeInt()
	 * @generated
	 */
	int EXT_ATTRIBUTE_INT = 1;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_INT__DOMAIN = EXT_ATTRIBUTE__DOMAIN;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_INT__LABEL = EXT_ATTRIBUTE__LABEL;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_INT__VALUE = EXT_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Ext Attribute Int</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_INT_FEATURE_COUNT = EXT_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link safecap.extension.impl.ExtAttributeDblImpl
	 * <em>Ext Attribute Dbl</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.extension.impl.ExtAttributeDblImpl
	 * @see safecap.extension.impl.ExtensionPackageImpl#getExtAttributeDbl()
	 * @generated
	 */
	int EXT_ATTRIBUTE_DBL = 2;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_DBL__DOMAIN = EXT_ATTRIBUTE__DOMAIN;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_DBL__LABEL = EXT_ATTRIBUTE__LABEL;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_DBL__VALUE = EXT_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Ext Attribute Dbl</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_DBL_FEATURE_COUNT = EXT_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link safecap.extension.impl.ExtAttributeStrImpl
	 * <em>Ext Attribute Str</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.extension.impl.ExtAttributeStrImpl
	 * @see safecap.extension.impl.ExtensionPackageImpl#getExtAttributeStr()
	 * @generated
	 */
	int EXT_ATTRIBUTE_STR = 3;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_STR__DOMAIN = EXT_ATTRIBUTE__DOMAIN;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_STR__LABEL = EXT_ATTRIBUTE__LABEL;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_STR__VALUE = EXT_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Ext Attribute Str</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_STR_FEATURE_COUNT = EXT_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link safecap.extension.impl.ExtAttributeBImpl
	 * <em>Ext Attribute B</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.extension.impl.ExtAttributeBImpl
	 * @see safecap.extension.impl.ExtensionPackageImpl#getExtAttributeB()
	 * @generated
	 */
	int EXT_ATTRIBUTE_B = 4;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_B__DOMAIN = EXT_ATTRIBUTE__DOMAIN;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_B__LABEL = EXT_ATTRIBUTE__LABEL;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_B__VALUE = EXT_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Ext Attribute B</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_ATTRIBUTE_B_FEATURE_COUNT = EXT_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link safecap.extension.impl.CustomFigureImpl
	 * <em>Custom Figure</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.extension.impl.CustomFigureImpl
	 * @see safecap.extension.impl.ExtensionPackageImpl#getCustomFigure()
	 * @generated
	 */
	int CUSTOM_FIGURE = 5;

	/**
	 * The feature id for the '<em><b>Filled</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_FIGURE__FILLED = 0;

	/**
	 * The feature id for the '<em><b>Foreground</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_FIGURE__FOREGROUND = 1;

	/**
	 * The feature id for the '<em><b>Background</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_FIGURE__BACKGROUND = 2;

	/**
	 * The number of structural features of the '<em>Custom Figure</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_FIGURE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link safecap.extension.impl.CustomLabelImpl
	 * <em>Custom Label</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.extension.impl.CustomLabelImpl
	 * @see safecap.extension.impl.ExtensionPackageImpl#getCustomLabel()
	 * @generated
	 */
	int CUSTOM_LABEL = 6;

	/**
	 * The feature id for the '<em><b>Filled</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_LABEL__FILLED = CUSTOM_FIGURE__FILLED;

	/**
	 * The feature id for the '<em><b>Foreground</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_LABEL__FOREGROUND = CUSTOM_FIGURE__FOREGROUND;

	/**
	 * The feature id for the '<em><b>Background</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_LABEL__BACKGROUND = CUSTOM_FIGURE__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_LABEL__TEXT = CUSTOM_FIGURE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Fontsize</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_LABEL__FONTSIZE = CUSTOM_FIGURE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Custom Label</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_LABEL_FEATURE_COUNT = CUSTOM_FIGURE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link safecap.extension.impl.PointImpl
	 * <em>Point</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.extension.impl.PointImpl
	 * @see safecap.extension.impl.ExtensionPackageImpl#getPoint()
	 * @generated
	 */
	int POINT = 7;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POINT__X = 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POINT__Y = 1;

	/**
	 * The number of structural features of the '<em>Point</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POINT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link safecap.extension.impl.CustomColourImpl
	 * <em>Custom Colour</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.extension.impl.CustomColourImpl
	 * @see safecap.extension.impl.ExtensionPackageImpl#getCustomColour()
	 * @generated
	 */
	int CUSTOM_COLOUR = 8;

	/**
	 * The feature id for the '<em><b>Red</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_COLOUR__RED = 0;

	/**
	 * The feature id for the '<em><b>Green</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_COLOUR__GREEN = 1;

	/**
	 * The feature id for the '<em><b>Blue</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_COLOUR__BLUE = 2;

	/**
	 * The number of structural features of the '<em>Custom Colour</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_COLOUR_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link safecap.extension.impl.CustomShapeImpl
	 * <em>Custom Shape</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.extension.impl.CustomShapeImpl
	 * @see safecap.extension.impl.ExtensionPackageImpl#getCustomShape()
	 * @generated
	 */
	int CUSTOM_SHAPE = 9;

	/**
	 * The feature id for the '<em><b>Filled</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_SHAPE__FILLED = CUSTOM_FIGURE__FILLED;

	/**
	 * The feature id for the '<em><b>Foreground</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_SHAPE__FOREGROUND = CUSTOM_FIGURE__FOREGROUND;

	/**
	 * The feature id for the '<em><b>Background</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_SHAPE__BACKGROUND = CUSTOM_FIGURE__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Shape</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_SHAPE__SHAPE = CUSTOM_FIGURE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Custom Shape</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CUSTOM_SHAPE_FEATURE_COUNT = CUSTOM_FIGURE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link safecap.extension.impl.ExtVisualImpl
	 * <em>Ext Visual</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.extension.impl.ExtVisualImpl
	 * @see safecap.extension.impl.ExtensionPackageImpl#getExtVisual()
	 * @generated
	 */
	int EXT_VISUAL = 10;

	/**
	 * The feature id for the '<em><b>Figures</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_VISUAL__FIGURES = 0;

	/**
	 * The number of structural features of the '<em>Ext Visual</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_VISUAL_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link safecap.extension.impl.ExtEquipmentImpl
	 * <em>Ext Equipment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.extension.impl.ExtEquipmentImpl
	 * @see safecap.extension.impl.ExtensionPackageImpl#getExtEquipment()
	 * @generated
	 */
	int EXT_EQUIPMENT = 11;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_EQUIPMENT__LABEL = TracksidePackage.EQUIPMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_EQUIPMENT__VISIBLE = TracksidePackage.EQUIPMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_EQUIPMENT__STYLE = TracksidePackage.EQUIPMENT__STYLE;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_EQUIPMENT__INTERVALS = TracksidePackage.EQUIPMENT__INTERVALS;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_EQUIPMENT__MARKERS = TracksidePackage.EQUIPMENT__MARKERS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_EQUIPMENT__ORIGIN = TracksidePackage.EQUIPMENT__ORIGIN;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_EQUIPMENT__AUTHOR = TracksidePackage.EQUIPMENT__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_EQUIPMENT__VERSION = TracksidePackage.EQUIPMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_EQUIPMENT__DATE = TracksidePackage.EQUIPMENT__DATE;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_EQUIPMENT__RUNTIME_ATTRIBUTES = TracksidePackage.EQUIPMENT__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_EQUIPMENT__ATTRIBUTES = TracksidePackage.EQUIPMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Visual</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_EQUIPMENT__VISUAL = TracksidePackage.EQUIPMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Ext Equipment</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXT_EQUIPMENT_FEATURE_COUNT = TracksidePackage.EQUIPMENT_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link safecap.extension.ExtAttribute
	 * <em>Ext Attribute</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Ext Attribute</em>'.
	 * @see safecap.extension.ExtAttribute
	 * @generated
	 */
	EClass getExtAttribute();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.extension.ExtAttribute#getDomain <em>Domain</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Domain</em>'.
	 * @see safecap.extension.ExtAttribute#getDomain()
	 * @see #getExtAttribute()
	 * @generated
	 */
	EAttribute getExtAttribute_Domain();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.extension.ExtAttribute#getLabel <em>Label</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see safecap.extension.ExtAttribute#getLabel()
	 * @see #getExtAttribute()
	 * @generated
	 */
	EAttribute getExtAttribute_Label();

	/**
	 * Returns the meta object for class '{@link safecap.extension.ExtAttributeInt
	 * <em>Ext Attribute Int</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Ext Attribute Int</em>'.
	 * @see safecap.extension.ExtAttributeInt
	 * @generated
	 */
	EClass getExtAttributeInt();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.extension.ExtAttributeInt#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see safecap.extension.ExtAttributeInt#getValue()
	 * @see #getExtAttributeInt()
	 * @generated
	 */
	EAttribute getExtAttributeInt_Value();

	/**
	 * Returns the meta object for class '{@link safecap.extension.ExtAttributeDbl
	 * <em>Ext Attribute Dbl</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Ext Attribute Dbl</em>'.
	 * @see safecap.extension.ExtAttributeDbl
	 * @generated
	 */
	EClass getExtAttributeDbl();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.extension.ExtAttributeDbl#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see safecap.extension.ExtAttributeDbl#getValue()
	 * @see #getExtAttributeDbl()
	 * @generated
	 */
	EAttribute getExtAttributeDbl_Value();

	/**
	 * Returns the meta object for class '{@link safecap.extension.ExtAttributeStr
	 * <em>Ext Attribute Str</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Ext Attribute Str</em>'.
	 * @see safecap.extension.ExtAttributeStr
	 * @generated
	 */
	EClass getExtAttributeStr();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.extension.ExtAttributeStr#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see safecap.extension.ExtAttributeStr#getValue()
	 * @see #getExtAttributeStr()
	 * @generated
	 */
	EAttribute getExtAttributeStr_Value();

	/**
	 * Returns the meta object for class '{@link safecap.extension.ExtAttributeB
	 * <em>Ext Attribute B</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Ext Attribute B</em>'.
	 * @see safecap.extension.ExtAttributeB
	 * @generated
	 */
	EClass getExtAttributeB();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.extension.ExtAttributeB#isValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see safecap.extension.ExtAttributeB#isValue()
	 * @see #getExtAttributeB()
	 * @generated
	 */
	EAttribute getExtAttributeB_Value();

	/**
	 * Returns the meta object for class '{@link safecap.extension.CustomFigure
	 * <em>Custom Figure</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Custom Figure</em>'.
	 * @see safecap.extension.CustomFigure
	 * @generated
	 */
	EClass getCustomFigure();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.extension.CustomFigure#isFilled <em>Filled</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Filled</em>'.
	 * @see safecap.extension.CustomFigure#isFilled()
	 * @see #getCustomFigure()
	 * @generated
	 */
	EAttribute getCustomFigure_Filled();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.extension.CustomFigure#getForeground <em>Foreground</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Foreground</em>'.
	 * @see safecap.extension.CustomFigure#getForeground()
	 * @see #getCustomFigure()
	 * @generated
	 */
	EReference getCustomFigure_Foreground();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.extension.CustomFigure#getBackground <em>Background</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Background</em>'.
	 * @see safecap.extension.CustomFigure#getBackground()
	 * @see #getCustomFigure()
	 * @generated
	 */
	EReference getCustomFigure_Background();

	/**
	 * Returns the meta object for class '{@link safecap.extension.CustomLabel
	 * <em>Custom Label</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Custom Label</em>'.
	 * @see safecap.extension.CustomLabel
	 * @generated
	 */
	EClass getCustomLabel();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.extension.CustomLabel#getText <em>Text</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see safecap.extension.CustomLabel#getText()
	 * @see #getCustomLabel()
	 * @generated
	 */
	EAttribute getCustomLabel_Text();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.extension.CustomLabel#getFontsize <em>Fontsize</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Fontsize</em>'.
	 * @see safecap.extension.CustomLabel#getFontsize()
	 * @see #getCustomLabel()
	 * @generated
	 */
	EAttribute getCustomLabel_Fontsize();

	/**
	 * Returns the meta object for class '{@link safecap.extension.Point
	 * <em>Point</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Point</em>'.
	 * @see safecap.extension.Point
	 * @generated
	 */
	EClass getPoint();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.extension.Point#getX <em>X</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see safecap.extension.Point#getX()
	 * @see #getPoint()
	 * @generated
	 */
	EAttribute getPoint_X();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.extension.Point#getY <em>Y</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see safecap.extension.Point#getY()
	 * @see #getPoint()
	 * @generated
	 */
	EAttribute getPoint_Y();

	/**
	 * Returns the meta object for class '{@link safecap.extension.CustomColour
	 * <em>Custom Colour</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Custom Colour</em>'.
	 * @see safecap.extension.CustomColour
	 * @generated
	 */
	EClass getCustomColour();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.extension.CustomColour#getRed <em>Red</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Red</em>'.
	 * @see safecap.extension.CustomColour#getRed()
	 * @see #getCustomColour()
	 * @generated
	 */
	EAttribute getCustomColour_Red();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.extension.CustomColour#getGreen <em>Green</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Green</em>'.
	 * @see safecap.extension.CustomColour#getGreen()
	 * @see #getCustomColour()
	 * @generated
	 */
	EAttribute getCustomColour_Green();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.extension.CustomColour#getBlue <em>Blue</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Blue</em>'.
	 * @see safecap.extension.CustomColour#getBlue()
	 * @see #getCustomColour()
	 * @generated
	 */
	EAttribute getCustomColour_Blue();

	/**
	 * Returns the meta object for class '{@link safecap.extension.CustomShape
	 * <em>Custom Shape</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Custom Shape</em>'.
	 * @see safecap.extension.CustomShape
	 * @generated
	 */
	EClass getCustomShape();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.extension.CustomShape#getShape <em>Shape</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Shape</em>'.
	 * @see safecap.extension.CustomShape#getShape()
	 * @see #getCustomShape()
	 * @generated
	 */
	EReference getCustomShape_Shape();

	/**
	 * Returns the meta object for class '{@link safecap.extension.ExtVisual <em>Ext
	 * Visual</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Ext Visual</em>'.
	 * @see safecap.extension.ExtVisual
	 * @generated
	 */
	EClass getExtVisual();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.extension.ExtVisual#getFigures <em>Figures</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Figures</em>'.
	 * @see safecap.extension.ExtVisual#getFigures()
	 * @see #getExtVisual()
	 * @generated
	 */
	EReference getExtVisual_Figures();

	/**
	 * Returns the meta object for class '{@link safecap.extension.ExtEquipment
	 * <em>Ext Equipment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Ext Equipment</em>'.
	 * @see safecap.extension.ExtEquipment
	 * @generated
	 */
	EClass getExtEquipment();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link safecap.extension.ExtEquipment#getVisual <em>Visual</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Visual</em>'.
	 * @see safecap.extension.ExtEquipment#getVisual()
	 * @see #getExtEquipment()
	 * @generated
	 */
	EReference getExtEquipment_Visual();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ExtensionFactory getExtensionFactory();

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
		 * The meta object literal for the
		 * '{@link safecap.extension.impl.ExtAttributeImpl <em>Ext Attribute</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.extension.impl.ExtAttributeImpl
		 * @see safecap.extension.impl.ExtensionPackageImpl#getExtAttribute()
		 * @generated
		 */
		EClass EXT_ATTRIBUTE = eINSTANCE.getExtAttribute();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXT_ATTRIBUTE__DOMAIN = eINSTANCE.getExtAttribute_Domain();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXT_ATTRIBUTE__LABEL = eINSTANCE.getExtAttribute_Label();

		/**
		 * The meta object literal for the
		 * '{@link safecap.extension.impl.ExtAttributeIntImpl <em>Ext Attribute
		 * Int</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.extension.impl.ExtAttributeIntImpl
		 * @see safecap.extension.impl.ExtensionPackageImpl#getExtAttributeInt()
		 * @generated
		 */
		EClass EXT_ATTRIBUTE_INT = eINSTANCE.getExtAttributeInt();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXT_ATTRIBUTE_INT__VALUE = eINSTANCE.getExtAttributeInt_Value();

		/**
		 * The meta object literal for the
		 * '{@link safecap.extension.impl.ExtAttributeDblImpl <em>Ext Attribute
		 * Dbl</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.extension.impl.ExtAttributeDblImpl
		 * @see safecap.extension.impl.ExtensionPackageImpl#getExtAttributeDbl()
		 * @generated
		 */
		EClass EXT_ATTRIBUTE_DBL = eINSTANCE.getExtAttributeDbl();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXT_ATTRIBUTE_DBL__VALUE = eINSTANCE.getExtAttributeDbl_Value();

		/**
		 * The meta object literal for the
		 * '{@link safecap.extension.impl.ExtAttributeStrImpl <em>Ext Attribute
		 * Str</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.extension.impl.ExtAttributeStrImpl
		 * @see safecap.extension.impl.ExtensionPackageImpl#getExtAttributeStr()
		 * @generated
		 */
		EClass EXT_ATTRIBUTE_STR = eINSTANCE.getExtAttributeStr();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXT_ATTRIBUTE_STR__VALUE = eINSTANCE.getExtAttributeStr_Value();

		/**
		 * The meta object literal for the
		 * '{@link safecap.extension.impl.ExtAttributeBImpl <em>Ext Attribute B</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.extension.impl.ExtAttributeBImpl
		 * @see safecap.extension.impl.ExtensionPackageImpl#getExtAttributeB()
		 * @generated
		 */
		EClass EXT_ATTRIBUTE_B = eINSTANCE.getExtAttributeB();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXT_ATTRIBUTE_B__VALUE = eINSTANCE.getExtAttributeB_Value();

		/**
		 * The meta object literal for the
		 * '{@link safecap.extension.impl.CustomFigureImpl <em>Custom Figure</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.extension.impl.CustomFigureImpl
		 * @see safecap.extension.impl.ExtensionPackageImpl#getCustomFigure()
		 * @generated
		 */
		EClass CUSTOM_FIGURE = eINSTANCE.getCustomFigure();

		/**
		 * The meta object literal for the '<em><b>Filled</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CUSTOM_FIGURE__FILLED = eINSTANCE.getCustomFigure_Filled();

		/**
		 * The meta object literal for the '<em><b>Foreground</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CUSTOM_FIGURE__FOREGROUND = eINSTANCE.getCustomFigure_Foreground();

		/**
		 * The meta object literal for the '<em><b>Background</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CUSTOM_FIGURE__BACKGROUND = eINSTANCE.getCustomFigure_Background();

		/**
		 * The meta object literal for the
		 * '{@link safecap.extension.impl.CustomLabelImpl <em>Custom Label</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.extension.impl.CustomLabelImpl
		 * @see safecap.extension.impl.ExtensionPackageImpl#getCustomLabel()
		 * @generated
		 */
		EClass CUSTOM_LABEL = eINSTANCE.getCustomLabel();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CUSTOM_LABEL__TEXT = eINSTANCE.getCustomLabel_Text();

		/**
		 * The meta object literal for the '<em><b>Fontsize</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CUSTOM_LABEL__FONTSIZE = eINSTANCE.getCustomLabel_Fontsize();

		/**
		 * The meta object literal for the '{@link safecap.extension.impl.PointImpl
		 * <em>Point</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.extension.impl.PointImpl
		 * @see safecap.extension.impl.ExtensionPackageImpl#getPoint()
		 * @generated
		 */
		EClass POINT = eINSTANCE.getPoint();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute POINT__X = eINSTANCE.getPoint_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute POINT__Y = eINSTANCE.getPoint_Y();

		/**
		 * The meta object literal for the
		 * '{@link safecap.extension.impl.CustomColourImpl <em>Custom Colour</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.extension.impl.CustomColourImpl
		 * @see safecap.extension.impl.ExtensionPackageImpl#getCustomColour()
		 * @generated
		 */
		EClass CUSTOM_COLOUR = eINSTANCE.getCustomColour();

		/**
		 * The meta object literal for the '<em><b>Red</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CUSTOM_COLOUR__RED = eINSTANCE.getCustomColour_Red();

		/**
		 * The meta object literal for the '<em><b>Green</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CUSTOM_COLOUR__GREEN = eINSTANCE.getCustomColour_Green();

		/**
		 * The meta object literal for the '<em><b>Blue</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CUSTOM_COLOUR__BLUE = eINSTANCE.getCustomColour_Blue();

		/**
		 * The meta object literal for the
		 * '{@link safecap.extension.impl.CustomShapeImpl <em>Custom Shape</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.extension.impl.CustomShapeImpl
		 * @see safecap.extension.impl.ExtensionPackageImpl#getCustomShape()
		 * @generated
		 */
		EClass CUSTOM_SHAPE = eINSTANCE.getCustomShape();

		/**
		 * The meta object literal for the '<em><b>Shape</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CUSTOM_SHAPE__SHAPE = eINSTANCE.getCustomShape_Shape();

		/**
		 * The meta object literal for the '{@link safecap.extension.impl.ExtVisualImpl
		 * <em>Ext Visual</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.extension.impl.ExtVisualImpl
		 * @see safecap.extension.impl.ExtensionPackageImpl#getExtVisual()
		 * @generated
		 */
		EClass EXT_VISUAL = eINSTANCE.getExtVisual();

		/**
		 * The meta object literal for the '<em><b>Figures</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXT_VISUAL__FIGURES = eINSTANCE.getExtVisual_Figures();

		/**
		 * The meta object literal for the
		 * '{@link safecap.extension.impl.ExtEquipmentImpl <em>Ext Equipment</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.extension.impl.ExtEquipmentImpl
		 * @see safecap.extension.impl.ExtensionPackageImpl#getExtEquipment()
		 * @generated
		 */
		EClass EXT_EQUIPMENT = eINSTANCE.getExtEquipment();

		/**
		 * The meta object literal for the '<em><b>Visual</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXT_EQUIPMENT__VISUAL = eINSTANCE.getExtEquipment_Visual();

	}

} // ExtensionPackage
