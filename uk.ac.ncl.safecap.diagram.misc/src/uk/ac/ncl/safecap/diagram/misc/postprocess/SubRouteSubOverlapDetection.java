package uk.ac.ncl.safecap.diagram.misc.postprocess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import safecap.Orientation;
import safecap.Project;
import safecap.derived.MergedAmbit;
import safecap.model.Ambit;
import safecap.model.Route;
import safecap.schema.Node;
import safecap.schema.NodeRole;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.LeftSignal;
import safecap.trackside.RightSignal;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.mcommon.conf.NodePath;
import uk.ac.ncl.safecap.mcommon.conf.SchemaConfig;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.DiagramError;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.Overlap;
import uk.ac.ncl.safecap.misc.core.OverlapUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.misc.core.ScopeB;
import uk.ac.ncl.safecap.misc.core.SignalUtil;
import uk.ac.ncl.safecap.misc.core.SubOverlap;
import uk.ac.ncl.safecap.misc.core.SubRoute;
import uk.ac.ncl.safecap.misc.core.SubRouteUtil;

public class SubRouteSubOverlapDetection {
	private final Project root;
	private final Map<Node, IGraphicalEditPart> map;
	private final IGraphicalEditPart projectPart;
	private final SchemaConfig config;

	public SubRouteSubOverlapDetection(IGraphicalEditPart projectPart, Project root) {
		this.root = root;
		this.projectPart = projectPart;
		config = new SchemaConfig(root);
		map = buildSegmentMap();

	}

	class ClockSort implements Comparator<Node> {
		private int cx;
		private boolean unstable = false;

		ClockSort(Ambit ambit, Collection<Node> nodes) {
			int sx = 0;
			int sx_c = 0;
			boolean done = false;
			for (final Node n : AmbitUtil.getAmbitNodes(ambit)) {
				if (!nodes.contains(n) && NodeUtil.isPointNode(n) && getPoint(n) != null) {
					sx += getPoint(n).x;
					sx_c++;
					done = true;
				}
			}

			if (done) {
				cx = sx / sx_c;
			} else {
				sx = 0;
				sx_c = 0;
				for (final Node n : nodes) {
					if (getPoint(n) != null) {
						sx += getPoint(n).x;
						sx_c++;
					}
				}
				cx = sx_c != 0 ? sx / sx_c : 0;
			}

		}

		@Override
		public int compare(Node arg0, Node arg1) {
			final Point p0 = getPoint(arg0);
			final Point p1 = getPoint(arg1);

			if (p0 == null || p1 == null) {
				return 0;
			}

			if (p0.x >= cx) {
				if (p1.x >= cx) {
					if (Math.abs(p0.y - p1.y) < 10) {
						unstable = true;
						return p0.y - p1.y + p1.x - p0.x;
					} else {
						return p0.y - p1.y;
					}
				} else {
					return -1;
				}
			} else {
				if (p1.x < cx) {
					if (Math.abs(p1.y - p0.y) < 10) {
						unstable = true;
						return p1.y - p0.y + p0.x - p1.x;
					} else {
						return p1.y - p0.y;
					}
				} else {
					return 1;
				}
			}
		}

		public boolean isUnstable() {
			return unstable;
		}

	}

	protected void processOverlaps(Project root) {
		final Collection<Signal> relevantExitSignals = new HashSet<>();

		for (final Node node : root.getNodes()) {
			ExtensionUtil.delete(node, SafecapConstants.EXT_OVERLAP_END_NODE);
		}

		for (final Equipment s : root.getEquipment()) {
			if (s instanceof Signal) {
				final Signal signal = (Signal) s;
				ExtensionUtil.delete(signal, SafecapConstants.EXT_OVERLAP_PATH);
				ExtensionUtil.delete(signal, SafecapConstants.EXT_OVERLAP_LENGTH);
				ExtensionUtil.delete(signal, SafecapConstants.EXT_OVERLAP_END_NODE);
				ExtensionUtil.delete(signal, SafecapConstants.EXT_OVERLAP_KIND);
				if (!SignalUtil.isAuto(signal) && (signal instanceof LeftSignal || signal instanceof RightSignal)) {
					relevantExitSignals.add(signal);
				}
			}
		}

		// System.out.println("Overlap signals " + relevantExitSignals);

		final Map<Segment, Ambit> samap = AmbitUtil.getSegmentAmbitMap(root);
		for (final Signal signal : relevantExitSignals) {
			// System.out.println("Overlap signal " + signal);
			final Segment segment = SignalUtil.getSignalSegment(signal);
			final Ambit ambit = samap.get(segment);
			final Set<SubRoute> subroutes = SubRouteUtil.getSubRoutes(ambit, segment,
					signal instanceof LeftSignal ? Orientation.LEFT : Orientation.RIGHT);
			if (subroutes.size() == 1) {
				final SubRoute signalSubroute = subroutes.iterator().next();
				final Map<SubRoute, Set<SubRoute>> graph = SubRouteUtil.getSubRouteGraph(EmfUtil.getProject(ambit));
				generateOverlaps(graph, root, signal, signalSubroute);
			} else {
				// System.out.println("\tinvalid position");
			}
		}
	}

	private void generateOverlaps(Map<SubRoute, Set<SubRoute>> graph, Project root, Signal signal, SubRoute signalSubroute) {

		final int offset = SignalUtil.getSignalOffsetFromJoint(root, signal);

		if (offset < getMinOverlapLength() && graph.containsKey(signalSubroute)) {
			for (final SubRoute subroute : graph.get(signalSubroute)) {
				if (subroute.getLength() > 0) {
					final List<SubRoute> list = new ArrayList<>(3);
					list.add(subroute);
					generateOverlap(graph, signal, list, offset + subroute.getLength());
				} else {
					// System.out.println("\tinvalid length");
				}
			}
		}

	}

	private void generateOverlap(Map<SubRoute, Set<SubRoute>> graph, Signal signal, List<SubRoute> list, int length) {
		if (length >= getMaxOverlapLength()) {
			commitOverlap(signal, list, length, -1);
		} else {
			final Collection<SubRoute> next = graph.get(list.get(list.size() - 1));
			if (next != null) {
				for (final SubRoute subroute : next) {
					if (subroute.getLength() > 0) {
						final List<SubRoute> nlist = new ArrayList<>(list);
						if (length > getMinOverlapLength() && length + subroute.getLength() > getMaxOverlapLength()) {
							commitOverlap(signal, list, length, -1);
							return;
						} else {
							nlist.add(subroute);
							generateOverlap(graph, signal, nlist, length + subroute.getLength());
						}
					}
				}
			}
		}
	}

	private int getMaxOverlapLength() {
		if (config != null && config.getOverlapConfig() != null) {
			return config.getOverlapConfig().getMaxLength();
		} else {
			return SchemaConfig.OVERLAP_LEN_MAX;
		}
	}

	private int getMinOverlapLength() {
		if (config != null && config.getOverlapConfig() != null) {
			return config.getOverlapConfig().getMinLength();
		} else {
			return SchemaConfig.OVERLAP_LEN_MIN;
		}
	}


	private static AbstractTransactionalCommand getDeleteOverlapCommand(final Signal signal, final String overlapName) {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(EmfUtil.getProject(signal));

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "DeleteOverlap", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				for (final Overlap overlap : OverlapUtil.getOverlaps(signal)) {
					if (overlap.getName().equals(overlapName)) {
						deleteOverlap(overlap);
						break;
					}
				}

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	private static AbstractTransactionalCommand getCommitOverlapCommand2(final Signal signal, final List<SubRoute> list, final int length,
			final int isFull) {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(EmfUtil.getProject(signal));

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "CommitOverlap", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				commitOverlap(signal, list, length, isFull);

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	public static void commitOverlapTransactionally(Signal signal, List<SubRoute> list, int length, int isFull) {
		try {
			getCommitOverlapCommand2(signal, list, length, isFull).execute(new NullProgressMonitor(), null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static void deleteOverlapTransactionally(Signal signal, String overlap) {
		try {
			getDeleteOverlapCommand(signal, overlap).execute(new NullProgressMonitor(), null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}	
	
	private static void computeNodeRole(Node node) {
		final Project root = EmfUtil.getProject(node);
		prepareNodeOverlaps(node);
		for (final Overlap overlap : OverlapUtil.getAllOverlapsForNode(root, node)) {
			setOverlapNodeRole(node, overlap);
		}
	}

	public static void commitOverlap(Signal signal, List<SubRoute> list, int length, int full) {
		final NodePath np = getNodePath(list);
		final String name = generateOverlapName(list, full);
		setOverlapPath(signal, name, np.getPathString());
		setOverlapLength(signal, name, length);
		setOverlapKind(signal, name, full);

		computeNodeRole(np.path.get(np.path.size() - 1));

		for (final SubRoute sr : list) {
			setSuboverlap(sr.getAmbit(), sr.getDirection());
		}

		// System.out.println("Committing overlap " + name + "; length " + length + ";
		// Signal " + signal);
	}
	


	public static void deleteOverlap(Overlap overlap) {
		final List<Node> zlist = OverlapUtil.getNodePath(overlap);
		if (!zlist.isEmpty()) {
			final String name = overlap.getName();
			final Signal signal = overlap.getSignal();
			ExtensionUtil.delete(signal, SafecapConstants.EXT_OVERLAP_PATH, name);
			ExtensionUtil.delete(signal, SafecapConstants.EXT_OVERLAP_LENGTH, name);
			ExtensionUtil.delete(signal, SafecapConstants.EXT_OVERLAP_END_NODE, signal.getLabel());
			ExtensionUtil.delete(signal, SafecapConstants.EXT_OVERLAP_KIND, name);

			computeNodeRole(zlist.get(zlist.size() - 1));

			// System.out.println("Deleted overlap " + name);
		}
	}


	private static String generateOverlapName(List<SubRoute> list, int kind) {
		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				sb.append(";");
			}
			final SubOverlap so = new SubOverlap(list.get(i).getAmbit(), list.get(i).getDirection());

			sb.append(so.toString());
		}

		if (kind == 1) {
			sb.append("-F");
		} else if (kind == 0) {
			sb.append("-R");
		}

		return "OVL:" + sb.toString();
	}
	
	

	private static NodePath getNodePath(List<SubRoute> list) {
		final List<Node> path = new ArrayList<>();
		int len = 0;
		for (final SubRoute sr : list) {
			final List<Node> p = sr.getNodePath();
			if (!path.isEmpty()) {
				assert path.get(path.size() - 1) == p.get(0);
				path.remove(path.size() - 1);
			}
			path.addAll(p);
			len += sr.getLength();
		}

		return new NodePath(path, len);
	}


	protected void prepareAmbitOverlaps(Ambit ambit) {
		ExtensionUtil.delete(ambit, SafecapConstants.EXT_SUBOVERLAP);
	}	
	

	protected static void prepareNodeOverlaps(Node node) {
		node.setRoles(node.getRoles() & ~(NodeRole.LEFT_OVERLAP_VALUE | NodeRole.RIGHT_OVERLAP_VALUE));
	}	
	
	private static void setOverlapNodeRole(Node node, Overlap overlap) {
		// ExtensionUtil.delete(node, SafecapConstants.EXT_OVERLAP_END_NODE,
		// signal.getLabel());
		node.getAttributes()
				.add(ExtensionUtil.mkString(SafecapConstants.EXT_OVERLAP_END_NODE, overlap.getSignal().getLabel(), overlap.getName()));

		if (overlap.getSignal() instanceof LeftSignal) {
			NodeUtil.setLeftOverlap(node);
		} else {
			NodeUtil.setRightOverlap(node);
		}
	}

	private static void setOverlapPath(Signal signal, String name, String path) {
		signal.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_OVERLAP_PATH, name, path));
	}

	private static void setOverlapLength(Signal signal, String name, Integer length) {
		signal.getAttributes().add(ExtensionUtil.mkInt(SafecapConstants.EXT_OVERLAP_LENGTH, name, length));
	}

	private static void setOverlapKind(Signal signal, String name, Integer attr) {
		signal.getAttributes().add(ExtensionUtil.mkInt(SafecapConstants.EXT_OVERLAP_KIND, name, attr));
	}	
	
	
	protected static void prepareAmbit(Ambit ambit) {
		ExtensionUtil.delete(ambit, SafecapConstants.EXT_SUBROUTE);
		ExtensionUtil.delete(ambit, SafecapConstants.EXT_SUBROUTE_PATH);
		ExtensionUtil.delete(ambit, SafecapConstants.EXT_SUBROUTE_LENGTH);
		ExtensionUtil.delete(ambit, SafecapConstants.EXT_ORIENTATION_DOMAIN);
		DiagramError.dropValidationRecords(ambit, "error");
		DiagramError.dropValidationRecords(ambit, "warning");
	}


	protected void prepareNode(Node node) {
		if (ExtensionUtil.getString(node, SafecapConstants.EXT_NODE_TAG_PROTECT, "right") == null) {
			ExtensionUtil.delete(node, SafecapConstants.EXT_NODE_TAG, "right");
		}
		if (ExtensionUtil.getString(node, SafecapConstants.EXT_NODE_TAG_PROTECT, "left") == null) {
			ExtensionUtil.delete(node, SafecapConstants.EXT_NODE_TAG, "left");
		}

		DiagramError.dropValidationRecords(node, "error");
		DiagramError.dropValidationRecords(node, "warning");

		// node.setRoles(node.getRoles() & ~(NodeRole.LEFT_OVERLAP_VALUE |
		// NodeRole.RIGHT_OVERLAP_VALUE));
	}


	protected void processForcedSubRoutes(Ambit ambit) {
		final Collection<String> used = AmbitUtil.getUsedSubRouteDirections(ambit);
		final Collection<String> all = AmbitUtil.getAllAmbitSubRouteDirections(ambit);
		final Collection<String> forced = AmbitUtil.getForcedSubRouteDirections(ambit);

		if (!forced.isEmpty()) {
			for (final String f : forced) {
				if (!all.contains(f)) {
					// DiagramError.addValidationRecord(ambit, "error", "Processing forced sub route
					// " + ambit.getLabel() + "-" + f + ": not a valid direction");
//					System.err.println(
//							"Processing forced sub route " + ambit.getLabel() + "-" + f + ": not a valid direction");
					continue;
				}

				if (used.contains(f)) {
//					DiagramError.addValidationRecord(ambit, "error", "Processing forced sub route " + ambit.getLabel() + "-" + f + ": already defined");
//					System.err
//							.println("Processing forced sub route " + ambit.getLabel() + "-" + f + ": already defined");
					continue;
				}

				setSubroute(ambit, null, f);
				// System.out.println("Processing forced sub route " + ambit.getLabel() + "-" +
				// f + ": added");
			}
		}
	}

	private static String getDirection(Ambit ambit, Node from, Node to, Orientation orientation, String def) {
		String from_tag;
		String to_tag;
		if (orientation == Orientation.LEFT) {
			from_tag = ExtensionUtil.getString(from, SafecapConstants.EXT_NODE_TAG, "left");
			to_tag = ExtensionUtil.getString(to, SafecapConstants.EXT_NODE_TAG, "right");
		} else {
			from_tag = ExtensionUtil.getString(to, SafecapConstants.EXT_NODE_TAG, "right");
			to_tag = ExtensionUtil.getString(from, SafecapConstants.EXT_NODE_TAG, "left");
		}
		String direction;
		if (from_tag == null || to_tag == null) {
			if (def != null) {
				direction = orientation == Orientation.LEFT ? def : new StringBuilder(def).reverse().toString();
			} else if (orientation == Orientation.LEFT) {
				direction = "BA";
			} else {
				direction = "AB";
			}
		} else {
			direction = from_tag + to_tag;
		}

		if (direction != null && (direction.length() != 2 || direction.charAt(0) == direction.charAt(1))
				&& !ambit.getSegments().isEmpty()) {
			DiagramError.addValidationRecord(ambit.getSegments().get(0), "error", "Invalid sub route marking for " + ambit.getLabel());
		}

		// assert direction == null || (direction.length() == 2 && direction.charAt(0)
		// != direction.charAt(1));
		return direction;
	}

	protected void processRouteAmbit(Route route, Ambit ambit) {
//		if (ambit instanceof MergedAmbit) {
//			MergedAmbit merged = (MergedAmbit) ambit;
//			if (merged.isDisjoint()) {
//				for(Ambit subambit: merged.getAmbits())
//					 processRouteAmbit(route, subambit);
//				return;
//			}
//			//System.out.println("processing merged " + merged.getLabel() + " :: " + merged.getSegments());
//		}

		if (ambit.getSegments().size() == 0) {
			return;
		} else if (ambit.getSegments().size() == 1 || ambit.getSegments().size() == 2) {
			setSubroute(ambit, route, getDirection(ambit, ambit.getSegments().get(0).getFrom(), ambit.getSegments().get(0).getTo(),
					route.getOrientation(), "BA"));
			return;
		} else {
			final Set<Node> nodes_left = AmbitUtil.getAmbitLeftBoundaryNodes(ambit);
			final Set<Node> nodes_right = AmbitUtil.getAmbitRightBoundaryNodes(ambit);

			final List<Node> nodes = new ArrayList<>(nodes_left);
			nodes.addAll(nodes_right);

			final List<Node> trapPointEnds = getTrapPointEnds(nodes);
			nodes.removeAll(trapPointEnds);

			Collections.sort(nodes, new ClockSort(ambit, nodes));

//			char right = 'A';
//			char left = 'B';
//
//			for (Segment s : route.getSegments()) {
//				if (nodes_left.contains(s.getFrom()))
//					left = (char) ('A' + nodes.indexOf(s.getFrom()));
//				if (nodes_right.contains(s.getTo()))
//					right = (char) ('A' + nodes.indexOf(s.getTo()));
//			}
//
//			if (route.getOrientation() == Orientation.LEFT)
//				setSubroute(ambit, route, "" + left + "" + right);
//			else
//				setSubroute(ambit, route, "" + right + "" + left);

			Node right = null;
			Node left = null;

			for (final Segment s : route.getSegments()) {
				if (nodes_left.contains(s.getFrom())) {
					left = s.getFrom();
				}
				if (nodes_right.contains(s.getTo())) {
					right = s.getTo();
				}
			}

			if (left != null && right != null) {

				// handle disjoint/composed merged ambits; such ambits require independent
				// annotations
				if (ambit instanceof MergedAmbit) {
					final MergedAmbit merged = (MergedAmbit) ambit;
					if (merged.isDisjoint() || merged.isComposed()) {
						for (final Ambit subambit : merged.getAmbits()) {
							final Collection<Node> allnodes = AmbitUtil.getAmbitNodes(subambit);
							if (allnodes.contains(left) && allnodes.contains(right)) {
								setSubroute(subambit, route, getDirection(subambit, left, right, route.getOrientation(), null));
							}
						}
					}
				}
				setSubroute(ambit, route, getDirection(ambit, left, right, route.getOrientation(), null));
			}
		}
	}

	private List<Node> getTrapPointEnds(List<Node> nodes) {
		final List<Node> result = new ArrayList<>();

		for (final Node node : nodes) {
			if (NodeUtil.isTrapPointEnd(node)) {
				result.add(node);
			}
		}

		return result;
	}

	protected void processAmbitNodeAnnotations(Ambit ambit) {

		if (ambit instanceof MergedAmbit) {
			final MergedAmbit merged = (MergedAmbit) ambit;
			if (merged.isDisjoint() || merged.isComposed()) {
				for (final Ambit subambit : merged.getAmbits()) {
					_processAmbitNodeAnnotations(subambit);
				}
				return;
			}
		}

		_processAmbitNodeAnnotations(ambit);
	}

	protected void _processAmbitNodeAnnotations(Ambit ambit) {

		final List<Node> nodes_right = new ArrayList<>(AmbitUtil.getAmbitRightBoundaryNodes(ambit));
		final List<Node> nodes_left = new ArrayList<>(AmbitUtil.getAmbitLeftBoundaryNodes(ambit));

		// Map<Node, Character> cMap = new HashMap<Node, Character>();

		final List<Node> nodes = new ArrayList<>(nodes_left);
		nodes.addAll(nodes_right);

		final List<Node> trapPointEnds = getTrapPointEnds(nodes);
		nodes.removeAll(trapPointEnds);
		final ClockSort cs = new ClockSort(ambit, nodes);
		Collections.sort(nodes, cs);

		if (cs.isUnstable() && !AmbitUtil.hasManualSubRoutePathAssignment(ambit)) {
			DiagramError.addValidationRecord(ambit.getSegments().get(0), "warning",
					"This ambit likely requires manual sub route paths annotations");
		}

		int i = 0;
		for (final Node n : nodes) {
			final char c = (char) ('A' + i++);
			if (nodes_right.contains(n)) {
				setNodeRightRole(n, String.valueOf(c));
			} else {
				setNodeLeftRole(n, String.valueOf(c));
				// cMap.put(n, c);
			}
		}
	}

	protected static void processAmbitSubRoutePaths(Ambit ambit) {

		if (ambit instanceof MergedAmbit) {
			final MergedAmbit merged = (MergedAmbit) ambit;
			if (merged.isDisjoint()) {
				for (final Ambit subambit : merged.getAmbits()) {
					_processAmbitSubRoutePaths(subambit, ambit);
				}
				return;
			}
		}

		_processAmbitSubRoutePaths(ambit, null);
	}

	protected static void _processAmbitSubRoutePaths(Ambit ambit, Ambit parent) {

		final List<Node> nodes_right = new ArrayList<>(AmbitUtil.getAmbitRightBoundaryNodes(ambit));
		final List<Node> nodes_left = new ArrayList<>(AmbitUtil.getAmbitLeftBoundaryNodes(ambit));

		try {
			for (final Node a : nodes_left) {
				for (final Node b : nodes_right) {
					final NodePath path = getNodePath(ambit, a, b);
					final String direction = getDirection(ambit, a, b, Orientation.LEFT, null);
					if (path != null && direction != null) {
						setSubroutePath(ambit, direction, path.getPathString());
						setSubrouteLength(ambit, direction, path.length);
						if (parent != null) {
							setSubroutePath(parent, direction, path.getPathString());
							setSubrouteLength(parent, direction, path.length);
						}
					}
				}
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}

	}

	private static void nameAllNodes(Project root) {
		for (final Node n : root.getNodes()) {
			if (n.getLabel() == null) {
				n.setLabel(ScopeB.getUniqueName(root, "N"));
			}
		}
	}

	private static NodePath getNodePath(Ambit ambit, Node entry, Node exit) {

		for (final Segment s : NodeUtil.getOutgoingSegments(entry)) {
			if (ambit.getSegments().contains(s)) {
				if (s.getTo() == exit) {
					final List<Node> list = new ArrayList<>();
					list.add(s.getFrom());
					list.add(exit);
					return new NodePath(list, s.getLength());
				}

				final NodePath path = getNodePath(ambit, s.getTo(), exit);
				if (path != null) {
					path.path.add(0, s.getFrom());
					path.length += s.getLength();
					return path;
				}
			}
		}

		return null;
	}

	private static void setSubroutePath(Ambit ambit, String direction, String path) {
		// assert direction.length() == 2 && direction.charAt(0) != direction.charAt(1);
		ambit.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_SUBROUTE_PATH, direction, path));
	}

	private static void setSubrouteLength(Ambit ambit, String direction, Integer length) {
		// assert direction.length() == 2 && direction.charAt(0) != direction.charAt(1);
		ambit.getAttributes().add(ExtensionUtil.mkInt(SafecapConstants.EXT_SUBROUTE_LENGTH, direction, length));
	}

	private static void setSuboverlap(Ambit ambit, String direction) {
		// assert direction.length() == 2 && direction.charAt(0) != direction.charAt(1);
		ambit.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_SUBOVERLAP, direction, true));
	}

	private static void setSubroute(Ambit ambit, Route route, String direction) {
		// assert direction.length() == 2 && direction.charAt(0) != direction.charAt(1);
		ambit.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_SUBROUTE, direction, true));

		if (route != null) {
			ambit.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_ORIENTATION_DOMAIN, route.getLabel(), direction));
		}
	}

	public static void setNodeLeftRole(Node node, String tag) {
		tag = ExtensionUtil.getString(node, SafecapConstants.EXT_NODE_TAG_PROTECT, "left", tag);
		ExtensionUtil.delete(node, SafecapConstants.EXT_NODE_TAG, "left");
		node.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_NODE_TAG, "left", tag));

	}

	public static void setNodeRightRole(Node node, String tag) {
		tag = ExtensionUtil.getString(node, SafecapConstants.EXT_NODE_TAG_PROTECT, "right", tag);
		ExtensionUtil.delete(node, SafecapConstants.EXT_NODE_TAG, "right");
		node.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_NODE_TAG, "right", tag));
	}

	public static void refresh(IGraphicalEditPart projectPart, Project root, IProgressMonitor monitor) {
		final SubRouteSubOverlapDetection cmd = new SubRouteSubOverlapDetection(projectPart, root);
		cmd.run(monitor);
	}

	public static void buildOverlaps(IGraphicalEditPart projectPart, Project root, IProgressMonitor monitor) {
		final SubRouteSubOverlapDetection cmd = new SubRouteSubOverlapDetection(projectPart, root);
		try {
			cmd.getOverlapsGenerationCommand().execute(monitor, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	public void run(IProgressMonitor monitor) {
		try {
			getSubRouteBuilderCommand().execute(monitor, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static void processAmbitSubRoutes(Ambit a) {
		ExtensionUtil.delete(a, SafecapConstants.EXT_SUBROUTE);
		ExtensionUtil.delete(a, SafecapConstants.EXT_SUBROUTE_PATH);
		ExtensionUtil.delete(a, SafecapConstants.EXT_SUBROUTE_LENGTH);
		processAmbitSubRoutePaths(a);
	}
	
	private AbstractTransactionalCommand getSubRouteBuilderCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "RefreshSegmentRole", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				nameAllNodes(root);

				for (final Ambit a : root.getAmbits()) {
					prepareAmbit(a);
				}

				for (final Node n : root.getNodes()) {
					prepareNode(n);
				}

				for (final Ambit a : root.getAmbits()) {
					processAmbitNodeAnnotations(a);
				}

				for (final Ambit a : root.getAmbits()) {
					processAmbitSubRoutePaths(a);
				}

				for (final Ambit a : root.getAmbits()) {
					for (final Route r : AmbitUtil.getAmbitRoutes(a)) {
						processRouteAmbit(r, a);
					}
				}

				for (final Ambit a : root.getAmbits()) {
					processForcedSubRoutes(a);
				}

				if (config.getOverlapConfig() != null) {
					try {
						processOverlaps(root);
					} catch (final Throwable e) {
						e.printStackTrace();
					}
				}

				for (final Ambit a : root.getAmbits()) {
					checkAmbitLabels(a);
				}

				return CommandResult.newOKCommandResult();
			}

		};

		return command;
	}

	protected void checkAmbitLabels(Ambit ambit) {
		if (ambit.getLabel() != null) {
			if (!AmbitUtil.isMerged(ambit) && !ExtensionUtil.isVirtual(ambit)
					&& (ambit.getLabel().indexOf('-') > 0 || ambit.getLabel().indexOf('/') > 0)) {
				DiagramError.addValidationRecord(ambit.getSegments().get(0), "warning", "The ambit likely needs to be merged or composed");

			}
		}

	}

	public AbstractTransactionalCommand getOverlapsGenerationCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "RefreshSegmentRole", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				for (final Ambit a : root.getAmbits()) {
					prepareAmbitOverlaps(a);
				}

//				for (Node n : root.getNodes())
//					prepareNodeOverlaps(n);

				try {
					processOverlaps(root);
				} catch (final Throwable e) {
					e.printStackTrace();
				}

				return CommandResult.newOKCommandResult();
			}

		};

		return command;
	}

	private Point getPoint(Node node) {

		final IGraphicalEditPart seg_part = map.get(node);
		if (seg_part == null) {
			return null;
		}

		final IFigure figure = seg_part.getFigure();
		if (figure == null) {
			return null;
		}

		final Figure shape = (Figure) figure;
		return shape.getLocation();
	}

	private Map<Node, IGraphicalEditPart> buildSegmentMap() {
		final Map<Node, IGraphicalEditPart> result = new HashMap<>(root.getSegments().size());

		for (final Object _ep : projectPart.getChildren()) {
			if (_ep instanceof NodeEditPart) {
				final NodeEditPart node = (NodeEditPart) _ep;
				final org.eclipse.gmf.runtime.notation.Shape shape = (org.eclipse.gmf.runtime.notation.Shape) node.getModel();
				if (shape.getElement() instanceof Node) {
					result.put((Node) shape.getElement(), (IGraphicalEditPart) _ep);
				}
			}
		}

		return result;
	}

}
