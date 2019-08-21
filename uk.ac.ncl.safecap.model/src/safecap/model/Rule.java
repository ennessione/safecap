/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.model;

import org.eclipse.emf.common.util.EList;

import safecap.Extensible;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Rule</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.model.Rule#getClear <em>Clear</em>}</li>
 * <li>{@link safecap.model.Rule#getOccupied <em>Occupied</em>}</li>
 * <li>{@link safecap.model.Rule#getNormal <em>Normal</em>}</li>
 * <li>{@link safecap.model.Rule#getReverse <em>Reverse</em>}</li>
 * <li>{@link safecap.model.Rule#getRouteState <em>Route State</em>}</li>
 * </ul>
 *
 * @see safecap.model.ModelPackage#getRule()
 * @model
 * @generated
 */
public interface Rule extends Extensible {
	/**
	 * Returns the value of the '<em><b>Clear</b></em>' reference list. The list
	 * contents are of type {@link safecap.model.Ambit}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Clear</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Clear</em>' reference list.
	 * @see safecap.model.ModelPackage#getRule_Clear()
	 * @model
	 * @generated
	 */
	EList<Ambit> getClear();

	/**
	 * Returns the value of the '<em><b>Occupied</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link safecap.model.TimedOccupationRule}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Occupied</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Occupied</em>' containment reference list.
	 * @see safecap.model.ModelPackage#getRule_Occupied()
	 * @model containment="true"
	 * @generated
	 */
	EList<TimedOccupationRule> getOccupied();

	/**
	 * Returns the value of the '<em><b>Normal</b></em>' reference list. The list
	 * contents are of type {@link safecap.model.Point}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Normal</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Normal</em>' reference list.
	 * @see safecap.model.ModelPackage#getRule_Normal()
	 * @model
	 * @generated
	 */
	EList<Point> getNormal();

	/**
	 * Returns the value of the '<em><b>Reverse</b></em>' reference list. The list
	 * contents are of type {@link safecap.model.Point}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reverse</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Reverse</em>' reference list.
	 * @see safecap.model.ModelPackage#getRule_Reverse()
	 * @model
	 * @generated
	 */
	EList<Point> getReverse();

	/**
	 * Returns the value of the '<em><b>Route State</b></em>' containment reference
	 * list. The list contents are of type {@link safecap.model.RouteStateRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Route State</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Route State</em>' containment reference list.
	 * @see safecap.model.ModelPackage#getRule_RouteState()
	 * @model containment="true"
	 * @generated
	 */
	EList<RouteStateRule> getRouteState();

} // Rule
