/**
 */
package safecap.derived.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import safecap.derived.DerivedPackage;
import safecap.derived.MergedPoint;
import safecap.model.ModelPackage;
import safecap.model.Point;
import safecap.model.Rule;
import safecap.schema.Node;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Merged
 * Point</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.derived.impl.MergedPointImpl#getNode <em>Node</em>}</li>
 * <li>{@link safecap.derived.impl.MergedPointImpl#getRule <em>Rule</em>}</li>
 * <li>{@link safecap.derived.impl.MergedPointImpl#getPoints
 * <em>Points</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MergedPointImpl extends DerivedElementImpl implements MergedPoint {
	/**
	 * The cached value of the '{@link #getNode() <em>Node</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNode()
	 * @generated
	 * @ordered
	 */
	protected Node node;
	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected Rule rule;
	/**
	 * The cached value of the '{@link #getPoints() <em>Points</em>}' reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPoints()
	 * @generated
	 * @ordered
	 */
	protected EList<Point> points;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MergedPointImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DerivedPackage.Literals.MERGED_POINT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Node getNode() {
		if (node != null && ((EObject) node).eIsProxy()) {
			final InternalEObject oldNode = (InternalEObject) node;
			node = (Node) eResolveProxy(oldNode);
			if (node != oldNode) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DerivedPackage.MERGED_POINT__NODE, oldNode, node));
				}
			}
		}
		return node;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Node basicGetNode() {
		return node;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setNode(Node newNode) {
		final Node oldNode = node;
		node = newNode;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DerivedPackage.MERGED_POINT__NODE, oldNode, node));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Rule getRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetRule(Rule newRule, NotificationChain msgs) {
		final Rule oldRule = rule;
		rule = newRule;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DerivedPackage.MERGED_POINT__RULE, oldRule,
					newRule);
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
	public void setRule(Rule newRule) {
		if (newRule != rule) {
			NotificationChain msgs = null;
			if (rule != null) {
				msgs = ((InternalEObject) rule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DerivedPackage.MERGED_POINT__RULE, null,
						msgs);
			}
			if (newRule != null) {
				msgs = ((InternalEObject) newRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DerivedPackage.MERGED_POINT__RULE, null,
						msgs);
			}
			msgs = basicSetRule(newRule, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DerivedPackage.MERGED_POINT__RULE, newRule, newRule));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Point> getPoints() {
		if (points == null) {
			points = new EObjectResolvingEList<>(Point.class, this, DerivedPackage.MERGED_POINT__POINTS);
		}
		return points;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DerivedPackage.MERGED_POINT__RULE:
			return basicSetRule(null, msgs);
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
		case DerivedPackage.MERGED_POINT__NODE:
			if (resolve) {
				return getNode();
			}
			return basicGetNode();
		case DerivedPackage.MERGED_POINT__RULE:
			return getRule();
		case DerivedPackage.MERGED_POINT__POINTS:
			return getPoints();
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
		case DerivedPackage.MERGED_POINT__NODE:
			setNode((Node) newValue);
			return;
		case DerivedPackage.MERGED_POINT__RULE:
			setRule((Rule) newValue);
			return;
		case DerivedPackage.MERGED_POINT__POINTS:
			getPoints().clear();
			getPoints().addAll((Collection<? extends Point>) newValue);
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
		case DerivedPackage.MERGED_POINT__NODE:
			setNode((Node) null);
			return;
		case DerivedPackage.MERGED_POINT__RULE:
			setRule((Rule) null);
			return;
		case DerivedPackage.MERGED_POINT__POINTS:
			getPoints().clear();
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
		case DerivedPackage.MERGED_POINT__NODE:
			return node != null;
		case DerivedPackage.MERGED_POINT__RULE:
			return rule != null;
		case DerivedPackage.MERGED_POINT__POINTS:
			return points != null && !points.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == Point.class) {
			switch (derivedFeatureID) {
			case DerivedPackage.MERGED_POINT__NODE:
				return ModelPackage.POINT__NODE;
			case DerivedPackage.MERGED_POINT__RULE:
				return ModelPackage.POINT__RULE;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == Point.class) {
			switch (baseFeatureID) {
			case ModelPackage.POINT__NODE:
				return DerivedPackage.MERGED_POINT__NODE;
			case ModelPackage.POINT__RULE:
				return DerivedPackage.MERGED_POINT__RULE;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} // MergedPointImpl
