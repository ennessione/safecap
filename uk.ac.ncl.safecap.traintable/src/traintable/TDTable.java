/**
 */
package traintable;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>TD
 * Table</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link traintable.TDTable#getLabel <em>Label</em>}</li>
 * <li>{@link traintable.TDTable#getColumnLabels <em>Column Labels</em>}</li>
 * <li>{@link traintable.TDTable#getRows <em>Rows</em>}</li>
 * </ul>
 * </p>
 *
 * @see traintable.TraintablePackage#getTDTable()
 * @model
 * @generated
 */
public interface TDTable extends TDAttribute {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see traintable.TraintablePackage#getTDTable_Label()
	 * @model required="true"
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link traintable.TDTable#getLabel <em>Label</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Column Labels</b></em>' attribute list. The
	 * list contents are of type {@link java.lang.String}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Labels</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Column Labels</em>' attribute list.
	 * @see traintable.TraintablePackage#getTDTable_ColumnLabels()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getColumnLabels();

	/**
	 * Returns the value of the '<em><b>Rows</b></em>' containment reference list.
	 * The list contents are of type {@link traintable.TDTableRow}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rows</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Rows</em>' containment reference list.
	 * @see traintable.TraintablePackage#getTDTable_Rows()
	 * @model containment="true"
	 * @generated
	 */
	EList<TDTableRow> getRows();

} // TDTable
