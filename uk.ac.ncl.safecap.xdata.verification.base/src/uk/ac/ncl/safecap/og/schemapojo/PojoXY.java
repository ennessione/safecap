package uk.ac.ncl.safecap.og.schemapojo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PojoXY {

	@SerializedName("layer")
	@Expose
	private String layer;
	@SerializedName("nodes")
	@Expose
	private List<PojoXYNode> nodes = null;

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public int getLayerInt() {
		return Integer.parseInt(layer);
	}

	public List<PojoXYNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<PojoXYNode> nodes) {
		this.nodes = nodes;
	}

}
