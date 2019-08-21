package uk.ac.ncl.safecap.xmldata.normalisation;

public abstract class Normalisation implements INormalisation {
	@Override
	public abstract String normalise(String literal, String type);
}
