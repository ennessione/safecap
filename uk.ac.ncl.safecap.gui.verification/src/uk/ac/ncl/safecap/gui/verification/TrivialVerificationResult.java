package uk.ac.ncl.safecap.gui.verification;

import org.eclipse.swt.widgets.Shell;

public class TrivialVerificationResult extends IVerificationTool.VerificationResult {
	public TrivialVerificationResult(int result) {
		super(result, false);
	}

	@Override
	public void showResultDialog(Shell shell) {
	}
}
