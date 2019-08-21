package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.File;

import uk.ac.ncl.safecap.common.report.ISRDocumentRenderer;
import uk.ac.ncl.safecap.common.report.SRDocument;
import uk.ac.ncl.safecap.xmldata.FileUtil;

public class HtmlReportBuilder implements IReportBuilder {
	public static final HtmlReportBuilder INSTANCE = new HtmlReportBuilder();

	private HtmlReportBuilder() {
	}

	@Override
	public void buildReport(SRDocument document, File file, boolean show) {
		try {
			final ISRDocumentRenderer renderer = new HtmlRenderer();
			final String result = renderer.render(document);
			if (file != null) {
				FileUtil.setFileContents(result, file);
			}

			org.eclipse.swt.program.Program.launch(file.toURI().toURL().toString());
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

}
