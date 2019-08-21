package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.List;

import safecap.Orientation;
import safecap.Project;
import safecap.model.Line;
import safecap.schema.Segment;
import safecap.trackside.LeftSpeedLimit;
import safecap.trackside.RightSpeedLimit;
import safecap.trackside.SpeedLimit;
import safecap.trackside.Wire;

public class SpeedLimitUtil {

	public static LeftSpeedLimit getLeftSpeedLimitOn(Segment segment) {
		final Project root = EmfUtil.getProject(segment);
		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof LeftSpeedLimit) {
				final LeftSpeedLimit slimit = (LeftSpeedLimit) wire.getFrom();
				return slimit;
			}
		}

		return null;
	}

	public static RightSpeedLimit getRightSpeedLimitOn(Segment segment) {
		final Project root = EmfUtil.getProject(segment);
		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof RightSpeedLimit) {
				final RightSpeedLimit slimit = (RightSpeedLimit) wire.getFrom();
				return slimit;
			}
		}

		return null;
	}

	public static SpeedLimit getSpeedLimitOn(Segment segment, Line line) {
		final Project root = EmfUtil.getProject(segment);
		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof SpeedLimit) {
				final SpeedLimit slimit = (SpeedLimit) wire.getFrom();
				if (slimit.getLine().contains(line)) {
					return slimit;
				}
			}
		}

		return null;
	}

	public static LeftSpeedLimit getLeftSpeedLimitOn(Segment segment, Line line) {
		final Project root = EmfUtil.getProject(segment);
		if (root == null) {
			return null;
		}

		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof LeftSpeedLimit) {
				final LeftSpeedLimit slimit = (LeftSpeedLimit) wire.getFrom();
				if (slimit.getLine().contains(line)) {
					return slimit;
				}
			}
		}

		return null;
	}

	public static RightSpeedLimit getRightSpeedLimitOn(Segment segment, Line line) {
		final Project root = EmfUtil.getProject(segment);
		if (root == null) {
			return null;
		}

		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof RightSpeedLimit) {
				final RightSpeedLimit slimit = (RightSpeedLimit) wire.getFrom();
				if (slimit.getLine().contains(line)) {
					return slimit;
				}
			}
		}

		return null;
	}

	public static Wire getLeftSpeedLimitWireOn(Segment segment, Line line) {
		final Project root = EmfUtil.getProject(segment);
		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof LeftSpeedLimit) {
				final LeftSpeedLimit slimit = (LeftSpeedLimit) wire.getFrom();
				if (slimit.getLine().contains(line)) {
					return wire;
				}
			}
		}

		return null;
	}

	public static Wire getRightSpeedLimitWireOn(Segment segment, Line line) {
		final Project root = EmfUtil.getProject(segment);
		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof RightSpeedLimit) {
				final RightSpeedLimit slimit = (RightSpeedLimit) wire.getFrom();
				if (slimit.getLine().contains(line)) {
					return wire;
				}
			}
		}

		return null;
	}

	public static Segment getSegment(SpeedLimit speedLimit) {
		final Project project = EmfUtil.getProject(speedLimit);
		for (final Wire wire : project.getWires()) {
			if (wire.getFrom() == speedLimit) {
				return wire.getTo();
			}
		}
		return null;
	}

	public static List<Line> getAllValidLines(SpeedLimit speedLimit) {
		final List<Line> validLines = new ArrayList<>();
		final Project project = EmfUtil.getProject(speedLimit);
		final Segment segment = getSegment(speedLimit);
		if (segment == null) {
			return validLines;
		}

		final List<Line> lines = project.getLines();
		for (final Line line : lines) {
			if (line.getOrientation() == Orientation.ANY
					|| line.getOrientation() == Orientation.LEFT && speedLimit instanceof LeftSpeedLimit
					|| line.getOrientation() == Orientation.RIGHT && speedLimit instanceof RightSpeedLimit) {
				if (LineUtil.containsSegment(line, segment)) {
					validLines.add(line);
				}
			}
		}
		return validLines;
	}

}
