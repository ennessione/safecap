/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import safecap.Extensible;
import safecap.HighlightedInterval;
import safecap.Labeled;
import safecap.Orientation;
import safecap.Project;
import safecap.Provenance;
import safecap.SafecapFactory;
import safecap.SafecapPackage;
import safecap.Style;
import safecap.TransientValues;
import safecap.Visual;
import safecap.VisualMarker;
import safecap.commentary.CommentaryPackage;
import safecap.commentary.impl.CommentaryPackageImpl;
import safecap.config.ConfigPackage;
import safecap.config.impl.ConfigPackageImpl;
import safecap.derived.DerivedPackage;
import safecap.derived.impl.DerivedPackageImpl;
import safecap.extension.ExtensionPackage;
import safecap.extension.impl.ExtensionPackageImpl;
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
public class SafecapPackageImpl extends EPackageImpl implements SafecapPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass labeledEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass projectEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass visualEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass styleEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass provenanceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass extensibleEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass highlightedIntervalEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass visualMarkerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass transientValuesEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum orientationEEnum = null;

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
	 * @see safecap.SafecapPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SafecapPackageImpl() {
		super(eNS_URI, SafecapFactory.eINSTANCE);
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
	 * This method is used to initialize {@link SafecapPackage#eINSTANCE} when that
	 * field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SafecapPackage init() {
		if (isInited) {
			return (SafecapPackage) EPackage.Registry.INSTANCE.getEPackage(SafecapPackage.eNS_URI);
		}

		// Obtain or create and register package
		final Object registeredSafecapPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		final SafecapPackageImpl theSafecapPackage = registeredSafecapPackage instanceof SafecapPackageImpl
				? (SafecapPackageImpl) registeredSafecapPackage
				: new SafecapPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(SchemaPackage.eNS_URI);
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
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ExtensionPackage.eNS_URI);
		final ExtensionPackageImpl theExtensionPackage = (ExtensionPackageImpl) (registeredPackage instanceof ExtensionPackageImpl
				? registeredPackage
				: ExtensionPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DerivedPackage.eNS_URI);
		final DerivedPackageImpl theDerivedPackage = (DerivedPackageImpl) (registeredPackage instanceof DerivedPackageImpl
				? registeredPackage
				: DerivedPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI);
		final ConfigPackageImpl theConfigPackage = (ConfigPackageImpl) (registeredPackage instanceof ConfigPackageImpl ? registeredPackage
				: ConfigPackage.eINSTANCE);

		// Create package meta-data objects
		theSafecapPackage.createPackageContents();
		theSchemaPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theTracksidePackage.createPackageContents();
		theCommentaryPackage.createPackageContents();
		theExtensionPackage.createPackageContents();
		theDerivedPackage.createPackageContents();
		theConfigPackage.createPackageContents();

		// Initialize created meta-data
		theSafecapPackage.initializePackageContents();
		theSchemaPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theTracksidePackage.initializePackageContents();
		theCommentaryPackage.initializePackageContents();
		theExtensionPackage.initializePackageContents();
		theDerivedPackage.initializePackageContents();
		theConfigPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSafecapPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SafecapPackage.eNS_URI, theSafecapPackage);
		return theSafecapPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getLabeled() {
		return labeledEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getLabeled_Label() {
		return (EAttribute) labeledEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getProject() {
		return projectEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getProject_Configuration() {
		return (EReference) projectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getProject_Routebuilder() {
		return (EReference) projectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getProject_Darkmatter() {
		return (EReference) projectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getVisual() {
		return visualEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVisual_Visible() {
		return (EAttribute) visualEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getVisual_Style() {
		return (EReference) visualEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getVisual_Intervals() {
		return (EReference) visualEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getVisual_Markers() {
		return (EReference) visualEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getStyle() {
		return styleEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getStyle_Foreground() {
		return (EAttribute) styleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getStyle_Background() {
		return (EAttribute) styleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getStyle_Linewidth() {
		return (EAttribute) styleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getStyle_Linestyle() {
		return (EAttribute) styleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getProvenance() {
		return provenanceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getProvenance_Origin() {
		return (EAttribute) provenanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getProvenance_Author() {
		return (EAttribute) provenanceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getProvenance_Version() {
		return (EAttribute) provenanceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getProvenance_Date() {
		return (EAttribute) provenanceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExtensible() {
		return extensibleEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getExtensible_Attributes() {
		return (EReference) extensibleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getHighlightedInterval() {
		return highlightedIntervalEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getHighlightedInterval_Style() {
		return (EReference) highlightedIntervalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getHighlightedInterval_Label() {
		return (EAttribute) highlightedIntervalEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getHighlightedInterval_From() {
		return (EAttribute) highlightedIntervalEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getHighlightedInterval_To() {
		return (EAttribute) highlightedIntervalEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getHighlightedInterval_Owner() {
		return (EAttribute) highlightedIntervalEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getHighlightedInterval_Index() {
		return (EAttribute) highlightedIntervalEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getHighlightedInterval_Voffset() {
		return (EAttribute) highlightedIntervalEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getVisualMarker() {
		return visualMarkerEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getVisualMarker_Style() {
		return (EReference) visualMarkerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVisualMarker_Label() {
		return (EAttribute) visualMarkerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVisualMarker_Position() {
		return (EAttribute) visualMarkerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVisualMarker_Owner() {
		return (EAttribute) visualMarkerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVisualMarker_Index() {
		return (EAttribute) visualMarkerEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVisualMarker_OffsetLabel() {
		return (EAttribute) visualMarkerEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTransientValues() {
		return transientValuesEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTransientValues_RuntimeAttributes() {
		return (EAttribute) transientValuesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getOrientation() {
		return orientationEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SafecapFactory getSafecapFactory() {
		return (SafecapFactory) getEFactoryInstance();
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
		labeledEClass = createEClass(LABELED);
		createEAttribute(labeledEClass, LABELED__LABEL);

		projectEClass = createEClass(PROJECT);
		createEReference(projectEClass, PROJECT__CONFIGURATION);
		createEReference(projectEClass, PROJECT__ROUTEBUILDER);
		createEReference(projectEClass, PROJECT__DARKMATTER);

		visualEClass = createEClass(VISUAL);
		createEAttribute(visualEClass, VISUAL__VISIBLE);
		createEReference(visualEClass, VISUAL__STYLE);
		createEReference(visualEClass, VISUAL__INTERVALS);
		createEReference(visualEClass, VISUAL__MARKERS);

		styleEClass = createEClass(STYLE);
		createEAttribute(styleEClass, STYLE__FOREGROUND);
		createEAttribute(styleEClass, STYLE__BACKGROUND);
		createEAttribute(styleEClass, STYLE__LINEWIDTH);
		createEAttribute(styleEClass, STYLE__LINESTYLE);

		provenanceEClass = createEClass(PROVENANCE);
		createEAttribute(provenanceEClass, PROVENANCE__ORIGIN);
		createEAttribute(provenanceEClass, PROVENANCE__AUTHOR);
		createEAttribute(provenanceEClass, PROVENANCE__VERSION);
		createEAttribute(provenanceEClass, PROVENANCE__DATE);

		extensibleEClass = createEClass(EXTENSIBLE);
		createEReference(extensibleEClass, EXTENSIBLE__ATTRIBUTES);

		highlightedIntervalEClass = createEClass(HIGHLIGHTED_INTERVAL);
		createEReference(highlightedIntervalEClass, HIGHLIGHTED_INTERVAL__STYLE);
		createEAttribute(highlightedIntervalEClass, HIGHLIGHTED_INTERVAL__LABEL);
		createEAttribute(highlightedIntervalEClass, HIGHLIGHTED_INTERVAL__FROM);
		createEAttribute(highlightedIntervalEClass, HIGHLIGHTED_INTERVAL__TO);
		createEAttribute(highlightedIntervalEClass, HIGHLIGHTED_INTERVAL__OWNER);
		createEAttribute(highlightedIntervalEClass, HIGHLIGHTED_INTERVAL__INDEX);
		createEAttribute(highlightedIntervalEClass, HIGHLIGHTED_INTERVAL__VOFFSET);

		visualMarkerEClass = createEClass(VISUAL_MARKER);
		createEReference(visualMarkerEClass, VISUAL_MARKER__STYLE);
		createEAttribute(visualMarkerEClass, VISUAL_MARKER__LABEL);
		createEAttribute(visualMarkerEClass, VISUAL_MARKER__POSITION);
		createEAttribute(visualMarkerEClass, VISUAL_MARKER__OWNER);
		createEAttribute(visualMarkerEClass, VISUAL_MARKER__INDEX);
		createEAttribute(visualMarkerEClass, VISUAL_MARKER__OFFSET_LABEL);

		transientValuesEClass = createEClass(TRANSIENT_VALUES);
		createEAttribute(transientValuesEClass, TRANSIENT_VALUES__RUNTIME_ATTRIBUTES);

		// Create enums
		orientationEEnum = createEEnum(ORIENTATION);
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
		final SchemaPackage theSchemaPackage = (SchemaPackage) EPackage.Registry.INSTANCE.getEPackage(SchemaPackage.eNS_URI);
		final ModelPackage theModelPackage = (ModelPackage) EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);
		final TracksidePackage theTracksidePackage = (TracksidePackage) EPackage.Registry.INSTANCE.getEPackage(TracksidePackage.eNS_URI);
		final CommentaryPackage theCommentaryPackage = (CommentaryPackage) EPackage.Registry.INSTANCE
				.getEPackage(CommentaryPackage.eNS_URI);
		final ExtensionPackage theExtensionPackage = (ExtensionPackage) EPackage.Registry.INSTANCE.getEPackage(ExtensionPackage.eNS_URI);
		final DerivedPackage theDerivedPackage = (DerivedPackage) EPackage.Registry.INSTANCE.getEPackage(DerivedPackage.eNS_URI);
		final ConfigPackage theConfigPackage = (ConfigPackage) EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theSchemaPackage);
		getESubpackages().add(theModelPackage);
		getESubpackages().add(theTracksidePackage);
		getESubpackages().add(theCommentaryPackage);
		getESubpackages().add(theExtensionPackage);
		getESubpackages().add(theDerivedPackage);
		getESubpackages().add(theConfigPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		projectEClass.getESuperTypes().add(getLabeled());
		projectEClass.getESuperTypes().add(theSchemaPackage.getSchema());
		projectEClass.getESuperTypes().add(theModelPackage.getModel());
		projectEClass.getESuperTypes().add(theTracksidePackage.getTrackside());
		projectEClass.getESuperTypes().add(theCommentaryPackage.getComments());
		projectEClass.getESuperTypes().add(getExtensible());
		projectEClass.getESuperTypes().add(theDerivedPackage.getDerived());
		projectEClass.getESuperTypes().add(getProvenance());
		extensibleEClass.getESuperTypes().add(getTransientValues());

		// Initialize classes and features; add operations and parameters
		initEClass(labeledEClass, Labeled.class, "Labeled", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLabeled_Label(), ecorePackage.getEString(), "label", null, 0, 1, Labeled.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(projectEClass, Project.class, "Project", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProject_Configuration(), theConfigPackage.getSchemaConfiguration(), null, "configuration", null, 0, 1,
				Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Routebuilder(), theConfigPackage.getRouteBuilderConfiguration(), null, "routebuilder", null, 0, 1,
				Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Darkmatter(), getLabeled(), null, "darkmatter", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(visualEClass, Visual.class, "Visual", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVisual_Visible(), ecorePackage.getEBoolean(), "visible", "true", 0, 1, Visual.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVisual_Style(), getStyle(), null, "style", null, 0, 1, Visual.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVisual_Intervals(), getHighlightedInterval(), null, "intervals", null, 0, -1, Visual.class, IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVisual_Markers(), getVisualMarker(), null, "markers", null, 0, -1, Visual.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(styleEClass, Style.class, "Style", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStyle_Foreground(), ecorePackage.getEJavaObject(), "foreground", null, 0, 1, Style.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStyle_Background(), ecorePackage.getEJavaObject(), "background", null, 0, 1, Style.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStyle_Linewidth(), ecorePackage.getEInt(), "linewidth", null, 0, 1, Style.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStyle_Linestyle(), ecorePackage.getEInt(), "linestyle", null, 0, 1, Style.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(provenanceEClass, Provenance.class, "Provenance", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProvenance_Origin(), ecorePackage.getEString(), "origin", null, 0, 1, Provenance.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProvenance_Author(), ecorePackage.getEString(), "author", null, 0, 1, Provenance.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProvenance_Version(), ecorePackage.getEString(), "version", null, 0, 1, Provenance.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProvenance_Date(), ecorePackage.getEDate(), "date", null, 0, 1, Provenance.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extensibleEClass, Extensible.class, "Extensible", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExtensible_Attributes(), theExtensionPackage.getExtAttribute(), null, "attributes", null, 0, -1, Extensible.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(highlightedIntervalEClass, HighlightedInterval.class, "HighlightedInterval", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHighlightedInterval_Style(), getStyle(), null, "style", null, 0, 1, HighlightedInterval.class, IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHighlightedInterval_Label(), ecorePackage.getEString(), "label", null, 0, 1, HighlightedInterval.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHighlightedInterval_From(), ecorePackage.getEDouble(), "from", null, 0, 1, HighlightedInterval.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHighlightedInterval_To(), ecorePackage.getEDouble(), "to", null, 0, 1, HighlightedInterval.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHighlightedInterval_Owner(), ecorePackage.getEJavaObject(), "owner", null, 0, 1, HighlightedInterval.class,
				IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHighlightedInterval_Index(), ecorePackage.getEInt(), "index", null, 0, 1, HighlightedInterval.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHighlightedInterval_Voffset(), ecorePackage.getEInt(), "voffset", null, 0, 1, HighlightedInterval.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(visualMarkerEClass, VisualMarker.class, "VisualMarker", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVisualMarker_Style(), getStyle(), null, "style", null, 0, 1, VisualMarker.class, IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVisualMarker_Label(), ecorePackage.getEString(), "label", null, 0, 1, VisualMarker.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVisualMarker_Position(), ecorePackage.getEDouble(), "position", null, 0, 1, VisualMarker.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVisualMarker_Owner(), ecorePackage.getEJavaObject(), "owner", null, 0, 1, VisualMarker.class, IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVisualMarker_Index(), ecorePackage.getEInt(), "index", null, 0, 1, VisualMarker.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVisualMarker_OffsetLabel(), ecorePackage.getEString(), "offsetLabel", null, 0, 1, VisualMarker.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(transientValuesEClass, TransientValues.class, "TransientValues", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		final EGenericType g1 = createEGenericType(ecorePackage.getEMap());
		EGenericType g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		initEAttribute(getTransientValues_RuntimeAttributes(), g1, "runtimeAttributes", null, 0, 1, TransientValues.class, IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(orientationEEnum, Orientation.class, "Orientation");
		addEEnumLiteral(orientationEEnum, Orientation.LEFT);
		addEEnumLiteral(orientationEEnum, Orientation.RIGHT);
		addEEnumLiteral(orientationEEnum, Orientation.ANY);

		// Create resource
		createResource(eNS_URI);
	}

} // SafecapPackageImpl
