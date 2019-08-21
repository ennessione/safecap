/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import safecap.model.Line;
import safecap.model.Route;
import safecap.trackside.StopAndGo;
import safecap.trackside.TracksidePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Stop
 * And Go</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.impl.StopAndGoImpl#getDelay <em>Delay</em>}</li>
 * <li>{@link safecap.trackside.impl.StopAndGoImpl#getLine <em>Line</em>}</li>
 * <li>{@link safecap.trackside.impl.StopAndGoImpl#getReleaseRoute <em>Release
 * Route</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StopAndGoImpl extends EquipmentImpl implements StopAndGo {
	/**
	 * The default value of the '{@link #getDelay() <em>Delay</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDelay()
	 * @generated
	 * @ordered
	 */
	protected static final int DELAY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDelay() <em>Delay</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDelay()
	 * @generated
	 * @ordered
	 */
	protected int delay = DELAY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLine() <em>Line</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLine()
	 * @generated
	 * @ordered
	 */
	protected EList<Line> line;

	/**
	 * The cached value of the '{@link #getReleaseRoute() <em>Release Route</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getReleaseRoute()
	 * @generated
	 * @ordered
	 */
	protected Route releaseRoute;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected StopAndGoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracksidePackage.Literals.STOP_AND_GO;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getDelay() {
		return delay;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDelay(int newDelay) {
		final int oldDelay = delay;
		delay = newDelay;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.STOP_AND_GO__DELAY, oldDelay, delay));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Line> getLine() {
		if (line == null) {
			line = new EObjectResolvingEList<>(Line.class, this, TracksidePackage.STOP_AND_GO__LINE);
		}
		return line;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Route getReleaseRoute() {
		if (releaseRoute != null && ((EObject) releaseRoute).eIsProxy()) {
			final InternalEObject oldReleaseRoute = (InternalEObject) releaseRoute;
			releaseRoute = (Route) eResolveProxy(oldReleaseRoute);
			if (releaseRoute != oldReleaseRoute) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracksidePackage.STOP_AND_GO__RELEASE_ROUTE, oldReleaseRoute,
							releaseRoute));
				}
			}
		}
		return releaseRoute;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Route basicGetReleaseRoute() {
		return releaseRoute;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setReleaseRoute(Route newReleaseRoute) {
		final Route oldReleaseRoute = releaseRoute;
		releaseRoute = newReleaseRoute;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.STOP_AND_GO__RELEASE_ROUTE, oldReleaseRoute,
					releaseRoute));
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
		case TracksidePackage.STOP_AND_GO__DELAY:
			return getDelay();
		case TracksidePackage.STOP_AND_GO__LINE:
			return getLine();
		case TracksidePackage.STOP_AND_GO__RELEASE_ROUTE:
			if (resolve) {
				return getReleaseRoute();
			}
			return basicGetReleaseRoute();
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
		case TracksidePackage.STOP_AND_GO__DELAY:
			setDelay((Integer) newValue);
			return;
		case TracksidePackage.STOP_AND_GO__LINE:
			getLine().clear();
			getLine().addAll((Collection<? extends Line>) newValue);
			return;
		case TracksidePackage.STOP_AND_GO__RELEASE_ROUTE:
			setReleaseRoute((Route) newValue);
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
		case TracksidePackage.STOP_AND_GO__DELAY:
			setDelay(DELAY_EDEFAULT);
			return;
		case TracksidePackage.STOP_AND_GO__LINE:
			getLine().clear();
			return;
		case TracksidePackage.STOP_AND_GO__RELEASE_ROUTE:
			setReleaseRoute((Route) null);
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
		case TracksidePackage.STOP_AND_GO__DELAY:
			return delay != DELAY_EDEFAULT;
		case TracksidePackage.STOP_AND_GO__LINE:
			return line != null && !line.isEmpty();
		case TracksidePackage.STOP_AND_GO__RELEASE_ROUTE:
			return releaseRoute != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public String toString() {
		return "STATION(" + getDelay() + ")";
	}

} // StopAndGoImpl
