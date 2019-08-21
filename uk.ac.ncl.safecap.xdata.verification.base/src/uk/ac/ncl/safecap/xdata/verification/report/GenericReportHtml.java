package uk.ac.ncl.safecap.xdata.verification.report;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries.PieSeriesRenderStyle;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.PieStyler.AnnotationType;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;

import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xmldata.FileUtil;

public class GenericReportHtml extends GenericReport {
	protected static final Map<String, String> NO_PARAM = new HashMap<>();
	protected static final String PARAM_DELIM_LAST = "delimlast";
	protected static final String PARAM_DELIM = "delim";
	protected static final String PARAM_EMPTY = "empty";

	protected RootCatalog project;
	protected SDAContext context;
	protected File folder;
	protected Stack<File> currentFile;

	private final StringBuilder sb;
	private final String title;

	protected GenericReportHtml(String title, RootCatalog project) {
		super(SDAUtils.getDataContext(project));
		sb = new StringBuilder();
		this.title = title;
		this.project = project;
		currentFile = new Stack<>();
		context = SDAUtils.getDataContext(project);
	}

	public File getFolder() {
		return folder;
	}

	public void setFolder(File folder) {
		this.folder = folder;
	}

	protected void embeddedStyles() {
	}

	protected void print(String text) {
		sb.append(text);
	}

	public StringBuilder getStringBuilder() {
		return sb;
	}

	protected void documentStart() {
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<script>function resizeIframe(obj) { obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';}</script>");
		sb.append("</head>");
		sb.append("<header>");
		sb.append("<title>");
		sb.append(title);
		sb.append("</title>");
		embeddedStyles();
		sb.append("</header>");
		sb.append("<body>");
	}

	protected void documentEnd() {
		sb.append("</body>");
		sb.append("</html>");
	}

	protected void formatting(String className, String text) {
		sb.append("<span class=\"" + className + "\">" + text + "</span>");
	}

	protected void sectionStart(String className) {
		sb.append("<div class=\"" + className + "\">");
	}

	protected void sectionStart(String className, String id) {
		sb.append("<div class=\"" + className + "\" id=\"" + id + "\">");
	}

	protected void listStart(String type) {
		sb.append("<ol type=\"" + type + "\" style=\"padding: 0px 0px 0px 30px; margin:0px;\">");
	}

	protected void listUStart() {
		sb.append("<ul style=\"padding: 0px 0px 0px 30px; margin:0px;\">");
	}

	protected void listItem(String text) {
		sb.append("<li>" + text + "</li>");
	}

	protected void listItemStart() {
		sb.append("<li>");
	}

	protected void listItemStart(String text) {
		sb.append("<li>" + text);
	}

	protected void listItemEnd() {
		sb.append("</li>");
	}

	protected void listUEnd() {
		sb.append("</ul>");
	}

	protected void listEnd() {
		sb.append("</ol>");
	}

	protected void section(String className, String text) {
		sb.append("<div class=\"" + className + "\">" + text + "</div>\n");
	}

	protected void subsection(String className, String text) {
		sb.append("<div class=\"" + className + "\">" + text + "</div>\n");
	}

	protected void sectionEnd() {
		sb.append("</div>\n");
	}

	protected void sectionExternal(String className, String text, int port, int height) {
		// onload=\"resizeIframe(this)\"
		sb.append("<div class=\"" + className + "\"> <iframe src=\"http://localhost:" + port + "/" + text
				+ "\" frameborder=\"0\" frameBorder=\"0\" scrolling=\"no\" style=\"width:800px; height: " + height
				+ "px\"></iframe></div>\n");
	}

	@Override
	public String toString() {
		return sb.toString();
	}

	protected static String sanitizeText(String content) {
		return content.replaceAll("\\r\\n|\\r|\\n", " ").replaceAll("\\s+", " ").trim();
	}

	protected static String sanitize(String text) {
		final StringBuffer sb = new StringBuffer();

		for (int i = 0; i < text.length(); i++) {
			final char c = text.charAt(i);
			if (i == 0 && Character.isDigit(c)) {
				sb.append("N");
				sb.append(c);
			} else if (!Character.isLetterOrDigit(c) && c != '_') {
				sb.append('_');
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	protected static String escapeHTML(String s) {
		final StringBuilder out = new StringBuilder(Math.max(16, s.length()));
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
				out.append("&#");
				out.append((int) c);
				out.append(';');
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	public void startNewFile(String string) {
		final File f = new File(folder.getAbsolutePath() + File.separator + string + ".html");
		currentFile.push(f);
	}

	public void saveFile() throws Exception {
		final File f = currentFile.pop();
		FileUtil.setFileContents(sb.toString(), f);
		sb.setLength(0);
	}

	protected String linkToLocal(String target, String text) {
		return "<a href=\"#" + sanitize(target) + "\">" + text + "</a>\n";
	}

	protected String linkTo(String target, String text) {
		return "<a href=\"" + target + ".html\">" + text + "</a>\n";
	}

	private int chartIndex = 0;

	@SuppressWarnings("rawtypes")
	protected void embedXChart(Chart chart) {
		try {
			final File base = new File(folder, "charts/");
			if (!base.exists()) {
				base.mkdirs();
			}
			final File f = new File(base, "chart" + chartIndex + ".gif");
			BitmapEncoder.saveBitmap(chart, f.getAbsolutePath(), BitmapFormat.GIF);
			// sb.append("Image at " + f.getAbsolutePath());
			sb.append("<img src=\"" + f.getAbsolutePath() + "\">");
			chartIndex++;
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	protected String ColorToHTML(Color c) {
		return String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
	}

	private static final Color[] chartColors = new Color[] { new Color(250, 100, 100), new Color(40, 180, 40), new Color(100, 100, 250),
			new Color(220, 220, 80), new Color(246, 199, 182), new Color(80, 220, 220), new Color(220, 80, 220), new Color(180, 180, 180),
			new Color(80, 160, 220), new Color(160, 80, 220), new Color(160, 220, 80), new Color(220, 160, 80), new Color(80, 220, 160),
			new Color(130, 100, 230), new Color(140, 140, 140), new Color(200, 120, 120), new Color(60, 140, 60), new Color(120, 120, 210),
			new Color(200, 200, 120), new Color(80, 80, 80) };

	protected void Chart_Bar(List<String> labels, List<Integer> data, String title) {
		final CategoryChart chart = new CategoryChartBuilder().width(900).height(300).theme(ChartTheme.Matlab).build();

		chart.getStyler().setLegendVisible(true);
		chart.getStyler().setPlotBorderVisible(false);
		chart.getStyler().setLegendBorderColor(java.awt.Color.white);
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		chart.getStyler().setLegendFont(chart.getStyler().getLegendFont().deriveFont(8.0f));
		chart.getStyler().setAxisTickLabelsFont(chart.getStyler().getBaseFont().deriveFont(8.0f));

		chart.getStyler().setSeriesColors(chartColors);

		chart.addSeries(title, labels, data);

		embedXChart(chart);
	}

	protected void Chart_Pie(Map<String, Integer> data) {
		final PieChart chart = new PieChartBuilder().width(900).height(300).theme(ChartTheme.Matlab).build();

		chart.getStyler().setLegendVisible(true);
		chart.getStyler().setPlotBorderVisible(false);
		chart.getStyler().setLegendBorderColor(java.awt.Color.white);
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		chart.getStyler().setLegendFont(chart.getStyler().getLegendFont().deriveFont(8.0f));

		chart.getStyler().setSeriesColors(chartColors);

		// Series
		for (final String label : data.keySet()) {
			chart.addSeries(label, data.get(label));
		}

		embedXChart(chart);
	}

	protected void Chart_DonutNoLabel(Map<String, Integer> data) {
		final PieChart chart = new PieChartBuilder().width(900).height(300).theme(ChartTheme.Matlab).build();

		chart.getStyler().setAnnotationsFont(chart.getStyler().getBaseFont().deriveFont(28.0f));
		chart.getStyler().setSumFont(chart.getStyler().getBaseFont().deriveFont(28.0f).deriveFont(Font.BOLD));
		chart.getStyler().setDonutThickness(0.5);
		chart.getStyler().setPlotBorderVisible(false);
		chart.getStyler().setAnnotationType(AnnotationType.Value);
		chart.getStyler().setSumVisible(true);
		chart.getStyler().setDecimalPattern("##");
		// chart.getStyler().setLegendVisible(false);
		// chart.getStyler().setAnnotationType(AnnotationType.Label);
		chart.getStyler().setAnnotationDistance(.72);
		// chart.getStyler().setPlotContentSize(0.3);
		chart.getStyler().setDefaultSeriesRenderStyle(PieSeriesRenderStyle.Donut);

		chart.getStyler().setSeriesColors(chartColors);

		// Series
		for (final String label : data.keySet()) {
			chart.addSeries(label, data.get(label));
		}

		embedXChart(chart);
	}
}
