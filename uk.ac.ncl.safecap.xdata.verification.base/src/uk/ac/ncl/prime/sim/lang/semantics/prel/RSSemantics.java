package uk.ac.ncl.prime.sim.lang.semantics.prel;

import uk.ac.ncl.prime.sim.lang.model.CLStatement;
import uk.ac.ncl.prime.sim.lang.model.CLSubstitution;
import uk.ac.ncl.prime.sim.lang.semantics.SymbolicExecutionConditional;
import uk.ac.ncl.prime.sim.lang.semantics.SymbolicExecutionContext;
import uk.ac.ncl.prime.sim.lang.semantics.TranslationValidation;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;

public class RSSemantics implements SymbolicExecutionContext {
	private final RSBlock block;
	private final TranslationValidation validation;
	private final RSTranslationContext root;

	public RSSemantics(TypingContext typing) {
		validation = new TranslationValidation();
		root = new RSTranslationContext(typing, validation);
		block = new RSBlock(root);
	}

	public static RSSemantics build(CLStatement statement, TypingContext typing) {
		if (statement != null && typing != null) {
			final RSSemantics scontext = new RSSemantics(typing);
			statement.executeSymbolically(scontext);
			scontext.getBlock().commit();
			return scontext;
		} else {
			return null;
		}
	}

	@Override
	public void addSubstitution(CLSubstitution substitution) {
		block.addSubstitution(substitution);
	}

	@Override
	public SymbolicExecutionConditional addConditional() {
		return block.addConditional();
	}

	public RSBlock getBlock() {
		return block;
	}

	public TranslationValidation getValidation() {
		return validation;
	}

	public RSTranslationContext getRoot() {
		return root;
	}

	@Override
	public String toString() {
		return block.toString();
	}

}
