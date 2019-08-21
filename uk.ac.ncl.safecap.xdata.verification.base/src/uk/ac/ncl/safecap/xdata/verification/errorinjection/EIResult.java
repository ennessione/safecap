package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EIResult {
	public enum QUERY {
		ALL, VALID, INVALID
	};

	private List<List<EIRecord>> records;
	private List<EIRecord> current;
	private String error;
	private transient String[] kinds;
	private transient String[] functions;
	private List<BaseInjection> correct;
	private List<BaseInjection> failed;

	public EIResult() {
		records = new ArrayList<>(100);
		current = new ArrayList<>(5);
		correct = new ArrayList<>();
		failed = new ArrayList<>();
	}

	public void addCorrect(BaseInjection injection) {
		correct.add(injection);
	}

	public void addFailed(BaseInjection injection) {
		failed.add(injection);
	}

	public List<BaseInjection> getCorrect() {
		return correct;
	}

	public List<BaseInjection> getFailed() {
		return failed;
	}

	public String[] getKinds() {
		if (functions == null) {
			final List<String> result = new ArrayList<>();
			if (records != null && records.size() > 0) {
				for (final List<EIRecord> set : records) {
					for (final EIRecord r : set) {
						if (!result.contains(r.getKind())) {
							result.add(r.getKind());
						}
					}
				}
			}

			functions = result.toArray(new String[result.size()]);
		}
		return functions;
	}

	public String[] getFunctions() {
		if (kinds == null) {
			final List<String> result = new ArrayList<>();
			if (records != null && records.size() > 0) {
				for (final List<EIRecord> set : records) {
					for (final EIRecord r : set) {
						if (!result.contains(r.getFunction())) {
							result.add(r.getFunction());
						}
					}
				}
			}

			kinds = result.toArray(new String[result.size()]);
		}
		return kinds;
	}

	public Map<String, Integer> getCumulativeValid() {
		final Map<String, Integer> result = new HashMap<>();

		if (records != null) {
			for (final List<EIRecord> set : records) {
				for (final EIRecord r : set) {
					final int current = result.containsKey(r.getProperty()) ? result.get(r.getProperty()) : 0;
					result.put(r.getProperty(), current + r.getResult());
				}
			}
		}

		return result;
	}

	public Map<String, int[]> getByFunction(QUERY query) {
		final Map<String, int[]> result = new HashMap<>();
		final String[] kinds = getKinds();
		if (records != null) {
			for (final List<EIRecord> set : records) {
				for (final EIRecord r : set) {
					int[] current = result.get(r.getFunction());
					if (current == null) {
						current = new int[kinds.length];
						result.put(r.getFunction(), current);
					}
					final int kindIndex = getIndexInArray(kinds, r.getKind());
					switch (query) {
					case ALL:
						current[kindIndex] = current[kindIndex] + 1;
						break;
					case VALID:
						current[kindIndex] = current[kindIndex] + r.getResult();
						break;
					case INVALID:
						current[kindIndex] = current[kindIndex] + 1 - r.getResult();
						break;
					}
				}
			}
		}

		return result;
	}

	public Map<String, int[]> getByProperty(QUERY query) {
		final Map<String, int[]> result = new HashMap<>();
		final String[] kinds = getKinds();
		if (records != null) {
			for (final List<EIRecord> set : records) {
				for (final EIRecord r : set) {
					int[] current = result.get(r.getProperty());
					if (current == null) {
						current = new int[kinds.length];
						result.put(r.getProperty(), current);
					}
					final int kindIndex = getIndexInArray(kinds, r.getKind());
					switch (query) {
					case ALL:
						current[kindIndex] = current[kindIndex] + 1;
						break;
					case VALID:
						current[kindIndex] = current[kindIndex] + r.getResult();
						break;
					case INVALID:
						current[kindIndex] = current[kindIndex] + 1 - r.getResult();
						break;
					}
				}
			}
		}

		return result;
	}

	private int getIndexInArray(String[] array, String kind) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(kind)) {
				return i;
			}
		}
		return 0;
	}

	public EIResult(String error) {
		this.error = error;
	}

	public void add(String property, String function, String kind, boolean result) {
		current.add(new EIRecord(property, function, kind, result));
	}

	public void advance() {
		records.add(current);
		current = new ArrayList<>(5);
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<List<EIRecord>> getRecords() {
		return records;
	}

}
