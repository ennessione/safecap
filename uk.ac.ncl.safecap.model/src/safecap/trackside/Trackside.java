/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside;

import org.eclipse.emf.common.util.EList;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Trackside</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.Trackside#getEquipment <em>Equipment</em>}</li>
 * <li>{@link safecap.trackside.Trackside#getWires <em>Wires</em>}</li>
 * <li>{@link safecap.trackside.Trackside#getNodewires <em>Nodewires</em>}</li>
 * <li>{@link safecap.trackside.Trackside#getSubwires <em>Subwires</em>}</li>
 * <li>{@link safecap.trackside.Trackside#getSubequipment
 * <em>Subequipment</em>}</li>
 * </ul>
 *
 * @see safecap.trackside.TracksidePackage#getTrackside()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface Trackside extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Equipment</b></em>' containment reference
	 * list. The list contents are of type {@link safecap.trackside.Equipment}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Equipment</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Equipment</em>' containment reference list.
	 * @see safecap.trackside.TracksidePackage#getTrackside_Equipment()
	 * @model containment="true"
	 * @generated
	 */
	EList<Equipment> getEquipment();

	/**
	 * Returns the value of the '<em><b>Wires</b></em>' containment reference list.
	 * The list contents are of type {@link safecap.trackside.Wire}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wires</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Wires</em>' containment reference list.
	 * @see safecap.trackside.TracksidePackage#getTrackside_Wires()
	 * @model containment="true"
	 * @generated
	 */
	EList<Wire> getWires();

	/**
	 * Returns the value of the '<em><b>Nodewires</b></em>' containment reference
	 * list. The list contents are of type {@link safecap.trackside.NodeWire}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nodewires</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Nodewires</em>' containment reference list.
	 * @see safecap.trackside.TracksidePackage#getTrackside_Nodewires()
	 * @model containment="true"
	 * @generated
	 */
	EList<NodeWire> getNodewires();

	/**
	 * Returns the value of the '<em><b>Subwires</b></em>' containment reference
	 * list. The list contents are of type {@link safecap.trackside.SubWire}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subwires</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Subwires</em>' containment reference list.
	 * @see safecap.trackside.TracksidePackage#getTrackside_Subwires()
	 * @model containment="true"
	 * @generated
	 */
	EList<SubWire> getSubwires();

	/**
	 * Returns the value of the '<em><b>Subequipment</b></em>' containment reference
	 * list. The list contents are of type {@link safecap.trackside.SubEquipment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subequipment</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Subequipment</em>' containment reference list.
	 * @see safecap.trackside.TracksidePackage#getTrackside_Subequipment()
	 * @model containment="true"
	 * @generated
	 */
	EList<SubEquipment> getSubequipment();

} // Trackside
