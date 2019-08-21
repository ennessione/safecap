package uk.ac.ncl.safecap.xdata.verification.report;

@SuppressWarnings("rawtypes")
public class HistogramSVGRenderer extends SVGRenderer {

	public HistogramSVGRenderer(XDataChart chart) {
		super(chart);
	}

	@Override
	protected double getMax() {
		return chart.getMaxSum();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderPlot() {
		for (final Object k : chart.getOrder()) {
			int baseheight = 0;
			for (int i = 0; i < chart.getLegend().length; i++) {
				final double value = chart.getValue(k)[i] * scale - base;
				final String color = COLORS[i % COLORS.length];
				final int y = (int) (logicalHeight - baseheight - value);
				final int height = (int) value;
				baseheight += height;
				sb.append("<rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:" + color
						+ ";stroke:none;opacity:0.5\"/>");

				final String valueString = format1.format(chart.getValue(k)[i]);

				if (width > 100) {
					addObjectLabelInPlace(y, valueString);
				} else {
					addObjectLabel(y, valueString);
				}

			}
			renderXAxisLabel(k);
			x += width + GAP_WIDTH;
		}
	}

}
