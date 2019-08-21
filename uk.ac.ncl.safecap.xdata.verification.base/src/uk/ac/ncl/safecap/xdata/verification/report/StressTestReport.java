package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;

import uk.ac.ncl.safecap.common.report.SFExternalImage;
import uk.ac.ncl.safecap.common.report.SRDocument;
import uk.ac.ncl.safecap.common.report.SRFigure;
import uk.ac.ncl.safecap.xdata.testinv.InvariantStressTesting;

public class StressTestReport {
	private final InvariantStressTesting stressTesting;
	private File folder;

	public StressTestReport(InvariantStressTesting stressTesting) {
		this.stressTesting = stressTesting;
	}

	public void build(final IReportBuilder builder, final File file) throws Exception {
		folder = file.getParentFile();

		final SRDocument document = new SRDocument("Stress testing report");

		document.add(chart());

		document.add(chart1());

		builder.buildReport(document, file, true);
	}

	protected SRFigure chart() {
		final CategoryChart chart = new CategoryChartBuilder().width(1200).height(400).theme(ChartTheme.Matlab).build();

		chart.getStyler().setLegendVisible(true);
		chart.getStyler().setPlotBorderVisible(false);
		chart.getStyler().setLegendBorderColor(java.awt.Color.white);
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		// chart.getStyler().setLegendFont(chart.getStyler().getLegendFont().deriveFont(8.0f));
		chart.getStyler().setAntiAlias(true);
		chart.getStyler().setAnnotationsFont(chart.getStyler().getBaseFont().deriveFont(8.0f));
		chart.getStyler().setAxisTickLabelsFont(chart.getStyler().getBaseFont().deriveFont(8.0f));

		final List<Integer> valid = new ArrayList<>(stressTesting.getResultTransforms().size());
		final List<Integer> invalid = new ArrayList<>(stressTesting.getResultTransforms().size());
		final List<String> labels = new ArrayList<>(stressTesting.getResultTransforms().size());

		for (final String s : stressTesting.getResultTransforms()) {
			if (stressTesting.getResultValid(s) != 0 || stressTesting.getResultInvalid(s) != 0) {
				valid.add(stressTesting.getResultValid(s));
				invalid.add(-stressTesting.getResultInvalid(s));
				labels.add(s);
			}

		}

		final SRFigure figure = new SRFigure("Stress testing results");

		if (!labels.isEmpty()) {
			chart.addSeries("Valid", labels, valid);
			chart.addSeries("Invalid", labels, invalid);

			final File file = embedXChart(chart);
			if (file != null) {
				final SFExternalImage ef = new SFExternalImage(file.getAbsolutePath());
				figure.add(ef);
			}
		}

		return figure;
	}

	protected SRFigure chart1() {
		final CategoryChart chart = new CategoryChartBuilder().width(1200).height(400).theme(ChartTheme.Matlab).build();

		chart.getStyler().setLegendVisible(false);
		chart.getStyler().setPlotBorderVisible(false);
		chart.getStyler().setLegendBorderColor(java.awt.Color.white);
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		// chart.getStyler().setLegendFont(chart.getStyler().getLegendFont().deriveFont(8.0f));
		chart.getStyler().setAntiAlias(true);
		chart.getStyler().setAnnotationsFont(chart.getStyler().getBaseFont().deriveFont(8.0f));
		chart.getStyler().setAxisTickLabelsFont(chart.getStyler().getBaseFont().deriveFont(8.0f));

		final List<Double> ratio = new ArrayList<>(stressTesting.getResultTransforms().size());
		final List<String> labels = new ArrayList<>(stressTesting.getResultTransforms().size());

		for (final String s : stressTesting.getResultTransforms()) {
			if (stressTesting.getResultValid(s) != 0 || stressTesting.getResultInvalid(s) != 0) {
				final double v = stressTesting.getResultValid(s);
				final double i = stressTesting.getResultInvalid(s);

				if (v != 0 || i != 0) {
					if (v != 0) {
						if (i != 0) {
							ratio.add((v - i) / (v + i));
						} else {
							ratio.add(1.0);
						}
					} else {
						ratio.add(0.0);
					}
					labels.add(s);
				}
			}
		}

		final SRFigure figure = new SRFigure("Stress testing results");

		if (!labels.isEmpty()) {
			chart.addSeries("Ratio", labels, ratio);

			final File file = embedXChart(chart);
			if (file != null) {
				final SFExternalImage ef = new SFExternalImage(file.getAbsolutePath());
				figure.add(ef);
			}
		}

		return figure;
	}

	private int chartIndex = 0;

	@SuppressWarnings("rawtypes")
	protected File embedXChart(Chart chart) {
		try {
			final File base = new File(folder, "charts/");
			if (!base.exists()) {
				base.mkdirs();
			}
			final File f = new File(base, "chart" + chartIndex + ".gif");
			BitmapEncoder.saveBitmap(chart, f.getAbsolutePath(), BitmapFormat.GIF);
			// sb.append("Image at " + f.getAbsolutePath());
			chartIndex++;
			return f;
		} catch (final Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

}
