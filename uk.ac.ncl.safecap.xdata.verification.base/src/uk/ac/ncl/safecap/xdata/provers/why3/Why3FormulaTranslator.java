package uk.ac.ncl.safecap.xdata.provers.why3;

import java.util.Collection;
import java.util.HashSet;

import uk.ac.ncl.eventb.why3.filter.FilterContext;
import uk.ac.ncl.eventb.why3.filter.TreeNode;
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
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLProductType;
import uk.ac.ncl.prime.sim.lang.typing.CLSeqType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeAny;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.CLUserType;
import uk.ac.ncl.safecap.xdata.provers.INameMapper;
import uk.ac.ncl.safecap.xdata.provers.TranslationException;

public class Why3FormulaTranslator implements ICLExpressionVisitor {
	private final INameMapper nameMapper;
	private final FilterContext filterContext;
	private TreeNode<String> astStringRoot, astStringTree;

	private final Collection<CLIdentifier> identifiers;
	private final Collection<CLUserType> typeIdentifiers;

	public Why3FormulaTranslator(INameMapper nameMapper, FilterContext filterContext) {
		this.nameMapper = nameMapper;
		this.filterContext = filterContext;

		identifiers = new HashSet<>();
		typeIdentifiers = new HashSet<>();
		astStringTree = new TreeNode<>("root");
		astStringRoot = astStringTree;
	}

	public void reset() {
		astStringTree.getChildren().clear();
		astStringRoot = null;
	}

	public String getIdentifierTranslation() throws TranslationException {

		final StringBuilder aux = new StringBuilder();

		for (final CLIdentifier fi : identifiers) {
			emitIdentifier(aux, fi.getName(), fi.getType());
		}

		for (final CLUserType fi : typeIdentifiers) {
			emitIdentifier(aux, fi.getName(), fi);
		}

		return aux.toString();
	}

	private void emitIdentifier(StringBuilder aux, String fi, CLType type) throws TranslationException {
		aux.append("\tconstant ");
		aux.append(nameMapper.mapName(fi));
		aux.append(" : ");
		translate(type, aux);
		aux.append("\n");
	}

	public Collection<CLUserType> getTypeIdentifiers() throws TranslationException {
		return typeIdentifiers;
	}

	public String translate(CLExpression expression) {
		final StringBuilder sb = new StringBuilder();
		expression.visit(this, sb);
		return sb.toString();
	}

	public String translatePredicate(CLExpression expression) {

		final StringBuilder sb = new StringBuilder();
		if (expression instanceof CLFunAppExpression || expression instanceof CLIdentifier || expression instanceof CLAtomicExpression) {
			sb.append("(");
			expression.visit(this, sb);
			sb.append(" = true)");
		} else {
			expression.visit(this, sb);
		}
		return sb.toString();
	}

	private void translatePredicate(CLExpression expression, StringBuilder sb) {

		if (expression instanceof CLFunAppExpression || expression instanceof CLIdentifier || expression instanceof CLAtomicExpression) {
			sb.append("(");
			expression.visit(this, sb);
			sb.append(" = true)");
		} else {
			expression.visit(this, sb);
		}
	}

	private void translate(CLExpression expression, StringBuilder sb) {
		expression.visit(this, sb);
	}

	public String translate(CLType type) throws CLExceptionNotImplemented, TranslationException {
		final StringBuilder sb = new StringBuilder();
		translate(type, sb);
		return sb.toString();
	}

	private void translate(CLType type, StringBuilder sb) throws CLExceptionNotImplemented, TranslationException {
		if (type instanceof CLTypeAny) {
			final CLType t = ((CLTypeAny) type).getBakedType();
			if (t == null) {
				throw new CLExceptionNotImplemented("Cannot translate type type variable " + type.toString());
			} else {
				translate(t, sb);
			}
		} else if (type.isRelation()) {
			sb.append("(rel ");
			translate(type.domType(), sb);
			sb.append(" ");
			translate(type.ranType(), sb);
			sb.append(")");
		} else if (type instanceof CLPowerType) {
			final CLPowerType pt = (CLPowerType) type;
			sb.append("(set ");
			translate(pt.getBase(), sb);
			sb.append(")");
		} else if (type instanceof CLSeqType) {
			final CLSeqType pt = (CLSeqType) type;
			sb.append("(seq ");
			translate(pt.getBase(), sb);
			sb.append(")");
		} else if (type instanceof CLProductType) {
			final CLProductType pt = (CLProductType) type;
			sb.append("(");
			translate(pt.getLeft(), sb);
			sb.append(", ");
			translate(pt.getRight(), sb);
			sb.append(")");
		} else if (type == CLTypeBool.INSTANCE) {
			sb.append("bool");
		} else if (type == CLTypeInteger.INSTANCE) {
			sb.append("int");
		} else if (type instanceof CLUserType) {
			final CLUserType gtype = (CLUserType) type;
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
		} else if (element instanceof CLFunAppExpression) {
			return visitx((CLFunAppExpression) element, sb);
		} else if (element instanceof CLIntegerExpression) {
			return visitx((CLIntegerExpression) element, sb);
		}

		throw new CLExceptionNotImplemented("Cannot translate " + element.asString());
	}

	private boolean visitx(CLIntegerExpression element, StringBuilder sb) {
		translateInteger(sb, element.getValue());
		return false;
	}

	private boolean visitx(CLFunAppExpression element, StringBuilder sb) {
		enter("apply");
		sb.append("(apply ");
		translate(element.getLeft(), sb);
		sb.append(" (");
		for (int i = 0; i < element.getRight().size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			translate(element.getRight().byIndex(i), sb);
		}
		sb.append("))");
		leave();
		return false;
	}

	private boolean visitx(CLSetCompExpression element, StringBuilder sb) {
		enter("bsetc");

		// declare bound identifiers
		for (int i = 0; i < element.getBoundParameters().size(); i++) {
			final CLParameter par = element.getBoundParameters().byIndex(i);
			nameMapper.insertLocallyBoundName(par.getName());
		}

		sb.append("(");
		sb.append("bsetc");
		sb.append(" ");
		sb.append("(\\z:(");
		int i = 0;
		for (final CLParameter ii : element.getBoundParameters()) {
			if (i > 0) {
				sb.append(", ");
			}
			translate(ii.getType(), sb);
			i++;
		}
		sb.append(") .");
		sb.append("(let (");
		i = 0;
		for (final CLParameter ii : element.getBoundParameters()) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(nameMapper.insertLocallyBoundName(ii.getName()));
			i++;
		}

		sb.append(")");
		sb.append(" = ");
		sb.append("z in ");
		translate(element.getPredicate(), sb);
		sb.append(")) ");
		sb.append("(\\z:(");
		i = 0;
		for (final CLParameter ii : element.getBoundParameters()) {
			if (i > 0) {
				sb.append(", ");
			}
			translate(ii.getType(), sb);
			i++;
		}
		sb.append(") .");
		sb.append("(let (");
		i = 0;
		for (final CLParameter ii : element.getBoundParameters()) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(nameMapper.insertLocallyBoundName(ii.getName()));
			i++;
		}
		sb.append(")");
		sb.append(" = ");
		sb.append("z in ");
		translate(element.getBody(), sb);
		sb.append(")) ");
		sb.append(")"); // Closing op

		// clean-up name space
		for (i = 0; i < element.getBoundParameters().size(); i++) {
			final CLParameter par = element.getBoundParameters().byIndex(i);
			nameMapper.removeLocallyBoundName(par.getName());
		}

		leave();
		return false;
	}

	private boolean visitx(CLForallExpression forall, StringBuilder sb) throws CLExceptionNotImplemented, TranslationException {
		enter("forall");

		sb.append("(");
		sb.append("forall ");
		for (int i = 0; i < forall.getBoundParameters().size(); i++) {
			final CLParameter par = forall.getBoundParameters().byIndex(i);

			if (i > 0) {
				sb.append(", ");
			}

			sb.append(nameMapper.insertLocallyBoundName(par.getName()));
			sb.append(" : ");
			translate(par.getType(), sb);
		}

		sb.append(" . (");

		translatePredicate(forall.getBody(), sb);

		sb.append("))");

		// clean-up name space
		for (int i = 0; i < forall.getBoundParameters().size(); i++) {
			final CLParameter par = forall.getBoundParameters().byIndex(i);
			nameMapper.removeLocallyBoundName(par.getName());
		}

		leave();

		return false;
	}

	private boolean visitx(CLExistsExpression exists, StringBuilder sb) throws CLExceptionNotImplemented, TranslationException {

		sb.append("(");
		sb.append("exists ");
		for (int i = 0; i < exists.getBoundParameters().size(); i++) {
			final CLParameter par = exists.getBoundParameters().byIndex(i);

			if (i > 0) {
				sb.append(", ");
			}

			sb.append(nameMapper.insertLocallyBoundName(par.getName()));
			sb.append(" : ");
			translate(par.getType(), sb);
		}

		sb.append(". (");

		translatePredicate(exists.getBody(), sb);

		sb.append("))");

		// clean-up name space
		for (int i = 0; i < exists.getBoundParameters().size(); i++) {
			final CLParameter par = exists.getBoundParameters().byIndex(i);
			nameMapper.removeLocallyBoundName(par.getName());
		}

		return false;
	}

	private boolean visitx(CLMultiExpression element, StringBuilder sb) throws TranslationException {
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
			foldInfixPredicate(sb, "/\\", element, element.getParts().size());
			break;
		case alphabet.OP_OR:
			foldInfixPredicate(sb, "\\/", element, element.getParts().size());
			break;
		case alphabet.OP_UNION:
			foldPrefix(sb, "union", element, element.getParts().size());
			break;
		case alphabet.OP_INTER:
			foldPrefix(sb, "inter", element, element.getParts().size());
			break;
		case alphabet.OP_OVR:
			foldInfix(sb, "<+", element, element.getParts().size());
			break;
		case alphabet.OP_SETMINUS:
			foldPrefix(sb, "diff", element, element.getParts().size());
			break;
		case alphabet.SETC:
			buildSet(sb, element, 0);
			break;
		case alphabet.SEQC:
			buildSeq(sb, element, 0);
			break;
		default:
			throw new CLExceptionNotImplemented("?(Multi " + element.getTag() + ")");
		}

		return false;
	}

	private void buildSet(StringBuilder sb, CLMultiExpression element, int index) throws TranslationException {
		if (index == element.getParts().size() - 1) {
			prefixUnaryExpression(sb, "singleton", element.getParts().byIndex(index));
		} else {
			enter("add");

			sb.append("(");
			sb.append("add");
			sb.append(" ");
			translate(element.getParts().byIndex(index), sb);
			sb.append(" ");
			buildSet(sb, element, index + 1);
			sb.append(")");

			leave();
		}
	}

	private void buildSeq(StringBuilder sb, CLMultiExpression element, int index) throws TranslationException {
		if (index == element.getParts().size() - 1) {
			enter("singleton");
			sb.append("(");
			sb.append("singleton");
			sb.append(" (");
			sb.append(index);
			sb.append(", ");
			translate(element.getParts().byIndex(index), sb);
			sb.append(") ");
			buildSet(sb, element, index + 1);
			sb.append(")");
			leave();
		} else {
			enter("add");

			sb.append("(");
			sb.append("add");
			sb.append(" (");
			sb.append(index);
			sb.append(", ");
			translate(element.getParts().byIndex(index), sb);
			sb.append(") ");
			buildSet(sb, element, index + 1);
			sb.append(")");

			leave();
		}
	}

	private void foldInfix(StringBuilder sb, String operator, CLMultiExpression element, int index) {
		assert index > 1;

		enter(operator);

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

		leave();
	}

	private void foldInfixPredicate(StringBuilder sb, String operator, CLMultiExpression element, int index) {
		assert index > 1;

		enter(operator);

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

		leave();
	}

	private void foldPrefix(StringBuilder sb, String operator, CLMultiExpression element, int index) {
		assert index > 1;

		enter(operator);

		if (index == 2) {
			sb.append("(");
			sb.append(operator);
			sb.append(" ");
			translate(element.getParts().byIndex(0), sb);
			sb.append(" ");
			translate(element.getParts().byIndex(1), sb);
			sb.append(")");
		} else {
			sb.append("(");
			sb.append(operator);
			sb.append(" ");
			foldPrefix(sb, operator, element, index - 1);
			sb.append(" ");
			translate(element.getParts().byIndex(index - 1), sb);
			sb.append(")");
		}

		leave();
	}

	/*
	 * private void foldPrefixPredicate(StringBuilder sb, String operator,
	 * CLMultiExpression element, int index) { assert (index > 1);
	 * 
	 * enter(operator);
	 * 
	 * if (index == 2) { sb.append("("); sb.append(operator); sb.append(" ");
	 * translatePredicate(element.getParts().byIndex(0), sb); sb.append(" ");
	 * translatePredicate(element.getParts().byIndex(1), sb); sb.append(")"); } else
	 * { sb.append("("); sb.append(operator); sb.append(" ");
	 * foldPrefixPredicate(sb, operator, element, index - 1); sb.append(" ");
	 * translatePredicate(element.getParts().byIndex(index - 1), sb);
	 * sb.append(")"); }
	 * 
	 * leave(); }
	 */

	private void prefixBinaryExpression(StringBuilder sb, String op, CLExpression a, CLExpression b) throws TranslationException {
		enter(op);

		sb.append("(");
		sb.append(op);
		sb.append(" ");
		translate(a, sb);
		sb.append(" ");
		translate(b, sb);
		sb.append(")");

		leave();
	}

	// makes a string of the form (op a) (for expressions)
	private void prefixUnaryExpression(StringBuilder sb, String op, CLExpression a) throws TranslationException {
		enter(op);

		sb.append("(");
		sb.append(op);
		sb.append(" ");
		translate(a, sb);
		sb.append(")");

		leave();
	}

	private void prefixUnaryPredicate(StringBuilder sb, String op, CLExpression a) throws TranslationException {
		enter(op);

		sb.append("(");
		sb.append(op);
		sb.append(" ");
		translatePredicate(a, sb);
		sb.append(")");

		leave();
	}

	// makes a string of the form (a op b) (for expressions)
	private void infixBinaryExpression(StringBuilder sb, String op, CLExpression a, CLExpression b) throws TranslationException {
		enter(op);

		sb.append("(");
		translate(a, sb);
		sb.append(" ");
		sb.append(op);
		sb.append(" ");
		translate(b, sb);
		sb.append(")");

		leave();
	}

	// makes a string of the form (a op b) (for expressions)
	private void infixBinaryPredicate(StringBuilder sb, String op, CLExpression a, CLExpression b) throws TranslationException {
		enter(op);

		sb.append("(");
		translatePredicate(a, sb);
		sb.append(" ");
		sb.append(op);
		sb.append(" ");
		translatePredicate(b, sb);
		sb.append(")");

		leave();
	}

	private void prefixBinaryExpression2(StringBuilder sb, String op1, String op2, CLExpression a, CLExpression b)
			throws TranslationException {
		enter(op1);
		enter(op2);

		sb.append("(");
		sb.append(op1);
		sb.append("(");
		sb.append(op2);
		sb.append(" ");
		translate(a, sb);
		sb.append(" ");
		translate(b, sb);
		sb.append(")");
		sb.append(")");

		leave();
		leave();
	}

	private boolean visitx(CLIdentifier id, StringBuilder sb) {
		assert nameMapper.getKind(id.getName()) != null;

		switch (nameMapper.getKind(id.getName())) {
		case TYPE:
			sb.append(nameMapper.mapName(id.getName() + "_set"));
			break;
		default:
			sb.append(nameMapper.mapName(id.getName()));
			break;
		}
		return false;
	}

	private boolean visitx(CLBinaryExpression element, StringBuilder sb) {
		switch (element.getTag()) {
		case alphabet.OP_PINJ:
			infixBinaryExpression(sb, ">+>", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_REL:
			prefixBinaryExpression2(sb, "pow", "cprod", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_PFUN:
			infixBinaryExpression(sb, "+->", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_CART:
			prefixBinaryExpression(sb, "cprod", element.getLeft(), element.getRight());
			break;
		case alphabet.IMAGE:
			prefixBinaryExpression(sb, "image", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_MAP:
			infixBinaryExpression(sb, ",", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_IMPLIES:
			infixBinaryPredicate(sb, "->", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_LEQ:
			infixBinaryExpression(sb, "<=", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_LSS:
			infixBinaryExpression(sb, "<", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_GEQ:
			infixBinaryExpression(sb, ">=", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_GRT:
			infixBinaryExpression(sb, ">", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_IN:
			prefixBinaryExpression(sb, "mem", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_NOTIN:
			prefixBinaryExpression2(sb, "not", "mem", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_MOD:
			prefixBinaryExpression(sb, "mod", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_NEQ:
			if (element.getLeft().getType() == CLTypeBool.INSTANCE) {
				sb.append("(not ");
				infixBinaryPredicate(sb, "<->", element.getLeft(), element.getRight());
				sb.append(")");
			} else {
				sb.append("(not ");
				infixBinaryExpression(sb, "=", element.getLeft(), element.getRight());
				sb.append(")");
			}
			break;
		case alphabet.OP_EQL:
			if (element.getLeft().getType() == CLTypeBool.INSTANCE) {
				infixBinaryPredicate(sb, "<->", element.getLeft(), element.getRight());
			} else {
				infixBinaryExpression(sb, "=", element.getLeft(), element.getRight());
			}
			break;
		case alphabet.OP_DOMRES:
			infixBinaryExpression(sb, "<|", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_DOMSUB:
			infixBinaryExpression(sb, "<<|", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_FCOMP:
			prefixBinaryExpression(sb, "fcomp", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_RANRES:
			infixBinaryExpression(sb, "|>", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_RANSUB:
			infixBinaryExpression(sb, "|>>", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_RANGE:
			prefixBinaryExpression(sb, "mk", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_SUBSET:
			prefixBinaryExpression(sb, "subsetprop", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_SUBSETEQ:
			prefixBinaryExpression(sb, "subset", element.getLeft(), element.getRight());
			break;
		case alphabet.OP_DIRECT_PRODUCT:
			prefixBinaryExpression(sb, "dprod", element.getLeft(), element.getRight());
			break;
		}

		return false;
	}

	private boolean visitx(CLUnaryExpression element, StringBuilder sb) throws CLExceptionNotImplemented {
		switch (element.getTag()) {
		case alphabet.OP_FINITE:
			prefixUnaryExpression(sb, "finite", element.getArgument());
			break;
		case alphabet.OP_SET:
			prefixUnaryExpression(sb, "pow", element.getArgument());
			break;
		case alphabet.OP_SEQ:
			throw new CLExceptionNotImplemented("seq");
		case alphabet.OP_NOT:
			prefixUnaryPredicate(sb, "not", element.getArgument());
			return false;
		case alphabet.OP_DOM:
			prefixUnaryExpression(sb, "dom", element.getArgument());
			break;
		case alphabet.OP_RAN:
			prefixUnaryExpression(sb, "ran", element.getArgument());
			break;
		case alphabet.OP_CARD:
			prefixUnaryExpression(sb, "card", element.getArgument());
			break;
		case alphabet.OP_UNARY_MINUS:
			prefixUnaryExpression(sb, "unMinus", element.getArgument());
			break;
		case alphabet.OP_GEN_UNION:
			prefixUnaryExpression(sb, "gunion", element.getArgument());
			break;
		case alphabet.OP_GEN_INTER:
			prefixUnaryExpression(sb, "ginter", element.getArgument());
			break;
		case alphabet.OP_MIN:
			prefixUnaryExpression(sb, "min", element.getArgument());
			break;
		case alphabet.OP_MAX:
			prefixUnaryExpression(sb, "max", element.getArgument());
			break;
		case alphabet.OP_SUM:
			prefixUnaryExpression(sb, "sum", element.getArgument());
			break;
		case alphabet.OP_CONVERSE:
			prefixUnaryExpression(sb, "inverse", element.getArgument());
			return false;
		case alphabet.OP_PRJ1:
			throw new CLExceptionNotImplemented("prj1");
		case alphabet.OP_PRJ2:
			throw new CLExceptionNotImplemented("prj2");
		case alphabet.OP_SOME:
			throw new CLExceptionNotImplemented("some");
		default:
			throw new CLExceptionNotImplemented(element.toString());
		}

		return false;
	}

	private boolean visitx(CLAtomicExpression expression, StringBuilder sb) {
		switch (expression.getTag()) {
		case alphabet.OP_INT:
			printOperator(sb, "integer");
			break;
		case alphabet.OP_BOOL:
			printOperator(sb, "boolean");
			break;
		case alphabet.OP_EMPTYSEQ:
			translateEmptySeq(sb, expression);
			break;
		case alphabet.OP_EMPTYSET:
			translateEmptySet(sb, expression);
			break;
		case alphabet.OP_TRUE:
			printOperator(sb, "true");
			break;
		case alphabet.OP_FALSE:
			printOperator(sb, "false");
			break;
		default:
			assert false;
		}

		return false;
	}

	private void translateEmptySet(StringBuilder sb, CLExpression es) throws TranslationException {
		enter("empty");

		// sb.append("(");
		sb.append("empty");
		// sb.append(":");
		// sb.append("(");
		// translate(es.getType(), sb);
		// sb.append(")");
		// sb.append(")");

		leave();
	}

	private void translateEmptySeq(StringBuilder sb, CLExpression es) throws TranslationException {
		enter("empty");

		sb.append("(");
		sb.append("emptyseq");
		sb.append(":");
		sb.append("(");
		translate(es.getType(), sb);
		sb.append(")");
		sb.append(")");

		leave();
	}

	private void printOperator(StringBuilder sb, String s) {
		enter(s);
		sb.append(s.toString());
		leave();
	}

	private void translateInteger(StringBuilder sb, Integer z) {
		if (z < 0) {
			enter("unMinus");

			sb.append("(unMinus ");
			sb.append(-z);
			sb.append(")");

			leave();
		} else {
			sb.append(z.toString());
		}
	}

	private void enter(String op) {
		astStringTree = astStringTree.addChild(op);
		if (filterContext != null) {
			filterContext.enter(op);
		}
	}

	private void leave() {
		astStringTree = astStringTree.getParent();
		if (filterContext != null) {
			filterContext.leave();
			if (astStringTree != astStringRoot) {
				filterContext.widthFilter(astStringTree);
			}
		}
	}
}
