package uk.ac.ncl.safecap.analytics.ctextract.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CTETokenList extends CTEPartBase {
	public static final CTETokenList EMPTY = new CTETokenList();

	private List<CTEPartBase> parts;

	public CTETokenList() {
	}

	public CTETokenList(List<CTEPartBase> parts) {
		this.parts = parts;
	}

	public List<CTEPartBase> getParts() {
		return parts;
	}

	public void setParts(List<CTEPartBase> parts) {
		this.parts = parts;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parts.size(); i++) {
			if (i > 0) {
				sb.append(" ");
			}
			if (parts.get(i) instanceof CTETokenList) {
				sb.append("(");
				sb.append(parts.get(i));
				sb.append(")");
			} else {
				sb.append(parts.get(i));
			}
		}

		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (parts == null ? 0 : parts.hashCode());
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
		final CTETokenList other = (CTETokenList) obj;
		if (parts == null) {
			if (other.parts != null) {
				return false;
			}
		} else if (!parts.equals(other.parts)) {
			return false;
		}
		return true;
	}

	@Override
	public CTEPartBase transform(ITokenTransfomer transform) {
		final List<CTEPartBase> tparts = new ArrayList<>();
		boolean changed = false;
		for (final CTEPartBase x : parts) {
			final CTEPartBase z = x.transform(transform);
			changed |= z != x;
			tparts.add(z);
		}

		if (changed) {
			return new CTETokenList(tparts);
		}

		return this;
	}

	@Override
	public void merge(CTEPartBase other) {
		if (other instanceof CTETokenList) {
			final CTETokenList lother = (CTETokenList) other;
			if (lother.parts.size() == parts.size()) {
				for (int i = 0; i < parts.size(); i++) {
					parts.get(i).merge(lother.parts.get(i));
				}
			}
		}
	}

	@Override
	public int index(int start) {
		for (final CTEPartBase x : parts) {
			start += x.index(start);
		}
		return start;
	}

	@Override
	public void visit(ITokenVisitor visitor, Object data) {
		for (final CTEPartBase x : parts) {
			x.visit(visitor, data);
		}
	}

	@Override
	public void visit(IVisitor visitor, Object data) {
		if (visitor.enter(this)) {
			for (int i = 0; i < parts.size(); i++) {
				final CTEPartBase x = parts.get(i);
				if (i > 0) {
					visitor.next();
				}
				x.visit(visitor, data);
			}
			visitor.leave(this);
		}
	}

	@Override
	public Map<String, String> match(CTEPartBase data) {
		if (data instanceof CTETokenList) {
			final CTETokenList other = (CTETokenList) data;
			if (other.parts.size() != parts.size()) {
				return null;
			}
			Map<String, String> result = null;
			for (int i = 0; i < parts.size(); i++) {
				final Map<String, String> r = parts.get(i).match(other.parts.get(i));
				if (r == null) {
					return null;
				}

				if (result == null) {
					result = new HashMap<>();
				} else {
					// detect conflicts
					for (final String key : r.keySet()) {
						if (result.containsKey(key) && !result.get(key).equals(r.get(key))) {
							return null;
						}
					}
				}

				result.putAll(r);
			}
			return result;
		}

		return null;
	}

	@Override
	public boolean visit(IParallelTokenVisitor visitor, CTEPartBase target, Object data) {
		if (target instanceof CTETokenList) {
			final CTETokenList list = (CTETokenList) target;
			if (list.parts.size() != parts.size()) {
				return false;
			}

			for (int i = 0; i < parts.size(); i++) {
				if (!parts.get(i).visit(visitor, list.parts.get(i), data)) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public CTEPartBase copy() {
		if (parts != null) {
			final List<CTEPartBase> partsCopy = new ArrayList<>();
			for (final CTEPartBase p : parts) {
				partsCopy.add(p.copy());
			}
			return new CTETokenList(partsCopy);
		} else {
			return this;
		}
	}

	@Override
	public CTEPartBase car() {
		return parts.get(0);
	}

	@Override
	public CTEPartBase cdr() {
		return new CTETokenList(parts.subList(1, parts.size()));
	}

	@Override
	public boolean isEmpty() {
		return parts == null || parts.size() == 0;
	}

}
