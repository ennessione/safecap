/**
 */
package traintable.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import traintable.Train;
import traintable.TrainTable;
import traintable.TraintablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Train
 * Table</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link traintable.impl.TrainTableImpl#getTrains <em>Trains</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TrainTableImpl extends EObjectImpl implements TrainTable {
	/**
	 * The cached value of the '{@link #getTrains() <em>Trains</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTrains()
	 * @generated
	 * @ordered
	 */
	protected EList<Train> trains;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TrainTableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TraintablePackage.Literals.TRAIN_TABLE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Train> getTrains() {
		if (trains == null) {
			trains = new EObjectContainmentEList<>(Train.class, this, TraintablePackage.TRAIN_TABLE__TRAINS);
		}
		return trains;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case TraintablePackage.TRAIN_TABLE__TRAINS:
			return ((InternalEList<?>) getTrains()).basicRemove(otherEnd, msgs);
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
		case TraintablePackage.TRAIN_TABLE__TRAINS:
			return getTrains();
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
		case TraintablePackage.TRAIN_TABLE__TRAINS:
			getTrains().clear();
			getTrains().addAll((Collection<? extends Train>) newValue);
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
		case TraintablePackage.TRAIN_TABLE__TRAINS:
			getTrains().clear();
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
		case TraintablePackage.TRAIN_TABLE__TRAINS:
			return trains != null && !trains.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // TrainTableImpl
