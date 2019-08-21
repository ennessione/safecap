/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema;

import org.eclipse.emf.common.util.EList;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Schema</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.Schema#getNodes <em>Nodes</em>}</li>
 * <li>{@link safecap.schema.Schema#getSegments <em>Segments</em>}</li>
 * </ul>
 *
 * @see safecap.schema.SchemaPackage#getSchema()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface Schema extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link safecap.schema.Node}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nodes</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see safecap.schema.SchemaPackage#getSchema_Nodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Node> getNodes();

	/**
	 * Returns the value of the '<em><b>Segments</b></em>' containment reference
	 * list. The list contents are of type {@link safecap.schema.Segment}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Segments</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Segments</em>' containment reference list.
	 * @see safecap.schema.SchemaPackage#getSchema_Segments()
	 * @model containment="true"
	 * @generated
	 */
	EList<Segment> getSegments();

} // Schema
