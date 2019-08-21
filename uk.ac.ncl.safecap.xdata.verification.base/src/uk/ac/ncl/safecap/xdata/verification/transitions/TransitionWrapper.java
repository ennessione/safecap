package uk.ac.ncl.safecap.xdata.verification.transitions;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.xdata.verification.TransitionDefinition;
import uk.ac.ncl.safecap.xdata.verification.registry.ISymbolicTransition;

public class TransitionWrapper implements ISymbolicTransition {
	private final TransitionDefinition pd;

	public TransitionWrapper(TransitionDefinition pd) {
		this.pd = pd;
	}

	@Override
	public String getId() {
		return pd.getId().content();
	}

	@Override
	public List<CLExpression> getPreconditions() {
		final CLExpression xx = pd.getPre().getParsed().content();
		if (xx.getTag() == alphabet.OP_AND) {
			final CLMultiExpression mxx = (CLMultiExpression) xx;
			return mxx.getParts().getParts();
		}

		return Collections.singletonList(xx);
	}

	@Override
	public List<CLExpression> getPostconditions() {
		final CLExpression xx = pd.getPost().getParsed().content();
		if (xx.getTag() == alphabet.OP_AND) {
			final CLMultiExpression mxx = (CLMultiExpression) xx;
			return mxx.getParts().getParts();
		}

		return Collections.singletonList(xx);
	}

}
