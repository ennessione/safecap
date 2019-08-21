/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.model;

import org.eclipse.emf.common.util.EList;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Model</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.model.Model#getLines <em>Lines</em>}</li>
 * <li>{@link safecap.model.Model#getRoutes <em>Routes</em>}</li>
 * <li>{@link safecap.model.Model#getAmbits <em>Ambits</em>}</li>
 * </ul>
 *
 * @see safecap.model.ModelPackage#getModel()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface Model extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Lines</b></em>' containment reference list.
	 * The list contents are of type {@link safecap.model.Line}. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Lines</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Lines</em>' containment reference list.
	 * @see safecap.model.ModelPackage#getModel_Lines()
	 * @model containment="true"
	 * @generated
	 */
	EList<Line> getLines();

	/**
	 * Returns the value of the '<em><b>Routes</b></em>' containment reference list.
	 * The list contents are of type {@link safecap.model.Route}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Routes</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Routes</em>' containment reference list.
	 * @see safecap.model.ModelPackage#getModel_Routes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Route> getRoutes();

	/**
	 * Returns the value of the '<em><b>Ambits</b></em>' containment reference list.
	 * The list contents are of type {@link safecap.model.Ambit}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ambits</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ambits</em>' containment reference list.
	 * @see safecap.model.ModelPackage#getModel_Ambits()
	 * @model containment="true"
	 * @generated
	 */
	EList<Ambit> getAmbits();

} // Model
