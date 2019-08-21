/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.model.Route;
import safecap.schema.RouteSpeed;
import safecap.schema.SchemaPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Route
 * Speed</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.impl.RouteSpeedImpl#getRoute <em>Route</em>}</li>
 * <li>{@link safecap.schema.impl.RouteSpeedImpl#getSpeed <em>Speed</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RouteSpeedImpl extends EObjectImpl implements RouteSpeed {
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
	 * The default value of the '{@link #getSpeed() <em>Speed</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSpeed()
	 * @generated
	 * @ordered
	 */
	protected static final double SPEED_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getSpeed() <em>Speed</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSpeed()
	 * @generated
	 * @ordered
	 */
	protected double speed = SPEED_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected RouteSpeedImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SchemaPackage.Literals.ROUTE_SPEED;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SchemaPackage.ROUTE_SPEED__ROUTE, oldRoute, route));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.ROUTE_SPEED__ROUTE, oldRoute, route));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public double getSpeed() {
		return speed;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSpeed(double newSpeed) {
		final double oldSpeed = speed;
		speed = newSpeed;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.ROUTE_SPEED__SPEED, oldSpeed, speed));
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
		case SchemaPackage.ROUTE_SPEED__ROUTE:
			if (resolve) {
				return getRoute();
			}
			return basicGetRoute();
		case SchemaPackage.ROUTE_SPEED__SPEED:
			return getSpeed();
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
		case SchemaPackage.ROUTE_SPEED__ROUTE:
			setRoute((Route) newValue);
			return;
		case SchemaPackage.ROUTE_SPEED__SPEED:
			setSpeed((Double) newValue);
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
		case SchemaPackage.ROUTE_SPEED__ROUTE:
			setRoute((Route) null);
			return;
		case SchemaPackage.ROUTE_SPEED__SPEED:
			setSpeed(SPEED_EDEFAULT);
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
		case SchemaPackage.ROUTE_SPEED__ROUTE:
			return route != null;
		case SchemaPackage.ROUTE_SPEED__SPEED:
			return speed != SPEED_EDEFAULT;
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
		result.append(" (speed: ");
		result.append(speed);
		result.append(')');
		return result.toString();
	}

} // RouteSpeedImpl
