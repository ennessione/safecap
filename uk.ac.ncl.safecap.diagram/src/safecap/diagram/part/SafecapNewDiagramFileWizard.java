package safecap.diagram.part;

import java.io.IOException;
import java.util.LinkedList;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateDiagramViewOperation;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import safecap.diagram.edit.parts.ProjectEditPart;

/**
 * @generated
 */
public class SafecapNewDiagramFileWizard extends Wizard {

	/**
	 * @generated
	 */
	private final WizardNewFileCreationPage myFileCreationPage;

	/**
	 * @generated
	 */
	private final ModelElementSelectionPage diagramRootElementSelectionPage;

	/**
	 * @generated
	 */
	private final TransactionalEditingDomain myEditingDomain;

	/**
	 * @generated
	 */
	public SafecapNewDiagramFileWizard(URI domainModelURI, EObject diagramRoot, TransactionalEditingDomain editingDomain) {
		assert domainModelURI != null : "Domain model uri must be specified"; //$NON-NLS-1$
		assert diagramRoot != null : "Doagram root element must be specified"; //$NON-NLS-1$
		assert editingDomain != null : "Editing domain must be specified"; //$NON-NLS-1$

		myFileCreationPage = new WizardNewFileCreationPage(Messages.SafecapNewDiagramFileWizard_CreationPageName,
				StructuredSelection.EMPTY);
		myFileCreationPage.setTitle(Messages.SafecapNewDiagramFileWizard_CreationPageTitle);
		myFileCreationPage.setDescription(NLS.bind(Messages.SafecapNewDiagramFileWizard_CreationPageDescription, ProjectEditPart.MODEL_ID));
		IPath filePath;
		final String fileName = URI.decode(domainModelURI.trimFileExtension().lastSegment());
		if (domainModelURI.isPlatformResource()) {
			filePath = new Path(domainModelURI.trimSegments(1).toPlatformString(true));
		} else if (domainModelURI.isFile()) {
			filePath = new Path(domainModelURI.trimSegments(1).toFileString());
		} else {
			// TODO : use some default path
			throw new IllegalArgumentException("Unsupported URI: " + domainModelURI); //$NON-NLS-1$
		}
		myFileCreationPage.setContainerFullPath(filePath);
		myFileCreationPage.setFileName(SafecapDiagramEditorUtil.getUniqueFileName(filePath, fileName, "safecap")); //$NON-NLS-1$

		diagramRootElementSelectionPage = new DiagramRootElementSelectionPage(Messages.SafecapNewDiagramFileWizard_RootSelectionPageName);
		diagramRootElementSelectionPage.setTitle(Messages.SafecapNewDiagramFileWizard_RootSelectionPageTitle);
		diagramRootElementSelectionPage.setDescription(Messages.SafecapNewDiagramFileWizard_RootSelectionPageDescription);
		diagramRootElementSelectionPage.setModelElement(diagramRoot);

		myEditingDomain = editingDomain;
	}

	/**
	 * @generated
	 */
	@Override
	public void addPages() {
		addPage(myFileCreationPage);
		addPage(diagramRootElementSelectionPage);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean performFinish() {
		final LinkedList<IFile> affectedFiles = new LinkedList<>();
		final IFile diagramFile = myFileCreationPage.createNewFile();
		SafecapDiagramEditorUtil.setCharset(diagramFile);
		affectedFiles.add(diagramFile);
		final URI diagramModelURI = URI.createPlatformResourceURI(diagramFile.getFullPath().toString(), true);
		final ResourceSet resourceSet = myEditingDomain.getResourceSet();
		final Resource diagramResource = resourceSet.createResource(diagramModelURI);
		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(myEditingDomain,
				Messages.SafecapNewDiagramFileWizard_InitDiagramCommand, affectedFiles) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				final int diagramVID = SafecapVisualIDRegistry.getDiagramVisualID(diagramRootElementSelectionPage.getModelElement());
				if (diagramVID != ProjectEditPart.VISUAL_ID) {
					return CommandResult.newErrorCommandResult(Messages.SafecapNewDiagramFileWizard_IncorrectRootError);
				}
				final Diagram diagram = ViewService.createDiagram(diagramRootElementSelectionPage.getModelElement(),
						ProjectEditPart.MODEL_ID, SafecapDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				diagramResource.getContents().add(diagram);
				diagramResource.getContents().add(diagram.getElement());
				return CommandResult.newOKCommandResult();
			}
		};
		try {
			OperationHistoryFactory.getOperationHistory().execute(command, new NullProgressMonitor(), null);
			diagramResource.save(SafecapDiagramEditorUtil.getSaveOptions());
			SafecapDiagramEditorUtil.openDiagram(diagramResource);
		} catch (final ExecutionException e) {
			SafecapDiagramEditorPlugin.getInstance().logError("Unable to create model and diagram", e); //$NON-NLS-1$
		} catch (final IOException ex) {
			SafecapDiagramEditorPlugin.getInstance().logError("Save operation failed for: " + diagramModelURI, ex); //$NON-NLS-1$
		} catch (final PartInitException ex) {
			SafecapDiagramEditorPlugin.getInstance().logError("Unable to open editor", ex); //$NON-NLS-1$
		}
		return true;
	}

	/**
	 * @generated
	 */
	private static class DiagramRootElementSelectionPage extends ModelElementSelectionPage {

		/**
		 * @generated
		 */
		protected DiagramRootElementSelectionPage(String pageName) {
			super(pageName);
		}

		/**
		 * @generated
		 */
		@Override
		protected String getSelectionTitle() {
			return Messages.SafecapNewDiagramFileWizard_RootSelectionPageSelectionTitle;
		}

		/**
		 * @generated
		 */
		@Override
		protected boolean validatePage() {
			if (getModelElement() == null) {
				setErrorMessage(Messages.SafecapNewDiagramFileWizard_RootSelectionPageNoSelectionMessage);
				return false;
			}
			final boolean result = ViewService.getInstance().provides(new CreateDiagramViewOperation(new EObjectAdapter(getModelElement()),
					ProjectEditPart.MODEL_ID, SafecapDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT));
			setErrorMessage(result ? null : Messages.SafecapNewDiagramFileWizard_RootSelectionPageInvalidSelectionMessage);
			return result;
		}
	}
}
