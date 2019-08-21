/**
 */
package safecap.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.HighlightedInterval;
import safecap.SafecapPackage;
import safecap.Style;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Highlighted Interval</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.impl.HighlightedIntervalImpl#getStyle <em>Style</em>}</li>
 * <li>{@link safecap.impl.HighlightedIntervalImpl#getLabel <em>Label</em>}</li>
 * <li>{@link safecap.impl.HighlightedIntervalImpl#getFrom <em>From</em>}</li>
 * <li>{@link safecap.impl.HighlightedIntervalImpl#getTo <em>To</em>}</li>
 * <li>{@link safecap.impl.HighlightedIntervalImpl#getOwner <em>Owner</em>}</li>
 * <li>{@link safecap.impl.HighlightedIntervalImpl#getIndex <em>Index</em>}</li>
 * <li>{@link safecap.impl.HighlightedIntervalImpl#getVoffset
 * <em>Voffset</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HighlightedIntervalImpl extends EObjectImpl implements HighlightedInterval {
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
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getFrom() <em>From</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected static final double FROM_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected double from = FROM_EDEFAULT;

	/**
	 * The default value of the '{@link #getTo() <em>To</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected static final double TO_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected double to = TO_EDEFAULT;

	/**
	 * The default value of the '{@link #getOwner() <em>Owner</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOwner()
	 * @generated
	 * @ordered
	 */
	protected static final Object OWNER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOwner() <em>Owner</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOwner()
	 * @generated
	 * @ordered
	 */
	protected Object owner = OWNER_EDEFAULT;

	/**
	 * The default value of the '{@link #getIndex() <em>Index</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getIndex() <em>Index</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected int index = INDEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getVoffset() <em>Voffset</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVoffset()
	 * @generated
	 * @ordered
	 */
	protected static final int VOFFSET_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getVoffset() <em>Voffset</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVoffset()
	 * @generated
	 * @ordered
	 */
	protected int voffset = VOFFSET_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected HighlightedIntervalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SafecapPackage.Literals.HIGHLIGHTED_INTERVAL;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SafecapPackage.HIGHLIGHTED_INTERVAL__STYLE, oldStyle, style));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.HIGHLIGHTED_INTERVAL__STYLE, oldStyle, style));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLabel(String newLabel) {
		final String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.HIGHLIGHTED_INTERVAL__LABEL, oldLabel, label));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public double getFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setFrom(double newFrom) {
		final double oldFrom = from;
		from = newFrom;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.HIGHLIGHTED_INTERVAL__FROM, oldFrom, from));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public double getTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTo(double newTo) {
		final double oldTo = to;
		to = newTo;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.HIGHLIGHTED_INTERVAL__TO, oldTo, to));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getOwner() {
		return owner;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOwner(Object newOwner) {
		final Object oldOwner = owner;
		owner = newOwner;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.HIGHLIGHTED_INTERVAL__OWNER, oldOwner, owner));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getIndex() {
		return index;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setIndex(int newIndex) {
		final int oldIndex = index;
		index = newIndex;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.HIGHLIGHTED_INTERVAL__INDEX, oldIndex, index));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getVoffset() {
		return voffset;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVoffset(int newVoffset) {
		final int oldVoffset = voffset;
		voffset = newVoffset;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.HIGHLIGHTED_INTERVAL__VOFFSET, oldVoffset, voffset));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case SafecapPackage.HIGHLIGHTED_INTERVAL__STYLE:
			if (resolve) {
				return getStyle();
			}
			return basicGetStyle();
		case SafecapPackage.HIGHLIGHTED_INTERVAL__LABEL:
			return getLabel();
		case SafecapPackage.HIGHLIGHTED_INTERVAL__FROM:
			return getFrom();
		case SafecapPackage.HIGHLIGHTED_INTERVAL__TO:
			return getTo();
		case SafecapPackage.HIGHLIGHTED_INTERVAL__OWNER:
			return getOwner();
		case SafecapPackage.HIGHLIGHTED_INTERVAL__INDEX:
			return getIndex();
		case SafecapPackage.HIGHLIGHTED_INTERVAL__VOFFSET:
			return getVoffset();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case SafecapPackage.HIGHLIGHTED_INTERVAL__STYLE:
			setStyle((Style) newValue);
			return;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__LABEL:
			setLabel((String) newValue);
			return;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__FROM:
			setFrom((Double) newValue);
			return;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__TO:
			setTo((Double) newValue);
			return;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__OWNER:
			setOwner(newValue);
			return;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__INDEX:
			setIndex((Integer) newValue);
			return;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__VOFFSET:
			setVoffset((Integer) newValue);
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
		case SafecapPackage.HIGHLIGHTED_INTERVAL__STYLE:
			setStyle((Style) null);
			return;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__LABEL:
			setLabel(LABEL_EDEFAULT);
			return;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__FROM:
			setFrom(FROM_EDEFAULT);
			return;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__TO:
			setTo(TO_EDEFAULT);
			return;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__OWNER:
			setOwner(OWNER_EDEFAULT);
			return;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__INDEX:
			setIndex(INDEX_EDEFAULT);
			return;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__VOFFSET:
			setVoffset(VOFFSET_EDEFAULT);
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
		case SafecapPackage.HIGHLIGHTED_INTERVAL__STYLE:
			return style != null;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__LABEL:
			return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
		case SafecapPackage.HIGHLIGHTED_INTERVAL__FROM:
			return from != FROM_EDEFAULT;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__TO:
			return to != TO_EDEFAULT;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__OWNER:
			return OWNER_EDEFAULT == null ? owner != null : !OWNER_EDEFAULT.equals(owner);
		case SafecapPackage.HIGHLIGHTED_INTERVAL__INDEX:
			return index != INDEX_EDEFAULT;
		case SafecapPackage.HIGHLIGHTED_INTERVAL__VOFFSET:
			return voffset != VOFFSET_EDEFAULT;
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
		result.append(" (label: ");
		result.append(label);
		result.append(", from: ");
		result.append(from);
		result.append(", to: ");
		result.append(to);
		result.append(", owner: ");
		result.append(owner);
		result.append(", index: ");
		result.append(index);
		result.append(", voffset: ");
		result.append(voffset);
		result.append(')');
		return result.toString();
	}

} // HighlightedIntervalImpl
