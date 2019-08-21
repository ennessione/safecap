/**
 */
package safecap.extension;

import org.eclipse.emf.common.util.EList;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Ext
 * Visual</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.extension.ExtVisual#getFigures <em>Figures</em>}</li>
 * </ul>
 *
 * @see safecap.extension.ExtensionPackage#getExtVisual()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface ExtVisual extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Figures</b></em>' containment reference
	 * list. The list contents are of type {@link safecap.extension.CustomFigure}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Figures</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Figures</em>' containment reference list.
	 * @see safecap.extension.ExtensionPackage#getExtVisual_Figures()
	 * @model containment="true"
	 * @generated
	 */
	EList<CustomFigure> getFigures();

} // ExtVisual
