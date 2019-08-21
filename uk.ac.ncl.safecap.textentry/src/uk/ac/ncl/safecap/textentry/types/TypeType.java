package uk.ac.ncl.safecap.textentry.types;

import java.util.Arrays;
import java.util.List;

import uk.ac.ncl.safecap.textentry.core.TEPart;
import uk.ac.ncl.safecap.textentry.core.TEPlugin;

public class TypeType implements ITEType {
	public static final String TYPE_INT = "int";
	public static final String TYPE_BOOL = "bool";
	public static final String TYPE_FLOAT = "float";
	public static final String TYPE_STRING = "string";

	private static final List<String> BUILT_IN_TYPES = Arrays.asList(new String[] { TYPE_INT, TYPE_BOOL, TYPE_FLOAT, TYPE_STRING, "type" });

	@Override
	public String checkMember(TEPart element, Object value) {
		final String name = value.toString();
		if (BUILT_IN_TYPES.contains(name)) {
			return null;
		}

		if (TEPlugin.getTERegistry().getTypes().contains(name)) {
			return null;
		}

		return "Unknown type " + name;
	}

	@Override
	public Object[] suggest(TEPart element, String prefix) {
		return TypeUtil.suggestEnum(prefix, BUILT_IN_TYPES, TEPlugin.getTERegistry().getTypes());
	}

	public static boolean isBuiltIn(String name) {
		return BUILT_IN_TYPES.contains(name);
	}

	@Override
	public String bestGuess(TEPart element, String entry) {
		return TYPE_INT;
	}

}
