/**
 */
package safecap.extension;

import safecap.trackside.Equipment;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Ext
 * Equipment</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.extension.ExtEquipment#getVisual <em>Visual</em>}</li>
 * </ul>
 *
 * @see safecap.extension.ExtensionPackage#getExtEquipment()
 * @model
 * @generated
 */
public interface ExtEquipment extends Equipment {
	/**
	 * Returns the value of the '<em><b>Visual</b></em>' containment reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visual</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Visual</em>' containment reference.
	 * @see #setVisual(ExtVisual)
	 * @see safecap.extension.ExtensionPackage#getExtEquipment_Visual()
	 * @model containment="true"
	 * @generated
	 */
	ExtVisual getVisual();

	/**
	 * Sets the value of the '{@link safecap.extension.ExtEquipment#getVisual
	 * <em>Visual</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Visual</em>' containment reference.
	 * @see #getVisual()
	 * @generated
	 */
	void setVisual(ExtVisual value);

} // ExtEquipment
