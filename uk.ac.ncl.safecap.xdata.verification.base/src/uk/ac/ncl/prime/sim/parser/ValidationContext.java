package uk.ac.ncl.prime.sim.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.prime.sim.lang.CLElement;
import uk.ac.ncl.prime.sim.lang.parser.ErrInfo;

public class ValidationContext {
	private static final List<ErrInfo> noParseErrors = new ArrayList<>();

	private final Map<CLElement, String> errors;
	private final Map<CLElement, String> warnings;
	private final Map<CLElement, String> notes;
	private final List<CLElement> errors_list;
	private final List<CLElement> warnings_list;
	private final List<CLElement> notes_list;
	private List<ErrInfo> parse_errors;

//	private final static String INV_PREFIX = "inv";
//	private final static String VAR_PREFIX = "vav";
//	private final static String ASSERT_PREFIX = "assert";
//	private final static String RELY_PREFIX = "rely";
//	private final static String GUAR_PREFIX = "guar";
//	private final static String PRE_PREFIX = "pre";
//	private final static String POST_PREFIX = "post";
//
//	private final int inv_counter = 0;
//	private final int var_counter = 0;
//	private int assert_counter = 0;
//	private final int rely_counter = 0;
//	private final int guar_counter = 0;
//	private final int pre_counter = 0;
//	private final int post_counter = 0;

	private int sourceOffset = 0;

	public ValidationContext() {
		parse_errors = noParseErrors;
		errors = new HashMap<>();
		warnings = new HashMap<>();
		notes = new HashMap<>();
		errors_list = new ArrayList<>();
		warnings_list = new ArrayList<>();
		notes_list = new ArrayList<>();
	}

	public ValidationContext(int offset) {
		this();

		if (offset > 0) {
			sourceOffset = offset;
		}
	}

	public int getSourceOffset() {
		return sourceOffset;
	}

//	public String getElementLabel(int tag) {
//		switch (tag) {
//		case alphabet.ASSERTION:
//			return ASSERT_PREFIX + assert_counter++;
//		// case alphabet.INVARIANT:
//		// return INV_PREFIX + inv_counter++;
//		// case alphabet.PRE:
//		// return PRE_PREFIX + pre_counter++;
//		// case alphabet.POST:
//		// return POST_PREFIX + post_counter++;
//		// case alphabet.RELY:
//		// return RELY_PREFIX + rely_counter++;
//		// case alphabet.GUAR:
//		// return GUAR_PREFIX + guar_counter++;
//		// case alphabet.VARIANT:
//		// return VAR_PREFIX + var_counter++;
//		default:
//			return "?";
//		}
//
//	}

	public void reset() {
		parse_errors = noParseErrors;
		errors.clear();
		warnings.clear();
		notes.clear();
		errors_list.clear();
		warnings_list.clear();
		notes_list.clear();
	}

	public List<ErrInfo> getParseErrors() {
		return parse_errors;
	}

	public boolean hasErrors() {
		return !errors.isEmpty() || !parse_errors.isEmpty();
	}

	public void setParseErrors(List<ErrInfo> perrors) {
		parse_errors = new ArrayList<>();

		for (final ErrInfo err : perrors) {
			if (err.line > 0) {
				parse_errors.add(err);
			}
		}

		if (parse_errors.isEmpty() && !perrors.isEmpty()) {
			parse_errors.add(perrors.get(0));
		}
	}

	public void addError(CLElement element, String message) {
		if (element != null && message != null) {
			errors.put(element, message);
			errors_list.add(element);
		}
	}

	public void addWarning(CLElement element, String message) {
		if (element != null && message != null) {
			warnings.put(element, message);
			warnings_list.add(element);
		}
	}

	public void addNote(CLElement element, String message) {
		if (element != null && message != null) {
			notes.put(element, message);
			notes_list.add(element);
		}
	}

	public Map<CLElement, String> getErrors() {
		return errors;
	}

	public Map<CLElement, String> getWarnings() {
		return warnings;
	}

	public Map<CLElement, String> getNotes() {
		return notes;
	}

}
