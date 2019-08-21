package uk.ac.ncl.prime.sim.lang.typing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.parser.ValidationContext;
import uk.ac.ncl.safecap.common.RELATION_KIND;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class TypingContext {
	public static final TypingContext EMPTY = new TypingContext(null);

	public enum SYMBOL_CLASS {
		IDENTIFIER, CONSTANT, ENUM_CONSTANT, PARAMETER, MODELVARIABLE, AUXILIARY, AUXILIARY_CONSTANT, BOUND, NONE;

		public static SYMBOL_CLASS make(SYMBOL_CLASS base, boolean isConstant) {
			switch (base) {
			case IDENTIFIER:
				if (isConstant) {
					return CONSTANT;
				} else {
					return IDENTIFIER;
				}
			case AUXILIARY:
				if (isConstant) {
					return AUXILIARY_CONSTANT;
				} else {
					return AUXILIARY;
				}
			default:
				return base;
			}
		}

		public boolean isAuxiliary() {
			return this == AUXILIARY || this == AUXILIARY_CONSTANT;
		}

		public boolean isConstant() {
			return this == CONSTANT || this == AUXILIARY_CONSTANT || this == MODELVARIABLE || this == ENUM_CONSTANT;
		}

		public boolean isMutable() {
			return this == IDENTIFIER;
		}

		@Override
		public String toString() {
			switch (this) {
			case IDENTIFIER:
				return "variable";
			case CONSTANT:
				return "constant";
			case PARAMETER:
				return "parameter identifier";
			case MODELVARIABLE:
				return "domain variable";
			case AUXILIARY:
				return "auxiliary variable";
			case AUXILIARY_CONSTANT:
				return "auxiliary constant";
			case BOUND:
				return "bound identifier";
			case NONE:
				return "support";
			case ENUM_CONSTANT:
				return "enum literal";
			default:
				return "unknown";
			}
		}
	};

	/**
	 * Auxiliary variable controlling code reachability. It is set to TRUE but actor
	 * or block termination commands (stop and return)
	 * 
	 * Initially (at module/actor/sub start), it must be set to FALSE
	 */
	public static final String DONE_VARIABLE = CLIdentifier.makeIdentifierName("", "done", true);
	public static final Set<String> DONE_VARIABLE_SET = java.util.Collections.singleton(DONE_VARIABLE);

	private final Map<String, CLType> symbolType;
	private final Map<String, SYMBOL_CLASS> symbolClass;
	private final Map<String, RELATION_KIND> relationKind; // relation properties for relational symbols where known (e.g., partial,
															// functional, surjective)
	private final Map<String, Integer> boundIndices;
	private final Set<CLUserType> givenTypes;
	
	private ValidationContext validation;
	private SDARuntimeExecutionContext context;
	private TypingContext parent;

	private TypeSolution typeSolution; // type inference table

	private boolean autoPrimeMutableIdentifiers = false;

	public TypingContext(ValidationContext validation, SDARuntimeExecutionContext context) {
		symbolType = new HashMap<>();
		symbolClass = new HashMap<>();
		boundIndices = new HashMap<>();
		relationKind = new HashMap<>();
		givenTypes = new HashSet<>();

		this.validation = validation;
		this.context = context;

		parent = null;
		typeSolution = new TypeSolution(this);

	}

	public TypingContext(TypingContext tctx) {
		symbolType = new HashMap<>();
		symbolClass = new HashMap<>();
		boundIndices = new HashMap<>();
		givenTypes = new HashSet<>();
		relationKind = new HashMap<>();

		parent = tctx;
		typeSolution = null;

		if (tctx != null) {
			typeSolution = tctx.typeSolution;
			validation = tctx.validation;
			context = tctx.context;
		}
	}

	public TypingContext() {
		symbolType = new HashMap<>();
		symbolClass = new HashMap<>();
		boundIndices = new HashMap<>();
		givenTypes = new HashSet<>();
		relationKind = new HashMap<>();
	}


	
	public int getSize() {
		return symbolType.size();
	}

	public void clear() {
		symbolType.clear();
		symbolClass.clear();
		boundIndices.clear();
		givenTypes.clear();
		relationKind.clear();
		typeSolution = new TypeSolution(this);
	}

	public boolean hasRelationKind(String name) {
		if (relationKind.containsKey(name)) {
			return true;
		} else if (parent != null) {
			return parent.hasRelationKind(name);
		} else {
			return false;
		}
	}

	public RELATION_KIND getRelationKind(String name) {
		if (relationKind.containsKey(name)) {
			return relationKind.get(name);
		} else if (parent != null) {
			return parent.getRelationKind(name);
		} else {
			return null;
		}
	}

	public void setRelationKind(String name, RELATION_KIND kind) {
		relationKind.put(name, kind);
	}

	public boolean isFunctional(String name) {
		final RELATION_KIND rk = getRelationKind(name);
		return rk != null && rk.isFunction();
	}

	public CLUserType resolveType(String name) {
		for (final CLUserType gt : givenTypes) {
			if (gt.getName().equals(name)) {
				return gt;
			}
		}

		if (parent != null) {
			return parent.resolveType(name);
		} else {
			return null;
		}
	}

	public void addType(CLUserType givenType) {
		givenTypes.add(givenType);
	}

	public Set<CLUserType> getGivenTypes() {
		return givenTypes;
	}

	public boolean hasGivenType(String name) {
		for (final CLUserType t : givenTypes) {
			if (t.getName().equals(name)) {
				return true;
			}
		}

		if (parent != null) {
			return parent.hasGivenType(name);
		}

		return false;
	}

	public boolean unify(CLType a, CLType b) {
		if (a == null) {
			return false;
		} else {
			return a.unify(typeSolution, b);
		}
	}

	public void bakeTypes() {
		typeSolution.bake(getValidation());
	}

	public void debug_print_typeSolutions() {
		System.out.println(typeSolution.toString());
	}

	public Set<String> getNewSymbols() {
		return symbolType.keySet();
	}

	public String getFreshName(String prefix) {
		if (getType(prefix) == null) {
			return prefix;
		}

		int index = 1;
		String candidate;

		do {
			candidate = prefix + index++;
		} while (getType(candidate) != null);

		return candidate;
	}

	public String getFreshQualifier(String prefix, String qual) {
		if (getType(prefix + "_" + qual) == null) {
			return qual;
		}

		int index = 0;
		String candidate;

		do {
			index++;
			candidate = prefix + "_" + qual + index;
		} while (getType(candidate) != null);

		return qual + index;
	}

	public SDARuntimeExecutionContext getRuntimeExecutionContext() {
		return context;
	}

	public ValidationContext getValidation() {
		return validation;
	}

	public void setValidation(ValidationContext validation) {
		this.validation = validation;
	}

	public void addSymbol(String name, CLType type, SYMBOL_CLASS sclass) {
		assert name != null && type != null && sclass != SYMBOL_CLASS.NONE;
		symbolType.put(name, type);
		symbolClass.put(name, sclass);
	}

	public CLType getType(String name) {
		if (symbolType.containsKey(name)) {
			return symbolType.get(name);
		}

		if (parent != null) {
			final CLType t = parent.getType(name);
			if (t != null) {
				return t;
			}
		}

		if (autoPrimeMutableIdentifiers && CLUtils.isPrimed(name)) {
			final CLType t = getType(CLUtils.unPrimed(name));
			if (t != null) {
				addPrimed(CLUtils.unPrimed(name));
			}
			return t;
		}

		return null;
	}

	public boolean isImmutable(String id) {
		final SYMBOL_CLASS c = getSymbolClass(id);
		return c != SYMBOL_CLASS.IDENTIFIER && c != SYMBOL_CLASS.AUXILIARY;
	}

	public void setSymbolClass(String id, SYMBOL_CLASS sclass) {
		symbolClass.put(id, sclass);
	}

	public SYMBOL_CLASS getSymbolClass(String id) {
		if (symbolClass.containsKey(id)) {
			return symbolClass.get(id);
		}

		if (parent != null) {
			return parent.getSymbolClass(id);
		}

		return SYMBOL_CLASS.NONE;
	}

	public Integer getBoundIndex(String id) {
		if (boundIndices.containsKey(id)) {
			return boundIndices.get(id);
		} else if (parent != null) {
			return parent.getBoundIndex(id);
		} else {
			return null;
		}
	}

	public void seBoundIndex(String id, int index) {
		boundIndices.put(id, index);
	}

	public void addPrimed(String id) {
		assert !CLUtils.isPrimed(id);
		final CLType type = getType(id);

		if (type != null) {
			addSymbol(CLIdentifier.makeIdentifierName(id, null, true), type, SYMBOL_CLASS.AUXILIARY);
		}
	}

	public void addPrimed(String id, CLType type) {
		assert !CLUtils.isPrimed(id);
		addSymbol(CLIdentifier.makeIdentifierName(id, null, true), type, SYMBOL_CLASS.AUXILIARY);
	}

	public void setParent(TypingContext parent) {
		assert this != parent;
		this.parent = parent;

	}

	public TypingContext getParent() {
		return parent;
	}

	public void setAutoPrimeMutableIdentifiers(boolean value) {
		autoPrimeMutableIdentifiers = value;
	}

	protected boolean isAutoPrimeMutableIdentifiers() {
		return autoPrimeMutableIdentifiers;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		for (final String n : symbolType.keySet()) {
			sb.append(n + " : " + symbolType.get(n) + "\n");
		}

		if (parent != null) {
			sb.append("+ parent " + parent.symbolType.size() + "\n");
		}

		return sb.toString();
	}
}
