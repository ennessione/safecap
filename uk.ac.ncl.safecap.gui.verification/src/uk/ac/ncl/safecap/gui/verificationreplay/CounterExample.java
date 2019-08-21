package uk.ac.ncl.safecap.gui.verificationreplay;

import java.util.ArrayList;
import java.util.List;

public class CounterExample {
	private List<TraceStep> steps;
	private int current;

	public CounterExample(String raw_trace) {
		if (raw_trace == null || raw_trace.length() == 0) {
			return;
		}

		final String[] parts = raw_trace.split("\\n");
		steps = new ArrayList<>(parts.length);
		for (final String p : parts) {
			final TraceStep step = new TraceStep(p);
			steps.add(step);
		}
	}

	public List<TraceStep> getSteps() {
		return steps;
	}

	public TraceStep getNext() {
		if (current < steps.size() - 1) {
			return steps.get(current++);
		} else {
			return null;
		}
	}

	public TraceStep getPrev() {
		if (current > 0) {
			return steps.get(current--);
		} else {
			return null;
		}
	}

}
