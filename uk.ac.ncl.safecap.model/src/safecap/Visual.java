/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap;

import org.eclipse.emf.common.util.EList;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Visual</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.Visual#isVisible <em>Visible</em>}</li>
 * <li>{@link safecap.Visual#getStyle <em>Style</em>}</li>
 * <li>{@link safecap.Visual#getIntervals <em>Intervals</em>}</li>
 * <li>{@link safecap.Visual#getMarkers <em>Markers</em>}</li>
 * </ul>
 *
 * @see safecap.SafecapPackage#getVisual()
 * @model abstract="true"
 * @extends ESerializableObject
 * @generated
 */
public interface Visual extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Visible</b></em>' attribute. The default
	 * value is <code>"true"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visible</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Visible</em>' attribute.
	 * @see #setVisible(boolean)
	 * @see safecap.SafecapPackage#getVisual_Visible()
	 * @model default="true"
	 * @generated
	 */
	boolean isVisible();

	/**
	 * Sets the value of the '{@link safecap.Visual#isVisible <em>Visible</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Visible</em>' attribute.
	 * @see #isVisible()
	 * @generated
	 */
	void setVisible(boolean value);

	/**
	 * Returns the value of the '<em><b>Style</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Style</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Style</em>' reference.
	 * @see #setStyle(Style)
	 * @see safecap.SafecapPackage#getVisual_Style()
	 * @model transient="true"
	 * @generated
	 */
	Style getStyle();

	/**
	 * Sets the value of the '{@link safecap.Visual#getStyle <em>Style</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Style</em>' reference.
	 * @see #getStyle()
	 * @generated
	 */
	void setStyle(Style value);

	/**
	 * Returns the value of the '<em><b>Intervals</b></em>' reference list. The list
	 * contents are of type {@link safecap.HighlightedInterval}. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Intervals</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Intervals</em>' reference list.
	 * @see safecap.SafecapPackage#getVisual_Intervals()
	 * @model transient="true"
	 * @generated
	 */
	EList<HighlightedInterval> getIntervals();

	/**
	 * Returns the value of the '<em><b>Markers</b></em>' containment reference
	 * list. The list contents are of type {@link safecap.VisualMarker}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Markers</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Markers</em>' containment reference list.
	 * @see safecap.SafecapPackage#getVisual_Markers()
	 * @model containment="true"
	 * @generated
	 */
	EList<VisualMarker> getMarkers();

} // Visual
