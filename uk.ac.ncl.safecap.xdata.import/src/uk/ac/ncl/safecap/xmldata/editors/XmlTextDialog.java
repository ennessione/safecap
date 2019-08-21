package uk.ac.ncl.safecap.xmldata.editors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import uk.ac.ncl.safecap.misc.core.LocatedValue;
import uk.ac.ncl.safecap.xmldata.FileUtil;

public class XmlTextDialog extends Dialog {
	private StyledText textarea;
	private TreeViewer tree;
	private Label position;
	private final String title;

	private final List<LocatedValue> highlights;
	private final Document document;
	private final String text;

	public XmlTextDialog(final Shell parentShell, String file) {
		super(parentShell);
		title = file;
		highlights = new ArrayList<>();

		text = getFileContents(file);
		document = getDocument(file);
	}

	public boolean isValid() {
		return text != null && document == null;
	}

	private String getFileContents(String file) {
		try {
			return FileUtil.getFileContents(new File(file));
		} catch (final Throwable e1) {
			return null;
		}
	}

	private Document getDocument(String file) {
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setIgnoringComments(true);
		try {
			final DocumentBuilder builder = dbf.newDocumentBuilder();
			final InputSource is = new InputSource(file);
			return builder.parse(is);
		} catch (final Throwable e) {
			return null;
		}
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
		contentsGridLayout.numColumns = 2;
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
		// textarea.addPaintListener(new PaintListener() {
		//
		// @Override
		// public void paintControl(PaintEvent e) {
		// e.gc.setBackground(ColorConstants.blue);
		// e.gc.fillRectangle(0, 0, 30, 30);
		// }
		//
		// });

		for (final LocatedValue l : highlights) {
			// StyleRange styleRange = new StyleRange();
			// styleRange.start = fixOffset(l);
			// styleRange.length = l.getLength();
			// styleRange.underline = false;
			// styleRange.foreground = ColorConstants.black;
			// styleRange.background = ColorConstants.yellow;
			// textarea.setStyleRange(styleRange);

			final StyleRange styleRange = new StyleRange();
			styleRange.start = l.getOffset1();
			styleRange.length = 1;
			styleRange.underline = false;
			styleRange.foreground = ColorConstants.black;
			styleRange.background = ColorConstants.lightBlue;
			textarea.setStyleRange(styleRange);
			//
			// styleRange = new StyleRange();
			// styleRange.start = l.getOffset2();
			// styleRange.length = 1;
			// styleRange.underline = false;
			// styleRange.foreground = ColorConstants.black;
			// styleRange.background = ColorConstants.lightGray;
			// textarea.setStyleRange(styleRange);

			textarea.setTopIndex(l.getLine() - 10);

		}

		tree = new TreeViewer(contents, SWT.V_SCROLL | SWT.H_SCROLL);
		tree.setContentProvider(new DOMContentProvider());
		tree.setLabelProvider(new DOMLabelProvider());
		tree.setAutoExpandLevel(2);
		tree.setInput(document);

		final GridData data1 = new GridData();
		data1.horizontalAlignment = SWT.FILL;
		data1.verticalAlignment = SWT.FILL;
		data1.grabExcessHorizontalSpace = true;
		data1.grabExcessVerticalSpace = true;
		data1.minimumWidth = 400;
		tree.getTree().setLayoutData(data1);

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
		position.setText("line:" + line + "; column:" + column + "; offset:" + caretOffset);
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

	class DOMLabelProvider implements ILabelProvider {

		@Override
		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub

		}

		@Override
		public Image getImage(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getText(Object element) {
			if (element instanceof Node) {
				final Node node = (Node) element;
				switch (node.getNodeType()) {
				case Node.DOCUMENT_NODE:
				case Node.DOCUMENT_TYPE_NODE:
				case Node.ELEMENT_NODE:
				case Node.ENTITY_NODE:
				case Node.ENTITY_REFERENCE_NODE:
				case Node.NOTATION_NODE:
				case Node.PROCESSING_INSTRUCTION_NODE:
					return node.getNodeName();
				case Node.TEXT_NODE:
					return node.getTextContent();
				}
			}
			return null;
		}

	}

	class DOMContentProvider implements ITreeContentProvider {

		@Override
		public void dispose() {
			// TODO Auto-generated method stub

		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub

		}

		@Override
		public Object[] getElements(Object inputElement) {
			return asArray(document.getChildNodes());
		}

		private Object[] asArray(NodeList childNodes) {
			final List<Object> result = new ArrayList<>(childNodes.getLength());
			for (int i = 0; i < childNodes.getLength(); i++) {
				final Node node = childNodes.item(i);
				// if (node.getNodeType() == Node.TEXT_NODE) {
				// String text = node.getTextContent().trim();
				// if (!text.isEmpty())
				// result.add(node);
				// } else {
				result.add(node);
				// }
			}
			return result.toArray();
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof Node) {
				final Node node = (Node) parentElement;
				return asArray(node.getChildNodes());
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof Node) {
				final Node node = (Node) element;
				return node.hasChildNodes();
			} else {
				return false;
			}
		}

	}
}
