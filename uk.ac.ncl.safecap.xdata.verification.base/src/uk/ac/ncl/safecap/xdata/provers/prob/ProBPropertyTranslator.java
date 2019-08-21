package uk.ac.ncl.safecap.xdata.provers.prob;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.provers.INameMapper;
import uk.ac.ncl.safecap.xdata.provers.IPropertyTranslator;
import uk.ac.ncl.safecap.xdata.provers.SDADependencies;
import uk.ac.ncl.safecap.xdata.provers.TranslationException;
import uk.ac.ncl.safecap.xdata.verification.IdentifierDefinition;
import uk.ac.ncl.safecap.xdata.verification.IdentifierKind;
import uk.ac.ncl.safecap.xdata.verification.core.PropertyModelDependencies;
import uk.ac.ncl.safecap.xdata.verification.core.PropertyModelDependenciesBase;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.IErrorInjector;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.base.ISDADataProvider;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;

public class ProBPropertyTranslator implements IPropertyTranslator {
	private String types;
	private String constants;
	private String definitions;
	private String goal;
	private final ProBElementTranslator elementTranslator;
	private boolean asAssertion = false;
	private int index = 0;

	public ProBPropertyTranslator(ISDADataProvider dataContext, boolean obfuscate, int index, boolean asAssertion) {
		this.index = index;
		this.asAssertion = asAssertion;
		elementTranslator = new ProBElementTranslator(dataContext, obfuscate);
	}

	@Override
	public String translateProperty(ICondition condition, boolean negated) throws TranslationException {
		final ProBFormulaTranslator ft = new ProBFormulaTranslator(elementTranslator.getNameMapper());
		final SDAContext dataContext = condition.getContext();
		final SDARuntimeExecutionContext model = dataContext.getRootRuntimeContext();
		final TypingContext typingContext = condition.getTypingContext();
		IErrorInjector errorInjector = null;

		if (dataContext.getRootRuntimeContext() instanceof SDARuntimeExecutionContext) {
			final SDARuntimeExecutionContext irunmodel = dataContext.getRootRuntimeContext();
			errorInjector = irunmodel.getErrorInjector();
		}

		final CLExpression goalParsed = negated ? CLUtils.negate(condition.getGoal()) : condition.getGoal();

		final CLExpression hypParsed = condition.getHypothesis();

		final SDADependencies dataDependency = new SDADependencies(condition);
		dataDependency.analyzeExpression(goalParsed);
		if (hypParsed != null) {
			dataDependency.analyzeExpression(hypParsed);
		}

		PropertyModelDependencies modelDependency = PropertyModelDependencies.EMPTY;

		// if (!isConjecture) {
		if (hypParsed != null) {
			modelDependency = new PropertyModelDependencies(new PropertyModelDependenciesBase(condition.getRoot()), goalParsed, hypParsed);
		} else {
			modelDependency = new PropertyModelDependencies(new PropertyModelDependenciesBase(condition.getRoot()), goalParsed);
		}
		// }

		// System.out.println("Model deps: " + modeldep.toString());

		final StringBuffer buffer = new StringBuffer();

		// SETS
		int c = 0;
		for (final XEnumType t : dataDependency.getTypes()) { // dataContext.getTypeRegistry().getEnums()
			if (c > 0) {
				buffer.append(";\n");
			}

			elementTranslator.translateDeclaration(buffer, t);
			c++;
		}

		for (final IdentifierDefinition id : modelDependency.getTypes()) {

			if (id.getKind().content() == IdentifierKind.TYPE) {
				if (c > 0) {
					buffer.append(";\n");
				}
				buffer.append(elementTranslator.mapTypeId(id.getId().content()));
				c++;
			} else if (id.getKind().content() == IdentifierKind.ENUM) {
				if (c > 0) {
					buffer.append(";\n");
				}
				final CLEnumType cltype = (CLEnumType) typingContext.resolveType(id.getId().content());
				buffer.append(elementTranslator.mapTypeId(id.getId().content()));
				buffer.append(" = ");
				buffer.append("{");
				for (int i = 0; i < cltype.getMembers().length; i++) {
					if (i > 0) {
						buffer.append(", ");
					}
					buffer.append(elementTranslator.mapTypeLiteral(cltype.getMembers()[i]));
				}
				buffer.append("}");
				c++;
			}
		}

		types = buffer.toString();
		buffer.setLength(0);

		// CONCRETE_CONSTANTS
		elementTranslator.getNameMapper().setNoNewTypes(true);

		c = 0;
		for (final IXFunction f : dataDependency.getFunctions()) {
			if (f.getFunctionType() != null) {
				if (c > 0) {
					buffer.append(",\n");
				}

				buffer.append(elementTranslator.getNameMapper().mapFreeIdentifier(f.getName()));
				c++;
			}
		}

		final Map<String, CLType> newNames = new HashMap<>();

		final Set<String> allNames = new HashSet<>();
		allNames.addAll(goalParsed.getFreeIdentifiers());

		if (hypParsed != null) {
			allNames.addAll(hypParsed.getFreeIdentifiers());
		}

		for (final String id : allNames) {
			if (!elementTranslator.getNameMapper().isKnown(id) && typingContext.getSymbolClass(id) != SYMBOL_CLASS.ENUM_CONSTANT) {
				if (c > 0) {
					buffer.append(",\n");
				}
				buffer.append(elementTranslator.getNameMapper().mapFreeIdentifier(id));

				CLIdentifier resolved = goalParsed.resolveIdentifiers(id);
				if (resolved == null && hypParsed != null) {
					resolved = hypParsed.resolveIdentifiers(id);
				}

				if (resolved != null) {
					assert resolved.getType() != null;
					newNames.put(id, resolved.getType());
				}

				c++;
			}
		}

		constants = buffer.toString();
		buffer.setLength(0);

		// PROPERTIES
		c = 0;
		for (final IXFunction f : dataDependency.getFunctions()) {
			if (f.getFunctionType() != null) {
				if (c > 0) {
					buffer.append(" & \n");
				}

				elementTranslator.translate(buffer, f, errorInjector);
				c++;
			}
		}

		for (final String id : newNames.keySet()) {
			if (c > 0) {
				buffer.append(" & \n");
			}

			buffer.append(elementTranslator.getNameMapper().mapFreeIdentifier(id));

			final IdentifierDefinition iddef = modelDependency.resolveIdentifier(id);

			Object value = null;
			try {
				value = model.getValue(id);
			} catch (final CLExecutionException e) {
			}

			if (iddef == null && value == null) {
				buffer.append(" : ");
				buffer.append(ft.translate(newNames.get(id)));
			} else if (iddef == null && value != null) {

				final CLExpression valueparsed = SDAUtils.parseString(model, value.toString());
				if (valueparsed != null && valueparsed.type(model.getRootContext()) != null) {
					buffer.append(" : ");
					buffer.append(ft.translate(newNames.get(id)));
					buffer.append(" & \n");
					buffer.append(elementTranslator.getNameMapper().mapFreeIdentifier(id));
					buffer.append(" = ");
					buffer.append(ft.translate(valueparsed));
				} else {
					buffer.append(" : ");
					buffer.append(ft.translate(newNames.get(id)));
				}
			} else if (iddef.getKind().content() == IdentifierKind.MODEL) {
				buffer.append(" : ");
				buffer.append(ft.translate(iddef.getParsed().content()));
			} else if (iddef.getKind().content() == IdentifierKind.CONSTANT) {
				buffer.append(" : ");
				buffer.append(ft.translate(newNames.get(id)));
				buffer.append(" & \n");
				buffer.append(elementTranslator.getNameMapper().mapFreeIdentifier(id));
				buffer.append(" = ");
				buffer.append(ft.translate(iddef.getParsed().content()));
			} else {
				assert false;
			}
			c++;
		}

		if (hypParsed != null) {
			if (hypParsed.getTag() == alphabet.OP_AND) {
				final CLMultiExpression and_expr = (CLMultiExpression) hypParsed;
				for (final CLExpression part : and_expr.getParts()) {
					if (c > 0) {
						buffer.append(" & \n");
					}
					buffer.append(ft.translatePredicate(part));
					c++;
				}
			} else {
				if (c > 0) {
					buffer.append(" & \n");
				}
				buffer.append(ft.translatePredicate(hypParsed));
			}
		}

		definitions = buffer.toString();
		buffer.setLength(0);

		goal = ft.translatePredicate(goalParsed);
		// CLExpression goal2 = goalParsed.simplify(typingContext);
		// goal = ft.translatePredicate(goal2);

		return toString();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("MACHINE PO_" + index + "\n");

		if (types.length() > 0) {
			sb.append("SETS\n");
			sb.append(types);
		}

		if (constants.length() > 0) {
			sb.append("\nCONCRETE_CONSTANTS\n");
			sb.append(constants);
		}

		sb.append("\nPROPERTIES\n");
		if (definitions.length() > 0) {
			sb.append(definitions);
			if (!asAssertion) {
				sb.append("\n & \n");
			}
		}
		if (asAssertion) {
			sb.append("\nASSERTIONS\n");
		}
		sb.append("\n/* goal */\n");
		sb.append(goal);
		sb.append("\n");
		sb.append("END\n");
		return sb.toString();
	}

	@Override
	public INameMapper getNameMapper() {
		return elementTranslator.getNameMapper();
	}
}
