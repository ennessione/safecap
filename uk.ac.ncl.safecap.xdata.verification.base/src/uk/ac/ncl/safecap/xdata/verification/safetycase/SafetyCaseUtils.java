package uk.ac.ncl.safecap.xdata.verification.safetycase;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SafetyCaseUtils {
	public static SafetyStatement findByName(SafetyCase scase, String name) {
		if (name == null || scase == null) {
			return null;
		}

		for (final SafetyStatement s : scase.getStatements()) {
			if (!s.getId().empty() && name.equals(s.getId().content())) {
				return s;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static Set<SafetyStatement> getParents(SafetyStatement element) {
		if (element == null) {
			return Collections.EMPTY_SET;
		}

		final Set<SafetyStatement> result = new HashSet<>();

		final SafetyCase scase = (SafetyCase) element.root();
		for (final SafetyLink slink : scase.getLinks()) {
			if (slink.getToStatement().target() == element) {
				result.add(slink.getFromStatement().target());
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static Set<SafetyStatement> getChildren(SafetyStatement element) {
		if (element == null) {
			return Collections.EMPTY_SET;
		}

		final Set<SafetyStatement> result = new HashSet<>();

		final SafetyCase scase = (SafetyCase) element.root();
		for (final SafetyLink slink : scase.getLinks()) {
			if (slink.getFromStatement().target() == element) {
				result.add(slink.getToStatement().target());
			}
		}

		return result;
	}

}
