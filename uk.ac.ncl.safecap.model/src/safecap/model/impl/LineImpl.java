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
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.Extensible;
import safecap.HighlightedInterval;
import safecap.Orientation;
import safecap.SafecapPackage;
import safecap.Style;
import safecap.TransientValues;
import safecap.Visual;
import safecap.VisualMarker;
import safecap.extension.ExtAttribute;
import safecap.impl.LabeledImpl;
import safecap.model.Line;
import safecap.model.ModelPackage;
import safecap.model.Route;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Line</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.model.impl.LineImpl#getRuntimeAttributes <em>Runtime
 * Attributes</em>}</li>
 * <li>{@link safecap.model.impl.LineImpl#getAttributes
 * <em>Attributes</em>}</li>
 * <li>{@link safecap.model.impl.LineImpl#isVisible <em>Visible</em>}</li>
 * <li>{@link safecap.model.impl.LineImpl#getStyle <em>Style</em>}</li>
 * <li>{@link safecap.model.impl.LineImpl#getIntervals <em>Intervals</em>}</li>
 * <li>{@link safecap.model.impl.LineImpl#getMarkers <em>Markers</em>}</li>
 * <li>{@link safecap.model.impl.LineImpl#getRoutes <em>Routes</em>}</li>
 * <li>{@link safecap.model.impl.LineImpl#getColor <em>Color</em>}</li>
 * <li>{@link safecap.model.impl.LineImpl#getOrientation
 * <em>Orientation</em>}</li>
 * <li>{@link safecap.model.impl.LineImpl#getTrafficmix
 * <em>Trafficmix</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LineImpl extends LabeledImpl implements Line {
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
	 * The cached value of the '{@link #getRoutes() <em>Routes</em>}' reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRoutes()
	 * @generated
	 * @ordered
	 */
	protected EList<Route> routes;

	/**
	 * The default value of the '{@link #getColor() <em>Color</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected static final Object COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getColor() <em>Color</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected Object color = COLOR_EDEFAULT;

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
	 * The cached value of the '{@link #getTrafficmix() <em>Trafficmix</em>}'
	 * attribute list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTrafficmix()
	 * @generated
	 * @ordered
	 */
	protected EList<String> trafficmix;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected LineImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.LINE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LINE__RUNTIME_ATTRIBUTES, oldRuntimeAttributes,
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
			attributes = new EObjectContainmentEList<>(ExtAttribute.class, this, ModelPackage.LINE__ATTRIBUTES);
		}
		return attributes;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LINE__VISIBLE, oldVisible, visible));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.LINE__STYLE, oldStyle, style));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LINE__STYLE, oldStyle, style));
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
			intervals = new EObjectResolvingEList<>(HighlightedInterval.class, this, ModelPackage.LINE__INTERVALS);
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
			markers = new EObjectContainmentEList<>(VisualMarker.class, this, ModelPackage.LINE__MARKERS);
		}
		return markers;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Route> getRoutes() {
		if (routes == null) {
			routes = new EObjectResolvingEList<>(Route.class, this, ModelPackage.LINE__ROUTES);
		}
		return routes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getColor() {
		return color;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setColor(Object newColor) {
		final Object oldColor = color;
		color = newColor;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LINE__COLOR, oldColor, color));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LINE__ORIENTATION, oldOrientation, orientation));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<String> getTrafficmix() {
		if (trafficmix == null) {
			trafficmix = new EDataTypeUniqueEList<>(String.class, this, ModelPackage.LINE__TRAFFICMIX);
		}
		return trafficmix;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.LINE__ATTRIBUTES:
			return ((InternalEList<?>) getAttributes()).basicRemove(otherEnd, msgs);
		case ModelPackage.LINE__MARKERS:
			return ((InternalEList<?>) getMarkers()).basicRemove(otherEnd, msgs);
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
		case ModelPackage.LINE__RUNTIME_ATTRIBUTES:
			return getRuntimeAttributes();
		case ModelPackage.LINE__ATTRIBUTES:
			return getAttributes();
		case ModelPackage.LINE__VISIBLE:
			return isVisible();
		case ModelPackage.LINE__STYLE:
			if (resolve) {
				return getStyle();
			}
			return basicGetStyle();
		case ModelPackage.LINE__INTERVALS:
			return getIntervals();
		case ModelPackage.LINE__MARKERS:
			return getMarkers();
		case ModelPackage.LINE__ROUTES:
			return getRoutes();
		case ModelPackage.LINE__COLOR:
			return getColor();
		case ModelPackage.LINE__ORIENTATION:
			return getOrientation();
		case ModelPackage.LINE__TRAFFICMIX:
			return getTrafficmix();
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
		case ModelPackage.LINE__RUNTIME_ATTRIBUTES:
			setRuntimeAttributes((Map<String, Object>) newValue);
			return;
		case ModelPackage.LINE__ATTRIBUTES:
			getAttributes().clear();
			getAttributes().addAll((Collection<? extends ExtAttribute>) newValue);
			return;
		case ModelPackage.LINE__VISIBLE:
			setVisible((Boolean) newValue);
			return;
		case ModelPackage.LINE__STYLE:
			setStyle((Style) newValue);
			return;
		case ModelPackage.LINE__INTERVALS:
			getIntervals().clear();
			getIntervals().addAll((Collection<? extends HighlightedInterval>) newValue);
			return;
		case ModelPackage.LINE__MARKERS:
			getMarkers().clear();
			getMarkers().addAll((Collection<? extends VisualMarker>) newValue);
			return;
		case ModelPackage.LINE__ROUTES:
			getRoutes().clear();
			getRoutes().addAll((Collection<? extends Route>) newValue);
			return;
		case ModelPackage.LINE__COLOR:
			setColor(newValue);
			return;
		case ModelPackage.LINE__ORIENTATION:
			setOrientation((Orientation) newValue);
			return;
		case ModelPackage.LINE__TRAFFICMIX:
			getTrafficmix().clear();
			getTrafficmix().addAll((Collection<? extends String>) newValue);
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
		case ModelPackage.LINE__RUNTIME_ATTRIBUTES:
			setRuntimeAttributes((Map<String, Object>) null);
			return;
		case ModelPackage.LINE__ATTRIBUTES:
			getAttributes().clear();
			return;
		case ModelPackage.LINE__VISIBLE:
			setVisible(VISIBLE_EDEFAULT);
			return;
		case ModelPackage.LINE__STYLE:
			setStyle((Style) null);
			return;
		case ModelPackage.LINE__INTERVALS:
			getIntervals().clear();
			return;
		case ModelPackage.LINE__MARKERS:
			getMarkers().clear();
			return;
		case ModelPackage.LINE__ROUTES:
			getRoutes().clear();
			return;
		case ModelPackage.LINE__COLOR:
			setColor(COLOR_EDEFAULT);
			return;
		case ModelPackage.LINE__ORIENTATION:
			setOrientation(ORIENTATION_EDEFAULT);
			return;
		case ModelPackage.LINE__TRAFFICMIX:
			getTrafficmix().clear();
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
		case ModelPackage.LINE__RUNTIME_ATTRIBUTES:
			return runtimeAttributes != null;
		case ModelPackage.LINE__ATTRIBUTES:
			return attributes != null && !attributes.isEmpty();
		case ModelPackage.LINE__VISIBLE:
			return visible != VISIBLE_EDEFAULT;
		case ModelPackage.LINE__STYLE:
			return style != null;
		case ModelPackage.LINE__INTERVALS:
			return intervals != null && !intervals.isEmpty();
		case ModelPackage.LINE__MARKERS:
			return markers != null && !markers.isEmpty();
		case ModelPackage.LINE__ROUTES:
			return routes != null && !routes.isEmpty();
		case ModelPackage.LINE__COLOR:
			return COLOR_EDEFAULT == null ? color != null : !COLOR_EDEFAULT.equals(color);
		case ModelPackage.LINE__ORIENTATION:
			return orientation != ORIENTATION_EDEFAULT;
		case ModelPackage.LINE__TRAFFICMIX:
			return trafficmix != null && !trafficmix.isEmpty();
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
			case ModelPackage.LINE__RUNTIME_ATTRIBUTES:
				return SafecapPackage.TRANSIENT_VALUES__RUNTIME_ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Extensible.class) {
			switch (derivedFeatureID) {
			case ModelPackage.LINE__ATTRIBUTES:
				return SafecapPackage.EXTENSIBLE__ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Visual.class) {
			switch (derivedFeatureID) {
			case ModelPackage.LINE__VISIBLE:
				return SafecapPackage.VISUAL__VISIBLE;
			case ModelPackage.LINE__STYLE:
				return SafecapPackage.VISUAL__STYLE;
			case ModelPackage.LINE__INTERVALS:
				return SafecapPackage.VISUAL__INTERVALS;
			case ModelPackage.LINE__MARKERS:
				return SafecapPackage.VISUAL__MARKERS;
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
				return ModelPackage.LINE__RUNTIME_ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Extensible.class) {
			switch (baseFeatureID) {
			case SafecapPackage.EXTENSIBLE__ATTRIBUTES:
				return ModelPackage.LINE__ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Visual.class) {
			switch (baseFeatureID) {
			case SafecapPackage.VISUAL__VISIBLE:
				return ModelPackage.LINE__VISIBLE;
			case SafecapPackage.VISUAL__STYLE:
				return ModelPackage.LINE__STYLE;
			case SafecapPackage.VISUAL__INTERVALS:
				return ModelPackage.LINE__INTERVALS;
			case SafecapPackage.VISUAL__MARKERS:
				return ModelPackage.LINE__MARKERS;
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

} // LineImpl
