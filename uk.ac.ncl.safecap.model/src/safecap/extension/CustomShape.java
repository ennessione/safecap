/**
 */
package safecap.extension;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Custom
 * Shape</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.extension.CustomShape#getShape <em>Shape</em>}</li>
 * </ul>
 *
 * @see safecap.extension.ExtensionPackage#getCustomShape()
 * @model
 * @generated
 */
public interface CustomShape extends CustomFigure {
	/**
	 * Returns the value of the '<em><b>Shape</b></em>' containment reference list.
	 * The list contents are of type {@link safecap.extension.Point}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shape</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Shape</em>' containment reference list.
	 * @see safecap.extension.ExtensionPackage#getCustomShape_Shape()
	 * @model containment="true"
	 * @generated
	 */
	EList<Point> getShape();

} // CustomShape
