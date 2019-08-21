/**
 */
package safecap;

import java.util.Map;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Transient Values</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.TransientValues#getRuntimeAttributes <em>Runtime
 * Attributes</em>}</li>
 * </ul>
 *
 * @see safecap.SafecapPackage#getTransientValues()
 * @model abstract="true"
 * @extends ESerializableObject
 * @generated
 */
public interface TransientValues extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Runtime Attributes</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Runtime Attributes</em>' attribute.
	 * @see #setRuntimeAttributes(Map)
	 * @see safecap.SafecapPackage#getTransientValues_RuntimeAttributes()
	 * @model transient="true"
	 * @generated
	 */
	Map<String, Object> getRuntimeAttributes();

	/**
	 * Sets the value of the '{@link safecap.TransientValues#getRuntimeAttributes
	 * <em>Runtime Attributes</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Runtime Attributes</em>' attribute.
	 * @see #getRuntimeAttributes()
	 * @generated
	 */
	void setRuntimeAttributes(Map<String, Object> value);

} // TransientValues
