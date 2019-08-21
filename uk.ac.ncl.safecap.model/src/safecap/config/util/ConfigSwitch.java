/**
 */
package safecap.config.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

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
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see safecap.config.ConfigPackage
 * @generated
 */
public class ConfigSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static ConfigPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public ConfigSwitch() {
		if (modelPackage == null) {
			modelPackage = ConfigPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a
	 * non null result; it yields that result. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case ConfigPackage.SCHEMA_CONFIGURATION: {
			final SchemaConfiguration schemaConfiguration = (SchemaConfiguration) theEObject;
			T result = caseSchemaConfiguration(schemaConfiguration);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ConfigPackage.MERGED_AMBITS_CONFIGURATION: {
			final MergedAmbitsConfiguration mergedAmbitsConfiguration = (MergedAmbitsConfiguration) theEObject;
			T result = caseMergedAmbitsConfiguration(mergedAmbitsConfiguration);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ConfigPackage.MERGED_POINTS_CONFIGURATION: {
			final MergedPointsConfiguration mergedPointsConfiguration = (MergedPointsConfiguration) theEObject;
			T result = caseMergedPointsConfiguration(mergedPointsConfiguration);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ConfigPackage.NORMALISATION_RULE: {
			final NormalisationRule normalisationRule = (NormalisationRule) theEObject;
			T result = caseNormalisationRule(normalisationRule);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ConfigPackage.AUTO_OVERLAPS_CONFIGURATION: {
			final AutoOverlapsConfiguration autoOverlapsConfiguration = (AutoOverlapsConfiguration) theEObject;
			T result = caseAutoOverlapsConfiguration(autoOverlapsConfiguration);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ConfigPackage.ROUTE_CONSTRUCTION_RULE: {
			final RouteConstructionRule routeConstructionRule = (RouteConstructionRule) theEObject;
			T result = caseRouteConstructionRule(routeConstructionRule);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ConfigPackage.AUTO_ROUTE_CONSTRUCTION_RULE: {
			final AutoRouteConstructionRule autoRouteConstructionRule = (AutoRouteConstructionRule) theEObject;
			T result = caseAutoRouteConstructionRule(autoRouteConstructionRule);
			if (result == null) {
				result = caseRouteConstructionRule(autoRouteConstructionRule);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE: {
			final PathRouteConstructionRule pathRouteConstructionRule = (PathRouteConstructionRule) theEObject;
			T result = casePathRouteConstructionRule(pathRouteConstructionRule);
			if (result == null) {
				result = caseRouteConstructionRule(pathRouteConstructionRule);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ConfigPackage.ROUTE_BUILDER_CONFIGURATION: {
			final RouteBuilderConfiguration routeBuilderConfiguration = (RouteBuilderConfiguration) theEObject;
			T result = caseRouteBuilderConfiguration(routeBuilderConfiguration);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Schema
	 * Configuration</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Schema
	 *         Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSchemaConfiguration(SchemaConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Merged
	 * Ambits Configuration</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merged
	 *         Ambits Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergedAmbitsConfiguration(MergedAmbitsConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Merged
	 * Points Configuration</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merged
	 *         Points Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergedPointsConfiguration(MergedPointsConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Normalisation Rule</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Normalisation Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNormalisationRule(NormalisationRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Auto
	 * Overlaps Configuration</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Auto
	 *         Overlaps Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAutoOverlapsConfiguration(AutoOverlapsConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Route
	 * Construction Rule</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Route
	 *         Construction Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRouteConstructionRule(RouteConstructionRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Auto
	 * Route Construction Rule</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Auto
	 *         Route Construction Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAutoRouteConstructionRule(AutoRouteConstructionRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Path
	 * Route Construction Rule</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Path
	 *         Route Construction Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePathRouteConstructionRule(PathRouteConstructionRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Route
	 * Builder Configuration</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Route
	 *         Builder Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRouteBuilderConfiguration(RouteBuilderConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>EObject</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last
	 * case anyway. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // ConfigSwitch
