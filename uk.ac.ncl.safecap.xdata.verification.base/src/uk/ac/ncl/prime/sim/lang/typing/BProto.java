package uk.ac.ncl.prime.sim.lang.typing;

import uk.ac.ncl.prime.sim.lang.CLUtils;

public class BProto<T> {
	protected Object id;
	protected String ppId;
	protected CLUserType type;

	public BProto(CLUserType type, String id) {
		this.id = id;
		this.type = type;
		if (CLUtils.needsQuotes(id.toString())) {
			ppId = "\"" + id + "\"";
		} else {
			ppId = id.toString();
		}
	}

	public void setId(String id) {
		this.id = id;
		if (CLUtils.needsQuotes(id.toString())) {
			ppId = "\"" + id + "\"";
		} else {
			ppId = id.toString();
		}
	}

	public Object getId() {
		return id;
	}

	public CLUserType getType() {
		return type;
	}

	@Override
	public String toString() {
		return ppId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
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
		final BProto other = (BProto) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
