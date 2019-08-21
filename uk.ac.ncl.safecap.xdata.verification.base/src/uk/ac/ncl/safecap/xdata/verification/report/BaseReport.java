package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.File;

public abstract class BaseReport {

	public abstract void build(final IReportBuilder builder, final File file) throws Exception;

	public void emitReport(String path) {
		if (path != null) {
			final File file = new File(path);
			try {
				build(HtmlReportBuilder.INSTANCE, file);
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public File emitReportTempFile() {
		try {
			final File file = File.createTempFile("safecap", ".html");
			build(HtmlReportBuilder.INSTANCE, file);
			return file;
		} catch (final Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
