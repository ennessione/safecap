/**
 */
package safecap.derived;

import org.eclipse.emf.common.util.EList;

import safecap.model.Ambit;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Merged
 * Ambit</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.derived.MergedAmbit#getAmbits <em>Ambits</em>}</li>
 * <li>{@link safecap.derived.MergedAmbit#isComposed <em>Composed</em>}</li>
 * <li>{@link safecap.derived.MergedAmbit#isDisjoint <em>Disjoint</em>}</li>
 * </ul>
 *
 * @see safecap.derived.DerivedPackage#getMergedAmbit()
 * @model abstract="true"
 * @generated
 */
public interface MergedAmbit extends DerivedElement, Ambit {
	/**
	 * Returns the value of the '<em><b>Ambits</b></em>' reference list. The list
	 * contents are of type {@link safecap.model.Ambit}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ambits</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ambits</em>' reference list.
	 * @see safecap.derived.DerivedPackage#getMergedAmbit_Ambits()
	 * @model
	 * @generated
	 */
	EList<Ambit> getAmbits();

	/**
	 * Returns the value of the '<em><b>Composed</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composed</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Composed</em>' attribute.
	 * @see #setComposed(boolean)
	 * @see safecap.derived.DerivedPackage#getMergedAmbit_Composed()
	 * @model
	 * @generated
	 */
	boolean isComposed();

	/**
	 * Sets the value of the '{@link safecap.derived.MergedAmbit#isComposed
	 * <em>Composed</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Composed</em>' attribute.
	 * @see #isComposed()
	 * @generated
	 */
	void setComposed(boolean value);

	/**
	 * Returns the value of the '<em><b>Disjoint</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Disjoint</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Disjoint</em>' attribute.
	 * @see #setDisjoint(boolean)
	 * @see safecap.derived.DerivedPackage#getMergedAmbit_Disjoint()
	 * @model
	 * @generated
	 */
	boolean isDisjoint();

	/**
	 * Sets the value of the '{@link safecap.derived.MergedAmbit#isDisjoint
	 * <em>Disjoint</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Disjoint</em>' attribute.
	 * @see #isDisjoint()
	 * @generated
	 */
	void setDisjoint(boolean value);

} // MergedAmbit
