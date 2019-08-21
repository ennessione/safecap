/**
 */
package safecap.extension;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Custom
 * Label</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.extension.CustomLabel#getText <em>Text</em>}</li>
 * <li>{@link safecap.extension.CustomLabel#getFontsize <em>Fontsize</em>}</li>
 * </ul>
 *
 * @see safecap.extension.ExtensionPackage#getCustomLabel()
 * @model
 * @generated
 */
public interface CustomLabel extends CustomFigure {
	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see safecap.extension.ExtensionPackage#getCustomLabel_Text()
	 * @model
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the '{@link safecap.extension.CustomLabel#getText
	 * <em>Text</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

	/**
	 * Returns the value of the '<em><b>Fontsize</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fontsize</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Fontsize</em>' attribute.
	 * @see #setFontsize(int)
	 * @see safecap.extension.ExtensionPackage#getCustomLabel_Fontsize()
	 * @model
	 * @generated
	 */
	int getFontsize();

	/**
	 * Sets the value of the '{@link safecap.extension.CustomLabel#getFontsize
	 * <em>Fontsize</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Fontsize</em>' attribute.
	 * @see #getFontsize()
	 * @generated
	 */
	void setFontsize(int value);

} // CustomLabel
