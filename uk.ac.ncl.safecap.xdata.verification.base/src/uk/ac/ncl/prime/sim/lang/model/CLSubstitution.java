package uk.ac.ncl.prime.sim.lang.model;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.semantics.SymbolicExecutionContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;

public abstract class CLSubstitution extends CLStatement {
	protected CLSubstitution(int tag, SourceLocation location) {
		super(tag, location);
	}

	public abstract CLExpression getSubstitutionPredicate();

	@Override
	public void doExecuteSymbolically(SymbolicExecutionContext context) {
		context.addSubstitution(this);
	}

	protected String ppLocation() {
		final SourceLocation sl = getLocation();
		if (sl != null) {
			return sl.toString();
		} else {
			return "<NO LOCATION>";
		}
	}
}
