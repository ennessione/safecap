package safecap.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.structure.DiagramStructure;

import safecap.Project;
import safecap.SafecapPackage;
import safecap.commentary.CommentaryPackage;
import safecap.diagram.edit.parts.BridgeEditPart;
import safecap.diagram.edit.parts.LeftSignalEditPart;
import safecap.diagram.edit.parts.LeftSignalLabelEditPart;
import safecap.diagram.edit.parts.LeftSpeedLimitEditPart;
import safecap.diagram.edit.parts.LeftStopAndGoEditPart;
import safecap.diagram.edit.parts.NodeEditPart;
import safecap.diagram.edit.parts.NodeLabelEditPart;
import safecap.diagram.edit.parts.NodeWireEditPart;
import safecap.diagram.edit.parts.ProjectEditPart;
import safecap.diagram.edit.parts.RedLeftSignalEditPart;
import safecap.diagram.edit.parts.RedRightSignalEditPart;
import safecap.diagram.edit.parts.RightSignalEditPart;
import safecap.diagram.edit.parts.RightSignalLabelEditPart;
import safecap.diagram.edit.parts.RightSpeedLimitEditPart;
import safecap.diagram.edit.parts.RightStopAndGoEditPart;
import safecap.diagram.edit.parts.SegmentEditPart;
import safecap.diagram.edit.parts.SegmentLabelEditPart;
import safecap.diagram.edit.parts.StationEditPart;
import safecap.diagram.edit.parts.SubSchemaNodeEditPart;
import safecap.diagram.edit.parts.SubSchemaNodeLabelEditPart;
import safecap.diagram.edit.parts.WireEditPart;
import safecap.schema.SchemaPackage;
import safecap.trackside.TracksidePackage;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented by
 * a domain model object.
 * 
 * @generated
 */
public class SafecapVisualIDRegistry {

	/**
	 * @generated
	 */
	private static final String DEBUG_KEY = "uk.ac.ncl.safecap.diagram/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static int getVisualID(View view) {
		if (view instanceof Diagram) {
			if (ProjectEditPart.MODEL_ID.equals(view.getType())) {
				return ProjectEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		return safecap.diagram.part.SafecapVisualIDRegistry.getVisualID(view.getType());
	}

	/**
	 * @generated
	 */
	public static String getModelID(View view) {
		final View diagram = view.getDiagram();
		while (view != diagram) {
			final EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
			if (annotation != null) {
				return annotation.getDetails().get("modelID"); //$NON-NLS-1$
			}
			view = (View) view.eContainer();
		}
		return diagram != null ? diagram.getType() : null;
	}

	/**
	 * @generated
	 */
	public static int getVisualID(String type) {
		try {
			return Integer.parseInt(type);
		} catch (final NumberFormatException e) {
			if (Boolean.TRUE.toString().equalsIgnoreCase(Platform.getDebugOption(DEBUG_KEY))) {
				SafecapDiagramEditorPlugin.getInstance().logError("Unable to parse view type as a visualID number: " + type);
			}
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static String getType(int visualID) {
		return Integer.toString(visualID);
	}

	/**
	 * @generated
	 */
	public static int getDiagramVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (SafecapPackage.eINSTANCE.getProject().isSuperTypeOf(domainElement.eClass()) && isDiagram((Project) domainElement)) {
			return ProjectEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static int getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		final String containerModelID = safecap.diagram.part.SafecapVisualIDRegistry.getModelID(containerView);
		if (!ProjectEditPart.MODEL_ID.equals(containerModelID)) {
			return -1;
		}
		int containerVisualID;
		if (ProjectEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = safecap.diagram.part.SafecapVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = ProjectEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		switch (containerVisualID) {
		case ProjectEditPart.VISUAL_ID:
			if (SchemaPackage.eINSTANCE.getSubSchemaNode().isSuperTypeOf(domainElement.eClass())) {
				return SubSchemaNodeEditPart.VISUAL_ID;
			}
			if (SchemaPackage.eINSTANCE.getNode().isSuperTypeOf(domainElement.eClass())) {
				return NodeEditPart.VISUAL_ID;
			}
			if (TracksidePackage.eINSTANCE.getLeftSignal().isSuperTypeOf(domainElement.eClass())) {
				return LeftSignalEditPart.VISUAL_ID;
			}
			if (TracksidePackage.eINSTANCE.getRightSignal().isSuperTypeOf(domainElement.eClass())) {
				return RightSignalEditPart.VISUAL_ID;
			}
			if (TracksidePackage.eINSTANCE.getLeftSpeedLimit().isSuperTypeOf(domainElement.eClass())) {
				return LeftSpeedLimitEditPart.VISUAL_ID;
			}
			if (TracksidePackage.eINSTANCE.getRightSpeedLimit().isSuperTypeOf(domainElement.eClass())) {
				return RightSpeedLimitEditPart.VISUAL_ID;
			}
			if (TracksidePackage.eINSTANCE.getRedLeftSignal().isSuperTypeOf(domainElement.eClass())) {
				return RedLeftSignalEditPart.VISUAL_ID;
			}
			if (TracksidePackage.eINSTANCE.getRedRightSignal().isSuperTypeOf(domainElement.eClass())) {
				return RedRightSignalEditPart.VISUAL_ID;
			}
			if (TracksidePackage.eINSTANCE.getLeftStopAndGo().isSuperTypeOf(domainElement.eClass())) {
				return LeftStopAndGoEditPart.VISUAL_ID;
			}
			if (TracksidePackage.eINSTANCE.getRightStopAndGo().isSuperTypeOf(domainElement.eClass())) {
				return RightStopAndGoEditPart.VISUAL_ID;
			}
			if (CommentaryPackage.eINSTANCE.getStation().isSuperTypeOf(domainElement.eClass())) {
				return StationEditPart.VISUAL_ID;
			}
			if (CommentaryPackage.eINSTANCE.getBridge().isSuperTypeOf(domainElement.eClass())) {
				return BridgeEditPart.VISUAL_ID;
			}
			break;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static boolean canCreateNode(View containerView, int nodeVisualID) {
		final String containerModelID = safecap.diagram.part.SafecapVisualIDRegistry.getModelID(containerView);
		if (!ProjectEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		int containerVisualID;
		if (ProjectEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = safecap.diagram.part.SafecapVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = ProjectEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		switch (containerVisualID) {
		case ProjectEditPart.VISUAL_ID:
			if (SubSchemaNodeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (NodeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (LeftSignalEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (RightSignalEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (LeftSpeedLimitEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (RightSpeedLimitEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (RedLeftSignalEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (RedRightSignalEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (LeftStopAndGoEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (RightStopAndGoEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (StationEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (BridgeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case SubSchemaNodeEditPart.VISUAL_ID:
			if (SubSchemaNodeLabelEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case NodeEditPart.VISUAL_ID:
			if (NodeLabelEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case LeftSignalEditPart.VISUAL_ID:
			if (LeftSignalLabelEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case RightSignalEditPart.VISUAL_ID:
			if (RightSignalLabelEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case SegmentEditPart.VISUAL_ID:
			if (SegmentLabelEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static int getLinkWithClassVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (SchemaPackage.eINSTANCE.getSegment().isSuperTypeOf(domainElement.eClass())) {
			return SegmentEditPart.VISUAL_ID;
		}
		if (TracksidePackage.eINSTANCE.getWire().isSuperTypeOf(domainElement.eClass())) {
			return WireEditPart.VISUAL_ID;
		}
		if (TracksidePackage.eINSTANCE.getNodeWire().isSuperTypeOf(domainElement.eClass())) {
			return NodeWireEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 * 
	 * @generated
	 */
	private static boolean isDiagram(Project element) {
		return true;
	}

	/**
	 * @generated
	 */
	public static boolean checkNodeVisualID(View containerView, EObject domainElement, int candidate) {
		if (candidate == -1) {
			// unrecognized id is always bad
			return false;
		}
		final int basic = getNodeVisualID(containerView, domainElement);
		return basic == candidate;
	}

	/**
	 * @generated
	 */
	public static boolean isCompartmentVisualID(int visualID) {
		return false;
	}

	/**
	 * @generated
	 */
	public static boolean isSemanticLeafVisualID(int visualID) {
		switch (visualID) {
		case ProjectEditPart.VISUAL_ID:
			return false;
		case NodeEditPart.VISUAL_ID:
		case LeftSignalEditPart.VISUAL_ID:
		case RightSignalEditPart.VISUAL_ID:
		case LeftSpeedLimitEditPart.VISUAL_ID:
		case RightSpeedLimitEditPart.VISUAL_ID:
		case RedLeftSignalEditPart.VISUAL_ID:
		case RedRightSignalEditPart.VISUAL_ID:
		case LeftStopAndGoEditPart.VISUAL_ID:
		case RightStopAndGoEditPart.VISUAL_ID:
		case StationEditPart.VISUAL_ID:
		case BridgeEditPart.VISUAL_ID:
		case SubSchemaNodeEditPart.VISUAL_ID:
			return true;
		default:
			break;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static final DiagramStructure TYPED_INSTANCE = new DiagramStructure() {
		/**
		 * @generated
		 */
		@Override
		public int getVisualID(View view) {
			return safecap.diagram.part.SafecapVisualIDRegistry.getVisualID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public String getModelID(View view) {
			return safecap.diagram.part.SafecapVisualIDRegistry.getModelID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public int getNodeVisualID(View containerView, EObject domainElement) {
			return safecap.diagram.part.SafecapVisualIDRegistry.getNodeVisualID(containerView, domainElement);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean checkNodeVisualID(View containerView, EObject domainElement, int candidate) {
			return safecap.diagram.part.SafecapVisualIDRegistry.checkNodeVisualID(containerView, domainElement, candidate);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isCompartmentVisualID(int visualID) {
			return safecap.diagram.part.SafecapVisualIDRegistry.isCompartmentVisualID(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isSemanticLeafVisualID(int visualID) {
			return safecap.diagram.part.SafecapVisualIDRegistry.isSemanticLeafVisualID(visualID);
		}
	};

}
