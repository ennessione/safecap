package uk.ac.ncl.safecap.analytics.ctextract.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPartBase.KIND;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTBin;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTProjectPart;
import uk.ac.ncl.safecap.cteparser.ErrInfo;
import uk.ac.ncl.safecap.cteparser.TEContext;

public class PRRule {
	private final CTEPartBase left;
	private final CTEPartBase right;

	public PRRule(CTEPartBase left, CTEPartBase right) {
		super();
		this.left = left;
		this.right = right == null ? CTETokenList.EMPTY : right;
	}

	public void check(CTProjectPart part, TEContext context) {
		if (rightSpan() >= span()) {
			addError(context, "Production rule target must be strictly smaller than source");
		}

		left.visit(new ITokenVisitor() {

			@Override
			public void visit(CTEToken token, Object data) {
				if (token.getKind() == KIND.ELEMENT && token.getBin() != null) {
					final String bin = token.getBin();
					final CTBin resolved = part.resolveBin(bin);
					if (resolved == null) {
						addError(context, "Invalid bin name " + bin);
					}
				}

			}

		}, null);
	}

	public int span() {
		if (left instanceof CTETokenList) {
			final CTETokenList list = (CTETokenList) left;
			return list.getParts().size();
		}

		return 1;
	}

	public int rightSpan() {
		if (right instanceof CTETokenList) {
			final CTETokenList list = (CTETokenList) right;
			return list.getParts() == null ? 0 : list.getParts().size();
		}

		return 1;
	}

	public CTEPartBase apply(CTEPartBase target, final Map<String, List<String>> context) {
		final Map<String, List<String>> ctx2 = new HashMap<>();
		if (applies(target, ctx2)) {
			context.putAll(ctx2);
			return right.copy();
		} else {
			return target;
		}
	}

	private boolean applies(CTEPartBase target, Map<String, List<String>> map) {
		return left.visit(new IParallelTokenVisitor() {
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
						if (token1.getBin() != null) {
							assert token2.getToken() != null;
							List<String> values = map.get(token1.getBin());
							if (values == null) {
								values = new ArrayList<>();
								map.put(token1.getBin(), values);
							}

							values.add(token2.getToken());
						}
						return true;
					}
					return false;
				}
				default:
					break;
				}
				return false;
			}

		}, target, null);
	}

	public CTEPartBase getLeft() {
		return left;
	}

	public CTEPartBase getRight() {
		return right;
	}

	private void addError(TEContext context, String error) {
		context.addError(new ErrInfo(0, 0, error));
	}

	@Override
	public String toString() {
		return left.toString() + " -> ()";
	}
}