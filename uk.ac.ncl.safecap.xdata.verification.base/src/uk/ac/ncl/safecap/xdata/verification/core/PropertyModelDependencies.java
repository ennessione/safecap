package uk.ac.ncl.safecap.xdata.verification.core;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.sapphire.Element;

import uk.ac.ncl.prime.sim.lang.CLException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.safecap.xdata.verification.INamed;
import uk.ac.ncl.safecap.xdata.verification.IdentifierDefinition;
import uk.ac.ncl.safecap.xdata.verification.IdentifierKind;
import uk.ac.ncl.safecap.xdata.verification.PredicateDefinition;
import uk.ac.ncl.safecap.xdata.verification.PredicateKind;

public class PropertyModelDependencies {
	public static final PropertyModelDependencies EMPTY = new PropertyModelDependencies();

	private PropertyModelDependenciesBase modelDependencies;
	private Set<PredicateDefinition> axioms;
	private Set<PredicateDefinition> lemmas;
	private Set<PredicateDefinition> invariants;
	private Set<IdentifierDefinition> types;
	private Set<IdentifierDefinition> identifiers;
	private Set<IdentifierDefinition> constants;

	private Set<String> accumSet;

	private PropertyModelDependencies() {
		modelDependencies = null;
		accumSet = new HashSet<>();

		axioms = new HashSet<>();
		lemmas = new HashSet<>();
		invariants = new HashSet<>();
		types = new HashSet<>();
		identifiers = new HashSet<>();
		constants = new HashSet<>();
	}

	public PropertyModelDependencies(PropertyModelDependenciesBase modelDependencies, CLExpression... expressions) {
		this.modelDependencies = modelDependencies;
		accumSet = new HashSet<>();

		axioms = new HashSet<>();
		lemmas = new HashSet<>();
		invariants = new HashSet<>();
		types = new HashSet<>();
		identifiers = new HashSet<>();
		constants = new HashSet<>();

		// stage 0
		for (final CLExpression e : expressions) {
			if (e != null) {
				accumSet.addAll(e.getFreeIdentifiers());
				e.visit(new ICLExpressionVisitor() {
					@Override
					public boolean visit(CLExpression element, Object userobject) throws CLException {
						if (element instanceof CLIdentifier) {
							final CLIdentifier identifier = (CLIdentifier) element;
							final CLType type = identifier.getType();
							if (type != null) {
								accumSet.addAll(type.getTypeVariables());
							}
						}
						return true;
					}

				}, null);
			}
		}

		// do closure
		int size;
		do {
			size = accumSet.size();
			iterate();
		} while (accumSet.size() > size);
	}

	private void iterate() {

		final Set<String> copy = new HashSet<>(accumSet);
		for (final String name : copy) {
			final Set<Element> elements = modelDependencies.getElements(name);
			if (elements != null) {
				for (final Element e : elements) {
					placeElement(e);
				}
			}
		}
	}

	private void addIdentifiers(PredicateDefinition predicate) {
		final CLExpression expression = predicate.getParsed().content();
		if (expression != null) {
			accumSet.addAll(expression.getFreeIdentifiers());
		}
	}

	private void addIdentifiers(IdentifierDefinition identifier) {
		accumSet.add(identifier.getId().content());
		if (identifier.getKind().content() == IdentifierKind.CONSTANT) {
			final CLExpression expression = identifier.getParsed().content();
			if (expression != null) {
				accumSet.addAll(expression.getFreeIdentifiers());
			}
		}
	}

	private void placeElement(Element element) {
		if (element instanceof PredicateDefinition) {
			final PredicateDefinition pd = (PredicateDefinition) element;
			if (pd.getKind().content() == PredicateKind.AXIOM) {
				axioms.add(pd);
			} else if (pd.getKind().content() == PredicateKind.LEMMA) {
				lemmas.add(pd);
			} else if (pd.getKind().content() == PredicateKind.INVARIANT) {
				invariants.add(pd);
			}

			addIdentifiers(pd);
		} else if (element instanceof IdentifierDefinition) {
			final IdentifierDefinition pd = (IdentifierDefinition) element;
			if (pd.getKind().content() == IdentifierKind.CONSTANT) {
				constants.add(pd);
			} else if (pd.getKind().content() == IdentifierKind.TYPE || pd.getKind().content() == IdentifierKind.ENUM) {
				types.add(pd);
			} else if (pd.getKind().content() == IdentifierKind.MODEL) {
				identifiers.add(pd);
			}

			addIdentifiers(pd);
		}
	}

	public Set<PredicateDefinition> getAxioms() {
		return axioms;
	}

	public Set<PredicateDefinition> getLemmas() {
		return lemmas;
	}

	public Set<PredicateDefinition> getInvariants() {
		return invariants;
	}

	public Set<IdentifierDefinition> getTypes() {
		return types;
	}

	public Set<IdentifierDefinition> getIdentifiers() {
		return identifiers;
	}

	public IdentifierDefinition resolveIdentifier(String name) {
		for (final IdentifierDefinition id : identifiers) {
			if (name.equals(id.getId().content())) {
				return id;
			}
		}

		return null;
	}

	public Set<IdentifierDefinition> getConstants() {
		return constants;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		if (!types.isEmpty()) {
			sb.append("types:" + namedToString(types) + "\n");
		}

		if (!constants.isEmpty()) {
			sb.append("constants:" + namedToString(constants) + "\n");
		}

		if (!identifiers.isEmpty()) {
			sb.append("identifiers:" + namedToString(identifiers) + "\n");
		}

		if (!invariants.isEmpty()) {
			sb.append("invariants:" + namedToString(invariants) + "\n");
		}

		if (!axioms.isEmpty()) {
			sb.append("axioms:" + namedToString(axioms) + "\n");
		}

		if (!lemmas.isEmpty()) {
			sb.append("lemmas:" + namedToString(lemmas) + "\n");
		}

		return sb.toString();
	}

	private String namedToString(Set<?> set) {
		final StringBuilder sb = new StringBuilder();
		int i = 0;
		for (final Object _o : set) {
			if (_o instanceof INamed) {
				final INamed named = (INamed) _o;
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(named.getId().content());
				i++;
			}
		}
		return sb.toString();
	}
}
