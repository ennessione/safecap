package safecap.diagram.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.update.DiagramUpdater;

import safecap.Project;
import safecap.commentary.Commentary;
import safecap.diagram.edit.parts.BridgeEditPart;
import safecap.diagram.edit.parts.LeftSignalEditPart;
import safecap.diagram.edit.parts.LeftSpeedLimitEditPart;
import safecap.diagram.edit.parts.LeftStopAndGoEditPart;
import safecap.diagram.edit.parts.NodeEditPart;
import safecap.diagram.edit.parts.NodeWireEditPart;
import safecap.diagram.edit.parts.ProjectEditPart;
import safecap.diagram.edit.parts.RedLeftSignalEditPart;
import safecap.diagram.edit.parts.RedRightSignalEditPart;
import safecap.diagram.edit.parts.RightSignalEditPart;
import safecap.diagram.edit.parts.RightSpeedLimitEditPart;
import safecap.diagram.edit.parts.RightStopAndGoEditPart;
import safecap.diagram.edit.parts.SegmentEditPart;
import safecap.diagram.edit.parts.StationEditPart;
import safecap.diagram.edit.parts.SubSchemaNodeEditPart;
import safecap.diagram.edit.parts.WireEditPart;
import safecap.diagram.providers.SafecapElementTypes;
import safecap.schema.Node;
import safecap.schema.Schema;
import safecap.schema.SchemaPackage;
import safecap.schema.Segment;
import safecap.schema.SubSchemaNode;
import safecap.trackside.Equipment;
import safecap.trackside.LeftSignal;
import safecap.trackside.LeftSpeedLimit;
import safecap.trackside.LeftStopAndGo;
import safecap.trackside.NodeWire;
import safecap.trackside.RedLeftSignal;
import safecap.trackside.RedRightSignal;
import safecap.trackside.RightSignal;
import safecap.trackside.RightSpeedLimit;
import safecap.trackside.RightStopAndGo;
import safecap.trackside.Trackside;
import safecap.trackside.TracksidePackage;
import safecap.trackside.Wire;

/**
 * @generated
 */
public class SafecapDiagramUpdater {

	/**
	 * @generated
	 */
	public static List<SafecapNodeDescriptor> getSemanticChildren(View view) {
		switch (SafecapVisualIDRegistry.getVisualID(view)) {
		case ProjectEditPart.VISUAL_ID:
			return getProject_1000SemanticChildren(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapNodeDescriptor> getProject_1000SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		final Project modelElement = (Project) view.getElement();
		final LinkedList<SafecapNodeDescriptor> result = new LinkedList<>();
//		for (Iterator<?> it = modelElement.getSubschemanodes().iterator(); it.hasNext();) {
//			SubSchemaNode childElement = (SubSchemaNode) it.next();
//			int visualID = SafecapVisualIDRegistry.getNodeVisualID(view, childElement);
//			if (visualID == SubSchemaNodeEditPart.VISUAL_ID) {
//				result.add(new SafecapNodeDescriptor(childElement, visualID));
//				continue;
//			}
//		}
		for (final Iterator<?> it = modelElement.getNodes().iterator(); it.hasNext();) {
			final Node childElement = (Node) it.next();
			final int visualID = SafecapVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == NodeEditPart.VISUAL_ID) {
				result.add(new SafecapNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == SubSchemaNodeEditPart.VISUAL_ID) {
				result.add(new SafecapNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (final Iterator<?> it = modelElement.getEquipment().iterator(); it.hasNext();) {
			final Equipment childElement = (Equipment) it.next();
			final int visualID = SafecapVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == LeftSignalEditPart.VISUAL_ID) {
				result.add(new SafecapNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RightSignalEditPart.VISUAL_ID) {
				result.add(new SafecapNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == LeftSpeedLimitEditPart.VISUAL_ID) {
				result.add(new SafecapNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RightSpeedLimitEditPart.VISUAL_ID) {
				result.add(new SafecapNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RedLeftSignalEditPart.VISUAL_ID) {
				result.add(new SafecapNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RedRightSignalEditPart.VISUAL_ID) {
				result.add(new SafecapNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == LeftStopAndGoEditPart.VISUAL_ID) {
				result.add(new SafecapNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RightStopAndGoEditPart.VISUAL_ID) {
				result.add(new SafecapNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (final Iterator<?> it = modelElement.getComments().iterator(); it.hasNext();) {
			final Commentary childElement = (Commentary) it.next();
			final int visualID = SafecapVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == StationEditPart.VISUAL_ID) {
				result.add(new SafecapNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == BridgeEditPart.VISUAL_ID) {
				result.add(new SafecapNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getContainedLinks(View view) {
		switch (SafecapVisualIDRegistry.getVisualID(view)) {
		case ProjectEditPart.VISUAL_ID:
			return getProject_1000ContainedLinks(view);
		case SubSchemaNodeEditPart.VISUAL_ID:
			return getSubSchemaNode_2015ContainedLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2003ContainedLinks(view);
		case LeftSignalEditPart.VISUAL_ID:
			return getLeftSignal_2004ContainedLinks(view);
		case RightSignalEditPart.VISUAL_ID:
			return getRightSignal_2005ContainedLinks(view);
		case LeftSpeedLimitEditPart.VISUAL_ID:
			return getLeftSpeedLimit_2006ContainedLinks(view);
		case RightSpeedLimitEditPart.VISUAL_ID:
			return getRightSpeedLimit_2007ContainedLinks(view);
		case RedLeftSignalEditPart.VISUAL_ID:
			return getRedLeftSignal_2008ContainedLinks(view);
		case RedRightSignalEditPart.VISUAL_ID:
			return getRedRightSignal_2009ContainedLinks(view);
		case LeftStopAndGoEditPart.VISUAL_ID:
			return getLeftStopAndGo_2010ContainedLinks(view);
		case RightStopAndGoEditPart.VISUAL_ID:
			return getRightStopAndGo_2011ContainedLinks(view);
		case StationEditPart.VISUAL_ID:
			return getStation_2012ContainedLinks(view);
		case BridgeEditPart.VISUAL_ID:
			return getBridge_2013ContainedLinks(view);
		case SegmentEditPart.VISUAL_ID:
			return getSegment_4001ContainedLinks(view);
		case WireEditPart.VISUAL_ID:
			return getWire_4002ContainedLinks(view);
		case NodeWireEditPart.VISUAL_ID:
			return getNodeWire_4004ContainedLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getIncomingLinks(View view) {
		switch (SafecapVisualIDRegistry.getVisualID(view)) {
		case SubSchemaNodeEditPart.VISUAL_ID:
			return getSubSchemaNode_2015IncomingLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2003IncomingLinks(view);
		case LeftSignalEditPart.VISUAL_ID:
			return getLeftSignal_2004IncomingLinks(view);
		case RightSignalEditPart.VISUAL_ID:
			return getRightSignal_2005IncomingLinks(view);
		case LeftSpeedLimitEditPart.VISUAL_ID:
			return getLeftSpeedLimit_2006IncomingLinks(view);
		case RightSpeedLimitEditPart.VISUAL_ID:
			return getRightSpeedLimit_2007IncomingLinks(view);
		case RedLeftSignalEditPart.VISUAL_ID:
			return getRedLeftSignal_2008IncomingLinks(view);
		case RedRightSignalEditPart.VISUAL_ID:
			return getRedRightSignal_2009IncomingLinks(view);
		case LeftStopAndGoEditPart.VISUAL_ID:
			return getLeftStopAndGo_2010IncomingLinks(view);
		case RightStopAndGoEditPart.VISUAL_ID:
			return getRightStopAndGo_2011IncomingLinks(view);
		case StationEditPart.VISUAL_ID:
			return getStation_2012IncomingLinks(view);
		case BridgeEditPart.VISUAL_ID:
			return getBridge_2013IncomingLinks(view);
		case SegmentEditPart.VISUAL_ID:
			return getSegment_4001IncomingLinks(view);
		case WireEditPart.VISUAL_ID:
			return getWire_4002IncomingLinks(view);
		case NodeWireEditPart.VISUAL_ID:
			return getNodeWire_4004IncomingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getOutgoingLinks(View view) {
		switch (SafecapVisualIDRegistry.getVisualID(view)) {
		case SubSchemaNodeEditPart.VISUAL_ID:
			return getSubSchemaNode_2015OutgoingLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2003OutgoingLinks(view);
		case LeftSignalEditPart.VISUAL_ID:
			return getLeftSignal_2004OutgoingLinks(view);
		case RightSignalEditPart.VISUAL_ID:
			return getRightSignal_2005OutgoingLinks(view);
		case LeftSpeedLimitEditPart.VISUAL_ID:
			return getLeftSpeedLimit_2006OutgoingLinks(view);
		case RightSpeedLimitEditPart.VISUAL_ID:
			return getRightSpeedLimit_2007OutgoingLinks(view);
		case RedLeftSignalEditPart.VISUAL_ID:
			return getRedLeftSignal_2008OutgoingLinks(view);
		case RedRightSignalEditPart.VISUAL_ID:
			return getRedRightSignal_2009OutgoingLinks(view);
		case LeftStopAndGoEditPart.VISUAL_ID:
			return getLeftStopAndGo_2010OutgoingLinks(view);
		case RightStopAndGoEditPart.VISUAL_ID:
			return getRightStopAndGo_2011OutgoingLinks(view);
		case StationEditPart.VISUAL_ID:
			return getStation_2012OutgoingLinks(view);
		case BridgeEditPart.VISUAL_ID:
			return getBridge_2013OutgoingLinks(view);
		case SegmentEditPart.VISUAL_ID:
			return getSegment_4001OutgoingLinks(view);
		case WireEditPart.VISUAL_ID:
			return getWire_4002OutgoingLinks(view);
		case NodeWireEditPart.VISUAL_ID:
			return getNodeWire_4004OutgoingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getProject_1000ContainedLinks(View view) {
		final Project modelElement = (Project) view.getElement();
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Segment_4001(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Wire_4002(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_NodeWire_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getNode_2003ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getLeftSignal_2004ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRightSignal_2005ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getLeftSpeedLimit_2006ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRightSpeedLimit_2007ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRedLeftSignal_2008ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRedRightSignal_2009ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getLeftStopAndGo_2010ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRightStopAndGo_2011ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getStation_2012ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getBridge_2013ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getSubSchemaNode_2015ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getSegment_4001ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getWire_4002ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getNodeWire_4004ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getNode_2003IncomingLinks(View view) {
		final Node modelElement = (Node) view.getElement();
		final Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Segment_4001(modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_NodeWire_4004(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getLeftSignal_2004IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRightSignal_2005IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getLeftSpeedLimit_2006IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRightSpeedLimit_2007IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRedLeftSignal_2008IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRedRightSignal_2009IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getLeftStopAndGo_2010IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRightStopAndGo_2011IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getStation_2012IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getBridge_2013IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getSubSchemaNode_2015IncomingLinks(View view) {
		final SubSchemaNode modelElement = (SubSchemaNode) view.getElement();
		final Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Segment_4001(modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_NodeWire_4004(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getSegment_4001IncomingLinks(View view) {
		final Segment modelElement = (Segment) view.getElement();
		final Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Wire_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getWire_4002IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getNodeWire_4004IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getNode_2003OutgoingLinks(View view) {
		final Node modelElement = (Node) view.getElement();
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Segment_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getLeftSignal_2004OutgoingLinks(View view) {
		final LeftSignal modelElement = (LeftSignal) view.getElement();
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Wire_4002(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_NodeWire_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRightSignal_2005OutgoingLinks(View view) {
		final RightSignal modelElement = (RightSignal) view.getElement();
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Wire_4002(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_NodeWire_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getLeftSpeedLimit_2006OutgoingLinks(View view) {
		final LeftSpeedLimit modelElement = (LeftSpeedLimit) view.getElement();
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Wire_4002(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_NodeWire_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRightSpeedLimit_2007OutgoingLinks(View view) {
		final RightSpeedLimit modelElement = (RightSpeedLimit) view.getElement();
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Wire_4002(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_NodeWire_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRedLeftSignal_2008OutgoingLinks(View view) {
		final RedLeftSignal modelElement = (RedLeftSignal) view.getElement();
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Wire_4002(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_NodeWire_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRedRightSignal_2009OutgoingLinks(View view) {
		final RedRightSignal modelElement = (RedRightSignal) view.getElement();
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Wire_4002(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_NodeWire_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getLeftStopAndGo_2010OutgoingLinks(View view) {
		final LeftStopAndGo modelElement = (LeftStopAndGo) view.getElement();
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Wire_4002(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_NodeWire_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getRightStopAndGo_2011OutgoingLinks(View view) {
		final RightStopAndGo modelElement = (RightStopAndGo) view.getElement();
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Wire_4002(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_NodeWire_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getStation_2012OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getBridge_2013OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getSubSchemaNode_2015OutgoingLinks(View view) {
		final SubSchemaNode modelElement = (SubSchemaNode) view.getElement();
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Segment_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getSegment_4001OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getWire_4002OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SafecapLinkDescriptor> getNodeWire_4004OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	private static Collection<SafecapLinkDescriptor> getContainedTypeModelFacetLinks_Segment_4001(Schema container) {
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		for (final Iterator<?> links = container.getSegments().iterator(); links.hasNext();) {
			final EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Segment) {
				continue;
			}
			final Segment link = (Segment) linkObject;
			if (SegmentEditPart.VISUAL_ID != SafecapVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			final Node dst = link.getTo();
			final Node src = link.getFrom();
			result.add(new SafecapLinkDescriptor(src, dst, link, SafecapElementTypes.Segment_4001, SegmentEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<SafecapLinkDescriptor> getContainedTypeModelFacetLinks_Wire_4002(Trackside container) {
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		for (final Iterator<?> links = container.getWires().iterator(); links.hasNext();) {
			final EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Wire) {
				continue;
			}
			final Wire link = (Wire) linkObject;
			if (WireEditPart.VISUAL_ID != SafecapVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			final Segment dst = link.getTo();
			final Equipment src = link.getFrom();
			result.add(new SafecapLinkDescriptor(src, dst, link, SafecapElementTypes.Wire_4002, WireEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<SafecapLinkDescriptor> getContainedTypeModelFacetLinks_NodeWire_4004(Trackside container) {
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		for (final Iterator<?> links = container.getNodewires().iterator(); links.hasNext();) {
			final EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof NodeWire) {
				continue;
			}
			final NodeWire link = (NodeWire) linkObject;
			if (NodeWireEditPart.VISUAL_ID != SafecapVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			final Node dst = link.getTo();
			final Equipment src = link.getFrom();
			result.add(new SafecapLinkDescriptor(src, dst, link, SafecapElementTypes.NodeWire_4004, NodeWireEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<SafecapLinkDescriptor> getIncomingTypeModelFacetLinks_Segment_4001(Node target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		final Collection<EStructuralFeature.Setting> settings = crossReferences.get(target);
		for (final EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != SchemaPackage.eINSTANCE.getSegment_To()
					|| false == setting.getEObject() instanceof Segment) {
				continue;
			}
			final Segment link = (Segment) setting.getEObject();
			if (SegmentEditPart.VISUAL_ID != SafecapVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			final Node src = link.getFrom();
			result.add(new SafecapLinkDescriptor(src, target, link, SafecapElementTypes.Segment_4001, SegmentEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<SafecapLinkDescriptor> getIncomingTypeModelFacetLinks_Wire_4002(Segment target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		final Collection<EStructuralFeature.Setting> settings = crossReferences.get(target);
		for (final EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != TracksidePackage.eINSTANCE.getWire_To()
					|| false == setting.getEObject() instanceof Wire) {
				continue;
			}
			final Wire link = (Wire) setting.getEObject();
			if (WireEditPart.VISUAL_ID != SafecapVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			final Equipment src = link.getFrom();
			result.add(new SafecapLinkDescriptor(src, target, link, SafecapElementTypes.Wire_4002, WireEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<SafecapLinkDescriptor> getIncomingTypeModelFacetLinks_NodeWire_4004(Node target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		final Collection<EStructuralFeature.Setting> settings = crossReferences.get(target);
		for (final EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != TracksidePackage.eINSTANCE.getNodeWire_To()
					|| false == setting.getEObject() instanceof NodeWire) {
				continue;
			}
			final NodeWire link = (NodeWire) setting.getEObject();
			if (NodeWireEditPart.VISUAL_ID != SafecapVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			final Equipment src = link.getFrom();
			result.add(new SafecapLinkDescriptor(src, target, link, SafecapElementTypes.NodeWire_4004, NodeWireEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<SafecapLinkDescriptor> getOutgoingTypeModelFacetLinks_Segment_4001(Node source) {
		Schema container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Schema) {
				container = (Schema) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		for (final Iterator<?> links = container.getSegments().iterator(); links.hasNext();) {
			final EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Segment) {
				continue;
			}
			final Segment link = (Segment) linkObject;
			if (SegmentEditPart.VISUAL_ID != SafecapVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			final Node dst = link.getTo();
			final Node src = link.getFrom();
			if (src != source) {
				continue;
			}
			result.add(new SafecapLinkDescriptor(src, dst, link, SafecapElementTypes.Segment_4001, SegmentEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<SafecapLinkDescriptor> getOutgoingTypeModelFacetLinks_Wire_4002(Equipment source) {
		Trackside container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Trackside) {
				container = (Trackside) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		for (final Iterator<?> links = container.getWires().iterator(); links.hasNext();) {
			final EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Wire) {
				continue;
			}
			final Wire link = (Wire) linkObject;
			if (WireEditPart.VISUAL_ID != SafecapVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			final Segment dst = link.getTo();
			final Equipment src = link.getFrom();
			if (src != source) {
				continue;
			}
			result.add(new SafecapLinkDescriptor(src, dst, link, SafecapElementTypes.Wire_4002, WireEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<SafecapLinkDescriptor> getOutgoingTypeModelFacetLinks_NodeWire_4004(Equipment source) {
		Trackside container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Trackside) {
				container = (Trackside) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		for (final Iterator<?> links = container.getNodewires().iterator(); links.hasNext();) {
			final EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof NodeWire) {
				continue;
			}
			final NodeWire link = (NodeWire) linkObject;
			if (NodeWireEditPart.VISUAL_ID != SafecapVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			final Node dst = link.getTo();
			final Equipment src = link.getFrom();
			if (src != source) {
				continue;
			}
			result.add(new SafecapLinkDescriptor(src, dst, link, SafecapElementTypes.NodeWire_4004, NodeWireEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static final DiagramUpdater TYPED_INSTANCE = new DiagramUpdater() {
		/**
		 * @generated
		 */
		@Override
		public List<SafecapNodeDescriptor> getSemanticChildren(View view) {
			return SafecapDiagramUpdater.getSemanticChildren(view);
		}

		/**
		 * @generated
		 */
		@Override
		public List<SafecapLinkDescriptor> getContainedLinks(View view) {
			return SafecapDiagramUpdater.getContainedLinks(view);
		}

		/**
		 * @generated
		 */
		@Override
		public List<SafecapLinkDescriptor> getIncomingLinks(View view) {
			return SafecapDiagramUpdater.getIncomingLinks(view);
		}

		/**
		 * @generated
		 */
		@Override
		public List<SafecapLinkDescriptor> getOutgoingLinks(View view) {
			return SafecapDiagramUpdater.getOutgoingLinks(view);
		}
	};

}
