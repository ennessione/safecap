package uk.ac.ncl.safecap.misc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import safecap.Orientation;
import safecap.model.Ambit;
import safecap.model.ControlLogic;
import safecap.model.Line;
import safecap.model.Point;
import safecap.model.Route;
import safecap.schema.HeightPoint;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.PointRole;
import safecap.schema.Segment;
import safecap.schema.SubSchemaNode;
import safecap.schema.SubSchemaPath;
import safecap.trackside.Equipment;
import safecap.trackside.LeftSpeedLimit;
import safecap.trackside.RightSpeedLimit;
import safecap.trackside.Signal;
import safecap.trackside.SpeedLimit;
import safecap.trackside.StopAndGo;
import safecap.trackside.Wire;

public class TrainLine implements Serializable {
	private static final long serialVersionUID = -8342914271534402469L;
	private final Line line;
	private List<Segment> path;
	private List<Ambit> ambit_path;
	private Map<Segment, Ambit> seg_ambit_map;
	private Map<Segment, Route> seg_route_map;
	private Map<Segment, Integer> dist_seg_map;
	private Map<Ambit, Double> ambit_length;
	private Map<Ambit, Double> ambit_offset;
	private Map<Equipment, Double> speed_limit_object; // the absolute position
														// of a speed limit
														// object on a given
														// line
	private Map<Route, ControlLogic> route_logic_map;
	private Map<Route, Signal> guardingsignal;
	private Map<Signal, Double> signalpos;

	private TreeMap<Double, Double> gradient_map;
	private int lineLength;
	private Double entryspeed = null;
	private Double exitspeed = null;

	private final Node startNode, endNode;

	private List<Point> normalPoints;
	private List<Point> reversePoints;

	public TrainLine(Line line) {
		this.line = line;
		buildSegmentPath();
		buildAmbitPath();
		buildMaps();
		buildGradientMap();
		buildPoints();

		startNode = LineUtil.getStartNode(line);
		endNode = LineUtil.getEndNode(line);

		// if (sequences == null) {
		// sequences = new HashMap<Point, PointSequence>();
		// readSequences("zz");
		// }

		if (startNode.getSpeedlimit() > 0) {
			entryspeed = new Double(startNode.getSpeedlimit());
		}

		if (endNode.getSpeedlimit() > 0) {
			exitspeed = new Double(endNode.getSpeedlimit());
		}

	}

	private void buildPoints() {
		normalPoints = new ArrayList<>();
		reversePoints = new ArrayList<>();
		for (final Segment segment : path) {
			checkSegmentForPoints(segment);
		}
	}

	public List<Point> getNormalPoints() {
		return normalPoints;
	}

	public List<Point> getReversePoints() {
		return reversePoints;
	}

	private void checkSegmentForPoints(Segment segment) {

		if (segment.getPointrole() != PointRole.NONE) {
			final Node left = segment.getFrom();
			final Node right = segment.getTo();

			Node point_node = null;
			if (left != null && left.getKind() == NodeKind.POINT) {
				point_node = left;
			} else if (right != null && right.getKind() == NodeKind.POINT) {
				point_node = right;
			}

			if (point_node != null) {
				final Point point = PointUtil.getPointFor(point_node);
				if (segment.getPointrole() == PointRole.NORMAL) {
					normalPoints.add(point);
				} else if (segment.getPointrole() == PointRole.REVERSE) {
					reversePoints.add(point);
				}
			}
		}
	}

	public double getRouteSignalPosition(Route route) {
		if (RouteUtil.getEntryNode(route).getKind() == NodeKind.BOUNDARY) {
			return 0;
		}

		final Signal signal = getGuardian(route);
		return getSignalPosition(signal);
	}

	public Node getStartNode() {
		return startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	public double getGradient(double offset) {
		final Entry<Double, Double> x = gradient_map.ceilingEntry(offset);
		if (x == null) {
			return 0;
		} else {
			return x.getValue();
		}
	}

	public double getGradientPoint(double offset) {
		final Entry<Double, Double> x = gradient_map.ceilingEntry(offset);
		if (x == null) {
			return Double.NaN;
		} else {
			return x.getKey();
		}
	}

	public SortedMap<Double, Double> getGradientMap() {
		return gradient_map;
	}

	private void buildGradientMap() {

		gradient_map = new TreeMap<>();

		for (final Segment s : path) {
			final double s_offset = getSegmentOffset(s);
			if (s.getGradient() != null && s.getGradient().length() > 0) {
				try {
					final double gradient = Double.parseDouble(s.getGradient()) / 100.0;
					gradient_map.put(s_offset + s.getLength(), gradient);
					continue;
				} catch (final NumberFormatException e) {
					// ignore
				}
			}
			if (line.getOrientation() == Orientation.LEFT) {
				final Node a = s.getFrom();
				double hgt = a.getAltitude();
				double pos = s_offset;
				if (s.getHeightmap() != null && s.getHeightmap().getPoints() != null) {
					for (final HeightPoint p : s.getHeightmap().getPoints()) {
						final double g = (p.getAltitude() - hgt) / (p.getPosition() - pos);
						hgt = p.getAltitude();
						pos = p.getPosition();
						gradient_map.put(pos, g);
					}
				}
				final Node b = s.getTo();
				final double g = (b.getAltitude() - hgt) / (s_offset + s.getLength() - pos);

				gradient_map.put(s_offset + s.getLength(), g);
			} else {
				final Node a = s.getTo();
				double hgt = a.getAltitude();
				double pos = s_offset;
				if (s.getHeightmap() != null && s.getHeightmap().getPoints() != null) {
					for (final HeightPoint p : s.getHeightmap().getPoints()) {
						final double g = (p.getAltitude() - hgt) / (p.getPosition() - pos);
						hgt = p.getAltitude();
						pos = p.getPosition();
						gradient_map.put(pos, g);
					}
				}
				final Node b = s.getFrom();
				final double g = (b.getAltitude() - hgt) / (s_offset + s.getLength() - pos);

				gradient_map.put(s_offset + s.getLength(), g);
			}
		}
	}

	public boolean hasEntrySpeedLimit() {
		return entryspeed != null;
	}

	public boolean hasExitSpeedLimit() {
		return exitspeed != null;
	}

	public double getExitSpeedLimit() {
		if (exitspeed != null) {
			return exitspeed;
		} else {
			return SafecapConstants.DEFAULT_SPEED_LIMIT;
		}
	}

	public double getEntrySpeedLimit() {
		if (entryspeed != null) {
			return entryspeed;
		} else {
			return SafecapConstants.DEFAULT_SPEED_LIMIT;
		}
	}

	public Ambit getNextAmbit(Ambit route) {
		final int index = getAmbitPath().indexOf(route);
		if (index < getAmbitPath().size() - 1) {
			return getAmbitPath().get(index + 1);
		}

		return null;
	}

	public Route getNextRoute(Route route) {
		final int index = getSchemaLine().getRoutes().indexOf(route);
		if (index < getSchemaLine().getRoutes().size() - 1) {
			return getSchemaLine().getRoutes().get(index + 1);
		}

		return null;
	}

	private void buildMaps() {
		seg_route_map = new HashMap<>();
		seg_ambit_map = new HashMap<>();
		dist_seg_map = new HashMap<>();
		ambit_length = new HashMap<>();
		ambit_offset = new HashMap<>();
		speed_limit_object = new HashMap<>();
		route_logic_map = new HashMap<>();

		guardingsignal = new HashMap<>();
		signalpos = new HashMap<>();

		// signal_sw = new HashMap<Signal, Stopwatch>();
		// route_stopwatch_map = new HashMap<Route, Stopwatch>(); //probably
		// won't use; not sure yet

		for (final Route r : line.getRoutes()) {
			final EList<Ambit> ambits = r.getAmbits();
			for (final Ambit a : ambits) {
				for (final Segment s : a.getSegments()) {
					seg_route_map.put(s, r);
					seg_ambit_map.put(s, a);
				}
			}
			final ControlLogic logic = RuleUtil.getControlLogic(r, line);
			if (logic != null) {
				route_logic_map.put(r, logic);
			}
		}

		lineLength = buildSegmentDistance();

		buildAmbitOffsetAndLengthMaps();
		buildSpeedLimitObjectMap();

		buildGuardingSignals();
	}

	private int buildSegmentDistance() {
		int dist = 0;
		for (int i = 0; i < path.size(); i++) {
			final Segment s = path.get(i);
			dist_seg_map.put(s, dist);
			dist += s.getLength();
			if (i < path.size() - 1) {
				dist += getSegmentExtraLength(s, path.get(i + 1));
			}
		}
		return dist;
	}

	private int getSegmentExtraLength(Segment current, Segment next) {
		Node node;
		if (line.getOrientation() == Orientation.LEFT) {
			node = current.getTo();
		} else {
			node = current.getFrom();
		}

		if (node instanceof SubSchemaNode) {
			final SubSchemaNode ssn = (SubSchemaNode) node;
			final String port_to = ExtensionUtil.getString(current, "og.port", "to");
			final String port_from = ExtensionUtil.getString(next, "og.port", "from");

			if (port_to != null && port_from != null) {
				for (final SubSchemaPath path : ssn.getPaths()) {
					if (port_to.equals(path.getFrom()) && port_from.equals(path.getTo())) {
						return path.getLength();
					}
				}
			}
		}

		return 0;
	}

	/**
	 * Builds guarding signals along with stopwatch object for every signal in the
	 * system.
	 */
	private void buildGuardingSignals() {
		// collect all speed limit signs
		if (line.getOrientation() == Orientation.LEFT) {
			for (final Segment s : getPath()) {
				final Wire sl = SignalUtil.getLeftSignalWireOn(s);
				if (sl != null) {
					final double s_pos = getSegmentOffset(s);
					double s_off = sl.getOffset();
					if (s_off == 0) {
						s_off = s.getLength();
					}
					signalpos.put((Signal) sl.getFrom(), s_pos + s_off);
				}
			}
		} else {
			for (final Segment s : getPath()) {
				final Wire sl = SignalUtil.getRightSignalWireOn(s);
				if (sl != null) {
					final double s_pos = getSegmentOffset(s);
					double s_off = sl.getOffset();
					if (s_off == 0) {
						s_off = s.getLength();
					}
					signalpos.put((Signal) sl.getFrom(), s_pos + s_off);
				}
			}
		}
		for (final Signal s : signalpos.keySet()) {
			final List<SegmentPath> paths = SignalUtil.getRouteSegmentPaths(s);
			for (final SegmentPath sp : paths) {
				for (final Route r : line.getRoutes()) {
					if (r.getSegments().equals(sp.getPath())) {
						guardingsignal.put(r, s);
					}
				}
			}
		}
	}

	public Signal getGuardian(Route route) {
		return guardingsignal.get(route);
	}

	public Double getSignalPosition(Signal signal) {
		return signalpos.get(signal);
	}

	public Ambit getContainingAmbit(double position) {
		double dist = 0;
		for (final Ambit a : ambit_path) {
			dist += ambit_length.get(a);
			if (dist > position) {
				return a;
			}
		}

		return null;
	}

	public Signal getNextSignal(double position) {
		Signal result = null;
		double min_distance = Double.POSITIVE_INFINITY;
		for (final Signal s : signalpos.keySet()) {
			final double p = signalpos.get(s);
			if (p > position) {
				final double distance = p - position;
				if (distance < min_distance) {
					result = s;
					min_distance = distance;
				}
			}
		}

		return result;
	}

	public EquipmentPosition getNextSpeedLimit(double position) {
		SpeedLimit result = null;
		double min_distance = Double.POSITIVE_INFINITY;
		for (final EObject o : speed_limit_object.keySet()) {
			if (o instanceof SpeedLimit) {
				final double p = speed_limit_object.get(o);
				if (p >= position) {
					final double distance = p - position;
					if (distance < min_distance) {
						result = (SpeedLimit) o;
						min_distance = distance;
					}
				}
			}
		}

		return new EquipmentPosition(result, min_distance);
	}

	public EquipmentPosition getNextStation(double position) {
		StopAndGo result = null;
		double min_distance = Double.POSITIVE_INFINITY;
		for (final EObject o : speed_limit_object.keySet()) {
			if (o instanceof StopAndGo) {
				final double p = speed_limit_object.get(o);
				if (p >= position) {
					final double distance = p - position;
					if (distance < min_distance) {
						result = (StopAndGo) o;
						min_distance = distance;
					}
				}
			}
		}

		return new EquipmentPosition(result, min_distance);
	}

	public Map<EObject, Double> getSpeedLimitersOnHorizon(double position, double horizon) {
		final Map<EObject, Double> result = new HashMap<>(5);

		for (final EObject o : speed_limit_object.keySet()) {
			final double p = speed_limit_object.get(o);
			if (p >= position && p <= position + horizon) {
				result.put(o, p);
			}
		}

		return result;
	}

	public Set<Equipment> getSpeedLimiterObjects() {
		return speed_limit_object.keySet();
	}

	public void insertSpeedLimiterObject(Equipment equipment, double position) {
		speed_limit_object.put(equipment, position);
	}

	public double getSpeedLimiterPosition(EObject object) {
		final Double pos = speed_limit_object.get(object);
		return pos;
	}

	private Double getSegmentSpeedLimit(Segment segment) {
		try {
			if (segment.getSpeedlimit() == null || segment.getSpeedlimit().length() == 0) {
				return null;
			}
			final Double v = Double.parseDouble(segment.getSpeedlimit());
			if (v.isNaN() || v.isInfinite() || v < 0) {
				return null;
			} else {
				return v;
			}
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	private void buildSpeedLimitObjectMap() {
		// collect all speed limit signs
		if (line.getOrientation() == Orientation.LEFT) {
			for (final Segment s : getPath()) {
				final double s_pos = getSegmentOffset(s);
				final Wire sl = SpeedLimitUtil.getLeftSpeedLimitWireOn(s, line);
				if (sl != null) {
					final double s_off = sl.getOffset();
					speed_limit_object.put(sl.getFrom(), s_pos + s_off);
				} else if (getSegmentSpeedLimit(s) != null) {
					final LeftSpeedLimit eq = safecap.trackside.TracksideFactory.eINSTANCE.createLeftSpeedLimit();
					eq.setLimit(getSegmentSpeedLimit(s));
					speed_limit_object.put(eq, s_pos);
				}
			}
		} else {
			for (final Segment s : getPath()) {
				final Wire sl = SpeedLimitUtil.getRightSpeedLimitWireOn(s, line);
				final double s_pos = getSegmentOffset(s);
				if (sl != null) {
					final double s_off = sl.getOffset();
					speed_limit_object.put(sl.getFrom(), s_pos + s_off);
				} else if (getSegmentSpeedLimit(s) != null) {
					final RightSpeedLimit eq = safecap.trackside.TracksideFactory.eINSTANCE.createRightSpeedLimit();
					eq.setLimit(getSegmentSpeedLimit(s));
					speed_limit_object.put(eq, s_pos);
				}
			}
		}

		// collect station stops
		if (line.getOrientation() == Orientation.LEFT) {
			for (final Segment s : getPath()) {
				final Wire sl = StationUtil.getLeftStationWireOn(s, line);
				if (sl != null) {
					final double s_pos = getSegmentOffset(s);
					final double s_off = sl.getOffset();
					speed_limit_object.put(sl.getFrom(), s_pos + s_off);
				}
			}
		} else {
			for (final Segment s : getPath()) {
				final Wire sl = StationUtil.getRightStationWireOn(s, line);
				if (sl != null) {
					final double s_pos = getSegmentOffset(s);
					final double s_off = sl.getOffset();
					speed_limit_object.put(sl.getFrom(), s_pos + s_off);
				}
			}
		}

	}

	private void buildAmbitOffsetAndLengthMaps() {
		for (final Ambit a : ambit_path) {
			double l = 0d;
			double o = -1d;
			for (final Segment s : path) {
				if (seg_ambit_map.get(s) == a) {
					l += s.getLength();
					if (o == -1d) {
						o = getSegmentOffset(s);
					}
				}
			}
			ambit_length.put(a, l);
			ambit_offset.put(a, o);
		}
	}

	public double getAmbitLength(Ambit ambit) {
		return ambit_length.get(ambit);
	}

	public Double getAmbitOffset(Ambit ambit) {
		return ambit_offset.get(ambit);
	}

	private void buildAmbitPath() {
		ambit_path = new ArrayList<>();

		for (final Route r : line.getRoutes()) {
			final EList<Ambit> ambits = r.getAmbits();
			for (final Ambit a : ambits) {
				if (!ambit_path.contains(a)) {
					ambit_path.add(a);
				}
			}
		}
	}

	private void buildSegmentPath() {
		path = new ArrayList<>();

		for (final Route r : line.getRoutes()) {
			path.addAll(r.getSegments());
		}
	}

	public int getLineLength() {
		return lineLength;
	}

	public Segment getSegment(double distance) {
		return path.get(getSegmentIndex(distance));
	}

	public int getSegmentIndex(double distance) {
		for (int i = 0; i < path.size(); i++) {
			final Segment s = path.get(i);
			double d = (double) dist_seg_map.get(s) + (double) s.getLength();
			if (i < path.size() - 1) {
				d += getSegmentExtraLength(s, path.get(i + 1));
			}
			if (d >= distance) {
				assert dist_seg_map.get(s) <= distance || distance < 0;
				return i;
			}
		}
		return path.size() - 1;
	}

	public double getSegmentOffset(Segment s) {
		return dist_seg_map.get(s);
	}

	public double getPositionInSegment(double distance) {
		final Segment s = getSegment(distance);
		final int d = dist_seg_map.get(s);
		return distance - d;
	}

	public Line getSchemaLine() {
		return line;
	}

	public List<Segment> getPath() {
		return path;
	}

	public List<Ambit> getAmbitPath() {
		return ambit_path;
	}

	public Route getSegmentRoute(Segment s) {
		return seg_route_map.get(s);
	}

	public Ambit getSegmentAmbit(Segment s) {
		return seg_ambit_map.get(s);
	}

	public ControlLogic getControlLogic(Route route) {
		return route_logic_map.get(route);
	}

	@Override
	public String toString() {
		return line.getLabel();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (line == null ? 0 : line.hashCode());
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
		final TrainLine other = (TrainLine) obj;
		if (line == null) {
			if (other.line != null) {
				return false;
			}
		} else if (!line.equals(other.line)) {
			return false;
		}
		return true;
	}

}
