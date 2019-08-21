package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.InjectorException;
import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.provers.NativeEvaluationVerifier;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;
import uk.ac.ncl.safecap.xdata.provers.VerificationTool;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xmldata.IXFunction;

public abstract class EIBaseExperiment {
	protected static final String SIGNALLING_TAG = "signalling";	
	protected boolean collectSamples = true;
	protected RootCatalog catalog;
	protected int runs;
	protected long timeLimit = 25000;
	protected SDAContext data;
	protected Collection<ICondition> conjectures;
	protected IErrorInjectorManager errorInjector;
	protected SDARuntimeExecutionContext model;
	protected Random rangen;
	protected boolean filterBaseInvalid = true; // flag to filter out
												// conjectures invalid in
												// base data set

	public EIBaseExperiment(RootCatalog catalog, int runs) throws InjectorException {
		this.catalog = catalog;
		this.runs = runs;
		data = SDAUtils.getDataContext(catalog);
		rangen = new Random();
		model = catalog.getContext().content().getRootRuntimeContext();
	}

	public boolean isCollectSamples() {
		return collectSamples;
	}

	public void setCollectSamples(boolean collectSamples) {
		this.collectSamples = collectSamples;
	}

	public long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
	}

	public boolean isFilterBaseInvalid() {
		return filterBaseInvalid;
	}

	public void setFilterBaseInvalid(boolean filterBaseInvalid) {
		this.filterBaseInvalid = filterBaseInvalid;
	}

	protected void setConjectures(Collection<ICondition> conjectures) {
		this.conjectures = conjectures;
	}

	public Collection<ICondition> getConjectures() {
		return conjectures;
	}

	public EIResult execute(IVerificationProgressMonitor monitor) {
		final EIResult eiResult = new EIResult();
		if (conjectures.isEmpty()) {
			eiResult.setError("No applicable conjectures");
			return eiResult;
		}

		try {
			model.setErrorInjector(NullInjector.INSTANCE);

			if (filterBaseInvalid) {
				monitor.report("Base run");
				monitor.beginTask("Preparing conditions", conjectures.size());
				System.out.println("Conjectures at start: " + ppConj(conjectures));
				final Collection<ICondition> trueConj = new ArrayList<>();
				final Collection<ICondition> patchedConj = new ArrayList<>();
				for (final ICondition c : conjectures) {
					monitor.subTask("Processing " + c.getName());
					if (VerificationTool.prove(NativeEvaluationVerifier.INSTANCE, c)) {
						trueConj.add(c);
					} else {
						monitor.subTask("Patching " + c.getName());
						System.out.println("Patching " + c.getName());
						final PatchedCondition patched = new PatchedCondition(c);
						if (patched.isPatchSuccessful()) {
							if (VerificationTool.prove(NativeEvaluationVerifier.INSTANCE, patched)) {
								patchedConj.add(patched);
								System.out.println("Successfully patched condition: " + c.getName());
							} else {
								System.out.println("Patched condition still fails: " + c.getName());
								monitor.subTask("Patching failed for " + c.getName());
							}
						} else {
							System.out.println("Patching failed");
							monitor.subTask("Patching failed for " + c.getName());
						}
					}
					monitor.worked(1);
				}

				conjectures.retainAll(trueConj);
				conjectures.addAll(patchedConj);

				if (conjectures.isEmpty()) {
					System.out.println("All selected conjectures filtered out on base run");
					eiResult.setError("All selected conjectures filtered out on base run");
					return eiResult;
				}
			}
			System.out.println("Conjectures in experiment: " + ppConj(conjectures));

			prepare(monitor);
			if (errorInjector == null) {
				eiResult.setError("No error injector");
				return eiResult;
			} else if (errorInjector.getFunctions().isEmpty()) {
				eiResult.setError("No error injector functions");
				return eiResult;
			} else if (errorInjector.getPoints() == 0) {
				eiResult.setError("No error injector points");
				return eiResult;
			}
			System.out.println("Injector functions: " + errorInjector.getFunctions());
			runs = errorInjector.getPoints();
			monitor.beginTask("Error injection simulation with " + runs + " steps", runs * conjectures.size());
			monitor.subTask("Error injection simulation");
			boolean canContinue = errorInjector.getPoints() > 0;
			final long startTime = System.currentTimeMillis();
			outer: for (int i = 0; i < runs && canContinue; i++) {
				try {
					model.setErrorInjector(errorInjector);
					VerificationResult.RESULT overallResult = VerificationResult.RESULT.VALID;
					System.out.println("Step " + i + " with " + errorInjector.getCurrentInjection());
					final BaseInjection bi = (BaseInjection) errorInjector.getCurrentInjection();
					IXFunction function = bi.getFunction();
					String tag = bi.getKind();
					String detector = null;
					
					//bi.instantiate(model, conjectures);
					
					//System.out.println("xx");
					
					for (final ICondition c : conjectures) {
						if (monitor.isCanceled() || System.currentTimeMillis() - startTime > timeLimit)
							break outer;
						VerificationResult result = VerificationTool.proveExt(NativeEvaluationVerifier.INSTANCE, c);
						overallResult = VerificationResult.combine(overallResult, result.getKind());
						if (!result.isValid()) {
							if (detector == null)
								detector = c.getShortName();
							else
								detector = detector + ", " + c.getShortName();
						}
						monitor.worked(1);
						if (overallResult == VerificationResult.RESULT.INVALID)
							break;
					}
					System.out.println(tag + " " + overallResult + ", F: " + (function != null ? function.getName() : "none")
							+ ", detectors: " + (detector != null ? ": " + detector : "none"));

					if (overallResult.isDefinite()) {
						eiResult.add(conjectures.iterator().next().getName(), function != null ? function.getName() : "xx", tag,
								overallResult.isValid());

						if (collectSamples) {
							if (overallResult.isValid()) {
								eiResult.addCorrect(bi);
							} else {
								eiResult.addFailed(bi);
							}
						}
					}
				} catch (Throwable e) {
					System.out.println("Skipping step " + i + ": " + e.getMessage());
				}

				canContinue = errorInjector.nextExperimentStep() && !monitor.isCanceled();
				eiResult.advance();
			}

			System.out.println("*** finished exp run with " + eiResult.getRecords().size() + " records; expect " + runs);
			return eiResult;
		} catch (final Throwable e) {
			e.printStackTrace();
			eiResult.setError(e.getMessage());
			return eiResult;
		} finally {
			model.setErrorInjector(NullInjector.INSTANCE);
			model.dropValueCaches();
		}
	}

	protected abstract void prepare(IVerificationProgressMonitor monitor);

	public ICondition prepareCondition(ICondition condition, String tag) {
		CLExpression flattened = CLUtils.flattenTermDependencies(model, condition.getGoal(), tag);
		if (flattened != condition.getGoal())
			return new ProxyCondition(condition, flattened);
		else
			return condition;
	}

	private String ppConj(Collection<ICondition> conjectures2) {
		final StringBuilder sb = new StringBuilder();

		boolean first = true;
		for (final ICondition c : conjectures) {
			if (!first) {
				sb.append(", ");
			}
			sb.append(c.getShortName() + ": " + c.getGoal());
			first = false;
		}
		return sb.toString();
	}

}
