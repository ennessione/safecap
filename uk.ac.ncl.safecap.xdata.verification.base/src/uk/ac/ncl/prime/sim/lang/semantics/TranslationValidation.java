package uk.ac.ncl.prime.sim.lang.semantics;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.parser.CLFormulaParser;
import uk.ac.ncl.prime.sim.parser.FormulaMarking;
import uk.ac.ncl.prime.sim.parser.SourceLocation;

public class TranslationValidation {
	private final List<TranslationIssue> issues;

	public TranslationValidation() {
		issues = new ArrayList<>();
	}

	public void addIssue(CLExpression expression, String issue) {
		issues.add(new TranslationIssue(expression.getLocation(), issue));
	}

	public void addIssue(SourceLocation location, String issue) {
		issues.add(new TranslationIssue(location, issue));
	}

	public List<TranslationIssue> getIssues() {
		return issues;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final TranslationIssue issue : issues) {
			sb.append(issue.toString() + "\n");
		}
		return sb.toString();
	}

	public static class TranslationIssue {
		private final SourceLocation location;
		private final String description;

		public TranslationIssue(SourceLocation location, String description) {
			this.location = location;
			this.description = description;
		}

		public SourceLocation getLocation() {
			return location;
		}

		public String getDescription() {
			return description;
		}

		public FormulaMarking getFormulaMarking() {
			int start = 0;
			int end = 1;
			if (location != null) {
				start = location.getStart() - CLFormulaParser.MAGIC_SUB.length();
				end = location.getEnd() - CLFormulaParser.MAGIC_SUB.length();
			}
			return new FormulaMarking(start, end, description);
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			sb.append(location == null ? "?" : location.asString());
			sb.append(": ");
			sb.append(description + "\n");
			return sb.toString();
		}

	}
}
