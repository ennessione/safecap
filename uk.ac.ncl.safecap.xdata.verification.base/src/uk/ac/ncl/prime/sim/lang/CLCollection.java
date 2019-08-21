package uk.ac.ncl.prime.sim.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.model.CLStatement;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.parser.SourceLocation;

public class CLCollection<T extends CLElement> extends CLElement implements Iterable<T> {
	@SuppressWarnings("rawtypes")
	public static final CLCollection EMPTY = new CLCollection(alphabet.SKIP);
	private List<T> parts;

	public CLCollection(int tag) {
		super(tag);
		parts = new ArrayList<>();
	}

	protected CLCollection(int tag, SourceLocation location) {
		super(tag, location);
		parts = new ArrayList<>();
	}

	public CLCollection(Collection<T> elements) {
		super(alphabet.SKIP);
		parts = new ArrayList<>(elements.size());
		parts.addAll(elements);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CLCollection<T> deepCopy() {
		final CLCollection result = new CLCollection(getTag());
		for (final T part : parts) {
			if (part instanceof CLExpression) {
				result.addPart(((CLExpression) part).deepCopy());
			} else if (part instanceof CLStatement) {
				result.addPart(((CLStatement) part).deepCopy());
			} else {
				result.addPart(part);
			}
		}

		assert result.size() == parts.size();

		return result;
	}

	@SuppressWarnings("unchecked")
	public CLCollection(T... elements) {
		super(alphabet.SKIP);
		parts = new ArrayList<>(elements.length);

		for (final T t : elements) {
			parts.add(t);
		}
	}

	public CLCollection<T> union(CLCollection<T> collection) {
		parts.addAll(collection.getParts());
		return this;
	}

	public void addPart(T part) {
		parts.add(part);
	}

	public boolean contains(T e) {
		return parts.contains(e);
	}

	public List<T> getParts() {
		return parts;
	}
	
	public void setParts(List<T> parts) {
		this.parts = parts;
	}	

	public int size() {
		return parts.size();
	}

	public T byIndex(int index) {
		return parts.get(index);
	}

	@Override
	public Iterator<T> iterator() {
		return parts.iterator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (parts == null ? 0 : parts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CLCollection) {
			final CLCollection<?> other = (CLCollection<?>) obj;
			if (parts.size() != other.size()) {
				return false;
			}

			// return parts.equals(other.parts);

			for (final Object x : parts) {
				if (!other.parts.contains(x)) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	public void visit(ICLExpressionVisitor visitor, Object userobject) {
		for (final CLElement e : parts) {
			if (e instanceof CLExpression) {
				final CLExpression exp = (CLExpression) e;
				exp.visit(visitor, userobject);
			}
		}
	}

	public boolean isImmutable() {
		for (final CLElement e : parts) {
			if (e instanceof CLExpression) {
				final CLExpression expr = (CLExpression) e;
				if (!expr.isImmutable()) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("{");
		for (int i = 0; i < parts.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}

			final T z = parts.get(i);
			if (z instanceof CLExpression) {
				final CLExpression ze = (CLExpression) z;
				sb.append(ze.asString());
			} else {
				sb.append(z.toString());
			}
		}
		sb.append("}");

		return sb.toString();
	}

	public boolean shallowEquals(List<CLExpression> args) {
		if (args == null || parts.size() != args.size()) {
			return false;
		}

		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i) != args.get(i)) {
				return false;
			}
		}

		return true;
	}
}
