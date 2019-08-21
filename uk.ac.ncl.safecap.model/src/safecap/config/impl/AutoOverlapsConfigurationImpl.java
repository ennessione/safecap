/**
 */
package safecap.config.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.config.AutoOverlapsConfiguration;
import safecap.config.ConfigPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Auto
 * Overlaps Configuration</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.config.impl.AutoOverlapsConfigurationImpl#getMinLength
 * <em>Min Length</em>}</li>
 * <li>{@link safecap.config.impl.AutoOverlapsConfigurationImpl#getMaxLength
 * <em>Max Length</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AutoOverlapsConfigurationImpl extends EObjectImpl implements AutoOverlapsConfiguration {
	/**
	 * The default value of the '{@link #getMinLength() <em>Min Length</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMinLength()
	 * @generated
	 * @ordered
	 */
	protected static final int MIN_LENGTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMinLength() <em>Min Length</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMinLength()
	 * @generated
	 * @ordered
	 */
	protected int minLength = MIN_LENGTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxLength() <em>Max Length</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMaxLength()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_LENGTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaxLength() <em>Max Length</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMaxLength()
	 * @generated
	 * @ordered
	 */
	protected int maxLength = MAX_LENGTH_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected AutoOverlapsConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigPackage.Literals.AUTO_OVERLAPS_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMinLength() {
		return minLength;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMinLength(int newMinLength) {
		final int oldMinLength = minLength;
		minLength = newMinLength;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.AUTO_OVERLAPS_CONFIGURATION__MIN_LENGTH, oldMinLength,
					minLength));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMaxLength(int newMaxLength) {
		final int oldMaxLength = maxLength;
		maxLength = newMaxLength;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.AUTO_OVERLAPS_CONFIGURATION__MAX_LENGTH, oldMaxLength,
					maxLength));
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
		case ConfigPackage.AUTO_OVERLAPS_CONFIGURATION__MIN_LENGTH:
			return getMinLength();
		case ConfigPackage.AUTO_OVERLAPS_CONFIGURATION__MAX_LENGTH:
			return getMaxLength();
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
		case ConfigPackage.AUTO_OVERLAPS_CONFIGURATION__MIN_LENGTH:
			setMinLength((Integer) newValue);
			return;
		case ConfigPackage.AUTO_OVERLAPS_CONFIGURATION__MAX_LENGTH:
			setMaxLength((Integer) newValue);
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
		case ConfigPackage.AUTO_OVERLAPS_CONFIGURATION__MIN_LENGTH:
			setMinLength(MIN_LENGTH_EDEFAULT);
			return;
		case ConfigPackage.AUTO_OVERLAPS_CONFIGURATION__MAX_LENGTH:
			setMaxLength(MAX_LENGTH_EDEFAULT);
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
		case ConfigPackage.AUTO_OVERLAPS_CONFIGURATION__MIN_LENGTH:
			return minLength != MIN_LENGTH_EDEFAULT;
		case ConfigPackage.AUTO_OVERLAPS_CONFIGURATION__MAX_LENGTH:
			return maxLength != MAX_LENGTH_EDEFAULT;
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
		result.append(" (minLength: ");
		result.append(minLength);
		result.append(", maxLength: ");
		result.append(maxLength);
		result.append(')');
		return result.toString();
	}

} // AutoOverlapsConfigurationImpl
