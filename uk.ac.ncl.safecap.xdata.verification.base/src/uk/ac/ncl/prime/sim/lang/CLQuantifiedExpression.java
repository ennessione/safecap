package uk.ac.ncl.prime.sim.lang;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.prime.sim.parser.SourceLocation;

public abstract class CLQuantifiedExpression extends CLExpression {
	public static final Set<CLParameter> emptyParamSet = new HashSet<>();

	private final CLCollection<CLParameter> bindingParameters;
	private final CLExpression body;
	private TypingContext context;

	protected CLQuantifiedExpression(int tag, CLCollection<CLParameter> bound_identifiers, CLExpression body, SourceLocation location) {
		super(tag, location);
		bindingParameters = bound_identifiers;
		this.body = body;
	}

	protected CLQuantifiedExpression(int tag, CLCollection<CLParameter> bound_identifiers, CLExpression body) {
		super(tag);
		bindingParameters = bound_identifiers;
		this.body = body;
	}

	public CLCollection<CLParameter> getBoundParameters() {
		return bindingParameters;
	}

	public CLExpression getBody() {
		return body;
	}

	public TypingContext getContext() {
		return context;
	}

	@Override
	protected Set<String> buildFreeIdentifiers() {
		final Set<String> idents = new HashSet<>(body.getFreeIdentifiers());
		for (final CLParameter ib : bindingParameters) {
			idents.remove(ib.getName());
		}

		return idents;

	}

	@Override
	protected Set<CLIdentifier> buildBoundIdentifiers() {
		final Set<CLIdentifier> boundIdentifiers = new HashSet<>(10);

		// add all the bound identifiers occurring in the formula body
		boundIdentifiers.addAll(body.getBoundIdentifiers());

		// remove those bound by this identifier
		for (final CLParameter p : bindingParameters) {
			boundIdentifiers.remove(new CLIdentifier(p.getName()));
		}

		return boundIdentifiers;
	}

	protected Set<CLParameter> getUnusedParametes() {
		final Set<CLParameter> unused = new HashSet<>(bindingParameters.getParts());
		for (final CLIdentifier id : body.getBoundIdentifiers()) {
			unused.remove(getParameter(id));
		}

		return unused;
	}

	private CLParameter getParameter(CLIdentifier id) {
		for (final CLParameter par : bindingParameters) {
			if (par.getName().equals(id.getName())) {
				return par;
			}
		}

		return null;
	}

	protected TypingContext QType(TypingContext ctx, CLType expected) {
		final TypingContext ctx0 = new TypingContext(ctx);

		int internalIndex = 0;
		for (final CLParameter par : bindingParameters) {
			CLUtils.checkIsFreshIdentifier(this, par.getName(), ctx0, false);
			ctx0.addSymbol(par.getName(), par.getTypeSafely(ctx), SYMBOL_CLASS.BOUND);
			ctx0.seBoundIndex(par.getName(), internalIndex);
			internalIndex++;
		}

		if (expected != null) {
			body.type(ctx0, expected);
		} else {
			body.type(ctx0);
		}

		context = ctx0;

		return ctx0;
	}

	@Override
	public CLIdentifier resolveIdentifiers(String name) {
		// suppress bound identifiers
		for (final CLParameter par : bindingParameters) {
			if (par.getName().equals(name)) {
				return null;
			}
		}

		return body.resolveIdentifiers(name);
	}

	@Override
	public void visit(ICLExpressionVisitor visitor, Object userobject) {
		if (visitor.visit(this, userobject)) {
			body.visit(visitor, userobject);
		}
	}

	@Override
	public boolean isImmutable() {
		return getBody().isImmutable();
	}

	private int hashcode = 0;

	@Override
	public int hashCode() {
		int result = hashcode;
		if (result == 0) {
			final int prime = 31;
			result = super.hashCode();
			result = prime * result + (bindingParameters == null ? 0 : bindingParameters.hashCode());
			result = prime * result + (body == null ? 0 : body.hashCode());
			hashcode = result;
		}

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
		final CLQuantifiedExpression other = (CLQuantifiedExpression) obj;
		if (bindingParameters == null) {
			if (other.bindingParameters != null) {
				return false;
			}
		} else if (!bindingParameters.equals(other.bindingParameters)) {
			return false;
		}
		if (body == null) {
			if (other.body != null) {
				return false;
			}
		} else if (!body.equals(other.body)) {
			return false;
		}
		return true;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	protected Map<String, CLExpression> _match(CLExpression template, TypingContext context) {
		final CLQuantifiedExpression other = (CLQuantifiedExpression) template;
		if (other.bindingParameters.size() != bindingParameters.size()) {
			return null;
		}
		return body.match(other.body, context);
	}

//	@Override
//	public Object getValue(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
//		try {
//			return getValueCompiled(context);
//		} catch (Throwable e) {
//			throw new CLExecutionException(e.getMessage());
//		}
//	}

}
