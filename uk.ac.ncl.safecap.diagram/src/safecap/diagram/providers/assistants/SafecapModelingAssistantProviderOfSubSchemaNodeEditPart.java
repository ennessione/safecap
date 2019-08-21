package safecap.diagram.providers.assistants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import safecap.diagram.edit.parts.NodeEditPart;
import safecap.diagram.edit.parts.SubSchemaNodeEditPart;
import safecap.diagram.providers.SafecapElementTypes;
import safecap.diagram.providers.SafecapModelingAssistantProvider;

/**
 * @generated
 */
public class SafecapModelingAssistantProviderOfSubSchemaNodeEditPart extends SafecapModelingAssistantProvider {

	/**
	 * @generated
	 */
	@Override
	public List<IElementType> getRelTypesOnSource(IAdaptable source) {
		final IGraphicalEditPart sourceEditPart = source.getAdapter(IGraphicalEditPart.class);
		return doGetRelTypesOnSource((SubSchemaNodeEditPart) sourceEditPart);
	}

	/**
	 * @generated
	 */
	public List<IElementType> doGetRelTypesOnSource(SubSchemaNodeEditPart source) {
		final List<IElementType> types = new ArrayList<>(1);
		types.add(SafecapElementTypes.Segment_4001);
		return types;
	}

	/**
	 * @generated
	 */
	@Override
	public List<IElementType> getRelTypesOnSourceAndTarget(IAdaptable source, IAdaptable target) {
		final IGraphicalEditPart sourceEditPart = source.getAdapter(IGraphicalEditPart.class);
		final IGraphicalEditPart targetEditPart = target.getAdapter(IGraphicalEditPart.class);
		return doGetRelTypesOnSourceAndTarget((SubSchemaNodeEditPart) sourceEditPart, targetEditPart);
	}

	/**
	 * @generated
	 */
	public List<IElementType> doGetRelTypesOnSourceAndTarget(SubSchemaNodeEditPart source, IGraphicalEditPart targetEditPart) {
		final List<IElementType> types = new LinkedList<>();
		if (targetEditPart instanceof SubSchemaNodeEditPart) {
			types.add(SafecapElementTypes.Segment_4001);
		}
		if (targetEditPart instanceof NodeEditPart) {
			types.add(SafecapElementTypes.Segment_4001);
		}
		return types;
	}

	/**
	 * @generated
	 */
	@Override
	public List<IElementType> getTypesForTarget(IAdaptable source, IElementType relationshipType) {
		final IGraphicalEditPart sourceEditPart = source.getAdapter(IGraphicalEditPart.class);
		return doGetTypesForTarget((SubSchemaNodeEditPart) sourceEditPart, relationshipType);
	}

	/**
	 * @generated
	 */
	public List<IElementType> doGetTypesForTarget(SubSchemaNodeEditPart source, IElementType relationshipType) {
		final List<IElementType> types = new ArrayList<>();
		if (relationshipType == SafecapElementTypes.Segment_4001) {
			types.add(SafecapElementTypes.SubSchemaNode_2015);
			types.add(SafecapElementTypes.Node_2003);
		}
		return types;
	}

	/**
	 * @generated
	 */
	@Override
	public List<IElementType> getRelTypesOnTarget(IAdaptable target) {
		final IGraphicalEditPart targetEditPart = target.getAdapter(IGraphicalEditPart.class);
		return doGetRelTypesOnTarget((SubSchemaNodeEditPart) targetEditPart);
	}

	/**
	 * @generated
	 */
	public List<IElementType> doGetRelTypesOnTarget(SubSchemaNodeEditPart target) {
		final List<IElementType> types = new ArrayList<>(2);
		types.add(SafecapElementTypes.Segment_4001);
		types.add(SafecapElementTypes.NodeWire_4004);
		return types;
	}

	/**
	 * @generated
	 */
	@Override
	public List<IElementType> getTypesForSource(IAdaptable target, IElementType relationshipType) {
		final IGraphicalEditPart targetEditPart = target.getAdapter(IGraphicalEditPart.class);
		return doGetTypesForSource((SubSchemaNodeEditPart) targetEditPart, relationshipType);
	}

	/**
	 * @generated
	 */
	public List<IElementType> doGetTypesForSource(SubSchemaNodeEditPart target, IElementType relationshipType) {
		final List<IElementType> types = new ArrayList<>();
		if (relationshipType == SafecapElementTypes.Segment_4001) {
			types.add(SafecapElementTypes.SubSchemaNode_2015);
			types.add(SafecapElementTypes.Node_2003);
		} else if (relationshipType == SafecapElementTypes.NodeWire_4004) {
			types.add(SafecapElementTypes.LeftSignal_2004);
			types.add(SafecapElementTypes.RightSignal_2005);
			types.add(SafecapElementTypes.LeftSpeedLimit_2006);
			types.add(SafecapElementTypes.RightSpeedLimit_2007);
			types.add(SafecapElementTypes.RedLeftSignal_2008);
			types.add(SafecapElementTypes.RedRightSignal_2009);
			types.add(SafecapElementTypes.LeftStopAndGo_2010);
			types.add(SafecapElementTypes.RightStopAndGo_2011);
		}
		return types;
	}

}
