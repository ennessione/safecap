package uk.ac.ncl.prime.sim.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;

public class MultiSourceLocation extends SourceLocation {
	private final List<SourceLocation> parts;

	public MultiSourceLocation(Collection<CLExpression> elements) {
		super();
		parts = new ArrayList<>(elements.size());
		int _start = Integer.MAX_VALUE;
		int _end = Integer.MIN_VALUE;
		for (final CLExpression e : elements) {
			processLocation(e);
		}

		for (final SourceLocation s : parts) {
			if (s.getStart() < _start) {
				_start = s.getStart();
			}
			if (s.getEnd() < _end) {
				_end = s.getEnd();
			}
		}

		super.start = _start;
		super.end = _end;
	}

	public MultiSourceLocation(CLExpression... elements) {
		super();
		parts = new ArrayList<>(elements.length);
		int _start = Integer.MAX_VALUE;
		int _end = Integer.MIN_VALUE;
		for (final CLExpression e : elements) {
			processLocation(e);
		}

		for (final SourceLocation s : parts) {
			if (s.getStart() < _start) {
				_start = s.getStart();
			}
			if (s.getEnd() < _end) {
				_end = s.getEnd();
			}
		}

		super.start = _start;
		super.end = _end;
	}

	private void processLocation(CLExpression e) {
		final SourceLocation sl = e.getLocation();
		if (sl instanceof MultiSourceLocation) {
			final MultiSourceLocation other = (MultiSourceLocation) sl;
			for (final SourceLocation s : other.parts) {
				processLocation(s);
			}
		} else if (sl != null) {
			if (!parts.contains(sl)) {
				if (e.getTag() == alphabet.ID) {
					final CLIdentifier ident = (CLIdentifier) e;
					sl.setKey(ident.getName());
				} else if (e.getTag() == alphabet.OP_MAP) {
					final CLBinaryExpression binary = (CLBinaryExpression) e;
					if (binary.getLeft().getTag() == alphabet.ID) {
						final CLIdentifier ident = (CLIdentifier) binary.getLeft();
						sl.setKey(ident.getName());
					}
				}
				parts.add(sl);
			}
		}
	}

	private void processLocation(SourceLocation sl) {
		if (sl instanceof MultiSourceLocation) {
			final MultiSourceLocation other = (MultiSourceLocation) sl;
			for (final SourceLocation s : other.parts) {
				processLocation(s);
			}
		} else if (sl != null) {
			if (!parts.contains(sl)) {
				parts.add(sl);
			}
		}
	}

	public List<SourceLocation> getParts() {
		return parts;
	}
}
