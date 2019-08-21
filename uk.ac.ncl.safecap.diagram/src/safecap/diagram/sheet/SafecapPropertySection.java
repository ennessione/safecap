package safecap.diagram.sheet;

import org.eclipse.gmf.tooling.runtime.sheet.DefaultPropertySection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

import safecap.Project;
import safecap.commentary.OrientableCommentary;
import safecap.model.Line;
import safecap.schema.Node;
import safecap.schema.Segment;
import safecap.trackside.Signal;
import safecap.trackside.SpeedLimit;
import safecap.trackside.StopAndGo;
import safecap.trackside.Wire;
import uk.ac.ncl.safecap.diagram.misc.propertypages.EmptyPropertySource;
import uk.ac.ncl.safecap.diagram.misc.propertypages.LinePropertySource;
import uk.ac.ncl.safecap.diagram.misc.propertypages.NodePropertySource;
import uk.ac.ncl.safecap.diagram.misc.propertypages.OrientablePropertySource;
import uk.ac.ncl.safecap.diagram.misc.propertypages.ProjectPropertySource;
import uk.ac.ncl.safecap.diagram.misc.propertypages.SegmentPropertySource;
import uk.ac.ncl.safecap.diagram.misc.propertypages.SignalPropertySource;
import uk.ac.ncl.safecap.diagram.misc.propertypages.SpeedLimitPropertySource;
import uk.ac.ncl.safecap.diagram.misc.propertypages.StopAndGoPropertySource;
import uk.ac.ncl.safecap.diagram.misc.propertypages.WirePropertySource;

/**
 * @generated
 */
public class SafecapPropertySection extends DefaultPropertySection implements IPropertySourceProvider {
	private IWorkbenchPart part;

	/**
	 * @generated NOT
	 */
	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		this.part = part;
		super.setInput(part, selection);
		super.refresh();
	}

	/**
	 * @generated NOT
	 */
	@Override
	public IPropertySource getPropertySource(Object object) {
		if (object instanceof Project) {
			return new ProjectPropertySource((Project) object, part, this);
		} else if (object instanceof Wire) {
			return new WirePropertySource((Wire) object);
		} else if (object instanceof Signal) {
			return new SignalPropertySource((Signal) object);
		} else if (object instanceof SpeedLimit) {
			return new SpeedLimitPropertySource((SpeedLimit) object);
		} else if (object instanceof StopAndGo) {
			return new StopAndGoPropertySource((StopAndGo) object);
		} else if (object instanceof Line) {
			return new LinePropertySource((Line) object);
		} else if (object instanceof Segment) {
			return new SegmentPropertySource((Segment) object);
		} else if (object instanceof Node) {
			return new NodePropertySource((Node) object);
		} else if (object instanceof OrientableCommentary) {
			return new OrientablePropertySource((OrientableCommentary) object);
		} else {
			return new EmptyPropertySource(object);
		}
	}

	/**
	 * Modify/unwrap selection.
	 * 
	 * @generated
	 */
	@Override
	protected Object transformSelection(Object selected) {
		selected = /* super. */transformSelectionToDomain(selected);
		return selected;
	}

}
