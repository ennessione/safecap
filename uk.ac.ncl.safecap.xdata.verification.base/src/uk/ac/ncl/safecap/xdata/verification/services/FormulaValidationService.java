package uk.ac.ncl.safecap.xdata.verification.services;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.Event;
import org.eclipse.sapphire.Listener;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.Status;
import org.eclipse.sapphire.services.ValidationService;

import uk.ac.ncl.prime.sim.parser.FormulaSource;
import uk.ac.ncl.prime.sim.parser.IFormulaSourceListener;
import uk.ac.ncl.safecap.xdata.verification.IParseable;

public class FormulaValidationService extends ValidationService implements IFormulaSourceListener {

	@Override
	protected void initValidationService() {
		final Value<?> value = context(Element.class).property(context(ValueProperty.class));
		final IParseable fs = (IParseable) value.element();

		fs.getFormulaSource().attach(new Listener() {
			@Override
			public void handle(Event event) {
				_subscribe(value);
			}
		});
		_subscribe(value);
	}

	private void _subscribe(final Value<?> value) {
		final IParseable fs = (IParseable) value.element();
		if (!fs.getFormulaSource().empty()) {
			final FormulaSource ff = fs.getFormulaSource().content();
			ff.subscribe(this);
		}
	}

	@Override
	protected Status compute() {
		final Value<?> value = context(Element.class).property(context(ValueProperty.class));
		final IParseable fs = (IParseable) value.element();
		if (!fs.getFormulaSource().empty()) {
			final FormulaSource ff = fs.getFormulaSource().content();
			ff.subscribe(this);
			if (ff.hasErrors()) {
				return Status.createErrorStatus(ff.getErrors().toString());
			}

		}

		return Status.createOkStatus();
	}

	@Override
	public void react(FormulaSource source) {
		refresh();
	}

}
