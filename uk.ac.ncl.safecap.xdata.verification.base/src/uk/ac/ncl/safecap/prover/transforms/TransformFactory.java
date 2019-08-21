package uk.ac.ncl.safecap.prover.transforms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLRewriteRule;
import uk.ac.ncl.safecap.prover.core.BundledRewriteRules;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.TacticPackage;
import uk.ac.ncl.safecap.prover.core.Transforms;
import uk.ac.ncl.safecap.xdata.provers.ConfiguredGoalTransform;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.rules.IProverFragments;

public class TransformFactory {
	private List<GoalTransform> goalTransforms;
	private List<GoalTransform> goalTransformsManual;
	private final TacticPackage tacticPackage;
	private final String[] autoTransformNames;
	private final RootCatalog root;
	private final IProverFragments fragments;

	public TransformFactory(RootCatalog root, ManagedProofObligation mpo) {
		tacticPackage = new TacticPackage(SDAUtils.getDataContext(root));
		this.root = root;
		fragments = mpo.getProverFragments();
		populateGoalTransforms();

		autoTransformNames = new String[goalTransforms.size()];
		int i = 0;
		for (final GoalTransform gt : goalTransforms) {
			autoTransformNames[i] = gt.getName();
			i++;
		}

		Arrays.sort(autoTransformNames);
	}

	public String[] getAutoTransformNames() {
		return autoTransformNames;
	}

	public TacticPackage getTacticPackage() {
		return tacticPackage;
	}

	public List<GoalTransform> getGoalTransforms() {
		return goalTransforms;
	}

	public List<GoalTransform> getTransformsFor(ProofGoal goal) {
		final List<GoalTransform> result = new ArrayList<>();
		for (final GoalTransform gt : goalTransforms) {
			final long start = System.currentTimeMillis();
			if (gt.isApplicable(goal) != null) {
				result.add(gt);
			}
			final long end = System.currentTimeMillis();
			if (end - start > 10) {
				System.out.println("Slow tactic applicability: " + gt.getName() + " - " + (end - start));
			}
		}

		for (final GoalTransform gt : goalTransformsManual) {
			if (gt.isApplicable(goal) != null) {
				result.add(gt);
			}
		}

		return result;
	}

	public ConfiguredGoalTransform getAutoTransformsFor(ProofGoal goal) {
		for (final GoalTransform gt : goalTransforms) {
			final Object configuration = gt.isApplicable(goal);
			if (configuration != null && (gt.isRepeatable() || gt.maxRepeatSteps() <= usedStepsBefore(gt, goal))) {
				return new ConfiguredGoalTransform(gt, configuration);
			}
		}

		return null;
	}

	private int usedStepsBefore(GoalTransform gt, ProofGoal goal) {

		Transforms s = goal.getGoal();
		int i = 0;
		while (s != null) {
			if (gt.getName().equals(s.getTransform())) {
				return i;
			}

			s = s.getOrigin();
			i++;
		}

		return Integer.MAX_VALUE;
	}

	private void populateGoalTransforms() {
		goalTransforms = new ArrayList<>();
		goalTransformsManual = new ArrayList<>();

		goalTransforms.add(new GoalInHyp(tacticPackage));
		goalTransforms.add(new ContradictionInHyp(tacticPackage));
		goalTransforms.add(new ConjunctionInHyp(tacticPackage));

		goalTransforms.add(new ForallInstantiation(tacticPackage));

		goalTransforms.add(new ConstantEquality(tacticPackage));
		goalTransforms.add(new PrimedEquality(tacticPackage));
		goalTransforms.add(new SetMinusPairToEqlHyp(tacticPackage));

		goalTransforms.add(new Simplify(tacticPackage));
		goalTransforms.add(new Fold(tacticPackage));

		// goalTransforms.add(new FilterHypothesis(tacticPackage, 0.1));

		goalTransforms.add(new OverrideElimination(tacticPackage));
		goalTransforms.add(new CaseFromOverride(tacticPackage));
		goalTransforms.add(new ImplicationElimination(tacticPackage));
		goalTransforms.add(new SubseteqToPredicate(tacticPackage));
		goalTransforms.add(new IntersectionToPredicate(tacticPackage));
		goalTransforms.add(new ImplicationDisjunction(tacticPackage));
		goalTransforms.add(new SplitConjunct(tacticPackage));
		goalTransforms.add(new SplitUnionInGoal(tacticPackage));
		goalTransforms.add(new CaseOvrEquality(tacticPackage));
		goalTransforms.add(new SplitMemInHyp(tacticPackage));
		goalTransforms.add(new SplitSetCMemInHyp(tacticPackage));
		goalTransforms.add(new SetDifferenceMembershipInGoal(tacticPackage));

		// add bundled rewrite rules
		final BundledRewriteRules bundled = new BundledRewriteRules(tacticPackage.getContext().getRootRuntimeContext());
		for (final GoalTransform t : bundled.getTransforms(tacticPackage)) {
			goalTransforms.add(t);
		}

		// add project rewrite rules
		if (!root.getBuiltProveFragments().empty() && root.getBuiltProveFragments().content().getRewrites() != null) {
			for (final CLRewriteRule rr : root.getBuiltProveFragments().content().getRewrites()) {
				goalTransforms.add(new CustomRewrite(tacticPackage, rr));
			}
		}

		// add project inference rules
		if (!root.getBuiltProveFragments().empty() && root.getBuiltProveFragments().content().getRules() != null) {
			for (final UserInferenceRule rr : root.getBuiltProveFragments().content().getRules()) {
				goalTransforms.add(new CustomInference(tacticPackage, rr));
			}
		}

		// add local rewrite rules
		if (fragments != null && !fragments.getBuiltProveFragments().empty()
				&& fragments.getBuiltProveFragments().content().getRewrites() != null) {
			for (final CLRewriteRule rr : fragments.getBuiltProveFragments().content().getRewrites()) {
				goalTransforms.add(new CustomRewrite(tacticPackage, rr));
			}
		}

		// add project inference rules
		if (fragments != null && !fragments.getBuiltProveFragments().empty()
				&& fragments.getBuiltProveFragments().content().getRules() != null) {
			for (final UserInferenceRule rr : fragments.getBuiltProveFragments().content().getRules()) {
				goalTransforms.add(new CustomInference(tacticPackage, rr));
			}
		}

		goalTransforms.add(new SplitMemInGoal(tacticPackage));

		goalTransforms.add(new ExistentialToDisjunction(tacticPackage));
		goalTransforms.add(new SplitDisjunctionInGoal(tacticPackage));
		goalTransforms.add(new DisjunctionInHyp(tacticPackage));

	}

}
