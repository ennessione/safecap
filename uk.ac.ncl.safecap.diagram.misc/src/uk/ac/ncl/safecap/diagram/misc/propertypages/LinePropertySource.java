package uk.ac.ncl.safecap.diagram.misc.propertypages;

import java.util.List;

import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import safecap.Project;
import safecap.model.Line;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class LinePropertySource extends LabeledPropertySource {
	protected Line line;
//	protected List<Route> known_routes;
	private final Project root;

	public LinePropertySource(final Line line) {
		super(line);
		this.line = line;
		root = EmfUtil.getProject(line);
	}

	@Override
	public void getDescriptors(List<IPropertyDescriptor> list) {
		list.add(new ColorPropertyDescriptor("color", "Colour tag"));
		list.add(new ComboBoxPropertyDescriptor("kind", "Kind", new String[] { "Left", "Right" }));
		list.add(new SetSelectionPropertyDescriptor("routes", "Routes", line.getRoutes(), root.getRoutes()));
		list.add(new TextPropertyDescriptor("traffic", "Traffic Mix"));

		super.getDescriptors(list);
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.toString().equals("color")) {
			return line.getColor();
		} else if (id.toString().equals("kind")) {
			return line.getOrientation().getValue();
		} else if (id.toString().equals("routes")) {
			return line.getRoutes();
		} else if (id.toString().equals("traffic")) {
			final StringBuilder sb = new StringBuilder();
			for (final String str : line.getTrafficmix()) {
				sb.append(str);
				if (line.getTrafficmix().indexOf(str) != line.getTrafficmix().size() - 1) {
					sb.append(", ");
				}
			}
			return sb.toString();
		} else {
			return super.getPropertyValue(id);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean setValueCommand(Object id, Object value) {
		if (id.toString().equals("color")) {
			line.setColor(value);
			return true;
		} else if (id.toString().equals("kind")) {
			final int k = ((Integer) value).intValue();
			line.setOrientation(safecap.Orientation.get(k));
			return true;
		} else if (id.toString().equals("routes")) {
			if (value instanceof List) {
				line.getRoutes().clear();
				line.getRoutes().addAll((List) value);
			}
			return true;
		} else if (id.toString().equals("traffic")) {
			final String[] arr = ((String) value).split(", ");
			line.getTrafficmix().clear();
			for (int i = 0; i < arr.length; i++) {
				line.getTrafficmix().add(arr[i]);
			}
			return true;
		} else {
			return super.setValueCommand(id, value);
		}
	}
}
