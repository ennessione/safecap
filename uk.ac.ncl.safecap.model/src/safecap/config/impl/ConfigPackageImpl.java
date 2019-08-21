/**
 */
package safecap.config.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import safecap.SafecapPackage;
import safecap.commentary.CommentaryPackage;
import safecap.commentary.impl.CommentaryPackageImpl;
import safecap.config.AutoOverlapsConfiguration;
import safecap.config.AutoRouteConstructionRule;
import safecap.config.ConfigFactory;
import safecap.config.ConfigPackage;
import safecap.config.MergedAmbitsConfiguration;
import safecap.config.MergedPointsConfiguration;
import safecap.config.NormalisationRule;
import safecap.config.PathRouteConstructionRule;
import safecap.config.RouteBuilderConfiguration;
import safecap.config.RouteConstructionRule;
import safecap.config.SchemaConfiguration;
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
public class ConfigPackageImpl extends EPackageImpl implements ConfigPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass schemaConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass mergedAmbitsConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass mergedPointsConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass normalisationRuleEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass autoOverlapsConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass routeConstructionRuleEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass autoRouteConstructionRuleEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass pathRouteConstructionRuleEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass routeBuilderConfigurationEClass = null;

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
	 * @see safecap.config.ConfigPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ConfigPackageImpl() {
		super(eNS_URI, ConfigFactory.eINSTANCE);
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
	 * This method is used to initialize {@link ConfigPackage#eINSTANCE} when that
	 * field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ConfigPackage init() {
		if (isInited) {
			return (ConfigPackage) EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI);
		}

		// Obtain or create and register package
		final Object registeredConfigPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		final ConfigPackageImpl theConfigPackage = registeredConfigPackage instanceof ConfigPackageImpl
				? (ConfigPackageImpl) registeredConfigPackage
				: new ConfigPackageImpl();

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
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ExtensionPackage.eNS_URI);
		final ExtensionPackageImpl theExtensionPackage = (ExtensionPackageImpl) (registeredPackage instanceof ExtensionPackageImpl
				? registeredPackage
				: ExtensionPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DerivedPackage.eNS_URI);
		final DerivedPackageImpl theDerivedPackage = (DerivedPackageImpl) (registeredPackage instanceof DerivedPackageImpl
				? registeredPackage
				: DerivedPackage.eINSTANCE);

		// Create package meta-data objects
		theConfigPackage.createPackageContents();
		theSafecapPackage.createPackageContents();
		theSchemaPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theTracksidePackage.createPackageContents();
		theCommentaryPackage.createPackageContents();
		theExtensionPackage.createPackageContents();
		theDerivedPackage.createPackageContents();

		// Initialize created meta-data
		theConfigPackage.initializePackageContents();
		theSafecapPackage.initializePackageContents();
		theSchemaPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theTracksidePackage.initializePackageContents();
		theCommentaryPackage.initializePackageContents();
		theExtensionPackage.initializePackageContents();
		theDerivedPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theConfigPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ConfigPackage.eNS_URI, theConfigPackage);
		return theConfigPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSchemaConfiguration() {
		return schemaConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSchemaConfiguration_WnSignal() {
		return (EAttribute) schemaConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSchemaConfiguration_WnTrack() {
		return (EAttribute) schemaConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSchemaConfiguration_Mergedambits() {
		return (EReference) schemaConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSchemaConfiguration_Mergedpoints() {
		return (EReference) schemaConfigurationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSchemaConfiguration_Normpoint() {
		return (EReference) schemaConfigurationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSchemaConfiguration_Normtrack() {
		return (EReference) schemaConfigurationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSchemaConfiguration_Normsignal() {
		return (EReference) schemaConfigurationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSchemaConfiguration_Overlaps() {
		return (EReference) schemaConfigurationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getMergedAmbitsConfiguration() {
		return mergedAmbitsConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMergedAmbitsConfiguration_Name() {
		return (EAttribute) mergedAmbitsConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMergedAmbitsConfiguration_Delimiter() {
		return (EAttribute) mergedAmbitsConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMergedAmbitsConfiguration_Code() {
		return (EAttribute) mergedAmbitsConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getMergedPointsConfiguration() {
		return mergedPointsConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMergedPointsConfiguration_Name() {
		return (EAttribute) mergedPointsConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMergedPointsConfiguration_Delimiter() {
		return (EAttribute) mergedPointsConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMergedPointsConfiguration_Code() {
		return (EAttribute) mergedPointsConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getNormalisationRule() {
		return normalisationRuleEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getNormalisationRule_Pattern() {
		return (EAttribute) normalisationRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getNormalisationRule_Template() {
		return (EAttribute) normalisationRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getAutoOverlapsConfiguration() {
		return autoOverlapsConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getAutoOverlapsConfiguration_MinLength() {
		return (EAttribute) autoOverlapsConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getAutoOverlapsConfiguration_MaxLength() {
		return (EAttribute) autoOverlapsConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRouteConstructionRule() {
		return routeConstructionRuleEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getRouteConstructionRule_Name() {
		return (EAttribute) routeConstructionRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getRouteConstructionRule_Ignoredsignals() {
		return (EAttribute) routeConstructionRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getAutoRouteConstructionRule() {
		return autoRouteConstructionRuleEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getPathRouteConstructionRule() {
		return pathRouteConstructionRuleEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPathRouteConstructionRule_Entry() {
		return (EAttribute) pathRouteConstructionRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPathRouteConstructionRule_Exit() {
		return (EAttribute) pathRouteConstructionRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPathRouteConstructionRule_Pointstates() {
		return (EAttribute) pathRouteConstructionRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRouteBuilderConfiguration() {
		return routeBuilderConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRouteBuilderConfiguration_Routes() {
		return (EReference) routeBuilderConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ConfigFactory getConfigFactory() {
		return (ConfigFactory) getEFactoryInstance();
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
		schemaConfigurationEClass = createEClass(SCHEMA_CONFIGURATION);
		createEAttribute(schemaConfigurationEClass, SCHEMA_CONFIGURATION__WN_SIGNAL);
		createEAttribute(schemaConfigurationEClass, SCHEMA_CONFIGURATION__WN_TRACK);
		createEReference(schemaConfigurationEClass, SCHEMA_CONFIGURATION__MERGEDAMBITS);
		createEReference(schemaConfigurationEClass, SCHEMA_CONFIGURATION__MERGEDPOINTS);
		createEReference(schemaConfigurationEClass, SCHEMA_CONFIGURATION__NORMPOINT);
		createEReference(schemaConfigurationEClass, SCHEMA_CONFIGURATION__NORMTRACK);
		createEReference(schemaConfigurationEClass, SCHEMA_CONFIGURATION__NORMSIGNAL);
		createEReference(schemaConfigurationEClass, SCHEMA_CONFIGURATION__OVERLAPS);

		mergedAmbitsConfigurationEClass = createEClass(MERGED_AMBITS_CONFIGURATION);
		createEAttribute(mergedAmbitsConfigurationEClass, MERGED_AMBITS_CONFIGURATION__NAME);
		createEAttribute(mergedAmbitsConfigurationEClass, MERGED_AMBITS_CONFIGURATION__DELIMITER);
		createEAttribute(mergedAmbitsConfigurationEClass, MERGED_AMBITS_CONFIGURATION__CODE);

		mergedPointsConfigurationEClass = createEClass(MERGED_POINTS_CONFIGURATION);
		createEAttribute(mergedPointsConfigurationEClass, MERGED_POINTS_CONFIGURATION__NAME);
		createEAttribute(mergedPointsConfigurationEClass, MERGED_POINTS_CONFIGURATION__DELIMITER);
		createEAttribute(mergedPointsConfigurationEClass, MERGED_POINTS_CONFIGURATION__CODE);

		normalisationRuleEClass = createEClass(NORMALISATION_RULE);
		createEAttribute(normalisationRuleEClass, NORMALISATION_RULE__PATTERN);
		createEAttribute(normalisationRuleEClass, NORMALISATION_RULE__TEMPLATE);

		autoOverlapsConfigurationEClass = createEClass(AUTO_OVERLAPS_CONFIGURATION);
		createEAttribute(autoOverlapsConfigurationEClass, AUTO_OVERLAPS_CONFIGURATION__MIN_LENGTH);
		createEAttribute(autoOverlapsConfigurationEClass, AUTO_OVERLAPS_CONFIGURATION__MAX_LENGTH);

		routeConstructionRuleEClass = createEClass(ROUTE_CONSTRUCTION_RULE);
		createEAttribute(routeConstructionRuleEClass, ROUTE_CONSTRUCTION_RULE__NAME);
		createEAttribute(routeConstructionRuleEClass, ROUTE_CONSTRUCTION_RULE__IGNOREDSIGNALS);

		autoRouteConstructionRuleEClass = createEClass(AUTO_ROUTE_CONSTRUCTION_RULE);

		pathRouteConstructionRuleEClass = createEClass(PATH_ROUTE_CONSTRUCTION_RULE);
		createEAttribute(pathRouteConstructionRuleEClass, PATH_ROUTE_CONSTRUCTION_RULE__ENTRY);
		createEAttribute(pathRouteConstructionRuleEClass, PATH_ROUTE_CONSTRUCTION_RULE__EXIT);
		createEAttribute(pathRouteConstructionRuleEClass, PATH_ROUTE_CONSTRUCTION_RULE__POINTSTATES);

		routeBuilderConfigurationEClass = createEClass(ROUTE_BUILDER_CONFIGURATION);
		createEReference(routeBuilderConfigurationEClass, ROUTE_BUILDER_CONFIGURATION__ROUTES);
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
		autoRouteConstructionRuleEClass.getESuperTypes().add(getRouteConstructionRule());
		pathRouteConstructionRuleEClass.getESuperTypes().add(getRouteConstructionRule());

		// Initialize classes and features; add operations and parameters
		initEClass(schemaConfigurationEClass, SchemaConfiguration.class, "SchemaConfiguration", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSchemaConfiguration_WnSignal(), ecorePackage.getEBoolean(), "wnSignal", null, 0, 1, SchemaConfiguration.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSchemaConfiguration_WnTrack(), ecorePackage.getEBoolean(), "wnTrack", null, 0, 1, SchemaConfiguration.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSchemaConfiguration_Mergedambits(), getMergedAmbitsConfiguration(), null, "mergedambits", null, 0, 1,
				SchemaConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSchemaConfiguration_Mergedpoints(), getMergedPointsConfiguration(), null, "mergedpoints", null, 0, 1,
				SchemaConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSchemaConfiguration_Normpoint(), getNormalisationRule(), null, "normpoint", null, 0, 1, SchemaConfiguration.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getSchemaConfiguration_Normtrack(), getNormalisationRule(), null, "normtrack", null, 0, 1, SchemaConfiguration.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getSchemaConfiguration_Normsignal(), getNormalisationRule(), null, "normsignal", null, 0, 1,
				SchemaConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSchemaConfiguration_Overlaps(), getAutoOverlapsConfiguration(), null, "overlaps", null, 0, 1,
				SchemaConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mergedAmbitsConfigurationEClass, MergedAmbitsConfiguration.class, "MergedAmbitsConfiguration", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMergedAmbitsConfiguration_Name(), ecorePackage.getEString(), "name", null, 1, 1, MergedAmbitsConfiguration.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMergedAmbitsConfiguration_Delimiter(), ecorePackage.getEString(), "delimiter", null, 1, 1,
				MergedAmbitsConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getMergedAmbitsConfiguration_Code(), ecorePackage.getEString(), "code", null, 1, 1, MergedAmbitsConfiguration.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mergedPointsConfigurationEClass, MergedPointsConfiguration.class, "MergedPointsConfiguration", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMergedPointsConfiguration_Name(), ecorePackage.getEString(), "name", null, 1, 1, MergedPointsConfiguration.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMergedPointsConfiguration_Delimiter(), ecorePackage.getEString(), "delimiter", null, 1, 1,
				MergedPointsConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getMergedPointsConfiguration_Code(), ecorePackage.getEString(), "code", null, 1, 1, MergedPointsConfiguration.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(normalisationRuleEClass, NormalisationRule.class, "NormalisationRule", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNormalisationRule_Pattern(), ecorePackage.getEString(), "pattern", null, 1, 1, NormalisationRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNormalisationRule_Template(), ecorePackage.getEString(), "template", null, 1, 1, NormalisationRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(autoOverlapsConfigurationEClass, AutoOverlapsConfiguration.class, "AutoOverlapsConfiguration", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAutoOverlapsConfiguration_MinLength(), ecorePackage.getEInt(), "minLength", null, 1, 1,
				AutoOverlapsConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getAutoOverlapsConfiguration_MaxLength(), ecorePackage.getEInt(), "maxLength", null, 1, 1,
				AutoOverlapsConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(routeConstructionRuleEClass, RouteConstructionRule.class, "RouteConstructionRule", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRouteConstructionRule_Name(), ecorePackage.getEString(), "name", null, 1, 1, RouteConstructionRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRouteConstructionRule_Ignoredsignals(), ecorePackage.getEString(), "ignoredsignals", null, 0, -1,
				RouteConstructionRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(autoRouteConstructionRuleEClass, AutoRouteConstructionRule.class, "AutoRouteConstructionRule", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(pathRouteConstructionRuleEClass, PathRouteConstructionRule.class, "PathRouteConstructionRule", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPathRouteConstructionRule_Entry(), ecorePackage.getEString(), "entry", null, 1, 1,
				PathRouteConstructionRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getPathRouteConstructionRule_Exit(), ecorePackage.getEString(), "exit", null, 1, 1, PathRouteConstructionRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPathRouteConstructionRule_Pointstates(), ecorePackage.getEString(), "pointstates", null, 0, -1,
				PathRouteConstructionRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(routeBuilderConfigurationEClass, RouteBuilderConfiguration.class, "RouteBuilderConfiguration", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRouteBuilderConfiguration_Routes(), getRouteConstructionRule(), null, "routes", null, 0, -1,
				RouteBuilderConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} // ConfigPackageImpl
