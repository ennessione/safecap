package uk.ac.ncl.safecap.xdata.provers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jface.dialogs.MessageDialog;

import uk.ac.ncl.prime.sim.parser.CLFormulaParser;
import uk.ac.ncl.safecap.xdata.provers.prob.ProBPropertyVerifier;
import uk.ac.ncl.safecap.xdata.provers.why3.Why3PropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.Verifiable;
import uk.ac.ncl.safecap.xdata.verification.VerificationProperty;
import uk.ac.ncl.safecap.xdata.verification.core.ElementVisitor;
import uk.ac.ncl.safecap.xdata.verification.core.IPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;
import uk.ac.ncl.safecap.xdata.verification.core.NullVerificationProgressMonitor;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;
import uk.ac.ncl.safecap.xdata.verification.registry.ISafeRunner;

public class VerificationTool {
	// public static final IPropertyVerifier DEFAULT_PROVER =
	// ProBPropertyVerifier.INSTANCE;
	public static final IPropertyVerifier DEFAULT_PROVER = NativeEvaluationVerifier.INSTANCE;
	public static final Map<String, IPropertyVerifier> provers;

	static {
		provers = new HashMap<>();
		provers.put("ProB Solv", ProBPropertyVerifier.INSTANCE);
		provers.put("NSymb", NativeSymbolicVerifier.INSTANCE);
		provers.put("NSymb+Contr", NativeSymbolicVerifierWithContradictionCheck.INSTANCE);
		provers.put("ProB Disp", NegatedProver.PROB_NEGATED);
		provers.put("Why3", Why3PropertyVerifier.INSTANCE);
		provers.put("ProB|NEval", DoubleProver.PROB_DOUBLE_AND_NATIVE_DOUBLE);
		provers.put("NEval", NativeEvaluationVerifier.INSTANCE);
	}

	// public static final IPropertyVerifier DEFAULT_PROVER =
	// NativeSymbolicVerifier.INSTANCE;

	// public static final IPropertyVerifier DEFAULT_PROVER =
	// NegatedProver.PROB_NEGATED;
	// public static final IPropertyVerifier DEFAULT_PROVER =
	// DoubleProver.PROB_DOUBLE_AND_NATIVE_DOUBLE;
	// public static final IPropertyVerifier DEFAULT_PROVER =
	// Why3PropertyVerifier.INSTANCE;

	public static IPropertyVerifier getProver(String name) {
		if (name == null || !provers.containsKey(name)) {
			return DEFAULT_PROVER;
		}

		return provers.get(name);
	}

	public static IPropertyVerifier getProver(RootCatalog catalog, String name) {
		if (name == null || !provers.containsKey(name)) {
			return getBestProverFor(catalog);
		}

		return provers.get(name);
	}

	public static IPropertyVerifier getBestProverFor(RootCatalog catalog) {
		final SDAContext x = SDAUtils.getDataContext(catalog);

		if (x != null && !x.getTransitionContainers().isEmpty()) {
			return NativeSymbolicVerifier.INSTANCE;
		}

		return ProBPropertyVerifier.INSTANCE;
	}

	public static CLFormulaParser getParser(RootCatalog catalog) {
		final SDAContext dataContext = SDAUtils.getDataContext(catalog);
		if (dataContext != null) {
			final SDARuntimeExecutionContext model = dataContext.getRootRuntimeContext();
			return new CLFormulaParser(model);
		} else {
			return null;
		}

	}

	public static CLFormulaParser getParser(SDARuntimeExecutionContext model) {
		return new CLFormulaParser(model);
	}

	public static void setValidationStatus(final ICondition property, final VerificationResult r,
			final IVerificationProgressMonitor monitor, ISafeRunner runner) {
		try {
			runner.execute(new Runnable() {

				@Override
				public void run() {
					if (r != null) {
						property.setResult(r);
						monitor.checkedProperty(property, r);
						monitor.worked(1);
					} else if (property != null) {
						monitor.checkedProperty(property, VerificationResult.FAILED);
						monitor.worked(1);
						property.setResult(r);
					}
				}
			});
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	public static void check(Verifiable property, ISafeRunner runner) {
		check(DEFAULT_PROVER, property, runner);
	}

	public static void check(ICondition property, ISafeRunner runner) {
		check(DEFAULT_PROVER, property, runner);
	}

	public static void check(IPropertyVerifier prover, Verifiable property, ISafeRunner runner) {
		try {
			ICondition c;
			if (property instanceof Conjecture) {
				c = new ConditionConjecture((Conjecture) property);
			} else {
				c = new ConditionProperty((VerificationProperty) property);
			}
			check(prover, c, runner);
		} catch (final Throwable e) {
			try {
				runner.execute(new Runnable() {
					@Override
					public void run() {
						MessageDialog.openError(null, "Verification failed", e.getMessage());
					}
				});
			} catch (final Exception e1) {
			}
		}
	}

	public static boolean prove(IPropertyVerifier prover, Verifiable property) {
		ICondition c;
		if (property instanceof Conjecture) {
			c = new ConditionConjecture((Conjecture) property);
		} else {
			c = new ConditionProperty((VerificationProperty) property);
		}
		return prove(prover, c);
	}

	public static boolean prove(IPropertyVerifier prover, Collection<? extends Verifiable> properties) {
		boolean result = true;
		for (final Verifiable v : properties) {
			result = result && prove(prover, v);
		}
		return result;
	}

	public static void check(IPropertyVerifier prover, ICondition property, ISafeRunner runner) {
		if (property.getGoal() == null) {
			VerificationTool.setValidationStatus(property, VerificationResult.getFailed(property, "not built"),
					NullVerificationProgressMonitor.INSTANCE, runner);
		} else {
			final VerificationResult r = prover.verify(property, NullVerificationProgressMonitor.INSTANCE, false);
			VerificationTool.setValidationStatus(property, r, NullVerificationProgressMonitor.INSTANCE, runner);
		}
	}

	public static boolean prove(IPropertyVerifier prover, ICondition property) {
		if (property.getGoal() == null) {
			return false;
		} else {
			return prover.verify(property, NullVerificationProgressMonitor.INSTANCE, false).isValid();
		}
	}
	
	public static VerificationResult proveExt(IPropertyVerifier prover, ICondition property) {
		if (property.getGoal() == null) {
			return VerificationResult.FAILED;
		} else {
			return prover.verify(property, NullVerificationProgressMonitor.INSTANCE, false);
		}
	}	

	public static void checkParallel(IPropertyVerifier prover, RootCatalog catalog, IVerificationProgressMonitor monitor,
			ISafeRunner runner) {
		try {
			final CatalogProverRunParallel op = new CatalogProverRunParallel(prover, catalog, monitor, runner);
			op.run();
		} catch (InvocationTargetException | InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public static void checkParallel(IPropertyVerifier prover, RootCatalog catalog, Collection<Conjecture> conjectures,
			IVerificationProgressMonitor monitor, ISafeRunner runner) {
		try {
			final CatalogProverRunParallel op = new CatalogProverRunParallel(prover, catalog, monitor, runner);
			op.setConjectures(conjectures);
			op.run();
		} catch (InvocationTargetException | InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public static Collection<Conjecture> getTrueConjectures(RootCatalog properties) {
		final VerificationStatistics s = new VerificationStatistics();
		s.collect(properties);
		return s.trueConjectures;
	}

	static class VerificationStatistics {
		Collection<Conjecture> trueConjectures;

		VerificationStatistics() {
			trueConjectures = new ArrayList<>();
		}

		protected void collect(RootCatalog properties) {
			VisitorUtils.visitAllProperties(properties, new ElementVisitor<Verifiable>() {
				@Override
				public Object visit(Verifiable element, Object userData) {
					if (element instanceof Conjecture) {
						final Conjecture c = (Conjecture) element;
						if (!c.getParsed().empty() && !c.isValid().empty() && c.isValid().content()) {
							trueConjectures.add(c);
						}
					}
					return null;
				}
			}, null);
		}
	}

}

class CatalogProverRunParallel {
	private final RootCatalog catalog;
	private final IVerificationProgressMonitor monitor;
	private Collection<ICondition> conjectures;
	private final IPropertyVerifier prover;
	private final ISafeRunner runner;

	public CatalogProverRunParallel(IPropertyVerifier prover, RootCatalog catalog, IVerificationProgressMonitor monitor,
			ISafeRunner runner) {
		this.catalog = catalog;
		this.monitor = monitor;
		this.prover = prover;
		this.runner = runner;
	}

	public void setConditions(List<ICondition> conjectures) {
		this.conjectures = conjectures;
	}

	public void setConjectures(Collection<Conjecture> data) {
		conjectures = new ArrayList<>(data.size());
		for (final Conjecture c : data) {
			conjectures.add(new ConditionConjecture(c));
		}
	}

	public void run() throws InvocationTargetException, InterruptedException {

		if (conjectures == null || conjectures.size() == 0) {
			conjectures = VisitorUtils.getAllVerifiables(catalog);
		}
		monitor.beginTask("Property verification", conjectures.size());

		// final ExecutorService execs = Executors.newFixedThreadPool(64);
		// final Collection<Callable<VerificationResult>> tasks = new
		// ArrayList<Callable<VerificationResult>>();

		final ExecutorService execs = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		final List<Callable<VerificationResult>> tasks = new ArrayList<>(conjectures.size());
		for (final ICondition c : conjectures) {
			tasks.add(new Callable<VerificationResult>() {
				@Override
				public VerificationResult call() throws Exception {
					if (!monitor.isCanceled()) {
						return verifyWrapper(c);
					} else {
						return null;
					}
				}
			});
		}

		final List<Future<VerificationResult>> features = execs.invokeAll(tasks, 1, TimeUnit.HOURS);

		if (monitor.isCanceled()) {
			return;
		}

		for (final Future<VerificationResult> f : features) {
			if (monitor.isCanceled()) {
				return;
			}
			if (f.isDone()) {
				try {
					final VerificationResult r = f.get();
					VerificationTool.setValidationStatus(r.getProperty(), r, monitor, runner);
				} catch (final ExecutionException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private VerificationResult verifyWrapper(final ICondition c) {
		// monitor.setTaskName("Started " + c.getName());
		final VerificationResult r = prover.verify(c, monitor, false);
		monitor.worked(1);
		// monitor.setTaskName("Done " + c.getName() + ": " + r.toString());
		if (r == null) {
			return VerificationResult.getFailed(c);
		} else {
			r.setProperty(c);
			return r;
		}
	}

}
