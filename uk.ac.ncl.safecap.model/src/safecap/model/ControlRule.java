/**
 */
package safecap.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Control
 * Rule</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link safecap.model.ControlRule#getClear <em>Clear</em>}</li>
 * <li>{@link safecap.model.ControlRule#getOccupied <em>Occupied</em>}</li>
 * <li>{@link safecap.model.ControlRule#getRouteState <em>Route State</em>}</li>
 * </ul>
 * </p>
 *
 * @see safecap.model.ModelPackage#getControlRule()
 * @model
 * @generated
 */
public interface ControlRule extends EObject {
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
	 * @see safecap.model.ModelPackage#getControlRule_Clear()
	 * @model
	 * @generated
	 */
	EList<Ambit> getClear();

	/**
	 * Returns the value of the '<em><b>Occupied</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link safecap.model.TimedOccupationRule}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Occupied</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Occupied</em>' containment reference list.
	 * @see safecap.model.ModelPackage#getControlRule_Occupied()
	 * @model containment="true"
	 * @generated
	 */
	EList<TimedOccupationRule> getOccupied();

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
	 * @see safecap.model.ModelPackage#getControlRule_RouteState()
	 * @model containment="true"
	 * @generated
	 */
	EList<RouteStateRule> getRouteState();

} // ControlRule
