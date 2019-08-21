package uk.ac.ncl.safecap.xdata.provers.prob;

import uk.ac.ncl.prime.sim.lang.CLAtomicExpression;
import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExceptionNotImplemented;
import uk.ac.ncl.prime.sim.lang.CLExistsExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLForallExpression;
import uk.ac.ncl.prime.sim.lang.CLFunAppExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLIntegerExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.CLSetCompExpression;
import uk.ac.ncl.prime.sim.lang.CLUnaryExpression;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.lang.bif.CLBuiltinFunction;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLEnumType;
import uk.ac.ncl.prime.sim.lang.typing.CLGivenType;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLProductType;
import uk.ac.ncl.prime.sim.lang.typing.CLSeqType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeAny;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.CLUserType;
import uk.ac.ncl.safecap.xdata.provers.INameMapper;

public class ProBFormulaTranslator implements ICLExpressionVisitor {
	private final INameMapper nameMapper;

	public ProBFormulaTranslator(INameMapper nameMapper) {
		this.nameMapper = nameMapper;
	}

	public String translatePredicate(CLExpression expression) {
		final StringBuilder sb = new StringBuilder();

		if (expression instanceof CLFunAppExpression || expression instanceof CLIdentifier || expression instanceof CLAtomicExpression) {
			sb.append("(");
			expression.visit(this, sb);
			sb.append(" = TRUE)");
		} else {
			expression.visit(this, sb);
		}
		return sb.toString();
	}

	public String translate(CLExpression expression) {
		final StringBuilder sb = new StringBuilder();
		expression.visit(this, sb);
		return sb.toString();
	}

	private void translatePredicate(CLExpression expression, StringBuilder sb) {

		if (expression instanceof CLFunAppExpression || expression instanceof CLIdentifier || expression instanceof CLAtomicExpression) {
			sb.append("(");
			expression.visit(this, sb);
			sb.append(" = TRUE)");
		} else {
			expression.visit(this, sb);
		}
	}

	private void translate(CLExpression expression, StringBuilder sb) {
		expression.visit(this, sb);
	}

	public String translate(CLType type) {
		final StringBuilder sb = new StringBuilder();
		translate(type, sb);
		return sb.toString();
	}

	private void translate(CLType type, StringBuilder sb) throws CLExceptionNotImplemented {
		if (type instanceof CLTypeAny) {
			final CLType t = ((CLTypeAny) type).getBakedType();
			if (t == null) {
				throw new CLExceptionNotImplemented("Cannot translate type type variable " + type.toString());
			} else {
				translate(t, sb);
			}
		} else if (type.isRelation()) {
			sb.append("POW(");
			translate(type.domType(), sb);
			sb.append(" * ");
			translate(type.ranType(), sb);
			sb.append(")");
		} else if (type instanceof CLPowerType) {
			final CLPowerType pt = (CLPowerType) type;
			sb.append("POW( ");
			translate(pt.getBase(), sb);
			sb.append(")");
		} else if (type instanceof CLSeqType) {
			final CLSeqType pt = (CLSeqType) type;
			sb.append("seq( ");
			translate(pt.getBase(), sb);
			sb.append(")");
		} else if (type instanceof CLProductType) {
			final CLProductType pt = (CLProductType) type;
			sb.append("(");
			translate(pt.getLeft(), sb);
			sb.append(" * ");
			translate(pt.getRight(), sb);
			sb.append(")");
		} else if (type == CLTypeBool.INSTANCE) {
			sb.append("BOOL");
		} else if (type == CLTypeInteger.INSTANCE) {
			sb.append("INT");
		} else if (type instanceof CLGivenType) {
			final CLGivenType gtype = (CLGivenType) type;
			sb.append(nameMapper.mapName(gtype.getName()));
		} else if (type instanceof CLEnumType) {
			final CLEnumType gtype = (CLEnumType) type;
			sb.append(nameMapper.mapName(gtype.getName()));
		} else {
			throw new CLExceptionNotImplemented("Cannot translate type " + type.toString());
		}
	}

	@Override
	public boolean visit(CLExpression element, Object userobject) throws CLExceptionNotImplemented {
		final StringBuilder sb = (StringBuilder) userobject;
		if (element instanceof CLAtomicExpression) {
			return visitx((CLAtomicExpression) element, sb);
		} else if (element instanceof CLUnaryExpression) {
			return visitx((CLUnaryExpression) element, sb);
		} else if (element instanceof CLBinaryExpression) {
			return visitx((CLBinaryExpression) element, sb);
		} else if (element instanceof CLMultiExpression) {
			return visitx((CLMultiExpression) element, sb);
		} else if (element instanceof CLIdentifier) {
			return visitx((CLIdentifier) element, sb);
		} else if (element instanceof CLForallExpression) {
			return visitx((CLForallExpression) element, sb);
		} else if (element instanceof CLExistsExpression) {
			return visitx((CLExistsExpression) element, sb);
		} else if (element instanceof CLSetCompExpression) {
			return visitx((CLSetCompExpression) element, sb);
		} else if (element instanceof CLBuiltinFunction) {
			return visitx((CLBuiltinFunction) element, sb);
		} else if (element instanceof CLFunAppExpression) {
			return visitx((CLFunAppExpression) element, sb);
		} else if (element instanceof CLIntegerExpression) {
			return visitx((CLIntegerExpression) element, sb);
		}

		throw new CLExceptionNotImplemented("Cannot translate " + element.asString());
	}

	private boolean visitx(CLIntegerExpression element, StringBuilder sb) {
		sb.append(element.getValue());
		return false;
	}

	private boolean visitx(CLBuiltinFunction element, StringBuilder sb) {
		final String probTranslation = element.getAttribute("prob.translation");
		if (probTranslation == null) {
			throw new CLExceptionNotImplemented("Cannot translate built-in function " + element.asString());
		}
		sb.append(probTranslation);
		sb.append("(");
		for (int i = 0; i < element.getRight().size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			translate(element.getRight().byIndex(i), sb);
		}
		sb.append(")");
		return false;
	}

	private boolean visitx(CLFunAppExpression element, StringBuilder sb) {
		sb.append("(");
		translate(element.getLeft(), sb);
		sb.append(")(");
		for (int i = 0; i < element.getRight().size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			translate(element.getRight().byIndex(i), sb);
		}
		sb.append(")");
		return false;
	}

	private boolean visitx(CLSetCompExpression element, StringBuilder sb) {
		// declare bound identifiers
		for (int i = 0; i < element.getBoundParameters().size(); i++) {
			final CLParameter par = element.getBoundParameters().byIndex(i);
			nameMapper.insertLocallyBoundName(par.getName());
		}

		sb.append("{ ");
		translate(element.getBody(), sb);
		sb.append(" | ");
		translatePredicate(element.getPredicate(), sb);
		sb.append("}");

		// clean-up name space
		for (int i = 0; i < element.getBoundParameters().size(); i++) {
			final CLParameter par = element.getBoundParameters().byIndex(i);
			nameMapper.removeLocallyBoundName(par.getName());
		}

		return false;
	}

	private boolean visitx(CLForallExpression forall, StringBuilder sb) {

		sb.append("(");
		sb.append("! ");
		for (int i = 0; i < forall.getBoundParameters().size(); i++) {
			final CLParameter par = forall.getBoundParameters().byIndex(i);

			if (i > 0) {
				sb.append(", ");
			}

			sb.append(nameMapper.insertLocallyBoundName(par.getName()));
		}

		sb.append(" . (");

		for (int i = 0; i < forall.getBoundParameters().size(); i++) {
			final CLParameter par = forall.getBoundParameters().byIndex(i);

			if (i > 0) {
				sb.append(" & ");
			}

			sb.append("(");
			sb.append(nameMapper.mapLocallyBoundName(par.getName()));
			sb.append(" : ");
			translate(par.getType(), sb);
			sb.append(")");
		}

		CLExpression forallBody = forall.getBody();

		if (forallBody.getTag() == alphabet.OP_IMPLIES) {
			final CLBinaryExpression implication = (CLBinaryExpression) forallBody;
			sb.append(" & ");
			translatePredicate(implication.getLeft(), sb);
			forallBody = implication.getRight();
		}

		sb.append(" => ");

		translatePredicate(forallBody, sb);

		sb.append("))");

		// clean-up name space
		for (int i = 0; i < forall.getBoundParameters().size(); i++) {
			final CLParameter par = forall.getBoundParameters().byIndex(i);
			nameMapper.removeLocallyBoundName(par.getName());
		}

		return false;
	}

	private boolean visitx(CLExistsExpression exists, StringBuilder sb) {

		sb.append("(");
		sb.append("# ");
		for (int i = 0; i < exists.getBoundParameters().size(); i++) {
			final CLParameter par = exists.getBoundParameters().byIndex(i);

			if (i > 0) {
				sb.append(", ");
			}

			sb.append(nameMapper.insertLocallyBoundName(par.getName()));
		}

		sb.append(".");

		translatePredicate(exists.getBody(), sb);

		sb.append(")");

		// clean-up name space
		for (int i = 0; i < exists.getBoundParameters().size(); i++) {
			final CLParameter par = exists.getBoundParameters().byIndex(i);
			nameMapper.removeLocallyBoundName(par.getName());
		}

		return false;
	}

	private boolean visitx(CLMultiExpression element, StringBuilder sb) {
		switch (element.getTag()) {
		case alphabet.OP_PLUS:
			foldInfix(sb, "+", element, element.getParts().size());
			break;
		case alphabet.OP_MINUS:
			foldInfix(sb, "-", element, element.getParts().size());
			break;
		case alphabet.OP_MUL:
			foldInfix(sb, "*", element, element.getParts().size());
			break;
		case alphabet.OP_DIV:
			foldInfix(sb, "/", element, element.getParts().size());
			break;
		case alphabet.OP_AND:
			foldInfixPredicate(sb, "&", element, element.getParts().size());
			break;
		case alphabet.OP_OR:
			foldInfixPredicate(sb, "or", element, element.getParts().size());
			break;
		case alphabet.OP_UNION:
			foldInfix(sb, "\\/", element, element.getParts().size());
			break;
		case alphabet.OP_INTER:
			foldInfix(sb, "/\\", element, element.getParts().size());
			break;
		case alphabet.OP_OVR:
			foldInfix(sb, "<+", element, element.getParts().size());
			break;
		case alphabet.OP_SETMINUS:
			foldInfix(sb, "\\", element, element.getParts().size());
			break;
		case alphabet.SETC:
			buildSet(sb, element, element.getParts().size());
			break;
		case alphabet.SEQC:
			buildSeq(sb, element, element.getParts().size());
			break;
		default:
			throw new CLExceptionNotImplemented("?(Multi " + element.getTag() + ")");
		}

		return false;
	}

	private void buildSet(StringBuilder sb, CLMultiExpression element, int index) {
		sb.append("{");
		int count = 0;
		for (final CLExpression part : element.getParts()) {
			if (count > 0) {
				sb.append(", ");
			}
			translate(part, sb);
			count++;
		}
		sb.append("}");
	}

	private void buildSeq(StringBuilder sb, CLMultiExpression element, int index) {
		sb.append("[");
		int count = 0;
		for (final CLExpression part : element.getParts()) {
			if (count > 0) {
				sb.append(", ");
			}
			translate(part, sb);
			count++;
		}
		sb.append("]");
	}

	private void foldInfix(StringBuilder sb, String operator, CLMultiExpression element, int index) {
		assert index > 1;

		if (index == 2) {
			sb.append("(");
			translate(element.getParts().byIndex(0), sb);
			sb.append(" ");
			sb.append(operator);
			sb.append(" ");
			translate(element.getParts().byIndex(1), sb);
			sb.append(")");
		} else {
			sb.append("(");
			foldInfix(sb, operator, element, index - 1);
			sb.append(" ");
			sb.append(operator);
			sb.append(" ");
			translate(element.getParts().byIndex(index - 1), sb);
			sb.append(")");
		}
	}

	private void foldInfixPredicate(StringBuilder sb, String operator, CLMultiExpression element, int index) {
		assert index > 1;

		if (index == 2) {
			sb.append("(");
			translatePredicate(element.getParts().byIndex(0), sb);
			sb.append(" ");
			sb.append(operator);
			sb.append(" ");
			translatePredicate(element.getParts().byIndex(1), sb);
			sb.append(")");
		} else {
			sb.append("(");
			foldInfix(sb, operator, element, index - 1);
			sb.append(" ");
			sb.append(operator);
			sb.append(" ");
			translatePredicate(element.getParts().byIndex(index - 1), sb);
			sb.append(")");
		}
	}

	private boolean visitx(CLIdentifier id, StringBuilder sb) {
		final String result = nameMapper.mapName(id.getName());
		assert result != null;
		sb.append(result);
		if (result == null || result.equals("null")) {
			System.out.println("fishy name: " + id);
		}
		return false;
	}

	private boolean visitx(CLBinaryExpression element, StringBuilder sb) {
		switch (element.getTag()) {
		case alphabet.OP_PINJ:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" >+> ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_REL:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" <-> ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_PFUN:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" +-> ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_CART:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" * ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.IMAGE:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append("[");
			translate(element.getRight(), sb);
			sb.append("])");
			break;
		case alphabet.OP_MAP:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" |-> ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_IMPLIES:
			sb.append("(");
			translatePredicate(element.getLeft(), sb);
			sb.append(" => ");
			translatePredicate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_LEQ:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" <= ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_LSS:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" < ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_GEQ:
			sb.append("(");
			translate(element.getRight(), sb);
			sb.append(" <= ");
			translate(element.getLeft(), sb);
			sb.append(")");
			break;
		case alphabet.OP_GRT:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" > ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_IN:
			if (!isTypigPredicate(element)) {
				sb.append("(");
				translate(element.getLeft(), sb);
				sb.append(" : ");
				translate(element.getRight(), sb);
				sb.append(")");
			} else {
				sb.append("(1 + 1 = 2)");
			}
			break;
		case alphabet.OP_NOTIN:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" /: ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_MOD:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" mod ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_NEQ:
			if (element.getLeft().getType() == CLTypeBool.INSTANCE) {
				sb.append("not(");
				translatePredicate(element.getLeft(), sb);
				sb.append(" <=> ");
				translatePredicate(element.getRight(), sb);
				sb.append(")");
			} else {
				sb.append("(");
				translate(element.getLeft(), sb);
				sb.append(" /= ");
				translate(element.getRight(), sb);
				sb.append(")");
			}
			break;
		case alphabet.OP_EQL:
			if (element.getLeft().getType() == CLTypeBool.INSTANCE) {
				sb.append("(");
				translatePredicate(element.getLeft(), sb);
				sb.append(" <=> ");
				translatePredicate(element.getRight(), sb);
				sb.append(")");
			} else {
				sb.append("(");
				translate(element.getLeft(), sb);
				sb.append(" = ");
				translate(element.getRight(), sb);
				sb.append(")");
			}
			break;
		case alphabet.OP_DOMRES:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" <| ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_DOMSUB:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" <<| ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_FCOMP:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(";");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_RANRES:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append("|>");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_RANSUB:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append("|>>");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_RANGE:
			sb.append("( ");
			translate(element.getLeft(), sb);
			sb.append(" .. ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_SUBSET:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" <<: ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_SUBSETEQ:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" <: ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		case alphabet.OP_DIRECT_PRODUCT:
			sb.append("(");
			translate(element.getLeft(), sb);
			sb.append(" >< ");
			translate(element.getRight(), sb);
			sb.append(")");
			break;
		}

		return false;
	}

	private boolean isTypigPredicate(CLBinaryExpression element) {
		if (element.getTag() == alphabet.OP_IN) {
			if (element.getLeft() instanceof CLIdentifier && element.getRight() instanceof CLIdentifier) {
				final CLIdentifier right = (CLIdentifier) element.getRight();
				if (right.getType() instanceof CLPowerType && right.getType().baseType() instanceof CLUserType) {
					final CLUserType type = (CLUserType) right.getType().baseType();
					return type.getName().equals(right.getName());
				}
			} else if (element.getRight().getTag() == alphabet.OP_INT && element.getLeft() instanceof CLIntegerExpression) {
				return true;
			} else if (element.getRight().getTag() == alphabet.OP_BOOL
					&& (element.getLeft().getTag() == alphabet.OP_TRUE || element.getLeft().getTag() == alphabet.OP_FALSE)) {
				return true;
			}
		}
		return false;
	}

	private boolean visitx(CLUnaryExpression element, StringBuilder sb) throws CLExceptionNotImplemented {
		switch (element.getTag()) {
		case alphabet.OP_FINITE:
			sb.append("FIN");
			break;
		case alphabet.OP_SET:
			sb.append("POW");
			break;
		case alphabet.OP_SEQ:
			throw new CLExceptionNotImplemented("seq");
		case alphabet.OP_NOT:
			sb.append("not");
			translatePredicate(element.getArgument(), sb);
			return false;
		case alphabet.OP_DOM:
			sb.append("dom");
			break;
		case alphabet.OP_RAN:
			sb.append("ran");
			break;
		case alphabet.OP_CARD:
			sb.append("card");
			break;
		case alphabet.OP_UNARY_MINUS:
			sb.append("-");
			break;
		case alphabet.OP_GEN_UNION:
			sb.append("union");
			break;
		case alphabet.OP_GEN_INTER:
			sb.append("inter");
			break;
		case alphabet.OP_MIN:
			sb.append("min");
			break;
		case alphabet.OP_MAX:
			sb.append("max");
			break;
		case alphabet.OP_SUM:
			sb.append("sum");
			break;
		case alphabet.OP_CONVERSE:
			sb.append("(");
			translate(element.getArgument(), sb);
			sb.append("~");
			sb.append(")");
			return false;
		case alphabet.OP_PRJ1:
			return projection(1, element, sb);
		case alphabet.OP_PRJ2:
			return projection(2, element, sb);
		case alphabet.OP_SOME:
			throw new CLExceptionNotImplemented("some");
		default:
			assert false;
		}

		sb.append("(");
		translate(element.getArgument(), sb);
		sb.append(")");
		return false;
	}

	private boolean projection(int kind, CLUnaryExpression element, StringBuilder sb) {

		sb.append("prj");
		sb.append(kind == 1 ? '1' : '2');
		sb.append("(");

		final CLType type = element.getArgument().getType();
		if (type.isRelation()) {
			translate(type.domType(), sb);
			sb.append(",");
			translate(type.ranType(), sb);
			sb.append(")[");
			translate(element.getArgument(), sb);
			sb.append("]");
		} else if (type instanceof CLProductType) {
			final CLProductType pr = (CLProductType) type;
			translate(pr.getLeft(), sb);
			sb.append(",");
			translate(pr.getRight(), sb);
			sb.append(")(");
			translate(element.getArgument(), sb);
			sb.append(")");
		} else {
			throw new CLExceptionNotImplemented("Invalid projection");
		}

		return false;
	}

	private boolean visitx(CLAtomicExpression expression, StringBuilder sb) {
		switch (expression.getTag()) {
		case alphabet.OP_INT:
			sb.append("INT");
			break;
		case alphabet.OP_BOOL:
			sb.append("BOOL");
			break;
		case alphabet.OP_EMPTYSEQ:
			sb.append("{}");
			break;
		case alphabet.OP_EMPTYSET:
			sb.append("{}");
			break;
		case alphabet.OP_TRUE:
			sb.append("TRUE");
			break;
		case alphabet.OP_FALSE:
			sb.append("FALSE");
			break;
		default:
			assert false;
		}

		return false;
	}
}
