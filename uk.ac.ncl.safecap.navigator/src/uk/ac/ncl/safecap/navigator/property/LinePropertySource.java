package uk.ac.ncl.safecap.navigator.property;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import safecap.Project;
import safecap.model.Line;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class LinePropertySource implements IPropertySource {
//    private String _value;
	private final Line line;
	private final Project root;

	public LinePropertySource(final Line line) {
		this.line = line;
		root = EmfUtil.getProject(line);
//        _value = arrToString(line.getTrafficmix());
	}

	private String arrToString(List<String> values) {
		final StringBuilder sb = new StringBuilder();
		for (final String str : values) {
			sb.append(str);
			if (values.indexOf(str) != values.size() - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	private List<String> stringToArr(String value) {
		final String[] arr = value.split(",");
		final List<String> list = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i].trim());
		}
		return list;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.toString().equals("traffic")) {
			return arrToString(line.getTrafficmix());
		} else {
			return "";
		}
	}

	@Override
	public Object getEditableValue() {
		return line;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] { new TextPropertyDescriptor("traffic", "Traffic Mix") };
	}

	@Override
	public boolean isPropertySet(Object arg0) {
		return true;
	}

	@Override
	public void resetPropertyValue(Object arg0) {
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if (id.toString().equals("traffic")) {
//            _value = (String) value;
			line.getTrafficmix().clear();
			line.getTrafficmix().addAll(stringToArr((String) value));
		}
	}
}