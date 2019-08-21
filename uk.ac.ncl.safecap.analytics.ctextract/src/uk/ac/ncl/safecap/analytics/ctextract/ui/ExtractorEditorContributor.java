package uk.ac.ncl.safecap.analytics.ctextract.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.wb.swt.ResourceManager;

import uk.ac.ncl.safecap.analytics.ctextract.main.CTProject;
import uk.ac.ncl.safecap.xmldata.ui.UIUtils;

public class ExtractorEditorContributor extends MultiPageEditorActionBarContributor {
	private ExtractionEditor activeEditorPart;
	private Action buildAction;
	private Action exportAction;
	private Action dataAction;

	public ExtractorEditorContributor() {
		super();
		createActions();
	}

	// protected IAction getAction(ITextEditor editor, String actionID) {
	// return (editor == null ? null : editor.getAction(actionID));
	// }

	@Override
	public void setActivePage(IEditorPart part) {
		if (activeEditorPart == part) {
			return;
		}

		activeEditorPart = (ExtractionEditor) part;
	}

	private void createActions() {

		buildAction = new Action() {
			@Override
			public void run() {
				final ExtractionEditor editor = getEditor();
				if (editor != null) {
					editor.rebuild();
				}
			}
		};
		buildAction.setText("Build");
		buildAction.setImageDescriptor(
				ResourceManager.getPluginImageDescriptor("uk.ac.ncl.safecap.analytics.ctextract", "icons/arrow_refresh_small.png"));
		buildAction.setToolTipText("Build the project");

		exportAction = new Action() {
			@Override
			public void run() {
				final ExtractionEditor editor = getEditor();
				if (editor != null) {
					final String file = UIUtils.openFileSaveDialog("*.xml");
					if (file != null) {
						try {
							final CTProject root = editor.getFileInput();
							root.xmlStringExport(file);
						} catch (final Exception e) {
							e.printStackTrace();
							MessageDialog.openError(null, "Export failed", e.getMessage());
						}
					}
				}
			}
		};

		exportAction.setText("XML export");
		exportAction.setImageDescriptor(
				ResourceManager.getPluginImageDescriptor("uk.ac.ncl.safecap.analytics.ctextract", "icons/xml_exports.png"));
		exportAction.setToolTipText("Save extracted data into XML file");

		dataAction = new Action() {
			@Override
			public void run() {
				final ExtractionEditor editor = getEditor();
				if (editor != null) {
					final CTProject root = editor.getLiveInput();
					if (root != null) {
						final String newFile = UIUtils.openXmlFileDialog();
						if (newFile != null) {
							root.setResourceName(newFile);
							root.normalise();
							editor.setDirty(true);
							editor.rebuild();
						}
					}
				}
			}
		};

		dataAction.setText("Change data set");
		dataAction.setImageDescriptor(
				ResourceManager.getPluginImageDescriptor("uk.ac.ncl.safecap.analytics.ctextract", "icons/database_cleanup.png"));
		dataAction.setToolTipText("Change source data set");
	}

	@Override
	public void contributeToMenu(IMenuManager manager) {
		final IMenuManager menu = new MenuManager("Extraction model");
		manager.prependToGroup(IWorkbenchActionConstants.MB_ADDITIONS, menu);
		menu.add(buildAction);
	}

	private ExtractionEditor getEditor() {
		final IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (part instanceof ExtractionEditor) {
			return (ExtractionEditor) part;
		} else {
			for (final IEditorReference x : PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences()) {
				if (x.getEditor(false) instanceof ExtractionEditor) {
					return (ExtractionEditor) x.getEditor(false);
				}
			}
		}
		return null;
	}

	@Override
	public void contributeToToolBar(IToolBarManager manager) {
		manager.add(new Separator());
		manager.add(dataAction);
		manager.add(buildAction);
//		manager.add(new Separator());
//		manager.add(autoClassifyAction);
//		manager.add(new Separator());
		manager.add(exportAction);
	}
}
