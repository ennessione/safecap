package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import safecap.Orientation;
import safecap.Project;
import safecap.model.Ambit;
import safecap.model.Route;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.LeftSide;
import safecap.trackside.LeftSignal;
import safecap.trackside.RedLeftSignal;
import safecap.trackside.RedRightSignal;
import safecap.trackside.RightSignal;
import safecap.trackside.Signal;
import safecap.trackside.SignalType;
import safecap.trackside.Wire;

public class SignalUtil {

	public static boolean isWarnerSignal(Signal signal) {
		return ExtensionUtil.getBool(signal, SafecapConstants.EXT_SIGNAL, SafecapConstants.SIGNAL_WARNER);
	}

	public static boolean isAuto(Signal signal) {
		return signal.getType() == SignalType.AUTOMATIC;
	}

	public static boolean isDistantOrRepeater(Signal signal) {
		return signal.getType() == SignalType.DISTANT || signal.getType() == SignalType.BANNER_REPEATER;
	}

	public static boolean isControlled(Signal signal) {
		return signal.getType() == SignalType.CONTROLLED;
	}

	public static Signal getByNumberCode(Project root, String code, String prefix) {
		final Signal s = getByNumberCodeWithPrefix(root, code, prefix);
		if (s == null) {
			return getByNumberCodeNoPrefix(root, code);
		} else {
			return s;
		}
	}

	public static int getSignalOffsetFromJoint(Project root, Signal signal) {
		for (final Wire wire : root.getWires()) {
			if (wire.getFrom() == signal) {
				final int startOffset = wire.getOffset();
				final Segment part = SignalUtil.getSignalSegment(signal);
				if (part != null) {
					return part.getLength() - startOffset > 0 ? part.getLength() - startOffset : 0;
				}
			}
		}

		return 0;
	}

	public static Wire getSignalWire(Project root, Signal signal) {
		for (final Wire wire : root.getWires()) {
			if (wire.getFrom() == signal) {
				return wire;
			}
		}

		return null;
	}

	public static Signal getByNumberCodeNoPrefix(Project root, String code) {
		Pattern pattern = Pattern.compile(code, Pattern.LITERAL);
		pattern = Pattern.compile("[A-Z]*" + pattern);

		for (final Equipment s : root.getEquipment()) {
			if (s instanceof Signal) {
				final Signal signal = (Signal) s;
				if (signal.getLabel() != null) {
					final Matcher matcher = pattern.matcher(signal.getLabel());
					if (matcher.matches()) {
						return signal;
					}
				}
			}
		}

		return null;
	}

	public static Signal getByNumberCodeWithPrefix(Project root, String code, String prefix) {
		final Pattern prefixPattern = Pattern.compile(prefix, Pattern.LITERAL);
		Pattern pattern = Pattern.compile(code, Pattern.LITERAL);
		pattern = Pattern.compile("" + prefixPattern + pattern);

		for (final Equipment s : root.getEquipment()) {
			if (s instanceof Signal) {
				final Signal signal = (Signal) s;
				if (signal.getLabel() != null) {
					final Matcher matcher = pattern.matcher(signal.getLabel());
					if (matcher.matches()) {
						return signal;
					}
				}
			}
		}

		return null;
	}

	public static Signal getByLabel(Project root, String label) {
		if (label == null) {
			return null;
		}

		for (final Equipment s : root.getEquipment()) {
			if (s instanceof Signal && label.equals(s.getLabel())) {
				return (Signal) s;
			}
		}

		return null;
	}

	public static LeftSignal getLeftSignalOn(Segment segment) {
		final Project root = EmfUtil.getProject(segment);
		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof LeftSignal) {
				return (LeftSignal) wire.getFrom();
			}
		}

		return null;
	}

	public static Signal getSignalOn(Segment segment, Orientation orientation) {
		return orientation == Orientation.LEFT ? getLeftSignalOn(segment) : getRightSignalOn(segment);
	}

	public static Wire getLeftSignalWireOn(Segment segment) {
		final Project root = EmfUtil.getProject(segment);
		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof LeftSignal) {
				return wire;
			}
		}

		return null;
	}

	public static RedLeftSignal getRedLeftSignalOn(Segment segment) {
		final Project root = EmfUtil.getProject(segment);
		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof RedLeftSignal) {
				return (RedLeftSignal) wire.getFrom();
			}
		}

		return null;
	}

	public static RightSignal getRightSignalOn(Segment segment) {
		final Project root = EmfUtil.getProject(segment);
		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof RightSignal) {
				return (RightSignal) wire.getFrom();
			}
		}

		return null;
	}

	public static Wire getRightSignalWireOn(Segment segment) {
		final Project root = EmfUtil.getProject(segment);
		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof RightSignal) {
				return wire;
			}
		}

		return null;
	}

	public static RedRightSignal getRedRightSignalOn(Segment segment) {
		final Project root = EmfUtil.getProject(segment);
		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == segment && wire.getFrom() instanceof RedRightSignal) {
				return (RedRightSignal) wire.getFrom();
			}
		}

		return null;
	}

	public static List<AmbitPath> getAllRoutes(Project root) {
		final List<AmbitPath> result = new ArrayList<>();

		for (final Node node : root.getNodes()) {
			if (node.getKind() == NodeKind.BOUNDARY && NodeUtil.isSource(node)) {
				final List<AmbitPath> paths = getRouteAmbitPaths(node);

				for (final AmbitPath p : paths) {

					// reject paths that do not end on sink nodes (but rather
					// end on some other nodes)
					if (p.getTo() instanceof Node) {
						final Node boundary = (Node) p.getTo();
						if (NodeUtil.isSink(boundary)) {
							result.add(p);
						}
					} else {
						result.add(p);
					}
				}
			}
		}

		for (final Equipment _s : root.getEquipment()) {
			if (_s instanceof LeftSignal || _s instanceof RightSignal) {
				final Signal s = (Signal) _s;

				result.addAll(getRouteAmbitPaths(s));
			}
		}

		return result;
	}

	public static List<AmbitPath> getRouteAmbitPaths(Node node) {
		final List<AmbitPath> result = new ArrayList<>();
		final Project root = EmfUtil.getProject(node);
		final Map<Segment, Ambit> map = AmbitUtil.getSegmentAmbitMap(root);

		final List<SegmentPath> seg_paths = getRouteSegmentPaths(node, false);

		for (final SegmentPath path : seg_paths) {
			final AmbitPath ambit_path = new AmbitPath(path, map, AmbitUtil.getAmbitToComposedMap(root));
			result.add(ambit_path);
		}

		return result;
	}

	public static List<AmbitPath> getRouteAmbitPaths(Signal signal) {
		final List<AmbitPath> result = new ArrayList<>();
		final Project root = EmfUtil.getProject(signal);
		final Map<Segment, Ambit> map = AmbitUtil.getSegmentAmbitMap(root);

		final List<SegmentPath> seg_paths = getRouteSegmentPaths(signal);

		for (final SegmentPath path : seg_paths) {
			final AmbitPath ambit_path = new AmbitPath(path, map, AmbitUtil.getAmbitToComposedMap(root));
			result.add(ambit_path);
		}

		return result;
	}

	public static List<Route> getRoutesProtectedBy(Signal signal) {
		final Project root = EmfUtil.getProject(signal);
		final List<Route> routes = new ArrayList<>();
		final List<SegmentPath> paths = SignalUtil.getRouteSegmentPaths(signal);
		final List<List<Segment>> segmentList = new ArrayList<>();
		for (final SegmentPath path : paths) {
			segmentList.add(path.getPath());
		}
		for (final Route route : root.getRoutes()) {
			if (segmentList.contains(route.getSegments()) && isOrientationMatch(signal, route)) {
				routes.add(route);
			}
		}
		return routes;
	}

	public static boolean isOrientationMatch(Signal signal, Route route) {
		return route.getOrientation() == Orientation.LEFT && signal instanceof LeftSignal
				|| route.getOrientation() == Orientation.RIGHT && signal instanceof RightSignal;
	}

	public static Set<Route> getRoutesFor(Signal signal) {
		final Set<Route> result = new HashSet<>();

		final Project root = EmfUtil.getProject(signal);
		Segment segment = null;

		for (final Wire wire : root.getWires()) {
			if (wire.getFrom() == signal) {
				segment = wire.getTo();
				break;
			}
		}

		if (segment == null) {
			return result;
		}

		List<Segment> seg;

		if (signal instanceof LeftSignal) {
			seg = SegmentUtil.getRightSegments(segment);
		} else {
			seg = SegmentUtil.getLeftSegments(segment);
		}

		if (seg.size() != 1) {
			return result;
		}

		result.addAll(SegmentUtil.getSegmentRoutes(segment));

		return result;
	}

	public static Segment getSignalSegment(Signal signal) {
		final Project root = EmfUtil.getProject(signal);
		if (root != null) {
			for (final Wire wire : root.getWires()) {
				if (wire.getFrom() == signal) {
					return wire.getTo();
				}
			}
		}

		return null;
	}

	public static Node getSignalNode(Signal signal) {
		final Segment startSegment = SignalUtil.getSignalSegment(signal);
		if (startSegment == null) {
			return null;
		}
		if (signal instanceof LeftSignal) {
			return startSegment.getTo();
		} else if (signal instanceof RightSignal) {
			return startSegment.getFrom();
		}

		return null;
	}

	public static List<SegmentPath> getRouteSegmentPaths(Signal signal, String... ignore) {
		final List<SegmentPath> result = new ArrayList<>();
		final Project root = EmfUtil.getProject(signal);
		Segment segment = null;

		for (final Wire wire : root.getWires()) {
			if (wire.getFrom() == signal) {
				segment = wire.getTo();
				break;
			}
		}

		if (segment == null) {
			return result;
		}

		if (signal instanceof LeftSignal) {
			final List<Segment> seg = SegmentUtil.getRightSegments(segment);
			if (seg.size() != 1) {
				return result;
			}

			final SegmentPath spath = new SegmentPath(Orientation.LEFT, signal);
			spath.setFromNode(segment.getTo());

			walkRight(seg.get(0), spath, result, false, ignore);
			return result;
		} else {
			final List<Segment> seg = SegmentUtil.getLeftSegments(segment);
			if (seg.size() != 1) {
				return result;
			}

			final SegmentPath spath = new SegmentPath(Orientation.RIGHT, signal);
			spath.setFromNode(segment.getFrom());
			walkLeft(seg.get(0), spath, result, false, ignore);
			return result;
		}
	}

	public static List<SegmentPath> getRouteSegmentPaths(Node node, boolean ignore_signals) {
		final List<SegmentPath> result = new ArrayList<>();

		if (node.getKind() != NodeKind.BOUNDARY) {
			return result;
		}

		final List<Segment> in = NodeUtil.getIncomingSegments(node);
		final List<Segment> out = NodeUtil.getOutgoingSegments(node);

		if (in.size() == 0) {
			if (out.size() != 1) {
				return result;
			}
			final SegmentPath spath = new SegmentPath(Orientation.LEFT, node);
			spath.setFromNode(node);
			walkRight(out.get(0), spath, result, ignore_signals);
			return result;
		} else {
			if (in.size() != 1) {
				return result;
			}
			final SegmentPath spath = new SegmentPath(Orientation.RIGHT, node);
			spath.setFromNode(node);
			walkLeft(in.get(0), spath, result, ignore_signals);
			return result;
		}
	}

	private static void walkRight(Segment current, SegmentPath path, List<SegmentPath> result, boolean ignore_signals, String... ignore) {
		path.getPath().add(current);
		final LeftSignal signal = getLeftSignalOn(current);

		if (signal != null && !ignore_signals && !inArray(ignore, signal)) {

			path.setTo(signal);
			path.setToNode(current.getTo());
			result.add(path);
			return;

		}

		final RedLeftSignal red_signal = getRedLeftSignalOn(current);
		if (red_signal != null) {
			return;
		}

		final Node node = current.getTo();
		if (node.getKind() == NodeKind.BOUNDARY) {
			if (NodeUtil.isSink(node)) {
				path.setTo(node);
				path.setToNode(node);
				result.add(path);
			}
			return;
		}

		final List<Segment> seg = SegmentUtil.getRightSegments(current);

		if (seg.size() > 0) {
			for (int i = 1; i < seg.size(); i++) {
				final SegmentPath path_copy = new SegmentPath(Orientation.LEFT, path.getFrom());
				path_copy.setFromNode(path.getFromNode());
				path_copy.getPath().addAll(path.getPath());
				walkRight(seg.get(i), path_copy, result, ignore_signals, ignore);
			}
			walkRight(seg.get(0), path, result, ignore_signals, ignore);
		}
	}

	private static void walkLeft(Segment current, SegmentPath path, List<SegmentPath> result, boolean ignore_signals, String... ignore) {
		final RightSignal signal = getRightSignalOn(current);
		path.getPath().add(current);

		if (signal != null && !ignore_signals && !inArray(ignore, signal)) {

			path.setTo(signal);
			path.setToNode(current.getFrom());
			result.add(path);
			return;
		}

		final RedRightSignal red_signal = getRedRightSignalOn(current);
		if (red_signal != null && !inArray(ignore, signal)) {
			// discard the current path
			return;
		}

		final Node node = current.getFrom();
		if (node.getKind() == NodeKind.BOUNDARY) {
			if (NodeUtil.isSink(node)) {
				path.setTo(node);
				path.setToNode(node);
				result.add(path);
			}
			return;
		}

		final List<Segment> seg = SegmentUtil.getLeftSegments(current);

		if (seg.size() > 0) {
			for (int i = 1; i < seg.size(); i++) {
				final SegmentPath path_copy = new SegmentPath(Orientation.RIGHT, path.getFrom());
				path_copy.setFromNode(path.getFromNode());
				path_copy.getPath().addAll(path.getPath());
				walkLeft(seg.get(i), path_copy, result, ignore_signals, ignore);
			}
			// System.out.println(seg.get(0).getLabel());
			// if (seg.get(0).getLabel().equals("TJRP")) {
			// System.out.print(true);
			// }

			walkLeft(seg.get(0), path, result, ignore_signals, ignore);
		}
	}

	private static boolean inArray(String[] array, Signal signal) {
		if (signal == null || signal.getLabel() == null || array == null) {
			return false;
		}

		for (final String s : array) {
			if (signal.getLabel().equals(s)) {
				return true;
			}
		}

		return false;
	}

	public static void renameSignals(Project root, String patternString, String template) {
		final Pattern pattern = Pattern.compile(patternString);

		for (final Equipment eqp : root.getEquipment()) {
			if (eqp instanceof Signal) {
				final Signal s = (Signal) eqp;
				if (s.getLabel() != null) {
					final Matcher matcher = pattern.matcher(s.getLabel());
					if (matcher.matches()) {
						String newSignalName = template;
						for (int i = 1; i < matcher.groupCount() + 1; i++) {
							final String value = matcher.group(i);
							newSignalName = newSignalName.replaceAll("\\$" + i, value);
						}
						s.setLabel(newSignalName);
					}
				}
			}
		}
	}

	public static Orientation getSignalOrientation(Signal exit) {
		if (exit instanceof LeftSide) {
			return Orientation.LEFT;
		} else {
			return Orientation.RIGHT;
		}
	}

}
