package uk.ac.ncl.safecap.textentry.editor;

import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.core.filebuffers.IDocumentSetupParticipantExtension;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

public class ValidatorDocumentSetupParticipant implements IDocumentSetupParticipant, IDocumentSetupParticipantExtension {

	@Override
	public void setup(IDocument document) {
	}

	@Override
	public void setup(IDocument document, IPath location, LocationKind locationKind) {
		if (locationKind == LocationKind.IFILE) {
			final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(location);
			try {
				file.deleteMarkers(null, true, IResource.DEPTH_INFINITE);
			} catch (final CoreException e) {
			}
			ProjectReconcilerStrategy.addMap(document, file);
		}
	}

}
