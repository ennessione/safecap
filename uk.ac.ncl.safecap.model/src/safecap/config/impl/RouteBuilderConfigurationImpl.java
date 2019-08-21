/**
 */
package safecap.config.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.config.ConfigPackage;
import safecap.config.RouteBuilderConfiguration;
import safecap.config.RouteConstructionRule;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Route
 * Builder Configuration</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.config.impl.RouteBuilderConfigurationImpl#getRoutes
 * <em>Routes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RouteBuilderConfigurationImpl extends EObjectImpl implements RouteBuilderConfiguration {
	/**
	 * The cached value of the '{@link #getRoutes() <em>Routes</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRoutes()
	 * @generated
	 * @ordered
	 */
	protected EList<RouteConstructionRule> routes;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected RouteBuilderConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigPackage.Literals.ROUTE_BUILDER_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<RouteConstructionRule> getRoutes() {
		if (routes == null) {
			routes = new EObjectContainmentEList<>(RouteConstructionRule.class, this,
					ConfigPackage.ROUTE_BUILDER_CONFIGURATION__ROUTES);
		}
		return routes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ConfigPackage.ROUTE_BUILDER_CONFIGURATION__ROUTES:
			return ((InternalEList<?>) getRoutes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ConfigPackage.ROUTE_BUILDER_CONFIGURATION__ROUTES:
			return getRoutes();
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
		case ConfigPackage.ROUTE_BUILDER_CONFIGURATION__ROUTES:
			getRoutes().clear();
			getRoutes().addAll((Collection<? extends RouteConstructionRule>) newValue);
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
		case ConfigPackage.ROUTE_BUILDER_CONFIGURATION__ROUTES:
			getRoutes().clear();
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
		case ConfigPackage.ROUTE_BUILDER_CONFIGURATION__ROUTES:
			return routes != null && !routes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // RouteBuilderConfigurationImpl
