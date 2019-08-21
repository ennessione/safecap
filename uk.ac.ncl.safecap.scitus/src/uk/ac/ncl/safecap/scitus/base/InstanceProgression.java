package uk.ac.ncl.safecap.scitus.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InstanceProgression extends Progression implements Serializable {
	private static final long serialVersionUID = 2670748833931170080L;
	private List<InstanceEvent> progressions;
	private double offset;

	public InstanceProgression(double offset) {
		super(null);
		this.offset = offset;
		progressions = new ArrayList<>();
	}

	public InstanceProgression() {
		super(null);
	}

	public List<InstanceEvent> getInstances() {
		return progressions;
	}

	public void add(InstanceEvent event) {
		progressions.add(event);
	}

	public double getOffset() {
		return offset;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("INSTANCE " + offset + ": ");

		for (final InstanceEvent ie : progressions) {
			sb.append(ie.toString() + "; ");
		}

		return sb.toString();
	}

	public InstanceProgression copy() {
		final InstanceProgression ip = new InstanceProgression(offset);
		ip.progressions = new ArrayList<>(progressions);
		return ip;
	}
}
