package uk.ac.ncl.safecap.xmldata;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class TypeDecomposition implements Comparable<TypeDecomposition> {
	private String type;
	private String name;
	private String pattern;
	private String template;
	private Map<String, String> mapping;
	private String description;
	private transient IXFunction function;

	public TypeDecomposition() {

	}

	public TypeDecomposition(String name, String type, String pattern, String template, Map<String, String> mapping, String description) {
		this.type = type;
		this.name = name;
		this.pattern = pattern;
		this.template = template;
		this.mapping = mapping;
		this.description = description;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@XmlElement
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public Map<String, String> getMapping() {
		return mapping;
	}

	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}

	@XmlTransient
	public IXFunction getFunction() {
		return function;
	}

	public void setFunction(IXFunction function) {
		this.function = function;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (type == null ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TypeDecomposition other = (TypeDecomposition) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(TypeDecomposition td) {
		final int test1 = type.compareTo(td.type);
		if (test1 != 0) {
			return test1;
		} else {
			return name.compareTo(td.name);
		}
	}

}
