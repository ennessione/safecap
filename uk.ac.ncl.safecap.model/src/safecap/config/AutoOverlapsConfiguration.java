/**
 */
package safecap.config;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Auto
 * Overlaps Configuration</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.config.AutoOverlapsConfiguration#getMinLength <em>Min
 * Length</em>}</li>
 * <li>{@link safecap.config.AutoOverlapsConfiguration#getMaxLength <em>Max
 * Length</em>}</li>
 * </ul>
 *
 * @see safecap.config.ConfigPackage#getAutoOverlapsConfiguration()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface AutoOverlapsConfiguration extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Min Length</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Length</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Min Length</em>' attribute.
	 * @see #setMinLength(int)
	 * @see safecap.config.ConfigPackage#getAutoOverlapsConfiguration_MinLength()
	 * @model required="true"
	 * @generated
	 */
	int getMinLength();

	/**
	 * Sets the value of the
	 * '{@link safecap.config.AutoOverlapsConfiguration#getMinLength <em>Min
	 * Length</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Min Length</em>' attribute.
	 * @see #getMinLength()
	 * @generated
	 */
	void setMinLength(int value);

	/**
	 * Returns the value of the '<em><b>Max Length</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Length</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Max Length</em>' attribute.
	 * @see #setMaxLength(int)
	 * @see safecap.config.ConfigPackage#getAutoOverlapsConfiguration_MaxLength()
	 * @model required="true"
	 * @generated
	 */
	int getMaxLength();

	/**
	 * Sets the value of the
	 * '{@link safecap.config.AutoOverlapsConfiguration#getMaxLength <em>Max
	 * Length</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Max Length</em>' attribute.
	 * @see #getMaxLength()
	 * @generated
	 */
	void setMaxLength(int value);

} // AutoOverlapsConfiguration
