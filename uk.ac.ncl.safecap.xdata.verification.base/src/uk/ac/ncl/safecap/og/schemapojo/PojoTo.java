
package uk.ac.ncl.safecap.og.schemapojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PojoTo {

	@SerializedName("node")
	@Expose
	private String node;
	@SerializedName("port")
	@Expose
	private String port;

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
