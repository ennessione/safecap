package uk.ac.ncl.safecap.xdata.verification.report;

@SuppressWarnings("rawtypes")
public class BarChartSVGRenderer extends SVGRenderer {

	public BarChartSVGRenderer(XDataChart chart) {
		super(chart);
	}

	@Override
	protected double getMax() {
		return chart.getMaxSum();
	}

	@Override
	protected void renderLegend(StringBuilder sb, int x) {

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderPlot() {
		int j = 0;
		for (final Object k : chart.getOrder()) {
			int baseheight = 0;
			final int i = 0;
			final double value = chart.getValue(k)[i] * scale - base;
			if (value > 0) {
				final String color = COLORS[j % COLORS.length];
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

				renderXAxisLabel(k);
				x += width + GAP_WIDTH;
				j++;
			}
		}
	}

}
