package uk.ac.ncl.safecap.scripting.verification;

public interface IVerificationStatus {
	void begin();

	void begin(int totalWork);

	void progress(int work);

	void end();

	void setStatusMessage(String message);

	boolean isTerminated();

}
