/**
 */
package safecap;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Visual
 * Marker</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.VisualMarker#getStyle <em>Style</em>}</li>
 * <li>{@link safecap.VisualMarker#getLabel <em>Label</em>}</li>
 * <li>{@link safecap.VisualMarker#getPosition <em>Position</em>}</li>
 * <li>{@link safecap.VisualMarker#getOwner <em>Owner</em>}</li>
 * <li>{@link safecap.VisualMarker#getIndex <em>Index</em>}</li>
 * <li>{@link safecap.VisualMarker#getOffsetLabel <em>Offset Label</em>}</li>
 * </ul>
 *
 * @see safecap.SafecapPackage#getVisualMarker()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface VisualMarker extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Style</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Style</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Style</em>' reference.
	 * @see #setStyle(Style)
	 * @see safecap.SafecapPackage#getVisualMarker_Style()
	 * @model transient="true"
	 * @generated
	 */
	Style getStyle();

	/**
	 * Sets the value of the '{@link safecap.VisualMarker#getStyle <em>Style</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Style</em>' reference.
	 * @see #getStyle()
	 * @generated
	 */
	void setStyle(Style value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see safecap.SafecapPackage#getVisualMarker_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link safecap.VisualMarker#getLabel <em>Label</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

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
	 * @see #setPosition(double)
	 * @see safecap.SafecapPackage#getVisualMarker_Position()
	 * @model
	 * @generated
	 */
	double getPosition();

	/**
	 * Sets the value of the '{@link safecap.VisualMarker#getPosition
	 * <em>Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Position</em>' attribute.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(double value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Owner</em>' attribute.
	 * @see #setOwner(Object)
	 * @see safecap.SafecapPackage#getVisualMarker_Owner()
	 * @model transient="true"
	 * @generated
	 */
	Object getOwner();

	/**
	 * Sets the value of the '{@link safecap.VisualMarker#getOwner <em>Owner</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Owner</em>' attribute.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(Object value);

	/**
	 * Returns the value of the '<em><b>Index</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Index</em>' attribute.
	 * @see #setIndex(int)
	 * @see safecap.SafecapPackage#getVisualMarker_Index()
	 * @model
	 * @generated
	 */
	int getIndex();

	/**
	 * Sets the value of the '{@link safecap.VisualMarker#getIndex <em>Index</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(int value);

	/**
	 * Returns the value of the '<em><b>Offset Label</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Offset Label</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Offset Label</em>' attribute.
	 * @see #setOffsetLabel(String)
	 * @see safecap.SafecapPackage#getVisualMarker_OffsetLabel()
	 * @model
	 * @generated
	 */
	String getOffsetLabel();

	/**
	 * Sets the value of the '{@link safecap.VisualMarker#getOffsetLabel <em>Offset
	 * Label</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Offset Label</em>' attribute.
	 * @see #getOffsetLabel()
	 * @generated
	 */
	void setOffsetLabel(String value);

} // VisualMarker
