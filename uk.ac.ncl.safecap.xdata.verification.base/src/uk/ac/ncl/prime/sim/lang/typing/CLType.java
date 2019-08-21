package uk.ac.ncl.prime.sim.lang.typing;

import java.util.Set;

public abstract class CLType {
	private Set<String> typeVariables;
	private final String id;
	private final int tag;

	protected CLType(int tag, String id) {
		this.tag = tag;
		this.id = id;
	}

	protected int getTag() {
		return tag;
	}

	/**
	 * Returns whether a given type is a template type
	 */
	public abstract boolean isTemplate();

	public abstract boolean isPolymorphic();

	public abstract boolean isExtended();

	public boolean isConcrete() {
		return !isTemplate() && !isPolymorphic();
	}

	protected abstract Set<String> buildTypeVariables();

	public Set<String> getTypeVariables() {
		if (typeVariables == null) {
			typeVariables = buildTypeVariables();
		}
		return typeVariables;
	}

	/**
	 * Transforms a template type into a new instance of a polymorphic type with
	 * fresh type variables
	 * 
	 * @return
	 */
	public CLType makePolymorphicType() {
		return this;
	}

	public CLType makePolymorphicType(CLType type) {
		if (type == null) {
			return new CLTypeAny(null);
		} else if (type.isTemplate()) {
			return type.makePolymorphicType();
		} else {
			return type;
		}
	}

	public boolean unify(TypeSolution solution, CLType other) {
		CLType t1 = this;
		CLType t2 = other;

		if (t2 == null) {
			return true;
		}

		if (t1 instanceof CLTypeAny) {
			final CLTypeAny any = (CLTypeAny) t1;
			t1 = solution.getSolvedType(any);
		}

		if (t2 instanceof CLTypeAny) {
			final CLTypeAny any = (CLTypeAny) t2;
			t2 = solution.getSolvedType(any);
		}

		if (t1 instanceof CLTypeAny) {
			return t2._unify(solution, t1);
		} else {
			return t1._unify(solution, t2);
		}
	}

	private boolean _unify(TypeSolution solution, CLType u) {
		if (equals(u)) {
			return true;
		} else if (this instanceof CLTypeAny) {
			final CLTypeAny any = (CLTypeAny) this;
			solution.constrainType(any, u);
			return true;
		} else if (u instanceof CLTypeAny) {
			final CLTypeAny any = (CLTypeAny) u;
			solution.constrainType(any, this);
			return true;
		} else {
			return false;
		}
	}

	public boolean isSet() {
		if (this instanceof CLPowerType) {
			return true;
		}

		return false;
	}

	public boolean isSequence() {
		if (this instanceof CLSeqType) {
			return true;
		}

		return false;
	}

	public boolean isInt() {
		if (this == CLTypeInteger.INSTANCE) {
			return true;
		}

		return false;
	}

	public boolean isReal() {
		if (this == CLTypeReal.INSTANCE) {
			return true;
		}

		return false;
	}

	public boolean isUser() {
		if (this instanceof CLUserType) {
			return true;
		}

		return false;
	}

	public boolean isRelation() {
		if (this instanceof CLPowerType) {
			final CLPowerType st = (CLPowerType) this;
			if (st.getBase() instanceof CLProductType) {
				return true;
			}
		}

		return false;
	}

	public boolean isCartesian() {
		return this instanceof CLProductType;
	}

	public CLType domType() {
		if (this instanceof CLPowerType) {
			final CLPowerType st = (CLPowerType) this;
			if (st.getBase() instanceof CLProductType) {
				final CLProductType pr = (CLProductType) st.getBase();
				return pr.getLeft();
			}
		} else if (this instanceof CLSeqType) {
			return CLTypeInteger.INSTANCE;
		}

		return null;
	}

	public CLType baseType() {
		if (this instanceof CLPowerType) {
			final CLPowerType st = (CLPowerType) this;
			return st.getBase();
		}

		return null;
	}

	public CLType ranType() {
		if (this instanceof CLPowerType) {
			final CLPowerType st = (CLPowerType) this;
			if (st.getBase() instanceof CLProductType) {
				final CLProductType pr = (CLProductType) st.getBase();
				return pr.getRight();
			}
		} else if (this instanceof CLSeqType) {
			final CLSeqType st = (CLSeqType) this;
			return st.getBase();
		}

		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + tag;
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
		final CLType other = (CLType) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (tag != other.tag) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return id;
	}
}
