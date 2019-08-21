/**
 */
package safecap;

import org.eclipse.emf.common.util.EList;

import safecap.extension.ExtAttribute;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Extensible</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.Extensible#getAttributes <em>Attributes</em>}</li>
 * </ul>
 *
 * @see safecap.SafecapPackage#getExtensible()
 * @model abstract="true"
 * @generated
 */
public interface Extensible extends TransientValues {
	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference
	 * list. The list contents are of type {@link safecap.extension.ExtAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see safecap.SafecapPackage#getExtensible_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<ExtAttribute> getAttributes();

} // Extensible
