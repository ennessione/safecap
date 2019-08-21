/**
 */
package safecap.derived;

import org.eclipse.emf.common.util.EList;

import safecap.Extensible;
import safecap.Labeled;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Element</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.derived.DerivedElement#getCodes <em>Codes</em>}</li>
 * </ul>
 *
 * @see safecap.derived.DerivedPackage#getDerivedElement()
 * @model abstract="true"
 * @generated
 */
public interface DerivedElement extends Labeled, Extensible {

	/**
	 * Returns the value of the '<em><b>Codes</b></em>' attribute list. The list
	 * contents are of type {@link java.lang.String}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Codes</em>' attribute list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Codes</em>' attribute list.
	 * @see safecap.derived.DerivedPackage#getDerivedElement_Codes()
	 * @model
	 * @generated
	 */
	EList<String> getCodes();
} // DerivedElement
