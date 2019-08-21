package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.ElementType;

public interface InvariantReport extends BaseReport, IFormulaSource {
	ElementType TYPE = new ElementType(InvariantReport.class);
}
