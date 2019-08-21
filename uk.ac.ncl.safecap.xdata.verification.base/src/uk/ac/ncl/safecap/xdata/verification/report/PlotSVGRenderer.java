package uk.ac.ncl.safecap.xdata.verification.report;

@SuppressWarnings("rawtypes")
public class PlotSVGRenderer extends SVGRenderer {

	public PlotSVGRenderer(XDataChart chart) {
		super(chart);
		setDimensions(2000, 1000);
		setViewWidth(650);
	}

	@Override
	protected String getOpacity() {
		return "1.0";
	}

	@Override
	protected void renderPlot() {

		x = width * chart.getOrder().size();
		addObjectLabel(coordMap(chart.getMin()), format1.format(chart.getMin()), 5);

		for (int i = 0; i < chart.getLegend().length; i++) {
			final double[] points = chart.getDataPointsFor(i);
			final String color = COLORS[i % COLORS.length];

			sb.append("<path d=\"");
			sb.append("M");
			sb.append("" + width / 2 + " " + coordMap(points[0]));
			for (int j = 1; j < points.length; j++) {
				sb.append(" L");
				final int y = coordMap(points[j]);
				sb.append("" + (width * j + width / 2) + " " + y);
			}
			sb.append("\" style=\"fill:none;stroke:" + color + ";stroke-width:10;stroke-linecap:round\"/>");

			addObjectLabel(coordMap(chart.getMaxFor(i)), format1.format(chart.getMaxFor(i)));
			addObjectLabel(coordMap(chart.getMeanFor(i)), format1.format(chart.getMeanFor(i)));
			addObjectLabel(coordMap(chart.getMinFor(i)), format1.format(chart.getMinFor(i)));
		}

		final int maxPoints = getViewWidth() / 50;
		final int skip = chart.getOrder().size() > maxPoints ? (int) Math.ceil(chart.getOrder().size() / maxPoints) : 1;
		for (int i = 0; i < chart.getOrder().size(); i += skip) {
			x = width * i;
			renderXAxisLabel(chart.getOrder().get(i));
		}

		x = width * chart.getOrder().size();
	}

	private int coordMap(double v) {
		return (int) (logicalHeight - v * scale + base);
	}
}
