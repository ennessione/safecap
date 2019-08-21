package uk.ac.ncl.eventb.why3.filter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import uk.ac.ncl.sexpr.ErrInfo;
import uk.ac.ncl.sexpr.alphabet;
import uk.ac.ncl.sexpr.syntree;

public class Why3FilterableLemma implements IFilterable {
	private final GlobalLemmataLibrary library;
	private final String why3LemmaBody;
	private final String why3LemmaName;
	private final Stack<Integer> operatorStack;
	private final SparseBitSet filter;

	public Why3FilterableLemma(GlobalLemmataLibrary library, String why3LemmaBody, String why3LemmaName) {
		this.library = library;
		this.why3LemmaBody = why3LemmaBody;
		this.why3LemmaName = why3LemmaName;
		operatorStack = new Stack<>();
		filter = new SparseBitSet();
	}

	public Why3FilterableLemma(GlobalLemmataLibrary library, Why3RawSexprLemma rawLemma) {
		this.library = library;
		why3LemmaBody = rawLemma.getWhy3LemmaBody();
		why3LemmaName = rawLemma.getWhy3LemmaName();
		operatorStack = new Stack<>();
		filter = new SparseBitSet();
		setFilterFromSExpression(rawLemma.getWhy3Sexpr());
	}

	public double rankDepth(FilterContext fcontext) {
		final SparseBitSet bitset = filter.clone();
		bitset.and(fcontext.getGoalFilterDepth());

		double rank = 0;
		int i = -1;
		while ((i = bitset.nextSetBit(i + 1)) != -1) {
			rank += library.getBitWeight(i);
		}
		return rank;
	}

	public double rankWidth(FilterContext fcontext) {
		final SparseBitSet bitset = filter.clone();
		bitset.and(fcontext.getGoalFilterWidth());

		double rank = 0;
		int i = -1;
		while ((i = bitset.nextSetBit(i + 1)) != -1) {
			rank += library.getBitWeight(i);
		}
		return rank;
	}

	public double rank(FilterContext fcontext) {
		final double rankDepth = rankDepth(fcontext);
		final double rankWidth = rankWidth(fcontext);

		return rankDepth + FilterGlobals.getWidthDepthRatio() * rankWidth;
	}

	public void setFilterFromSExpression(String sexpr) {
		try {
			final syntree syntree = parse(sexpr);
			if (syntree != null) {
				walk(syntree);
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	private void walk(syntree syntree) {
		switch (syntree.op()) {
		case alphabet.OPERATOR:
			final int code = library.mapWhy3Operator(syntree.value().toString());
			enter(code);
			for (int i = 0; i < syntree.siblings(); i++) {
				walk(syntree.sibling(i));
			}
			leave(code);
			break;
		case alphabet.CONST:
			break;
		case alphabet.TYPE:
			break;
		case alphabet.TYPE_VAR:
			break;
		}
	}

	private void enter(int code) {
		operatorStack.push(code);
	}

	private void leave(int code) {
		assert !operatorStack.isEmpty();
		assert operatorStack.peek() == code;

		FilterGlobals.insertFilter(operatorStack, filter);

		operatorStack.pop();
	}

	private static syntree parse(String string) {
		try {
			final InputStream is = new ByteArrayInputStream(string.getBytes());
			final List<ErrInfo> info = new ArrayList<>(0);
			final syntree result = uk.ac.ncl.sexpr.Parser.parse(is, info);
			if (!info.isEmpty()) {
				System.err.println("Parse failed for " + string + " : " + info.toString());
				return null;
			}

			return result;
		} catch (final Throwable e) {
			System.err.println("Parse failed for " + string + " : " + e.getMessage());
			return null;
		}
	}

	public String getWhy3LemmaBody() {
		return why3LemmaBody;
	}

	public String getWhy3LemmaName() {
		return why3LemmaName;
	}

	@Override
	public SparseBitSet getBitSet() {
		return filter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (why3LemmaBody == null ? 0 : why3LemmaBody.hashCode());
		result = prime * result + (why3LemmaName == null ? 0 : why3LemmaName.hashCode());
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
		final Why3FilterableLemma other = (Why3FilterableLemma) obj;
		if (why3LemmaBody == null) {
			if (other.why3LemmaBody != null) {
				return false;
			}
		} else if (!why3LemmaBody.equals(other.why3LemmaBody)) {
			return false;
		}
		if (why3LemmaName == null) {
			if (other.why3LemmaName != null) {
				return false;
			}
		} else if (!why3LemmaName.equals(other.why3LemmaName)) {
			return false;
		}
		return true;
	}

}
