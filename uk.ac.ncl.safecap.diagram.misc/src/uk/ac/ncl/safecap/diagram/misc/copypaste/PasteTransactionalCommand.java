package uk.ac.ncl.safecap.diagram.misc.copypaste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import safecap.Project;
import safecap.commentary.Commentary;
import safecap.schema.Node;
import safecap.schema.SchemaFactory;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.Signal;
import safecap.trackside.TracksideFactory;
import safecap.trackside.Wire;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ScopeB;
import uk.ac.ncl.safecap.misc.core.ScopeC;

public class PasteTransactionalCommand extends AbstractTransactionalCommand {
	private final EObject target;
	private final List<ObjectCopy> elemToPaste;
	private final MyEditorPasteCommand pasteCmd;

	public PasteTransactionalCommand(TransactionalEditingDomain domain, List<ObjectCopy> elemToPaste, EObject targetElement,
			MyEditorPasteCommand pasteCmd) {

		super(domain, PasteTransactionalCommand.class.getName(), getWorkspaceFiles(elemToPaste));
		target = targetElement;
		this.elemToPaste = elemToPaste;
		this.pasteCmd = pasteCmd;
	}

	@Override
	public void addContext(IUndoContext context) {
		super.addContext(context);
		pasteCmd.addContext(context);
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		final Set<Node> old_nodes = new HashSet<>(20);
		final Set<Equipment> old_eqp = new HashSet<>(20);

		if (target instanceof Project && elemToPaste.size() > 0) {
			final Project schema = (Project) target;
			final Project src_schema = EmfUtil.getProject(elemToPaste.get(0).getSourceObject());

			if (src_schema != null) {

				for (final ObjectCopy oc : elemToPaste) {
					final EObject obj = oc.getCopy();
					final EObject src = oc.getSourceObject();
					if (obj instanceof Node) {

						final Node node = (Node) obj;

						if (node.getLabel() != null && ScopeB.getByLabel(schema, node.getLabel()) != null) {
							final String label = ScopeB.getUniqueName(schema, node.getLabel());
							node.setLabel(label);
						}

						schema.getNodes().add(node);
						old_nodes.add((Node) src);

					} else if (obj instanceof Equipment) {

						if (obj instanceof Signal) {
							final Signal signal = (Signal) obj;
							if (signal.getLabel() != null && ScopeB.getByLabel(schema, signal.getLabel()) != null) {
								final String label = ScopeB.getUniqueName(schema, signal.getLabel());
								signal.setLabel(label);
							}
						}

						old_eqp.add((Equipment) src);
						schema.getEquipment().add((Equipment) obj);
					} else if (obj instanceof Commentary) {
						schema.getComments().add((Commentary) obj);
					}
				}

				final Map<Segment, Segment> seg_map = recoverSegments(src_schema, schema, old_nodes);
				recoverWires(src_schema, schema, old_eqp, seg_map);
			}
		}

		return CommandResult.newOKCommandResult();
	}

	private void recoverWires(Project source_schema, Project schema, Set<Equipment> old_eqp, Map<Segment, Segment> seg_map) {
		final List<Wire> tocopy = new ArrayList<>(20);
		for (final Wire w : source_schema.getWires()) {
			if (old_eqp.contains(w.getFrom())) {
				tocopy.add(w);
			}
		}

		for (final Wire w : tocopy) {
			copyWire(schema, w, seg_map);
		}
	}

	private void copyWire(Project schema, Wire w, Map<Segment, Segment> seg_map) {
		final Equipment from = (Equipment) resolveFromOriginal(w.getFrom());
		final Segment to = seg_map.get(w.getTo());

		if (from == null || to == null) {
			return;
		}

		final Wire copy = TracksideFactory.eINSTANCE.createWire();
		copy.setFrom(from);
		copy.setTo(to);
		copy.setOffset(w.getOffset());

		schema.getWires().add(copy);
	}

	private Map<Segment, Segment> recoverSegments(Project source_schema, Project schema, Set<Node> old_nodes) {

		final List<Segment> tocopy = new ArrayList<>(20);
		for (final Segment s : source_schema.getSegments()) {
			if (old_nodes.contains(s.getFrom()) && old_nodes.contains(s.getTo())) {
				tocopy.add(s);
			}
		}

		final Map<Segment, Segment> map = new HashMap<>(tocopy.size());

		for (final Segment s : tocopy) {
			final Segment c = copySegment(schema, s);
			map.put(s, c);
		}

		return map;
	}

	private Segment copySegment(Project schema, Segment s) {
		final Node from = (Node) resolveFromOriginal(s.getFrom());
		final Node to = (Node) resolveFromOriginal(s.getTo());

		final Segment copy = SchemaFactory.eINSTANCE.createSegment();
		copy.setFrom(from);
		copy.setTo(to);
		copy.setLength(s.getLength());
		copy.setPointrole(s.getPointrole());
		copy.setRole(s.getRole());
		copy.setStyle(s.getStyle());
		copy.setHeightmap(EcoreUtil.copy(s.getHeightmap()));
		copy.setVisible(true);
		copy.getAttributes().addAll(EcoreUtil.copyAll(s.getAttributes()));

		String label = s.getLabel();

		if (ScopeC.getByLabel(schema, label) != null) {
			label = ScopeC.getUniqueName(schema, label);
		}

		copy.setLabel(label);

		schema.getSegments().add(copy);

		return copy;
	}

	private EObject resolveFromOriginal(EObject source) {
		for (final ObjectCopy oc : elemToPaste) {
			if (oc.getSourceObject() == source) {
				return oc.getCopy();
			}
		}

		return null;
	}
}
