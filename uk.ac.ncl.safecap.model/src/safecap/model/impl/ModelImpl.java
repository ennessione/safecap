/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.model.Ambit;
import safecap.model.Line;
import safecap.model.Model;
import safecap.model.ModelPackage;
import safecap.model.Route;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Model</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.model.impl.ModelImpl#getLines <em>Lines</em>}</li>
 * <li>{@link safecap.model.impl.ModelImpl#getRoutes <em>Routes</em>}</li>
 * <li>{@link safecap.model.impl.ModelImpl#getAmbits <em>Ambits</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModelImpl extends EObjectImpl implements Model {
	/**
	 * The cached value of the '{@link #getLines() <em>Lines</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLines()
	 * @generated
	 * @ordered
	 */
	protected EList<Line> lines;

	/**
	 * The cached value of the '{@link #getRoutes() <em>Routes</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRoutes()
	 * @generated
	 * @ordered
	 */
	protected EList<Route> routes;

	/**
	 * The cached value of the '{@link #getAmbits() <em>Ambits</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAmbits()
	 * @generated
	 * @ordered
	 */
	protected EList<Ambit> ambits;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.MODEL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Line> getLines() {
		if (lines == null) {
			lines = new EObjectContainmentEList<>(Line.class, this, ModelPackage.MODEL__LINES);
		}
		return lines;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Route> getRoutes() {
		if (routes == null) {
			routes = new EObjectContainmentEList<>(Route.class, this, ModelPackage.MODEL__ROUTES);
		}
		return routes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Ambit> getAmbits() {
		if (ambits == null) {
			ambits = new EObjectContainmentEList<>(Ambit.class, this, ModelPackage.MODEL__AMBITS);
		}
		return ambits;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.MODEL__LINES:
			return ((InternalEList<?>) getLines()).basicRemove(otherEnd, msgs);
		case ModelPackage.MODEL__ROUTES:
			return ((InternalEList<?>) getRoutes()).basicRemove(otherEnd, msgs);
		case ModelPackage.MODEL__AMBITS:
			return ((InternalEList<?>) getAmbits()).basicRemove(otherEnd, msgs);
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
		case ModelPackage.MODEL__LINES:
			return getLines();
		case ModelPackage.MODEL__ROUTES:
			return getRoutes();
		case ModelPackage.MODEL__AMBITS:
			return getAmbits();
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
		case ModelPackage.MODEL__LINES:
			getLines().clear();
			getLines().addAll((Collection<? extends Line>) newValue);
			return;
		case ModelPackage.MODEL__ROUTES:
			getRoutes().clear();
			getRoutes().addAll((Collection<? extends Route>) newValue);
			return;
		case ModelPackage.MODEL__AMBITS:
			getAmbits().clear();
			getAmbits().addAll((Collection<? extends Ambit>) newValue);
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
		case ModelPackage.MODEL__LINES:
			getLines().clear();
			return;
		case ModelPackage.MODEL__ROUTES:
			getRoutes().clear();
			return;
		case ModelPackage.MODEL__AMBITS:
			getAmbits().clear();
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
		case ModelPackage.MODEL__LINES:
			return lines != null && !lines.isEmpty();
		case ModelPackage.MODEL__ROUTES:
			return routes != null && !routes.isEmpty();
		case ModelPackage.MODEL__AMBITS:
			return ambits != null && !ambits.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // ModelImpl
