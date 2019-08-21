/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.impl;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.Provenance;
import safecap.SafecapPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Provenance</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.impl.ProvenanceImpl#getOrigin <em>Origin</em>}</li>
 * <li>{@link safecap.impl.ProvenanceImpl#getAuthor <em>Author</em>}</li>
 * <li>{@link safecap.impl.ProvenanceImpl#getVersion <em>Version</em>}</li>
 * <li>{@link safecap.impl.ProvenanceImpl#getDate <em>Date</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ProvenanceImpl extends EObjectImpl implements Provenance {
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ProvenanceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SafecapPackage.Literals.PROVENANCE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.PROVENANCE__ORIGIN, oldOrigin, origin));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.PROVENANCE__AUTHOR, oldAuthor, author));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.PROVENANCE__VERSION, oldVersion, version));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.PROVENANCE__DATE, oldDate, date));
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
		case SafecapPackage.PROVENANCE__ORIGIN:
			return getOrigin();
		case SafecapPackage.PROVENANCE__AUTHOR:
			return getAuthor();
		case SafecapPackage.PROVENANCE__VERSION:
			return getVersion();
		case SafecapPackage.PROVENANCE__DATE:
			return getDate();
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
		case SafecapPackage.PROVENANCE__ORIGIN:
			setOrigin((String) newValue);
			return;
		case SafecapPackage.PROVENANCE__AUTHOR:
			setAuthor((String) newValue);
			return;
		case SafecapPackage.PROVENANCE__VERSION:
			setVersion((String) newValue);
			return;
		case SafecapPackage.PROVENANCE__DATE:
			setDate((Date) newValue);
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
		case SafecapPackage.PROVENANCE__ORIGIN:
			setOrigin(ORIGIN_EDEFAULT);
			return;
		case SafecapPackage.PROVENANCE__AUTHOR:
			setAuthor(AUTHOR_EDEFAULT);
			return;
		case SafecapPackage.PROVENANCE__VERSION:
			setVersion(VERSION_EDEFAULT);
			return;
		case SafecapPackage.PROVENANCE__DATE:
			setDate(DATE_EDEFAULT);
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
		case SafecapPackage.PROVENANCE__ORIGIN:
			return ORIGIN_EDEFAULT == null ? origin != null : !ORIGIN_EDEFAULT.equals(origin);
		case SafecapPackage.PROVENANCE__AUTHOR:
			return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
		case SafecapPackage.PROVENANCE__VERSION:
			return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
		case SafecapPackage.PROVENANCE__DATE:
			return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
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
		result.append(" (origin: ");
		result.append(origin);
		result.append(", author: ");
		result.append(author);
		result.append(", version: ");
		result.append(version);
		result.append(", date: ");
		result.append(date);
		result.append(')');
		return result.toString();
	}

} // ProvenanceImpl
