/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema;

import org.eclipse.emf.common.util.EList;

import safecap.Extensible;
import safecap.Labeled;
import safecap.Orientation;
import safecap.Visual;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Segment</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.Segment#getFrom <em>From</em>}</li>
 * <li>{@link safecap.schema.Segment#getTo <em>To</em>}</li>
 * <li>{@link safecap.schema.Segment#getHeightmap <em>Heightmap</em>}</li>
 * <li>{@link safecap.schema.Segment#getRole <em>Role</em>}</li>
 * <li>{@link safecap.schema.Segment#getPointrole <em>Pointrole</em>}</li>
 * <li>{@link safecap.schema.Segment#getLength <em>Length</em>}</li>
 * <li>{@link safecap.schema.Segment#getSpeed <em>Speed</em>}</li>
 * <li>{@link safecap.schema.Segment#getGradient <em>Gradient</em>}</li>
 * <li>{@link safecap.schema.Segment#getSpeedlimit <em>Speedlimit</em>}</li>
 * <li>{@link safecap.schema.Segment#getOrientation <em>Orientation</em>}</li>
 * </ul>
 *
 * @see safecap.schema.SchemaPackage#getSegment()
 * @model
 * @generated
 */
public interface Segment extends Labeled, Visual, Extensible {
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
	 * @see #setFrom(Node)
	 * @see safecap.schema.SchemaPackage#getSegment_From()
	 * @model
	 * @generated
	 */
	Node getFrom();

	/**
	 * Sets the value of the '{@link safecap.schema.Segment#getFrom <em>From</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(Node value);

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
	 * @see safecap.schema.SchemaPackage#getSegment_To()
	 * @model
	 * @generated
	 */
	Node getTo();

	/**
	 * Sets the value of the '{@link safecap.schema.Segment#getTo <em>To</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Node value);

	/**
	 * Returns the value of the '<em><b>Heightmap</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Heightmap</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Heightmap</em>' containment reference.
	 * @see #setHeightmap(HeightMap)
	 * @see safecap.schema.SchemaPackage#getSegment_Heightmap()
	 * @model containment="true"
	 * @generated
	 */
	HeightMap getHeightmap();

	/**
	 * Sets the value of the '{@link safecap.schema.Segment#getHeightmap
	 * <em>Heightmap</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Heightmap</em>' containment reference.
	 * @see #getHeightmap()
	 * @generated
	 */
	void setHeightmap(HeightMap value);

	/**
	 * Returns the value of the '<em><b>Role</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Role</em>' attribute.
	 * @see #setRole(int)
	 * @see safecap.schema.SchemaPackage#getSegment_Role()
	 * @model
	 * @generated
	 */
	int getRole();

	/**
	 * Sets the value of the '{@link safecap.schema.Segment#getRole <em>Role</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Role</em>' attribute.
	 * @see #getRole()
	 * @generated
	 */
	void setRole(int value);

	/**
	 * Returns the value of the '<em><b>Pointrole</b></em>' attribute. The literals
	 * are from the enumeration {@link safecap.schema.PointRole}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pointrole</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Pointrole</em>' attribute.
	 * @see safecap.schema.PointRole
	 * @see #setPointrole(PointRole)
	 * @see safecap.schema.SchemaPackage#getSegment_Pointrole()
	 * @model
	 * @generated
	 */
	PointRole getPointrole();

	/**
	 * Sets the value of the '{@link safecap.schema.Segment#getPointrole
	 * <em>Pointrole</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Pointrole</em>' attribute.
	 * @see safecap.schema.PointRole
	 * @see #getPointrole()
	 * @generated
	 */
	void setPointrole(PointRole value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute. The default
	 * value is <code>"500"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(int)
	 * @see safecap.schema.SchemaPackage#getSegment_Length()
	 * @model default="500"
	 * @generated
	 */
	int getLength();

	/**
	 * Sets the value of the '{@link safecap.schema.Segment#getLength
	 * <em>Length</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(int value);

	/**
	 * Returns the value of the '<em><b>Speed</b></em>' containment reference list.
	 * The list contents are of type {@link safecap.schema.RouteSpeed}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Speed</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Speed</em>' containment reference list.
	 * @see safecap.schema.SchemaPackage#getSegment_Speed()
	 * @model containment="true"
	 * @generated
	 */
	EList<RouteSpeed> getSpeed();

	/**
	 * Returns the value of the '<em><b>Gradient</b></em>' attribute. The default
	 * value is <code>""</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gradient</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Gradient</em>' attribute.
	 * @see #setGradient(String)
	 * @see safecap.schema.SchemaPackage#getSegment_Gradient()
	 * @model default=""
	 * @generated
	 */
	String getGradient();

	/**
	 * Sets the value of the '{@link safecap.schema.Segment#getGradient
	 * <em>Gradient</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Gradient</em>' attribute.
	 * @see #getGradient()
	 * @generated
	 */
	void setGradient(String value);

	/**
	 * Returns the value of the '<em><b>Speedlimit</b></em>' attribute. The default
	 * value is <code>""</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Speedlimit</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Speedlimit</em>' attribute.
	 * @see #setSpeedlimit(String)
	 * @see safecap.schema.SchemaPackage#getSegment_Speedlimit()
	 * @model default=""
	 * @generated
	 */
	String getSpeedlimit();

	/**
	 * Sets the value of the '{@link safecap.schema.Segment#getSpeedlimit
	 * <em>Speedlimit</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Speedlimit</em>' attribute.
	 * @see #getSpeedlimit()
	 * @generated
	 */
	void setSpeedlimit(String value);

	/**
	 * Returns the value of the '<em><b>Orientation</b></em>' attribute. The default
	 * value is <code>"Any"</code>. The literals are from the enumeration
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
	 * @see safecap.schema.SchemaPackage#getSegment_Orientation()
	 * @model default="Any"
	 * @generated
	 */
	Orientation getOrientation();

	/**
	 * Sets the value of the '{@link safecap.schema.Segment#getOrientation
	 * <em>Orientation</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Orientation</em>' attribute.
	 * @see safecap.Orientation
	 * @see #getOrientation()
	 * @generated
	 */
	void setOrientation(Orientation value);

} // Segment
