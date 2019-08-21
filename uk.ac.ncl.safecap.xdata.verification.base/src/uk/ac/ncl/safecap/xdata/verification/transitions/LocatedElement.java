package uk.ac.ncl.safecap.xdata.verification.transitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.parser.MultiSourceLocation;
import uk.ac.ncl.prime.sim.parser.SourceLocation;

public class LocatedElement extends LocatedEntity {
	private LocatedEntity enclosure;
	private String[] trace;
	private LocatedElement[] parts;
	private String key;

	public LocatedElement() {

	}

	protected LocatedElement(SourceLocation source) {
		super(source.getStart(), source.getEnd(), source.getSource());

		if (source instanceof MultiSourceLocation) {
			final MultiSourceLocation msl = (MultiSourceLocation) source;
			final List<LocatedElement> _parts = new ArrayList<>(msl.getParts().size());
			insertMSL(msl, _parts);
			parts = _parts.toArray(new LocatedElement[_parts.size()]);
			if (!source.getTrace().isEmpty()) {
				trace = new String[source.getTrace().size()];
				for (int i = 0; i < trace.length; i++) {
					trace[i] = source.getTrace().get(i);
				}
			}
			key = source.getKey();
		} else {
			if (source.getEnclosure() != null) {
				enclosure = new LocatedEntity(source.getEnclosure().getStart(), source.getEnclosure().getEnd(),
						source.getEnclosure().getSource());
			}

			key = source.getKey();

			if (!source.getTrace().isEmpty()) {
				trace = new String[source.getTrace().size()];
				for (int i = 0; i < trace.length; i++) {
					trace[i] = source.getTrace().get(i);
				}
			}
		}
	}

	private void insertMSL(MultiSourceLocation msl, List<LocatedElement> dest) {
		for (int i = 0; i < msl.getParts().size(); i++) {
			final SourceLocation sl = msl.getParts().get(i);
			if (sl instanceof MultiSourceLocation) {
				insertMSL((MultiSourceLocation) sl, dest);
			} else {
				dest.add(new LocatedElement(sl));
			}
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public LocatedEntity getEnclosure() {
		return enclosure;
	}

	public static LocatedElement make(CLExpression expression) {
		if (expression.getLocation() != null) {
			return new LocatedElement(expression.getLocation());
		} else {
			return new LocatedElement();
		}
	}

	public String[] getTrace() {
		return trace;
	}

	public void setTrace(String[] trace) {
		this.trace = trace;
	}

	public String ppLocation() {
		if (trace != null) {
			return Arrays.toString(trace) + ":" + super.toString();
		} else {
			return super.toString();
		}
	}

	public LocatedElement[] getParts() {
		return parts;
	}

	public void setParts(LocatedElement[] parts) {
		this.parts = parts;
	}

	public void setEnclosure(LocatedEntity enclosure) {
		this.enclosure = enclosure;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (enclosure == null ? 0 : enclosure.hashCode());
		result = prime * result + (key == null ? 0 : key.hashCode());
		result = prime * result + Arrays.hashCode(parts);
		result = prime * result + Arrays.hashCode(trace);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final LocatedElement other = (LocatedElement) obj;
		if (enclosure == null) {
			if (other.enclosure != null) {
				return false;
			}
		} else if (!enclosure.equals(other.enclosure)) {
			return false;
		}
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!key.equals(other.key)) {
			return false;
		}
		if (!Arrays.equals(parts, other.parts)) {
			return false;
		}
		if (!Arrays.equals(trace, other.trace)) {
			return false;
		}
		return true;
	}

}
