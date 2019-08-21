package uk.ac.ncl.safecap.xdata.provers;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLException;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLFunAppExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLQuantifiedExpression;
import uk.ac.ncl.prime.sim.lang.CLSetCompExpression;
import uk.ac.ncl.prime.sim.lang.CLTopDownRewriter;
import uk.ac.ncl.prime.sim.lang.CLUnaryExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class ConstantFoldingTopDown extends CLTopDownRewriter {
	private final SDARuntimeExecutionContext model;

	public ConstantFoldingTopDown(SDARuntimeExecutionContext model) {
		this.model = model;
	}

	public CLExpression doFolding(CLExpression element) {
		try {
			return rewrite(element);
		} catch (final Throwable e) {
			e.printStackTrace();
			return element;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public CLExpression rewriter(CLIdentifier original) throws CLException, CLExecutionException, InvalidSetOpException {
		if (CLUtils.isConstant(original, model)) {
			final Object value = original.getValueInterpreted(model);
			if (value instanceof BSet) {
				final BSet set = (BSet) value;
				if (set.card() < 5) {
					return getValue(original);
				}
			}

		}
		return original;
	}

	@Override
	public CLExpression rewriter(CLUnaryExpression original) throws CLException, CLExecutionException, InvalidSetOpException {
		if (CLUtils.isConstant(original, model)) {
			return getValue(original);
		} else {
			return original;
		}
	}

	@Override
	public CLExpression rewriter(CLBinaryExpression original) throws CLException, CLExecutionException, InvalidSetOpException {
		if (CLUtils.isConstant(original, model) && original.getTag() != alphabet.OP_MAP) {
			return getValue(original);
		} else {
			return original;
		}
	}

	@Override
	public CLExpression rewriter(CLMultiExpression original) throws CLException, CLExecutionException, InvalidSetOpException {
		if (original.getTag() == alphabet.OP_UNION || original.getTag() == alphabet.OP_INTER || original.getTag() == alphabet.OP_SETMINUS
				|| original.getTag() == alphabet.OP_OR || original.getTag() == alphabet.OP_AND) {
			if (CLUtils.isConstant(original, model)) {
				return getValue(original);
			}
		}

		return original;
	}

	@Override
	public CLExpression rewriter(CLFunAppExpression original) throws CLException, CLExecutionException, InvalidSetOpException {
		if (CLUtils.isConstant(original, model)) {
			return getValue(original);
		} else {
			return original;
		}
	}

	@Override
	public CLExpression rewriter(CLSetCompExpression original) throws CLException, CLExecutionException, InvalidSetOpException {
		if (CLUtils.isConstant(original, model)) {
			return getValue(original);
		} else {
			return original;
		}
	}

	@Override
	public CLExpression rewriter(CLQuantifiedExpression original) throws CLException, CLExecutionException, InvalidSetOpException {
		if (CLUtils.isConstant(original, model)) {
			return getValue(original);
		} else {
			return original;
		}
	}

	private CLExpression getValue(CLExpression original) {
		try {
			final Object value = original.getValueInterpreted(model);
			if (value == null) {
				return original;
			}
			final CLExpression result = CLUtils.makeLiteral(value);
			if (result != null) {
				if (original.getType() == null) {
					return result;
				}
				result.type(model.getRootContext(), original.getType());
				return result;
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}
		return original;
	}
}
