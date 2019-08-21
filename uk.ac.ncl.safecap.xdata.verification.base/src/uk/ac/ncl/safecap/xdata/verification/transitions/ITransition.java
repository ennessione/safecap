package uk.ac.ncl.safecap.xdata.verification.transitions;

import java.util.List;

public interface ITransition {
	String getName();

	List<LocatedString> getPreconditions();

	List<LocatedString> getPostconditions();

	TransitionParsed getParsed();

	void setParsed(TransitionParsed parsed);

	boolean isValid();
}
