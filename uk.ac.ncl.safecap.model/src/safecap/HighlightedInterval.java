/**
 */
package safecap;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Highlighted Interval</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.HighlightedInterval#getStyle <em>Style</em>}</li>
 * <li>{@link safecap.HighlightedInterval#getLabel <em>Label</em>}</li>
 * <li>{@link safecap.HighlightedInterval#getFrom <em>From</em>}</li>
 * <li>{@link safecap.HighlightedInterval#getTo <em>To</em>}</li>
 * <li>{@link safecap.HighlightedInterval#getOwner <em>Owner</em>}</li>
 * <li>{@link safecap.HighlightedInterval#getIndex <em>Index</em>}</li>
 * <li>{@link safecap.HighlightedInterval#getVoffset <em>Voffset</em>}</li>
 * </ul>
 *
 * @see safecap.SafecapPackage#getHighlightedInterval()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface HighlightedInterval extends ESerializableObject {
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
	 * @see safecap.SafecapPackage#getHighlightedInterval_Style()
	 * @model transient="true"
	 * @generated
	 */
	Style getStyle();

	/**
	 * Sets the value of the '{@link safecap.HighlightedInterval#getStyle
	 * <em>Style</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * @see safecap.SafecapPackage#getHighlightedInterval_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link safecap.HighlightedInterval#getLabel
	 * <em>Label</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>From</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>From</em>' attribute.
	 * @see #setFrom(double)
	 * @see safecap.SafecapPackage#getHighlightedInterval_From()
	 * @model
	 * @generated
	 */
	double getFrom();

	/**
	 * Sets the value of the '{@link safecap.HighlightedInterval#getFrom
	 * <em>From</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>From</em>' attribute.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(double value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' attribute. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>To</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>To</em>' attribute.
	 * @see #setTo(double)
	 * @see safecap.SafecapPackage#getHighlightedInterval_To()
	 * @model
	 * @generated
	 */
	double getTo();

	/**
	 * Sets the value of the '{@link safecap.HighlightedInterval#getTo <em>To</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>To</em>' attribute.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(double value);

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
	 * @see safecap.SafecapPackage#getHighlightedInterval_Owner()
	 * @model transient="true"
	 * @generated
	 */
	Object getOwner();

	/**
	 * Sets the value of the '{@link safecap.HighlightedInterval#getOwner
	 * <em>Owner</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * @see safecap.SafecapPackage#getHighlightedInterval_Index()
	 * @model
	 * @generated
	 */
	int getIndex();

	/**
	 * Sets the value of the '{@link safecap.HighlightedInterval#getIndex
	 * <em>Index</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(int value);

	/**
	 * Returns the value of the '<em><b>Voffset</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Voffset</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Voffset</em>' attribute.
	 * @see #setVoffset(int)
	 * @see safecap.SafecapPackage#getHighlightedInterval_Voffset()
	 * @model
	 * @generated
	 */
	int getVoffset();

	/**
	 * Sets the value of the '{@link safecap.HighlightedInterval#getVoffset
	 * <em>Voffset</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Voffset</em>' attribute.
	 * @see #getVoffset()
	 * @generated
	 */
	void setVoffset(int value);

} // HighlightedInterval
