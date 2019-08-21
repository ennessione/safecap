/**
 */
package safecap.extension.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import safecap.SafecapPackage;
import safecap.commentary.CommentaryPackage;
import safecap.commentary.impl.CommentaryPackageImpl;
import safecap.config.ConfigPackage;
import safecap.config.impl.ConfigPackageImpl;
import safecap.derived.DerivedPackage;
import safecap.derived.impl.DerivedPackageImpl;
import safecap.extension.CustomColour;
import safecap.extension.CustomFigure;
import safecap.extension.CustomLabel;
import safecap.extension.CustomShape;
import safecap.extension.ExtAttribute;
import safecap.extension.ExtAttributeB;
import safecap.extension.ExtAttributeDbl;
import safecap.extension.ExtAttributeInt;
import safecap.extension.ExtAttributeStr;
import safecap.extension.ExtEquipment;
import safecap.extension.ExtVisual;
import safecap.extension.ExtensionFactory;
import safecap.extension.ExtensionPackage;
import safecap.extension.Point;
import safecap.impl.SafecapPackageImpl;
import safecap.model.ModelPackage;
import safecap.model.impl.ModelPackageImpl;
import safecap.schema.SchemaPackage;
import safecap.schema.impl.SchemaPackageImpl;
import safecap.trackside.TracksidePackage;
import safecap.trackside.impl.TracksidePackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class ExtensionPackageImpl extends EPackageImpl implements ExtensionPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass extAttributeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass extAttributeIntEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass extAttributeDblEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass extAttributeStrEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass extAttributeBEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass customFigureEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass customLabelEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass pointEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass customColourEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass customShapeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass extVisualEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass extEquipmentEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method
	 * {@link #init init()}, which also performs initialization of the package, or
	 * returns the registered package, if one already exists. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see safecap.extension.ExtensionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ExtensionPackageImpl() {
		super(eNS_URI, ExtensionFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and
	 * for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link ExtensionPackage#eINSTANCE} when
	 * that field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ExtensionPackage init() {
		if (isInited) {
			return (ExtensionPackage) EPackage.Registry.INSTANCE.getEPackage(ExtensionPackage.eNS_URI);
		}

		// Obtain or create and register package
		final Object registeredExtensionPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		final ExtensionPackageImpl theExtensionPackage = registeredExtensionPackage instanceof ExtensionPackageImpl
				? (ExtensionPackageImpl) registeredExtensionPackage
				: new ExtensionPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(SafecapPackage.eNS_URI);
		final SafecapPackageImpl theSafecapPackage = (SafecapPackageImpl) (registeredPackage instanceof SafecapPackageImpl
				? registeredPackage
				: SafecapPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(SchemaPackage.eNS_URI);
		final SchemaPackageImpl theSchemaPackage = (SchemaPackageImpl) (registeredPackage instanceof SchemaPackageImpl ? registeredPackage
				: SchemaPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);
		final ModelPackageImpl theModelPackage = (ModelPackageImpl) (registeredPackage instanceof ModelPackageImpl ? registeredPackage
				: ModelPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(TracksidePackage.eNS_URI);
		final TracksidePackageImpl theTracksidePackage = (TracksidePackageImpl) (registeredPackage instanceof TracksidePackageImpl
				? registeredPackage
				: TracksidePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(CommentaryPackage.eNS_URI);
		final CommentaryPackageImpl theCommentaryPackage = (CommentaryPackageImpl) (registeredPackage instanceof CommentaryPackageImpl
				? registeredPackage
				: CommentaryPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DerivedPackage.eNS_URI);
		final DerivedPackageImpl theDerivedPackage = (DerivedPackageImpl) (registeredPackage instanceof DerivedPackageImpl
				? registeredPackage
				: DerivedPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI);
		final ConfigPackageImpl theConfigPackage = (ConfigPackageImpl) (registeredPackage instanceof ConfigPackageImpl ? registeredPackage
				: ConfigPackage.eINSTANCE);

		// Create package meta-data objects
		theExtensionPackage.createPackageContents();
		theSafecapPackage.createPackageContents();
		theSchemaPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theTracksidePackage.createPackageContents();
		theCommentaryPackage.createPackageContents();
		theDerivedPackage.createPackageContents();
		theConfigPackage.createPackageContents();

		// Initialize created meta-data
		theExtensionPackage.initializePackageContents();
		theSafecapPackage.initializePackageContents();
		theSchemaPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theTracksidePackage.initializePackageContents();
		theCommentaryPackage.initializePackageContents();
		theDerivedPackage.initializePackageContents();
		theConfigPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theExtensionPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ExtensionPackage.eNS_URI, theExtensionPackage);
		return theExtensionPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExtAttribute() {
		return extAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExtAttribute_Domain() {
		return (EAttribute) extAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExtAttribute_Label() {
		return (EAttribute) extAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExtAttributeInt() {
		return extAttributeIntEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExtAttributeInt_Value() {
		return (EAttribute) extAttributeIntEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExtAttributeDbl() {
		return extAttributeDblEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExtAttributeDbl_Value() {
		return (EAttribute) extAttributeDblEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExtAttributeStr() {
		return extAttributeStrEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExtAttributeStr_Value() {
		return (EAttribute) extAttributeStrEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExtAttributeB() {
		return extAttributeBEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExtAttributeB_Value() {
		return (EAttribute) extAttributeBEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCustomFigure() {
		return customFigureEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCustomFigure_Filled() {
		return (EAttribute) customFigureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCustomFigure_Foreground() {
		return (EReference) customFigureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCustomFigure_Background() {
		return (EReference) customFigureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCustomLabel() {
		return customLabelEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCustomLabel_Text() {
		return (EAttribute) customLabelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCustomLabel_Fontsize() {
		return (EAttribute) customLabelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getPoint() {
		return pointEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPoint_X() {
		return (EAttribute) pointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPoint_Y() {
		return (EAttribute) pointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCustomColour() {
		return customColourEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCustomColour_Red() {
		return (EAttribute) customColourEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCustomColour_Green() {
		return (EAttribute) customColourEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCustomColour_Blue() {
		return (EAttribute) customColourEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCustomShape() {
		return customShapeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCustomShape_Shape() {
		return (EReference) customShapeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExtVisual() {
		return extVisualEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getExtVisual_Figures() {
		return (EReference) extVisualEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExtEquipment() {
		return extEquipmentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getExtEquipment_Visual() {
		return (EReference) extEquipmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ExtensionFactory getExtensionFactory() {
		return (ExtensionFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to
	 * have no affect on any invocation but its first. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		extAttributeEClass = createEClass(EXT_ATTRIBUTE);
		createEAttribute(extAttributeEClass, EXT_ATTRIBUTE__DOMAIN);
		createEAttribute(extAttributeEClass, EXT_ATTRIBUTE__LABEL);

		extAttributeIntEClass = createEClass(EXT_ATTRIBUTE_INT);
		createEAttribute(extAttributeIntEClass, EXT_ATTRIBUTE_INT__VALUE);

		extAttributeDblEClass = createEClass(EXT_ATTRIBUTE_DBL);
		createEAttribute(extAttributeDblEClass, EXT_ATTRIBUTE_DBL__VALUE);

		extAttributeStrEClass = createEClass(EXT_ATTRIBUTE_STR);
		createEAttribute(extAttributeStrEClass, EXT_ATTRIBUTE_STR__VALUE);

		extAttributeBEClass = createEClass(EXT_ATTRIBUTE_B);
		createEAttribute(extAttributeBEClass, EXT_ATTRIBUTE_B__VALUE);

		customFigureEClass = createEClass(CUSTOM_FIGURE);
		createEAttribute(customFigureEClass, CUSTOM_FIGURE__FILLED);
		createEReference(customFigureEClass, CUSTOM_FIGURE__FOREGROUND);
		createEReference(customFigureEClass, CUSTOM_FIGURE__BACKGROUND);

		customLabelEClass = createEClass(CUSTOM_LABEL);
		createEAttribute(customLabelEClass, CUSTOM_LABEL__TEXT);
		createEAttribute(customLabelEClass, CUSTOM_LABEL__FONTSIZE);

		pointEClass = createEClass(POINT);
		createEAttribute(pointEClass, POINT__X);
		createEAttribute(pointEClass, POINT__Y);

		customColourEClass = createEClass(CUSTOM_COLOUR);
		createEAttribute(customColourEClass, CUSTOM_COLOUR__RED);
		createEAttribute(customColourEClass, CUSTOM_COLOUR__GREEN);
		createEAttribute(customColourEClass, CUSTOM_COLOUR__BLUE);

		customShapeEClass = createEClass(CUSTOM_SHAPE);
		createEReference(customShapeEClass, CUSTOM_SHAPE__SHAPE);

		extVisualEClass = createEClass(EXT_VISUAL);
		createEReference(extVisualEClass, EXT_VISUAL__FIGURES);

		extEquipmentEClass = createEClass(EXT_EQUIPMENT);
		createEReference(extEquipmentEClass, EXT_EQUIPMENT__VISUAL);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is
	 * guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		final TracksidePackage theTracksidePackage = (TracksidePackage) EPackage.Registry.INSTANCE.getEPackage(TracksidePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		extAttributeIntEClass.getESuperTypes().add(getExtAttribute());
		extAttributeDblEClass.getESuperTypes().add(getExtAttribute());
		extAttributeStrEClass.getESuperTypes().add(getExtAttribute());
		extAttributeBEClass.getESuperTypes().add(getExtAttribute());
		customLabelEClass.getESuperTypes().add(getCustomFigure());
		customShapeEClass.getESuperTypes().add(getCustomFigure());
		extEquipmentEClass.getESuperTypes().add(theTracksidePackage.getEquipment());

		// Initialize classes and features; add operations and parameters
		initEClass(extAttributeEClass, ExtAttribute.class, "ExtAttribute", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExtAttribute_Domain(), ecorePackage.getEString(), "domain", null, 0, 1, ExtAttribute.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtAttribute_Label(), ecorePackage.getEString(), "label", null, 0, 1, ExtAttribute.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extAttributeIntEClass, ExtAttributeInt.class, "ExtAttributeInt", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExtAttributeInt_Value(), ecorePackage.getEInt(), "value", null, 0, 1, ExtAttributeInt.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extAttributeDblEClass, ExtAttributeDbl.class, "ExtAttributeDbl", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExtAttributeDbl_Value(), ecorePackage.getEDouble(), "value", null, 0, 1, ExtAttributeDbl.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extAttributeStrEClass, ExtAttributeStr.class, "ExtAttributeStr", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExtAttributeStr_Value(), ecorePackage.getEString(), "value", null, 0, 1, ExtAttributeStr.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extAttributeBEClass, ExtAttributeB.class, "ExtAttributeB", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExtAttributeB_Value(), ecorePackage.getEBoolean(), "value", null, 0, 1, ExtAttributeB.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(customFigureEClass, CustomFigure.class, "CustomFigure", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCustomFigure_Filled(), ecorePackage.getEBoolean(), "filled", null, 0, 1, CustomFigure.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCustomFigure_Foreground(), getCustomColour(), null, "foreground", null, 0, 1, CustomFigure.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCustomFigure_Background(), getCustomColour(), null, "background", null, 0, 1, CustomFigure.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(customLabelEClass, CustomLabel.class, "CustomLabel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCustomLabel_Text(), ecorePackage.getEString(), "text", null, 0, 1, CustomLabel.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCustomLabel_Fontsize(), ecorePackage.getEInt(), "fontsize", null, 0, 1, CustomLabel.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pointEClass, Point.class, "Point", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPoint_X(), ecorePackage.getEInt(), "x", null, 0, 1, Point.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPoint_Y(), ecorePackage.getEInt(), "y", null, 0, 1, Point.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(customColourEClass, CustomColour.class, "CustomColour", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCustomColour_Red(), ecorePackage.getEInt(), "red", null, 0, 1, CustomColour.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCustomColour_Green(), ecorePackage.getEInt(), "green", null, 0, 1, CustomColour.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCustomColour_Blue(), ecorePackage.getEInt(), "blue", null, 0, 1, CustomColour.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(customShapeEClass, CustomShape.class, "CustomShape", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCustomShape_Shape(), getPoint(), null, "shape", null, 0, -1, CustomShape.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extVisualEClass, ExtVisual.class, "ExtVisual", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExtVisual_Figures(), getCustomFigure(), null, "figures", null, 0, -1, ExtVisual.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extEquipmentEClass, ExtEquipment.class, "ExtEquipment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExtEquipment_Visual(), getExtVisual(), null, "visual", null, 0, 1, ExtEquipment.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} // ExtensionPackageImpl
