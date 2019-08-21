package uk.ac.ncl.safecap.xdata.verification.configsave;

import java.io.File;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.safecap.xdata.verification.report.ReportConfig;
import uk.ac.ncl.safecap.xmldata.DataContext;

public interface IProjectConfig {
	String getProfile();

	String getName();

	String getAuthor();

	String getDescription();

	ReportConfig getReportConfig();

	List<IVerificationStep> getHistoricReports();

	void addVerificationAttempt(String schemaFileName, String schemaFileHash, DataContext schema, String ctFileName, String ctFileHash,
			DataContext ct, String interlocking, ReportResult result, File reportFolder);

	Object[] getExistingPropertyReview(String key);

	Map<String, List<PackagedPropertyReview>> getAllPropertyReviews();
}
