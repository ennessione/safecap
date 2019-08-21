package uk.ac.ncl.prime.sim.lang;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.BProto;
import uk.ac.ncl.prime.sim.lang.typing.CLGivenType;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLProductType;
import uk.ac.ncl.prime.sim.lang.typing.CLSeqType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeAny;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeReal;
import uk.ac.ncl.prime.sim.lang.typing.CLUserType;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.prime.sim.parser.MultiSourceLocation;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.BMap;
import uk.ac.ncl.prime.sim.sets.BRel;
import uk.ac.ncl.prime.sim.sets.BSeq;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.common.MyReader;
import uk.ac.ncl.safecap.xdata.provers.ConstantFoldingTopDown;
import uk.ac.ncl.safecap.xdata.verification.core.RuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;

public class CLUtils {

	public static List<CLExpression> instantiate(SDARuntimeExecutionContext context, CLExpression expression, CLType type, CLExpression target) {
		if (expression instanceof CLForallExpression) {
			CLForallExpression fa = (CLForallExpression) expression;
			
			List<CLParameter> matching = new ArrayList<>();
			for(CLParameter p: fa.getBoundParameters()) 
				if (type.equals(p.getType())) 
					matching.add(p);
			
			if (matching.isEmpty())
				return Collections.singletonList(expression);
			
			List<CLExpression> instd = new ArrayList<>();
			for(CLParameter p: matching) {
				instd.add(instantiate(context, fa, p, target));
			}
			
			return instd;
		}
		
		return Collections.singletonList(expression);
	}
	
	private static CLExpression instantiate(SDARuntimeExecutionContext context, CLForallExpression fa, CLParameter p, CLExpression target) {
		CLExpression body = fa.getBody();
		int index = fa.getBoundParameters().getParts().indexOf(p);
		body = body.rewrite("" + index, target);
		List<CLParameter> npar = new ArrayList<>(fa.getBoundParameters().getParts());
		npar.remove(p);
		
		CLExpression r;
		
		if (npar.size() > 0)
			r = new CLForallExpression(new CLCollection<>(npar), body, fa.getLocation());
		else
			r = body;
			
		r.type(context.getRootContext());
		r = r.simplify(context.getRootContext());
		ConstantFoldingTopDown folder = new ConstantFoldingTopDown(context);
		r = folder.doFolding(r);
		r = r.simplify(context.getRootContext());
		return r;
	}

	public static CLExpression flattenTermDependencies(final SDARuntimeExecutionContext model, CLExpression expression) {
		return expression.rewrite(model.getBuiltTerms());
	}
	
	public static CLExpression flattenTermDependencies(final SDARuntimeExecutionContext model, CLExpression expression, String tag) {
		return expression.rewrite(model.getBuiltTerms(tag));
	}	
	
	/**
	 * Checks if the expression has free identifiers declared in a data context and carrying a given tag
	 * @return true if there is such dependency
	 */
	public static boolean hasDependencyOnTag(CLExpression expression, SDAContext context, String tag) {
		for(String fi: expression.getFreeIdentifiers()) {
			IXFunction ff = context.getFunction(fi);
			if (ff != null && tag.equals(ff.getTag()) && !ff.isDerived())
				return true;
		}
		return false;
	}	
	
	public static String compilationTypeCoercion(String part, CLType type) {
		if (type != null) {
			if (type.isSequence()) {
				return "toSeq(" + part + ")";
			} else if (type.isSet()) {
				return "toSet(" + part + ")";
			} else if (type.isInt()) {
				return "toInt(" + part + ")";
			} else if (type.isReal()) {
				return "toDbl(" + part + ")";
			}
		}

		return part;
	}

	/**
	 * Synchronised LRU cache
	 * 
	 * @param maxSize cache size (old values overwritten)
	 * @return new LRU cache object
	 */
	public static <K, V> Map<K, V> lruCache(final int maxSize) {
		return Collections.synchronizedMap(new LinkedHashMap<K, V>(maxSize * 4 / 3, 0.75f, true) {
			@Override
			protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
				return size() > maxSize;
			}
		});
	}

	public static boolean needsQuotes(String rm) {
		if (rm == null || rm.length() == 0 || rm.charAt(0) == '"') {
			return false;
		}

		char c = rm.charAt(0);
		if (!Character.isLetter(c) && c != '$') {
			return true;
		}

		for (int i = 1; i < rm.length(); i++) {
			c = rm.charAt(i);
			if (!Character.isLetterOrDigit(c) && c != '_' && c != '\'' && c != '$') {
				return true;
			}
		}
		return false;
	}

	/**
	 * Quotes a formula string if needsQuotes(..) says so
	 * 
	 * @param formula string
	 * @return string, possibly quoted
	 */
	public static String quote(String string) {
		if (needsQuotes(string)) {
			return '"' + string + '"';
		} else {
			return string;
		}
	}

	public static String unquote(String string) {
		if (string.charAt(0) == '"' && string.charAt(string.length() - 1) == '"') {
			return string.substring(1, string.length() - 1);
		} else {
			return string;
		}
	}

	/**
	 * Takes a formula and parses it naively with a tokenizer quoting all the word
	 * that do not appear to be valid identifiers/literals
	 * 
	 * @param string
	 * @return a string with quoted invalid identifiers
	 */
	public static String quoteString(String string) {
		final MyReader reader = new MyReader(string);
		final StreamTokenizer tokenizer = new StreamTokenizer(reader);
		tokenizer.eolIsSignificant(false);
		tokenizer.slashStarComments(false);
		tokenizer.ordinaryChars('/', '/');
		tokenizer.ordinaryChars('\\', '\\');
		tokenizer.ordinaryChars(' ', ' ');
		tokenizer.ordinaryChars('\r', '\r');
		tokenizer.ordinaryChars('\n', '\n');
		tokenizer.ordinaryChars('\t', '\t');
		tokenizer.wordChars('_', '_');
		tokenizer.wordChars('$', '$');
		tokenizer.parseNumbers();

		try {
			final StringBuffer sb = new StringBuffer();
			while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
				switch (tokenizer.ttype) {
				case StreamTokenizer.TT_WORD:
					final String rm = tokenizer.sval;
					if (rm != null) {
						if (CLUtils.needsQuotes(rm)) {
							sb.append('"');
							sb.append(rm);
							sb.append('"');
						} else {
							sb.append(rm);
						}
					} else {
						sb.append(tokenizer.sval);
					}
					break;
				case ' ':
				case '\r':
				case '\n':
				case '\t':
					sb.append(' ');
					break;
				default:
					sb.append((char) tokenizer.ttype);
					break;
				}
			}

			return sb.toString();
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Horrible dirty hack: breaks immutable expression interface!
	 * 
	 * @param expression
	 */
	@Deprecated
	public static void transformBoundtoUnbound(CLExpression expression) {
		expression.visit(Visitor.INSTANCE, expression.getBoundIdentifiers());
	}

	private static class Visitor implements ICLExpressionVisitor {
		static final Visitor INSTANCE = new Visitor();

		@SuppressWarnings("unchecked")
		@Override
		public boolean visit(CLExpression element, Object userobject) throws CLException {
			final Set<Integer> names = (Set<Integer>) userobject;
			if (element instanceof CLIdentifier) {
				final CLIdentifier identifier = (CLIdentifier) element;
				if (identifier.isBound() && names.contains(identifier.getIndex())) {
					identifier.removeBound();
				}
			}

			element.clearIdentifierCaches();

			return true;
		}

	}

	public static void checkNotPolymorphic(CLElement element, String identifier, TypingContext context) {
		if (context.getType(identifier).isPolymorphic()) {
			context.getValidation().addError(element,
					"Identifier " + identifier + " has open polymorphic type " + context.getType(identifier).toString());
			return;
		}
	}

	public static void checkNoAuxIdentifiers(CLElement element, CLExpression expression, TypingContext context) {
		for (final String s : expression.getFreeIdentifiers()) {
			if (context.getSymbolClass(s).isAuxiliary()) {
				context.getValidation().addError(element, "State propagation from auxiliary variable");
				return;
			}
		}
	}

	public static void checkNoPrimedIdentifiers(CLElement element, CLExpression expression, TypingContext context) {
		for (final String s : expression.getFreeIdentifiers()) {
			if (isPrimed(s)) {
				context.getValidation().addError(element, "Invalid next state reference");
				return;
			}
		}
	}

	public static void checkIsFreshIdentifier(CLElement element, String identifier, TypingContext context, boolean error) {
		final CLType type = context.getType(identifier);
		if (type != null) {
			if (!error) {
				context.getValidation().addWarning(element, "Redefinition of identifier '" + identifier + "'");
				return;
			}

			switch (context.getSymbolClass(identifier)) {
			case MODELVARIABLE:
				context.getValidation().addError(element, "Identifier definition conflicts with model variable '" + identifier + "'");
				break;
			case AUXILIARY:
				context.getValidation().addError(element, "Identifier definition conflicts with auxiliary variable '" + identifier + "'");
				break;
			case BOUND:
				context.getValidation().addError(element, "Identifier definition conflicts with bound identifier '" + identifier + "'");
				break;
			default:
				context.getValidation().addError(element, "Redefinition of identifier '" + identifier + "'");
				break;
			}
		}
	}

	public static CLExpression buildConjunct(Collection<CLExpression> parts, SourceLocation loc) {
		if (parts.size() > 1) {
			return new CLMultiExpression(alphabet.OP_AND, parts, loc);
		} else if (parts.size() == 1) {
			return parts.iterator().next();
		} else {
			return CLAtomicExpression.TRUE;
		}
	}

	public static CLExpression buildConjunctMSL(Collection<CLExpression> parts) {
		if (parts.size() > 1) {
			return new CLMultiExpression(alphabet.OP_AND, parts, new MultiSourceLocation(parts));
		} else if (parts.size() == 1) {
			return parts.iterator().next();
		} else {
			return CLAtomicExpression.TRUE;
		}
	}

	public static CLExpression buildConjunct(CLExpression parta, CLExpression partb, SourceLocation loc) {
		return new CLMultiExpression(alphabet.OP_AND, parta, partb, loc);
	}

	public static CLExpression buildConjunctMSL(CLExpression parta, CLExpression partb) {
		final MultiSourceLocation msl = new MultiSourceLocation(parta, partb);
		return new CLMultiExpression(alphabet.OP_AND, parta, partb, msl);
	}

	public static CLExpression buildDisjunct(Collection<CLExpression> parts, SourceLocation loc) {
		if (parts.size() > 1) {
			return new CLMultiExpression(alphabet.OP_OR, parts, loc);
		} else if (parts.size() == 1) {
			return parts.iterator().next();
		} else {
			return CLAtomicExpression.TRUE;
		}
	}

	public static CLExpression buildConjunct(CLCollection<CLExpression> parts, SourceLocation loc) {
		if (parts.size() > 1) {
			return new CLMultiExpression(alphabet.OP_AND, parts, loc);
		} else if (parts.size() == 1) {
			return parts.iterator().next();
		} else {
			return CLAtomicExpression.TRUE;
		}
	}

	public static CLExpression buildMultiExpr(int tag, Collection<CLExpression> parts, SourceLocation loc) {
		if (parts.size() > 1) {
			return new CLMultiExpression(tag, parts, loc);
		} else if (parts.size() == 1) {
			return parts.iterator().next();
		} else {
			assert false;
			return null;
		}
	}

	public static boolean isBoolLiteral(CLExpression exp) {
		return exp.getTag() == alphabet.OP_TRUE || exp.getTag() == alphabet.OP_FALSE;
	}

	public static boolean isEnumLiteral(CLExpression exp, TypingContext ctx) {
		if (exp.getTag() == alphabet.ID) {
			final CLIdentifier id = (CLIdentifier) exp;
			return ctx.getSymbolClass(id.getName()) == SYMBOL_CLASS.ENUM_CONSTANT;
		}

		return false;
	}

	public static boolean isNumberLiteral(CLExpression exp) {
		return exp.getTag() == alphabet.NUMBER;
	}

	public static boolean getBooleanValue(CLExpression exp) {
		if (exp.getTag() == alphabet.OP_TRUE) {
			return true;
		} else {
			return false;
		}
	}

	public static int getIntegerValue(CLExpression exp) {
		if (exp.getTag() == alphabet.NUMBER) {
			final CLIntegerExpression number = (CLIntegerExpression) exp;
			return number.getValue();
		} else {
			return 0;
		}
	}

	public static boolean isSetLiteral(CLExpression exp, TypingContext ctx) {
		if (exp.getTag() == alphabet.SETC) {
			final CLMultiExpression me = (CLMultiExpression) exp;
			for (final CLExpression e : me.getParts()) {
				if (!isLiteralValue(e, ctx)) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	@SuppressWarnings("rawtypes")
	public static BSet getSetLiteral(CLExpression exp) throws InvalidSetOpException {
		final CLMultiExpression me = (CLMultiExpression) exp;
		final List<Object> result = new ArrayList<>(me.getParts().size());

		for (final CLExpression e : me.getParts()) {
			result.add(getLiteralValue(e));
		}

		return new BSet<>(result);
	}

	public static BSeq<?> getSeqLiteral(CLExpression exp) throws InvalidSetOpException {
		final CLMultiExpression me = (CLMultiExpression) exp;
		final List<Object> result = new ArrayList<>(me.getParts().size());

		for (final CLExpression e : me.getParts()) {
			result.add(getLiteralValue(e));
		}

		return BSeq.make(result);
	}

	public static boolean isSeqLiteral(CLExpression exp, TypingContext ctx) {
		if (exp.getTag() == alphabet.SEQC) {
			final CLMultiExpression me = (CLMultiExpression) exp;
			for (final CLExpression e : me.getParts()) {
				if (!isLiteralValue(e, ctx)) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	public static boolean isLiteralValue(CLExpression exp, TypingContext ctx) {
		return isBoolLiteral(exp) || isNumberLiteral(exp) || isEnumLiteral(exp, ctx) || isSetLiteral(exp, ctx) || isSeqLiteral(exp, ctx);
	}

	public static Object getLiteralValue(CLExpression exp) throws InvalidSetOpException {
		if (exp.getTag() == alphabet.OP_TRUE) {
			return Boolean.TRUE;
		} else if (exp.getTag() == alphabet.OP_FALSE) {
			return Boolean.FALSE;
		} else if (exp.getTag() == alphabet.NUMBER) {
			return ((CLIntegerExpression) exp).getValue();
		} else if (exp.getTag() == alphabet.SETC) {
			return getSetLiteral(exp);
		} else if (exp.getTag() == alphabet.SEQC) {
			return getSeqLiteral(exp);
		}
		return null;
	}

	public static String exceptionToString(Throwable exception) {

		final Writer writer = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(writer);

		if (exception instanceof ICLLocatableException) {
			final ICLLocatableException loc = (ICLLocatableException) exception;
			if (loc.getElement() != null) {
				final CLElement el = loc.getElement();
				if (el.getLocation() != null) {
					printWriter.print(el.getLocation().toString() + " \"" + el.toString() + "\": ");
				}
			}
		}

		if (exception.getCause() != null) {
			exception = exception.getCause();
		}

		if (exception.getMessage() != null && exception.getMessage().length() > 0) {
			printWriter.println(exception.getMessage());
		} else {
			exception.printStackTrace(printWriter);
		}
		return writer.toString();
	}

	public static Object mkValueOfType(CLType type) {
		if (type instanceof CLSeqType) {
			return BSeq.BSEQ_EMPTY;
		} else if (type.isRelation()) {
			return BRel.BREL_EMPTY;
		} else if (type instanceof CLPowerType) {
			return BSet.BSET_EMPTY;
		} else if (type instanceof CLProductType) {
			return new BMap<>(null, null);
		} else if (type.equals(CLTypeInteger.INSTANCE)) {
			return new Integer(0);
		} else if (type.equals(CLTypeBool.INSTANCE)) {
			return Boolean.TRUE;
		} else if (type instanceof CLGivenType) {
			return null;
		}

		return null;
	}

	public static CLExpression makeBoolLiteral(boolean value) {
		if (value) {
			return CLAtomicExpression.TRUE;
		} else {
			return CLAtomicExpression.FALSE;
		}
	}

	public static CLExpression makeSetLiteral(BSet<?> x) throws InvalidSetOpException {

		if (x.isEmpty()) {
			return CLAtomicExpression.EMPTYSET;
		}

		final CLCollection<CLExpression> parts = new CLCollection<>(x.card());

		for (final Object o : x) {
			parts.addPart(makeLiteral(o));
		}

		final CLExpression e = new CLMultiExpression(alphabet.SETC, parts, null);
		// e.type(null);

		return e;
	}

	public static CLExpression makeSeqLiteral(BSeq<?> x) throws InvalidSetOpException {

		final CLCollection<CLExpression> parts = new CLCollection<>(x.card());

		for (final Object o : x) {
			parts.addPart(makeLiteral(o));
		}

		final CLExpression e = new CLMultiExpression(alphabet.SEQC, parts, null);
		// e.type(null);

		return e;
	}

	public static CLExpression makeLiteral(Object object) throws InvalidSetOpException {

		if (object instanceof Boolean) {
			return makeBoolLiteral((Boolean) object);
		} else if (object instanceof Integer) {
			return new CLIntegerExpression((Integer) object);
		} else if (object instanceof BMap) {
			final BMap<?, ?> map = (BMap<?, ?>) object;
			return new CLBinaryExpression(alphabet.OP_MAP, makeLiteral(map.prj1()), makeLiteral(map.prj2()));
		} else if (object instanceof BSet) {
			final BSet<?> set = (BSet<?>) object;
			return makeSetLiteral(set);
		} else if (object instanceof BSeq) {
			final BSeq<?> set = (BSeq<?>) object;
			return makeSeqLiteral(set);
		} else if (object instanceof BProto<?>) {
			final BProto<?> bproto = (BProto<?>) object;
			return new CLIdentifier(bproto.getId().toString());
			// return new CLNamespace(bproto.getType().getName(),
			// bproto.getId().toString());
		} else if (object instanceof String) {
			return new CLIdentifier((String) object);
		}

		throw new InvalidSetOpException();

	}

	public static CLExpression listToMap(List<?> query) throws InvalidSetOpException {
		if (query.size() == 0) {
			throw new CLException("Invalid counter-example expression");
		} else if (query.size() == 1) {
			return makeLiteral(query.get(0));
		} else {
			// build left-associate map
			CLExpression map = new CLBinaryExpression(alphabet.OP_MAP, makeLiteral(query.get(0)), makeLiteral(query.get(1)));
			for (int i = 2; i < query.size(); i++) {
				map = new CLBinaryExpression(alphabet.OP_MAP, map, makeLiteral(query.get(i)));
			}
			return map;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BMap listToMapValue(List<Object> query) throws InvalidSetOpException {
		if (query.size() < 2) {
			throw new CLException("Invalid counter-example expression");
		} else {
			// build left-associate map
			BMap map = new BMap(query.get(0), query.get(1));
			for (int i = 2; i < query.size(); i++) {
				map = new BMap(map, query.get(i));
			}
			return map;
		}
	}

	private static CLExpression _wrapInExistential(CLExpression predicate, Collection<CLIdentifier> identifiers) {

		final CLCollection<CLParameter> bound_identifiers = new CLCollection<>(-1);
		final Map<String, CLExpression> map = new HashMap<>(identifiers.size());
		for (final CLIdentifier s : identifiers) {
			if (s == null) {
				return predicate;
			}

			map.put(s.getName(), new CLIdentifier(s.getName()));
			final CLParameter zpar = new CLParameter(s.getName(), s.getType());
			bound_identifiers.addPart(zpar);
		}

		// rewrite expression with copies of identifiers to make sure binding
		// does not affect the original expression
		predicate = predicate.rewrite(map);

		return new CLExistsExpression(bound_identifiers, predicate);
	}

	public static CLExpression _wrapInExistentialRaw(CLExpression predicate, CLCollection<CLParameter> bound_identifiers) {
		final Map<String, CLExpression> map = new HashMap<>(bound_identifiers.size());
		for (final CLParameter s : bound_identifiers) {
			map.put(s.getName(), new CLIdentifier(s.getName()));
		}

		// rewrite expression with copies of identifiers to make sure binding
		// does not affect the original expression
		predicate = predicate.rewrite(map);

		return new CLExistsExpression(bound_identifiers, predicate);
	}

	private static CLExpression _wrapInUniversal(CLExpression predicate, Collection<CLIdentifier> identifiers) {

		final CLCollection<CLParameter> bound_identifiers = new CLCollection<>(-1);

		final Map<String, CLExpression> map = new HashMap<>(identifiers.size());
		for (final CLIdentifier s : identifiers) {
			if (s == null) {
				return predicate;
			}

			map.put(s.getName(), new CLIdentifier(s.getName()));
			final CLParameter zpar = new CLParameter(s.getName(), s.getType());
			bound_identifiers.addPart(zpar);
		}

		// rewrite expression with copies of identifiers to make sure binding
		// does not affect the original expression
		predicate = predicate.rewrite(map);

		return new CLForallExpression(bound_identifiers, predicate);
	}

	public static CLExpression wrapInExistential(CLExpression predicate, Collection<String> identifiers) {
		final Collection<CLIdentifier> ident = new HashSet<>(identifiers.size());
		for (final String s : identifiers) {
			final CLIdentifier ii = predicate.resolveIdentifiers(s);

			if (ii != null) {
				ident.add(ii);
			}
		}

		if (!ident.isEmpty()) {
			return _wrapInExistential(predicate, ident);
		} else {
			return predicate;
		}
	}

	public static CLExpression wrapInExistential(CLExpression predicate, CLCollection<CLParameter> identifiers) {
		final Collection<CLIdentifier> ident = new HashSet<>(identifiers.size());
		for (final CLParameter s : identifiers) {
			final CLIdentifier ii = predicate.resolveIdentifiers(s.getName());

			if (ii != null) {
				ident.add(ii);
			}
		}

		if (!ident.isEmpty()) {
			return _wrapInExistential(predicate, ident);
		} else {
			return predicate;
		}
	}

	/**
	 * Wraps a predicate into a universal quantifier binding the given set of
	 * variables. The result is not valid until the returned expressed is type
	 * checked (this forces recalculation of bound identifiers in the quantifier
	 * body)
	 */
	public static CLExpression wrapInUniversal(CLExpression predicate, CLCollection<CLParameter> identifiers) {
		final Collection<CLIdentifier> ident = new HashSet<>(identifiers.size());
		for (final CLParameter s : identifiers) {
			final CLIdentifier ii = predicate.resolveIdentifiers(s.getName());

			if (ii != null) {
				ident.add(ii);
			}
		}

		if (!ident.isEmpty()) {
			return _wrapInUniversal(predicate, ident);
		} else {
			return predicate;
		}
	}

	/**
	 * Checks that a given identifier is not primed
	 */
	public static void identifierNotPrimed(CLElement element, String id, TypingContext context) {
		if (isPrimed(id)) {
			context.getValidation().addError(element, "Identifier '" + id + "' may not be primed");
		}

	}

	/**
	 * Tests whether identifier is primed, i.e., has form name'[qualifier]
	 * 
	 * @param id an identifier name
	 * @return true if the identifier is primed
	 */
	public static boolean isPrimed(String id) {
		return id != null && id.indexOf('\'') > 0;
	}

	/**
	 * Tests whether identifier is qualifier, i.e., of the form name'qualifier or
	 * name' or 'qualifier
	 * 
	 * @param id an identifier name
	 * @return true if the identifier is qualified
	 */
	public static boolean isQualified(String id) {
		if (id != null && !id.isEmpty()) {
			final int index = id.indexOf('\'');
			return index >= 0 && index < id.length() - 1;
		}
		return false;
	}

	/**
	 * Tests whether identifier is qualifier, i.e., of the form id'qualifier or
	 * name' or 'qualifier
	 * 
	 * @param id        an identifier name
	 * @param qualifier qualifier string
	 * @return true if the identifier is qualified with the given qualifier string
	 */
	public static boolean isQualified(String id, String qualifier) {
		if (id != null && !id.isEmpty()) {
			final int index = id.indexOf('\'');
			if (index >= 0 && index < id.length() - 1) {
				return id.substring(index + 1).equals(qualifier);
			}
		}
		return false;
	}

	/**
	 * Tests whether a given variable is unreal and has the form
	 * <code>'qualifier</code>. An unreal variable has no trace in user-facing
	 * specification conditions, i.e., invariants or post-conditions and is a form
	 * of internal auxiliary variable. Every unreal variable is also a qualified
	 * one. <br>
	 * One important rule is that there may be no context properties (invariants,
	 * relies) mentioning unreal variables.
	 * 
	 * @param id an identifier name
	 * @return true if the identifier is unreal
	 */
	public static boolean isUnreal(String id) {
		if (id != null && !id.isEmpty()) {
			if (id.charAt(0) == '\'') {
				return true;
			}
		}
		return false;
	}

	public static String unPrimed(String id) {
		if (isPrimed(id)) {
			return id.substring(0, id.indexOf('\''));
		} else {
			return id;
		}
	}

	public static String primed(String id) {
		if (isPrimed(id)) {
			return id;
		} else {
			return id + "'";
		}
	}

	public final static boolean isRelevant(Collection<String> identifiers, CLExpression expression) {
		if (identifiers.isEmpty()) {
			return false;
		}

		final Set<String> expression_identifiers = expression.getFreeIdentifiers();

		if (expression_identifiers.size() < identifiers.size()) {
			for (final String s : expression_identifiers) {
				if (identifiers.contains(s)) {
					return true;
				}
			}
		} else {
			for (final String s : identifiers) {
				if (expression_identifiers.contains(s)) {
					return true;
				}
			}

		}

		return false;
	}

	public final static boolean isRelevant(String identifier, CLExpression expression) {
		final Set<String> expression_identifiers = expression.getFreeIdentifiers();
		return expression_identifiers.contains(identifier);
	}

	public static CLExpression negate(CLExpression expr) {
		if (expr.getTag() == alphabet.OP_NOT) {
			return ((CLUnaryExpression) expr).getArgument();
		} else if (expr.getTag() == alphabet.B_FORALL) {
			final CLForallExpression bexpr = (CLForallExpression) expr;
			return new CLExistsExpression(bexpr.getBoundParameters(), negate(bexpr.getBody()), expr.getLocation());
		} else if (expr.getTag() == alphabet.B_EXISTS) {
			final CLExistsExpression bexpr = (CLExistsExpression) expr;
			return new CLForallExpression(bexpr.getBoundParameters(), negate(bexpr.getBody()), expr.getLocation());
		} else if (expr.getTag() == alphabet.OP_EQL) {
			final CLBinaryExpression bexpr = (CLBinaryExpression) expr;
			return new CLBinaryExpression(alphabet.OP_NEQ, bexpr.getLeft(), bexpr.getRight(), expr.getLocation());
		} else if (expr.getTag() == alphabet.OP_NEQ) {
			final CLBinaryExpression bexpr = (CLBinaryExpression) expr;
			return new CLBinaryExpression(alphabet.OP_EQL, bexpr.getLeft(), bexpr.getRight(), expr.getLocation());
		} else if (expr.getTag() == alphabet.OP_IN) {
			final CLBinaryExpression bexpr = (CLBinaryExpression) expr;
			return new CLBinaryExpression(alphabet.OP_NOTIN, bexpr.getLeft(), bexpr.getRight(), expr.getLocation());
		} else if (expr.getTag() == alphabet.OP_NOTIN) {
			final CLBinaryExpression bexpr = (CLBinaryExpression) expr;
			return new CLBinaryExpression(alphabet.OP_IN, bexpr.getLeft(), bexpr.getRight(), expr.getLocation());
		} else if (expr.getTag() == alphabet.OP_AND) {
			final CLMultiExpression bexpr = (CLMultiExpression) expr;
			final Collection<CLExpression> parts = new ArrayList<>();
			for (final CLExpression e : bexpr.getParts().getParts()) {
				parts.add(negate(e));
			}
			return new CLMultiExpression(alphabet.OP_OR, parts, expr.getLocation());
		} else if (expr.getTag() == alphabet.OP_OR) {
			final CLMultiExpression bexpr = (CLMultiExpression) expr;
			final Collection<CLExpression> parts = new ArrayList<>();
			for (final CLExpression e : bexpr.getParts().getParts()) {
				parts.add(negate(e));
			}
			return new CLMultiExpression(alphabet.OP_AND, parts, expr.getLocation());
		} else {
			return new CLUnaryExpression(alphabet.OP_NOT, expr, expr.getLocation());
		}
	}

	/*
	 * public static boolean isConstant0(CLExpression expression,
	 * RuntimeExecutionContext model) { try { Set<String> expression_identifiers =
	 * expression.getFreeIdentifiers(); TypingContext ctx = model.getRootContext();
	 * for (String s : expression_identifiers) if
	 * (!ctx.getSymbolClass(s).isConstant() || model.getValue(s) == null) return
	 * false;
	 * 
	 * return expression.getBoundIdentifiers().size() == 0; } catch
	 * (CLExecutionException e) { e.printStackTrace(); return false; } }
	 * 
	 * public static boolean isConstant(CLExpression expression,
	 * RuntimeExecutionContext model) { if (expression.getBoundIdentifiers().size()
	 * != 0) return false;
	 * 
	 * Set<String> expression_identifiers = expression.getFreeIdentifiers();
	 * TypingContext ctx = model.getRootContext(); for (String s :
	 * expression_identifiers) if (!ctx.getSymbolClass(s).isConstant()) return
	 * false;
	 * 
	 * return true; }
	 * 
	 * public static boolean isConstant(CLExpression expression, TypingContext ctx)
	 * { Set<String> expression_identifiers = expression.getFreeIdentifiers(); for
	 * (String s : expression_identifiers) if (!ctx.getSymbolClass(s).isConstant())
	 * return false;
	 * 
	 * return expression.getBoundIdentifiers().size() == 0; }
	 */

	public static boolean isConstant(CLExpression expression, TypingContext ctx) {
		return expression.isConstant(ctx);
	}

	public static boolean isConstant(CLExpression expression, RuntimeExecutionContext model) {
		return expression.isConstant(model.getRootContext());
	}

	public static boolean isTrivialConstant(CLExpression expression, RuntimeExecutionContext model) {
		try {
			final Set<String> expression_identifiers = expression.getFreeIdentifiers();
			if (expression_identifiers.size() > 1) {
				return false;
			}
			final TypingContext ctx = model.getRootContext();
			for (final String s : expression_identifiers) {
				if (!ctx.getSymbolClass(s).isConstant() || model.getValue(s) == null) {
					return false;
				}
			}

			return true;
		} catch (final CLExecutionException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static CLExpression makeSetC(Collection<CLExpression> elements) {
		if (elements.isEmpty()) {
			return CLAtomicExpression.EMPTYSET;
		}

		return new CLMultiExpression(alphabet.SETC, elements, null);
	}

	public static CLExpression typeToExpression(CLType type) {
		if (type instanceof CLTypeBool) {
			return CLAtomicExpression.BOOL;
		} else if (type instanceof CLTypeInteger) {
			return CLAtomicExpression.INTEGER;
		} else if (type instanceof CLTypeReal) {
			return null;
		} else if (type instanceof CLPowerType) {
			return new CLUnaryExpression(alphabet.OP_SET, typeToExpression(type.baseType()));
		} else if (type instanceof CLSeqType) {
			return new CLUnaryExpression(alphabet.OP_SEQ, typeToExpression(type.baseType()));
		} else if (type instanceof CLProductType) {
			final CLProductType ptype = (CLProductType) type;
			return new CLBinaryExpression(alphabet.OP_CART, typeToExpression(ptype.getLeft()), typeToExpression(ptype.getRight()));
		} else if (type instanceof CLUserType) {
			final CLUserType ptype = (CLUserType) type;
			return new CLIdentifier(ptype.getName());
		} else if (type instanceof CLTypeAny) {
			final CLTypeAny ptype = (CLTypeAny) type;
			if (ptype.getBakedType() != null) {
				return typeToExpression(ptype.getBakedType());
			}
		}

		return null;

	}

	public static String getSourceLocationMap(CLExpression expr) {
		final StringBuilder sb = new StringBuilder();
		getSourceLocationMap(expr, sb);
		return sb.toString();
	}

	private static void getSourceLocationMap(CLExpression expr, StringBuilder sb) {
		if (expr.getLocation() != null) {
			sb.append(expr.getLocation().asString());
		} else if (expr instanceof CLMultiExpression) {
			final CLMultiExpression me = (CLMultiExpression) expr;
			sb.append(me._opsymbol() + "[");
			for (final CLExpression c : me.getParts()) {
				if (hasSourceLocation(c)) {
					getSourceLocationMap(c, sb);
				} else {
					sb.append("_");
				}
			}
			sb.append("]");
		} else if (expr instanceof CLBinaryExpression) {
			final CLBinaryExpression me = (CLBinaryExpression) expr;
			if (hasSourceLocation(me.getLeft())) {
				getSourceLocationMap(me.getLeft(), sb);
			} else {
				sb.append("_");
			}
			sb.append(" " + me._opsymbol() + " ");
			if (hasSourceLocation(me.getLeft())) {
				getSourceLocationMap(me.getRight(), sb);
			} else {
				sb.append("_");
			}
		}
	}

	private static boolean hasSourceLocation(CLExpression expr) {
		if (expr.getLocation() != null) {
			return true;
		} else if (expr instanceof CLMultiExpression) {
			final CLMultiExpression me = (CLMultiExpression) expr;
			for (final CLExpression c : me.getParts()) {
				if (hasSourceLocation(c)) {
					return true;
				}
			}
			return false;
		} else if (expr instanceof CLBinaryExpression) {
			final CLBinaryExpression me = (CLBinaryExpression) expr;
			return hasSourceLocation(me.getLeft()) || hasSourceLocation(me.getRight());
		} else {
			return false;
		}
	}

	public static int getOrMaxFanIn(CLExpression expr) {
		int r = 0;
		if (expr.getTag() == alphabet.OP_AND) {
			final CLMultiExpression me = (CLMultiExpression) expr;
			for (final CLExpression c : me.getParts()) {
				r += getOrMaxFanIn(c);
			}
		} else if (expr.getTag() == alphabet.OP_OR) {
			final CLMultiExpression me = (CLMultiExpression) expr;
			for (final CLExpression c : me.getParts()) {
				r += getOrMaxFanIn(c);
			}
			r += me.getParts().size();
		}
		return r;
	}

	public static void setLocationTrace(CLExpression expression, List<String> trace) {
		if (expression.getLocation() != null) {

			if (expression.getTag() == alphabet.OP_AND) {
				final CLMultiExpression me = (CLMultiExpression) expression;
				for (final CLExpression e : me.getParts()) {
					setLocationTrace(e, trace);
				}
			} else if (expression instanceof CLAtomicExpression) {
				return;
			} else {
				expression.setLocation(new SourceLocation(trace, expression.getLocation()));
			}
		}
	}

	public static CLExpression iterativeSimplify(CLExpression a, TypingContext ctx) {
		CLExpression t = a.simplify(ctx);
		int maxDepth = 5;
		while (maxDepth-- > 0 && t != a) {
			a = t;
			t = t.simplify(ctx);
		}
		return t;
	}



}
