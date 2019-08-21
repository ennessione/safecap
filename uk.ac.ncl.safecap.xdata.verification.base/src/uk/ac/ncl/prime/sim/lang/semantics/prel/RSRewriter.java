package uk.ac.ncl.prime.sim.lang.semantics.prel;

import java.util.Map;

import uk.ac.ncl.prime.sim.lang.CLAtomicExpression;
import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLException;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLGenericRewriter;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.semantics.prel.RSTranslationContext.TranslationAssignment;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;

public class RSRewriter extends CLGenericRewriter {
	private final RSTranslationContext context;

	public RSRewriter(RSTranslationContext context) {
		this.context = context;
	}

	private boolean isConstant(CLExpression id) {
		return id.isConstant(context.getTyping());
	}

	@Override
	public CLExpression rewriter(CLIdentifier original) throws CLException, CLExecutionException, InvalidSetOpException {
		CLIdentifier id = original;
		if (context.hasStage(id.getName())) {
			id = context.current(id.getName());
		}

		final CLExpression asg = context.getAssigmentFor(id.getName());
		if (asg != null) {
			if (context.getPartialsFor(id.getName()) != null) {
				// combine assignment and partials
				final TranslationAssignment ta = context.getPartialsFor(id.getName());
				final CLExpression expr = ta.makeExtendedPredicateRHS(id.getName());
				return expr.rewrite(id.getName(), asg);
			} else {
				return asg;
			}
		} else if (context.getPartialsFor(id.getName()) != null) {
			final TranslationAssignment ta = context.getPartialsFor(id.getName());
			return ta.makeExtendedPredicateRHS(id.getName());
		} else {
			return id;
		}
	}

	@Override
	public CLExpression rewriter(CLBinaryExpression original, CLExpression left, CLExpression right)
			throws CLException, CLExecutionException, InvalidSetOpException {

		if (isConstant(left) && original.getTag() == alphabet.OP_IN) {
			CLExpression template = union(var("a"), tem("b", alphabet.SETC, true));
			Map<String, CLExpression> m = right.match(template, context.getTyping());
			if (m != null) {
				final CLExpression a = m.get("a");
				final CLMultiExpression b = (CLMultiExpression) m.get("b");
				if (b.getParts().contains(left)) {
					return CLAtomicExpression.TRUE;
				}
				return in(left, a);
			}

			template = setminus(var("a"), tem("b", alphabet.SETC, true));
			m = right.match(template, context.getTyping());
			if (m != null) {
				final CLExpression a = m.get("a");
				final CLMultiExpression b = (CLMultiExpression) m.get("b");
				if (b.getParts().contains(left)) {
					return CLAtomicExpression.FALSE;
				}
				return in(left, a);
			}

		} else if (isConstant(left) && original.getTag() == alphabet.OP_NOTIN) {
			CLExpression template = union(var("a"), tem("b", alphabet.SETC, true));
			Map<String, CLExpression> m = right.match(template, context.getTyping());
			if (m != null) {
				final CLExpression a = m.get("a");
				final CLMultiExpression b = (CLMultiExpression) m.get("b");
				if (b.getParts().contains(left)) {
					return CLAtomicExpression.FALSE;
				}
				return notin(left, a);
			}

			template = setminus(var("a"), tem("b", alphabet.SETC, true));
			m = right.match(template, context.getTyping());
			if (m != null) {
				final CLExpression a = m.get("a");
				final CLMultiExpression b = (CLMultiExpression) m.get("b");
				if (b.getParts().contains(left)) {
					return CLAtomicExpression.TRUE;
				}
				return notin(left, a);
			}
		}

		return super.rewriter(original, left, right);
	}
}
