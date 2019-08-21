package uk.ac.ncl.safecap.xdata.provers.prob;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import uk.ac.ncl.safecap.common.MyEntry;
import uk.ac.ncl.safecap.misc.core.LocatedValue;
import uk.ac.ncl.safecap.xdata.provers.IElementTranslator;
import uk.ac.ncl.safecap.xdata.provers.TNameMapper;
import uk.ac.ncl.safecap.xdata.provers.TranslationException;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.IErrorInjector;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.NullInjector;
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

public class ProBElementTranslator implements IElementTranslator {
	private final TNameMapper nameMapper;
	private final ISDADataProvider dataContext;

	public ProBElementTranslator(ISDADataProvider dataContext, boolean obfuscate) {
		obfuscate = false;
		nameMapper = new TNameMapper(null, obfuscate, true);
		this.dataContext = dataContext;
	}

	public TNameMapper getNameMapper() {
		return nameMapper;
	}

	@Override
	public void translate(StringBuffer sb, XType type) throws TranslationException {
		if (type instanceof XBoolType) {
			sb.append("BOOL");
		} else if (type instanceof XIntegerType) {
			sb.append("INT");
		} else if (type instanceof XEnumType) {
			final XEnumType gt = (XEnumType) type;
			sb.append(mapTypeId(gt.getName()));
		} else if (type instanceof XRelationType) {
			final XRelationType rt = (XRelationType) type;
			sb.append("POW(");
			translate(sb, rt.getDomain());
			sb.append(" ** ");
			translate(sb, rt.getRange());
			sb.append(")");
		} else {
			throw new TranslationException("Cannot translate type " + type.toString());
		}
	}

	@Override
	public void translateDeclaration(StringBuffer sb, XType type) throws TranslationException {
		if (type instanceof XEnumType) {
			final XEnumType et = (XEnumType) type;
			final String mappedId = mapTypeId(et.getName());
			// sb.append("\t/*" + type.toString() + "*/\n");
			sb.append("\t");
			sb.append(mappedId);
			sb.append(" = {");

			final Iterator<String> iter = dataContext.getEnumMembers(et).iterator();

			if (iter.hasNext()) {
				sb.append(mapTypeLiteral(iter.next()));
			}

			while (iter.hasNext()) {
				sb.append(", ");
				sb.append(mapTypeLiteral(iter.next()));
			}

			sb.append("}");

		} else {
			throw new TranslationException("Cannot translate type " + type.toString());
		}
	}

	@Override
	public void translate(StringBuffer sb, IXFunction function, IErrorInjector errorInjector) throws TranslationException {
		final XType type = function.getFunctionType();
		if (type == null || !(type instanceof XRelationType)) {
			return;
		}

		if (errorInjector == null || errorInjector == NullInjector.INSTANCE) {
			sb.append("\t");
			sb.append(nameMapper.mapFreeIdentifier(function.getName()));
			sb.append(" = {");
			setextension(sb, ((XFunctionBasic) function).getParts(), 0);
			sb.append(" }");
		} else {
			sb.append("\t");
			sb.append(nameMapper.mapFreeIdentifier(function.getName()));
			sb.append(" = {");
			setextension(sb, getParts(function, errorInjector), 0);
			sb.append(" }");
		}
	}

	@SuppressWarnings("unchecked")
	public Entry<Object, Object>[] getParts(IXFunction function, IErrorInjector errorInjector) {
		final List<Entry<Object, Object>> result = new ArrayList<>();
		for (final Object k : function.domain()) {
			for (final Object v : errorInjector.injectError(function, k)) {
				result.add(new MyEntry<>(k, v));
			}
		}
		return result.toArray(new Entry[result.size()]);
	}

	@Override
	public String mapTypeId(String identifier) throws TranslationException {
		return nameMapper.mapType(identifier);
	}

	@Override
	public String mapTypeLiteral(String identifier) {
		return nameMapper.mapTypeLiteral(identifier);
	}

	private void setextension(StringBuffer sb, Entry<Object, Object>[] parts, int offset) throws TranslationException {

		for (int i = 0; i < parts.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}

			translateMapping(sb, parts[i]);
		}
	}

	private void translateMapping(StringBuffer sb, Entry<Object, Object> value) throws TranslationException {
		final String keyName = nameMapper.map(value.getKey().toString());
		sb.append("(");
		sb.append(keyName);
		sb.append(" |-> ");
		sb.append(mapLiteral(value.getValue()));
		sb.append(")");
	}

	private String mapLiteral(Object literal) throws TranslationException {
		if (literal instanceof Integer) {
			return literal.toString();
		} else if (literal == Boolean.TRUE) {
			return "TRUE";
		} else if (literal == Boolean.FALSE) {
			return "FALSE";
		} else if (literal instanceof LocatedValue) {
			final LocatedValue lv = (LocatedValue) literal;
			return mapLiteral(lv.getValue());
		} else if (literal instanceof String) {
			final String result = nameMapper.map(literal.toString());
			assert result != null;
			return result;
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
			} else if (vlist.getType() instanceof XProductType) {
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
			} else if (vlist.getType() instanceof XPowType) {
				final StringBuffer sb = new StringBuffer();
				sb.append("{");
				for (int i = 0; i < vlist.size(); i++) {
					if (i > 0) {
						sb.append(", ");
					}
					sb.append(mapLiteral(vlist.get(i)));
				}
				sb.append("}");
				return sb.toString();
			} else {
				throw new TranslationException("Cannot translate literal " + literal);
			}
		} else {
			throw new TranslationException("Cannot translate literal " + literal);
		}
	}
}
