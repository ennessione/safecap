package uk.ac.ncl.prime.sim.lang;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.mb.ModelBuilderCompiled;
import uk.ac.ncl.prime.sim.lang.mb.ModelSolver;
import uk.ac.ncl.prime.sim.lang.mb.ModelSolverSetComprehensionNative;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLSetCompExpression extends CLQuantifiedExpression {
	private final CLExpression predicate;

	protected CLSetCompExpression(CLCollection<CLParameter> bind, CLExpression expression, CLExpression predicate,
			SourceLocation location) {
		super(alphabet.B_SETCOMP, bind, expression, location);
		this.predicate = predicate;
	}

	public CLSetCompExpression(CLCollection<CLParameter> bind, CLExpression expression, CLExpression predicate) {
		super(alphabet.B_SETCOMP, bind, expression);
		this.predicate = predicate;
	}

	public CLExpression getPredicate() {
		return predicate;
	}

	@Override
	protected CLType getType(TypingContext ctx) {
		final TypingContext ctx0 = super.QType(ctx, null);

		predicate.type(ctx0, CLTypeBool.INSTANCE);

		return new CLPowerType(getBody().getType());
	}

	@Override
	public Object getValueInterpreted(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
//		TypingContext ctx0 = new TypingContext(context.getRootContext());
//		Collection<String> vars = buildBoundVariables(ctx0);

		final ModelSolverSetComprehensionNative solver = new ModelSolverSetComprehensionNative(context.getRootContext(), predicate,
				getBody(), getBoundParameters().getParts());
		solver.solve();

		if (solver.getSolution() == ModelSolver.SOLUTION.UNKNOWN) {
			throw new CLNonExecutableException(this);
		} else {
			return solver.getResult();
		}
	}

	private Collection<String> buildBoundVariables(TypingContext ctx0) {
		final Collection<String> vars = new HashSet<>(getBoundIdentifiers().size());
		for (final CLParameter id : super.getBoundParameters()) {
			final CLExpression ptype = CLUtils.typeToExpression(id.getType());
			final CLType ptype_type = ptype.getType(ctx0);
			ctx0.addSymbol(id.getName(), ptype_type, SYMBOL_CLASS.IDENTIFIER);
			vars.add(id.getName());
		}
		return vars;
	}

	@Override
	protected Set<CLIdentifier> buildBoundIdentifiers() {
		final Set<CLIdentifier> s1 = predicate.getBoundIdentifiers();
		final Set<CLIdentifier> s2 = getBody().getBoundIdentifiers();

		final Set<CLIdentifier> boundIdentifiers = new HashSet<>(s1.size() + s2.size());
		boundIdentifiers.addAll(s1);
		boundIdentifiers.addAll(s2);

		// remove those bound by this identifier
		for (final CLParameter p : getBoundParameters()) {
			boundIdentifiers.remove(new CLIdentifier(p.getName()));
		}

		for (final CLParameter p : getBoundParameters()) {
			boundIdentifiers.remove(new CLIdentifier(p.getName()));
		}

		if (boundIdentifiers.size() > 0) {
			return boundIdentifiers;
		} else {
			return CLExpression.noBoundIdentifiers;
		}
	}

	@Override
	public String asString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{");

		sb.append(getBody().asString());

		sb.append(" | ");

		boolean first = true;
		for (final CLParameter par : getBoundParameters()) {
			if (!first) {
				sb.append(", ");
			}
			sb.append(par.getName());
			sb.append(":");
			sb.append(par.getType().toString());
			first = !first;
		}

		sb.append("; ");
		sb.append(predicate.asString());

		sb.append("}");

		return sb.toString();
	}

	@Override
	public CLExpression rewrite(Map<String, CLExpression> map) {
		final CLExpression r_body = getBody().rewrite(map);
		final CLExpression r_predicate = predicate.rewrite(map);

		if (r_body != getBody() || r_predicate != predicate) {
			return new CLSetCompExpression(getBoundParameters(), r_body, r_predicate);
		}

		return this;
	}

	@Override
	public CLExpression simplify(TypingContext ctx) {
		return this;
	}

	@Override
	public CLIdentifier resolveIdentifiers(String name) {
		// suppress bound identifiers
		for (final CLParameter par : getBoundParameters()) {
			if (par.getName().equals(name)) {
				return null;
			}
		}

		final CLIdentifier x = getBody().resolveIdentifiers(name);
		if (x != null) {
			return x;
		}

		return predicate.resolveIdentifiers(name);
	}

	@Override
	public void visit(ICLExpressionVisitor visitor, Object userobject) {
		if (visitor.visit(this, userobject)) {
			predicate.visit(visitor, userobject);
			getBody().visit(visitor, userobject);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (predicate == null ? 0 : predicate.hashCode());
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
		final CLSetCompExpression other = (CLSetCompExpression) obj;
		if (predicate == null) {
			if (other.predicate != null) {
				return false;
			}
		} else if (!predicate.equals(other.predicate)) {
			return false;
		}
		return true;
	}

	@Override
	public CLExpression deepCopy() {
		return new CLSetCompExpression(getBoundParameters(), getBody().deepCopy(), getPredicate().deepCopy(), getLocation());
	}

	@Override
	public CLExpression shallowCopy() {
		return new CLSetCompExpression(getBoundParameters(), getBody(), getPredicate(), getLocation());
	}

	@Override
	public String compile(SDARuntimeExecutionContext context) throws CLNonExecutableException {
		try {
			final Collection<String> vars = new HashSet<>(getBoundIdentifiers().size());
			for (final CLParameter id : super.getBoundParameters()) {
				vars.add(id.getName());
			}

//				ConstantSubExprCompiler csc = new ConstantSubExprCompiler(context);
//				CLExpression hcPredicate = csc.compileConstantSubExpressions(getPredicate());
//				CLExpression hcBody = csc.compileConstantSubExpressions(getBody());			

			final CLExpression hcPredicate = getPredicate();
			final CLExpression hcBody = getBody();

			final ModelBuilderCompiled mbc = new ModelBuilderCompiled(context, hcPredicate, hcBody, getBoundParameters().getParts());
			mbc.prepare();
			return context.getFastRuntimeContext().insertFastRuntimeContextObject(mbc);
		} catch (CLExecutionException | InvalidSetOpException e) {
			throw new CLNonExecutableException(predicate, e.getMessage());
		}
	}

}
