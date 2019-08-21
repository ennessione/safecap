/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.model.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.Extensible;
import safecap.Orientation;
import safecap.SafecapPackage;
import safecap.TransientValues;
import safecap.extension.ExtAttribute;
import safecap.impl.LabeledImpl;
import safecap.model.Ambit;
import safecap.model.ControlLogic;
import safecap.model.ModelPackage;
import safecap.model.Route;
import safecap.model.Rule;
import safecap.schema.Segment;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Route</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.model.impl.RouteImpl#getRuntimeAttributes <em>Runtime
 * Attributes</em>}</li>
 * <li>{@link safecap.model.impl.RouteImpl#getAttributes
 * <em>Attributes</em>}</li>
 * <li>{@link safecap.model.impl.RouteImpl#getAmbits <em>Ambits</em>}</li>
 * <li>{@link safecap.model.impl.RouteImpl#getSegments <em>Segments</em>}</li>
 * <li>{@link safecap.model.impl.RouteImpl#getOverlap <em>Overlap</em>}</li>
 * <li>{@link safecap.model.impl.RouteImpl#getRule <em>Rule</em>}</li>
 * <li>{@link safecap.model.impl.RouteImpl#getAspects <em>Aspects</em>}</li>
 * <li>{@link safecap.model.impl.RouteImpl#getOrientation
 * <em>Orientation</em>}</li>
 * <li>{@link safecap.model.impl.RouteImpl#getLogic <em>Logic</em>}</li>
 * <li>{@link safecap.model.impl.RouteImpl#getDefaultlogic
 * <em>Defaultlogic</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RouteImpl extends LabeledImpl implements Route {
	/**
	 * The cached value of the '{@link #getRuntimeAttributes() <em>Runtime
	 * Attributes</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRuntimeAttributes()
	 * @generated
	 * @ordered
	 */
	protected Map<String, Object> runtimeAttributes;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<ExtAttribute> attributes;

	/**
	 * The cached value of the '{@link #getAmbits() <em>Ambits</em>}' reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAmbits()
	 * @generated
	 * @ordered
	 */
	protected EList<Ambit> ambits;

	/**
	 * The cached value of the '{@link #getSegments() <em>Segments</em>}' reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSegments()
	 * @generated
	 * @ordered
	 */
	protected EList<Segment> segments;

	/**
	 * The cached value of the '{@link #getOverlap() <em>Overlap</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOverlap()
	 * @generated
	 * @ordered
	 */
	protected Ambit overlap;

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected EList<Rule> rule;

	/**
	 * The default value of the '{@link #getAspects() <em>Aspects</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAspects()
	 * @generated
	 * @ordered
	 */
	protected static final int ASPECTS_EDEFAULT = 2;

	/**
	 * The cached value of the '{@link #getAspects() <em>Aspects</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAspects()
	 * @generated
	 * @ordered
	 */
	protected int aspects = ASPECTS_EDEFAULT;

	/**
	 * The default value of the '{@link #getOrientation() <em>Orientation</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOrientation()
	 * @generated
	 * @ordered
	 */
	protected static final Orientation ORIENTATION_EDEFAULT = Orientation.LEFT;

	/**
	 * The cached value of the '{@link #getOrientation() <em>Orientation</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOrientation()
	 * @generated
	 * @ordered
	 */
	protected Orientation orientation = ORIENTATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLogic() <em>Logic</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLogic()
	 * @generated
	 * @ordered
	 */
	protected EList<ControlLogic> logic;

	/**
	 * The cached value of the '{@link #getDefaultlogic() <em>Defaultlogic</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDefaultlogic()
	 * @generated
	 * @ordered
	 */
	protected ControlLogic defaultlogic;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected RouteImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.ROUTE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Map<String, Object> getRuntimeAttributes() {
		return runtimeAttributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setRuntimeAttributes(Map<String, Object> newRuntimeAttributes) {
		final Map<String, Object> oldRuntimeAttributes = runtimeAttributes;
		runtimeAttributes = newRuntimeAttributes;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ROUTE__RUNTIME_ATTRIBUTES, oldRuntimeAttributes,
					runtimeAttributes));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<ExtAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList<>(ExtAttribute.class, this, ModelPackage.ROUTE__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Ambit> getAmbits() {
		if (ambits == null) {
			ambits = new EObjectResolvingEList<>(Ambit.class, this, ModelPackage.ROUTE__AMBITS);
		}
		return ambits;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Segment> getSegments() {
		if (segments == null) {
			segments = new EObjectResolvingEList<>(Segment.class, this, ModelPackage.ROUTE__SEGMENTS);
		}
		return segments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Ambit getOverlap() {
		if (overlap != null && ((EObject) overlap).eIsProxy()) {
			final InternalEObject oldOverlap = (InternalEObject) overlap;
			overlap = (Ambit) eResolveProxy(oldOverlap);
			if (overlap != oldOverlap) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.ROUTE__OVERLAP, oldOverlap, overlap));
				}
			}
		}
		return overlap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Ambit basicGetOverlap() {
		return overlap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOverlap(Ambit newOverlap) {
		final Ambit oldOverlap = overlap;
		overlap = newOverlap;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ROUTE__OVERLAP, oldOverlap, overlap));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Rule> getRule() {
		if (rule == null) {
			rule = new EObjectContainmentEList<>(Rule.class, this, ModelPackage.ROUTE__RULE);
		}
		return rule;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getAspects() {
		return aspects;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setAspects(int newAspects) {
		final int oldAspects = aspects;
		aspects = newAspects;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ROUTE__ASPECTS, oldAspects, aspects));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOrientation(Orientation newOrientation) {
		final Orientation oldOrientation = orientation;
		orientation = newOrientation == null ? ORIENTATION_EDEFAULT : newOrientation;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ROUTE__ORIENTATION, oldOrientation, orientation));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<ControlLogic> getLogic() {
		if (logic == null) {
			logic = new EObjectContainmentEList<>(ControlLogic.class, this, ModelPackage.ROUTE__LOGIC);
		}
		return logic;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ControlLogic getDefaultlogic() {
		return defaultlogic;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetDefaultlogic(ControlLogic newDefaultlogic, NotificationChain msgs) {
		final ControlLogic oldDefaultlogic = defaultlogic;
		defaultlogic = newDefaultlogic;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.ROUTE__DEFAULTLOGIC,
					oldDefaultlogic, newDefaultlogic);
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
	public void setDefaultlogic(ControlLogic newDefaultlogic) {
		if (newDefaultlogic != defaultlogic) {
			NotificationChain msgs = null;
			if (defaultlogic != null) {
				msgs = ((InternalEObject) defaultlogic).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.ROUTE__DEFAULTLOGIC,
						null, msgs);
			}
			if (newDefaultlogic != null) {
				msgs = ((InternalEObject) newDefaultlogic).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.ROUTE__DEFAULTLOGIC,
						null, msgs);
			}
			msgs = basicSetDefaultlogic(newDefaultlogic, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ROUTE__DEFAULTLOGIC, newDefaultlogic, newDefaultlogic));
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
		case ModelPackage.ROUTE__ATTRIBUTES:
			return ((InternalEList<?>) getAttributes()).basicRemove(otherEnd, msgs);
		case ModelPackage.ROUTE__RULE:
			return ((InternalEList<?>) getRule()).basicRemove(otherEnd, msgs);
		case ModelPackage.ROUTE__LOGIC:
			return ((InternalEList<?>) getLogic()).basicRemove(otherEnd, msgs);
		case ModelPackage.ROUTE__DEFAULTLOGIC:
			return basicSetDefaultlogic(null, msgs);
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
		case ModelPackage.ROUTE__RUNTIME_ATTRIBUTES:
			return getRuntimeAttributes();
		case ModelPackage.ROUTE__ATTRIBUTES:
			return getAttributes();
		case ModelPackage.ROUTE__AMBITS:
			return getAmbits();
		case ModelPackage.ROUTE__SEGMENTS:
			return getSegments();
		case ModelPackage.ROUTE__OVERLAP:
			if (resolve) {
				return getOverlap();
			}
			return basicGetOverlap();
		case ModelPackage.ROUTE__RULE:
			return getRule();
		case ModelPackage.ROUTE__ASPECTS:
			return getAspects();
		case ModelPackage.ROUTE__ORIENTATION:
			return getOrientation();
		case ModelPackage.ROUTE__LOGIC:
			return getLogic();
		case ModelPackage.ROUTE__DEFAULTLOGIC:
			return getDefaultlogic();
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
		case ModelPackage.ROUTE__RUNTIME_ATTRIBUTES:
			setRuntimeAttributes((Map<String, Object>) newValue);
			return;
		case ModelPackage.ROUTE__ATTRIBUTES:
			getAttributes().clear();
			getAttributes().addAll((Collection<? extends ExtAttribute>) newValue);
			return;
		case ModelPackage.ROUTE__AMBITS:
			getAmbits().clear();
			getAmbits().addAll((Collection<? extends Ambit>) newValue);
			return;
		case ModelPackage.ROUTE__SEGMENTS:
			getSegments().clear();
			getSegments().addAll((Collection<? extends Segment>) newValue);
			return;
		case ModelPackage.ROUTE__OVERLAP:
			setOverlap((Ambit) newValue);
			return;
		case ModelPackage.ROUTE__RULE:
			getRule().clear();
			getRule().addAll((Collection<? extends Rule>) newValue);
			return;
		case ModelPackage.ROUTE__ASPECTS:
			setAspects((Integer) newValue);
			return;
		case ModelPackage.ROUTE__ORIENTATION:
			setOrientation((Orientation) newValue);
			return;
		case ModelPackage.ROUTE__LOGIC:
			getLogic().clear();
			getLogic().addAll((Collection<? extends ControlLogic>) newValue);
			return;
		case ModelPackage.ROUTE__DEFAULTLOGIC:
			setDefaultlogic((ControlLogic) newValue);
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
		case ModelPackage.ROUTE__RUNTIME_ATTRIBUTES:
			setRuntimeAttributes((Map<String, Object>) null);
			return;
		case ModelPackage.ROUTE__ATTRIBUTES:
			getAttributes().clear();
			return;
		case ModelPackage.ROUTE__AMBITS:
			getAmbits().clear();
			return;
		case ModelPackage.ROUTE__SEGMENTS:
			getSegments().clear();
			return;
		case ModelPackage.ROUTE__OVERLAP:
			setOverlap((Ambit) null);
			return;
		case ModelPackage.ROUTE__RULE:
			getRule().clear();
			return;
		case ModelPackage.ROUTE__ASPECTS:
			setAspects(ASPECTS_EDEFAULT);
			return;
		case ModelPackage.ROUTE__ORIENTATION:
			setOrientation(ORIENTATION_EDEFAULT);
			return;
		case ModelPackage.ROUTE__LOGIC:
			getLogic().clear();
			return;
		case ModelPackage.ROUTE__DEFAULTLOGIC:
			setDefaultlogic((ControlLogic) null);
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
		case ModelPackage.ROUTE__RUNTIME_ATTRIBUTES:
			return runtimeAttributes != null;
		case ModelPackage.ROUTE__ATTRIBUTES:
			return attributes != null && !attributes.isEmpty();
		case ModelPackage.ROUTE__AMBITS:
			return ambits != null && !ambits.isEmpty();
		case ModelPackage.ROUTE__SEGMENTS:
			return segments != null && !segments.isEmpty();
		case ModelPackage.ROUTE__OVERLAP:
			return overlap != null;
		case ModelPackage.ROUTE__RULE:
			return rule != null && !rule.isEmpty();
		case ModelPackage.ROUTE__ASPECTS:
			return aspects != ASPECTS_EDEFAULT;
		case ModelPackage.ROUTE__ORIENTATION:
			return orientation != ORIENTATION_EDEFAULT;
		case ModelPackage.ROUTE__LOGIC:
			return logic != null && !logic.isEmpty();
		case ModelPackage.ROUTE__DEFAULTLOGIC:
			return defaultlogic != null;
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
		if (baseClass == TransientValues.class) {
			switch (derivedFeatureID) {
			case ModelPackage.ROUTE__RUNTIME_ATTRIBUTES:
				return SafecapPackage.TRANSIENT_VALUES__RUNTIME_ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Extensible.class) {
			switch (derivedFeatureID) {
			case ModelPackage.ROUTE__ATTRIBUTES:
				return SafecapPackage.EXTENSIBLE__ATTRIBUTES;
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
		if (baseClass == TransientValues.class) {
			switch (baseFeatureID) {
			case SafecapPackage.TRANSIENT_VALUES__RUNTIME_ATTRIBUTES:
				return ModelPackage.ROUTE__RUNTIME_ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Extensible.class) {
			switch (baseFeatureID) {
			case SafecapPackage.EXTENSIBLE__ATTRIBUTES:
				return ModelPackage.ROUTE__ATTRIBUTES;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public String toString() {
		return getLabel();
	}

} // RouteImpl
