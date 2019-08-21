/**
 */
package safecap.config.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import safecap.config.AutoOverlapsConfiguration;
import safecap.config.AutoRouteConstructionRule;
import safecap.config.ConfigPackage;
import safecap.config.MergedAmbitsConfiguration;
import safecap.config.MergedPointsConfiguration;
import safecap.config.NormalisationRule;
import safecap.config.PathRouteConstructionRule;
import safecap.config.RouteBuilderConfiguration;
import safecap.config.RouteConstructionRule;
import safecap.config.SchemaConfiguration;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * 
 * @see safecap.config.ConfigPackage
 * @generated
 */
public class ConfigAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static ConfigPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public ConfigAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ConfigPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object. <!--
	 * begin-user-doc --> This implementation returns <code>true</code> if the
	 * object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ConfigSwitch<Adapter> modelSwitch = new ConfigSwitch<Adapter>() {
		@Override
		public Adapter caseSchemaConfiguration(SchemaConfiguration object) {
			return createSchemaConfigurationAdapter();
		}

		@Override
		public Adapter caseMergedAmbitsConfiguration(MergedAmbitsConfiguration object) {
			return createMergedAmbitsConfigurationAdapter();
		}

		@Override
		public Adapter caseMergedPointsConfiguration(MergedPointsConfiguration object) {
			return createMergedPointsConfigurationAdapter();
		}

		@Override
		public Adapter caseNormalisationRule(NormalisationRule object) {
			return createNormalisationRuleAdapter();
		}

		@Override
		public Adapter caseAutoOverlapsConfiguration(AutoOverlapsConfiguration object) {
			return createAutoOverlapsConfigurationAdapter();
		}

		@Override
		public Adapter caseRouteConstructionRule(RouteConstructionRule object) {
			return createRouteConstructionRuleAdapter();
		}

		@Override
		public Adapter caseAutoRouteConstructionRule(AutoRouteConstructionRule object) {
			return createAutoRouteConstructionRuleAdapter();
		}

		@Override
		public Adapter casePathRouteConstructionRule(PathRouteConstructionRule object) {
			return createPathRouteConstructionRuleAdapter();
		}

		@Override
		public Adapter caseRouteBuilderConfiguration(RouteBuilderConfiguration object) {
			return createRouteBuilderConfigurationAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.config.SchemaConfiguration <em>Schema Configuration</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will
	 * catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.config.SchemaConfiguration
	 * @generated
	 */
	public Adapter createSchemaConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.config.MergedAmbitsConfiguration <em>Merged Ambits
	 * Configuration</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.config.MergedAmbitsConfiguration
	 * @generated
	 */
	public Adapter createMergedAmbitsConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.config.MergedPointsConfiguration <em>Merged Points
	 * Configuration</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.config.MergedPointsConfiguration
	 * @generated
	 */
	public Adapter createMergedPointsConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.config.NormalisationRule <em>Normalisation Rule</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.config.NormalisationRule
	 * @generated
	 */
	public Adapter createNormalisationRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.config.AutoOverlapsConfiguration <em>Auto Overlaps
	 * Configuration</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.config.AutoOverlapsConfiguration
	 * @generated
	 */
	public Adapter createAutoOverlapsConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.config.RouteConstructionRule <em>Route Construction
	 * Rule</em>}'. <!-- begin-user-doc --> This default implementation returns null
	 * so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.config.RouteConstructionRule
	 * @generated
	 */
	public Adapter createRouteConstructionRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.config.AutoRouteConstructionRule <em>Auto Route Construction
	 * Rule</em>}'. <!-- begin-user-doc --> This default implementation returns null
	 * so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.config.AutoRouteConstructionRule
	 * @generated
	 */
	public Adapter createAutoRouteConstructionRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.config.PathRouteConstructionRule <em>Path Route Construction
	 * Rule</em>}'. <!-- begin-user-doc --> This default implementation returns null
	 * so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.config.PathRouteConstructionRule
	 * @generated
	 */
	public Adapter createPathRouteConstructionRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.config.RouteBuilderConfiguration <em>Route Builder
	 * Configuration</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.config.RouteBuilderConfiguration
	 * @generated
	 */
	public Adapter createRouteBuilderConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This
	 * default implementation returns null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // ConfigAdapterFactory
