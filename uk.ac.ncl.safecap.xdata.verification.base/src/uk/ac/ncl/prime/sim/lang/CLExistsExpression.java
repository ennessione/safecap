package uk.ac.ncl.prime.sim.lang;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import uk.ac.ncl.prime.sim.lang.mb.CompiledModelSolverExistential;
import uk.ac.ncl.prime.sim.lang.mb.ISolverCallback;
import uk.ac.ncl.prime.sim.lang.mb.ModelSolver;
import uk.ac.ncl.prime.sim.lang.mb.NativeModelSolverExistential;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLExistsExpression extends CLQuantifiedExpression {

	protected CLExistsExpression(CLCollection<CLParameter> bound_identifiers, CLExpression body, SourceLocation location) {
		super(alphabet.B_EXISTS, bound_identifiers, body, location);
	}

	public CLExistsExpression(CLCollection<CLParameter> bound_identifiers, CLExpression body) {
		super(alphabet.B_EXISTS, bound_identifiers, body);
	}

	@Override
	protected CLType getType(TypingContext ctx) {
		super.QType(ctx, CLTypeBool.INSTANCE);
		return CLTypeBool.INSTANCE;
	}

	// ISolverCallback

	@Override
	public Object getValueInterpreted(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
		return getValueInterpreted(context, null, 1);
	}

	public Object getValueInterpreted(SDARuntimeExecutionContext context, ISolverCallback callback, int maxwitnesses)
			throws CLExecutionException, InvalidSetOpException {

		final NativeModelSolverExistential solver = new NativeModelSolverExistential(context.getRootContext(), super.getBody(),
				getBoundParameters().getParts());
		solver.setCallback(callback);
		solver.setFindNumber(maxwitnesses);
		solver.solve();

		if (solver.getSolution() == ModelSolver.SOLUTION.UNKNOWN) {
			throw new CLNonExecutableException(this);
		} else if (solver.getSolution() == ModelSolver.SOLUTION.TRUE) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public String asString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("exists ");

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

		sb.append(" ");
		sb.append("(");

		sb.append(getBody().asString());

		sb.append(")");

		return sb.toString();
	}

	@Override
	public CLExpression rewrite(Map<String, CLExpression> map) {
		final CLExpression r_body = getBody().rewrite(map);

		if (r_body != getBody()) {
			return new CLExistsExpression(getBoundParameters(), r_body);
		}

		return this;
	}

	@Override
	public CLExpression simplify(TypingContext ctx) {
//		CLExpression r_body = getBody().simplify(ctx);
//
//		if (r_body != getBody()) {
//			CLExistsExpression forall = new CLExistsExpression(getBoundParameters(), r_body);
//
//			Set<CLParameter> up = forall.getUnusedParametes();
//			if (up.size() == getBoundParameters().size()) {
//				return r_body;
//			} else if (up.size() > 0){
//				CLCollection<CLParameter> bb = new CLCollection<CLParameter>(alphabet.SKIP);
//				bb.getParts().addAll(getBoundParameters().getParts());
//				bb.getParts().retainAll(up);
//				return new CLExistsExpression(bb, r_body);
//			} else {
//				return forall;
//			}
//		}
//		

		return this;
	}

	@Override
	public CLExpression deepCopy() {
		return new CLExistsExpression(getBoundParameters(), getBody().deepCopy(), getLocation());
	}

	@Override
	public CLExpression shallowCopy() {
		return new CLExistsExpression(getBoundParameters(), getBody(), getLocation());
	}

	@Override
	public String compile(SDARuntimeExecutionContext context) throws CLNonExecutableException {
		try {
			final Collection<String> vars = new HashSet<>(getBoundIdentifiers().size());
			for (final CLParameter id : super.getBoundParameters()) {
				vars.add(id.getName());
			}

//			ConstantSubExprCompiler csc = new ConstantSubExprCompiler(context);
//			CLExpression hcBody = csc.compileConstantSubExpressions(getBody());

			final CLExpression hcBody = getBody();

			final CompiledModelSolverExistential mbc = new CompiledModelSolverExistential(context, hcBody, getBoundParameters().getParts());
			mbc.prepare();
			return context.getFastRuntimeContext().insertFastRuntimeContextObject(mbc);
		} catch (final Throwable e) {
			// e.printStackTrace();
			throw new CLNonExecutableException(this);
		}
	}
}
