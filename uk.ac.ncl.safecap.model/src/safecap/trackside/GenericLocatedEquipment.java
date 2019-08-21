/**
 */
package safecap.trackside;

import safecap.Orientation;
import safecap.schema.Segment;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Generic
 * Located Equipment</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.GenericLocatedEquipment#getOffset
 * <em>Offset</em>}</li>
 * <li>{@link safecap.trackside.GenericLocatedEquipment#getType
 * <em>Type</em>}</li>
 * <li>{@link safecap.trackside.GenericLocatedEquipment#getSegment
 * <em>Segment</em>}</li>
 * <li>{@link safecap.trackside.GenericLocatedEquipment#getOrientation
 * <em>Orientation</em>}</li>
 * </ul>
 *
 * @see safecap.trackside.TracksidePackage#getGenericLocatedEquipment()
 * @model
 * @generated
 */
public interface GenericLocatedEquipment extends Equipment {
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
	 * @see safecap.trackside.TracksidePackage#getGenericLocatedEquipment_Offset()
	 * @model
	 * @generated
	 */
	int getOffset();

	/**
	 * Sets the value of the
	 * '{@link safecap.trackside.GenericLocatedEquipment#getOffset <em>Offset</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Offset</em>' attribute.
	 * @see #getOffset()
	 * @generated
	 */
	void setOffset(int value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see safecap.trackside.TracksidePackage#getGenericLocatedEquipment_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the
	 * '{@link safecap.trackside.GenericLocatedEquipment#getType <em>Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Segment</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Segment</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Segment</em>' reference.
	 * @see #setSegment(Segment)
	 * @see safecap.trackside.TracksidePackage#getGenericLocatedEquipment_Segment()
	 * @model
	 * @generated
	 */
	Segment getSegment();

	/**
	 * Sets the value of the
	 * '{@link safecap.trackside.GenericLocatedEquipment#getSegment
	 * <em>Segment</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Segment</em>' reference.
	 * @see #getSegment()
	 * @generated
	 */
	void setSegment(Segment value);

	/**
	 * Returns the value of the '<em><b>Orientation</b></em>' attribute. The
	 * literals are from the enumeration {@link safecap.Orientation}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Orientation</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Orientation</em>' attribute.
	 * @see safecap.Orientation
	 * @see #setOrientation(Orientation)
	 * @see safecap.trackside.TracksidePackage#getGenericLocatedEquipment_Orientation()
	 * @model
	 * @generated
	 */
	Orientation getOrientation();

	/**
	 * Sets the value of the
	 * '{@link safecap.trackside.GenericLocatedEquipment#getOrientation
	 * <em>Orientation</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Orientation</em>' attribute.
	 * @see safecap.Orientation
	 * @see #getOrientation()
	 * @generated
	 */
	void setOrientation(Orientation value);

} // GenericLocatedEquipment
