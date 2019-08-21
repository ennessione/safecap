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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Height
 * Map</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.HeightMap#getPoints <em>Points</em>}</li>
 * </ul>
 *
 * @see safecap.schema.SchemaPackage#getHeightMap()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface HeightMap extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Points</b></em>' containment reference list.
	 * The list contents are of type {@link safecap.schema.HeightPoint}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Points</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Points</em>' containment reference list.
	 * @see safecap.schema.SchemaPackage#getHeightMap_Points()
	 * @model containment="true"
	 * @generated
	 */
	EList<HeightPoint> getPoints();

} // HeightMap
