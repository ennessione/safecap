package uk.ac.ncl.safecap.textentry.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.reconciler.Reconciler;
import org.eclipse.jface.text.source.projection.ProjectionViewer;

public class ProjectReconciler extends Reconciler {

	private final ProjectReconcilerStrategy fStrategy;

	public ProjectReconciler() {
		// TODO this is logic for .project file to fold tags. Replace with your language
		// logic!
		fStrategy = new ProjectReconcilerStrategy();
		setReconcilingStrategy(fStrategy, IDocument.DEFAULT_CONTENT_TYPE);
	}

	@Override
	public void install(ITextViewer textViewer) {
		super.install(textViewer);
		final ProjectionViewer pViewer = (ProjectionViewer) textViewer;
		fStrategy.setProjectionViewer(pViewer);
	}
}