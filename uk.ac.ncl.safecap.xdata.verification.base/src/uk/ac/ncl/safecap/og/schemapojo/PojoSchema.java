
package uk.ac.ncl.safecap.og.schemapojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PojoSchema {

	@SerializedName("layer")
	@Expose
	private Integer layer;
	@SerializedName("contents")
	@Expose
	private PojoContents contents;

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public PojoContents getContents() {
		return contents;
	}

	public void setContents(PojoContents contents) {
		this.contents = contents;
	}

}
