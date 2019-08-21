/**
 */
package safecap.schema.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.schema.SchemaPackage;
import safecap.schema.SubSchemaPath;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Sub
 * Schema Path</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.impl.SubSchemaPathImpl#getLength
 * <em>Length</em>}</li>
 * <li>{@link safecap.schema.impl.SubSchemaPathImpl#getFrom <em>From</em>}</li>
 * <li>{@link safecap.schema.impl.SubSchemaPathImpl#getTo <em>To</em>}</li>
 * <li>{@link safecap.schema.impl.SubSchemaPathImpl#getPnormal
 * <em>Pnormal</em>}</li>
 * <li>{@link safecap.schema.impl.SubSchemaPathImpl#getPreverse
 * <em>Preverse</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SubSchemaPathImpl extends EObjectImpl implements SubSchemaPath {
	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final int LENGTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected int length = LENGTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getFrom() <em>From</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected static final String FROM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected String from = FROM_EDEFAULT;

	/**
	 * The default value of the '{@link #getTo() <em>To</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected static final String TO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected String to = TO_EDEFAULT;

	/**
	 * The default value of the '{@link #getPnormal() <em>Pnormal</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPnormal()
	 * @generated
	 * @ordered
	 */
	protected static final int PNORMAL_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPnormal() <em>Pnormal</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPnormal()
	 * @generated
	 * @ordered
	 */
	protected int pnormal = PNORMAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getPreverse() <em>Preverse</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPreverse()
	 * @generated
	 * @ordered
	 */
	protected static final int PREVERSE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPreverse() <em>Preverse</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPreverse()
	 * @generated
	 * @ordered
	 */
	protected int preverse = PREVERSE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SubSchemaPathImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SchemaPackage.Literals.SUB_SCHEMA_PATH;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setFrom(String newFrom) {
		final String oldFrom = from;
		from = newFrom;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SUB_SCHEMA_PATH__FROM, oldFrom, from));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTo(String newTo) {
		final String oldTo = to;
		to = newTo;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SUB_SCHEMA_PATH__TO, oldTo, to));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getPnormal() {
		return pnormal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setPnormal(int newPnormal) {
		final int oldPnormal = pnormal;
		pnormal = newPnormal;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SUB_SCHEMA_PATH__PNORMAL, oldPnormal, pnormal));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getPreverse() {
		return preverse;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setPreverse(int newPreverse) {
		final int oldPreverse = preverse;
		preverse = newPreverse;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SUB_SCHEMA_PATH__PREVERSE, oldPreverse, preverse));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getLength() {
		return length;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLength(int newLength) {
		final int oldLength = length;
		length = newLength;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SUB_SCHEMA_PATH__LENGTH, oldLength, length));
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
		case SchemaPackage.SUB_SCHEMA_PATH__LENGTH:
			return getLength();
		case SchemaPackage.SUB_SCHEMA_PATH__FROM:
			return getFrom();
		case SchemaPackage.SUB_SCHEMA_PATH__TO:
			return getTo();
		case SchemaPackage.SUB_SCHEMA_PATH__PNORMAL:
			return getPnormal();
		case SchemaPackage.SUB_SCHEMA_PATH__PREVERSE:
			return getPreverse();
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
		case SchemaPackage.SUB_SCHEMA_PATH__LENGTH:
			setLength((Integer) newValue);
			return;
		case SchemaPackage.SUB_SCHEMA_PATH__FROM:
			setFrom((String) newValue);
			return;
		case SchemaPackage.SUB_SCHEMA_PATH__TO:
			setTo((String) newValue);
			return;
		case SchemaPackage.SUB_SCHEMA_PATH__PNORMAL:
			setPnormal((Integer) newValue);
			return;
		case SchemaPackage.SUB_SCHEMA_PATH__PREVERSE:
			setPreverse((Integer) newValue);
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
		case SchemaPackage.SUB_SCHEMA_PATH__LENGTH:
			setLength(LENGTH_EDEFAULT);
			return;
		case SchemaPackage.SUB_SCHEMA_PATH__FROM:
			setFrom(FROM_EDEFAULT);
			return;
		case SchemaPackage.SUB_SCHEMA_PATH__TO:
			setTo(TO_EDEFAULT);
			return;
		case SchemaPackage.SUB_SCHEMA_PATH__PNORMAL:
			setPnormal(PNORMAL_EDEFAULT);
			return;
		case SchemaPackage.SUB_SCHEMA_PATH__PREVERSE:
			setPreverse(PREVERSE_EDEFAULT);
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
		case SchemaPackage.SUB_SCHEMA_PATH__LENGTH:
			return length != LENGTH_EDEFAULT;
		case SchemaPackage.SUB_SCHEMA_PATH__FROM:
			return FROM_EDEFAULT == null ? from != null : !FROM_EDEFAULT.equals(from);
		case SchemaPackage.SUB_SCHEMA_PATH__TO:
			return TO_EDEFAULT == null ? to != null : !TO_EDEFAULT.equals(to);
		case SchemaPackage.SUB_SCHEMA_PATH__PNORMAL:
			return pnormal != PNORMAL_EDEFAULT;
		case SchemaPackage.SUB_SCHEMA_PATH__PREVERSE:
			return preverse != PREVERSE_EDEFAULT;
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
		result.append(" (length: ");
		result.append(length);
		result.append(", from: ");
		result.append(from);
		result.append(", to: ");
		result.append(to);
		result.append(", pnormal: ");
		result.append(pnormal);
		result.append(", preverse: ");
		result.append(preverse);
		result.append(')');
		return result.toString();
	}

} // SubSchemaPathImpl
