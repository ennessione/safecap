package uk.ac.ncl.prime.sim.lang.semantics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLAtomicExpression;
import uk.ac.ncl.prime.sim.lang.CLException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.lang.model.CLStatement;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;

public class SymbFrame {
	private Collection<String> read;
	private Collection<String> write;

	public SymbFrame(Collection<String> read, Collection<String> write) {
		this.read = read;
		this.write = write;
	}

	public SymbFrame(CLStatement s) {
		read = new HashSet<>();
		s.visitExpressions(new ICLExpressionVisitor() {
			@Override
			public boolean visit(CLExpression element, Object userobject) throws CLException {
				read.addAll(element.getFreeIdentifiers());
				return false;
			}

		}, null);
		write = s.getUpdatedVariables();
	}

	public SymbFrame(CLExpression s) {
		read = new HashSet<>();
		for (final String z : s.getPrimedIdentifiers()) {
			read.add(CLUtils.unPrimed(z));
		}

		write = new HashSet<>(s.getFreeIdentifiers());
		write.removeAll(s.getPrimedIdentifiers());
	}

	public CLExpression filter(CLExpression expression) {
		if (expression.getTag() == alphabet.OP_AND) {
			final List<CLExpression> parts = new ArrayList<>();
			final CLMultiExpression me = (CLMultiExpression) expression;
			boolean changed = false;
			for (final CLExpression e : me.getParts()) {
				final CLExpression ef = filter(e);
				changed |= e != ef;
				if (ef != null) {
					parts.add(ef);
				}
			}

			if (changed) {
				if (parts.isEmpty()) {
					return CLAtomicExpression.TRUE;
				} else {
					return CLUtils.buildConjunct(parts, null);
				}
			}

			return expression;
		} else {
			if (!reads(expression.getFreeIdentifiers())) {
				return null;
			} else {
				return expression;
			}
		}
	}

	public boolean updates(Collection<String> identifiers) {
		if (write == null) {
			return true;
		}

		for (final String s : identifiers) {
			if (write.contains(s)) {
				return true;
			}
		}

		return false;
	}

	public boolean reads(Collection<String> identifiers) {
		if (read == null) {
			return true;
		}

		for (final String s : identifiers) {
			if (read.contains(s)) {
				return true;
			}
		}

		return false;
	}

	public Collection<String> getRead() {
		return read;
	}

	public Collection<String> getWrite() {
		return write;
	}

	public void setReadAny() {
		read = null;
	}
}
