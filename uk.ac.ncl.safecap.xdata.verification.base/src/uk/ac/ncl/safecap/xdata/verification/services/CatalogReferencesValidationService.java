package uk.ac.ncl.safecap.xdata.verification.services;

import org.eclipse.sapphire.modeling.Status;
import org.eclipse.sapphire.services.ValidationService;

public class CatalogReferencesValidationService extends ValidationService {

	@Override
	protected Status compute() {
//		RootCatalog catalog = (RootCatalog) context(Element.class).root();
//		SDAContext context = SDAUtils.getDataContext(catalog);
//		try {
//			if (context != null)
//				context.refreshReferences(catalog);
//		} catch (SDAImportException e) {
//			return Status.createErrorStatus(e);
//		}
//
		return Status.createOkStatus();
	}
}
