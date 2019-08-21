package uk.ac.ncl.safecap.diagram.misc.diagramfilters;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RegistryToggleState;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import safecap.Project;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;

public abstract class FilterBase extends AbstractHandler implements IElementUpdater, IPartListener {
	private Project _schema;
	private IFile _schemaFile;
	private UIElement elementCached;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);

		final Project project = getSelectedModel(editor);
		if (project != null) {
			_schema = project;

			// diagram flag
			final boolean diagramState = getToggleState(project);

			// button flag
			final Command command = event.getCommand();
			final State _buttonState = command.getState(RegistryToggleState.STATE_ID);
			final boolean buttonState = (Boolean) _buttonState.getValue();

			if (buttonState != diagramState) {
				System.err.println("State mismatch");
			}

			final FlagToggleCommand zcmd = new FlagToggleCommand(project, this);
			zcmd.run(!diagramState);

			_buttonState.setValue(new Boolean(diagramState));

			// toggle the flag
			HandlerUtil.toggleCommandState(command);

			// update editor
			final DiagramDocumentEditor deditor = (DiagramDocumentEditor) editor;
			deditor.getDiagramGraphicalViewer().getControl().redraw();
			final DiagramRootEditPart rep = (DiagramRootEditPart) deditor.getDiagramGraphicalViewer().getRootEditPart();
			rep.getLayer(org.eclipse.gef.LayerConstants.CONNECTION_LAYER).validate();
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

	public abstract String getFlagId();

	public boolean getToggleState(Project schema) {
		return ExtensionUtil.getFlag(schema, getFlagId());
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		elementCached = element;
		final IWorkbenchPage wbp = element.getServiceLocator().getService(IWorkbenchPage.class);
		if (wbp != null) {
			wbp.addPartListener(this);
		}

		if (_schema != null) {
			element.setChecked(getToggleState(_schema));
		} else {
			element.setChecked(false);
		}
	}

	@Override
	public void partActivated(IWorkbenchPart part) {
		if (part instanceof EditorPart) {
			final IEditorPart editor = (IEditorPart) part;
			final IFileEditorInput finput = (IFileEditorInput) editor.getEditorInput();
			final IFile file = finput.getFile();
			if (file != null && file.exists() && file.getFileExtension() != null
					&& file.getFileExtension().equals(SafecapConstants.SAFECAP_FILE_EXTENSION)) {
				// update _schema
				// System.err.println("Set schema " + file);
				_schemaFile = file;
				_schema = EmfUtil.fromFile(file);
				super.setBaseEnabled(true);
				if (elementCached != null) {
					elementCached.setChecked(getToggleState(_schema));
				}
			}
		}
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		if (part instanceof IEditorPart) {
			final IEditorPart editor = (IEditorPart) part;

			final IFileEditorInput finput = (IFileEditorInput) editor.getEditorInput();
			final IFile file = finput.getFile();
			if (file != null && file.equals(_schemaFile)) {
				System.err.println("Forget schema " + file);
				_schemaFile = null;
				_schema = null;
				if (elementCached != null) {
					elementCached.setChecked(false);
				}
			}
		}

	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub

	}

}

class FlagToggleCommand {
	private final Project root;
	private final FilterBase base;

	public FlagToggleCommand(Project root, FilterBase base) {
		this.root = root;
		this.base = base;
	}

	public void run(final boolean visibility) {
		try {
			getCommand(visibility).execute(null, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getCommand(final boolean visibility) {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "Obfuscate", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				try {
					ExtensionUtil.setFlag(root, base.getFlagId(), visibility);
					// root.eResource().save(Collections.EMPTY_MAP);
				} catch (final Throwable e) {
					e.printStackTrace();
					return CommandResult.newErrorCommandResult(e);
				}

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}
}