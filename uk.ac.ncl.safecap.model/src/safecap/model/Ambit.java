/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.model;

import org.eclipse.emf.common.util.EList;

import safecap.Extensible;
import safecap.Labeled;
import safecap.schema.Segment;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Ambit</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.model.Ambit#getSegments <em>Segments</em>}</li>
 * </ul>
 *
 * @see safecap.model.ModelPackage#getAmbit()
 * @model abstract="true"
 * @generated
 */
public interface Ambit extends Labeled, Extensible {
	/**
	 * Returns the value of the '<em><b>Segments</b></em>' reference list. The list
	 * contents are of type {@link safecap.schema.Segment}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Segments</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Segments</em>' reference list.
	 * @see safecap.model.ModelPackage#getAmbit_Segments()
	 * @model required="true"
	 * @generated
	 */
	EList<Segment> getSegments();

} // Ambit
