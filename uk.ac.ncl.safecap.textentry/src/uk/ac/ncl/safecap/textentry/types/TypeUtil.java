package uk.ac.ncl.safecap.textentry.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.ac.ncl.safecap.textentry.core.TEPlugin;

public class TypeUtil {

	public List<String> suggestSchemaTypeCompletion(String prefix) {
		if (prefix == null) {
			prefix = "";
		}

		final List<String> result = new ArrayList<>();

		for (final String type : TEPlugin.getTERegistry().getTypes()) {
			if (type.startsWith(prefix)) {
				result.add(type);
			}
		}

		return result;
	}

	@SuppressWarnings("rawtypes")
	public static Object[] suggestEnum(String prefix, Collection... collections) {
		final List<Object> result = new ArrayList<>();

		for (final Collection l : collections) {
			for (final Object e : l) {
				final String name = e.toString();
				if (name.startsWith(prefix)) {
					result.add(e);
				}
			}
		}

		return result.toArray();

	}
}
