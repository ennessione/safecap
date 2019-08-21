/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside.impl;

import java.util.Collection;
import java.util.Date;
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
import safecap.Provenance;
import safecap.SafecapPackage;
import safecap.Style;
import safecap.TransientValues;
import safecap.Visual;
import safecap.VisualMarker;
import safecap.extension.ExtAttribute;
import safecap.impl.LabeledImpl;
import safecap.trackside.Equipment;
import safecap.trackside.TracksidePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Equipment</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.impl.EquipmentImpl#isVisible
 * <em>Visible</em>}</li>
 * <li>{@link safecap.trackside.impl.EquipmentImpl#getStyle <em>Style</em>}</li>
 * <li>{@link safecap.trackside.impl.EquipmentImpl#getIntervals
 * <em>Intervals</em>}</li>
 * <li>{@link safecap.trackside.impl.EquipmentImpl#getMarkers
 * <em>Markers</em>}</li>
 * <li>{@link safecap.trackside.impl.EquipmentImpl#getOrigin
 * <em>Origin</em>}</li>
 * <li>{@link safecap.trackside.impl.EquipmentImpl#getAuthor
 * <em>Author</em>}</li>
 * <li>{@link safecap.trackside.impl.EquipmentImpl#getVersion
 * <em>Version</em>}</li>
 * <li>{@link safecap.trackside.impl.EquipmentImpl#getDate <em>Date</em>}</li>
 * <li>{@link safecap.trackside.impl.EquipmentImpl#getRuntimeAttributes
 * <em>Runtime Attributes</em>}</li>
 * <li>{@link safecap.trackside.impl.EquipmentImpl#getAttributes
 * <em>Attributes</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class EquipmentImpl extends LabeledImpl implements Equipment {
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
	 * The default value of the '{@link #getOrigin() <em>Origin</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOrigin()
	 * @generated
	 * @ordered
	 */
	protected static final String ORIGIN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOrigin() <em>Origin</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOrigin()
	 * @generated
	 * @ordered
	 */
	protected String origin = ORIGIN_EDEFAULT;

	/**
	 * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected String author = AUTHOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getDate() <em>Date</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDate() <em>Date</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected Date date = DATE_EDEFAULT;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected EquipmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracksidePackage.Literals.EQUIPMENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.EQUIPMENT__VISIBLE, oldVisible, visible));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracksidePackage.EQUIPMENT__STYLE, oldStyle, style));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.EQUIPMENT__STYLE, oldStyle, style));
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
			intervals = new EObjectResolvingEList<>(HighlightedInterval.class, this,
					TracksidePackage.EQUIPMENT__INTERVALS);
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
			markers = new EObjectContainmentEList<>(VisualMarker.class, this, TracksidePackage.EQUIPMENT__MARKERS);
		}
		return markers;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getOrigin() {
		return origin;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOrigin(String newOrigin) {
		final String oldOrigin = origin;
		origin = newOrigin;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.EQUIPMENT__ORIGIN, oldOrigin, origin));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getAuthor() {
		return author;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setAuthor(String newAuthor) {
		final String oldAuthor = author;
		author = newAuthor;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.EQUIPMENT__AUTHOR, oldAuthor, author));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVersion(String newVersion) {
		final String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.EQUIPMENT__VERSION, oldVersion, version));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Date getDate() {
		return date;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDate(Date newDate) {
		final Date oldDate = date;
		date = newDate;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.EQUIPMENT__DATE, oldDate, date));
		}
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
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.EQUIPMENT__RUNTIME_ATTRIBUTES, oldRuntimeAttributes,
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
			attributes = new EObjectContainmentEList<>(ExtAttribute.class, this, TracksidePackage.EQUIPMENT__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case TracksidePackage.EQUIPMENT__MARKERS:
			return ((InternalEList<?>) getMarkers()).basicRemove(otherEnd, msgs);
		case TracksidePackage.EQUIPMENT__ATTRIBUTES:
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
		case TracksidePackage.EQUIPMENT__VISIBLE:
			return isVisible();
		case TracksidePackage.EQUIPMENT__STYLE:
			if (resolve) {
				return getStyle();
			}
			return basicGetStyle();
		case TracksidePackage.EQUIPMENT__INTERVALS:
			return getIntervals();
		case TracksidePackage.EQUIPMENT__MARKERS:
			return getMarkers();
		case TracksidePackage.EQUIPMENT__ORIGIN:
			return getOrigin();
		case TracksidePackage.EQUIPMENT__AUTHOR:
			return getAuthor();
		case TracksidePackage.EQUIPMENT__VERSION:
			return getVersion();
		case TracksidePackage.EQUIPMENT__DATE:
			return getDate();
		case TracksidePackage.EQUIPMENT__RUNTIME_ATTRIBUTES:
			return getRuntimeAttributes();
		case TracksidePackage.EQUIPMENT__ATTRIBUTES:
			return getAttributes();
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
		case TracksidePackage.EQUIPMENT__VISIBLE:
			setVisible((Boolean) newValue);
			return;
		case TracksidePackage.EQUIPMENT__STYLE:
			setStyle((Style) newValue);
			return;
		case TracksidePackage.EQUIPMENT__INTERVALS:
			getIntervals().clear();
			getIntervals().addAll((Collection<? extends HighlightedInterval>) newValue);
			return;
		case TracksidePackage.EQUIPMENT__MARKERS:
			getMarkers().clear();
			getMarkers().addAll((Collection<? extends VisualMarker>) newValue);
			return;
		case TracksidePackage.EQUIPMENT__ORIGIN:
			setOrigin((String) newValue);
			return;
		case TracksidePackage.EQUIPMENT__AUTHOR:
			setAuthor((String) newValue);
			return;
		case TracksidePackage.EQUIPMENT__VERSION:
			setVersion((String) newValue);
			return;
		case TracksidePackage.EQUIPMENT__DATE:
			setDate((Date) newValue);
			return;
		case TracksidePackage.EQUIPMENT__RUNTIME_ATTRIBUTES:
			setRuntimeAttributes((Map<String, Object>) newValue);
			return;
		case TracksidePackage.EQUIPMENT__ATTRIBUTES:
			getAttributes().clear();
			getAttributes().addAll((Collection<? extends ExtAttribute>) newValue);
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
		case TracksidePackage.EQUIPMENT__VISIBLE:
			setVisible(VISIBLE_EDEFAULT);
			return;
		case TracksidePackage.EQUIPMENT__STYLE:
			setStyle((Style) null);
			return;
		case TracksidePackage.EQUIPMENT__INTERVALS:
			getIntervals().clear();
			return;
		case TracksidePackage.EQUIPMENT__MARKERS:
			getMarkers().clear();
			return;
		case TracksidePackage.EQUIPMENT__ORIGIN:
			setOrigin(ORIGIN_EDEFAULT);
			return;
		case TracksidePackage.EQUIPMENT__AUTHOR:
			setAuthor(AUTHOR_EDEFAULT);
			return;
		case TracksidePackage.EQUIPMENT__VERSION:
			setVersion(VERSION_EDEFAULT);
			return;
		case TracksidePackage.EQUIPMENT__DATE:
			setDate(DATE_EDEFAULT);
			return;
		case TracksidePackage.EQUIPMENT__RUNTIME_ATTRIBUTES:
			setRuntimeAttributes((Map<String, Object>) null);
			return;
		case TracksidePackage.EQUIPMENT__ATTRIBUTES:
			getAttributes().clear();
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
		case TracksidePackage.EQUIPMENT__VISIBLE:
			return visible != VISIBLE_EDEFAULT;
		case TracksidePackage.EQUIPMENT__STYLE:
			return style != null;
		case TracksidePackage.EQUIPMENT__INTERVALS:
			return intervals != null && !intervals.isEmpty();
		case TracksidePackage.EQUIPMENT__MARKERS:
			return markers != null && !markers.isEmpty();
		case TracksidePackage.EQUIPMENT__ORIGIN:
			return ORIGIN_EDEFAULT == null ? origin != null : !ORIGIN_EDEFAULT.equals(origin);
		case TracksidePackage.EQUIPMENT__AUTHOR:
			return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
		case TracksidePackage.EQUIPMENT__VERSION:
			return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
		case TracksidePackage.EQUIPMENT__DATE:
			return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
		case TracksidePackage.EQUIPMENT__RUNTIME_ATTRIBUTES:
			return runtimeAttributes != null;
		case TracksidePackage.EQUIPMENT__ATTRIBUTES:
			return attributes != null && !attributes.isEmpty();
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
			case TracksidePackage.EQUIPMENT__VISIBLE:
				return SafecapPackage.VISUAL__VISIBLE;
			case TracksidePackage.EQUIPMENT__STYLE:
				return SafecapPackage.VISUAL__STYLE;
			case TracksidePackage.EQUIPMENT__INTERVALS:
				return SafecapPackage.VISUAL__INTERVALS;
			case TracksidePackage.EQUIPMENT__MARKERS:
				return SafecapPackage.VISUAL__MARKERS;
			default:
				return -1;
			}
		}
		if (baseClass == Provenance.class) {
			switch (derivedFeatureID) {
			case TracksidePackage.EQUIPMENT__ORIGIN:
				return SafecapPackage.PROVENANCE__ORIGIN;
			case TracksidePackage.EQUIPMENT__AUTHOR:
				return SafecapPackage.PROVENANCE__AUTHOR;
			case TracksidePackage.EQUIPMENT__VERSION:
				return SafecapPackage.PROVENANCE__VERSION;
			case TracksidePackage.EQUIPMENT__DATE:
				return SafecapPackage.PROVENANCE__DATE;
			default:
				return -1;
			}
		}
		if (baseClass == TransientValues.class) {
			switch (derivedFeatureID) {
			case TracksidePackage.EQUIPMENT__RUNTIME_ATTRIBUTES:
				return SafecapPackage.TRANSIENT_VALUES__RUNTIME_ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Extensible.class) {
			switch (derivedFeatureID) {
			case TracksidePackage.EQUIPMENT__ATTRIBUTES:
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
				return TracksidePackage.EQUIPMENT__VISIBLE;
			case SafecapPackage.VISUAL__STYLE:
				return TracksidePackage.EQUIPMENT__STYLE;
			case SafecapPackage.VISUAL__INTERVALS:
				return TracksidePackage.EQUIPMENT__INTERVALS;
			case SafecapPackage.VISUAL__MARKERS:
				return TracksidePackage.EQUIPMENT__MARKERS;
			default:
				return -1;
			}
		}
		if (baseClass == Provenance.class) {
			switch (baseFeatureID) {
			case SafecapPackage.PROVENANCE__ORIGIN:
				return TracksidePackage.EQUIPMENT__ORIGIN;
			case SafecapPackage.PROVENANCE__AUTHOR:
				return TracksidePackage.EQUIPMENT__AUTHOR;
			case SafecapPackage.PROVENANCE__VERSION:
				return TracksidePackage.EQUIPMENT__VERSION;
			case SafecapPackage.PROVENANCE__DATE:
				return TracksidePackage.EQUIPMENT__DATE;
			default:
				return -1;
			}
		}
		if (baseClass == TransientValues.class) {
			switch (baseFeatureID) {
			case SafecapPackage.TRANSIENT_VALUES__RUNTIME_ATTRIBUTES:
				return TracksidePackage.EQUIPMENT__RUNTIME_ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Extensible.class) {
			switch (baseFeatureID) {
			case SafecapPackage.EXTENSIBLE__ATTRIBUTES:
				return TracksidePackage.EQUIPMENT__ATTRIBUTES;
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
		result.append(", origin: ");
		result.append(origin);
		result.append(", author: ");
		result.append(author);
		result.append(", version: ");
		result.append(version);
		result.append(", date: ");
		result.append(date);
		result.append(", runtimeAttributes: ");
		result.append(runtimeAttributes);
		result.append(')');
		return result.toString();
	}

} // EquipmentImpl
