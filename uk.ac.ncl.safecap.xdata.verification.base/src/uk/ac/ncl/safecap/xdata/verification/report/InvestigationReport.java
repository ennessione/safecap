package uk.ac.ncl.safecap.xdata.verification.report;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.RadarChart;
import org.knowm.xchart.RadarChart.RadarRenderStyle;
import org.knowm.xchart.RadarChartBuilder;
import org.knowm.xchart.style.Styler.ChartTheme;

import uk.ac.ncl.safecap.common.report.SFBold;
import uk.ac.ncl.safecap.common.report.SFPlain;
import uk.ac.ncl.safecap.common.report.SRBlock;
import uk.ac.ncl.safecap.common.report.SRCode;
import uk.ac.ncl.safecap.common.report.SRContainer;
import uk.ac.ncl.safecap.common.report.SRContent;
import uk.ac.ncl.safecap.common.report.SRDocument;
import uk.ac.ncl.safecap.common.report.SRGrid;
import uk.ac.ncl.safecap.common.report.SRList;
import uk.ac.ncl.safecap.common.report.SRPart;
import uk.ac.ncl.safecap.common.report.SRSection;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ColumnModel;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.TableModel;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.TablesModel;
import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.BaseInjection;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.ConjectureModelExperiment;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.EIResult;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.EIResult.QUERY;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.PatchedCondition;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;

public class InvestigationReport extends BaseReport {
	private final SDAContext context;
	private final ConjectureModelExperiment errorInjection;
	private final ICondition conjecture;
	private SRXChart srxChart;
	private TablesModel model = null;

	public InvestigationReport(SDAContext context, ConjectureModelExperiment errorInjection) {
		this.context = context;
		this.errorInjection = errorInjection;
		conjecture = errorInjection.getConjectures().iterator().next();
		if (context.getConceptMap() != null) {
			model = context.getConceptMap().getTreeModel();
		}
	}

	@Override
	public void build(final IReportBuilder builder, final File file) throws Exception {
		srxChart = new SRXChart(file.getParentFile());

		final SRDocument document = new SRDocument("Investigation report for " + conjecture.getName());

		final SRSection section = new SRSection(conjecture.getName());

		buildReportPatch(section);

		buildReportChart(section);

		buildConceptCharts(section);

		document.add(section);

		if (model != null) {
			buildCorrectSamples(document);
		}

		builder.buildReport(document, file, true);
	}

	private void buildReportPatch(SRSection section) {
		if (conjecture instanceof PatchedCondition) {
			final PatchedCondition patched = (PatchedCondition) conjecture;
			final SRSection subsection = new SRSection("Patched property");
			subsection.add(new SFPlain("This property is violated for this dataset. It has been patched to remove these violations."));
			final SRSection subsubsection = new SRSection("Violations");
			final SRList list = new SRList();
			for (final Object z : patched.getPatchedValues()) {
				list.add(z.toString());
			}
			subsubsection.add(list);
			subsubsection.add("Original predicate").add(new SRCode(patched.getOriginalCondition().getGoal().asString()));
			subsubsection.add("Patched predicate").add(new SRCode(patched.getGoal().asString()));

			subsection.add(subsubsection);
			section.add(subsection);
		}

	}

	private void buildCorrectSamples(SRContainer container) {

		final List<BaseInjection> correct = errorInjection.getResult().getCorrect();
		if (correct == null || correct.isEmpty()) {
			return;
		}

//		SRSection subsection = new SRSection("Valid cases");
//		subsection.set(HtmlRenderer.CLASS, "section1flex");
//
//		// add artificial line break
//		subsection.add(new SRBlock().set(SRPart.WIDTH, "100%"));
//		container.add(subsection);

		final List<BaseInjection> filtered = filter(correct);
		for (final BaseInjection bi : filtered) {
			buildTablesForBaseInjection(container, bi);
		}

	}

	private void buildTablesForBaseInjection(SRContainer container, final BaseInjection bi) {
		final String column = column(bi.getFunction().getName());
		assert column != null;

		// final ColumnModel cm = model.findColumn(column);
		final TableModel tm = model.findTopLevelTable(column);
		final Object domain = bi.getDomain();
		final XFunctionBasic ff = (XFunctionBasic) bi.getFunction();

		class ColumnRenderer extends STReportColumnContents {

			@Override
			public SRPart getColumnBody(ColumnModel column, Object user) {
				final SRBlock block = new SRBlock();
				for (final String cfn : column.getConceptPaths()) {
					final IXFunction cf = CTReportUtils.getFunctionByPath(context, cfn);
					if (cf != null) {
						final List<Object> values = cf.get(domain);
						if (values != null && !values.isEmpty()) {
							block.add(CTReportUtils.formatHTMLCell(values));
						} else {
							// block.add(new SFPlain("&nbsp;").set(SRPart.NOESCAPE, true));
						}
					} else {
						System.err.println(cfn);
					}

					if (cf == ff) {
						final List<Object> values = bi.injectError(ff, domain);
						block.add(new SFBold("injected:"));
						block.add(CTReportUtils.formatHTMLCell(values));
					}
				}
				return block;
			}
		}

		final String kind = fullName(bi.getKind());

		final String title = "Injection: " + kind + " on " + ff.getName() + " for " + domain.toString();

		final SRPart part = CTReportUtils.buildTable(tm, title, new ColumnRenderer(), null);
		if (part != null) {
			container.add(part);
		}
	}

	private List<BaseInjection> filter(List<BaseInjection> correct) {
		final Collection<String> kinds = new HashSet<>();
		final List<BaseInjection> result = new ArrayList<>();
		for (final BaseInjection bi : correct) {
			if (!kinds.contains(bi.getKind()) && column(bi.getFunction().getName()) != null && !bi.getKind().equals("X")
					&& !bi.getKind().equals("A")) {
				result.add(bi);
				kinds.add(bi.getKind());
			}
		}
		return result;
	}

	private void buildReportChart(SRContainer parent) {
		final EIResult result = errorInjection.getResult();
		final Map<String, int[]> invalidByProperty = result.getByFunction(QUERY.INVALID);
		final Map<String, int[]> validByProperty = result.getByFunction(QUERY.VALID);
		final List<Integer> valid = new ArrayList<>(invalidByProperty.size());
		final List<Integer> invalid = new ArrayList<>(invalidByProperty.size());

		final Map<String, Integer> sensitivity = new HashMap<>();
		final Map<String, String> rating = new HashMap<>();

		for (final String s : invalidByProperty.keySet()) {
			final int a = Arrays.stream(validByProperty.get(s)).sum();
			final int b = Arrays.stream(invalidByProperty.get(s)).sum();
			valid.add(a);
			invalid.add(b);
			int category = rating((double) b / (a + b));
			sensitivity.put(s, category);
			
			StringBuilder ratingString = new StringBuilder();
			for(int i = 0; i < result.getKinds().length; i++) {
				String kind = result.getKinds()[i];
				int k_valid = validByProperty.get(s)[i];
				int k_invalid = invalidByProperty.get(s)[i];
				int k_category = rating( (double) k_invalid / (k_valid + k_invalid));
				String ratingInfo = rating(k_category, kind);
				if (ratingInfo.length() > 0) {
					ratingString.append(rating(k_category, kind));
					ratingString.append(' ');
				}
			}
			
			if (ratingString.length() == 0) {
				ratingString.append("none");
			}
			
			rating.put(s, ratingString.toString());
		}

		final List<String> ll = Arrays.asList(result.getFunctions());

		if (!ll.isEmpty()) {
			final SRContent chart = reportChart(valid, invalid, ll);
			parent.add(chart);
			final SRList list = new SRList(parent, "The property sensitivity is");
			for (final String s : sensitivity.keySet()) 
				list.add(sensitivityLabel(sensitivity.get(s)) + " for " + getColumnName(s));
			
			final SRList list2 = new SRList(parent, "The property rating is");
			for (final String s : sensitivity.keySet()) 
				list2.add(rating.get(s) + " for " + getColumnName(s));			
		}
	}

	private static int rating(final double z) {
		int category = 0;
		if (z >= 0.98) {
			category = 4;
		} else if (z >= 0.75) {
			category = 3;
		} else if (z >= 0.35) {
			category = 2;
		} else if (z > 0.001) {
			category = 1;
		}
		return category;
	}

	private String column(String concept) {
		if (context.getConceptMap() == null) {
			return null;
		}

		final String canonical = context.getFunction(concept).getCanonicalName();
		if (canonical == null) {
			return null;
		}

		return context.getConceptMap().getConceptProvenance(canonical);
	}

	private String getColumnName(String concept) {
		final String name = column(concept);
		if (name != null) {
			return name;
		} else {
			return concept;
		}
	}

	private String sensitivityLabel(int sensitivity) {
		switch (sensitivity) {
		case 0:
			return "absent";
		case 1:
			return "low";
		case 2:
			return "medium";
		case 3:
			return "high";
		default:
			return "complete";
		}
	}
	
	public static String rating(int invalid, int valid, String kind) {
		double c = (double) invalid / (valid + invalid);
		return rating(rating(c), kind);
	}

	public static String rating(int sensitivity, String kind) {
		switch (sensitivity) {
		case 0:
			return "-";
		case 1:
			return kind.toLowerCase();
		case 2:
			return kind.toLowerCase() + kind.toLowerCase();
		case 3:
			return kind.toUpperCase();
		default:
			return kind.toUpperCase() + kind.toUpperCase();
		}
	}	

	private void buildConceptCharts(SRContainer document) {
		final EIResult result = errorInjection.getResult();
		final Map<String, int[]> invalidByFunction = result.getByFunction(QUERY.INVALID);
		final Map<String, int[]> totalByFunction = result.getByFunction(QUERY.ALL);

		for (final String s : invalidByFunction.keySet()) {
			final int[] invalid = invalidByFunction.get(s);
			final int[] total = totalByFunction.get(s);
			final double totalMax = Arrays.stream(total).max().getAsInt();
			final int totalAll = Arrays.stream(total).sum();
			final double[] data_1 = new double[invalid.length];
			final double[] data_2 = new double[invalid.length];
			for (int i = 0; i < invalid.length; i++) {
				if (total[i] > 0) {
					data_1[i] = sigmoid(invalid[i] / totalMax);
				} else {
					data_1[i] = 0.1;
				}

				if (totalMax > 0) {
					data_2[i] = sigmoid(total[i] / totalMax);
				} else {
					data_2[i] = 0.1;
				}
			}

			final SRGrid grid = new SRGrid(2);

			final SRList list = new SRList();
			list.add("Total injections " + totalAll);
			for (int i = 0; i < result.getKinds().length; i++) {
				final String kind = result.getKinds()[i];
				list.add(fullName(kind) + ": invalid " + invalid[i] + ", total " + total[i]);
			}

			final String[] ll = Arrays.stream(result.getKinds()).map(InvestigationReport::fullName).toArray(size -> new String[size]);
			grid.add(conceptChart(s, data_1, data_2, ll));
			grid.add(list);

			document.add(grid);
		}

	}

	private double sigmoid(double d) {
		return d * 0.9 + 0.1;
	}

	private SRContent reportChart(List<Integer> valid_l, List<Integer> invalid_l, List<String> labels) {
		final String title = "Injection testing for " + conjecture.getName();
		final CategoryChart chart = new CategoryChartBuilder().width(200 + valid_l.size() * 150).height(300).yAxisTitle("Injections")
				.theme(ChartTheme.Matlab).build();

		chart.getStyler().setPlotGridVerticalLinesVisible(false);
		chart.getStyler().setStacked(true);
		chart.getStyler().setPlotBorderColor(Color.WHITE);
		chart.getStyler().setLegendBorderColor(Color.WHITE);
		chart.getStyler().setSeriesColors(new Color[] { new Color(40, 200, 40, 150), new Color(250, 40, 40, 150) });

		chart.addSeries("Valid", labels, valid_l);
		chart.addSeries("Invalid", labels, invalid_l);

		return srxChart.insertXChart(chart, title);
	}

	private SRContent conceptChart(String concept, double[] data_1, double[] data_2, String[] labels) {
		final RadarChart chart = new RadarChartBuilder().width(400).height(400).theme(ChartTheme.Matlab).build();

		chart.setTitle(concept);
		chart.getStyler().setLegendVisible(false);
		chart.getStyler().setPlotBorderVisible(false);
		chart.getStyler().setAntiAlias(true);
		chart.getStyler().setAnnotationsFont(chart.getStyler().getBaseFont().deriveFont(8.0f));
		chart.setRadarRenderStyle(RadarRenderStyle.Polygon);
		chart.setVariableLabels(labels);
		chart.addSeries("Overall", data_2);
		chart.addSeries("Sensitivity", data_1);
		chart.getStyler().setSeriesColors(new Color[] { new Color(120, 120, 120, 80), new Color(250, 40, 40, 150) });

		return srxChart.insertXChart(chart, concept);
	}

	private static String fullName(String kind) {
		if ("A".equals(kind)) {
			return "Add";
		} else if ("R".equals(kind)) {
			return "Remove";
		} else if ("M".equals(kind)) {
			return "Mutate";
		} else if ("X".equals(kind)) {
			return "Exclude";
		} else if ("S".equals(kind)) {
			return "Swap";
		} else {
			return kind;
		}
	}

}
