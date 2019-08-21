package uk.ac.ncl.safecap.xdata.verification.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.sapphire.Element;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.safecap.xdata.verification.IdentifierCategory;
import uk.ac.ncl.safecap.xdata.verification.IdentifierDefinition;
import uk.ac.ncl.safecap.xdata.verification.IdentifierKind;
import uk.ac.ncl.safecap.xdata.verification.PredicateCategory;
import uk.ac.ncl.safecap.xdata.verification.PredicateDefinition;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;

public class PropertyModelDependenciesBase {
	private Map<String, Set<Element>> referenceMap;

	public PropertyModelDependenciesBase(RootCatalog catalog) {
		buildReferenceMap(catalog);
	}

	public Set<Element> getElements(String name) {
		return referenceMap.get(name);
	}

	private void buildReferenceMap(RootCatalog root) {
		referenceMap = new HashMap<>();

		for (final PredicateCategory spc : root.getPredicateCategories()) {
			processPredicateCategory(spc);
		}

		for (final IdentifierCategory spc : root.getIdentifierCategories()) {
			processIdentifierCategory(spc);
		}

	}

	private void processPredicateCategory(PredicateCategory pc) {
		for (final PredicateCategory spc : pc.getPredicateCategories()) {
			processPredicateCategory(spc);
		}

		for (final PredicateDefinition pd : pc.getPredicates()) {
			if (pd.validation().ok() && !pd.getId().empty() && !pd.getParsed().empty()) {
				final CLExpression parsed = pd.getParsed().content();
				for (final String s : parsed.getFreeIdentifiers()) {
					addRefMap(s, pd);
				}
			}
		}
	}

	private void processIdentifierCategory(IdentifierCategory pc) {
		for (final IdentifierCategory spc : pc.getIdentifierCategories()) {
			processIdentifierCategory(spc);
		}

		for (final IdentifierDefinition pd : pc.getIdentifiers()) {
			if (pd.validation().ok() && !pd.getId().empty()) {
				addRefMap(pd.getId().content(), pd);
				// System.out.println("@@@ ref map for " + pd.getId().content());
				if (pd.getKind().content() == IdentifierKind.CONSTANT && !pd.getParsed().empty()) {
					final CLExpression parsed = pd.getParsed().content();
					for (final String s : parsed.getFreeIdentifiers()) {
						addRefMap(s, pd);
					}
				}
			} else {
				// System.out.println("@@@ skipping " + pd.getId().content());
			}
		}
	}

	private void addRefMap(String key, Element value) {
		Set<Element> set = referenceMap.get(key);
		if (set == null) {
			set = new HashSet<>();
			referenceMap.put(key, set);
		}

		set.add(value);
	}
}
