/**
 */
package safecap.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.model.ModelPackage;
import safecap.model.Route;
import safecap.model.RouteStateRule;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Route
 * State Rule</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.model.impl.RouteStateRuleImpl#getRoute
 * <em>Route</em>}</li>
 * <li>{@link safecap.model.impl.RouteStateRuleImpl#getState
 * <em>State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RouteStateRuleImpl extends EObjectImpl implements RouteStateRule {
	/**
	 * The cached value of the '{@link #getRoute() <em>Route</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRoute()
	 * @generated
	 * @ordered
	 */
	protected Route route;

	/**
	 * The default value of the '{@link #getState() <em>State</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected static final int STATE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected int state = STATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected RouteStateRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.ROUTE_STATE_RULE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Route getRoute() {
		if (route != null && ((EObject) route).eIsProxy()) {
			final InternalEObject oldRoute = (InternalEObject) route;
			route = (Route) eResolveProxy(oldRoute);
			if (route != oldRoute) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.ROUTE_STATE_RULE__ROUTE, oldRoute, route));
				}
			}
		}
		return route;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Route basicGetRoute() {
		return route;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setRoute(Route newRoute) {
		final Route oldRoute = route;
		route = newRoute;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ROUTE_STATE_RULE__ROUTE, oldRoute, route));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getState() {
		return state;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setState(int newState) {
		final int oldState = state;
		state = newState;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ROUTE_STATE_RULE__STATE, oldState, state));
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
		case ModelPackage.ROUTE_STATE_RULE__ROUTE:
			if (resolve) {
				return getRoute();
			}
			return basicGetRoute();
		case ModelPackage.ROUTE_STATE_RULE__STATE:
			return getState();
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
		case ModelPackage.ROUTE_STATE_RULE__ROUTE:
			setRoute((Route) newValue);
			return;
		case ModelPackage.ROUTE_STATE_RULE__STATE:
			setState((Integer) newValue);
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
		case ModelPackage.ROUTE_STATE_RULE__ROUTE:
			setRoute((Route) null);
			return;
		case ModelPackage.ROUTE_STATE_RULE__STATE:
			setState(STATE_EDEFAULT);
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
		case ModelPackage.ROUTE_STATE_RULE__ROUTE:
			return route != null;
		case ModelPackage.ROUTE_STATE_RULE__STATE:
			return state != STATE_EDEFAULT;
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
		result.append(" (state: ");
		result.append(state);
		result.append(')');
		return result.toString();
	}

} // RouteStateRuleImpl
