package uk.ac.ncl.safecap.textentry.editor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.jface.text.source.projection.ProjectionViewer;

import uk.ac.ncl.safecap.textentry.core.TEBuilder;
import uk.ac.ncl.safecap.textentry.core.TEPart;
import uk.ac.ncl.safecap.textentry.parser.TEContext;
import uk.ac.ncl.safecap.textentry.parser.TEMarker;

public class ProjectReconcilerStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension {
	private static Map<IDocument, IFile> documentMap = new HashMap<>();

	private IDocument document;
	private String oldDocument;
	private ProjectionViewer projectionViewer;

	public static synchronized void addMap(IDocument document, IFile file) {
		if (file == null) {
			documentMap.remove(document);
		} else {
			documentMap.put(document, file);
		}
	}

	@Override
	public void setDocument(IDocument document) {
		this.document = document;
	}

	public void setProjectionViewer(ProjectionViewer projectionViewer) {
		this.projectionViewer = projectionViewer;
	}

	@Override
	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		initialReconcile();
	}

	@Override
	public void reconcile(IRegion partition) {
		initialReconcile();
	}

	@Override
	public void initialReconcile() {
		try {
			clearErrorMarkers();

			final TEContext context = new TEContext();
			final TEPart part = TEBuilder.build(document.get(), context);

			createErrorMarkers(context);

			// System.out.println(part);

		} catch (final Throwable e) {
			e.printStackTrace();
		}

//		try {
//			if (file != null) {
//				IMarker marker = file.createMarker(IMarker.PROBLEM);
//				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
//				marker.setAttribute(IMarker.MESSAGE, "Test");
//			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
//			marker.setAttribute(IMarker.CHAR_START, offset);
//			marker.setAttribute(IMarker.CHAR_END, offset + 1);
//			}
//		} catch (CoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private void clearErrorMarkers() {
		final IFile file = documentMap.get(document);
		if (file != null) {
			try {
				file.deleteMarkers(null, true, IResource.DEPTH_INFINITE);
			} catch (final CoreException e) {
			}
		}
	}

	private void createErrorMarkers(TEContext context) {
		final IFile file = documentMap.get(document);
		if (file != null) {
			processMarkers(context.getErrors(), file, IMarker.SEVERITY_ERROR);
			processMarkers(context.getWarnings(), file, IMarker.SEVERITY_WARNING);
			processMarkers(context.getNotes(), file, IMarker.SEVERITY_INFO);
		}
	}

	private void processMarkers(List<TEMarker> markers, IFile file, int severity) {
		int limit = 50;
		for (final TEMarker info : markers) {
			try {
				final IMarker marker = file.createMarker(IMarker.PROBLEM);
				marker.setAttribute(IMarker.SEVERITY, severity);
				marker.setAttribute(IMarker.MESSAGE, info.message);
				marker.setAttribute(IMarker.LINE_NUMBER, info.line);
				marker.setAttribute(IMarker.CHAR_START, info.start);
				marker.setAttribute(IMarker.CHAR_END, info.end);
				limit--;
				if (limit == 0) {
					break;
				}
			} catch (final CoreException e) {
			}
		}
	}

	@Override
	public void setProgressMonitor(IProgressMonitor monitor) {
		// no progress monitor used
	}

}