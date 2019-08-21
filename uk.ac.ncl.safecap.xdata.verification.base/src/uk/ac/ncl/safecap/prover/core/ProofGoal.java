package uk.ac.ncl.safecap.prover.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.prime.sim.lang.CLAtomicExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLForeignLocationExpression;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.safecap.prover.transforms.trace.GoalProvenance;
import uk.ac.ncl.safecap.prover.transforms.trace.Initial;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;

public class ProofGoal implements IProofBranch, ICLFormulaProvider {
	private IGoalContainer parent;
	private List<ProofHypothesis> branchHypotheses;
	private boolean branchHypothesesInherited = true;
	private Transforms goal;
	private TypingContext typingContext;
	private VerificationResult status;
	private boolean closed = false;
	private boolean stuck = false;
	private boolean contradictionTest = false;
	private String name;
	private final GoalProvenance provenance;

	public ProofGoal(IGoalContainer po, Transforms goal, String name) {
		parent = po;
		this.goal = goal;
		typingContext = po.getTypingContext();
		this.name = name;
		provenance = Initial.INSTANCE;

		checkClosed();
	}

	public ProofGoal(IGoalContainer po, Transforms goal, IProofBranch parent, GoalProvenance provenance) {
		this.parent = po;
		this.goal = goal;
		typingContext = parent.getTypingContext();
		name = parent.getName() + "/?";
		this.provenance = provenance;

		if (parent instanceof ProofGoal) {
			branchHypotheses = parent.getHypothesis();
		}

		checkClosed();
	}

	public boolean isContradictionTest() {
		return contradictionTest;
	}

	public void setContradictionTest(boolean contradictionTest) {
		this.contradictionTest = contradictionTest;
	}

	public void setParent(IGoalContainer po) {
		parent = po;
	}

	public GoalProvenance getProvenance() {
		return provenance;
	}

	public void purge() {
		if (closed) {
			branchHypotheses = null;
			goal = null;
		} else {
			goal.purge();
		}
	}

	/**
	 * Collects all values matching given template from the branch hypotheses
	 * 
	 * @param template
	 * @return
	 */
	public Map<String, Collection<CLForeignLocationExpression>> harvest(CLExpression template) {
		final Map<String, Collection<CLForeignLocationExpression>> result = new HashMap<>();
		for (final ProofHypothesis hyp : this) {
			final Map<String, CLExpression> map = hyp.getFormula().match(template, getTypingContext());
			if (map != null) {
				for (final String key : map.keySet()) {
					harvesterInsert(result, hyp.getFormula().getLocation(), key, map.get(key));
				}
			}
		}
		return result;
	}

	/**
	 * Finds a value defined by the passed template within the branch hypotheses.
	 * Any one matching would do.
	 * 
	 * @param template
	 * @return
	 */
	public CLExpression findAnyOne(CLExpression template) {
		for (final ProofHypothesis hyp : this) {
			final Map<String, CLExpression> map = hyp.getFormula().match(template, getTypingContext());
			if (map != null && map.size() > 0) {
				return map.get(map.keySet().iterator().next());
			}
		}
		return null;
	}

	public Map<String, CLExpression> findAny(CLExpression template) {
		for (final ProofHypothesis hyp : this) {
			final Map<String, CLExpression> map = hyp.getFormula().match(template, getTypingContext());
			if (map != null && map.size() > 0) {
				return map;
			}
		}
		return null;
	}

	public boolean containsHypothesis(CLExpression test) {
		for (final ProofHypothesis hyp : this) {
			if (hyp.getFormula().equals(test)) {
				return true;
			}
		}

		return false;
	}

	private void harvesterInsert(Map<String, Collection<CLForeignLocationExpression>> result, SourceLocation location, String key,
			CLExpression exp) {
		Collection<CLForeignLocationExpression> collection = result.get(key);
		if (collection == null) {
			collection = new ArrayList<>(2);
			result.put(key, collection);
		}

		CLForeignLocationExpression ee = new CLForeignLocationExpression(exp, location);

		if (!collection.contains(ee)) {
			collection.add(ee);
		}
	}

	protected void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setGoal(Transforms goal) {
		this.goal = goal;
	}

//	public void computeRank() {
//		for (ProofHypothesis hyp : getHypothesis())
//			hyp.computeRank();
//	}

	private void checkClosed() {
		if (isContradictionTest()) {
			closed = stuck && !goal.getFormula().equals(CLAtomicExpression.TRUE);
			status = new VerificationResult(closed ? VerificationResult.RESULT.VALID : VerificationResult.RESULT.INVALID, "internal");
		} else {
			if (status != null && status.isDefinite()) {
				closed = true;
				return;
			}

			if (goal.getFormula().equals(CLAtomicExpression.TRUE)) {
				status = new VerificationResult(VerificationResult.RESULT.VALID, "internal");
				closed = true;
			}
		}

	}

	@Override
	public TypingContext getTypingContext() {
		return typingContext;
	}

	public void setTypingContext(TypingContext typingContext) {
		this.typingContext = typingContext;
	}

	@Override
	public CLExpression getFormula() {
		return goal.getFormula();
	}

	public Transforms getOrigin() {
		return goal.getOrigin();
	}

	public IGoalContainer getGoalContainer() {
		return parent;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProofHypothesis> getHypothesis() {
		return branchHypotheses == null ? Collections.EMPTY_LIST : branchHypotheses;
	}

	public void modifyHypothesis() {
		if (branchHypotheses == null || branchHypotheses == Collections.EMPTY_LIST || branchHypothesesInherited) {
			final List<ProofHypothesis> _branchHypotheses = new ArrayList<>();
			if (branchHypotheses != null) {
				_branchHypotheses.addAll(branchHypotheses);
			}

			branchHypotheses = _branchHypotheses;
			branchHypothesesInherited = false;
		}
	}

	public ProofHypothesis addHypothesis(ProofHypothesis hyp) {
		modifyHypothesis();
		branchHypotheses.add(hyp);
		return hyp;
	}

	public ProofHypothesis addHypothesis(CLExpression pred, String transformid, IGoalContainer mpo, String id) {
		return addHypothesis(new ProofHypothesis(this, new Transforms(null, pred, transformid, mpo), id));
	}

	public ProofHypothesis addHypothesis(CLExpression pred, Transforms origin, String transformid, IGoalContainer mpo, String id) {
		return addHypothesis(new ProofHypothesis(this, new Transforms(origin, pred, transformid, mpo), id));
	}

	public Transforms getGoal() {
		return goal;
	}

	public boolean isClosed() {
		return closed;
	}

	public VerificationResult getStatus() {
		return status;
	}

	public void setStatus(VerificationResult status) {
		this.status = status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<ProofHypothesis> iterator() {
		return new MultiIterator(parent.getHypothesis(), getHypothesis());
	}

	public void setStuck() {
		stuck = true;
	}

	public boolean isStuck() {
		return stuck;
	}

	public void revert(int stage) {
		Transforms c = goal;

		while (c.getStage() > stage && c.getOrigin() != null) {
			c = c.getOrigin();
		}

		if (c != goal) {
			closed = false;
			stuck = false;
			status = null;
		}

		goal = c;
	}

}
