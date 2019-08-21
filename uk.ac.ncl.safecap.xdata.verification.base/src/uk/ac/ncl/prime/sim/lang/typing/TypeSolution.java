package uk.ac.ncl.prime.sim.lang.typing;

import java.util.HashMap;
import java.util.Map;

import uk.ac.ncl.prime.sim.lang.CLDummyElement;
import uk.ac.ncl.prime.sim.parser.ValidationContext;

public class TypeSolution {
	private final TypingContext typingContext;
	private final Map<CLTypeAny, CLType> typeSolution;

	public TypeSolution(TypingContext typingContext) {
		typeSolution = new HashMap<>();
		this.typingContext = typingContext;
	}

	public void clear() {
		typeSolution.clear();
	}

	public ValidationContext getValidation() {
		return typingContext.getValidation();
	}

	/**
	 * Constraints a given type variable to a monomorphic or polymorphic type. The
	 * result is the unification of the current type constraint and the new
	 * constraint
	 * 
	 * @param type
	 * @param constraint
	 */
	public void constrainType(CLTypeAny type, CLType constraint) {

		// a template type constraint (i.e., set(..)) must be transformed into a
		// template
		// (i.e., set(?)), we add new type variables as necessary
		if (constraint.isTemplate()) {
			final CLType t = constraint.makePolymorphicType();
			typeSolution.put(type, t);
		} else {
			typeSolution.put(type, constraint);
		}
	}

	public void bake(ValidationContext validation) {
		for (final CLTypeAny t : typeSolution.keySet()) {
			final CLType sol = getSolvedType(t);
			if (sol != null && !sol.isTemplate() && !sol.isPolymorphic()) {
				t.bake(sol);
			} else {
				if (validation != null && t.getLocation() != null) {
					validation.addError(new CLDummyElement(t.getLocation()), "Unable to constrain polymorphic type " + sol);
				}
			}
		}
	}

	public CLType getSolvedType(CLTypeAny key) {
		if (typeSolution.containsKey(key)) {
			final CLType current = typeSolution.get(key);
			if (current instanceof CLTypeAny) {
				final CLTypeAny any = (CLTypeAny) current;
				final CLType t = getSolvedType(any);
				if (t != null) {
					return t;
				}
			}

			return current;
		} else {
			return key;
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("\nType solutions:\n");
		for (final CLTypeAny t : typeSolution.keySet()) {
			sb.append("\t");
			sb.append(t.toString());
			sb.append(" = ");
			sb.append(getSolvedType(t).toString());
			sb.append("\n");
		}

		return sb.toString();
	}
}
