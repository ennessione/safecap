package uk.ac.ncl.safecap.diagram.misc.propertypages;

import java.util.List;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import safecap.trackside.Signal;
import safecap.trackside.SignalType;

public class SignalPropertySource extends LabeledPropertySource {
	private static final String[] SIGNAL_TYPES;
	protected Signal signal;

	static {
		SIGNAL_TYPES = new String[SignalType.values().length];
		for (int i = 0; i < SIGNAL_TYPES.length; i++) {
			SIGNAL_TYPES[i] = SignalType.values()[i].getLiteral();
		}

	}

	public SignalPropertySource(final Signal signal) {
		super(signal);
		this.signal = signal;
	}

	@Override
	public void getDescriptors(List<IPropertyDescriptor> list) {
		list.add(new ComboBoxPropertyDescriptor("type", "Signal type", SIGNAL_TYPES));
		super.getDescriptors(list);
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.toString().equals("type")) {
			return signal.getType().ordinal();
		} else {
			return super.getPropertyValue(id);
		}
	}

	@Override
	public boolean setValueCommand(Object id, Object value) {
		if (value == null || value.toString().length() == 0) {
			return true;
		}

		if (id.toString().equals("type")) {
			final SignalType st = SignalType.get((Integer) value);
			if (st != null) {
				signal.setType(st);
			}
			return true;
		} else {
			return super.setValueCommand(id, value);
		}
	}

}
