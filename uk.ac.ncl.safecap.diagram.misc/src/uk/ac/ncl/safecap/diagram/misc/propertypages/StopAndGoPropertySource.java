package uk.ac.ncl.safecap.diagram.misc.propertypages;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import safecap.Project;
import safecap.model.Line;
import safecap.model.Route;
import safecap.trackside.StopAndGo;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.RouteUtil;

public class StopAndGoPropertySource extends ExtensiblePropertySource {
	protected StopAndGo delay;
	private String[] lines;
	private final Project project;

	public StopAndGoPropertySource(final StopAndGo limit) {
		super(limit);
		delay = limit;

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

	private int lineIndex(Line line) {
		if (line == null) {
			return 0;
		}

		final String name = line.toString();

		for (int i = 0; i < lines.length; i++) {
			if (lines[i].equals(name)) {
				return i;
			}
		}

		return 0;
	}

	@Override
	public void getDescriptors(List<IPropertyDescriptor> list) {
		list.add(new TextPropertyDescriptor("delay", "Delay"));
		list.add(new SetSelectionPropertyDescriptor("line", "Lines", delay.getLine(), project.getLines()));
		list.add(new TextPropertyDescriptor("route", "Release Route"));

//		list.add(new ComboBoxPropertyDescriptor("line", "Line", lines));
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.toString().equals("line")) {
			return delay.getLine();
		} else if (id.toString().equals("delay")) {
			return "" + delay.getDelay();
		} else if (id.toString().equals("route")) {
			return "" + (delay.getReleaseRoute() == null ? "none" : delay.getReleaseRoute().getLabel());
		} else {
			return null;
		}
	}

	@Override
	public boolean setValueCommand(Object id, Object value) {
		if (id.toString().equals("line")) {
			if (value instanceof List) {
				delay.getLine().clear();
				delay.getLine().addAll((List) value);
			}
			return true;
		} else if (id.toString().equals("delay")) {
			try {
				final String s = value.toString();
				delay.setDelay(Integer.parseInt(s));
			} catch (final NumberFormatException e) {
				// ignore
			}
			return true;
		} else if (id.toString().equals("route")) {
			try {
				final String s = value.toString();
				if (s == null || s.length() == 0) {
					delay.setReleaseRoute(null);
				} else {
					final Route r = RouteUtil.getByLabel(project, s);
					if (r != null) {
						delay.setReleaseRoute(r);
					} else {
						delay.setReleaseRoute(null);
					}
				}
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
		return delay;
	}

}
