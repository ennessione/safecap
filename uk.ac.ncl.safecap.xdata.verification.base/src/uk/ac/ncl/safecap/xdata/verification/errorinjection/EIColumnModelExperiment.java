package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ColumnModelInfo;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.InjectorException;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.TablesModel;
import uk.ac.ncl.safecap.xdata.provers.ConditionConjecture;
import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;
import uk.ac.ncl.safecap.xmldata.IXFunction;

public class EIColumnModelExperiment extends EIBaseExperiment {
	private List<IXFunction> concepts;
	private Collection<String> names;
	private final TablesModel tablesModel;

	public EIColumnModelExperiment(RootCatalog catalog, TablesModel model) throws InjectorException {
		super(catalog, (Integer) model.getProperty("Runs", 10));
		tablesModel = model;
		buildConjectures();
	}

	@Override
	protected void prepare(IVerificationProgressMonitor monitor) {
		rebuildConcepts();
		final PoolingInjector poolingInjector = new PoolingInjector(data, concepts, super.runs, 30);
		errorInjector = poolingInjector;

		for (final ColumnModelInfo columnModel : tablesModel.getAllTests()) {
			switch (columnModel.getKind()) {
			case MUTATE:
				poolingInjector.addGenerator(1.0, GeneratorMutate.INSTANCE);
				break;
			case ADD:
				poolingInjector.addGenerator(1.0, GeneratorAdd.INSTANCE);
				break;
			case REMOVE:
				poolingInjector.addGenerator(1.0, GeneratorRemove.INSTANCE);
				break;
			case SWAP:
				poolingInjector.addGenerator(1.0, GeneratorSwap.INSTANCE);
				break;
			case EXCLUDE:
				poolingInjector.addGenerator(1.0, GeneratorExclude.INSTANCE);
				break;
			}
		}

		poolingInjector.buildInjections(monitor);
		concepts = poolingInjector.getFunctions();
	}

	public Collection<String> getNames() {
		return names;
	}

	private void buildConjectures() throws InjectorException {
		names = new HashSet<>();
		conjectures = new ArrayList<>();
		concepts = new ArrayList<>();

		for (final ColumnModelInfo columnModel : tablesModel.getAllTests()) {
			for (final String concept : columnModel.getParent().getConceptPaths()) {
				final IXFunction f = SDAUtils.resolveFromXPath(data, concept);
				if (f == null) {
					throw new InjectorException("Invalid concept path " + concept + " for column " + columnModel.getParent().getName());
				}
				concepts.add(f);
				names.add(f.getName());
			}
		}

		final List<Conjecture> allConjectures = VisitorUtils.getAllConjectures(catalog);
		for (final Conjecture c : allConjectures) {
			if (!c.getParsed().empty()) {
				final CLExpression formula = c.getParsed().content();
				if (CLUtils.isRelevant(names, formula)) {
					conjectures.add(prepareCondition(new ConditionConjecture(c), SIGNALLING_TAG));
				} else {
					System.out.println("Not relevant: " + c.getId().content() + ": " + names + " vs " + formula.getFreeIdentifiers());
				}
			} else {
				System.out.println("Not parsed: " + c.getId().content());
			}
		}

	}

	private void rebuildConcepts() {
		final List<IXFunction> newconcepts = new ArrayList<>();
		for (final ICondition c : conjectures) {
			final CLExpression expr = c.getGoal();
			for (final String id : expr.getFreeIdentifiers()) {
				if (data.getFunction(id) != null && concepts.contains(data.getFunction(id))) {
					newconcepts.add(data.getFunction(id));
				}
			}
		}
		concepts = newconcepts;

	}

}
