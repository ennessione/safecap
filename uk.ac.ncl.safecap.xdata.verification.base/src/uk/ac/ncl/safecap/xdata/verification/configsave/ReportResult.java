package uk.ac.ncl.safecap.xdata.verification.configsave;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportResult implements Serializable {
	private static final long serialVersionUID = 6026447246789622902L;
	private final Map<String, List<PropertyViolation>> violations;
	private final Map<String, PropertyReview> reviews;

	public ReportResult() {
		violations = new HashMap<>();
		reviews = new HashMap<>();
	}

	public Map<String, PropertyReview> getReviews() {
		return reviews;
	}

	public void addReview(String key, PropertyReview.STATE state, String commentary) {
		reviews.put(key, new PropertyReview(state, commentary));
	}

	public PropertyReview addReview(String key) {
		final PropertyReview pr = new PropertyReview(PropertyReview.STATE.NONE, "");
		reviews.put(key, pr);
		return pr;
	}

	public Collection<String> getConjectureKeys() {
		return violations.keySet();
	}

	public Collection<String> getReviewKeys() {
		return reviews.keySet();
	}

	public List<PropertyViolation> getViolations(String key) {
		return violations.get(key);
	}

	public PropertyViolation getViolation(String key, String hash) {
		if (!violations.containsKey(key)) {
			return null;
		}

		for (final PropertyViolation v : violations.get(key)) {
			if (hash.equals(v.getHash())) {
				return v;
			}
		}

		return null;
	}

	public PropertyReview getReviews(String key) {
		return reviews.get(key);
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

	public void addPropertyViolation(String key, String hash, String location) {
		addPropertyViolation(key, new PropertyViolation(hash, location));
	}

	public PropertyViolation getPreviousViolation(String key, String hash) {
		final List<PropertyViolation> list = violations.get(key);
		if (list != null) {
			for (int i = 1; i < list.size(); i++) {
				if (list.get(i).getHash().equals(hash)) {
					return list.get(i - 1);
				}
			}
		}

		return null;
	}

}
