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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.impl.ExtensibleImpl;
import safecap.model.Ambit;
import safecap.model.ModelPackage;
import safecap.model.Point;
import safecap.model.RouteStateRule;
import safecap.model.Rule;
import safecap.model.TimedOccupationRule;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Rule</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.model.impl.RuleImpl#getClear <em>Clear</em>}</li>
 * <li>{@link safecap.model.impl.RuleImpl#getOccupied <em>Occupied</em>}</li>
 * <li>{@link safecap.model.impl.RuleImpl#getNormal <em>Normal</em>}</li>
 * <li>{@link safecap.model.impl.RuleImpl#getReverse <em>Reverse</em>}</li>
 * <li>{@link safecap.model.impl.RuleImpl#getRouteState <em>Route
 * State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RuleImpl extends ExtensibleImpl implements Rule {
	/**
	 * The cached value of the '{@link #getClear() <em>Clear</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getClear()
	 * @generated
	 * @ordered
	 */
	protected EList<Ambit> clear;

	/**
	 * The cached value of the '{@link #getOccupied() <em>Occupied</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOccupied()
	 * @generated
	 * @ordered
	 */
	protected EList<TimedOccupationRule> occupied;

	/**
	 * The cached value of the '{@link #getNormal() <em>Normal</em>}' reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNormal()
	 * @generated
	 * @ordered
	 */
	protected EList<Point> normal;

	/**
	 * The cached value of the '{@link #getReverse() <em>Reverse</em>}' reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getReverse()
	 * @generated
	 * @ordered
	 */
	protected EList<Point> reverse;

	/**
	 * The cached value of the '{@link #getRouteState() <em>Route State</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRouteState()
	 * @generated
	 * @ordered
	 */
	protected EList<RouteStateRule> routeState;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected RuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.RULE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Ambit> getClear() {
		if (clear == null) {
			clear = new EObjectResolvingEList<>(Ambit.class, this, ModelPackage.RULE__CLEAR);
		}
		return clear;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<TimedOccupationRule> getOccupied() {
		if (occupied == null) {
			occupied = new EObjectContainmentEList<>(TimedOccupationRule.class, this, ModelPackage.RULE__OCCUPIED);
		}
		return occupied;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Point> getNormal() {
		if (normal == null) {
			normal = new EObjectResolvingEList<>(Point.class, this, ModelPackage.RULE__NORMAL);
		}
		return normal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Point> getReverse() {
		if (reverse == null) {
			reverse = new EObjectResolvingEList<>(Point.class, this, ModelPackage.RULE__REVERSE);
		}
		return reverse;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<RouteStateRule> getRouteState() {
		if (routeState == null) {
			routeState = new EObjectContainmentEList<>(RouteStateRule.class, this, ModelPackage.RULE__ROUTE_STATE);
		}
		return routeState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.RULE__OCCUPIED:
			return ((InternalEList<?>) getOccupied()).basicRemove(otherEnd, msgs);
		case ModelPackage.RULE__ROUTE_STATE:
			return ((InternalEList<?>) getRouteState()).basicRemove(otherEnd, msgs);
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
		case ModelPackage.RULE__CLEAR:
			return getClear();
		case ModelPackage.RULE__OCCUPIED:
			return getOccupied();
		case ModelPackage.RULE__NORMAL:
			return getNormal();
		case ModelPackage.RULE__REVERSE:
			return getReverse();
		case ModelPackage.RULE__ROUTE_STATE:
			return getRouteState();
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
		case ModelPackage.RULE__CLEAR:
			getClear().clear();
			getClear().addAll((Collection<? extends Ambit>) newValue);
			return;
		case ModelPackage.RULE__OCCUPIED:
			getOccupied().clear();
			getOccupied().addAll((Collection<? extends TimedOccupationRule>) newValue);
			return;
		case ModelPackage.RULE__NORMAL:
			getNormal().clear();
			getNormal().addAll((Collection<? extends Point>) newValue);
			return;
		case ModelPackage.RULE__REVERSE:
			getReverse().clear();
			getReverse().addAll((Collection<? extends Point>) newValue);
			return;
		case ModelPackage.RULE__ROUTE_STATE:
			getRouteState().clear();
			getRouteState().addAll((Collection<? extends RouteStateRule>) newValue);
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
		case ModelPackage.RULE__CLEAR:
			getClear().clear();
			return;
		case ModelPackage.RULE__OCCUPIED:
			getOccupied().clear();
			return;
		case ModelPackage.RULE__NORMAL:
			getNormal().clear();
			return;
		case ModelPackage.RULE__REVERSE:
			getReverse().clear();
			return;
		case ModelPackage.RULE__ROUTE_STATE:
			getRouteState().clear();
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
		case ModelPackage.RULE__CLEAR:
			return clear != null && !clear.isEmpty();
		case ModelPackage.RULE__OCCUPIED:
			return occupied != null && !occupied.isEmpty();
		case ModelPackage.RULE__NORMAL:
			return normal != null && !normal.isEmpty();
		case ModelPackage.RULE__REVERSE:
			return reverse != null && !reverse.isEmpty();
		case ModelPackage.RULE__ROUTE_STATE:
			return routeState != null && !routeState.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // RuleImpl
