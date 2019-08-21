package uk.ac.ncl.safecap.navigator.property;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
//import org.eclipse.gmf.runtime.diagram.ui.properties.sections.AdvancedPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import safecap.model.Line;

public class TrafficPropertySection extends AbstractPropertySection {

	private LinePropertyComposite _composite;
	private Composite _parent;

	public TrafficPropertySection() {
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		_parent = parent;
		_composite = new LinePropertyComposite(_parent, SWT.NONE);
	}

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structSel = (IStructuredSelection) selection;
			if (structSel.getFirstElement() instanceof Line) {
				final Line line = (Line) structSel.getFirstElement();
				_composite.setInput(line);
			}
		}
	}

	@Override
	public void dispose() {
//        _composite.dispose();
		super.dispose();
	}
}
