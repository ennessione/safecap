package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.File;

import uk.ac.ncl.safecap.common.report.SRDocument;

public interface IReportBuilder {
	void buildReport(SRDocument document, File file, boolean show);
}
