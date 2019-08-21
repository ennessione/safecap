package uk.ac.ncl.safecap.xdata.verification.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.typing.BProto;
import uk.ac.ncl.prime.sim.lang.typing.CLEnumType;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLProductType;
import uk.ac.ncl.prime.sim.lang.typing.CLSeqType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLUserType;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.prime.sim.parser.ValidationContext;
import uk.ac.ncl.prime.sim.sets.BFun;
import uk.ac.ncl.prime.sim.sets.BMap;
import uk.ac.ncl.prime.sim.sets.BRel;
import uk.ac.ncl.prime.sim.sets.BSeq;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.common.RELATION_KIND;
import uk.ac.ncl.safecap.misc.core.LocatedValue;
import uk.ac.ncl.safecap.xdata.verification.dictionary.DictionarySection;
import uk.ac.ncl.safecap.xdata.verification.dictionary.TermDefinition;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.IErrorInjector;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.IErrorInjectorManager;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.NullInjector;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.ValueList;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;
import uk.ac.ncl.safecap.xmldata.types.XType;

public class SDARuntimeExecutionContext extends RuntimeExecutionContext {
	private final SDAContext sdaContext;
	private final Map<Object, Object> valueCache;
	private final TypingContext dataContext, modelContext;
	private final Map<String, TermDefinition> terms;
	private final Map<String, CLExpression> builtTerms;
	private final List<String> termIds;

	private IErrorInjectorManager errorInjector = NullInjector.INSTANCE;
	private FastRuntimeContext fastRuntimeContext;
	private SDARuntimeExecutionContext parent;

	protected SDARuntimeExecutionContext() {
		sdaContext = null;
		valueCache = null;
		dataContext = null;
		modelContext = null;
		terms = null;
		termIds = null;
		fastRuntimeContext = null;
		parent = null;
		builtTerms = null;
	}

	public SDARuntimeExecutionContext(SDARuntimeExecutionContext parent) {
		super(parent);

		this.parent = parent;
		errorInjector = parent == null ? null : parent.errorInjector;
		sdaContext = parent == null ? SDAContext.EMPTY : parent.sdaContext;
		valueCache = parent == null ? new HashMap<>() : new HashMap<>(parent.valueCache);
		terms = parent == null ? new HashMap<>() : parent.terms;
		builtTerms = parent == null ? new HashMap<>() : parent.builtTerms;
		termIds = parent == null ? new ArrayList<>() : parent.termIds;
		dataContext = parent == null ? new TypingContext(new ValidationContext(), this) : parent.dataContext;
		modelContext = parent == null ? new TypingContext(dataContext) : parent.modelContext;

	}

	public SDARuntimeExecutionContext(SDAContext sdaContext, boolean build) {
		super();
		this.sdaContext = sdaContext;
		valueCache = new HashMap<>();
		terms = new HashMap<>();
		builtTerms = new HashMap<>();
		termIds = new ArrayList<>();
		dataContext = new TypingContext(new ValidationContext(), this);
		modelContext = new TypingContext(dataContext);

		if (build) 
			build(sdaContext);
	}

	public void setTermExpression(String id, CLExpression expr) {
		builtTerms.put(id, CLUtils.flattenTermDependencies(this, expr));
	}
	
	public Map<String, CLExpression> getBuiltTerms() {
		if (builtTerms != null)
			return builtTerms;
		else
			return Collections.emptyMap();
	}
	
	public Map<String, CLExpression> getBuiltTerms(String tag) {
		if (builtTerms != null) {
			Map<String, CLExpression> r = new HashMap<>();
			for(String key: builtTerms.keySet()) {
				CLExpression e = builtTerms.get(key);
				if (CLUtils.hasDependencyOnTag(e, sdaContext, tag)) 
					r.put(key, e);
			}
			return r;
		} else
			return Collections.emptyMap();
	}	

	public void build(SDAContext sdaContext) {
		dataContext.clear();
		modelContext.clear();
		modelContext.setAutoPrimeMutableIdentifiers(true);
		refresh();

		if (sdaContext.getCatalog() != null) {
			refreshTerms();
		}
	}

	public SDARuntimeExecutionContext getParent() {
		return parent;
	}

	public FastRuntimeContext getFastRuntimeContext() {
		if (fastRuntimeContext == null) {
			fastRuntimeContext = new FastRuntimeContext(this);
		}

		return fastRuntimeContext;
	}

	public FastRuntimeContext getFastRuntimeContext(CLExpression expr) throws CLExecutionException {
		if (fastRuntimeContext == null) {
			fastRuntimeContext = new FastRuntimeContext(this);
		}

		fastRuntimeContext.prepare(expr.getFreeIdentifiers());
		return fastRuntimeContext;
	}

	public FastRuntimeContext getFastRuntimeContext(Collection<String> names) throws CLExecutionException {
		if (fastRuntimeContext == null) {
			fastRuntimeContext = new FastRuntimeContext(this);
		}

		fastRuntimeContext.prepare(names);
		return fastRuntimeContext;
	}

	public SDAContext getSdaContext() {
		return sdaContext;
	}

	public IErrorInjector getErrorInjector() {
		return errorInjector;
	}

	public void setErrorInjector(IErrorInjectorManager errorInjector) {
		// drop old injector values
		for (final IXFunction f : this.errorInjector.getFunctions()) {
			valueCache.remove(f.getName());
		}

		// clear new injector values
		this.errorInjector = errorInjector;
		if (this.errorInjector != NullInjector.INSTANCE) {
			for (final IXFunction f : this.errorInjector.getFunctions()) {
				valueCache.remove(f.getName());
				// System.out.println("#RI: cleared cache for " + f.getName());
			}
		}
	}

	public void dropValueCaches() {
		for (final String fid : sdaContext.getFunctionIds()) {
			valueCache.remove(fid);
		}
	}

	public void refreshTerms() {
		terms.clear();
		termIds.clear();

		if (!sdaContext.getCatalog().getSections().validation().ok()) {
			return;
		}

		for (final DictionarySection ds : sdaContext.getCatalog().getSections()) {
			for (final TermDefinition td : ds.getTerms()) {
				if (td.validation().ok()) {
					termIds.add(td.getId().content());
					terms.put(td.getId().content(), td);
				}
			}
		}

		// System.out.println("# term rebuild:" + terms);
		// processTerms();
	}

//	private void processTerms() {
//		ValidationContext validation = new ValidationContext();
//		CLBuilder builder = new CLBuilder(validation, this);
//		builder.processTerms();
//	}

	@Override
	public List<String> getTerms() {
		return termIds;
	}

	@Override
	public TermDefinition getTerm(String name) {
		return terms.get(name);
	}

	private void refresh() {
		makeDataTypingContext();
	}

	public TypingContext getDataContext() {
		return dataContext;
	}

	public TypingContext getModelContext() {
		return modelContext;
	}

	@Override
	public TypingContext getRootContext() {
		return modelContext;
	}

	public CLUserType resolveType(String name) {
		return getRootContext().resolveType(name);
	}

	private void makeDataTypingContext() {
		dataContext.clear();
		for (final String f : sdaContext.getEnums()) {
			final XEnumType gt = sdaContext.getEnum(f);
			final CLEnumType r = new CLEnumType(gt.getName(),
					sdaContext.getEnumMembers(gt).toArray(new String[sdaContext.getEnumMembers(gt).size()]));

			// System.out.println("###### Add type " + r.getName() + " " +
			// sdaContext.getEnumMembers(gt).size());

			dataContext.addType(r);
			for (final String s : sdaContext.getEnumMembers(gt)) {
				dataContext.addSymbol(s, r, SYMBOL_CLASS.ENUM_CONSTANT);
			}

			dataContext.addSymbol(f, new CLPowerType(r), SYMBOL_CLASS.CONSTANT);
		}

		for (final String f : sdaContext.getFunctionIds()) {
			final IXFunction xf = sdaContext.getFunction(f);
			if (xf.getFunctionType() != null) {
				final XType type = xf.getFunctionType();
				dataContext.addSymbol(f, SDAUtils.mapType(xf.getFunctionType(), this), SYMBOL_CLASS.MODELVARIABLE);
				if (type instanceof XRelationType) {
					final XRelationType rt = (XRelationType) type;
					dataContext.setRelationKind(f, rt.getKind());
				}
			}
		}
	}

	/**
	 * Sets cached value. This value sits on top of state context and overwrites
	 * value (if any) imported from data context. Use with care as has complex life
	 * span issue and interaction with the static builder.
	 */
	@Override
	public void setValue(String id, Object value) {
		valueCache.put(id, value);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized Object getValue(String id) throws CLExecutionException {
		if (valueCache.containsKey(id)) {
			return valueCache.get(id);
		} else if (sdaContext.getFunction(id) != null) {
			try {
				final XFunctionBasic f = (XFunctionBasic) sdaContext.getFunction(id);
				final BRel value = translateToSet(f);
				valueCache.put(id, value);
				return value;
			} catch (final InvalidSetOpException e) {
				e.printStackTrace();
				sdaContext.logError(e);
				return null;
			}
		} else if (sdaContext.getEnum(id) != null) {
			final List<Object> value = new ArrayList<>();
			try {
				final XEnumType t = sdaContext.getEnum(id);
				for (final String s : sdaContext.getEnumMembers(t)) {
					value.add(normalize(s));
				}
				final BSet set = new BSet(value);
				valueCache.put(id, set);
				return set;
			} catch (final InvalidSetOpException e) {
				e.printStackTrace();
				sdaContext.logError(e);
				return null;
			}
		} else if (dataContext.getType(id) instanceof CLUserType) {
			final Object result = new BProto((CLUserType) dataContext.getType(id), id);
			valueCache.put(id, result);
			return result;
		} else {
			return super.getValue(id);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private BRel translateToSet(XFunctionBasic f) throws InvalidSetOpException, CLExecutionException {
		if (f.getFunctionType() == null) {
			throw new InvalidSetOpException("Cannot translate untyped relation");
		}

		final XType myType = f.getFunctionType();

		if (myType instanceof XRelationType) {
			final XRelationType rtype = (XRelationType) myType;
			final RELATION_KIND kind = rtype.getKind();
			if (kind.isFunction()) {
				final List<BMap> raw = new ArrayList<>();
				for (final Object z : f.getMaps().keySet()) {
					final List<Object> zval0 = f.getMaps().get(z);
					final List<Object> zval = errorInjector.injectError(f, z);
//					if (!zval0.equals(zval))
//						System.out.println(" ---- EI " + z + " : " + zval0 + " to " + zval);
					assert zval.size() <= 1;
//					if (zval.isEmpty())
//						System.out.println(" ---- EI " + z + " : " + zval0 + " to " + zval);

					if (!zval.isEmpty()) {
						raw.add(new BMap(normalize(z), normalize(zval.get(0))));
					}

				}

				for (final Object z : errorInjector.getExtraDomain(f)) {
					final List<Object> zval = errorInjector.injectError(f, z);
//					System.out.println(" +++++ EI " + zval);
					assert zval.size() == 1;
					raw.add(new BMap(normalize(z), normalize(zval.get(0))));
				}

				return new BFun(raw);
			} else {
				final List<BMap> raw = new ArrayList<>();
				for (final Object a : f.getMaps().keySet()) {
					final List<Object> zval0 = f.getMaps().get(a);
					final List<Object> zval = errorInjector.injectError(f, a);
//					if (!zval0.equals(zval))
//						System.out.println(" ---- EI " + zval0 + " to " + zval);
//					if (zval.isEmpty())
//						System.out.println(" ---- EI " + a + " : " + zval0 + " to " + zval);

					for (final Object b : zval) {
						raw.add(new BMap(normalize(a), normalize(b)));
					}
				}

				for (final Object a : errorInjector.getExtraDomain(f)) {
					final List<Object> zval = errorInjector.injectError(f, a);
					// System.out.println(" +++++ EI " + zval);
					for (final Object b : zval) {
						raw.add(new BMap(normalize(a), normalize(b)));
					}
				}

				return new BRel(raw);
			}
		}

		throw new InvalidSetOpException("Cannot translate ill-typed relation");

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object normalize(Object value) throws CLExecutionException, InvalidSetOpException {
		if (valueCache.containsKey(value)) {
			return valueCache.get(value);
		} else if (value instanceof Integer || value instanceof Boolean) {
			return value;
		} else if (value == null) {
			throw new CLExecutionException("Null value in valuation");
		} else if (value instanceof ValueList) {
			final ValueList vlist = (ValueList) value;
			final CLType ctype = SDAUtils.mapType(vlist.getType(), this);
			if (ctype == null) {
				throw new CLExecutionException("Invalid constant for value list " + vlist);
			}

			if (ctype instanceof CLSeqType) {
				final List<Object> seq = new ArrayList<>();
				for (final Object v : vlist.getValues()) {
					seq.add(normalize(v));
				}
				return BSeq.make(seq);
			} else if (ctype instanceof CLPowerType) {
				final List<Object> set = new ArrayList<>();
				for (final Object v : vlist.getValues()) {
					set.add(normalize(v));
				}
				return new BSet(set);
			} else if (ctype instanceof CLProductType) {
				Object result = normalize(vlist.get(0));
				for (int i = 1; i < vlist.size(); i++) {
					result = new BMap(result, normalize(vlist.get(i)));
				}
				return result;
			} else {
				throw new CLExecutionException("Unsupported value list type " + vlist.getType().toString());
			}

		} else if (value instanceof LocatedValue) {
			final LocatedValue s = (LocatedValue) value;
			final CLType t = dataContext.getType(s.toString());
			if (t instanceof CLUserType) {
				final Object result = new BProto((CLUserType) t, s.toString());
				valueCache.put(value, result);
				return result;
			} else if (t == null) {
				throw new CLExecutionException("Untyped literal " + s);
			} else {
				throw new CLExecutionException("Literal " + s + " has type " + t.toString() + " but must be of a given type.");
			}
		} else if (value instanceof String) {
			final String s = (String) value;
			final CLType t = dataContext.getType(s);
			if (t instanceof CLUserType) {
				final Object result = new BProto((CLUserType) t, s);
				valueCache.put(value, result);
				return result;
			} else if (t == null) {
				throw new CLExecutionException("Untyped literal " + s);
			} else {
				throw new CLExecutionException("Literal " + s + " has type " + t.toString() + " but must be of a given type.");
			}
		} else {
			throw new CLExecutionException("Invalid literal " + value + " (raw type " + value.getClass().getSimpleName() + ")");
		}
	}

}