/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside.impl;

import java.util.Collection;

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

import safecap.HighlightedInterval;
import safecap.SafecapPackage;
import safecap.Style;
import safecap.Visual;
import safecap.VisualMarker;
import safecap.impl.ExtensibleImpl;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.TracksidePackage;
import safecap.trackside.Wire;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Wire</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.impl.WireImpl#isVisible <em>Visible</em>}</li>
 * <li>{@link safecap.trackside.impl.WireImpl#getStyle <em>Style</em>}</li>
 * <li>{@link safecap.trackside.impl.WireImpl#getIntervals
 * <em>Intervals</em>}</li>
 * <li>{@link safecap.trackside.impl.WireImpl#getMarkers <em>Markers</em>}</li>
 * <li>{@link safecap.trackside.impl.WireImpl#getFrom <em>From</em>}</li>
 * <li>{@link safecap.trackside.impl.WireImpl#getTo <em>To</em>}</li>
 * <li>{@link safecap.trackside.impl.WireImpl#getOffset <em>Offset</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WireImpl extends ExtensibleImpl implements Wire {
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
	 * The cached value of the '{@link #getFrom() <em>From</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected Equipment from;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected Segment to;

	/**
	 * The default value of the '{@link #getOffset() <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOffset()
	 * @generated
	 * @ordered
	 */
	protected static final int OFFSET_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOffset() <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOffset()
	 * @generated
	 * @ordered
	 */
	protected int offset = OFFSET_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected WireImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracksidePackage.Literals.WIRE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.WIRE__VISIBLE, oldVisible, visible));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracksidePackage.WIRE__STYLE, oldStyle, style));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.WIRE__STYLE, oldStyle, style));
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
			intervals = new EObjectResolvingEList<>(HighlightedInterval.class, this, TracksidePackage.WIRE__INTERVALS);
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
			markers = new EObjectContainmentEList<>(VisualMarker.class, this, TracksidePackage.WIRE__MARKERS);
		}
		return markers;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Equipment getFrom() {
		if (from != null && ((EObject) from).eIsProxy()) {
			final InternalEObject oldFrom = (InternalEObject) from;
			from = (Equipment) eResolveProxy(oldFrom);
			if (from != oldFrom) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracksidePackage.WIRE__FROM, oldFrom, from));
				}
			}
		}
		return from;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Equipment basicGetFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setFrom(Equipment newFrom) {
		final Equipment oldFrom = from;
		from = newFrom;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.WIRE__FROM, oldFrom, from));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Segment getTo() {
		if (to != null && ((EObject) to).eIsProxy()) {
			final InternalEObject oldTo = (InternalEObject) to;
			to = (Segment) eResolveProxy(oldTo);
			if (to != oldTo) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracksidePackage.WIRE__TO, oldTo, to));
				}
			}
		}
		return to;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Segment basicGetTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTo(Segment newTo) {
		final Segment oldTo = to;
		to = newTo;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.WIRE__TO, oldTo, to));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getOffset() {
		return offset;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOffset(int newOffset) {
		final int oldOffset = offset;
		offset = newOffset;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.WIRE__OFFSET, oldOffset, offset));
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
		case TracksidePackage.WIRE__MARKERS:
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
		case TracksidePackage.WIRE__VISIBLE:
			return isVisible();
		case TracksidePackage.WIRE__STYLE:
			if (resolve) {
				return getStyle();
			}
			return basicGetStyle();
		case TracksidePackage.WIRE__INTERVALS:
			return getIntervals();
		case TracksidePackage.WIRE__MARKERS:
			return getMarkers();
		case TracksidePackage.WIRE__FROM:
			if (resolve) {
				return getFrom();
			}
			return basicGetFrom();
		case TracksidePackage.WIRE__TO:
			if (resolve) {
				return getTo();
			}
			return basicGetTo();
		case TracksidePackage.WIRE__OFFSET:
			return getOffset();
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
		case TracksidePackage.WIRE__VISIBLE:
			setVisible((Boolean) newValue);
			return;
		case TracksidePackage.WIRE__STYLE:
			setStyle((Style) newValue);
			return;
		case TracksidePackage.WIRE__INTERVALS:
			getIntervals().clear();
			getIntervals().addAll((Collection<? extends HighlightedInterval>) newValue);
			return;
		case TracksidePackage.WIRE__MARKERS:
			getMarkers().clear();
			getMarkers().addAll((Collection<? extends VisualMarker>) newValue);
			return;
		case TracksidePackage.WIRE__FROM:
			setFrom((Equipment) newValue);
			return;
		case TracksidePackage.WIRE__TO:
			setTo((Segment) newValue);
			return;
		case TracksidePackage.WIRE__OFFSET:
			setOffset((Integer) newValue);
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
		case TracksidePackage.WIRE__VISIBLE:
			setVisible(VISIBLE_EDEFAULT);
			return;
		case TracksidePackage.WIRE__STYLE:
			setStyle((Style) null);
			return;
		case TracksidePackage.WIRE__INTERVALS:
			getIntervals().clear();
			return;
		case TracksidePackage.WIRE__MARKERS:
			getMarkers().clear();
			return;
		case TracksidePackage.WIRE__FROM:
			setFrom((Equipment) null);
			return;
		case TracksidePackage.WIRE__TO:
			setTo((Segment) null);
			return;
		case TracksidePackage.WIRE__OFFSET:
			setOffset(OFFSET_EDEFAULT);
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
		case TracksidePackage.WIRE__VISIBLE:
			return visible != VISIBLE_EDEFAULT;
		case TracksidePackage.WIRE__STYLE:
			return style != null;
		case TracksidePackage.WIRE__INTERVALS:
			return intervals != null && !intervals.isEmpty();
		case TracksidePackage.WIRE__MARKERS:
			return markers != null && !markers.isEmpty();
		case TracksidePackage.WIRE__FROM:
			return from != null;
		case TracksidePackage.WIRE__TO:
			return to != null;
		case TracksidePackage.WIRE__OFFSET:
			return offset != OFFSET_EDEFAULT;
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
			case TracksidePackage.WIRE__VISIBLE:
				return SafecapPackage.VISUAL__VISIBLE;
			case TracksidePackage.WIRE__STYLE:
				return SafecapPackage.VISUAL__STYLE;
			case TracksidePackage.WIRE__INTERVALS:
				return SafecapPackage.VISUAL__INTERVALS;
			case TracksidePackage.WIRE__MARKERS:
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
		if (baseClass == Visual.class) {
			switch (baseFeatureID) {
			case SafecapPackage.VISUAL__VISIBLE:
				return TracksidePackage.WIRE__VISIBLE;
			case SafecapPackage.VISUAL__STYLE:
				return TracksidePackage.WIRE__STYLE;
			case SafecapPackage.VISUAL__INTERVALS:
				return TracksidePackage.WIRE__INTERVALS;
			case SafecapPackage.VISUAL__MARKERS:
				return TracksidePackage.WIRE__MARKERS;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (visible: ");
		result.append(visible);
		result.append(", offset: ");
		result.append(offset);
		result.append(')');
		return result.toString();
	}

} // WireImpl
