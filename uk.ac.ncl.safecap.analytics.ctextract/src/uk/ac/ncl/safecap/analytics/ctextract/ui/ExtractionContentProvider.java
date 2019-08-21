package uk.ac.ncl.safecap.analytics.ctextract.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPartBase;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTProductionRule;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTProject;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTProjectPart;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTRewriteRule;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTRule;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTRuleContainer;
import uk.ac.ncl.safecap.analytics.ctextract.main.PatternInfo;
import uk.ac.ncl.safecap.analytics.ctextract.main.PatternSampleInfo;
import uk.ac.ncl.safecap.analytics.ctextract.parser.CTBuilder;
import uk.ac.ncl.safecap.cteparser.TEContext;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ColumnModel;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.TableModel;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;

public class ExtractionContentProvider implements ITreeContentProvider {
	private final CTProject root;

	public ExtractionContentProvider(CTProject root) {
		this.root = root;
	}

	@Override
	public Object[] getElements(Object element) {
		return getChildren(element);
	}

	@Override
	public Object[] getChildren(Object element) {
		final List<Object> list = new ArrayList<>();
		if (element instanceof CTProject) {
			final CTProject prj = (CTProject) element;
			if (prj.getResourceName() != null) {
				final File file = new File(prj.getResourceName());
				list.add(new DataSource(file.getName()));
			}
			if (prj.getModel() == null || prj.getData() == null) {
				list.add("The extractor model is broken");
				if (prj.getModel() == null) {
					list.add("Cannot resolve concept model "
							+ (prj.getTablesModelName() == null ? "(none specified)" : prj.getTablesModelName()));
				}
				if (prj.getData() == null) {
					list.add("Cannot resolve data model " + (prj.getResourceName() == null ? "(none specified)" : prj.getResourceName()));
				}
			} else {
				list.addAll(prj.getModel().getTables());
				list.add(new RewriteRules(prj));
			}
		} else if (element instanceof TableModel) {
			final TableModel tm = (TableModel) element;
			list.addAll(tm.getParts());
		} else if (element instanceof ColumnModel) {
			final ColumnModel cm = (ColumnModel) element;
			for (final String path : cm.getConceptPaths()) {
				list.add(root.fetchPart(path));
			}
		} else if (element instanceof CTProjectPart) {
			final CTProjectPart pp = (CTProjectPart) element;
			list.addAll(pp.getPatterns());
			list.add(new Bins(pp));
			list.add(new RewriteRules(pp));
			list.add(new ProductionRules(pp));
			list.add(new Trace(pp));
		} else if (element instanceof Trace) {
			final Trace trace = (Trace) element;
			final IXFunction f = trace.part.getSourceDataFunction();
			if (f instanceof XFunctionBasic) {
				final XFunctionBasic ff = (XFunctionBasic) f;
				for (final Object d : ff.domain()) {
					list.add(new TraceCell(trace.part, d));
				}
			}
		} else if (element instanceof TraceCell) {
			final TraceCell cell = (TraceCell) element;
			for (final Object z : cell.part.getSourceDataFunction().get(cell.domain)) {
				final String raw = z.toString();
				final CTEPartBase pattern = CTBuilder.build(raw);
				if (pattern != null) {
					list.add(new TraceParsedCell(cell.part, pattern));
				}
			}
		} else if (element instanceof TraceParsedCell) {
			final TraceParsedCell cell = (TraceParsedCell) element;
			for (final CTRewriteRule rr : cell.part.getRewriteRules()) {
				final CTEPartBase result = cell.part.rewrite_rwe(cell.parsed, rr);
				if (!result.equals(cell.parsed)) {
					list.add(new TraceRule(cell.part, cell.parsed, rr, result));
				}
			}
			for (final CTRewriteRule rr : cell.part.getParent().getRewriteRules()) {
				final CTEPartBase result = cell.part.rewrite_rwe(cell.parsed, rr);
				if (!result.equals(cell.parsed)) {
					list.add(new TraceRule(cell.part, cell.parsed, rr, result));
				}
			}
			for (final CTProductionRule rr : cell.part.getProductionRules()) {
				final CTEPartBase result = cell.part.rewrite_pro(cell.parsed, rr);
				if (!result.equals(cell.parsed)) {
					list.add(new TraceRule(cell.part, cell.parsed, rr, result));
				}
			}
		} else if (element instanceof TraceRule) {
			final TraceRule trule = (TraceRule) element;
			list.add(new TraceParsedCell(trule.part, trule.result));
		} else if (element instanceof RewriteRules) {
			final RewriteRules tm = (RewriteRules) element;
			list.addAll(tm.container.getRewriteRules());
		} else if (element instanceof ProductionRules) {
			final ProductionRules tm = (ProductionRules) element;
			list.addAll(tm.container.getProductionRules());
		} else if (element instanceof CTRule) {
			final CTRule rule = (CTRule) element;
			list.add(new RulePattern(rule));
			list.add(new RuleTemplate(rule));
			if (rule.isEnabled() && rule instanceof CTProductionRule) {
				list.add(new ProductionResult((CTProductionRule) rule));
			}
			if (rule.getContext() != null) {
				if (!rule.getContext().getErrors().isEmpty()) {
					list.add(new ContextErrors(rule.getContext()));
				}
				if (!rule.getContext().getWarnings().isEmpty()) {
					list.add(new ContextWarnings(rule.getContext()));
				}
			}
		} else if (element instanceof ContextErrors) {
			final ContextErrors ctx = (ContextErrors) element;
			list.addAll(ctx.context.getErrors());
		} else if (element instanceof ContextWarnings) {
			final ContextWarnings ctx = (ContextWarnings) element;
			list.addAll(ctx.context.getWarnings());
		} else if (element instanceof Bins) {
			final Bins bins = (Bins) element;
			if (bins.container.getBins() != null) {
				list.addAll(bins.container.getBins());
			}
		} else if (element instanceof ProductionResult) {
			final ProductionResult cm = (ProductionResult) element;
			final Map<String, Map<String, List<String>>> result = cm.rule.evaluatePattern();
			for (final String key : result.keySet()) {
				list.add(new RuleSamplesEntry(key, result));
			}
		} else if (element instanceof RuleSamplesEntry) {
			final RuleSamplesEntry cm = (RuleSamplesEntry) element;
			for (final String dom : cm.result.get(cm.key).keySet()) {
				list.add(new RuleSamplesSubEntry(dom, cm.result.get(cm.key)));
			}
		} else if (element instanceof PatternInfo) {
			final PatternInfo cm = (PatternInfo) element;
			for (final PatternSampleInfo si : cm.samples) {
				list.add(new PatternSamplePart(si));
			}
		} else if (element instanceof PatternSamplePart) {
			final PatternSamplePart cm = (PatternSamplePart) element;
			list.add(new PatternSampleText(cm.sample));
		}
		return list.toArray();
	}

	static class DataSource {
		String name;

		public DataSource(String name) {
			super();
			this.name = name;
		}
	}

	static class Trace {
		CTProjectPart part;

		public Trace(CTProjectPart part) {
			this.part = part;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (part == null ? 0 : part.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final Trace other = (Trace) obj;
			if (part == null) {
				if (other.part != null) {
					return false;
				}
			} else if (!part.equals(other.part)) {
				return false;
			}
			return true;
		}

	}

	static class TraceCell {
		CTProjectPart part;
		Object domain;

		public TraceCell(CTProjectPart part, Object domain) {
			this.part = part;
			this.domain = domain;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (domain == null ? 0 : domain.hashCode());
			result = prime * result + (part == null ? 0 : part.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final TraceCell other = (TraceCell) obj;
			if (domain == null) {
				if (other.domain != null) {
					return false;
				}
			} else if (!domain.equals(other.domain)) {
				return false;
			}
			if (part == null) {
				if (other.part != null) {
					return false;
				}
			} else if (!part.equals(other.part)) {
				return false;
			}
			return true;
		}

	}

	static class TraceParsedCell {
		CTProjectPart part;
		CTEPartBase parsed;

		public TraceParsedCell(CTProjectPart part, CTEPartBase parsed) {
			this.part = part;
			this.parsed = parsed;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (parsed == null ? 0 : parsed.hashCode());
			result = prime * result + (part == null ? 0 : part.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final TraceParsedCell other = (TraceParsedCell) obj;
			if (parsed == null) {
				if (other.parsed != null) {
					return false;
				}
			} else if (!parsed.equals(other.parsed)) {
				return false;
			}
			if (part == null) {
				if (other.part != null) {
					return false;
				}
			} else if (!part.equals(other.part)) {
				return false;
			}
			return true;
		}

	}

	static class TraceRule {
		CTProjectPart part;
		CTEPartBase parsed;
		CTRule rule;
		CTEPartBase result;

		public TraceRule(CTProjectPart part, CTEPartBase parsed, CTRule rule, CTEPartBase result) {
			this.part = part;
			this.parsed = parsed;
			this.rule = rule;
			this.result = result;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (parsed == null ? 0 : parsed.hashCode());
			result = prime * result + (part == null ? 0 : part.hashCode());
			result = prime * result + (this.result == null ? 0 : this.result.hashCode());
			result = prime * result + (rule == null ? 0 : rule.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final TraceRule other = (TraceRule) obj;
			if (parsed == null) {
				if (other.parsed != null) {
					return false;
				}
			} else if (!parsed.equals(other.parsed)) {
				return false;
			}
			if (part == null) {
				if (other.part != null) {
					return false;
				}
			} else if (!part.equals(other.part)) {
				return false;
			}
			if (result == null) {
				if (other.result != null) {
					return false;
				}
			} else if (!result.equals(other.result)) {
				return false;
			}
			if (rule == null) {
				if (other.rule != null) {
					return false;
				}
			} else if (!rule.equals(other.rule)) {
				return false;
			}
			return true;
		}

	}

	static class RuleSamplesSubEntry {
		String domain;
		Map<String, List<String>> map;

		public RuleSamplesSubEntry(String domain, Map<String, List<String>> map) {
			this.domain = domain;
			this.map = map;
		}
	}

	static class ProductionResult {
		CTProductionRule rule;

		public ProductionResult(CTProductionRule rule) {
			super();
			this.rule = rule;
		}
	}

	static class ContextErrors {
		TEContext context;

		public ContextErrors(TEContext context) {
			this.context = context;
		}
	}

	static class ContextWarnings {
		TEContext context;

		public ContextWarnings(TEContext context) {
			this.context = context;
		}
	}

	static class PatternSamplePart {
		PatternSampleInfo sample;

		public PatternSamplePart(PatternSampleInfo sample) {
			this.sample = sample;
		}
	}

	static class PatternSampleText {
		PatternSampleInfo sample;

		public PatternSampleText(PatternSampleInfo sample) {
			this.sample = sample;
		}
	}

	static class RuleSamplesEntry {
		String key;
		Map<String, Map<String, List<String>>> result;

		public RuleSamplesEntry(String key, Map<String, Map<String, List<String>>> result) {
			this.key = key;
			this.result = result;
		}
	}

	static class Bins {
		CTProjectPart container;

		public Bins(CTProjectPart container) {
			this.container = container;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (container == null ? 0 : container.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final Bins other = (Bins) obj;
			if (container == null) {
				if (other.container != null) {
					return false;
				}
			} else if (!container.equals(other.container)) {
				return false;
			}
			return true;
		}
	}

	static class RuleContainer {
		CTRuleContainer container;

		public RuleContainer(CTRuleContainer container) {
			this.container = container;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (container == null ? 0 : container.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final RuleContainer other = (RuleContainer) obj;
			if (container == null) {
				if (other.container != null) {
					return false;
				}
			} else if (!container.equals(other.container)) {
				return false;
			}
			return true;
		}
	}

	static class RewriteRules extends RuleContainer {

		public RewriteRules(CTRuleContainer container) {
			super(container);
		}
	}

	static class ProductionRules extends RuleContainer {
		public ProductionRules(CTRuleContainer container) {
			super(container);
		}
	}

	static class RulePattern {
		CTRule rule;

		public RulePattern(CTRule rule) {
			this.rule = rule;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (rule == null ? 0 : rule.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final RulePattern other = (RulePattern) obj;
			if (rule == null) {
				if (other.rule != null) {
					return false;
				}
			} else if (!rule.equals(other.rule)) {
				return false;
			}
			return true;
		}

	}

	static class RuleTemplate {
		CTRule rule;

		public RuleTemplate(CTRule rule) {
			this.rule = rule;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (rule == null ? 0 : rule.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final RuleTemplate other = (RuleTemplate) obj;
			if (rule == null) {
				if (other.rule != null) {
					return false;
				}
			} else if (!rule.equals(other.rule)) {
				return false;
			}
			return true;
		}

	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof CTProject) {
			return true;
		} else if (element instanceof TableModel) {
			return true;
		} else if (element instanceof ColumnModel) {
			return true;
		} else if (element instanceof PatternInfo) {
			return true;
		} else if (element instanceof CTProjectPart) {
			return true;
		} else if (element instanceof RewriteRules) {
			final RewriteRules cr = (RewriteRules) element;
			return !cr.container.getRewriteRules().isEmpty();
		} else if (element instanceof ProductionRules) {
			final ProductionRules cr = (ProductionRules) element;
			return !cr.container.getProductionRules().isEmpty();
		} else if (element instanceof CTRule) {
			return true;
		} else if (element instanceof Bins) {
			final Bins br = (Bins) element;
			return br.container.getBins() != null && br.container.getBins().size() > 0;
		} else if (element instanceof RuleSamplesEntry) {
			return true;
		} else if (element instanceof PatternSamplePart) {
			return true;
		} else if (element instanceof ContextErrors) {
			return true;
		} else if (element instanceof ContextWarnings) {
			return true;
		} else if (element instanceof ProductionResult) {
			return true;
		} else if (element instanceof Trace) {
			return true;
		} else if (element instanceof TraceCell) {
			return true;
		} else if (element instanceof TraceParsedCell) {
			final TraceParsedCell trace = (TraceParsedCell) element;
			return !trace.parsed.isEmpty();
		} else if (element instanceof TraceRule) {
			return true;
		}
		return false;
	}
}
