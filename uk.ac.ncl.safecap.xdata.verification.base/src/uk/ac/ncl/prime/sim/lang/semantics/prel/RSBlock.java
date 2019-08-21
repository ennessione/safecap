package uk.ac.ncl.prime.sim.lang.semantics.prel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.model.CLSubstitution;
import uk.ac.ncl.prime.sim.lang.semantics.SymbFrame;
import uk.ac.ncl.prime.sim.lang.semantics.SymbolicExecutionConditional;
import uk.ac.ncl.prime.sim.lang.semantics.SymbolicExecutionContext;

public class RSBlock implements SymbolicExecutionContext {
	private RSTranslationContext context;
	private List<RSConditional> parts;
	private List<CLSubstitution> substitutions;

	public RSBlock(RSTranslationContext parent) {
		context = new RSTranslationContext(parent);
		parts = new ArrayList<>();
		substitutions = null;
	}

	public RSBlock() {
	}

	@Override
	public void addSubstitution(CLSubstitution substitution) {
		context.process(substitution);
	}

	public List<CLSubstitution> getSubstitutions() {
		return substitutions;
	}

	public Collection<String> getAssignedIdentifiers() {
		if (substitutions == null) {
			return Collections.emptySet();
		}

		final Collection<String> result = new ArrayList<>();
		for (final CLSubstitution s : substitutions) {
			result.addAll(s.getUpdatedVariables());
		}

		return result;
	}

	public void addFact(CLExpression expr) {
		context.addFact(expr);
	}

	@Override
	public SymbolicExecutionConditional addConditional() {
		final RSConditional c = new RSConditional(context);
		parts.add(c);
		return c;
	}

	public void commit() {
		// commit all nested branches
		for (final RSConditional p : parts) {
			p.commit();
		}

		// multiply all the conditionals to get one overall conditional
		RSConditional base;
		if (parts.size() > 1) {
			base = parts.get(0);
			for (int i = 1; i < parts.size(); i++) {
				base = base.cartesian(parts.get(i));
				base.commit();
				base.cleanup();
				if (base.getParts().size() > 2048) {
					System.err.print("Block is too large, baling out at part " + i + " of " + parts.size() + " with " + base.getParts().size() + " parts");
					break;
				}
			}
		} else if (parts.size() == 1) {
			base = parts.get(0);
		} else {
			base = null;
		}

		parts.clear();
		// commit any substitutions of the branch
		final List<CLSubstitution> substs = context.commit();

		// embed substitutions in the conditional, if one exists
		if (base != null && !base.isEmpty()) {
			context.forget(substs);
			for (final CLSubstitution s : substs) {
				base.embed(s);
			}

			base.commit();
			base.cleanup();

			if (!base.isEmpty()) {
				parts.add(base);
			}
		} else {
			substitutions = substs;
		}
	}

	public List<RSConditional> getParts() {
		return parts;
	}

	public RSBlock filter(SymbFrame frame) {
		final List<CLSubstitution> f_substitutions = new ArrayList<>();
		final List<RSConditional> f_parts = new ArrayList<>();

		if (parts != null) {
			for (final RSConditional c : parts) {
				final RSConditional c_f = c.filter(frame);
				if (c_f != null) {
					f_parts.add(c_f);
				}
			}
		}

		if (substitutions != null) {
			for (final CLSubstitution s : substitutions) {
				if (frame.updates(s.getUpdatedVariables())) {
					f_substitutions.add(s);
				}
			}
		}

		final RSBlock block = new RSBlock();
		block.context = context;
		block.parts = f_parts;
		block.substitutions = f_substitutions;

		return block;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final RSConditional c : parts) {
			sb.append("\t" + c.toString() + "\n");
		}

		if (substitutions != null) {
			for (final CLSubstitution s : substitutions) {
				sb.append("&emsp;" + s.asString(1) + "\n");
			}
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (parts == null ? 0 : parts.hashCode());
		result = prime * result + (substitutions == null ? 0 : substitutions.hashCode());
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
		final RSBlock other = (RSBlock) obj;
		if (parts == null) {
			if (other.parts != null) {
				return false;
			}
		} else if (!parts.equals(other.parts)) {
			return false;
		}
		if (substitutions == null) {
			if (other.substitutions != null) {
				return false;
			}
		} else if (!substitutions.equals(other.substitutions)) {
			return false;
		}
		return true;
	}

}
