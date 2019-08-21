package uk.ac.ncl.safecap.xdata.verification.registry;

import java.util.ArrayList;
import java.util.Collection;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.safecap.xdata.verification.rules.IProverFragments;

public abstract class POModule<T> {

	public abstract boolean isApplicable(T element);

	public abstract String getName(T element);

	public abstract IProverFragments getIProverFragments(T element);

	public abstract Collection<String> getPrimed();

	protected boolean isRelated(T element, String id) {
		return id.equals(getName(element));
	}

	public Collection<CLExpression> getHypotheses(TypingContext ctx, T element) {
		return new ArrayList<>();
	}

	public abstract CLExpression getGoal(TypingContext ctx, T element);

}
