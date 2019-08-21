package safecap.diagram.providers;

import safecap.diagram.part.SafecapDiagramEditorPlugin;

/**
 * @generated
 */
public class ElementInitializers {

	protected ElementInitializers() {
		// use #getInstance to access cached instance
	}

	/**
	 * @generated
	 */
	public static ElementInitializers getInstance() {
		ElementInitializers cached = SafecapDiagramEditorPlugin.getInstance().getElementInitializers();
		if (cached == null) {
			SafecapDiagramEditorPlugin.getInstance().setElementInitializers(cached = new ElementInitializers());
		}
		return cached;
	}
}
