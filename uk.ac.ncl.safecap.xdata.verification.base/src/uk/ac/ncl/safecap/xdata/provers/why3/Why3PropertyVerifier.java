package uk.ac.ncl.safecap.xdata.provers.why3;

import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.provers.IPropertyTranslator;
import uk.ac.ncl.safecap.xdata.provers.Properties;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;
import uk.ac.ncl.safecap.xdata.verification.core.IPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;

public class Why3PropertyVerifier implements IPropertyVerifier {
	public static final Why3PropertyVerifier INSTANCE = new Why3PropertyVerifier();

	@Override
	public VerificationResult verify(ICondition condition, IVerificationProgressMonitor monitor, boolean negated) {
		final long start = System.currentTimeMillis();
		try {
			final IPropertyTranslator ptran = new Why3PropertyTranslator(condition.getContext());
			final String model = ptran.translateProperty(condition, negated);
			if (Properties.DEBUG) {
				System.out.println("why3 model:" + model);
			}
			final Why3Tool etool = new Why3Tool(model, "Spass", 5);
			final VerificationResult.RESULT result = etool.check(monitor);
			final VerificationResult report = new VerificationResult(result);
			report.setProver("why3");
			report.setTime(System.currentTimeMillis() - start);
			return report;
		} catch (final Throwable e) {
			e.printStackTrace();

			final VerificationResult report = new VerificationResult(VerificationResult.RESULT.FAILED);
			report.setProver("why3");
			report.setTime(System.currentTimeMillis() - start);
			return report;
		}
	}

}
