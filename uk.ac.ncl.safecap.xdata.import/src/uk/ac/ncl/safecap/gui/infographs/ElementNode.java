package uk.ac.ncl.safecap.gui.infographs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;

import safecap.Labeled;

public abstract class ElementNode {
	public static final Font standard_font;
	private final Object element;

	static {
		standard_font = makeFont("Helvetica", 8, SWT.BOLD);
	}

	private static Font makeFont(final String font, final int size, final int style) {
		final IWorkbench wb = PlatformUI.getWorkbench();
		return new Font(wb.getDisplay(), font, size, style);
	}

	public ElementNode(Object element) {
		this.element = element;
	}

	public Object getElement() {
		return element;
	}

	public String getLabel() {
		if (element instanceof Labeled) {
			return ((Labeled) element).getLabel();
		}

		return element.toString();
	}

	public Image getImage() {
		return null;
	}

	public Double getSize() {
		return null;
	}

	public Font getFont() {
		return standard_font;
	}

	public void setVisual(GraphNode node) {
	}

	public GraphNode drawElement(Graph g) {
		final GraphNode gnode = new GraphNode(g, SWT.NONE, getLabel());
		final Image image = getImage();

		if (image != null) {
			gnode.setImage(image);
		}
		gnode.setData(this);
		gnode.setFont(getFont());
		setVisual(gnode);
		return gnode;
	}

	protected void handleDoubleClick() {
		// do nothing
	}
}