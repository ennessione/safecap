package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLBase;
import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExistsExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLForallExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.CLQuantifiedExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.mb.ISolverCallback;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.sets.BMap;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;

public class PatchedCondition extends CLBase implements ICondition {
	private static final int MAX_VIOLATIONS = 40;
	private final ICondition condition;
	private VerificationResult result;
	private CLExpression goal;
	private boolean patchSuccessful = false;
	private List<Object> patchedValues;

	public PatchedCondition(ICondition condition) {
		this.condition = condition;
		goal = condition.getGoal();
		patch();
	}

	public ICondition getOriginalCondition() {
		return condition;
	}

	public List<Object> getPatchedValues() {
		return patchedValues;
	}

	public void patch() {
		final CLExpression expr = condition.getGoal();
		try {
			if (expr.getTag() == alphabet.B_FORALL) {
				final CLExpression patched = patchUniversal(condition);
				if (patched != null) {
					System.out.println("Patched " + goal + " to " + patched);
					goal = patched;
					patchSuccessful = true;
				}
			}
		} catch (final InvalidSetOpException e) {
			e.printStackTrace();
		}
	}

	public boolean isPatchSuccessful() {
		return patchSuccessful;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private CLExpression patchUniversal(ICondition condition) throws InvalidSetOpException {
		final CLExpression expr = condition.getGoal();
		assert expr.getTag() == alphabet.B_FORALL;

		final CLForallExpression original = (CLForallExpression) expr;
		final CLExistsExpression negated = (CLExistsExpression) CLUtils.negate(expr);
		if (negated.getBoundParameters().size() > 2) {
			return null;
		}
		patchedValues = new ArrayList<>();
		try {

			negated.getValueInterpreted(condition.getContext().getRootRuntimeContext(), new ISolverCallback() {
				@Override
				public void solved(Map<String, Object> solution, int index) {
					patchedValues.add(makeValue(negated, solution));
				}

			}, MAX_VIOLATIONS);

		} catch (final Throwable e) {
			e.printStackTrace();
			return null;
		}

		if (patchedValues.size() > 0 && patchedValues.size() < MAX_VIOLATIONS) {
			final CLExpression eset = CLUtils.makeLiteral(new BSet(patchedValues));
			final List<String> names = new ArrayList<>();
			for (final CLParameter p : negated.getBoundParameters()) {
				names.add(p.getName());
			}
			final CLExpression enames = CLUtils.listToMap(names);
			final CLExpression patch = notin(enames, eset);

			final CLExpression forall_body = original.getBody().deepCopy();
			
			CLUtils.transformBoundtoUnbound(forall_body);
			CLExpression patched_body;
			if (forall_body.getTag() == alphabet.OP_IMPLIES) {
				final CLBinaryExpression forall_body_binary = (CLBinaryExpression) forall_body;
				patched_body = limp(and(forall_body_binary.getLeft(), patch), forall_body_binary.getRight());
			} else {
				patched_body = limp(patch, forall_body);
			}

			final CLForallExpression patched = new CLForallExpression(original.getBoundParameters(), patched_body, original.getLocation());
			Set<String> x1 = patched.getFreeIdentifiers();
			Set<CLIdentifier> x2 = patched.getBody().getBoundIdentifiers();
			patched.type(getTypingContext(), CLTypeBool.INSTANCE);
			return patched;
		}

		return null;
	}

	private static Object makeValue(CLQuantifiedExpression q, Map<String, Object> map) {
		if (q.getBoundParameters().size() == 1) {
			return map.values().iterator().next();
		} else {
			final CLParameter f = q.getBoundParameters().byIndex(0);
			final CLParameter s = q.getBoundParameters().byIndex(1);
			return new BMap<>(map.get(f.getName()), map.get(s.getName()));
		}
	}

	@Override
	public RootCatalog getRoot() {
		return condition.getRoot();
	}

	@Override
	public String getName() {
		return condition.getName();
	}

	@Override
	public SDAContext getContext() {
		return condition.getContext();
	}

	@Override
	public CLExpression getHypothesis() {
		return condition.getHypothesis();
	}

	@Override
	public CLExpression getGoal() {
		return goal;
	}

	@Override
	public boolean isConjecture() {
		return condition.isConjecture();
	}

	@Override
	public void setResult(VerificationResult result) {
		this.result = result;
	}

	@Override
	public VerificationResult getResult() {
		return result;
	}

	@Override
	public ManagedProofObligation getManagedProofObligation() {
		return condition.getManagedProofObligation();
	}

	@Override
	public TypingContext getTypingContext() {
		return condition.getTypingContext();
	}

	@Override
	public String getShortName() {
		return condition.getShortName() + "/P";
	}
}
