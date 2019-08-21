package uk.ac.ncl.safecap.mcommon.conf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import safecap.Project;
import safecap.model.Ambit;
import safecap.schema.Node;
import safecap.schema.NodeRole;
import safecap.trackside.LeftSignal;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.Overlap;
import uk.ac.ncl.safecap.misc.core.OverlapUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.misc.core.SignalUtil;
import uk.ac.ncl.safecap.misc.core.SubOverlap;
import uk.ac.ncl.safecap.misc.core.SubRoute;
import uk.ac.ncl.safecap.misc.core.SubRouteUtil;

public class SubOverlapBuilder {

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

	public static void commitOverlap(Signal signal, List<SubOverlap> list, int full, boolean custom) {
		final NodePath np = getNodePath2(list);
		final String name = generateOverlapName2(list, full);
		setOverlapPath(signal, name, np.getPathString());
		setOverlapLength(signal, name, np.length);
		setOverlapKind(signal, name, full);
		setOverlapCustom(signal, name);

		computeNodeRole(np.path.get(np.path.size() - 1));

		// System.out.println("Committing overlap " + name + "; length " + length + ";
		// Signal " + signal);
	}

	private static String generateOverlapName2(List<SubOverlap> list, int kind) {
		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				sb.append(";");
			}
			sb.append(list.get(i).toString());
		}

		if (kind == 1) {
			sb.append("-F");
		} else if (kind == 0) {
			sb.append("-R");
		}

		return "OVL:" + sb.toString();
	}

	private static NodePath getNodePath2(List<SubOverlap> list) {
		final List<Node> path = new ArrayList<>();
		int len = 0;
		for (final SubOverlap sr : list) {
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

	public static void deleteOverlapDefinition(Signal signal, String name) {
		Overlap overlap = new Overlap(signal, name);
		final List<Node> zlist = OverlapUtil.getNodePath(overlap);

		if (!zlist.isEmpty()) {
			ExtensionUtil.delete(signal, SafecapConstants.EXT_OVERLAP_PATH, name);
			ExtensionUtil.delete(signal, SafecapConstants.EXT_OVERLAP_LENGTH, name);
			ExtensionUtil.delete(signal, SafecapConstants.EXT_OVERLAP_END_NODE, signal.getLabel());
			ExtensionUtil.delete(signal, SafecapConstants.EXT_OVERLAP_KIND, name);
			ExtensionUtil.delete(signal, SafecapConstants.EXT_OVERLAP_CUSTOM, name);

			computeNodeRole(zlist.get(zlist.size() - 1));
		}
	}
	
	public static void deleteSubOverlap(SubOverlap so) {
		ExtensionUtil.delete(so.getAmbit(), SafecapConstants.EXT_SUBOVERLAP, so.getDirection());
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
			ExtensionUtil.delete(signal, SafecapConstants.EXT_OVERLAP_CUSTOM, name);

			final List<SubOverlap> suboverlaps = OverlapUtil.getOverlapSubOverlaps(EmfUtil.getProject(signal), overlap);
			if (suboverlaps != null) {
				for (final SubOverlap so : suboverlaps) {
					ExtensionUtil.delete(so.getAmbit(), SafecapConstants.EXT_SUBOVERLAP, so.getDirection());
				}
			}

			computeNodeRole(zlist.get(zlist.size() - 1));

			// System.out.println("Deleted overlap " + name);
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

	public static void commitOverlapTransactionally(Project project, Signal s, Node n, int isFull) {
		try {
			final List<SubRoute> path = SubRouteUtil.getSubRoutePath(s, n);
			int length = SignalUtil.getSignalOffsetFromJoint(project, s);
			for (final SubRoute sr : path) {
				length += sr.getLength();
			}

			if (path.isEmpty()) {
				final Node self = SignalUtil.getSignalNode(s);
				if (n.equals(self)) {
					System.err.println("\tNo track extend for overlap from " + s + " to node " + n);
				} else {
					System.err.println("\tCannot find sub route path from signal " + s + " to node " + n);
				}
			} else {
				getCommitOverlapCommand2(s, path, length, isFull).execute(new NullProgressMonitor(), null);
				// System.out.println("\tCommitted overlap from " + s + " to node " + n);
			}
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
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

	private static void prepareNodeOverlaps(Node node) {
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

	private static void setOverlapCustom(Signal signal, String name) {
		signal.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_OVERLAP_CUSTOM, name, true));
	}

	public static void setSuboverlap(Ambit ambit, String orientation) {
		// System.out.println("Setting flag " + SafecapConstants.EXT_SUBOVERLAP + " for
		// " + ambit.getLabel() + "/" + orientation);
		ambit.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_SUBOVERLAP, orientation, true));
	}

	public static void setSuboverlap(Ambit ambit, String orientation, String name) {
		// System.out.println("Setting flag " + SafecapConstants.EXT_SUBOVERLAP + " for
		// " + ambit.getLabel() + "/" + orientation);
		ambit.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_SUBOVERLAP, orientation, true));
		ambit.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_SUBOVERLAP_PLUS, orientation, name));
	}

}
