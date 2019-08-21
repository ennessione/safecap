/**
 */
package safecap.config.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import safecap.config.ConfigPackage;
import safecap.config.RouteConstructionRule;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Route
 * Construction Rule</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.config.impl.RouteConstructionRuleImpl#getName
 * <em>Name</em>}</li>
 * <li>{@link safecap.config.impl.RouteConstructionRuleImpl#getIgnoredsignals
 * <em>Ignoredsignals</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class RouteConstructionRuleImpl extends EObjectImpl implements RouteConstructionRule {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getIgnoredsignals()
	 * <em>Ignoredsignals</em>}' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getIgnoredsignals()
	 * @generated
	 * @ordered
	 */
	protected EList<String> ignoredsignals;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected RouteConstructionRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigPackage.Literals.ROUTE_CONSTRUCTION_RULE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		final String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.ROUTE_CONSTRUCTION_RULE__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<String> getIgnoredsignals() {
		if (ignoredsignals == null) {
			ignoredsignals = new EDataTypeUniqueEList<>(String.class, this, ConfigPackage.ROUTE_CONSTRUCTION_RULE__IGNOREDSIGNALS);
		}
		return ignoredsignals;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ConfigPackage.ROUTE_CONSTRUCTION_RULE__NAME:
			return getName();
		case ConfigPackage.ROUTE_CONSTRUCTION_RULE__IGNOREDSIGNALS:
			return getIgnoredsignals();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ConfigPackage.ROUTE_CONSTRUCTION_RULE__NAME:
			setName((String) newValue);
			return;
		case ConfigPackage.ROUTE_CONSTRUCTION_RULE__IGNOREDSIGNALS:
			getIgnoredsignals().clear();
			getIgnoredsignals().addAll((Collection<? extends String>) newValue);
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
		case ConfigPackage.ROUTE_CONSTRUCTION_RULE__NAME:
			setName(NAME_EDEFAULT);
			return;
		case ConfigPackage.ROUTE_CONSTRUCTION_RULE__IGNOREDSIGNALS:
			getIgnoredsignals().clear();
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
		case ConfigPackage.ROUTE_CONSTRUCTION_RULE__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case ConfigPackage.ROUTE_CONSTRUCTION_RULE__IGNOREDSIGNALS:
			return ignoredsignals != null && !ignoredsignals.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", ignoredsignals: ");
		result.append(ignoredsignals);
		result.append(')');
		return result.toString();
	}

} // RouteConstructionRuleImpl
