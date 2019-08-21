package uk.ac.ncl.safecap.xdata.verification.registry;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLRewriteRule;
import uk.ac.ncl.prime.sim.parser.CLFormulaParser;
import uk.ac.ncl.prime.sim.parser.FormulaSource;
import uk.ac.ncl.safecap.prover.core.BuiltProveFragments;
import uk.ac.ncl.safecap.prover.transforms.UserInferenceRule;
import uk.ac.ncl.safecap.xdata.provers.VerificationTool;
import uk.ac.ncl.safecap.xdata.verification.IFormulaSource;
import uk.ac.ncl.safecap.xdata.verification.IParseable;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.rules.IProverFragments;
import uk.ac.ncl.safecap.xdata.verification.rules.InferenceRule;

public class ProveFragmentsUtils {

	public static void setRewriteRules(IProverFragments catalog, List<CLRewriteRule> rules) {
		if (rules == null) {
			if (!catalog.getBuiltProveFragments().empty()) {
				catalog.getBuiltProveFragments().content().setRewrites(null);
			}
		} else {
			if (catalog.getBuiltProveFragments().empty()) {
				catalog.setBuiltProveFragments(new BuiltProveFragments());
			}
			catalog.getBuiltProveFragments().content().setRewrites(rules);
		}
	}

	public static void setUserInferenceRule(IProverFragments catalog, List<UserInferenceRule> rules) {
		if (rules == null) {
			if (!catalog.getBuiltProveFragments().empty()) {
				catalog.getBuiltProveFragments().content().setRules(rules);
				;
			}
		} else {
			if (catalog.getBuiltProveFragments().empty()) {
				catalog.setBuiltProveFragments(new BuiltProveFragments());
			}
			catalog.getBuiltProveFragments().content().setRules(rules);
		}

	}

	public static CLRewriteRule buildRewriteRule(SDARuntimeExecutionContext model, String line) {
		if (model == null) {
			throw new IllegalArgumentException("Runtime model cannot be omitted");
		}

		if (line == null) {
			throw new IllegalArgumentException("Null string");
		}

		final int index0 = line.indexOf("::");
		if (index0 == -1) {
			throw new IllegalArgumentException("Invalid rule format in " + line);
		}
		final String name = line.substring(0, index0);
		String text = line.substring(index0 + 2);

		final int index1 = text.indexOf("-->");
		if (index1 == -1) {
			throw new IllegalArgumentException("Invalid rule format in " + line);
		}
		final int index2 = text.indexOf('#', index1);

		if (index2 > 0) {
			text = text.substring(0, index2);
		}

		final String template = text.substring(0, index1).trim();
		final String pattern = text.substring(index1 + 3).trim();

		return buildRewriteRule(model, name, template, pattern);
	}

	public static CLRewriteRule buildRewriteRule(SDARuntimeExecutionContext model, String name, String template, String pattern) {
		if (model == null) {
			throw new IllegalArgumentException("Runtime model cannot be omitted");
		}

		if (template == null) {
			throw new IllegalArgumentException("Null template string");
		}

		if (pattern == null) {
			throw new IllegalArgumentException("Null pattern string");
		}

		template = template.trim();
		pattern = pattern.trim();

		if (template.length() == 0) {
			throw new IllegalArgumentException("Template is empty");
		}

		if (pattern.length() == 0) {
			throw new IllegalArgumentException("Pattern is empty");
		}

		final CLExpression t_parsed = SDAUtils.parseString(model, template);
		if (t_parsed == null) {
			throw new IllegalArgumentException("Failed parsing " + template);
		}
		final CLExpression p_parsed = SDAUtils.parseString(model, pattern);
		if (p_parsed == null) {
			throw new IllegalArgumentException("Failed parsing " + pattern);
		}

		return new CLRewriteRule(name, t_parsed, p_parsed);
	}

	public static CLRewriteRule buildRewriteRule(SDARuntimeExecutionContext model, String name, IFormulaSource template,
			IFormulaSource pattern) {
		template.getParsed().clear();
		pattern.getParsed().clear();
		final CLExpression t_parsed = SDAUtils.getParsedNoType(model, template);
		final CLExpression p_parsed = SDAUtils.getParsedNoType(model, pattern);
		if (t_parsed == null || p_parsed == null) {
			return null;
		}

		return new CLRewriteRule(name, t_parsed, p_parsed);
	}

	public static UserInferenceRule buildUserInferenceRule(SDARuntimeExecutionContext model, String line) {
		if (model == null) {
			throw new IllegalArgumentException("Runtime model cannot be omitted");
		}

		if (line == null) {
			throw new IllegalArgumentException("Null string");
		}

		final int index0 = line.indexOf("::");
		if (index0 == -1) {
			throw new IllegalArgumentException("Invalid rule format in " + line);
		}
		final String name = line.substring(0, index0);
		String text = line.substring(index0 + 2);

		final int index1 = text.indexOf("-->");
		if (index1 == -1) {
			throw new IllegalArgumentException("Invalid rule format in " + line);
		}

		final int index2 = text.indexOf("??");

		final int index3 = text.indexOf('#', index1);

		if (index3 > 0) {
			text = text.substring(0, index2);
		}

		final String premise = text.substring(0, index1).trim();
		final String conclusion = text.substring(index1 + 3).trim();
		final String condition = index2 != -1 ? text.substring(index2 + 2).trim() : "true";

		return buildUserInferenceRule(model, name, premise, conclusion, condition);
	}

	public static UserInferenceRule buildUserInferenceRule(SDARuntimeExecutionContext model, String name, String premise, String conclusion,
			String condition) {
		if (model == null) {
			throw new IllegalArgumentException("Runtime model cannot be omitted");
		}

		if (name == null) {
			throw new IllegalArgumentException("Null name string");
		}

		if (premise == null) {
			throw new IllegalArgumentException("Null premise string");
		}

		if (conclusion == null) {
			throw new IllegalArgumentException("Null conclusion string");
		}

		if (condition == null) {
			throw new IllegalArgumentException("Null condition string");
		}

		final IRPremise pobject = buildIRPremise(model, premise);
		if (pobject == null) {
			throw new IllegalArgumentException("Failed parsing premises");
		}
		final IRConclusion cobject = buildIRConclusion(model, conclusion);
		if (cobject == null) {
			throw new IllegalArgumentException("Failed parsing conclusions");
		}
		final CLExpression cond = SDAUtils.parseString(model, condition);
		if (cond == null) {
			throw new IllegalArgumentException("Failed parsing condition");
		}

		return new UserInferenceRule(name, pobject.hypPremises, pobject.goalPremises, cond, cobject.hypConclusion, cobject.goalConclusion);
	}

	public static IRPremise buildIRPremise(SDARuntimeExecutionContext model, String text) {
		String goal = null;
		final int posG = text.indexOf("|-");
		if (posG != -1) {
			goal = text.substring(posG + 2);
			text = text.substring(0, posG);
		}
		final List<CLExpression> result = new ArrayList<>();
		final String[] parts = text.split("&&");
		for (final String p : parts) {
			final String f = p.trim();
			if (f.length() > 0) {
				final CLExpression p_parsed = SDAUtils.parseString(model, p);
				if (p_parsed == null) {
					return null;
				}
				result.add(p_parsed);
			}
		}

		if (goal != null) {
			final CLExpression g = SDAUtils.parseString(model, goal);
			if (g == null) {
				return null;
			}
			return new IRPremise(result, g);
		} else {
			return new IRPremise(result, null);
		}
	}

	public static IRConclusion buildIRConclusion(SDARuntimeExecutionContext model, String text) {
		String hyp = null;
		String goal = text;
		final int posG = text.indexOf("|-");
		if (posG != -1) {
			goal = text.substring(posG + 2);
			hyp = text.substring(0, posG);
		}

		CLExpression h = null;

		if (hyp != null) {
			h = SDAUtils.parseString(model, goal);
			if (h == null) {
				return null;
			}
		}

		if (goal != null) {
			final CLExpression g = SDAUtils.parseString(model, goal);
			if (g == null) {
				return null;
			}
			return new IRConclusion(h, g);
		}

		return null;

	}

	private static IRConclusion buildIRConclusion(SDARuntimeExecutionContext model, InferenceRule element) {
		try {
			final FormulaSource source = SDAUtils.getSource(element.getConclusion());
			source.clear();

			if (element.getConclusion().empty()) {
				markError(element.getConclusion(), "The conclusion is missing");
				return null;
			}

			final CLFormulaParser parser = VerificationTool.getParser(model);

			final String text = element.getConclusion().getFormula().content();
			String hyp = null;
			String goal = text;
			final int posG = text.indexOf("|-");
			if (posG != -1) {
				goal = text.substring(posG + 2);
				hyp = text.substring(0, posG);
			}

			CLExpression h = null;

			if (hyp != null) {
				h = parser.parseOnly(source, 0, posG, false);
				if (h == null) {
					return null;
				}
			}

			if (goal != null) {
				final CLExpression g = parser.parseOnly(source, posG == -1 ? 0 : posG + 2, text.length(), false);
				if (g == null) {
					return null;
				}
				return new IRConclusion(h, g);
			}

			return null;
		} catch (final Exception e) {
			markError(element.getConclusion(), "Parsing error: " + e.getMessage());
			return null;
		}

	}

	public static UserInferenceRule buildUserInferenceRule(SDARuntimeExecutionContext model, InferenceRule element) {
		final IRPremise premise = buildIRPremise(model, element);
		if (premise == null) {
			return null;
		}

		final IRConclusion conclusion = buildIRConclusion(model, element);
		if (conclusion == null) {
			return null;
		}

		element.getCondition().getParsed().clear();
		element.getConclusion().getParsed().clear();
		final CLExpression condition = SDAUtils.getParsedNoType(model, element.getCondition());
		if (premise.hypPremises == null) {
			markError(element.getPremise(), "The premise is not parsable");
			return null;
		}

		if (condition == null) {
			markError(element.getCondition(), "The condition is not parsable");
			return null;
		}

		return new UserInferenceRule(element.getId().content(), premise.hypPremises, premise.goalPremises, condition,
				conclusion.hypConclusion, conclusion.goalConclusion);
	}

	public static class IRConclusion {
		public CLExpression hypConclusion;
		public CLExpression goalConclusion;

		private IRConclusion(CLExpression hypConclusion, CLExpression goalConclusion) {
			super();
			this.hypConclusion = hypConclusion;
			this.goalConclusion = goalConclusion;
		}

	}

	public static class IRPremise {
		public List<CLExpression> hypPremises;
		public CLExpression goalPremises;

		public IRPremise(List<CLExpression> hypPremises, CLExpression goalPremises) {
			this.hypPremises = hypPremises;
			this.goalPremises = goalPremises;
		}
	}

	private static IRPremise buildIRPremise(SDARuntimeExecutionContext model, InferenceRule element) {
		final FormulaSource source = SDAUtils.getSource(element.getPremise());
		source.clear();

		if (element.getPremise().empty()) {
			markError(element.getPremise(), "The premise is missing");
			return null;
		}

		final CLFormulaParser parser = VerificationTool.getParser(model);
		final List<CLExpression> hypPremises = new ArrayList<>();

		String hyps = element.getPremise().getFormula().content();
		String goal = null;

		final int posG = hyps.indexOf("|-");
		if (posG != -1) {
			goal = hyps.substring(posG + 2);
			hyps = hyps.substring(0, posG);
		}

		int pos0 = 0;
		int pos1 = hyps.indexOf("&&");
		if (pos1 == -1) {
			pos1 = hyps.length();
		}

		try {
			while (pos0 >= 0 && pos1 > pos0) {
				final CLExpression r = parser.parseOnly(source, pos0, pos1, false);
				if (r == null) {
					// markError(element.getPremise(), pos0, pos1, "Parsing failed");
					return null;
				}
				hypPremises.add(r);
				element.getPremise().getFormulaSource().content().mark(pos0, pos1);
				pos0 = pos1 + 2;
				pos1 = hyps.indexOf("&&", pos1 + 2);
				if (pos1 == -1) {
					pos1 = hyps.length();
				}
			}

			if (goal != null) {
				final CLExpression g = parser.parseOnly(source, posG + 2, posG + 2 + goal.length(), false);
				if (g == null) {
					// markError(element.getPremise(), pos0, pos1, "Parsing failed");
					return null;
				}
				element.getPremise().getFormulaSource().content().mark(posG + 2, posG + 2 + goal.length());
				return new IRPremise(hypPremises, g);
			} else {
				return new IRPremise(hypPremises, null);
			}

		} catch (final Exception e) {
			markError(element.getPremise(), "Parsing error: " + e.getMessage());
			return null;
		}

	}

	static private void markError(IParseable source, String message) {
		if (!source.getFormulaSource().empty()) {
			source.getFormulaSource().content().add(0, source.getFormulaSource().content().getText().length(), message);
		}
	}

	static private void markError(IParseable source, int start, int end, String message) {
		if (!source.getFormulaSource().empty()) {
			source.getFormulaSource().content().add(start, end, message);
		}
	}
}
