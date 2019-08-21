package uk.ac.ncl.safecap.mcommon.og;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import safecap.Orientation;
import safecap.Project;
import safecap.model.Ambit;
import safecap.model.Route;
import safecap.schema.Node;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;

public class SchemaToOG {
	private OrderGraph OGInterlocking;
	private OrderGraph OGRoutes;
	private OrderGraph OGAmbit;
	private OrderGraph OGSegment;
	private final Project project;

	public SchemaToOG(Project project) {
		this.project = project;
		buildInterlockingGraph();
		buildRouteGraph();
		buildAmbitGraph();
		buildSegmentGraph();
	}

	public OrderGraph getOGInterlocking() {
		return OGInterlocking;
	}

	public OrderGraph getOGRoutes() {
		return OGRoutes;
	}

	public OrderGraph getOGAmbit() {
		return OGAmbit;
	}

	public OrderGraph getOGSegment() {
		return OGSegment;
	}

	private void buildInterlockingGraph() {
		OGInterlocking = new OrderGraph();
		final Map<String, Collection<Ambit>> interlockings = new HashMap<>();
		interlockings.put("default", new HashSet<Ambit>());

		for (final Ambit a : project.getAmbits()) {
			final String interlocking = ExtensionUtil.getString(a, SafecapConstants.EXT_LDL_GEN, "interlocking");
			if (interlocking != null) {
				if (interlockings.containsKey(interlocking)) {
					interlockings.get(interlocking).add(a);
				} else {
					final Collection<Ambit> c = new HashSet<>();
					c.add(a);
					interlockings.put(interlocking, c);
				}
			} else {
				interlockings.get("default").add(a);
			}
		}

		for (final String i : interlockings.keySet()) {
			final OGNode node = new OGNode(i);
			final OrderGraph sub = new OrderGraph();
			for (final Ambit a : interlockings.get(i)) {
				sub.addNode(a.getLabel());
			}

			node.setSubgraph(sub);
			OGInterlocking.addNode(node);
		}
	}

	private void buildRouteGraph() {
		OGRoutes = new OrderGraph();
		for (final Route r : project.getRoutes()) {
			final OGNode node = new OGNode(r.getLabel());
			final OrderGraph sub = new OrderGraph();
			for (final Ambit a : r.getAmbits()) {
				sub.addNode(a.getLabel());
			}

			node.setSubgraph(sub);
			OGRoutes.addNode(node);
		}

		for (final Route r : project.getRoutes()) {
			for (final Route t : getNextRoutesFrom(r)) {
				OGRoutes.addEdge(r.getLabel() + "_" + t.getLabel(), r.getLabel(), t.getLabel());
			}
		}
	}

	private void buildAmbitGraph() {
		OGAmbit = new OrderGraph();
		for (final Ambit a : project.getAmbits()) {
			final OGNode node = new OGNode(a.getLabel());
			final OrderGraph sub = new OrderGraph();
			for (final Segment s : a.getSegments()) {
				sub.addNode(s.getLabel());
			}
			node.setSubgraph(sub);
			OGAmbit.addNode(node);
		}

		for (final Ambit a : project.getAmbits()) {
			for (final Ambit x : AmbitUtil.getNextAmbitsExclusive(a)) {
				OGAmbit.addEdge(a.getLabel() + "_" + x.getLabel(), a.getLabel(), x.getLabel());
			}
		}
	}

	private void buildSegmentGraph() {
		OGSegment = new OrderGraph();
		for (final Node n : project.getNodes()) {
			OGSegment.addNode(n.getLabel());
		}

		for (final Segment s : project.getSegments()) {
			OGSegment.addEdge(s.getLabel(), s.getFrom().getLabel(), s.getTo().getLabel());
		}

	}

	private Collection<Route> getNextRoutesFrom(Route route) {
		Node node;
		if (route.getOrientation() == Orientation.LEFT) {
			node = route.getSegments().get(route.getSegments().size() - 1).getTo();
		} else {
			node = route.getSegments().get(route.getSegments().size() - 1).getFrom();
		}

		final Collection<Route> result = new HashSet<>();
		for (final Route r : project.getRoutes()) {
			final Segment last = r.getSegments().get(r.getSegments().size() - 1);
			if (r.getOrientation() == Orientation.LEFT && last.getTo() == node) {
				result.add(r);
			} else if (r.getOrientation() == Orientation.RIGHT && last.getFrom() == node) {
				result.add(r);
			}
		}

		return result;
	}

}
