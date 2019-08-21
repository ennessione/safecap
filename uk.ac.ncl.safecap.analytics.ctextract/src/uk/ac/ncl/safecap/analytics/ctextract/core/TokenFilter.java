package uk.ac.ncl.safecap.analytics.ctextract.core;

import java.util.ArrayList;
import java.util.List;

public class TokenFilter implements ITokenVisitor {
	private final ITokenFilter filter;
	private final List<CTEToken> result;

	public TokenFilter(ITokenFilter filter) {
		this.filter = filter;
		result = new ArrayList<>();
	}

	public List<CTEToken> getResult() {
		return result;
	}

	@Override
	public void visit(CTEToken token, Object data) {
		if (filter.accept(token, data)) {
			result.add(token);
		}
	}

}
