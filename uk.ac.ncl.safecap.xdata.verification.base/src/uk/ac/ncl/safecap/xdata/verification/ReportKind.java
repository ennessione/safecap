package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.modeling.annotations.Label;

@Label(standard = "reportkind")

public enum ReportKind {
	@Label(standard = "single")
	SINGLE,

	@Label(standard = "full")
	FULL
}