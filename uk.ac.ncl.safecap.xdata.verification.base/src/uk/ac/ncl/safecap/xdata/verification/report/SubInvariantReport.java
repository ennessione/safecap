package uk.ac.ncl.safecap.xdata.verification.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransition;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransitionPathSource;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionContainer;

public class SubInvariantReport {
	public TransitionContainer tc;
	public ITransitionPathSource source;
	public ITransition element;
	public ManagedProofObligation tpo;
	public Set<SubInvariantReportIssue> issues;
	public List<SubSubInvariantReport> otherSources;

	public SubInvariantReport(TransitionContainer tc, ITransition element, ITransitionPathSource source, ManagedProofObligation tpo) {
		this.tc = tc;
		this.element = element;
		this.source = source;
		this.tpo = tpo;
		issues = InvariantReport.getIssues(tpo);
	}

	public void addOtherSource(SubSubInvariantReport loc) {
		if (otherSources == null) {
			otherSources = new ArrayList<>();
		}

		if (!otherSources.contains(loc)) {
			otherSources.add(loc);
		}
	}

	@Override
	public String toString() {
		return "SubInvariantReport [element=" + element.getName() + ", issues=" + issues + ", otherSources=" + otherSources + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (element == null ? 0 : element.hashCode());
		result = prime * result + (issues == null ? 0 : issues.hashCode());
		result = prime * result + (source == null ? 0 : source.hashCode());
		result = prime * result + (tc == null ? 0 : tc.hashCode());
		result = prime * result + (tpo == null ? 0 : tpo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SubInvariantReport other = (SubInvariantReport) obj;
		if (element == null) {
			if (other.element != null) {
				return false;
			}
		} else if (!element.equals(other.element)) {
			return false;
		}
		if (issues == null) {
			if (other.issues != null) {
				return false;
			}
		} else if (!issues.equals(other.issues)) {
			return false;
		}
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!source.equals(other.source)) {
			return false;
		}
		if (tc == null) {
			if (other.tc != null) {
				return false;
			}
		} else if (!tc.equals(other.tc)) {
			return false;
		}
		if (tpo == null) {
			if (other.tpo != null) {
				return false;
			}
		} else if (!tpo.equals(other.tpo)) {
			return false;
		}
		return true;
	}

}