package uk.ac.ncl.safecap.diagram.misc.postprocess;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;

import safecap.Project;
import uk.ac.ncl.safecap.diagram.misc.actions.PointCrossoverDetection;
import uk.ac.ncl.safecap.diagram.misc.actions.RouteSync;

public class PostUtil {
	public static void standardPostProcessor(IGraphicalEditPart project, Project root, IProgressMonitor monitor) {
		RefreshNodeKindAndRole.refresh(root, monitor);
		// RefreshSegmentRole.refresh(root, monitor);
		SegmentAmbitLink.refresh(root, monitor);
		AmbitLabelling.refresh(root, monitor);

		if (project != null && PointCrossoverDetection.getToggleState()) {
			PointRoleDetection.refresh(project, root, monitor);
		}

		JunctionPoints.refresh(root, monitor);

		if (RouteSync.getToggleState()) {
			BuildRoutes.doSignalRoutes(root, monitor);
		}

		ModelCleanup.refresh(root, monitor);
	}
}
