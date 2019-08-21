/**
 */
package safecap.model;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Timed
 * Occupation Rule</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.model.TimedOccupationRule#getAmbit <em>Ambit</em>}</li>
 * <li>{@link safecap.model.TimedOccupationRule#getTime <em>Time</em>}</li>
 * </ul>
 *
 * @see safecap.model.ModelPackage#getTimedOccupationRule()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface TimedOccupationRule extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Ambit</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ambit</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ambit</em>' reference.
	 * @see #setAmbit(Ambit)
	 * @see safecap.model.ModelPackage#getTimedOccupationRule_Ambit()
	 * @model
	 * @generated
	 */
	Ambit getAmbit();

	/**
	 * Sets the value of the '{@link safecap.model.TimedOccupationRule#getAmbit
	 * <em>Ambit</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Ambit</em>' reference.
	 * @see #getAmbit()
	 * @generated
	 */
	void setAmbit(Ambit value);

	/**
	 * Returns the value of the '<em><b>Time</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Time</em>' attribute.
	 * @see #setTime(double)
	 * @see safecap.model.ModelPackage#getTimedOccupationRule_Time()
	 * @model
	 * @generated
	 */
	double getTime();

	/**
	 * Sets the value of the '{@link safecap.model.TimedOccupationRule#getTime
	 * <em>Time</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Time</em>' attribute.
	 * @see #getTime()
	 * @generated
	 */
	void setTime(double value);

} // TimedOccupationRule
