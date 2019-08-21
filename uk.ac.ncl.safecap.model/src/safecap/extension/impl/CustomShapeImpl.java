/**
 */
package safecap.extension.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.extension.CustomShape;
import safecap.extension.ExtensionPackage;
import safecap.extension.Point;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Custom
 * Shape</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.extension.impl.CustomShapeImpl#getShape
 * <em>Shape</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CustomShapeImpl extends CustomFigureImpl implements CustomShape {
	/**
	 * The cached value of the '{@link #getShape() <em>Shape</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getShape()
	 * @generated
	 * @ordered
	 */
	protected EList<Point> shape;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CustomShapeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtensionPackage.Literals.CUSTOM_SHAPE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Point> getShape() {
		if (shape == null) {
			shape = new EObjectContainmentEList<>(Point.class, this, ExtensionPackage.CUSTOM_SHAPE__SHAPE);
		}
		return shape;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ExtensionPackage.CUSTOM_SHAPE__SHAPE:
			return ((InternalEList<?>) getShape()).basicRemove(otherEnd, msgs);
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
		case ExtensionPackage.CUSTOM_SHAPE__SHAPE:
			return getShape();
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
		case ExtensionPackage.CUSTOM_SHAPE__SHAPE:
			getShape().clear();
			getShape().addAll((Collection<? extends Point>) newValue);
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
		case ExtensionPackage.CUSTOM_SHAPE__SHAPE:
			getShape().clear();
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
		case ExtensionPackage.CUSTOM_SHAPE__SHAPE:
			return shape != null && !shape.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // CustomShapeImpl
