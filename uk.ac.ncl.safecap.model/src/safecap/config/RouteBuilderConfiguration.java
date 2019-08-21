/**
 */
package safecap.config;

import org.eclipse.emf.common.util.EList;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Route
 * Builder Configuration</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.config.RouteBuilderConfiguration#getRoutes
 * <em>Routes</em>}</li>
 * </ul>
 *
 * @see safecap.config.ConfigPackage#getRouteBuilderConfiguration()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface RouteBuilderConfiguration extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Routes</b></em>' containment reference list.
	 * The list contents are of type {@link safecap.config.RouteConstructionRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Routes</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Routes</em>' containment reference list.
	 * @see safecap.config.ConfigPackage#getRouteBuilderConfiguration_Routes()
	 * @model containment="true"
	 * @generated
	 */
	EList<RouteConstructionRule> getRoutes();

} // RouteBuilderConfiguration
