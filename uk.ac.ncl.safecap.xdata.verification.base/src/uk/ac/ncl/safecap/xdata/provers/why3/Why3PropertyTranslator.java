package uk.ac.ncl.safecap.xdata.provers.why3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.eventb.why3.filter.FilterContext;
import uk.ac.ncl.eventb.why3.filter.FilteringContextProvider;
import uk.ac.ncl.eventb.why3.filter.GlobalLemmataLibrary;
import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLEnumType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.safecap.xdata.provers.ConstantFoldingTopDown;
import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.provers.INameMapper;
import uk.ac.ncl.safecap.xdata.provers.IPropertyTranslator;
import uk.ac.ncl.safecap.xdata.provers.SDADependencies;
import uk.ac.ncl.safecap.xdata.provers.TNameMapper;
import uk.ac.ncl.safecap.xdata.provers.TranslationException;
import uk.ac.ncl.safecap.xdata.verification.IdentifierDefinition;
import uk.ac.ncl.safecap.xdata.verification.IdentifierKind;
import uk.ac.ncl.safecap.xdata.verification.core.PropertyModelDependencies;
import uk.ac.ncl.safecap.xdata.verification.core.PropertyModelDependenciesBase;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.base.ISDADataProvider;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;

public class Why3PropertyTranslator implements IPropertyTranslator {
	private final Why3ElementTranslator etrans;
	private Why3FormulaTranslator ftrans;
	private FilterContext filterContext;
	private final GlobalLemmataLibrary library;
	private final TNameMapper nameMapper;
	private TheoremTranslated translated;

	public Why3PropertyTranslator(ISDADataProvider dataContext) {

		library = GlobalLemmataLibrary.getInstance();
		if (library != null) {
			filterContext = new FilterContext(library);
		} else {
			filterContext = null;
		}

		etrans = new Why3ElementTranslator(dataContext, filterContext);

		nameMapper = etrans.getNameMapper();
	}

	@Override
	public String translateProperty(ICondition condition, boolean negated) throws TranslationException {

		ftrans = new Why3FormulaTranslator(etrans.getNameMapper(), filterContext);
		final SDAContext dataContext = condition.getContext();
		final SDARuntimeExecutionContext model = dataContext.getRootRuntimeContext();
		final TypingContext typingContext = condition.getTypingContext();

		// get goal
		CLExpression goalParsed = negated ? CLUtils.negate(condition.getGoal()) : condition.getGoal();

		// do goal constant folding
		final ConstantFoldingTopDown folding = new ConstantFoldingTopDown(model);
		goalParsed = folding.doFolding(goalParsed);

		CLExpression hypParsed = condition.getHypothesis();
		if (hypParsed != null) {
			hypParsed = folding.doFolding(hypParsed);
		}

		final boolean isConjecture = condition.isConjecture();

		final SDADependencies bd = new SDADependencies(condition);
		bd.analyzeExpression(goalParsed);
		if (hypParsed != null) {
			bd.analyzeExpression(hypParsed);
		}

		PropertyModelDependencies modeldep = PropertyModelDependencies.EMPTY;

		if (!isConjecture) {
			if (hypParsed != null) {
				modeldep = new PropertyModelDependencies(new PropertyModelDependenciesBase(condition.getRoot()), goalParsed, hypParsed);
			} else {
				modeldep = new PropertyModelDependencies(new PropertyModelDependenciesBase(condition.getRoot()), goalParsed);
			}
		}

		// System.out.println("Model deps: " + modeldep.toString());

		translated = new TheoremTranslated(new FilteringContextProvider(library, filterContext));

		final StringBuffer buffer = new StringBuffer();

		// data deps
		for (final XEnumType t : bd.getTypes()) {
			etrans.translateDeclaration(translated, t);
		}

		for (final IXFunction f : bd.getFunctions()) {
			etrans.translate(translated, f);
		}

		// model type dependencies
		for (final IdentifierDefinition id : modeldep.getTypes()) {

			if (id.getKind().content() == IdentifierKind.TYPE) {
				etrans.translateCarrier(translated, id.getId().content());
			} else if (id.getKind().content() == IdentifierKind.ENUM) {
				final CLEnumType cltype = (CLEnumType) typingContext.resolveType(id.getId().content());
				etrans.translateEnum(translated, id.getId().content(), cltype.getMembers());
			}
		}

		buffer.setLength(0);

		// CONCRETE_CONSTANTS
		nameMapper.setNoNewTypes(true);

		final Map<String, CLType> newNames = new HashMap<>();

		final Set<String> allNames = new HashSet<>();
		allNames.addAll(goalParsed.getFreeIdentifiers());

		if (hypParsed != null) {
			allNames.addAll(hypParsed.getFreeIdentifiers());
		}

		for (final String id : allNames) {
			if (!etrans.getNameMapper().isKnown(id) && typingContext.getSymbolClass(id) != SYMBOL_CLASS.ENUM_CONSTANT) {

				CLIdentifier resolved = goalParsed.resolveIdentifiers(id);
				if (resolved == null && hypParsed != null) {
					resolved = hypParsed.resolveIdentifiers(id);
				}

				if (resolved != null) {
					newNames.put(id, resolved.getType());
				}
			}
		}

		for (final String id : newNames.keySet()) {

			buffer.setLength(0);
			buffer.append("constant ");
			buffer.append(etrans.getNameMapper().mapFreeIdentifier(id));

			final IdentifierDefinition iddef = modeldep.resolveIdentifier(id);

			Object value = null;
			try {
				value = model.getValue(id);
			} catch (final CLExecutionException e) {
			}

			if (iddef == null && value == null) {
				buffer.append(" : ");
				buffer.append(ftrans.translate(newNames.get(id)));
			} else if (iddef == null && value != null) {
				final CLExpression valueparsed = SDAUtils.parseString(model, value.toString());
				if (valueparsed != null && valueparsed.type(model.getRootContext()) != null) {
					buffer.append(" : ");
					buffer.append(ftrans.translate(newNames.get(id)));
					buffer.append(" = ");
					buffer.append(ftrans.translate(valueparsed));
				} else {
					buffer.append(" : ");
					buffer.append(ftrans.translate(newNames.get(id)));
				}
			} else if (iddef.getKind().content() == IdentifierKind.MODEL) {
				buffer.append(" : ");
				buffer.append(ftrans.translate(newNames.get(id)));
				final CLExpression typing_axm = new CLBinaryExpression(alphabet.OP_IN, new CLIdentifier(id), iddef.getParsed().content());
				translated.addHypothesis(ftrans.translate(typing_axm));
			} else if (iddef.getKind().content() == IdentifierKind.CONSTANT) {
				buffer.append(" : ");
				buffer.append(ftrans.translate(newNames.get(id)));
				buffer.append(" = ");
				buffer.append(ftrans.translate(iddef.getParsed().content()));
			} else {
				assert false;
			}

			translated.addDefinition(buffer.toString());
		}

		if (hypParsed != null) {
			if (hypParsed.getTag() == alphabet.OP_AND) {
				final CLMultiExpression and_expr = (CLMultiExpression) hypParsed;
				for (final CLExpression part : and_expr.getParts()) {
					buffer.setLength(0);
					buffer.append(ftrans.translatePredicate(part));
					translated.addHypothesis(buffer.toString());
				}
			} else {
				buffer.setLength(0);
				buffer.append(ftrans.translatePredicate(hypParsed));
				translated.addHypothesis(buffer.toString());
			}
		}

		translated.setGoal(ftrans.translatePredicate(goalParsed));

		return toString();
	}

	@Override
	public String toString() {
		return translated.toString();
	}

	@Override
	public INameMapper getNameMapper() {
		return etrans.getNameMapper();
	}
}
