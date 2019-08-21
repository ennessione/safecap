/**
 */
package traintable;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Train</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link traintable.Train#getName <em>Name</em>}</li>
 * <li>{@link traintable.Train#getSpeed <em>Speed</em>}</li>
 * <li>{@link traintable.Train#getAcceleration <em>Acceleration</em>}</li>
 * <li>{@link traintable.Train#getDeceleration <em>Deceleration</em>}</li>
 * <li>{@link traintable.Train#getLength <em>Length</em>}</li>
 * <li>{@link traintable.Train#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @see traintable.TraintablePackage#getTrain()
 * @model
 * @generated
 */
public interface Train extends EObject {
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
	 * @see traintable.TraintablePackage#getTrain_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link traintable.Train#getName <em>Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Speed</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Speed</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Speed</em>' attribute.
	 * @see #setSpeed(int)
	 * @see traintable.TraintablePackage#getTrain_Speed()
	 * @model
	 * @generated
	 */
	int getSpeed();

	/**
	 * Sets the value of the '{@link traintable.Train#getSpeed <em>Speed</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Speed</em>' attribute.
	 * @see #getSpeed()
	 * @generated
	 */
	void setSpeed(int value);

	/**
	 * Returns the value of the '<em><b>Acceleration</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Acceleration</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Acceleration</em>' attribute.
	 * @see #setAcceleration(float)
	 * @see traintable.TraintablePackage#getTrain_Acceleration()
	 * @model
	 * @generated
	 */
	float getAcceleration();

	/**
	 * Sets the value of the '{@link traintable.Train#getAcceleration
	 * <em>Acceleration</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Acceleration</em>' attribute.
	 * @see #getAcceleration()
	 * @generated
	 */
	void setAcceleration(float value);

	/**
	 * Returns the value of the '<em><b>Deceleration</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deceleration</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Deceleration</em>' attribute.
	 * @see #setDeceleration(float)
	 * @see traintable.TraintablePackage#getTrain_Deceleration()
	 * @model
	 * @generated
	 */
	float getDeceleration();

	/**
	 * Sets the value of the '{@link traintable.Train#getDeceleration
	 * <em>Deceleration</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Deceleration</em>' attribute.
	 * @see #getDeceleration()
	 * @generated
	 */
	void setDeceleration(float value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(int)
	 * @see traintable.TraintablePackage#getTrain_Length()
	 * @model
	 * @generated
	 */
	int getLength();

	/**
	 * Sets the value of the '{@link traintable.Train#getLength <em>Length</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(int value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference
	 * list. The list contents are of type {@link traintable.TDAttribute}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see traintable.TraintablePackage#getTrain_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<TDAttribute> getAttributes();

} // Train
