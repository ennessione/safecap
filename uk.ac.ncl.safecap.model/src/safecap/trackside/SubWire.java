/**
 */
package safecap.trackside;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Sub
 * Wire</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.SubWire#getFrom <em>From</em>}</li>
 * <li>{@link safecap.trackside.SubWire#getTo <em>To</em>}</li>
 * </ul>
 *
 * @see safecap.trackside.TracksidePackage#getSubWire()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface SubWire extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>From</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(SubEquipment)
	 * @see safecap.trackside.TracksidePackage#getSubWire_From()
	 * @model
	 * @generated
	 */
	SubEquipment getFrom();

	/**
	 * Sets the value of the '{@link safecap.trackside.SubWire#getFrom
	 * <em>From</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(SubEquipment value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>To</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(Equipment)
	 * @see safecap.trackside.TracksidePackage#getSubWire_To()
	 * @model
	 * @generated
	 */
	Equipment getTo();

	/**
	 * Sets the value of the '{@link safecap.trackside.SubWire#getTo <em>To</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Equipment value);

} // SubWire
