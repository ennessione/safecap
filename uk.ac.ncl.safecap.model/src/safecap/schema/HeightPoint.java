/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Height
 * Point</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.HeightPoint#getAltitude <em>Altitude</em>}</li>
 * <li>{@link safecap.schema.HeightPoint#getPosition <em>Position</em>}</li>
 * </ul>
 *
 * @see safecap.schema.SchemaPackage#getHeightPoint()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface HeightPoint extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Altitude</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Altitude</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Altitude</em>' attribute.
	 * @see #setAltitude(int)
	 * @see safecap.schema.SchemaPackage#getHeightPoint_Altitude()
	 * @model
	 * @generated
	 */
	int getAltitude();

	/**
	 * Sets the value of the '{@link safecap.schema.HeightPoint#getAltitude
	 * <em>Altitude</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Altitude</em>' attribute.
	 * @see #getAltitude()
	 * @generated
	 */
	void setAltitude(int value);

	/**
	 * Returns the value of the '<em><b>Position</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Position</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Position</em>' attribute.
	 * @see #setPosition(int)
	 * @see safecap.schema.SchemaPackage#getHeightPoint_Position()
	 * @model
	 * @generated
	 */
	int getPosition();

	/**
	 * Sets the value of the '{@link safecap.schema.HeightPoint#getPosition
	 * <em>Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Position</em>' attribute.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(int value);

} // HeightPoint
