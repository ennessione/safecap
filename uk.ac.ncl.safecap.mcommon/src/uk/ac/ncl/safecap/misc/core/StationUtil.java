package uk.ac.ncl.safecap.misc.core;

import safecap.Project;
import safecap.model.Line;
import safecap.schema.Segment;
import safecap.trackside.LeftStopAndGo;
import safecap.trackside.RightStopAndGo;
import safecap.trackside.Wire;

public class StationUtil {
	public static Wire getLeftStationWireOn(Segment segment, Line line) {
		final Project root = EmfUtil.getProject(segment);
		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof LeftStopAndGo) {
				final LeftStopAndGo slimit = (LeftStopAndGo) wire.getFrom();
				if (slimit.getLine().contains(line)) {
					return wire;
				}
			}
		}

		return null;
	}

	public static Wire getRightStationWireOn(Segment segment, Line line) {
		final Project root = EmfUtil.getProject(segment);
		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof RightStopAndGo) {
				final RightStopAndGo slimit = (RightStopAndGo) wire.getFrom();
				if (slimit.getLine().contains(line)) {
					return wire;
				}
			}
		}

		return null;
	}
}
