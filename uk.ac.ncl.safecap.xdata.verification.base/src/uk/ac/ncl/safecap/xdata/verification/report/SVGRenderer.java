package uk.ac.ncl.safecap.xdata.verification.report;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public abstract class SVGRenderer {
	public static final String[] COLORS = new String[] { "coral", "cornflowerblue", "gold", "indianred", "lightblue", "darkgray", "red",
			"darkkhaki", "brown", "forestgreen", "indigo" };

	protected XDataChart chart;
	protected int logicalWidth = 4000;
	protected int logicalHeight = 1000;
	protected int logicalWidthMargin = 100;
	protected int logicalHeightMargin = 300;
	protected int maxElementWidth = 700;
	protected static final DecimalFormat format1 = new DecimalFormat("#.##");

	protected static final int GAP_WIDTH = 20;

	protected Map<String, Integer> valueSet;
	protected StringBuilder sb;
	protected int width;
	protected int x;
	protected double scale;
	protected double base;

	protected int viewWidth = 750;

	public SVGRenderer(XDataChart chart) {
		this.chart = chart;
		valueSet = new HashMap<>();
		sb = new StringBuilder();
	}

	protected void setDimensions(int logicalWidth, int logicalHeight) {
		this.logicalWidth = logicalWidth;
		this.logicalHeight = logicalHeight;
		logicalWidthMargin = logicalWidth / 40;
		maxElementWidth = logicalWidthMargin * 7;
		logicalHeightMargin = (int) (0.3 * logicalHeight);
	}

	final protected double getRatio() {
		return (double) (logicalWidth + logicalWidthMargin * 6) / (double) (logicalHeight + logicalHeightMargin);
	}

	protected double getMin() {
		return chart.getMin();
	}

	protected double getMax() {
		return chart.getMax();
	}

	protected void pepareChart() {
		chart.compress(400);
	}

	@SuppressWarnings("unchecked")
	public String render() {

		pepareChart();
		Collections.sort(chart.getOrder());
		ppHeader(sb);

		final double diff = getMax() - getMin();

		scale = (logicalHeight - logicalWidthMargin * 2) / (diff == 0 ? 1 : diff);
		base = chart.getMin() * scale - logicalWidthMargin - logicalHeight / (2 + diff);

		width = (logicalWidth - logicalWidthMargin * 2) / chart.getOrder().size();
		if (width > maxElementWidth) {
			width = maxElementWidth;
		}

		x = 0;

		renderPlot();

		renderLegend(sb, x);

		x += logicalWidthMargin * 3;
		ppAxis(sb, x);

		ppFooter(sb);

		return sb.toString();
	}

	protected abstract void renderPlot();

	protected void renderXAxisLabel(Object k) {
		sb.append("<text x=\"" + (x + width / 2) + "\" y=\"" + (logicalHeight + normalise(width / 4, 30, 50) + 20)
				+ "\" text-anchor=\"middle\" font-family=\"Verdana\" font-size=\"" + normalise(width / 4, 50, 70) + "\">");
		sb.append(k);
		sb.append("</text>");
	}

	protected void addObjectLabelInPlace(int y, String valueString) {
		sb.append("<text x=\"" + (x + width / 2) + "\" y=\"" + (y - 45)
				+ "\" text-anchor=\"middle\" dominant-baseline=\"middle\" font-family=\"Verdana\" font-size=\"70\">");
		sb.append(valueString);
		sb.append("</text>");
	}

	protected void addObjectLabel(int y, String valueString) {
		addObjectLabel(y, valueString, 0);
	}

	protected void addObjectLabel(int y, String valueString, int lineWidth) {
		if (!valueSet.containsKey(valueString)) {
			final int minSep = getViewHeight() / 20;
			final int minSepViewport = (int) ((double) logicalHeight / (double) getViewHeight() * minSep);
			for (final String key : valueSet.keySet()) {
				final int pos = valueSet.get(key);
				if (Math.abs(y - pos) < minSepViewport) {
					return;
				}
			}
			if (lineWidth > 0) {
				sb.append("<path d=\"M0," + y + " L" + x + "," + y + "\" style=\"fill:none;stroke:black;opacity:0.5;stroke-width:"
						+ lineWidth + "\"/>");
			}
			sb.append("<path d=\"M-15," + y + "L15," + y + "\" style=\"fill:none;stroke:black;stroke-width:10\"/>");
			sb.append("<text x=\"" + -20 + "\" y=\"" + y
					+ "\" text-anchor=\"end\" dominant-baseline=\"middle\" font-family=\"Verdana\" font-size=\"50\">");
			sb.append(valueString);
			sb.append("</text>");
			valueSet.put(valueString, y);
		}
	}

	protected void renderLegend(StringBuilder sb, int x) {
		// render legend
		final int size = logicalWidthMargin / 2;
		final int gap = logicalWidthMargin / 10;
		for (int i = 0; i < chart.getLegend().length; i++) {
			final String color = COLORS[i % COLORS.length];
			sb.append("<rect x=\"" + (x + size) + "\" y=\"" + i * logicalWidthMargin + "\" width=\"" + size + "\" height=\"" + size
					+ "\" style=\"fill:" + color + ";stroke:none;opacity:" + getOpacity() + "\"/>");
			sb.append("<text x=\"" + (x + logicalWidthMargin + gap) + "\" y=\"" + (i * logicalWidthMargin + size)
					+ "\" text-anchor=\"start \" font-family=\"Verdana\" font-size=\"60\">");
			sb.append(chart.getLegend()[i]);
			sb.append("</text>");
		}
	}

	protected String getOpacity() {
		return "0.5";
	}

	protected int normalise(int i, int j, int k) {
		if (i < j) {
			return j;
		} else if (i > k) {
			return k;
		} else {
			return i;
		}
	}

	protected void ppAxis(StringBuilder sb, int width) {
		sb.append(
				"<path d=\"M0," + logicalHeight + " L0,0\" style=\"fill:none;stroke:black;stroke-width:10\" marker-end=\"url(#arrow)\"/>");
		sb.append("<path d=\"M0," + logicalHeight + " L" + width + "," + logicalHeight
				+ "\" style=\"fill:none;stroke:black;stroke-width:10\" marker-end=\"url(#arrow)\"/>");
	}

	protected void ppHeader(StringBuilder sb) {
		sb.append("<svg width=\"" + viewWidth + "px\" height=\"" + getViewHeight() + "px\">");
		sb.append(" <defs>");
		sb.append(
				"    <marker id=\"arrow\" markerWidth=\"10\" markerHeight=\"10\" refX=\"0\" refY=\"3\" orient=\"auto\" markerUnits=\"strokeWidth\">");
		sb.append("		      <path d=\"M0,0 L0,6 L9,3 z\" fill=\"#00\" />");
		sb.append("		    </marker>");
		sb.append(" </defs>");
		sb.append("<svg viewBox=\"-" + logicalWidthMargin * 4 + " -" + logicalWidthMargin + " " + (logicalWidth + logicalWidthMargin * 2)
				+ " " + (logicalHeight + logicalHeightMargin) + "\">");
	}

	protected void ppFooter(StringBuilder sb) {
		sb.append("</svg>");
		sb.append("</svg>");
	}

	public int getViewWidth() {
		return viewWidth;
	}

	public void setViewWidth(int viewWidth) {
		this.viewWidth = viewWidth;
	}

	public int getViewHeight() {
		return (int) (viewWidth / getRatio());
	}
}
