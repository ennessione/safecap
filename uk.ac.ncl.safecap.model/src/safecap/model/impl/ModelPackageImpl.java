/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.model.impl;

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
import safecap.extension.ExtensionPackage;
import safecap.extension.impl.ExtensionPackageImpl;
import safecap.impl.SafecapPackageImpl;
import safecap.model.Ambit;
import safecap.model.ControlLogic;
import safecap.model.Junction;
import safecap.model.Line;
import safecap.model.Model;
import safecap.model.ModelFactory;
import safecap.model.ModelPackage;
import safecap.model.Point;
import safecap.model.Route;
import safecap.model.RouteStateRule;
import safecap.model.Rule;
import safecap.model.Section;
import safecap.model.TimedOccupationRule;
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
public class ModelPackageImpl extends EPackageImpl implements ModelPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass modelEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass lineEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass routeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass ambitEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass sectionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass junctionEClass = null;

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
	private EClass ruleEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass timedOccupationRuleEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass routeStateRuleEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass controlLogicEClass = null;

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
	 * @see safecap.model.ModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModelPackageImpl() {
		super(eNS_URI, ModelFactory.eINSTANCE);
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
	 * This method is used to initialize {@link ModelPackage#eINSTANCE} when that
	 * field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModelPackage init() {
		if (isInited) {
			return (ModelPackage) EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);
		}

		// Obtain or create and register package
		final Object registeredModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		final ModelPackageImpl theModelPackage = registeredModelPackage instanceof ModelPackageImpl
				? (ModelPackageImpl) registeredModelPackage
				: new ModelPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(SafecapPackage.eNS_URI);
		final SafecapPackageImpl theSafecapPackage = (SafecapPackageImpl) (registeredPackage instanceof SafecapPackageImpl
				? registeredPackage
				: SafecapPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(SchemaPackage.eNS_URI);
		final SchemaPackageImpl theSchemaPackage = (SchemaPackageImpl) (registeredPackage instanceof SchemaPackageImpl ? registeredPackage
				: SchemaPackage.eINSTANCE);
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
		theModelPackage.createPackageContents();
		theSafecapPackage.createPackageContents();
		theSchemaPackage.createPackageContents();
		theTracksidePackage.createPackageContents();
		theCommentaryPackage.createPackageContents();
		theExtensionPackage.createPackageContents();
		theDerivedPackage.createPackageContents();
		theConfigPackage.createPackageContents();

		// Initialize created meta-data
		theModelPackage.initializePackageContents();
		theSafecapPackage.initializePackageContents();
		theSchemaPackage.initializePackageContents();
		theTracksidePackage.initializePackageContents();
		theCommentaryPackage.initializePackageContents();
		theExtensionPackage.initializePackageContents();
		theDerivedPackage.initializePackageContents();
		theConfigPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModelPackage.eNS_URI, theModelPackage);
		return theModelPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getModel() {
		return modelEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getModel_Lines() {
		return (EReference) modelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getModel_Routes() {
		return (EReference) modelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getModel_Ambits() {
		return (EReference) modelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getLine() {
		return lineEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getLine_Routes() {
		return (EReference) lineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getLine_Color() {
		return (EAttribute) lineEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getLine_Orientation() {
		return (EAttribute) lineEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getLine_Trafficmix() {
		return (EAttribute) lineEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRoute() {
		return routeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRoute_Ambits() {
		return (EReference) routeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRoute_Segments() {
		return (EReference) routeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRoute_Overlap() {
		return (EReference) routeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRoute_Rule() {
		return (EReference) routeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getRoute_Aspects() {
		return (EAttribute) routeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getRoute_Orientation() {
		return (EAttribute) routeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRoute_Logic() {
		return (EReference) routeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRoute_Defaultlogic() {
		return (EReference) routeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getAmbit() {
		return ambitEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getAmbit_Segments() {
		return (EReference) ambitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSection() {
		return sectionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getJunction() {
		return junctionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getJunction_Points() {
		return (EReference) junctionEClass.getEStructuralFeatures().get(0);
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
	public EReference getPoint_Node() {
		return (EReference) pointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getPoint_Rule() {
		return (EReference) pointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRule() {
		return ruleEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRule_Clear() {
		return (EReference) ruleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRule_Occupied() {
		return (EReference) ruleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRule_Normal() {
		return (EReference) ruleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRule_Reverse() {
		return (EReference) ruleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRule_RouteState() {
		return (EReference) ruleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTimedOccupationRule() {
		return timedOccupationRuleEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTimedOccupationRule_Ambit() {
		return (EReference) timedOccupationRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTimedOccupationRule_Time() {
		return (EAttribute) timedOccupationRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRouteStateRule() {
		return routeStateRuleEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRouteStateRule_Route() {
		return (EReference) routeStateRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getRouteStateRule_State() {
		return (EAttribute) routeStateRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getControlLogic() {
		return controlLogicEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getControlLogic_Line() {
		return (EReference) controlLogicEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getControlLogic_Aspects() {
		return (EAttribute) controlLogicEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getControlLogic_Rule() {
		return (EReference) controlLogicEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getControlLogic_Normal() {
		return (EReference) controlLogicEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getControlLogic_Reverse() {
		return (EReference) controlLogicEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ModelFactory getModelFactory() {
		return (ModelFactory) getEFactoryInstance();
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
		modelEClass = createEClass(MODEL);
		createEReference(modelEClass, MODEL__LINES);
		createEReference(modelEClass, MODEL__ROUTES);
		createEReference(modelEClass, MODEL__AMBITS);

		lineEClass = createEClass(LINE);
		createEReference(lineEClass, LINE__ROUTES);
		createEAttribute(lineEClass, LINE__COLOR);
		createEAttribute(lineEClass, LINE__ORIENTATION);
		createEAttribute(lineEClass, LINE__TRAFFICMIX);

		routeEClass = createEClass(ROUTE);
		createEReference(routeEClass, ROUTE__AMBITS);
		createEReference(routeEClass, ROUTE__SEGMENTS);
		createEReference(routeEClass, ROUTE__OVERLAP);
		createEReference(routeEClass, ROUTE__RULE);
		createEAttribute(routeEClass, ROUTE__ASPECTS);
		createEAttribute(routeEClass, ROUTE__ORIENTATION);
		createEReference(routeEClass, ROUTE__LOGIC);
		createEReference(routeEClass, ROUTE__DEFAULTLOGIC);

		ambitEClass = createEClass(AMBIT);
		createEReference(ambitEClass, AMBIT__SEGMENTS);

		sectionEClass = createEClass(SECTION);

		junctionEClass = createEClass(JUNCTION);
		createEReference(junctionEClass, JUNCTION__POINTS);

		pointEClass = createEClass(POINT);
		createEReference(pointEClass, POINT__NODE);
		createEReference(pointEClass, POINT__RULE);

		ruleEClass = createEClass(RULE);
		createEReference(ruleEClass, RULE__CLEAR);
		createEReference(ruleEClass, RULE__OCCUPIED);
		createEReference(ruleEClass, RULE__NORMAL);
		createEReference(ruleEClass, RULE__REVERSE);
		createEReference(ruleEClass, RULE__ROUTE_STATE);

		timedOccupationRuleEClass = createEClass(TIMED_OCCUPATION_RULE);
		createEReference(timedOccupationRuleEClass, TIMED_OCCUPATION_RULE__AMBIT);
		createEAttribute(timedOccupationRuleEClass, TIMED_OCCUPATION_RULE__TIME);

		routeStateRuleEClass = createEClass(ROUTE_STATE_RULE);
		createEReference(routeStateRuleEClass, ROUTE_STATE_RULE__ROUTE);
		createEAttribute(routeStateRuleEClass, ROUTE_STATE_RULE__STATE);

		controlLogicEClass = createEClass(CONTROL_LOGIC);
		createEReference(controlLogicEClass, CONTROL_LOGIC__LINE);
		createEAttribute(controlLogicEClass, CONTROL_LOGIC__ASPECTS);
		createEReference(controlLogicEClass, CONTROL_LOGIC__RULE);
		createEReference(controlLogicEClass, CONTROL_LOGIC__NORMAL);
		createEReference(controlLogicEClass, CONTROL_LOGIC__REVERSE);
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
		final SchemaPackage theSchemaPackage = (SchemaPackage) EPackage.Registry.INSTANCE.getEPackage(SchemaPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		lineEClass.getESuperTypes().add(theSafecapPackage.getLabeled());
		lineEClass.getESuperTypes().add(theSafecapPackage.getExtensible());
		lineEClass.getESuperTypes().add(theSafecapPackage.getVisual());
		routeEClass.getESuperTypes().add(theSafecapPackage.getLabeled());
		routeEClass.getESuperTypes().add(theSafecapPackage.getExtensible());
		ambitEClass.getESuperTypes().add(theSafecapPackage.getLabeled());
		ambitEClass.getESuperTypes().add(theSafecapPackage.getExtensible());
		sectionEClass.getESuperTypes().add(getAmbit());
		junctionEClass.getESuperTypes().add(getAmbit());
		pointEClass.getESuperTypes().add(theSafecapPackage.getExtensible());
		ruleEClass.getESuperTypes().add(theSafecapPackage.getExtensible());

		// Initialize classes and features; add operations and parameters
		initEClass(modelEClass, Model.class, "Model", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModel_Lines(), getLine(), null, "lines", null, 0, -1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModel_Routes(), getRoute(), null, "routes", null, 0, -1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModel_Ambits(), getAmbit(), null, "ambits", null, 0, -1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(lineEClass, Line.class, "Line", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLine_Routes(), getRoute(), null, "routes", null, 0, -1, Line.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLine_Color(), ecorePackage.getEJavaObject(), "color", null, 0, 1, Line.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLine_Orientation(), theSafecapPackage.getOrientation(), "orientation", "Left", 0, 1, Line.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLine_Trafficmix(), ecorePackage.getEString(), "trafficmix", null, 0, -1, Line.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(routeEClass, Route.class, "Route", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRoute_Ambits(), getAmbit(), null, "ambits", null, 1, -1, Route.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoute_Segments(), theSchemaPackage.getSegment(), null, "segments", null, 1, -1, Route.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoute_Overlap(), getAmbit(), null, "overlap", null, 0, 1, Route.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoute_Rule(), getRule(), null, "rule", null, 0, -1, Route.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoute_Aspects(), ecorePackage.getEInt(), "aspects", "2", 0, 1, Route.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoute_Orientation(), theSafecapPackage.getOrientation(), "orientation", "Left", 0, 1, Route.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoute_Logic(), getControlLogic(), null, "logic", null, 0, -1, Route.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoute_Defaultlogic(), getControlLogic(), null, "defaultlogic", null, 0, 1, Route.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ambitEClass, Ambit.class, "Ambit", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAmbit_Segments(), theSchemaPackage.getSegment(), null, "segments", null, 1, -1, Ambit.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sectionEClass, Section.class, "Section", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(junctionEClass, Junction.class, "Junction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJunction_Points(), getPoint(), null, "points", null, 0, -1, Junction.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pointEClass, Point.class, "Point", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPoint_Node(), theSchemaPackage.getNode(), null, "node", null, 0, 1, Point.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPoint_Rule(), getRule(), null, "rule", null, 0, 1, Point.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleEClass, Rule.class, "Rule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRule_Clear(), getAmbit(), null, "clear", null, 0, -1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRule_Occupied(), getTimedOccupationRule(), null, "occupied", null, 0, -1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRule_Normal(), getPoint(), null, "normal", null, 0, -1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRule_Reverse(), getPoint(), null, "reverse", null, 0, -1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRule_RouteState(), getRouteStateRule(), null, "routeState", null, 0, -1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(timedOccupationRuleEClass, TimedOccupationRule.class, "TimedOccupationRule", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTimedOccupationRule_Ambit(), getAmbit(), null, "ambit", null, 0, 1, TimedOccupationRule.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTimedOccupationRule_Time(), ecorePackage.getEDouble(), "time", null, 0, 1, TimedOccupationRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(routeStateRuleEClass, RouteStateRule.class, "RouteStateRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRouteStateRule_Route(), getRoute(), null, "route", null, 0, 1, RouteStateRule.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRouteStateRule_State(), ecorePackage.getEInt(), "state", null, 0, 1, RouteStateRule.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(controlLogicEClass, ControlLogic.class, "ControlLogic", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getControlLogic_Line(), getLine(), null, "line", null, 0, 1, ControlLogic.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlLogic_Aspects(), ecorePackage.getEInt(), "aspects", "2", 0, 1, ControlLogic.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getControlLogic_Rule(), getRule(), null, "rule", null, 0, -1, ControlLogic.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getControlLogic_Normal(), getPoint(), null, "normal", null, 0, -1, ControlLogic.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getControlLogic_Reverse(), getPoint(), null, "reverse", null, 0, -1, ControlLogic.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} // ModelPackageImpl
