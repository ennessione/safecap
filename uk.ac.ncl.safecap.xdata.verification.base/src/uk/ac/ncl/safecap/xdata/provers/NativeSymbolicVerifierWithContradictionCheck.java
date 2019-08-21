package uk.ac.ncl.safecap.xdata.provers;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.Transforms;
import uk.ac.ncl.safecap.xdata.verification.core.IPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.ProxyPOCondition;

public class NativeSymbolicVerifierWithContradictionCheck implements IPropertyVerifier {
	public static final NativeSymbolicVerifierWithContradictionCheck INSTANCE = new NativeSymbolicVerifierWithContradictionCheck();

	private static final String NAME = "symb_contr";
	
	
	@Override
	public VerificationResult verify(ICondition condition, IVerificationProgressMonitor monitor, boolean negated) {
		final ManagedProofObligation po = condition.getManagedProofObligation();
		
		if (po != null && po.getGoals() != null && po.getGoals().size() == 1) {
			ProofGoal pg = po.getGoals().get(0);
			CLExpression goal = pg.getFormula();
			CLExpression negatedGoal = CLUtils.negate(goal);
			ManagedProofObligation poe = new ManagedProofObligation(po);
			poe.getGoals().clear();
			poe.addGoal(new ProofGoal(po, new Transforms(negatedGoal), "C"));
			ProxyPOCondition pc = new ProxyPOCondition(condition, poe);			
			
			VerificationResult vr1 = NativeSymbolicVerifier.INSTANCE.verify(condition, monitor, negated);
			if (!vr1.isDefinite())
				return vr1;

			VerificationResult vr2 = NativeSymbolicVerifier.INSTANCE.verify(pc, monitor, negated);
			vr1.setProperty("contradiction", vr2.isValid());
			return vr1;
		}
		
		return NativeSymbolicVerifier.FAILED;
	}

}
