package uk.ac.ncl.safecap.xdata.verification.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import safecap.Orientation;
import safecap.Project;
import safecap.model.Ambit;
import safecap.model.Line;
import safecap.schema.Node;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.common.report.SRPart;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.PointConf;
import uk.ac.ncl.safecap.misc.core.SubRoute;
import uk.ac.ncl.safecap.misc.core.SubRouteUtil;
import uk.ac.ncl.safecap.misc.core.TrainLine;

public class BuildOG extends SchemaBaseCommand {

	public static BuildOG INSTANCE = new BuildOG();

	private BuildOG() {
	}

	@Override
	public String getName() {
		return "buildog";
	}

	@Override
	public int getArguments() {
		return 0;
	}

	@Override
	public void execute(ISafeCapConsole console, String[] arguments) {
		final int step = 0;
		final OGModel next = buildInitial(console.getProject());
		System.out.println("{");
		System.out.println("\"levels\": [");
		printStep(console, next, step);
		System.out.println("]}");
//		System.out.println("{");
//		System.out.println("\"levels\": [");
//		while (next != null) {
//			if (step > 0)
//				System.out.println(", ");
//			printStep(console, next, step);
//			prev = next;
//			next = compress(prev, 2);
//			step++;
//		}
//		System.out.println("]}");

	}

	private void printStep(ISafeCapConsole console, OGModel model, int step) {
		console.out("Writing layer " + step);
		System.out.println("{\"layer\": " + step + ", ");
		System.out.println("\"contents\": " + model.toString());
		System.out.println("}");
	}

	private OGModel compress(OGModel model, int power) {
		OGModel m = forgetKids(model);
		for (int i = 0; i < power; i++) {
			m = compressOneEdge(m);
			if (m == null) {
				return m;
			}

		}
		return m;
	}

	private OGModel forgetKids(OGModel model) {
		final OGModel clone = model.clone();
		for (final OGNode n : clone.nodes) {
			n.subnodes = Collections.singleton(n);
		}
		assert clone != null;
		return clone;
	}

	private OGModel compressOneEdge(OGModel model) {
		final OGEdge edge = model.someGoodEdge();
		if (edge == null) {
			return null;
		}

		final Collection<OGNode> own = new HashSet<>(2);
		own.add(edge.from);
		own.add(edge.to);

		final OGNode node = new OGNode(model.getNodeName(own));
		node.subnodes = own;

		for (final OGPath path : edge.from.paths) {
			boolean processed = false;
			for (final OGPath path2 : edge.to.paths) {
				if (path2.from.equals(path.to)) {
					final OGPath newPath = buildCombinedPath(path, path2);
					node.addPath(newPath);
					processed = true;
				}
				if (path2.to.equals(path.from)) {
					processed = true;
				}
			}

			if (!processed) {
				node.addPath(path);
			}

		}

		for (final OGPath path : edge.to.paths) {
			boolean processed = false;
			for (final OGPath path2 : edge.from.paths) {
				if (path2.from.equals(path.to)) {
					final OGPath newPath = buildCombinedPath(path, path2);
					node.addPath(newPath);
					processed = true;
				}
				if (path2.to.equals(path.from)) {
					processed = true;
				}
			}
			if (!processed) {
				node.addPath(path);
			}
		}

		// bug1 : N47 - N47
		// bug2: absent N31 - N48
		// bug3: absent N46 paths in both directions

		/*
		 * HC paths are N47 - N48 and N48 - N47 HB paths N31 - N46, N31 - N47, N46 - N31
		 * and N47 - N31 for HB_HC expect paths N31 - N48 N31 - N46 N48 - N31 N46 - N31
		 */

		final Collection<OGEdge> in = model.getIncoming(edge.from);
		in.addAll(model.getIncoming(edge.to));

		final Collection<OGEdge> out = model.getOutgoing(edge.from);
		out.addAll(model.getOutgoing(edge.to));

		final OGModel clone = model.clone();

		// remove internal edges
		for (final OGEdge e : in) {
			if (own.contains(e.from)) {
				clone.edges.remove(e);
			}
		}

		for (final OGEdge e : out) {
			if (own.contains(e.to)) {
				clone.edges.remove(e);
			}
		}

		// re-route external "in" edge to the new node
		for (final OGEdge e : in) {
			if (clone.edges.contains(e)) { // not removed
				clone.edges.remove(e);
				final OGEdge ne = new OGEdge(e.from, node, 2);
				ne.setFromPort(e.getFromPort());
				ne.setToPort(e.getToPort());
				clone.addEdge(ne);
			}
		}

		for (final OGEdge e : out) {
			if (clone.edges.contains(e)) { // not removed
				clone.edges.remove(e);
				final OGEdge ne = new OGEdge(node, e.to, 2);
				ne.setFromPort(e.getFromPort());
				ne.setToPort(e.getToPort());
				clone.addEdge(ne);
			}
		}

		clone.edges.removeAll(in);
		clone.edges.removeAll(out);

		// removed hidden nodes
		for (final OGNode n : own) {
			clone.nodes.remove(n);
		}

		// add the new node
		clone.nodes.add(node);

		return clone;

	}

	private OGPath buildCombinedPath(OGPath path, OGPath path2) {
		final OGPath result = new OGPath(path.from, path2.to);

		path.set("length", path.get("length", 0) + path2.get("length", 0));
		path.set("normal", concatJSONLists(path.get("normal", "[]"), path2.get("normal", "[]")));
		path.set("normal", concatJSONLists(path.get("reverse", "[]"), path2.get("reverse", "[]")));

		path.set("cost", path.get("cost", 0.0) + path.get("cost", 0.0));

		assert !path.from.equals(path2.to);

		return result;
	}

	private String concatJSONLists(String a, String b) {
		assert a.charAt(0) == '[' && a.charAt(a.length() - 1) == ']';
		assert b.charAt(0) == '[' && b.charAt(b.length() - 1) == ']';

		final String ca = a.substring(1, a.length() - 1).trim();
		final String cb = b.substring(1, b.length() - 1).trim();

		final String[] pa = ca.length() > 0 ? ca.split(",") : new String[0];
		final String[] pb = cb.length() > 0 ? cb.split(",") : new String[0];

		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < pa.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(pa[i]);
		}

		if (pa.length > 0) {
			sb.append(", ");
		}

		for (int i = 0; i < pb.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(pb[i]);
		}

		assert !sb.toString().trim().startsWith(",");

		return "[" + sb.toString() + "]";
	}

	private OGModel buildInitial(Project root) {
		final OGModel model = new OGModel();
		for (final Ambit ambit : root.getAmbits()) {
			final OGNode node = new OGNode(ambit.getLabel());
			model.addNode(node);
			for (final SubRoute sr : SubRouteUtil.getSubRoutes(ambit)) {
				final List<Node> nodepath = sr.getNodePath();
				if (nodepath != null) {
					final OGPath path = new OGPath(nodepath.get(0).getLabel(), nodepath.get(nodepath.size() - 1).getLabel());
					node.addPath(path);
					path.set("length", sr.getLength());
					final PointConf conf = new PointConf(root, nodepath);
					if (!conf.getNormal().isEmpty()) {
						path.set("normal", toJSONList(conf.getNormal()));
					}
					if (!conf.getReverse().isEmpty()) {
						path.set("reverse", toJSONList(conf.getReverse()));
					}
					double cost = conf.getNormal().size() * 0.2;
					cost += conf.getReverse().size() * 5;
					cost += sr.getLength() * 0.01;
					path.set("cost", "" + cost);
				}
			}
		}

		for (final Ambit ambit : root.getAmbits()) {
			for (final Ambit next : AmbitUtil.getNextAmbitsExclusive(ambit)) {
				final OGEdge edge = new OGEdge(model.getNodeByLabel(ambit.getLabel()), model.getNodeByLabel(next.getLabel()), 0);
				model.addEdge(edge);
				final Collection<Node> nodes = AmbitUtil.getSharedNodes(ambit, next);
				if (!nodes.isEmpty()) {
					final Node node = nodes.iterator().next();
					edge.setFromPort(node.getLabel());
					edge.setToPort(node.getLabel());
				}
			}
		}

		for (final Ambit ambit : root.getAmbits()) {
			for (final Ambit next : AmbitUtil.getPrevAmbitsExclusive(ambit)) {
				final OGEdge edge = new OGEdge(model.getNodeByLabel(ambit.getLabel()), model.getNodeByLabel(next.getLabel()), 1);
				model.addEdge(edge);
				final Collection<Node> nodes = AmbitUtil.getSharedNodes(ambit, next);
				if (!nodes.isEmpty()) {
					final Node node = nodes.iterator().next();
					edge.setFromPort(node.getLabel());
					edge.setToPort(node.getLabel());
				}
			}
		}

		for (final Line line : root.getLines()) {
			final TrainLine tl = new TrainLine(line);
			final List<String> nodes = new ArrayList<>();
			nodes.add(tl.getStartNode().getLabel());

			if (line.getOrientation() == Orientation.LEFT) {
				for (final Segment s : tl.getPath()) {
					nodes.add(s.getTo().getLabel());
				}
			} else {
				for (final Segment s : tl.getPath()) {
					nodes.add(s.getFrom().getLabel());
				}
			}

			final OGLine ogline = new OGLine(line.getLabel(), nodes);
			model.addLine(ogline);
		}

		return model;
	}

	private String toJSONList(Collection<?> data) {
		final StringBuilder sb = new StringBuilder();

		sb.append('[');
		boolean first = true;
		for (final Object z : data) {
			if (!first) {
				sb.append(", ");
			}
			sb.append('"');
			sb.append(z.toString());
			sb.append('"');
			first = false;
		}
		sb.append(']');
		return sb.toString();
	}

}

class OGModel {
	private final Random random;
	Collection<OGNode> nodes;
	Collection<OGEdge> edges;
	Collection<OGLine> lines;

	OGModel() {
		random = new Random(System.currentTimeMillis());
		nodes = new HashSet<>();
		edges = new HashSet<>();
		lines = new HashSet<>();
	}

	public String getNodeName(Collection<OGNode> nodes) {
		final StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (final OGNode node : nodes) {
			if (!first) {
				sb.append("_");
			}
			sb.append(node.label);
			first = false;
		}
		return sb.toString();
	}

	@Override
	public OGModel clone() {
		final OGModel clone = new OGModel();
		clone.nodes.addAll(nodes);
		clone.edges.addAll(edges);
		return clone;
	}

	public void addNode(OGNode node) {
		nodes.add(node);
	}

	public void addEdge(OGEdge edge) {
		edges.add(edge);
	}

	public void addLine(OGLine line) {
		lines.add(line);
	}

	private boolean isGoodEdge(OGEdge e) {
		final boolean from = e.from.subnodes == null || e.from.subnodes.equals(Collections.singleton(e.from));
		final boolean to = e.to.subnodes == null || e.to.subnodes.equals(Collections.singleton(e.to));
		return from && to;
	}

	public OGEdge someGoodEdge() {
		if (edges.isEmpty()) {
			return null;
		}

		// get a random good edge
		OGEdge e = someEdge();
		int tries = 0;
		while (!isGoodEdge(e) && tries++ < 10) {
			e = someEdge();
		}

		// try again, exhaustively
		for (final OGEdge z : edges) {
			if (isGoodEdge(z)) {
				return z;
			}
		}

		// give up, return any edge
		return edges.iterator().next();
	}

	public OGEdge someEdge() {
		if (edges.isEmpty()) {
			return null;
		}
		final int index = random.nextInt(edges.size());
		int i = 0;
		for (final OGEdge e : edges) {
			if (i == index) {
				return e;
			}
			i++;
		}

		return null;
	}

	public OGNode getNodeByLabel(String label) {
		for (final OGNode node : nodes) {
			if (node.label.equals(label)) {
				return node;
			}
		}

		return null;
	}

	public Collection<OGEdge> getIncoming(OGNode node) {
		final HashSet<OGEdge> incoming = new HashSet<>();
		for (final OGEdge edge : edges) {
			if (edge.to == node) {
				incoming.add(edge);
			}
		}

		return incoming;
	}

	public Collection<OGEdge> getOutgoing(OGNode node) {
		final HashSet<OGEdge> incoming = new HashSet<>();
		for (final OGEdge edge : edges) {
			if (edge.from == node) {
				incoming.add(edge);
			}
		}

		return incoming;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"lines\": [");

		boolean first = true;
		for (final OGLine line : lines) {
			if (!first) {
				sb.append(", ");
			}
			sb.append(line.toString());
			first = false;
		}

		sb.append("]");

		sb.append(", \"nodes\": [");

		first = true;
		for (final OGNode node : nodes) {
			if (!first) {
				sb.append(", ");
			}
			sb.append(node.toString());
			first = false;
		}

		sb.append("]");

		if (!edges.isEmpty()) {
			sb.append(", \"edges\": [");
			first = true;
			for (final OGEdge edge : edges) {
				if (!first) {
					sb.append(", ");
				}
				sb.append(edge.toString());
				first = false;
			}
			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}
}

class OGLine extends SRPart {
	protected String name;
	protected List<String> nodes;

	public OGLine(String name, List<String> nodes) {
		super();
		this.name = name;
		this.nodes = nodes;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append("\"label\":" + wrapq(name));
		sb.append(", \"nodes\": [");
		boolean first = true;
		for (final String n : nodes) {
			if (!first) {
				sb.append(", ");
			}
			sb.append(wrapq(n));
			first = false;
		}
		sb.append("]");
		sb.append("}");
		return sb.toString();
	}

	private String wrapq(String s) {
		return "\"" + s + "\"";
	}

}

class OGPath extends SRPart {
	protected String from;
	protected String to;

	public OGPath(String from, String to) {
		super();
		this.from = from;
		this.to = to;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append("\"from\":" + wrapq(from));
		sb.append(", \"to\":" + wrapq(to));
		sb.append(", \"length\":" + wrapq(get("length", "0")));
		sb.append(", \"normal\":" + get("normal", "[]"));
		sb.append(", \"reverse\":" + get("reverse", "[]"));
		sb.append(", \"cost\":" + wrapq(get("cost", "0")));
		sb.append("}");

		return sb.toString();
	}

	private String wrapq(String s) {
		return "\"" + s + "\"";
	}
}

class OGNode {
	protected String label;
	protected Collection<OGNode> subnodes;
	protected List<OGPath> paths;

	public OGNode(String label) {
		super();
		this.label = label;
		paths = new ArrayList<>();
	}

	public void addPath(OGPath path) {
		paths.add(path);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (label == null ? 0 : label.hashCode());
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
		final OGNode other = (OGNode) obj;
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append("\"label\": " + wrapq(label));
		if (subnodes != null) {
			sb.append(", \"subnodes\": [");
			boolean first = true;
			for (final OGNode n : subnodes) {
				if (!first) {
					sb.append(", ");
				}
				sb.append(wrapq(n.label));
				first = false;
			}
			sb.append("]");
		}

		if (paths != null && !paths.isEmpty()) {
			sb.append(", \"paths\": [");
			boolean first = true;
			for (final OGPath p : paths) {
				if (!first) {
					sb.append(", ");
				}
				sb.append(p.toString());
				first = false;
			}
			sb.append("]");
		}
		sb.append("}");

		return sb.toString();
	}

	private String wrapq(String s) {
		return "\"" + s + "\"";
	}
}

class OGEdge {
	protected OGNode from;
	protected OGNode to;
	private String fromPort;
	private String toPort;
	private final int orientation;

	public OGEdge(OGNode from, OGNode to, int orientation) {
		super();
		this.from = from;
		this.to = to;
		this.orientation = orientation;
	}

	public String getFromPort() {
		return fromPort;
	}

	public void setFromPort(String fromPort) {
		this.fromPort = fromPort;
	}

	public String getToPort() {
		return toPort;
	}

	public void setToPort(String toPort) {
		this.toPort = toPort;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (from == null ? 0 : from.hashCode());
		result = prime * result + (fromPort == null ? 0 : fromPort.hashCode());
		result = prime * result + orientation;
		result = prime * result + (to == null ? 0 : to.hashCode());
		result = prime * result + (toPort == null ? 0 : toPort.hashCode());
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
		final OGEdge other = (OGEdge) obj;
		if (from == null) {
			if (other.from != null) {
				return false;
			}
		} else if (!from.equals(other.from)) {
			return false;
		}
		if (fromPort == null) {
			if (other.fromPort != null) {
				return false;
			}
		} else if (!fromPort.equals(other.fromPort)) {
			return false;
		}
		if (orientation != other.orientation) {
			return false;
		}
		if (to == null) {
			if (other.to != null) {
				return false;
			}
		} else if (!to.equals(other.to)) {
			return false;
		}
		if (toPort == null) {
			if (other.toPort != null) {
				return false;
			}
		} else if (!toPort.equals(other.toPort)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append("\"dir\":" + orientation);
		sb.append(", \"label\":" + wrapq(from.label + "-" + to.label));
		sb.append(", \"from\": {\"node\": " + wrapq(from.label));
		if (fromPort != null) {
			sb.append(", \"port\": " + wrapq(fromPort));
		}
		sb.append("}, ");
		sb.append(" \"to\": {\"node\": " + wrapq(to.label));
		if (toPort != null) {
			sb.append(", \"port\": " + wrapq(toPort));
		}
		sb.append("}");
		sb.append("}");

		return sb.toString();
	}

	private String wrapq(String s) {
		return "\"" + s + "\"";
	}
}
