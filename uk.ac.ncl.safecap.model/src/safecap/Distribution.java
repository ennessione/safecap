/**
 */
package safecap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration
 * '<em><b>Distribution</b></em>', and utility methods for working with them.
 * <!-- end-user-doc -->
 * 
 * @see safecap.SafecapPackage#getDistribution()
 * @model
 * @generated
 */
public enum Distribution implements Enumerator {
	/**
	 * The '<em><b>Uniform</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #UNIFORM_VALUE
	 * @generated
	 * @ordered
	 */
	UNIFORM(0, "Uniform", "Uniform"),

	/**
	 * The '<em><b>Normal</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #NORMAL_VALUE
	 * @generated
	 * @ordered
	 */
	NORMAL(1, "Normal", "Normal"),

	/**
	 * The '<em><b>Poisson</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #POISSON_VALUE
	 * @generated
	 * @ordered
	 */
	POISSON(2, "Poisson", "Poisson"),

	/**
	 * The '<em><b>Exponential</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #EXPONENTIAL_VALUE
	 * @generated
	 * @ordered
	 */
	EXPONENTIAL(3, "Exponential", "Exponential");

	/**
	 * The '<em><b>Uniform</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Uniform</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #UNIFORM
	 * @model name="Uniform"
	 * @generated
	 * @ordered
	 */
	public static final int UNIFORM_VALUE = 0;

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
	public static final int NORMAL_VALUE = 1;

	/**
	 * The '<em><b>Poisson</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Poisson</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #POISSON
	 * @model name="Poisson"
	 * @generated
	 * @ordered
	 */
	public static final int POISSON_VALUE = 2;

	/**
	 * The '<em><b>Exponential</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Exponential</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #EXPONENTIAL
	 * @model name="Exponential"
	 * @generated
	 * @ordered
	 */
	public static final int EXPONENTIAL_VALUE = 3;

	/**
	 * An array of all the '<em><b>Distribution</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final Distribution[] VALUES_ARRAY = new Distribution[] { UNIFORM, NORMAL, POISSON, EXPONENTIAL, };

	/**
	 * A public read-only list of all the '<em><b>Distribution</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<Distribution> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Distribution</b></em>' literal with the specified literal
	 * value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static Distribution get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			final Distribution result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Distribution</b></em>' literal with the specified name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static Distribution getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			final Distribution result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Distribution</b></em>' literal with the specified integer
	 * value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static Distribution get(int value) {
		switch (value) {
		case UNIFORM_VALUE:
			return UNIFORM;
		case NORMAL_VALUE:
			return NORMAL;
		case POISSON_VALUE:
			return POISSON;
		case EXPONENTIAL_VALUE:
			return EXPONENTIAL;
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
	private Distribution(int value, String name, String literal) {
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

} // Distribution
