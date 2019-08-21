package safecap.diagram.providers;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.tooling.runtime.providers.DiagramElementTypeImages;
import org.eclipse.gmf.tooling.runtime.providers.DiagramElementTypes;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import safecap.SafecapPackage;
import safecap.commentary.CommentaryPackage;
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
import safecap.diagram.part.SafecapDiagramEditorPlugin;
import safecap.schema.SchemaPackage;
import safecap.trackside.TracksidePackage;

/**
 * @generated
 */
public class SafecapElementTypes {

	/**
	 * @generated
	 */
	private SafecapElementTypes() {
	}

	/**
	 * @generated
	 */
	private static Map<IElementType, ENamedElement> elements;

	/**
	 * @generated
	 */
	private static DiagramElementTypeImages elementTypeImages = new DiagramElementTypeImages(
			SafecapDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory());

	/**
	 * @generated
	 */
	private static Set<IElementType> KNOWN_ELEMENT_TYPES;

	/**
	 * @generated
	 */
	public static final IElementType Project_1000 = getElementType("uk.ac.ncl.safecap.diagram.Project_1000"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Node_2003 = getElementType("uk.ac.ncl.safecap.diagram.Node_2003"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType LeftSignal_2004 = getElementType("uk.ac.ncl.safecap.diagram.LeftSignal_2004"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType RightSignal_2005 = getElementType("uk.ac.ncl.safecap.diagram.RightSignal_2005"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType LeftSpeedLimit_2006 = getElementType("uk.ac.ncl.safecap.diagram.LeftSpeedLimit_2006"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType RightSpeedLimit_2007 = getElementType("uk.ac.ncl.safecap.diagram.RightSpeedLimit_2007"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType RedLeftSignal_2008 = getElementType("uk.ac.ncl.safecap.diagram.RedLeftSignal_2008"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType RedRightSignal_2009 = getElementType("uk.ac.ncl.safecap.diagram.RedRightSignal_2009"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType LeftStopAndGo_2010 = getElementType("uk.ac.ncl.safecap.diagram.LeftStopAndGo_2010"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType RightStopAndGo_2011 = getElementType("uk.ac.ncl.safecap.diagram.RightStopAndGo_2011"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Station_2012 = getElementType("uk.ac.ncl.safecap.diagram.Station_2012"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Bridge_2013 = getElementType("uk.ac.ncl.safecap.diagram.Bridge_2013"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType SubSchemaNode_2015 = getElementType("uk.ac.ncl.safecap.diagram.SubSchemaNode_2015"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Segment_4001 = getElementType("uk.ac.ncl.safecap.diagram.Segment_4001"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Wire_4002 = getElementType("uk.ac.ncl.safecap.diagram.Wire_4002"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType NodeWire_4004 = getElementType("uk.ac.ncl.safecap.diagram.NodeWire_4004"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(ENamedElement element) {
		return elementTypeImages.getImageDescriptor(element);
	}

	/**
	 * @generated
	 */
	public static Image getImage(ENamedElement element) {
		return elementTypeImages.getImage(element);
	}

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(IAdaptable hint) {
		return getImageDescriptor(getElement(hint));
	}

	/**
	 * @generated
	 */
	public static Image getImage(IAdaptable hint) {
		return getImage(getElement(hint));
	}

	/**
	 * Returns 'type' of the ecore object associated with the hint.
	 * 
	 * @generated
	 */
	public static ENamedElement getElement(IAdaptable hint) {
		final Object type = hint.getAdapter(IElementType.class);
		if (elements == null) {
			elements = new IdentityHashMap<>();

			elements.put(Project_1000, SafecapPackage.eINSTANCE.getProject());

			elements.put(SubSchemaNode_2015, SchemaPackage.eINSTANCE.getSubSchemaNode());

			elements.put(Node_2003, SchemaPackage.eINSTANCE.getNode());

			elements.put(LeftSignal_2004, TracksidePackage.eINSTANCE.getLeftSignal());

			elements.put(RightSignal_2005, TracksidePackage.eINSTANCE.getRightSignal());

			elements.put(LeftSpeedLimit_2006, TracksidePackage.eINSTANCE.getLeftSpeedLimit());

			elements.put(RightSpeedLimit_2007, TracksidePackage.eINSTANCE.getRightSpeedLimit());

			elements.put(RedLeftSignal_2008, TracksidePackage.eINSTANCE.getRedLeftSignal());

			elements.put(RedRightSignal_2009, TracksidePackage.eINSTANCE.getRedRightSignal());

			elements.put(LeftStopAndGo_2010, TracksidePackage.eINSTANCE.getLeftStopAndGo());

			elements.put(RightStopAndGo_2011, TracksidePackage.eINSTANCE.getRightStopAndGo());

			elements.put(Station_2012, CommentaryPackage.eINSTANCE.getStation());

			elements.put(Bridge_2013, CommentaryPackage.eINSTANCE.getBridge());

			elements.put(Segment_4001, SchemaPackage.eINSTANCE.getSegment());

			elements.put(Wire_4002, TracksidePackage.eINSTANCE.getWire());

			elements.put(NodeWire_4004, TracksidePackage.eINSTANCE.getNodeWire());
		}
		return elements.get(type);
	}

	/**
	 * @generated
	 */
	private static IElementType getElementType(String id) {
		return ElementTypeRegistry.getInstance().getType(id);
	}

	/**
	 * @generated
	 */
	public static boolean isKnownElementType(IElementType elementType) {
		if (KNOWN_ELEMENT_TYPES == null) {
			KNOWN_ELEMENT_TYPES = new HashSet<>();
			KNOWN_ELEMENT_TYPES.add(Project_1000);
			KNOWN_ELEMENT_TYPES.add(SubSchemaNode_2015);
			KNOWN_ELEMENT_TYPES.add(Node_2003);
			KNOWN_ELEMENT_TYPES.add(LeftSignal_2004);
			KNOWN_ELEMENT_TYPES.add(RightSignal_2005);
			KNOWN_ELEMENT_TYPES.add(LeftSpeedLimit_2006);
			KNOWN_ELEMENT_TYPES.add(RightSpeedLimit_2007);
			KNOWN_ELEMENT_TYPES.add(RedLeftSignal_2008);
			KNOWN_ELEMENT_TYPES.add(RedRightSignal_2009);
			KNOWN_ELEMENT_TYPES.add(LeftStopAndGo_2010);
			KNOWN_ELEMENT_TYPES.add(RightStopAndGo_2011);
			KNOWN_ELEMENT_TYPES.add(Station_2012);
			KNOWN_ELEMENT_TYPES.add(Bridge_2013);
			KNOWN_ELEMENT_TYPES.add(Segment_4001);
			KNOWN_ELEMENT_TYPES.add(Wire_4002);
			KNOWN_ELEMENT_TYPES.add(NodeWire_4004);
		}
		return KNOWN_ELEMENT_TYPES.contains(elementType);
	}

	/**
	 * @generated
	 */
	public static IElementType getElementType(int visualID) {
		switch (visualID) {
		case ProjectEditPart.VISUAL_ID:
			return Project_1000;
		case SubSchemaNodeEditPart.VISUAL_ID:
			return SubSchemaNode_2015;
		case NodeEditPart.VISUAL_ID:
			return Node_2003;
		case LeftSignalEditPart.VISUAL_ID:
			return LeftSignal_2004;
		case RightSignalEditPart.VISUAL_ID:
			return RightSignal_2005;
		case LeftSpeedLimitEditPart.VISUAL_ID:
			return LeftSpeedLimit_2006;
		case RightSpeedLimitEditPart.VISUAL_ID:
			return RightSpeedLimit_2007;
		case RedLeftSignalEditPart.VISUAL_ID:
			return RedLeftSignal_2008;
		case RedRightSignalEditPart.VISUAL_ID:
			return RedRightSignal_2009;
		case LeftStopAndGoEditPart.VISUAL_ID:
			return LeftStopAndGo_2010;
		case RightStopAndGoEditPart.VISUAL_ID:
			return RightStopAndGo_2011;
		case StationEditPart.VISUAL_ID:
			return Station_2012;
		case BridgeEditPart.VISUAL_ID:
			return Bridge_2013;
		case SegmentEditPart.VISUAL_ID:
			return Segment_4001;
		case WireEditPart.VISUAL_ID:
			return Wire_4002;
		case NodeWireEditPart.VISUAL_ID:
			return NodeWire_4004;
		}
		return null;
	}

	/**
	 * @generated
	 */
	public static final DiagramElementTypes TYPED_INSTANCE = new DiagramElementTypes(elementTypeImages) {

		/**
		 * @generated
		 */
		@Override
		public boolean isKnownElementType(IElementType elementType) {
			return safecap.diagram.providers.SafecapElementTypes.isKnownElementType(elementType);
		}

		/**
		 * @generated
		 */
		@Override
		public IElementType getElementTypeForVisualId(int visualID) {
			return safecap.diagram.providers.SafecapElementTypes.getElementType(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public ENamedElement getDefiningNamedElement(IAdaptable elementTypeAdapter) {
			return safecap.diagram.providers.SafecapElementTypes.getElement(elementTypeAdapter);
		}
	};

}
