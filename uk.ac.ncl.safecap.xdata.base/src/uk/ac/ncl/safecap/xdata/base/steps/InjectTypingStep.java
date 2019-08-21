package uk.ac.ncl.safecap.xdata.base.steps;

import uk.ac.ncl.safecap.xmldata.DataConfigurationException;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataException;

public class InjectTypingStep {

	public static void execute(DataContext data, DataContext other) throws DataConfigurationException, DataException {

		if (data == null || other == null) {
			return;
		}

		data.getTypeRegistry().inject(other.getTypeRegistry());
		data.build();

	}
}
