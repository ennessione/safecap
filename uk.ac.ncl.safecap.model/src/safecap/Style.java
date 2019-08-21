/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Style</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.Style#getForeground <em>Foreground</em>}</li>
 * <li>{@link safecap.Style#getBackground <em>Background</em>}</li>
 * <li>{@link safecap.Style#getLinewidth <em>Linewidth</em>}</li>
 * <li>{@link safecap.Style#getLinestyle <em>Linestyle</em>}</li>
 * </ul>
 *
 * @see safecap.SafecapPackage#getStyle()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface Style extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Foreground</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Foreground</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Foreground</em>' attribute.
	 * @see #setForeground(Object)
	 * @see safecap.SafecapPackage#getStyle_Foreground()
	 * @model
	 * @generated
	 */
	Object getForeground();

	/**
	 * Sets the value of the '{@link safecap.Style#getForeground
	 * <em>Foreground</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Foreground</em>' attribute.
	 * @see #getForeground()
	 * @generated
	 */
	void setForeground(Object value);

	/**
	 * Returns the value of the '<em><b>Background</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Background</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Background</em>' attribute.
	 * @see #setBackground(Object)
	 * @see safecap.SafecapPackage#getStyle_Background()
	 * @model
	 * @generated
	 */
	Object getBackground();

	/**
	 * Sets the value of the '{@link safecap.Style#getBackground
	 * <em>Background</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Background</em>' attribute.
	 * @see #getBackground()
	 * @generated
	 */
	void setBackground(Object value);

	/**
	 * Returns the value of the '<em><b>Linewidth</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Linewidth</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Linewidth</em>' attribute.
	 * @see #setLinewidth(int)
	 * @see safecap.SafecapPackage#getStyle_Linewidth()
	 * @model
	 * @generated
	 */
	int getLinewidth();

	/**
	 * Sets the value of the '{@link safecap.Style#getLinewidth <em>Linewidth</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Linewidth</em>' attribute.
	 * @see #getLinewidth()
	 * @generated
	 */
	void setLinewidth(int value);

	/**
	 * Returns the value of the '<em><b>Linestyle</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Linestyle</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Linestyle</em>' attribute.
	 * @see #setLinestyle(int)
	 * @see safecap.SafecapPackage#getStyle_Linestyle()
	 * @model
	 * @generated
	 */
	int getLinestyle();

	/**
	 * Sets the value of the '{@link safecap.Style#getLinestyle <em>Linestyle</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Linestyle</em>' attribute.
	 * @see #getLinestyle()
	 * @generated
	 */
	void setLinestyle(int value);

} // Style
