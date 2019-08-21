package safecap.diagram.providers;

import org.eclipse.gef.RootEditPart;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.CreateRootEditPartOperation;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.tooling.runtime.providers.DefaultEditPartProvider;

import safecap.diagram.edit.parts.ProjectEditPart;
import safecap.diagram.edit.parts.SafecapEditPartFactory;
import safecap.diagram.part.SafecapVisualIDRegistry;

/**
 * @generated
 */
public class SafecapEditPartProvider extends DefaultEditPartProvider {

	/**
	 * @generated NOT
	 */
	@Override
	public boolean provides(IOperation operation) {
		if (operation instanceof CreateRootEditPartOperation) {
			return true;
		} else {
			return super.provides(operation);
		}
	}

	/**
	 * @generated NOT
	 */
	@Override
	public RootEditPart createRootEditPart(Diagram diagram) {
		return new MyDiagramRootEditPart(diagram.getMeasurementUnit());
	}

	/**
	 * @generated
	 */
	public SafecapEditPartProvider() {
		super(new SafecapEditPartFactory(), SafecapVisualIDRegistry.TYPED_INSTANCE, ProjectEditPart.MODEL_ID);
	}
}
