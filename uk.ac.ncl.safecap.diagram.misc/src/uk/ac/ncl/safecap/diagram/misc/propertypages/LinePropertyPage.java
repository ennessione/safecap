package uk.ac.ncl.safecap.diagram.misc.propertypages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;

public class LinePropertyPage extends PropertyPage implements IWorkbenchPropertyPage {

	public LinePropertyPage() {
		System.out.println("test");
	}

	@Override
	protected Control createContents(Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText("test");
		return parent;
	}

}
