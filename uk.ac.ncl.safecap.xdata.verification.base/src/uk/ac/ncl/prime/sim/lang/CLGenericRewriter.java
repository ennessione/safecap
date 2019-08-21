package uk.ac.ncl.prime.sim.lang;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;

public abstract class CLGenericRewriter extends CLBase {

	public CLExpression rewriter(CLIdentifier original) throws CLException, CLExecutionException, InvalidSetOpException {
		return original;
	}

	public CLExpression rewriter(CLUnaryExpression original, CLExpression arg)
			throws CLException, CLExecutionException, InvalidSetOpException {
		if (arg != original.getArgument()) {
			return new CLUnaryExpression(original.getTag(), arg, original.getLocation());
		} else {
			return original;
		}
	}

	public CLExpression rewriter(CLBinaryExpression original, CLExpression left, CLExpression right)
			throws CLException, CLExecutionException, InvalidSetOpException {
		if (left != original.getLeft() || right != original.getRight()) {
			return new CLBinaryExpression(original.getTag(), left, right, original.getLocation());
		} else {
			return original;
		}
	}

	public CLExpression rewriter(CLMultiExpression original, List<CLExpression> args)
			throws CLException, CLExecutionException, InvalidSetOpException {
		if (!original.getParts().shallowEquals(args)) {
			return new CLMultiExpression(original.getTag(), args, original.getLocation());
		} else {
			return original;
		}

	}

	public CLExpression rewriter(CLFunAppExpression original, CLExpression left, CLCollection<CLExpression> args)
			throws CLException, CLExecutionException, InvalidSetOpException {
		if (original.getLeft() != left || !original.getRight().shallowEquals(args.getParts())) {
			return new CLFunAppExpression(left, args, original.getLocation());
		} else {
			return original;
		}
	}

	public CLExpression rewriter(CLSetCompExpression original, CLExpression body, CLExpression pred)
			throws CLException, CLExecutionException, InvalidSetOpException {
		if (original.getBody() != body || original.getPredicate() != pred) {
			return new CLSetCompExpression(original.getBoundParameters(), body, pred, original.getLocation());
		} else {
			return original;
		}

	}

	public CLExpression rewriter(CLQuantifiedExpression original, CLExpression body)
			throws CLException, CLExecutionException, InvalidSetOpException {
		if (body != original.getBody()) {
			switch (original.getTag()) {
			case alphabet.B_FORALL:
				return new CLForallExpression(original.getBoundParameters(), body, original.getLocation());
			case alphabet.B_EXISTS:
				return new CLExistsExpression(original.getBoundParameters(), body, original.getLocation());
			}
		}

		return original;
	}

	public final CLExpression rewrite(CLExpression element) throws CLException, CLExecutionException, InvalidSetOpException {

		if (element instanceof CLUnaryExpression) {
			final CLUnaryExpression expr = (CLUnaryExpression) element;
			final CLExpression left = rewrite(expr.getArgument());
			return rewriter(expr, left);
		} else if (element instanceof CLIdentifier) {
			final CLIdentifier expr = (CLIdentifier) element;
			return rewriter(expr);
		} else if (element instanceof CLBinaryExpression) {
			final CLBinaryExpression expr = (CLBinaryExpression) element;
			final CLExpression left = rewrite(expr.getLeft());
			final CLExpression right = rewrite(expr.getRight());
			return rewriter(expr, left, right);
		} else if (element instanceof CLMultiExpression) {
			final CLMultiExpression expr = (CLMultiExpression) element;
			final List<CLExpression> args = new ArrayList<>(expr.getParts().size());
			for (final CLExpression a : expr.getParts()) {
				final CLExpression r = rewrite(a);
				args.add(r);
			}
			return rewriter(expr, args);
		} else if (element instanceof CLFunAppExpression) {
			final CLFunAppExpression expr = (CLFunAppExpression) element;
			final CLExpression left = rewrite(expr.getLeft());
			final CLCollection<CLExpression> args = new CLCollection<>(expr.getRight().size());
			for (final CLExpression a : expr.getRight()) {
				final CLExpression r = rewrite(a);
				args.addPart(r);
			}
			return rewriter(expr, left, args);
		} else if (element instanceof CLSetCompExpression) {
			final CLSetCompExpression expr = (CLSetCompExpression) element;
			final CLExpression body = rewrite(expr.getBody());
			final CLExpression pred = rewrite(expr.getPredicate());
			return rewriter(expr, body, pred);
		} else if (element instanceof CLQuantifiedExpression) {
			final CLQuantifiedExpression expr = (CLQuantifiedExpression) element;
			final CLExpression body = rewrite(expr.getBody());
			return rewriter(expr, body);
		}
		return element;
	}

}
