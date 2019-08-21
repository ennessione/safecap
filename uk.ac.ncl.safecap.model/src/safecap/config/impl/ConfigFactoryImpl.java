/**
 */
package safecap.config.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import safecap.config.AutoOverlapsConfiguration;
import safecap.config.AutoRouteConstructionRule;
import safecap.config.ConfigFactory;
import safecap.config.ConfigPackage;
import safecap.config.MergedAmbitsConfiguration;
import safecap.config.MergedPointsConfiguration;
import safecap.config.NormalisationRule;
import safecap.config.PathRouteConstructionRule;
import safecap.config.RouteBuilderConfiguration;
import safecap.config.SchemaConfiguration;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class ConfigFactoryImpl extends EFactoryImpl implements ConfigFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static ConfigFactory init() {
		try {
			final ConfigFactory theConfigFactory = (ConfigFactory) EPackage.Registry.INSTANCE.getEFactory(ConfigPackage.eNS_URI);
			if (theConfigFactory != null) {
				return theConfigFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ConfigFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public ConfigFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case ConfigPackage.SCHEMA_CONFIGURATION:
			return createSchemaConfiguration();
		case ConfigPackage.MERGED_AMBITS_CONFIGURATION:
			return createMergedAmbitsConfiguration();
		case ConfigPackage.MERGED_POINTS_CONFIGURATION:
			return createMergedPointsConfiguration();
		case ConfigPackage.NORMALISATION_RULE:
			return createNormalisationRule();
		case ConfigPackage.AUTO_OVERLAPS_CONFIGURATION:
			return createAutoOverlapsConfiguration();
		case ConfigPackage.AUTO_ROUTE_CONSTRUCTION_RULE:
			return createAutoRouteConstructionRule();
		case ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE:
			return createPathRouteConstructionRule();
		case ConfigPackage.ROUTE_BUILDER_CONFIGURATION:
			return createRouteBuilderConfiguration();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SchemaConfiguration createSchemaConfiguration() {
		final SchemaConfigurationImpl schemaConfiguration = new SchemaConfigurationImpl();
		return schemaConfiguration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MergedAmbitsConfiguration createMergedAmbitsConfiguration() {
		final MergedAmbitsConfigurationImpl mergedAmbitsConfiguration = new MergedAmbitsConfigurationImpl();
		return mergedAmbitsConfiguration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MergedPointsConfiguration createMergedPointsConfiguration() {
		final MergedPointsConfigurationImpl mergedPointsConfiguration = new MergedPointsConfigurationImpl();
		return mergedPointsConfiguration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NormalisationRule createNormalisationRule() {
		final NormalisationRuleImpl normalisationRule = new NormalisationRuleImpl();
		return normalisationRule;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public AutoOverlapsConfiguration createAutoOverlapsConfiguration() {
		final AutoOverlapsConfigurationImpl autoOverlapsConfiguration = new AutoOverlapsConfigurationImpl();
		return autoOverlapsConfiguration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public AutoRouteConstructionRule createAutoRouteConstructionRule() {
		final AutoRouteConstructionRuleImpl autoRouteConstructionRule = new AutoRouteConstructionRuleImpl();
		return autoRouteConstructionRule;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PathRouteConstructionRule createPathRouteConstructionRule() {
		final PathRouteConstructionRuleImpl pathRouteConstructionRule = new PathRouteConstructionRuleImpl();
		return pathRouteConstructionRule;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public RouteBuilderConfiguration createRouteBuilderConfiguration() {
		final RouteBuilderConfigurationImpl routeBuilderConfiguration = new RouteBuilderConfigurationImpl();
		return routeBuilderConfiguration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ConfigPackage getConfigPackage() {
		return (ConfigPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ConfigPackage getPackage() {
		return ConfigPackage.eINSTANCE;
	}

} // ConfigFactoryImpl
