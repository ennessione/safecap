package uk.ac.ncl.safecap.navigator.filters;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import uk.ac.ncl.safecap.misc.Container;

public class HideNonSchemaFiles extends ViewerFilter {
	public static final String UTDP_EXTENSION = "utdp"; //$NON-NLS-1$

	public HideNonSchemaFiles() {
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (parentElement instanceof TreePath) {
			final TreePath path = (TreePath) parentElement;
			final Object parent = path.getLastSegment();
			if (parent instanceof IWorkspaceRoot) {
				return element instanceof IProject;
			} else if (parent instanceof IProject) {
				return element instanceof Container;
			} else {
				return true;
			}
		} else if (parentElement instanceof IWorkspaceRoot) {
			return element instanceof IProject;
		} else {
			return false;
		}
//		if (element instanceof IResource) {
//			IResource resource = (IResource) element;
//			if (resource instanceof IFile) {
//				IProject project = resource.getProject();
//				try {
//					if (project.hasNature(SafecapNature.ID)) {
//						return SafecapConstants.SAFECAP_FILE_EXTENSION.equals(resource.getFileExtension()) ||
//									  SafecapConstants.XMLDATA_EXTENSION.equals(resource.getFileExtension()) ||
//									  SafecapConstants.VCATALOG_EXTENSION.equals(resource.getFileExtension()) ||
//									  SafecapConstants.TRANSITIONS_EXTENSION.equals(resource.getFileExtension()) ||
//									  SafecapConstants.CONTRACT_EXTENSION.equals(resource.getFileExtension()) ||
//									  SafecapConstants.CONFIGURATION_EXTENSION.equals(resource.getFileExtension()) ||
//									  SafecapConstants.DEFINITION_EXTENSION.equals(resource.getFileExtension()) ||
//									  SafecapConstants.S2_EXTENSION.equals(resource.getFileExtension()) ||
//									  SafecapConstants.CSV_EXTENSION.equals(resource.getFileExtension()) ||
//									  SafecapConstants.S2INS_EXTENSION.equals(resource.getFileExtension());
//					}
//				} catch (CoreException e) {
//					e.printStackTrace();
//					return true;
//				}
//			}
//
//			return true;
//		}
//
//		return true;
	}

}
