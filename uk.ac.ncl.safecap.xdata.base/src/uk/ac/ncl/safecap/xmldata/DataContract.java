package uk.ac.ncl.safecap.xmldata;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.safecap.xmldata.normalisation.Normalisation;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;
import uk.ac.ncl.safecap.xmldata.types.XType;

public class DataContract {

	private static class TypeDec {
		String type;
		String template;
		String canonicalName;

		public TypeDec(String canonicalName, String type, String template) {
			this.canonicalName = canonicalName;
			this.type = type;
			this.template = template;
		}
	}

	private static class EnumType {
		String name;
		List<String> members;

		private EnumType(String name, List<String> members) {
			super();
			this.name = name;
			this.members = members;
		}

	}

	private final Collection<DataContractElement> elements;
	private final Collection<String> namespaces;
	private final Collection<TypeDec> typeDecompositions;
	private final Collection<DataContractExternalMap> externalMapElements;
	private final Collection<String> externalFileLocations;
	private final Collection<DataContractSplit> elementSplits;
	private final Collection<Normalisation> normalisers;
	private final Collection<EnumType> enums;

	private boolean force;
	private String name;
	private String conceptMap;

	private File persistContractFile = null;

	public DataContract() {
		elements = new ArrayList<>();
		namespaces = new ArrayList<>();
		typeDecompositions = new ArrayList<>();
		externalMapElements = new ArrayList<>();
		externalFileLocations = new HashSet<>();
		elementSplits = new ArrayList<>();
		normalisers = new ArrayList<>();
		enums = new ArrayList<>();
	}

	public Collection<String> getExternalFileLocations() {
		return externalFileLocations;
	}

	public void addExternalMapLocation(String location) {
		externalFileLocations.add(location);
	}

	public void addExternalMapElement(DataContractExternalMap mapElement) {
		externalMapElements.add(mapElement);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConceptMap() {
		if (conceptMap != null) {
			return conceptMap;
		} else {
			return "tables";
		}
	}

	public void setConceptMap(String conceptMap) {
		this.conceptMap = conceptMap;
	}

	public void addEnum(String name, List<String> parts) {
		final EnumType type = new EnumType(name, parts);
		enums.add(type);
	}

	public void addNamespace(String namespace) throws DataConfigurationException {

		if (namespace == null || namespace.isEmpty()) {
			throw new DataConfigurationException("Empty namespace name");
		}

		for (final String ns : namespaces) {
			if (ns.equals(namespace)) {
				throw new DataConfigurationException("Duplicate namepsace declaration - " + namespace);
			}
		}

		namespaces.add(namespace);
	}

	public void addConcreteElement(String canonicalName, String shortName, String domain, String range, String kind, String description,
			String tag) throws DataConfigurationException {
		for (final DataContractElement _el : elements) {
			if (_el instanceof ConcreteDataContractElement) {
				final ConcreteDataContractElement el = (ConcreteDataContractElement) _el;
				if (el.match(canonicalName)) {
					throw new DataConfigurationException("Duplicate element declaration (canonical name) - " + canonicalName);
				} else if (el.getShortName().equals(shortName)) {
					throw new DataConfigurationException("Duplicate element declaration (short name) - " + shortName);
				}
			}
		}

		if (canonicalName == null || canonicalName.isEmpty()) {
			throw new DataConfigurationException("Empty element canonical name");
		}

		if (shortName == null || shortName.isEmpty()) {
			throw new DataConfigurationException("Empty element short name for " + canonicalName);
		}

		if (domain == null || domain.isEmpty()) {
			throw new DataConfigurationException("Empty element domain for " + canonicalName);
		}

		if (range == null || range.isEmpty()) {
			throw new DataConfigurationException("Empty element range for " + canonicalName);
		}

		elements.add(new ConcreteDataContractElement(canonicalName, shortName, domain, range, kind, description, tag, false));
	}

	public void addTemplateElement(String template, String domain, String range, String description) throws DataConfigurationException {
		if (template == null || template.isEmpty()) {
			throw new DataConfigurationException("Empty template name for template element");
		}

		if (domain != null && domain.length() == 0) {
			domain = null;
		}

		if (range != null && range.length() == 0) {
			range = null;
		}

		if (domain == null && range == null) {
			throw new DataConfigurationException("Empty element domain and range for " + template);
		}

		elements.add(new TemplateDataContractElement(template, domain, range, description));
	}

	public void addTypeDecomposition(String type, String name, String pattern, String template) throws DataConfigurationException {
		if (type == null || type.isEmpty()) {
			throw new DataConfigurationException("Empty type name in type decomposition");
		}

		if (name == null || name.isEmpty()) {
			throw new DataConfigurationException("Empty name in type decomposition " + type);
		}

		if (pattern == null || pattern.isEmpty()) {
			throw new DataConfigurationException("Empty pattern in type decomposition " + type);
		}

		String ts = "";
		if (template != null && template.length() > 0) {
			ts = ":" + template;
		}

		typeDecompositions.add(new TypeDec(type + "." + name, type, name + ":" + pattern + ts));
	}

	public void addNormaliser(Normalisation n) {
		normalisers.add(n);
	}

	public void prepare(DataContext context) throws DataConfigurationException {

		if (persistContractFile != null) {
			try {
				context.setPersistentContract(FileUtil.getFileContents(persistContractFile));
			} catch (final Throwable e) {
				throw new DataConfigurationException("Failed persisting contract: " + e.getMessage());
			}
		}
		for (final Normalisation n : normalisers) {
			context.getTypeRegistry().addNormaliser(n);
		}
	}

	public void enactEnums(DataContext context) throws DataConfigurationException {
		for (final EnumType et : enums) {
			if (context.getTypeRegistry().getEnum(name) != null) {
				throw new DataConfigurationException("Redeclaration of type " + et.name);
			}
			context.getTypeRegistry().createNewEnum(et.name);
			for (final String m : et.members) {
				context.getTypeRegistry().setType(m, et.name);
			}
		}

	}

	public void enactTypeDec(DataContext context) throws DataConfigurationException {
		for (final TypeDec typedec : typeDecompositions) {
			try {
				new TypeDecompositionBuilder(context, typedec.type, typedec.template);
			} catch (final TypeDecompositionException e) {
				if (!force) {
					throw new DataConfigurationException("Type decomposition compilation error: " + e.getMessage());
				}
			}
		}
	}

	private boolean isTypeDec(String canonicalName) {
		for (final TypeDec typedec : typeDecompositions) {
			if (typedec.canonicalName.equals(canonicalName)) {
				return true;
			}
		}

		return false;
	}

	public void enactElements(DataContext context, Map<String, String> namemap) throws DataConfigurationException {
		for (final DataContractElement element : elements) {
			boolean found = false;
			for (final IXFunction f : context.getFunctions()) {
				if (element.match(f)) {
					enactElement(context, element, f, namemap);
					found = true;
					if (element instanceof ConcreteDataContractElement) {
						break;
					}
				}
			}

			if (!found) {
				if (element instanceof ConcreteDataContractElement) {
					final ConcreteDataContractElement el2 = (ConcreteDataContractElement) element;
					if (!el2.isNotEmpty()) {
						context.addEmptyFunction(el2.getCanonicalName(), el2.getShortName(), el2.getDomain(), el2.getRange(), el2.getTag());
						// System.err.println("Add empty function " + el2.getShortName());
					} else {
						if (!force && !isTypeDec(el2.getCanonicalName())) {
							throw new DataConfigurationException("Element " + el2.getCanonicalName() + " is not present in the data set");
						}
					}
				}
			}

		}
	}

	public void enactExternalMaps(DataContext context) throws DataConfigurationException {
		if (externalFileLocations != null && !externalFileLocations.isEmpty()) {
			context.setExternalFileLocations(externalFileLocations);
		}

		for (final DataContractExternalMap element : externalMapElements) {
			boolean found = false;
			for (final IXFunction f : context.getFunctions()) {
				if (element.getCanonicalName().equals(f.getCanonicalName())) {
					enactExternalMap(context, element, f);
					found = true;
					break;
				}
			}

			if (!found && !force) {
				throw new DataConfigurationException(
						"External map element " + element.getCanonicalName() + " is not present in the data set");
			}
		}
	}

	private void enactExternalMap(DataContext context, DataContractExternalMap element, IXFunction f) {
		final RegexStringRewrite rewriterDomain = new RegexStringRewrite(element.getTemplateDomain(), element.getTargetDomain());
		final RegexStringRewrite rewriterRange = new RegexStringRewrite(element.getTemplateRange(), element.getTargetRange());
		context.useFunctionAsExternalMap(f, rewriterRange, rewriterDomain);
	}

	public void enactDecompositionElements(DataContext context, Map<String, String> namemap) throws DataConfigurationException {
		for (final TypeDec typedec : typeDecompositions) {
			final IXFunction f = context.getFunction(typedec.canonicalName);
			if (f == null) {
				if (!force) {
					throw new DataConfigurationException(
							"Decomposition element " + typedec.canonicalName + " is not present in the data set");
				} else {
					continue;
				}
			}

			boolean found = false;
			for (final DataContractElement _element : elements) {
				if (_element instanceof ConcreteDataContractElement) {
					final ConcreteDataContractElement element = (ConcreteDataContractElement) _element;
					if (f.getCanonicalName().equals(element.getCanonicalName())) {
						enactElement(context, element, f, namemap);
						found = true;
						break;
					}
				}
			}

			if (!found && !force) {
				throw new DataConfigurationException("Decomposition element " + f.getCanonicalName() + " is not present in the contract");
			}
		}
	}

	private void enactElement(DataContext context, DataContractElement element, IXFunction f, Map<String, String> namemap)
			throws DataConfigurationException {

		if (f instanceof XFunctionBasic) {
			// set tag
			if (element instanceof ConcreteDataContractElement) {
				final ConcreteDataContractElement ce = (ConcreteDataContractElement) element;
				context.setFunctionTag(f, ce.getTag());
			}
			if (force) {
				try {
					if (element.getDomain() != null) {
						context.setDomainTypeInfo(f, element.getDomain());
					}
				} catch (final Throwable e) {
					System.err.println("enactElement: domain contract for " + f.getCanonicalName() + " failed - " + e.getMessage());
				}

				try {
					if (element.getRange() != null) {
						context.setRangeTypeInfo(f, element.getRange());
					}
				} catch (final Throwable e) {
					System.err.println("enactElement: range contract for " + f.getCanonicalName() + " failed - " + e.getMessage());
				}

				// set description
				if (element.getDescription() != null && element.getDescription().length() > 0) {
					f.setDescription(element.getDescription());
				}

			} else {
				if (element.getDomain() != null) {
					context.setDomainTypeInfo(f, element.getDomain());
				}
				if (element.getRange() != null) {
					context.setRangeTypeInfo(f, element.getRange());
				}

				// set description
				if (element.getDescription() != null && element.getDescription().length() > 0
						&& (f.getDescription() == null || f.getDescription().length() == 0)) {
					f.setDescription(element.getDescription());
				}
			}

			namemap.put(f.getName(), element.getPreferredShortName(f));
		}
	}

	public void enactNamespaces(DataContext context) throws DataConfigurationException {
		final Collection<String> ans = new HashSet<>(context.getNamespaces().size());
		for (final DataNamespace ns : context.getNamespaces()) {
			if (!force && !namespaces.contains(ns.getId())) {
				throw new DataConfigurationException("Namespace \"" + ns.getId() + "\" is not expected");
			}
			ans.add(ns.getId());
		}

		if (!force) {
			for (final String ns : namespaces) {
				if (!ans.contains(ns)) {
					throw new DataConfigurationException("Namespace \"" + ns + "\" is not present");
				}
			}
		}
	}

	public void enacSplitElements(DataContext context) throws DataConfigurationException {
		for (final DataContractSplit split : elementSplits) {
			enacSplitElements(context, split);
		}
	}

	private void enacSplitElements(DataContext context, DataContractSplit split) throws DataConfigurationException {
		final IXFunction ff = findFunction(context, split.getPath());
		if (ff == null) {
			throw new DataConfigurationException("Invalid element path \"" + split.getPath() + "\" for split");
		}

		if (ff.getFunctionType() != null) {
			throw new DataConfigurationException(
					"Invalid element \"" + split.getPath() + "\" for split: the element is well-type with type " + ff.getFunctionType());
		}

		for (final String subpart : split.getParts()) {
			enacSplitElements((XFunctionBasic) ff, context, split, subpart);
		}

	}

	private void enacSplitElements(XFunctionBasic ff, DataContext context, DataContractSplit split, String subpart)
			throws DataConfigurationException {
		final XEnumType type = context.getEnum(subpart);
		if (type == null) {
			throw new DataConfigurationException("Split part \"" + subpart + "\" is not a valid type name");
		}

		if (context.getEnumMembers(type).isEmpty()) {
			throw new DataConfigurationException("Split part \"" + subpart + "\" is an empty type");
		}

		final String fullName = split.getBase() + "." + subpart;
		final IXFunction f = context.getFunction(fullName);
		if (f != null) {
			throw new DataConfigurationException("Split part \"" + subpart + "\" conflicts with a previously declared concept");
		}

		final XFunctionBasic function = new XFunctionBasic(context, fullName);
		function.setCanonicalName(split.getPath() + "/" + subpart);
		function.setTag("split");
		function.setDescription("Split of " + split.getPath());

		XType domainType = null;
		for (final Object key : ff.getMaps().keySet()) {
			final XType keytype = XType.resolve(context.getTypeRegistry(), key);
			if (keytype == null) {
				throw new DataConfigurationException("Split failed on ill typed domain element " + key);
			}

			if (domainType != null && !domainType.equals(keytype)) {
				throw new DataConfigurationException(
						"Split failed on ill typed domain element " + key + " of type " + keytype + "; expect " + domainType);
			}

			domainType = keytype;

			for (final Object val : ff.get(key)) {
				// XType valtype = XType.resolve(context.getTypeRegistry(), val);

				Object casted = null;
				if (val instanceof ValueList) {
					final ValueList vl = (ValueList) val;
					if (vl.size() == 2) {
						casted = TypeCasting.castToType(vl.get(1), type, context.getTypeRegistry());
					}
				} else {
					casted = TypeCasting.castToType(val, type, context.getTypeRegistry());
				}
				if (casted != null) {
					function.add(key, casted);
//				else
//					System.out.println("Rejecting " + valtype + "; expecting " + type);
				}
			}
		}

		context.addFunction(function.getName(), function);
	}

	private IXFunction findFunction(DataContext context, String canonical) {
		for (final IXFunction f : context.getFunctions()) {
			if (f.getCanonicalName().equals(canonical)) {
				return f;
			}
		}
		return null;
	}

	public void setForce(boolean force) {
		this.force = force;

	}

	public void addDataContractSplit(DataContractSplit split) {
		elementSplits.add(split);
	}

	public Collection<DataContractSplit> getDataContractSplit() {
		return elementSplits;
	}

	public File getPersistContractFile() {
		return persistContractFile;
	}

	public void setPersistContractFile(File file) {
		persistContractFile = file;
	}
}
