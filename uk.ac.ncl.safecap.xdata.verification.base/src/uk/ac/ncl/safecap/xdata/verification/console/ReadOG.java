package uk.ac.ncl.safecap.xdata.verification.console;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Bounds;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import safecap.Project;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.Segment;
import safecap.schema.SubSchemaNode;
import safecap.schema.SubSchemaPath;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.og.schemapojo.PojoEdge;
import uk.ac.ncl.safecap.og.schemapojo.PojoNode;
import uk.ac.ncl.safecap.og.schemapojo.PojoPath;
import uk.ac.ncl.safecap.og.schemapojo.PojoSchema;
import uk.ac.ncl.safecap.og.schemapojo.PojoXY;
import uk.ac.ncl.safecap.og.schemapojo.PojoXYNode;

public class ReadOG extends SchemaBaseCommand {
	public static ReadOG INSTANCE = new ReadOG();

	private ReadOG() {
	}

	@Override
	public String getName() {
		return "readog";
	}

	@Override
	public int getArguments() {
		return -1;
	}

	@Override
	public void execute(final ISafeCapConsole console, String[] arguments) {
		if (arguments.length == 0 || arguments.length > 2) {
			console.err("require one or two arguments");
			return;
		}
		final File file0 = new File(arguments[0]);
		if (!file0.exists() || !file0.canRead()) {
			console.err("invalid file name: " + file0);
			return;
		}

		if (arguments.length > 1) {
			final File file1 = new File(arguments[1]);
			if (!file1.exists() || !file1.canRead()) {
				console.err("invalid file name: " + file1);
				return;
			}
		}

		if (console.getGraphicalEditPart() == null) {
			console.err("require live editor sesssion");
			return;
		}

		final Collection<Node> extraNodes = new ArrayList<>();

		try {
			final Gson gson = new Gson();
			Reader reader = new FileReader(arguments[0]);
			final PojoSchema pojo = gson.fromJson(reader, PojoSchema.class);

			List<PojoXY> xy = null;
			if (arguments.length > 1) {
				reader = new FileReader(arguments[1]);
				final Type listType = new TypeToken<List<PojoXY>>() {
				}.getType();
				xy = gson.fromJson(reader, listType);
			}

			final Project root = console.getProject();

			final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);
			domain.getCommandStack().execute(new RecordingCommand(domain) {

				@Override
				protected void doExecute() {
					prepareSchema(root);
					importLayer(console, root, pojo, extraNodes);
					ExtensionUtil.setFlag(root, "forcePointDetection", false);
				}
			});

			if (xy != null) {
				final List<PojoXY> _xy = xy;
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						setNodeCoordinates(pojo.getLayer(), _xy, root, console.getGraphicalEditPart(), extraNodes);
						ExtensionUtil.setFlag(root, "forcePointDetection", true);
					}
				});
			}

		} catch (final Throwable e) {
			console.err("exception: " + e.getMessage());
			e.printStackTrace();
		}

	}

	protected void setNodeCoordinates(int layer, List<PojoXY> xy, Project root, IGraphicalEditPart graphicalEditPart,
			Collection<Node> extraNodes) {
		PojoXY actual = null;
		for (final PojoXY l : xy) {
			if (l.getLayerInt() == layer) {
				actual = l;
			}
		}

		if (actual == null) {
			return;
		}

		final Map<Node, Bounds> nodeBounds = new HashMap<>();

		for (final Node node : root.getNodes()) {
			for (final PojoXYNode xynode : actual.getNodes()) {
				if (xynode.getLabel().equals(node.getLabel())) {
					final IGraphicalEditPart a_part = (IGraphicalEditPart) graphicalEditPart.findEditPart(graphicalEditPart, node);
					if (a_part != null) {
						final Bounds ac = (Bounds) ((org.eclipse.gmf.runtime.notation.Node) a_part.getModel()).getLayoutConstraint();
						ac.setX(xynode.getX());
						ac.setY(xynode.getY());
						nodeBounds.put(node, ac);
						System.out.println("Setting location for " + node.getLabel());
					}
				}
			}
		}

		for (final Node node : extraNodes) {
			final IGraphicalEditPart a_part = (IGraphicalEditPart) graphicalEditPart.findEditPart(graphicalEditPart, node);
			final Bounds ac = (Bounds) ((org.eclipse.gmf.runtime.notation.Node) a_part.getModel()).getLayoutConstraint();

			final Node left = NodeUtil.getIncomingSegments(node).get(0).getFrom();
			final Node right = NodeUtil.getOutgoingSegments(node).get(0).getTo();
			final int x = (nodeBounds.get(left).getX() + nodeBounds.get(right).getX()) / 2;
			final int y = (nodeBounds.get(left).getY() + nodeBounds.get(right).getY()) / 2;

			ac.setX(x);
			ac.setY(y);
		}

	}

	private void prepareSchema(Project root) {
		root.getSegments().clear();
		root.getNodes().clear();
		root.getAmbits().clear();
		root.getLines().clear();
		root.getRoutes().clear();
		root.getAttributes().clear();
		root.getEquipment().clear();
	}

	private void importLayer(final ISafeCapConsole console, Project root, PojoSchema pojo, Collection<Node> extraNodes) {

		final Map<String, Node> nodeMap = new HashMap<>();
		for (final PojoNode n : pojo.getContents().getNodes()) {
			try {
				final SubSchemaNode newNode = safecap.schema.SchemaFactory.eINSTANCE.createSubSchemaNode();
				newNode.setLabel(n.getLabel());

				for (final PojoPath pp : n.getPaths()) {
					final SubSchemaPath newPath = safecap.schema.SchemaFactory.eINSTANCE.createSubSchemaPath();
					newPath.setFrom(pp.getFrom());
					newPath.setTo(pp.getTo());
					newPath.setLength(pp.getLength());
					newNode.getPaths().add(newPath);
				}

				root.getNodes().add(newNode);
				nodeMap.put(n.getLabel(), newNode);
				console.out("Adding node " + n.getLabel());
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}

		for (final PojoEdge edge : pojo.getContents().getEdges()) {
			if (edge.getDir() == 0) {
				final Segment newSegment = safecap.schema.SchemaFactory.eINSTANCE.createSegment();
				newSegment.setLabel(edge.getLabel());
				final Node from = nodeMap.get(edge.getFrom().getNode());
				final Node to = nodeMap.get(edge.getTo().getNode());
				newSegment.setFrom(from);
				newSegment.setTo(to);
				newSegment.setVisible(true);

				newSegment.getAttributes().add(ExtensionUtil.mkString("og.port", "from", edge.getFrom().getPort()));
				newSegment.getAttributes().add(ExtensionUtil.mkString("og.port", "to", edge.getTo().getPort()));

				root.getSegments().add(newSegment);
				console.out("Adding edge from " + from.getLabel() + ":" + edge.getFrom().getPort() + " to " + to.getLabel() + ":"
						+ edge.getTo().getPort());
			}
		}

		cleanup(root, extraNodes);
	}

	private void cleanup(Project root, Collection<Node> extraNodes) {
		final Collection<Segment> toAdd = new ArrayList<>();

		for (final Segment s : root.getSegments()) {
			split(root, s, toAdd, extraNodes);
		}

		root.getSegments().addAll(toAdd);
	}

	private boolean split(Project root, Segment s, Collection<Segment> toAdd, Collection<Node> extraNodes) {
		if (NodeUtil.getSegments(s.getFrom()).size() > 2 && NodeUtil.getSegments(s.getTo()).size() > 2) {
			final Node xx = safecap.schema.SchemaFactory.eINSTANCE.createNode();
			extraNodes.add(xx);
			root.getNodes().add(xx);
			xx.setKind(NodeKind.NORMAL);
			final Segment ss = safecap.schema.SchemaFactory.eINSTANCE.createSegment();
			ss.setFrom(s.getFrom());
			ss.setTo(xx);
			ss.setLabel(s.getLabel() + "-aux");
			s.setFrom(xx);
			toAdd.add(ss);
		}
		return false;
	}

}
