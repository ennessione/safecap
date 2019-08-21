package uk.ac.ncl.safecap.xdata.verification.report;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLForeignLocationExpression;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class SubInvariantReportIssue {
	public ProofGoal goal;
	public String kind;
	public Set<String> elements;
	public ProofStateExplainer goalState;

	public SubInvariantReportIssue(ProofGoal goal, String kind) {
		this.kind = kind;
		this.goal = goal;
		elements = new HashSet<>();
	}

	public Collection<String> getAuxElements(final SDARuntimeExecutionContext model, String kind2) {
		try {
			final ProofStateExplainer state = getGoalState();
			if (state != null) {
				final Collection<CLForeignLocationExpression> r = state.state.get(kind2);
				if (r != null) {
					final Collection<String> result = new HashSet<>();
					for (final CLForeignLocationExpression ee : r) {
						CLExpression e = ee.getExpression();
						if (e.getValue(model) != null) {
							result.add(e.getValue(model).toString());
						}
					}
					return result;
				}
			}
		} catch (final Throwable e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public ProofStateExplainer getGoalState() {
		if (goalState == null) {
			goalState = new ProofStateExplainer(goal);
		}
		return goalState;
	}

	void addElement(String element) {
		elements.add(element);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (elements == null ? 0 : elements.hashCode());
		result = prime * result + (kind == null ? 0 : kind.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SubInvariantReportIssue other = (SubInvariantReportIssue) obj;
		if (elements == null) {
			if (other.elements != null) {
				return false;
			}
		} else if (!elements.equals(other.elements)) {
			return false;
		}
		if (kind == null) {
			if (other.kind != null) {
				return false;
			}
		} else if (!kind.equals(other.kind)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Issue [kind=" + kind + ", elements=" + elements + "]";
	}

}