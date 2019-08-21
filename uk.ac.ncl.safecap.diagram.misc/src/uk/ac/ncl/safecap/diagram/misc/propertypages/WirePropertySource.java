package uk.ac.ncl.safecap.diagram.misc.propertypages;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import safecap.trackside.Wire;

public class WirePropertySource extends ExtensiblePropertySource {
	protected Wire wire;

	public WirePropertySource(final Wire limit) {
		super(limit);
		wire = limit;
	}

	@Override
	public void getDescriptors(List<IPropertyDescriptor> list) {
		list.add(new TextPropertyDescriptor("offset", "Offset"));
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.toString().equals("offset")) {
			return "" + wire.getOffset();
		} else {
			return null;
		}
	}

	@Override
	public boolean setValueCommand(Object id, Object value) {
		if (value == null || value.toString().length() == 0) {
			return true;
		}

		if (id.toString().equals("offset")) {
			try {
				final String s = value.toString();
				wire.setOffset(Integer.parseInt(s));
			} catch (final NumberFormatException e) {
				// ignore
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object getEditableValue() {
		return wire;
	}

}
