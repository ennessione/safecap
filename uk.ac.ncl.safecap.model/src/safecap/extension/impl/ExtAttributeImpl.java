/**
 */
package safecap.extension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.extension.ExtAttribute;
import safecap.extension.ExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Ext
 * Attribute</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.extension.impl.ExtAttributeImpl#getDomain
 * <em>Domain</em>}</li>
 * <li>{@link safecap.extension.impl.ExtAttributeImpl#getLabel
 * <em>Label</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ExtAttributeImpl extends EObjectImpl implements ExtAttribute {
	/**
	 * The default value of the '{@link #getDomain() <em>Domain</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDomain()
	 * @generated
	 * @ordered
	 */
	protected static final String DOMAIN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDomain() <em>Domain</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDomain()
	 * @generated
	 * @ordered
	 */
	protected String domain = DOMAIN_EDEFAULT;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ExtAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtensionPackage.Literals.EXT_ATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getDomain() {
		return domain;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDomain(String newDomain) {
		final String oldDomain = domain;
		domain = newDomain;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ExtensionPackage.EXT_ATTRIBUTE__DOMAIN, oldDomain, domain));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLabel(String newLabel) {
		final String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ExtensionPackage.EXT_ATTRIBUTE__LABEL, oldLabel, label));
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
		case ExtensionPackage.EXT_ATTRIBUTE__DOMAIN:
			return getDomain();
		case ExtensionPackage.EXT_ATTRIBUTE__LABEL:
			return getLabel();
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
		case ExtensionPackage.EXT_ATTRIBUTE__DOMAIN:
			setDomain((String) newValue);
			return;
		case ExtensionPackage.EXT_ATTRIBUTE__LABEL:
			setLabel((String) newValue);
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
		case ExtensionPackage.EXT_ATTRIBUTE__DOMAIN:
			setDomain(DOMAIN_EDEFAULT);
			return;
		case ExtensionPackage.EXT_ATTRIBUTE__LABEL:
			setLabel(LABEL_EDEFAULT);
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
		case ExtensionPackage.EXT_ATTRIBUTE__DOMAIN:
			return DOMAIN_EDEFAULT == null ? domain != null : !DOMAIN_EDEFAULT.equals(domain);
		case ExtensionPackage.EXT_ATTRIBUTE__LABEL:
			return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
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
		result.append(" (domain: ");
		result.append(domain);
		result.append(", label: ");
		result.append(label);
		result.append(')');
		return result.toString();
	}

} // ExtAttributeImpl
