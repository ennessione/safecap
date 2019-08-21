package safecap.diagram.edit.policies;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;

import safecap.diagram.edit.commands.BridgeCreateCommand;
import safecap.diagram.edit.commands.LeftSignalCreateCommand;
import safecap.diagram.edit.commands.LeftSpeedLimitCreateCommand;
import safecap.diagram.edit.commands.LeftStopAndGoCreateCommand;
import safecap.diagram.edit.commands.NodeCreateCommand;
import safecap.diagram.edit.commands.RedLeftSignalCreateCommand;
import safecap.diagram.edit.commands.RedRightSignalCreateCommand;
import safecap.diagram.edit.commands.RightSignalCreateCommand;
import safecap.diagram.edit.commands.RightSpeedLimitCreateCommand;
import safecap.diagram.edit.commands.RightStopAndGoCreateCommand;
import safecap.diagram.edit.commands.StationCreateCommand;
import safecap.diagram.edit.commands.SubSchemaNodeCreateCommand;
import safecap.diagram.providers.SafecapElementTypes;

/**
 * @generated
 */
public class ProjectItemSemanticEditPolicy extends SafecapBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public ProjectItemSemanticEditPolicy() {
		super(SafecapElementTypes.Project_1000);
	}

	/**
	 * @generated
	 */
	@Override
	protected Command getCreateCommand(CreateElementRequest req) {
		if (SafecapElementTypes.SubSchemaNode_2015 == req.getElementType()) {
			return getGEFWrapper(new SubSchemaNodeCreateCommand(req));
		}
		if (SafecapElementTypes.Node_2003 == req.getElementType()) {
			return getGEFWrapper(new NodeCreateCommand(req));
		}
		if (SafecapElementTypes.LeftSignal_2004 == req.getElementType()) {
			return getGEFWrapper(new LeftSignalCreateCommand(req));
		}
		if (SafecapElementTypes.RightSignal_2005 == req.getElementType()) {
			return getGEFWrapper(new RightSignalCreateCommand(req));
		}
		if (SafecapElementTypes.LeftSpeedLimit_2006 == req.getElementType()) {
			return getGEFWrapper(new LeftSpeedLimitCreateCommand(req));
		}
		if (SafecapElementTypes.RightSpeedLimit_2007 == req.getElementType()) {
			return getGEFWrapper(new RightSpeedLimitCreateCommand(req));
		}
		if (SafecapElementTypes.RedLeftSignal_2008 == req.getElementType()) {
			return getGEFWrapper(new RedLeftSignalCreateCommand(req));
		}
		if (SafecapElementTypes.RedRightSignal_2009 == req.getElementType()) {
			return getGEFWrapper(new RedRightSignalCreateCommand(req));
		}
		if (SafecapElementTypes.LeftStopAndGo_2010 == req.getElementType()) {
			return getGEFWrapper(new LeftStopAndGoCreateCommand(req));
		}
		if (SafecapElementTypes.RightStopAndGo_2011 == req.getElementType()) {
			return getGEFWrapper(new RightStopAndGoCreateCommand(req));
		}
		if (SafecapElementTypes.Station_2012 == req.getElementType()) {
			return getGEFWrapper(new StationCreateCommand(req));
		}
		if (SafecapElementTypes.Bridge_2013 == req.getElementType()) {
			return getGEFWrapper(new BridgeCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

	/**
	 * @generated
	 */
	@Override
	protected Command getDuplicateCommand(DuplicateElementsRequest req) {
		final TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		return getGEFWrapper(new DuplicateAnythingCommand(editingDomain, req));
	}

	/**
	 * @generated
	 */
	private static class DuplicateAnythingCommand extends DuplicateEObjectsCommand {

		/**
		 * @generated
		 */
		public DuplicateAnythingCommand(TransactionalEditingDomain editingDomain, DuplicateElementsRequest req) {
			super(editingDomain, req.getLabel(), req.getElementsToBeDuplicated(), req.getAllDuplicatedElementsMap());
		}

	}

}
