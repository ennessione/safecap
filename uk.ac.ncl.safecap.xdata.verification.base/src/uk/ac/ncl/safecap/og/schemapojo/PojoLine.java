
package uk.ac.ncl.safecap.og.schemapojo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PojoLine {

	@SerializedName("label")
	@Expose
	private String label;
	@SerializedName("nodes")
	@Expose
	private List<String> nodes = null;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

	@Override
	public String toString() {
		return label;
	}
}
