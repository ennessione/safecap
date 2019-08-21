/**
 */
package safecap.trackside.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.trackside.Equipment;
import safecap.trackside.SubEquipment;
import safecap.trackside.SubWire;
import safecap.trackside.TracksidePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Sub
 * Wire</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.impl.SubWireImpl#getFrom <em>From</em>}</li>
 * <li>{@link safecap.trackside.impl.SubWireImpl#getTo <em>To</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SubWireImpl extends EObjectImpl implements SubWire {
	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected SubEquipment from;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected Equipment to;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SubWireImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracksidePackage.Literals.SUB_WIRE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SubEquipment getFrom() {
		if (from != null && ((EObject) from).eIsProxy()) {
			final InternalEObject oldFrom = (InternalEObject) from;
			from = (SubEquipment) eResolveProxy(oldFrom);
			if (from != oldFrom) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracksidePackage.SUB_WIRE__FROM, oldFrom, from));
				}
			}
		}
		return from;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SubEquipment basicGetFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setFrom(SubEquipment newFrom) {
		final SubEquipment oldFrom = from;
		from = newFrom;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.SUB_WIRE__FROM, oldFrom, from));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Equipment getTo() {
		if (to != null && ((EObject) to).eIsProxy()) {
			final InternalEObject oldTo = (InternalEObject) to;
			to = (Equipment) eResolveProxy(oldTo);
			if (to != oldTo) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracksidePackage.SUB_WIRE__TO, oldTo, to));
				}
			}
		}
		return to;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Equipment basicGetTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTo(Equipment newTo) {
		final Equipment oldTo = to;
		to = newTo;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.SUB_WIRE__TO, oldTo, to));
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
		case TracksidePackage.SUB_WIRE__FROM:
			if (resolve) {
				return getFrom();
			}
			return basicGetFrom();
		case TracksidePackage.SUB_WIRE__TO:
			if (resolve) {
				return getTo();
			}
			return basicGetTo();
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
		case TracksidePackage.SUB_WIRE__FROM:
			setFrom((SubEquipment) newValue);
			return;
		case TracksidePackage.SUB_WIRE__TO:
			setTo((Equipment) newValue);
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
		case TracksidePackage.SUB_WIRE__FROM:
			setFrom((SubEquipment) null);
			return;
		case TracksidePackage.SUB_WIRE__TO:
			setTo((Equipment) null);
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
		case TracksidePackage.SUB_WIRE__FROM:
			return from != null;
		case TracksidePackage.SUB_WIRE__TO:
			return to != null;
		}
		return super.eIsSet(featureID);
	}

} // SubWireImpl
