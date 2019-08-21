/**
 */
package safecap.derived.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.derived.Derived;
import safecap.derived.DerivedPackage;
import safecap.derived.MergedPoint;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Derived</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.derived.impl.DerivedImpl#getMergedpoints
 * <em>Mergedpoints</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DerivedImpl extends EObjectImpl implements Derived {
	/**
	 * The cached value of the '{@link #getMergedpoints() <em>Mergedpoints</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMergedpoints()
	 * @generated
	 * @ordered
	 */
	protected EList<MergedPoint> mergedpoints;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected DerivedImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DerivedPackage.Literals.DERIVED;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<MergedPoint> getMergedpoints() {
		if (mergedpoints == null) {
			mergedpoints = new EObjectContainmentEList<>(MergedPoint.class, this, DerivedPackage.DERIVED__MERGEDPOINTS);
		}
		return mergedpoints;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DerivedPackage.DERIVED__MERGEDPOINTS:
			return ((InternalEList<?>) getMergedpoints()).basicRemove(otherEnd, msgs);
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
		case DerivedPackage.DERIVED__MERGEDPOINTS:
			return getMergedpoints();
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
		case DerivedPackage.DERIVED__MERGEDPOINTS:
			getMergedpoints().clear();
			getMergedpoints().addAll((Collection<? extends MergedPoint>) newValue);
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
		case DerivedPackage.DERIVED__MERGEDPOINTS:
			getMergedpoints().clear();
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
		case DerivedPackage.DERIVED__MERGEDPOINTS:
			return mergedpoints != null && !mergedpoints.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // DerivedImpl
