package uk.ac.ncl.safecap.diagram.misc.propertypages;

import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.internal.properties.WorkspaceViewerProperties;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;

import safecap.Project;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;

@SuppressWarnings("restriction")
public class ProjectPropertySource extends LabeledPropertySource {
	protected Project project;
	protected IWorkbenchPart part;
	private IPreferenceStore prefs;
	private AbstractPropertySection psec;

	public ProjectPropertySource(final Project project, IWorkbenchPart part, AbstractPropertySection psec) {
		super(project);
		this.project = project;
		this.part = part;
		this.psec = psec;
		if (part == null) {
			return;
		}
		if (part instanceof IDiagramWorkbenchPart) {
			final IDiagramWorkbenchPart editor = (IDiagramWorkbenchPart) part;
			final DiagramGraphicalViewer viewer = (DiagramGraphicalViewer) editor.getDiagramGraphicalViewer();
			prefs = viewer.getWorkspaceViewerPreferenceStore();
		}
	}

	@Override
	public void getDescriptors(List<IPropertyDescriptor> list) {

		if (prefs != null) {
			list.add(new ComboBoxPropertyDescriptor(SafecapConstants.EXT_SCHEMA_FLAG + ":normalised",
					SafecapConstants.EXT_SCHEMA_FLAG + ":normalised", yesno));
			list.add(new ComboBoxPropertyDescriptor(WorkspaceViewerProperties.VIEWGRID, "Show grid", yesno));
			list.add(new ComboBoxPropertyDescriptor(WorkspaceViewerProperties.SNAPTOGRID, "Snap to grid", yesno));
			list.add(new TextPropertyDescriptor(WorkspaceViewerProperties.GRIDSPACING, "Spacing"));
			list.add(new TextPropertyDescriptor(WorkspaceViewerProperties.GRIDSPACING + ".ratio", "Spacing ratio"));
			super.getDescriptors(list);
		}
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.toString().startsWith(SafecapConstants.EXT_SCHEMA_FLAG)) {
			final String[] parts = id.toString().split(":");
			if (parts.length == 2 && parts[0].equals(SafecapConstants.EXT_SCHEMA_FLAG)) {
				final Boolean val = ExtensionUtil.getBool(project, SafecapConstants.EXT_SCHEMA_FLAG, parts[1]);
				return val ? 0 : 1;
			}
		}

		if (prefs != null) {
			if (id.equals(WorkspaceViewerProperties.VIEWGRID)) {
				final Boolean val = prefs.getBoolean(WorkspaceViewerProperties.VIEWGRID);
				final int index = Boolean.TRUE.equals(val) ? 0 : 1;
				return index;
			} else if (id.equals(WorkspaceViewerProperties.SNAPTOGRID)) {
				final Boolean val = prefs.getBoolean(WorkspaceViewerProperties.SNAPTOGRID);
				final int index = Boolean.TRUE.equals(val) ? 0 : 1;
				return index;
			} else if (id.equals(WorkspaceViewerProperties.GRIDSPACING)) {
				// System.out.println("Get " + WorkspaceViewerProperties.GRIDSPACING);
				final Double val = prefs.getDouble(WorkspaceViewerProperties.GRIDSPACING);
				return val.toString();
			} else if (id.equals(WorkspaceViewerProperties.GRIDSPACING + ".ratio")) {
				final Double val = prefs.getDouble(WorkspaceViewerProperties.GRIDSPACING + ".ratio");
				return val.toString();
			}
		}
		return super.getPropertyValue(id);
	}

	@Override
	public boolean setValueCommand(Object id, Object value) {
		try {
			if (id.toString().startsWith(SafecapConstants.EXT_SCHEMA_FLAG)) {
				final String[] parts = id.toString().split(":");
				if (parts.length == 2 && parts[0].equals(SafecapConstants.EXT_SCHEMA_FLAG)) {
					final int index = Integer.parseInt(value.toString());
					ExtensionUtil.delete(project, SafecapConstants.EXT_SCHEMA_FLAG, parts[1]);
					project.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_SCHEMA_FLAG, parts[1], index == 0));
					return true;
				}
			} else if (part != null && id.equals(WorkspaceViewerProperties.VIEWGRID)) {
				final int index = Integer.parseInt(value.toString());
				prefs.setValue(WorkspaceViewerProperties.VIEWGRID, index == 0);
				refresh();
				return true;
			} else if (part != null && id.equals(WorkspaceViewerProperties.SNAPTOGRID)) {
				final int index = Integer.parseInt(value.toString());
				prefs.setValue(WorkspaceViewerProperties.SNAPTOGRID, index == 0);
				refresh();
				return true;
			} else if (part != null && id.equals(WorkspaceViewerProperties.GRIDSPACING)) {
				// System.out.println("Set " + WorkspaceViewerProperties.GRIDSPACING);
				//final Double val = Double.valueOf(value.toString());
				prefs.setValue(WorkspaceViewerProperties.GRIDSPACING, 100.0);
				refresh();
				return true;
			} else if (part != null && id.equals(WorkspaceViewerProperties.GRIDSPACING + ".ratio")) {
				//final Double val = Double.valueOf(value.toString());
				prefs.setValue(WorkspaceViewerProperties.GRIDSPACING + ".ratio", 1.0);
				refresh();
				return true;
			}
		} catch (final Throwable e) {
		}

		return super.setValueCommand(id, value);
	}

	private void refresh() {
		part.getSite().getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				psec.refresh();
			}
		});
	}

}
