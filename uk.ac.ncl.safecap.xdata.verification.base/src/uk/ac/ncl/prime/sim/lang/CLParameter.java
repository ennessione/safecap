package uk.ac.ncl.prime.sim.lang;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;

public class CLParameter extends CLElement {
	private final String id;
	private final CLType type;
	private int internalIndex;

	public CLParameter(String id, CLType type) {
		super(alphabet.TYPED_ID);
		this.id = id;
		this.type = type;

		// assert(id != null && type != null);
	}

	public String getName() {
		return id;
	}

	public void type(TypingContext ctx) {
		ctx.addSymbol(id, type, SYMBOL_CLASS.BOUND);
	}

	public CLType getTypeSafely(TypingContext ctx) {
		if (type != null) {
			final CLExpression ptype = CLUtils.typeToExpression(type);
			if (ptype != null) {
				final CLType ptype_type = ptype.getType(ctx);
				return ptype_type.baseType();
			} else {
				return type;
			}
		} else {
			return null;
		}
	}

	public CLType getType() {
		return type;
	}

	@Override
	public String toString() {
		return id + ":" + type.toString();
	}

}
