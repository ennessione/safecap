package uk.ac.ncl.safecap.navigator.filters;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.platform.SafecapNature;

public class HideDirectSchemaFiles extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IFile) {
			final IFile file = (IFile) element;
			final IProject project = file.getProject();
			try {
				if (project.hasNature(SafecapNature.ID) && SafecapConstants.SAFECAP_FILE_EXTENSION.equals(file.getFileExtension())
						&& parentElement instanceof TreePath) {
					final TreePath path = (TreePath) parentElement;
					if (path.getLastSegment() instanceof IProject) {
						return false;
					}
				}
			} catch (final CoreException e) {
				e.printStackTrace();
				return true;
			}
		}
		return true;
	}

}
