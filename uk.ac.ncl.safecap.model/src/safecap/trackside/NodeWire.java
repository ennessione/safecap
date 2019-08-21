/**
 */
package safecap.trackside;

import safecap.schema.Node;
import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Node
 * Wire</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.NodeWire#getFrom <em>From</em>}</li>
 * <li>{@link safecap.trackside.NodeWire#getTo <em>To</em>}</li>
 * </ul>
 *
 * @see safecap.trackside.TracksidePackage#getNodeWire()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface NodeWire extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>From</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(Equipment)
	 * @see safecap.trackside.TracksidePackage#getNodeWire_From()
	 * @model
	 * @generated
	 */
	Equipment getFrom();

	/**
	 * Sets the value of the '{@link safecap.trackside.NodeWire#getFrom
	 * <em>From</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(Equipment value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>To</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(Node)
	 * @see safecap.trackside.TracksidePackage#getNodeWire_To()
	 * @model
	 * @generated
	 */
	Node getTo();

	/**
	 * Sets the value of the '{@link safecap.trackside.NodeWire#getTo <em>To</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Node value);

} // NodeWire
