/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside;

import org.eclipse.emf.common.util.EList;

import safecap.model.Line;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Speed
 * Limit</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.SpeedLimit#getLine <em>Line</em>}</li>
 * <li>{@link safecap.trackside.SpeedLimit#getLimit <em>Limit</em>}</li>
 * </ul>
 *
 * @see safecap.trackside.TracksidePackage#getSpeedLimit()
 * @model abstract="true"
 * @generated
 */
public interface SpeedLimit extends Equipment {
	/**
	 * Returns the value of the '<em><b>Line</b></em>' reference list. The list
	 * contents are of type {@link safecap.model.Line}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Line</em>' reference list.
	 * @see safecap.trackside.TracksidePackage#getSpeedLimit_Line()
	 * @model
	 * @generated
	 */
	EList<Line> getLine();

	/**
	 * Returns the value of the '<em><b>Limit</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Limit</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Limit</em>' attribute.
	 * @see #setLimit(double)
	 * @see safecap.trackside.TracksidePackage#getSpeedLimit_Limit()
	 * @model
	 * @generated
	 */
	double getLimit();

	/**
	 * Sets the value of the '{@link safecap.trackside.SpeedLimit#getLimit
	 * <em>Limit</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Limit</em>' attribute.
	 * @see #getLimit()
	 * @generated
	 */
	void setLimit(double value);

} // SpeedLimit
