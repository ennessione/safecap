package uk.ac.ncl.safecap.analytics.ctextract.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPartBase.KIND;

public class RWERule {
	private static Collection<String> dictionary = Collections.emptyList();

	private final CTEPartBase left;
	private final CTEPartBase right;

	private final Map<String, Pattern> compiledPatterns;

	static {
		try {
			dictionary = CTEPlugin.getLibFileLineContents("dict/words");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public CTEPartBase getLeft() {
		return left;
	}

	public CTEPartBase getRight() {
		return right;
	}

	public RWERule(CTEPartBase left, CTEPartBase right) {
		this.left = left;
		this.right = right;
		compiledPatterns = new HashMap<>();
	}

	public int span() {
		if (left instanceof CTETokenList) {
			final CTETokenList list = (CTETokenList) left;
			return list.getParts().size();
		}

		return 1;
	}

	private Pattern getPattern(String text) {
		Pattern pattern = compiledPatterns.get(text);
		if (pattern == null) {
			pattern = Pattern.compile(text);
			compiledPatterns.put(text, pattern);
		}

		return pattern;
	}

	public CTEPartBase apply(CTEPartBase target) {
		final Map<IntPair, String> map = applies(target);
		if (map != null) {
			final CTEPartBase result = right.copy();
			result.visit(new ITokenVisitor() {
				@Override
				public void visit(CTEToken token, Object data) {
					token.flagSet("rwe", "none");
					substitute(token, map);
				}

			}, null);
			return result;
		}

		return target;
	}

	protected void substitute(CTEToken token, Map<IntPair, String> map) {
		String text = token.getToken();
		for (final IntPair index : map.keySet()) {
			final String pattern = "$" + index.first + ":" + index.second;
			final String value = map.get(index);
			text = text.replace(pattern, value);
		}

		token.setToken(text);
	}

	public Map<IntPair, String> applies(CTEPartBase target) {
		final Map<IntPair, String> map = new HashMap<>();

		final boolean r = left.visit(new IParallelTokenVisitor() {
			@Override
			public boolean visit(CTEToken token1, CTEToken token2, Object data) {
				switch (token1.getKind()) {
				case END:
				case START:
				case NOISE:
					return token2.getKind() == token1.getKind();
				case OPERATOR: {
					if (token2.getKind() == KIND.OPERATOR) {
						return token2.getToken() == token1.getToken() || token2.getToken().equals(token1.getToken());
					}

					return false;
				}
				case ELEMENT: {
					if (token2.getKind() == KIND.ELEMENT) {
						if ("??word".equals(token1.getToken())) {
							final String other = token2.getToken().toLowerCase();
							if (other.length() > 2) {
								return dictionary.contains(other);
							} else {
								return false;
							}
						} else {
							final Pattern pattern = getPattern(token1.getToken());
							final Matcher matcher = pattern.matcher(token2.getToken());
							if (matcher.matches()) {
								for (int i = 0; i <= matcher.groupCount(); i++) {
									final IntPair index = new IntPair(token1.getIndex(), i);
									map.put(index, matcher.group(i));
								}
								return true;
							}
						}
						return false;
					}
					return false;
				}
				default:
					break;
				}
				return false;
			}

		}, target, null);

		if (r) {
			return map;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return left.toString() + " -> " + right.toString();
	}
}

class IntPair {
	int first;
	int second;

	public IntPair(int first, int second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + first;
		result = prime * result + second;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final IntPair other = (IntPair) obj;
		if (first != other.first) {
			return false;
		}
		if (second != other.second) {
			return false;
		}
		return true;
	}

}