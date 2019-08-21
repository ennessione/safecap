package uk.ac.ncl.safecap.prover.transforms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLFunAppExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public abstract class OverrideCases extends GoalTransform {

	protected OverrideCases(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	protected CLExpression makeNonePredicate(CasesDescriptor cd) {
		final List<CLExpression> result = new ArrayList<>();

		for (final CLExpression val : cd.values) {
			final CLExpression noteq = new CLBinaryExpression(alphabet.OP_NEQ, cd.variable, val);
			result.add(noteq);
		}

		if (result.size() == 1) {
			return result.get(0);
		} else {
			return CLUtils.buildConjunct(result, null);
		}
	}

	protected CasesDescriptor getCasesDescriptor(ProofGoal goal) {
		final CaseDescriptorBuilder builder = new CaseDescriptorBuilder(goal);
		goal.getFormula().visit(builder, null);
		return builder.result;
	}

	class CaseDescriptorBuilder implements ICLExpressionVisitor {
		private final ProofGoal goal;
		CasesDescriptor result = null;

		public CaseDescriptorBuilder(ProofGoal goal) {
			this.goal = goal;
		}

		@Override
		public boolean visit(CLExpression element, Object userobject) throws CLException {
			if (result == null && element.getTag() == alphabet.FAPP) {
				final CLFunAppExpression funapp = (CLFunAppExpression) element;

				// expression of the form (f <+ X)(y)
				if (funapp.getLeft().getTag() == alphabet.OP_OVR && funapp.getRight().size() == 1
						&& funapp.getRight().byIndex(0).getTag() == alphabet.ID) {
					final CLIdentifier ident = (CLIdentifier) funapp.getRight().byIndex(0);
					if (goal.getTypingContext().getType(ident.getName()) == null
							&& goal.getTypingContext().getSymbolClass(ident.getName()) != SYMBOL_CLASS.IDENTIFIER) {
						return false;
					}

					final CasesDescriptor cd = new CasesDescriptor(funapp, (CLIdentifier) funapp.getRight().byIndex(0));
					final CLMultiExpression left = (CLMultiExpression) funapp.getLeft();

					// X of the form {A -> a, B -> b, ...}
					if (left.getParts().size() == 2) {
						final CLExpression left_s = left.getParts().byIndex(1);
						if (left_s.getTag() == alphabet.SETC) {
							final CLMultiExpression left_setc = (CLMultiExpression) left_s;
							if (left_setc.getParts().size() == 0) {
								return false;
							}

							for (final CLExpression map : left_setc.getParts()) {
								if (map.getTag() == alphabet.OP_MAP) {
									final CLBinaryExpression mapb = (CLBinaryExpression) map;
									cd.addValue(mapb.getLeft());
								}
							}
							result = cd;
						}
					}

					return false;
				}
			}
			return true;
		}
	}

	class CasesDescriptor {
		CLFunAppExpression funapp;
		CLIdentifier variable;
		Set<CLExpression> values;

		CasesDescriptor(CLFunAppExpression funapp, CLIdentifier variable) {
			this.funapp = funapp;
			this.variable = variable;
			values = new HashSet<>();
		}

		public void addValue(CLExpression value) {
			values.add(value);
		}

		@Override
		public String toString() {
			return "CasesDescriptor [variable=" + variable + ", values=" + values + "]";
		}
	}

}
