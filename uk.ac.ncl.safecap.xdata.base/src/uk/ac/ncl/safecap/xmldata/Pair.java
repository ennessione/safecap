package uk.ac.ncl.safecap.xmldata;

import javax.xml.bind.annotation.XmlElement;

public class Pair {
	private Object l;
	private Object r;

	public Pair() {
	}

	public Pair(Object l, Object r) {
		this.l = l;
		this.r = r;
	}

	@Override
	public Object clone() {
		return new Pair(l, r);
	}

	@XmlElement(name = "l", namespace = "http://www.w3.org/2001/XMLSchema")
	public Object getKey() {
		return l;
	}

	public void setKey(Object l) {
		this.l = l;
	}

	@XmlElement(name = "r")
	// , namespace = "http://www.w3.org/2001/XMLSchema"
	public Object getR() {
		return r;
	}

	public void setR(Object r) {
		this.r = r;
	}

	public Object getValue() {
		return r;
	}

	@Override
	public String toString() {
		return "(" + (l != null ? l.toString() : "null") + ", " + (r != null ? r.toString() : "null") + ")";
	}
}