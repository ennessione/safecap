/**
 */
package safecap.derived;

import org.eclipse.emf.common.util.EList;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Derived</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.derived.Derived#getMergedpoints
 * <em>Mergedpoints</em>}</li>
 * </ul>
 *
 * @see safecap.derived.DerivedPackage#getDerived()
 * @model abstract="true"
 * @extends ESerializableObject
 * @generated
 */
public interface Derived extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Mergedpoints</b></em>' containment reference
	 * list. The list contents are of type {@link safecap.derived.MergedPoint}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mergedpoints</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Mergedpoints</em>' containment reference list.
	 * @see safecap.derived.DerivedPackage#getDerived_Mergedpoints()
	 * @model containment="true"
	 * @generated
	 */
	EList<MergedPoint> getMergedpoints();

} // Derived
