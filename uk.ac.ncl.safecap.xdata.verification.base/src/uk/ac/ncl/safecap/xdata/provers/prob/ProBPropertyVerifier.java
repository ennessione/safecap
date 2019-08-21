package uk.ac.ncl.safecap.xdata.provers.prob;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.provers.IPropertyTranslator;
import uk.ac.ncl.safecap.xdata.provers.Properties;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;
import uk.ac.ncl.safecap.xdata.verification.core.IPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;

public class ProBPropertyVerifier implements IPropertyVerifier {
	private IProBTool tool;
	private boolean obfuscate = false;
	private boolean asAssertion = false;
	private int index = 0;

//	public static final ProBPropertyVerifier INSTANCE_OLD =
//			new ProBPropertyVerifier(
//					new ProBTool(20),
//					false,
//					false,
//					0
//					);

	public static final ProBPropertyVerifier INSTANCE = new ProBPropertyVerifier(new ProBTool2(20), false, false, 0);

	public ProBPropertyVerifier(IProBTool tool, boolean obfuscate, boolean asAssertion, int index) {
		this.tool = tool;
		this.obfuscate = obfuscate;
		this.asAssertion = asAssertion;
		this.index = index;
	}

	@Override
	public VerificationResult verify(ICondition condition, IVerificationProgressMonitor monitor, boolean negated) {
		final long start = System.currentTimeMillis();
		try {
			if (condition.getGoal() == null) {
				return new VerificationResult(VerificationResult.RESULT.FAILED, "not built");
			}

			if (condition.getGoal().getType() == null) {
				return new VerificationResult(VerificationResult.RESULT.FAILED, "not type checked");
			}

			final IPropertyTranslator ptran = new ProBPropertyTranslator(condition.getContext(), obfuscate, index, asAssertion);
			final String model = ptran.translateProperty(condition, negated);
			if (model == null) {
				return new VerificationResult(VerificationResult.RESULT.FAILED, "translation failed");
			}

			if (Properties.DEBUG) {
				System.out.println("model:" + model);
			}

			tool = tool.getInstance(model);
			final VerificationResult.RESULT result = tool.check(monitor);
			final VerificationResult report = new VerificationResult(result);
			report.setProver("prob");
			report.setTime(System.currentTimeMillis() - start);
			return report;
		} catch (final Throwable e) {
			final VerificationResult report = new VerificationResult(VerificationResult.RESULT.FAILED, e);
			report.setProver("prob");
			report.setTime(System.currentTimeMillis() - start);
			if (Display.getCurrent() != null) {
				MessageDialog.openError(null, "Prover call failed", e.getMessage());
			}
			return report;
		}
	}

}
