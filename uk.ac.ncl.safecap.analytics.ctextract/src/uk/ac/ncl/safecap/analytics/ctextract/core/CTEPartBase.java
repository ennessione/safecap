package uk.ac.ncl.safecap.analytics.ctextract.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public abstract class CTEPartBase {
	public enum KIND {
		NOISE, ELEMENT, OPERATOR, START, END;
		@Override
		public String toString() {
			switch (this) {
			case NOISE:
				return "N";
			case ELEMENT:
				return "E";
			case OPERATOR:
				return "O";
			case START:
				return "S";
			case END:
				return "X";
			default:
				return "E";
			}
		}

		public static KIND parse(String text) {
			final KIND r = _parse(text);
			if (r != null) {
				return r;
			} else {
				return ELEMENT;
			}
		}

		public static boolean isValidText(String text) {
			return _parse(text) != null;
		}

		public static KIND _parse(String text) {
			if ("N".equals(text) || NOISE.name().equals(text)) {
				return NOISE;
			} else if ("E".equals(text) || ELEMENT.name().equals(text)) {
				return ELEMENT;
			} else if ("O".equals(text) || OPERATOR.name().equals(text)) {
				return OPERATOR;
			} else if ("S".equals(text) || START.name().equals(text)) {
				return START;
			} else if ("X".equals(text) || END.name().equals(text)) {
				return END;
			} else {
				return null;
			}
		}
	}

	public abstract CTEPartBase transform(ITokenTransfomer transform);

	public abstract void merge(CTEPartBase other);

	public abstract int index(int start);

	public abstract void visit(ITokenVisitor visitor, Object data);

	public abstract void visit(IVisitor visitor, Object data);

	public abstract boolean visit(IParallelTokenVisitor visitor, CTEPartBase target, Object data);

	public List<CTEToken> filter(ITokenFilter filter, Object data) {
		final TokenFilter tf = new TokenFilter(filter);
		visit(tf, data);
		return tf.getResult();
	}

	/**
	 * Builds index set
	 * 
	 * @return collection of all token indices
	 */
	public Collection<Integer> indices() {
		final Collection<Integer> indices = new HashSet<>();
		visit(new ITokenVisitor() {
			@Override
			public void visit(CTEToken token, Object data) {
				indices.add(token.getIndex());
			}

		}, null);
		return indices;
	}

	/**
	 * Removes token literal for ELEMENT and NOISE tokens
	 */
	public void clean() {
		this.visit(new ITokenVisitor() {
			@Override
			public void visit(CTEToken token, Object data) {
				if (token.getKind() != KIND.OPERATOR) {
					token.setToken(null);
				}
			}

		}, null);
	}

	public abstract Map<String, String> match(CTEPartBase data);

	/**
	 * Deep copy of a given structure
	 */
	public abstract CTEPartBase copy();

	/**
	 * Returns first element of a list or element itselt
	 */
	public abstract CTEPartBase car();

	/**
	 * Returns rest of a list or element itself. Note the result is backed by the
	 * original list so should not be modified
	 */
	public abstract CTEPartBase cdr();

	public abstract boolean isEmpty();
}
