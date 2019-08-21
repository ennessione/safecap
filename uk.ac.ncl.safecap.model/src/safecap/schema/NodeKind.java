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
 * '<em><b>Node Kind</b></em>', and utility methods for working with them. <!--
 * end-user-doc -->
 * 
 * @see safecap.schema.SchemaPackage#getNodeKind()
 * @model
 * @generated
 */
public enum NodeKind implements Enumerator {
	/**
	 * The '<em><b>Silent</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #SILENT_VALUE
	 * @generated
	 * @ordered
	 */
	SILENT(-1, "Silent", "Silent"),
	/**
	 * The '<em><b>Normal</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #NORMAL_VALUE
	 * @generated
	 * @ordered
	 */
	NORMAL(0, "Normal", "Normal"),

	/**
	 * The '<em><b>Boundary</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #BOUNDARY_VALUE
	 * @generated
	 * @ordered
	 */
	BOUNDARY(1, "Boundary", "Boundary"),

	/**
	 * The '<em><b>Terminal</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #TERMINAL_VALUE
	 * @generated
	 * @ordered
	 */
	TERMINAL(2, "Terminal", "Terminal"),

	/**
	 * The '<em><b>Crossover</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #CROSSOVER_VALUE
	 * @generated
	 * @ordered
	 */
	CROSSOVER(3, "Crossover", "Crossover"),
	/**
	 * The '<em><b>Point</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #POINT_VALUE
	 * @generated
	 * @ordered
	 */
	POINT(4, "Point", "Point"),
	/**
	 * The '<em><b>Switched Crossover</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #SWITCHED_CROSSOVER_VALUE
	 * @generated
	 * @ordered
	 */
	SWITCHED_CROSSOVER(5, "SwitchedCrossover", "SwitchedCrossover"),
	/**
	 * The '<em><b>Invalid</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #INVALID_VALUE
	 * @generated
	 * @ordered
	 */
	INVALID(101, "Invalid", "Invalid");

	/**
	 * The '<em><b>Silent</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Silent</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #SILENT
	 * @model name="Silent"
	 * @generated
	 * @ordered
	 */
	public static final int SILENT_VALUE = -1;

	/**
	 * The '<em><b>Normal</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Normal</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #NORMAL
	 * @model name="Normal"
	 * @generated
	 * @ordered
	 */
	public static final int NORMAL_VALUE = 0;

	/**
	 * The '<em><b>Boundary</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Boundary</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #BOUNDARY
	 * @model name="Boundary"
	 * @generated
	 * @ordered
	 */
	public static final int BOUNDARY_VALUE = 1;

	/**
	 * The '<em><b>Terminal</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Terminal</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #TERMINAL
	 * @model name="Terminal"
	 * @generated
	 * @ordered
	 */
	public static final int TERMINAL_VALUE = 2;

	/**
	 * The '<em><b>Crossover</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Crossover</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #CROSSOVER
	 * @model name="Crossover"
	 * @generated
	 * @ordered
	 */
	public static final int CROSSOVER_VALUE = 3;

	/**
	 * The '<em><b>Point</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Point</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #POINT
	 * @model name="Point"
	 * @generated
	 * @ordered
	 */
	public static final int POINT_VALUE = 4;

	/**
	 * The '<em><b>Switched Crossover</b></em>' literal value. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of '<em><b>Switched Crossover</b></em>' literal object isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #SWITCHED_CROSSOVER
	 * @model name="SwitchedCrossover"
	 * @generated
	 * @ordered
	 */
	public static final int SWITCHED_CROSSOVER_VALUE = 5;

	/**
	 * The '<em><b>Invalid</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Invalid</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #INVALID
	 * @model name="Invalid"
	 * @generated
	 * @ordered
	 */
	public static final int INVALID_VALUE = 101;

	/**
	 * An array of all the '<em><b>Node Kind</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final NodeKind[] VALUES_ARRAY = new NodeKind[] { SILENT, NORMAL, BOUNDARY, TERMINAL, CROSSOVER, POINT,
			SWITCHED_CROSSOVER, INVALID, };

	/**
	 * A public read-only list of all the '<em><b>Node Kind</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<NodeKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Node Kind</b></em>' literal with the specified literal
	 * value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static NodeKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			final NodeKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Node Kind</b></em>' literal with the specified name. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static NodeKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			final NodeKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Node Kind</b></em>' literal with the specified integer
	 * value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static NodeKind get(int value) {
		switch (value) {
		case SILENT_VALUE:
			return SILENT;
		case NORMAL_VALUE:
			return NORMAL;
		case BOUNDARY_VALUE:
			return BOUNDARY;
		case TERMINAL_VALUE:
			return TERMINAL;
		case CROSSOVER_VALUE:
			return CROSSOVER;
		case POINT_VALUE:
			return POINT;
		case SWITCHED_CROSSOVER_VALUE:
			return SWITCHED_CROSSOVER;
		case INVALID_VALUE:
			return INVALID;
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
	private NodeKind(int value, String name, String literal) {
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

} // NodeKind
