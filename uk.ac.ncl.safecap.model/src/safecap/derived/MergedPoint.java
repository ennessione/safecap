/**
 */
package safecap.derived;

import org.eclipse.emf.common.util.EList;

import safecap.model.Point;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Merged
 * Point</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.derived.MergedPoint#getPoints <em>Points</em>}</li>
 * </ul>
 *
 * @see safecap.derived.DerivedPackage#getMergedPoint()
 * @model
 * @generated
 */
public interface MergedPoint extends DerivedElement, Point {
	/**
	 * Returns the value of the '<em><b>Points</b></em>' reference list. The list
	 * contents are of type {@link safecap.model.Point}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Points</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Points</em>' reference list.
	 * @see safecap.derived.DerivedPackage#getMergedPoint_Points()
	 * @model
	 * @generated
	 */
	EList<Point> getPoints();

} // MergedPoint
