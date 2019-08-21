
package uk.ac.ncl.safecap.og.schemapojo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PojoContents {

	@SerializedName("lines")
	@Expose
	private List<PojoLine> lines = null;
	@SerializedName("nodes")
	@Expose
	private List<PojoNode> nodes = null;
	@SerializedName("edges")
	@Expose
	private List<PojoEdge> edges = null;

	public List<PojoLine> getLines() {
		return lines;
	}

	public void setLines(List<PojoLine> lines) {
		this.lines = lines;
	}

	public List<PojoNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<PojoNode> nodes) {
		this.nodes = nodes;
	}

	public List<PojoEdge> getEdges() {
		return edges;
	}

	public void setEdges(List<PojoEdge> edges) {
		this.edges = edges;
	}

}
