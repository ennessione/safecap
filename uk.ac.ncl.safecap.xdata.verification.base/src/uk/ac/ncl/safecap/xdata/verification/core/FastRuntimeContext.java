package uk.ac.ncl.safecap.xdata.verification.core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import gnu.jel.CompiledExpression;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.mb.ModelBuilderCompiled;
import uk.ac.ncl.prime.sim.lang.mb.ModelSolverCompiled;
import uk.ac.ncl.prime.sim.lang.typing.CLProductType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeReal;
import uk.ac.ncl.prime.sim.lang.typing.CLUserType;
import uk.ac.ncl.prime.sim.sets.BMap;
import uk.ac.ncl.prime.sim.sets.BSeq;
import uk.ac.ncl.prime.sim.sets.BSet;

/**
 * This class exposes all values entities as a flat array of objects (primitive
 * types must be boxed). This enables expression compilation with JEL library
 * without incurring any penalty for value access.
 * 
 * There is caching of values that must be invalidated elsewhere.
 *
 */
public class FastRuntimeContext {
	private static final int MAX_COMPILATION_CACHE = 100;
	private final SDARuntimeExecutionContext parent;
	private final Object[] context;

	private final List<String> setNames;
	private final List<String> seqNames;
	private final List<String> mapNames;
	private final List<String> intNames;
	private final List<String> dblNames;
	private final List<String> objNames;

	private final boolean buildMode = true;
	private final boolean fullMode = false;

	private transient Map<CLExpression, CompiledExpression> lruCompilationCache;

	// expose these fields to compiled expressions
	@SuppressWarnings("rawtypes")
	public BSet[] sets; // set values
	@SuppressWarnings("rawtypes")
	public BSeq[] seqs;
	@SuppressWarnings("rawtypes")
	public BMap[] maps;
	public Integer[] ints;
	public Double[] dbls;
	public Object[] objs;

	public FastRuntimeContext(SDARuntimeExecutionContext parent) {
		this.parent = parent;
		context = new Object[] { this };
		setNames = new ArrayList<>();
		seqNames = new ArrayList<>();
		mapNames = new ArrayList<>();
		intNames = new ArrayList<>();
		dblNames = new ArrayList<>();
		objNames = new ArrayList<>();
		lruCompilationCache = CLUtils.lruCache(MAX_COMPILATION_CACHE);
	}

	public void clear() {
		setNames.clear();
		seqNames.clear();
		mapNames.clear();
		intNames.clear();
		dblNames.clear();
		objNames.clear();
		lruCompilationCache.clear();
	}

	public CompiledExpression getCompiledExpression(CLExpression expression) {
		return lruCompilationCache.get(expression);
	}

	public void storeCompiledExpression(CLExpression expression, CompiledExpression compiled) {
		lruCompilationCache.put(expression, compiled);
	}

	public Object[] getContext() {
		return context;
	}

	public void buildAll() throws CLExecutionException {
		prepare(parent.getSdaContext().getFunctionIds());
		prepare(parent.getSdaContext().getEnums());

		for (final CLUserType ut : parent.getDataContext().getGivenTypes()) {
			add(ut.getName());
		}

		walkStateContexts(parent);
	}

	private void walkStateContexts(SDARuntimeExecutionContext ctx) throws CLExecutionException {
		for (final String s : ctx.getStateContext().getIdentifiers()) {
			add(s);
		}

		final SDARuntimeExecutionContext pctx = ctx.getParent();
		if (pctx != null) {
			walkStateContexts(pctx);
		}

	}

	public void prepare(Collection<String> names) throws CLExecutionException {
		if (fullMode) {
			return;
		} else if (buildMode) {
			for (final String s : names) {
				add(s);
			}
		} else {
			buildAll();
		}
	}

	@SuppressWarnings("rawtypes")
	private void add(String s, CLType type, Object value) {
		assert type != null;

		if (value == null) {
			System.out.println("Empty value?");
		}

		if (type.isSet()) {
			if (s != null && setNames.contains(s)) {
				sets[setNames.indexOf(s)] = (BSet) value;
			} else {
				setNames.add(s);
				sets = resize(sets, setNames.size(), BSet.class);
				sets[setNames.size() - 1] = (BSet) value;
			}
		} else if (type.isSequence()) {
			if (s != null && seqNames.contains(s)) {
				seqs[seqNames.indexOf(s)] = (BSeq) value;
			} else {
				seqNames.add(s);
				seqs = resize(seqs, seqNames.size(), BSeq.class);
				seqs[seqNames.size() - 1] = (BSeq) value;
			}
		} else if (type instanceof CLProductType) {
			if (s != null && mapNames.contains(s)) {
				maps[mapNames.indexOf(s)] = (BMap) value;
			} else {
				mapNames.add(s);
				maps = resize(maps, mapNames.size(), BMap.class);
				maps[mapNames.size() - 1] = (BMap) value;
			}
		} else if (type == CLTypeInteger.INSTANCE) {
			if (s != null && intNames.contains(s)) {
				ints[intNames.indexOf(s)] = (Integer) value;
			} else {
				intNames.add(s);
				ints = resize(ints, intNames.size(), Integer.class);
				ints[intNames.size() - 1] = (Integer) value;
			}
		} else if (type == CLTypeReal.INSTANCE) {
			if (s != null && dblNames.contains(s)) {
				dbls[dblNames.indexOf(s)] = (Double) value;
			} else {
				dblNames.add(s);
				dbls = resize(dbls, dblNames.size(), Double.class);
				dbls[dblNames.size() - 1] = (Double) value;
			}
		} else if (type instanceof CLUserType) {
			if (s != null && objNames.contains(s)) {
				objs[objNames.indexOf(s)] = value;
			} else {
				objNames.add(s);
				objs = resize(objs, objNames.size(), Object.class);
				objs[objNames.size() - 1] = value;
			}
		} else {
			assert false;
		}
	}

	private void add(String s) throws CLExecutionException {
		final CLType type = parent.getRootContext().getType(s);
		if (type == null) {
			return;
		}
		final Object value = parent.getValue(s);
		add(s, type, value);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> T[] resize(T[] array, int size, Class clazz) {
		final int sze = (size + 99) / 100 * 100;

		if (array == null) {
			return (T[]) Array.newInstance(clazz, sze);
		}

		if (array.length < size) {
			return Arrays.copyOf(array, sze);
		} else {
			return array;
		}
	}

	public String getCompiledName(String name) {
		final CLType type = parent.getRootContext().getType(name);
		assert type != null;
		if (type.isSet()) {
			final int index = setNames.indexOf(name);
			assert index >= 0;
			return "sets[" + index + "]";
		} else if (type.isSequence()) {
			final int index = seqNames.indexOf(name);
			assert index >= 0;
			return "seqs[" + index + "]";
		} else if (type instanceof CLProductType) {
			final int index = mapNames.indexOf(name);
			assert index >= 0;
			return "maps[" + index + "]";
		} else if (type == CLTypeInteger.INSTANCE) {
			final int index = intNames.indexOf(name);
			assert index >= 0;
			return "ints[" + index + "]";
		} else if (type == CLTypeReal.INSTANCE) {
			final int index = dblNames.indexOf(name);
			assert index >= 0;
			return "dbls[" + index + "]";
		} else if (type instanceof CLUserType) {
			final int index = objNames.indexOf(name);
			assert index >= 0;
			return "objs[" + index + "]";
		} else {
			assert false;
			return null;
		}
	}

	/**
	 * Insert new anonymous value
	 * 
	 * @param type  CLType of the value
	 * @param value raw object value
	 * @return compiled JEL string
	 * @throws CLExecutionException
	 */
	public synchronized String insert(CLType type, Object value) {
		add(null, type, value);
		if (type.isSet()) {
			return "sets[" + (setNames.size() - 1) + "]";
		} else if (type.isSequence()) {
			return "seqs[" + (seqNames.size() - 1) + "]";
		} else if (type instanceof CLProductType) {
			return "maps[" + (mapNames.size() - 1) + "]";
		} else if (type == CLTypeInteger.INSTANCE) {
			return "ints[" + (intNames.size() - 1) + "]";
		} else if (type == CLTypeReal.INSTANCE) {
			return "dbls[" + (dblNames.size() - 1) + "]";
		} else if (type instanceof CLUserType) {
			return "objs[" + (objNames.size() - 1) + "]";
		} else {
			assert false;
			return null;
		}
	}

	public String insertFastRuntimeContextObject(ModelBuilderCompiled mbc) {
		objNames.add(null);
		objs = resize(objs, objNames.size(), Object.class);
		objs[objNames.size() - 1] = mbc;
		return "setcomprehension(objs[" + (objNames.size() - 1) + "])";
	}

	public String insertFastRuntimeContextObject(ModelSolverCompiled mbc) {
		objNames.add(null);
		objs = resize(objs, objNames.size(), Object.class);
		objs[objNames.size() - 1] = mbc;
		return "quantifier(objs[" + (objNames.size() - 1) + "])";
	}
}
