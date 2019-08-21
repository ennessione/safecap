/**
 */
package safecap.config;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Merged
 * Ambits Configuration</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.config.MergedAmbitsConfiguration#getName
 * <em>Name</em>}</li>
 * <li>{@link safecap.config.MergedAmbitsConfiguration#getDelimiter
 * <em>Delimiter</em>}</li>
 * <li>{@link safecap.config.MergedAmbitsConfiguration#getCode
 * <em>Code</em>}</li>
 * </ul>
 *
 * @see safecap.config.ConfigPackage#getMergedAmbitsConfiguration()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface MergedAmbitsConfiguration extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see safecap.config.ConfigPackage#getMergedAmbitsConfiguration_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the
	 * '{@link safecap.config.MergedAmbitsConfiguration#getName <em>Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Delimiter</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delimiter</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Delimiter</em>' attribute.
	 * @see #setDelimiter(String)
	 * @see safecap.config.ConfigPackage#getMergedAmbitsConfiguration_Delimiter()
	 * @model required="true"
	 * @generated
	 */
	String getDelimiter();

	/**
	 * Sets the value of the
	 * '{@link safecap.config.MergedAmbitsConfiguration#getDelimiter
	 * <em>Delimiter</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Delimiter</em>' attribute.
	 * @see #getDelimiter()
	 * @generated
	 */
	void setDelimiter(String value);

	/**
	 * Returns the value of the '<em><b>Code</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Code</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Code</em>' attribute.
	 * @see #setCode(String)
	 * @see safecap.config.ConfigPackage#getMergedAmbitsConfiguration_Code()
	 * @model required="true"
	 * @generated
	 */
	String getCode();

	/**
	 * Sets the value of the
	 * '{@link safecap.config.MergedAmbitsConfiguration#getCode <em>Code</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Code</em>' attribute.
	 * @see #getCode()
	 * @generated
	 */
	void setCode(String value);

} // MergedAmbitsConfiguration
