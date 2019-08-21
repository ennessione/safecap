package uk.ac.ncl.prime.sim.lang.mb;

import java.util.Collection;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.CLValuationContext;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public abstract class ModelBuilderNative extends ModelBuilderBase {

	public ModelBuilderNative(TypingContext typingContext, CLExpression predicate, List<CLParameter> variables) {
		super(typingContext, predicate, variables);
	}

	public ModelBuilderNative(TypingContext typingContext, CLExpression predicate, Collection<String> variables) {
		super(typingContext, predicate, variables);
	}

	protected Object eval(int[] index) throws CLExecutionException, InvalidSetOpException {
		final SDARuntimeExecutionContext runtimeContext = new SDARuntimeExecutionContext(getTypingContext().getRuntimeExecutionContext());
		final CLValuationContext state = runtimeContext.getStateContext();
		// System.out.print("Testing with ");
		for (int i = 0; i < index.length; i++) {
			state.defineNew(getVariables().get(i).getName(), members[i][index[i]]);
			// System.out.print(getVariables().get(i).getName() + " = " +
			// members[i][index[i]]);
		}

		final Object val = getPredicate().getValueInterpreted(runtimeContext);
		// System.out.println(": " + val);
		return val;
	}
}
