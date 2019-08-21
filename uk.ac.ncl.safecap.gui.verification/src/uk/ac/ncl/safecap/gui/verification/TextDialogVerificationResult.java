package uk.ac.ncl.safecap.gui.verification;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TextDialogVerificationResult extends IVerificationTool.VerificationResult {
	private static class TextDialog extends Dialog {
		private Text textarea;
		private final String text;

		public TextDialog(final Shell parentShell, String text) {
			super(parentShell);
			this.text = text;
		}

		@Override
		protected Control createDialogArea(final Composite parent) {
			final Composite contents = (Composite) super.createDialogArea(parent);

			final GridLayout contentsGridLayout = (GridLayout) contents.getLayout();
			contentsGridLayout.numColumns = 1;

			final GridData contentsGridData = (GridData) contents.getLayoutData();
			contentsGridData.horizontalAlignment = SWT.FILL;
			contentsGridData.verticalAlignment = SWT.FILL;

			textarea = new Text(contents, SWT.V_SCROLL | SWT.H_SCROLL);

			{
				final GridData availableGridData = new GridData();
				availableGridData.widthHint = Display.getCurrent().getBounds().width / 3;
				availableGridData.heightHint = Display.getCurrent().getBounds().height / 2;
				availableGridData.verticalAlignment = SWT.FILL;
				availableGridData.horizontalAlignment = SWT.FILL;
				availableGridData.grabExcessHorizontalSpace = true;
				availableGridData.grabExcessVerticalSpace = true;
				textarea.setLayoutData(availableGridData);
			}

			textarea.setEditable(false);
			textarea.setText(text);
			return contents;
		}

	}

	public String reportContent;

	public TextDialogVerificationResult(int result, String reportContent) {
		super(result, true);
		this.reportContent = reportContent;
		if (reportContent == null) {
			throw new NullPointerException("Null reportContent supplied");
		}
	}

	@Override
	public void showResultDialog(Shell shell) {
		final TextDialog dialog = new TextDialog(null, reportContent);
		dialog.open();
	}
}
