package uk.ac.ncl.safecap.xmldata;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeDecompositionBuilder {
	private DataContext context;
	private String type;
	private String outTemplate;
	private Pattern pattern;

	private Map<String, String> result;

	public TypeDecompositionBuilder(DataContext context, String type, String template) throws TypeDecompositionException {
		this.context = context;
		this.type = type;

		final String[] parts = template.split(":");
		if (parts.length < 2 || parts.length > 3) {
			throw new TypeDecompositionException("Invalid template format '" + template + "' - expect <name>:<regex>[:<template>]");
		}

		if (context.getEnum(type) == null) {
			return;
			// throw new TypeDecompositionException("Invalid type name - " + type);
		}

		if (parts.length == 3) {
			outTemplate = parts[2].trim();
		}

		result = new HashMap<>();
		try {
			pattern = Pattern.compile(parts[1].trim());
			build(parts[0].trim());
		} catch (final Throwable e) {
			throw new TypeDecompositionException(e.getMessage());
		}

		// System.out.println("pattern: " + pattern);
		// System.out.println("result: " + result);

		// if (result.isEmpty())
		// throw new TypeDecompositionException("Pattern " + template + " matches no
		// values for type " + type);

		final TypeDecomposition td = context.getTypeDecompositions(type, parts[0].trim());
		if (td != null) {
			td.getMapping().putAll(result);
		} else {
			final TypeDecomposition tdec = new TypeDecomposition(parts[0].trim(), type, pattern.pattern(), outTemplate, result, "");
			context.addTypeDecomposition(tdec);
		}
	}

	private void build(String function) throws TypeDecompositionException {
		for (final String element : context.getEnumMembers(type)) {
			build(function, element);
		}
	}

	private void build(String function, String element) throws TypeDecompositionException {
		final Matcher matcher = pattern.matcher(element);

		if (matcher.matches()) {
			if (matcher.groupCount() > 0) {
				if (outTemplate == null) {
					if (matcher.groupCount() > 1) {
						throw new TypeDecompositionException(
								"Invalid template format '" + pattern + "'- expect regex with one capture group");
					} else if (matcher.groupCount() == 1) {
						final String newValue = matcher.group(1);
						result.put(element, newValue);
					}
				} else {
					String newValue = outTemplate;
					for (int i = 1; i < matcher.groupCount() + 1; i++) {
						final String value = matcher.group(i);
						newValue = newValue.replaceAll("\\$" + i, value);
					}
					newValue = newValue.trim();
					result.put(element, newValue);
				}
			} else {
				result.put(element, "nil");
			}
		}
	}

}