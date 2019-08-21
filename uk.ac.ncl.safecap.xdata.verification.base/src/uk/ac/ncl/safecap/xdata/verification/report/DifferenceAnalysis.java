package uk.ac.ncl.safecap.xdata.verification.report;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;

public class DifferenceAnalysis {
	private final Collection<String> missingConcepts;
	private final Collection<String> newConcepts;
	private final Collection<String> changedConcepts;
	private final Collection<Conjecture> affectedConjectures;

	public DifferenceAnalysis(DataContext original, DataContext current) {
		missingConcepts = new HashSet<>();
		newConcepts = new HashSet<>();
		changedConcepts = new HashSet<>();
		affectedConjectures = new HashSet<>();
		analyze(original, current);
	}

	public DifferenceAnalysis(Collection<String> changedConcepts) {
		missingConcepts = Collections.emptySet();
		newConcepts = Collections.emptySet();
		this.changedConcepts = changedConcepts;
		affectedConjectures = new HashSet<>();
	}

	public void compute(RootCatalog catalog) {
		for (final Conjecture c : VisitorUtils.getAllConjectures(catalog)) {
			analyze(c);
		}
	}

	public Collection<String> getMissingConcepts() {
		return missingConcepts;
	}

	public Collection<String> getNewConcepts() {
		return newConcepts;
	}

	public Collection<String> getChangedConcepts() {
		return changedConcepts;
	}

	public Collection<Conjecture> getAffectedConjectures() {
		return affectedConjectures;
	}

	private void analyze(Conjecture c) {
		if (!c.getParsed().empty()) {
			final CLExpression expr = c.getParsed().content();
			for (final String s : expr.getFreeIdentifiers()) {
				if (missingConcepts.contains(s) || newConcepts.contains(s) || changedConcepts.contains(s)) {
					affectedConjectures.add(c);
				}
			}
		}
	}

	private void analyze(DataContext original, DataContext current) {
		for (final String f : original.getFunctionIds()) {
			if (!current.getFunctionIds().contains(f)) {
				missingConcepts.add(f);
			}
		}

		for (final String f : current.getFunctionIds()) {
			if (!original.getFunctionIds().contains(f)) {
				newConcepts.add(f);
			}
		}

		for (final String f : original.getFunctionIds()) {
			if (current.getFunctionIds().contains(f)) {
				final IXFunction forig = original.getFunction(f);
				final IXFunction fcurr = current.getFunction(f);
				analyze(forig, fcurr);
			}
		}
	}

	private void analyze(IXFunction forig, IXFunction fcurr) {
		if (!forig.getFunctionType().equals(fcurr.getFunctionType())) {
			changedConcepts.add(forig.getName());
			return;
		}

		final XFunctionBasic xforig = (XFunctionBasic) forig;
		final XFunctionBasic xfcurr = (XFunctionBasic) fcurr;
		if (!xforig.getMaps().keySet().equals(xfcurr.getMaps().keySet())) {
			changedConcepts.add(forig.getName());
			return;
		}

		for (final Object z : xforig.getMaps().keySet()) {
			final Collection<Object> oval = xforig.getMaps().get(z);
			final Collection<Object> cval = xfcurr.getMaps().get(z);
			if (!oval.equals(cval)) {
				changedConcepts.add(forig.getName());
				return;
			}
		}
	}

}
