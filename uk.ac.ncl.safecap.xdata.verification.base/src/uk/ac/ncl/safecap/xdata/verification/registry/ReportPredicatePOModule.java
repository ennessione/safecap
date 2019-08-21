package uk.ac.ncl.safecap.xdata.verification.registry;

import java.util.Collection;
import java.util.Collections;

import uk.ac.ncl.prime.sim.lang.CLAtomicExpression;
import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeAny;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.prime.sim.parser.CLFormulaParser;
import uk.ac.ncl.prime.sim.parser.FormulaSource;
import uk.ac.ncl.safecap.xdata.provers.VerificationTool;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.PropertyReport;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.rules.IProverFragments;

public class ReportPredicatePOModule extends POModule<PropertyReport> {
	public static ReportPredicatePOModule INSTANCE = new ReportPredicatePOModule();

	private ReportPredicatePOModule() {
	}

	@Override
	public boolean isApplicable(PropertyReport element) {
		final boolean test1 = element.validation().ok() && !element.getParsed().empty();
		if (test1 && element.parent().element() instanceof Conjecture) {
			final Conjecture conj = (Conjecture) element.parent().element();
			return !conj.getParsed().empty();
		}

		return false;
	}

	@Override
	public Collection<String> getPrimed() {
		return Collections.emptySet();
	}

	@Override
	public String getName(PropertyReport element) {
		return "REPORT/" + element.getId();
	}

	@Override
	protected boolean isRelated(PropertyReport element, String id) {
		return id.startsWith("REPORT/");
	}

	@Override
	public CLExpression getGoal(TypingContext ctx, PropertyReport element) {
		try {
			final Conjecture conj = (Conjecture) element.parent().element();
			final CLExpression expr = conj.getParsed().content();
			final CLExpression left = CLUtils.negate(expr);
			final CLFormulaParser parser = VerificationTool.getParser((RootCatalog) element.root());
			final FormulaSource source = element.getFormulaSource().content();
			final CLExpression right = wrapInExistential(parser, source, ctx, element.getParsed().content());
			final CLExpression result = new CLBinaryExpression(alphabet.OP_IMPLIES, left, right);
			result.type(ctx, CLTypeBool.INSTANCE);
			return result;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static CLExpression wrapInExistential(CLFormulaParser parser, FormulaSource source, TypingContext typing,
			CLExpression predicate) throws Exception {
		final CLCollection<CLParameter> boundident = new CLCollection<>();

		final CLExpression parsed = parser.parseOnly(source);
		final TypingContext extra = new TypingContext(typing);
		if (parsed.getFreeIdentifiers() != null) {
			for (final String name : parsed.getFreeIdentifiers()) {
				if (typing.getType(name) == null) {
					extra.addSymbol(name, new CLTypeAny(null), SYMBOL_CLASS.IDENTIFIER);
				}
			}
		}
		parsed.type(extra, CLTypeBool.INSTANCE);
		extra.bakeTypes();

		if (extra.getNewSymbols().isEmpty()) {
			return predicate;
		}

		CLExpression extra_conditions = null;

		for (final String id : extra.getNewSymbols()) {
			CLType type = extra.getType(id);
			if (type == null) {
				return null;
			}
			if (type instanceof CLTypeAny) {
				type = ((CLTypeAny) type).getBakedType();
			}

			if (type.isSet()) {
				final CLExpression not_empty = new CLBinaryExpression(alphabet.OP_NEQ, new CLIdentifier(id), CLAtomicExpression.EMPTYSET);
				if (extra_conditions == null) {
					extra_conditions = not_empty;
				} else {
					extra_conditions = new CLMultiExpression(alphabet.OP_AND, extra_conditions, not_empty, null);
				}
			}

			final CLParameter param = new CLParameter(id, type);
			boundident.addPart(param);
		}

		if (extra_conditions != null) {
			predicate = new CLMultiExpression(alphabet.OP_AND, predicate, extra_conditions, null);
		}

		return CLUtils._wrapInExistentialRaw(predicate, boundident);
	}

	@Override
	public IProverFragments getIProverFragments(PropertyReport element) {
		return null;
	}

}
