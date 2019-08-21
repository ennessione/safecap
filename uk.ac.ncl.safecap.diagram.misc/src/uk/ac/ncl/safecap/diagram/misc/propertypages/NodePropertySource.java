package uk.ac.ncl.safecap.diagram.misc.propertypages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import safecap.Project;
import safecap.derived.MergedPoint;
import safecap.model.Ambit;
import safecap.model.Point;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.NodeRole;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.diagram.misc.postprocess.SubRouteSubOverlapDetection;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.Overlap;
import uk.ac.ncl.safecap.misc.core.OverlapUtil;
import uk.ac.ncl.safecap.misc.core.PointUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;
import uk.ac.ncl.safecap.misc.core.SubOverlap;

public class NodePropertySource extends LabeledPropertySource {
	protected Node node;
	protected static final String[] kinds1 = new String[] { "Boundary", "Terminal" };
	protected static final String[] kinds2 = new String[] { "Circuit limit", "Transparent" };
	protected static final String[] roles = new String[] { "Sink", "Source", "Both" };
	protected static final String[] ctype = new String[] { "Unswitched", "Switched" };

	private MergedPoint merged;
	private final Map<String, Overlap> overlaps;

	public NodePropertySource(final Node node) {
		super(node);
		this.node = node;
		overlaps = new HashMap<>();
	}

	@Override
	public void getDescriptors(List<IPropertyDescriptor> list) {
		if (node.getKind() == NodeKind.BOUNDARY || node.getKind() == NodeKind.TERMINAL) {
			list.add(new ComboBoxPropertyDescriptor("kind", "Kind", kinds1));
		} else if (node.getKind() == NodeKind.NORMAL || node.getKind() == NodeKind.SILENT) {
			list.add(new ComboBoxPropertyDescriptor("kind", "Kind", kinds2));
		}

		if (node.getKind() == NodeKind.POINT) {
			list.add(new ComboBoxPropertyDescriptor("trap", "A trap point", yesno));
			final Point point = PointUtil.getPointFor(node);
			merged = PointUtil.getMergedByPoint(EmfUtil.getProject(node), point);

			if (merged != null) {
				list.add(new TextPropertyDescriptor("owner", "Owner"));
			}
		}

		if (node.getKind() == NodeKind.BOUNDARY) {
			list.add(new ComboBoxPropertyDescriptor("role", "Role", roles));

			String tag;

			if (NodeUtil.isOnlySink(node)) {
				tag = "Exit speed";
			} else if (NodeUtil.isOnlySource(node)) {
				tag = "Entry speed";
			} else {
				tag = "Entry/Exit speed";
			}

			list.add(new TextPropertyDescriptor("speed", tag));
		}

		if (node.getKind() == NodeKind.NORMAL || node.getKind() == NodeKind.BOUNDARY || node.getKind() == NodeKind.TERMINAL) {
			if (ExtensionUtil.getBool(node, SafecapConstants.EXT_LDL_GEN, "isBoundary")) {

				if (!NodeUtil.getIncomingSegments(node).isEmpty()) {
					list.add(new TextPropertyDescriptor("interlockingLeft", "Interlocking name, left"));
				}
				if (!NodeUtil.getOutgoingSegments(node).isEmpty()) {
					list.add(new TextPropertyDescriptor("interlockingRight", "Interlocking name, right"));
				}
			}
			list.add(new ComboBoxPropertyDescriptor("interlockingBoundary", "Interlocking boundary", yesno));
		}

		if (node.getKind() == NodeKind.CROSSOVER || node.getKind() == NodeKind.SWITCHED_CROSSOVER) {
			list.add(new ComboBoxPropertyDescriptor("ctype", "Crossover kind", ctype));
		}

		if (!NodeUtil.isJunctionNode(node)) {
			final Project root = EmfUtil.getProject(node);
			int i = 1;
			for (final Overlap overlap : OverlapUtil.getAllOverlapsForNode(root, node)) {
				list.add(new TextPropertyDescriptor("overlap" + i, "Overlap " + i));
				overlaps.put("overlap" + i, overlap);
				i++;
			}

			list.add(new TextPropertyDescriptor("protect.right", "Sub route marker, left"));
			list.add(new TextPropertyDescriptor("protect.left", "Sub route marker, right"));
		}

		super.getDescriptors(list);
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.toString().startsWith("overlap")) {
			final Overlap ol = overlaps.get(id.toString());
			if (ol != null) {
				final Project root = EmfUtil.getProject(node);
				final StringBuilder sb = new StringBuilder();
				sb.append(ol.describe());
				sb.append(": ");
				final List<SubOverlap> so_list = OverlapUtil.getOverlapSubOverlaps(root, ol);
				if (so_list == null) {
					sb.append("invalid path " + ol.getName());
				} else {
					sb.append(so_list.toString());
				}
				return sb.toString();
			}
		} else if (id.toString().equals("protect.left")) {
			return ExtensionUtil.getString(node, SafecapConstants.EXT_NODE_TAG_PROTECT, "left", "auto");
		} else if (id.toString().equals("protect.right")) {
			return ExtensionUtil.getString(node, SafecapConstants.EXT_NODE_TAG_PROTECT, "right", "auto");
		} else if (id.toString().equals("interlockingBoundary")) {
			return ExtensionUtil.getBool(node, SafecapConstants.EXT_LDL_GEN, "isBoundary") ? 0 : 1;
		} else if (id.toString().equals("interlockingLeft")) {
			return ExtensionUtil.getString(node, SafecapConstants.EXT_LDL_GEN, "interlockingLeft", "unknown");
		} else if (id.toString().equals("interlockingRight")) {
			return ExtensionUtil.getString(node, SafecapConstants.EXT_LDL_GEN, "interlockingRight", "unknown");
		} else if (id.toString().equals("ctype")) {
			if (node.getKind() == NodeKind.CROSSOVER) {
				return 0;
			} else {
				return 1;
			}
		} else if (id.toString().equals("speed")) {
			if (node.getSpeedlimit() == 0) {
				return "UNLIMITED";
			} else {
				return "" + node.getSpeedlimit();
			}
		} else if (id.toString().equals("trap")) {
			final boolean isTrap = ExtensionUtil.getBool(node, SafecapConstants.EXT_POINT, SafecapConstants.EXT_POINT_IS_TRAP);
			return isTrap ? 0 : 1;
		} else if (id.toString().equals("owner")) {
			return merged.getLabel();
		} else if (id.toString().equals("kind")) {
			if (node.getKind() == NodeKind.BOUNDARY || node.getKind() == NodeKind.TERMINAL) {
				if (node.getKind() == NodeKind.TERMINAL) {
					return 1;
				} else {
					return 0;
				}
			} else {
				if (node.getKind() == NodeKind.SILENT) {
					return 1;
				} else {
					return 0;
				}
			}

		}
		if (id.toString().equals("role")) {
			if (NodeUtil.isOnlySink(node)) {
				return 0;
			} else if (NodeUtil.isOnlySource(node)) {
				return 1;
			} else {
				return 2;
			}
		} else {
			return super.getPropertyValue(id);
		}
	}

	@Override
	public boolean setValueCommand(Object id, Object value) {
		if (id.toString().equals("protect.left")) {
			if (value instanceof String && ((String) value).length() == 1) {
				final String v = (String) value;
				ExtensionUtil.delete(node, SafecapConstants.EXT_NODE_TAG_PROTECT, "left");
				node.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_NODE_TAG_PROTECT, "left", v));
				SubRouteSubOverlapDetection.setNodeLeftRole(node, v);

			} else {
				ExtensionUtil.delete(node, SafecapConstants.EXT_NODE_TAG_PROTECT, "left");
			}
			return true;
		} else if (id.toString().equals("protect.right")) {
			if (value instanceof String && ((String) value).length() == 1) {
				final String v = (String) value;
				ExtensionUtil.delete(node, SafecapConstants.EXT_NODE_TAG_PROTECT, "right");
				node.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_NODE_TAG_PROTECT, "right", v));
				SubRouteSubOverlapDetection.setNodeRightRole(node, v);
			} else {
				ExtensionUtil.delete(node, SafecapConstants.EXT_NODE_TAG_PROTECT, "right");
			}
			return true;
		}

		if (value == null || value.toString().length() == 0) {
			return true;
		}

		if (id.toString().equals("ctype")) {
			final String s = value.toString();
			final int index = Integer.parseInt(s);
			if (node.getKind() == NodeKind.CROSSOVER || node.getKind() == NodeKind.SWITCHED_CROSSOVER) {
				switch (index) {
				case 0:
					node.setKind(NodeKind.CROSSOVER);
					break;
				case 1:
					node.setKind(NodeKind.SWITCHED_CROSSOVER);
					break;
				}
			}
			return true;
		} else if (id.toString().equals("speed")) {
			try {
				final String s = value.toString();
				if (s.equals("UNLIMITED")) {
					node.setSpeedlimit(0);
				} else {
					final double speed = Double.parseDouble(s);
					node.setSpeedlimit(speed);
				}
			} catch (final NumberFormatException e) {
				node.setSpeedlimit(0);
			}
			return true;
		} else if (id.toString().equals("trap")) {
			try {
				final String s = value.toString();
				final int index = Integer.parseInt(s);
				ExtensionUtil.delete(node, SafecapConstants.EXT_POINT, SafecapConstants.EXT_POINT_IS_TRAP);
				if (index == 0) {
					node.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_POINT, SafecapConstants.EXT_POINT_IS_TRAP, true));
				}
			} catch (final NumberFormatException e) {
				node.setSpeedlimit(0);
			}
			return true;
		} else if (id.toString().equals("interlockingBoundary")) {
			try {
				final String s = value.toString();
				final int index = Integer.parseInt(s);
				ExtensionUtil.delete(node, SafecapConstants.EXT_LDL_GEN, "isBoundary");
				if (index == 0) {
					node.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_LDL_GEN, "isBoundary", true));
				} else {
					ExtensionUtil.delete(node, SafecapConstants.EXT_LDL_GEN, "interlockingLeft");
					ExtensionUtil.delete(node, SafecapConstants.EXT_LDL_GEN, "interlockingRight");
				}
			} catch (final NumberFormatException e) {
				node.setSpeedlimit(0);
			}
			return true;
		} else if (id.toString().equals("interlockingLeft")) {
			if (value.toString().length() > 0) {
				final String prev = ExtensionUtil.getString(node, SafecapConstants.EXT_LDL_GEN, "interlockingLeft");
				ExtensionUtil.delete(node, SafecapConstants.EXT_LDL_GEN, "interlockingLeft");
				node.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_LDL_GEN, "interlockingLeft", value.toString()));
				if (prev == null || !prev.equals(value.toString())) {
					try {
						setInterlockingLeft(value.toString());
					} catch (final Throwable e) {
						ExtensionUtil.delete(node, SafecapConstants.EXT_LDL_GEN, "isBoundary");
						ExtensionUtil.delete(node, SafecapConstants.EXT_LDL_GEN, "interlockingLeft");
						ExtensionUtil.delete(node, SafecapConstants.EXT_LDL_GEN, "interlockingRight");
						MessageDialog.openError(null, "Interlocking setting failed", e.getMessage());
						return false;
					}
				}
				return true;
			}
			return false;
		} else if (id.toString().equals("interlockingRight")) {
			if (value.toString().length() > 0) {
				final String prev = ExtensionUtil.getString(node, SafecapConstants.EXT_LDL_GEN, "interlockingRight");
				ExtensionUtil.delete(node, SafecapConstants.EXT_LDL_GEN, "interlockingRight");
				node.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_LDL_GEN, "interlockingRight", value.toString()));
				if (prev == null || !prev.equals(value.toString())) {
					try {
						setInterlockingRight(value.toString());
					} catch (final Throwable e) {
						ExtensionUtil.delete(node, SafecapConstants.EXT_LDL_GEN, "isBoundary");
						ExtensionUtil.delete(node, SafecapConstants.EXT_LDL_GEN, "interlockingLeft");
						ExtensionUtil.delete(node, SafecapConstants.EXT_LDL_GEN, "interlockingRight");
						MessageDialog.openError(null, "Interlocking setting failed", e.getMessage());
						return false;
					}
				}
				return true;
			}
			return false;
		} else if (id.toString().equals("kind")) {
			final String s = value.toString();
			final int index = Integer.parseInt(s);

			if (node.getKind() == NodeKind.BOUNDARY || node.getKind() == NodeKind.TERMINAL) {
				switch (index) {
				case 0:
					node.setKind(NodeKind.BOUNDARY);
					break;
				case 1:
					node.setKind(NodeKind.TERMINAL);
					break;
				}

			} else {
				switch (index) {
				case 0:
					node.setKind(NodeKind.NORMAL);
					break;
				case 1:
					node.setKind(NodeKind.SILENT);
					break;
				}
			}

			return true;
		} else if (id.toString().equals("role")) {
			final String s = value.toString();
			final int index = Integer.parseInt(s);
			int role = node.getRoles() & ~(NodeRole.SINK_VALUE | NodeRole.SOURCE_VALUE); // clear sink/source bits
			switch (index) {
			case 0:
				role |= NodeRole.SINK_VALUE;
				break;
			case 1:
				role |= NodeRole.SOURCE_VALUE;
				break;
			case 2:
				role |= NodeRole.SINK_VALUE | NodeRole.SOURCE_VALUE;
				break;
			}
			node.setRoles(role);
			return true;
		} else {
			return super.setValueCommand(id, value);
		}
	}

	private void setInterlockingLeft(String name) {
		final List<Segment> list = NodeUtil.getIncomingSegments(node);
		if (list.size() == 1) {
			final Segment segment = list.get(0);
			final Ambit ambit = SegmentUtil.getSegmentAmbit(segment);
			final List<Ambit> visited = new ArrayList<>(50);
			setInterlockingLeft(ambit, name, visited);
		}
	}

	private void setInterlockingRight(String name) {
		final List<Segment> list = NodeUtil.getOutgoingSegments(node);
		if (list.size() == 1) {
			final Segment segment = list.get(0);
			final Ambit ambit = SegmentUtil.getSegmentAmbit(segment);
			final List<Ambit> visited = new ArrayList<>(50);
			setInterlockingRight(ambit, name, visited);
		}
	}

	private void setInterlockingLeft(Ambit ambit, String name, List<Ambit> visited) {
		ExtensionUtil.delete(ambit, SafecapConstants.EXT_LDL_GEN, "interlocking");
		if (name != null) {
			ambit.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_LDL_GEN, "interlocking", name));
		}
		visited.add(ambit);
		for (final Node n : AmbitUtil.getAmbitLeftBoundaryNodes(ambit)) {
			final boolean isBoundary = ExtensionUtil.getBool(n, SafecapConstants.EXT_LDL_GEN, "isBoundary");
			if (isBoundary) {
				final String interlockingRight = ExtensionUtil.getString(n, SafecapConstants.EXT_LDL_GEN, "interlockingRight", null);
				if (name == null && interlockingRight != null || name != null && !name.equals(interlockingRight)) {
					throw new IllegalArgumentException("Node "
							+ (n.getLabel() != null ? n.getLabel() : " to the right of " + ambit.getLabel())
							+ " has conflicting interlocking declaration " + (interlockingRight != null ? interlockingRight : "<none>"));
				}
			} else {
				for (final Ambit a : NodeUtil.getRightBoundaryAmbits(n)) {
					if (!visited.contains(a)) {
						setInterlockingLeft(a, name, visited);
					}
				}
			}
		}
	}

	private void setInterlockingRight(Ambit ambit, String name, List<Ambit> visited) {
		ExtensionUtil.delete(ambit, SafecapConstants.EXT_LDL_GEN, "interlocking");
		if (name != null) {
			ambit.getAttributes().add(ExtensionUtil.mkString(SafecapConstants.EXT_LDL_GEN, "interlocking", name));
		}
		visited.add(ambit);
		for (final Node n : AmbitUtil.getAmbitRightBoundaryNodes(ambit)) {
			final boolean isBoundary = ExtensionUtil.getBool(n, SafecapConstants.EXT_LDL_GEN, "isBoundary");
			if (isBoundary) {
				final String interlockingLeft = ExtensionUtil.getString(n, SafecapConstants.EXT_LDL_GEN, "interlockingLeft", null);
				if (name == null && interlockingLeft != null || name != null && !name.equals(interlockingLeft)) {
					throw new IllegalArgumentException("Node "
							+ (n.getLabel() != null ? n.getLabel() : " to the right of " + ambit.getLabel())
							+ " has conflicting interlocking declaration " + (interlockingLeft != null ? interlockingLeft : "<none>"));
				}
			} else {
				for (final Ambit a : NodeUtil.getLeftBoundaryAmbits(n)) {
					if (!visited.contains(a)) {
						setInterlockingRight(a, name, visited);
					}
				}
			}
		}
	}

}
