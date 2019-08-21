package uk.ac.ncl.safecap.misc;

import java.util.Arrays;
import java.util.List;

public class StringUtil {

	public static String ppList(List<?> list, String separator) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				sb.append(separator);
			}
			sb.append(list.get(i));
		}
		return sb.toString();
	}
	
	public static List<String> parseList(String text, String separator) {
		return Arrays.asList(text.split(separator));
	}	

}
