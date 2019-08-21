package uk.ac.ncl.safecap.xdata.provers.why3;

import java.util.Map.Entry;

import uk.ac.ncl.eventb.why3.filter.FilterContext;
import uk.ac.ncl.eventb.why3.filter.TreeNode;
import uk.ac.ncl.safecap.misc.core.LocatedValue;
import uk.ac.ncl.safecap.xdata.provers.TNameMapper;
import uk.ac.ncl.safecap.xdata.provers.TranslationException;
import uk.ac.ncl.safecap.xmldata.IXFunction;
// import uk.ac.ncl.safecap.xmldata.TypedId;
import uk.ac.ncl.safecap.xmldata.ValueList;
import uk.ac.ncl.safecap.xmldata.base.ISDADataProvider;
import uk.ac.ncl.safecap.xmldata.types.XBoolType;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;
import uk.ac.ncl.safecap.xmldata.types.XIntegerType;
import uk.ac.ncl.safecap.xmldata.types.XPowType;
import uk.ac.ncl.safecap.xmldata.types.XProductType;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;
import uk.ac.ncl.safecap.xmldata.types.XSeqType;
import uk.ac.ncl.safecap.xmldata.types.XType;

public class Why3ElementTranslator {
	private final TNameMapper nameMapper;
	private final ISDADataProvider dataContext;
	private final FilterContext filterContext;

	private final TreeNode<String> astStringRoot;
	private TreeNode<String> astStringTree;

	public Why3ElementTranslator(ISDADataProvider dataContext, FilterContext filterContext) {
		nameMapper = new TNameMapper(null, false, true);
		this.dataContext = dataContext;
		this.filterContext = filterContext;

		astStringTree = new TreeNode<>("root");
		astStringRoot = astStringTree;
	}

	public TNameMapper getNameMapper() {
		return nameMapper;
	}

	public void translate(StringBuilder sb, XType type) throws TranslationException {
		if (type instanceof XBoolType) {
			sb.append("bool");
		} else if (type instanceof XIntegerType) {
			sb.append("int");
		} else if (type instanceof XEnumType) {
			final XEnumType gt = (XEnumType) type;
			sb.append(mapTypeId(gt.getName()));
		} else if (type instanceof XPowType) {
			final XPowType gt = (XPowType) type;
			sb.append("(set ");
			translate(sb, gt.getBase());
			sb.append(")");
		} else if (type instanceof XSeqType) {
			final XSeqType gt = (XSeqType) type;
			sb.append("(seq ");
			translate(sb, gt.getBase());
			sb.append(")");
		} else if (type instanceof XProductType) {
			final XProductType gt = (XProductType) type;
			sb.append("(");
			translate(sb, gt.getMembers().get(0));
			for (int i = 1; i < gt.getSize(); i++) {
				sb.append(", ");
				translate(sb, gt.getMembers().get(i));
			}
			sb.append(")");
		} else if (type instanceof XRelationType) {
			final XRelationType rt = (XRelationType) type;
			sb.append("(rel ");
			translate(sb, rt.getDomain());
			sb.append(" ");
			translate(sb, rt.getRange());
			sb.append(")");
		} else {
			throw new TranslationException("Cannot translate type " + type.toString());
		}
	}

	public void translateDeclaration(TheoremTranslated translated, XType type) throws TranslationException {
		if (type instanceof XEnumType) {
			final XEnumType et = (XEnumType) type;
			translateEnum(translated, et.getName(),
					dataContext.getEnumMembers(et).toArray(new String[dataContext.getEnumMembers(et).size()]));
		} else {
			throw new TranslationException("Cannot translate type " + type.toString());
		}
	}

	public void translateEnum(TheoremTranslated translated, String type, String[] members) throws TranslationException {
		final StringBuilder sb = new StringBuilder();
		final String mappedId = mapTypeId(type);
		final String mappedIdSet = nameMapper.mapFreeIdentifier(type + "_set");
		sb.append(mappedId);
		sb.append(" = ");

		if (members.length > 0) {
			sb.append(mapTypeLiteral(members[0]));
		}

		for (int i = 1; i < members.length; i++) {
			sb.append(" | ");
			sb.append(mapTypeLiteral(members[i]));
		}
		translated.addType(sb.toString());
		translated.addDefinition("constant " + mappedIdSet + ": (set " + mappedId + ")");
		translated.addHypothesis("(maxset " + mappedIdSet + ")");
	}

	public void translateCarrier(TheoremTranslated translated, String type) throws TranslationException {
		final StringBuilder sb = new StringBuilder();
		final String mappedId = mapTypeId(type);
		final String mappedIdSet = nameMapper.mapFreeIdentifier(type + "_set");
		sb.append(mappedId);
		translated.addType(sb.toString());
		translated.addDefinition("constant " + mappedIdSet + ": (set " + mappedId + ")");
		translated.addHypothesis("(maxset " + mappedIdSet + ")");
	}

	public void translate(TheoremTranslated translated, IXFunction function) throws TranslationException {
		final StringBuilder sb = new StringBuilder();
		final XType type = function.getFunctionType();
		if (type == null || !(type instanceof XRelationType)) {
			translated.addDefinition("(*failed translating " + function.getCanonicalName() + "*)");
			return;
		}

		sb.append("constant ");
		sb.append(nameMapper.mapFreeIdentifier(function.getName()));
		sb.append(": ");
		translate(sb, function.getFunctionType());
		sb.append(" = ");
		setextension(sb, ((XFunctionBasic) function).getParts(), 0);
		translated.addDefinition(sb.toString());
	}

	public String mapTypeId(String identifier) throws TranslationException {
		return nameMapper.mapType(identifier);
	}

	public String mapTypeLiteral(String identifier) {
		return nameMapper.mapTypeLiteral(identifier);
	}

	private void setextension(StringBuilder sb, Entry<Object, Object>[] parts, int offset) throws TranslationException {
		if (offset == parts.length - 1) {
			enter("singleton");
			sb.append("(");
			sb.append("singleton ");
			translateMapping(sb, parts[parts.length - 1]);
			sb.append(")");
			leave();
		} else {
			enter("add");
			sb.append("(");
			sb.append("add");
			sb.append(" ");
			translateMapping(sb, parts[offset]);
			sb.append(" ");
			setextension(sb, parts, offset + 1);
			sb.append(")");
			leave();
		}
	}

	private void translateMapping(StringBuilder sb, Entry<Object, Object> value) throws TranslationException {
		final String keyName = nameMapper.map(value.getKey().toString());
		sb.append("(");
		sb.append(keyName);
		sb.append(", ");
		sb.append(mapLiteral(value.getValue()));
		sb.append(")");
	}

	private String mapLiteral(Object literal) throws TranslationException {
		if (literal instanceof Integer) {
			return literal.toString();
		} else if (literal == Boolean.TRUE) {
			return "true";
		} else if (literal == Boolean.FALSE) {
			return "false";
			// } else if (literal instanceof TypedId) {
			// TypedId tid = (TypedId) literal;
			// String result = nameMapper.map(tid.getId());
			// assert(result != null);
			// return result;
		} else if (literal instanceof String) {
			final String result = nameMapper.map(literal.toString());
			assert result != null;
			return result;
		} else if (literal instanceof LocatedValue) {
			final LocatedValue lv = (LocatedValue) literal;
			return mapLiteral(lv.getValue());
		} else if (literal instanceof ValueList) {
			final ValueList vlist = (ValueList) literal;
			if (vlist.getType() instanceof XSeqType) {
				final StringBuffer sb = new StringBuffer();
				sb.append("[");
				for (int i = 0; i < vlist.size(); i++) {
					if (i > 0) {
						sb.append(", ");
					}
					sb.append(mapLiteral(vlist.get(i)));
				}
				sb.append("]");
				return sb.toString();
			} else {
				final StringBuffer sb = new StringBuffer();
				sb.append("(");
				for (int i = 0; i < vlist.size(); i++) {
					if (i > 0) {
						sb.append(", ");
					}
					sb.append(mapLiteral(vlist.get(i)));
				}
				sb.append(")");
				return sb.toString();
				// } else {
				// throw new TranslationException("Cannot translate literal " +
				// literal + " of type " + (vlist.getType() == null ? "unknown"
				// : vlist.getType()));
			}
		} else {
			throw new TranslationException("Cannot translate literal " + literal + " of type " + literal.getClass().getSimpleName());
		}
	}

	private void enter(String op) {
		astStringTree = astStringTree.addChild(op);
		if (filterContext != null) {
			filterContext.enter(op);
		}
	}

	private void leave() {
		astStringTree = astStringTree.getParent();
		if (filterContext != null) {
			filterContext.leave();
			if (astStringTree != astStringRoot) {
				filterContext.widthFilter(astStringTree);
			}
		}
	}
}
