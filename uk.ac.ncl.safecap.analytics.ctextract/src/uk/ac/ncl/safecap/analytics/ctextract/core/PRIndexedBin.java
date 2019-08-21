package uk.ac.ncl.safecap.analytics.ctextract.core;

public class PRIndexedBin {
	public int index;
	public String bin;

	public PRIndexedBin(int index, String bin) {
		super();
		this.index = index;
		this.bin = bin;
	}

	@Override
	public String toString() {
		return index + "{" + bin + "}";
	}
}