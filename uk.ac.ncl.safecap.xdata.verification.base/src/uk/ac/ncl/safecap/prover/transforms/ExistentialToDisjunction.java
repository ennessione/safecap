package uk.ac.ncl.safecap.prover.transforms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.prime.sim.lang.CLExistsExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class ExistentialToDisjunction extends GoalTransform {
	public static String NAME = "exists to disj";

	protected ExistentialToDisjunction(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final ExistentialToDisjunctionConfig config = (ExistentialToDisjunctionConfig) data;
		final TypingContext local = new TypingContext(goal.getTypingContext());

		final String newName = local.getFreshName(config.var.getName());
		local.addSymbol(newName, config.var.getTypeSafely(goal.getTypingContext()), SYMBOL_CLASS.IDENTIFIER);

		final List<CLExpression> cases = new ArrayList<>(config.cases.size());
		for (final CLExpression e : config.cases) {
			cases.add(limp(eql(var(newName), e), config.base));
		}

		CLExpression body = CLUtils.buildDisjunct(cases, null);

		if (newName.equals(config.var.getName())) {
			body = body.rewrite(config.var.getName(), new CLIdentifier(newName));
		}

		return Collections.singletonList(makeGoal(goal, body));
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		if (goal.getFormula().getTag() == alphabet.B_EXISTS) {
			final CLExistsExpression exists = (CLExistsExpression) goal.getFormula();

			if (exists.getBoundParameters().size() == 1) {
				final CLParameter bound = exists.getBoundParameters().byIndex(0);
				if (bound.getType() != null && exists.getBody().getTag() == alphabet.OP_AND) {
					CLMultiExpression me = (CLMultiExpression) exists.getBody();
					Map<String, CLExpression> found = null;
					CLExpression toRemove = null;
					for (final CLExpression part : me.getParts()) {
						final Map<String, CLExpression> r = part.match(in(varq(bound.getName()), tem("set", alphabet.SETC, true)),
								goal.getTypingContext());
						if (r != null) {
							if (found != null) {
								return null;
							}
							found = r;
							toRemove = part;
						}
					}

					if (found != null) {
						me = (CLMultiExpression) me.deepCopy();
						CLUtils.transformBoundtoUnbound(me);
						final List<CLExpression> list = new ArrayList<>(me.getParts().getParts());
						list.remove(toRemove);
						final CLMultiExpression setc = (CLMultiExpression) found.get("set");
						return new ExistentialToDisjunctionConfig(bound, setc.getParts().getParts(), CLUtils.buildConjunct(list, null));
					}
				}
			}
		}

		return null;
	}

	private static class ExistentialToDisjunctionConfig {
		CLParameter var;
		List<CLExpression> cases;
		CLExpression base;

		private ExistentialToDisjunctionConfig(CLParameter var, List<CLExpression> cases, CLExpression base) {
			super();
			this.var = var;
			this.cases = cases;
			this.base = base;
		}
	}

	@Override
	public String getName() {
		return NAME;
	}

}
