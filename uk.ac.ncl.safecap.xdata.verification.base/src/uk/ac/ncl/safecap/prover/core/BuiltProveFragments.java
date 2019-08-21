package uk.ac.ncl.safecap.prover.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLRewriteRule;
import uk.ac.ncl.safecap.prover.transforms.CustomRewrite;
import uk.ac.ncl.safecap.prover.transforms.UserInferenceRule;

public class BuiltProveFragments implements ITransformProvider {
	private List<CLRewriteRule> rewrites = null;
	private List<UserInferenceRule> rules = null;

	public List<CLRewriteRule> getRewrites() {
		return rewrites;
	}

	public void setRewrites(List<CLRewriteRule> rewrites) {
		this.rewrites = rewrites;
	}

	public List<UserInferenceRule> getRules() {
		return rules;
	}

	public void setRules(List<UserInferenceRule> rules) {
		this.rules = rules;
	}

	@Override
	public Collection<GoalTransform> getTransforms(TacticPackage tacticPackage) {
		if (rewrites == null) {
			return Collections.emptySet();
		}
		final Collection<GoalTransform> result = new ArrayList<>(rewrites.size());
		for (final CLRewriteRule r : rewrites) {
			result.add(new CustomRewrite(tacticPackage, r));
		}
		return result;
	}

}
