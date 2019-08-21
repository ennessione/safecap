package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import uk.ac.ncl.safecap.xdata.base.tablesmodel.TablesModel;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;

public class InjectionUtils {

	public static ExperimentResults executeColumnInjector(RootCatalog catalog, TablesModel model, IVerificationProgressMonitor monitor) {
		final ExperimentResults results = new ExperimentResults();
		try {
			final EIColumnModelExperiment experiment = new EIColumnModelExperiment(catalog, model);
			if (experiment.getConjectures().size() != 0) {
				final EIResult result = experiment.execute(monitor);
				results.add(result);
			}
		} catch (final Throwable e) {
			results.add(new EIResult(e.getMessage()));
			e.printStackTrace();
		}

		return results;
	}

}
