package uk.ac.ncl.safecap.xdata.verification.registry;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.sapphire.Element;
//import org.eclipse.swt.widgets.Display;

import uk.ac.ncl.prime.sim.lang.CLAtomicExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.xdata.provers.ConstantFoldingTopDown;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.ConjectureCategory;
import uk.ac.ncl.safecap.xdata.verification.DataReference;
import uk.ac.ncl.safecap.xdata.verification.IVerificationSource;
import uk.ac.ncl.safecap.xdata.verification.PredicateCategory;
import uk.ac.ncl.safecap.xdata.verification.PredicateDefinition;
import uk.ac.ncl.safecap.xdata.verification.PredicateKind;
import uk.ac.ncl.safecap.xdata.verification.PropertyReport;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.TransitionCategory;
import uk.ac.ncl.safecap.xdata.verification.TransitionDefinition;
import uk.ac.ncl.safecap.xdata.verification.VerificationProperty;
import uk.ac.ncl.safecap.xdata.verification.core.ElementVisitor;
import uk.ac.ncl.safecap.xdata.verification.core.InvariantInfo;
import uk.ac.ncl.safecap.xdata.verification.core.RuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.core.VerificationBasePlugin;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;
import uk.ac.ncl.safecap.xdata.verification.registry.ModelBuilderJob.JOBKIND;
import uk.ac.ncl.safecap.xdata.verification.rules.IProverFragments;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransition;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransitionPOs;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionCluster;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionClusterPart;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionContainer;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionWrapper;

public class ModelPOGenerator {
	private static List<POModule<PredicateDefinition>> poPredicateModules;
	private static List<POModule<ISymbolicTransition>> poTransitionModules;
	private static List<POModule<PropertyReport>> poPropertyReportModules;

	static {
		poPredicateModules = new ArrayList<>();
		poPredicateModules.add(LemmaPOModule.INSTANCE);

		poTransitionModules = new ArrayList<>();

		poPropertyReportModules = new ArrayList<>();
		poPropertyReportModules.add(ReportPredicatePOModule.INSTANCE);
	}

	private ModelPOGeneratorJob currentJob = null;
	private ISafeRunner runner;

	public ModelPOGenerator(ISafeRunner runner) {
		this.runner = runner;
	}

	public void runPOGenerator(Element element, InvariantInfo[] invariants) {
		JOBKIND kind;

		if (element instanceof PredicateDefinition) {
			kind = JOBKIND.PREDICATE;
		} else if (element instanceof TransitionDefinition) {
			kind = JOBKIND.TRANSITION;
		} else {
			kind = JOBKIND.ALL;
		}

		if (currentJob != null && (kind == JOBKIND.ALL || currentJob.getElement() == element)) {
			currentJob.cancel();
		}

		final SDAContext dataContext = SDAUtils.getDataContext((RootCatalog) element.root());
		if (dataContext == null) {
			System.err.println("POG: no data context, abort");
			return;
		}
		final TypingContext typingContext = dataContext.getRootRuntimeContext().getRootContext();
		if (typingContext == null) {
			System.err.println("POG: no typing context, abort");
			return;
		}

		if (kind != JOBKIND.TRANSITION) {
			rebuildPOModules(dataContext.getRootRuntimeContext(), (RootCatalog) element.root(), invariants);
		}

		final ModelPOGeneratorJob job = new ModelPOGeneratorJob(dataContext.getRootRuntimeContext(), typingContext, element);
		job.schedule(50L);
		currentJob = job;
	}

	private void rebuildPOModules(RuntimeExecutionContext model, RootCatalog root, InvariantInfo[] invariants) {
		rebuildPOInvariantModules(model, root, invariants);
	}

	private void rebuildPOInvariantModules(RuntimeExecutionContext model, RootCatalog root, InvariantInfo[] invariants) {
		// remove all invariant modules
		final Collection<POModule<ISymbolicTransition>> toRemove = new HashSet<>();
		for (final POModule<ISymbolicTransition> pom : poTransitionModules) {
			if (pom instanceof InvariantPOModule) {
				toRemove.add(pom);
			}
		}

		poTransitionModules.removeAll(toRemove);

		for (final InvariantInfo inv : invariants) {
			poTransitionModules.add(new InvariantPOModule(model, inv));
		}
	}

	class ModelPOGeneratorJob extends Job {
		private final Element element;
		private final TypingContext typingContext;
		private final SDARuntimeExecutionContext model;
		private final RootCatalog catalog;

		public ModelPOGeneratorJob(SDARuntimeExecutionContext model, TypingContext typingContext, Element element) {
			super("SafeCap PO generator");
			this.typingContext = typingContext;
			this.element = element;
			this.model = model;
			catalog = (RootCatalog) element.root();
		}

		public Element getElement() {
			return element;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			if (element instanceof PredicateDefinition) {
				final PredicateDefinition pd = (PredicateDefinition) element;
				processLemma(pd);
			} else if (element instanceof TransitionDefinition) {
				final TransitionDefinition tr = (TransitionDefinition) element;
				processTransition(tr);
			} else if (element instanceof PropertyReport) {
				final PropertyReport tr = (PropertyReport) element;
				processPropertyReport(tr);
			} else if (element instanceof RootCatalog) {
				final RootCatalog root = (RootCatalog) element;
				for (final PredicateCategory pc : root.getPredicateCategories()) {
					processPredicateCategory(pc);
				}
				for (final TransitionCategory tc : root.getTransitionCategories()) {
					processTransitionCategory(tc);
				}
				for (final ConjectureCategory tc : root.getConjectureCategories()) {
					processConjectureCategory(tc);
				}

				processImportedTransitions(root, monitor);
			}
			return Status.OK_STATUS;
		}

		private void processImportedTransitions(RootCatalog root, final IProgressMonitor monitor) {
			final SDAContext sdaContext = SDAUtils.getDataContext(root);
			final TransitionDefinition td = TransitionDefinition.TYPE.instantiate();
			final ISafeRunner old = runner;
			runner = TransparentRunner.INSTANCE;
			// Disposable suspend = td.suspend();

			for (final DataReference tdr : sdaContext.getTransitionReferences()) {
				if (!tdr.disposed()) {
					final TransitionContainer tc = sdaContext.getTransitionContainer(tdr);
					processImportedTransitions(td, tc, monitor);
					final File file = getInputFile(tdr);

					try {
						old.execute(new Runnable() {
							@Override
							public void run() {
								VerificationBasePlugin.getTransitionCache().fireChange(file);
							}

						});
					} catch (final Exception e) {
						e.printStackTrace();
					}
				}

			}
			runner = old;
			// suspend.dispose();
		}

		private File getInputFile(DataReference tdr) {
			File file = tdr.getPath().content().toFile();
			if (!file.exists()) {
				file = SDAUtils.resolveResource(file);
			}
			return file;
		}

		private void processImportedTransitions(final TransitionDefinition td, TransitionContainer tc, final IProgressMonitor monitor) {
//			for (TransitionRaw t : tc.getTransitions())
//				processImportedTransition(td, t);

			monitor.beginTask("Imported transitions", tc.getClusters().size());
			for (final TransitionCluster t : tc.getClusters()) {
				processImportedTransition(td, t);
				monitor.worked(1);
			}
		}

		private void processImportedTransition(final TransitionDefinition td, TransitionCluster tc) {
			final Collection<ManagedProofObligation> pos = new ArrayList<>();
			tc.setPos(pos);
			for (final POModule<ISymbolicTransition> module : poTransitionModules) {
				if (module instanceof InvariantPOModule) {
					final InvariantPOModule inv = (InvariantPOModule) module;
					if (!tc.isIncluded(inv.getInvariantInfo())) {
						//System.out.println("Suppressed invariant " + inv.getInvariantInfo().name + " for " + tc.getPath());
						continue;
					}
				}
				processModuleForCluster(td, tc, module);
				// System.out.print("*");
				// System.out.flush();
			}

			for (final ManagedProofObligation po : tc.getPos()) {
				assert po != null;
			}
		}

		private void processModuleForCluster(TransitionDefinition td, TransitionCluster tc, POModule<ISymbolicTransition> module) {
			if (tc.getParts() != null) {
				// 1. filter
				final TransitionCluster filtered = tc.filter(module.getPrimed());
				//final TransitionCluster filtered = tc;
				// 2. apply
				if (filtered != null) {
					for (final TransitionClusterPart part : filtered.getParts()) {
						if (part.isValid()) {
							processModule(td, part, tc, module);
						}
					}
				}
			}
		}

		private void processModule(final TransitionDefinition td, final ITransition tr, ITransitionPOs pos,
				final POModule<ISymbolicTransition> module) {
//			System.out.println("processing inv po " + module + " for transition " + tr.getClass().getSimpleName() + " "
//					+ tr.getName());

			if (module.isApplicable(tr.getParsed())) {
				final CLExpression goal = module.getGoal(typingContext, tr.getParsed());
				final Collection<CLExpression> hyp = module.getHypotheses(typingContext, tr.getParsed());
				final ManagedProofObligation mpo = new ManagedProofObligation(typingContext, module.getName(tr.getParsed()), hyp, goal);
				mpo.setProverFragments(module.getIProverFragments(tr.getParsed()));
				pos.getPos().add(mpo);
			}
//
//
//			property.setProofScript(mpo);
//
//			td.getProperties().clear();
//			td.setId(tr.getName());
//
//			assert (tr.getName() != null);
//
//			String overallPre = getOverallPredicate(tr.getPreconditions());
//			FormulaSource fsPre = new FormulaSource(overallPre);
//
//			CLExpression pre = tr.getParsed().getOverallPrecondition();
//			pre.type(typingContext, CLTypeBool.INSTANCE);
//
//			td.getPre().setFormula(overallPre);
//			td.getPre().setFormulaSource(fsPre);
//			td.getPre().setParsed(tr.getParsed().getOverallPrecondition());
//
//			String overallPost = getOverallPredicate(tr.getPostconditions());
//			FormulaSource fsPost = new FormulaSource(overallPost);
//
//			td.getPost().setFormula(overallPost);
//			td.getPost().setFormulaSource(fsPost);
//			td.getPost().setParsed(tr.getParsed().getOverallPostcondition());
//
//			processModuleTransition(td, module);
//
//			for (VerificationProperty prop : td.getProperties()) {
//				// ManagedProofObligation tpo = new ManagedProofObligation(typingContext, prop);
//				if (!prop.getProofScript().empty())
//					pos.getPos().add(prop.getProofScript().content());
//				// pos.getPos().add(tpo);
//			}

			// td.dispose();
		}

//		private String getOverallPredicate(List<LocatedString> parts) {
//			StringBuilder sb = new StringBuilder();
//
//			for (int i = 0; i < parts.size(); i++) {
//				if (i > 0)
//					sb.append(" and ");
//
//				sb.append(parts.get(i));
//			}
//
//			return sb.toString();
//		}

		private void processConjectureCategory(ConjectureCategory pc) {
			for (final ConjectureCategory subpc : pc.getConjectureCategories()) {
				processConjectureCategory(subpc);
			}

			for (final Conjecture pr : pc.getConjectures()) {
				processConjecture(pr);
			}

		}

		private void processConjecture(Conjecture pr) {
			for (final PropertyReport rep : pr.getReports()) {
				processPropertyReport(rep);
			}
		}

		private void processPropertyReport(PropertyReport rp) {
			if (rp.getFormulaSource().empty() || rp.getFormulaSource().content().hasErrors()) {
				return;
			}
			clearPOs(rp);
			// System.out.println("processing po for property report " + rp.getId());
			for (final POModule<PropertyReport> module : poPropertyReportModules) {
				processModule(rp, module);
			}
		}

		private void processPredicateCategory(PredicateCategory pc) {
			for (final PredicateCategory subpc : pc.getPredicateCategories()) {
				processPredicateCategory(subpc);
			}

			for (final PredicateDefinition pr : pc.getPredicates()) {
				processLemma(pr);
			}
		}

		private void processTransitionCategory(TransitionCategory pc) {
			for (final TransitionCategory subpc : pc.getTransitionCategories()) {
				processTransitionCategory(subpc);
			}

			for (final TransitionDefinition tr : pc.getTransitions()) {
				processTransition(tr);
			}
		}

		private void processTransition(TransitionDefinition pd) {
			if (pd.getPre().getFormulaSource().empty() || pd.getPre().getFormulaSource().content().hasErrors()
					|| pd.getPost().getFormulaSource().empty() || pd.getPost().getFormulaSource().content().hasErrors()) {
				return;
			}
			clearPOs(pd);
			// System.out.println("processing po for transition " + pd.getId());
			for (final POModule<ISymbolicTransition> module : poTransitionModules) {
				if (module instanceof InvariantPOModule) {
					final InvariantPOModule inv = (InvariantPOModule) module;
					final TransitionWrapper ss = new TransitionWrapper(pd);
					if (inv.isApplicable(ss)) {
						_processModule(pd, ss, module);
					}
				} else {
					processModule(pd, module);
				}
			}

		}

		private void processLemma(PredicateDefinition pd) {
			if (pd.getFormulaSource().empty() || pd.getFormulaSource().content().hasErrors()) {
				return;
			}
			clearPOs(pd);
			// System.out.println("processing po for predicate " + pd.getId());
			for (final POModule<PredicateDefinition> module : poPredicateModules) {
				processModule(pd, module);
			}
		}

		private void clearPOs(final IVerificationSource pd) {
			try {
				runner.execute(new Runnable() {
					@Override
					public void run() {
						pd.getProperties().clear();
					}
				});
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		private void processModule(final IVerificationSource pd, final POModule module) {
			if (module.isApplicable(pd)) {
				_processModule(pd, module);
			}
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		private void _processModule(final IVerificationSource pd, final POModule module) {
			final String name = module.getName(pd);
			final CLExpression goal = module.getGoal(typingContext, pd);
			if (goal.equals(CLAtomicExpression.TRUE)) {
				return;
			}
			final Collection<CLExpression> hyp = module.getHypotheses(typingContext, pd);
			if (pd instanceof PredicateDefinition) {
				collectHypotheses((PredicateDefinition) pd, goal, hyp);
			}
			emit(pd, name, hyp, goal, module.getIProverFragments(pd));
		}

		private void _processModule(final TransitionDefinition td, final ISymbolicTransition pd,
				final POModule<ISymbolicTransition> module) {
			final String name = module.getName(pd);
			final CLExpression goal = module.getGoal(typingContext, pd);
			if (goal.equals(CLAtomicExpression.TRUE)) {
				return;
			}
			final Collection<CLExpression> hyp = module.getHypotheses(typingContext, pd);
			collectHypotheses(td, goal, hyp);
			emit(td, name, hyp, goal, module.getIProverFragments(pd));
		}

		private void collectHypotheses(final PredicateDefinition predicate, CLExpression goal, Collection<CLExpression> hyp) {
			final Set<String> freeIdentifiers = new HashSet<>();
			freeIdentifiers.addAll(goal.getFreeIdentifiers());
			for (final CLExpression ce : hyp) {
				freeIdentifiers.addAll(ce.getFreeIdentifiers());
			}

			VisitorUtils.visitAllPredicates(catalog, new ElementVisitor<PredicateDefinition>() {
				@Override
				public Object visit(PredicateDefinition pre, Object userData) {
					if (pre == predicate) {
						return pre;
					}
					final HypContext hypctx = (HypContext) userData;
					processHyp(hypctx.freeIdentifiers, hypctx.hyp, pre);
					return null;
				}
			}, new HypContext(hyp, freeIdentifiers));
		}

		private void collectHypotheses(final TransitionDefinition transition, CLExpression goal, Collection<CLExpression> hyp) {
			final Set<String> freeIdentifiers = new HashSet<>();
			freeIdentifiers.addAll(goal.getFreeIdentifiers());
			// for (CLExpression ce : hyp)
			// freeIdentifiers.addAll(ce.getFreeIdentifiers());

			VisitorUtils.visitAllPredicates(catalog, new ElementVisitor<PredicateDefinition>() {
				@Override
				public Object visit(PredicateDefinition pre, Object userData) {
					if (pre.getKind().content() == PredicateKind.AXIOM || pre.getKind().content() == PredicateKind.LEMMA) {
						final HypContext hypctx = (HypContext) userData;
						processHyp(hypctx.freeIdentifiers, hypctx.hyp, pre);
					}
					return null;
				}
			}, new HypContext(hyp, freeIdentifiers));
		}

		class HypContext {
			Collection<CLExpression> hyp;
			Set<String> freeIdentifiers;

			public HypContext(Collection<CLExpression> hyp, Set<String> freeIdentifiers) {
				super();
				this.hyp = hyp;
				this.freeIdentifiers = freeIdentifiers;
			}
		}

		private void processModule(final TransitionDefinition tr, final POModule<TransitionDefinition> module, boolean eager) {
			// System.out.println("processing inv po " + module + " for predicate " +
			// tr.getId());
			if (module.isApplicable(tr)) {
				// System.out.println("doing po " + module + " for predicate " + tr.getId());
				final String name = module.getName(tr);
				final CLExpression goal = module.getGoal(typingContext, tr);
				if (goal.equals(CLAtomicExpression.TRUE)) {
					return;
				}

				final Collection<CLExpression> hyp = module.getHypotheses(typingContext, tr);

				if (eager) {
					collectHypotheses(tr, goal, hyp);
				}
				emit(tr, name, hyp, goal, module.getIProverFragments(tr));
			}

		}

		private void processHyp(Set<String> freeIdentifiers, Collection<CLExpression> hyp, PredicateDefinition now) {
			if (!now.validation().ok() || now.getParsed().empty()) {
				return;
			}

			CLExpression nexpr = now.getParsed().content();
			final ConstantFoldingTopDown folding = new ConstantFoldingTopDown(model);
			nexpr = folding.doFolding(nexpr);

			if (CLUtils.isRelevant(freeIdentifiers, nexpr)) {
				// freeIdentifiers.addAll(now.getParsed().content().getFreeIdentifiers());
				if (!hyp.contains(now.getParsed().content())) {
					hyp.add(now.getParsed().content());
				}
			}

		}

		private void emit(final IVerificationSource pd, final String name, Collection<CLExpression> hyp, final CLExpression goal,
				final IProverFragments proverFragments) {
			if (goal != null && goal.getType() != null && goal.getType().equals(CLTypeBool.INSTANCE)) {
				final CLExpression hype;
				if (hyp != null && !hyp.isEmpty()) {
					if (hyp.size() > 1) {
						hype = new CLMultiExpression(alphabet.OP_AND, hyp, null);
						hype.type(typingContext, CLTypeBool.INSTANCE);
					} else {
						hype = hyp.iterator().next();
					}
				} else {
					hype = CLAtomicExpression.TRUE;
				}

				try {
					runner.execute(new Runnable() {
						@Override
						public void run() {
							// long t0 = System.currentTimeMillis();
							final VerificationProperty property = getProperty(pd, name);
							property.getHypotheses().setFormula(formattedHyp(hype));
							property.getHypotheses().setParsed(hype);
							property.setFormula(goal.asString());
							property.setParsed(goal);

							final ManagedProofObligation mpo = new ManagedProofObligation(typingContext, property);
							mpo.setProverFragments(proverFragments);
							property.setProofScript(mpo);

//							 System.out.println("Emitting po " + name);
//
//							 System.out.println("\tHYP: " + formattedHyp(hype));
//							 System.out.println("\tGOAL: " + goal); System.out.println("\n");

							// long t1 = System.currentTimeMillis();
							// System.out.println("Emitting po time " + (t1 -
							// t0));
						}
					});
				} catch (final Exception e) {
					e.printStackTrace();
				}

			}
		}

		private String formattedHyp(CLExpression hyp) {
			if (hyp.getTag() == alphabet.OP_AND) {
				final StringBuilder sb = new StringBuilder();
				final CLMultiExpression me = (CLMultiExpression) hyp;
				for (int i = 0; i < me.getParts().size(); i++) {
					sb.append(me.getParts().byIndex(i).asString());
					if (i < me.getParts().size() - 1) {
						sb.append(" and\n");
					}
				}
				return sb.toString();
			} else {
				return hyp.asString();
			}
		}

		private VerificationProperty getProperty(IVerificationSource pd, String name) {
			for (final VerificationProperty vp : pd.getProperties()) {
				if (!vp.getId().empty() && vp.getId().content().equals(name)) {
					return vp;
				}
			}

			final VerificationProperty vp = pd.getProperties().insert();
			vp.setId(name);
			return vp;
		}
	}
}
