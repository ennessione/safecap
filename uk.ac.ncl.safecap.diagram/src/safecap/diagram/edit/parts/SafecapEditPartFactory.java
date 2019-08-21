package safecap.diagram.edit.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.directedit.locator.CellEditorLocatorAccess;

import safecap.diagram.part.SafecapVisualIDRegistry;

/**
 * @generated
 */
public class SafecapEditPartFactory implements EditPartFactory {

	/**
	 * @generated
	 */
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			final View view = (View) model;
			switch (SafecapVisualIDRegistry.getVisualID(view)) {

			case ProjectEditPart.VISUAL_ID:
				return new ProjectEditPart(view);

			case SubSchemaNodeEditPart.VISUAL_ID:
				return new SubSchemaNodeEditPart(view);

			case SubSchemaNodeLabelEditPart.VISUAL_ID:
				return new SubSchemaNodeLabelEditPart(view);

			case NodeEditPart.VISUAL_ID:
				return new NodeEditPart(view);

			case NodeLabelEditPart.VISUAL_ID:
				return new NodeLabelEditPart(view);

			case LeftSignalEditPart.VISUAL_ID:
				return new LeftSignalEditPart(view);

			case LeftSignalLabelEditPart.VISUAL_ID:
				return new LeftSignalLabelEditPart(view);

			case RightSignalEditPart.VISUAL_ID:
				return new RightSignalEditPart(view);

			case RightSignalLabelEditPart.VISUAL_ID:
				return new RightSignalLabelEditPart(view);

			case LeftSpeedLimitEditPart.VISUAL_ID:
				return new LeftSpeedLimitEditPart(view);

			case RightSpeedLimitEditPart.VISUAL_ID:
				return new RightSpeedLimitEditPart(view);

			case RedLeftSignalEditPart.VISUAL_ID:
				return new RedLeftSignalEditPart(view);

			case RedRightSignalEditPart.VISUAL_ID:
				return new RedRightSignalEditPart(view);

			case LeftStopAndGoEditPart.VISUAL_ID:
				return new LeftStopAndGoEditPart(view);

			case RightStopAndGoEditPart.VISUAL_ID:
				return new RightStopAndGoEditPart(view);

			case StationEditPart.VISUAL_ID:
				return new StationEditPart(view);

			case BridgeEditPart.VISUAL_ID:
				return new BridgeEditPart(view);

			case SegmentEditPart.VISUAL_ID:
				return new SegmentEditPart(view);

			case SegmentLabelEditPart.VISUAL_ID:
				return new SegmentLabelEditPart(view);

			case WireEditPart.VISUAL_ID:
				return new WireEditPart(view);

			case NodeWireEditPart.VISUAL_ID:
				return new NodeWireEditPart(view);

			}
		}
		return createUnrecognizedEditPart(context, model);
	}

	/**
	 * @generated
	 */
	private EditPart createUnrecognizedEditPart(EditPart context, Object model) {
		// Handle creation of unrecognized child node EditParts here
		return null;
	}

	/**
	 * @generated
	 */
	public static CellEditorLocator getTextCellEditorLocator(ITextAwareEditPart source) {
		return CellEditorLocatorAccess.INSTANCE.getTextCellEditorLocator(source);
	}
}
