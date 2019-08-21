package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.File;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.internal.chartpart.Chart;

import uk.ac.ncl.safecap.common.report.SFExternalImage;
import uk.ac.ncl.safecap.common.report.SRContent;
import uk.ac.ncl.safecap.common.report.SRFigure;

public class SRXChart {
	private final File folder;
	private int chartIndex = 0;

	public SRXChart(File folder) {
		this.folder = folder;
	}

	@SuppressWarnings("rawtypes")
	public SRContent insertXChart(Chart chart, String title) {
		final SRFigure figure = new SRFigure(title);

		final File file = embedXChart(chart);
		if (file != null) {
			final SFExternalImage ef = new SFExternalImage(file.getAbsolutePath());
			figure.add(ef);
		}
		return figure;
	}

	@SuppressWarnings("rawtypes")
	private File embedXChart(Chart chart) {
		try {
			final File base = new File(folder, "charts/");
			if (!base.exists()) {
				base.mkdirs();
			}
			final File f = new File(base, "chart" + chartIndex + ".gif");
			BitmapEncoder.saveBitmap(chart, f.getAbsolutePath(), BitmapFormat.GIF);
			chartIndex++;
			return f;
		} catch (final Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
}
