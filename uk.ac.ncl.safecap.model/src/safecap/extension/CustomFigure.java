/**
 */
package safecap.extension;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Custom
 * Figure</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.extension.CustomFigure#isFilled <em>Filled</em>}</li>
 * <li>{@link safecap.extension.CustomFigure#getForeground
 * <em>Foreground</em>}</li>
 * <li>{@link safecap.extension.CustomFigure#getBackground
 * <em>Background</em>}</li>
 * </ul>
 *
 * @see safecap.extension.ExtensionPackage#getCustomFigure()
 * @model abstract="true"
 * @extends ESerializableObject
 * @generated
 */
public interface CustomFigure extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Filled</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filled</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Filled</em>' attribute.
	 * @see #setFilled(boolean)
	 * @see safecap.extension.ExtensionPackage#getCustomFigure_Filled()
	 * @model
	 * @generated
	 */
	boolean isFilled();

	/**
	 * Sets the value of the '{@link safecap.extension.CustomFigure#isFilled
	 * <em>Filled</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Filled</em>' attribute.
	 * @see #isFilled()
	 * @generated
	 */
	void setFilled(boolean value);

	/**
	 * Returns the value of the '<em><b>Foreground</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Foreground</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Foreground</em>' reference.
	 * @see #setForeground(CustomColour)
	 * @see safecap.extension.ExtensionPackage#getCustomFigure_Foreground()
	 * @model
	 * @generated
	 */
	CustomColour getForeground();

	/**
	 * Sets the value of the '{@link safecap.extension.CustomFigure#getForeground
	 * <em>Foreground</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Foreground</em>' reference.
	 * @see #getForeground()
	 * @generated
	 */
	void setForeground(CustomColour value);

	/**
	 * Returns the value of the '<em><b>Background</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Background</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Background</em>' reference.
	 * @see #setBackground(CustomColour)
	 * @see safecap.extension.ExtensionPackage#getCustomFigure_Background()
	 * @model
	 * @generated
	 */
	CustomColour getBackground();

	/**
	 * Sets the value of the '{@link safecap.extension.CustomFigure#getBackground
	 * <em>Background</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Background</em>' reference.
	 * @see #getBackground()
	 * @generated
	 */
	void setBackground(CustomColour value);

} // CustomFigure
