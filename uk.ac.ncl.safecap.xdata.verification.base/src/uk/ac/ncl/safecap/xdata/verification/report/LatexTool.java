package uk.ac.ncl.safecap.xdata.verification.report;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ncl.safecap.xdata.provers.ToolConnection;

public class LatexTool extends ToolConnection<Boolean> {
	private final File fileinput;

	public LatexTool(File input) throws IOException {
		super("pdflatex", Boolean.TRUE);
		fileinput = input;
	}

	public boolean translate(IProgressMonitor monitor, boolean show) {
		try {
			process(monitor);
			if (!hasErrors) {
				if (Desktop.isDesktopSupported()) {
					try {
						final File myFile = new File(fileinput.getName());
						Desktop.getDesktop().open(myFile);
					} catch (final IOException ex) {
						return false;
					}
				}
			}
			return !hasErrors;
		} catch (final Throwable e) {
			return false;
		}
	}

	@Override
	public String[] getToolArguments() {
		return new String[] { "-synctex=1", "-interaction=nonstopmode", fileinput.getAbsolutePath() };
	}

	@Override
	public void finalReport() {
	}

	@Override
	public void processLine(String line) {
		if (line.startsWith("! LaTeX Error:")) {
			hasErrors = true;
		}

		System.out.println("LatexTool>>" + line);
	}
}
