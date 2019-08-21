package safecap.diagram.part;

import java.util.Collection;
import java.util.List;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool;

public class NoSelectionUnspecifiedTypeCreationTool extends CreationTool {

	/**
	 * List of element types of which one will be created (of type
	 * <code>IElementType</code>).
	 */
	private final List elementTypes;

	/**
	 * Creates a new instance with a list of possible element types.
	 * 
	 * @param elementTypes List of element types of which one will be created (of
	 *                     type <code>IElementType</code>).
	 */
	public NoSelectionUnspecifiedTypeCreationTool(List elementTypes) {
		super();
		this.elementTypes = elementTypes;
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
		return new CreateUnspecifiedTypeRequest(elementTypes, getPreferencesHint());
	}
}