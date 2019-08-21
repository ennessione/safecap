/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside;

import org.eclipse.emf.common.util.EList;

import safecap.model.Line;
import safecap.model.Route;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Stop And
 * Go</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.StopAndGo#getDelay <em>Delay</em>}</li>
 * <li>{@link safecap.trackside.StopAndGo#getLine <em>Line</em>}</li>
 * <li>{@link safecap.trackside.StopAndGo#getReleaseRoute <em>Release
 * Route</em>}</li>
 * </ul>
 *
 * @see safecap.trackside.TracksidePackage#getStopAndGo()
 * @model
 * @generated
 */
public interface StopAndGo extends Equipment {
	/**
	 * Returns the value of the '<em><b>Delay</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delay</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Delay</em>' attribute.
	 * @see #setDelay(int)
	 * @see safecap.trackside.TracksidePackage#getStopAndGo_Delay()
	 * @model
	 * @generated
	 */
	int getDelay();

	/**
	 * Sets the value of the '{@link safecap.trackside.StopAndGo#getDelay
	 * <em>Delay</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Delay</em>' attribute.
	 * @see #getDelay()
	 * @generated
	 */
	void setDelay(int value);

	/**
	 * Returns the value of the '<em><b>Line</b></em>' reference list. The list
	 * contents are of type {@link safecap.model.Line}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Line</em>' reference list.
	 * @see safecap.trackside.TracksidePackage#getStopAndGo_Line()
	 * @model
	 * @generated
	 */
	EList<Line> getLine();

	/**
	 * Returns the value of the '<em><b>Release Route</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Release Route</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Release Route</em>' reference.
	 * @see #setReleaseRoute(Route)
	 * @see safecap.trackside.TracksidePackage#getStopAndGo_ReleaseRoute()
	 * @model
	 * @generated
	 */
	Route getReleaseRoute();

	/**
	 * Sets the value of the '{@link safecap.trackside.StopAndGo#getReleaseRoute
	 * <em>Release Route</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Release Route</em>' reference.
	 * @see #getReleaseRoute()
	 * @generated
	 */
	void setReleaseRoute(Route value);

} // StopAndGo
