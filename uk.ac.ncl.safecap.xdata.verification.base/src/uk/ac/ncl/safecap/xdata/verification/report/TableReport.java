package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import uk.ac.ncl.safecap.xdata.base.tablesmodel.ColumnModel;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ITablePart;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.TableModel;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.TablesModel;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.NullVerificationProgressMonitor;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.core.VerificationBasePlugin;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.ConceptModelExperiment;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.EIResult;
import uk.ac.ncl.safecap.xdata.verification.report.ConjectureExpandedReport.SectionId;
import uk.ac.ncl.safecap.xmldata.FileUtil;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.base.ISDADataProvider;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;

public class TableReport extends GenericReportHtml {
	private static final double[] WEIGHTS = new double[] { 1.0, 0.2, 0.2, 0.0, 0.0 };
	private static final int RUNS = 20;
	private final TablesModel model;
	private final Collection<String> unassigned;
	private final Map<String, String> canonicalToId;

	private final Stack<SectionId> sectionStack;
	private SectionId current;

	public TableReport(String title, TablesModel model, ISDADataProvider context, RootCatalog project, Collection<String> unassigned,
			Map<String, String> canonicalToId) {
		super(title, project);
		this.model = model;
		this.unassigned = unassigned;
		this.canonicalToId = canonicalToId;
		sectionStack = new Stack<>();
		current = new SectionId();
	}

	void report() {

	}

	public void build(final File file) throws Exception {
		documentStart();

		statistics();

		for (final TableModel table : model.getTables()) {
			process(table);
		}

		if (!unassigned.isEmpty()) {
			sectionStart("property", sanitize("unassigned"));
			subsection("title", "Unassigned XPath's");
			listStart("1");
			for (final String s : unassigned) {
				formatConceptPath(s);
			}
			listEnd();
			sectionEnd();
		}

		documentEnd();

		if (file != null) {
			FileUtil.setFileContents(super.toString(), file);
		}
	}

	private class Stats {
		int totalColumns;
		int columnsWithConcepts;
		int columnsWithProperties;

	}

	private void statistics() {
		sectionStart("property", "stats");
		subsection("title", "Statistics");

		final Stats stats = new Stats();
		for (final TableModel table : model.getTables()) {
			walk(stats, table);
		}

		sectionStart("block");
		print("Statistics:");
		listStart("*");
		listItem("Total tables: " + model.getTables().size());
		listItem("Total properties: " + VisitorUtils.getAllConjectures(project).size());

		int signallingIds = 0;
		int derivedIds = 0;
		for (final String fid : context.getFunctionIds()) {
			final IXFunction f = context.getFunction(fid);
			if ("signalling".equals(f.getTag())) {
				signallingIds++;
			} else if ("derived".equals(f.getTag())) {
				derivedIds++;
			}
		}

		listItem("Total concepts: " + context.getFunctionIds().size());
		listItem("Total signalling concepts: " + signallingIds);
		listItem("Total derived concepts: " + derivedIds);
		listItem("Total columns: " + stats.totalColumns);
		listItem("Column mapped into XML data: " + stats.columnsWithConcepts);
		listItem("Column with data and verification: " + stats.columnsWithProperties);
		listEnd();

		subsection("title", "Column chart");
		XDataChart<String> h = new XDataChart<>("Count", "Kind", new String[] { "Column" });
		h.set("Total", new double[] { stats.totalColumns });
		h.set("With data", new double[] { stats.columnsWithConcepts });
		h.set("Validated", new double[] { stats.columnsWithProperties });
		subsection("histrogram", new BarChartSVGRenderer(h).render());

		subsection("title", "Concept chart");
		h = new XDataChart<>("Count", "Kind", new String[] { "Concept" });
		h.set("Total concepts", new double[] { context.getFunctionIds().size() });
		h.set("Signalling", new double[] { signallingIds });
		h.set("Derived", new double[] { derivedIds });
		subsection("histrogram", new BarChartSVGRenderer(h).render());
		sectionEnd();
		sectionEnd();
	}

	private void walk(Stats stats, TableModel table) {
		for (final ITablePart part : table.getParts()) {
			if (part instanceof TableModel) {
				walk(stats, (TableModel) part);
			} else {
				walk(stats, (ColumnModel) part);
			}
		}
	}

	private void walk(Stats stats, ColumnModel part) {
		stats.totalColumns++;
		if (part.getConceptPaths() != null && part.getConceptPaths().size() > 0) {
			for (final String cp : part.getConceptPaths()) {
				final IXFunction function = isValidXPath(cp);
				if (function != null) {
					stats.columnsWithConcepts++;
					break;
				}
			}

			for (final String cp : part.getConceptPaths()) {

				if (getAffected(cp).size() > 0) {
					stats.columnsWithProperties++;
					break;
				}
			}
		}
	}

	private void process(TableModel table) {
		current.next();
		sectionStart("property", sanitize(table.getName()));
		subsection("title", getSectionIndex() + " " + table.getName());

		sectionStack.push(current);
		current = new SectionId();

		for (final ITablePart part : table.getParts()) {
			if (part instanceof TableModel) {
				final TableModel tmodel = (TableModel) part;
				process(tmodel);
			} else if (part instanceof ColumnModel) {
				final ColumnModel cmodel = (ColumnModel) part;
				process(cmodel);
			}
		}

		current = sectionStack.pop();

		sectionEnd();
	}

	private void process(ColumnModel column) {
		current.next();
		sectionStart("report");
		subsection("reportname", getSectionIndex() + " " + "Column " + column.getName());
		if (column.isPseudo()) {
			subsection("infomessage inotice", "auxiliary concept");
		}
		if (column.getConceptPaths() != null && !column.getConceptPaths().isEmpty()) {
			listStart("a");
			for (final String cp : column.getConceptPaths()) {
				formatConceptPath(cp);
			}
			listEnd();
			if (column.getSemantics() != null) {
				print("Semantics:" + column.getSemantics());
			} else if (column.getConceptPaths().size() > 1) {
				print("<pre contenteditable=\"true\">(semantics missing)</pre>");
			}

			// processExperiment(column.getConceptPaths());
		} else {
			print("<pre contenteditable=\"true\">(type in)</pre>");
			subsection("infomessage ierror", "XPath missing");
		}
		if (column.getReason() != null) {
			subsection("infobox", column.getReason());
		}
		sectionEnd();
	}

	private void processExperiment(List<String> conceptPaths) {
		sectionStart("property");
		subsection("title", "Statistical checking");
		final List<IXFunction> concepts = new ArrayList<>();
		for (final String s : conceptPaths) {
			final IXFunction function = isValidXPath(s);
			if (function != null) {
				concepts.add(function);
			}
		}
		try {
			if (concepts.size() > 0) {
				final ConceptModelExperiment experiment = new ConceptModelExperiment(project, concepts, WEIGHTS, RUNS);
				experiment.setFilterBaseInvalid(true);
				final EIResult result = experiment.execute(NullVerificationProgressMonitor.INSTANCE);
				if (result.getError() == null) {
//					XDataChart<String> h = ChartUtils.buildKindChartSingletonProperty(result, WEIGHTS);
//					subsection("histogram", new BarChartSVGRenderer(h).render());
				} else {
					subsection("infomessage ierror", result.getError());
				}
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}
		sectionEnd();
	}

	private void formatConceptPath(String cp) {
		final IXFunction function = isValidXPath(cp);
		final String editable = function != null ? "" : " contenteditable=\"true\" ";
		listItem("<pre" + editable + ">" + cp + "</pre>");

		if (function == null) {
			subsection("infomessage ierror", "XPath invalid");
		} else {
			final XFunctionBasic ff = (XFunctionBasic) function;
			if (ff.getDomainType() != null && ff.getRangeType() != null) {
				subsection("infoline",
						"Type: from " + ff.getDomainType().toStringNoBrackets() + " to " + ff.getRangeType().toStringNoBrackets());
			} else {
				subsection("infomessage ierror", "no type");
			}
			subsection("infoline", "Elements:" + ff.getMaps().size());
			if (ff.getDescription() != null && !ff.getDescription().isEmpty()) {
				subsection("infoline", "Description:" + ff.getDescription());
			}

			final boolean possiblyInvalid = ValidFlagUtil.hasPossiblyInvalidData(context, function);
			if (possiblyInvalid) {
				subsection("infomessage iwarning", "Some elements marked invalid");
				final XFunctionBasic validf = (XFunctionBasic) ValidFlagUtil.getValidFlagFunctionFor(context, function);
				if (validf != null) {
					sectionStart("block");
					print("<details>");
					print("<summary>Elements tagged invalid</summary>");
					listStart("1");
					for (final Object k : validf.domain()) {
						for (final Object o : validf.get(k)) {
							if (o instanceof Boolean && o.equals(Boolean.FALSE)) {
								print("<li>" + format(k, NO_PARAM) + "</li>");
								break;
							}
						}
					}
					listEnd();
					print("</details>");
					sectionEnd();

				}
			}

			final Collection<Conjecture> affected = getAffected(cp);
			if (!affected.isEmpty()) {
				sectionStart("block");
				print("Properties:");
				for (final Conjecture c : affected) {
					formatConjecture(c);
				}
				sectionEnd();
			}

		}

		print("<pre contenteditable=\"true\">(commentary)</pre>");

	}

	private void formatConjecture(Conjecture c) {
		sectionStart("block");
		subsection("title", c.getKey().empty() ? c.getId().content() : c.getKey().content() + ": " + c.getId().content());

		boolean isCorrectness = false;
		switch (c.getClassification().content()) {
		case NONE:
			subsection("infomessage iwarning", "no class");
			break;
		case CONSISTENCY:
			subsection("infomessage inotice", "consistency");
			break;
		case CORRECTNESS:
			subsection("infomessage inotice", "safety");
			isCorrectness = true;
			break;
		}

		final boolean isBalanced = SDAUtils.isBalanced(context, c);

		if (!isBalanced && isCorrectness) {
			subsection("infomessage ierror", "unbalanced");
		} else if (isBalanced && isCorrectness) {
			subsection("infomessage ipositive", "balanced");
		}

		final boolean hasSchemaDependency = SDAUtils.hasSchemaDepedency(context, c);

		if (hasSchemaDependency && isCorrectness) {
			subsection("infomessage iwarning", "schema dependency");
		}

		if (!c.isValid().empty() && !c.getVResult().empty()) {
			if (!c.isValid().content()) {
				subsection("infomessage ierror", "false");
				subsection("infomessage inotice", c.getVResult().content().toString());
			} else {
				subsection("infomessage ipositive", "true");
				subsection("infomessage inotice", c.getVResult().content().toString());
			}
		} else {
			subsection("infomessage iwarning", "unverified");
		}

		// if (c.getReports().isEmpty())
		// subsection("infomessage iwarning",
		// "no verification reports defined");

		sectionEnd();
	}

	private String getSectionIndex() {
		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < sectionStack.size(); i++) {
			sb.append(sectionStack.get(i).getIndex());
			sb.append('.');
		}

		return sb.toString() + current.getIndex();
	}

	private IXFunction isValidXPath(String path) {
		for (final String f : context.getFunctionIds()) {
			if (context.getFunction(f).getCanonicalName().equals(path)) {
				return context.getFunction(f);
			}
		}
		return null;
	}

	public Collection<Conjecture> getAffected(String xpath) {
		final String id = canonicalToId.get(xpath);
		if (id != null) {
			final DifferenceAnalysis diff = new DifferenceAnalysis(Collections.singleton(id));
			diff.compute(project);
			return diff.getAffectedConjectures();
		}

		return Collections.emptySet();
	}

	@Override
	protected void embeddedStyles() {
		try {
			print(VerificationBasePlugin.getLibFileContents("style_data.css"));
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}
}
