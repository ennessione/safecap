/**
 */
package traintable.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import traintable.TDAttribute;
import traintable.TDTable;
import traintable.TDTableRow;
import traintable.TDValue;
import traintable.Train;
import traintable.TrainTable;
import traintable.TraintableFactory;
import traintable.TraintablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class TraintablePackageImpl extends EPackageImpl implements TraintablePackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tdAttributeEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass trainTableEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass trainEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tdTableRowEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tdTableEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tdValueEClass = null;

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
	 * @see traintable.TraintablePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TraintablePackageImpl() {
		super(eNS_URI, TraintableFactory.eINSTANCE);
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
	 * This method is used to initialize {@link TraintablePackage#eINSTANCE} when
	 * that field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TraintablePackage init() {
		if (isInited) {
			return (TraintablePackage) EPackage.Registry.INSTANCE.getEPackage(TraintablePackage.eNS_URI);
		}

		// Obtain or create and register package
		final TraintablePackageImpl theTraintablePackage = (TraintablePackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof TraintablePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TraintablePackageImpl());

		isInited = true;

		// Create package meta-data objects
		theTraintablePackage.createPackageContents();

		// Initialize created meta-data
		theTraintablePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTraintablePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TraintablePackage.eNS_URI, theTraintablePackage);
		return theTraintablePackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTDAttribute() {
		return tdAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTrainTable() {
		return trainTableEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrainTable_Trains() {
		return (EReference) trainTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTrain() {
		return trainEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrain_Name() {
		return (EAttribute) trainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrain_Speed() {
		return (EAttribute) trainEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrain_Acceleration() {
		return (EAttribute) trainEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrain_Deceleration() {
		return (EAttribute) trainEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrain_Length() {
		return (EAttribute) trainEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrain_Attributes() {
		return (EReference) trainEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTDTableRow() {
		return tdTableRowEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTDTableRow_Values() {
		return (EAttribute) tdTableRowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTDTableRow_Label() {
		return (EAttribute) tdTableRowEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTDTable() {
		return tdTableEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTDTable_Label() {
		return (EAttribute) tdTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTDTable_ColumnLabels() {
		return (EAttribute) tdTableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTDTable_Rows() {
		return (EReference) tdTableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTDValue() {
		return tdValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTDValue_Label() {
		return (EAttribute) tdValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTDValue_Value() {
		return (EAttribute) tdValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TraintableFactory getTraintableFactory() {
		return (TraintableFactory) getEFactoryInstance();
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
		tdAttributeEClass = createEClass(TD_ATTRIBUTE);

		trainTableEClass = createEClass(TRAIN_TABLE);
		createEReference(trainTableEClass, TRAIN_TABLE__TRAINS);

		trainEClass = createEClass(TRAIN);
		createEAttribute(trainEClass, TRAIN__NAME);
		createEAttribute(trainEClass, TRAIN__SPEED);
		createEAttribute(trainEClass, TRAIN__ACCELERATION);
		createEAttribute(trainEClass, TRAIN__DECELERATION);
		createEAttribute(trainEClass, TRAIN__LENGTH);
		createEReference(trainEClass, TRAIN__ATTRIBUTES);

		tdTableRowEClass = createEClass(TD_TABLE_ROW);
		createEAttribute(tdTableRowEClass, TD_TABLE_ROW__VALUES);
		createEAttribute(tdTableRowEClass, TD_TABLE_ROW__LABEL);

		tdTableEClass = createEClass(TD_TABLE);
		createEAttribute(tdTableEClass, TD_TABLE__LABEL);
		createEAttribute(tdTableEClass, TD_TABLE__COLUMN_LABELS);
		createEReference(tdTableEClass, TD_TABLE__ROWS);

		tdValueEClass = createEClass(TD_VALUE);
		createEAttribute(tdValueEClass, TD_VALUE__LABEL);
		createEAttribute(tdValueEClass, TD_VALUE__VALUE);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tdTableEClass.getESuperTypes().add(getTDAttribute());
		tdValueEClass.getESuperTypes().add(getTDAttribute());

		// Initialize classes and features; add operations and parameters
		initEClass(tdAttributeEClass, TDAttribute.class, "TDAttribute", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(trainTableEClass, TrainTable.class, "TrainTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTrainTable_Trains(), getTrain(), null, "trains", null, 0, -1, TrainTable.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(trainEClass, Train.class, "Train", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTrain_Name(), ecorePackage.getEString(), "name", null, 0, 1, Train.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrain_Speed(), ecorePackage.getEInt(), "speed", null, 0, 1, Train.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrain_Acceleration(), ecorePackage.getEFloat(), "acceleration", null, 0, 1, Train.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrain_Deceleration(), ecorePackage.getEFloat(), "deceleration", null, 0, 1, Train.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrain_Length(), ecorePackage.getEInt(), "length", null, 0, 1, Train.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrain_Attributes(), getTDAttribute(), null, "attributes", null, 0, -1, Train.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tdTableRowEClass, TDTableRow.class, "TDTableRow", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTDTableRow_Values(), ecorePackage.getEString(), "values", null, 0, -1, TDTableRow.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTDTableRow_Label(), ecorePackage.getEString(), "label", null, 0, 1, TDTableRow.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tdTableEClass, TDTable.class, "TDTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTDTable_Label(), ecorePackage.getEString(), "label", null, 1, 1, TDTable.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTDTable_ColumnLabels(), ecorePackage.getEString(), "columnLabels", null, 0, -1, TDTable.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTDTable_Rows(), getTDTableRow(), null, "rows", null, 0, -1, TDTable.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tdValueEClass, TDValue.class, "TDValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTDValue_Label(), ecorePackage.getEString(), "label", null, 1, 1, TDValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTDValue_Value(), ecorePackage.getEString(), "value", null, 0, 1, TDValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // TraintablePackageImpl
