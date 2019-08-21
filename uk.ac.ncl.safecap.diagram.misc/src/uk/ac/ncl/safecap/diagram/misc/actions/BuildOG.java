package uk.ac.ncl.safecap.diagram.misc.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import safecap.Orientation;
import safecap.Project;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.schema.Node;
import safecap.schema.Segment;
import safecap.schema.SubSchemaNode;
import safecap.schema.SubSchemaPath;
import safecap.trackside.Equipment;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.mcommon.og.OGEdge;
import uk.ac.ncl.safecap.mcommon.og.OGNode;
import uk.ac.ncl.safecap.mcommon.og.OrderGraph;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.EquipmentUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.ISegmentWalkerFilter;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.PointConf;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.misc.core.SegmentPath;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;
import uk.ac.ncl.safecap.misc.core.SignalUtil;

public class BuildOG extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);

		final Project project = getSelectedModel(editor);
		if (project != null) {
			final BuildOGCommand cmd = new BuildOGCommand(project);
			cmd.run();
		}

		return null;
	}

	private static Project getSelectedModel(IEditorPart editor) {
		final IEditorInput obj = editor.getEditorInput();

		if (obj instanceof FileEditorInput) {
			final FileEditorInput input = (FileEditorInput) obj;
			return EmfUtil.fromFile(input.getFile());
		}

		return null;
	}

}

class BuildOGCommand {
	private final Project root;
	private SubSchemaNode newNode;
	private Collection<Node> oldNodes;
	private int nodeCounter = 0;

	public BuildOGCommand(Project root) {
		this.root = root;
	}

	public static void refresh(Project root) {
		final BuildOGCommand cmd = new BuildOGCommand(root);
		cmd.run();
	}

	public void run() {
		try {
			OperationHistoryFactory.getOperationHistory().execute(getCommand(), null, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "Build OG", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				System.out.println("{");
				System.out.println("\"levels\": [");
				int i = 0;
				dumpStep(i++);
				while (combineStep(i)) {
					System.out.println(", ");
					dumpStep(i++);
				}

				System.out.println("]}");

				return CommandResult.newOKCommandResult();
			}

			private boolean combineStep(int i) {
				if (!root.getAmbits().isEmpty()) {
					for (final Ambit a : root.getAmbits()) {
						if (a instanceof Junction) {
							combine(a, i);
							return true;
						}
					}
					for (final Ambit a : root.getAmbits()) {
						combine(a, i);
						return true;
					}
				} else {
					for (final Segment s : root.getSegments()) {
						if (s.getFrom() instanceof SubSchemaNode && s.getTo() instanceof SubSchemaNode) {
							final SubSchemaNode a = (SubSchemaNode) s.getFrom();
							final SubSchemaNode b = (SubSchemaNode) s.getTo();
							if (NodeUtil.getOutgoingSegments(a).size() == 1 && NodeUtil.getIncomingSegments(b).size() == 1) {
								combine(a, b, i);
								return true;
							}
						}
					}
				}

				return false;
			}

			private void dumpStep(int step) {
				final OrderGraph og = new OrderGraph();
				if (!root.getSegments().isEmpty()) {
					for (final Segment s : root.getSegments()) {
						final OGNode a = new OGNode(s.getFrom().getLabel());
						final OGNode b = new OGNode(s.getTo().getLabel());
						annotateNode(a, s.getFrom());
						annotateNode(b, s.getTo());
						og.addNode(a);
						og.addNode(b);
						final OGEdge edge = new OGEdge(s.getLabel(), a, b);
						annotateEdge(edge, s);
						og.addEdge(edge);
					}
				} else {
					for (final Node n : root.getNodes()) {
						og.addNode(new OGNode(n.getLabel()));
					}
				}

				System.out.println("{\"layer\": " + step + ", ");
				System.out.println("\"contents\": " + og.toString());
				System.out.println("}");

			}

			private void annotateEdge(OGEdge edge, Segment s) {
				final String[] ports = getPorts(s);
				if (ports[0] != null) {
					edge.setAttribute("port.from", ports[0]);
				}
				if (ports[1] != null) {
					edge.setAttribute("port.to", ports[1]);
				}

				if (!(s.getTo() instanceof SubSchemaNode && s.getFrom() instanceof SubSchemaNode)) {
					edge.setAttribute("length", s.getLength());
					edge.setAttribute("gradient", s.getGradient() == null ? "0" : s.getGradient());
					edge.setAttribute("speedlimit", s.getSpeedlimit() == null ? "40" : s.getSpeedlimit());
				}
			}

			private void annotateNode(OGNode a, Node s) {
				if (s instanceof SubSchemaNode) {
					final SubSchemaNode ssn = (SubSchemaNode) s;
					a.setAttribute("ssn", ssn);
				}
			}
		};

		return command;
	}

	private SubSchemaNode combine(Ambit a, int i) {
		final SubSchemaNode ssn = safecap.schema.SchemaFactory.eINSTANCE.createSubSchemaNode();

		for (final Node n : AmbitUtil.getAmbitNodes(a)) {
			ssn.getChildren().add(n.getLabel());
		}

		ssn.setLabel("FN" + nodeCounter++);
		root.getNodes().add(ssn);

		final Set<Node> nodesin = AmbitUtil.getAmbitLeftBoundaryNodes(a);
		final Set<Node> nodesout = AmbitUtil.getAmbitRightBoundaryNodes(a);

		oldNodes = AmbitUtil.getAmbitNodes(a);

		for (final Node n : nodesin) {
			for (final Segment s : NodeUtil.getIncomingSegments(n)) {
				setPorts(s, null, s.getTo().getLabel());
				s.setTo(ssn);
			}
		}

		for (final Node n : nodesout) {
			for (final Segment s : NodeUtil.getOutgoingSegments(n)) {
				setPorts(s, s.getFrom().getLabel(), null);
				s.setFrom(ssn);
			}
		}

		// build paths
		final Collection<SegmentPath> paths_left = SegmentUtil.buildPaths(a.getSegments(), Orientation.LEFT,
				ISegmentWalkerFilter.AcceptAllSegmentWalkerFilter.INSTANCE);
		final Collection<SegmentPath> paths_right = SegmentUtil.buildPaths(a.getSegments(), Orientation.RIGHT,
				ISegmentWalkerFilter.AcceptAllSegmentWalkerFilter.INSTANCE);

//		Map<String, SubSchemaPort> ports = new HashMap<String, SubSchemaPort>();
//		for (Node n : nodesin) {
//			SubSchemaPort port = safecap.schema.SchemaFactory.eINSTANCE.createSubSchemaPort();
//			port.setLabel(n.getLabel());
//			ssn.getInputs().add(port);
//			ports.put(n.getLabel(), port);
//		}
//
//		for (Node n : nodesout) {
//			SubSchemaPort port = safecap.schema.SchemaFactory.eINSTANCE.createSubSchemaPort();
//			port.setLabel(n.getLabel());
//			ssn.getOutputs().add(port);
//			ports.put(n.getLabel(), port);
//		}

		for (final SegmentPath p : paths_left) {
			if (!NodeUtil.isJunctionNode(p.getFirst().getFrom()) && !NodeUtil.isJunctionNode(p.getLast().getTo())) {
				final SubSchemaPath path = safecap.schema.SchemaFactory.eINSTANCE.createSubSchemaPath();
				path.setFrom(p.getFirst().getFrom().getLabel());
				path.setTo(p.getLast().getTo().getLabel());
				path.setLength((int) p.getLength());
				ssn.getPaths().add(path);
				final PointConf pc = new PointConf(p.getPath());
				path.setPnormal(pc.getNormal().size());
				path.setPreverse(pc.getReverse().size());
			}
		}

		for (final SegmentPath p : paths_right) {
			if (!NodeUtil.isJunctionNode(p.getFirst().getTo()) && !NodeUtil.isJunctionNode(p.getLast().getFrom())) {
				final SubSchemaPath path = safecap.schema.SchemaFactory.eINSTANCE.createSubSchemaPath();
				path.setFrom(p.getFirst().getTo().getLabel());
				path.setTo(p.getLast().getFrom().getLabel());
				path.setLength((int) p.getLength());
				ssn.getPaths().add(path);
				final PointConf pc = new PointConf(p.getPath());
				path.setPnormal(pc.getNormal().size());
				path.setPreverse(pc.getReverse().size());
			}
		}

		root.getAmbits().remove(a);

		final List<Equipment> toremove = new ArrayList<>();
		for (final Segment s : a.getSegments()) {
			final Signal ls = SignalUtil.getLeftSignalOn(s);
			if (ls != null) {
				toremove.add(ls);
			}
			final Signal rs = SignalUtil.getRightSignalOn(s);
			if (rs != null) {
				toremove.add(rs);
			}
			root.getSegments().remove(s);
		}

		EquipmentUtil.removeEquipment(toremove);

		root.getNodes().removeAll(AmbitUtil.getAmbitNodes(a));

		return ssn;
	}

	private SubSchemaNode combine(SubSchemaNode left, SubSchemaNode right, int i) {
		final SubSchemaNode ssn = safecap.schema.SchemaFactory.eINSTANCE.createSubSchemaNode();
		ssn.setLabel("FN" + nodeCounter++ + "-Z");
		ssn.getChildren().add(left.getLabel());
		ssn.getChildren().add(right.getLabel());
		root.getNodes().add(ssn);

		for (final Segment s : root.getSegments()) {
			if (s.getFrom() == left && s.getTo() == right) {
				final SubSchemaPath path = safecap.schema.SchemaFactory.eINSTANCE.createSubSchemaPath();
				path.setFrom(left.getLabel());
				path.setTo(right.getLabel());
				path.setLength(s.getLength());
				ssn.getPaths().add(path);
			} else if (s.getFrom() == right && s.getTo() == left) {
				final SubSchemaPath path = safecap.schema.SchemaFactory.eINSTANCE.createSubSchemaPath();
				path.setFrom(right.getLabel());
				path.setTo(left.getLabel());
				path.setLength(s.getLength());
				ssn.getPaths().add(path);
			}
		}

		for (final Segment s : NodeUtil.getIncomingSegments(left)) {
			s.setTo(ssn);
		}

		for (final Segment s : NodeUtil.getIncomingSegments(right)) {
			s.setFrom(ssn);
		}

		for (final Segment s : NodeUtil.getOutgoingSegments(left)) {
			root.getSegments().remove(s);
		}

		for (final Segment s : NodeUtil.getIncomingSegments(right)) {
			root.getSegments().remove(s);
		}

		oldNodes = new ArrayList<>();
		oldNodes.add(left);
		oldNodes.add(right);

		root.getNodes().remove(left);
		root.getNodes().remove(right);
		return ssn;
	}

	private static void setPorts(Segment s, String left, String right) {
		ExtensionUtil.delete(s, SafecapConstants.EXT_PORT, "left");
		ExtensionUtil.delete(s, SafecapConstants.EXT_PORT, "right");
		if (left != null) {
			s.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_PORT, "left", left));
		}
		if (right != null) {
			s.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_PORT, "right", right));
		}
	}

	private static String[] getPorts(Segment s) {
		final String left = ExtensionUtil.getString(s, SafecapConstants.EXT_PORT, "left");
		final String right = ExtensionUtil.getString(s, SafecapConstants.EXT_PORT, "right");

		if (left != null || right != null) {
			return new String[] { left, right };
		} else {
			return new String[] { null, null };
			// return new String[] {s.getFrom().getLabel(), s.getTo().getLabel()};
		}
	}
}