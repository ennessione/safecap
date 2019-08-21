/**
 */
package traintable.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import traintable.TDAttribute;
import traintable.Train;
import traintable.TraintablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Train</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link traintable.impl.TrainImpl#getName <em>Name</em>}</li>
 * <li>{@link traintable.impl.TrainImpl#getSpeed <em>Speed</em>}</li>
 * <li>{@link traintable.impl.TrainImpl#getAcceleration
 * <em>Acceleration</em>}</li>
 * <li>{@link traintable.impl.TrainImpl#getDeceleration
 * <em>Deceleration</em>}</li>
 * <li>{@link traintable.impl.TrainImpl#getLength <em>Length</em>}</li>
 * <li>{@link traintable.impl.TrainImpl#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TrainImpl extends EObjectImpl implements Train {
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
	 * The default value of the '{@link #getSpeed() <em>Speed</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSpeed()
	 * @generated
	 * @ordered
	 */
	protected static final int SPEED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSpeed() <em>Speed</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSpeed()
	 * @generated
	 * @ordered
	 */
	protected int speed = SPEED_EDEFAULT;

	/**
	 * The default value of the '{@link #getAcceleration() <em>Acceleration</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAcceleration()
	 * @generated
	 * @ordered
	 */
	protected static final float ACCELERATION_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getAcceleration() <em>Acceleration</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAcceleration()
	 * @generated
	 * @ordered
	 */
	protected float acceleration = ACCELERATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getDeceleration() <em>Deceleration</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDeceleration()
	 * @generated
	 * @ordered
	 */
	protected static final float DECELERATION_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getDeceleration() <em>Deceleration</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDeceleration()
	 * @generated
	 * @ordered
	 */
	protected float deceleration = DECELERATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final int LENGTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected int length = LENGTH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<TDAttribute> attributes;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TrainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TraintablePackage.Literals.TRAIN;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TraintablePackage.TRAIN__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getSpeed() {
		return speed;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSpeed(int newSpeed) {
		final int oldSpeed = speed;
		speed = newSpeed;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TraintablePackage.TRAIN__SPEED, oldSpeed, speed));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public float getAcceleration() {
		return acceleration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setAcceleration(float newAcceleration) {
		final float oldAcceleration = acceleration;
		acceleration = newAcceleration;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TraintablePackage.TRAIN__ACCELERATION, oldAcceleration, acceleration));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public float getDeceleration() {
		return deceleration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDeceleration(float newDeceleration) {
		final float oldDeceleration = deceleration;
		deceleration = newDeceleration;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TraintablePackage.TRAIN__DECELERATION, oldDeceleration, deceleration));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getLength() {
		return length;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLength(int newLength) {
		final int oldLength = length;
		length = newLength;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TraintablePackage.TRAIN__LENGTH, oldLength, length));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<TDAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList<>(TDAttribute.class, this, TraintablePackage.TRAIN__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case TraintablePackage.TRAIN__ATTRIBUTES:
			return ((InternalEList<?>) getAttributes()).basicRemove(otherEnd, msgs);
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
		case TraintablePackage.TRAIN__NAME:
			return getName();
		case TraintablePackage.TRAIN__SPEED:
			return getSpeed();
		case TraintablePackage.TRAIN__ACCELERATION:
			return getAcceleration();
		case TraintablePackage.TRAIN__DECELERATION:
			return getDeceleration();
		case TraintablePackage.TRAIN__LENGTH:
			return getLength();
		case TraintablePackage.TRAIN__ATTRIBUTES:
			return getAttributes();
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
		case TraintablePackage.TRAIN__NAME:
			setName((String) newValue);
			return;
		case TraintablePackage.TRAIN__SPEED:
			setSpeed((Integer) newValue);
			return;
		case TraintablePackage.TRAIN__ACCELERATION:
			setAcceleration((Float) newValue);
			return;
		case TraintablePackage.TRAIN__DECELERATION:
			setDeceleration((Float) newValue);
			return;
		case TraintablePackage.TRAIN__LENGTH:
			setLength((Integer) newValue);
			return;
		case TraintablePackage.TRAIN__ATTRIBUTES:
			getAttributes().clear();
			getAttributes().addAll((Collection<? extends TDAttribute>) newValue);
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
		case TraintablePackage.TRAIN__NAME:
			setName(NAME_EDEFAULT);
			return;
		case TraintablePackage.TRAIN__SPEED:
			setSpeed(SPEED_EDEFAULT);
			return;
		case TraintablePackage.TRAIN__ACCELERATION:
			setAcceleration(ACCELERATION_EDEFAULT);
			return;
		case TraintablePackage.TRAIN__DECELERATION:
			setDeceleration(DECELERATION_EDEFAULT);
			return;
		case TraintablePackage.TRAIN__LENGTH:
			setLength(LENGTH_EDEFAULT);
			return;
		case TraintablePackage.TRAIN__ATTRIBUTES:
			getAttributes().clear();
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
		case TraintablePackage.TRAIN__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case TraintablePackage.TRAIN__SPEED:
			return speed != SPEED_EDEFAULT;
		case TraintablePackage.TRAIN__ACCELERATION:
			return acceleration != ACCELERATION_EDEFAULT;
		case TraintablePackage.TRAIN__DECELERATION:
			return deceleration != DECELERATION_EDEFAULT;
		case TraintablePackage.TRAIN__LENGTH:
			return length != LENGTH_EDEFAULT;
		case TraintablePackage.TRAIN__ATTRIBUTES:
			return attributes != null && !attributes.isEmpty();
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

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", speed: ");
		result.append(speed);
		result.append(", acceleration: ");
		result.append(acceleration);
		result.append(", deceleration: ");
		result.append(deceleration);
		result.append(", length: ");
		result.append(length);
		result.append(')');
		return result.toString();
	}

} // TrainImpl
