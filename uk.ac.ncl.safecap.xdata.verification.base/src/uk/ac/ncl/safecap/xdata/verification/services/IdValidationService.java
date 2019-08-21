package uk.ac.ncl.safecap.xdata.verification.services;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.Status;
import org.eclipse.sapphire.services.ValidationService;

import uk.ac.ncl.safecap.xdata.verification.INamed;
import uk.ac.ncl.safecap.xdata.verification.IdentifierCategory;
import uk.ac.ncl.safecap.xdata.verification.IdentifierDefinition;
import uk.ac.ncl.safecap.xdata.verification.PredicateCategory;
import uk.ac.ncl.safecap.xdata.verification.PredicateDefinition;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.TransitionCategory;
import uk.ac.ncl.safecap.xdata.verification.TransitionDefinition;
import uk.ac.ncl.safecap.xdata.verification.core.ElementVisitor;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;

public class IdValidationService extends ValidationService {

	@SuppressWarnings("unchecked")
	@Override
	protected Status compute() {
		final Value<?> value = context(Element.class).property(context(ValueProperty.class));
		final INamed el = (INamed) value.element();

		if (el.getId().empty() || !(el.root() instanceof RootCatalog)) {
			return Status.createOkStatus();
		}

		final RootCatalog catalog = (RootCatalog) el.root();

		if (el instanceof IdentifierCategory || el instanceof PredicateCategory || el instanceof TransitionCategory) {
			if (el.parent() instanceof ElementList) {
				final ElementList<INamed> parentlist = (ElementList<INamed>) el.parent();
				for (final INamed named : parentlist) {
					if (named != el && !named.getId().empty() && named.getId().content().equals(el.getId().content())) {
						return Status.createErrorStatus("Duplicate category label");
					}
				}
			}
		} else if (el instanceof IdentifierDefinition) {
			final IdentifierDefinition idt = (IdentifierDefinition) el;
			final Object result = VisitorUtils.visitAllIdentifiers(catalog, new ElementVisitor<IdentifierDefinition>() {
				@Override
				public Object visit(IdentifierDefinition element, Object userData) {
					if (!element.getId().empty() && element.getId().content().equals(idt.getId().content()) && element != idt) {
						return element;
					} else {
						return null;
					}
				}

			}, null);

			if (result != null) {
				// try {
				// IFile file =
				// VerificationBasePlugin.getVCatalogRegistry().resolveByCatalogToFile(catalog);
				// IResource resource = ResourcesPlugin.getWorkspace().getRoot()
				// .findMember(file.getFullPath());
				// IMarker marker =
				// resource.createMarker("uk.ac.ncl.safecap.xdata.verification.marker");
				// marker.setAttribute(IMarker.SEVERITY,
				// IMarker.SEVERITY_ERROR);
				// marker.setAttribute(IMarker.MESSAGE,
				// "Duplicate identifier definition");
				// marker.setAttribute(IMarker.LOCATION,
				// SDAUtils.fullElementName(el));
				// } catch (CoreException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				return Status.createErrorStatus("Duplicate identifier definition");
			}
		} else if (el instanceof PredicateDefinition || el instanceof TransitionDefinition) {
			if (el.parent() instanceof ElementList) {
				final ElementList<INamed> parentlist = (ElementList<INamed>) el.parent();
				for (final INamed named : parentlist) {
					if (named != el && !named.getId().empty() && named.getId().content().equals(el.getId().content())) {
						return Status.createErrorStatus("Duplicate definition label");
					}
				}
			}
		}

		return Status.createOkStatus();
	}

}
