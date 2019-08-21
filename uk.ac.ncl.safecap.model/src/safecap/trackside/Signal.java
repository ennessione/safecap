/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Signal</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.Signal#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see safecap.trackside.TracksidePackage#getSignal()
 * @model abstract="true"
 * @generated
 */
public interface Signal extends Equipment {

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute. The default value
	 * is <code>"Controlled"</code>. The literals are from the enumeration
	 * {@link safecap.trackside.SignalType}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see safecap.trackside.SignalType
	 * @see #setType(SignalType)
	 * @see safecap.trackside.TracksidePackage#getSignal_Type()
	 * @model default="Controlled"
	 * @generated
	 */
	SignalType getType();

	/**
	 * Sets the value of the '{@link safecap.trackside.Signal#getType
	 * <em>Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see safecap.trackside.SignalType
	 * @see #getType()
	 * @generated
	 */
	void setType(SignalType value);
} // Signal
