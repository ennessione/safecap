/**
 */
package safecap.config;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Path
 * Route Construction Rule</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.config.PathRouteConstructionRule#getEntry
 * <em>Entry</em>}</li>
 * <li>{@link safecap.config.PathRouteConstructionRule#getExit
 * <em>Exit</em>}</li>
 * <li>{@link safecap.config.PathRouteConstructionRule#getPointstates
 * <em>Pointstates</em>}</li>
 * </ul>
 *
 * @see safecap.config.ConfigPackage#getPathRouteConstructionRule()
 * @model
 * @generated
 */
public interface PathRouteConstructionRule extends RouteConstructionRule {
	/**
	 * Returns the value of the '<em><b>Entry</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Entry</em>' attribute.
	 * @see #setEntry(String)
	 * @see safecap.config.ConfigPackage#getPathRouteConstructionRule_Entry()
	 * @model required="true"
	 * @generated
	 */
	String getEntry();

	/**
	 * Sets the value of the
	 * '{@link safecap.config.PathRouteConstructionRule#getEntry <em>Entry</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Entry</em>' attribute.
	 * @see #getEntry()
	 * @generated
	 */
	void setEntry(String value);

	/**
	 * Returns the value of the '<em><b>Exit</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exit</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Exit</em>' attribute.
	 * @see #setExit(String)
	 * @see safecap.config.ConfigPackage#getPathRouteConstructionRule_Exit()
	 * @model required="true"
	 * @generated
	 */
	String getExit();

	/**
	 * Sets the value of the
	 * '{@link safecap.config.PathRouteConstructionRule#getExit <em>Exit</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Exit</em>' attribute.
	 * @see #getExit()
	 * @generated
	 */
	void setExit(String value);

	/**
	 * Returns the value of the '<em><b>Pointstates</b></em>' attribute list. The
	 * list contents are of type {@link java.lang.String}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pointstates</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Pointstates</em>' attribute list.
	 * @see safecap.config.ConfigPackage#getPathRouteConstructionRule_Pointstates()
	 * @model
	 * @generated
	 */
	EList<String> getPointstates();

} // PathRouteConstructionRule
