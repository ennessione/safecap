/**
 */
package safecap.extension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.extension.CustomColour;
import safecap.extension.CustomFigure;
import safecap.extension.ExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Custom
 * Figure</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.extension.impl.CustomFigureImpl#isFilled
 * <em>Filled</em>}</li>
 * <li>{@link safecap.extension.impl.CustomFigureImpl#getForeground
 * <em>Foreground</em>}</li>
 * <li>{@link safecap.extension.impl.CustomFigureImpl#getBackground
 * <em>Background</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class CustomFigureImpl extends EObjectImpl implements CustomFigure {
	/**
	 * The default value of the '{@link #isFilled() <em>Filled</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isFilled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FILLED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFilled() <em>Filled</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isFilled()
	 * @generated
	 * @ordered
	 */
	protected boolean filled = FILLED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getForeground() <em>Foreground</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getForeground()
	 * @generated
	 * @ordered
	 */
	protected CustomColour foreground;

	/**
	 * The cached value of the '{@link #getBackground() <em>Background</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBackground()
	 * @generated
	 * @ordered
	 */
	protected CustomColour background;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CustomFigureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtensionPackage.Literals.CUSTOM_FIGURE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isFilled() {
		return filled;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setFilled(boolean newFilled) {
		final boolean oldFilled = filled;
		filled = newFilled;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ExtensionPackage.CUSTOM_FIGURE__FILLED, oldFilled, filled));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CustomColour getForeground() {
		if (foreground != null && ((EObject) foreground).eIsProxy()) {
			final InternalEObject oldForeground = (InternalEObject) foreground;
			foreground = (CustomColour) eResolveProxy(oldForeground);
			if (foreground != oldForeground) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ExtensionPackage.CUSTOM_FIGURE__FOREGROUND, oldForeground,
							foreground));
				}
			}
		}
		return foreground;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CustomColour basicGetForeground() {
		return foreground;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setForeground(CustomColour newForeground) {
		final CustomColour oldForeground = foreground;
		foreground = newForeground;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ExtensionPackage.CUSTOM_FIGURE__FOREGROUND, oldForeground, foreground));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CustomColour getBackground() {
		if (background != null && ((EObject) background).eIsProxy()) {
			final InternalEObject oldBackground = (InternalEObject) background;
			background = (CustomColour) eResolveProxy(oldBackground);
			if (background != oldBackground) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ExtensionPackage.CUSTOM_FIGURE__BACKGROUND, oldBackground,
							background));
				}
			}
		}
		return background;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CustomColour basicGetBackground() {
		return background;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setBackground(CustomColour newBackground) {
		final CustomColour oldBackground = background;
		background = newBackground;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ExtensionPackage.CUSTOM_FIGURE__BACKGROUND, oldBackground, background));
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
		case ExtensionPackage.CUSTOM_FIGURE__FILLED:
			return isFilled();
		case ExtensionPackage.CUSTOM_FIGURE__FOREGROUND:
			if (resolve) {
				return getForeground();
			}
			return basicGetForeground();
		case ExtensionPackage.CUSTOM_FIGURE__BACKGROUND:
			if (resolve) {
				return getBackground();
			}
			return basicGetBackground();
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
		case ExtensionPackage.CUSTOM_FIGURE__FILLED:
			setFilled((Boolean) newValue);
			return;
		case ExtensionPackage.CUSTOM_FIGURE__FOREGROUND:
			setForeground((CustomColour) newValue);
			return;
		case ExtensionPackage.CUSTOM_FIGURE__BACKGROUND:
			setBackground((CustomColour) newValue);
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
		case ExtensionPackage.CUSTOM_FIGURE__FILLED:
			setFilled(FILLED_EDEFAULT);
			return;
		case ExtensionPackage.CUSTOM_FIGURE__FOREGROUND:
			setForeground((CustomColour) null);
			return;
		case ExtensionPackage.CUSTOM_FIGURE__BACKGROUND:
			setBackground((CustomColour) null);
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
		case ExtensionPackage.CUSTOM_FIGURE__FILLED:
			return filled != FILLED_EDEFAULT;
		case ExtensionPackage.CUSTOM_FIGURE__FOREGROUND:
			return foreground != null;
		case ExtensionPackage.CUSTOM_FIGURE__BACKGROUND:
			return background != null;
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
		result.append(" (filled: ");
		result.append(filled);
		result.append(')');
		return result.toString();
	}

} // CustomFigureImpl
