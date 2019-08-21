package uk.ac.ncl.safecap.xdata.verification.services;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.Status;
import org.eclipse.sapphire.services.ValidationService;

import uk.ac.ncl.safecap.xdata.verification.IKeyCarrier;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.Verifiable;
import uk.ac.ncl.safecap.xdata.verification.core.ElementVisitor;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;

public class KeyValidationService extends ValidationService {

	@Override
	protected Status compute() {
		final Value<?> value = context(Element.class).property(context(ValueProperty.class));

		final IKeyCarrier el = (IKeyCarrier) value.element();

		if (el.getKey().empty() || !(el.root() instanceof RootCatalog)) {
			return Status.createOkStatus();
		}

		final RootCatalog catalog = (RootCatalog) el.root();

		final Object result = VisitorUtils.visitAllProperties(catalog, new ElementVisitor<Verifiable>() {
			@Override
			public Object visit(Verifiable element, Object userData) {
				if (element != el && element instanceof IKeyCarrier) {
					final IKeyCarrier conj = (IKeyCarrier) element;
					if (!conj.getKey().empty() && el.getKey().content().equals(conj.getKey().content())) {
						return element;
					}
				}
				return null;
			}

		}, null);

		if (result != null) {
			return Status.createErrorStatus("Duplicate key label");
		}

		return Status.createOkStatus();
	}

}
