/**
 */
package safecap.config;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see safecap.config.ConfigFactory
 * @model kind="package"
 * @generated
 */
public interface ConfigPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "config";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "uk.ac.ncl.safecap.emf.config";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "uk.ac.ncl.safecap.emf.config";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	ConfigPackage eINSTANCE = safecap.config.impl.ConfigPackageImpl.init();

	/**
	 * The meta object id for the
	 * '{@link safecap.config.impl.SchemaConfigurationImpl <em>Schema
	 * Configuration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.config.impl.SchemaConfigurationImpl
	 * @see safecap.config.impl.ConfigPackageImpl#getSchemaConfiguration()
	 * @generated
	 */
	int SCHEMA_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Wn Signal</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCHEMA_CONFIGURATION__WN_SIGNAL = 0;

	/**
	 * The feature id for the '<em><b>Wn Track</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCHEMA_CONFIGURATION__WN_TRACK = 1;

	/**
	 * The feature id for the '<em><b>Mergedambits</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCHEMA_CONFIGURATION__MERGEDAMBITS = 2;

	/**
	 * The feature id for the '<em><b>Mergedpoints</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCHEMA_CONFIGURATION__MERGEDPOINTS = 3;

	/**
	 * The feature id for the '<em><b>Normpoint</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCHEMA_CONFIGURATION__NORMPOINT = 4;

	/**
	 * The feature id for the '<em><b>Normtrack</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCHEMA_CONFIGURATION__NORMTRACK = 5;

	/**
	 * The feature id for the '<em><b>Normsignal</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCHEMA_CONFIGURATION__NORMSIGNAL = 6;

	/**
	 * The feature id for the '<em><b>Overlaps</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCHEMA_CONFIGURATION__OVERLAPS = 7;

	/**
	 * The number of structural features of the '<em>Schema Configuration</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCHEMA_CONFIGURATION_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the
	 * '{@link safecap.config.impl.MergedAmbitsConfigurationImpl <em>Merged Ambits
	 * Configuration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.config.impl.MergedAmbitsConfigurationImpl
	 * @see safecap.config.impl.ConfigPackageImpl#getMergedAmbitsConfiguration()
	 * @generated
	 */
	int MERGED_AMBITS_CONFIGURATION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_AMBITS_CONFIGURATION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Delimiter</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_AMBITS_CONFIGURATION__DELIMITER = 1;

	/**
	 * The feature id for the '<em><b>Code</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_AMBITS_CONFIGURATION__CODE = 2;

	/**
	 * The number of structural features of the '<em>Merged Ambits
	 * Configuration</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_AMBITS_CONFIGURATION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the
	 * '{@link safecap.config.impl.MergedPointsConfigurationImpl <em>Merged Points
	 * Configuration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.config.impl.MergedPointsConfigurationImpl
	 * @see safecap.config.impl.ConfigPackageImpl#getMergedPointsConfiguration()
	 * @generated
	 */
	int MERGED_POINTS_CONFIGURATION = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_POINTS_CONFIGURATION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Delimiter</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_POINTS_CONFIGURATION__DELIMITER = 1;

	/**
	 * The feature id for the '<em><b>Code</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_POINTS_CONFIGURATION__CODE = 2;

	/**
	 * The number of structural features of the '<em>Merged Points
	 * Configuration</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_POINTS_CONFIGURATION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link safecap.config.impl.NormalisationRuleImpl
	 * <em>Normalisation Rule</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see safecap.config.impl.NormalisationRuleImpl
	 * @see safecap.config.impl.ConfigPackageImpl#getNormalisationRule()
	 * @generated
	 */
	int NORMALISATION_RULE = 3;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NORMALISATION_RULE__PATTERN = 0;

	/**
	 * The feature id for the '<em><b>Template</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NORMALISATION_RULE__TEMPLATE = 1;

	/**
	 * The number of structural features of the '<em>Normalisation Rule</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NORMALISATION_RULE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the
	 * '{@link safecap.config.impl.AutoOverlapsConfigurationImpl <em>Auto Overlaps
	 * Configuration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.config.impl.AutoOverlapsConfigurationImpl
	 * @see safecap.config.impl.ConfigPackageImpl#getAutoOverlapsConfiguration()
	 * @generated
	 */
	int AUTO_OVERLAPS_CONFIGURATION = 4;

	/**
	 * The feature id for the '<em><b>Min Length</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AUTO_OVERLAPS_CONFIGURATION__MIN_LENGTH = 0;

	/**
	 * The feature id for the '<em><b>Max Length</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AUTO_OVERLAPS_CONFIGURATION__MAX_LENGTH = 1;

	/**
	 * The number of structural features of the '<em>Auto Overlaps
	 * Configuration</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AUTO_OVERLAPS_CONFIGURATION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the
	 * '{@link safecap.config.impl.RouteConstructionRuleImpl <em>Route Construction
	 * Rule</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.config.impl.RouteConstructionRuleImpl
	 * @see safecap.config.impl.ConfigPackageImpl#getRouteConstructionRule()
	 * @generated
	 */
	int ROUTE_CONSTRUCTION_RULE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_CONSTRUCTION_RULE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Ignoredsignals</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_CONSTRUCTION_RULE__IGNOREDSIGNALS = 1;

	/**
	 * The number of structural features of the '<em>Route Construction Rule</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_CONSTRUCTION_RULE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the
	 * '{@link safecap.config.impl.AutoRouteConstructionRuleImpl <em>Auto Route
	 * Construction Rule</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.config.impl.AutoRouteConstructionRuleImpl
	 * @see safecap.config.impl.ConfigPackageImpl#getAutoRouteConstructionRule()
	 * @generated
	 */
	int AUTO_ROUTE_CONSTRUCTION_RULE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AUTO_ROUTE_CONSTRUCTION_RULE__NAME = ROUTE_CONSTRUCTION_RULE__NAME;

	/**
	 * The feature id for the '<em><b>Ignoredsignals</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AUTO_ROUTE_CONSTRUCTION_RULE__IGNOREDSIGNALS = ROUTE_CONSTRUCTION_RULE__IGNOREDSIGNALS;

	/**
	 * The number of structural features of the '<em>Auto Route Construction
	 * Rule</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AUTO_ROUTE_CONSTRUCTION_RULE_FEATURE_COUNT = ROUTE_CONSTRUCTION_RULE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link safecap.config.impl.PathRouteConstructionRuleImpl <em>Path Route
	 * Construction Rule</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.config.impl.PathRouteConstructionRuleImpl
	 * @see safecap.config.impl.ConfigPackageImpl#getPathRouteConstructionRule()
	 * @generated
	 */
	int PATH_ROUTE_CONSTRUCTION_RULE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATH_ROUTE_CONSTRUCTION_RULE__NAME = ROUTE_CONSTRUCTION_RULE__NAME;

	/**
	 * The feature id for the '<em><b>Ignoredsignals</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATH_ROUTE_CONSTRUCTION_RULE__IGNOREDSIGNALS = ROUTE_CONSTRUCTION_RULE__IGNOREDSIGNALS;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATH_ROUTE_CONSTRUCTION_RULE__ENTRY = ROUTE_CONSTRUCTION_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Exit</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATH_ROUTE_CONSTRUCTION_RULE__EXIT = ROUTE_CONSTRUCTION_RULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Pointstates</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATH_ROUTE_CONSTRUCTION_RULE__POINTSTATES = ROUTE_CONSTRUCTION_RULE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Path Route Construction
	 * Rule</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATH_ROUTE_CONSTRUCTION_RULE_FEATURE_COUNT = ROUTE_CONSTRUCTION_RULE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the
	 * '{@link safecap.config.impl.RouteBuilderConfigurationImpl <em>Route Builder
	 * Configuration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.config.impl.RouteBuilderConfigurationImpl
	 * @see safecap.config.impl.ConfigPackageImpl#getRouteBuilderConfiguration()
	 * @generated
	 */
	int ROUTE_BUILDER_CONFIGURATION = 8;

	/**
	 * The feature id for the '<em><b>Routes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_BUILDER_CONFIGURATION__ROUTES = 0;

	/**
	 * The number of structural features of the '<em>Route Builder
	 * Configuration</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_BUILDER_CONFIGURATION_FEATURE_COUNT = 1;

	/**
	 * Returns the meta object for class '{@link safecap.config.SchemaConfiguration
	 * <em>Schema Configuration</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Schema Configuration</em>'.
	 * @see safecap.config.SchemaConfiguration
	 * @generated
	 */
	EClass getSchemaConfiguration();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.SchemaConfiguration#isWnSignal <em>Wn Signal</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Wn Signal</em>'.
	 * @see safecap.config.SchemaConfiguration#isWnSignal()
	 * @see #getSchemaConfiguration()
	 * @generated
	 */
	EAttribute getSchemaConfiguration_WnSignal();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.SchemaConfiguration#isWnTrack <em>Wn Track</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Wn Track</em>'.
	 * @see safecap.config.SchemaConfiguration#isWnTrack()
	 * @see #getSchemaConfiguration()
	 * @generated
	 */
	EAttribute getSchemaConfiguration_WnTrack();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.config.SchemaConfiguration#getMergedambits
	 * <em>Mergedambits</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Mergedambits</em>'.
	 * @see safecap.config.SchemaConfiguration#getMergedambits()
	 * @see #getSchemaConfiguration()
	 * @generated
	 */
	EReference getSchemaConfiguration_Mergedambits();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.config.SchemaConfiguration#getMergedpoints
	 * <em>Mergedpoints</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Mergedpoints</em>'.
	 * @see safecap.config.SchemaConfiguration#getMergedpoints()
	 * @see #getSchemaConfiguration()
	 * @generated
	 */
	EReference getSchemaConfiguration_Mergedpoints();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.config.SchemaConfiguration#getNormpoint <em>Normpoint</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Normpoint</em>'.
	 * @see safecap.config.SchemaConfiguration#getNormpoint()
	 * @see #getSchemaConfiguration()
	 * @generated
	 */
	EReference getSchemaConfiguration_Normpoint();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.config.SchemaConfiguration#getNormtrack <em>Normtrack</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Normtrack</em>'.
	 * @see safecap.config.SchemaConfiguration#getNormtrack()
	 * @see #getSchemaConfiguration()
	 * @generated
	 */
	EReference getSchemaConfiguration_Normtrack();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.config.SchemaConfiguration#getNormsignal
	 * <em>Normsignal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Normsignal</em>'.
	 * @see safecap.config.SchemaConfiguration#getNormsignal()
	 * @see #getSchemaConfiguration()
	 * @generated
	 */
	EReference getSchemaConfiguration_Normsignal();

	/**
	 * Returns the meta object for the reference
	 * '{@link safecap.config.SchemaConfiguration#getOverlaps <em>Overlaps</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Overlaps</em>'.
	 * @see safecap.config.SchemaConfiguration#getOverlaps()
	 * @see #getSchemaConfiguration()
	 * @generated
	 */
	EReference getSchemaConfiguration_Overlaps();

	/**
	 * Returns the meta object for class
	 * '{@link safecap.config.MergedAmbitsConfiguration <em>Merged Ambits
	 * Configuration</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Merged Ambits Configuration</em>'.
	 * @see safecap.config.MergedAmbitsConfiguration
	 * @generated
	 */
	EClass getMergedAmbitsConfiguration();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.MergedAmbitsConfiguration#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see safecap.config.MergedAmbitsConfiguration#getName()
	 * @see #getMergedAmbitsConfiguration()
	 * @generated
	 */
	EAttribute getMergedAmbitsConfiguration_Name();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.MergedAmbitsConfiguration#getDelimiter
	 * <em>Delimiter</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Delimiter</em>'.
	 * @see safecap.config.MergedAmbitsConfiguration#getDelimiter()
	 * @see #getMergedAmbitsConfiguration()
	 * @generated
	 */
	EAttribute getMergedAmbitsConfiguration_Delimiter();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.MergedAmbitsConfiguration#getCode <em>Code</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Code</em>'.
	 * @see safecap.config.MergedAmbitsConfiguration#getCode()
	 * @see #getMergedAmbitsConfiguration()
	 * @generated
	 */
	EAttribute getMergedAmbitsConfiguration_Code();

	/**
	 * Returns the meta object for class
	 * '{@link safecap.config.MergedPointsConfiguration <em>Merged Points
	 * Configuration</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Merged Points Configuration</em>'.
	 * @see safecap.config.MergedPointsConfiguration
	 * @generated
	 */
	EClass getMergedPointsConfiguration();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.MergedPointsConfiguration#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see safecap.config.MergedPointsConfiguration#getName()
	 * @see #getMergedPointsConfiguration()
	 * @generated
	 */
	EAttribute getMergedPointsConfiguration_Name();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.MergedPointsConfiguration#getDelimiter
	 * <em>Delimiter</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Delimiter</em>'.
	 * @see safecap.config.MergedPointsConfiguration#getDelimiter()
	 * @see #getMergedPointsConfiguration()
	 * @generated
	 */
	EAttribute getMergedPointsConfiguration_Delimiter();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.MergedPointsConfiguration#getCode <em>Code</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Code</em>'.
	 * @see safecap.config.MergedPointsConfiguration#getCode()
	 * @see #getMergedPointsConfiguration()
	 * @generated
	 */
	EAttribute getMergedPointsConfiguration_Code();

	/**
	 * Returns the meta object for class '{@link safecap.config.NormalisationRule
	 * <em>Normalisation Rule</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Normalisation Rule</em>'.
	 * @see safecap.config.NormalisationRule
	 * @generated
	 */
	EClass getNormalisationRule();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.NormalisationRule#getPattern <em>Pattern</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Pattern</em>'.
	 * @see safecap.config.NormalisationRule#getPattern()
	 * @see #getNormalisationRule()
	 * @generated
	 */
	EAttribute getNormalisationRule_Pattern();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.NormalisationRule#getTemplate <em>Template</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Template</em>'.
	 * @see safecap.config.NormalisationRule#getTemplate()
	 * @see #getNormalisationRule()
	 * @generated
	 */
	EAttribute getNormalisationRule_Template();

	/**
	 * Returns the meta object for class
	 * '{@link safecap.config.AutoOverlapsConfiguration <em>Auto Overlaps
	 * Configuration</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Auto Overlaps Configuration</em>'.
	 * @see safecap.config.AutoOverlapsConfiguration
	 * @generated
	 */
	EClass getAutoOverlapsConfiguration();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.AutoOverlapsConfiguration#getMinLength <em>Min
	 * Length</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Min Length</em>'.
	 * @see safecap.config.AutoOverlapsConfiguration#getMinLength()
	 * @see #getAutoOverlapsConfiguration()
	 * @generated
	 */
	EAttribute getAutoOverlapsConfiguration_MinLength();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.AutoOverlapsConfiguration#getMaxLength <em>Max
	 * Length</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Max Length</em>'.
	 * @see safecap.config.AutoOverlapsConfiguration#getMaxLength()
	 * @see #getAutoOverlapsConfiguration()
	 * @generated
	 */
	EAttribute getAutoOverlapsConfiguration_MaxLength();

	/**
	 * Returns the meta object for class
	 * '{@link safecap.config.RouteConstructionRule <em>Route Construction
	 * Rule</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Route Construction Rule</em>'.
	 * @see safecap.config.RouteConstructionRule
	 * @generated
	 */
	EClass getRouteConstructionRule();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.RouteConstructionRule#getName <em>Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see safecap.config.RouteConstructionRule#getName()
	 * @see #getRouteConstructionRule()
	 * @generated
	 */
	EAttribute getRouteConstructionRule_Name();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link safecap.config.RouteConstructionRule#getIgnoredsignals
	 * <em>Ignoredsignals</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Ignoredsignals</em>'.
	 * @see safecap.config.RouteConstructionRule#getIgnoredsignals()
	 * @see #getRouteConstructionRule()
	 * @generated
	 */
	EAttribute getRouteConstructionRule_Ignoredsignals();

	/**
	 * Returns the meta object for class
	 * '{@link safecap.config.AutoRouteConstructionRule <em>Auto Route Construction
	 * Rule</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Auto Route Construction Rule</em>'.
	 * @see safecap.config.AutoRouteConstructionRule
	 * @generated
	 */
	EClass getAutoRouteConstructionRule();

	/**
	 * Returns the meta object for class
	 * '{@link safecap.config.PathRouteConstructionRule <em>Path Route Construction
	 * Rule</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Path Route Construction Rule</em>'.
	 * @see safecap.config.PathRouteConstructionRule
	 * @generated
	 */
	EClass getPathRouteConstructionRule();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.PathRouteConstructionRule#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Entry</em>'.
	 * @see safecap.config.PathRouteConstructionRule#getEntry()
	 * @see #getPathRouteConstructionRule()
	 * @generated
	 */
	EAttribute getPathRouteConstructionRule_Entry();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.config.PathRouteConstructionRule#getExit <em>Exit</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Exit</em>'.
	 * @see safecap.config.PathRouteConstructionRule#getExit()
	 * @see #getPathRouteConstructionRule()
	 * @generated
	 */
	EAttribute getPathRouteConstructionRule_Exit();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link safecap.config.PathRouteConstructionRule#getPointstates
	 * <em>Pointstates</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Pointstates</em>'.
	 * @see safecap.config.PathRouteConstructionRule#getPointstates()
	 * @see #getPathRouteConstructionRule()
	 * @generated
	 */
	EAttribute getPathRouteConstructionRule_Pointstates();

	/**
	 * Returns the meta object for class
	 * '{@link safecap.config.RouteBuilderConfiguration <em>Route Builder
	 * Configuration</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Route Builder Configuration</em>'.
	 * @see safecap.config.RouteBuilderConfiguration
	 * @generated
	 */
	EClass getRouteBuilderConfiguration();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.config.RouteBuilderConfiguration#getRoutes <em>Routes</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Routes</em>'.
	 * @see safecap.config.RouteBuilderConfiguration#getRoutes()
	 * @see #getRouteBuilderConfiguration()
	 * @generated
	 */
	EReference getRouteBuilderConfiguration_Routes();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ConfigFactory getConfigFactory();

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
		 * '{@link safecap.config.impl.SchemaConfigurationImpl <em>Schema
		 * Configuration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.config.impl.SchemaConfigurationImpl
		 * @see safecap.config.impl.ConfigPackageImpl#getSchemaConfiguration()
		 * @generated
		 */
		EClass SCHEMA_CONFIGURATION = eINSTANCE.getSchemaConfiguration();

		/**
		 * The meta object literal for the '<em><b>Wn Signal</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SCHEMA_CONFIGURATION__WN_SIGNAL = eINSTANCE.getSchemaConfiguration_WnSignal();

		/**
		 * The meta object literal for the '<em><b>Wn Track</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SCHEMA_CONFIGURATION__WN_TRACK = eINSTANCE.getSchemaConfiguration_WnTrack();

		/**
		 * The meta object literal for the '<em><b>Mergedambits</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SCHEMA_CONFIGURATION__MERGEDAMBITS = eINSTANCE.getSchemaConfiguration_Mergedambits();

		/**
		 * The meta object literal for the '<em><b>Mergedpoints</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SCHEMA_CONFIGURATION__MERGEDPOINTS = eINSTANCE.getSchemaConfiguration_Mergedpoints();

		/**
		 * The meta object literal for the '<em><b>Normpoint</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SCHEMA_CONFIGURATION__NORMPOINT = eINSTANCE.getSchemaConfiguration_Normpoint();

		/**
		 * The meta object literal for the '<em><b>Normtrack</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SCHEMA_CONFIGURATION__NORMTRACK = eINSTANCE.getSchemaConfiguration_Normtrack();

		/**
		 * The meta object literal for the '<em><b>Normsignal</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SCHEMA_CONFIGURATION__NORMSIGNAL = eINSTANCE.getSchemaConfiguration_Normsignal();

		/**
		 * The meta object literal for the '<em><b>Overlaps</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SCHEMA_CONFIGURATION__OVERLAPS = eINSTANCE.getSchemaConfiguration_Overlaps();

		/**
		 * The meta object literal for the
		 * '{@link safecap.config.impl.MergedAmbitsConfigurationImpl <em>Merged Ambits
		 * Configuration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.config.impl.MergedAmbitsConfigurationImpl
		 * @see safecap.config.impl.ConfigPackageImpl#getMergedAmbitsConfiguration()
		 * @generated
		 */
		EClass MERGED_AMBITS_CONFIGURATION = eINSTANCE.getMergedAmbitsConfiguration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MERGED_AMBITS_CONFIGURATION__NAME = eINSTANCE.getMergedAmbitsConfiguration_Name();

		/**
		 * The meta object literal for the '<em><b>Delimiter</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MERGED_AMBITS_CONFIGURATION__DELIMITER = eINSTANCE.getMergedAmbitsConfiguration_Delimiter();

		/**
		 * The meta object literal for the '<em><b>Code</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MERGED_AMBITS_CONFIGURATION__CODE = eINSTANCE.getMergedAmbitsConfiguration_Code();

		/**
		 * The meta object literal for the
		 * '{@link safecap.config.impl.MergedPointsConfigurationImpl <em>Merged Points
		 * Configuration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.config.impl.MergedPointsConfigurationImpl
		 * @see safecap.config.impl.ConfigPackageImpl#getMergedPointsConfiguration()
		 * @generated
		 */
		EClass MERGED_POINTS_CONFIGURATION = eINSTANCE.getMergedPointsConfiguration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MERGED_POINTS_CONFIGURATION__NAME = eINSTANCE.getMergedPointsConfiguration_Name();

		/**
		 * The meta object literal for the '<em><b>Delimiter</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MERGED_POINTS_CONFIGURATION__DELIMITER = eINSTANCE.getMergedPointsConfiguration_Delimiter();

		/**
		 * The meta object literal for the '<em><b>Code</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MERGED_POINTS_CONFIGURATION__CODE = eINSTANCE.getMergedPointsConfiguration_Code();

		/**
		 * The meta object literal for the
		 * '{@link safecap.config.impl.NormalisationRuleImpl <em>Normalisation
		 * Rule</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.config.impl.NormalisationRuleImpl
		 * @see safecap.config.impl.ConfigPackageImpl#getNormalisationRule()
		 * @generated
		 */
		EClass NORMALISATION_RULE = eINSTANCE.getNormalisationRule();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute NORMALISATION_RULE__PATTERN = eINSTANCE.getNormalisationRule_Pattern();

		/**
		 * The meta object literal for the '<em><b>Template</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute NORMALISATION_RULE__TEMPLATE = eINSTANCE.getNormalisationRule_Template();

		/**
		 * The meta object literal for the
		 * '{@link safecap.config.impl.AutoOverlapsConfigurationImpl <em>Auto Overlaps
		 * Configuration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.config.impl.AutoOverlapsConfigurationImpl
		 * @see safecap.config.impl.ConfigPackageImpl#getAutoOverlapsConfiguration()
		 * @generated
		 */
		EClass AUTO_OVERLAPS_CONFIGURATION = eINSTANCE.getAutoOverlapsConfiguration();

		/**
		 * The meta object literal for the '<em><b>Min Length</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute AUTO_OVERLAPS_CONFIGURATION__MIN_LENGTH = eINSTANCE.getAutoOverlapsConfiguration_MinLength();

		/**
		 * The meta object literal for the '<em><b>Max Length</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute AUTO_OVERLAPS_CONFIGURATION__MAX_LENGTH = eINSTANCE.getAutoOverlapsConfiguration_MaxLength();

		/**
		 * The meta object literal for the
		 * '{@link safecap.config.impl.RouteConstructionRuleImpl <em>Route Construction
		 * Rule</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.config.impl.RouteConstructionRuleImpl
		 * @see safecap.config.impl.ConfigPackageImpl#getRouteConstructionRule()
		 * @generated
		 */
		EClass ROUTE_CONSTRUCTION_RULE = eINSTANCE.getRouteConstructionRule();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ROUTE_CONSTRUCTION_RULE__NAME = eINSTANCE.getRouteConstructionRule_Name();

		/**
		 * The meta object literal for the '<em><b>Ignoredsignals</b></em>' attribute
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ROUTE_CONSTRUCTION_RULE__IGNOREDSIGNALS = eINSTANCE.getRouteConstructionRule_Ignoredsignals();

		/**
		 * The meta object literal for the
		 * '{@link safecap.config.impl.AutoRouteConstructionRuleImpl <em>Auto Route
		 * Construction Rule</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.config.impl.AutoRouteConstructionRuleImpl
		 * @see safecap.config.impl.ConfigPackageImpl#getAutoRouteConstructionRule()
		 * @generated
		 */
		EClass AUTO_ROUTE_CONSTRUCTION_RULE = eINSTANCE.getAutoRouteConstructionRule();

		/**
		 * The meta object literal for the
		 * '{@link safecap.config.impl.PathRouteConstructionRuleImpl <em>Path Route
		 * Construction Rule</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.config.impl.PathRouteConstructionRuleImpl
		 * @see safecap.config.impl.ConfigPackageImpl#getPathRouteConstructionRule()
		 * @generated
		 */
		EClass PATH_ROUTE_CONSTRUCTION_RULE = eINSTANCE.getPathRouteConstructionRule();

		/**
		 * The meta object literal for the '<em><b>Entry</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PATH_ROUTE_CONSTRUCTION_RULE__ENTRY = eINSTANCE.getPathRouteConstructionRule_Entry();

		/**
		 * The meta object literal for the '<em><b>Exit</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PATH_ROUTE_CONSTRUCTION_RULE__EXIT = eINSTANCE.getPathRouteConstructionRule_Exit();

		/**
		 * The meta object literal for the '<em><b>Pointstates</b></em>' attribute list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PATH_ROUTE_CONSTRUCTION_RULE__POINTSTATES = eINSTANCE.getPathRouteConstructionRule_Pointstates();

		/**
		 * The meta object literal for the
		 * '{@link safecap.config.impl.RouteBuilderConfigurationImpl <em>Route Builder
		 * Configuration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.config.impl.RouteBuilderConfigurationImpl
		 * @see safecap.config.impl.ConfigPackageImpl#getRouteBuilderConfiguration()
		 * @generated
		 */
		EClass ROUTE_BUILDER_CONFIGURATION = eINSTANCE.getRouteBuilderConfiguration();

		/**
		 * The meta object literal for the '<em><b>Routes</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROUTE_BUILDER_CONFIGURATION__ROUTES = eINSTANCE.getRouteBuilderConfiguration_Routes();

	}

} // ConfigPackage
