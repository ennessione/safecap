package uk.ac.ncl.safecap.xdata.verification.transitions;

import javax.xml.bind.annotation.XmlTransient;

import uk.ac.ncl.prime.sim.lang.CLExpression;

public class TransitionConstant {
	private String name;
	private LocatedString value;
	private boolean isEnum = false;

	transient private CLExpression parsed;

	public TransitionConstant() {
	}

	public TransitionConstant(String name, CLExpression value, boolean isEnum) {
		assert name != null;
		this.name = name;
		this.value = LocatedString.make(value);
		parsed = value;
		this.isEnum = isEnum;
	}

	public TransitionConstant(String name, CLExpression value) {
		this(name, value, false);
	}

	public TransitionConstant(String name, LocatedString value) {
		assert name != null;
		this.name = name;
		this.value = value;
	}

	public boolean isEnum() {
		return isEnum;
	}

	public void setEnum(boolean isEnum) {
		this.isEnum = isEnum;
	}

	public LocatedString getValue() {
		return value;
	}

	public void setValue(LocatedString value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String kind) {
		name = kind;
	}

	@XmlTransient
	public void setParsed(CLExpression parsed) {
		this.parsed = parsed;
	}

	public CLExpression getParsed() {
		return parsed;
	}

	public boolean isValid() {
		return parsed != null;
	}
}
