package uk.ac.ncl.safecap.diagram.misc.propertypages;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public class EmptyPropertySource implements IPropertySource {

	private final Object object;

	public EmptyPropertySource(final Object o) {
		object = o;
	}

	@Override
	public Object getEditableValue() {
		return object;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {};
	}

	@Override
	public Object getPropertyValue(final Object id) {
		return null;
	}

	@Override
	public boolean isPropertySet(final Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(final Object id) {
		// do nothing
	}

	@Override
	public void setPropertyValue(final Object id, final Object value) {
		// do nothing
	}

}
