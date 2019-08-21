package uk.ac.ncl.safecap.xdata.provers;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLException;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLFunAppExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLUnaryExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.RuntimeExecutionContext;

public class CopyOfConstantFolding extends GenericRewriter {
	private RuntimeExecutionContext model;

	public CopyOfConstantFolding(RuntimeExecutionContext model) {
		this.model = model;
	}

	public CLExpression doFolding(CLExpression element) {
		try {
			return rewrite(element);
		} catch (Throwable e) {
			e.printStackTrace();
			return element;
		}
	}

	@Override
	public CLExpression rewriter(CLUnaryExpression original, CLExpression arg) throws CLException, CLExecutionException, InvalidSetOpException {
		if (CLUtils.isConstant(arg, model))
			return getValue(original);
		else
			return super.rewriter(original, arg);
	}

	@Override
	public CLExpression rewriter(CLBinaryExpression original, CLExpression left, CLExpression right) throws CLException, CLExecutionException,
			InvalidSetOpException {
		if (CLUtils.isConstant(left, model) && CLUtils.isConstant(right, model) && original.getTag() != alphabet.OP_MAP)
			return getValue(original);
		else
			return super.rewriter(original, left, right);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public CLExpression rewriter(CLMultiExpression original, List<CLExpression> args) throws CLException, CLExecutionException, InvalidSetOpException {
		if (original.getTag() == alphabet.OP_UNION || original.getTag() == alphabet.OP_INTER) {
			BSet constVal = null;
			List<CLExpression> newArgs = new ArrayList<CLExpression>();
			for (CLExpression p : args) {
				if (CLUtils.isConstant(p, model)) {
					if (constVal == null)
						constVal = (BSet) p.getValue(model.getRootContext(), model);
					else if (original.getTag() == alphabet.OP_UNION)
						constVal = constVal._union((BSet) p.getValue(model.getRootContext(), model));
					else if (original.getTag() == alphabet.OP_INTER)
						constVal = constVal._inter((BSet) p.getValue(model.getRootContext(), model));
				} else {
					newArgs.add(p);
				}
			}

			if (constVal != null && newArgs.size() + 1 < args.size()) {
				newArgs.add(CLUtils.makeLiteral(constVal));
				return new CLMultiExpression(original.getTag(), newArgs);
			}
		}

		return super.rewriter(original, args);
	}

	@Override
	public CLExpression rewriter(CLFunAppExpression original, CLExpression left, CLCollection<CLExpression> args) throws CLException, CLExecutionException,
			InvalidSetOpException {
		if (CLUtils.isConstant(left, model)) {
			for (CLExpression e : args)
				if (!CLUtils.isConstant(e, model))
					return super.rewriter(original, left, args);

			CLExpression v = getValue(original);
			if (v != null)
				return v;
		}

		return super.rewriter(original, left, args);
	}

	private CLExpression getValue(CLExpression original) {
		try {
			Object value = original.getValue(model.getRootContext(), model);
			if (value == null)
				return null;
			CLExpression result = CLUtils.makeLiteral(value);
			if (result != null) {
				if (original.getType() == null)
					return result;
				result.type(model.getRootContext(), original.getType());
				return result;
			}
		} catch (Throwable e) {

		}

		return null;
	}

}
