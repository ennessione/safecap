/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.SafecapPackage;
import safecap.Style;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Style</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.impl.StyleImpl#getForeground <em>Foreground</em>}</li>
 * <li>{@link safecap.impl.StyleImpl#getBackground <em>Background</em>}</li>
 * <li>{@link safecap.impl.StyleImpl#getLinewidth <em>Linewidth</em>}</li>
 * <li>{@link safecap.impl.StyleImpl#getLinestyle <em>Linestyle</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StyleImpl extends EObjectImpl implements Style {
	/**
	 * The default value of the '{@link #getForeground() <em>Foreground</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getForeground()
	 * @generated
	 * @ordered
	 */
	protected static final Object FOREGROUND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getForeground() <em>Foreground</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getForeground()
	 * @generated
	 * @ordered
	 */
	protected Object foreground = FOREGROUND_EDEFAULT;

	/**
	 * The default value of the '{@link #getBackground() <em>Background</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBackground()
	 * @generated
	 * @ordered
	 */
	protected static final Object BACKGROUND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBackground() <em>Background</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBackground()
	 * @generated
	 * @ordered
	 */
	protected Object background = BACKGROUND_EDEFAULT;

	/**
	 * The default value of the '{@link #getLinewidth() <em>Linewidth</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLinewidth()
	 * @generated
	 * @ordered
	 */
	protected static final int LINEWIDTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLinewidth() <em>Linewidth</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLinewidth()
	 * @generated
	 * @ordered
	 */
	protected int linewidth = LINEWIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getLinestyle() <em>Linestyle</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLinestyle()
	 * @generated
	 * @ordered
	 */
	protected static final int LINESTYLE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLinestyle() <em>Linestyle</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLinestyle()
	 * @generated
	 * @ordered
	 */
	protected int linestyle = LINESTYLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected StyleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SafecapPackage.Literals.STYLE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getForeground() {
		return foreground;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setForeground(Object newForeground) {
		final Object oldForeground = foreground;
		foreground = newForeground;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.STYLE__FOREGROUND, oldForeground, foreground));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getBackground() {
		return background;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setBackground(Object newBackground) {
		final Object oldBackground = background;
		background = newBackground;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.STYLE__BACKGROUND, oldBackground, background));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getLinewidth() {
		return linewidth;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLinewidth(int newLinewidth) {
		final int oldLinewidth = linewidth;
		linewidth = newLinewidth;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.STYLE__LINEWIDTH, oldLinewidth, linewidth));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getLinestyle() {
		return linestyle;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLinestyle(int newLinestyle) {
		final int oldLinestyle = linestyle;
		linestyle = newLinestyle;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.STYLE__LINESTYLE, oldLinestyle, linestyle));
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
		case SafecapPackage.STYLE__FOREGROUND:
			return getForeground();
		case SafecapPackage.STYLE__BACKGROUND:
			return getBackground();
		case SafecapPackage.STYLE__LINEWIDTH:
			return getLinewidth();
		case SafecapPackage.STYLE__LINESTYLE:
			return getLinestyle();
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
		case SafecapPackage.STYLE__FOREGROUND:
			setForeground(newValue);
			return;
		case SafecapPackage.STYLE__BACKGROUND:
			setBackground(newValue);
			return;
		case SafecapPackage.STYLE__LINEWIDTH:
			setLinewidth((Integer) newValue);
			return;
		case SafecapPackage.STYLE__LINESTYLE:
			setLinestyle((Integer) newValue);
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
		case SafecapPackage.STYLE__FOREGROUND:
			setForeground(FOREGROUND_EDEFAULT);
			return;
		case SafecapPackage.STYLE__BACKGROUND:
			setBackground(BACKGROUND_EDEFAULT);
			return;
		case SafecapPackage.STYLE__LINEWIDTH:
			setLinewidth(LINEWIDTH_EDEFAULT);
			return;
		case SafecapPackage.STYLE__LINESTYLE:
			setLinestyle(LINESTYLE_EDEFAULT);
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
		case SafecapPackage.STYLE__FOREGROUND:
			return FOREGROUND_EDEFAULT == null ? foreground != null : !FOREGROUND_EDEFAULT.equals(foreground);
		case SafecapPackage.STYLE__BACKGROUND:
			return BACKGROUND_EDEFAULT == null ? background != null : !BACKGROUND_EDEFAULT.equals(background);
		case SafecapPackage.STYLE__LINEWIDTH:
			return linewidth != LINEWIDTH_EDEFAULT;
		case SafecapPackage.STYLE__LINESTYLE:
			return linestyle != LINESTYLE_EDEFAULT;
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
		result.append(" (foreground: ");
		result.append(foreground);
		result.append(", background: ");
		result.append(background);
		result.append(", linewidth: ");
		result.append(linewidth);
		result.append(", linestyle: ");
		result.append(linestyle);
		result.append(')');
		return result.toString();
	}

} // StyleImpl
