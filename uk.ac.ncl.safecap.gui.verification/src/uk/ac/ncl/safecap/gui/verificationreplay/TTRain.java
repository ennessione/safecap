package uk.ac.ncl.safecap.gui.verificationreplay;

public class TTRain {
	private final String name;

	public TTRain(String id) {
		name = id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
