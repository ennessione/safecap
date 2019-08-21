/**
 */
package safecap.extension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import safecap.extension.ExtEquipment;
import safecap.extension.ExtVisual;
import safecap.extension.ExtensionPackage;
import safecap.trackside.impl.EquipmentImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Ext
 * Equipment</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.extension.impl.ExtEquipmentImpl#getVisual
 * <em>Visual</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExtEquipmentImpl extends EquipmentImpl implements ExtEquipment {
	/**
	 * The cached value of the '{@link #getVisual() <em>Visual</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVisual()
	 * @generated
	 * @ordered
	 */
	protected ExtVisual visual;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ExtEquipmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtensionPackage.Literals.EXT_EQUIPMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ExtVisual getVisual() {
		return visual;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetVisual(ExtVisual newVisual, NotificationChain msgs) {
		final ExtVisual oldVisual = visual;
		visual = newVisual;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ExtensionPackage.EXT_EQUIPMENT__VISUAL,
					oldVisual, newVisual);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVisual(ExtVisual newVisual) {
		if (newVisual != visual) {
			NotificationChain msgs = null;
			if (visual != null) {
				msgs = ((InternalEObject) visual).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ExtensionPackage.EXT_EQUIPMENT__VISUAL,
						null, msgs);
			}
			if (newVisual != null) {
				msgs = ((InternalEObject) newVisual).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ExtensionPackage.EXT_EQUIPMENT__VISUAL,
						null, msgs);
			}
			msgs = basicSetVisual(newVisual, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ExtensionPackage.EXT_EQUIPMENT__VISUAL, newVisual, newVisual));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ExtensionPackage.EXT_EQUIPMENT__VISUAL:
			return basicSetVisual(null, msgs);
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
		case ExtensionPackage.EXT_EQUIPMENT__VISUAL:
			return getVisual();
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
		case ExtensionPackage.EXT_EQUIPMENT__VISUAL:
			setVisual((ExtVisual) newValue);
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
		case ExtensionPackage.EXT_EQUIPMENT__VISUAL:
			setVisual((ExtVisual) null);
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
		case ExtensionPackage.EXT_EQUIPMENT__VISUAL:
			return visual != null;
		}
		return super.eIsSet(featureID);
	}

} // ExtEquipmentImpl
