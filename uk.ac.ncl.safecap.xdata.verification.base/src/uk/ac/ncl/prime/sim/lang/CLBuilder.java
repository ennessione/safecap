package uk.ac.ncl.prime.sim.lang;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ncl.prime.sim.lang.bif.BuiltinFunction;
import uk.ac.ncl.prime.sim.lang.bif.CLBuiltinFunction;
import uk.ac.ncl.prime.sim.lang.model.CLAssertStatement;
import uk.ac.ncl.prime.sim.lang.model.CLAssignmentStatement;
import uk.ac.ncl.prime.sim.lang.model.CLBlockStatement;
import uk.ac.ncl.prime.sim.lang.model.CLIfStatement;
import uk.ac.ncl.prime.sim.lang.model.CLNonDetAssignmentStatement;
import uk.ac.ncl.prime.sim.lang.model.CLParallelAssignmentStatement;
import uk.ac.ncl.prime.sim.lang.model.CLStatement;
import uk.ac.ncl.prime.sim.lang.model.CLSubstitution;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.parser.syntree;
import uk.ac.ncl.prime.sim.lang.typing.CLEnumType;
import uk.ac.ncl.prime.sim.lang.typing.CLGivenType;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLProductType;
import uk.ac.ncl.prime.sim.lang.typing.CLSeqType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeAny;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeReal;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.prime.sim.parser.CLFormulaParser;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.parser.ValidationContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.dictionary.TermDefinition;
import uk.ac.ncl.safecap.xdata.verification.dictionary.TermKind;

public class CLBuilder {
	private static final int ANY = -1;

	private final ValidationContext validation;
	private final SDARuntimeExecutionContext model;

	public CLBuilder(ValidationContext validation, SDARuntimeExecutionContext model) {
		this.validation = validation;
		this.model = model;
	}

	@SuppressWarnings("unchecked")
	public <T> T build(syntree ast, Class<T> _class) {
		if (ast == null) {
			return null;
		}

		final CLElement el = build(ast);

		if (el != null && !_class.isInstance(el)) {
			validation.addError(el, "Syntax error: expect an element of kind " + _class.getName());
			return null;
		} else {
			return (T) el;
		}
	}

	public CLType typeBuilder(syntree ast) {
		switch (ast.op()) {
		case alphabet.TYPE_ANY: {
			SourceLocation location = null;
			if (validation != null) {
				location = new SourceLocation(ast, null);
			}
			return new CLTypeAny(location);
		}
		case alphabet.TYPE_NULL:
			return null;
		case alphabet.INT:
			return CLTypeInteger.INSTANCE;
		case alphabet.TYPE_REAL:
			return CLTypeReal.INSTANCE;
		case alphabet.BOOL:
			return CLTypeBool.INSTANCE;
		case alphabet.SET: {
			require(ast, 1, ANY);
			final CLType base = typeBuilder(ast.sibling(0));
			return new CLPowerType(base);
		}
		case alphabet.SEQ: {
			require(ast, 1, ANY);
			final CLType base = typeBuilder(ast.sibling(0));
			return new CLSeqType(base);
		}
		case alphabet.CART: {
			require(ast, 2, ANY, ANY);
			final CLType l = typeBuilder(ast.sibling(0));
			final CLType r = typeBuilder(ast.sibling(1));
			return new CLProductType(l, r);
		}
		case alphabet.ID: {
			final String id = (String) ast.value().getValue();

			final CLType gt = model.getRootContext().resolveType(id);
			if (gt == null || !(gt instanceof CLGivenType || gt instanceof CLEnumType)) {
				final SourceLocation location = new SourceLocation(ast, null);
				validation.addError(new CLDummyElement(location), "Invalid type name - " + id);
				return null;
			}
			return gt;
		}
		default: {
			final SourceLocation location = new SourceLocation(ast, null);
			validation.addError(new CLDummyElement(location), "Don't know how to interpret this type syntax");
			return null;
		}
		}
	}

	@SuppressWarnings("unchecked")
	public CLElement build(syntree ast) {
		SourceLocation location = null;
		if (validation != null) {
			location = new SourceLocation(ast, null);
		}

		switch (ast.op()) {
		// notation part

		case alphabet.ASSERTION: {
			final CLExpression cond = build(ast.sibling(0), CLExpression.class);
			return new CLAssertStatement(cond, location);
		}
		case alphabet.BLOCK: {
			require(ast, 1, alphabet.STM_LIST);
			final CLCollection<CLStatement> stm_list = build(ast.sibling(0), CLCollection.class);
			return new CLBlockStatement(stm_list, location);
		}
		case alphabet.STM_LIST: {
			final CLCollection<CLStatement> stm_list = new CLCollection<>(alphabet.STM_LIST, location);
			for (final syntree p : ast.getSiblings()) {
				final Object r = build(p, Object.class);
				if (r instanceof CLStatement) {
					stm_list.addPart((CLStatement) r);
				} else if (r instanceof CLCollection) { // expand VAR_LIST into individual statements
					final CLCollection<?> col = (CLCollection<?>) r;
					for (final Object z : col) {
						if (z instanceof CLStatement) {
							stm_list.addPart((CLStatement) z);
						} else {
							validation.addError(new CLDummyElement(location), "Invalid statement syntax");
						}
					}
				} else {
					validation.addError(new CLDummyElement(location), "Invalid statement syntax");
				}
			}
			return stm_list;
		}
		case alphabet.IF: {
			require(ast, 3);
			final CLExpression cond = build(ast.sibling(0), CLExpression.class);
			final CLStatement body_if = build(ast.sibling(1), CLStatement.class);
			final CLStatement body_else = build(ast.sibling(2), CLStatement.class);
			return new CLIfStatement(cond, body_if, body_else, location);
		}
		case alphabet.BCMEQ: {
			require(ast, 4);
			final String id = (String) ast.sibling(0).value().getValue();
			final CLType type = typeBuilder(ast.sibling(1));
			final CLExpression val = build(ast.sibling(2), CLExpression.class);
			return new CLAssignmentStatement(id, type, val, SYMBOL_CLASS.IDENTIFIER, location);
		}
		case alphabet.BCMSUCH: {
			require(ast, 3);
			if (ast.sibling(0).op() == alphabet.TYPED_ID_LIST) {
				final CLCollection<CLParameter> name_list = build(ast.sibling(0), CLCollection.class);
				final CLExpression val = build(ast.sibling(1), CLExpression.class);
				return new CLNonDetAssignmentStatement(name_list, val, SYMBOL_CLASS.IDENTIFIER, location);
			} else {
				validation.addError(new CLDummyElement(location), "Invalid substitution form");
				return null;
			}
		}
		case alphabet.PAR_ASSIGN: {
			final CLCollection<CLSubstitution> assign_list = new CLCollection<>(alphabet.PAR_ASSIGN, location);
			for (final syntree p : ast.getSiblings()) {
				assign_list.addPart(build(p, CLSubstitution.class));
			}
			return new CLParallelAssignmentStatement(assign_list, location);
		}

		// expression part
		case alphabet.TYPED_ID: {
			final String id = (String) ast.value().getValue();
			final CLType type = typeBuilder(ast.sibling(0));
			return new CLParameter(id, type);
		}
		case alphabet.TYPED_ID_LIST: {
			final CLCollection<CLParameter> par_list = new CLCollection<>(alphabet.TYPED_ID_LIST, location);
			for (final syntree p : ast.getSiblings()) {
				par_list.addPart(build(p, CLParameter.class));
			}
			return par_list;
		}
		case alphabet.EXP_LIST: {
			final CLCollection<CLExpression> exp_list = new CLCollection<>(alphabet.EXP_LIST, location);
			for (final syntree p : ast.getSiblings()) {
				exp_list.addPart(build(p, CLExpression.class));
			}
			return exp_list;
		}
		case alphabet.FAPP: {
			require(ast, 2);
			final CLExpression left = build(ast.sibling(0), CLExpression.class);
			final CLCollection<CLExpression> right = build(ast.sibling(1), CLCollection.class);

			// handle built-in functions
			if (left.getTag() == alphabet.ID) {
				final CLIdentifier id = (CLIdentifier) left;
				final BuiltinFunction bif = CLBuiltinFunction.resolve(id.getName());
				if (bif != null) {
					return new CLBuiltinFunction(left, bif, right, location);
				}
			}

			return new CLFunAppExpression(left, right, location);
		}
		case alphabet.SETC:
		case alphabet.SEQC: {
			require(ast, 1);
			final CLCollection<CLExpression> exp_list = build(ast.sibling(0), CLCollection.class);
			return new CLMultiExpression(ast.op(), exp_list, location);
		}
		case alphabet.OP_UNION:
		case alphabet.OP_INTER:
		case alphabet.OP_PLUS:
		case alphabet.OP_MINUS:
		case alphabet.OP_MUL:
		case alphabet.OP_DIV:
		case alphabet.OP_AND:
		case alphabet.OP_SETMINUS:
		case alphabet.OP_OVR:
		case alphabet.OP_OR: {
			final CLCollection<CLExpression> exp = new CLCollection<>(alphabet.SKIP, location);
			for (final syntree p : ast.getSiblings()) {
				exp.addPart(build(p, CLExpression.class));
			}
			if (exp.size() < 2) {
				validation.addError(new CLDummyElement(location), "Invalid multi-expression");
			}

			return new CLMultiExpression(ast.op(), exp, location);
		}

		case alphabet.OP_PFUN:
		case alphabet.OP_PINJ:
		case alphabet.OP_REL:
		case alphabet.OP_CART:
		case alphabet.OP_IMPLIES:
		case alphabet.OP_SUBSET:
		case alphabet.OP_SUBSETEQ:
		case alphabet.OP_RANGE:
		case alphabet.IMAGE:
		case alphabet.OP_LEQ:
		case alphabet.OP_LSS:
		case alphabet.OP_GEQ:
		case alphabet.OP_GRT:
		case alphabet.OP_IN:
		case alphabet.OP_NOTIN:
		case alphabet.OP_MOD:
		case alphabet.OP_NEQ:
		case alphabet.OP_EQL:
		case alphabet.OP_DOMRES:
		case alphabet.OP_DOMSUB:
		case alphabet.OP_RANRES:
		case alphabet.OP_RANSUB:
		case alphabet.OP_FCOMP:
		case alphabet.OP_DIRECT_PRODUCT:
		case alphabet.OP_MAP: {
			require(ast, 2);
			final CLExpression left = build(ast.sibling(0), CLExpression.class);
			final CLExpression right = build(ast.sibling(1), CLExpression.class);
			return new CLBinaryExpression(ast.op(), left, right, location);
		}

		case alphabet.OP_FINITE:
		case alphabet.OP_SET:
		case alphabet.OP_SEQ:
		case alphabet.OP_SOME:
		case alphabet.OP_PRJ1:
		case alphabet.OP_PRJ2:
		case alphabet.OP_CONVERSE:
		case alphabet.OP_NOT:
		case alphabet.OP_DOM:
		case alphabet.OP_RAN:
		case alphabet.OP_CARD:
		case alphabet.OP_GEN_UNION:
		case alphabet.OP_GEN_INTER:
		case alphabet.OP_MIN:
		case alphabet.OP_MAX:
		case alphabet.OP_SUM:
		case alphabet.OP_UNARY_MINUS: {
			final CLExpression arg = build(ast.sibling(0), CLExpression.class);
			return new CLUnaryExpression(ast.op(), arg, location);
		}

		case alphabet.OP_INT:
		case alphabet.OP_BOOL:
		case alphabet.OP_TRUE:
		case alphabet.OP_FALSE:
		case alphabet.OP_EMPTYSET:
		case alphabet.OP_EMPTYSEQ:
			return new CLAtomicExpression(ast.op(), location);
		case alphabet.NUMBER: {
			final Integer val = (Integer) ast.value().getValue();
			return new CLIntegerExpression(val, location);
		}
		case alphabet.REAL: {
			final Double val = (Double) ast.value().getValue();
			return new CLRealExpression(val, location);
		}
		case alphabet.XTAG: {
			String tag = (String) ast.value().getValue();
			final CLElement element = build(ast.sibling(0));
			if (element instanceof CLIdentifier) {
				CLIdentifier ide = (CLIdentifier) element;
				tag = CLUtils.unquote(tag);
				String[] parts = tag.split(";");
				for(String p: parts) {
					String[] sparts = p.split("=");
					if (sparts.length == 2) 
						setTag(ide, sparts[0], sparts[1]);
				}
			}
			return element;
		}
		case alphabet.ID: {
			String id = (String) ast.value().getValue();
			if (id.startsWith("\"")) {
				id = id.substring(1, id.length() - 1);
			}

			// see if is a defined term
			final TermDefinition def = model.getTerm(id);
			if (def != null) {
				CLExpression result = SDAUtils.getParsedNoType(model, def);
				if (result != null && !def.getKind().empty() && def.getKind().content() == TermKind.CACHED) {
					if (model.getRootContext().getType(def.getId().content()) == null) {
						try {
							// Object value = result.getValueInterpreted(model);
							final CLType rtype = result.type(model.getRootContext());

							if (rtype != null) {
								final Object value = result.getValueCompiled(model);
								model.setValue(def.getId().content(), value);
								final CLType type = result.type(model.getRootContext());
								model.getRootContext().addSymbol(def.getId().content(), type, SYMBOL_CLASS.CONSTANT);
								return new CLIdentifier(id, location);
							} else {
								result = new CLIdentifier(id, location);
								validation.addError(result, "Invalid term \"" + id + "\": cannot infer the type");
								return result;
							}
						} catch (final Throwable e) {
							result = new CLIdentifier(id, location);
							validation.addError(result, "Invalid term \"" + id + "\":" + e.getMessage());
							return result;
						}
					} else {
						return new CLIdentifier(id, location);
					}
				} else {
					if (result == null) {
						result = new CLIdentifier(id, location);
						validation.addError(result, "Invalid term \"" + id + "\"");
					}
					return result;
				}
			} else {
				return new CLIdentifier(id, location);
			}
		}
		case alphabet.OP_PRIME: {
			final String id = (String) ast.sibling(0).value().getValue();
			return new CLIdentifier(id, null, location);
		}
		case alphabet.OP_QUAL: {
			final String id = (String) ast.sibling(0).value().getValue();
			final String qual = ast.sibling(1).value().getValue().toString();
			return new CLIdentifier(id, qual, location);
		}
		case alphabet.ID_TEMPL_TYPE: {
			final String id = (String) ast.sibling(0).value().getValue();
			final CLType tt = typeBuilder(ast.sibling(1));
			final String tag = (String) ast.sibling(2).value().getValue();
			final CLIdentifier element = new CLIdentifier(id, location);
			element.setTypeConstraint(tt);
			element.setTypeDescriptor(tag);
			return element;
		}
		case alphabet.STRING: {
			String id = (String) ast.value().getValue();
			id = id.substring(1, id.length() - 1);
			return new CLStringExpression(id, location);
		}
		case alphabet.B_SETCOMP: {
			require(ast, 3);
			final CLCollection<CLParameter> param = build(ast.sibling(0), CLCollection.class);
			final CLExpression expr = build(ast.sibling(1), CLExpression.class);
			final CLExpression pred = build(ast.sibling(2), CLExpression.class);
			return new CLSetCompExpression(param, expr, pred, location);
		}
		case alphabet.B_FORALL: {
			require(ast, 2);
			final CLCollection<CLParameter> param = build(ast.sibling(0), CLCollection.class);
			final CLExpression expr = build(ast.sibling(1), CLExpression.class);
			return new CLForallExpression(param, expr, location);
		}
		case alphabet.B_EXISTS: {
			require(ast, 2);
			final CLCollection<CLParameter> param = build(ast.sibling(0), CLCollection.class);
			final CLExpression expr = build(ast.sibling(1), CLExpression.class);
			return new CLExistsExpression(param, expr, location);
		}
		case alphabet.SKIP:
			return null;

		default:
			validation.addError(new CLDummyElement(location), "Don't know how to interpret this syntax (tag = " + ast.op() + ")");
			return null;
		}
	}

	private void setTag(CLIdentifier ide, String key, String value) {
		try {
			if ("type".equals(key)) {
				ValidationContext vc = new ValidationContext();
				CLFormulaParser parser = new CLFormulaParser(model);
				CLType type = parser.parseType(validation, value);
				if (type == null) {
					validation.addError(ide, "Failed parsing type annotation " + value);
					return;
				}
				ide.setTypeConstraint(type);
			} else if ("tag".equals(key)) {
				// TODO
			} else if ("des".equals(key)) {
				ide.setTypeDescriptor(value);
			}
		} catch (Exception e) {
			validation.addError(ide, "Failed parsing element annotation");
		}
		
	}

	public void processTerms(IProgressMonitor monitor) {
		monitor.beginTask("Build terms", model.getTerms().size());
		for (final String t : model.getTerms()) {

			try {
				final TermDefinition tdef = model.getTerm(t);
				if (model.getRootContext().getType(tdef.getId().content()) == null && !tdef.getKind().empty()) {
					final CLExpression result = SDAUtils.getParsedNoType(model, tdef);
					if (result != null) {
						final CLType type = result.type(model.getRootContext());
						if (type != null) {
							model.setTermExpression(tdef.getId().content(), result);
							if (tdef.getKind().content() == TermKind.CACHED) {
								try {
									Object value = result.getValue(model);
									if (value != null) {
										model.setValue(tdef.getId().content(), value);
										model.getRootContext().addSymbol(tdef.getId().content(), type, SYMBOL_CLASS.CONSTANT);
									}
								} catch (final Throwable e) {
									// validation.addError(result, "Invalid term \"" + t + "\":" + e.getMessage());
									System.err.println("Failed building term " + t + ": " + e.getMessage());
								}
							}
						}
					}
				}
			} catch (final Throwable e) {
				// validation.addError(CLDummyElement.INSTANCE, "Failed building term " + t + ":
				// " + e.getMessage());
				// System.err.println("Failed building term " + t + ": " + e.getMessage());
			}

			monitor.worked(1);
		}
	}

	public ValidationContext getValidation() {
		return validation;
	}

	private void require(syntree s, int len, int... tags) {
		final SourceLocation location = new SourceLocation(s, null);
		if (s.siblings() != len) {
			validation.addError(new CLDummyElement(location),
					"Syntax error in " + s.op() + ": require " + len + " parts (got " + s.siblings() + ")");
		} else {
			for (int i = 0; i < len; i++) {
				if (tags[i] != ANY && tags[i] != s.sibling(i).op()) {
					validation.addError(new CLDummyElement(location),
							"Syntax error: require part of kind " + tags[i] + " (actual " + s.sibling(i).op() + ")");
				}
			}
		}
	}

	private void require(syntree s, int len) {
		if (s.siblings() != len) {
			final SourceLocation location = new SourceLocation(s, null);
			validation.addError(new CLDummyElement(location),
					"Syntax error in " + s.op() + ": require " + len + " parts (got " + s.siblings() + ")");
		}
	}

}
