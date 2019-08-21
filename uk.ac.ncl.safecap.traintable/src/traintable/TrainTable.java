/**
 */
package traintable;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Train
 * Table</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link traintable.TrainTable#getTrains <em>Trains</em>}</li>
 * </ul>
 * </p>
 *
 * @see traintable.TraintablePackage#getTrainTable()
 * @model
 * @generated
 */
public interface TrainTable extends EObject {
	/**
	 * Returns the value of the '<em><b>Trains</b></em>' containment reference list.
	 * The list contents are of type {@link traintable.Train}. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Trains</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Trains</em>' containment reference list.
	 * @see traintable.TraintablePackage#getTrainTable_Trains()
	 * @model containment="true"
	 * @generated
	 */
	EList<Train> getTrains();

} // TrainTable
