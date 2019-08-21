/**
 */
package safecap.config;

import org.eclipse.emf.common.util.EList;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Route
 * Construction Rule</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.config.RouteConstructionRule#getName <em>Name</em>}</li>
 * <li>{@link safecap.config.RouteConstructionRule#getIgnoredsignals
 * <em>Ignoredsignals</em>}</li>
 * </ul>
 *
 * @see safecap.config.ConfigPackage#getRouteConstructionRule()
 * @model abstract="true"
 * @extends ESerializableObject
 * @generated
 */
public interface RouteConstructionRule extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see safecap.config.ConfigPackage#getRouteConstructionRule_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link safecap.config.RouteConstructionRule#getName
	 * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Ignoredsignals</b></em>' attribute list. The
	 * list contents are of type {@link java.lang.String}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ignoredsignals</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ignoredsignals</em>' attribute list.
	 * @see safecap.config.ConfigPackage#getRouteConstructionRule_Ignoredsignals()
	 * @model
	 * @generated
	 */
	EList<String> getIgnoredsignals();

} // RouteConstructionRule
