package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.File;

import org.eclipse.core.runtime.NullProgressMonitor;

import uk.ac.ncl.safecap.common.report.SRDocument;
import uk.ac.ncl.safecap.xmldata.FileUtil;

public class PDFReportBuilder implements IReportBuilder {
	public static final PDFReportBuilder INSTANCE = new PDFReportBuilder();

	private PDFReportBuilder() {
	}

	@Override
	public void buildReport(SRDocument document, File outputPdfFile, boolean show) {
		try {
			final LatexRenderer renderer = new LatexRenderer();
			final String result = renderer.render(document);
			final File main = renderer.saveInAFile(result, "main", ".tex");
			final LatexTool latexTool = new LatexTool(main);
			latexTool.process(new NullProgressMonitor());

			final String pdfFileName = FileUtil.replaceExtension(main.getAbsolutePath(), ".pdf");
			final File pdfFile = new File(pdfFileName);
			if (pdfFile.exists()) {
				FileUtil.copy(pdfFile, outputPdfFile);
			}

			org.eclipse.swt.program.Program.launch(outputPdfFile.toURI().toURL().toString());
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}
}
