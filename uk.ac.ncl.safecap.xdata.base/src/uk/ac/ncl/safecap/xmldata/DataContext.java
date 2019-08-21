package uk.ac.ncl.safecap.xmldata;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import uk.ac.ncl.safecap.common.IUniqueNameChecker;
import uk.ac.ncl.safecap.common.SimpleTree;
import uk.ac.ncl.safecap.common.SimpleTreeException;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ConceptMapFactory;
import uk.ac.ncl.safecap.xmldata.base.ISDADataProvider;
import uk.ac.ncl.safecap.xmldata.normalisation.Normalisation;
import uk.ac.ncl.safecap.xmldata.normalisation.NormalisationNaive;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;
import uk.ac.ncl.safecap.xmldata.types.XType;

@XmlRootElement
public class DataContext implements IFunctionBuilder, ISDADataProvider, Comparator<IXFunction>, IUniqueNameChecker {
	private TypeRegistry typeRegistry;
	private List<DataNamespace> namespaces;
	private final Map<String, String> functionInfo;
	private Map<String, String> shortName;
	private Collection<TypeDecomposition> typeDecomposition;
	private Collection<String> extraFunctions;
	private Collection<String> deletedFunctions;
	private Map<String, String> functionDomainType;
	private Map<String, String> functionRangeType;
	private Map<String, String> functionTag;
	private Collection<String> externalFileLocations;
	private String sourceFile;
	private String name;
	private String conceptMapSource;
	private String persistentContract;

	private Collection<StringPair> elementExternalMap;

	private transient Collection<Pattern> deletedFunctionPatterns;
	private transient DataContract conf;
	private transient Map<String, IXFunction> functions;
	private transient IConceptMap conceptMap;

	public DataContext() {
		typeRegistry = new TypeRegistry();
		namespaces = new ArrayList<>();
		functions = new HashMap<>();
		functionInfo = new HashMap<>();
		typeDecomposition = new HashSet<>();
		deletedFunctions = new HashSet<>();
		deletedFunctionPatterns = new HashSet<>();
		functionDomainType = new HashMap<>();
		functionRangeType = new HashMap<>();
		functionTag = new HashMap<>();
		shortName = new HashMap<>();
		externalFileLocations = new HashSet<>();
	}

	@XmlElement(name = "contract")
	public String getPersistentContract() {
		return persistentContract;
	}

	public void setPersistentContract(String persistentContract) {
		this.persistentContract = persistentContract;
	}

	public void useFunctionAsExternalMap(IXFunction f, IStringRewriter domainRewriter, IStringRewriter rangeRewriter) {
		if (elementExternalMap == null) {
			elementExternalMap = new ArrayList<>();
		}

		for (final Object z : f.domain()) {
			final String key = z.toString();
			for (final Object q : f.get(z)) {
				addExternalMap(domainRewriter.rewrite(key), rangeRewriter.rewrite(q.toString()));
			}
		}
	}

	private void addExternalMap(String z, String string) {
		elementExternalMap.add(new StringPair(z, string));
	}

	@Override
	public Collection<String> getExternalMap(String element) {
		if (elementExternalMap != null) {
			String element2 = element;
			if (element.startsWith("\"") && element.endsWith("\"")) {
				element2 = element.substring(1, element.length() - 1);
			}
			final Collection<String> result = new ArrayList<>();
			for (final StringPair p : elementExternalMap) {
				if (p.getKey().equals(element) || p.getKey().equals(element2)) {
					result.add(p.getValue());
				}

			}
			return result;
		} else {
			return Collections.emptySet();
		}
	}

	@XmlElement(name = "extmap")
	public Collection<StringPair> getElementExternalMap() {
		return elementExternalMap;
	}

	public void setElementExternalMap(Collection<StringPair> elementExternalMap) {
		this.elementExternalMap = elementExternalMap;
	}

	public Normalisation getNormalisation() {
		return NormalisationNaive.INSTANCE;
	}

	@Override
	@XmlElement(name = "extfileloc")
	public Collection<String> getExternalFileLocations() {
		return externalFileLocations;
	}

	public void setExternalFileLocations(Collection<String> externalFileLocations) {
		this.externalFileLocations = externalFileLocations;
	}

	@XmlElement(name = "ftag")
	public Map<String, String> getFunctionTag() {
		return functionTag;
	}

	public void setFunctionTag(Map<String, String> functionTag) {
		this.functionTag = functionTag;
	}

	@Override
	@XmlElement(name = "conceptsource")
	public String getConceptMapSource() {
		return conceptMapSource;
	}

	public void setConceptMapSource(String conceptMapSource) {
		this.conceptMapSource = conceptMapSource;
	}

	@Override
	public IConceptMap getConceptMap() {
		if (conceptMap == null && conceptMapSource != null) {
			conceptMap = ConceptMapFactory.make(conceptMapSource);
		}
		return conceptMap;
	}

	@XmlElement(name = "file")
	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	@XmlElement(name = "shortname")
	public Map<String, String> getShortName() {
		return shortName;
	}

	public void setShortName(Map<String, String> shortName) {
		this.shortName = shortName;
	}

	public synchronized void renameFunction(String oldf, String newf, IXFunction f, boolean contract) {
		if (!contract && shortName.containsKey(f.getCanonicalName())) {
			newf = shortName.get(f.getCanonicalName());
		}

		final IXFunction ff = functions.get(oldf);
		if (ff != null) {
			final String info = functionInfo.get(oldf);
			if (info != null) {
				functionInfo.remove(oldf);
				functionInfo.put(newf, info);
			}

			f.setName(newf);
			functions.remove(oldf);
			functions.put(newf, f);
			shortName.put(f.getCanonicalName(), newf);

		}
	}

	public void setContract(DataContract contract) {
		conf = contract;
	}

	@XmlElement(name = "deletedfunctions")
	public Collection<String> getDeletedFunctions() {
		return deletedFunctions;
	}

	public void setDeletedFunctions(Collection<String> deletedFunctions) {
		this.deletedFunctions = deletedFunctions;
		deletedFunctionPatterns.clear();
		for (final String pattern : deletedFunctions) {
			addPattern(pattern);
		}
	}

	public void addDeletePattern(String pattern) {
		deletedFunctions.add(pattern);
		addPattern(pattern);
	}

	private void addPattern(String pattern) {
		try {
			final Pattern p = Pattern.compile(pattern);
			deletedFunctionPatterns.add(p);
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	@XmlElement(name = "extrafunctions")
	public Collection<String> getCollectedFunctions() {
		return extraFunctions;
	}

	public void setCollectedFunctions(Collection<String> collectedFunctions) {
		extraFunctions = collectedFunctions;
		// System.out.println("Set extra functions to " +
		// collectedFunctions.size());
	}

	public void addCollectedFunctions(Collection<String> collectedFunctions) {
		if (extraFunctions == null) {
			extraFunctions = new HashSet<>();
		}

		extraFunctions.addAll(collectedFunctions);
		// System.out.println("Set extra functions to " +
		// collectedFunctions.size());
	}

	public void addEnum(String name, String[] members) {
		for (final String m : members) {
			typeRegistry.setType(m, name);
		}
	}

	public void addTypeDecomposition(TypeDecomposition decomposition) {
		typeDecomposition.add(decomposition);
	}

	public Collection<TypeDecomposition> getTypeDecompositionsFor(String type) {
		final Collection<TypeDecomposition> result = new HashSet<>();

		for (final TypeDecomposition td : typeDecomposition) {
			if (td.getType().equals(type)) {
				result.add(td);
			}
		}

		return result;
	}

	public TypeDecomposition getTypeDecompositions(String type, String name) {
		for (final TypeDecomposition td : typeDecomposition) {
			if (td.getType().equals(type) && td.getName().equals(name)) {
				return td;
			}
		}

		return null;
	}

	@XmlElement
	public Map<String, String> getFunctionDomainType() {
		return functionDomainType;
	}

	public void setFunctionDomainType(Map<String, String> map) {
		functionDomainType = map;
	}

	@XmlElement
	public Map<String, String> getFunctionRangeType() {
		return functionRangeType;
	}

	public void setFunctionRangeType(Map<String, String> map) {
		functionRangeType = map;
	}

	@XmlElement(name = "typedecomposition")
	public Collection<TypeDecomposition> getTypeDecomposition() {
		return typeDecomposition;
	}

	public void setTypeDecomposition(Collection<TypeDecomposition> typeDecomposition) {
		this.typeDecomposition = typeDecomposition;
	}

	@Override
	public Set<String> getEnumMembers(XEnumType type) {
		return typeRegistry.getEnumMembers(type);
	}

	@Override
	public Set<String> getEnumMembers(String type) {
		return typeRegistry.getEnumMembers(typeRegistry.getEnum(type));
	}

	public void build() throws DataConfigurationException {
		// System.out.println("[CTX] Start");

		typeRegistry.addNormaliser(getNormalisation());
		if (conf == null) {
			if (persistentContract != null) {
				DataContract contract = null;
				try {
					final StringReader reader = new StringReader(persistentContract);
					contract = DataUtils.readContract(reader, null);
				} catch (final Throwable e) {
					// System.out.println("[CTX] Loading stored contract failed: " +
					// e.getMessage());
				}

				if (contract != null) {
					// System.out.println("[CTX] Loaded stored contract");
					conf = contract;
					typeRegistry.clear();
					buildFunctionsWithContract(false);
					// System.out.println("[CTX] Done");
					return;
				}
			}
			// System.out.println("[CTX] No contract");
			buildFunctionsWithoutContract();
		} else {
			// System.out.println("[CTX] Using live contract");
			typeRegistry.clear();
			buildFunctionsWithContract(false);
		}

		// System.out.println("[CTX] Done");
	}

	public void buildContractForced() throws DataConfigurationException {
		// System.out.println("DATA BUILD");

		if (conf != null) {
			typeRegistry.clear();
			buildFunctionsWithContract(true);
		}
	}

	public void setDomainTypeInfo(IXFunction f, String type) throws DataConfigurationException {
		TypeCasting.castFunctionDomain((XFunctionBasic) f, type, typeRegistry);
		functionDomainType.put(f.getCanonicalName(), type);
	}

	public void setRangeTypeInfo(IXFunction f, String type) throws DataConfigurationException {
		TypeCasting.castFunctionRange((XFunctionBasic) f, type, typeRegistry);
		functionRangeType.put(f.getCanonicalName(), type);
	}

	public void setDomainTypeInfo(IXFunction f, XType type) throws DataConfigurationException {
		TypeCasting.castFunctionDomain((XFunctionBasic) f, type, typeRegistry);
		functionDomainType.put(f.getCanonicalName(), type.toString());
	}

	public void setFunctionTag(IXFunction f, String tag) throws DataConfigurationException {
		((XFunctionBasic) f).setTag(tag);
		functionTag.put(f.getCanonicalName(), tag);
	}

	public void setRangeTypeInfo(IXFunction f, XType type) throws DataConfigurationException {
		TypeCasting.castFunctionRange((XFunctionBasic) f, type, typeRegistry);
		functionRangeType.put(f.getCanonicalName(), type.toString());
	}

	public void computeTypes() {
		for (final IXFunction f : getFunctions()) {
			f.computeType();
		}
	}

	public void computeTypeDecompositions() throws DataConfigurationException {
		if (conf == null) {
			// build type decomposition functions
			for (final TypeDecomposition x : typeDecomposition) {
				buildtypeDecomposition(x);
			}
		} else {
			conf.enactTypeDec(this);
			// build type decomposition functions
			for (final TypeDecomposition x : typeDecomposition) {
				buildtypeDecomposition(x);
			}
		}
	}

	private synchronized void buildFunctionsWithContract(boolean force) throws DataConfigurationException {
		// System.out.println("## build: with contract");

		// prepare
		functions.clear();
		conf.setForce(force);
		conf.prepare(this);

		final Map<String, String> namemap = new HashMap<>(functions.size());

		conf.enactNamespaces(this);
		conf.enactEnums(this);

		// build main functions
		for (final DataNamespace nmsp : namespaces) {
			nmsp.buildFunctions(this);
		}

		// clean up names
		compactNames();

		conf.enactElements(this, namemap);

		conf.enactExternalMaps(this);

		name = conf.getName();
		if (conf.getConceptMap() != null) {
			setConceptMapSource(conf.getConceptMap());
		}

		conf.enactTypeDec(this);

		// build type decomposition functions
		for (final TypeDecomposition x : typeDecomposition) {
			buildtypeDecomposition(x);
		}

		conf.enactDecompositionElements(this, namemap);

		for (final String s : namemap.keySet()) {
			renameFunction(s, namemap.get(s), functions.get(s), true);
		}

		conf.enacSplitElements(this);

//		for (IXFunction f : getFunctions())
//			f.computeType();
//
//		for (IXFunction f : getFunctions())
//			f.inferType();

		for (final IXFunction f : getFunctions()) {
			f.computeType();
		}
	}

	private void buildFunctionsWithoutContract() {

		// prepare
		functions.clear();

		// build type decomposition functions
		for (final TypeDecomposition x : typeDecomposition) {
			buildtypeDecomposition(x);
		}

		// build main functions
		for (final DataNamespace nmsp : namespaces) {
			nmsp.buildFunctions(this);
		}

		// filter out deleted functions
		final Collection<String> toDelete = new HashSet<>();
		for (final String f : functions.keySet()) {
			for (final Pattern pattern : deletedFunctionPatterns) {
				if (pattern.matcher(f).matches()) {
					toDelete.add(f);
				}
			}
		}

		if (extraFunctions != null) {
			for (final String f : extraFunctions) {
				for (final Pattern pattern : deletedFunctionPatterns) {
					if (pattern.matcher(f).matches()) {
						toDelete.add(f);
					}
				}
			}
		}

		for (final String df : toDelete) {
			functions.remove(df);
			if (extraFunctions != null) {
				extraFunctions.remove(df);
			}
		}

		// filter out extra functions
		if (extraFunctions != null) {
			for (final String f : functions.keySet()) {
				extraFunctions.remove(f);
			}
		}

		// System.out.println("Extra functions: " + extraFunctions);
		// emit extra functions
		if (extraFunctions != null) {
			for (final String name : extraFunctions) {
				functions.put(name, new XFunctionBasic(this, name));
			}
		}

		// clean up names
		compactNames();

		// use type casting information
		for (final IXFunction ff : functions.values()) {
			final XFunctionBasic f = (XFunctionBasic) ff;
			try {
				final String domainType = functionDomainType.get(f.getCanonicalName());
				if (domainType != null) {
					TypeCasting.castFunctionDomain(f, domainType, typeRegistry);
				}

				final String rangeType = functionRangeType.get(f.getCanonicalName());
				if (rangeType != null) {
					TypeCasting.castFunctionRange(f, rangeType, typeRegistry);
				}

				final String tag = functionTag.get(f.getCanonicalName());
				if (tag != null) {
					f.setTag(tag);
				}
			} catch (final DataConfigurationException e) {
				e.printStackTrace();
			}

		}

		// type inference
		for (final IXFunction f : getFunctions()) {
			f.computeType();
		}

	}

	private void compactNames() {
		final List<IXFunction> functions_copy = new ArrayList<>(functions.size());
		functions_copy.addAll(functions.values());
		// functions_copy.sort(this);
		Collections.sort(functions_copy, this);
		try {
			final SimpleTree tree = new SimpleTree("Root");
			for (final IXFunction f : functions_copy) {
				if (!f.isDerived()) {
					tree.addPath(f.getCanonicalName());
				}
			}

			for (final IXFunction f : functions_copy) {

				if (!f.isDerived()) {
					// String canonical = f.getCanonicalName();
					// if (f.getCanonicalName().equals(f.getName()))
					// canonical = "/" + f.getName().replace('.', '/');

					// if (shortName.containsKey(canonical)) {
					// String new_name = shortName.get(canonical);
					// condenseFunctionName(f, new_name);
					// } else {
					final String new_name = tree.compactPath(f.getCanonicalName(), this);
					condenseFunctionName(f, new_name);
					// }
				}
			}
		} catch (final SimpleTreeException e) {
			e.printStackTrace();
		}
	}

	public void typeInference() {
		// type
		for (final IXFunction f : getFunctions()) {
			f.inferType();
		}
	}

	private void buildtypeDecomposition(TypeDecomposition x) {
		for (final Entry<String, String> e : x.getMapping().entrySet()) {
			addMapping(x.getType(), x.getType(), x.getName(), e.getKey(), e.getValue());
		}

		IXFunction function = getFunction(x.getType() + "." + x.getName());
		if (function == null) { // add empty function
			function = new XFunctionBasic(this, x.getType() + "." + x.getName());
			functions.put(x.getType() + "." + x.getName(), function);
		}

		x.setFunction(function);
		function.setDerived(true);
	}

	private void condenseFunctionName(IXFunction f, String new_name) {
		if (f.getCanonicalName().equals(f.getName())) {
			f.setCanonicalName("/" + f.getName().replace('.', '/'));
		}
		renameFunction(f.getName(), new_name, f, false);
	}

	@Override
	public Set<String> getFunctionIds() {
		return functions.keySet();
	}

	public Collection<IXFunction> getFunctions() {
		return functions.values();
	}

	@Override
	public IXFunction getFunction(String name) {
		return functions.get(name);
	}

	public IXFunction getFunctionByCanonical(String name) {
		for (final IXFunction f : functions.values()) {
			if (f.getCanonicalName().equals(name)) {
				return f;
			}
		}
		return null;
	}

	@Override
	public void addMapping(String canonical_namespace, String namespace, String attribute, Object block, Object value) {
		final String canonical_name = canonical_namespace + "." + attribute;
		IXFunction f = functions.get(canonical_name);
		if (f == null) {
			f = new XFunctionBasic(this, canonical_name);
			functions.put(canonical_name, f);
		}

		f.add(block, value);
	}

	public void addEmptyFunction(String canonical_name, String short_name, XType domain, XType range, String tag) {

		final XFunctionBasic f = new XFunctionBasic(this, short_name, domain, range);
		f.setTag(tag);
		functions.put(canonical_name, f);
		shortName.put(canonical_name, short_name);
		functionDomainType.put(canonical_name, domain.toString());
		functionRangeType.put(canonical_name, range.toString());
	}

	public void addNamespace(DataNamespace nmsp) {
		namespaces.add(nmsp);
	}

	@XmlElement(name = "info")
	public Map<String, String> getFunctionInfo() {
		return functionInfo;
	}

	@XmlElement(name = "namespaces")
	public List<DataNamespace> getNamespaces() {
		return namespaces;
	}

	public void setNamespaces(List<DataNamespace> namespaces) {
		this.namespaces = namespaces;
	}

	@XmlElement(name = "types")
	public TypeRegistry getTypeRegistry() {
		return typeRegistry;
	}

	public void setTypeRegistry(TypeRegistry typeRegistry) {
		this.typeRegistry = typeRegistry;
	}

	public void dropTypes() {
		typeRegistry.clear();
		functionDomainType.clear();
		functionRangeType.clear();
		typeDecomposition.clear();
		if (elementExternalMap != null) {
			elementExternalMap.clear();
		}
		externalFileLocations.clear();
		persistentContract = null;
		conf = null;
	}

	public void dropData() {
		deletedFunctionPatterns.clear();

		deletedFunctions.clear();

		if (extraFunctions != null) {
			extraFunctions.clear();
		}

		shortName.clear();
		functionInfo.clear();
		functionTag.clear();
		functions.clear();
		namespaces.clear();
	}

	public DataNamespace getNamespaceById(String id) {
		for (final DataNamespace nmsp : namespaces) {
			if (nmsp.getId().equals(id)) {
				return nmsp;
			}
		}
		return null;
	}

	public DataNamespace getNamespaceByCanonicalId(String id) {
		for (final DataNamespace nmsp : namespaces) {
			if (nmsp.getCononical_id().equals(id)) {
				return nmsp;
			}
		}
		return null;
	}

	public String completeName(String text) {
		final List<String> candidates = new ArrayList<>();
		for (final String fn : functions.keySet()) {
			if (fn.startsWith(text)) {
				candidates.add(fn);
			}
		}

		for (final String fn : getTypeRegistry().getEnums()) {
			if (fn.startsWith(text)) {
				candidates.add(fn);
			}
		}

		if (candidates.isEmpty()) {
			return text;
		}

		final String result = longestCommonPrefix(candidates.toArray(new String[candidates.size()]));
		return result;
	}

	private static String longestCommonPrefix(String[] strings) {
		if (strings.length == 0) {
			return ""; // Or maybe return null?
		}

		for (int prefixLen = 0; prefixLen < strings[0].length(); prefixLen++) {
			final char c = strings[0].charAt(prefixLen);
			for (int i = 1; i < strings.length; i++) {
				if (prefixLen >= strings[i].length() || strings[i].charAt(prefixLen) != c) {
					// Mismatch found
					return strings[i].substring(0, prefixLen);
				}
			}
		}
		return strings[0];
	}

	public void injectData(DataContext other) throws DataException, DataConfigurationException {
		// first check and add types on a copy of a type registry
		final TypeRegistry typeRegistry_new = new TypeRegistry();
		typeRegistry_new.addUnsafe(typeRegistry);
		typeRegistry_new.inject(other.getTypeRegistry());

		final List<DataNamespace> namespaces_old = new ArrayList<>();
		for (final DataNamespace nmsp : namespaces) {
			namespaces_old.add((DataNamespace) nmsp.clone());
		}

		try {
			// process namespaces
			for (final DataNamespace nmsp : other.getNamespaces()) {
				final DataNamespace my_nmsp = getNamespaceById(nmsp.getId());
				if (my_nmsp != null) {
					// insert new blocks
					my_nmsp.inject(nmsp);
				} else {
					namespaces.add(nmsp);
				}
			}

			typeRegistry = typeRegistry_new;
		} catch (final Throwable e) {
			namespaces = namespaces_old;
			throw new DataException("Injection failed: " + e.getMessage());
		}

		if (other.getCollectedFunctions() != null) {
			addCollectedFunctions(other.getCollectedFunctions());
		}

		if (sourceFile == null) {
			sourceFile = other.sourceFile;
		}
	}

	@Override
	public XEnumType getEnum(String id) {
		return typeRegistry.getEnum(id);
	}

	@Override
	public Set<String> getEnums() {
		return typeRegistry.getEnums();
	}

	@Override
	public int compare(IXFunction o1, IXFunction o2) {
		return o1.getName().compareTo(o2.getName());
	}

	@Override
	public boolean isUnique(String canonical, String name) {
		for (final String s : shortName.keySet()) {
			if (!s.equals(canonical) && shortName.get(s).equals(name)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String getName() {
		if (name == null) {
			return sourceFile == null ? "generic" : sourceFile;
		} else {
			return name;
		}
	}

	public void addFunction(String name, XFunctionBasic function) {
		functions.put(name, function);
	}
}
