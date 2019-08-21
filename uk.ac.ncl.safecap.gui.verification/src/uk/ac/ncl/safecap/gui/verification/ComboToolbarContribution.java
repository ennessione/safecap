package uk.ac.ncl.safecap.gui.verification;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

public class ComboToolbarContribution extends WorkbenchWindowControlContribution {
	private Combo mReader;

	public ComboToolbarContribution() {

	}

	@Override
	protected Control createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		final GridLayout glContainer = new GridLayout(1, false);
		glContainer.marginTop = -1;
		glContainer.marginHeight = 0;
		glContainer.marginWidth = 0;
		container.setLayout(glContainer);
		final GridData glReader = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		glReader.widthHint = 280;
		mReader = new Combo(container, SWT.BORDER | SWT.READ_ONLY | SWT.DROP_DOWN);
		mReader.setLayoutData(glReader);

		return container;
	}

	@Override
	protected int computeWidth(Control control) {
		return 300;
	}
}