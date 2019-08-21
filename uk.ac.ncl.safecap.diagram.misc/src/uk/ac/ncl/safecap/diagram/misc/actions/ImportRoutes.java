package uk.ac.ncl.safecap.diagram.misc.actions;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import safecap.Project;
import uk.ac.ncl.prime.sim.sets.BMap;
import uk.ac.ncl.safecap.diagram.misc.postprocess.SubRouteSubOverlapDetection;
import uk.ac.ncl.safecap.mcommon.conf.RouteConfig;

public class ImportRoutes extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final BMap<Project, IGraphicalEditPart> projectInfo = getSelectedModel(editor);
		if (projectInfo != null) {
			try {
				Shell shell = Display.getCurrent().getActiveShell();
				if (shell == null) {
					shell = editor.getSite().getShell();
				}

				if (shell == null) {
					return null;
				}

				final FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Open an .csv file with route definitions");
				fd.setFilterExtensions(new String[] { "*.csv", "*.CSV" });
				final String filename = fd.open();

				if (filename != null) {
					final File file = new File(filename);
					if (file.exists() && file.isFile()) {
						// System.out.println("route list " + projectInfo.prj1().getRoutes());
						final Reader in = new FileReader(file);
						final RouteConfig routeConfig = new RouteConfig(projectInfo.prj1(), projectInfo.prj2());
						final IStatus status = routeConfig.getCommandBuildInteractive(in).execute(null, null);
						if (!status.isOK()) {
							MessageDialog.openError(null, "Route definition import/build error", status.getMessage());
							return null;
						}
						// System.out.println("route " + projectInfo.prj1().hashCode() + " list " +
						// projectInfo.prj1().getRoutes());

						SubRouteSubOverlapDetection.refresh(projectInfo.prj2(), projectInfo.prj1(), new NullProgressMonitor());

//						System.out.println("saving ...");
//						EmfUtil.saveProject(projectInfo.prj1());
					}
				}
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private static BMap<Project, IGraphicalEditPart> getSelectedModel(IEditorPart editor) {
		if (editor instanceof DiagramEditor) {
			final DiagramEditor deditor = (DiagramEditor) editor;
			// deditor.getDiagramEditPart();
			return new BMap<>((Project) deditor.getDiagram().getElement(), deditor.getDiagramEditPart());

		}
		return null;
	}

}
