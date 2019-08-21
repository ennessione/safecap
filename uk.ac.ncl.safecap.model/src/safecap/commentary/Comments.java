/**
 */
package safecap.commentary;

import org.eclipse.emf.common.util.EList;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Comments</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.commentary.Comments#getComments <em>Comments</em>}</li>
 * </ul>
 *
 * @see safecap.commentary.CommentaryPackage#getComments()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface Comments extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Comments</b></em>' containment reference
	 * list. The list contents are of type {@link safecap.commentary.Commentary}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comments</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Comments</em>' containment reference list.
	 * @see safecap.commentary.CommentaryPackage#getComments_Comments()
	 * @model containment="true"
	 * @generated
	 */
	EList<Commentary> getComments();

} // Comments
