/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.model;

import safecap.Extensible;
import safecap.schema.Node;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Point</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.model.Point#getNode <em>Node</em>}</li>
 * <li>{@link safecap.model.Point#getRule <em>Rule</em>}</li>
 * </ul>
 *
 * @see safecap.model.ModelPackage#getPoint()
 * @model
 * @generated
 */
public interface Point extends Extensible {
	/**
	 * Returns the value of the '<em><b>Node</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Node</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Node</em>' reference.
	 * @see #setNode(Node)
	 * @see safecap.model.ModelPackage#getPoint_Node()
	 * @model
	 * @generated
	 */
	Node getNode();

	/**
	 * Sets the value of the '{@link safecap.model.Point#getNode <em>Node</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Node</em>' reference.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(Node value);

	/**
	 * Returns the value of the '<em><b>Rule</b></em>' containment reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Rule</em>' containment reference.
	 * @see #setRule(Rule)
	 * @see safecap.model.ModelPackage#getPoint_Rule()
	 * @model containment="true"
	 * @generated
	 */
	Rule getRule();

	/**
	 * Sets the value of the '{@link safecap.model.Point#getRule <em>Rule</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Rule</em>' containment reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(Rule value);

} // Point
