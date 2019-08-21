package uk.ac.ncl.safecap.xdata.provers;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.prover.core.Transforms;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.core.VerificationBasePlugin;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransition;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransitionPathSource;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionContainer;

public class ConditionManagedPO implements ICondition {
	private TransitionContainer container;
	private final ITransitionPathSource element;
	private final ManagedProofObligation tpo;
	private final ProofGoal goal;
	private final SDAContext context;
	private final RootCatalog catalog;
	private CLExpression hyp;

	public ConditionManagedPO(TransitionContainer container, ITransitionPathSource element, RootCatalog catalog, ProofGoal goal) {
		this.container = container;
		this.element = element;
		tpo = goal.getGoalContainer().getManagedProofObligation();
		this.goal = goal;
		context = SDAUtils.getDataContext(catalog);
		this.catalog = catalog;
	}

	public ITransitionPathSource getElement() {
		return element;
	}

	public TransitionContainer getContainer() {
		return container;
	}

	public void setContainer(TransitionContainer container) {
		this.container = container;
	}

	@Override
	public ManagedProofObligation getManagedProofObligation() {
		return tpo;
	}

	@Override
	public CLExpression getHypothesis() {
		if (hyp == null) {
			final List<CLExpression> parts = new ArrayList<>(tpo.getHypothesis().size());
			for (final ProofHypothesis t : tpo.getHypothesis()) {
				if (!t.getHypothesis().isExclude()) {
					parts.add(t.getFormula());
				}
			}

			for (final ProofHypothesis t : goal.getHypothesis()) {
				if (!t.getHypothesis().isExclude()) {
					parts.add(t.getFormula());
				}
			}

			final CLExpression result = CLUtils.buildConjunct(parts, null);
			result.type(getTypingContext(), CLTypeBool.INSTANCE);
			hyp = result;
		}
		return hyp;
	}

	@Override
	public CLExpression getGoal() {
		goal.getFormula().type(getTypingContext());
		return goal.getFormula();
	}

	@Override
	public boolean isConjecture() {
		return false;
	}

	@Override
	public void setResult(VerificationResult result) {
		if (goal == null || goal.getGoal() == null) {
			return;
		}

		if (result != null) {
			final Transforms tr = new Transforms(goal.getGoal(), goal.getFormula(),
					result.isValid() ? result.getProver() + ": success" : result.getProver() + ": failed", goal.getGoalContainer());
			goal.setGoal(tr);
		}

		goal.setStatus(result);

		if (container != null) {
			VerificationBasePlugin.getTransitionCache().fireChange(container, goal.getGoalContainer());
		}
	}

	@Override
	public SDAContext getContext() {
		return context;
	}

	@Override
	public String getName() {
		if (element instanceof ITransition) {
			return ((ITransition) element).getName() + "/" + tpo.getName() + "/" + goal.getName();
		} else {
			return tpo.getName() + "/" + goal.getName();
		}
	}

	@Override
	public RootCatalog getRoot() {
		return catalog;
	}

	@Override
	public VerificationResult getResult() {
		return goal.getStatus();
	}

	@Override
	public TypingContext getTypingContext() {
		return goal.getTypingContext();
	}

	@Override
	public String getShortName() {
		return getName();
	}
}
