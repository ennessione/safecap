/**
 */
package safecap.schema;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Sub
 * Schema Node</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.SubSchemaNode#getPaths <em>Paths</em>}</li>
 * <li>{@link safecap.schema.SubSchemaNode#getChildren <em>Children</em>}</li>
 * </ul>
 *
 * @see safecap.schema.SchemaPackage#getSubSchemaNode()
 * @model
 * @generated
 */
public interface SubSchemaNode extends Node {
	/**
	 * Returns the value of the '<em><b>Paths</b></em>' containment reference list.
	 * The list contents are of type {@link safecap.schema.SubSchemaPath}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Paths</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Paths</em>' containment reference list.
	 * @see safecap.schema.SchemaPackage#getSubSchemaNode_Paths()
	 * @model containment="true"
	 * @generated
	 */
	EList<SubSchemaPath> getPaths();

	/**
	 * Returns the value of the '<em><b>Children</b></em>' attribute list. The list
	 * contents are of type {@link java.lang.String}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' attribute list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Children</em>' attribute list.
	 * @see safecap.schema.SchemaPackage#getSubSchemaNode_Children()
	 * @model
	 * @generated
	 */
	EList<String> getChildren();

} // SubSchemaNode
