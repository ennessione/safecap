package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class TextDialog extends Dialog {
	private StyledText textarea;
	private Label position;
	private final String text;
	private String title;
	private final List<LocatedValue> highlights;

	public TextDialog(final Shell parentShell, String text) {
		super(parentShell);
		this.text = text;
		highlights = new ArrayList<>();
	}

	public TextDialog(final Shell parentShell, String title, String text) {
		super(parentShell);
		this.text = text;
		this.title = title;
		highlights = new ArrayList<>();
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (title != null) {
			shell.setText(title);
		}
	}

	public void addHighlight(LocatedValue location) {
		highlights.add(location);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		final GridLayout layout = (GridLayout) parent.getLayout();
		layout.marginHeight = 0;
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite contents = (Composite) super.createDialogArea(parent);

		final GridLayout contentsGridLayout = (GridLayout) contents.getLayout();
		contentsGridLayout.numColumns = 1;
		contentsGridLayout.marginHeight = 0;
		contentsGridLayout.marginWidth = 0;

		final GridData contentsGridData = (GridData) contents.getLayoutData();
		contentsGridData.horizontalAlignment = SWT.FILL;
		contentsGridData.verticalAlignment = SWT.FILL;
		contentsGridData.verticalIndent = 0;
		contentsGridData.horizontalIndent = 0;

		textarea = new StyledText(contents, SWT.V_SCROLL | SWT.H_SCROLL);
		{
			final GridData availableGridData = new GridData();
			availableGridData.widthHint = Display.getCurrent().getBounds().width / 3;
			availableGridData.heightHint = Display.getCurrent().getBounds().height / 2;
			availableGridData.verticalAlignment = SWT.FILL;
			availableGridData.horizontalAlignment = SWT.FILL;
			availableGridData.grabExcessHorizontalSpace = true;
			availableGridData.grabExcessVerticalSpace = true;
			availableGridData.verticalIndent = 0;
			availableGridData.horizontalIndent = 0;
			textarea.setLayoutData(availableGridData);
		}

		textarea.setEditable(false);
		textarea.setText(text);
		textarea.setWordWrap(false);

		for (final LocatedValue l : highlights) {
			final StyleRange styleRange = new StyleRange();
			styleRange.start = fixOffset(l);
			styleRange.length = l.getLength();
			styleRange.underline = true;
			styleRange.foreground = ColorConstants.white;
			styleRange.background = ColorConstants.black;

			if (styleRange.start == -1) {
				styleRange.start = textarea.getOffsetAtLine(l.getLine());
				styleRange.length = textarea.getLine(l.getLine()).length();
			}
			textarea.setStyleRange(styleRange);
			textarea.setTopIndex(l.getLine() - 10);
		}

		position = new Label(contents, SWT.NONE);
		final GridData data2 = new GridData();
		data2.horizontalAlignment = SWT.FILL;
		data2.grabExcessHorizontalSpace = true;
		position.setLayoutData(data2);

		textarea.addCaretListener(new CaretListener() {
			@Override
			public void caretMoved(CaretEvent event) {
				setPositionLabel(event.caretOffset);
			}

		});

		return contents;
	}

	private void setPositionLabel(int caretOffset) {
		final int line = textarea.getLineAtOffset(caretOffset);
		final int base = textarea.getOffsetAtLine(line);
		final int column = caretOffset - base;
		position.setText("" + line + ":" + column);
	}

	private int fixOffset(LocatedValue l) {
		int start = l.getOffset() - 50;
		if (start < 0) {
			start = 0;
		}

		final String text = org.apache.commons.lang.StringEscapeUtils.escapeHtml(l.getValue().toString());

		final int pos = textarea.getText().indexOf(text, start);
		if (pos == -1 || pos > start + 100) {
			return -1;
		}

		return pos;
	}
}