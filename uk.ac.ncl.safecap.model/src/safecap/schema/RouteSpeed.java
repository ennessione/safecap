/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema;

import safecap.model.Route;
import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Route
 * Speed</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.RouteSpeed#getRoute <em>Route</em>}</li>
 * <li>{@link safecap.schema.RouteSpeed#getSpeed <em>Speed</em>}</li>
 * </ul>
 *
 * @see safecap.schema.SchemaPackage#getRouteSpeed()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface RouteSpeed extends ESerializableObject {
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
	 * @see safecap.schema.SchemaPackage#getRouteSpeed_Route()
	 * @model
	 * @generated
	 */
	Route getRoute();

	/**
	 * Sets the value of the '{@link safecap.schema.RouteSpeed#getRoute
	 * <em>Route</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Route</em>' reference.
	 * @see #getRoute()
	 * @generated
	 */
	void setRoute(Route value);

	/**
	 * Returns the value of the '<em><b>Speed</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Speed</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Speed</em>' attribute.
	 * @see #setSpeed(double)
	 * @see safecap.schema.SchemaPackage#getRouteSpeed_Speed()
	 * @model
	 * @generated
	 */
	double getSpeed();

	/**
	 * Sets the value of the '{@link safecap.schema.RouteSpeed#getSpeed
	 * <em>Speed</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Speed</em>' attribute.
	 * @see #getSpeed()
	 * @generated
	 */
	void setSpeed(double value);

} // RouteSpeed
