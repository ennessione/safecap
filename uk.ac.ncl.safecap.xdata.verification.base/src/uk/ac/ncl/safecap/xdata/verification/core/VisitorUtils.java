package uk.ac.ncl.safecap.xdata.verification.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.xdata.provers.ConditionConjecture;
import uk.ac.ncl.safecap.xdata.provers.ConditionManagedPO;
import uk.ac.ncl.safecap.xdata.provers.ConditionProperty;
import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.ConjectureCategory;
import uk.ac.ncl.safecap.xdata.verification.DataReference;
import uk.ac.ncl.safecap.xdata.verification.IdentifierCategory;
import uk.ac.ncl.safecap.xdata.verification.IdentifierDefinition;
import uk.ac.ncl.safecap.xdata.verification.PredicateCategory;
import uk.ac.ncl.safecap.xdata.verification.PredicateDefinition;
import uk.ac.ncl.safecap.xdata.verification.PredicateKind;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.TransitionCategory;
import uk.ac.ncl.safecap.xdata.verification.TransitionDefinition;
import uk.ac.ncl.safecap.xdata.verification.Verifiable;
import uk.ac.ncl.safecap.xdata.verification.VerificationProperty;
import uk.ac.ncl.safecap.xdata.verification.dictionary.DictionarySection;
import uk.ac.ncl.safecap.xdata.verification.dictionary.TermDefinition;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransitionPOs;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransitionPathSource;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionCluster;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionContainer;

public class VisitorUtils {

	public static List<Conjecture> getConjecturesByProfile(RootCatalog catalog, String[] profiles) {
		final List<Conjecture> conjectures = VisitorUtils.getAllConjectures(catalog);
		final SDAContext dataContext = SDAUtils.getDataContext(catalog);
		final List<Conjecture> matching = new ArrayList<>();
		for (final Conjecture c : conjectures) {
			if (isProfileMatchingConjecture(profiles, dataContext, c) && !matching.contains(c)) {
				matching.add(c);
			}
		}

		return matching;
	}

	public static boolean isProfileMatchingConjecture(String[] profiles, SDAContext dataContext, Conjecture c) {
		if (!c.getSemiFormal().empty()) {
			final SemiFormalState state = new SemiFormalState(dataContext, c.getSemiFormal().content());
			if (state.getErrors().isEmpty()) {

				final String profile = state.getProperty("profile");
				if (profile != null) {
					final String[] parts = profile.split(",");
					for (final String p : parts) {
						for (String q : profiles) {
							q = q.trim();
							if (q.length() > 0 && q.charAt(0) == '~') {
								if (p.trim().equals(q.substring(1).trim())) {
									return false;
								}
							} else {
								if (p.trim().equals(q)) {
									return true;
								}
							}
						}
					}
				}
			}
		}

		return hasDefault(profiles);
	}

	private static boolean hasDefault(String[] profiles) {
		for (final String s : profiles) {
			if ("default".equals(s)) {
				return true;
			}
		}

		return false;
	}

	public static double similarity(Verifiable first, Verifiable second) {
		if (first.getParsed().empty() || second.getParsed().empty()) {
			return 0;
		}

		final CLExpression e_first = first.getParsed().content();
		final CLExpression e_second = second.getParsed().content();

		final Set<String> id_first = e_first.getFreeIdentifiers();
		final Set<String> id_second = e_second.getFreeIdentifiers();

		if (id_first.size() == 0 || id_second.size() == 0) {
			return 0;
		}

		double common = 0;
		for (final String s : id_first) {
			if (id_second.contains(s)) {
				common++;
			}
		}

		return common / (id_first.size() + id_second.size());
	}

	public static Map<Conjecture, Double> getAllRelated(Conjecture self, double threshold) {
		final Map<Conjecture, Double> result = new HashMap<>();
		for (final Conjecture c : VisitorUtils.getAllConjectures((RootCatalog) self.root())) {
			if (c != self) {
				final double rank = similarity(self, c);
				if (rank >= threshold) {
					result.put(c, rank);
				}
			}
		}

		return result;
	}

	public static Collection<InvariantInfo> collectInvariants(RootCatalog root) {
		final Collection<InvariantInfo> invariants = new ArrayList<>();
		for (final PredicateCategory spc : root.getPredicateCategories()) {
			collectInvariants(spc, invariants);
		}

		return invariants;
	}

	private static void collectInvariants(PredicateCategory pc, Collection<InvariantInfo> invariants) {
		for (final PredicateCategory spc : pc.getPredicateCategories()) {
			collectInvariants(spc, invariants);
		}

		for (final PredicateDefinition pr : pc.getPredicates()) {
			if (!pr.getKind().empty() && pr.getKind().content() == PredicateKind.INVARIANT && !pr.getParsed().empty()
					&& pr.getParsed().content().getType() != null && pr.getParsed().content().getType().equals(CLTypeBool.INSTANCE)) {
				invariants.add(new InvariantInfo(pr.getParsed().content(), pr.getId().content(), pr));
			}
		}
	}

	public static Object visitAllConditionsInImportedTransitions(RootCatalog root, ElementVisitor<ITransitionPOs> visitor) {
		final SDAContext sdaContext = SDAUtils.getDataContext(root);
		for (final DataReference tdr : sdaContext.getTransitionReferences()) {
			final TransitionContainer tc = sdaContext.getTransitionContainer(tdr);
			processImportedTransitions(tc, visitor);
		}
		return null;
	}

	private static void processImportedTransitions(TransitionContainer tc, ElementVisitor<ITransitionPOs> visitor) {
		for (final TransitionCluster c : tc.getClusters()) {
			visitor.visit(c, tc);
		}
	}

	public static Object visitAllIdentifiers(RootCatalog root, ElementVisitor<IdentifierDefinition> visitor, Object userData) {
		for (final IdentifierCategory prc : root.getIdentifierCategories()) {
			final Object result = visitAllIdentifiers(prc, visitor, userData);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	private static Object visitAllIdentifiers(IdentifierCategory prc, ElementVisitor<IdentifierDefinition> visitor, Object userData) {
		for (final IdentifierCategory pc : prc.getIdentifierCategories()) {
			final Object result = visitAllIdentifiers(pc, visitor, userData);
			if (result != null) {
				return result;
			}
		}

		for (final IdentifierDefinition pc : prc.getIdentifiers()) {
			final Object result = visitor.visit(pc, userData);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	//

	public static Object visitAllTermDefinitions(RootCatalog root, ElementVisitor<TermDefinition> visitor, Object userData) {

		for (final DictionarySection prc : root.getSections()) {
			for (final TermDefinition pc : prc.getTerms()) {
				final Object result = visitor.visit(pc, userData);
				if (result != null) {
					return result;
				}
			}
		}

		return null;
	}

	public static Object visitAllPredicates(RootCatalog root, ElementVisitor<PredicateDefinition> visitor, Object userData) {

		for (final PredicateCategory prc : root.getPredicateCategories()) {
			final Object result = visitAllPredicates(prc, visitor, userData);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	public static int countAllPredicatesOfKind(RootCatalog root, final PredicateKind kind) {
		class Counter implements ElementVisitor<PredicateDefinition> {
			int count = 0;

			@Override
			public Object visit(PredicateDefinition element, Object userData) {
				if (element.getKind().content() == kind) {
					count++;
				}
				return null;
			}
		}

		final Counter c = new Counter();
		visitAllPredicates(root, c, null);
		return c.count;
	}

	public static Object visitAllPredicates(PredicateCategory prc, ElementVisitor<PredicateDefinition> visitor, Object userData) {
		for (final PredicateCategory pc : prc.getPredicateCategories()) {
			final Object result = visitAllPredicates(pc, visitor, userData);
			if (result != null) {
				return result;
			}
		}

		for (final PredicateDefinition pc : prc.getPredicates()) {
			final Object result = visitor.visit(pc, userData);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	public static Object visitAllTransitions(RootCatalog root, ElementVisitor<TransitionDefinition> visitor, Object userData) {

		for (final TransitionCategory prc : root.getTransitionCategories()) {
			final Object result = visitAllTransitions(prc, visitor, userData);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	private static Object visitAllTransitions(TransitionCategory prc, ElementVisitor<TransitionDefinition> visitor, Object userData) {
		for (final TransitionCategory pc : prc.getTransitionCategories()) {
			final Object result = visitAllTransitions(pc, visitor, userData);
			if (result != null) {
				return result;
			}
		}

		for (final TransitionDefinition pc : prc.getTransitions()) {
			final Object result = visitor.visit(pc, userData);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	public static Object visitAllProperties(RootCatalog root, ElementVisitor<Verifiable> visitor, final Object data) {
		for (final ConjectureCategory prc : root.getConjectureCategories()) {
			final Object result = visitAllPropertiesInConjectures(prc, visitor, data);
			if (result != null) {
				return result;
			}
		}

		for (final PredicateCategory prc : root.getPredicateCategories()) {
			final Object result = visitAllPropertiesInPredicates(prc, visitor, data);
			if (result != null) {
				return result;
			}
		}

		for (final TransitionCategory trc : root.getTransitionCategories()) {
			final Object result = visitAllPropertiesInTransitions(trc, visitor, data);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	public static Collection<Conjecture> getAllFailedConjectures(RootCatalog root) {
		final Collection<Conjecture> r = new ArrayList<>();

		visitAllProperties(root, new ElementVisitor<Verifiable>() {
			@Override
			public Object visit(Verifiable property, Object userdata) {
				if (!property.isValid().empty() && property.isValid().content() == false && property instanceof Conjecture) {
					r.add((Conjecture) property);
				}

				return null;
			}
		}, null);

		return r;
	}

	public static List<Conjecture> getAllConjectures(RootCatalog root) {
		final List<Conjecture> r = new ArrayList<>();

		visitAllProperties(root, new ElementVisitor<Verifiable>() {
			@Override
			public Object visit(Verifiable property, Object userdata) {
				if (property instanceof Conjecture && !r.contains(property)) {
					r.add((Conjecture) property);
				}
				return null;
			}
		}, null);

		return r;
	}
	
	public static List<Conjecture> getAllConjectures(RootCatalog root, List<String> identifiers) {
		final List<Conjecture> r = new ArrayList<>();

		visitAllProperties(root, new ElementVisitor<Verifiable>() {
			@Override
			public Object visit(Verifiable property, Object userdata) {
				if (property instanceof Conjecture &&
					!r.contains(property) &&
					!property.getParsed().empty() &&
					CLUtils.isRelevant(identifiers, property.getParsed().content())) {
						r.add((Conjecture) property);
				}
				return null;
			}
		}, null);

		return r;
	}	

	public static Conjecture getConjectureByKey(RootCatalog root, final String key) {
		return (Conjecture) visitAllProperties(root, new ElementVisitor<Verifiable>() {
			@Override
			public Object visit(Verifiable property, Object userdata) {
				if (property instanceof Conjecture && key.equals(((Conjecture) property).getKey().content())) {
					return property;
				}
				return null;
			}
		}, null);

	}

	public static List<ManagedProofObligation> getAllManagedProofObligation(final RootCatalog root) {
		final List<ManagedProofObligation> r = new ArrayList<>();

		visitAllConditionsInImportedTransitions(root, new ElementVisitor<ITransitionPOs>() {

			@Override
			public Object visit(ITransitionPOs element, Object userData) {
				if (element.getPos() != null) {
					r.addAll(element.getPos());
				}
				return null;
			}
		});

		return r;
	}

	public static List<ICondition> getAllVerifiables(final RootCatalog root) {
		final List<ICondition> r = new ArrayList<>();

		visitAllProperties(root, new ElementVisitor<Verifiable>() {
			@Override
			public Object visit(Verifiable property, Object userdata) {
				if (property instanceof Conjecture) {
					r.add(new ConditionConjecture((Conjecture) property));
				} else if (property instanceof VerificationProperty) {
					r.add(new ConditionProperty((VerificationProperty) property));
				}
				return null;
			}
		}, null);

		visitAllConditionsInImportedTransitions(root, new ElementVisitor<ITransitionPOs>() {

			@Override
			public Object visit(ITransitionPOs element, Object userData) {
				if (element.getPos() != null) {
					for (final ManagedProofObligation tpo : element.getPos()) {
						for (final ProofGoal pg : tpo.getGoals()) {
							r.add(new ConditionManagedPO((TransitionContainer) userData, (ITransitionPathSource) element, root, pg));
						}
					}
				}
				return null;
			}

		});

		return r;
	}

	public static Object visitAllPropertiesInPredicates(PredicateCategory parent, ElementVisitor<Verifiable> visitor, final Object data) {

		for (final PredicateCategory prc : parent.getPredicateCategories()) {
			final Object result = visitAllPropertiesInPredicates(prc, visitor, data);
			if (result != null) {
				return result;
			}
		}

		for (final PredicateDefinition pd : parent.getPredicates()) {
			for (final VerificationProperty vp : pd.getProperties()) {
				final Object result = visitor.visit(vp, null);
				if (result != null) {
					return result;
				}
			}
		}

		return null;
	}

	public static Object visitAllPropertiesInConjectures(ConjectureCategory parent, ElementVisitor<Verifiable> visitor, final Object data) {

		for (final ConjectureCategory prc : parent.getConjectureCategories()) {
			final Object result = visitAllPropertiesInConjectures(prc, visitor, data);
			if (result != null) {
				return result;
			}
		}

		for (final Conjecture vp : parent.getConjectures()) {
			final Object result = visitor.visit(vp, null);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	public static Object visitAllPropertiesInTransitions(TransitionCategory parent, ElementVisitor<Verifiable> visitor, final Object data) {

		for (final TransitionCategory trc : parent.getTransitionCategories()) {
			final Object result = visitAllPropertiesInTransitions(trc, visitor, data);
			if (result != null) {
				return result;
			}
		}

		for (final TransitionDefinition pd : parent.getTransitions()) {
			for (final VerificationProperty vp : pd.getProperties()) {
				final Object result = visitor.visit(vp, data);
				if (result != null) {
					return result;
				}
			}
		}

		return null;
	}

}
