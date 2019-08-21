/**
 */
package safecap.extension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.extension.CustomColour;
import safecap.extension.ExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Custom
 * Colour</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.extension.impl.CustomColourImpl#getRed <em>Red</em>}</li>
 * <li>{@link safecap.extension.impl.CustomColourImpl#getGreen
 * <em>Green</em>}</li>
 * <li>{@link safecap.extension.impl.CustomColourImpl#getBlue
 * <em>Blue</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CustomColourImpl extends EObjectImpl implements CustomColour {
	/**
	 * The default value of the '{@link #getRed() <em>Red</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRed()
	 * @generated
	 * @ordered
	 */
	protected static final int RED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getRed() <em>Red</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRed()
	 * @generated
	 * @ordered
	 */
	protected int red = RED_EDEFAULT;

	/**
	 * The default value of the '{@link #getGreen() <em>Green</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getGreen()
	 * @generated
	 * @ordered
	 */
	protected static final int GREEN_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getGreen() <em>Green</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getGreen()
	 * @generated
	 * @ordered
	 */
	protected int green = GREEN_EDEFAULT;

	/**
	 * The default value of the '{@link #getBlue() <em>Blue</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBlue()
	 * @generated
	 * @ordered
	 */
	protected static final int BLUE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getBlue() <em>Blue</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBlue()
	 * @generated
	 * @ordered
	 */
	protected int blue = BLUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CustomColourImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtensionPackage.Literals.CUSTOM_COLOUR;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getRed() {
		return red;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setRed(int newRed) {
		final int oldRed = red;
		red = newRed;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ExtensionPackage.CUSTOM_COLOUR__RED, oldRed, red));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getGreen() {
		return green;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setGreen(int newGreen) {
		final int oldGreen = green;
		green = newGreen;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ExtensionPackage.CUSTOM_COLOUR__GREEN, oldGreen, green));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getBlue() {
		return blue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setBlue(int newBlue) {
		final int oldBlue = blue;
		blue = newBlue;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ExtensionPackage.CUSTOM_COLOUR__BLUE, oldBlue, blue));
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
		case ExtensionPackage.CUSTOM_COLOUR__RED:
			return getRed();
		case ExtensionPackage.CUSTOM_COLOUR__GREEN:
			return getGreen();
		case ExtensionPackage.CUSTOM_COLOUR__BLUE:
			return getBlue();
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
		case ExtensionPackage.CUSTOM_COLOUR__RED:
			setRed((Integer) newValue);
			return;
		case ExtensionPackage.CUSTOM_COLOUR__GREEN:
			setGreen((Integer) newValue);
			return;
		case ExtensionPackage.CUSTOM_COLOUR__BLUE:
			setBlue((Integer) newValue);
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
		case ExtensionPackage.CUSTOM_COLOUR__RED:
			setRed(RED_EDEFAULT);
			return;
		case ExtensionPackage.CUSTOM_COLOUR__GREEN:
			setGreen(GREEN_EDEFAULT);
			return;
		case ExtensionPackage.CUSTOM_COLOUR__BLUE:
			setBlue(BLUE_EDEFAULT);
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
		case ExtensionPackage.CUSTOM_COLOUR__RED:
			return red != RED_EDEFAULT;
		case ExtensionPackage.CUSTOM_COLOUR__GREEN:
			return green != GREEN_EDEFAULT;
		case ExtensionPackage.CUSTOM_COLOUR__BLUE:
			return blue != BLUE_EDEFAULT;
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
		result.append(" (red: ");
		result.append(red);
		result.append(", green: ");
		result.append(green);
		result.append(", blue: ");
		result.append(blue);
		result.append(')');
		return result.toString();
	}

} // CustomColourImpl
