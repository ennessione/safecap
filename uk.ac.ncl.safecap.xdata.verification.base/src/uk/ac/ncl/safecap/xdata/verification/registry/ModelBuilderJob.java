package uk.ac.ncl.safecap.xdata.verification.registry;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.sapphire.Disposable;
import org.eclipse.sapphire.Element;

import uk.ac.ncl.prime.sim.lang.CLElement;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLRewriteRule;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.model.CLStatement;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.BProto;
import uk.ac.ncl.prime.sim.lang.typing.CLEnumType;
import uk.ac.ncl.prime.sim.lang.typing.CLGivenType;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeAny;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.prime.sim.parser.CLFormulaParser;
import uk.ac.ncl.prime.sim.parser.FormulaSource;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.safecap.common.RELATION_KIND;
import uk.ac.ncl.safecap.prover.transforms.UserInferenceRule;
import uk.ac.ncl.safecap.xdata.provers.VerificationTool;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.ConjectureCategory;
import uk.ac.ncl.safecap.xdata.verification.DataReference;
import uk.ac.ncl.safecap.xdata.verification.FragmentCategory;
import uk.ac.ncl.safecap.xdata.verification.FragmentDefinition;
import uk.ac.ncl.safecap.xdata.verification.IFormulaSource;
import uk.ac.ncl.safecap.xdata.verification.INamed;
import uk.ac.ncl.safecap.xdata.verification.IParseable;
import uk.ac.ncl.safecap.xdata.verification.IdentifierCategory;
import uk.ac.ncl.safecap.xdata.verification.IdentifierDefinition;
import uk.ac.ncl.safecap.xdata.verification.IdentifierKind;
import uk.ac.ncl.safecap.xdata.verification.PredicateCategory;
import uk.ac.ncl.safecap.xdata.verification.PredicateDefinition;
import uk.ac.ncl.safecap.xdata.verification.PredicateKind;
import uk.ac.ncl.safecap.xdata.verification.PropertyReport;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.TransitionCategory;
import uk.ac.ncl.safecap.xdata.verification.TransitionDefinition;
import uk.ac.ncl.safecap.xdata.verification.Verifiable;
import uk.ac.ncl.safecap.xdata.verification.VerificationProperty;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.core.VerificationBasePlugin;
import uk.ac.ncl.safecap.xdata.verification.dictionary.TermDefinition;
import uk.ac.ncl.safecap.xdata.verification.report.GenericReport;
import uk.ac.ncl.safecap.xdata.verification.rules.IProverFragments;
import uk.ac.ncl.safecap.xdata.verification.rules.InferenceRule;
import uk.ac.ncl.safecap.xdata.verification.rules.RewriteRule;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransition;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionCluster;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionClusterPart;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionContainer;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionParsed;

public class ModelBuilderJob extends Job {
	public static enum JOBKIND {
		ALL, PREDICATE, TRANSITION, PROPERTY, FORMULA, FRAGMENT
	};

	private final RootCatalog catalog;
	private final JOBKIND kind;
	private final Element element;
	private final SDAContext sdaContext;
	private boolean noImportedTransitions;

	public ModelBuilderJob(RootCatalog catalog, JOBKIND kind, Element element) {
		super("SafeCap model builder");
		this.catalog = catalog;
		this.kind = kind;
		this.element = element;
		sdaContext = SDAUtils.getDataContext(catalog);
	}

	public static void build(RootCatalog catalog) {
		final ModelBuilderJob job = new ModelBuilderJob(catalog, JOBKIND.ALL, catalog);
		job.schedule();
	}

	/**
	 * Synchronised build
	 * 
	 * @param catalog
	 */
	public static void buildSyn(RootCatalog catalog) {
		final ModelBuilderJob job = new ModelBuilderJob(catalog, JOBKIND.ALL, catalog);
		job.schedule();
		try {
			job.join();
		} catch (final InterruptedException e) {
		}
	}

	/**
	 * Synchronised build, no imported transitions
	 * 
	 * @param catalog
	 */
	public static void buildSynNoImportedTransitions(RootCatalog catalog) {
		final ModelBuilderJob job = new ModelBuilderJob(catalog, JOBKIND.ALL, catalog);
		job.setNoImportedTransitions();
		job.schedule();
		try {
			job.join();
		} catch (final InterruptedException e) {
		}
	}

	private void setNoImportedTransitions() {
		noImportedTransitions = true;
	}

	public JOBKIND getKind() {
		return kind;
	}

	@Override
	public IStatus run(IProgressMonitor monitor) {

		if (catalog.disposed()) {
			return Status.CANCEL_STATUS;
		}

		if (sdaContext == null) {
			return Status.CANCEL_STATUS;
		}

		final Disposable suspend = catalog.suspend();
		try {
			// final long timeStart = System.currentTimeMillis();
			final CLFormulaParser parser = VerificationTool.getParser(catalog);

			if (kind == JOBKIND.ALL) {
				final SDARuntimeExecutionContext context = parser.getModel();

				context.getFastRuntimeContext().clear();

				final TypingContext typing = context.getRootContext();
				typing.clear();

				parser.processTerms(monitor);

				// System.out.println("conjecture cats: " +
				// catalog.getConjectureCategories().size());

				int totalWork = catalog.getConjectureCategories().size();
				totalWork += catalog.getPredicateCategories().size();
				totalWork += catalog.getTransitionCategories().size();
				totalWork += catalog.getFragmentCategories().size();
				if (!noImportedTransitions) {
					totalWork += importedTransitionsSize();
				}
				totalWork += catalog.getRewriteRules().size();
				totalWork += catalog.getInferenceRules().size();

				monitor.beginTask("Building model", totalWork);

				monitor.subTask("Build conjectures");
				for (final ConjectureCategory spc : catalog.getConjectureCategories()) {
					processConjectureCategory(spc, parser, monitor);
				}
				monitor.subTask("Build identifiers");
				for (final IdentifierCategory spc : catalog.getIdentifierCategories()) {
					processIdentifierCategory(spc, parser, monitor);
				}

				// context.clearFastRuntimeContext();

				monitor.subTask("Build predicates");
				for (final PredicateCategory spc : catalog.getPredicateCategories()) {
					processPredicateCategory(spc, parser, monitor);
				}
				monitor.subTask("Build transitions");
				for (final TransitionCategory spc : catalog.getTransitionCategories()) {
					processTransitionCategory(spc, parser, monitor);
				}
				monitor.subTask("Build fragments");
				for (final FragmentCategory spc : catalog.getFragmentCategories()) {
					processFragmentCategory(spc, parser, monitor);
				}

				if (!noImportedTransitions) {
					monitor.subTask("Build imported transitions");
					processImportedTransitions(typing, parser, monitor);
				}

				monitor.subTask("Build prove fragments");
				processRewriteRules(catalog, typing, parser, monitor);
				processInferenceRules(catalog, typing, parser, monitor);

			} else if ((kind == JOBKIND.PREDICATE || kind == JOBKIND.PROPERTY || kind == JOBKIND.FORMULA) && element instanceof INamed) {
				final IFormulaSource pd = (IFormulaSource) element;
				final INamed named = (INamed) element;
				if (!named.getId().empty() && !pd.getFormula().empty()) {
					handlePredicate(pd, parser);
				}
			} else if (kind == JOBKIND.FRAGMENT) {
				final FragmentDefinition pd = (FragmentDefinition) element;
				handleFragment(pd, parser);
			} else if (kind == JOBKIND.TRANSITION) {
				final TransitionDefinition pd = (TransitionDefinition) element;
				if (!pd.getId().empty() && !pd.getPre().empty()) {
					handlePrecondition(pd, parser);
				}
				if (!pd.getId().empty() && !pd.getPost().empty()) {
					handlePostcondition(pd, parser);
				}
			}

			// final long timeEnd = System.currentTimeMillis();
			// System.out.println("Builder: sc in " + (timeEnd - timeStart) + " for " +
			// element);

			// if (catalog.validation().ok())
			// runPOGenerator();

			return Status.OK_STATUS;
		} finally {
			suspend.dispose();
		}
	}

	private void processRewriteRules(IProverFragments catalog, TypingContext typing, CLFormulaParser parser, IProgressMonitor monitor) {
		final List<CLRewriteRule> rules = new ArrayList<>();

		for (final RewriteRule rr : catalog.getRewriteRules()) {
			final CLRewriteRule rule = ProveFragmentsUtils.buildRewriteRule(parser.getModel(), rr.getId().content(), rr.getTemplate(),
					rr.getTarget());
			if (rule != null) {
				rules.add(rule);
			}
			if (monitor != null) {
				monitor.worked(1);
			}
		}

		if (!rules.isEmpty()) {
			ProveFragmentsUtils.setRewriteRules(catalog, rules);
		} else {
			ProveFragmentsUtils.setRewriteRules(catalog, null);
		}
	}

	private void processInferenceRules(IProverFragments catalog, TypingContext typing, CLFormulaParser parser, IProgressMonitor monitor) {
		final List<UserInferenceRule> rules = new ArrayList<>();

		for (final InferenceRule rr : catalog.getInferenceRules()) {
			final UserInferenceRule rule = ProveFragmentsUtils.buildUserInferenceRule(parser.getModel(), rr);
			if (rule != null) {
				rules.add(rule);
			}
			if (monitor != null) {
				monitor.worked(1);
			}
		}

		if (!rules.isEmpty()) {
			ProveFragmentsUtils.setUserInferenceRule(catalog, rules);
		} else {
			ProveFragmentsUtils.setUserInferenceRule(catalog, null);
		}
	}

	private int importedTransitionsSize() {
		int result = 0;
		for (final DataReference tdr : sdaContext.getTransitionReferences()) {
			if (!tdr.disposed()) {
				final TransitionContainer tc = sdaContext.getTransitionContainer(tdr);
				result += tc.getClusters().size();
			}
		}

		return result;
	}

	private void processImportedTransitions(TypingContext typing, CLFormulaParser parser, IProgressMonitor monitor) {
		for (final DataReference tdr : sdaContext.getTransitionReferences()) {
			if (!tdr.disposed()) {
				final TransitionContainer tc = sdaContext.getTransitionContainer(tdr);
				processImportedTransitions(tc, typing, parser, monitor);
				if (!tdr.getPath().disposed()) {
					File file = tdr.getPath().content().toFile();
					if (!file.exists()) {
						file = SDAUtils.resolveResource(file);
					}
					VerificationBasePlugin.getTransitionCache().fireChange(file);
				}
			}
		}
	}

	// extended with clusters
	private void processImportedTransitions(TransitionContainer tc, TypingContext typing, CLFormulaParser parser,
			IProgressMonitor monitor) {
		for (final TransitionCluster t : tc.getClusters()) {
			processImportedCluster(t, typing, parser);
			monitor.worked(1);
		}
	}

	private void processImportedCluster(TransitionCluster t, TypingContext typing, CLFormulaParser parser) {
		if (t.getParts() != null) {
			for (final TransitionClusterPart part : t.getParts()) {
				processImportedTransition(part, typing, parser);
			}
		}
	}

	private void processImportedTransition(ITransition t, TypingContext typing, CLFormulaParser parser) {
		final TransitionParsed tparsed = new TransitionParsed(t, typing, parser);
		t.setParsed(tparsed);
		/*
		 * for (int i = 0; i < tparsed.getFsPreconditions().size(); i++)
		 * handleImportedPrecondition(t, i, parser);
		 * 
		 * for (int i = 0; i < tparsed.getFsPostconditions().size(); i++)
		 * handleImportedPostcondition(t, i, parser);
		 */
	}

	private void handleImportedPrecondition(ITransition t, int i, CLFormulaParser parser) {
		try {
			final FormulaSource source = t.getParsed().getFsPreconditions().get(i);
			if (source == null) {
				return;
			}
			final CLExpression new_parsed = parser.parse(source);
			if (new_parsed != null && new_parsed.getType() != null) {
				t.getParsed().getPreconditions().set(i, new_parsed);
				isPredicate(source, new_parsed);
				noPrimed(source, new_parsed);
			}
			source.mark();
		} catch (final Exception e) {
			t.getParsed().getPreconditions().set(i, null);
			e.printStackTrace();
		}
	}

	private void handleImportedPostcondition(ITransition t, int i, CLFormulaParser parser) {
		try {
			final FormulaSource source = t.getParsed().getFsPostconditions().get(i);
			if (source == null) {
				return;
			}

			final CLExpression new_parsed = parser.parse(source);
			if (new_parsed != null && new_parsed.getType() != null) {
				t.getParsed().getPostconditions().set(i, new_parsed);
				isBoolean(source, new_parsed);
				hasPrimed(source, new_parsed);
			}
			source.mark();

		} catch (final Exception e) {
			t.getParsed().setPostconditions(null);
			e.printStackTrace();
		}
	}

	private void processConjectureCategory(ConjectureCategory pc, CLFormulaParser parser, IProgressMonitor monitor) {
		for (final ConjectureCategory spc : pc.getConjectureCategories()) {
			processConjectureCategory(spc, parser);
			monitor.worked(1);
		}

		for (final Conjecture pd : pc.getConjectures()) {
			if (!pd.getId().empty() && !pd.getFormula().empty()) {
				handlePredicate(pd, parser);
			}
		}
	}

	private void processConjectureCategory(ConjectureCategory pc, CLFormulaParser parser) {
		for (final ConjectureCategory spc : pc.getConjectureCategories()) {
			processConjectureCategory(spc, parser);
		}

		for (final Conjecture pd : pc.getConjectures()) {
			if (!pd.getId().empty() && !pd.getFormula().empty()) {
				handlePredicate(pd, parser);
			}
		}
	}

	private void processFragmentCategory(FragmentCategory pc, CLFormulaParser parser, IProgressMonitor monitor) {
		for (final FragmentCategory spc : pc.getFragmentCategories()) {
			processFragmentCategory(spc, parser);
			monitor.worked(1);
		}

		for (final FragmentDefinition pd : pc.getFragments()) {
			if (!pd.getId().empty()) {
				handleFragment(pd, parser);
			}
		}
	}

	private void processFragmentCategory(FragmentCategory pc, CLFormulaParser parser) {
		for (final FragmentCategory spc : pc.getFragmentCategories()) {
			processFragmentCategory(spc, parser);
		}

		for (final FragmentDefinition pd : pc.getFragments()) {
			if (!pd.getId().empty()) {
				handleFragment(pd, parser);
			}
		}
	}

	private void handleFragment(FragmentDefinition pd, CLFormulaParser parser) {
		try {
			pd.getParsed().clear();
			final FormulaSource source = getSource(pd);
			if (source == null) {
				return;
			}
			final CLStatement new_parsed = parser.fragmentParse(source);
			if (new_parsed != null) {
				pd.setStatement(new_parsed);
			}
			source.mark();
		} catch (final Exception e) {
			pd.getParsed().clear();
			e.printStackTrace();
		}
	}

	private void processTransitionCategory(TransitionCategory pc, CLFormulaParser parser, IProgressMonitor monitor) {
		for (final TransitionCategory spc : pc.getTransitionCategories()) {
			processTransitionCategory(spc, parser);
			monitor.worked(1);
		}

		for (final TransitionDefinition pd : pc.getTransitions()) {
			if (!pd.getId().empty() && !pd.getPre().empty()) {
				handlePrecondition(pd, parser);
			}
			if (!pd.getId().empty() && !pd.getPost().empty()) {
				handlePostcondition(pd, parser);
			}
		}
	}

	private void processTransitionCategory(TransitionCategory pc, CLFormulaParser parser) {
		for (final TransitionCategory spc : pc.getTransitionCategories()) {
			processTransitionCategory(spc, parser);
		}

		for (final TransitionDefinition pd : pc.getTransitions()) {
			if (!pd.getId().empty() && !pd.getPre().empty()) {
				handlePrecondition(pd, parser);
			}
			if (!pd.getId().empty() && !pd.getPost().empty()) {
				handlePostcondition(pd, parser);
			}
		}
	}

	private void handlePostcondition(TransitionDefinition pd, CLFormulaParser parser) {
		try {
			pd.getPost().getParsed().clear();
			final FormulaSource source = getSource(pd.getPost());
			if (source == null) {
				return;
			}

			final CLExpression new_parsed = parser.parse(source);
			if (new_parsed != null && new_parsed.getType() != null) {
				pd.getPost().setParsed(new_parsed);
				isBoolean(source, new_parsed);
				hasPrimed(source, new_parsed);
			}
			source.mark();

		} catch (final Exception e) {
			pd.getPost().getParsed().clear();
			e.printStackTrace();
		}
	}

	private void handlePrecondition(TransitionDefinition pd, CLFormulaParser parser) {
		try {
			pd.getPre().getParsed().clear();
			final FormulaSource source = getSource(pd.getPre());
			if (source == null) {
				return;
			}
			final CLExpression new_parsed = parser.parse(source);
			if (new_parsed != null && new_parsed.getType() != null) {
				pd.getPre().setParsed(new_parsed);
				isPredicate(source, new_parsed);
				noPrimed(source, new_parsed);
			}
			source.mark();
		} catch (final Exception e) {
			pd.getPre().getParsed().clear();
			e.printStackTrace();
		}
	}

	private void processPredicateCategory(PredicateCategory pc, CLFormulaParser parser, IProgressMonitor monitor) {
		for (final PredicateCategory spc : pc.getPredicateCategories()) {
			processPredicateCategory(spc, parser);
			monitor.worked(1);
		}

		for (final PredicateDefinition pd : pc.getPredicates()) {
			if (!pd.getId().empty() && !pd.getFormula().empty()) {
				handlePredicate(pd, parser);
			}
		}
	}

	private void processPredicateCategory(PredicateCategory pc, CLFormulaParser parser) {
		for (final PredicateCategory spc : pc.getPredicateCategories()) {
			processPredicateCategory(spc, parser);
		}

		for (final PredicateDefinition pd : pc.getPredicates()) {
			if (!pd.getId().empty() && !pd.getFormula().empty()) {
				handlePredicate(pd, parser);
			}
		}
	}

	private void handlePredicate(IFormulaSource pd, CLFormulaParser parser) {
		try {
			pd.getParsed().clear();
			final FormulaSource source = getSource(pd);
			if (source == null) {
				return;
			}
			CLExpression new_parsed = null;
			new_parsed = parser.parse(source);
			// markError(source, 0, source.getText().length(), e.getMessage());
			if (new_parsed != null && new_parsed.getType() != null) {
				boolean changed = true;

				if (!pd.getParsed().empty()) {
					if (pd.getParsed().content().equals(new_parsed)) {
						changed = false;
					}
				}

				pd.setParsed(new_parsed);

				if (!(pd instanceof TermDefinition)) {
					isPredicate(source, new_parsed);
				}

				if (!(pd instanceof VerificationProperty) && !(pd instanceof PredicateDefinition)) {
					noPrimed(source, new_parsed);
				}

				if (pd instanceof PredicateDefinition) {
					final PredicateDefinition pred = (PredicateDefinition) pd;
					if (pred.getKind().content() == PredicateKind.INVARIANT) {
						noPrimedExcept(pd, source, new_parsed, "p");
					} else {
						noPrimed(source, new_parsed);
					}
				}

				// clear proof status
				if (pd instanceof Verifiable) {
					final Verifiable vp = (Verifiable) pd;

					if (changed) {
						vp.isValid().clear();
						vp.getValue().clear();
					}
				}

				// parse reports
				if (pd instanceof Conjecture) {
					final Conjecture vp = (Conjecture) pd;

					if (changed) {
						vp.isValid().clear();
						vp.getValue().clear();
					}

					for (final PropertyReport rep : vp.getReports()) {
						handlePropertyReport(rep, parser);
					}
				}

			}
			source.mark();

			if (pd instanceof IProverFragments) {
				final IProverFragments pf = (IProverFragments) pd;
				processRewriteRules(pf, parser.getModel().getRootContext(), parser, null);
				processInferenceRules(pf, parser.getModel().getRootContext(), parser, null);
			}
		} catch (final Throwable e) {
			pd.getParsed().clear();
			System.err.println("Failed building predicate " + ((INamed) pd).getId().content() + ": " + pd.getFormula().content() + " - "
					+ e.getMessage());
		}
	}

	private void handlePropertyReport(PropertyReport pd, CLFormulaParser parser) {
		try {
			final TypingContext extra = handlePropertyReportPredicate(pd, parser);
			if (!pd.getLocation().empty()) {
				handlePropertyReportText(pd, parser, pd.getLocation(), extra);
			}
			if (!pd.getDescription().empty()) {
				handlePropertyReportText(pd, parser, pd.getDescription(), extra);
			}
			if (!pd.getSolution().empty()) {
				handlePropertyReportText(pd, parser, pd.getSolution(), extra);
			}
		} catch (final Exception e) {
			pd.getParsed().clear();
			e.printStackTrace();
		}
	}

	private void handlePropertyReportText(PropertyReport pd, CLFormulaParser parser, IParseable parseable, TypingContext extra) {
		try {
			if (parseable.getFormula().empty()) {
				return;
			}

			final Collection<String> values = new HashSet<>();
			final FormulaSource source = getSource(parseable);
			source.clear();

			final String template = source.getText();

			int pos0 = template.indexOf("${");
			int pos1 = template.indexOf("}$", pos0);

			while (pos0 >= 0 && pos1 > pos0) {
				final Map<String, String> paramsmap = new HashMap<>();
				final String[] tparts = GenericReport.parseTemplateParts(template, pos0, paramsmap);
				final int[] extent = GenericReport.parseTemplateExpressionPart(template, pos0, paramsmap);
				parser.parse(source, extent[0], extent[1], false, extra);

				if (tparts[0] != null) {
					if (values.contains(tparts[0])) {
						markError(source, extent[0], extent[1], "Duplicate expression identifier - " + tparts[0]);
					}

					values.add(tparts[0]);
				}

				source.mark(extent[0], extent[1]);

				pos0 = template.indexOf("${", pos1);
				pos1 = template.indexOf("}$", pos0);
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private TypingContext handlePropertyReportPredicate(PropertyReport pd, CLFormulaParser parser) {
		try {
			final SDARuntimeExecutionContext context = parser.getModel();
			final TypingContext typing = context.getRootContext();

			pd.getParsed().clear();
			final FormulaSource source = getSource(pd);
			if (source == null) {
				return null;
			}
			final CLExpression parsed = parser.parseOnly(source);
			final TypingContext extra = new TypingContext(typing);

			if (parsed != null) {

				if (parsed.getFreeIdentifiers() != null) {
					for (final String name : parsed.getFreeIdentifiers()) {
						if (typing.getType(name) == null) {
							extra.addSymbol(name, new CLTypeAny(null), SYMBOL_CLASS.IDENTIFIER);
						}
					}
				}

				// System.out.println("fresh names:" +
				// extra.getNewSymbols());

				parsed.type(extra, CLTypeBool.INSTANCE);
				extra.bakeTypes();
				// extra.debug_print_typeSolutions();
				pd.setParsed(parsed);

				if (parsed.getType() != null) {
					isPredicate(source, parsed);
					noPrimed(source, parsed);
				}

			}
			source.mark();
			return extra;
		} catch (final Throwable e) {
			pd.getParsed().clear();
			e.printStackTrace();
		}
		return null;
	}

	private void processIdentifierCategory(IdentifierCategory pc, CLFormulaParser parser, IProgressMonitor monitor) {
		for (final IdentifierCategory spc : pc.getIdentifierCategories()) {
			processIdentifierCategory(spc, parser);
			monitor.worked(1);
		}

		for (final IdentifierDefinition pd : pc.getIdentifiers()) {
			if (!pd.getId().empty()) {
				handleIdentifier(pd, parser);
			}
		}
	}

	private void processIdentifierCategory(IdentifierCategory pc, CLFormulaParser parser) {
		for (final IdentifierCategory spc : pc.getIdentifierCategories()) {
			processIdentifierCategory(spc, parser);
		}

		for (final IdentifierDefinition pd : pc.getIdentifiers()) {
			if (!pd.getId().empty()) {
				handleIdentifier(pd, parser);
			}
		}
	}

	private void handleIdentifier(IdentifierDefinition pd, CLFormulaParser parser) {
		if (pd.getKind().content() == IdentifierKind.CONSTANT) {
			handleConstantIdentifier(pd, parser);
		} else if (pd.getKind().content() == IdentifierKind.MODEL) {
			handleModelIdentifier(pd, parser);
		} else if (pd.getKind().content() == IdentifierKind.TYPE) {
			handleTypeIdentifier(pd, parser);
		} else if (pd.getKind().content() == IdentifierKind.ENUM) {
			handleEnumIdentifier(pd, parser);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void handleEnumIdentifier(IdentifierDefinition pd, CLFormulaParser parser) {

		try {
			final String id = pd.getId().content();

			pd.getParsed().clear();
			final FormulaSource source = getSource(pd);
			if (source == null) {
				return;
			}

			final CLExpression new_parsed = parser.parseOnly(source);
			if (new_parsed != null && new_parsed.getTag() == alphabet.SETC) {
				// add set as a new given type
				final SDARuntimeExecutionContext context = parser.getModel();
				final TypingContext ctx = context.getRootContext();

				final boolean redeclaration = ctx.hasGivenType(id);

				CLEnumType type;
				final String idRedec = id + ".redec";
				final String idOrig = id + ".orig";

				if (!redeclaration) {
					type = new CLEnumType(id, null);
					ctx.addType(type);
					ctx.addSymbol(id, new CLPowerType(type), SYMBOL_CLASS.CONSTANT);
				} else if (ctx.resolveType(id) instanceof CLEnumType) {
					type = (CLEnumType) ctx.resolveType(id);
					ctx.addSymbol(idRedec, new CLPowerType(type), SYMBOL_CLASS.CONSTANT);
					ctx.addSymbol(idOrig, new CLPowerType(type), SYMBOL_CLASS.CONSTANT);
				} else {
					markError(source, new_parsed, "Invalid type redeclaration");
					return;
				}

				final CLMultiExpression me = (CLMultiExpression) new_parsed;
				pd.setParsed(new_parsed);

				final Collection<String> members = new HashSet<>();
				if (redeclaration) {
					for (final String m : type.getMembers()) {
						members.add(m);
					}
				}

				final List<Object> set = new ArrayList<>(); // set of all enumerated elements
				final List<Object> newSet = new ArrayList<>(); // set of newly declared enumerated

				if (redeclaration && context.getValue(id) instanceof BSet) {
					final BSet _bset = (BSet) context.getValue(id);
					set.addAll(_bset.asCollection());
				}

				for (int i = 0; i < me.getParts().size(); i++) {
					final CLIdentifier p = (CLIdentifier) me.getParts().byIndex(i);

					if (!redeclaration) {
						isFresh(source, p, ctx);
						ctx.addSymbol(p.getName(), type, SYMBOL_CLASS.ENUM_CONSTANT);

						members.add(p.getName());
						final BProto el = new BProto(type, p.getName());
						context.getStateContext().defineNew(p.getName(), el);
						set.add(el);
					} else {
						final SYMBOL_CLASS sclass = ctx.getSymbolClass(p.getName());
						if (sclass != SYMBOL_CLASS.NONE) {
							if (sclass != SYMBOL_CLASS.ENUM_CONSTANT) {
								markError(source, new_parsed, "Invalid type redeclaration: mismatch on " + p.getName());
								return;
							}

							if (!(ctx.getType(p.getName()) instanceof CLEnumType)) {
								markError(source, new_parsed, "Invalid type redeclaration: mismatch on " + p.getName() + " - wrong type "
										+ ctx.getType(p.getName()));
								return;
							}

							final CLEnumType ptype = (CLEnumType) ctx.getType(p.getName());
							if (!ptype.getName().equals(type.getName())) {
								markError(source, new_parsed, "Invalid type redeclaration: mismatch on " + p.getName() + " - wrong type "
										+ ctx.getType(p.getName()));
								return;
							}

							// already declared, ignore other than add to new
							// set
							newSet.add(context.getValue(p.getName()));
						} else {

							// declare and add to both sets
							ctx.addSymbol(p.getName(), type, SYMBOL_CLASS.ENUM_CONSTANT);
							members.add(p.getName());
							final BProto el = new BProto(type, p.getName());
							context.getStateContext().defineNew(p.getName(), el);
							set.add(el);
							newSet.add(el);
						}
					}
				}
				type.setMembers(members.toArray(new String[members.size()]));

				final BSet bset = new BSet(set);
				final BSet bnewSet = new BSet(newSet);

				if (redeclaration) {
					context.getStateContext().defineNew(idRedec, bnewSet);
					context.getStateContext().defineNew(idOrig, bset.setminus(bset.setminus(bnewSet)));
					// redefine set value
					context.setValue(id, bset);
				} else {
					// set set value
					context.getStateContext().defineNew(id, bset);
				}

				noPrimed(source, new_parsed);
			}
			source.mark();

		} catch (final Exception e) {
			pd.getParsed().clear();
			e.printStackTrace();
		}
	}

	private void handleTypeIdentifier(IdentifierDefinition pd, CLFormulaParser parser) {

		try {
			final SDARuntimeExecutionContext context = parser.getModel();
			final String id = pd.getId().content();

			if (context.getRootContext().hasGivenType(id)) {
				System.err.println("Shadowing of type " + id);
				return;
			}

			final CLGivenType type = new CLGivenType(id);
			context.getRootContext().addType(type);
			context.getRootContext().addSymbol(id, new CLPowerType(type), SYMBOL_CLASS.CONSTANT);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void handleModelIdentifier(IdentifierDefinition pd, CLFormulaParser parser) {
		try {
			pd.getParsed().clear();
			final FormulaSource source = getSource(pd);
			if (source == null) {
				return;
			}

			final CLExpression new_parsed = parser.parse(source);
			if (new_parsed != null && new_parsed.getType() != null) {
				if (new_parsed.getType().isSet()) {
					final CLType type = new_parsed.getType().baseType();
					if (type != null) {
						pd.setParsed(new_parsed);
						isConstant(source, new_parsed, parser.getModel().getRootContext());
						noPrimed(source, new_parsed);
						parser.getModel().getRootContext().addSymbol(pd.getId().content(), type, SYMBOL_CLASS.IDENTIFIER);
						switch (new_parsed.getTag()) {
						case alphabet.OP_PFUN:
						case alphabet.OP_PINJ:
							parser.getModel().getRootContext().setRelationKind(pd.getId().content(), RELATION_KIND.PARTIAL_FUNCTION);
							break;
						case alphabet.OP_REL:
							parser.getModel().getRootContext().setRelationKind(pd.getId().content(), RELATION_KIND.RELATION);
							break;
						}
					}
				} else {
					source.add(0, source.getText().length(), "Must be a set expression");
				}
			}
			source.mark();
		} catch (final Exception e) {
			pd.getParsed().clear();
			e.printStackTrace();
		}
	}

	private void handleConstantIdentifier(IdentifierDefinition pd, CLFormulaParser parser) {
		try {
			pd.getParsed().clear();
			final FormulaSource source = getSource(pd);
			if (source == null) {
				return;
			}
			final CLExpression new_parsed = parser.parse(source);
			if (new_parsed != null && new_parsed.getType() != null) {
				pd.setParsed(new_parsed);
				final TypingContext ctx = parser.getModel().getRootContext();
				ctx.addSymbol(pd.getId().content(), new_parsed.getType(), SYMBOL_CLASS.CONSTANT);

				// set value
				parser.getModel().getStateContext().defineNew(pd.getId().content(), new_parsed.getValueInterpreted(parser.getModel()));
				isConstant(source, new_parsed, ctx);
				noPrimed(source, new_parsed);

//				System.out.println("Defined " + pd.getId().content() + " to be " + new_parsed.getValue(parser.getModel()) +
//						"; model: " + parser.getModel().hashCode());
			}
			source.mark();
		} catch (final Exception e) {
			pd.getParsed().clear();
			e.printStackTrace();
		}
	}

	static void isPredicate(FormulaSource source, CLExpression new_parsed) {
		if (!new_parsed.getType().equals(CLTypeBool.INSTANCE)) {
			markError(source, 0, source.getText().length(), "Must be a predicate");
		}
	}

	static void isConstant(FormulaSource source, CLExpression new_parsed, TypingContext ctx) {
		// check is constant
		for (final String fi : new_parsed.getFreeIdentifiers()) {
			if (!ctx.getSymbolClass(fi).isConstant()) {
				markError(source, 0, source.getText().length(), "Must be a constant expression");
				break;
			}
		}
	}

	static void hasPrimed(FormulaSource source, CLExpression new_parsed) {
		if (new_parsed.getPrimedIdentifiers().isEmpty()) {
			markError(source, 0, source.getText().length(), "Must contain primed identifiers");
		}
	}

	static void noPrimed(FormulaSource source, CLExpression new_parsed) {
		if (!new_parsed.getPrimedIdentifiers().isEmpty()) {
			markError(source, 0, source.getText().length(), "Must not contain primed identifiers");
		}
	}

	static void noPrimedExcept(IParseable element, FormulaSource source, CLExpression new_parsed, String... quals) {
		for (final String ii : new_parsed.getPrimedIdentifiers()) {
			boolean pass = false;
			for (final String t : quals) {
				if (CLUtils.isQualified(ii, t)) {
					pass = true;
				}
			}

			if (!pass) {
				final CLIdentifier el = new_parsed.resolveIdentifiers(ii);
				if (el != null) {
					markError(element, el, "Must not contain primed identifiers except qualifiers " + Arrays.toString(quals));
				}
			}
		}
	}

	static void isBoolean(FormulaSource source, CLExpression new_parsed) {
		if (!new_parsed.getType().equals(CLTypeBool.INSTANCE)) {
			markError(source, 0, source.getText().length(), "Must be a predicate");
		}
	}

	static void markError(FormulaSource source, CLExpression new_parsed, String message) {
		markError(source, 0, source.getText().length(), message);
	}

	static private void markError(IParseable source, int start, int end, String message) {
		if (!source.getFormulaSource().empty()) {
			source.getFormulaSource().content().add(start, end, message);
		}
	}

	static private void markError(FormulaSource source, int start, int end, String message) {
		if (source != null) {
			source.add(start, end, message);
		}
	}

	static public void markError(IParseable source, CLElement el, String message) {
		if (!source.getFormulaSource().empty()) {
			if (el != null && el.getLocation() != null) {
				source.getFormulaSource().content().add(el.getLocation().getStart(), el.getLocation().getEnd(), message);
			} else {
				source.getFormulaSource().content().add(0, source.getFormulaSource().content().getText().length(), message);
			}
		}
	}

	// private void markError(IFormulaSource source, int start, int end, String
	// message, String tag) {
	// if (!source.getFormulaSource().empty())
	// source.getFormulaSource().content().add(start, end, message, tag);
	// }

	static FormulaSource getSource(IParseable id) {
		if (id.getFormulaSource().empty()) {
			if (id.getFormula().empty()) {
				return null;
			}
			final FormulaSource fs = new FormulaSource(id.getFormula().content());
			id.setFormulaSource(fs);
			return fs;
		} else {
			final FormulaSource fs = id.getFormulaSource().content();
			if (fs.getText() == null) {
				if (id.getFormula().empty()) {
					return null;
				}

				fs.setText(id.getFormula().content());
			}
			return fs;

		}
	}

	static void isFresh(FormulaSource source, CLIdentifier name, TypingContext ctx) {
		if (ctx.getType(name.getName()) != null) {
			if (name.getLocation() != null) {
				source.add(name.getLocation().getStart(), name.getLocation().getEnd(), "Must be a fresh identifier");
			} else {
				source.add(0, -1, "Identifier \"" + name + "\" is declared elsewhere");
			}
		}
	}
}