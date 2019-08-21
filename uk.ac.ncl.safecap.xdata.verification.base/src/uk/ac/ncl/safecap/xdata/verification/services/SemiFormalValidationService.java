package uk.ac.ncl.safecap.xdata.verification.services;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.Status;
import org.eclipse.sapphire.services.ValidationService;

import uk.ac.ncl.safecap.xdata.verification.ISemiFormal;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.core.SemiFormalState;

public class SemiFormalValidationService extends ValidationService {

	@Override
	protected Status compute() {
		SDAContext dataContext = null;
		try {
			final Value<?> value = context(Element.class).property(context(ValueProperty.class));
			final ISemiFormal el = (ISemiFormal) value.element();

			if (el.getSemiFormal().empty() || !(el.root() instanceof RootCatalog)) {
				return Status.createOkStatus();
			}

			final RootCatalog catalog = (RootCatalog) el.root();
			dataContext = SDAUtils.getDataContext(catalog);
			final SemiFormalState state = new SemiFormalState(dataContext, el.getSemiFormal().content());

			if (!state.getErrors().isEmpty()) {
				return Status.createErrorStatus(state.getErrors().toString());
			}

			if (!state.getMissingData().isEmpty()) {
				return Status.createWarningStatus("Missing data " + state.getMissingData().toString());
			}

			return Status.createOkStatus();
		} catch (final Throwable e) {
			final StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			final String info = errors.toString();
			return Status.createErrorStatus("Failed processing semi-formal definition (ctx= " + dataContext + "):\n " + info);
		}
	}
}
