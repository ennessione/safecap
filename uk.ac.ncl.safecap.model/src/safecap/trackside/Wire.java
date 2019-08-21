/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside;

import safecap.Extensible;
import safecap.Visual;
import safecap.schema.Segment;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Wire</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.Wire#getFrom <em>From</em>}</li>
 * <li>{@link safecap.trackside.Wire#getTo <em>To</em>}</li>
 * <li>{@link safecap.trackside.Wire#getOffset <em>Offset</em>}</li>
 * </ul>
 *
 * @see safecap.trackside.TracksidePackage#getWire()
 * @model
 * @generated
 */
public interface Wire extends Extensible, Visual {
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
	 * @see #setFrom(Equipment)
	 * @see safecap.trackside.TracksidePackage#getWire_From()
	 * @model
	 * @generated
	 */
	Equipment getFrom();

	/**
	 * Sets the value of the '{@link safecap.trackside.Wire#getFrom <em>From</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(Equipment value);

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
	 * @see #setTo(Segment)
	 * @see safecap.trackside.TracksidePackage#getWire_To()
	 * @model
	 * @generated
	 */
	Segment getTo();

	/**
	 * Sets the value of the '{@link safecap.trackside.Wire#getTo <em>To</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Segment value);

	/**
	 * Returns the value of the '<em><b>Offset</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Offset</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Offset</em>' attribute.
	 * @see #setOffset(int)
	 * @see safecap.trackside.TracksidePackage#getWire_Offset()
	 * @model
	 * @generated
	 */
	int getOffset();

	/**
	 * Sets the value of the '{@link safecap.trackside.Wire#getOffset
	 * <em>Offset</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Offset</em>' attribute.
	 * @see #getOffset()
	 * @generated
	 */
	void setOffset(int value);

} // Wire
