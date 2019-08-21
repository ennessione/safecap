package uk.ac.ncl.safecap.xdata.verification.services;

import org.eclipse.sapphire.modeling.Status;
import org.eclipse.sapphire.services.ValidationService;

public class ModelLinkValidationService extends ValidationService {

	@Override
	protected Status compute() {

		System.out.println("++ Model link validation");
//
//		ModelReference modelReference = (ModelReference) context(Element.class);
//		modelReference.getResolved().clear();
//
//		RootCatalog catalog = (RootCatalog) modelReference.parent().element();
//
//		if (modelReference.getName().empty())
//			return Status.createOkStatus();
//
//		RootCatalog ref = XDataPlugin.getVCatalogRegistry().resolveByName(modelReference.getName().content());
//		if (ref == null)
//			return Status.createErrorStatus("Model \""+modelReference.getName().content() + "\" cannot be found");
//
//
//		ref.validation();
//		for(DataReference x: ref.getReferences())
//			x.validation();
//
//		for(ModelReference x: ref.getModels())
//			x.validation();
//
//		modelReference.setResolved(ref);
//
//		String selfname = XDataPlugin.getVCatalogRegistry().resolveByCatalog(catalog);
//		Set<String> nameset = new HashSet<String>();
//		nameset.add(selfname);
//		boolean dup = hasDuplicates(catalog, nameset);
//		if (dup) {
//			modelReference.getResolved().clear();
//			return Status.createErrorStatus("Duplicate/cyclic model link");
//		}
//
//
//		System.out.println("++ Starting builder");
//		XDataPlugin.getModelBuilder().build(ref);
//
//		if (!ref.getContext().empty()) {
//			SDAContext context = SDAUtils.getDataContextNoRefresh(catalog);
//			context.refreshReferences();
//			XDataPlugin.getModelBuilder().build(catalog);
//			System.out.println("++ Imported types " + ref.getContext().content().getRootRuntimeContext().getRootContext().toString());
//		} else {
//			return Status.createErrorStatus("Referenced model contains errors");
//		}

		return Status.createOkStatus();
	}

//	private boolean hasDuplicates(RootCatalog catalog, Set<String> names) {
//		for(ModelReference r: catalog.getModels()) {
//			if (!r.getName().empty()) {
//				if (names.contains(r.getName().content()))
//					return true;
//
//				names.add(r.getName().content());
//				if (!r.getResolved().empty()) {
//					boolean cycles = hasDuplicates(r.getResolved().content(), names);
//					if (cycles)
//						return true;
//				}
//			}
//		}
//
//		return false;
//	}

}
