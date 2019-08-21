package uk.ac.ncl.prime.sim.lang;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;

public abstract class CLTopDownRewriter extends CLBase {

	public CLExpression rewriter(CLIdentifier original) throws CLException, CLExecutionException, InvalidSetOpException {
		return original;
	}

	public CLExpression rewriter(CLUnaryExpression original) throws CLException, CLExecutionException, InvalidSetOpException {
		return original;
	}

	public CLExpression rewriter(CLBinaryExpression original) throws CLException, CLExecutionException, InvalidSetOpException {
		return original;
	}

	public CLExpression rewriter(CLMultiExpression original) throws CLException, CLExecutionException, InvalidSetOpException {
		return original;
	}

	public CLExpression rewriter(CLFunAppExpression original) throws CLException, CLExecutionException, InvalidSetOpException {
		return original;
	}

	public CLExpression rewriter(CLSetCompExpression original) throws CLException, CLExecutionException, InvalidSetOpException {
		return original;
	}

	public CLExpression rewriter(CLQuantifiedExpression original) throws CLException, CLExecutionException, InvalidSetOpException {
		return original;
	}

	public final CLExpression rewrite(CLExpression element) throws CLException, CLExecutionException, InvalidSetOpException {

		if (element instanceof CLIdentifier) {
			final CLIdentifier expr = (CLIdentifier) element;
			final CLExpression rewritten = rewriter(expr);
			if (expr != rewritten) {
				return rewritten;
			} else {
				return element;
			}
		} else if (element instanceof CLUnaryExpression) {
			final CLUnaryExpression expr = (CLUnaryExpression) element;
			final CLExpression rewritten = rewriter(expr);
			if (expr != rewritten) {
				return rewritten;
			} else {
				final CLExpression left = rewrite(expr.getArgument());
				if (left != expr.getArgument()) {
					return new CLUnaryExpression(element.getTag(), left, expr.getLocation());
				} else {
					return element;
				}
			}
		} else if (element instanceof CLBinaryExpression) {
			final CLBinaryExpression expr = (CLBinaryExpression) element;
			final CLExpression rewritten = rewriter(expr);
			if (expr != rewritten) {
				return rewritten;
			} else {
				final CLExpression left = rewrite(expr.getLeft());
				final CLExpression right = rewrite(expr.getRight());
				if (left != expr.getLeft() || right != expr.getRight()) {
					return new CLBinaryExpression(element.getTag(), left, right, expr.getLocation());
				} else {
					return element;
				}
			}
		} else if (element instanceof CLMultiExpression) {
			final CLMultiExpression expr = (CLMultiExpression) element;
			final CLExpression rewritten = rewriter(expr);
			if (expr != rewritten) {
				return rewritten;
			} else {
				final List<CLExpression> args = new ArrayList<>(expr.getParts().size());
				boolean changed = false;
				for (final CLExpression a : expr.getParts()) {
					final CLExpression r = rewrite(a);
					args.add(r);
					changed |= r != a;
				}
				if (changed) {
					return new CLMultiExpression(expr.getTag(), args, expr.getLocation());
				} else {
					return element;
				}
			}
		} else if (element instanceof CLFunAppExpression) {
			final CLFunAppExpression expr = (CLFunAppExpression) element;
			final CLExpression rewritten = rewriter(expr);
			if (expr != rewritten) {
				return rewritten;
			} else {
				final CLExpression left = rewrite(expr.getLeft());
				boolean changed = left != expr.getLeft();
				final CLCollection<CLExpression> args = new CLCollection<>(expr.getRight().size());
				for (final CLExpression a : expr.getRight()) {
					final CLExpression r = rewrite(a);
					args.addPart(r);
					changed |= r != a;
				}
				if (changed) {
					return new CLFunAppExpression(left, args, expr.getLocation());
				} else {
					return element;
				}
			}
//		} else if (element instanceof CLSetCompExpression) {
//			CLSetCompExpression expr = (CLSetCompExpression) element;
//			CLExpression rewritten = rewriter(expr);
//			if (expr != rewritten)
//				return rewritten;
//			else {
//				CLExpression body = rewrite(expr.getBody());
//				CLExpression pred = rewrite(expr.getPredicate());
//				if (body != expr.getBody() || pred != expr.getPredicate())
//					return new CLSetCompExpression(expr.getBoundParameters(), body, pred);
//				else
//					return element;
//			}
//		} else if (element instanceof CLQuantifiedExpression) {
		} else if (element instanceof CLExistsExpression) {
			final CLQuantifiedExpression expr = (CLQuantifiedExpression) element;
			final CLExpression rewritten = rewriter(expr);
			if (expr != rewritten) {
				return rewritten;
			} else {
				final CLExpression body = rewrite(expr.getBody());
				if (body != expr.getBody()) {
					switch (expr.getTag()) {
					case alphabet.B_FORALL:
						return new CLForallExpression(expr.getBoundParameters(), body);
					case alphabet.B_EXISTS:
						return new CLExistsExpression(expr.getBoundParameters(), body);
					}
				} else {
					return element;
				}
			}
		}
		return element;
	}

}
