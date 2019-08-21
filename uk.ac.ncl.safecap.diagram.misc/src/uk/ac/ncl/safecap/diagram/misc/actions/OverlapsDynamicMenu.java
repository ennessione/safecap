package uk.ac.ncl.safecap.diagram.misc.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

import safecap.Orientation;
import safecap.Project;
import safecap.model.Ambit;
import safecap.schema.Node;
import safecap.schema.Segment;
import safecap.trackside.LeftSignal;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.common.resources.CommonPlugin;
import uk.ac.ncl.safecap.mcommon.preferences.SchemaPreferenceConstants;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ISegmentWalkerFilter2;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.Overlap;
import uk.ac.ncl.safecap.misc.core.OverlapUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.misc.core.SegmentPath;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;
import uk.ac.ncl.safecap.misc.core.SignalUtil;
import uk.ac.ncl.safecap.misc.core.SubRoute;
import uk.ac.ncl.safecap.misc.core.SubRouteUtil;

public class OverlapsDynamicMenu extends CompoundContributionItem {

	@Override
	protected IContributionItem[] getContributionItems() {

		final List<CommandContributionItem> contributionItems = new ArrayList<>();
		final Node node = getCurrentEditorContent();
		contributionItems.addAll(populate(node, Orientation.LEFT));
		contributionItems.addAll(populate(node, Orientation.RIGHT));
		if (CommonPlugin.getBooleanPreference(SchemaPreferenceConstants.P_F_OVERLAP_CUSTOM)) {
			contributionItems.add(makeCustomItem());
		}
		contributionItems.addAll(populateDelete(node));

		return contributionItems.toArray(new CommandContributionItem[contributionItems.size()]);
	}

	private List<CommandContributionItem> populateDelete(Node node) {
		final List<CommandContributionItem> contributionItems = new ArrayList<>();
		final Project root = EmfUtil.getProject(node);

		// System.out.println("all overlaps: " + OverlapUtil.getAllOverlaps(root));

		for (final Overlap overlap : OverlapUtil.getAllOverlapsForNode(root, node)) {

			final String facing = overlap.getSignal() instanceof LeftSignal ? "left" : "right";
			final int kind = overlap.getKind();
			if (kind == -1) {
				contributionItems.add(makeDeleteItem(
						"Delete overlap from " + overlap.getSignal().getLabel() + " (" + overlap.getLength() + "m, " + facing + " facing)",
						overlap.getSignal() + ":::" + overlap.getName()));
			} else if (kind == 0) {
				contributionItems
						.add(makeDeleteItem("Delete reduced overlap from " + overlap.getSignal().getLabel() + "(" + facing + " facing)",
								overlap.getSignal() + ":::" + overlap.getName()));
			} else if (kind == 1) {
				contributionItems
						.add(makeDeleteItem("Delete full overlap from " + overlap.getSignal().getLabel() + "(" + facing + " facing)",
								overlap.getSignal() + ":::" + overlap.getName()));
			}

		}
		return contributionItems;
	}

	public static List<PotentialOverlap> compute(Node node, Orientation or, ISegmentWalkerFilter2 filter) {

		final Project root = EmfUtil.getProject(node);

		final List<Segment> _start = or == Orientation.LEFT ? NodeUtil.getIncomingSegments(node) : NodeUtil.getOutgoingSegments(node);
		final List<PotentialOverlap> overlapSet = new ArrayList<>();
		for (final Segment s : _start) {
			final Collection<SegmentPath> paths = SegmentUtil.buildPaths(s, or.opposite(), filter);
			for (final SegmentPath path : paths) {
				final Ambit lastAmbit = AmbitUtil.getSegmentAmbitMap(root).get(path.getFirst());
				final Set<SubRoute> subroutes = SubRouteUtil.getSubRoutesEnding(lastAmbit, node);
				final Signal signal = SignalUtil.getSignalOn(path.getLast(), or);
				if (signal == null) {
					continue;
				}
				final List<SubRoute> subroutepath = SubRouteUtil.getSubRoutePath(signal, node);
				if (path.getPath().size() > 1 && !subroutes.isEmpty() && subroutepath.size() > 0) {
					final Set<Overlap> overlaps = OverlapUtil.getAllOverlapsForNode(signal, root, node);
					final int length = (int) path.getLength() + SignalUtil.getSignalOffsetFromJoint(root, signal)
							- path.getLast().getLength();

					if (CommonPlugin.getBooleanPreference(SchemaPreferenceConstants.P_F_OVERLAP_FULL)) {
						final boolean hasFull = hasFullOverlap(overlaps);
						if (!hasFull) {
							overlapSet.add(new PotentialOverlap(path, or, signal, true, true, length));
						}
					}

					if (CommonPlugin.getBooleanPreference(SchemaPreferenceConstants.P_F_OVERLAP_REDUCED)) {
						final boolean hasReduced = hasReducedOverlap(overlaps);
						if (!hasReduced) {
							overlapSet.add(new PotentialOverlap(path, or, signal, false, true, length));
						}
					}

					if (CommonPlugin.getBooleanPreference(SchemaPreferenceConstants.P_F_OVERLAP_LENGTH)) {
						final boolean hasLength = hasOtherOverlap(overlaps);
						if (!hasLength) {
							overlapSet.add(
									new PotentialOverlap(path, or, signal, length >= SafecapConstants.FULL_OVERLAP_LENGTH, false, length));
						}
					}
				}
			}
		}

		return overlapSet;
	}

	private List<CommandContributionItem> populate(Node node, Orientation or) {
		final List<PotentialOverlap> overlapSet = compute(node, or,
				CommonPlugin.getBooleanPreference(SchemaPreferenceConstants.P_F_OVERLAP_STEP_OVER) ? SegmentUtil.OverlapFilter2.INSTANCE
						: SegmentUtil.OverlapFilter1.INSTANCE);

		final List<CommandContributionItem> contributionItems = new ArrayList<>(overlapSet.size());

		for (final PotentialOverlap po : overlapSet) {
			contributionItems.add(makeItem(po.getLabel(), po.getSignal().getLabel(), po.getFullFlag()));
		}

		return contributionItems;
	}

	public static class PotentialOverlap implements Comparable {
		private final Orientation orientation;
		private final Signal signal;
		private final boolean full;
		private final boolean ignoreLength;
		private final int length;
		private final SegmentPath path;

		public PotentialOverlap(SegmentPath path, Orientation orientation, Signal signal, boolean full, boolean ignoreLength, int length) {
			this.path = path;
			this.orientation = orientation;
			this.signal = signal;
			this.full = full;
			this.ignoreLength = ignoreLength;
			this.length = length;
		}

		public SegmentPath getPath() {
			return path;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((path == null) ? 0 : path.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PotentialOverlap other = (PotentialOverlap) obj;
			if (path == null) {
				if (other.path != null)
					return false;
			} else if (!path.equals(other.path))
				return false;
			return true;
		}

		public String getLabel() {
			if (ignoreLength) {
				return "From " + signal.getLabel() + ", " + (full ? "full" : "reduced") + " [" + orientation.name() + "]";
			} else {
				final boolean isFull = length >= SafecapConstants.FULL_OVERLAP_LENGTH;
				return "From " + signal.getLabel() + ", (" + length + "m), " + (isFull ? "full" : "reduced") + " [" + orientation.name()
						+ "]";
			}
		}

		public Signal getSignal() {
			return signal;
		}

		public boolean isFull() {
			return full;
		}

		public String getFullFlag() {
			if (!ignoreLength) {
				return null;
			} else if (full) {
				return "full";
			} else {
				return "reduced";
			}
		}

		public boolean isIgnoreLength() {
			return ignoreLength;
		}

		@Override
		public int compareTo(Object o) {
			if (o instanceof PotentialOverlap) {
				final PotentialOverlap other = (PotentialOverlap) o;
				final int c0 = signal.getLabel().compareTo(other.signal.getLabel());
				if (c0 != 0) {
					return c0;
				}
				final int c1 = new Boolean(ignoreLength).compareTo(new Boolean(other.ignoreLength));
				if (c1 != 0) {
					return c1;
				}
				final int c2 = new Integer(length).compareTo(new Integer(other.length));
				if (c2 != 0) {
					return c2;
				}
			}
			return 0;
		}

		@Override
		public String toString() {
			return path.toString();
		}
	}

	private static boolean hasFullOverlap(Set<Overlap> overlaps) {
		for (final Overlap overlap : overlaps) {
			if (overlap.getKind() == 1) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasReducedOverlap(Set<Overlap> overlaps) {
		for (final Overlap overlap : overlaps) {
			if (overlap.getKind() == 0) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasOtherOverlap(Set<Overlap> overlaps) {
		for (final Overlap overlap : overlaps) {
			if (overlap.getKind() == -1) {
				return true;
			}
		}
		return false;
	}

	private CommandContributionItem makeItem(String text, String signal, String full) {
		final CommandContributionItemParameter parm = new CommandContributionItemParameter(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null, OverlapAdd.COMMAND_ID, CommandContributionItem.STYLE_PUSH);

		final HashMap<String, Object> cmdParms = new HashMap<>();
		cmdParms.put(OverlapAdd.PARAM_SIGNAL, signal + (full != null ? "///" + full : ""));
		// cmdParms.put("zz", signal);
		parm.parameters = cmdParms;
		parm.label = text;
		final CommandContributionItem item = new CommandContributionItem(parm);
		item.setVisible(true);
		return item;
	}

	private CommandContributionItem makeCustomItem() {
		final CommandContributionItemParameter parm = new CommandContributionItemParameter(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null, OverlapCustom.COMMAND_ID, CommandContributionItem.STYLE_PUSH);

		parm.label = "Custom";
		final CommandContributionItem item = new CommandContributionItem(parm);
		item.setVisible(true);
		return item;
	}

	private CommandContributionItem makeDeleteItem(String text, String signal) {
		final CommandContributionItemParameter parm = new CommandContributionItemParameter(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null, OverlapDelete.COMMAND_ID, CommandContributionItem.STYLE_PUSH);

		final HashMap<String, String> cmdParms = new HashMap<>();
		cmdParms.put(OverlapDelete.PARAM_SIGNAL, signal);
		parm.parameters = cmdParms;
		parm.label = text;
		final CommandContributionItem item = new CommandContributionItem(parm);
		item.setVisible(true);
		return item;
	}

	public Node getCurrentEditorContent() {
		final IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (activeEditor instanceof DiagramEditor) {
			final DiagramEditor sde = (DiagramEditor) activeEditor;
			final List<?> selection = sde.getDiagramGraphicalViewer().getSelectedEditParts();
			if (selection.size() == 1 && selection.get(0) instanceof ShapeEditPart) {
				final ShapeEditPart node = (ShapeEditPart) selection.get(0);
				if (node.resolveSemanticElement() instanceof Node) {
					return (Node) node.resolveSemanticElement();
				}
			}
		}

		return null;
	}
}
