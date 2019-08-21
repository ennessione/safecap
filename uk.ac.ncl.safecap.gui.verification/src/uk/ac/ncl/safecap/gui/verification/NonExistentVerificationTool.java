package uk.ac.ncl.safecap.gui.verification;

import org.eclipse.emf.ecore.EObject;

import uk.ac.ncl.safecap.scripting.verification.IVerificationStatus;

public class NonExistentVerificationTool implements IVerificationTool {
	private final String _name;

	public NonExistentVerificationTool(String name) {
		_name = name;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public VerificationResult run(EObject object, VerificationContext context, IVerificationStatus status) throws InterruptedException {
		status.begin();
		status.end();
		return new TextDialogVerificationResult(VerificationResult.RESULT_NONE, "Verification tool does not exist");
	}

	@Override
	public boolean exists() {
		return false;
	}

}
