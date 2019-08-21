package uk.ac.ncl.prime.sim.lang;

import java.util.HashMap;

public class XTagContext {
	private final HashMap<String, CLElement> xtags;

	public XTagContext() {
		xtags = new HashMap<>();
	}

	public void addXTag(String tag, CLElement element) {
		xtags.put(tag, element);
	}

	public void getXTag(String tag) {
		xtags.get(tag);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		for (final String s : xtags.keySet()) {
			sb.append(s);
			sb.append(": ");
			final CLElement el = xtags.get(s);
			if (el instanceof CLExpression) {
				final CLExpression st = (CLExpression) el;
				sb.append(st.asString());
			}
			sb.append(" at ");
			sb.append(el.getLocation().getStart() + 1);
			sb.append("-");
			sb.append(el.getLocation().getEnd() + 1);

			sb.append("\n");
		}

		return sb.toString();
	}
}
