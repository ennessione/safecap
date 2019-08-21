package uk.ac.ncl.safecap.xdata.verification.report;

public interface IReportFormatter {
	void enterSection(String title);

	void leaveSection();

	void formulaDisplay(String formula);

	void paragraph(String text);
}
