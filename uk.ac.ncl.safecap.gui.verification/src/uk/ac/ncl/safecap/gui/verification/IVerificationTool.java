package uk.ac.ncl.safecap.gui.verification;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;

import uk.ac.ncl.safecap.scripting.verification.IVerificationStatus;

public interface IVerificationTool {
	public static abstract class VerificationResult {
		public static final int RESULT_OK = 0, RESULT_MAYBE = 1, RESULT_FAIL = 2, RESULT_NONE = -1;

		/**
		 * contains one of RESULT_OK, RESULT_MAYBE, or RESULT_FAIL
		 */
		public int result;
		public boolean report;

		public VerificationResult(int result, boolean report) {
			super();
			this.result = result;
			this.report = report;
		}

		public abstract void showResultDialog(Shell shell);
	}

	String getName();

	VerificationResult run(EObject target, VerificationContext context, IVerificationStatus status) throws InterruptedException;

	boolean exists();
}
