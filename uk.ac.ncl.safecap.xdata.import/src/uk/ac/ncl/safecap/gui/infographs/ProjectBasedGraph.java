package uk.ac.ncl.safecap.gui.infographs;

import org.eclipse.core.resources.IFile;

import safecap.Project;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public abstract class ProjectBasedGraph extends InfoGraph {
	private final String title;

	ProjectBasedGraph(String title) {
		this.title = title;
	}

	@Override
	public void setInput(Object input) {
		if (input instanceof IFile) {
			final IFile file = (IFile) input;
			final Project project = EmfUtil.fromFile(file);
			if (project != null) {
				buildGraph(project);
				super.setPartName(title);
			}
		}
	}

	public void buildGraph(Project project) {
		clearGraph();

		buildNodes(project);

		buildEdges(project);

		graph.setVisible(true);
		graph.applyLayout();
	}

	public abstract void buildNodes(Project project);

	public abstract void buildEdges(Project project);

}
