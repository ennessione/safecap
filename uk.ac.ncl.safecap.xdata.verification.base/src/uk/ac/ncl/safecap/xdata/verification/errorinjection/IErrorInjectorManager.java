package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.List;

import uk.ac.ncl.safecap.xmldata.IXFunction;

public interface IErrorInjectorManager extends IErrorInjector {

	boolean nextExperimentStep();

	List<IXFunction> getFunctions();

	int getPoints();

	IErrorInjector getCurrentInjection();
}
