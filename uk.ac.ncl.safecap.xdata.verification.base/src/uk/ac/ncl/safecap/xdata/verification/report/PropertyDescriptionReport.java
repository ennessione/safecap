package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.File;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Display;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.mb.NativeModelSolverExistential;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeAny;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.prime.sim.parser.CLFormulaParser;
import uk.ac.ncl.prime.sim.parser.CLValuationContext;
import uk.ac.ncl.prime.sim.parser.FormulaSource;
import uk.ac.ncl.safecap.common.IObjectResolver;
import uk.ac.ncl.safecap.common.report.SFBold;
import uk.ac.ncl.safecap.common.report.SFPlain;
import uk.ac.ncl.safecap.common.report.SMError;
import uk.ac.ncl.safecap.common.report.SRBlock;
import uk.ac.ncl.safecap.common.report.SRCode;
import uk.ac.ncl.safecap.common.report.SRComment;
import uk.ac.ncl.safecap.common.report.SRContent;
import uk.ac.ncl.safecap.common.report.SRDocument;
import uk.ac.ncl.safecap.common.report.SRFigure;
import uk.ac.ncl.safecap.common.report.SRList;
import uk.ac.ncl.safecap.common.report.SRPart;
import uk.ac.ncl.safecap.common.report.SRSection;
import uk.ac.ncl.safecap.ldl.image.ImageGenerator;
import uk.ac.ncl.safecap.ldl.image.ObjectHighlight;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.InjectorException;
import uk.ac.ncl.safecap.xdata.provers.VerificationTool;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.ConjectureCategory;
import uk.ac.ncl.safecap.xdata.verification.ICommented;
import uk.ac.ncl.safecap.xdata.verification.INamed;
import uk.ac.ncl.safecap.xdata.verification.IParseable;
import uk.ac.ncl.safecap.xdata.verification.ISemiFormal;
import uk.ac.ncl.safecap.xdata.verification.PredicateCategory;
import uk.ac.ncl.safecap.xdata.verification.PredicateDefinition;
import uk.ac.ncl.safecap.xdata.verification.PropertyReport;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.NullVerificationProgressMonitor;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.core.SemiFormalState;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.ConjectureModelExperiment;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.EIResult;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.IErrorInjector;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.NullInjector;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.SinglePointInjector;

public class PropertyDescriptionReport extends GenericReport {
	private static final double[] WEIGHTS = new double[] { 1.0, 0.2, 0.2, 0.2, 0.2 };
	private static final int RUNS = 50;
	private final RootCatalog catalog;
	private final SDAContext dataContext;
	private ImageGenerator imageGenerator;
	private final Resource diagramResource;
	private final Display display;
	private final CLFormulaParser parser;
	private final SDARuntimeExecutionContext model;

	public PropertyDescriptionReport(Display display, Resource diagramResource, RootCatalog catalog) {
		super(SDAUtils.getDataContext(catalog));
		this.catalog = catalog;
		this.diagramResource = diagramResource;
		this.display = display;
		dataContext = SDAUtils.getDataContext(catalog);
		parser = VerificationTool.getParser(catalog);
		model = dataContext.getRootRuntimeContext();
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

	public void build(final IReportBuilder builder, final File file) throws Exception {
		initializeImageGenerator(file);
		final SRDocument document = new SRDocument("Property report");

		for (final ConjectureCategory category : catalog.getConjectureCategories()) {
			document.add(processConjectureCategory(category));
		}

		for (final PredicateCategory category : catalog.getPredicateCategories()) {
			document.add(processPredicateCategory(category));
		}

		builder.buildReport(document, file, true);
	}

	private SRSection processConjectureCategory(ConjectureCategory category) {
		final SRSection section = new SRSection(category.getId().content());
		for (final ConjectureCategory subcategory : category.getConjectureCategories()) {
			section.add(processConjectureCategory(subcategory));
		}

		for (final Conjecture conjecture : category.getConjectures()) {
			section.add(processConjecture(conjecture));
		}

		return section;
	}

	private SRSection processPredicateCategory(PredicateCategory category) {
		final SRSection section = new SRSection(category.getId().content());
		for (final PredicateCategory subcategory : category.getPredicateCategories()) {
			section.add(processPredicateCategory(subcategory));
		}

		for (final PredicateDefinition predicate : category.getPredicates()) {
			section.add(processPredicate(predicate));
		}

		return section;
	}

	private SRSection processConjecture(Conjecture conjecture) {
		final SRSection section = new SRSection(conjecture.getId().content());

		section

				.add(processConjectureComment(conjecture)).add(processConjectureSemiFormal(conjecture))
				.add(processConjecturePredicate(conjecture)) // .add(processConjectureAnalysis(conjecture))

		;

		return section;
	}

	private SRSection processPredicate(PredicateDefinition predicate) {
		final SRSection section = new SRSection(predicate.getId().content());

		section.add(processConjectureComment(predicate)).add(processConjectureSemiFormal(predicate))
				.add(processConjecturePredicate(predicate))

		;

		return section;
	}

	private SRPart processConjectureAnalysis(Conjecture conjecture) {
		if (conjecture.getParsed().empty()) {
			return null;
		}

		if (conjecture.isValid().empty() || !conjecture.isValid().content()) {
			return null;
		}

		try {
			final ConjectureModelExperiment experiment = new ConjectureModelExperiment(catalog, conjecture, WEIGHTS, RUNS);
			experiment.setFilterBaseInvalid(true);
			if (experiment.getConjectures().size() != 0) {
				final EIResult result = experiment.execute(NullVerificationProgressMonitor.INSTANCE);
				if (result.getError() != null) {
					return new SRBlock().add(new SMError(result.getError()));
				}
				if (result.getRecords() == null || result.getRecords().isEmpty()) {
					return new SRBlock().add(new SMError("Error injection returned no records"));
				}

				final double total = result.getRecords().size();
				final double valid = result.getCumulativeValid().values().iterator().next();

				final SRSection section = new SRSection("Analysis");
				section.add(SRPair("Ratio", "" + valid / total));
//				XDataChart<String> h = ChartUtils.buildKindChartSingletonProperty(result, WEIGHTS);
//				if (h != null) {
//					section.add(new SRFigure("Valid by conjecture/injector").add(new BarChartSVGRenderer(h).render()));
//				}

				if (!result.getCorrect().isEmpty()) {
					final SRSection subsection = new SRSection("Data changes not resulting in error");
					for (final IErrorInjector inj : result.getCorrect()) {
						try {
							final SinglePointInjector injector = new SinglePointInjector(inj);
							model.setErrorInjector(injector);
							subsection.add(new SRCode(inj.toString()));
							subsection.add(handlePropertyReports(conjecture));
						} finally {
							model.setErrorInjector(NullInjector.INSTANCE);
						}
					}
					section.add(subsection);
				}

				if (!result.getFailed().isEmpty()) {
					final SRSection subsection = new SRSection("Data changes resulting in error");
					for (final IErrorInjector inj : result.getFailed()) {
						try {
							final SinglePointInjector injector = new SinglePointInjector(inj);
							model.setErrorInjector(injector);
							subsection.add(new SRCode(inj.toString()));
							subsection.add(handlePropertyReports(conjecture));
						} finally {
							model.setErrorInjector(NullInjector.INSTANCE);
						}
					}
					section.add(subsection);

				}

				return section;
			} else {
				return new SRBlock().add(new SMError("Error injection returned no records"));
			}
		} catch (final InjectorException e) {
			e.printStackTrace();
		}
		return null;
	}

	private SRContent processConjectureComment(ICommented conjecture) {
		if (conjecture.getComment().empty()) {
			return null;
		} else {
			return new SRComment(conjecture.getComment().content());
		}
	}

	private SRPart processConjectureSemiFormal(ISemiFormal source) {
		if (source.getSemiFormal().empty()) {
			return null;
		} else {
			final SemiFormalState state = new SemiFormalState(dataContext, source.getSemiFormal().content());
			if (!state.getErrors().isEmpty()) {
				return new SRList(state.getErrors());
			}

			final SRBlock semiFormal = new SRBlock();
			semiFormal.add(new SFBold("for"));
			semiFormal.add(new SFPlain(state.getPredicatet()));
			semiFormal.add(new SFBold("it holds that"));
			semiFormal.add(new SFPlain(state.getStatement()));
			semiFormal.add(new SFPlain("\n"));
			if (state.getProperty("author") != null) {
				semiFormal.add(new SFBold("author: ")).add(new SFPlain(state.getProperty("author"))).add("\n");
			}
			if (state.getProperty("date") != null) {
				semiFormal.add(new SFBold("date: ")).add(new SFPlain(state.getProperty("date"))).add("\n");
			}
			if (state.getProperty("date") != null) {
				semiFormal.add(new SFBold("confidence: ")).add(new SFPlain(state.getProperty("confidence"))).add("\n");
			}
			if (state.getProperty("testing") != null) {
				semiFormal.add(new SFBold("testing: ")).add(new SFPlain(state.getProperty("testing"))).add("\n");
			}

			final SRSection section = (SRSection) new SRSection("Semi formal", false).add(semiFormal);

			if (state.getMissingData() != null && !state.getMissingData().isEmpty()) {
				section.add(new SRSection("Missing data", false).add(new SRList(state.getMissingData())).add(new SFPlain("\n")));
			}

			return section;
		}
	}

	private SRContent processConjecturePredicate(IParseable conjecture) {
		if (conjecture.getFormula().empty()) {
			return new SRBlock().add(new SMError("no formal definition"));
		} else {
			return new SRCode(conjecture.getFormula().content());
		}
	}

	private SRSection handlePropertyReports(Conjecture property) {
		final SRSection section = new SRSection();
		for (final PropertyReport report : property.getReports()) {
			section.add(handlePropertyReport(report));
		}

		if (section.getChildren().isEmpty()) {
			return null;
		} else if (section.getChildren().size() == 1) {
			return (SRSection) section.getChildren().get(0);
		} else {
			return section;
		}
	}

	private SRSection handlePropertyReport(final PropertyReport pd) {
		try {
			final SDARuntimeExecutionContext runtime_context = parser.getModel();
			final TypingContext typing = runtime_context.getRootContext();

			pd.getParsed().clear();
			final FormulaSource source = SDAUtils.getSource(pd);
			if (source == null) {
				return null;
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
				pd.setParsed(parsed);

				if (!source.hasErrors() && parsed.getType() != null) {
					final NativeModelSolverExistential solver = new NativeModelSolverExistential(extra, parsed, extra.getNewSymbols());
					solver.setFindNumber(1);
					solver.solve();
					if (solver.isSolved()) {
						return emitReport(extra, pd, solver.getSolutions());
					} else {
						return null;
					}
				} else {
					return null;
				}
			} else {
				pd.getParsed().clear();
			}
		} catch (final Throwable e) {
			e.printStackTrace();
			System.err.println("Error in property report '" + pd.getId().content() + "' of property '"
					+ ((INamed) pd.parent().element()).getId().content() + "': " + e.getMessage());
		}

		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private SRSection emitReport(final TypingContext extra, PropertyReport pd, Map<String, Object> solutions) throws Exception {
		final SDARuntimeExecutionContext newContext = new SDARuntimeExecutionContext(model);
		final CLValuationContext state = newContext.getStateContext();

		String locationText = null;
		String descriptionText = null;

		for (final String var : solutions.keySet()) {
			state.defineNew(var, solutions.get(var));
		}

		final SRSection result = new SRSection(pd.getId().content(), false);

		try {
			if (!pd.getLocation().getFormula().empty()) {
				locationText = expand(parser, extra, pd.getLocation().getFormula().content(), newContext);
				result.add(new SFPlain("xx"));
				result.add(new SFPlain(locationText).set(SRPart.NOESCAPE, true));
			}
		} catch (final Exception e) {
			System.err.println("Error in Location part of property report '" + pd.getId().content() + "' of property '"
					+ ((INamed) pd.parent().element()).getId().content() + "': " + e.getMessage());
		}

		try {
			if (!pd.getDescription().getFormula().empty()) {
				descriptionText = expand(parser, extra, pd.getDescription().getFormula().content(), newContext);
				result.add(new SRBlock(descriptionText).set(SRPart.NOESCAPE, true));
			}
		} catch (final Exception e) {
			System.err.println("Error in Description part of property report '" + pd.getId().content() + "' of property '"
					+ ((INamed) pd.parent().element()).getId().content() + "': " + e.getMessage());
		}

		if (!pd.getKeyElements().empty()) {
			final IObjectResolver resolver = getObjectResolver(parser, diagramResource, extra, newContext, true);
			final ObjectHighlight highlights = new ObjectHighlight(resolver, pd.getKeyElements().content());
			final SRFigure figure = new SRFigure("Illustration");
			emitElementImage(figure, highlights);
			if (!figure.getChildren().isEmpty()) {
				result.add(figure);
			}
		}

		return result;
	}

	private void emitElementImage(final SRFigure figure, final ObjectHighlight highlight) {
		if (imageGenerator != null && display != null && highlight != null) {

			display.syncExec(new Runnable() {
				@Override
				public void run() {
					final StringBuilder sb = new StringBuilder();
					final boolean success = imageGenerator.getInlineImage(sb, highlight);
					if (success) {
						figure.add(sb.toString());
					}
				}
			});

		}
	}

	private SRPart SRPair(String name, String value) {
		return new SRBlock().add(new SFBold(name)).add(new SFPlain("  ")).add(new SFPlain(value)).add(new SFPlain("\n"))
				.set(HtmlRenderer.CLASS, "pair");
	}
}
