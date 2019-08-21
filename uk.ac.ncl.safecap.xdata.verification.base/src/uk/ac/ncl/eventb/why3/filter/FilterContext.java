package uk.ac.ncl.eventb.why3.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import uk.ac.ncl.prime.sim.lang.CLExpression;

public class FilterContext {
	private final GlobalLemmataLibrary library;
	private final Map<CLExpression, SparseBitSet> hypFilterDepth;
	private final SparseBitSet goalDepth;

	private final Map<CLExpression, SparseBitSet> hypFilterWidth;
	private final SparseBitSet goalWidth;

	private final SparseBitSet extraLemmata;

	private final Stack<Integer> operatorStackDepth;
	private CLExpression currentHyp = null;

	public FilterContext(GlobalLemmataLibrary library) {
		this.library = library;
		hypFilterDepth = new HashMap<>();
		hypFilterWidth = new HashMap<>();

		goalDepth = new SparseBitSet();
		goalWidth = new SparseBitSet();
		extraLemmata = new SparseBitSet();

		operatorStackDepth = new Stack<>();
	}

	public SparseBitSet getExtraLemmataFilter() {
		return extraLemmata;
	}

	public void injectExtraLemmataFilter(SparseBitSet bitset) {
		extraLemmata.or(bitset);
	}

	public void startHypothesis(CLExpression predicate) {
		hypFilterDepth.put(predicate, new SparseBitSet());
		hypFilterWidth.put(predicate, new SparseBitSet());
		currentHyp = predicate;
		operatorStackDepth.clear();
	}

	public void startGoal() {
		currentHyp = null;
		operatorStackDepth.clear();
	}

	public SparseBitSet getGoalFilterDepth() {
		return goalDepth;
	}

	public SparseBitSet getGoalFilterWidth() {
		return goalWidth;
	}

	public Set<CLExpression> getHypothesis() {
		return hypFilterDepth.keySet();
	}

	public SparseBitSet getHypothesisFilterDepth(CLExpression predicate) {
		return hypFilterDepth.get(predicate);
	}

	public SparseBitSet getHypothesisFilterWidth(CLExpression predicate) {
		return hypFilterWidth.get(predicate);
	}

	private SparseBitSet getCurrentFilterDepth() {
		if (currentHyp == null) {
			return goalDepth;
		} else {
			return hypFilterDepth.get(currentHyp);
		}
	}

	private SparseBitSet getCurrentFilterWidth() {
		if (currentHyp == null) {
			return goalWidth;
		} else {
			return hypFilterWidth.get(currentHyp);
		}
	}

	public void widthFilter(TreeNode<String> node) {
		if (node.getChildren().size() > 1) {
			// System.out.println("Processing tree :" + node.toString());
			FilterGlobals.insertFilter(library, node, getCurrentFilterWidth());
		}
	}

	public void reference(String operator) {
		FilterGlobals.insertFilter(library.mapWhy3Operator(operator), getCurrentFilterDepth());
	}

	public void enter(String operator) {
		final int code = library.mapWhy3Operator(operator);
		operatorStackDepth.push(code);
	}

	public void leave() {
		assert !operatorStackDepth.isEmpty();
		FilterGlobals.insertFilter(operatorStackDepth, getCurrentFilterDepth());
		operatorStackDepth.pop();
	}
}
