package uk.ac.ncl.prime.sim.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.parser.syntree;
import uk.ac.ncl.safecap.xdata.verification.transitions.LocatedElement;

public class SourceLocation {
	public static List<String> EMPTY_TRACE = Collections.emptyList();
	public static final SourceLocation Dummy = new SourceLocation();

	private String source;
	protected int start;
	protected int end;
	private List<String> trace;
	private SourceLocation enclosure;
	private String key;

	public SourceLocation(List<String> trace, SourceLocation loc) {
		if (loc != null) {
			start = loc.start;
			end = loc.end;
			source = loc.source;
			enclosure = loc.enclosure;
			if (loc.trace.isEmpty()) {
				this.trace = trace;
			} else {
				this.trace = new ArrayList<>(trace);
				this.trace.addAll(loc.getTrace());
				this.trace = Collections.unmodifiableList(this.trace);
			}

			if (hasDuplicateTrace()) {
				System.out.println("Duplicated trace " + trace);
			}
		}
	}

	public SourceLocation(LocatedElement element) {
		start = element.getStart();
		end = element.getEnd();
		source = element.getSource();
		enclosure = element.getEnclosure() != null ? new SourceLocation(null, element.getEnclosure().getStart(),
				element.getEnclosure().getEnd(), element.getEnclosure().getSource()) : null;
		key = element.getKey();
		trace = element.getTrace() != null ? Arrays.asList(element.getTrace()) : Collections.emptyList();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private boolean hasDuplicateTrace() {
		for (int i = 0; i < trace.size(); i++) {
			for (int j = i + 1; j < trace.size(); j++) {
				if (trace.get(i).equals(trace.get(j))) {
					return true;
				}
			}
		}

		return false;
	}

	public SourceLocation(SourceLocation loc) {
		start = loc.start;
		end = loc.end;
		source = loc.source;
		enclosure = loc.enclosure;
		trace = EMPTY_TRACE;
	}

	public SourceLocation() {
		start = Integer.MAX_VALUE;
		end = Integer.MIN_VALUE;
		source = null;
		trace = EMPTY_TRACE;
	}

	public SourceLocation(syntree ast, String source) {
		start = ast.getStart();
		end = ast.getEnd();
		this.source = source;
		trace = EMPTY_TRACE;
	}

	public SourceLocation(List<String> trace, SourceLocation enc, int start, int end, String source) {
		this.start = start;
		this.end = end;
		this.source = source;
		this.trace = trace;
		enclosure = enc;
	}

	public SourceLocation(List<String> trace, int start, int end, String source) {
		this.start = start;
		this.end = end;
		this.source = source;
		this.trace = trace;
		enclosure = null;
	}

	public List<String> getTrace() {
		return trace;
	}

	public SourceLocation getEnclosure() {
		return enclosure;
	}

	public SourceLocation extend(SourceLocation other) {
		if (other.start < start) {
			start = other.start;
		}
		if (other.end > end) {
			end = other.end;
		}
		if (other.source != null && source == null) {
			source = other.source;
		}
		return this;
	}

	public SourceLocation copyAndExtend(SourceLocation other) {
		final SourceLocation copy = new SourceLocation(this);
		if (other.start < start) {
			copy.start = other.start;
		}
		if (other.end > end) {
			copy.end = other.end;
		}
		if (other.source != null && source == null) {
			copy.source = other.source;
		}
		return copy;
	}

	public String getSource() {
		return source;
	}

	/**
	 * Character offset in the input file of the given location start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Character offset in the input file of the given location end
	 */
	public int getEnd() {
		return end;
	}

	/*
	 * The span of the location
	 */
	public int span() {
		return end - start;
	}

	public boolean hasLength() {
		return start != end;
	}

	public boolean contains(int offset) {
		return offset >= start && offset < end;
	}

	@Override
	public String toString() {
		return asString();
	}

	public String asString() {
		if (this == Dummy) {
			return "?";
		} else {
			return (source == null ? "?" : source) + ":" + start + "-" + end + "::" + trace.toString();
		}
	}

	public int distance(SourceLocation other) {
		if (end < other.start) {
			return other.start - end;
		} else if (start > other.end) {
			return start - other.end;
		} else {
			return 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enclosure == null ? 0 : enclosure.hashCode());
		result = prime * result + end;
		result = prime * result + (source == null ? 0 : source.hashCode());
		result = prime * result + start;
		result = prime * result + (trace == null ? 0 : trace.hashCode());
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
		final SourceLocation other = (SourceLocation) obj;
		if (enclosure == null) {
			if (other.enclosure != null) {
				return false;
			}
		} else if (!enclosure.equals(other.enclosure)) {
			return false;
		}
		if (end != other.end) {
			return false;
		}
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!source.equals(other.source)) {
			return false;
		}
		if (start != other.start) {
			return false;
		}
		if (trace == null) {
			if (other.trace != null) {
				return false;
			}
		} else if (!trace.equals(other.trace)) {
			return false;
		}
		return true;
	}

}
