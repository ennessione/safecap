package uk.ac.ncl.safecap.xmldata;

import uk.ac.ncl.safecap.xmldata.types.XType;

public class TypeCastingAbort extends Exception {
	static final TypeCastingAbort NOINFO = new TypeCastingAbort();
	private static final long serialVersionUID = 5271412880412371242L;

	private IXFunction function;
	private Object value;
	private Object domain;
	private XType type;
	private XType sourceType;
	private XType rangeType;
	private XType domainType;

	private TypeCastingAbort() {
	}

	public TypeCastingAbort(IXFunction function, Object value, XType type) {
		super();
		this.function = function;
		this.value = value;
		this.type = type;
	}

	public TypeCastingAbort(Object value, XType type) {
		super();
		this.value = value;
		this.type = type;
	}

	public TypeCastingAbort(Object value, XType type, XType sourcetype) {
		super();
		this.value = value;
		this.type = type;
		sourceType = sourcetype;
	}

	public IXFunction getFunction() {
		return function;
	}

	public void setFunction(IXFunction function) {
		this.function = function;
	}

	public Object getValue() {
		return value;
	}

	public XType getType() {
		return type;
	}

	public Object getDomain() {
		return domain;
	}

	public void setDomain(Object domain) {
		this.domain = domain;
	}

	public XType getRangeType() {
		return rangeType;
	}

	public void setRangeType(XType rangeType) {
		this.rangeType = rangeType;
	}

	public XType getDomainType() {
		return domainType;
	}

	public void setDomainType(XType domainType) {
		this.domainType = domainType;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("Type casting error in contract\n");

		if (function != null) {
			sb.append("\telement: ");
			sb.append(function.getName());
			sb.append("\n");
			sb.append("\tpath: ");
			sb.append(function.getCanonicalName());
			sb.append("\n");
		}

		if (domainType != null || rangeType != null) {
			sb.append("\tcontract rule: ");
			sb.append(domainType != null ? domainType : "?");
			sb.append(" -> ");
			sb.append(rangeType != null ? rangeType : "?");
			sb.append("\n");
		}

		sb.append("\terror: cannot cast value \"");
		sb.append(value.toString());
		sb.append("\" to type ");
		sb.append(type.toString());
		sb.append("\n");

		if (sourceType != null) {
			sb.append("\tvalue type: ");
			sb.append(sourceType.toString());
			sb.append("\n");
		}

		if (domain != null && function != null) {
			sb.append("\tmapping:");
			sb.append(domain);
			sb.append(" -> ");
			sb.append(function.get(domain));
		}

		return sb.toString();
	}
}