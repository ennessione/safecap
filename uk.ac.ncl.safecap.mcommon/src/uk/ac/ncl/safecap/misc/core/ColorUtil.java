package uk.ac.ncl.safecap.misc.core;

public class ColorUtil {

	public static String makeDarkColorCode(String text) {
		int hash = text.hashCode();
		final boolean f1 = hash % 2 == 0;
		final boolean f2 = (hash >> 1) % 2 == 0;
		hash = hash >> 2;
		final int code1 = (hash & 0xF) % 64;
		hash = hash >> 4;
		final int code2 = (hash & 0xF) % 64;

		if (f1 && f2) {
			final String red = Integer.toHexString(40 + code1);
			final String green = Integer.toHexString(code2);
			return "#" + red + green + "00";
		} else if (f1 && !f2) {
			final String red = Integer.toHexString(40 + code1);
			final String blue = Integer.toHexString(code2);
			return "#" + red + "00" + blue;
		} else if (!f1 && f2) {
			final String green = Integer.toHexString(40 + code1);
			final String blue = Integer.toHexString(code2);
			return "#" + "00" + green + blue;
		} else {
			final String red = Integer.toHexString(20 + hash % 64);
			final String green = Integer.toHexString(20 + code1);
			final String blue = Integer.toHexString(20 + code2);
			return "#" + red + green + blue;
		}
	}

	public static String makeLightColorCode(String text) {
		int hash = text.hashCode();
		final boolean f1 = hash % 2 == 0;
		final boolean f2 = (hash >> 1) % 2 == 0;
		hash = hash >> 2;
		final int code1 = (hash & 0xF) % 64;
		hash = hash >> 4;
		final int code2 = (hash & 0xF) % 64;

		if (f1 && f2) {
			final String red = Integer.toHexString(120 + code1);
			final String green = Integer.toHexString(60 + code2);
			return "#" + red + green + "00";
		} else if (f1 && !f2) {
			final String red = Integer.toHexString(120 + code1);
			final String blue = Integer.toHexString(60 + code2);
			return "#" + red + "00" + blue;
		} else if (!f1 && f2) {
			final String green = Integer.toHexString(120 + code1);
			final String blue = Integer.toHexString(60 + code2);
			return "#" + "00" + green + blue;
		} else {
			final String red = Integer.toHexString(90 + hash % 64);
			final String green = Integer.toHexString(90 + code1);
			final String blue = Integer.toHexString(90 + code2);
			return "#" + red + green + blue;
		}
	}
}
