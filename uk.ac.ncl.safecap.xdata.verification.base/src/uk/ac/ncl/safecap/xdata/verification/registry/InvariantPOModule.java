package uk.ac.ncl.safecap.xdata.verification.registry;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.safecap.xdata.verification.core.InvariantInfo;
import uk.ac.ncl.safecap.xdata.verification.core.RuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.rules.IProverFragments;

public class InvariantPOModule extends POModule<ISymbolicTransition> {
	private final InvariantInfo invariant;
	private CLExpression invariantExpression;
	private final Set<String> relevantIdentifiers;

	public InvariantPOModule(RuntimeExecutionContext model, InvariantInfo invariant) {
		this.invariant = invariant;
		invariantExpression = invariant.invariant;
		relevantIdentifiers = new HashSet<>();
		for (final String id : invariant.invariant.getFreeIdentifiers()) {
			relevantIdentifiers.add(CLUtils.primed(id));
		}
	}

	public InvariantInfo getInvariantInfo() {
		return invariant;
	}

	@Override
	public Collection<String> getPrimed() {
		return relevantIdentifiers;
	}

	@Override
	public boolean isApplicable(ISymbolicTransition element) {
		if (element.getPreconditions().isEmpty() || element.getPostconditions().isEmpty()) {
			return false;
		}

		for (final CLExpression p : element.getPostconditions()) {
			for (final String s : p.getPrimedIdentifiers()) {
				if (relevantIdentifiers.contains(s)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public Collection<CLExpression> getHypotheses(TypingContext ctx, ISymbolicTransition element) {
		final Collection<CLExpression> hyp = super.getHypotheses(ctx, element);
		hyp.addAll(element.getPreconditions());
		hyp.add(invariant.invariant);
		hyp.addAll(element.getPostconditions());
		return hyp;
	}

	@Override
	public String getName(ISymbolicTransition element) {
		return "INV/" + invariant.name + "/" + element.getId();
	}

	@Override
	protected boolean isRelated(ISymbolicTransition element, String id) {
		return id.startsWith("INV/" + invariant.name);
	}

	@Override
	public CLExpression getGoal(TypingContext ctx, ISymbolicTransition element) {
		CLExpression post;

		if (element.getPostconditions().size() > 0) {
			post = CLUtils.buildConjunct(element.getPostconditions(), element.getPostconditions().get(0).getLocation());
		} else {
			post = element.getPostconditions().get(0);
		}

		final Set<String> primed = post.getPrimedIdentifiers();

		final Set<String> commonIdentifiers = new HashSet<>();
		for (final String s : primed) {
			if (relevantIdentifiers.contains(s)) {
				commonIdentifiers.add(CLUtils.unPrimed(s));
			}
		}

		invariantExpression = invariant.invariant.prime(ctx, commonIdentifiers);
		invariantExpression = invariantExpression.unprime(ctx, "p");
		invariantExpression.type(ctx, CLTypeBool.INSTANCE);
		invariantExpression = CLUtils.iterativeSimplify(invariantExpression, ctx);
		return invariantExpression;
	}

	@Override
	public IProverFragments getIProverFragments(ISymbolicTransition element) {
		return invariant.source;
	}
}
