package uk.ac.ncl.safecap.xdata.verification.registry;

import java.util.Collection;
import java.util.Collections;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.safecap.xdata.verification.PredicateDefinition;
import uk.ac.ncl.safecap.xdata.verification.PredicateKind;
import uk.ac.ncl.safecap.xdata.verification.rules.IProverFragments;

public class LemmaPOModule extends POModule<PredicateDefinition> {
	public static LemmaPOModule INSTANCE = new LemmaPOModule();

	private LemmaPOModule() {
	}

	@Override
	public boolean isApplicable(PredicateDefinition element) {
		return element.validation().ok() && element.getKind().content() == PredicateKind.LEMMA && !element.getParsed().empty();
	}

	@Override
	public Collection<String> getPrimed() {
		return Collections.emptySet();
	}

	@Override
	public String getName(PredicateDefinition element) {
		return "LEMMA/" + element.getId();
	}

	@Override
	protected boolean isRelated(PredicateDefinition element, String id) {
		return id.startsWith("LEMMA/");
	}

	@Override
	public CLExpression getGoal(TypingContext ctx, PredicateDefinition element) {
		return element.getParsed().content();
	}

	@Override
	public IProverFragments getIProverFragments(PredicateDefinition element) {
		return element;
	}

}
