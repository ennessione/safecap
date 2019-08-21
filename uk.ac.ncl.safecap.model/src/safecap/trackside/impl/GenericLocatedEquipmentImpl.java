/**
 */
package safecap.trackside.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import safecap.Orientation;
import safecap.schema.Segment;
import safecap.trackside.GenericLocatedEquipment;
import safecap.trackside.TracksidePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Generic
 * Located Equipment</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.impl.GenericLocatedEquipmentImpl#getOffset
 * <em>Offset</em>}</li>
 * <li>{@link safecap.trackside.impl.GenericLocatedEquipmentImpl#getType
 * <em>Type</em>}</li>
 * <li>{@link safecap.trackside.impl.GenericLocatedEquipmentImpl#getSegment
 * <em>Segment</em>}</li>
 * <li>{@link safecap.trackside.impl.GenericLocatedEquipmentImpl#getOrientation
 * <em>Orientation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenericLocatedEquipmentImpl extends EquipmentImpl implements GenericLocatedEquipment {
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
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSegment() <em>Segment</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSegment()
	 * @generated
	 * @ordered
	 */
	protected Segment segment;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected GenericLocatedEquipmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracksidePackage.Literals.GENERIC_LOCATED_EQUIPMENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.GENERIC_LOCATED_EQUIPMENT__OFFSET, oldOffset, offset));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setType(String newType) {
		final String oldType = type;
		type = newType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.GENERIC_LOCATED_EQUIPMENT__TYPE, oldType, type));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Segment getSegment() {
		if (segment != null && ((EObject) segment).eIsProxy()) {
			final InternalEObject oldSegment = (InternalEObject) segment;
			segment = (Segment) eResolveProxy(oldSegment);
			if (segment != oldSegment) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracksidePackage.GENERIC_LOCATED_EQUIPMENT__SEGMENT,
							oldSegment, segment));
				}
			}
		}
		return segment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Segment basicGetSegment() {
		return segment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSegment(Segment newSegment) {
		final Segment oldSegment = segment;
		segment = newSegment;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.GENERIC_LOCATED_EQUIPMENT__SEGMENT, oldSegment,
					segment));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TracksidePackage.GENERIC_LOCATED_EQUIPMENT__ORIENTATION, oldOrientation,
					orientation));
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
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__OFFSET:
			return getOffset();
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__TYPE:
			return getType();
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__SEGMENT:
			if (resolve) {
				return getSegment();
			}
			return basicGetSegment();
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__ORIENTATION:
			return getOrientation();
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
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__OFFSET:
			setOffset((Integer) newValue);
			return;
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__TYPE:
			setType((String) newValue);
			return;
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__SEGMENT:
			setSegment((Segment) newValue);
			return;
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__ORIENTATION:
			setOrientation((Orientation) newValue);
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
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__OFFSET:
			setOffset(OFFSET_EDEFAULT);
			return;
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__TYPE:
			setType(TYPE_EDEFAULT);
			return;
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__SEGMENT:
			setSegment((Segment) null);
			return;
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__ORIENTATION:
			setOrientation(ORIENTATION_EDEFAULT);
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
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__OFFSET:
			return offset != OFFSET_EDEFAULT;
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__TYPE:
			return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__SEGMENT:
			return segment != null;
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT__ORIENTATION:
			return orientation != ORIENTATION_EDEFAULT;
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
		result.append(" (offset: ");
		result.append(offset);
		result.append(", type: ");
		result.append(type);
		result.append(", orientation: ");
		result.append(orientation);
		result.append(')');
		return result.toString();
	}

} // GenericLocatedEquipmentImpl
