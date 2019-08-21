package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.InjectorException;
import uk.ac.ncl.safecap.xdata.provers.ConditionConjecture;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;
import uk.ac.ncl.safecap.xdata.verification.core.WrappingProgressMonitor;
import uk.ac.ncl.safecap.xmldata.IXFunction;

public class ConjectureModelExperiment extends EIBaseExperiment {
	private static final String SIGNALLING_TAG = "signalling";

	private static final double[] WEIGHTS_DEFAULT = new double[] { 0.2, 0.2, 0.2, 0.2, 0.2 };

	private List<IXFunction> concepts;
	// private Collection<String> names;
	private final double[] weights;
	private List<String> includeKinds;
	private List<String> excludeKinds;
	private EIResult result;

	public ConjectureModelExperiment(RootCatalog catalog, Conjecture conjecture, double[] weights, int runs) throws InjectorException {
		super(catalog, runs);
		this.weights = weights;
		conjectures = new ArrayList<>();
		conjectures.add(prepareCondition(new ConditionConjecture(conjecture), SIGNALLING_TAG));
		concepts = new ArrayList<>();
		includeKinds(SIGNALLING_TAG);
		buildConcepts();
	}

	public ConjectureModelExperiment(Conjecture conjecture, int runs) throws InjectorException {
		this((RootCatalog) conjecture.root(), conjecture, WEIGHTS_DEFAULT, runs);
	}

	public IRunnableWithProgress prepareTask() {
		return new RunnableWithProgressWrapper();
	}

	public EIResult getResult() {
		return result;
	}

	class RunnableWithProgressWrapper implements IRunnableWithProgress {

		@Override
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			final WrappingProgressMonitor xx = new WrappingProgressMonitor(monitor);
			result = execute(xx);
		}
	}

	public void setIncludeKinds(List<String> includeKinds) {
		this.includeKinds = includeKinds;
	}

	public void setExcludeKinds(List<String> excludeKinds) {
		this.excludeKinds = excludeKinds;
	}

	public void includeKinds(String... array) {
		includeKinds = Arrays.asList(array);
	}

	public void excludeKinds(String... array) {
		excludeKinds = Arrays.asList(array);
	}

	@Override
	protected void prepare(IVerificationProgressMonitor monitor) {

		final PoolingInjector poolingInjector = new PoolingInjector(data, concepts, super.runs, 1);
		errorInjector = poolingInjector;

		if (weights.length > 0 && weights[0] > 0) {
			poolingInjector.addGenerator(weights[0], GeneratorMutate.INSTANCE);
		}

		if (weights.length > 1 && weights[1] > 0) {
			poolingInjector.addGenerator(weights[1], GeneratorAdd.INSTANCE);
		}

		if (weights.length > 2 && weights[2] > 0) {
			poolingInjector.addGenerator(weights[2], GeneratorRemove.INSTANCE);
		}

		if (weights.length > 3 && weights[3] > 0) {
			poolingInjector.addGenerator(weights[3], GeneratorSwap.INSTANCE);
		}

		if (weights.length > 4 && weights[4] > 0) {
			poolingInjector.addGenerator(weights[4], GeneratorExclude.INSTANCE);
		}

		poolingInjector.buildInjections(monitor);
		concepts = poolingInjector.getFunctions();
	}

	private void buildConcepts() {
		final CLExpression expr = conjectures.iterator().next().getGoal();
		for (final String id : expr.getFreeIdentifiers()) {
			final IXFunction ff = data.getFunction(id);
			if (ff != null && !ff.isDerived()) {
				if ((includeKinds == null || includeKinds.contains(ff.getTag()))
						&& (excludeKinds == null || !excludeKinds.contains(ff.getTag()))) {
					concepts.add(data.getFunction(id));
				}
			}
		}
	}

}
