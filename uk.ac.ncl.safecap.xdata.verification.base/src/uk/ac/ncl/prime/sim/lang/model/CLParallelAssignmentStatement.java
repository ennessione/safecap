package uk.ac.ncl.prime.sim.lang.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLParallelAssignmentStatement extends CLSubstitution {
	private final CLCollection<CLSubstitution> assign_list;
	private CLExpression predicate;

	public CLParallelAssignmentStatement(CLCollection<CLSubstitution> assign_list, SourceLocation location) {
		super(alphabet.PAR_ASSIGN, location);
		this.assign_list = assign_list;
	}

	@Override
	public void type(TypingContext ctx) {
		final Set<String> updated_previous = new HashSet<>();
		final Collection<CLExpression> predicates = new HashSet<>(assign_list.size());

		final TypingContext ctx1 = new TypingContext(ctx);

		for (final CLSubstitution st : assign_list) {

			st.type(ctx1);

			// check for double assignment
			for (final String z : st.getUpdatedVariables()) {
				if (updated_previous.contains(z)) {
					ctx.getValidation().addError(st, "Parallel assignment of identifier '" + z + "'");
				}

				ctx1.addPrimed(z);
			}
			updated_previous.addAll(st.getUpdatedVariables());
			predicates.add(st.getSubstitutionPredicate());
		}

		predicate = CLUtils.buildConjunctMSL(predicates);
		predicate.type(ctx1, CLTypeBool.INSTANCE);
		predicate.setLocation(getLocation());

		super.type(ctx1);
	}

	// @Override
	// public SourceLocation getLocation() {
	// return assign_list.getParts().get(0).getLocation();
	// }

	@Override
	public void buildUpdatedVariables() {
		updatedvariables = new HashSet<>(assign_list.size());
		for (final CLStatement st : assign_list) {
			updatedvariables.addAll(st.getUpdatedVariables());
		}
	}

	@Override
	public void execute(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
		synchronized (context) {
			for (final CLStatement st : assign_list) {
				st.execute(context);
			}
		}
	}

	@Override
	public String asString(int tabs) {
		final StringBuilder sb = new StringBuilder();

		sb.append(getTabs(tabs));
		for (int i = 0; i < assign_list.size(); i++) {
			if (i > 0) {
				sb.append(" | ");
			}
			sb.append(assign_list.getParts().get(i).asString());
		}

		sb.append("; " + ppLocation());

		return sb.toString();
	}

	public CLCollection<CLSubstitution> getAssignments() {
		return assign_list;
	}

	@Override
	public CLExpression getSubstitutionPredicate() {
		return predicate;
	}

	@Override
	public void visitExpressions(ICLExpressionVisitor visitor, Object userobject) {
		for (final CLSubstitution st : assign_list) {
			st.visitExpressions(visitor, userobject);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (assign_list == null ? 0 : assign_list.hashCode());
		return result;
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
		final CLParallelAssignmentStatement other = (CLParallelAssignmentStatement) obj;
		if (assign_list == null) {
			if (other.assign_list != null) {
				return false;
			}
		} else if (!assign_list.equals(other.assign_list)) {
			return false;
		}
		return true;
	}

	@Override
	public void visitStatements(ICLStatementVisitor visitor, Object userobject) {
		visitor.visit(this, userobject);
		for (final CLStatement st : assign_list) {
			visitor.visit(st, userobject);
		}
	}

	@Override
	public CLStatement deepCopy() {
		return new CLParallelAssignmentStatement(assign_list.deepCopy(), getLocation());
	}
}
