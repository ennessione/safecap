package uk.ac.ncl.safecap.prover.transforms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class CustomInference extends GoalTransform {
	private final UserInferenceRule rule;

	protected CustomInference(TacticPackage tacticPackage, UserInferenceRule rule) {
		super(tacticPackage);
		this.rule = rule;
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final CustomInferenceResult result = (CustomInferenceResult) data;

		ProofGoal newGoal = goal;
		if (result.goal != null) {
			newGoal = makeGoal(goal, result.goal);
		}

		if (result.hyp != null) {
			newGoal.addHypothesis(result.hyp, rule.getName(), newGoal.getGoalContainer(), "DER" + goal.getGoal().getStage());
		}

		return Collections.singletonList(newGoal);
	}

	private static class CustomInferenceResult {
		private final CLExpression goal;
		private final CLExpression hyp;

		private CustomInferenceResult(CLExpression goal, CLExpression hyp) {
			super();
			this.goal = goal;
			this.hyp = hyp;
		}

	}

	@Override
	public Object isApplicable(ProofGoal source) {
		try {
			Map<String, CLExpression> map;
			if (rule.getPremisesGoal() != null) {
				final Map<String, CLExpression> m0 = source.getFormula().match(rule.getPremisesGoal(), source.getTypingContext());
				if (m0 != null) {
					map = m0;
				} else {
					return null;
				}
			} else {
				map = new HashMap<>();
			}

			if (rule.getHypPremises().size() > 0) {
				return handleCase(source, map);
			} else {
				return source.getFormula();
			}

			/*
			 * List<ProofHypothesis> matched = new ArrayList<ProofHypothesis>(); if
			 * (!rule.getHypPremises().isEmpty()) { for (CLExpression t :
			 * rule.getHypPremises()) if (!matchHypothesis(source, t, matched, map)) return
			 * null; }
			 * 
			 * // if (map.isEmpty()) // return null;
			 * 
			 * CLExpression expr = rule.getCondition().rewrite(map);
			 * expr.type(source.getTypingContext(), CLTypeBool.INSTANCE); if (expr.getType()
			 * == null) { System.out.println("Rule " + rule.getName() +
			 * " failed on condition type checking"); return null; }
			 * 
			 * Object value = expr.getValue(getSDAContext().getRootRuntimeContext()); if
			 * (Boolean.TRUE.equals(value)) { CLExpression goal =
			 * rule.getConclusion().rewrite(map); goal.type(source.getTypingContext(),
			 * CLTypeBool.INSTANCE); source.getTypingContext().bakeTypes(); return new
			 * CustomInferenceConfig(goal, matched); }
			 * 
			 */
		} catch (final Throwable e) {
			e.printStackTrace();
		}

		return null;
	}

	private boolean matchHypothesis(ProofGoal goal, CLExpression template, List<ProofHypothesis> matched, Map<String, CLExpression> map) {
		for (final ProofHypothesis hyp : goal) {
			if (!matched.contains(hyp)) {
				final Map<String, CLExpression> m = hyp.getFormula().match(template, goal.getTypingContext());
				if (m != null) {
					if (CLExpression.match_compatible_results(map, m)) {
						map.putAll(m);
						matched.add(hyp);
						return true;
					}
				}
			}
		}

		return false;
	}

	private CustomInferenceResult handleCase(ProofGoal source, Map<String, CLExpression> map) {
		try {
			final CLExpression expr = rule.getCondition().rewrite(map);
			expr.type(source.getTypingContext(), CLTypeBool.INSTANCE);
			if (expr.getType() == null) {
				System.out.println("Rule " + rule.getName() + " failed on condition type checking");
				return null;
			}

			final Object value = expr.getValueInterpreted(getSDAContext().getRootRuntimeContext());
			if (Boolean.TRUE.equals(value)) {
				final CLExpression goal = rule.getConclusionGoal() != null ? rule.getConclusionGoal().rewrite(map) : null;
				final CLExpression hyp = rule.getConclusionHyp() != null ? rule.getConclusionHyp().rewrite(map) : null;

				if (goal == null && hyp == null) {
					return null;
				}

				if (goal != null) {
					goal.type(source.getTypingContext(), CLTypeBool.INSTANCE);
				}

				if (hyp != null) {
					hyp.type(source.getTypingContext(), CLTypeBool.INSTANCE);

					// prevent adding a copy of an existing hypothesis
					if (hasPredicateInHypotheses(source, hyp)) {
						return null;
					}
				}

				source.getTypingContext().bakeTypes();
				return new CustomInferenceResult(goal, hyp);
			}
		} catch (final Throwable e) {
			//e.printStackTrace();
		}
		return null;
	}

	private CustomInferenceResult complexMatching(ProofGoal goal, List<CLExpression> templates, Map<String, CLExpression> map) {
		for (final ProofHypothesis hyp : goal) {
			final Map<String, CLExpression> m = hyp.getFormula().match(templates.get(0), goal.getTypingContext());
			if (m != null) {
				final List<ProofHypothesis> matched = new ArrayList<>();
				if (CLExpression.match_compatible_results(map, m)) {
					m.putAll(map);
					matched.add(hyp);
					if (templates.size() > 1) {
						final CustomInferenceResult xx = complexMatching(goal, templates, 1, matched, m);
						if (xx != null) {
							return xx;
						}
					} else {
						return new CustomInferenceResult(goal.getFormula(), hyp.getFormula());
					}
				}
			}
		}
		return null;
	}

	private CustomInferenceResult complexMatching(ProofGoal goal, List<CLExpression> templates, int index, List<ProofHypothesis> matched,
			Map<String, CLExpression> map) {
		final CLExpression template = templates.get(index);
//		System.out.println("GLOBAL HYPs: " + goal.getProofObligation().getHypothesis());
//		System.out.println("LOCAL HYPs: " + goal.getHypothesis());
		for (final ProofHypothesis hyp : goal) {
//			System.out.println("HYP: " + hyp);
			if (!matched.contains(hyp)) {
				final Map<String, CLExpression> m = hyp.getFormula().match(template, goal.getTypingContext());
				if (m != null) {
					if (CLExpression.match_compatible_results(map, m)) {
						m.putAll(map);
						matched.add(hyp);
						CustomInferenceResult r;
						if (index < templates.size() - 1) {
							r = complexMatching(goal, templates, index + 1, matched, m);
						} else {
							r = handleCase(goal, m);
						}
						if (r != null) {
							return r;
						}
					}
				}
			}
		}

		return null;
	}

	@Override
	public String getName() {
		return rule.getName();
	}

}
