/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap;

import org.eclipse.emf.common.util.EList;

import safecap.commentary.Comments;
import safecap.config.RouteBuilderConfiguration;
import safecap.config.SchemaConfiguration;
import safecap.derived.Derived;
import safecap.model.Model;
import safecap.schema.Schema;
import safecap.trackside.Trackside;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Project</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.Project#getConfiguration <em>Configuration</em>}</li>
 * <li>{@link safecap.Project#getRoutebuilder <em>Routebuilder</em>}</li>
 * <li>{@link safecap.Project#getDarkmatter <em>Darkmatter</em>}</li>
 * </ul>
 *
 * @see safecap.SafecapPackage#getProject()
 * @model
 * @generated
 */
public interface Project extends Labeled, Schema, Model, Trackside, Comments, Extensible, Derived, Provenance {

	/**
	 * Returns the value of the '<em><b>Configuration</b></em>' containment
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configuration</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Configuration</em>' containment reference.
	 * @see #setConfiguration(SchemaConfiguration)
	 * @see safecap.SafecapPackage#getProject_Configuration()
	 * @model containment="true"
	 * @generated
	 */
	SchemaConfiguration getConfiguration();

	/**
	 * Sets the value of the '{@link safecap.Project#getConfiguration
	 * <em>Configuration</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Configuration</em>' containment
	 *              reference.
	 * @see #getConfiguration()
	 * @generated
	 */
	void setConfiguration(SchemaConfiguration value);

	/**
	 * Returns the value of the '<em><b>Routebuilder</b></em>' containment
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Routebuilder</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Routebuilder</em>' containment reference.
	 * @see #setRoutebuilder(RouteBuilderConfiguration)
	 * @see safecap.SafecapPackage#getProject_Routebuilder()
	 * @model containment="true"
	 * @generated
	 */
	RouteBuilderConfiguration getRoutebuilder();

	/**
	 * Sets the value of the '{@link safecap.Project#getRoutebuilder
	 * <em>Routebuilder</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Routebuilder</em>' containment
	 *              reference.
	 * @see #getRoutebuilder()
	 * @generated
	 */
	void setRoutebuilder(RouteBuilderConfiguration value);

	/**
	 * Returns the value of the '<em><b>Darkmatter</b></em>' containment reference
	 * list. The list contents are of type {@link safecap.Labeled}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Darkmatter</em>' containment reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Darkmatter</em>' containment reference list.
	 * @see safecap.SafecapPackage#getProject_Darkmatter()
	 * @model containment="true"
	 * @generated
	 */
	EList<Labeled> getDarkmatter();
} // Project
