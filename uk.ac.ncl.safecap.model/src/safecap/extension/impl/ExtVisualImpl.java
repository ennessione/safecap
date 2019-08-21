/**
 */
package safecap.extension.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.extension.CustomFigure;
import safecap.extension.ExtVisual;
import safecap.extension.ExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Ext
 * Visual</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.extension.impl.ExtVisualImpl#getFigures
 * <em>Figures</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExtVisualImpl extends EObjectImpl implements ExtVisual {
	/**
	 * The cached value of the '{@link #getFigures() <em>Figures</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFigures()
	 * @generated
	 * @ordered
	 */
	protected EList<CustomFigure> figures;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ExtVisualImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtensionPackage.Literals.EXT_VISUAL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<CustomFigure> getFigures() {
		if (figures == null) {
			figures = new EObjectContainmentEList<>(CustomFigure.class, this, ExtensionPackage.EXT_VISUAL__FIGURES);
		}
		return figures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ExtensionPackage.EXT_VISUAL__FIGURES:
			return ((InternalEList<?>) getFigures()).basicRemove(otherEnd, msgs);
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
		case ExtensionPackage.EXT_VISUAL__FIGURES:
			return getFigures();
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
		case ExtensionPackage.EXT_VISUAL__FIGURES:
			getFigures().clear();
			getFigures().addAll((Collection<? extends CustomFigure>) newValue);
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
		case ExtensionPackage.EXT_VISUAL__FIGURES:
			getFigures().clear();
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
		case ExtensionPackage.EXT_VISUAL__FIGURES:
			return figures != null && !figures.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // ExtVisualImpl
