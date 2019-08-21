package uk.ac.ncl.safecap.og.schemapojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PojoXYNode {

	@SerializedName("label")
	@Expose
	private String label;
	@SerializedName("x")
	@Expose
	private int x;
	@SerializedName("y")
	@Expose
	private int y;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}