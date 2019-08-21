package uk.ac.ncl.prime.sim.lang.typing;

import java.util.Arrays;

public class CLEnumType extends CLUserType {
	private String ppId;
	private String[] members;

	public CLEnumType(String id, String[] members) {
		super(id);
		this.members = members;
	}

	public String[] getMembers() {
		return members;
	}

	public void setMembers(String[] members) {
		this.members = members;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CLEnumType other = (CLEnumType) obj;
		if (!Arrays.equals(members, other.members)) {
			return false;
		}
		if (ppId == null) {
			if (other.ppId != null) {
				return false;
			}
		} else if (!ppId.equals(other.ppId)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(members);
		result = prime * result + (ppId == null ? 0 : ppId.hashCode());
		return result;
	}

	@Override
	public boolean isPolymorphic() {
		return false;
	}

	@Override
	public boolean isTemplate() {
		return false;
	}

	@Override
	public boolean isExtended() {
		return false;
	}

}
