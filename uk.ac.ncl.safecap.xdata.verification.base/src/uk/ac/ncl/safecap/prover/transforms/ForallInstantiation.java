package uk.ac.ncl.safecap.prover.transforms;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLForallExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class ForallInstantiation extends GoalTransform {
	public static String NAME = "forall elim";

	public ForallInstantiation(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final CLForallExpression forall = (CLForallExpression) goal.getFormula();

		final CLExpression body = forall.getBody().deepCopy();
		CLUtils.transformBoundtoUnbound(body);

		final TypingContext local = new TypingContext(goal.getTypingContext());
		final ProofGoal newGoal = makeGoal(goal, body);
		newGoal.setTypingContext(local);

		for (final CLParameter p : forall.getBoundParameters()) {
			final String newName = local.getFreshName(p.getName());
			final CLExpression set = CLUtils.typeToExpression(p.getTypeSafely(goal.getTypingContext()));
			final CLExpression inset = new CLBinaryExpression(alphabet.OP_IN, new CLIdentifier(newName), set);
			inset.type(local, CLTypeBool.INSTANCE);
			local.addSymbol(newName, p.getTypeSafely(goal.getTypingContext()), SYMBOL_CLASS.IDENTIFIER);
			newGoal.addHypothesis(inset, getName(), goal.getGoalContainer(), "DER" + goal.getGoal().getStage());
		}
		body.type(local, CLTypeBool.INSTANCE);
		return Collections.singletonList(newGoal);
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		return goal.getFormula().getTag() == alphabet.B_FORALL ? Boolean.TRUE : null;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
