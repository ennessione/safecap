/**
 */
package safecap.trackside;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration
 * '<em><b>Signal Type</b></em>', and utility methods for working with them.
 * <!-- end-user-doc -->
 * 
 * @see safecap.trackside.TracksidePackage#getSignalType()
 * @model
 * @generated
 */
public enum SignalType implements Enumerator {
	/**
	 * The '<em><b>Controlled</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #CONTROLLED_VALUE
	 * @generated
	 * @ordered
	 */
	CONTROLLED(0, "Controlled", "Controlled"),

	/**
	 * The '<em><b>Automatic</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #AUTOMATIC_VALUE
	 * @generated
	 * @ordered
	 */
	AUTOMATIC(1, "Automatic", "Automatic"),

	/**
	 * The '<em><b>Distant</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #DISTANT_VALUE
	 * @generated
	 * @ordered
	 */
	DISTANT(2, "Distant", "Distant"),

	/**
	 * The '<em><b>Semi Automatic</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #SEMI_AUTOMATIC_VALUE
	 * @generated
	 * @ordered
	 */
	SEMI_AUTOMATIC(3, "SemiAutomatic", "Semi Automatic"),

	/**
	 * The '<em><b>Banner Repeater</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #BANNER_REPEATER_VALUE
	 * @generated
	 * @ordered
	 */
	BANNER_REPEATER(4, "BannerRepeater", "Banner Repeater"),
	/**
	 * The '<em><b>Shunt</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #SHUNT_VALUE
	 * @generated
	 * @ordered
	 */
	SHUNT(5, "Shunt", "Shunt"),
	/**
	 * The '<em><b>Preset Shunt</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #PRESET_SHUNT_VALUE
	 * @generated
	 * @ordered
	 */
	PRESET_SHUNT(6, "PresetShunt", "PresetShunt"),
	/**
	 * The '<em><b>Block Marker</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #BLOCK_MARKER_VALUE
	 * @generated
	 * @ordered
	 */
	BLOCK_MARKER(7, "BlockMarker", "BlockMarker"),
	/**
	 * The '<em><b>Virtual Block Marker</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #VIRTUAL_BLOCK_MARKER_VALUE
	 * @generated
	 * @ordered
	 */
	VIRTUAL_BLOCK_MARKER(8, "VirtualBlockMarker", "VirtualBlockMarker");

	/**
	 * The '<em><b>Controlled</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Controlled</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #CONTROLLED
	 * @model name="Controlled"
	 * @generated
	 * @ordered
	 */
	public static final int CONTROLLED_VALUE = 0;

	/**
	 * The '<em><b>Automatic</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Automatic</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #AUTOMATIC
	 * @model name="Automatic"
	 * @generated
	 * @ordered
	 */
	public static final int AUTOMATIC_VALUE = 1;

	/**
	 * The '<em><b>Distant</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Distant</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #DISTANT
	 * @model name="Distant"
	 * @generated
	 * @ordered
	 */
	public static final int DISTANT_VALUE = 2;

	/**
	 * The '<em><b>Semi Automatic</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Semi Automatic</b></em>' literal object isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #SEMI_AUTOMATIC
	 * @model name="SemiAutomatic" literal="Semi Automatic"
	 * @generated
	 * @ordered
	 */
	public static final int SEMI_AUTOMATIC_VALUE = 3;

	/**
	 * The '<em><b>Banner Repeater</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Banner Repeater</b></em>' literal object isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #BANNER_REPEATER
	 * @model name="BannerRepeater" literal="Banner Repeater"
	 * @generated
	 * @ordered
	 */
	public static final int BANNER_REPEATER_VALUE = 4;

	/**
	 * The '<em><b>Shunt</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Shunt</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #SHUNT
	 * @model name="Shunt"
	 * @generated
	 * @ordered
	 */
	public static final int SHUNT_VALUE = 5;

	/**
	 * The '<em><b>Preset Shunt</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Preset Shunt</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #PRESET_SHUNT
	 * @model name="PresetShunt"
	 * @generated
	 * @ordered
	 */
	public static final int PRESET_SHUNT_VALUE = 6;

	/**
	 * The '<em><b>Block Marker</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Block Marker</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #BLOCK_MARKER
	 * @model name="BlockMarker"
	 * @generated
	 * @ordered
	 */
	public static final int BLOCK_MARKER_VALUE = 7;

	/**
	 * The '<em><b>Virtual Block Marker</b></em>' literal value. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of '<em><b>Virtual Block Marker</b></em>' literal object isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #VIRTUAL_BLOCK_MARKER
	 * @model name="VirtualBlockMarker"
	 * @generated
	 * @ordered
	 */
	public static final int VIRTUAL_BLOCK_MARKER_VALUE = 8;

	/**
	 * An array of all the '<em><b>Signal Type</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final SignalType[] VALUES_ARRAY = new SignalType[] { CONTROLLED, AUTOMATIC, DISTANT, SEMI_AUTOMATIC, BANNER_REPEATER,
			SHUNT, PRESET_SHUNT, BLOCK_MARKER, VIRTUAL_BLOCK_MARKER, };

	/**
	 * A public read-only list of all the '<em><b>Signal Type</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<SignalType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Signal Type</b></em>' literal with the specified literal
	 * value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SignalType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			final SignalType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Signal Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SignalType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			final SignalType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Signal Type</b></em>' literal with the specified integer
	 * value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SignalType get(int value) {
		switch (value) {
		case CONTROLLED_VALUE:
			return CONTROLLED;
		case AUTOMATIC_VALUE:
			return AUTOMATIC;
		case DISTANT_VALUE:
			return DISTANT;
		case SEMI_AUTOMATIC_VALUE:
			return SEMI_AUTOMATIC;
		case BANNER_REPEATER_VALUE:
			return BANNER_REPEATER;
		case SHUNT_VALUE:
			return SHUNT;
		case PRESET_SHUNT_VALUE:
			return PRESET_SHUNT;
		case BLOCK_MARKER_VALUE:
			return BLOCK_MARKER;
		case VIRTUAL_BLOCK_MARKER_VALUE:
			return VIRTUAL_BLOCK_MARKER;
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
	private SignalType(int value, String name, String literal) {
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

} // SignalType
