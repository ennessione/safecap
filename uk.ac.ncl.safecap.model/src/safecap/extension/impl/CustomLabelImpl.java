/**
 */
package safecap.extension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import safecap.extension.CustomLabel;
import safecap.extension.ExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Custom
 * Label</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.extension.impl.CustomLabelImpl#getText <em>Text</em>}</li>
 * <li>{@link safecap.extension.impl.CustomLabelImpl#getFontsize
 * <em>Fontsize</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CustomLabelImpl extends CustomFigureImpl implements CustomLabel {
	/**
	 * The default value of the '{@link #getText() <em>Text</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getText()
	 * @generated
	 * @ordered
	 */
	protected static final String TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getText() <em>Text</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getText()
	 * @generated
	 * @ordered
	 */
	protected String text = TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getFontsize() <em>Fontsize</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFontsize()
	 * @generated
	 * @ordered
	 */
	protected static final int FONTSIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getFontsize() <em>Fontsize</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFontsize()
	 * @generated
	 * @ordered
	 */
	protected int fontsize = FONTSIZE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CustomLabelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtensionPackage.Literals.CUSTOM_LABEL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText() {
		return text;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setText(String newText) {
		final String oldText = text;
		text = newText;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ExtensionPackage.CUSTOM_LABEL__TEXT, oldText, text));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getFontsize() {
		return fontsize;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setFontsize(int newFontsize) {
		final int oldFontsize = fontsize;
		fontsize = newFontsize;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ExtensionPackage.CUSTOM_LABEL__FONTSIZE, oldFontsize, fontsize));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ExtensionPackage.CUSTOM_LABEL__TEXT:
			return getText();
		case ExtensionPackage.CUSTOM_LABEL__FONTSIZE:
			return getFontsize();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ExtensionPackage.CUSTOM_LABEL__TEXT:
			setText((String) newValue);
			return;
		case ExtensionPackage.CUSTOM_LABEL__FONTSIZE:
			setFontsize((Integer) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ExtensionPackage.CUSTOM_LABEL__TEXT:
			setText(TEXT_EDEFAULT);
			return;
		case ExtensionPackage.CUSTOM_LABEL__FONTSIZE:
			setFontsize(FONTSIZE_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ExtensionPackage.CUSTOM_LABEL__TEXT:
			return TEXT_EDEFAULT == null ? text != null : !TEXT_EDEFAULT.equals(text);
		case ExtensionPackage.CUSTOM_LABEL__FONTSIZE:
			return fontsize != FONTSIZE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (text: ");
		result.append(text);
		result.append(", fontsize: ");
		result.append(fontsize);
		result.append(')');
		return result.toString();
	}

} // CustomLabelImpl
