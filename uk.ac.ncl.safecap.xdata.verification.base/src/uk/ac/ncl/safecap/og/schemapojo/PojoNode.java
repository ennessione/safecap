
package uk.ac.ncl.safecap.og.schemapojo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PojoNode {

	@SerializedName("label")
	@Expose
	private String label;
	@SerializedName("layer")
	@Expose
	private Integer layer;
	@SerializedName("leafnodes")
	@Expose
	private List<String> leafnodes = null;
	@SerializedName("paths")
	@Expose
	private List<PojoPath> paths = null;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public List<String> getLeafnodes() {
		return leafnodes;
	}

	public void setLeafnodes(List<String> leafnodes) {
		this.leafnodes = leafnodes;
	}

	public List<PojoPath> getPaths() {
		return paths;
	}

	public void setPaths(List<PojoPath> paths) {
		this.paths = paths;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (label == null ? 0 : label.hashCode());
		result = prime * result + (layer == null ? 0 : layer.hashCode());
		result = prime * result + (leafnodes == null ? 0 : leafnodes.hashCode());
		result = prime * result + (paths == null ? 0 : paths.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PojoNode other = (PojoNode) obj;
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		if (layer == null) {
			if (other.layer != null) {
				return false;
			}
		} else if (!layer.equals(other.layer)) {
			return false;
		}
		if (leafnodes == null) {
			if (other.leafnodes != null) {
				return false;
			}
		} else if (!leafnodes.equals(other.leafnodes)) {
			return false;
		}
		if (paths == null) {
			if (other.paths != null) {
				return false;
			}
		} else if (!paths.equals(other.paths)) {
			return false;
		}
		return true;
	}

}
