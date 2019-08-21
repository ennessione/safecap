package uk.ac.ncl.safecap.xdata.verification.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import safecap.derived.MergedPoint;
import safecap.trackside.GenericLocatedEquipment;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLForeignLocationValue;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.typing.BProto;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.CLFormulaParser;
import uk.ac.ncl.prime.sim.parser.FormulaSource;
import uk.ac.ncl.prime.sim.sets.BMap;
import uk.ac.ncl.prime.sim.sets.BSeq;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.common.IObjectResolver;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.ColorUtil;
import uk.ac.ncl.safecap.misc.core.EquipmentUtil;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.OverlapUtil;
import uk.ac.ncl.safecap.misc.core.PointUtil;
import uk.ac.ncl.safecap.misc.core.RouteUtil;
import uk.ac.ncl.safecap.misc.core.SignalUtil;
import uk.ac.ncl.safecap.misc.core.SubOverlapUtil;
import uk.ac.ncl.safecap.misc.core.SubRouteUtil;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xmldata.IConceptMap;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.ValueList;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;

public class GenericReport {
	protected static final Map<String, String> NO_PARAM = new HashMap<>();
	protected static final String PARAM_DELIM_LAST = "delimlast";
	protected static final String PARAM_DELIM = "delim";
	protected static final String PARAM_EMPTY = "empty";
	protected static final String PARAM_LOC = "loc";

	protected SDAContext context;

	protected GenericReport(SDAContext context) {
		this.context = context;
	}

	protected boolean isValidElement(Object element) {
		return true;
	}

	@SuppressWarnings("rawtypes")
	protected String format(Object val, Map<String, String> paramsmap) {
		try {
			final StringBuilder result = new StringBuilder();
			if (val instanceof BSeq) {
				final BSeq seq = (BSeq) val;
				if (seq.isEmpty()) {
					result.append("<b>" + getEmpty(paramsmap) + "</b>");
				}
				for (int i = 0; i < seq.card(); i++) {
					if (i > 0 && i < seq.card() - 1) {
						result.append(getDelim(paramsmap));
					} else if (i == seq.card() - 1 && seq.card() > 1) {
						result.append(getDelimLast(paramsmap));
					}
					result.append(format(seq.getByIndex(i), paramsmap));
				}
			} else if (val instanceof BSet) {
				final BSet set = (BSet) val;
				if (set.isEmpty()) {
					result.append("<b>" + getEmpty(paramsmap) + "</b>");
				} else if (getParam(paramsmap, "ref", "false").equals("true")) {
					final Collection<String> values = new HashSet<>();
					for (final Object x : set) {
						values.add(x.toString());
					}
					// System.out.println("Values: " + values);
					result.append(formattedReferences(values));
				} else {
					for (int i = 0; i < set.card(); i++) {
						if (i > 0 && i < set.card() - 1) {
							result.append(getDelim(paramsmap));
						} else if (i == set.card() - 1 && set.card() > 1) {
							result.append(getDelimLast(paramsmap));
						}
						result.append(format(set.getByIndex(i), paramsmap));
					}
				}
			} else if (val instanceof BProto) {
				if (!isValidElement(val)) {
					result.append("<span color=\"red\">&#10060;</span>");
				}
				final BProto proto = (BProto) val;
				if (getParam(paramsmap, "ref", "false").equals("true")) {
					result.append(formattedReferences(Collections.singleton(proto.getId().toString())));
				} else {
					result.append(format("element", color(generateObjectColor(proto), proto.getId().toString())));
				}
				desribe(result, proto);
			} else if (val instanceof BMap) {
				final BMap map = (BMap) val;
				result.append(getParam(paramsmap, "beforemap", ""));
				result.append(format(map.prj1(), paramsmap));
				result.append(getParam(paramsmap, "map", "&#8614;"));
				result.append(format(map.prj2(), paramsmap));
				result.append(getParam(paramsmap, "aftermap", ""));
			} else if (val instanceof CLForeignLocationValue) {
				final CLForeignLocationValue locval = (CLForeignLocationValue) val;
				result.append(format(locval.getValue(), paramsmap));
				if (locval.getLocation() != null && locval.getLocation().getSource() != null) {
					//result.append(format("code", " " + locval.getLocation()));
					//result.append(format("code", "@[" + locval.getLocation().getSource() + "]"));
				}
			} else {
				if (!isValidElement(val)) {
					result.append("<span color=\"red\">&#10060;</span>");
				}
				result.append(format("element", color(generateObjectColor(val), CLUtils.unquote(val.toString()))));
			}
			return result.toString();
		} catch (final InvalidSetOpException e) {
			e.printStackTrace();
			return "";
		}
	}

	private void desribe(StringBuilder result, BProto proto) {
		if (proto.getType().getName().equals("SubOverlap")) {
			desribeElementSuffix(result, proto, "SubOverlap", "suboverlap:pointnormal", "N", "suboverlap:pointreverse", "R");
		} else if (proto.getType().getName().equals("SubRoute")) {
			desribeElementSuffix(result, proto, "SubRoute", "subroute:pointnormal", "N", "subroute:pointreverse", "R");
		} else if (proto.getType().getName().equals("Node")) {
			desribeElementPrefix(result, proto, "Node", "point:tracks", "tracks", "point:trap", "trap");
		}

	}

	private void desribeElementPrefix(StringBuilder result, BProto proto, String type, String... functions) {
		if (proto.getType().getName().equals(type)) {
			final Map<String, List<Object>> map = new HashMap<>();
			for (int i = 0; i < functions.length; i += 2) {
				final List<Object> list = new ArrayList<>();
				desribeElementSuffix(proto, functions[i], "", list);
				if (!list.isEmpty()) {
					map.put(functions[i + 1], list);
				}
			}

			if (!map.isEmpty()) {
				result.append(" (");
				boolean first = true;
				for (final String key : map.keySet()) {
					if (!first) {
						result.append("; ");
					}
					result.append(key);
					result.append(": ");
					ppList(result, map.get(key), false);
					first = false;
				}

				result.append(")");
			}

		}
	}

	private void desribeElementSuffix(StringBuilder result, BProto proto, String type, String... functions) {
		if (proto.getType().getName().equals(type)) {
			final List<Object> list = new ArrayList<>();
			for (int i = 0; i < functions.length; i += 2) {
				desribeElementSuffix(proto, functions[i], functions[i + 1], list);
			}

			ppList(result, list, true);

		}
	}

	private void ppList(StringBuilder result, List<Object> list, boolean brackets) {
		if (!list.isEmpty()) {
			if (brackets) {
				result.append(" (");
			}
			for (int i = 0; i < list.size(); i++) {
				if (i > 0) {
					result.append(", ");
				}
				result.append(list.get(i));
			}

			if (brackets) {
				result.append(")");
			}
		}
	}

	private void desribeElementSuffix(BProto proto, String f, String suffix, List<Object> list) {
		final IXFunction ff = context.getFunction(f);
		if (ff != null && ff.get(proto.getId()) != null) {
			for (final Object obj : ff.get(proto.getId())) {
				list.add(obj.toString() + suffix);
			}
		}
	}

	protected static String color(String color, String text) {
		return "<span style=\"color:" + color + "\">" + text + "</span>";
	}

	protected static String format(String className, String text) {
		return "<span class=\"" + className + "\">" + text + "</span>";
	}

	private String formattedReferences(Collection<String> literals) {
		final StringBuilder sb = new StringBuilder();

		sb.append("<ul>");
		boolean empty = true;
		for (final String s : context.getFunctionIds()) {
			final IXFunction ff = context.getFunction(s);
			if (ff.getFunctionType() != null && ff.getFunctionType() instanceof XRelationType && literalFilter(ff, literals)) {
				empty = false;
				final IConceptMap conceptMap = context.getConceptMap();
				final String external = conceptMap != null ? conceptMap.getConceptProvenance(ff.getCanonicalName()) : null;
				// System.out.println("Matched: " + ff.getName() + "; external " + external);
				sb.append("<li>");
				if (external != null && external.length() > 0) {
					sb.append(format("element", external));
				} else {
					sb.append(format("element", ff.getName()));
				}
				sb.append("</li>");
			}
		}
		if (empty) {
			sb.append("<li>");
			sb.append(format("element", "none"));
			sb.append("</li>");
		}
		sb.append("</ul>");

		return sb.toString();
	}

	private boolean literalFilter(IXFunction ff, Collection<String> literals) {
		if (ff instanceof XFunctionBasic) {
			final XFunctionBasic fb = (XFunctionBasic) ff;
			for (final Object member : fb.domain()) {
				if (literals.contains(member.toString())) {
					return true;
				}
				return literalFilter(fb.get(member), literals);
			}
		}

		return false;
	}

	private boolean literalFilter(Collection<Object> collection, Collection<String> literals) {
		for (final Object value : collection) {
			if (value instanceof ValueList) {
				final ValueList vl = (ValueList) value;
				final boolean result = literalFilter(vl.getValues(), literals);
				if (result) {
					return true;
				}
			} else if (literals.contains(value.toString())) {
				return true;
			}
		}

		return false;
	}

	protected static String getParam(Map<String, String> paramsmap, String key, String defvalue) {
		if (paramsmap.containsKey(key)) {
			return paramsmap.get(key);
		} else {
			return defvalue;
		}
	}

	protected static String getDelim(Map<String, String> paramsmap) {
		return getParam(paramsmap, PARAM_DELIM, ", ");
	}

	protected static String getDelimLast(Map<String, String> paramsmap) {
		return getParam(paramsmap, PARAM_DELIM_LAST, getParam(paramsmap, PARAM_DELIM, " and "));
	}

	protected static String getEmpty(Map<String, String> paramsmap) {
		return getParam(paramsmap, PARAM_EMPTY, "nothing");
	}
	
	protected static boolean ppLocation(Map<String, String> paramsmap) {
		return "yes".equals(getParam(paramsmap, PARAM_LOC, "no"));
	}	

	@SuppressWarnings("rawtypes")
	protected static String generateObjectColor(Object value) {
		if (value instanceof BProto) {
			final CLType type = ((BProto) value).getType();
			if (type == null) {
				return "#000000";
			}
			return ColorUtil.makeLightColorCode(type.toString());
		} else if (value instanceof Integer) {
			return "#5B2C6F";
		} else if (value instanceof Boolean) {
			return "#2874A6";
		} else if (value instanceof Double) {
			return "#7D3C98";
		} else if (value instanceof String) {
			return ColorUtil.makeLightColorCode(value.toString());
		}

		return "#000000";
	}

	protected static Object evaluate(CLFormulaParser parser, TypingContext extra, String expr, SDARuntimeExecutionContext mctx)
			throws Exception {
		final CLExpression parsed = parser.parseOnly(new FormulaSource(expr));
		if (parsed == null) {
			throw new Exception("Failed parsing expression " + expr);
		}

		parsed.type(extra);
		if (parsed.getType() == null) {
			throw new Exception("Failed typing expression " + expr);
		}

		Object result;
		try {
			result = parsed.getValueInterpreted(mctx);
		} catch (final Throwable e) {
			throw new Exception("Failed evaluating expression " + expr + ": " + e.getMessage());
		}

		return result;
	}

	protected static IObjectResolver getObjectResolver(final CLFormulaParser parser, final Resource diagramResource,
			final TypingContext extra, final SDARuntimeExecutionContext newContext, final boolean toVisual) {
		return new IObjectResolver() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Collection<Object> resolve(String expression) {
				try {
					final Object elements = evaluate(parser, extra, expression, newContext);
					Collection<Object> _c;
					if (elements instanceof BSet) {
						final BSet set = (BSet) elements;
						_c = flattenSet(set.asCollection());
					} else {
						_c = flattenSet(Collections.singleton(elements));
					}

					final Collection<Object> resolved = new ArrayList<>();

					if (toVisual) {
						for (final Object e : _c) {
							final Collection<Object> r = resolveToSafecapElement(diagramResource, e);
							if (r != null) {
								resolved.addAll(r);
							}
						}
						return resolved;
					} else {
						return _c;
					}
				} catch (final Throwable e) {
					System.err.println("GenericReport::getObjectResolver: " + e.getMessage());
					return null;
				}
			}

		};
	}

	protected static Collection<Object> flattenSet(Collection<Object> set) {
		final Collection<Object> result = new HashSet<>();
		for (final Object o : set) {
			_flattenSet(o, result);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	protected static void _flattenSet(Object object, Collection<Object> result) {
		if (object instanceof BSet) {
			final BSet set = (BSet) object;
			for (final Object o : set.elements()) {
				result.add(o);
			}
		} else if (object instanceof BMap) {
			final BMap map = (BMap) object;
			result.add(map.prj1());
			result.add(map.prj2());
		} else {
			result.add(object);
		}
	}

	@SuppressWarnings("rawtypes")
	protected static Collection resolveToSafecapElement(Resource diagramResource, Object value) {
		if (value instanceof BProto && diagramResource != null) {
			final safecap.Project root = (safecap.Project) diagramResource.getContents().get(0);
			final BProto proto = (BProto) value;
			final String type = proto.getType().getName();
			if (type.equals("Route")) {
				return Collections.singleton(RouteUtil.getByLabel(root, proto.getId().toString()));
			} else if (type.equals("Track")) {
				return Collections.singleton(AmbitUtil.getByLabelPrefix(root, proto.getId().toString()));
			} else if (type.equals("Signal")) {
				return Collections.singleton(SignalUtil.getByLabel(root, proto.getId().toString()));
			} else if (type.equals("Node")) {
				final EObject result = NodeUtil.getByLabel(root, proto.getId().toString());
				if (result != null) {
					return Collections.singleton(result);
				} else {
					final MergedPoint mp = PointUtil.getMergedByLabel(root, proto.getId().toString());
					if (mp != null) {
						return mp.getPoints();
					}
				}

			} else if (type.equals("SubRoute")) {
				final String s = proto.getId().toString();
				return Collections.singleton(SubRouteUtil.getSubRouteFromName(root, s));
			} else if (type.equals("SubOverlap")) {
				final String s = proto.getId().toString();
				return Collections.singleton(SubOverlapUtil.getSubOverlapFromName(root, s));
			} else if (type.equals("Overlap")) {
				final String s = proto.getId().toString();
				return Collections.singleton(OverlapUtil.getOverlapByName(root, s));
			} else {
				final String s = proto.getId().toString();
				final GenericLocatedEquipment gleqp = EquipmentUtil.getGenEqpByLabel(root, s);
				if (gleqp != null) {
					return Collections.singleton(gleqp.getSegment());
				}
				System.err.println("Do not know how to resolve elements of type " + type);
				return null;
			}
		}

		return null;
	}

	protected String expand(CLFormulaParser parser, TypingContext extra, String template, SDARuntimeExecutionContext mctx)
			throws Exception {
		final Map<String, Object> values = new HashMap<>();

		// pass one: compute expression templates
		final List<String> parts = new ArrayList<>(10);
		int pos0 = template.indexOf("${");
		int pos1 = template.indexOf("}$", pos0);

		int last = 0;
		while (pos0 >= 0 && pos1 > pos0) {
			final Map<String, String> paramsmap = new HashMap<>();
			final String[] tparts = parseTemplateParts(template, pos0, paramsmap);
			final String pre = template.substring(last, pos0);
			last = pos1 + 2;

			parts.add(pre);
			final Object v = evaluate(parser, extra, tparts[1], mctx);

			if (tparts[0] != null) {
				if (values.keySet().contains(tparts[0])) {
					throw new Exception("Duplicate expression id - " + tparts[0]);
				}
				values.put(tparts[0], v);
			}
			parts.add(format(v, paramsmap));

			pos0 = template.indexOf("${", pos1);
			pos1 = template.indexOf("}$", pos0);
		}
		parts.add(template.substring(last));

		// pass two: compute string templates
		final List<String> result_parts = new ArrayList<>(parts.size());
		for (final String p : parts) {
			result_parts.add(expandStringTemplates(p, values));
		}

		final StringBuilder result = new StringBuilder();

		for (final String p : result_parts) {
			result.append(p);
		}

		return result.toString();
	}

	@SuppressWarnings("rawtypes")
	protected static String expandStringTemplates(String text, Map<String, Object> values) throws Exception {
		final List<String> parts = new ArrayList<>(10);
		int pos0 = text.indexOf("$[");
		int pos1 = text.indexOf("]$", pos0);

		int last = 0;
		while (pos0 >= 0 && pos1 > pos0) {
			final String body = text.substring(pos0 + 2, pos1);

			/*
			 * $[id / empty / singular / plural]$ part[0] = id part[1] = empty part[2] =
			 * singular part[3] = plural
			 */
			final String[] tparts = body.split("/");

			if (tparts.length != 4) {
				throw new Exception("Invalid string template body - " + body);
			}

			final String id = tparts[0];
			final String empty = tparts[1];
			final String singular = tparts[2];
			final String plural = tparts[3];
			final String pre = text.substring(last, pos0);
			last = pos1 + 2;
			final Object eval = values.get(id);
			if (eval == null) {
				throw new Exception("Invalid string template expression id - " + id);
			}

			parts.add(pre);

			if (eval instanceof BSet) {
				final BSet set = (BSet) eval;
				if (set.isEmpty()) {
					parts.add(empty);
				} else if (set.card() == 1) {
					parts.add(singular);
				} else {
					parts.add(plural);
				}
			}

			pos0 = text.indexOf("$[", pos1);
			pos1 = text.indexOf("]$", pos0);
		}
		parts.add(text.substring(last));

		final StringBuilder sb = new StringBuilder();

		for (final String p : parts) {
			sb.append(p);
		}

		return sb.toString();
	}

	public static String[] parseTemplateParts(String template, int pos0, Map<String, String> paramsmap) throws Exception {
		int pos1 = template.indexOf("$", pos0 + 2);
		final int pos2 = template.indexOf("}$", pos0);
		int pos3 = template.indexOf("}{", pos0);
		if (pos1 > pos2) {
			pos1 = -1;
		}
		if (pos3 > pos2) {
			pos3 = -1;
		}
		String id = null;
		String expression = null;
		String params = null;

		if (pos1 != -1) {
			id = template.substring(pos0 + 2, pos1);
			if (pos3 != -1) {
				expression = template.substring(pos1 + 1, pos3);
				params = template.substring(pos3 + 2, pos2);
			} else {
				expression = template.substring(pos1 + 1, pos2);
			}
		} else {
			if (pos3 != -1) {
				expression = template.substring(pos0 + 2, pos3);
				params = template.substring(pos3 + 2, pos2);
			} else {
				expression = template.substring(pos0 + 2, pos2);
			}
		}

		if (params != null) {
			parseParams(params, paramsmap);
		}

		return new String[] { id, expression };
	}

	public static int[] parseTemplateExpressionPart(String template, int pos0, Map<String, String> paramsmap) throws Exception {
		int pos1 = template.indexOf("$", pos0 + 2);
		final int pos2 = template.indexOf("}$", pos0);
		int pos3 = template.indexOf("}{", pos0);
		if (pos1 > pos2) {
			pos1 = -1;
		}
		if (pos3 > pos2) {
			pos3 = -1;
		}

		if (pos1 != -1) {
			if (pos3 != -1) {
				return new int[] { pos1 + 1, pos3 };
			} else {
				return new int[] { pos1 + 1, pos2 };
			}
		} else {
			if (pos3 != -1) {
				return new int[] { pos0 + 2, pos3 };
			} else {
				return new int[] { pos0 + 2, pos2 };
			}
		}

	}

	protected static void parseParams(String text, Map<String, String> map) throws Exception {
		final String[] parts = text.split(";");
		for (final String part : parts) {
			final String[] subparts = part.split("=");
			if (subparts.length == 2) {
				map.put(subparts[0], subparts[1]);
			}
		}
	}

}
