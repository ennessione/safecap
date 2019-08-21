package uk.ac.ncl.safecap.xmldata;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

public class TypeTransformation {
	private String type;
	private String name;
	private String pattern;
	private Map<String, String> mapping;

	public TypeTransformation() {

	}

	public TypeTransformation(String name, String type, String pattern, Map<String, String> mapping) {
		this.type = type;
		this.name = name;
		this.pattern = pattern;
		this.mapping = mapping;
	}

	@XmlElement
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
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

}
