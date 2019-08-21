/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration
 * '<em><b>Node Role</b></em>', and utility methods for working with them. <!--
 * end-user-doc -->
 * 
 * @see safecap.schema.SchemaPackage#getNodeRole()
 * @model
 * @generated
 */
public enum NodeRole implements Enumerator {
	/**
	 * The '<em><b>None</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(0, "None", "None"),

	/**
	 * The '<em><b>Left Overlap</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #LEFT_OVERLAP_VALUE
	 * @generated
	 * @ordered
	 */
	LEFT_OVERLAP(1, "LeftOverlap", "LeftOverlap"),
	/**
	 * The '<em><b>Right Overlap</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #RIGHT_OVERLAP_VALUE
	 * @generated
	 * @ordered
	 */
	RIGHT_OVERLAP(2, "RightOverlap", "RightOverlap"),
	/**
	 * The '<em><b>Left Point</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #LEFT_POINT_VALUE
	 * @generated
	 * @ordered
	 */
	LEFT_POINT(4, "LeftPoint", "LeftPoint"),
	/**
	 * The '<em><b>Source</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #SOURCE_VALUE
	 * @generated
	 * @ordered
	 */
	SOURCE(8, "Source", "Source"),
	/**
	 * The '<em><b>Sink</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #SINK_VALUE
	 * @generated
	 * @ordered
	 */
	SINK(16, "Sink", "Sink");

	/**
	 * The '<em><b>None</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>None</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #NONE
	 * @model name="None"
	 * @generated
	 * @ordered
	 */
	public static final int NONE_VALUE = 0;

	/**
	 * The '<em><b>Left Overlap</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Left Overlap</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #LEFT_OVERLAP
	 * @model name="LeftOverlap"
	 * @generated
	 * @ordered
	 */
	public static final int LEFT_OVERLAP_VALUE = 1;

	/**
	 * The '<em><b>Right Overlap</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Right Overlap</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #RIGHT_OVERLAP
	 * @model name="RightOverlap"
	 * @generated
	 * @ordered
	 */
	public static final int RIGHT_OVERLAP_VALUE = 2;

	/**
	 * The '<em><b>Left Point</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Left Point</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #LEFT_POINT
	 * @model name="LeftPoint"
	 * @generated
	 * @ordered
	 */
	public static final int LEFT_POINT_VALUE = 4;

	/**
	 * The '<em><b>Source</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Source</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #SOURCE
	 * @model name="Source"
	 * @generated
	 * @ordered
	 */
	public static final int SOURCE_VALUE = 8;

	/**
	 * The '<em><b>Sink</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Sink</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #SINK
	 * @model name="Sink"
	 * @generated
	 * @ordered
	 */
	public static final int SINK_VALUE = 16;

	/**
	 * An array of all the '<em><b>Node Role</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final NodeRole[] VALUES_ARRAY = new NodeRole[] { NONE, LEFT_OVERLAP, RIGHT_OVERLAP, LEFT_POINT, SOURCE, SINK, };

	/**
	 * A public read-only list of all the '<em><b>Node Role</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<NodeRole> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Node Role</b></em>' literal with the specified literal
	 * value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static NodeRole get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			final NodeRole result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Node Role</b></em>' literal with the specified name. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static NodeRole getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			final NodeRole result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Node Role</b></em>' literal with the specified integer
	 * value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static NodeRole get(int value) {
		switch (value) {
		case NONE_VALUE:
			return NONE;
		case LEFT_OVERLAP_VALUE:
			return LEFT_OVERLAP;
		case RIGHT_OVERLAP_VALUE:
			return RIGHT_OVERLAP;
		case LEFT_POINT_VALUE:
			return LEFT_POINT;
		case SOURCE_VALUE:
			return SOURCE;
		case SINK_VALUE:
			return SINK;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	private NodeRole(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string
	 * representation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // NodeRole
