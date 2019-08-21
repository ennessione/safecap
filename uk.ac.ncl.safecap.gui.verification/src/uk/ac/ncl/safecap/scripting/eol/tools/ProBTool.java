package uk.ac.ncl.safecap.scripting.eol.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.safecap.scripting.verification.IVerificationStatus;

public class ProBTool extends ReportingTool {
	private final List<String> errors;
	private static Map<String, String> explanations;

	static {
		explanations = new HashMap<>();
		explanations.put("!(n).(n : 2 .. RASPECT(l |-> r) - 1 => LINE(l)(r) |-> n - 1 : CT_SIGNAL((l |-> r) |-> n)))",
				"When a route signal shows an aspect > 1 (i.e., YY for 4-aspect signal) it is necessary that the successor route state is >= aspect - 1 ");
		explanations.put("!(n).(n : 1 .. RASPECT(l |-> r) - 1 => TA[SROUTE(r)] <: CT_CLEAR((l |-> r) |-> n)))",
				"A permissive signal may be lit only when all route ambits are clear");
		explanations.put("=> union(POINTG_REVERSE[LP - CT_NORMAL(l |-> r)]) /\\ SROUTE(r) = {})",
				"All the necessary 'normal' points are set");
		explanations.put("union(POINTG_NORMAL[LP - CT_REVERSE(l |-> r)]) /\\ SROUTE(r) = {})",
				"All the necessary 'reverse' points are set");
	}

	protected ProBTool(String tool_path, String args, String success, IVerificationStatus status) throws IOException {
		super(tool_path, args, success, status);
		errors = new ArrayList<>(20);
	}

	private enum STATE {
		NONE, PREDICATE1, PREDICATE2, TESTING
	};

	private STATE state = STATE.NONE;
	private String predicate;
	private int conditions = 0;
	private int conditions_true = 0;
	private int conditions_false = 0;

	@Override
	public void parseReport(String line) {

		switch (state) {
		case NONE:
			String p = isConditionStart(line);
			if (p != null) {
				state = STATE.PREDICATE1;
				predicate = p;
				conditions++;
			} else {
				p = isTestingStart(line);
				if (p != null) {
					state = STATE.TESTING;
					predicate = p;
					conditions++;
				}
			}
			break;
		case TESTING:
		case PREDICATE1:
			if (isTRUE(line)) {
				state = STATE.PREDICATE2;
				conditions_true++;
				statusReport();
			} else if (isFALSE(line)) {
				errors.add(predicate);
				state = STATE.PREDICATE2;
				conditions_false++;
				statusReport();
			}
			break;
		case PREDICATE2:
			if (isTRUE(line)) {
				state = STATE.NONE;
			} else if (isTRUE(line)) {
				state = STATE.NONE;
			}
			break;
		}

	}

	private void statusReport() {
		super.setStatus("Total " + conditions + " conditions; " + "true: " + conditions_true + "; false: " + conditions_false);
	}

	private boolean isTRUE(String line) {
		return line.startsWith("TRUE");
	}

	private boolean isFALSE(String line) {
		return line.startsWith("FALSE") || line.startsWith("false");
	}

	private String isTestingStart(String line) {
		if (line.startsWith("Testing: ")) {
			return line.substring(9);
		}

		return null;
	}

	private String isConditionStart(String line) {
		if (line.startsWith("==> ")) {
			return line.substring(4);
		}

		return null;
	}

	@Override
	public void finalReport() {
		log("Verification report");
		log("---");
		log("Total " + conditions + " conditions");
		log("True conditions: " + conditions_true);
		log("False conditions: " + conditions_false);

		if (conditions_false > 0) {
			log("---");
			log("Report on false conditions");
			log("---");

			int i = 1;
			for (final String e : errors) {
				log("Condition " + i++ + ":");
				log(e);
				final String exp = getExplanation(e);
				if (exp != null) {
					log("\tExplanation: " + exp);
				}
				log("");
			}
		}
	}

	private String getExplanation(String error) {
		for (final String k : explanations.keySet()) {
			if (error.endsWith(k)) {
				return explanations.get(k);
			}
		}

		return null;
	}
}
