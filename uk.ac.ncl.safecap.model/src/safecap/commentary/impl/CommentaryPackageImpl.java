/**
 */
package safecap.commentary.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import safecap.SafecapPackage;
import safecap.commentary.Bridge;
import safecap.commentary.Commentary;
import safecap.commentary.CommentaryFactory;
import safecap.commentary.CommentaryPackage;
import safecap.commentary.Comments;
import safecap.commentary.OrientableCommentary;
import safecap.commentary.Road;
import safecap.commentary.Station;
import safecap.commentary.Tunnel;
import safecap.config.ConfigPackage;
import safecap.config.impl.ConfigPackageImpl;
import safecap.derived.DerivedPackage;
import safecap.derived.impl.DerivedPackageImpl;
import safecap.extension.ExtensionPackage;
import safecap.extension.impl.ExtensionPackageImpl;
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
public class CommentaryPackageImpl extends EPackageImpl implements CommentaryPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass commentaryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass orientableCommentaryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass stationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass bridgeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass roadEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tunnelEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass commentsEClass = null;

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
	 * @see safecap.commentary.CommentaryPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CommentaryPackageImpl() {
		super(eNS_URI, CommentaryFactory.eINSTANCE);
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
	 * This method is used to initialize {@link CommentaryPackage#eINSTANCE} when
	 * that field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CommentaryPackage init() {
		if (isInited) {
			return (CommentaryPackage) EPackage.Registry.INSTANCE.getEPackage(CommentaryPackage.eNS_URI);
		}

		// Obtain or create and register package
		final Object registeredCommentaryPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		final CommentaryPackageImpl theCommentaryPackage = registeredCommentaryPackage instanceof CommentaryPackageImpl
				? (CommentaryPackageImpl) registeredCommentaryPackage
				: new CommentaryPackageImpl();

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
		theCommentaryPackage.createPackageContents();
		theSafecapPackage.createPackageContents();
		theSchemaPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theTracksidePackage.createPackageContents();
		theExtensionPackage.createPackageContents();
		theDerivedPackage.createPackageContents();
		theConfigPackage.createPackageContents();

		// Initialize created meta-data
		theCommentaryPackage.initializePackageContents();
		theSafecapPackage.initializePackageContents();
		theSchemaPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theTracksidePackage.initializePackageContents();
		theExtensionPackage.initializePackageContents();
		theDerivedPackage.initializePackageContents();
		theConfigPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCommentaryPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CommentaryPackage.eNS_URI, theCommentaryPackage);
		return theCommentaryPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCommentary() {
		return commentaryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getOrientableCommentary() {
		return orientableCommentaryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getOrientableCommentary_Angle() {
		return (EAttribute) orientableCommentaryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getStation() {
		return stationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getBridge() {
		return bridgeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRoad() {
		return roadEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTunnel() {
		return tunnelEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getComments() {
		return commentsEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getComments_Comments() {
		return (EReference) commentsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CommentaryFactory getCommentaryFactory() {
		return (CommentaryFactory) getEFactoryInstance();
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
		commentsEClass = createEClass(COMMENTS);
		createEReference(commentsEClass, COMMENTS__COMMENTS);

		commentaryEClass = createEClass(COMMENTARY);

		orientableCommentaryEClass = createEClass(ORIENTABLE_COMMENTARY);
		createEAttribute(orientableCommentaryEClass, ORIENTABLE_COMMENTARY__ANGLE);

		stationEClass = createEClass(STATION);

		bridgeEClass = createEClass(BRIDGE);

		roadEClass = createEClass(ROAD);

		tunnelEClass = createEClass(TUNNEL);
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
		final SafecapPackage theSafecapPackage = (SafecapPackage) EPackage.Registry.INSTANCE.getEPackage(SafecapPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		orientableCommentaryEClass.getESuperTypes().add(getCommentary());
		stationEClass.getESuperTypes().add(theSafecapPackage.getLabeled());
		stationEClass.getESuperTypes().add(getOrientableCommentary());
		bridgeEClass.getESuperTypes().add(getOrientableCommentary());
		bridgeEClass.getESuperTypes().add(theSafecapPackage.getLabeled());
		roadEClass.getESuperTypes().add(getOrientableCommentary());
		roadEClass.getESuperTypes().add(theSafecapPackage.getLabeled());
		tunnelEClass.getESuperTypes().add(getOrientableCommentary());
		tunnelEClass.getESuperTypes().add(theSafecapPackage.getLabeled());

		// Initialize classes and features; add operations and parameters
		initEClass(commentsEClass, Comments.class, "Comments", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComments_Comments(), getCommentary(), null, "comments", null, 0, -1, Comments.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commentaryEClass, Commentary.class, "Commentary", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(orientableCommentaryEClass, OrientableCommentary.class, "OrientableCommentary", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOrientableCommentary_Angle(), ecorePackage.getEInt(), "angle", null, 0, 1, OrientableCommentary.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stationEClass, Station.class, "Station", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(bridgeEClass, Bridge.class, "Bridge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(roadEClass, Road.class, "Road", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(tunnelEClass, Tunnel.class, "Tunnel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
	}

} // CommentaryPackageImpl
