package safecap.diagram.part;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * @generated
 */
public class SafecapCreationWizard extends Wizard implements INewWizard {

	/**
	 * @generated
	 */
	private IWorkbench workbench;

	/**
	 * @generated
	 */
	protected IStructuredSelection selection;

	/**
	 * @generated
	 */
	protected SafecapCreationWizardPage diagramModelFilePage;

	/**
	 * @generated
	 */
	protected Resource diagram;

	/**
	 * @generated
	 */
	private boolean openNewlyCreatedDiagramEditor = true;

	/**
	 * @generated
	 */
	public IWorkbench getWorkbench() {
		return workbench;
	}

	/**
	 * @generated
	 */
	public IStructuredSelection getSelection() {
		return selection;
	}

	/**
	 * @generated
	 */
	public final Resource getDiagram() {
		return diagram;
	}

	/**
	 * @generated
	 */
	public final boolean isOpenNewlyCreatedDiagramEditor() {
		return openNewlyCreatedDiagramEditor;
	}

	/**
	 * @generated
	 */
	public void setOpenNewlyCreatedDiagramEditor(boolean openNewlyCreatedDiagramEditor) {
		this.openNewlyCreatedDiagramEditor = openNewlyCreatedDiagramEditor;
	}

	/**
	 * @generated
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		setWindowTitle(Messages.SafecapCreationWizardTitle);
		setDefaultPageImageDescriptor(SafecapDiagramEditorPlugin.getBundledImageDescriptor("icons/wizban/NewSafecapWizard.gif")); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}

	/**
	 * @generated
	 */
	@Override
	public void addPages() {
		diagramModelFilePage = new SafecapCreationWizardPage("DiagramModelFile", getSelection(), "safecap"); //$NON-NLS-1$ //$NON-NLS-2$
		diagramModelFilePage.setTitle(Messages.SafecapCreationWizard_DiagramModelFilePageTitle);
		diagramModelFilePage.setDescription(Messages.SafecapCreationWizard_DiagramModelFilePageDescription);
		addPage(diagramModelFilePage);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean performFinish() {
		final IRunnableWithProgress op = new WorkspaceModifyOperation(null) {

			@Override
			protected void execute(IProgressMonitor monitor) throws CoreException, InterruptedException {
				diagram = SafecapDiagramEditorUtil.createDiagram(diagramModelFilePage.getURI(), monitor);
				if (isOpenNewlyCreatedDiagramEditor() && diagram != null) {
					try {
						SafecapDiagramEditorUtil.openDiagram(diagram);
					} catch (final PartInitException e) {
						ErrorDialog.openError(getContainer().getShell(), Messages.SafecapCreationWizardOpenEditorError, null,
								e.getStatus());
					}
				}
			}
		};
		try {
			getContainer().run(false, true, op);
		} catch (final InterruptedException e) {
			return false;
		} catch (final InvocationTargetException e) {
			if (e.getTargetException() instanceof CoreException) {
				ErrorDialog.openError(getContainer().getShell(), Messages.SafecapCreationWizardCreationError, null,
						((CoreException) e.getTargetException()).getStatus());
			} else {
				SafecapDiagramEditorPlugin.getInstance().logError("Error creating diagram", e.getTargetException()); //$NON-NLS-1$
			}
			return false;
		}
		return diagram != null;
	}
}
