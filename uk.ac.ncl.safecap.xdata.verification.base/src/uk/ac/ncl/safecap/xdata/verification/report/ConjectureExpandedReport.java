package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Display;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.mb.ISolverCallback;
import uk.ac.ncl.prime.sim.lang.mb.NativeModelSolverExistential;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeAny;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.prime.sim.parser.CLFormulaParser;
import uk.ac.ncl.prime.sim.parser.CLValuationContext;
import uk.ac.ncl.prime.sim.parser.FormulaSource;
import uk.ac.ncl.safecap.common.IObjectResolver;
import uk.ac.ncl.safecap.ldl.image.ImageGenerator;
import uk.ac.ncl.safecap.ldl.image.ObjectHighlight;
import uk.ac.ncl.safecap.ldl.image.ObjectSet;
import uk.ac.ncl.safecap.xdata.provers.VerificationTool;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.ConjectureDescription;
import uk.ac.ncl.safecap.xdata.verification.INamed;
import uk.ac.ncl.safecap.xdata.verification.PropertyReport;
import uk.ac.ncl.safecap.xdata.verification.ReportKind;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.configsave.IProjectConfig;
import uk.ac.ncl.safecap.xdata.verification.configsave.ReportResult;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.core.VerificationBasePlugin;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;
import uk.ac.ncl.safecap.xmldata.base.ErrorLogEntry;

public class ConjectureExpandedReport extends GenericReportHtml {
	private final SDARuntimeExecutionContext model;
	private final CLFormulaParser parser;
	private final Stack<SectionId> sectionStack;
	private SectionId current;

	private final ReportConfig config;
	private ImageGenerator imageGenerator;
	private final Resource diagramResource;
	private final Display display;

	private final ReportResult reportResult;
	private final Html2Text htmlParser;

	private IProjectConfig projectConfig;

	public static class SectionId {
		private int index;

		public SectionId() {
			index = 0;
		}

		public int getIndex() {
			return index;
		}

		public void next() {
			index++;
		}
	}

	public ConjectureExpandedReport(Display display, Resource diagramResource, ReportConfig config, RootCatalog project) {
		super("Verification report", project);

		this.config = config;

		model = context.getRootRuntimeContext();
		parser = VerificationTool.getParser(project);
		sectionStack = new Stack<>();
		current = new SectionId();
		this.diagramResource = diagramResource;
		this.display = display;
		reportResult = new ReportResult();
		htmlParser = new Html2Text();
	}

	public void setProjectConfig(IProjectConfig projectConfig) {
		this.projectConfig = projectConfig;
	}

	public ReportResult getReportResult() {
		return reportResult;
	}

	public void setImageGenerator(ImageGenerator imageGenerator) {
		this.imageGenerator = imageGenerator;
	}

	public void build(final File file) throws Exception {

		initializeImageGenerator(file);

		context.start("Report");

		super.documentStart();

		current.next();
		sectionStack.push(current);
		current = new SectionId();

		// current = sectionStack.pop();
		// current.next();
		// sectionStack.push(current);
		// current = new SectionId();

		Collection<Conjecture> conjectures = config.getSelectedConjecturesSet();
		if (conjectures == null) {
			conjectures = config.isShowSuccessful() ? VisitorUtils.getAllConjectures(project)
					: VisitorUtils.getAllFailedConjectures(project);
		}

		for (final Conjecture property : conjectures) {
			if (config.isShowSuccessful() || !isValid(property)) {
				if (config.isShowUnknown() || !property.isValid().empty()) {
					switch (property.getKind().content()) {
					case ERROR:
						build(property);
						break;
					case WARNING:
						if (config.isShowWarning()) {
							build(property);
						}
						break;
					case NOTICE:
						if (config.isShowInfo()) {
							build(property);
						}
						break;
					}
				}
			}
		}

		/*
		 * if (config.isShowNotCoveredConcepts()) { CoverageAnalysis coverage = new
		 * CoverageAnalysis(conjectures); if
		 * (!coverage.getUncoveredConcepts().isEmpty()) { List<String> sorted = new
		 * ArrayList<String>(coverage.getUncoveredConcepts()); Collections.sort(sorted);
		 * sectionStart("coverage"); for(String s: sorted) subsection("coveragepart",
		 * s);
		 * 
		 * sectionEnd(); } }
		 */

		// buildErrorReport();

		super.documentEnd();
//
//		if (file != null)
//			FileUtil.setFileContents(super.toString(), file);
	}

	private void initializeImageGenerator(final File file) {
		if (diagramResource != null && display != null) {
			display.syncExec(new Runnable() {
				@Override
				public void run() {
					try {
						if (imageGenerator == null) {
							imageGenerator = new ImageGenerator(diagramResource);
						}

						imageGenerator.initialize(file.getParentFile());
					} catch (final Throwable e) {
						e.printStackTrace();
						imageGenerator = null;
					}
				}
			});
		}
	}

	private boolean isValid(Conjecture property) {
		return !property.isValid().empty() && property.isValid().content();
	}

	// private void buildErrorReport() {
	// for (String section : context.getSections()) {
	// sectionStart("errorsection");
	// current.next();
	// section("title", getSectionIndex() + " " + section);
	// buildErrorReport(context.getEntries(section));
	// sectionEnd();
	// }
	//
	// }

	private void buildErrorReport(Collection<ErrorLogEntry> entries) {
		for (final ErrorLogEntry entry : entries) {
			switch (entry.getSeverity()) {
			case CRITICAL:
				section("critical", formatEntry(entry));
				break;
			case WARNING:
				section("warning", formatEntry(entry));
				break;
			case INFO:
				section("info", formatEntry(entry));
				break;
			}
		}

	}

	private String formatEntry(ErrorLogEntry entry) {
		return entry.getDescription();
	}

	@Override
	protected void embeddedStyles() {
		try {
			print(VerificationBasePlugin.getLibFileContents("style_data.css"));
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	private String getSectionIndex() {
		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < sectionStack.size(); i++) {
			sb.append(sectionStack.get(i).index);
			sb.append('.');
		}

		return sb.toString() + current.index;
	}

	private void build(Conjecture property) {

		final boolean unknown = property.isValid().empty();
		final boolean proven = !unknown && property.isValid().content();

		/*
		 * String sub_class = property.getKind().content().name().toLowerCase(); if
		 * (unknown) sub_class = "unknown"; else if (proven) sub_class = "success";
		 */

		context.getCurrentSection().clear();

		final String prefix = SDAUtils._fullElementPrefixPoint(property);
		final String title = property.getKey().empty() ? property.getId().content()
				: property.getKey().content() + ":" + property.getId().content();

		// sectionStart("property " + sub_class, sanitize(prefix + "_" +
		// title));
		sectionStart("property", sanitize(prefix + "_" + title));
		current.next();

		subsection("title", getSectionIndex() + " " + title);
		// subsection("subtitle", "(" + prefix + ")");
		if (unknown) {
			subsection("infomessage iwarning", "unknown");
		} else if (proven) {
			subsection("infomessage inotice", "true");
		} else {
			subsection("infomessage ierror", "false");
		}

		if (!property.getComment().empty()) {
			subsection("comment", sanitizeText(escapeHTML(property.getComment().content())));
		}

		if (config.isShowPredicate()) {
			if (property.getParsed().empty()) {
				subsection("infomessage ierror", "no formal verification statement defined");
				for (final ConjectureDescription des : property.getDescriptions()) {
					subsection("infomessage iwarning", des.getDescription().content());
				}
			} else {
				subsection("predicate", sanitizeText(escapeHTML(property.getFormula().content())));
			}
		}

		// if (!property.getVResult().empty())
		// subsection("provers", property.getVResult().content().toString());

		if (config.isShowInvalid() && !property.getParsed().empty()) {
			if (ValidFlagUtil.hasPossiblyInvalidData(context, property.getParsed().content())) {
				subsection("infomessage iwarning", "source data might be invalid");
			}
		}

		if (!unknown && !proven) {
			if (!property.getReports().empty()) {
				sectionStack.push(current);
				current = new SectionId();
				for (final PropertyReport report : property.getReports()) {
					handlePropertyReport(report);
				}
				current = sectionStack.pop();
			} else {
				subsection("infomessage iwarning", "no verification report defined");
			}

			if (config.isShowRelated()) {
				final SortedSet<Entry<Conjecture, Double>> related = entriesSortedByValues(
						VisitorUtils.getAllRelated(property, config.getRelatedThreshold()));
				if (!related.isEmpty()) {
					sectionStart("related");
					print("<b>Related properties:</b>");
					listStart("1");
					try {
						final Iterator<Entry<Conjecture, Double>> iterator = related.iterator();
						int maxr = config.getRelatedMax();
						while (iterator.hasNext() && maxr > 0) {
							final Entry<Conjecture, Double> v = iterator.next();
							reportRelated(v.getKey(), v.getValue());
							maxr--;
						}
					} finally {
						listEnd();
						sectionEnd();
					}
				}
			}
		}

		if (!context.getCurrentSection().getEntries().isEmpty()) {
			buildErrorReport(context.getCurrentSection().getEntries());
		}

		sectionEnd();

	}

	private void reportRelated(Conjecture c, Double rank) {
		final String prefix = SDAUtils._fullElementPrefixPoint(c);
		final String title = c.getId().content();
		final int h = (int) (rank * 16.0);
		listItem("<a class=\"relatedlink\" href=\"#" + sanitize(prefix + "_" + title) + "\">" + title + "</a>"
				+ " <span style=\"font-size: 50%; background-color: gray; vertical-align: middle; padding: 0px " + h
				+ "px\">&nbsp;</span>");
	}

	static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
		final SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<>(new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
				final int res = e2.getValue().compareTo(e1.getValue());
				return res != 0 ? res : 1;
			}
		});
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}

	private void handlePropertyReport(final PropertyReport pd) {
		try {
			current.next();
			final SDARuntimeExecutionContext runtime_context = parser.getModel();
			final TypingContext typing = runtime_context.getRootContext();

			final boolean findAll = pd.getReportKind().content() == ReportKind.SINGLE && config.getWitnessMax() > 1;

			pd.getParsed().clear();
			final FormulaSource source = SDAUtils.getSource(pd);
			if (source == null) {
				return;
			}
			final CLExpression parsed = parser.parseOnly(source);
			if (parsed != null) {
				final TypingContext extra = new TypingContext(typing);
				if (parsed.getFreeIdentifiers() != null) {
					for (final String name : parsed.getFreeIdentifiers()) {
						if (typing.getType(name) == null) {
							extra.addSymbol(name, new CLTypeAny(null), SYMBOL_CLASS.IDENTIFIER);
						}
					}
				}

				// System.out.println("fresh names:" + extra.getNewSymbols());

				parsed.type(extra, CLTypeBool.INSTANCE);
				extra.bakeTypes();
				// extra.debug_print_typeSolutions();
				pd.setParsed(parsed);

				boolean reportSuccess = false;
				if (!source.hasErrors() && parsed.getType() != null) {
					final NativeModelSolverExistential solver = new NativeModelSolverExistential(extra, parsed, extra.getNewSymbols());
					solver.setFindNumber(config.getWitnessMax());
					if (findAll) {
						sectionStack.push(current);
						current = new SectionId();
						solver.setCallback(new ISolverCallback() {
							@Override
							public void solved(Map<String, Object> solution, int index) {
								try {
									if (config.isWitnessLongForm() || solver.getFoundSolutions() == 1) {
										current.next();
										emitReport(extra, pd, solution);
									} else {
										emitExtraWitness(extra, pd, solution, solver.getFoundSolutions());
									}
								} catch (final Throwable e) {
									context.logError(e);
									e.printStackTrace();
								}
							}
						});
						solver.solve();
						reportSuccess = solver.getFoundSolutions() > 0;
						current = sectionStack.pop();
					} else {
						solver.solve();
						if (solver.isSolved()) {
							emitReport(extra, pd, solver.getSolutions());
						}
					}

					if (reportSuccess) {
						if (solver.hasFoundAll()) {
							subsection("infomessage inotice", "no more witnesses");
						} else {
							subsection("infomessage iwarning", "possibly more witnesses");
							subsection("infomessage inotice",
									"found " + solver.getFoundSolutions() + " witnesses with " + config.getWitnessMax() + " limit");
						}
					} else {
						// subsection("failedreport",
						// "Could not compute report \"" + pd.getId().content()
						// + "\" for this property");
					}
				}
			} else {
				pd.getParsed().clear();
			}

			source.mark();
		} catch (final Throwable e) {
			e.printStackTrace();
			System.err.println("Error in property report '" + pd.getId().content() + "' of property '"
					+ ((INamed) pd.parent().element()).getId().content() + "': " + e.getMessage());
			context.logError(e);
			context.logError("Error in property report " + pd.getId().content() + " of property "
					+ ((INamed) pd.parent().element()).getId().content());
		}
	}

	protected void emitExtraWitness(TypingContext extra, PropertyReport pd, Map<String, Object> solution, int index) {
		if (index == 2) {
			subsection("extrawitnessstart", "Additional elements violating this property");
		}

		final StringBuilder sb = new StringBuilder();
		int i = 0;
		sb.append("(");
		for (final String k : solution.keySet()) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(format(solution.get(k), NO_PARAM));
			// emitElementImage(solution.get(k));
			i++;
		}
		sb.append(")");
		subsection("extrawitness", sb.toString());
	}

	private void emitElementImage(final IObjectResolver resolver, final String keyElements) {
		if (imageGenerator != null && display != null && resolver != null && keyElements.length() > 0) {

			display.syncExec(new Runnable() {
				@Override
				public void run() {
					final ObjectHighlight highlight = new ObjectHighlight(resolver, keyElements);
					final StringBuilder sb = new StringBuilder();
					final boolean success = imageGenerator.getInlineImage(sb, highlight);
					if (success) {
						subsection("elementimage", sb.toString());
					}
				}
			});

		}
	}

	private void emitReport(final TypingContext extra, PropertyReport pd, Map<String, Object> solutions) throws Exception {
		final SDARuntimeExecutionContext newContext = new SDARuntimeExecutionContext(model);
		final CLValuationContext state = newContext.getStateContext();

		String indexName = null;
		String locationText = null;
		String descriptionText = null;

		for (final String var : solutions.keySet()) {
			state.defineNew(var, solutions.get(var));
		}

		sectionStart("report");

		indexName = getSectionIndex();
		subsection("reportname", indexName + " " + pd.getId().content());

		try {
			if (!pd.getLocation().getFormula().empty()) {
				locationText = expand(parser, extra, sanitizeText(pd.getLocation().getFormula().content()), newContext);
				subsection("location", locationText);
			}

		} catch (final Exception e) {
			System.err.println("Error in Location part of property report '" + pd.getId().content() + "' of property '"
					+ ((INamed) pd.parent().element()).getId().content() + "': " + e.getMessage());
		}

		try {
			if (!pd.getDescription().getFormula().empty()) {
				descriptionText = expand(parser, extra, sanitizeText(pd.getDescription().getFormula().content()), newContext);
				subsection("description", descriptionText);
			}
		} catch (final Exception e) {
			System.err.println("Error in Description part of property report '" + pd.getId().content() + "' of property '"
					+ ((INamed) pd.parent().element()).getId().content() + "': " + e.getMessage());
		}

		try {
			if (!pd.getSolution().getFormula().empty() && config.isShowSolution()) {
				subsection("solution", expand(parser, extra, sanitizeText(pd.getSolution().getFormula().content()), newContext));
			}
		} catch (final Exception e) {
			System.err.println("Error in Solution part of property report '" + pd.getId().content() + "' of property '"
					+ ((INamed) pd.parent().element()).getId().content() + "': " + e.getMessage());
		}

		if (config.isShowIllustration() && !pd.getKeyElements().empty()) {

			final IObjectResolver resolver = getObjectResolver(parser, diagramResource, extra, newContext, true);

			emitElementImage(resolver, pd.getKeyElements().content());

			if (config.isShowExtermalMapLinks()) {
				final IObjectResolver resolver2 = getObjectResolver(parser, diagramResource, extra, newContext, false);
				final ObjectHighlight highlight2 = new ObjectHighlight(resolver2, pd.getKeyElements().content());
				final Map<String, Collection<String>> links = new HashMap<>();
				for (final ObjectSet os : highlight2.getHighlights()) {
					for (final Object z : os.getObjects()) {
						final String name = z.toString();
						for (final String s : context.getExternalMap(name)) {
							if (links.containsKey(name)) {
								links.get(name).add(s);
							} else {
								final List<String> l = new ArrayList<>(2);
								l.add(s);
								links.put(name, l);
							}
						}
					}
				}

				sectionStart("related");
				print("<b>Source tables:</b>");
				listStart("1");

				for (final String id : links.keySet()) {
					for (final String t : links.get(id)) {
						emitExternalLink(id, t);
					}
				}

				listEnd();
				sectionEnd();

			}

		}

		sectionEnd();

//		reportResult.addPropertyViolation(((Conjecture) pd.parent().element()).getId().content(), solutions.values(), indexName,
//				htmlParser.convert(locationText));

		// model.popContext();
	}

	private void emitExternalLink(String name, String s) {
		final File file = SDAUtils.resolveFileReference(context, s);
		if (file != null) {
			listItem(name + ": <a href=\"file://" + file.getAbsolutePath() + "\" target=\"_blank\">" + s + "</a>");
		}
	}

}
