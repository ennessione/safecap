/**
 */
package safecap.config.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import safecap.config.ConfigPackage;
import safecap.config.PathRouteConstructionRule;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Path
 * Route Construction Rule</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.config.impl.PathRouteConstructionRuleImpl#getEntry
 * <em>Entry</em>}</li>
 * <li>{@link safecap.config.impl.PathRouteConstructionRuleImpl#getExit
 * <em>Exit</em>}</li>
 * <li>{@link safecap.config.impl.PathRouteConstructionRuleImpl#getPointstates
 * <em>Pointstates</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PathRouteConstructionRuleImpl extends RouteConstructionRuleImpl implements PathRouteConstructionRule {
	/**
	 * The default value of the '{@link #getEntry() <em>Entry</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEntry()
	 * @generated
	 * @ordered
	 */
	protected static final String ENTRY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEntry() <em>Entry</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEntry()
	 * @generated
	 * @ordered
	 */
	protected String entry = ENTRY_EDEFAULT;

	/**
	 * The default value of the '{@link #getExit() <em>Exit</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExit()
	 * @generated
	 * @ordered
	 */
	protected static final String EXIT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExit() <em>Exit</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExit()
	 * @generated
	 * @ordered
	 */
	protected String exit = EXIT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPointstates() <em>Pointstates</em>}'
	 * attribute list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPointstates()
	 * @generated
	 * @ordered
	 */
	protected EList<String> pointstates;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected PathRouteConstructionRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigPackage.Literals.PATH_ROUTE_CONSTRUCTION_RULE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getEntry() {
		return entry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setEntry(String newEntry) {
		final String oldEntry = entry;
		entry = newEntry;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__ENTRY, oldEntry, entry));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getExit() {
		return exit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setExit(String newExit) {
		final String oldExit = exit;
		exit = newExit;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__EXIT, oldExit, exit));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<String> getPointstates() {
		if (pointstates == null) {
			pointstates = new EDataTypeUniqueEList<>(String.class, this, ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__POINTSTATES);
		}
		return pointstates;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__ENTRY:
			return getEntry();
		case ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__EXIT:
			return getExit();
		case ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__POINTSTATES:
			return getPointstates();
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
		case ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__ENTRY:
			setEntry((String) newValue);
			return;
		case ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__EXIT:
			setExit((String) newValue);
			return;
		case ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__POINTSTATES:
			getPointstates().clear();
			getPointstates().addAll((Collection<? extends String>) newValue);
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
		case ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__ENTRY:
			setEntry(ENTRY_EDEFAULT);
			return;
		case ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__EXIT:
			setExit(EXIT_EDEFAULT);
			return;
		case ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__POINTSTATES:
			getPointstates().clear();
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
		case ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__ENTRY:
			return ENTRY_EDEFAULT == null ? entry != null : !ENTRY_EDEFAULT.equals(entry);
		case ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__EXIT:
			return EXIT_EDEFAULT == null ? exit != null : !EXIT_EDEFAULT.equals(exit);
		case ConfigPackage.PATH_ROUTE_CONSTRUCTION_RULE__POINTSTATES:
			return pointstates != null && !pointstates.isEmpty();
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
		result.append(" (entry: ");
		result.append(entry);
		result.append(", exit: ");
		result.append(exit);
		result.append(", pointstates: ");
		result.append(pointstates);
		result.append(')');
		return result.toString();
	}

} // PathRouteConstructionRuleImpl
