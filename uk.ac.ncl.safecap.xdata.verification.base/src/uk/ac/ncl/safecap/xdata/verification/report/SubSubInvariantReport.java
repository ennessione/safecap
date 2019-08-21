package uk.ac.ncl.safecap.xdata.verification.report;

import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransition;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransitionPathSource;

public class SubSubInvariantReport {
	ITransition element;
	ITransitionPathSource source;
	ManagedProofObligation tpo;

	public SubSubInvariantReport(ITransition element, ITransitionPathSource source, ManagedProofObligation tpo) {
		this.element = element;
		this.source = source;
		this.tpo = tpo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (element == null ? 0 : element.hashCode());
		result = prime * result + (source == null ? 0 : source.hashCode());
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
		final SubSubInvariantReport other = (SubSubInvariantReport) obj;
		if (element == null) {
			if (other.element != null) {
				return false;
			}
		} else if (!element.equals(other.element)) {
			return false;
		}
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!source.equals(other.source)) {
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