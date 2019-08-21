package safecap.diagram.part;

import java.util.Collection;
import java.util.List;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.tools.ConnectionCreationTool;

public class NoSelectionUnspecifiedTypeConnectionTool extends ConnectionCreationTool {

	/**
	 * The possible connection types to appear in the popup (of type
	 * <code>IElementType</code>).
	 */
	private final List connectionTypes;

	/**
	 * Creates a new instance with a list of possible connection types.
	 * 
	 * @param connectionTypes The possible connection types to appear in the popup
	 *                        (of type <code>IElementType</code>).
	 */
	public NoSelectionUnspecifiedTypeConnectionTool(List connectionTypes) {
		super();
		this.connectionTypes = connectionTypes;
	}

	@Override
	protected void selectAddedObject(EditPartViewer viewer, Collection objects) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.TargetingTool#createTargetRequest()
	 */
	@Override
	protected Request createTargetRequest() {
		return new CreateUnspecifiedTypeConnectionRequest(connectionTypes, false, getPreferencesHint());
	}

}