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
import safecap.Orientation;
import safecap.Visual;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Line</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.model.Line#getRoutes <em>Routes</em>}</li>
 * <li>{@link safecap.model.Line#getColor <em>Color</em>}</li>
 * <li>{@link safecap.model.Line#getOrientation <em>Orientation</em>}</li>
 * <li>{@link safecap.model.Line#getTrafficmix <em>Trafficmix</em>}</li>
 * </ul>
 *
 * @see safecap.model.ModelPackage#getLine()
 * @model
 * @generated
 */
public interface Line extends Labeled, Extensible, Visual {
	/**
	 * Returns the value of the '<em><b>Routes</b></em>' reference list. The list
	 * contents are of type {@link safecap.model.Route}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Routes</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Routes</em>' reference list.
	 * @see safecap.model.ModelPackage#getLine_Routes()
	 * @model
	 * @generated
	 */
	EList<Route> getRoutes();

	/**
	 * Returns the value of the '<em><b>Color</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Color</em>' attribute.
	 * @see #setColor(Object)
	 * @see safecap.model.ModelPackage#getLine_Color()
	 * @model
	 * @generated
	 */
	Object getColor();

	/**
	 * Sets the value of the '{@link safecap.model.Line#getColor <em>Color</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Color</em>' attribute.
	 * @see #getColor()
	 * @generated
	 */
	void setColor(Object value);

	/**
	 * Returns the value of the '<em><b>Orientation</b></em>' attribute. The default
	 * value is <code>"Left"</code>. The literals are from the enumeration
	 * {@link safecap.Orientation}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Orientation</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Orientation</em>' attribute.
	 * @see safecap.Orientation
	 * @see #setOrientation(Orientation)
	 * @see safecap.model.ModelPackage#getLine_Orientation()
	 * @model default="Left"
	 * @generated
	 */
	Orientation getOrientation();

	/**
	 * Sets the value of the '{@link safecap.model.Line#getOrientation
	 * <em>Orientation</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Orientation</em>' attribute.
	 * @see safecap.Orientation
	 * @see #getOrientation()
	 * @generated
	 */
	void setOrientation(Orientation value);

	/**
	 * Returns the value of the '<em><b>Trafficmix</b></em>' attribute list. The
	 * list contents are of type {@link java.lang.String}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trafficmix</em>' attribute list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Trafficmix</em>' attribute list.
	 * @see safecap.model.ModelPackage#getLine_Trafficmix()
	 * @model
	 * @generated
	 */
	EList<String> getTrafficmix();

} // Line
