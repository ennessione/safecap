package uk.ac.ncl.safecap.xmldata.ui;

import java.io.Reader;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import safecap.Project;
import uk.ac.ncl.safecap.diagram.misc.postprocess.PostUtil;
import uk.ac.ncl.safecap.mcommon.conf.CSVUtil;
import uk.ac.ncl.safecap.mcommon.conf.SchemaConfig;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class LoadSchemaConf extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final Project project = getSelectedModel(editor);
		if (project != null) {
			Shell shell = Display.getCurrent().getActiveShell();
			if (shell == null) {
				shell = editor.getSite().getShell();
			}

			if (shell != null) {
				final Reader in = CSVUtil.getInputFile(shell, "Schema configuration", "*.csv", "*.CSV");
				if (in != null) {
					final SchemaConfig sconfig = new SchemaConfig(project);
					IStatus status = sconfig.getCommandReset().execute(null, null);
					IGraphicalEditPart gproject = null;
					if (editor != null && editor instanceof DiagramEditor) {
						final DiagramEditor sc_editor = (DiagramEditor) editor;
						gproject = sc_editor.getDiagramEditPart();
						final Project x = EmfUtil.getProject(sc_editor.getDiagram().getElement());
						assert x == project;
					}
					PostUtil.standardPostProcessor(gproject, project, null);

					status = sconfig.getCommandReadConfigInteractive(in).execute(null, null);
					if (!status.isOK()) {
						MessageDialog.openError(null, "Schema configuration import error", status.getMessage());
						return null;
					}

					status = sconfig.getCommandNormalisationInteractive().execute(null, null);
					if (!status.isOK()) {
						MessageDialog.openError(null, "Normalisation error", status.getMessage());
						return null;
					}

					status = sconfig.getCommandMergingInteractive().execute(null, null);
					if (!status.isOK()) {
						MessageDialog.openError(null, "Merging error", status.getMessage());
						return null;
					}

					EmfUtil.saveProject(project);
					UIUtils.forceRefreshSafecapEditor(editor);

//					for (Ambit a : project.getAmbits())
//						System.out.println("Ambit: " + a);

				}

			}
		}
		return null;
	}

	private static Project getSelectedModel(IEditorPart editor) {
		final IEditorInput obj = editor.getEditorInput();
		if (obj instanceof FileEditorInput) {
			final FileEditorInput input = (FileEditorInput) obj;
			return EmfUtil.fromFile(input.getFile());
		}
		return null;
	}
}
