package uk.ac.ncl.prime.sim.lang.bif;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLFunAppExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLNonExecutableException;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLBuiltinFunction extends CLFunAppExpression {
	private final BuiltinFunction descriptor;

	private static List<BuiltinFunction> builtinFunctions = new ArrayList<>();

	static {
		builtinFunctions.add(BIFIteration.INSTANCE);
		builtinFunctions.add(BIFClosure1.INSTANCE);
		builtinFunctions.add(BIFClosure.INSTANCE);
		builtinFunctions.add(BIFIdentity.INSTANCE);
		builtinFunctions.add(BIFSeqToRel.INSTANCE);
		builtinFunctions.add(BIFFunToRel.INSTANCE);
		builtinFunctions.add(BIFRelToFun.INSTANCE);
		builtinFunctions.add(BIFShiftLeft.INSTANCE);
	}

	public CLBuiltinFunction(CLExpression left, BuiltinFunction descriptor, CLCollection<CLExpression> right, SourceLocation location) {
		super(left, right, location);
		this.descriptor = descriptor;
	}

	public String getAttribute(String attribute) {
		return descriptor.getAttribute(attribute);
	}

	public static BuiltinFunction resolve(String name) {
		for (final BuiltinFunction bif : builtinFunctions) {
			if (bif.getName().equals(name)) {
				return bif;
			}
		}

		return null;
	}

	@Override
	public boolean isImmutable() {
		return right.isImmutable();
	}

	@Override
	protected CLType getType(TypingContext ctx) {
		return descriptor.getType(this, ctx, right);
	}

	@Override
	public Object getValueInterpreted(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
		return descriptor.evaluate(context, right);
	}

	@Override
	public String compile(SDARuntimeExecutionContext context) throws CLNonExecutableException {
		return descriptor.compile(context, right);
	}

	@Override
	public CLExpression simplify(TypingContext ctx) {
		return descriptor.simplify(this, ctx, right);
	}

	@Override
	public CLIdentifier resolveIdentifiers(String name) {
		for (final CLExpression a : right) {
			final CLIdentifier x = a.resolveIdentifiers(name);
			if (x != null) {
				return x;
			}
		}
		return null;
	}

	@Override
	public void visit(ICLExpressionVisitor visitor, Object userobject) {
		if (visitor.visit(this, userobject)) {
			for (final CLExpression a : right) {
				a.visit(visitor, userobject);
			}
		}
	}

	@Override
	protected Set<String> buildFreeIdentifiers() {
		final Set<String> freeIdentifiers = new HashSet<>(10);

		for (final CLExpression p : right) {
			freeIdentifiers.addAll(p.getFreeIdentifiers());
		}

		return freeIdentifiers;
	}

	@Override
	protected Set<CLIdentifier> buildBoundIdentifiers() {
		final Set<CLIdentifier> boundIdentifiers = new HashSet<>(10);

		for (final CLExpression p : right) {
			boundIdentifiers.addAll(p.getBoundIdentifiers());
		}

		return boundIdentifiers;
	}

	@Override
	public CLExpression deepCopy() {
		return new CLBuiltinFunction(left.deepCopy(), descriptor, right.deepCopy(), getLocation());
	}

	@Override
	public CLExpression shallowCopy() {
		return new CLBuiltinFunction(left, descriptor, right, getLocation());

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (descriptor == null ? 0 : descriptor.hashCode());
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
		final CLBuiltinFunction other = (CLBuiltinFunction) obj;
		if (descriptor == null) {
			if (other.descriptor != null) {
				return false;
			}
		} else if (!descriptor.equals(other.descriptor)) {
			return false;
		}
		return true;
	}
}
