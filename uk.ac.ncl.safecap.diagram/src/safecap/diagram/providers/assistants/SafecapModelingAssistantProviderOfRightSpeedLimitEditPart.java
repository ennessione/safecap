package safecap.diagram.providers.assistants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import safecap.diagram.edit.parts.NodeEditPart;
import safecap.diagram.edit.parts.RightSpeedLimitEditPart;
import safecap.diagram.edit.parts.SubSchemaNodeEditPart;
import safecap.diagram.providers.SafecapElementTypes;
import safecap.diagram.providers.SafecapModelingAssistantProvider;

/**
 * @generated
 */
public class SafecapModelingAssistantProviderOfRightSpeedLimitEditPart extends SafecapModelingAssistantProvider {

	/**
	 * @generated
	 */
	@Override
	public List<IElementType> getRelTypesOnSource(IAdaptable source) {
		final IGraphicalEditPart sourceEditPart = source.getAdapter(IGraphicalEditPart.class);
		return doGetRelTypesOnSource((RightSpeedLimitEditPart) sourceEditPart);
	}

	/**
	 * @generated
	 */
	public List<IElementType> doGetRelTypesOnSource(RightSpeedLimitEditPart source) {
		final List<IElementType> types = new ArrayList<>(1);
		types.add(SafecapElementTypes.NodeWire_4004);
		return types;
	}

	/**
	 * @generated
	 */
	@Override
	public List<IElementType> getRelTypesOnSourceAndTarget(IAdaptable source, IAdaptable target) {
		final IGraphicalEditPart sourceEditPart = source.getAdapter(IGraphicalEditPart.class);
		final IGraphicalEditPart targetEditPart = target.getAdapter(IGraphicalEditPart.class);
		return doGetRelTypesOnSourceAndTarget((RightSpeedLimitEditPart) sourceEditPart, targetEditPart);
	}

	/**
	 * @generated
	 */
	public List<IElementType> doGetRelTypesOnSourceAndTarget(RightSpeedLimitEditPart source, IGraphicalEditPart targetEditPart) {
		final List<IElementType> types = new LinkedList<>();
		if (targetEditPart instanceof SubSchemaNodeEditPart) {
			types.add(SafecapElementTypes.NodeWire_4004);
		}
		if (targetEditPart instanceof NodeEditPart) {
			types.add(SafecapElementTypes.NodeWire_4004);
		}
		return types;
	}

	/**
	 * @generated
	 */
	@Override
	public List<IElementType> getTypesForTarget(IAdaptable source, IElementType relationshipType) {
		final IGraphicalEditPart sourceEditPart = source.getAdapter(IGraphicalEditPart.class);
		return doGetTypesForTarget((RightSpeedLimitEditPart) sourceEditPart, relationshipType);
	}

	/**
	 * @generated
	 */
	public List<IElementType> doGetTypesForTarget(RightSpeedLimitEditPart source, IElementType relationshipType) {
		final List<IElementType> types = new ArrayList<>();
		if (relationshipType == SafecapElementTypes.NodeWire_4004) {
			types.add(SafecapElementTypes.SubSchemaNode_2015);
			types.add(SafecapElementTypes.Node_2003);
		}
		return types;
	}

}
