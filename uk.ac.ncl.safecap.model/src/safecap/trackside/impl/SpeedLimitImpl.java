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
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import safecap.model.Line;
import safecap.trackside.SpeedLimit;
import safecap.trackside.TracksidePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Speed
 * Limit</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.impl.SpeedLimitImpl#getLine <em>Line</em>}</li>
 * <li>{@link safecap.trackside.impl.SpeedLimitImpl#getLimit
 * <em>Limit</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SpeedLimitImpl extends EquipmentImpl implements SpeedLimit {
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
	 * The default value of the '{@link #getLimit() <em>Limit</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLimit()
	 * @generated
	 * @ordered
	 */
	protected static final double LIMIT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getLimit() <em>Limit</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLimit()
	 * @generated
	 * @ordered
	 */
	protected double limit = LIMIT_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SpeedLimitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracksidePackage.Literals.SPEED_LIMIT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Line> getLine() {
		if (line == null) {
			line = new EObjectResolvingEList<>(Line.class, this, TracksidePackage.SPEED_LIMIT__LINE);
		}
		return line;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public double getLimit() {
		return limit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLimit(double newLimit) {
		final double oldLimit = limit;
		limit = newLimit;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.SPEED_LIMIT__LIMIT, oldLimit, limit));
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
		case TracksidePackage.SPEED_LIMIT__LINE:
			return getLine();
		case TracksidePackage.SPEED_LIMIT__LIMIT:
			return getLimit();
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
		case TracksidePackage.SPEED_LIMIT__LINE:
			getLine().clear();
			getLine().addAll((Collection<? extends Line>) newValue);
			return;
		case TracksidePackage.SPEED_LIMIT__LIMIT:
			setLimit((Double) newValue);
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
		case TracksidePackage.SPEED_LIMIT__LINE:
			getLine().clear();
			return;
		case TracksidePackage.SPEED_LIMIT__LIMIT:
			setLimit(LIMIT_EDEFAULT);
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
		case TracksidePackage.SPEED_LIMIT__LINE:
			return line != null && !line.isEmpty();
		case TracksidePackage.SPEED_LIMIT__LIMIT:
			return limit != LIMIT_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public String toString() {
		return "LIMIT(" + getLimit() + ")";
	}

} // SpeedLimitImpl
