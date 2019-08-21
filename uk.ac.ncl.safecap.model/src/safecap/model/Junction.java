/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Junction</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.model.Junction#getPoints <em>Points</em>}</li>
 * </ul>
 *
 * @see safecap.model.ModelPackage#getJunction()
 * @model
 * @generated
 */
public interface Junction extends Ambit {
	/**
	 * Returns the value of the '<em><b>Points</b></em>' containment reference list.
	 * The list contents are of type {@link safecap.model.Point}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Points</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Points</em>' containment reference list.
	 * @see safecap.model.ModelPackage#getJunction_Points()
	 * @model containment="true"
	 * @generated
	 */
	EList<Point> getPoints();

} // Junction
