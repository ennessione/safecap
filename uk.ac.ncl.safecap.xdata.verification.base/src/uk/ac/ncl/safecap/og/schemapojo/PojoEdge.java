
package uk.ac.ncl.safecap.og.schemapojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PojoEdge {

	@SerializedName("label")
	@Expose
	private String label;
	@SerializedName("from")
	@Expose
	private PojoFrom from;
	@SerializedName("to")
	@Expose
	private PojoTo to;
	@Expose
	private int dir;

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public PojoFrom getFrom() {
		return from;
	}

	public void setFrom(PojoFrom from) {
		this.from = from;
	}

	public PojoTo getTo() {
		return to;
	}

	public void setTo(PojoTo to) {
		this.to = to;
	}

}
