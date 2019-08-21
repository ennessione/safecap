package uk.ac.ncl.safecap.prover.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLRewriteRule;
import uk.ac.ncl.safecap.prover.transforms.CustomRewrite;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.VerificationBasePlugin;
import uk.ac.ncl.safecap.xdata.verification.registry.ProveFragmentsUtils;

public class BundledRewriteRules implements ITransformProvider {
	private static final String BUNDLED_REWRITE = "bundled.rewrite";
	private List<CLRewriteRule> rules = null;
	private final SDARuntimeExecutionContext context;

	public BundledRewriteRules(SDARuntimeExecutionContext context) {
		this.context = context;
		load();
	}

	private void load() {
		if (rules == null) {
			rules = new ArrayList<>();
			loadBundled();
		}
	}

	private void loadBundled() {
		final URL bundled = VerificationBasePlugin.getLibFile(BUNDLED_REWRITE);
		if (bundled == null) {
			return;
		}
		try {
			final BufferedReader in = new BufferedReader(new InputStreamReader(bundled.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				rules.add(ProveFragmentsUtils.buildRewriteRule(context, inputLine));
			}

			in.close();
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public Collection<GoalTransform> getTransforms(TacticPackage tacticPackage) {
		final Collection<GoalTransform> result = new ArrayList<>(rules.size());
		for (final CLRewriteRule r : rules) {
			result.add(new CustomRewrite(tacticPackage, r));
		}
		return result;
	}

}
