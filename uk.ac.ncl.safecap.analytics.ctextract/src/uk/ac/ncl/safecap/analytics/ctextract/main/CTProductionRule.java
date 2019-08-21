package uk.ac.ncl.safecap.analytics.ctextract.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPartBase;
import uk.ac.ncl.safecap.analytics.ctextract.core.CTETokenList;
import uk.ac.ncl.safecap.analytics.ctextract.core.PRRule;
import uk.ac.ncl.safecap.analytics.ctextract.parser.CTBuilder;
import uk.ac.ncl.safecap.analytics.ctextract.parser.PRBuilder;
import uk.ac.ncl.safecap.analytics.ctextract.parser.RWEBuilder;
import uk.ac.ncl.safecap.cteparser.TEContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;

public class CTProductionRule extends CTRule {
	private transient PRRule rule;

	public Map<String, Map<String, List<String>>> evaluatePattern() {
		final IXFunction f = parent().getSourceDataFunction();
		final Map<String, Map<String, List<String>>> result = new HashMap<>();
		if (f != null) {
			final XFunctionBasic ff = (XFunctionBasic) f;
			for (final Object zz : ff.getMaps().keySet()) {
				final List<Object> ll = ff.getMaps().get(zz);
				final Map<String, List<String>> map = new HashMap<>();
				evaluatePatternFor(map, ll);
				if (!map.isEmpty()) {
					result.put(zz.toString(), map);
				}
			}
		}

		return result;
	}

	public Map<String, List<String>> evaluatePattern(Object domain) {
		final IXFunction f = parent().getSourceDataFunction();
		final Map<String, List<String>> result = new HashMap<>();
		if (f != null) {
			final XFunctionBasic ff = (XFunctionBasic) f;
			final List<Object> ll = ff.get(domain);
			if (ll != null) {
				evaluatePatternFor(result, ll);
			}
		}

		return result;
	}

	private CTProjectPart parent() {
		return (CTProjectPart) getContainer();
	}

	private void evaluatePatternFor(Map<String, List<String>> result, List<Object> ll) {
		for (final Object l : ll) {
			final String rawCellText = l.toString();
			CTEPartBase data = CTBuilder.build(rawCellText);
			data = parent().rewrite_rwe(data);
			parent().rewrite_pro(data, this, result);
		}
	}

	public PRRule computePRRule() {
		if (rule != null || context != null && !context.getErrors().isEmpty()) {
			return rule;
		}

		context = new TEContext();
		final CTEPartBase left = PRBuilder.build(getPattern(), context);

		CTEPartBase right = CTETokenList.EMPTY;
		if (getTemplate() != null && getTemplate().length() > 0 && !getTemplate().equals("()")) {
			right = RWEBuilder.build(getTemplate(), context, false);
		}

		if (left == null || right == null) {
			return null;
		}

		rule = new PRRule(left, right);

		rule.check(parent(), context);
		if (!context.getErrors().isEmpty()) {
			rule = null;
		}

		return rule;
	}

	@Override
	public void setPattern(String pattern) {
		rule = null;
		if (context != null) {
			context.clear();
		}
		super.setPattern(pattern);
	}

	@Override
	public void setTemplate(String template) {
		rule = null;

		if (context != null) {
			context.clear();
		}
		super.setTemplate(template);
	}

	public void dsExport(DatasetModel output) {
		String concept = parent().getPartName();
		final XFunctionBasic f = (XFunctionBasic) parent().getSourceDataFunction();
		for (final Object z : f.getMaps().keySet()) {
			final String id = z.toString();
			final DatasetBlock block = output.getBlock(concept, id);
			final Map<String, List<String>> result = evaluatePattern(z);
			for (final String key : result.keySet()) {
				final CTBin bin = parent().resolveBin(key);
				if (bin != null) {
					if (bin.getPath() == null || bin.getPath().equals(parent().getPath())) {
						for (final String value : result.get(key)) {
							block.addEntry(key, value);
						}
					} else {
						final CTProjectPart foreign = parent().getParent().resolveProjectPartByPath(bin.getPath());
						if (foreign != null) {
							concept = foreign.getPartName();
							for (final String value : result.get(key)) {
								output.addEntry(concept, id, key, value);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		if (rule == null) {
			return "?";
		} else {
			return rule.toString();
		}
	}
}
