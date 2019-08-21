package uk.ac.ncl.prime.sim.lang.semantics.prel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLBase;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.model.CLSubstitution;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.semantics.SymbFrame;
import uk.ac.ncl.prime.sim.lang.semantics.SymbolicExecutionConditional;
import uk.ac.ncl.prime.sim.lang.semantics.SymbolicExecutionContext;
import uk.ac.ncl.prime.sim.lang.typing.CLType;

public class RSConditional extends CLBase implements SymbolicExecutionConditional {
	private RSTranslationContext context;
	private List<CPart> parts;

	public RSConditional(RSTranslationContext context) {
		this.context = context;
		parts = new ArrayList<>();
	}

	private RSConditional() {
	}

	@Override
	public SymbolicExecutionContext enterBranch(CLExpression _condition) {
		final CLType type = _condition.type(context.getTyping());
		final CLExpression condition = context.prepare(_condition, _condition.getLocation());
		final RSBlock branch = new RSBlock(context);
		branch.addFact(condition);
		final CPart p = new CPart(condition, branch);
		if (type == null) {
			context.getValidation().addIssue(_condition, "Failed typing branch condition " + _condition);
		} else {
			parts.add(p);
		}
		return branch;
	}

	private SymbolicExecutionContext enterBranchC(CLExpression condition) {
		final RSBlock branch = new RSBlock(context);
		branch.addFact(condition);
		final CPart p = new CPart(condition, branch);
		parts.add(p);
		return branch;
	}

	public void commit() {
		final List<CPart> added = new ArrayList<>();
		for (final CPart p : parts) {
			added.addAll(p.commit());
		}

		parts.addAll(added);
	}

	public boolean isEmpty() {
		for (final CPart a : parts) {
			if (!a.getBranch().getSubstitutions().isEmpty()) {
				return false;
			}
		}

		return true;
	}

	public RSConditional cartesian(RSConditional other) {

		final RSConditional nc = new RSConditional(context);

		for (final CPart a : parts) {
			for (final CPart b : other.parts) {
				CLExpression c = CLUtils.buildConjunctMSL(a.getCondition(), b.getCondition());
				c = c.simplify(context.getTyping());
				if (!isConstantFalse(c)) {
					final SymbolicExecutionContext branch = nc.enterBranchC(c);

					if (a.getBranch().getSubstitutions() != null) {
						for (final CLSubstitution sa : a.getBranch().getSubstitutions()) {
							branch.addSubstitution(sa);
						}
					}

					if (b.getBranch().getSubstitutions() != null) {
						for (final CLSubstitution sb : b.getBranch().getSubstitutions()) {
							branch.addSubstitution(sb);
						}
					}
				}
			}
		}

		return nc;
	}

	public void embed(CLSubstitution s) {
		for (final CPart p : parts) {
			if (!p.branch.getAssignedIdentifiers().containsAll(s.getUpdatedVariables())) {
				p.branch.addSubstitution(s);
			}
		}
	}

	public void cleanup() {
		final List<CPart> toRemove = new ArrayList<>();
		for (final CPart p : parts) 
			if (isConstantFalse(p.condition) || p.getBranch().getSubstitutions().isEmpty()) 
				toRemove.add(p);

		parts.removeAll(toRemove);
	}

	public List<CPart> getParts() {
		return parts;
	}

	public RSConditional filter(SymbFrame frame) {
		final List<CPart> newparts = new ArrayList<>();
		boolean changed = false;
		for (final CPart part : parts) {
			final CPart f_part = part.filter(frame);
			changed |= f_part != part;
			if (f_part != null) {
				newparts.add(f_part);
			}
		}

		if (changed) {
			if (newparts.isEmpty()) {
				return null;
			}
			final RSConditional cond = new RSConditional();
			cond.context = context;
			cond.parts = newparts;
			return cond;
		} else {
			return this;
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final CPart p : parts) {
			sb.append(p.toString());
		}
		return sb.toString();
	}

	public class CPart {
		private CLExpression condition;
		private RSBlock branch;

		public CPart(CLExpression condition, RSBlock branch) {
			super();
			this.condition = condition;
			this.branch = branch;
		}

		public CPart filter(SymbFrame frame) {
			if (!frame.updates(branch.getAssignedIdentifiers())) {
				return null;
			}

			final CLExpression f_condition = frame.filter(condition);
			final RSBlock f_branch = branch.filter(frame);

			if (f_condition != condition || f_branch != branch) {
				return new CPart(f_condition, f_branch);
			} else {
				return this;
			}

		}

		public List<CPart> commit() {
			final List<CPart> newparts = new ArrayList<>();
			branch.commit();
			if (!branch.getParts().isEmpty()) {
				assert branch.getParts().size() == 1;
				final RSConditional cond = branch.getParts().get(0);
				final CLExpression conditionCopy = condition;
				condition = CLUtils.buildConjunctMSL(condition, cond.parts.get(0).condition);
				branch = cond.parts.get(0).branch;
				for (int i = 1; i < cond.parts.size(); i++) {
					final CPart part = cond.parts.get(i);
					final CPart npart = new CPart(CLUtils.buildConjunctMSL(conditionCopy, part.condition), part.branch);
					newparts.add(npart);
				}
			}
			return newparts;
		}

		public CLExpression getCondition() {
			return condition;
		}

		public RSBlock getBranch() {
			return branch;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();

			sb.append(condition.asString());
			sb.append(" ==> {\n");
			sb.append(branch.toString());
			sb.append("}\n");
			return sb.toString();
		}

		public List<CLExpression> getPreconditions() {
			if (condition.getTag() == alphabet.OP_AND) {
				return ((CLMultiExpression) condition).getParts().getParts();
			} else {
				return Collections.singletonList(condition);
			}
		}

		public List<CLExpression> getPostconditions() {
			if (branch.getSubstitutions() != null) {
				final List<CLExpression> result = new ArrayList<>(branch.getSubstitutions().size());
				for (final CLSubstitution s : branch.getSubstitutions()) {
					final CLExpression p = s.getSubstitutionPredicate();
					result.add(p);
				}
				return result;
			} else {
				return null;
			}
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (parts == null ? 0 : parts.hashCode());
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
		final RSConditional other = (RSConditional) obj;
		if (parts == null) {
			if (other.parts != null) {
				return false;
			}
		} else if (!parts.equals(other.parts)) {
			return false;
		}
		return true;
	}

}
