package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import uk.ac.ncl.safecap.common.report.BaseRenderer;
import uk.ac.ncl.safecap.common.report.SFBold;
import uk.ac.ncl.safecap.common.report.SFElement;
import uk.ac.ncl.safecap.common.report.SFEmph;
import uk.ac.ncl.safecap.common.report.SFExternalImage;
import uk.ac.ncl.safecap.common.report.SFLink;
import uk.ac.ncl.safecap.common.report.SFPath;
import uk.ac.ncl.safecap.common.report.SFPlain;
import uk.ac.ncl.safecap.common.report.SFSource;
import uk.ac.ncl.safecap.common.report.SFType;
import uk.ac.ncl.safecap.common.report.SMError;
import uk.ac.ncl.safecap.common.report.SMNotice;
import uk.ac.ncl.safecap.common.report.SMWarning;
import uk.ac.ncl.safecap.common.report.SRBlock;
import uk.ac.ncl.safecap.common.report.SRCode;
import uk.ac.ncl.safecap.common.report.SRComment;
import uk.ac.ncl.safecap.common.report.SRFigure;
import uk.ac.ncl.safecap.common.report.SRFoldable;
import uk.ac.ncl.safecap.common.report.SRFormula;
import uk.ac.ncl.safecap.common.report.SRGrid;
import uk.ac.ncl.safecap.common.report.SRList;
import uk.ac.ncl.safecap.common.report.SRPart;
import uk.ac.ncl.safecap.common.report.SRRelated;
import uk.ac.ncl.safecap.common.report.SRSection;
import uk.ac.ncl.safecap.xmldata.FileUtil;

public class LatexRenderer extends BaseRenderer {
	private Path folder;

	public LatexRenderer() {
		try {
			folder = Files.createTempDirectory("sclatex");
		} catch (final IOException e) {

		}
	}

	private File createFile(String prefix, String ext) {
		try {
			final Path dataFile = Files.createTempFile(folder, prefix, ext);
			return dataFile.toFile();
		} catch (final Throwable e) {
			return null;
		}
	}

	public File saveInAFile(String contents, String prefix, String ext) {
		try {
			final File file = createFile(prefix, ext);
			if (file == null) {
				return null;
			}
			FileUtil.setFileContents(contents, file);
			return file;
		} catch (final Throwable e) {
			return null;
		}
	}

	@Override
	protected void renderFormatted(SFElement child) {
		print("\\textrm{" + sanitize(child.getContent()) + "}");
	}

	@Override
	protected void renderFormatted(SFType child) {
		print("\\textsf{" + sanitize(child.getContent()) + "}");
	}

	@Override
	protected void renderFormatted(SFPath child) {
		print("\\texttt{" + sanitize(child.getContent()) + "}");
	}

	@Override
	protected void renderFormatted(SFBold child) {
		print("\\textbf{" + sanitize(child.getContent()) + "}");
	}

	@Override
	protected void renderFormatted(SFEmph child) {
		print("\\emph{" + sanitize(child.getContent()) + "}");
	}

	@Override
	protected void renderFormatted(SFPlain child) {
		print(sanitize(child.getContent()));
	}

	@Override
	protected void renderFormatted(SFSource child) {
		print("\\texttt{" + sanitize(child.getContent()) + "}");
	}

	@Override
	protected void renderMarker(SMError error) {
		print("\\textbf{" + sanitize(error.getContent()) + "}");
	}

	@Override
	protected void renderMarker(SMWarning error) {
		print("\\textbf{" + sanitize(error.getContent()) + "}");
	}

	@Override
	protected void renderMarker(SMNotice error) {
		print("\\textbf{" + sanitize(error.getContent()) + "}");
	}

	@Override
	protected void renderContentStart(SRBlock child) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void renderContentEnd(SRBlock child) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void renderContentStart(SRComment child) {
		print("\\begin{quote}");
	}

	@Override
	protected void renderContentEnd(SRComment child) {
		print("\\end{quote}");
	}

	@Override
	protected void renderContentStart(SRCode child) {
		print("\\begin{verbatim}");
	}

	@Override
	protected void renderContentEnd(SRCode child) {
		print("\\end{verbatim}");
	}

	@Override
	protected void renderContentStart(SRFigure child) {
		print("\\begin{figure}");

		if ("svg".equals(child.get(SRFigure.IMAGE_TYPE)) && child.getChildren().size() > 0) { // save svg document in a separate file
			print("\\centering");
			for (int i = 0; i < child.getChildren().size(); i++) {
				final File file = saveInAFile(child.getChildren().get(i).getContent(), "image", ".svg");
				if (file != null) {
					print("\\includegraphics[scale=\textwidth]{" + file.getName() + "}");
					if (child.getTag() != null) {
						print("\\label{" + child.getTag() + "}");
					}
					child.getChildren().clear();
				}
			}
			print("\\caption{" + child.get(SRFigure.CAPTION, "Unnamed") + "}");
		}
	}

	@Override
	protected void renderContentEnd(SRFigure child) {
		print("\\end{figure}");
	}

	@Override
	protected void renderContentStart(SRFormula child) {
		print("\\begin{verbatim}");
	}

	@Override
	protected void renderContentEnd(SRFormula child) {
		print("\\end{verbatim}");
	}

	@Override
	protected void renderContentStart(SRRelated child) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void renderContentEnd(SRRelated child) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void documentStart(String title) {
		print("\\documentclass{report}");
		print("\\usepackage[latin1]{inputenc}");
		print("\\usepackage[cmex10]{amsmath}");
		print("\\usepackage[latin1]{inputenc}");
		print("\\usepackage{amsfonts}");
		print("\\usepackage{amssymb}");
		print("\\usepackage{graphicx}");

		print("\\title{" + title + "}");

		print("\\begin{document}");
		print("\\maketitle");
	}

	@Override
	protected void documentEnd() {
		print("\\end{document}");

	}

	@Override
	protected void embeddedStyles() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void renderListStart(SRList list, String title) {
		print("\\begin{itemize}");
	}

	@Override
	protected void renderListItemStart(SRList list, SRPart item) {
		print("\\item");
	}

	@Override
	protected void renderListItemEnd(SRList list, SRPart item) {
		// nothing
	}

	@Override
	protected void renderListEnd(SRList list) {
		print("\\end{itemize}");
	}

	@Override
	protected void renderSectionStart(SRSection section, String index) {
		final String title = sanitize(section.get(SRPart.TITLE, "Untitled"));
		switch (getSectionDepth()) {
		case 1:
			print("\\section{" + title + "}");
			break;
		case 2:
			print("\\subsection{" + title + "}");
			break;
		case 3:
			print("\\subsubsection{" + title + "}");
			break;
		default:
			print("\\subsubsection{" + title + "}");
			break;
		}
	}

	@Override
	protected void renderSectionEnd(SRSection section) {
		// nothing
	}

	private String sanitize(String text) {
		// remove non-printables
		text = text.replaceAll("\\P{Print}", "");
		text = text.replaceAll("\\%", "\\%");
		text = text.replaceAll("\\{", "\\{");
		text = text.replaceAll("\\}", "\\}");
		return text;
	}

	@Override
	protected void renderFoldableStart(SRFoldable section) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void renderFoldableEnd(SRFoldable section) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void renderFormatted(SFExternalImage child) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void renderGridStart(SRGrid grid) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void renderGridEnd(SRGrid grid) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void renderFormatted(SFLink child) {
		// TODO Auto-generated method stub
		
	}
}
