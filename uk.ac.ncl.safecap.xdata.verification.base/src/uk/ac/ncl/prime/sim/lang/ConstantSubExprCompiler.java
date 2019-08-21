package uk.ac.ncl.prime.sim.lang;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class ConstantSubExprCompiler extends CLTopDownRewriter {
	private final SDARuntimeExecutionContext model;

	public ConstantSubExprCompiler(SDARuntimeExecutionContext model) {
		this.model = model;
	}

	public CLExpression compileConstantSubExpressions(CLExpression element) {
		try {
			return rewrite(element);
		} catch (final Throwable e) {
			e.printStackTrace();
			return element;
		}
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
		if (CLUtils.isConstant(original, model)) {
			return getValue(original);
		} else {
			return original;
		}
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
			final Object value = original.getValue(model);
			if (value == null) {
				return original;
			}

			final CLType type = original.getType(model.getRootContext());
			if (type == null) {
				return original;
			}

			final String compiledName = model.getFastRuntimeContext().insert(type, value);
			final CLIdentifier identifier = new CLIdentifier(compiledName, null);
			identifier.setCompileVerbatim(true);
			return identifier;
		} catch (final Throwable e) {
			e.printStackTrace();
			return original;
		}
	}
}
