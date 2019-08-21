package safecap.diagram.part;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import safecap.diagram.providers.SafecapElementTypes;

/**
 * @generated
 */
public class SafecapPaletteFactory {

	/**
	 * @generated
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createRail1Group());
		paletteRoot.add(createTrackside2Group());
		paletteRoot.add(createCommentary3Group());
	}

	/**
	 * Creates "Rail" palette tool group
	 * 
	 * @generated
	 */
	private PaletteContainer createRail1Group() {
		final PaletteGroup paletteContainer = new PaletteGroup(Messages.Rail1Group_title);
		paletteContainer.setId("createRail1Group"); //$NON-NLS-1$
		paletteContainer.setDescription(Messages.Rail1Group_desc);
		paletteContainer.add(createNode1CreationTool());
		paletteContainer.add(createSegment2CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "Trackside" palette tool group
	 * 
	 * @generated
	 */
	private PaletteContainer createTrackside2Group() {
		final PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Trackside2Group_title);
		paletteContainer.setId("createTrackside2Group"); //$NON-NLS-1$
		paletteContainer.add(createWire1CreationTool());
		paletteContainer.add(createSignalL2CreationTool());
		paletteContainer.add(createSignalR3CreationTool());
		paletteContainer.add(createSpeedLimitL4CreationTool());
		paletteContainer.add(createSpeedLimitR5CreationTool());
		paletteContainer.add(createFixedRedL6CreationTool());
		paletteContainer.add(createFixedRedR7CreationTool());
		paletteContainer.add(createStationL8CreationTool());
		paletteContainer.add(createStationR9CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "Commentary" palette tool group
	 * 
	 * @generated
	 */
	private PaletteContainer createCommentary3Group() {
		final PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Commentary3Group_title);
		paletteContainer.setId("createCommentary3Group"); //$NON-NLS-1$
		paletteContainer.add(createPlatform1CreationTool());
		paletteContainer.add(createBridge2CreationTool());
		paletteContainer.add(createSubSchema3CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createNode1CreationTool() {
		final NodeToolEntry entry = new NodeToolEntry(Messages.Node1CreationTool_title, Messages.Node1CreationTool_desc,
				Collections.singletonList(SafecapElementTypes.Node_2003));
		entry.setId("createNode1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SafecapElementTypes.getImageDescriptor(SafecapElementTypes.Node_2003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createSegment2CreationTool() {
		final LinkToolEntry entry = new LinkToolEntry(Messages.Segment2CreationTool_title, Messages.Segment2CreationTool_desc,
				Collections.singletonList(SafecapElementTypes.Segment_4001));
		entry.setId("createSegment2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SafecapElementTypes.getImageDescriptor(SafecapElementTypes.Segment_4001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createWire1CreationTool() {
		final ArrayList<IElementType> types = new ArrayList<>(2);
		types.add(SafecapElementTypes.Wire_4002);
		types.add(SafecapElementTypes.NodeWire_4004);
		final LinkToolEntry entry = new LinkToolEntry(Messages.Wire1CreationTool_title, null, types);
		entry.setId("createWire1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SafecapElementTypes.getImageDescriptor(SafecapElementTypes.Wire_4002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createSignalL2CreationTool() {
		final NodeToolEntry entry = new NodeToolEntry(Messages.SignalL2CreationTool_title, null,
				Collections.singletonList(SafecapElementTypes.LeftSignal_2004));
		entry.setId("createSignalL2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SafecapElementTypes.getImageDescriptor(SafecapElementTypes.LeftSignal_2004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createSignalR3CreationTool() {
		final NodeToolEntry entry = new NodeToolEntry(Messages.SignalR3CreationTool_title, null,
				Collections.singletonList(SafecapElementTypes.RightSignal_2005));
		entry.setId("createSignalR3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SafecapElementTypes.getImageDescriptor(SafecapElementTypes.RightSignal_2005));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createSpeedLimitL4CreationTool() {
		final NodeToolEntry entry = new NodeToolEntry(Messages.SpeedLimitL4CreationTool_title, null,
				Collections.singletonList(SafecapElementTypes.LeftSpeedLimit_2006));
		entry.setId("createSpeedLimitL4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SafecapElementTypes.getImageDescriptor(SafecapElementTypes.LeftSpeedLimit_2006));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createSpeedLimitR5CreationTool() {
		final NodeToolEntry entry = new NodeToolEntry(Messages.SpeedLimitR5CreationTool_title, null,
				Collections.singletonList(SafecapElementTypes.RightSpeedLimit_2007));
		entry.setId("createSpeedLimitR5CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SafecapElementTypes.getImageDescriptor(SafecapElementTypes.RightSpeedLimit_2007));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createFixedRedL6CreationTool() {
		final NodeToolEntry entry = new NodeToolEntry(Messages.FixedRedL6CreationTool_title, null,
				Collections.singletonList(SafecapElementTypes.RedLeftSignal_2008));
		entry.setId("createFixedRedL6CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SafecapElementTypes.getImageDescriptor(SafecapElementTypes.RedLeftSignal_2008));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createFixedRedR7CreationTool() {
		final NodeToolEntry entry = new NodeToolEntry(Messages.FixedRedR7CreationTool_title, null,
				Collections.singletonList(SafecapElementTypes.RedRightSignal_2009));
		entry.setId("createFixedRedR7CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SafecapElementTypes.getImageDescriptor(SafecapElementTypes.RedRightSignal_2009));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createStationL8CreationTool() {
		final NodeToolEntry entry = new NodeToolEntry(Messages.StationL8CreationTool_title, Messages.StationL8CreationTool_desc,
				Collections.singletonList(SafecapElementTypes.LeftStopAndGo_2010));
		entry.setId("createStationL8CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SafecapElementTypes.getImageDescriptor(SafecapElementTypes.LeftStopAndGo_2010));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createStationR9CreationTool() {
		final NodeToolEntry entry = new NodeToolEntry(Messages.StationR9CreationTool_title, Messages.StationR9CreationTool_desc,
				Collections.singletonList(SafecapElementTypes.RightStopAndGo_2011));
		entry.setId("createStationR9CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SafecapElementTypes.getImageDescriptor(SafecapElementTypes.RightStopAndGo_2011));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createPlatform1CreationTool() {
		final NodeToolEntry entry = new NodeToolEntry(Messages.Platform1CreationTool_title, Messages.Platform1CreationTool_desc,
				Collections.singletonList(SafecapElementTypes.Station_2012));
		entry.setId("createPlatform1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SafecapElementTypes.getImageDescriptor(SafecapElementTypes.Station_2012));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createBridge2CreationTool() {
		final NodeToolEntry entry = new NodeToolEntry(Messages.Bridge2CreationTool_title, Messages.Bridge2CreationTool_desc,
				Collections.singletonList(SafecapElementTypes.Bridge_2013));
		entry.setId("createBridge2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SafecapElementTypes.getImageDescriptor(SafecapElementTypes.Bridge_2013));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createSubSchema3CreationTool() {
		final NodeToolEntry entry = new NodeToolEntry(Messages.SubSchema3CreationTool_title, Messages.SubSchema3CreationTool_desc,
				Collections.singletonList(SafecapElementTypes.SubSchemaNode_2015));
		entry.setId("createSubSchema3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SafecapElementTypes.getImageDescriptor(SafecapElementTypes.SubSchemaNode_2015));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private static class NodeToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List<IElementType> elementTypes;

		/**
		 * @generated
		 */
		private NodeToolEntry(String title, String description, List<IElementType> elementTypes) {
			super(title, description, null, null);
			this.elementTypes = elementTypes;
		}

		/**
		 * @generated
		 */
		@Override
		public Tool createTool() {
			final Tool tool = new UnspecifiedTypeCreationTool(elementTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

	/**
	 * @generated
	 */
	private static class LinkToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List<IElementType> relationshipTypes;

		/**
		 * @generated
		 */
		private LinkToolEntry(String title, String description, List<IElementType> relationshipTypes) {
			super(title, description, null, null);
			this.relationshipTypes = relationshipTypes;
		}

		/**
		 * @generated
		 */
		@Override
		public Tool createTool() {
			final Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}
}
