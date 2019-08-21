package uk.ac.ncl.safecap.xdata.verification.report;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;

public class CoverageAnalysis {
	private Set<String> coveredConcepts;
	private Set<String> knownConcepts;
	private Set<String> uncoveredConcepts;

	public CoverageAnalysis(Collection<Conjecture> conjectures) {
		if (conjectures.isEmpty()) {
			return;
		}

		coveredConcepts = new HashSet<>();

		for (final Conjecture c : conjectures) {
			analyze(c);
		}

		coveredConcepts.retainAll(knownConcepts);

		uncoveredConcepts = new HashSet<>();
		uncoveredConcepts.addAll(knownConcepts);
		uncoveredConcepts.removeAll(coveredConcepts);
	}

	public CoverageAnalysis getPropertiesCoverage(RootCatalog catalog) {
		return new CoverageAnalysis(VisitorUtils.getAllConjectures(catalog));
	}

	public double coverageRate() {
		final double coveredSize = coveredConcepts.size();
		final double knownSize = knownConcepts.size();
		return coveredSize / knownSize;
	}

	public Set<String> getCoveredConcepts() {
		return coveredConcepts;
	}

	public Set<String> getKnownConcepts() {
		return knownConcepts;
	}

	public Set<String> getUncoveredConcepts() {
		return uncoveredConcepts;
	}

	private void getGeneral(Conjecture c) {
		final RootCatalog catalog = (RootCatalog) c.root();
		final SDAContext context = SDAUtils.getDataContext(catalog);
		knownConcepts = new HashSet<>();
		for (final String f : context.getFunctionIds()) {
			if (!f.startsWith("/")) {
				knownConcepts.add(f);
			}
		}
	}

	private void analyze(Conjecture c) {
		if (knownConcepts == null) {
			getGeneral(c);
		}
		if (!c.getParsed().empty()) {
			final CLExpression expr = c.getParsed().content();
			coveredConcepts.addAll(expr.getFreeIdentifiers());
		}
	}
}
