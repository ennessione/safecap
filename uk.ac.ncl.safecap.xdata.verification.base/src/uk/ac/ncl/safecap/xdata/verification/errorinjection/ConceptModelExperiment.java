package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.InjectorException;
import uk.ac.ncl.safecap.xdata.provers.ConditionConjecture;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;
import uk.ac.ncl.safecap.xdata.verification.core.WrappingProgressMonitor;
import uk.ac.ncl.safecap.xmldata.IXFunction;

public class ConceptModelExperiment extends EIBaseExperiment {
	private static final double[] WEIGHTS_DEFAULT = new double[] { 0.2, 0.2, 0.2, 0.2, 0.2 };
	private List<IXFunction> concepts;
	private Collection<String> names;
	private final double[] weights;
	private EIResult result;

	public ConceptModelExperiment(RootCatalog catalog, List<IXFunction> concepts, int runs) throws InjectorException {
		super(catalog, runs);
		this.concepts = concepts;
		weights = WEIGHTS_DEFAULT;
		buildConjectures();
	}

	public ConceptModelExperiment(RootCatalog catalog, List<IXFunction> concepts, double[] weights, int runs) throws InjectorException {
		super(catalog, runs);
		this.concepts = concepts;
		this.weights = weights;
		buildConjectures();
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

	private void buildConjectures() throws InjectorException {
		names = new HashSet<>();
		conjectures = new ArrayList<>();

		for (final IXFunction f : concepts) 
			names.add(f.getName());


		final List<Conjecture> allConjectures = VisitorUtils.getAllConjectures(catalog);
		for (final Conjecture c : allConjectures) {
			if (!c.getParsed().empty()) {
				final CLExpression formula = c.getParsed().content();
				if (CLUtils.isRelevant(names, formula)) {
					conjectures.add(prepareCondition(new ConditionConjecture(c), SIGNALLING_TAG));
				}
			}
		}

	}

	public Collection<String> getNames() {
		return names;
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

}
