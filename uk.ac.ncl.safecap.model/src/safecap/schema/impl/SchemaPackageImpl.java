/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
import safecap.extension.ExtensionPackage;
import safecap.extension.impl.ExtensionPackageImpl;
import safecap.impl.SafecapPackageImpl;
import safecap.model.ModelPackage;
import safecap.model.impl.ModelPackageImpl;
import safecap.schema.HeightMap;
import safecap.schema.HeightPoint;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.NodeRole;
import safecap.schema.PointRole;
import safecap.schema.RouteSpeed;
import safecap.schema.Schema;
import safecap.schema.SchemaFactory;
import safecap.schema.SchemaPackage;
import safecap.schema.Segment;
import safecap.schema.SegmentRole;
import safecap.schema.SubSchemaNode;
import safecap.schema.SubSchemaPath;
import safecap.schema.TerminalNode;
import safecap.trackside.TracksidePackage;
import safecap.trackside.impl.TracksidePackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class SchemaPackageImpl extends EPackageImpl implements SchemaPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass schemaEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass segmentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass nodeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass terminalNodeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass heightMapEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass heightPointEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass routeSpeedEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass subSchemaNodeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass subSchemaPathEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum nodeKindEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum nodeRoleEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum segmentRoleEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum pointRoleEEnum = null;

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
	 * @see safecap.schema.SchemaPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SchemaPackageImpl() {
		super(eNS_URI, SchemaFactory.eINSTANCE);
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
	 * This method is used to initialize {@link SchemaPackage#eINSTANCE} when that
	 * field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SchemaPackage init() {
		if (isInited) {
			return (SchemaPackage) EPackage.Registry.INSTANCE.getEPackage(SchemaPackage.eNS_URI);
		}

		// Obtain or create and register package
		final Object registeredSchemaPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		final SchemaPackageImpl theSchemaPackage = registeredSchemaPackage instanceof SchemaPackageImpl
				? (SchemaPackageImpl) registeredSchemaPackage
				: new SchemaPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(SafecapPackage.eNS_URI);
		final SafecapPackageImpl theSafecapPackage = (SafecapPackageImpl) (registeredPackage instanceof SafecapPackageImpl
				? registeredPackage
				: SafecapPackage.eINSTANCE);
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
		theSchemaPackage.createPackageContents();
		theSafecapPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theTracksidePackage.createPackageContents();
		theCommentaryPackage.createPackageContents();
		theExtensionPackage.createPackageContents();
		theDerivedPackage.createPackageContents();
		theConfigPackage.createPackageContents();

		// Initialize created meta-data
		theSchemaPackage.initializePackageContents();
		theSafecapPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theTracksidePackage.initializePackageContents();
		theCommentaryPackage.initializePackageContents();
		theExtensionPackage.initializePackageContents();
		theDerivedPackage.initializePackageContents();
		theConfigPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSchemaPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SchemaPackage.eNS_URI, theSchemaPackage);
		return theSchemaPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSchema() {
		return schemaEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSchema_Nodes() {
		return (EReference) schemaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSchema_Segments() {
		return (EReference) schemaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSegment() {
		return segmentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSegment_From() {
		return (EReference) segmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSegment_To() {
		return (EReference) segmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSegment_Heightmap() {
		return (EReference) segmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSegment_Role() {
		return (EAttribute) segmentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSegment_Pointrole() {
		return (EAttribute) segmentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSegment_Length() {
		return (EAttribute) segmentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSegment_Speed() {
		return (EReference) segmentEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSegment_Gradient() {
		return (EAttribute) segmentEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSegment_Speedlimit() {
		return (EAttribute) segmentEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSegment_Orientation() {
		return (EAttribute) segmentEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getNode() {
		return nodeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getNode_Altitude() {
		return (EAttribute) nodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getNode_Kind() {
		return (EAttribute) nodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getNode_Roles() {
		return (EAttribute) nodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getNode_Speedlimit() {
		return (EAttribute) nodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTerminalNode() {
		return terminalNodeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getHeightMap() {
		return heightMapEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getHeightMap_Points() {
		return (EReference) heightMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getHeightPoint() {
		return heightPointEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getHeightPoint_Altitude() {
		return (EAttribute) heightPointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getHeightPoint_Position() {
		return (EAttribute) heightPointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRouteSpeed() {
		return routeSpeedEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRouteSpeed_Route() {
		return (EReference) routeSpeedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getRouteSpeed_Speed() {
		return (EAttribute) routeSpeedEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSubSchemaNode() {
		return subSchemaNodeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSubSchemaNode_Paths() {
		return (EReference) subSchemaNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSubSchemaNode_Children() {
		return (EAttribute) subSchemaNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSubSchemaPath() {
		return subSchemaPathEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSubSchemaPath_From() {
		return (EAttribute) subSchemaPathEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSubSchemaPath_To() {
		return (EAttribute) subSchemaPathEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSubSchemaPath_Pnormal() {
		return (EAttribute) subSchemaPathEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSubSchemaPath_Preverse() {
		return (EAttribute) subSchemaPathEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSubSchemaPath_Length() {
		return (EAttribute) subSchemaPathEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getNodeKind() {
		return nodeKindEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getNodeRole() {
		return nodeRoleEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getSegmentRole() {
		return segmentRoleEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getPointRole() {
		return pointRoleEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SchemaFactory getSchemaFactory() {
		return (SchemaFactory) getEFactoryInstance();
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
		schemaEClass = createEClass(SCHEMA);
		createEReference(schemaEClass, SCHEMA__NODES);
		createEReference(schemaEClass, SCHEMA__SEGMENTS);

		segmentEClass = createEClass(SEGMENT);
		createEReference(segmentEClass, SEGMENT__FROM);
		createEReference(segmentEClass, SEGMENT__TO);
		createEReference(segmentEClass, SEGMENT__HEIGHTMAP);
		createEAttribute(segmentEClass, SEGMENT__ROLE);
		createEAttribute(segmentEClass, SEGMENT__POINTROLE);
		createEAttribute(segmentEClass, SEGMENT__LENGTH);
		createEReference(segmentEClass, SEGMENT__SPEED);
		createEAttribute(segmentEClass, SEGMENT__GRADIENT);
		createEAttribute(segmentEClass, SEGMENT__SPEEDLIMIT);
		createEAttribute(segmentEClass, SEGMENT__ORIENTATION);

		nodeEClass = createEClass(NODE);
		createEAttribute(nodeEClass, NODE__ALTITUDE);
		createEAttribute(nodeEClass, NODE__KIND);
		createEAttribute(nodeEClass, NODE__ROLES);
		createEAttribute(nodeEClass, NODE__SPEEDLIMIT);

		terminalNodeEClass = createEClass(TERMINAL_NODE);

		heightMapEClass = createEClass(HEIGHT_MAP);
		createEReference(heightMapEClass, HEIGHT_MAP__POINTS);

		heightPointEClass = createEClass(HEIGHT_POINT);
		createEAttribute(heightPointEClass, HEIGHT_POINT__ALTITUDE);
		createEAttribute(heightPointEClass, HEIGHT_POINT__POSITION);

		routeSpeedEClass = createEClass(ROUTE_SPEED);
		createEReference(routeSpeedEClass, ROUTE_SPEED__ROUTE);
		createEAttribute(routeSpeedEClass, ROUTE_SPEED__SPEED);

		subSchemaNodeEClass = createEClass(SUB_SCHEMA_NODE);
		createEReference(subSchemaNodeEClass, SUB_SCHEMA_NODE__PATHS);
		createEAttribute(subSchemaNodeEClass, SUB_SCHEMA_NODE__CHILDREN);

		subSchemaPathEClass = createEClass(SUB_SCHEMA_PATH);
		createEAttribute(subSchemaPathEClass, SUB_SCHEMA_PATH__LENGTH);
		createEAttribute(subSchemaPathEClass, SUB_SCHEMA_PATH__FROM);
		createEAttribute(subSchemaPathEClass, SUB_SCHEMA_PATH__TO);
		createEAttribute(subSchemaPathEClass, SUB_SCHEMA_PATH__PNORMAL);
		createEAttribute(subSchemaPathEClass, SUB_SCHEMA_PATH__PREVERSE);

		// Create enums
		nodeKindEEnum = createEEnum(NODE_KIND);
		nodeRoleEEnum = createEEnum(NODE_ROLE);
		segmentRoleEEnum = createEEnum(SEGMENT_ROLE);
		pointRoleEEnum = createEEnum(POINT_ROLE);
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
		final ModelPackage theModelPackage = (ModelPackage) EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		segmentEClass.getESuperTypes().add(theSafecapPackage.getLabeled());
		segmentEClass.getESuperTypes().add(theSafecapPackage.getVisual());
		segmentEClass.getESuperTypes().add(theSafecapPackage.getExtensible());
		nodeEClass.getESuperTypes().add(theSafecapPackage.getLabeled());
		nodeEClass.getESuperTypes().add(theSafecapPackage.getVisual());
		nodeEClass.getESuperTypes().add(theSafecapPackage.getExtensible());
		terminalNodeEClass.getESuperTypes().add(getNode());
		subSchemaNodeEClass.getESuperTypes().add(getNode());

		// Initialize classes and features; add operations and parameters
		initEClass(schemaEClass, Schema.class, "Schema", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSchema_Nodes(), getNode(), null, "nodes", null, 0, -1, Schema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSchema_Segments(), getSegment(), null, "segments", null, 0, -1, Schema.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(segmentEClass, Segment.class, "Segment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSegment_From(), getNode(), null, "from", null, 0, 1, Segment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSegment_To(), getNode(), null, "to", null, 0, 1, Segment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSegment_Heightmap(), getHeightMap(), null, "heightmap", null, 0, 1, Segment.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSegment_Role(), ecorePackage.getEInt(), "role", null, 0, 1, Segment.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSegment_Pointrole(), getPointRole(), "pointrole", null, 0, 1, Segment.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSegment_Length(), ecorePackage.getEInt(), "length", "500", 0, 1, Segment.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSegment_Speed(), getRouteSpeed(), null, "speed", null, 0, -1, Segment.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSegment_Gradient(), ecorePackage.getEString(), "gradient", "", 0, 1, Segment.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSegment_Speedlimit(), ecorePackage.getEString(), "speedlimit", "", 0, 1, Segment.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSegment_Orientation(), theSafecapPackage.getOrientation(), "orientation", "Any", 0, 1, Segment.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nodeEClass, Node.class, "Node", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNode_Altitude(), ecorePackage.getEInt(), "altitude", null, 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNode_Kind(), getNodeKind(), "kind", "Normal", 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNode_Roles(), ecorePackage.getEInt(), "roles", null, 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNode_Speedlimit(), ecorePackage.getEDouble(), "speedlimit", null, 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(terminalNodeEClass, TerminalNode.class, "TerminalNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(heightMapEClass, HeightMap.class, "HeightMap", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHeightMap_Points(), getHeightPoint(), null, "points", null, 0, -1, HeightMap.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(heightPointEClass, HeightPoint.class, "HeightPoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHeightPoint_Altitude(), ecorePackage.getEInt(), "altitude", null, 0, 1, HeightPoint.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHeightPoint_Position(), ecorePackage.getEInt(), "position", null, 0, 1, HeightPoint.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(routeSpeedEClass, RouteSpeed.class, "RouteSpeed", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRouteSpeed_Route(), theModelPackage.getRoute(), null, "route", null, 0, 1, RouteSpeed.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRouteSpeed_Speed(), ecorePackage.getEDouble(), "speed", null, 0, 1, RouteSpeed.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subSchemaNodeEClass, SubSchemaNode.class, "SubSchemaNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSubSchemaNode_Paths(), getSubSchemaPath(), null, "paths", null, 0, -1, SubSchemaNode.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubSchemaNode_Children(), ecorePackage.getEString(), "children", null, 0, -1, SubSchemaNode.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subSchemaPathEClass, SubSchemaPath.class, "SubSchemaPath", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSubSchemaPath_Length(), ecorePackage.getEInt(), "length", "0", 0, 1, SubSchemaPath.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubSchemaPath_From(), ecorePackage.getEString(), "from", null, 0, 1, SubSchemaPath.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubSchemaPath_To(), ecorePackage.getEString(), "to", null, 0, 1, SubSchemaPath.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubSchemaPath_Pnormal(), ecorePackage.getEInt(), "pnormal", null, 0, 1, SubSchemaPath.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubSchemaPath_Preverse(), ecorePackage.getEInt(), "preverse", null, 0, 1, SubSchemaPath.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(nodeKindEEnum, NodeKind.class, "NodeKind");
		addEEnumLiteral(nodeKindEEnum, NodeKind.SILENT);
		addEEnumLiteral(nodeKindEEnum, NodeKind.NORMAL);
		addEEnumLiteral(nodeKindEEnum, NodeKind.BOUNDARY);
		addEEnumLiteral(nodeKindEEnum, NodeKind.TERMINAL);
		addEEnumLiteral(nodeKindEEnum, NodeKind.CROSSOVER);
		addEEnumLiteral(nodeKindEEnum, NodeKind.POINT);
		addEEnumLiteral(nodeKindEEnum, NodeKind.SWITCHED_CROSSOVER);
		addEEnumLiteral(nodeKindEEnum, NodeKind.INVALID);

		initEEnum(nodeRoleEEnum, NodeRole.class, "NodeRole");
		addEEnumLiteral(nodeRoleEEnum, NodeRole.NONE);
		addEEnumLiteral(nodeRoleEEnum, NodeRole.LEFT_OVERLAP);
		addEEnumLiteral(nodeRoleEEnum, NodeRole.RIGHT_OVERLAP);
		addEEnumLiteral(nodeRoleEEnum, NodeRole.LEFT_POINT);
		addEEnumLiteral(nodeRoleEEnum, NodeRole.SOURCE);
		addEEnumLiteral(nodeRoleEEnum, NodeRole.SINK);

		initEEnum(segmentRoleEEnum, SegmentRole.class, "SegmentRole");
		addEEnumLiteral(segmentRoleEEnum, SegmentRole.NONE);
		addEEnumLiteral(segmentRoleEEnum, SegmentRole.JUNCTION);
		addEEnumLiteral(segmentRoleEEnum, SegmentRole.CROSS_A);
		addEEnumLiteral(segmentRoleEEnum, SegmentRole.CROSS_B);

		initEEnum(pointRoleEEnum, PointRole.class, "PointRole");
		addEEnumLiteral(pointRoleEEnum, PointRole.NONE);
		addEEnumLiteral(pointRoleEEnum, PointRole.NORMAL);
		addEEnumLiteral(pointRoleEEnum, PointRole.REVERSE);
	}

} // SchemaPackageImpl
