package uk.ac.ncl.safecap.diagram.misc.propertypages;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import safecap.Project;
import safecap.model.Line;
import safecap.trackside.SpeedLimit;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class SpeedLimitPropertySource extends ExtensiblePropertySource {
	protected SpeedLimit limit;
	private String[] lines;
	private final Project project;

	public SpeedLimitPropertySource(final SpeedLimit limit) {
		super(limit);
		this.limit = limit;

		project = EmfUtil.getProject(limit);

		if (project != null) {
			lines = new String[project.getLines().size() + 1];
			lines[0] = "";
			int i = 1;
			for (final Line l : project.getLines()) {
				lines[i++] = l.toString();
			}
		} else {
			lines = new String[] {};
		}
	}

	@Override
	public void getDescriptors(List<IPropertyDescriptor> list) {
		list.add(new TextPropertyDescriptor("speed1", "Limit (mps)"));
		list.add(new TextPropertyDescriptor("speed2", "Limit (kph)"));
		list.add(new TextPropertyDescriptor("speed3", "Limit (mph)"));
		list.add(new SetSelectionPropertyDescriptor("line", "Lines", limit.getLine(), project.getLines()));
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.toString().equals("line")) {
			return limit.getLine();
		}
		if (id.toString().equals("speed1")) {
			return "" + limit.getLimit();
		} else if (id.toString().equals("speed2")) {
			return "" + limit.getLimit() * 3.6;
		} else if (id.toString().equals("speed3")) {
			return "" + limit.getLimit() / 0.44704;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean setValueCommand(Object id, Object value) {
		if (id.toString().equals("line")) {
			if (value instanceof List) {
				limit.getLine().clear();
				limit.getLine().addAll((List) value);
			}
			return true;
		} else if (id.toString().equals("speed1")) {
			try {
				final String s = value.toString();
				limit.setLimit(Double.parseDouble(s));
			} catch (final NumberFormatException e) {
				// ignore
			}
			return true;
		} else if (id.toString().equals("speed2")) {
			try {
				final String s = value.toString();
				limit.setLimit(Double.parseDouble(s) / 3.6);
			} catch (final NumberFormatException e) {
				// ignore
			}
			return true;
		} else if (id.toString().equals("speed3")) {
			try {
				final String s = value.toString();
				limit.setLimit(Double.parseDouble(s) * 0.44704);
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
		return limit;
	}

}
