/**
 */
package traintable.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import traintable.TDTable;
import traintable.TDTableRow;
import traintable.TraintablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>TD
 * Table</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link traintable.impl.TDTableImpl#getLabel <em>Label</em>}</li>
 * <li>{@link traintable.impl.TDTableImpl#getColumnLabels <em>Column
 * Labels</em>}</li>
 * <li>{@link traintable.impl.TDTableImpl#getRows <em>Rows</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TDTableImpl extends TDAttributeImpl implements TDTable {
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
	 * The cached value of the '{@link #getColumnLabels() <em>Column Labels</em>}'
	 * attribute list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getColumnLabels()
	 * @generated
	 * @ordered
	 */
	protected EList<String> columnLabels;

	/**
	 * The cached value of the '{@link #getRows() <em>Rows</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRows()
	 * @generated
	 * @ordered
	 */
	protected EList<TDTableRow> rows;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TDTableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TraintablePackage.Literals.TD_TABLE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TraintablePackage.TD_TABLE__LABEL, oldLabel, label));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<String> getColumnLabels() {
		if (columnLabels == null) {
			columnLabels = new EDataTypeEList<>(String.class, this, TraintablePackage.TD_TABLE__COLUMN_LABELS);
		}
		return columnLabels;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<TDTableRow> getRows() {
		if (rows == null) {
			rows = new EObjectContainmentEList<>(TDTableRow.class, this, TraintablePackage.TD_TABLE__ROWS);
		}
		return rows;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case TraintablePackage.TD_TABLE__ROWS:
			return ((InternalEList<?>) getRows()).basicRemove(otherEnd, msgs);
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
		case TraintablePackage.TD_TABLE__LABEL:
			return getLabel();
		case TraintablePackage.TD_TABLE__COLUMN_LABELS:
			return getColumnLabels();
		case TraintablePackage.TD_TABLE__ROWS:
			return getRows();
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
		case TraintablePackage.TD_TABLE__LABEL:
			setLabel((String) newValue);
			return;
		case TraintablePackage.TD_TABLE__COLUMN_LABELS:
			getColumnLabels().clear();
			getColumnLabels().addAll((Collection<? extends String>) newValue);
			return;
		case TraintablePackage.TD_TABLE__ROWS:
			getRows().clear();
			getRows().addAll((Collection<? extends TDTableRow>) newValue);
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
		case TraintablePackage.TD_TABLE__LABEL:
			setLabel(LABEL_EDEFAULT);
			return;
		case TraintablePackage.TD_TABLE__COLUMN_LABELS:
			getColumnLabels().clear();
			return;
		case TraintablePackage.TD_TABLE__ROWS:
			getRows().clear();
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
		case TraintablePackage.TD_TABLE__LABEL:
			return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
		case TraintablePackage.TD_TABLE__COLUMN_LABELS:
			return columnLabels != null && !columnLabels.isEmpty();
		case TraintablePackage.TD_TABLE__ROWS:
			return rows != null && !rows.isEmpty();
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

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (label: ");
		result.append(label);
		result.append(", columnLabels: ");
		result.append(columnLabels);
		result.append(')');
		return result.toString();
	}

} // TDTableImpl
