
package uk.ac.ncl.safecap.og.schemapojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PojoPath {

	@SerializedName("from")
	@Expose
	private String from;
	@SerializedName("to")
	@Expose
	private String to;
	@SerializedName("length")
	@Expose
	private Integer length;
	@SerializedName("delay")
	@Expose
	private Double delay;
	@SerializedName("normal")
	@Expose
	private Integer normal;
	@SerializedName("reverse")
	@Expose
	private Integer reverse;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Double getDelay() {
		return delay;
	}

	public void setDelay(Double delay) {
		this.delay = delay;
	}

	public Integer getNormal() {
		return normal;
	}

	public void setNormal(Integer normal) {
		this.normal = normal;
	}

	public Integer getReverse() {
		return reverse;
	}

	public void setReverse(Integer reverse) {
		this.reverse = reverse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (delay == null ? 0 : delay.hashCode());
		result = prime * result + (from == null ? 0 : from.hashCode());
		result = prime * result + (length == null ? 0 : length.hashCode());
		result = prime * result + (normal == null ? 0 : normal.hashCode());
		result = prime * result + (reverse == null ? 0 : reverse.hashCode());
		result = prime * result + (to == null ? 0 : to.hashCode());
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
		final PojoPath other = (PojoPath) obj;
		if (delay == null) {
			if (other.delay != null) {
				return false;
			}
		} else if (!delay.equals(other.delay)) {
			return false;
		}
		if (from == null) {
			if (other.from != null) {
				return false;
			}
		} else if (!from.equals(other.from)) {
			return false;
		}
		if (length == null) {
			if (other.length != null) {
				return false;
			}
		} else if (!length.equals(other.length)) {
			return false;
		}
		if (normal == null) {
			if (other.normal != null) {
				return false;
			}
		} else if (!normal.equals(other.normal)) {
			return false;
		}
		if (reverse == null) {
			if (other.reverse != null) {
				return false;
			}
		} else if (!reverse.equals(other.reverse)) {
			return false;
		}
		if (to == null) {
			if (other.to != null) {
				return false;
			}
		} else if (!to.equals(other.to)) {
			return false;
		}
		return true;
	}

}
