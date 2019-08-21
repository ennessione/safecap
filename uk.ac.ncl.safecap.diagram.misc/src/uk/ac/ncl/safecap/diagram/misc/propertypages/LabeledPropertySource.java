package uk.ac.ncl.safecap.diagram.misc.propertypages;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import safecap.Labeled;

public class LabeledPropertySource extends ExtensiblePropertySource {
	protected Labeled object;

	public LabeledPropertySource(Labeled object) {
		super(object);
		this.object = object;
	}

	@Override
	public void getDescriptors(List<IPropertyDescriptor> list) {
		list.add(new TextPropertyDescriptor("name", "[Identifier]"));
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.toString().equals("name")) {
			return object.getLabel() != null ? object.getLabel() : "";
		}

		return null;
	}

	@Override
	public boolean setValueCommand(Object id, Object value) {
		if (id.toString().equals("name")) {
			object.setLabel(value.toString());
			return true;
		}
		return false;
	}

	@Override
	public Object getEditableValue() {
		return object;
	}
}
