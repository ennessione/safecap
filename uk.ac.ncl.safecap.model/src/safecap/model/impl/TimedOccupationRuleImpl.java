/**
 */
package safecap.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.model.Ambit;
import safecap.model.ModelPackage;
import safecap.model.TimedOccupationRule;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Timed
 * Occupation Rule</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.model.impl.TimedOccupationRuleImpl#getAmbit
 * <em>Ambit</em>}</li>
 * <li>{@link safecap.model.impl.TimedOccupationRuleImpl#getTime
 * <em>Time</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TimedOccupationRuleImpl extends EObjectImpl implements TimedOccupationRule {
	/**
	 * The cached value of the '{@link #getAmbit() <em>Ambit</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAmbit()
	 * @generated
	 * @ordered
	 */
	protected Ambit ambit;

	/**
	 * The default value of the '{@link #getTime() <em>Time</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTime()
	 * @generated
	 * @ordered
	 */
	protected static final double TIME_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getTime() <em>Time</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTime()
	 * @generated
	 * @ordered
	 */
	protected double time = TIME_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TimedOccupationRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.TIMED_OCCUPATION_RULE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Ambit getAmbit() {
		if (ambit != null && ((EObject) ambit).eIsProxy()) {
			final InternalEObject oldAmbit = (InternalEObject) ambit;
			ambit = (Ambit) eResolveProxy(oldAmbit);
			if (ambit != oldAmbit) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.TIMED_OCCUPATION_RULE__AMBIT, oldAmbit, ambit));
				}
			}
		}
		return ambit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Ambit basicGetAmbit() {
		return ambit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setAmbit(Ambit newAmbit) {
		final Ambit oldAmbit = ambit;
		ambit = newAmbit;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TIMED_OCCUPATION_RULE__AMBIT, oldAmbit, ambit));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public double getTime() {
		return time;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTime(double newTime) {
		final double oldTime = time;
		time = newTime;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TIMED_OCCUPATION_RULE__TIME, oldTime, time));
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
		case ModelPackage.TIMED_OCCUPATION_RULE__AMBIT:
			if (resolve) {
				return getAmbit();
			}
			return basicGetAmbit();
		case ModelPackage.TIMED_OCCUPATION_RULE__TIME:
			return getTime();
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
		case ModelPackage.TIMED_OCCUPATION_RULE__AMBIT:
			setAmbit((Ambit) newValue);
			return;
		case ModelPackage.TIMED_OCCUPATION_RULE__TIME:
			setTime((Double) newValue);
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
		case ModelPackage.TIMED_OCCUPATION_RULE__AMBIT:
			setAmbit((Ambit) null);
			return;
		case ModelPackage.TIMED_OCCUPATION_RULE__TIME:
			setTime(TIME_EDEFAULT);
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
		case ModelPackage.TIMED_OCCUPATION_RULE__AMBIT:
			return ambit != null;
		case ModelPackage.TIMED_OCCUPATION_RULE__TIME:
			return time != TIME_EDEFAULT;
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
		result.append(" (time: ");
		result.append(time);
		result.append(')');
		return result.toString();
	}

} // TimedOccupationRuleImpl
