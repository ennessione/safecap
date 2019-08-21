/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema.impl;

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
import safecap.HighlightedInterval;
import safecap.SafecapPackage;
import safecap.Style;
import safecap.TransientValues;
import safecap.Visual;
import safecap.VisualMarker;
import safecap.extension.ExtAttribute;
import safecap.impl.LabeledImpl;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.SchemaPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Node</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.impl.NodeImpl#isVisible <em>Visible</em>}</li>
 * <li>{@link safecap.schema.impl.NodeImpl#getStyle <em>Style</em>}</li>
 * <li>{@link safecap.schema.impl.NodeImpl#getIntervals <em>Intervals</em>}</li>
 * <li>{@link safecap.schema.impl.NodeImpl#getMarkers <em>Markers</em>}</li>
 * <li>{@link safecap.schema.impl.NodeImpl#getRuntimeAttributes <em>Runtime
 * Attributes</em>}</li>
 * <li>{@link safecap.schema.impl.NodeImpl#getAttributes
 * <em>Attributes</em>}</li>
 * <li>{@link safecap.schema.impl.NodeImpl#getAltitude <em>Altitude</em>}</li>
 * <li>{@link safecap.schema.impl.NodeImpl#getKind <em>Kind</em>}</li>
 * <li>{@link safecap.schema.impl.NodeImpl#getRoles <em>Roles</em>}</li>
 * <li>{@link safecap.schema.impl.NodeImpl#getSpeedlimit
 * <em>Speedlimit</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeImpl extends LabeledImpl implements Node {
	/**
	 * The default value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VISIBLE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected boolean visible = VISIBLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStyle() <em>Style</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStyle()
	 * @generated
	 * @ordered
	 */
	protected Style style;

	/**
	 * The cached value of the '{@link #getIntervals() <em>Intervals</em>}'
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getIntervals()
	 * @generated
	 * @ordered
	 */
	protected EList<HighlightedInterval> intervals;

	/**
	 * The cached value of the '{@link #getMarkers() <em>Markers</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMarkers()
	 * @generated
	 * @ordered
	 */
	protected EList<VisualMarker> markers;

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
	 * The default value of the '{@link #getAltitude() <em>Altitude</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAltitude()
	 * @generated
	 * @ordered
	 */
	protected static final int ALTITUDE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getAltitude() <em>Altitude</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAltitude()
	 * @generated
	 * @ordered
	 */
	protected int altitude = ALTITUDE_EDEFAULT;

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final NodeKind KIND_EDEFAULT = NodeKind.NORMAL;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected NodeKind kind = KIND_EDEFAULT;

	/**
	 * The default value of the '{@link #getRoles() <em>Roles</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRoles()
	 * @generated
	 * @ordered
	 */
	protected static final int ROLES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getRoles() <em>Roles</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRoles()
	 * @generated
	 * @ordered
	 */
	protected int roles = ROLES_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpeedlimit() <em>Speedlimit</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSpeedlimit()
	 * @generated
	 * @ordered
	 */
	protected static final double SPEEDLIMIT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getSpeedlimit() <em>Speedlimit</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSpeedlimit()
	 * @generated
	 * @ordered
	 */
	protected double speedlimit = SPEEDLIMIT_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected NodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SchemaPackage.Literals.NODE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isVisible() {
		return visible;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVisible(boolean newVisible) {
		final boolean oldVisible = visible;
		visible = newVisible;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.NODE__VISIBLE, oldVisible, visible));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Style getStyle() {
		if (style != null && ((EObject) style).eIsProxy()) {
			final InternalEObject oldStyle = (InternalEObject) style;
			style = (Style) eResolveProxy(oldStyle);
			if (style != oldStyle) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SchemaPackage.NODE__STYLE, oldStyle, style));
				}
			}
		}
		return style;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Style basicGetStyle() {
		return style;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setStyle(Style newStyle) {
		final Style oldStyle = style;
		style = newStyle;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.NODE__STYLE, oldStyle, style));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<HighlightedInterval> getIntervals() {
		if (intervals == null) {
			intervals = new EObjectResolvingEList<>(HighlightedInterval.class, this, SchemaPackage.NODE__INTERVALS);
		}
		return intervals;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<VisualMarker> getMarkers() {
		if (markers == null) {
			markers = new EObjectContainmentEList<>(VisualMarker.class, this, SchemaPackage.NODE__MARKERS);
		}
		return markers;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.NODE__RUNTIME_ATTRIBUTES, oldRuntimeAttributes,
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
			attributes = new EObjectContainmentEList<>(ExtAttribute.class, this, SchemaPackage.NODE__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getAltitude() {
		return altitude;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setAltitude(int newAltitude) {
		final int oldAltitude = altitude;
		altitude = newAltitude;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.NODE__ALTITUDE, oldAltitude, altitude));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NodeKind getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setKind(NodeKind newKind) {
		final NodeKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.NODE__KIND, oldKind, kind));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getRoles() {
		return roles;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setRoles(int newRoles) {
		final int oldRoles = roles;
		roles = newRoles;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.NODE__ROLES, oldRoles, roles));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public double getSpeedlimit() {
		return speedlimit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSpeedlimit(double newSpeedlimit) {
		final double oldSpeedlimit = speedlimit;
		speedlimit = newSpeedlimit;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.NODE__SPEEDLIMIT, oldSpeedlimit, speedlimit));
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
		case SchemaPackage.NODE__MARKERS:
			return ((InternalEList<?>) getMarkers()).basicRemove(otherEnd, msgs);
		case SchemaPackage.NODE__ATTRIBUTES:
			return ((InternalEList<?>) getAttributes()).basicRemove(otherEnd, msgs);
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
		case SchemaPackage.NODE__VISIBLE:
			return isVisible();
		case SchemaPackage.NODE__STYLE:
			if (resolve) {
				return getStyle();
			}
			return basicGetStyle();
		case SchemaPackage.NODE__INTERVALS:
			return getIntervals();
		case SchemaPackage.NODE__MARKERS:
			return getMarkers();
		case SchemaPackage.NODE__RUNTIME_ATTRIBUTES:
			return getRuntimeAttributes();
		case SchemaPackage.NODE__ATTRIBUTES:
			return getAttributes();
		case SchemaPackage.NODE__ALTITUDE:
			return getAltitude();
		case SchemaPackage.NODE__KIND:
			return getKind();
		case SchemaPackage.NODE__ROLES:
			return getRoles();
		case SchemaPackage.NODE__SPEEDLIMIT:
			return getSpeedlimit();
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
		case SchemaPackage.NODE__VISIBLE:
			setVisible((Boolean) newValue);
			return;
		case SchemaPackage.NODE__STYLE:
			setStyle((Style) newValue);
			return;
		case SchemaPackage.NODE__INTERVALS:
			getIntervals().clear();
			getIntervals().addAll((Collection<? extends HighlightedInterval>) newValue);
			return;
		case SchemaPackage.NODE__MARKERS:
			getMarkers().clear();
			getMarkers().addAll((Collection<? extends VisualMarker>) newValue);
			return;
		case SchemaPackage.NODE__RUNTIME_ATTRIBUTES:
			setRuntimeAttributes((Map<String, Object>) newValue);
			return;
		case SchemaPackage.NODE__ATTRIBUTES:
			getAttributes().clear();
			getAttributes().addAll((Collection<? extends ExtAttribute>) newValue);
			return;
		case SchemaPackage.NODE__ALTITUDE:
			setAltitude((Integer) newValue);
			return;
		case SchemaPackage.NODE__KIND:
			setKind((NodeKind) newValue);
			return;
		case SchemaPackage.NODE__ROLES:
			setRoles((Integer) newValue);
			return;
		case SchemaPackage.NODE__SPEEDLIMIT:
			setSpeedlimit((Double) newValue);
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
		case SchemaPackage.NODE__VISIBLE:
			setVisible(VISIBLE_EDEFAULT);
			return;
		case SchemaPackage.NODE__STYLE:
			setStyle((Style) null);
			return;
		case SchemaPackage.NODE__INTERVALS:
			getIntervals().clear();
			return;
		case SchemaPackage.NODE__MARKERS:
			getMarkers().clear();
			return;
		case SchemaPackage.NODE__RUNTIME_ATTRIBUTES:
			setRuntimeAttributes((Map<String, Object>) null);
			return;
		case SchemaPackage.NODE__ATTRIBUTES:
			getAttributes().clear();
			return;
		case SchemaPackage.NODE__ALTITUDE:
			setAltitude(ALTITUDE_EDEFAULT);
			return;
		case SchemaPackage.NODE__KIND:
			setKind(KIND_EDEFAULT);
			return;
		case SchemaPackage.NODE__ROLES:
			setRoles(ROLES_EDEFAULT);
			return;
		case SchemaPackage.NODE__SPEEDLIMIT:
			setSpeedlimit(SPEEDLIMIT_EDEFAULT);
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
		case SchemaPackage.NODE__VISIBLE:
			return visible != VISIBLE_EDEFAULT;
		case SchemaPackage.NODE__STYLE:
			return style != null;
		case SchemaPackage.NODE__INTERVALS:
			return intervals != null && !intervals.isEmpty();
		case SchemaPackage.NODE__MARKERS:
			return markers != null && !markers.isEmpty();
		case SchemaPackage.NODE__RUNTIME_ATTRIBUTES:
			return runtimeAttributes != null;
		case SchemaPackage.NODE__ATTRIBUTES:
			return attributes != null && !attributes.isEmpty();
		case SchemaPackage.NODE__ALTITUDE:
			return altitude != ALTITUDE_EDEFAULT;
		case SchemaPackage.NODE__KIND:
			return kind != KIND_EDEFAULT;
		case SchemaPackage.NODE__ROLES:
			return roles != ROLES_EDEFAULT;
		case SchemaPackage.NODE__SPEEDLIMIT:
			return speedlimit != SPEEDLIMIT_EDEFAULT;
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
		if (baseClass == Visual.class) {
			switch (derivedFeatureID) {
			case SchemaPackage.NODE__VISIBLE:
				return SafecapPackage.VISUAL__VISIBLE;
			case SchemaPackage.NODE__STYLE:
				return SafecapPackage.VISUAL__STYLE;
			case SchemaPackage.NODE__INTERVALS:
				return SafecapPackage.VISUAL__INTERVALS;
			case SchemaPackage.NODE__MARKERS:
				return SafecapPackage.VISUAL__MARKERS;
			default:
				return -1;
			}
		}
		if (baseClass == TransientValues.class) {
			switch (derivedFeatureID) {
			case SchemaPackage.NODE__RUNTIME_ATTRIBUTES:
				return SafecapPackage.TRANSIENT_VALUES__RUNTIME_ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Extensible.class) {
			switch (derivedFeatureID) {
			case SchemaPackage.NODE__ATTRIBUTES:
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
		if (baseClass == Visual.class) {
			switch (baseFeatureID) {
			case SafecapPackage.VISUAL__VISIBLE:
				return SchemaPackage.NODE__VISIBLE;
			case SafecapPackage.VISUAL__STYLE:
				return SchemaPackage.NODE__STYLE;
			case SafecapPackage.VISUAL__INTERVALS:
				return SchemaPackage.NODE__INTERVALS;
			case SafecapPackage.VISUAL__MARKERS:
				return SchemaPackage.NODE__MARKERS;
			default:
				return -1;
			}
		}
		if (baseClass == TransientValues.class) {
			switch (baseFeatureID) {
			case SafecapPackage.TRANSIENT_VALUES__RUNTIME_ATTRIBUTES:
				return SchemaPackage.NODE__RUNTIME_ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Extensible.class) {
			switch (baseFeatureID) {
			case SafecapPackage.EXTENSIBLE__ATTRIBUTES:
				return SchemaPackage.NODE__ATTRIBUTES;
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

} // NodeImpl
