package uk.ac.ncl.safecap.xmldata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import uk.ac.ncl.safecap.xmldata.normalisation.INormalisation;
import uk.ac.ncl.safecap.xmldata.normalisation.Normalisation;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;

public class TypeRegistry implements INormalisation {
	private Map<String, String> idToType;
	private final Map<String, XEnumType> enums;
	private List<TypeRegistry> linked;
	private final Map<XEnumType, Set<String>> enumMembers;
	private final List<Normalisation> normalisers;

	public TypeRegistry() {
		idToType = new HashMap<>();
		enums = new HashMap<>();
		enumMembers = new HashMap<>();
		normalisers = new ArrayList<>();
	}

	@Override
	public String normalise(String literal, String type) {
		for (final Normalisation n : normalisers) {
			final String r = n.normalise(literal, type);
			if (r != null) {
				return r;
			}
		}

		return literal;
	}

	public void addNormaliser(Normalisation normaliser) {
		normalisers.add(0, normaliser);
	}

	public void clear() {
		idToType.clear();
		enums.clear();
		if (linked != null) {
			linked.clear();
		}
		enumMembers.clear();
		normalisers.clear();
	}

	public void setIdToType(Map<String, String> idToType) {
		this.idToType = idToType;
		for (final String id : idToType.keySet()) {
			updateEnum(id, idToType.get(id));
		}
	}

	@XmlElement
	public Map<String, String> getIdToType() {
		return idToType;
	}

	public void createNewEnum(String id) {
		final XEnumType enumt = new XEnumType(id);
		enums.put(id, enumt);
	}

	public void setType(String id, String type) {
		if (idToType.containsKey(id)) {
			assert idToType.get(id).equals(type);
			return;
		}

		idToType.put(id, type);
		updateEnum(id, type);
	}

	private void updateEnum(String id, String type) {
		XEnumType enumt = enums.get(type);
		if (enumt == null) {
			enumt = new XEnumType(type);
			enums.put(type, enumt);
		}

		Set<String> eset = enumMembers.get(enumt);
		if (eset == null) {
			eset = new HashSet<>();
			enumMembers.put(enumt, eset);
		}

		eset.add(id);
	}

	public Set<String> getEnumMembers(XEnumType type) {
		return enumMembers.get(type);
	}

	public XEnumType getEnum(String id) {
		return enums.get(id);
	}

	public String getType(String id) {
		String type = idToType.get(id);
		if (type == null && linked != null) {
			for (final TypeRegistry tr : linked) {
				type = tr.idToType.get(id);
				if (type != null) {
					System.out.println("Typed " + id + " to " + type + " via linked context");
					setType(id, type);
					return type;
				}
			}
		}

		return type;
	}

	public Set<String> getEnums() {
		return enums.keySet();
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		for (final String key : idToType.keySet()) {
			sb.append(key);
			sb.append(" -> ");
			sb.append(idToType.get(key));
			sb.append("\n");
		}

		return sb.toString();
	}

	/**
	 * Copies type declaration without checks
	 * 
	 * @param other
	 * @throws DataException
	 */
	public void addUnsafe(TypeRegistry other) throws DataException {
		idToType.putAll(other.idToType);
		for (final String id : other.idToType.keySet()) {
			updateEnum(id, other.idToType.get(id));
		}
	}

	public void inject(TypeRegistry other) throws DataException {
		for (final String k : other.idToType.keySet()) {
			if (idToType.containsKey(k) && !getType(k).equals(other.getType(k))) {
				throw new DataException("Conflicting type declarations: " + k + " -> " + getType(k) + " (old) and " + k + " -> "
						+ other.getType(k) + " (new)");
			}
		}

		// no conflicts, add all
		idToType.putAll(other.idToType);
		for (final String id : other.idToType.keySet()) {
			updateEnum(id, other.idToType.get(id));
		}
	}

}
