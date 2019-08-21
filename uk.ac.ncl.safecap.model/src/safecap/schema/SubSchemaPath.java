/**
 */
package safecap.schema;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Sub
 * Schema Path</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.SubSchemaPath#getLength <em>Length</em>}</li>
 * <li>{@link safecap.schema.SubSchemaPath#getFrom <em>From</em>}</li>
 * <li>{@link safecap.schema.SubSchemaPath#getTo <em>To</em>}</li>
 * <li>{@link safecap.schema.SubSchemaPath#getPnormal <em>Pnormal</em>}</li>
 * <li>{@link safecap.schema.SubSchemaPath#getPreverse <em>Preverse</em>}</li>
 * </ul>
 *
 * @see safecap.schema.SchemaPackage#getSubSchemaPath()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface SubSchemaPath extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>From</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>From</em>' attribute.
	 * @see #setFrom(String)
	 * @see safecap.schema.SchemaPackage#getSubSchemaPath_From()
	 * @model
	 * @generated
	 */
	String getFrom();

	/**
	 * Sets the value of the '{@link safecap.schema.SubSchemaPath#getFrom
	 * <em>From</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>From</em>' attribute.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(String value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' attribute. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>To</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>To</em>' attribute.
	 * @see #setTo(String)
	 * @see safecap.schema.SchemaPackage#getSubSchemaPath_To()
	 * @model
	 * @generated
	 */
	String getTo();

	/**
	 * Sets the value of the '{@link safecap.schema.SubSchemaPath#getTo
	 * <em>To</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>To</em>' attribute.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(String value);

	/**
	 * Returns the value of the '<em><b>Pnormal</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pnormal</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Pnormal</em>' attribute.
	 * @see #setPnormal(int)
	 * @see safecap.schema.SchemaPackage#getSubSchemaPath_Pnormal()
	 * @model
	 * @generated
	 */
	int getPnormal();

	/**
	 * Sets the value of the '{@link safecap.schema.SubSchemaPath#getPnormal
	 * <em>Pnormal</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Pnormal</em>' attribute.
	 * @see #getPnormal()
	 * @generated
	 */
	void setPnormal(int value);

	/**
	 * Returns the value of the '<em><b>Preverse</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Preverse</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Preverse</em>' attribute.
	 * @see #setPreverse(int)
	 * @see safecap.schema.SchemaPackage#getSubSchemaPath_Preverse()
	 * @model
	 * @generated
	 */
	int getPreverse();

	/**
	 * Sets the value of the '{@link safecap.schema.SubSchemaPath#getPreverse
	 * <em>Preverse</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Preverse</em>' attribute.
	 * @see #getPreverse()
	 * @generated
	 */
	void setPreverse(int value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute. The default
	 * value is <code>"0"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(int)
	 * @see safecap.schema.SchemaPackage#getSubSchemaPath_Length()
	 * @model default="0"
	 * @generated
	 */
	int getLength();

	/**
	 * Sets the value of the '{@link safecap.schema.SubSchemaPath#getLength
	 * <em>Length</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(int value);

} // SubSchemaPath
