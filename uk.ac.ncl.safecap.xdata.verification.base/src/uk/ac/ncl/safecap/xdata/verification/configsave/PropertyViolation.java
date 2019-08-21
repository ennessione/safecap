package uk.ac.ncl.safecap.xdata.verification.configsave;

import java.io.Serializable;
import java.util.Date;

public class PropertyViolation implements Serializable {

	private static final long serialVersionUID = -488403965444992808L;
	private String hash;
	private String location;
	private String blame;
	private String commentary;
	private Date date;

	public PropertyViolation(String hash, String location) {
		this.hash = hash;
		this.location = location;
		date = new Date();
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHash() {
		return hash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (hash == null ? 0 : hash.hashCode());
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
		final PropertyViolation other = (PropertyViolation) obj;
		if (hash == null) {
			if (other.hash != null) {
				return false;
			}
		} else if (!hash.equals(other.hash)) {
			return false;
		}
		return true;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public String getBlame() {
		return blame;
	}

	public void setBlame(String blame) {
		this.blame = blame;
		date = new Date();
	}

	public Date getDate() {
		return date;
	}
}
