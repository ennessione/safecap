/**
 */
package safecap.config;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see safecap.config.ConfigPackage
 * @generated
 */
public interface ConfigFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	ConfigFactory eINSTANCE = safecap.config.impl.ConfigFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Schema Configuration</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Schema Configuration</em>'.
	 * @generated
	 */
	SchemaConfiguration createSchemaConfiguration();

	/**
	 * Returns a new object of class '<em>Merged Ambits Configuration</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Merged Ambits Configuration</em>'.
	 * @generated
	 */
	MergedAmbitsConfiguration createMergedAmbitsConfiguration();

	/**
	 * Returns a new object of class '<em>Merged Points Configuration</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Merged Points Configuration</em>'.
	 * @generated
	 */
	MergedPointsConfiguration createMergedPointsConfiguration();

	/**
	 * Returns a new object of class '<em>Normalisation Rule</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Normalisation Rule</em>'.
	 * @generated
	 */
	NormalisationRule createNormalisationRule();

	/**
	 * Returns a new object of class '<em>Auto Overlaps Configuration</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Auto Overlaps Configuration</em>'.
	 * @generated
	 */
	AutoOverlapsConfiguration createAutoOverlapsConfiguration();

	/**
	 * Returns a new object of class '<em>Auto Route Construction Rule</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Auto Route Construction Rule</em>'.
	 * @generated
	 */
	AutoRouteConstructionRule createAutoRouteConstructionRule();

	/**
	 * Returns a new object of class '<em>Path Route Construction Rule</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Path Route Construction Rule</em>'.
	 * @generated
	 */
	PathRouteConstructionRule createPathRouteConstructionRule();

	/**
	 * Returns a new object of class '<em>Route Builder Configuration</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Route Builder Configuration</em>'.
	 * @generated
	 */
	RouteBuilderConfiguration createRouteBuilderConfiguration();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	ConfigPackage getConfigPackage();

} // ConfigFactory
