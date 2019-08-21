package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.List;

import safecap.Orientation;
import safecap.Project;
import safecap.model.Line;
import safecap.schema.Segment;
import safecap.trackside.LeftStopAndGo;
import safecap.trackside.RightStopAndGo;
import safecap.trackside.StopAndGo;
import safecap.trackside.Wire;

public class PlatformUtil {
	public static Segment getSegment(StopAndGo platform) {
		final Project project = EmfUtil.getProject(platform);
		for (final Wire wire : project.getWires()) {
			if (wire.getFrom() == platform) {
				return wire.getTo();
			}
		}
		return null;
	}

	public static List<Line> getAllValidLines(StopAndGo platform) {
		final List<Line> validLines = new ArrayList<>();
		final Project project = EmfUtil.getProject(platform);
		final Segment segment = getSegment(platform);
		if (segment == null) {
			return validLines;
		}

		final List<Line> lines = project.getLines();
		for (final Line line : lines) {
			if (line.getOrientation() == Orientation.ANY || line.getOrientation() == Orientation.LEFT && platform instanceof LeftStopAndGo
					|| line.getOrientation() == Orientation.RIGHT && platform instanceof RightStopAndGo) {
				if (LineUtil.containsSegment(line, segment)) {
					validLines.add(line);
				}
			}
		}
		return validLines;
	}
}
