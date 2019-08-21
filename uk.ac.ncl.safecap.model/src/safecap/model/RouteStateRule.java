/**
 */
package safecap.model;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Route
 * State Rule</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.model.RouteStateRule#getRoute <em>Route</em>}</li>
 * <li>{@link safecap.model.RouteStateRule#getState <em>State</em>}</li>
 * </ul>
 *
 * @see safecap.model.ModelPackage#getRouteStateRule()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface RouteStateRule extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Route</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Route</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Route</em>' reference.
	 * @see #setRoute(Route)
	 * @see safecap.model.ModelPackage#getRouteStateRule_Route()
	 * @model
	 * @generated
	 */
	Route getRoute();

	/**
	 * Sets the value of the '{@link safecap.model.RouteStateRule#getRoute
	 * <em>Route</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Route</em>' reference.
	 * @see #getRoute()
	 * @generated
	 */
	void setRoute(Route value);

	/**
	 * Returns the value of the '<em><b>State</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>State</em>' attribute.
	 * @see #setState(int)
	 * @see safecap.model.ModelPackage#getRouteStateRule_State()
	 * @model
	 * @generated
	 */
	int getState();

	/**
	 * Sets the value of the '{@link safecap.model.RouteStateRule#getState
	 * <em>State</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>State</em>' attribute.
	 * @see #getState()
	 * @generated
	 */
	void setState(int value);

} // RouteStateRule
