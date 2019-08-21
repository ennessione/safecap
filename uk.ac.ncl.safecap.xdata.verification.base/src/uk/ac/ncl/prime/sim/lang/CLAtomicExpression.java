package uk.ac.ncl.prime.sim.lang;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLSeqType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.BSeq;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLAtomicExpression extends CLExpression {
	public static final CLAtomicExpression NATURAL = new CLAtomicExpression(alphabet.OP_INT);
	public static final CLAtomicExpression NATURAL1 = new CLAtomicExpression(alphabet.OP_INT);
	public static final CLAtomicExpression INTEGER = new CLAtomicExpression(alphabet.OP_INT);
	public static final CLAtomicExpression BOOL = new CLAtomicExpression(alphabet.OP_BOOL);
	public static final CLAtomicExpression TRUE = new CLAtomicExpression(alphabet.OP_TRUE);
	public static final CLAtomicExpression FALSE = new CLAtomicExpression(alphabet.OP_FALSE);
	public static final CLAtomicExpression EMPTYSEQ = new CLAtomicExpression(alphabet.OP_EMPTYSET);
	public static final CLAtomicExpression EMPTYSET = new CLAtomicExpression(alphabet.OP_EMPTYSET);

	public CLAtomicExpression(int tag, SourceLocation location) {
		super(tag, location);
	}

	public CLAtomicExpression(int tag) {
		super(tag);
	}

	@Override
	public CLType getType() {
		switch (getTag()) {
		case alphabet.OP_INT:
			return CLTypeInteger.POWER_INSTANCE;
		case alphabet.OP_BOOL:
			return CLTypeBool.POWER_INSTANCE;
		case alphabet.OP_EMPTYSEQ:
			// parametric type SEQ(*)
			return new CLSeqType(null);
		case alphabet.OP_EMPTYSET:
			// parametric type POW(*)
			return new CLPowerType(null);
		case alphabet.OP_TRUE:
			return CLTypeBool.INSTANCE;
		case alphabet.OP_FALSE:
			return CLTypeBool.INSTANCE;
		default:
			return null;
		}
	}

	@Override
	protected CLType getType(TypingContext ctx) {
		return getType();
	}

	@Override
	public Object getValueInterpreted(SDARuntimeExecutionContext context) throws CLExecutionException {
		switch (getTag()) {
		case alphabet.OP_INT:
		case alphabet.OP_BOOL:
			throw new CLNonExecutableException(this);
		case alphabet.OP_EMPTYSET:
			return BSet.BSET_EMPTY;
		case alphabet.OP_EMPTYSEQ:
			return BSeq.BSEQ_EMPTY;
		case alphabet.OP_TRUE:
			return Boolean.TRUE;
		case alphabet.OP_FALSE:
			return Boolean.FALSE;
		}

		throw new CLExecutionException("Feature not implemented: " + getTag());
	}

	@Override
	protected Set<String> buildFreeIdentifiers() {
		return CLExpression.noFreeIdentifiers;
	}

	@Override
	protected Set<CLIdentifier> buildBoundIdentifiers() {
		return CLExpression.noBoundIdentifiers;
	}

	@Override
	public String asString() {
		switch (getTag()) {
		case alphabet.OP_INT:
			return "int";
		case alphabet.OP_BOOL:
			return "bool";
		case alphabet.OP_EMPTYSET:
			return "{}";
		case alphabet.OP_EMPTYSEQ:
			return "[]";
		case alphabet.OP_TRUE:
			return "true";
		case alphabet.OP_FALSE:
			return "false";
		}
		return "?(Atomic " + getTag() + ")";

	}

	@Override
	public CLExpression rewrite(Map<String, CLExpression> map) {
		return this;
	}

	@Override
	public CLExpression simplify(TypingContext ctx) {
		return this;
	}

	@Override
	public CLIdentifier resolveIdentifiers(String name) {
		return null;
	}

	@Override
	public void visit(ICLExpressionVisitor visitor, Object userobject) {
		visitor.visit(this, userobject);
	}

	@Override
	public boolean isImmutable() {
		return true;
	}

	@Override
	public int getPriority() {
		return -1;
	}

	@Override
	public CLExpression deepCopy() {
		return this;
		// return new CLAtomicExpression(getTag(), getLocation());
	}

	@Override
	public CLExpression shallowCopy() {
		return this;
		// return new CLAtomicExpression(getTag(), getLocation());
	}

	@Override
	public Map<String, CLExpression> _match(CLExpression template, TypingContext context) {
		return Collections.emptyMap();
	}

	@Override
	public String compile(SDARuntimeExecutionContext typingContext) throws CLNonExecutableException {
		switch (getTag()) {
		case alphabet.OP_INT:
		case alphabet.OP_BOOL:
			throw new CLNonExecutableException(this);
		case alphabet.OP_EMPTYSET:
			return "BSET_EMPTY";
		case alphabet.OP_EMPTYSEQ:
			return "BSEQ_EMPTY";
		case alphabet.OP_TRUE:
			return "true";
		case alphabet.OP_FALSE:
			return "false";
		}
		throw new CLNonExecutableException(this);
	}

}
