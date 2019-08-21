package uk.ac.ncl.eventb.why3.filter;

import uk.ac.ncl.safecap.xdata.provers.TranslationException;

public abstract class ContextProviderBase implements IContextProvider {

	public void addInplaceLemmas(StringBuilder sb) throws TranslationException {
		if (!getInplaceLemmas().isEmpty()) {
			sb.append("\n\t(* supporting lemmata *)\n");
			for (final Why3FilterableLemma l : getInplaceLemmas()) {
				sb.append("\t(* rank " + l.rank(getFilterContext()) + " *)\n");
				printLemma(sb, l);
			}

		}
	}

	private void printLemma(StringBuilder sb, Why3FilterableLemma l) {
		sb.append("\tlemma ");
		sb.append(l.getWhy3LemmaName());
		sb.append(": \n\t\t");
		sb.append(l.getWhy3LemmaBody());
		sb.append("\n\n");
	}

	public void addTheoryImports(StringBuilder sb) throws TranslationException {
		for (final String theory : getTheoryImports()) {
			sb.append("\tuse import " + FilterGlobals.getTheoryFile() + "." + theory + "\n");
		}
	}

}
