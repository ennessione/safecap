package uk.ac.ncl.prime.sim.parser;

public class FormulaMarking {
	private int start;
	private int end;
	private final String message;
	private String tag;

	public FormulaMarking(int start, int end, String message) {
		this.start = start;
		this.end = end;
		this.message = message;
	}

	public FormulaMarking(int start, int end, String message, String tag) {
		this.start = start;
		this.end = end;
		this.message = message;
		this.tag = tag;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public String getMessage() {
		return message;
	}

	public String getTag() {
		return tag;
	}

	@Override
	public String toString() {
		return "(" + start + " - " + end + "): " + message;
	}

	public String toString(FormulaSource source, int offset) {
		start = start - offset;
		end = end - offset;
		if (source.getText() != null && end > start && start >= 0 && end < source.getText().length()) {
			final String t = source.getText().substring(start, end);
			return "(" + start + " - " + end + ")<" + t + ">: " + message;
		} else {
			return "(" + start + " - " + end + "): " + message;
		}
	}
}
