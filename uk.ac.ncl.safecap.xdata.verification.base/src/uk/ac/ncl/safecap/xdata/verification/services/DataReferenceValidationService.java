package uk.ac.ncl.safecap.xdata.verification.services;

import org.eclipse.sapphire.modeling.Status;
import org.eclipse.sapphire.services.ValidationService;

public class DataReferenceValidationService extends ValidationService {

	@Override
	protected Status compute() {
		// DataReference dataReference = (DataReference) context(Element.class);
		// RootCatalog catalog = (RootCatalog) dataReference.parent().element();
		// SDAContext context = SDAUtils.getDataContext(catalog);

		return Status.createOkStatus();
	}

}
