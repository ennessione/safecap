package uk.ac.ncl.safecap.gui.infographs;

import uk.ac.ncl.safecap.xmldata.DataContext;

public abstract class DataBasedGraph extends InfoGraph {
	String title;

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setInput(Object input) {
		// if (input instanceof IFile) {
		// IFile file = (IFile) input;
		// if ("xmldata".equals(file.getFileExtension())) {
		// DataContext data =
		// UIUtils.getPreparedDataContextFromFile(file.getLocation().toFile());
		// if (data != null) {
		// buildGraph(data);
		// super.setPartName(title);
		// }
		// }
		// }
	}

	public void buildGraph(DataContext data) {
		// clearGraph();
		//
		// buildNodes(data);
		//
		// buildEdges(data);
		//
		// graph.setVisible(true);
		// graph.applyLayout();
	}

	public abstract void buildNodes(DataContext data);

	public abstract void buildEdges(DataContext data);

}
