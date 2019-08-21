/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.schema.HeightPoint;
import safecap.schema.SchemaPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Height
 * Point</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.impl.HeightPointImpl#getAltitude
 * <em>Altitude</em>}</li>
 * <li>{@link safecap.schema.impl.HeightPointImpl#getPosition
 * <em>Position</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HeightPointImpl extends EObjectImpl implements HeightPoint {
	/**
	 * The default value of the '{@link #getAltitude() <em>Altitude</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAltitude()
	 * @generated
	 * @ordered
	 */
	protected static final int ALTITUDE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getAltitude() <em>Altitude</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAltitude()
	 * @generated
	 * @ordered
	 */
	protected int altitude = ALTITUDE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPosition() <em>Position</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected int position = POSITION_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected HeightPointImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SchemaPackage.Literals.HEIGHT_POINT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getAltitude() {
		return altitude;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setAltitude(int newAltitude) {
		final int oldAltitude = altitude;
		altitude = newAltitude;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.HEIGHT_POINT__ALTITUDE, oldAltitude, altitude));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getPosition() {
		return position;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setPosition(int newPosition) {
		final int oldPosition = position;
		position = newPosition;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.HEIGHT_POINT__POSITION, oldPosition, position));
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
		case SchemaPackage.HEIGHT_POINT__ALTITUDE:
			return getAltitude();
		case SchemaPackage.HEIGHT_POINT__POSITION:
			return getPosition();
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
		case SchemaPackage.HEIGHT_POINT__ALTITUDE:
			setAltitude((Integer) newValue);
			return;
		case SchemaPackage.HEIGHT_POINT__POSITION:
			setPosition((Integer) newValue);
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
		case SchemaPackage.HEIGHT_POINT__ALTITUDE:
			setAltitude(ALTITUDE_EDEFAULT);
			return;
		case SchemaPackage.HEIGHT_POINT__POSITION:
			setPosition(POSITION_EDEFAULT);
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
		case SchemaPackage.HEIGHT_POINT__ALTITUDE:
			return altitude != ALTITUDE_EDEFAULT;
		case SchemaPackage.HEIGHT_POINT__POSITION:
			return position != POSITION_EDEFAULT;
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
		result.append(" (altitude: ");
		result.append(altitude);
		result.append(", position: ");
		result.append(position);
		result.append(')');
		return result.toString();
	}

} // HeightPointImpl
