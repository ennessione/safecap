package uk.ac.ncl.safecap.xmldata.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.safecap.xmldata.IConceptMap;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;

public class SDAAggregatingProvider implements ISDADataProvider {
	private final Collection<ISDADataProvider> providers;
	private final Set<String> functionIdCache;
	private final Set<String> typeIdCache;
	private final Map<String, ISDADataProvider> functionOrigin;
	private final Map<String, ISDADataProvider> typeOrigin;
	private final Map<XEnumType, Set<String>> enumMembers;

	public SDAAggregatingProvider() {
		providers = new ArrayList<>();
		functionIdCache = new HashSet<>();
		typeIdCache = new HashSet<>();
		enumMembers = new HashMap<>();

		functionOrigin = new HashMap<>();
		typeOrigin = new HashMap<>();
	}

	public void insertSource(ISDADataProvider source, boolean override) {
		synchronized (this) {
			if (providers.contains(source)) {
				return;
			}

			if (override) {
				((List<ISDADataProvider>) providers).add(0, source);
			} else {
				providers.add(source);
			}

			extendTypes(source);
			extendCaches(source);
		}
	}

	public Collection<ISDADataProvider> getProviders() {
		return providers;
	}

	public ISDADataProvider getFunctionOrigin(String function) {
		return functionOrigin.get(function);
	}

	public ISDADataProvider getTypeOrigin(String type) {
		return typeOrigin.get(type);
	}

	private void extendTypes(ISDADataProvider source) {
		for (final String et : source.getEnums()) {
			final XEnumType t = source.getEnum(et);
			if (enumMembers.containsKey(t)) { // might be dangerous!
				final Set<String> values = enumMembers.get(t);
				values.addAll(source.getEnumMembers(t));
			} else {
				final Set<String> values = source.getEnumMembers(t);
				assert values != null;
				enumMembers.put(t, source.getEnumMembers(et));
			}
		}
	}

	public void deleteSource(ISDADataProvider source) {
		synchronized (this) {
			if (!providers.contains(source)) {
				return;
			}

			providers.remove(source);

			rebuiltCaches();
		}
	}

	private void extendCaches(ISDADataProvider source) {
		for (final String s : source.getFunctionIds()) {
			functionIdCache.add(s);
			functionOrigin.put(s, source);
		}

		for (final String s : source.getEnums()) {
			typeIdCache.add(s);
			typeOrigin.put(s, source);
		}
	}

	private void rebuiltCaches() {
		functionIdCache.clear();
		typeIdCache.clear();
		for (final ISDADataProvider p : providers) {
			extendCaches(p);
		}
	}

	@Override
	public Set<String> getFunctionIds() {
		return functionIdCache;
	}

	@Override
	public IXFunction getFunction(String name) {
		for (final ISDADataProvider p : providers) {
			final IXFunction f = p.getFunction(name);
			if (f != null) {
				return f;
			}
		}
		return null;
	}

	@Override
	public XEnumType getEnum(String id) {
		for (final ISDADataProvider p : providers) {
			final XEnumType t = p.getEnum(id);
			if (t != null) {
				return t;
			}
		}
		return null;
	}

	@Override
	public Set<String> getEnums() {
		return typeIdCache;
	}

	@Override
	public Set<String> getEnumMembers(XEnumType type) {
		return enumMembers.get(type);
	}

	@Override
	public Set<String> getEnumMembers(String type) {
		return enumMembers.get(getEnum(type));
	}

	@Override
	public String getName() {
		return "aggregator";
	}

	@Override
	public String getConceptMapSource() {
		for (final ISDADataProvider p : providers) {
			if (p.getConceptMapSource() != null) {
				return p.getConceptMapSource();
			}
		}

		return null;
	}

	@Override
	public IConceptMap getConceptMap() {
		for (final ISDADataProvider p : providers) {
			if (p.getConceptMap() != null) {
				return p.getConceptMap();
			}
		}
		return null;
	}

	@Override
	public Collection<String> getExternalMap(String element) {
		for (final ISDADataProvider p : providers) {
			if (!p.getExternalMap(element).isEmpty()) {
				return p.getExternalMap(element);
			}
		}
		return Collections.emptySet();
	}

	@Override
	public Collection<String> getExternalFileLocations() {
		final Collection<String> result = new HashSet<>();
		for (final ISDADataProvider p : providers) {
			if (p.getExternalFileLocations() != null) {
				result.addAll(p.getExternalFileLocations());
			}
		}

		return result;
	}

}
