package uk.ac.ncl.safecap.gui.infographs;

import org.eclipse.core.resources.IFile;

import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.ui.UIUtils;

public abstract class XDataBasedGraph extends InfoGraph {
	@Override
	public void setInput(Object input) {
		if (input instanceof IFile) {
			final IFile file = (IFile) input;
			if ("xmldata".equals(file.getFileExtension())) {
				final DataContext data = UIUtils.getPreparedDataContextFromFile(file.getLocation().toFile());
				if (data != null) {
					buildGraph(data);
				}
			}
		}
	}

	public void buildGraph(DataContext data) {
		clearGraph();

		buildNodes(data);

		buildEdges(data);

		graph.setVisible(true);
		graph.applyLayout();
	}

	public abstract void buildNodes(DataContext data);

	public abstract void buildEdges(DataContext data);
}
