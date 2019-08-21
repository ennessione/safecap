package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Display;

import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLForeignLocationExpression;
import uk.ac.ncl.prime.sim.lang.CLForeignLocationValue;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.typing.BProto;
import uk.ac.ncl.prime.sim.lang.typing.CLGivenType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeString;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.prime.sim.parser.CLFormulaParser;
import uk.ac.ncl.prime.sim.sets.BRel;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.common.IObjectResolver;
import uk.ac.ncl.safecap.common.MD5Checksum;
import uk.ac.ncl.safecap.db.common.DBUtils;
import uk.ac.ncl.safecap.db.common.PlatformFingerprint;
import uk.ac.ncl.safecap.ldl.image.ImageGenerator;
import uk.ac.ncl.safecap.ldl.image.ObjectHighlight;
import uk.ac.ncl.safecap.ldl.image.ObjectSet;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.xdata.provers.VerificationTool;
import uk.ac.ncl.safecap.xdata.verification.DataReference;
import uk.ac.ncl.safecap.xdata.verification.INamed;
import uk.ac.ncl.safecap.xdata.verification.ITag;
import uk.ac.ncl.safecap.xdata.verification.PredicateCategory;
import uk.ac.ncl.safecap.xdata.verification.PredicateDefinition;
import uk.ac.ncl.safecap.xdata.verification.PredicateKind;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.InvariantInfo;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.core.VerificationBasePlugin;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;
import uk.ac.ncl.safecap.xdata.verification.registry.ProveFragmentsUtils;
import uk.ac.ncl.safecap.xdata.verification.registry.ProveFragmentsUtils.IRPremise;
import uk.ac.ncl.safecap.xdata.verification.report.ConjectureExpandedReport.SectionId;
import uk.ac.ncl.safecap.xdata.verification.report.SubInvariantReportResult.PositiveCaseInfo;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransitionPathSource;
import uk.ac.ncl.safecap.xdata.verification.transitions.LocatedElement;
import uk.ac.ncl.safecap.xdata.verification.transitions.LocatedEntity;
import uk.ac.ncl.safecap.xdata.verification.transitions.LocatedString;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionCluster;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionContainer;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionContainer.SourceInformation;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionContainerInfo;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionContainerInvariantInfo;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionUtil;
import uk.ac.ncl.safecap.xmldata.FileUtil;

public class InvariantReport extends GenericReportHtml {
	private static final boolean OPTION_SHOW_CODE_FRAGMENT = false;
	private static final boolean OPTION_SHOW_FULL_POSITIVE = false;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	private final SubInvariantReportResult result;

	private final SDARuntimeExecutionContext model;
	private final CLFormulaParser parser;

	private final Stack<SectionId> sectionStack;
	private SectionId current;

	private ImageGenerator imageGenerator;
	private final Resource diagramResource;
	private final Display display;

	private TransitionContainer transitionContainer;
	private TransitionContainerInfo transitionContainerInfo;

	private final boolean indexEnabled = false;

	public InvariantReport(Display display, Resource diagramResource, RootCatalog project) {
		super("Verification Report", project);
		model = context.getRootRuntimeContext();
		parser = VerificationTool.getParser(project);
		sectionStack = new Stack<>();
		current = new SectionId();
		this.diagramResource = diagramResource;
		this.display = display;
		result = new SubInvariantReportResult(project);
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

	private void initiliazeTransitionContainerInfo() {
		for (final DataReference tdr : context.getTransitionReferences()) {
			transitionContainer = context.getTransitionContainer(tdr);
			transitionContainerInfo = new TransitionContainerInfo(transitionContainer);
			transitionContainerInfo.compute();
			break;
		}
	}

	public void setImageGenerator(ImageGenerator imageGenerator) {
		this.imageGenerator = imageGenerator;
	}

	private void buildOverviewPieChart() {
		final int totalGoodInvariants = result.getAllInvariants().size();
		final int totalInvariants = VisitorUtils.countAllPredicatesOfKind(project, PredicateKind.INVARIANT);
		final int unfinishedInvariants = totalInvariants - totalGoodInvariants;
		final int notSelectedInvariants = totalGoodInvariants - result.getProvedInvariants().size();
		final int invalid = result.getSubreports().size();
		final int valid = result.getProvedInvariants().size() - invalid;

		final Map<String, Integer> map = new LinkedHashMap<>();
		map.put("Invalid", invalid);
		map.put("Valid", valid);
		map.put("Unfinished", unfinishedInvariants);
		map.put("Not selected", notSelectedInvariants);

		Chart_DonutNoLabel(map);
	}

	private InvariantInfo getInvariantInfo(String name) {
		for (final InvariantInfo ii : result.getAllInvariants()) {
			if (ii.name.equals(name)) {
				return ii;
			}
		}
		return null;
	}

	private void buildSplitPieChart() {
		final List<String> labels = new ArrayList<>();
		final List<Integer> values = new ArrayList<>();
		final List<String> keys = new ArrayList<>(result.getSubreports().keySet());
		Collections.sort(keys);
		for (final String s : keys) {
			final InvariantInfo ii = getInvariantInfo(s);
			if (ii != null) {
				labels.add(ii.key);
			} else {
				labels.add(s);
			}
			values.add(result.getSubreports().get(s).size());
		}

		if (labels.size() > 0) {
			super.Chart_Bar(labels, values, "Violations");
		}
	}

	public void build(final File file) throws Exception {
		initializeImageGenerator(file);
		initiliazeTransitionContainerInfo();

		// commitSubReports();

		setFolder(file.getParentFile());

		documentStart();

		buildProlog();
		
		buildOverviewPieChart();

		buildSplitPieChart();
		
		buildIndex();

		for (final PredicateCategory pc : project.getPredicateCategories()) {
			if (hasInvariants(pc)) {
				build(pc);
			}
		}

		documentEnd();

		FileUtil.setFileContents(super.toString(), file);

	}
	
	private void buildProlog() {
		listUStart();
			listItem("Date: " + new Date());
			listItem("UUID: " + PlatformFingerprint.getUUID());
			listItem("SafeCap version: " + DBUtils.getSafeCapVersion());
			listItem("Model version: " + project.getId().content());
		listUEnd();
	}	

	private void buildIndex() {
		listUStart();
		for (InvariantInfo inv : result.getAllInvariants()) {
			PredicateDefinition pd = inv.source;
			final String name = pd.getId().content();
			final String key = pd.getKey().content();
			listItem(linkToLocal(key + "_" + name, key + " " + name));
			if (!result.getProvedInvariants().contains(inv)) {
				listStart("a");
				listItem("Not checked");
				listEnd();
			} else if (!result.getSubreports().containsKey(name)) {
				listStart("a");
				listItem("Correct");
				ppPositiveInList(inv);
			} else {
				buildIndex(inv);
			}

		}
//		for (final PredicateCategory pc : project.getPredicateCategories()) {
//			if (hasInvariants(pc)) {
//				buildIndex(pc);
//			}
//		}
		listUEnd();
	}

	private void ppPositiveInList(InvariantInfo inv) {
		List<PositiveCaseInfo> contr = result.getContradictions().get(inv);
		if (contr != null) {
			listItem("Positive cases");
			listStart("*");
			if (OPTION_SHOW_FULL_POSITIVE) {
				for (PositiveCaseInfo ci : contr) {
					listItem(ci.getSource().getPath());
					describePositiveCase(ci);
				}
			} else {
				int total = contr.size();
				int contradictions = 0;
				long time = 0;
				int branches = 0;
				int steps = 0;
				for (PositiveCaseInfo ci : contr) {
					if (ci.isContradiction())
						contradictions++;
					time += ci.getPo().getResult().getTime();
					branches += ci.getPo().getGoals().size();
					steps += ci.getSteps();
				}
				listItem("Number of proof obligations: " + total);
				listItem("Of these, relying on contradiction: " + contradictions);
				/*
				listStart("1");
				for (PositiveCaseInfo ci : contr) {
					if (ci.isContradiction()) {
						listItem(ci.getSource().getPath());
						listItem("Contradictions:");
						listStart("1");
						for(ProofGoal pg: ci.getPo().getGoals()) {
							if (pg.getGoal().getTransform().equals(ContradictionInHyp.NAME) && pg.getGoal().getProperty("configuration") instanceof ContradictionInHypConfiguration) {
								ContradictionInHypConfiguration config = (ContradictionInHypConfiguration) pg.getGoal().getProperty("configuration");
								listItem(config.getName());
								listStart("1");
								for(CLExpression e: config.getHypothesis()) {
									listItem(e.asStringWithLocation());
								}
								listEnd();
							}
						}
						listEnd();
					}
				}
				listEnd();
				*/
				listItem("Proof time: " + time);
				listItem("Branches: " + branches);
				listItem("Steps: " + steps);

			}
			listEnd();
		}
		listEnd();
	}

	private void describePositiveCase(PositiveCaseInfo ci) {
		listStart("*");
		if (ci.isContradiction())
			listItem("<b>the proof relies on contradiction in hypothesis</b>");
		listItem("Steps: " + ci.getSteps());
		listItem("Branches: " + ci.getPo().getGoals().size());
		listItem("Closing tactics: " + ci.getClosingTactics());
		listEnd();

	}

//	private void buildIndex(PredicateCategory pc) {
//		if (containsInvariants(pc)) {
//			listItemStart(pc.getId().content());
//			listUStart();
//			for (final PredicateCategory pc2 : pc.getPredicateCategories()) {
//				if (hasInvariants(pc2)) {
//					buildIndex(pc2);
//				}
//			}
//
//			for (final PredicateDefinition pd : pc.getPredicates()) {
//				if (pd.getKind().content() == PredicateKind.INVARIANT) { //  && result.getSubreports().containsKey(pd.getId().content())
//					buildIndex(pd);
//				}
//			}
//			listUEnd();
//			listItemEnd();
//		} else {
//			decentIndex(pc);
//		}
//	}

//	private void decentIndex(PredicateCategory pc) {
//		for (final PredicateCategory pc2 : pc.getPredicateCategories()) {
//			if (hasInvariants(pc2)) {
//				buildIndex(pc2);
//			}
//		}
//	}

	private void buildIndex(InvariantInfo inv) {
		PredicateDefinition pd = inv.source;
		final String name = pd.getId().content();
		listStart("a");
		final List<SubInvariantReport> subreport = result.getSubreports().get(name);
		if (subreport == null) {
			listItem("Not checked");
		} else {
			for (final SubInvariantReport sr : subreport)
				for (final SubInvariantReportIssue issue : sr.issues)
					listItem(formatIssueFull(sr, issue));
		}
		ppPositiveInList(inv);
		listEnd();
	}

	private void build(PredicateCategory pc) {
		if (containsInvariants(pc)) {
			current.next();
			sectionStart("property", sanitize(pc.getId().content()));
			subsection("title", printIndex() + pc.getId().content());

			sectionStack.push(current);
			current = new SectionId();
			for (final PredicateCategory pc2 : pc.getPredicateCategories()) {
				if (hasInvariants(pc2)) {
					build(pc2);
				}
			}
			current = sectionStack.pop();

			sectionStack.push(current);
			current = new SectionId();
			for (final PredicateDefinition pd : pc.getPredicates()) {
				if (pd.getKind().content() == PredicateKind.INVARIANT && result.getSubreports().containsKey(pd.getId().content())) {
					build(pd);
				}
			}
			current = sectionStack.pop();
			sectionEnd();
		} else {
			decent(pc);
		}
	}

	private void decent(PredicateCategory pc) {
		for (final PredicateCategory pc2 : pc.getPredicateCategories()) {
			if (hasInvariants(pc2)) {
				build(pc2);
			}
		}
	}

	private void build(PredicateDefinition pd) {
		final String name = pd.getId().content();
		final String key = pd.getKey().content();

		current.next();
		sectionStart("property", sanitize(key + "_" + name));
		subsection("subtitle", printIndex() + key + " " + name);

		final InvariantInfo info = new InvariantInfo(pd);

		final List<SubInvariantReport> subreport = result.getSubreports().get(name);
		if (subreport == null) {
			print("Not checked");
		} else {
			if (!info.source.getComment().empty()) {
				subsection("comment", sanitizeText(escapeHTML(info.source.getComment().content())));
			}

			if (info.hasAttribute("filter in")) {
				subsection("comment", "Filter in: " + sanitizeText(escapeHTML(info.getAttribute("filter in").toString())));
			}

			if (info.hasAttribute("filter out")) {
				subsection("comment", "Filter out: " + sanitizeText(escapeHTML(info.getAttribute("filter out").toString())));
			}

			sectionStack.push(current);
			current = new SectionId();

			for (final SubInvariantReport sr : subreport) {
				processErrorReport(info, sr);
			}
			current = sectionStack.pop();
		}

		sectionEnd();
	}

	private boolean hasInvariants(PredicateCategory pc) {
		if (containsInvariants(pc)) {
			return true;
		}

		for (final PredicateCategory pc2 : pc.getPredicateCategories()) {
			if (hasInvariants(pc2)) {
				return true;
			}
		}

		return false;
	}

	private boolean containsInvariants(PredicateCategory pc) {
		for (final PredicateDefinition pd : pc.getPredicates()) {
			if (pd.getKind().content() == PredicateKind.INVARIANT && result.getSubreports().containsKey(pd.getId().content())) {
				return true;
			}
		}
		return false;
	}

	private String printIndex() {
		if (indexEnabled) {
			return getSectionIndex() + ": ";
		} else {
			return "";
		}
	}

	private void buildIndex0() {
		current.next();
		sectionStart("index", "Index");

		sectionStack.push(current);
		current = new SectionId();

		for (final DataReference tdr : context.getTransitionReferences()) {
			final TransitionContainer tc = context.getTransitionContainer(tdr);
			final TransitionContainerInfo info = new TransitionContainerInfo(tc);
			info.compute();
			for (final String key : info.map.keySet()) {
				final TransitionContainerInvariantInfo ii = info.map.get(key);
				current.next();
				subsection("title", printIndex() + ii.name);

				listStart("1");
				listItem("Generated: " + ii.generated);
				listItem("Proved: " + ii.proved);
				listItem("Goals: " + ii.goals);
				listItem("Goals, closed: " + ii.goals_closed);
				listItem("Goals, stuck: " + ii.goals_stuck);
				listItem("Goals, abandoned: " + (ii.goals - (ii.goals_closed + ii.goals_stuck)));
				listItem("Time: " + (ii.time_correct + ii.time_failed));
				listItem("Time, closed goals: " + ii.time_correct);
				listItem("Time, failed goals: " + ii.time_failed);

				final List<SubInvariantReport> report = result.getSubreports().get(key);
				if (report != null) {
					listItem("Violations: " + report.size());
				}

				listItem("<a href=\"#" + sanitize(key) + "\">Link</a>");

				listEnd();
			}
			break;
		}

		current = sectionStack.pop();

		sectionEnd();
	}

	private void processErrorReport(InvariantInfo info, SubInvariantReport subreport) {
		final String location = TransitionUtil.ppTransitionKind(subreport.source);
//		if (!location.startsWith("route"))
//			return;

		sectionStart("report");

		current.next();
		subsection("reportname", printIndex() + location);

		Set<String> relevantIdentifiers = null;
		try {
			relevantIdentifiers = new HashSet<>();
			for (final String id : info.invariant.getFreeIdentifiers()) {
				relevantIdentifiers.add(CLUtils.primed(id));
			}
		} catch (final Throwable e1) {
			e1.printStackTrace();
		}

		if (OPTION_SHOW_CODE_FRAGMENT) {
			if (subreport.source instanceof TransitionCluster) {
				final TransitionCluster tc = (TransitionCluster) subreport.source;
				if (tc.getCodeFragment() != null) {
					print("<pre>");
					print(tc.getCodeFragment());
					print("</pre>");
				}
			}
		}

		boolean reportProduced = false;

		if (!subreport.issues.isEmpty()) {
			reportProduced = processExplanations(info, subreport);
		}

		if (!reportProduced) {

			subsection("comment", "No invariant report matches this case");

			Collection<SourceInformation> allSources = new HashSet<SourceInformation>();
			for (int i = 0; i < subreport.element.getPostconditions().size(); i++) {
				final LocatedString ls = subreport.element.getPostconditions().get(i);
				final CLExpression pexr = subreport.element.getParsed().getPostconditions().get(i);
				if (CLUtils.isRelevant(relevantIdentifiers, pexr)) {
					final SourceInformation srcinfo[] = subreport.tc.getSourceInformation(ls);
					for (final SourceInformation si : srcinfo) {
						if (!allSources.contains(si)) {
							emitElementSource(si, subreport);
							allSources.add(si);
						}
					}
				}
			}

			sectionStart("block");
			// print("Issues:");
			listStart("1");
			for (final SubInvariantReportIssue issue : subreport.issues) {
				printIssue(issue);
			}
			listEnd();
			sectionEnd();

			final IObjectResolver resolver = getObjectResolver(parser, diagramResource, model.getRootContext(), model, true);
			final ObjectHighlight highlights = new ObjectHighlight(resolver);
			final ObjectSet route = highlights.addSet("blue"); //
			final ObjectSet element = highlights.addSet("green");
			final ObjectSet issues = highlights.addSet("red");
			final ObjectSet aux0 = highlights.addSet("slategray");
			// ObjectSet aux1 = highlights.addSet("sandybrown");

			if (subreport.source.getKind().length >= 3 && getReportBaseElement(subreport) != null) {
				route.addPart(resolver, CLUtils.quote(getReportBaseElement(subreport)));
			}

			final String[] elements = subreport.element.getName().split("\\.");
			for (final String e : elements) {
				element.addPart(resolver, CLUtils.quote(e));
			}

			for (final SubInvariantReportIssue issue : subreport.issues) {
				for (final String el : issue.elements) {
					issues.addPart(resolver, CLUtils.quote(el));
				}
			}

			for (final SubInvariantReportIssue issue : subreport.issues) {
				addAuxElements(resolver, aux0, issue, "Sub overlaps locked");
				addAuxElements(resolver, aux0, issue, "Points commanded normal");
				addAuxElements(resolver, aux0, issue, "Points commanded reverse");
			}

			emitElementImage(highlights);

		}

		sectionStart("block");
		listStart("1");
		for (final SubInvariantReportIssue issue : subreport.issues) {
			formatProofState(issue);
		}
		listEnd();
		sectionEnd();

		print("<pre contenteditable=\"true\">put comments here</pre>");

		if (subreport.otherSources != null) {
			sectionStart("block");
			print("<details>");
			print("<summary>");
			print("Other locations with same error:");
			print("</summary>");
			listStart("1");
			final HashSet<String> otherSources = new HashSet<>();
			for (final SubSubInvariantReport loc : subreport.otherSources) {
				otherSources.add(TransitionUtil.ppTransitionKind(loc.source));
			}
			for (final String os : otherSources) {
				listItem(os);
			}
			listEnd();
			print("</details>");
			sectionEnd();
		}

		sectionEnd();
	}

	private String getReportBaseElement(SubInvariantReport subreport) {
		if (subreport.source.getKind().length < 3) {
			return null;
		}
		final String base = subreport.source.getKind()[2];
		if (base == null || base.length() < 2) {
			return null;
		}
		final String rname = normaliseRouteName(subreport.source.getKind()[2]);
		return rname;
	}

	private void addAuxElements(IObjectResolver resolver, ObjectSet aux0, SubInvariantReportIssue issue, String kind) {
		final Collection<String> overlapsFree = issue.getAuxElements(model, kind);
		if (overlapsFree != null) {
			for (final String e : overlapsFree) {
				aux0.addPart(resolver, e);
			}
		}
	}

	private String normaliseRouteName(String string) {
//		int lastClosingBracket = string.lastIndexOf(')');
//		if (lastClosingBracket > 0 && lastClosingBracket < string.length() - 1)
//			return string.substring(0, lastClosingBracket + 1);
		return string;
	}

	private boolean processExplanations(InvariantInfo info, SubInvariantReport subreport) {

		final SubInvariantReportIssue issue = subreport.issues.iterator().next();

		boolean success = false;
		for (final uk.ac.ncl.safecap.xdata.verification.InvariantReport report : info.source.getReports()) {
			try {

				final SDARuntimeExecutionContext newContext = new SDARuntimeExecutionContext(model);
				final TypingContext extra = new TypingContext(model.getRootContext());
				// final CLValuationContext state = newContext.getStateContext();

				final String reportBaseElement = getReportBaseElement(subreport);
				if (reportBaseElement != null) {
					final CLExpression el = new CLIdentifier(reportBaseElement);
					final CLType type = el.type(extra);
					if (type != null) {
						final Object valuation = el.getValue(newContext);
						if (valuation != null) {
							newContext.defineNew("reportbase", valuation);
							extra.addSymbol("reportbase", type, SYMBOL_CLASS.CONSTANT);
						}
					} else {
						newContext.defineNew("reportbase", reportBaseElement);
						extra.addSymbol("reportbase", CLTypeString.INSTANCE, SYMBOL_CLASS.CONSTANT);
					}
				} else {
					System.out.println("No reportbase");
				}

				if (extractPrimaryProofState(newContext, extra, issue.goal, report.getFormula().content())) {
					success = true;
					// find source
					final LocatedEntity source = findSource(newContext, info, report, subreport);
					// System.out.println("Source " + source);

					final SourceInformation srcinfo[] = subreport.tc.getSourceInformation(source);
					for (final SourceInformation si : srcinfo) {
						emitElementSource(si, subreport);
					}

					// build source tree based on subreport

					// emit description

					if (!report.getDescription().getFormula().empty()) {
						final String descriptionText = expand(parser, extra, sanitizeText(report.getDescription().getFormula().content()),
								newContext);
						subsection("description", descriptionText);
					}

					// emit diagram
					if (!report.getKeyElements().empty()) {
						final IObjectResolver resolver = getObjectResolver(parser, diagramResource, extra, newContext, true);
						final ObjectHighlight highlight = new ObjectHighlight(resolver, report.getKeyElements().content());
						emitElementImage(highlight);
					}

					if ("stop".equals(SDAUtils.getTagValue(report, "continuation")))
						break;
				}

			} catch (final Exception e) {
				System.err.println("Error in Description part of property report '" + report.getId().content() + "' of property '"
						+ ((INamed) report.parent().element()).getId().content() + "': " + e.getMessage());
			}
		}

		return success;

	}

	private static LocatedEntity findSource(SDARuntimeExecutionContext model, InvariantInfo info,
			uk.ac.ncl.safecap.xdata.verification.InvariantReport report, SubInvariantReport subreport) throws CLExecutionException {
		final String sourceKey = getAttribute(report, "source.key");
		if (sourceKey != null) {
			final Object value = model.getValue(sourceKey);
			if (value != null) {
				for (final LocatedString ls : subreport.element.getPostconditions()) {
					final LocatedElement le = ls.resolveKey(value.toString());
					if (le != null) {
						return le;
					}
				}
			}
		}

		final Collection<LocatedElement> locations = new ArrayList<>();
		for (final LocatedString ls : subreport.element.getPostconditions()) {
			if (ls.getParts() != null) {
				for (final LocatedElement le : ls.getParts()) {
					locations.add(le);
				}
			} else {
				locations.add(ls);
			}
		}

		final LocatedEntity result = findCommonAncestor(subreport, locations);

		return result;
	}

	private static LocatedEntity findCommonAncestor(SubInvariantReport subreport, Collection<LocatedElement> locations) {
		LocatedEntity enclosure = null;
		for (final LocatedElement le : locations) {
			if (enclosure == null) {
				enclosure = le.getEnclosure();
			} else if (!enclosure.equals(le.getEnclosure())) {
				enclosure = null;
				break;
			}
		}

		if (enclosure != null) {
			return enclosure;
		}

		final List<LocatedElement> list = new ArrayList<>(locations);
		assert list.size() > 1;

		outer: for (int i = list.get(0).getTrace().length - 1; i >= 0; i--) {
			final String candidate = list.get(0).getTrace()[i];
			for (int j = 1; j < list.size(); j++) {
				boolean contains = false;
				for (int k = 0; k < list.get(j).getTrace().length; k++) {
					if (list.get(j).getTrace()[k].equals(candidate)) {
						contains = true;
						break;
					}
				}
				if (!contains) {
					continue outer;
				}
			}

			return subreport.tc.resolveNamedLocation(candidate);
		}

		return null;
	}

	private static String getAttribute(uk.ac.ncl.safecap.xdata.verification.InvariantReport report, String name) {
		for (final ITag tag : report.getTags()) {
			if (tag.getName().content().equals(name)) {
				return tag.getValue().content();
			}
		}
		return null;
	}

	private void formatOtherSources(ITransitionPathSource loc) {
		listItem(TransitionUtil.ppTransitionKind(loc));
	}

	private void printIssue(SubInvariantReportIssue issue) {
		print("Prove that");

		super.listItem(formatIssue(issue));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String formatIssue(SubInvariantReportIssue issue) {
		final StringBuilder sb = new StringBuilder();
		sb.append(issue.kind);

		sb.append(": ");

		final CLGivenType type = new CLGivenType(issue.kind);

		final List<Object> set = new ArrayList<>();
		for (final String element : issue.elements) {
			set.add(new BProto(type, element));
		}

		sb.append(format(new BSet(set), Collections.EMPTY_MAP));

		return sb.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String formatIssueFull(SubInvariantReport subreport, SubInvariantReportIssue issue) {
		final StringBuilder sb = new StringBuilder();

		final String reportBaseElement = getReportBaseElement(subreport);

		sb.append(issue.kind);

		sb.append(" ");

		final CLGivenType type = new CLGivenType(issue.kind);

		final List<Object> set = new ArrayList<>();
		for (final String element : issue.elements) {
			set.add(new BProto(type, CLUtils.unquote(element)));
		}

		sb.append(format(new BSet(set), Collections.EMPTY_MAP));

		if (reportBaseElement != null) {
			sb.append(" in " + reportBaseElement + " ");
		}

		return sb.toString();
	}

	private void formatProofState(SubInvariantReportIssue issue) {
		try {
			if (issue.goal == null) {
				return;
			}

			print("Proof state");
			final ProofStateExplainer goalState = issue.getGoalState();
			sectionStart("block");
			listStart("A");
			for (final String key : goalState.state.keySet()) {
				formatProofStatePart(issue.goal, key, goalState.state.get(key));
			}
			listEnd();
			sectionEnd();
		} catch (final Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void formatProofStatePart(ProofGoal goal, String key, Collection<CLForeignLocationExpression> collection)
			throws CLExecutionException, InvalidSetOpException {

		final List<Object> set = new ArrayList<>();
		// ManagedProofObligation mpo = goal.getProofObligation();
		for (final CLForeignLocationExpression ee : collection) {
			CLExpression expr = ee.getExpression();
			try {
				Object value = expr.getValue(model);
				if (value != null) {
					CLForeignLocationValue locatedval = new CLForeignLocationValue(value, ee.getLocation());
					set.add(locatedval);
				}
			} catch (final Throwable e) {
				// e.printStackTrace();
			}
		}

		listItemStart();
		print(key + ": ");
		print(format(new BSet(set), Collections.singletonMap(GenericReport.PARAM_LOC, "yes")));

		listItemEnd();
	}

	private void emitElementSource(final SourceInformation srcinfo, SubInvariantReport subreport) {
		print("<pre>");
		formatSource(srcinfo, "#FFFF00", true);

		if (srcinfo.trace != null) {
			for (int i = srcinfo.trace.length - 1; i >= 0; i--) {
				final String pp = srcinfo.trace[i];
				print("<details>");
				print("<summary>");
				print("called from " + pp);
				print("</summary>");
				final LocatedEntity lb = subreport.tc.resolveNamedLocation(pp);
				if (lb != null) {
					final SourceInformation[] pfmsrcinfo = subreport.tc.getSourceInformation(lb);
					for (final SourceInformation si : pfmsrcinfo) {
						formatSource(si, "#8ef883", true);
					}
				}
				print("</details>");
			}
		}

		for (final LocatedString pre : subreport.element.getPreconditions()) {
			if ("PFM".equals(pre.getSource())) {
				print("<details>");
				print("<summary>");
				print("PFM import");
				print("</summary>");
				final SourceInformation[] pfmsrcinfo = subreport.tc.getSourceInformation(pre);
				for (final SourceInformation si : pfmsrcinfo) {
					formatSource(si, "#8ef883", true);
				}
				print("</details>");
			}
		}

		print("</pre>");
	}

	private static class TextSegment implements Comparable<TextSegment> {
		int offset;
		String text;
		String style;

		public TextSegment(int offset, String text) {
			super();
			this.offset = offset;
			this.text = text;
			style = null;
		}

		public TextSegment(int offset, String text, String style) {
			super();
			this.offset = offset;
			this.text = text;
			this.style = style;
		}

		@Override
		public String toString() {
			if (style != null) {
				return "<span style=\"" + style + "\">" + text + "</span>";
			} else {
				return text;
			}
		}

		@Override
		public int compareTo(TextSegment o) {
			return offset - o.offset;
		}
	}

//	private static class ProofState {
//		List<ProofStateExplainer> goalStates;
//
//		public ProofState(ManagedProofObligation mpo) {
//			goalStates = new ArrayList<ProofStateExplainer>();
//			for (ProofGoal pg : mpo.getGoals())
//				if (!pg.isClosed())
//					process(pg);
//		}
//
//		private void process(ProofGoal pg) {
//			goalStates.add(new ProofStateExplainer(pg));
//		}
//	}

	private static boolean extractPrimaryProofState(SDARuntimeExecutionContext model, TypingContext extra, ProofGoal goal, String text) {
		try {
			final IRPremise premise = ProveFragmentsUtils.buildIRPremise(model, text);
			if (premise == null) {
				System.err.println("Invalid matching pattern " + text);
				return false;
			}
			for (final CLExpression expr : premise.hypPremises) {
				final Map<String, CLExpression> map = goal.findAny(expr);
				if (map == null) {
					return false;
				}
				insertStateValue(model, extra, map);
			}

			if (premise.goalPremises != null) {
				final Map<String, CLExpression> map = goal.getFormula().match(premise.goalPremises, model.getRootContext());
				if (map == null) {
					return false;
				}
				insertStateValue(model, extra, map);
			}
		} catch (final Throwable e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static void insertStateValue(SDARuntimeExecutionContext model, TypingContext extra, Map<String, CLExpression> map)
			throws CLExecutionException, InvalidSetOpException {
		for (final String key : map.keySet()) {
			final CLExpression value = map.get(key);
			final CLType type = value.type(extra);
			if (type != null) {
				final Object valuation = value.getValue(model);
				if (valuation != null) {
					model.defineNew(key, valuation);
					extra.addSymbol(key, type, SYMBOL_CLASS.CONSTANT);
				}
			}
		}

	}

	private void formatSource(final SourceInformation srcinfo, String colour, boolean simplified) {
		if (srcinfo.text == null) {
			return;
		}

		final StringBuilder sb = new StringBuilder();
		if (!simplified) {
			final List<TextSegment> segments = new ArrayList<>(srcinfo.highlightStart.length);

			int prev = 0;
			int next;
			for (int i = 0; i < srcinfo.highlightStart.length; i++) {
				next = srcinfo.highlightStart[i];
				segments.add(new TextSegment(prev, srcinfo.text.substring(prev, next)));
				prev = next;
				next = srcinfo.highlightEnd[i];
				segments.add(new TextSegment(prev, srcinfo.text.substring(prev, next), "background: yellow;"));
				prev = next;
			}
			segments.add(new TextSegment(prev, srcinfo.text.substring(prev)));

			Collections.sort(segments);

			for (final TextSegment ts : segments) {
				sb.append(ts);
			}
		} else {
			sb.append(srcinfo.text);
		}

		if (!simplified) {

			if (srcinfo.fileName != null) {
				print(srcinfo.fileName);
			}
			if (srcinfo.filePath != null) {
				final File file = new File(srcinfo.filePath);
				if (file.exists()) {
					try {
						print("/" + MD5Checksum.getMD5Checksum(srcinfo.filePath));
						print("/[" + sdf.format(file.lastModified()) + "]");
						if (srcinfo.trace != null) {
							print("/" + Arrays.toString(srcinfo.trace));
						}
					} catch (final Throwable e) {

					}
				}
			}

			print("\n");
		}

		int lineno = srcinfo.getStartLine();
		boolean first = true;
		final String lines[] = sb.toString().split("\\r?\\n");
		for (final String line : lines) {
			if (line.trim().length() == 0 && first) {
				continue;
			}
			if (lineno != -1) {
				print("" + lineno + ": ");
				lineno++;
			}
			print(line);
			print("\n");
			first = false;
		}
	}

	private void emitElementImage(final ObjectHighlight highlight) {
		if (imageGenerator != null && display != null && highlight != null) {

			display.syncExec(new Runnable() {
				@Override
				public void run() {
					final StringBuilder sb = new StringBuilder();
					final boolean success = imageGenerator.getInlineImage(sb, highlight);
					if (success) {
						subsection("elementimage", sb.toString());
					}
				}
			});

		}
	}

	static Set<SubInvariantReportIssue> getIssues(ManagedProofObligation tpo) {
		final Set<SubInvariantReportIssue> problems = new HashSet<>();

		// look for a stuck goal, ignore non stuck ones
		for (final ProofGoal goal : tpo.getGoals()) {
			if (goal.isStuck()) {
				addIssue(goal, problems, analyzeGoal(goal));
				return problems;
			}
		}

		// no stuck goal yet not closed - should not happen normally
		for (final ProofGoal goal : tpo.getGoals()) {
			if (!goal.isClosed()) {
				addIssue(goal, problems, analyzeGoal(goal));
				return problems;
			}
		}

		return problems;
	}

	private static void addIssue(ProofGoal goal, Set<SubInvariantReportIssue> problems, RawIssue rissue) {
		for (final SubInvariantReportIssue issue : problems) {
			if (issue.kind.equals(rissue.kind)) {
				issue.addElement(rissue.element);
				return;
			}
		}

		final SubInvariantReportIssue newIssue = new SubInvariantReportIssue(goal, rissue.kind);
		newIssue.addElement(rissue.element);
		problems.add(newIssue);
	}

	private static RawIssue analyzeGoal(ProofGoal goal) {
		if (!goal.isStuck()) {
			return new RawIssue("Unfinished proof branch", "unknown");
		}

		final ProofStateExplainer state = new ProofStateExplainer(goal, false);
		if (!state.state.isEmpty()) {
			final String key = state.state.keySet().iterator().next();
			final Collection<CLForeignLocationExpression> element = state.state.get(key);
			if (element.size() == 0) {
				return new RawIssue(key, "unknown");
			} else if (element.size() == 1) {
				return new RawIssue(key, element.iterator().next().getExpression().asString());
			} else {
				return new RawIssue(key, element.toString());
			}
		}

		/*
		 * CLExpression formula = goal.getFormula(); if (formula.getTag() ==
		 * alphabet.OP_IN || formula.getTag() == alphabet.OP_NOTIN) {
		 * 
		 * boolean positive = formula.getTag() == alphabet.OP_IN;
		 * 
		 * CLBinaryExpression binary = (CLBinaryExpression) formula; if
		 * (CLUtils.isEnumLiteral(binary.getLeft(), goal.getTypingContext()) &&
		 * binary.getRight().getTag() == alphabet.ID) { String function =
		 * ((CLIdentifier) binary.getRight()).getName(); String element =
		 * ((CLIdentifier) binary.getLeft()).getName();
		 * 
		 * if (function.equals("subroute_l")) { return new RawIssue("Sub routes " +
		 * (positive ? "locked" : "unlocked"), element); } else if
		 * (function.equals("track_o")) { return new RawIssue("Sections " + (positive ?
		 * "occupied" : "free"), element); } else if (function.equals("route_a")) {
		 * return new RawIssue("Routes " + (positive ? "available" : "not available"),
		 * element); } else if (function.equals("overlap_l")) { return new
		 * RawIssue("Sub overlaps " + (positive ? "locked" : "unlocked"), element); }
		 * else if (function.equals("route_s")) { return new RawIssue("Routes " +
		 * (positive ? "set" : "not set"), element); } } }
		 */
		return new RawIssue(goal.getFormula().asString(), "unknown");
	}

//	private InvariantInfo getInvariant(String name) {
//		for (final InvariantInfo inv : allInvariants) {
//			if (inv.name.equals(name)) {
//				return inv;
//			}
//		}
//
//		return null;
//	}

	@Override
	protected boolean isValidElement(Object element) {
		try {
			if (element instanceof BProto) {
				final BProto proto = (BProto) element;
				final String typeName = proto.getType().getName();
				final String functionName = typeName + ".derived";
				final Object value = model.getValue(functionName);
				if (value instanceof BRel) {
					final BRel f = (BRel) value;
					return f.dom().contains(proto);
				} else {
					return true;
				}
			}
		} catch (CLExecutionException | InvalidSetOpException e) {
			e.printStackTrace();
		}
		return true;
	}

	static class RawIssue {
		String kind;
		String element;

		public RawIssue(String kind, String element) {
			super();
			this.kind = kind;
			this.element = element;
		}

		@Override
		public String toString() {
			return "RawIssue [kind=" + kind + ", element=" + element + "]";
		}

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
			sb.append(sectionStack.get(i).getIndex());
			sb.append('.');
		}

		return sb.toString() + current.getIndex();
	}
}
