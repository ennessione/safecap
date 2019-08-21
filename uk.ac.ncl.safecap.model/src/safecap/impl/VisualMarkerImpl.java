/**
 */
package safecap.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.SafecapPackage;
import safecap.Style;
import safecap.VisualMarker;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Visual
 * Marker</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.impl.VisualMarkerImpl#getStyle <em>Style</em>}</li>
 * <li>{@link safecap.impl.VisualMarkerImpl#getLabel <em>Label</em>}</li>
 * <li>{@link safecap.impl.VisualMarkerImpl#getPosition <em>Position</em>}</li>
 * <li>{@link safecap.impl.VisualMarkerImpl#getOwner <em>Owner</em>}</li>
 * <li>{@link safecap.impl.VisualMarkerImpl#getIndex <em>Index</em>}</li>
 * <li>{@link safecap.impl.VisualMarkerImpl#getOffsetLabel <em>Offset
 * Label</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VisualMarkerImpl extends EObjectImpl implements VisualMarker {
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
	 * The default value of the '{@link #getPosition() <em>Position</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected static final double POSITION_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected double position = POSITION_EDEFAULT;

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
	 * The default value of the '{@link #getOffsetLabel() <em>Offset Label</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOffsetLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String OFFSET_LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOffsetLabel() <em>Offset Label</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOffsetLabel()
	 * @generated
	 * @ordered
	 */
	protected String offsetLabel = OFFSET_LABEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VisualMarkerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SafecapPackage.Literals.VISUAL_MARKER;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SafecapPackage.VISUAL_MARKER__STYLE, oldStyle, style));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.VISUAL_MARKER__STYLE, oldStyle, style));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.VISUAL_MARKER__LABEL, oldLabel, label));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public double getPosition() {
		return position;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setPosition(double newPosition) {
		final double oldPosition = position;
		position = newPosition;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.VISUAL_MARKER__POSITION, oldPosition, position));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.VISUAL_MARKER__OWNER, oldOwner, owner));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.VISUAL_MARKER__INDEX, oldIndex, index));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getOffsetLabel() {
		return offsetLabel;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOffsetLabel(String newOffsetLabel) {
		final String oldOffsetLabel = offsetLabel;
		offsetLabel = newOffsetLabel;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.VISUAL_MARKER__OFFSET_LABEL, oldOffsetLabel, offsetLabel));
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
		case SafecapPackage.VISUAL_MARKER__STYLE:
			if (resolve) {
				return getStyle();
			}
			return basicGetStyle();
		case SafecapPackage.VISUAL_MARKER__LABEL:
			return getLabel();
		case SafecapPackage.VISUAL_MARKER__POSITION:
			return getPosition();
		case SafecapPackage.VISUAL_MARKER__OWNER:
			return getOwner();
		case SafecapPackage.VISUAL_MARKER__INDEX:
			return getIndex();
		case SafecapPackage.VISUAL_MARKER__OFFSET_LABEL:
			return getOffsetLabel();
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
		case SafecapPackage.VISUAL_MARKER__STYLE:
			setStyle((Style) newValue);
			return;
		case SafecapPackage.VISUAL_MARKER__LABEL:
			setLabel((String) newValue);
			return;
		case SafecapPackage.VISUAL_MARKER__POSITION:
			setPosition((Double) newValue);
			return;
		case SafecapPackage.VISUAL_MARKER__OWNER:
			setOwner(newValue);
			return;
		case SafecapPackage.VISUAL_MARKER__INDEX:
			setIndex((Integer) newValue);
			return;
		case SafecapPackage.VISUAL_MARKER__OFFSET_LABEL:
			setOffsetLabel((String) newValue);
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
		case SafecapPackage.VISUAL_MARKER__STYLE:
			setStyle((Style) null);
			return;
		case SafecapPackage.VISUAL_MARKER__LABEL:
			setLabel(LABEL_EDEFAULT);
			return;
		case SafecapPackage.VISUAL_MARKER__POSITION:
			setPosition(POSITION_EDEFAULT);
			return;
		case SafecapPackage.VISUAL_MARKER__OWNER:
			setOwner(OWNER_EDEFAULT);
			return;
		case SafecapPackage.VISUAL_MARKER__INDEX:
			setIndex(INDEX_EDEFAULT);
			return;
		case SafecapPackage.VISUAL_MARKER__OFFSET_LABEL:
			setOffsetLabel(OFFSET_LABEL_EDEFAULT);
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
		case SafecapPackage.VISUAL_MARKER__STYLE:
			return style != null;
		case SafecapPackage.VISUAL_MARKER__LABEL:
			return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
		case SafecapPackage.VISUAL_MARKER__POSITION:
			return position != POSITION_EDEFAULT;
		case SafecapPackage.VISUAL_MARKER__OWNER:
			return OWNER_EDEFAULT == null ? owner != null : !OWNER_EDEFAULT.equals(owner);
		case SafecapPackage.VISUAL_MARKER__INDEX:
			return index != INDEX_EDEFAULT;
		case SafecapPackage.VISUAL_MARKER__OFFSET_LABEL:
			return OFFSET_LABEL_EDEFAULT == null ? offsetLabel != null : !OFFSET_LABEL_EDEFAULT.equals(offsetLabel);
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
		result.append(", position: ");
		result.append(position);
		result.append(", owner: ");
		result.append(owner);
		result.append(", index: ");
		result.append(index);
		result.append(", offsetLabel: ");
		result.append(offsetLabel);
		result.append(')');
		return result.toString();
	}

} // VisualMarkerImpl
