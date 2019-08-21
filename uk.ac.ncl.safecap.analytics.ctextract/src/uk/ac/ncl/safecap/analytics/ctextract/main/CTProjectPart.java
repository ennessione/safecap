package uk.ac.ncl.safecap.analytics.ctextract.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.bind.annotation.XmlTransient;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPartBase;
import uk.ac.ncl.safecap.analytics.ctextract.core.CTEToken;
import uk.ac.ncl.safecap.analytics.ctextract.core.CTETokenList;
import uk.ac.ncl.safecap.analytics.ctextract.parser.CTBuilder;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ColumnModel;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;

public class CTProjectPart extends CTRuleContainer {
	private static final int REWRITE_DEPTH = 5;
	private transient CTProject parent;
	private String path;
	private List<CTBin> bins;

	private transient ICTRuleContext<CTRewriteRule> rewriteContext;
	private transient IXFunction sourceDataFunction;

	public CTProjectPart() {
		bins = new ArrayList<>();
		rewriteContext = getRewriteContext();
	}

	public CTProjectPart(CTProject parent, String path) {
		this.parent = parent;
		this.path = path;
		bins = new ArrayList<>();
		rewriteContext = getRewriteContext();
	}

	@XmlTransient
	public IXFunction getSourceDataFunction() {
		if (sourceDataFunction == null) {
			final DataContext context = getParent().getData();
			sourceDataFunction = context.getFunctionByCanonical(getPath());
		}
		return sourceDataFunction;
	}

	public ColumnModel ownColumn() {
		return parent.resolvePath(getPath());
	}

	public void normalise() {
		sourceDataFunction = null;

		if (bins != null) {
			for (final CTBin b : bins) {
				b.setParent(this);
			}
		}

		for (final CTRewriteRule r : getRewriteRules()) {
			r.setContainer(this);
		}

		for (final CTProductionRule r : getProductionRules()) {
			r.setContainer(this);
		}
	}

	@XmlTransient
	public CTProject getParent() {
		return parent;
	}

	public void setParent(CTProject parent) {
		this.parent = parent;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<CTBin> getBins() {
		return bins;
	}

	public void setBins(List<CTBin> bins) {
		this.bins = bins;
	}

	public boolean isProductionReduced(CTEPartBase pattern) {
		return rewrite_pro(pattern) == CTETokenList.EMPTY;
	}

	public Collection<PatternInfo> getPatterns() {
		try {
			final DataContext context = parent.getData();
			final IXFunction f = context.getFunctionByCanonical(path);
			if (f != null) {
				final XFunctionBasic ff = (XFunctionBasic) f;
				final List<PatternInfo> patterns = new ArrayList<>();
				for (final Object zz : ff.getMaps().keySet()) {
					final List<Object> ll = ff.getMaps().get(zz);
					for (final Object l : ll) {
						final String rawCellText = l.toString();
						CTEPartBase pattern = CTBuilder.build(rawCellText);
						if (pattern != null) {
							pattern = rewrite_rwe(pattern);
							pattern = rewrite_pro(pattern);
							if (!pattern.isEmpty()) {
								final PatternSampleInfo sampleInfo = new PatternSampleInfo(zz, pattern, rawCellText);
								final List<PatternSampleInfo> slist = new ArrayList<>();
								slist.add(sampleInfo);
								final PatternInfo self = new PatternInfo(pattern, slist);
								if (patterns.indexOf(self) != -1) {
									final PatternInfo other = patterns.get(patterns.indexOf(self));
									other.samples.add(sampleInfo);
									other.pattern.merge(pattern);
								} else {
									patterns.add(self);
								}
							}
						} else {
							System.err.println("[CTE] failed to parse " + rawCellText);
						}

					}
				}
				for (final PatternInfo pinfo : patterns) {
					pinfo.pattern.index(0);
				}
				return patterns;
			}
		} catch (final Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	private ICTRuleContext<CTRewriteRule> getRewriteContext() {
		return new ICTRuleContext<CTRewriteRule>() {
			@Override
			public Collection<CTRewriteRule> getRulesLocal() {
				return CTProjectPart.this.getRewriteRules();
			}

			@Override
			public Collection<CTRewriteRule> getRulesGlobal() {
				return parent.getRewriteRules();
			}

			@Override
			public CTEPartBase apply(CTRewriteRule rule, CTEPartBase target) {
				return rule.computeRWERule().apply(target);
			}

			@Override
			public int span(CTRewriteRule rule) {
				return rule.computeRWERule().span();
			}

			@Override
			public boolean isReady(CTRewriteRule rule) {
				return rule.computeRWERule() != null;
			}

		};
	}

	private ICTRuleContext<CTRewriteRule> getRewriteContext(final CTRewriteRule rule) {
		return new ICTRuleContext<CTRewriteRule>() {
			@Override
			public Collection<CTRewriteRule> getRulesLocal() {
				return Collections.singleton(rule);
			}

			@Override
			public Collection<CTRewriteRule> getRulesGlobal() {
				return Collections.emptyList();
			}

			@Override
			public CTEPartBase apply(CTRewriteRule rule, CTEPartBase target) {
				return rule.computeRWERule().apply(target);
			}

			@Override
			public int span(CTRewriteRule rule) {
				return rule.computeRWERule().span();
			}

			@Override
			public boolean isReady(CTRewriteRule rule) {
				return rule.computeRWERule() != null;
			}
		};
	}

	private ICTRuleContext<CTProductionRule> getProductionContext(final Map<String, List<String>> context) {
		return new ICTRuleContext<CTProductionRule>() {
			@Override
			public Collection<CTProductionRule> getRulesLocal() {
				return CTProjectPart.this.getProductionRules();
			}

			@Override
			public Collection<CTProductionRule> getRulesGlobal() {
				return Collections.emptyList();
			}

			@Override
			public CTEPartBase apply(CTProductionRule rule, CTEPartBase target) {
				return rule.computePRRule().apply(target, context);
			}

			@Override
			public int span(CTProductionRule rule) {
				return rule.computePRRule().span();
			}

			@Override
			public boolean isReady(CTProductionRule rule) {
				return rule.computePRRule() != null;
			}
		};
	}

	private ICTRuleContext<CTProductionRule> getProductionContextSingle(final CTProductionRule rule,
			final Map<String, List<String>> context) {
		return new ICTRuleContext<CTProductionRule>() {
			@Override
			public Collection<CTProductionRule> getRulesLocal() {
				return Collections.singleton(rule);
			}

			@Override
			public Collection<CTProductionRule> getRulesGlobal() {
				return Collections.emptyList();
			}

			@Override
			public CTEPartBase apply(CTProductionRule rule, CTEPartBase target) {
				return rule.computePRRule().apply(target, context);
			}

			@Override
			public int span(CTProductionRule rule) {
				return rule.computePRRule().span();
			}

			@Override
			public boolean isReady(CTProductionRule rule) {
				return rule.computePRRule() != null;
			}
		};
	}

	/**
	 * Rewrite the target using rewrite rules
	 * 
	 * @param target expression to rewrite
	 * @return rewrite result
	 */
	public CTEPartBase rewrite_rwe(CTEPartBase target) {
		return rewrite(rewriteContext, target);
	}

	public CTEPartBase rewrite_rwe(CTEPartBase target, CTRewriteRule rule) {
		return rewrite(getRewriteContext(rule), target);
	}

	/**
	 * Rewrite the target using production rules
	 * 
	 * @param target expression to rewrite
	 * @return rewrite result
	 */
	public CTEPartBase rewrite_pro(CTEPartBase target, Map<String, List<String>> map) {
		final ICTRuleContext<CTProductionRule> context = getProductionContext(map);
		return rewrite(context, target);
	}

	public CTEPartBase rewrite_pro(CTEPartBase target) {
		Map<String, List<String>> map = new HashMap<>();
		final ICTRuleContext<CTProductionRule> context = getProductionContext(map);
		map = null;
		return rewrite(context, target);
	}

	/**
	 * Rewrite the target using production rules
	 * 
	 * @param target expression to rewrite
	 * @return rewrite result
	 */
	public CTEPartBase rewrite_pro(CTEPartBase target, CTProductionRule rule, Map<String, List<String>> map) {
		final ICTRuleContext<CTProductionRule> context = getProductionContextSingle(rule, map);
		return rewrite(context, target);
	}

	public CTEPartBase rewrite_pro(CTEPartBase target, CTProductionRule rule) {
		Map<String, List<String>> map = new HashMap<>();
		final ICTRuleContext<CTProductionRule> context = getProductionContextSingle(rule, map);
		map = null;
		return rewrite(context, target);
	}

	private CTEPartBase rewrite(ICTRuleContext<?> context, CTEPartBase target) {
		int iterations = REWRITE_DEPTH;
		CTEPartBase result = rewrite_once(context, target);
		while (!result.equals(target) && iterations-- > 0) {
			target = result;
			result = rewrite_once(context, result);
		}

		return result;
	}

	private CTEPartBase rewrite_once(ICTRuleContext<?> context, CTEPartBase target) {
		if (target instanceof CTETokenList) {
			return rewrite_list(context, (CTETokenList) target, true);
		} else if (target instanceof CTEToken) {
			return rewrite_token(context, (CTEToken) target, true);
		} else {
			return null;
		}
	}

	private <T extends CTRule> CTEPartBase rewrite_list(ICTRuleContext<T> context, CTETokenList target, boolean top) {
		final Stack<CTEPartBase> stack = new Stack<>();

		if (top) {
			rewrite_list_iteration(context, stack, CTEToken.Start);
		}

		for (final CTEPartBase p : target.getParts()) {
			rewrite_list_iteration(context, stack, p);
		}

		if (top) {
			rewrite_list_iteration(context, stack, CTEToken.End);
		}

		CTEPartBase result;

		if (top) {
			chopStartAndEnd(stack);
		}

		result = new CTETokenList(stack);

		return result;
	}

	private void chopStartAndEnd(Stack<CTEPartBase> stack) {
		if (!stack.isEmpty()) {
			if (stack.get(0) == CTEToken.Start) {
				stack.remove(0);
			}
			if (!stack.isEmpty() && stack.get(stack.size() - 1) == CTEToken.End) {
				stack.remove(stack.size() - 1);
			}
		}
	}

	private <T extends CTRule> CTEPartBase rewrite_list_iteration(ICTRuleContext<T> context, Stack<CTEPartBase> stack, CTEPartBase p) {
		if (p instanceof CTETokenList) {
			final CTETokenList plist = (CTETokenList) p;
			p = rewrite_list(context, plist, false);
		}
		stack.add(p);
		rewrite_step(context, stack);
		return p;
	}

	private <T extends CTRule> void rewrite_step(ICTRuleContext<T> context, Stack<CTEPartBase> stack) {
		final List<T> sortedRules = sortRulesBySpan(context);

		for (final T rule : sortedRules) {
			if (context.isReady(rule) && rule.isEnabled()) {
				final int span = context.span(rule);
				if (stack.size() >= span) {
					final CTEPartBase extraction = new CTETokenList(stack.subList(stack.size() - span, stack.size()));
					final CTEPartBase result = context.apply(rule, extraction);
					if (result != extraction) {
						stack.setSize(stack.size() - span);
						if (result instanceof CTETokenList) {
							final CTETokenList rlist = (CTETokenList) result;
							if (rlist.getParts() != null) {
								for (final CTEPartBase p : rlist.getParts()) {
									stack.add(p);
								}
							}
						} else {
							stack.add(result);
						}
					}
				}
			}
		}
	}

	private <T extends CTRule> List<T> sortRulesBySpan(final ICTRuleContext<T> context) {
		final List<T> list = new ArrayList<>(context.getRulesLocal());
		list.addAll(context.getRulesGlobal());
		Collections.sort(list, new Comparator<T>() {

			@Override
			public int compare(T o1, T o2) {
				if (!context.isReady(o1) || !o1.isEnabled()) {
					return 1;
				}
				if (!context.isReady(o2) || !o2.isEnabled()) {
					return -1;
				}
				return context.span(o2) - context.span(o1);
			}

		});

		return list;
	}

	private CTEPartBase rewrite_token(ICTRuleContext<?> context, CTEToken target, boolean top) {
		final Stack<CTEPartBase> stack = new Stack<>();

		if (top) {
			stack.add(CTEToken.Start);
		}

		stack.add(target);

		if (top) {
			stack.add(CTEToken.End);
		}

		rewrite_step(context, stack);

		if (top) {
			chopStartAndEnd(stack);
		}

		if (stack.isEmpty()) {
			return null;
		} else if (stack.size() == 1) {
			return stack.peek();
		} else {
			return new CTETokenList(stack);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (bins == null ? 0 : bins.hashCode());
		result = prime * result + (path == null ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CTProjectPart other = (CTProjectPart) obj;
		if (bins == null) {
			if (other.bins != null) {
				return false;
			}
		} else if (!bins.equals(other.bins)) {
			return false;
		}
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!path.equals(other.path)) {
			return false;
		}
		return true;
	}

	public CTBin resolveBin(String id) {
		for (final CTBin b : bins) {
			if (b.getName().equals(id)) {
				return b;
			}
		}

		return null;
	}

	public void dsExport(DatasetModel output) {
		for (final CTProductionRule p : getProductionRules()) {
			p.dsExport(output);
		}
	}

	public String getPartName() {
		final String[] parts = path.split("/");
		return parts[parts.length - 1].trim();
	}

}
