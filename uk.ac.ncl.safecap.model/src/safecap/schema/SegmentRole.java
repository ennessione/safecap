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
 * '<em><b>Segment Role</b></em>', and utility methods for working with them.
 * <!-- end-user-doc -->
 * 
 * @see safecap.schema.SchemaPackage#getSegmentRole()
 * @model
 * @generated
 */
public enum SegmentRole implements Enumerator {
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
	 * The '<em><b>Junction</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #JUNCTION_VALUE
	 * @generated
	 * @ordered
	 */
	JUNCTION(1, "Junction", "Junction"),
	/**
	 * The '<em><b>Cross A</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #CROSS_A_VALUE
	 * @generated
	 * @ordered
	 */
	CROSS_A(2, "CrossA", "CrossA"),
	/**
	 * The '<em><b>Cross B</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #CROSS_B_VALUE
	 * @generated
	 * @ordered
	 */
	CROSS_B(4, "CrossB", "CrossB");

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
	 * The '<em><b>Junction</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Junction</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #JUNCTION
	 * @model name="Junction"
	 * @generated
	 * @ordered
	 */
	public static final int JUNCTION_VALUE = 1;

	/**
	 * The '<em><b>Cross A</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Cross A</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #CROSS_A
	 * @model name="CrossA"
	 * @generated
	 * @ordered
	 */
	public static final int CROSS_A_VALUE = 2;

	/**
	 * The '<em><b>Cross B</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Cross B</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #CROSS_B
	 * @model name="CrossB"
	 * @generated
	 * @ordered
	 */
	public static final int CROSS_B_VALUE = 4;

	/**
	 * An array of all the '<em><b>Segment Role</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final SegmentRole[] VALUES_ARRAY = new SegmentRole[] { NONE, JUNCTION, CROSS_A, CROSS_B, };

	/**
	 * A public read-only list of all the '<em><b>Segment Role</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<SegmentRole> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Segment Role</b></em>' literal with the specified literal
	 * value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SegmentRole get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			final SegmentRole result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Segment Role</b></em>' literal with the specified name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SegmentRole getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			final SegmentRole result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Segment Role</b></em>' literal with the specified integer
	 * value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SegmentRole get(int value) {
		switch (value) {
		case NONE_VALUE:
			return NONE;
		case JUNCTION_VALUE:
			return JUNCTION;
		case CROSS_A_VALUE:
			return CROSS_A;
		case CROSS_B_VALUE:
			return CROSS_B;
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
	private SegmentRole(int value, String name, String literal) {
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

} // SegmentRole
