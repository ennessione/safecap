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
import safecap.schema.Segment;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Route</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.model.Route#getAmbits <em>Ambits</em>}</li>
 * <li>{@link safecap.model.Route#getSegments <em>Segments</em>}</li>
 * <li>{@link safecap.model.Route#getOverlap <em>Overlap</em>}</li>
 * <li>{@link safecap.model.Route#getRule <em>Rule</em>}</li>
 * <li>{@link safecap.model.Route#getAspects <em>Aspects</em>}</li>
 * <li>{@link safecap.model.Route#getOrientation <em>Orientation</em>}</li>
 * <li>{@link safecap.model.Route#getLogic <em>Logic</em>}</li>
 * <li>{@link safecap.model.Route#getDefaultlogic <em>Defaultlogic</em>}</li>
 * </ul>
 *
 * @see safecap.model.ModelPackage#getRoute()
 * @model
 * @generated
 */
public interface Route extends Labeled, Extensible {
	/**
	 * Returns the value of the '<em><b>Ambits</b></em>' reference list. The list
	 * contents are of type {@link safecap.model.Ambit}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ambits</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ambits</em>' reference list.
	 * @see safecap.model.ModelPackage#getRoute_Ambits()
	 * @model required="true"
	 * @generated
	 */
	EList<Ambit> getAmbits();

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
	 * @see safecap.model.ModelPackage#getRoute_Segments()
	 * @model required="true"
	 * @generated
	 */
	EList<Segment> getSegments();

	/**
	 * Returns the value of the '<em><b>Overlap</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Overlap</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Overlap</em>' reference.
	 * @see #setOverlap(Ambit)
	 * @see safecap.model.ModelPackage#getRoute_Overlap()
	 * @model
	 * @generated
	 */
	Ambit getOverlap();

	/**
	 * Sets the value of the '{@link safecap.model.Route#getOverlap
	 * <em>Overlap</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Overlap</em>' reference.
	 * @see #getOverlap()
	 * @generated
	 */
	void setOverlap(Ambit value);

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
	 * @see safecap.model.ModelPackage#getRoute_Rule()
	 * @model containment="true"
	 * @generated
	 */
	EList<Rule> getRule();

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
	 * @see safecap.model.ModelPackage#getRoute_Aspects()
	 * @model default="2"
	 * @generated
	 */
	int getAspects();

	/**
	 * Sets the value of the '{@link safecap.model.Route#getAspects
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
	 * @see safecap.model.ModelPackage#getRoute_Rule()
	 * @model containment="true"
	 * @deprecated
	 * @generated NOT
	 */
	// private EList<Rule> getRule();

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
	 * @see safecap.model.ModelPackage#getRoute_Aspects()
	 * @model default="2"
	 * @deprecated
	 * @generated NOT
	 */
	// int getAspects();

	/**
	 * Sets the value of the '{@link safecap.model.Route#getAspects
	 * <em>Aspects</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Aspects</em>' attribute.
	 * @see #getAspects()
	 * @deprecated
	 * @generated NOT
	 */
	// void setAspects(int value);

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
	 * @see safecap.model.ModelPackage#getRoute_Orientation()
	 * @model default="Left"
	 * @generated
	 */
	Orientation getOrientation();

	/**
	 * Sets the value of the '{@link safecap.model.Route#getOrientation
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
	 * Returns the value of the '<em><b>Logic</b></em>' containment reference list.
	 * The list contents are of type {@link safecap.model.ControlLogic}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Logic</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Logic</em>' containment reference list.
	 * @see safecap.model.ModelPackage#getRoute_Logic()
	 * @model containment="true"
	 * @generated
	 */
	EList<ControlLogic> getLogic();

	/**
	 * Returns the value of the '<em><b>Defaultlogic</b></em>' containment
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Defaultlogic</em>' containment reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Defaultlogic</em>' containment reference.
	 * @see #setDefaultlogic(ControlLogic)
	 * @see safecap.model.ModelPackage#getRoute_Defaultlogic()
	 * @model containment="true"
	 * @generated
	 */
	ControlLogic getDefaultlogic();

	/**
	 * Sets the value of the '{@link safecap.model.Route#getDefaultlogic
	 * <em>Defaultlogic</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Defaultlogic</em>' containment
	 *              reference.
	 * @see #getDefaultlogic()
	 * @generated
	 */
	void setDefaultlogic(ControlLogic value);

} // Route
