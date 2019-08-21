package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.safecap.xdata.verification.Conjecture;

public class ReportResult implements Serializable {
	private static final long serialVersionUID = 6026447246789622902L;
	private final Map<String, List<PropertyViolation>> violations;

	public ReportResult() {
		violations = new HashMap<>();
	}

	public Collection<String> getConjectureKeys() {
		return violations.keySet();
	}

	public List<PropertyViolation> getViolations(String key) {
		return violations.get(key);
	}

	public void addPropertyViolation(String key, PropertyViolation v) {
		List<PropertyViolation> list;
		if (violations.containsKey(key)) {
			list = violations.get(key);
		} else {
			list = new ArrayList<>();
			violations.put(key, list);
		}

		list.add(v);
	}

	public void addPropertyViolation(Conjecture conjecture, Collection<Object> elements, String index, String location) {
		addPropertyViolation(conjecture.getKey().content(), new PropertyViolation(elements, index, location));
	}

}
