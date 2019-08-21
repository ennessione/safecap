package uk.ac.ncl.prime.sim.lang.semantics.prel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLBase;
import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.model.CLAssignmentStatement;
import uk.ac.ncl.prime.sim.lang.model.CLIndexedAssignmentStatement;
import uk.ac.ncl.prime.sim.lang.model.CLNonDetAssignmentStatement;
import uk.ac.ncl.prime.sim.lang.model.CLParallelAssignmentStatement;
import uk.ac.ncl.prime.sim.lang.model.CLSetAssignmentStatement;
import uk.ac.ncl.prime.sim.lang.model.CLSubstitution;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.semantics.TranslationValidation;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeAny;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.prime.sim.parser.MultiSourceLocation;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.BSet;

public class RSTranslationContext extends CLBase {
	private static final List<String> VAR_FILTER = Arrays.asList(new String[] {});
	
	private final TypingContext typing;
	private final TranslationValidation validation;
	private final RSTranslationContext parent;
	protected Map<String, Integer> identifierStage;

	private final Map<String, TranslationAssignment> partials;
	private final Map<String, CLExpression> assignments;
	private final Map<String, CLExpression> substitutions;
	private final Map<String, CLExpression> simpleFacts; // equalities derived from conditions
	private final List<CLExpression> facts; // other useful facts from conditions
	private final RSRewriter rewriter;

	public RSTranslationContext(TypingContext typing, TranslationValidation validation) {
		this.validation = validation;
		parent = null;
		this.typing = typing;
		identifierStage = new HashMap<>();
		partials = new HashMap<>();
		assignments = new HashMap<>();
		substitutions = new HashMap<>();
		simpleFacts = new HashMap<>();
		facts = new ArrayList<>();
		rewriter = new RSRewriter(this);
	}

	public RSTranslationContext(RSTranslationContext parent) {
		validation = parent.validation;
		this.parent = parent;
		typing = parent.typing;
		identifierStage = new HashMap<>();
		partials = new HashMap<>();
		assignments = new HashMap<>();
		substitutions = new HashMap<>();
		simpleFacts = new HashMap<>();
		facts = new ArrayList<>();
		rewriter = new RSRewriter(this);
	}

	public TranslationValidation getValidation() {
		return validation;
	}

	public void addFact(CLExpression expr) {
		if (expr.getTag() == alphabet.OP_AND) { // break up conjunction
			final CLMultiExpression m = (CLMultiExpression) expr;
			for (final CLExpression p : m.getParts()) {
				addFact(p);
			}
		} else if (expr.getTag() == alphabet.OP_EQL) { // break up conjunction
			final CLBinaryExpression be = (CLBinaryExpression) expr;
			if (!be.getLeft().isConstant(typing) && be.getLeft() instanceof CLIdentifier) {
				final CLIdentifier id = (CLIdentifier) be.getLeft();
				simpleFacts.put(id.getName(), be.getRight());
			} else if (!be.getRight().isConstant(typing) && be.getRight() instanceof CLIdentifier) {
				final CLIdentifier id = (CLIdentifier) be.getRight();
				simpleFacts.put(id.getName(), be.getLeft());
			} else {
				facts.add(expr);
			}
		} else if (relevant(expr.getTag())) { // other facts
			facts.add(expr);
		}
	}

	public Map<String, CLExpression> getSimpleFacts() {
		return simpleFacts;
	}

	public List<CLExpression> getFacts() {
		return facts;
	}

	private static final int[] RELEVANT = { alphabet.OP_IN, alphabet.OP_NEQ, alphabet.OP_NOTIN, alphabet.OP_LEQ, alphabet.OP_GEQ,
			alphabet.OP_LSS, alphabet.OP_GRT };

	private boolean relevant(int tag) {
		for (int i = 0; i < RELEVANT.length; i++) {
			if (RELEVANT[i] == tag) {
				return true;
			}
		}

		return false;
	}

	private boolean isKnownFact(CLExpression template) {
		for (final CLExpression e : facts) {
			if (e.match(template, typing) != null) {
				return true;
			}
		}

		return false;
	}

	public CLExpression getAssigmentFor(String name) {
		CLExpression result = simpleFacts.get(name);
		if (result != null) {
			return result;
		}
		result = assignments.get(name);
		if (result != null) {
			return result;
		} else if (parent != null) {
			return parent.getAssigmentFor(name);
		} else {
			return null;
		}
	}

	public TranslationAssignment getPartialsFor(String name) {
		final TranslationAssignment result = partials.get(name);
		if (result != null) {
			return result;
		} else if (parent != null) {
			return parent.getPartialsFor(name);
		} else {
			return null;
		}
	}

	public void process(CLSubstitution substitution) {
		if (!type(substitution)) {
			return;
		}

		if (VAR_FILTER.containsAll(substitution.getUpdatedVariables()))
			return;
		
		if (substitution instanceof CLAssignmentStatement) {
			_process((CLAssignmentStatement) substitution);
		} else if (substitution instanceof CLIndexedAssignmentStatement) {
			_process((CLIndexedAssignmentStatement) substitution);
		} else if (substitution instanceof CLSetAssignmentStatement) {
			_process((CLSetAssignmentStatement) substitution);
		} else if (substitution instanceof CLNonDetAssignmentStatement) {
			_process((CLNonDetAssignmentStatement) substitution);
		} else if (substitution instanceof CLParallelAssignmentStatement) {
			_process((CLParallelAssignmentStatement) substitution);
		}
	}

	private boolean type(CLSubstitution substitution) {
		CLExpression expr = substitution.getSubstitutionPredicate();
		if (expr == null || expr.getType() == null) {
			substitution.type(typing);
			expr = substitution.getSubstitutionPredicate();
			if (expr == null || expr.getType() == null) {
				validation.addIssue(substitution.getLocation(), "Failed typing substitution " + substitution);
				return false;
			}
		}

		return true;
	}

	private void _process(CLAssignmentStatement assignment) {

		// test for x = x \/ {E}
		// CLExpression template_union = new CLMultiExpression(alphabet.OP_UNION, new
		// CLIdentifier("$" + assignment.getVariableName()), new CLIdentifier("b"));
		final CLExpression template_union = union(varq(assignment.getVariableName()), tem("b", alphabet.SETC));
		Map<String, CLExpression> result = assignment.getValue().match(template_union, typing);
		if (result != null) {
			final CLExpression b = result.get("b");
			final CLMultiExpression me = (CLMultiExpression) b;
			for (final CLExpression e : me.getParts()) {
				addPartial(assignment.getVariableName(), prepare(e, assignment.getLocation()), assignment);
			}
			return;
		}

		// test for x = x \ {E}
		final CLExpression template_setminus = setminus(varq(assignment.getVariableName()), tem("b", alphabet.SETC));
		result = assignment.getValue().match(template_setminus, typing);
		if (result != null) {
			final CLExpression b = result.get("b");
			final CLMultiExpression me = (CLMultiExpression) b;
			for (final CLExpression e : me.getParts()) {
				remPartial(assignment.getVariableName(), prepare(e, assignment.getLocation()), assignment);
			}
			return;
		}

		// test for x = x \/ {Q} \ {E}
		final CLExpression template_union_setminus = setminus(union(varq(assignment.getVariableName()), tem("b", alphabet.SETC)),
				tem("c", alphabet.SETC));
		result = assignment.getValue().match(template_union_setminus, typing);
		if (result != null) {
			final CLExpression b = result.get("b");
			final CLExpression c = result.get("c");

			final CLMultiExpression me_add = (CLMultiExpression) b;
			for (final CLExpression e : me_add.getParts()) {
				addPartial(assignment.getVariableName(), prepare(e, assignment.getLocation()), assignment);
			}

			final CLMultiExpression me_rem = (CLMultiExpression) c;
			for (final CLExpression e : me_rem.getParts()) {
				remPartial(assignment.getVariableName(), prepare(e, assignment.getLocation()), assignment);
			}
			return;
		}

		// test for x = x <+ {E}
		// CLExpression template_union = new CLMultiExpression(alphabet.OP_UNION, new
		// CLIdentifier("$" + assignment.getVariableName()), new CLIdentifier("b"));
		final CLExpression template_ovr = ovr(varq(assignment.getVariableName()), tem("b", alphabet.SETC));
		result = assignment.getValue().match(template_ovr, typing);
		if (result != null) {
			final CLExpression b = result.get("b");
			final CLMultiExpression me = (CLMultiExpression) b;
			for (final CLExpression e : me.getParts()) {
				addPartial(assignment.getVariableName(), prepare(e, assignment.getLocation()), assignment);
			}
			return;
		}

		// fallback to generic case
		final CLExpression expression = prepare(assignment.getValue(), assignment.getLocation());
		forget(expression, assignment.getUpdatedVariables());

		if (expression != null) {
			assignments.put(assignment.getVariableName(), expression);
			partials.remove(assignment.getVariableName());
		}
	}

	private void _process(CLIndexedAssignmentStatement assignment) {
		assert assignment.getMaps().size() == 1;
		final CLExpression ovr = prepare(assignment.getMaps().get(0), assignment.getLocation());
		if (ovr != null) {
			addPartial(assignment.getId(), ovr, assignment);
		}

	}

	private void _process(CLSetAssignmentStatement assignment) {
		for (final CLExpression _a : assignment.getAddedElements()) {
			final CLExpression a = prepare(_a, assignment.getLocation());
			if (a != null) {
				addPartial(assignment.getId(), a, assignment);
			}
		}

		for (final CLExpression _a : assignment.getRemovedElements()) {
			final CLExpression a = prepare(_a, assignment.getLocation());
			if (a != null) {
				remPartial(assignment.getId(), a, assignment);
			}
		}
	}

	private void _process(CLNonDetAssignmentStatement assignment) {
		forget(assignment.getPredicate(), assignment.getUpdatedVariables());
		final CLExpression expression = prepare(assignment.getPredicate(), assignment.getLocation());
		if (expression != null) {
			addSubstitution(assignment, expression);
		}
	}

	private void _process(CLParallelAssignmentStatement assignment) {
		for (final CLSubstitution subst : assignment.getAssignments()) {
			process(subst);
		}
	}

	private void addPartial(String id, CLExpression expr, CLSubstitution subst) {
		// filter out known facts
		final CLExpression template = in(quote(expr), varq(id));
		if (!isKnownFact(template)) {
			final TranslationAssignment ta = getPartial(id);
			ta.addTAPart(expr);
		} else {
			validation.addIssue(subst.getLocation(), "Unnecessary assignment");
		}

	}

	private void remPartial(String id, CLExpression expr, CLSubstitution subst) {
		final CLExpression template = notin(quote(expr), varq(id));
		if (!isKnownFact(template)) {
			final TranslationAssignment ta = getPartial(id);
			ta.removeTAPart(expr);
		} else {
			validation.addIssue(subst.getLocation(), "Unnecessary assignment");
		}
	}

	private TranslationAssignment getPartial(String var) {
		if (!partials.containsKey(var)) {
			final TranslationAssignment ta = new TranslationAssignment(parent.getPartialsFor(var));
			partials.put(var, ta);
			return ta;
		} else {
			return partials.get(var);
		}
	}

	public CLExpression prepare(CLExpression value, SourceLocation location) {
		try {
			CLExpression r = rewriter.rewrite(value);
			r = r.simplify(typing);
			r.setLocation(location);
			return r;
		} catch (final Throwable e) {
			e.printStackTrace();
			validation.addIssue(value, "rewrite failed (internal)");
			return null;
		}
	}

	private void forget(CLExpression expression, Set<String> updated) {
		final BSet<String> a = new BSet<>(expression.getFreeIdentifiers());
		final BSet<String> b = new BSet<>(updated);

		for (final String s : b.setminus(a)) {
			forget(s);
		}

	}

	private void forget(String s) {
		partials.remove(s);
		assignments.remove(s);
		substitutions.remove(s);
		simpleFacts.remove(s);

		final List<CLExpression> toRemove = new ArrayList<>();
		for (final CLExpression e : facts) {
			if (e.getFreeIdentifiers().contains(s)) {
				toRemove.add(e);
			}
		}

		facts.removeAll(toRemove);
	}

	/**
	 * we know some P(q,s,t,u) substitution updates some q and t, i.e.,
	 * q'=E(q,s,t,u) t'=W(q,s,t,u)
	 * 
	 * rename all occurrences of q and t in P and then add the substitutions P' =
	 * P[q' -> next(q), t' - next(t)] \/ {q' == E(prev(q,s,t,u)), t' ==
	 * W(prev(q,s,t,u))}
	 */
	private void addSubstitution(CLSubstitution substitution, CLExpression expression) {
		final Collection<String> toRename = new HashSet<>();

		// detect any identifiers that need to be rewritten in the facts
		for (final String v : substitution.getUpdatedVariables()) {
			if (identifierStage.keySet().contains(v)) {
				toRename.add(v);
			}
		}

		// rewrite
		if (!toRename.isEmpty()) {
			final Map<String, CLExpression> map = new HashMap<>(toRename.size());

			for (final String s : toRename) {
				map.put(CLUtils.primed(s), current(s));
			}

			final Collection<String> keys = new ArrayList<>(substitutions.keySet());
			for (final String s : keys) {
				CLExpression e = substitutions.get(s);
				e = e.rewrite(map);
				substitutions.put(s, e);
			}
			toRename.clear();
		}

		final Collection<String> toPrime = new HashSet<>();

		// detect any identifiers that need to be rewritten in the substitution
		for (final String v : expression.getFreeIdentifiers()) {
			if (!CLUtils.isPrimed(v) && identifierStage.keySet().contains(v)) {
				if (substitution.getUpdatedVariables().contains(v)) {
					toRename.add(v);
				} else {
					toPrime.add(v);
				}
			}
		}

		CLExpression newSubst = expression;
		// rewrite
		if (!toRename.isEmpty()) {
			final Map<String, CLExpression> map = new HashMap<>(toRename.size());
			for (final String s : toRename) {
				map.put(s, current(s));
			}

			for (final String s : toPrime) {
				map.put(s, new CLIdentifier(CLUtils.primed(s)));
			}

			newSubst = newSubst.rewrite(map);
		}

		// populate stage map
		for (final String v : substitution.getUpdatedVariables()) {
			next(v);
		}

		// save
		for (final String var : substitution.getUpdatedVariables()) {
			substitutions.put(var, newSubst);
			partials.remove(var);
			assignments.remove(var);
		}
	}

	protected Integer getStage(String s) {
		if (identifierStage.containsKey(s)) {
			return identifierStage.get(s);
		} else if (parent != null) {
			return parent.getStage(s);
		} else {
			return null;
		}
	}

	protected boolean hasStage(String s) {
		return getStage(s) != null;
	}

	protected CLIdentifier current(String s) {
		assert getStage(s) != null;
		return new CLIdentifier(s, "" + getStage(s), null);
	}

	protected int getStageInternal(String s) {
		if (identifierStage.containsKey(s)) {
			return identifierStage.get(s);
		} else if (parent != null) {
			return parent.getStage(s);
		} else {
			return 0;
		}
	}

	private CLExpression next(String s) {
		final int index = getStageInternal(s);
		identifierStage.put(s, index);
		return new CLIdentifier(s, "" + index, null);
	}

	public List<CLSubstitution> commit() {
		final List<CLSubstitution> result = new ArrayList<>();

		for (final String s : assignments.keySet()) {
			final CLSubstitution subst = new CLAssignmentStatement(s, new CLTypeAny(null), assignments.get(s), SYMBOL_CLASS.IDENTIFIER,
					assignments.get(s).getLocation());
			subst.type(getTyping());
			result.add(subst);
		}

		for (final String s : partials.keySet()) {
			final CLSubstitution subst = new CLAssignmentStatement(s, new CLTypeAny(null), partials.get(s).makeExtendedPredicateRHS(s),
					SYMBOL_CLASS.IDENTIFIER, partials.get(s).getLocation());
			subst.type(getTyping());
			result.add(subst);
		}

		return result;
	}

	public void forget(List<CLSubstitution> substs) {
		for (final CLSubstitution s : substs) {
			for (final String z : s.getUpdatedVariables()) {
				forget(z);
			}
		}
	}

	class TranslationAssignment {
		private final List<CLExpression> added;
		private final List<CLExpression> removed;
		private boolean isPairBased = false;
		private final boolean isContradiction = false;;

		public TranslationAssignment(TranslationAssignment parent) {

			added = new ArrayList<>();
			removed = new ArrayList<>();
			if (parent != null) {
				added.addAll(parent.added);
				removed.addAll(parent.removed);
				isPairBased = parent.isPairBased;
			}

		}

		public CLExpression makeExtendedPredicateRHS(String identifier) {
			final CLIdentifier var = new CLIdentifier(identifier);
			if (isPairBased) {
				assert !added.isEmpty() && removed.isEmpty();
				final CLCollection<CLExpression> parts = new CLCollection<>(added.size() + 1);
				parts.addPart(var);
				parts.addPart(CLUtils.makeSetC(added));
				return new CLMultiExpression(alphabet.OP_OVR, parts, getLocation());
			} else {
				assert !added.isEmpty() || !removed.isEmpty();
				if (!added.isEmpty()) {
					final CLCollection<CLExpression> parts = new CLCollection<>(added.size() + 1);
					parts.addPart(var);
					parts.addPart(CLUtils.makeSetC(added));
					CLExpression result = new CLMultiExpression(alphabet.OP_UNION, parts, getLocation());
					if (!removed.isEmpty()) {
						final CLCollection<CLExpression> parts2 = new CLCollection<>(removed.size() + 1);
						parts2.addPart(result);
						parts2.addPart(CLUtils.makeSetC(removed));
						result = new CLMultiExpression(alphabet.OP_SETMINUS, parts2, getLocation());
					}
					return result;
				} else {
					final CLCollection<CLExpression> parts2 = new CLCollection<>(removed.size() + 1);
					parts2.addPart(var);
					parts2.addPart(CLUtils.makeSetC(removed));
					return new CLMultiExpression(alphabet.OP_SETMINUS, parts2, getLocation());
				}
			}
		}

		public void addTAPart(CLExpression expr) {
			if (added.contains(expr)) {
				validation.addIssue(expr, "Duplicate value setting " + expr);
				return;
			}

			if (removed.contains(expr)) {
				validation.addIssue(expr, "Shadowed value setting " + expr);
			}

			final boolean isPair = expr.getTag() == alphabet.OP_MAP;

			if (!added.isEmpty() || !removed.isEmpty()) {
				assert isPair == isPairBased;
			}

			isPairBased = isPair;

			if (isPairBased) {
				Map<CLExpression, CLExpression> map = new HashMap<>();
				for (final CLExpression a : added) {
					final CLBinaryExpression be = (CLBinaryExpression) a;
					if (map.containsKey(be.getLeft())) {
						if (map.get(be.getLeft()) == be.getRight()) {
							validation.addIssue(expr, "Duplicate pair setting " + be);
						} else {
							validation.addIssue(expr, "Shodowing pair setting " + be);
						}
					}
					map.put(be.getLeft(), be.getRight());
				}
				map = null;
			}
			added.add(expr);
			removed.remove(expr);
		}

		public void removeTAPart(CLExpression expr) {
			if (removed.contains(expr)) {
				validation.addIssue(expr, "Duplicate value removal " + expr);
				return;
			}

			if (added.contains(expr)) {
				validation.addIssue(expr, "Removing previously added value " + expr);
			}

			final boolean isPair = expr.getTag() == alphabet.OP_MAP;
			assert isPair == false;
			isPairBased = isPair;

			removed.add(expr);
			added.remove(expr);
		}

		public SourceLocation getLocation() {
			if (!added.isEmpty() && removed.isEmpty()) {
				if (added.size() == 1) {
					return added.get(0).getLocation();
				} else {
					return new MultiSourceLocation(added);
				}
			} else if (added.isEmpty() && !removed.isEmpty()) {
				if (removed.size() == 1) {
					return removed.get(0).getLocation();
				} else {
					return new MultiSourceLocation(removed);
				}
			} else {
				final List<CLExpression> overall = new ArrayList<>(added);
				overall.addAll(removed);
				assert overall.size() > 1;
				return new MultiSourceLocation(overall);
			}
		}
	}

	public TypingContext getTyping() {
		return typing;
	}

}
