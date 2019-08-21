package uk.ac.ncl.safecap.textentry.core;

public class TEUtil {

	public static String getProtoSchemaType(String text, int offset) {
		final int leftBracket = offset;
		// scan left for separator
		int namePos = leftBracket - 1;
		while (namePos > 0 && isSeparator(text.charAt(namePos))) {
			namePos--;
		}
		for (; namePos > 0 && !isSeparator(text.charAt(namePos)); namePos--) {
			;
		}
		if (isSeparator(text.charAt(namePos))) {
			// scan left again for another separator
			int typePos = namePos - 1;
			while (typePos > 0 && isSeparator(text.charAt(typePos))) {
				typePos--;
			}
			for (; typePos >= 0 && !isSeparator(text.charAt(typePos)); typePos--) {
				;
			}
			if (typePos == -1 || isSeparator(text.charAt(typePos))) {
				final String type = text.substring(typePos, namePos).trim();
				return type;
			}
		}

		return null;
	}

	public static String getEntryName(String text, int offset) {
		// scan left for whitespace
		// scan left for :
		int colon = offset;
		for (; colon > 0 && text.charAt(colon) != ':' && !isBracket(text.charAt(colon)); colon--) {
			;
		}

		if (text.charAt(colon) == ':') {
			int namePos1 = colon - 1;
			while (namePos1 > 0 && Character.isWhitespace(text.charAt(namePos1))) {
				namePos1--;
			}

			int namePos2 = namePos1;
			while (namePos2 > 0 && !isSeparator(text.charAt(namePos2))) {
				namePos2--;
			}

			final String result = text.substring(namePos2 + 1, namePos1 + 1);
			return result;

		}

		return null;
	}

	public static TEPart getEnclosingSchemaInfo(String text, int offset) {
		final String type = getEnclosingSchemaType(text, offset);
		if (type != null) {
			return TEPlugin.getTERegistry().getSchemaFor(type);
		}

		return null;
	}

	public static String getEnclosingSchemaType(String text, int offset) {
		// scan left for {
		int leftBracket = offset;
		for (; leftBracket > 0 && text.charAt(leftBracket) != '{'; leftBracket--) {
			;
		}

		// scan left for }
		int rightBracket = offset;
		for (; rightBracket < text.length() && text.charAt(rightBracket) != '}'; rightBracket++) {
			;
		}

		if (text.charAt(leftBracket) == '{' && text.charAt(rightBracket) == '}') {
			// scan left for separator
			int namePos = leftBracket - 1;
			while (namePos > 0 && isSeparator(text.charAt(namePos))) {
				namePos--;
			}
			for (; namePos > 0 && !isSeparator(text.charAt(namePos)); namePos--) {
				;
			}
			if (isSeparator(text.charAt(namePos))) {
				// scan left again for another separator
				int typePos = namePos - 1;
				while (typePos > 0 && isSeparator(text.charAt(typePos))) {
					typePos--;
				}
				for (; typePos >= 0 && !isSeparator(text.charAt(typePos)); typePos--) {
					;
				}
				if (typePos == -1 || isSeparator(text.charAt(typePos))) {
					final String type = text.substring(typePos, namePos).trim();
					return type;
				}
			}
		}

		return null;
	}

	public static TEPart getEnclosingPart(String text, int offset) {
		// scan left for {
		int leftBracket = offset;
		for (; leftBracket > 0 && text.charAt(leftBracket) != '{'; leftBracket--) {
			;
		}

		// scan left for }
		int rightBracket = offset;
		for (; rightBracket < text.length() && text.charAt(rightBracket) != '}'; rightBracket++) {
			;
		}

		if (text.charAt(leftBracket) == '{' && text.charAt(rightBracket) == '}') {
			// scan left for separator
			int namePos = leftBracket - 1;
			while (namePos > 0 && isSeparator(text.charAt(namePos))) {
				namePos--;
			}
			for (; namePos > 0 && !isSeparator(text.charAt(namePos)); namePos--) {
				;
			}
			if (isSeparator(text.charAt(namePos))) {
				// scan left again for another separator
				int typePos = namePos - 1;
				while (typePos > 0 && isSeparator(text.charAt(typePos))) {
					typePos--;
				}
				for (; typePos >= 0 && !isSeparator(text.charAt(typePos)); typePos--) {
					;
				}
				if (typePos == -1 || isSeparator(text.charAt(typePos))) {
					final String partText = text.substring(typePos + 1, rightBracket + 1);
					return TEBuilder.build(partText);
				}
			}
		}

		return null;
	}

	private static boolean isBracket(char c) {
		return c == '{' || c == '}';
	}

	private static boolean isSeparator(char c) {
		return Character.isWhitespace(c) || c == '{' || c == '}' || c == ':' || c == '"' || c == '[' || c == ']';
	}
}
