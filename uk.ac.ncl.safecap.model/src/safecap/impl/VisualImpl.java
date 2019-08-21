/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.HighlightedInterval;
import safecap.SafecapPackage;
import safecap.Style;
import safecap.Visual;
import safecap.VisualMarker;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Visual</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.impl.VisualImpl#isVisible <em>Visible</em>}</li>
 * <li>{@link safecap.impl.VisualImpl#getStyle <em>Style</em>}</li>
 * <li>{@link safecap.impl.VisualImpl#getIntervals <em>Intervals</em>}</li>
 * <li>{@link safecap.impl.VisualImpl#getMarkers <em>Markers</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class VisualImpl extends EObjectImpl implements Visual {
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VisualImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SafecapPackage.Literals.VISUAL;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.VISUAL__VISIBLE, oldVisible, visible));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SafecapPackage.VISUAL__STYLE, oldStyle, style));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.VISUAL__STYLE, oldStyle, style));
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
			intervals = new EObjectResolvingEList<>(HighlightedInterval.class, this, SafecapPackage.VISUAL__INTERVALS);
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
			markers = new EObjectContainmentEList<>(VisualMarker.class, this, SafecapPackage.VISUAL__MARKERS);
		}
		return markers;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case SafecapPackage.VISUAL__MARKERS:
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
		case SafecapPackage.VISUAL__VISIBLE:
			return isVisible();
		case SafecapPackage.VISUAL__STYLE:
			if (resolve) {
				return getStyle();
			}
			return basicGetStyle();
		case SafecapPackage.VISUAL__INTERVALS:
			return getIntervals();
		case SafecapPackage.VISUAL__MARKERS:
			return getMarkers();
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
		case SafecapPackage.VISUAL__VISIBLE:
			setVisible((Boolean) newValue);
			return;
		case SafecapPackage.VISUAL__STYLE:
			setStyle((Style) newValue);
			return;
		case SafecapPackage.VISUAL__INTERVALS:
			getIntervals().clear();
			getIntervals().addAll((Collection<? extends HighlightedInterval>) newValue);
			return;
		case SafecapPackage.VISUAL__MARKERS:
			getMarkers().clear();
			getMarkers().addAll((Collection<? extends VisualMarker>) newValue);
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
		case SafecapPackage.VISUAL__VISIBLE:
			setVisible(VISIBLE_EDEFAULT);
			return;
		case SafecapPackage.VISUAL__STYLE:
			setStyle((Style) null);
			return;
		case SafecapPackage.VISUAL__INTERVALS:
			getIntervals().clear();
			return;
		case SafecapPackage.VISUAL__MARKERS:
			getMarkers().clear();
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
		case SafecapPackage.VISUAL__VISIBLE:
			return visible != VISIBLE_EDEFAULT;
		case SafecapPackage.VISUAL__STYLE:
			return style != null;
		case SafecapPackage.VISUAL__INTERVALS:
			return intervals != null && !intervals.isEmpty();
		case SafecapPackage.VISUAL__MARKERS:
			return markers != null && !markers.isEmpty();
		}
		return super.eIsSet(featureID);
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
		result.append(')');
		return result.toString();
	}

} // VisualImpl
