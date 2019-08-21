/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema;

import safecap.Extensible;
import safecap.Labeled;
import safecap.Visual;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Node</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.Node#getAltitude <em>Altitude</em>}</li>
 * <li>{@link safecap.schema.Node#getKind <em>Kind</em>}</li>
 * <li>{@link safecap.schema.Node#getRoles <em>Roles</em>}</li>
 * <li>{@link safecap.schema.Node#getSpeedlimit <em>Speedlimit</em>}</li>
 * </ul>
 *
 * @see safecap.schema.SchemaPackage#getNode()
 * @model
 * @generated
 */
public interface Node extends Labeled, Visual, Extensible {
	/**
	 * Returns the value of the '<em><b>Altitude</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Altitude</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Altitude</em>' attribute.
	 * @see #setAltitude(int)
	 * @see safecap.schema.SchemaPackage#getNode_Altitude()
	 * @model
	 * @generated
	 */
	int getAltitude();

	/**
	 * Sets the value of the '{@link safecap.schema.Node#getAltitude
	 * <em>Altitude</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Altitude</em>' attribute.
	 * @see #getAltitude()
	 * @generated
	 */
	void setAltitude(int value);

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute. The default value
	 * is <code>"Normal"</code>. The literals are from the enumeration
	 * {@link safecap.schema.NodeKind}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see safecap.schema.NodeKind
	 * @see #setKind(NodeKind)
	 * @see safecap.schema.SchemaPackage#getNode_Kind()
	 * @model default="Normal"
	 * @generated
	 */
	NodeKind getKind();

	/**
	 * Sets the value of the '{@link safecap.schema.Node#getKind <em>Kind</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see safecap.schema.NodeKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(NodeKind value);

	/**
	 * Returns the value of the '<em><b>Roles</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Roles</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Roles</em>' attribute.
	 * @see #setRoles(int)
	 * @see safecap.schema.SchemaPackage#getNode_Roles()
	 * @model
	 * @generated
	 */
	int getRoles();

	/**
	 * Sets the value of the '{@link safecap.schema.Node#getRoles <em>Roles</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Roles</em>' attribute.
	 * @see #getRoles()
	 * @generated
	 */
	void setRoles(int value);

	/**
	 * Returns the value of the '<em><b>Speedlimit</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Speedlimit</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Speedlimit</em>' attribute.
	 * @see #setSpeedlimit(double)
	 * @see safecap.schema.SchemaPackage#getNode_Speedlimit()
	 * @model
	 * @generated
	 */
	double getSpeedlimit();

	/**
	 * Sets the value of the '{@link safecap.schema.Node#getSpeedlimit
	 * <em>Speedlimit</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Speedlimit</em>' attribute.
	 * @see #getSpeedlimit()
	 * @generated
	 */
	void setSpeedlimit(double value);

} // Node
