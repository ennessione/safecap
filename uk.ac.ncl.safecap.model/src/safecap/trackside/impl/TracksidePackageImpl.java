/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside.impl;

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
import safecap.schema.SchemaPackage;
import safecap.schema.impl.SchemaPackageImpl;
import safecap.trackside.Equipment;
import safecap.trackside.GenericLocatedEquipment;
import safecap.trackside.LeftSide;
import safecap.trackside.LeftSignal;
import safecap.trackside.LeftSpeedLimit;
import safecap.trackside.LeftStopAndGo;
import safecap.trackside.NodeWire;
import safecap.trackside.RedLeftSignal;
import safecap.trackside.RedRightSignal;
import safecap.trackside.RightSide;
import safecap.trackside.RightSignal;
import safecap.trackside.RightSpeedLimit;
import safecap.trackside.RightStopAndGo;
import safecap.trackside.Signal;
import safecap.trackside.SignalType;
import safecap.trackside.SpeedLimit;
import safecap.trackside.StopAndGo;
import safecap.trackside.SubEquipment;
import safecap.trackside.SubWire;
import safecap.trackside.Trackside;
import safecap.trackside.TracksideFactory;
import safecap.trackside.TracksidePackage;
import safecap.trackside.Wire;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class TracksidePackageImpl extends EPackageImpl implements TracksidePackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tracksideEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass equipmentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass signalEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass speedLimitEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass leftSignalEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass rightSignalEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass leftSpeedLimitEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass rightSpeedLimitEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass wireEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass redLeftSignalEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass redRightSignalEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass leftSideEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass rightSideEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass stopAndGoEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass leftStopAndGoEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass rightStopAndGoEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass subEquipmentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass subWireEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass nodeWireEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass genericLocatedEquipmentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum signalTypeEEnum = null;

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
	 * @see safecap.trackside.TracksidePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TracksidePackageImpl() {
		super(eNS_URI, TracksideFactory.eINSTANCE);
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
	 * This method is used to initialize {@link TracksidePackage#eINSTANCE} when
	 * that field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TracksidePackage init() {
		if (isInited) {
			return (TracksidePackage) EPackage.Registry.INSTANCE.getEPackage(TracksidePackage.eNS_URI);
		}

		// Obtain or create and register package
		final Object registeredTracksidePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		final TracksidePackageImpl theTracksidePackage = registeredTracksidePackage instanceof TracksidePackageImpl
				? (TracksidePackageImpl) registeredTracksidePackage
				: new TracksidePackageImpl();

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
		theTracksidePackage.createPackageContents();
		theSafecapPackage.createPackageContents();
		theSchemaPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theCommentaryPackage.createPackageContents();
		theExtensionPackage.createPackageContents();
		theDerivedPackage.createPackageContents();
		theConfigPackage.createPackageContents();

		// Initialize created meta-data
		theTracksidePackage.initializePackageContents();
		theSafecapPackage.initializePackageContents();
		theSchemaPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theCommentaryPackage.initializePackageContents();
		theExtensionPackage.initializePackageContents();
		theDerivedPackage.initializePackageContents();
		theConfigPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTracksidePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TracksidePackage.eNS_URI, theTracksidePackage);
		return theTracksidePackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTrackside() {
		return tracksideEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackside_Equipment() {
		return (EReference) tracksideEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackside_Wires() {
		return (EReference) tracksideEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackside_Nodewires() {
		return (EReference) tracksideEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackside_Subwires() {
		return (EReference) tracksideEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackside_Subequipment() {
		return (EReference) tracksideEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getEquipment() {
		return equipmentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSignal() {
		return signalEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSignal_Type() {
		return (EAttribute) signalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSpeedLimit() {
		return speedLimitEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSpeedLimit_Line() {
		return (EReference) speedLimitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSpeedLimit_Limit() {
		return (EAttribute) speedLimitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getLeftSignal() {
		return leftSignalEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRightSignal() {
		return rightSignalEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getLeftSpeedLimit() {
		return leftSpeedLimitEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRightSpeedLimit() {
		return rightSpeedLimitEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getWire() {
		return wireEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getWire_From() {
		return (EReference) wireEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getWire_To() {
		return (EReference) wireEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getWire_Offset() {
		return (EAttribute) wireEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRedLeftSignal() {
		return redLeftSignalEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRedRightSignal() {
		return redRightSignalEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getLeftSide() {
		return leftSideEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRightSide() {
		return rightSideEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getStopAndGo() {
		return stopAndGoEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getStopAndGo_Delay() {
		return (EAttribute) stopAndGoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getStopAndGo_Line() {
		return (EReference) stopAndGoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getStopAndGo_ReleaseRoute() {
		return (EReference) stopAndGoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getLeftStopAndGo() {
		return leftStopAndGoEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRightStopAndGo() {
		return rightStopAndGoEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSubEquipment() {
		return subEquipmentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSubWire() {
		return subWireEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSubWire_From() {
		return (EReference) subWireEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSubWire_To() {
		return (EReference) subWireEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getNodeWire() {
		return nodeWireEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getNodeWire_From() {
		return (EReference) nodeWireEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getNodeWire_To() {
		return (EReference) nodeWireEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getGenericLocatedEquipment() {
		return genericLocatedEquipmentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getGenericLocatedEquipment_Offset() {
		return (EAttribute) genericLocatedEquipmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getGenericLocatedEquipment_Type() {
		return (EAttribute) genericLocatedEquipmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getGenericLocatedEquipment_Segment() {
		return (EReference) genericLocatedEquipmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getGenericLocatedEquipment_Orientation() {
		return (EAttribute) genericLocatedEquipmentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getSignalType() {
		return signalTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TracksideFactory getTracksideFactory() {
		return (TracksideFactory) getEFactoryInstance();
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
		tracksideEClass = createEClass(TRACKSIDE);
		createEReference(tracksideEClass, TRACKSIDE__EQUIPMENT);
		createEReference(tracksideEClass, TRACKSIDE__WIRES);
		createEReference(tracksideEClass, TRACKSIDE__NODEWIRES);
		createEReference(tracksideEClass, TRACKSIDE__SUBWIRES);
		createEReference(tracksideEClass, TRACKSIDE__SUBEQUIPMENT);

		equipmentEClass = createEClass(EQUIPMENT);

		signalEClass = createEClass(SIGNAL);
		createEAttribute(signalEClass, SIGNAL__TYPE);

		speedLimitEClass = createEClass(SPEED_LIMIT);
		createEReference(speedLimitEClass, SPEED_LIMIT__LINE);
		createEAttribute(speedLimitEClass, SPEED_LIMIT__LIMIT);

		leftSignalEClass = createEClass(LEFT_SIGNAL);

		rightSignalEClass = createEClass(RIGHT_SIGNAL);

		leftSpeedLimitEClass = createEClass(LEFT_SPEED_LIMIT);

		rightSpeedLimitEClass = createEClass(RIGHT_SPEED_LIMIT);

		wireEClass = createEClass(WIRE);
		createEReference(wireEClass, WIRE__FROM);
		createEReference(wireEClass, WIRE__TO);
		createEAttribute(wireEClass, WIRE__OFFSET);

		redLeftSignalEClass = createEClass(RED_LEFT_SIGNAL);

		redRightSignalEClass = createEClass(RED_RIGHT_SIGNAL);

		leftSideEClass = createEClass(LEFT_SIDE);

		rightSideEClass = createEClass(RIGHT_SIDE);

		stopAndGoEClass = createEClass(STOP_AND_GO);
		createEAttribute(stopAndGoEClass, STOP_AND_GO__DELAY);
		createEReference(stopAndGoEClass, STOP_AND_GO__LINE);
		createEReference(stopAndGoEClass, STOP_AND_GO__RELEASE_ROUTE);

		leftStopAndGoEClass = createEClass(LEFT_STOP_AND_GO);

		rightStopAndGoEClass = createEClass(RIGHT_STOP_AND_GO);

		subEquipmentEClass = createEClass(SUB_EQUIPMENT);

		subWireEClass = createEClass(SUB_WIRE);
		createEReference(subWireEClass, SUB_WIRE__FROM);
		createEReference(subWireEClass, SUB_WIRE__TO);

		nodeWireEClass = createEClass(NODE_WIRE);
		createEReference(nodeWireEClass, NODE_WIRE__FROM);
		createEReference(nodeWireEClass, NODE_WIRE__TO);

		genericLocatedEquipmentEClass = createEClass(GENERIC_LOCATED_EQUIPMENT);
		createEAttribute(genericLocatedEquipmentEClass, GENERIC_LOCATED_EQUIPMENT__OFFSET);
		createEAttribute(genericLocatedEquipmentEClass, GENERIC_LOCATED_EQUIPMENT__TYPE);
		createEReference(genericLocatedEquipmentEClass, GENERIC_LOCATED_EQUIPMENT__SEGMENT);
		createEAttribute(genericLocatedEquipmentEClass, GENERIC_LOCATED_EQUIPMENT__ORIENTATION);

		// Create enums
		signalTypeEEnum = createEEnum(SIGNAL_TYPE);
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
		final SchemaPackage theSchemaPackage = (SchemaPackage) EPackage.Registry.INSTANCE.getEPackage(SchemaPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		equipmentEClass.getESuperTypes().add(theSafecapPackage.getLabeled());
		equipmentEClass.getESuperTypes().add(theSafecapPackage.getVisual());
		equipmentEClass.getESuperTypes().add(theSafecapPackage.getProvenance());
		equipmentEClass.getESuperTypes().add(theSafecapPackage.getExtensible());
		signalEClass.getESuperTypes().add(getEquipment());
		speedLimitEClass.getESuperTypes().add(getEquipment());
		leftSignalEClass.getESuperTypes().add(getSignal());
		leftSignalEClass.getESuperTypes().add(getLeftSide());
		rightSignalEClass.getESuperTypes().add(getSignal());
		rightSignalEClass.getESuperTypes().add(getRightSide());
		leftSpeedLimitEClass.getESuperTypes().add(getSpeedLimit());
		leftSpeedLimitEClass.getESuperTypes().add(getLeftSide());
		rightSpeedLimitEClass.getESuperTypes().add(getSpeedLimit());
		rightSpeedLimitEClass.getESuperTypes().add(getRightSide());
		wireEClass.getESuperTypes().add(theSafecapPackage.getExtensible());
		wireEClass.getESuperTypes().add(theSafecapPackage.getVisual());
		redLeftSignalEClass.getESuperTypes().add(getSignal());
		redLeftSignalEClass.getESuperTypes().add(getLeftSide());
		redRightSignalEClass.getESuperTypes().add(getSignal());
		redRightSignalEClass.getESuperTypes().add(getRightSide());
		stopAndGoEClass.getESuperTypes().add(getEquipment());
		leftStopAndGoEClass.getESuperTypes().add(getStopAndGo());
		leftStopAndGoEClass.getESuperTypes().add(getLeftSide());
		rightStopAndGoEClass.getESuperTypes().add(getStopAndGo());
		rightStopAndGoEClass.getESuperTypes().add(getRightSide());
		subEquipmentEClass.getESuperTypes().add(getEquipment());
		genericLocatedEquipmentEClass.getESuperTypes().add(getEquipment());

		// Initialize classes and features; add operations and parameters
		initEClass(tracksideEClass, Trackside.class, "Trackside", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTrackside_Equipment(), getEquipment(), null, "equipment", null, 0, -1, Trackside.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrackside_Wires(), getWire(), null, "wires", null, 0, -1, Trackside.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrackside_Nodewires(), getNodeWire(), null, "nodewires", null, 0, -1, Trackside.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrackside_Subwires(), getSubWire(), null, "subwires", null, 0, -1, Trackside.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrackside_Subequipment(), getSubEquipment(), null, "subequipment", null, 0, -1, Trackside.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(equipmentEClass, Equipment.class, "Equipment", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(signalEClass, Signal.class, "Signal", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSignal_Type(), getSignalType(), "type", "Controlled", 0, 1, Signal.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(speedLimitEClass, SpeedLimit.class, "SpeedLimit", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSpeedLimit_Line(), theModelPackage.getLine(), null, "line", null, 0, -1, SpeedLimit.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSpeedLimit_Limit(), ecorePackage.getEDouble(), "limit", null, 0, 1, SpeedLimit.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(leftSignalEClass, LeftSignal.class, "LeftSignal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(rightSignalEClass, RightSignal.class, "RightSignal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(leftSpeedLimitEClass, LeftSpeedLimit.class, "LeftSpeedLimit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(rightSpeedLimitEClass, RightSpeedLimit.class, "RightSpeedLimit", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(wireEClass, Wire.class, "Wire", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWire_From(), getEquipment(), null, "from", null, 0, 1, Wire.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWire_To(), theSchemaPackage.getSegment(), null, "to", null, 0, 1, Wire.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWire_Offset(), ecorePackage.getEInt(), "offset", null, 0, 1, Wire.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(redLeftSignalEClass, RedLeftSignal.class, "RedLeftSignal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(redRightSignalEClass, RedRightSignal.class, "RedRightSignal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(leftSideEClass, LeftSide.class, "LeftSide", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(rightSideEClass, RightSide.class, "RightSide", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(stopAndGoEClass, StopAndGo.class, "StopAndGo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStopAndGo_Delay(), ecorePackage.getEInt(), "delay", null, 0, 1, StopAndGo.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStopAndGo_Line(), theModelPackage.getLine(), null, "line", null, 0, -1, StopAndGo.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStopAndGo_ReleaseRoute(), theModelPackage.getRoute(), null, "releaseRoute", null, 0, 1, StopAndGo.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(leftStopAndGoEClass, LeftStopAndGo.class, "LeftStopAndGo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(rightStopAndGoEClass, RightStopAndGo.class, "RightStopAndGo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(subEquipmentEClass, SubEquipment.class, "SubEquipment", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(subWireEClass, SubWire.class, "SubWire", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSubWire_From(), getSubEquipment(), null, "from", null, 0, 1, SubWire.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSubWire_To(), getEquipment(), null, "to", null, 0, 1, SubWire.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nodeWireEClass, NodeWire.class, "NodeWire", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNodeWire_From(), getEquipment(), null, "from", null, 0, 1, NodeWire.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeWire_To(), theSchemaPackage.getNode(), null, "to", null, 0, 1, NodeWire.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(genericLocatedEquipmentEClass, GenericLocatedEquipment.class, "GenericLocatedEquipment", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGenericLocatedEquipment_Offset(), ecorePackage.getEInt(), "offset", null, 0, 1, GenericLocatedEquipment.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenericLocatedEquipment_Type(), ecorePackage.getEString(), "type", null, 0, 1, GenericLocatedEquipment.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGenericLocatedEquipment_Segment(), theSchemaPackage.getSegment(), null, "segment", null, 0, 1,
				GenericLocatedEquipment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenericLocatedEquipment_Orientation(), theSafecapPackage.getOrientation(), "orientation", null, 0, 1,
				GenericLocatedEquipment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(signalTypeEEnum, SignalType.class, "SignalType");
		addEEnumLiteral(signalTypeEEnum, SignalType.CONTROLLED);
		addEEnumLiteral(signalTypeEEnum, SignalType.AUTOMATIC);
		addEEnumLiteral(signalTypeEEnum, SignalType.DISTANT);
		addEEnumLiteral(signalTypeEEnum, SignalType.SEMI_AUTOMATIC);
		addEEnumLiteral(signalTypeEEnum, SignalType.BANNER_REPEATER);
		addEEnumLiteral(signalTypeEEnum, SignalType.SHUNT);
		addEEnumLiteral(signalTypeEEnum, SignalType.PRESET_SHUNT);
		addEEnumLiteral(signalTypeEEnum, SignalType.BLOCK_MARKER);
		addEEnumLiteral(signalTypeEEnum, SignalType.VIRTUAL_BLOCK_MARKER);
	}

} // TracksidePackageImpl
