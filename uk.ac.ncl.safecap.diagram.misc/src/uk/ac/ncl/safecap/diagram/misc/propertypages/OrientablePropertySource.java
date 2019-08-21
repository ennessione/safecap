package uk.ac.ncl.safecap.diagram.misc.propertypages;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import safecap.Labeled;
import safecap.commentary.OrientableCommentary;

public class OrientablePropertySource extends ExtensiblePropertySource {
	protected OrientableCommentary node;

	public OrientablePropertySource(final OrientableCommentary node) {
		super(node);
		this.node = node;
	}

	@Override
	public void getDescriptors(List<IPropertyDescriptor> list) {
		if (node instanceof Labeled) {
			list.add(new TextPropertyDescriptor("name", "Label"));
		}

		list.add(new TextPropertyDescriptor("angle", "Angle"));
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.toString().equals("name")) {
			return ((Labeled) node).getLabel() != null ? ((Labeled) node).getLabel() : "";
		} else if (id.toString().equals("angle")) {
			return String.valueOf(node.getAngle());
		}

		return null;
	}

	@Override
	public boolean setValueCommand(Object id, Object value) {
		if (id.toString().equals("name")) {
			((Labeled) node).setLabel(value.toString());
			return true;
		} else if (id.toString().equals("angle")) {
			final String s = value.toString();
			final int a = Integer.parseInt(s);
			node.setAngle(a);
		}
		return false;
	}

	@Override
	public Object getEditableValue() {
		return node;
	}

}
