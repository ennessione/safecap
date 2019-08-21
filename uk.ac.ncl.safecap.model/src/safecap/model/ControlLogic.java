/**
 */
package safecap.model;

import org.eclipse.emf.common.util.EList;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Control
 * Logic</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.model.ControlLogic#getLine <em>Line</em>}</li>
 * <li>{@link safecap.model.ControlLogic#getAspects <em>Aspects</em>}</li>
 * <li>{@link safecap.model.ControlLogic#getRule <em>Rule</em>}</li>
 * <li>{@link safecap.model.ControlLogic#getNormal <em>Normal</em>}</li>
 * <li>{@link safecap.model.ControlLogic#getReverse <em>Reverse</em>}</li>
 * </ul>
 *
 * @see safecap.model.ModelPackage#getControlLogic()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface ControlLogic extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Line</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Line</em>' reference.
	 * @see #setLine(Line)
	 * @see safecap.model.ModelPackage#getControlLogic_Line()
	 * @model
	 * @generated
	 */
	Line getLine();

	/**
	 * Sets the value of the '{@link safecap.model.ControlLogic#getLine
	 * <em>Line</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Line</em>' reference.
	 * @see #getLine()
	 * @generated
	 */
	void setLine(Line value);

	/**
	 * Returns the value of the '<em><b>Aspects</b></em>' attribute. The default
	 * value is <code>"2"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Aspects</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Aspects</em>' attribute.
	 * @see #setAspects(int)
	 * @see safecap.model.ModelPackage#getControlLogic_Aspects()
	 * @model default="2"
	 * @generated
	 */
	int getAspects();

	/**
	 * Sets the value of the '{@link safecap.model.ControlLogic#getAspects
	 * <em>Aspects</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Aspects</em>' attribute.
	 * @see #getAspects()
	 * @generated
	 */
	void setAspects(int value);

	/**
	 * Returns the value of the '<em><b>Rule</b></em>' containment reference list.
	 * The list contents are of type {@link safecap.model.Rule}. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Rule</em>' containment reference list.
	 * @see safecap.model.ModelPackage#getControlLogic_Rule()
	 * @model containment="true"
	 * @generated
	 */
	EList<Rule> getRule();

	/**
	 * Returns the value of the '<em><b>Normal</b></em>' reference list. The list
	 * contents are of type {@link safecap.model.Point}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Normal</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Normal</em>' reference list.
	 * @see safecap.model.ModelPackage#getControlLogic_Normal()
	 * @model
	 * @generated
	 */
	EList<Point> getNormal();

	/**
	 * Returns the value of the '<em><b>Reverse</b></em>' reference list. The list
	 * contents are of type {@link safecap.model.Point}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reverse</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Reverse</em>' reference list.
	 * @see safecap.model.ModelPackage#getControlLogic_Reverse()
	 * @model
	 * @generated
	 */
	EList<Point> getReverse();

} // ControlLogic
