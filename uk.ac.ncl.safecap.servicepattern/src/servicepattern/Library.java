/**
 */
package servicepattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Library</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link servicepattern.Library#getPatterns <em>Patterns</em>}</li>
 * </ul>
 * </p>
 *
 * @see servicepattern.ServicepatternPackage#getLibrary()
 * @model
 * @generated
 */
public interface Library extends EObject {
	/**
	 * Returns the value of the '<em><b>Patterns</b></em>' containment reference
	 * list. The list contents are of type {@link servicepattern.Pattern}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Patterns</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Patterns</em>' containment reference list.
	 * @see servicepattern.ServicepatternPackage#getLibrary_Patterns()
	 * @model containment="true"
	 * @generated
	 */
	EList<Pattern> getPatterns();

} // Library
