package uk.ac.ncl.safecap.gui.infographs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.DirectedGraphLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import uk.ac.ncl.safecap.gui.infographs.dependencies.ZoomContributionViewItemSpecial;
import uk.ac.ncl.safecap.gui.infographs.dependencies.ZoomManager;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;

public abstract class InfoGraph extends ViewPart implements INodeMapper, ISelectionListener, ControlListener {
	public static LayoutAlgorithm treeLayout = new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	public static LayoutAlgorithm springLayout = new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	public static LayoutAlgorithm directedLayout = new DirectedGraphLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	public static LayoutAlgorithm radialLayout = new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);

	private static final Color Colors[] = new Color[] { ColorConstants.blue, ColorConstants.green, ColorConstants.red, ColorConstants.cyan,
			ColorConstants.lightGray };

	protected Graph graph;
	private ZoomManager zoom;
	private Action export;

	public Color makeNewColor1(int code) {
		final int R = 64;
		final int G = 128 + (code << 8) & 127;
		final int B = 64;
		return new Color(super.getSite().getShell().getDisplay(), R, G, B); // random color, but can be bright or dull
	}

	public Color makeNewColor2(int code) {
		final int R = 128 + (code & 127);
		final int G = 128 + (code >> 8 & 127);
		final int B = 128 + (code >> 16 & 127);
		return new Color(super.getSite().getShell().getDisplay(), R, G, B); // random color, but can be bright or dull
	}

	private void createActions() {
		export = new Action("Export") {
			@Override
			public void run() {
				exportGraph();
			}
		};

		export.setImageDescriptor(ResourceManager.getPluginImageDescriptor("uk.ac.ncl.safecap.xdata.import", "icons/xml_exports.png"));
		export.setToolTipText("Export to dotty");
	}

	public void exportGraph() {
		final FileDialog dialog = new FileDialog(Display.getDefault().getActiveShell(), SWT.SAVE);
		dialog.setFilterNames(new String[] { "Dotty", "dotty formal (*.dot)" });
		dialog.setFilterExtensions(new String[] { "*.dot", "*.*" });
		dialog.setFileName("graph.dot");
		final String path = dialog.open();
		if (path == null) {
			return;
		}
		final File file = new File(path);

		try {

			final FileWriter writer = new FileWriter(file);

			writer.append("digraph G {\n");

			for (final Object _c : graph.getNodes()) {
				final GraphNode c = (GraphNode) _c;

				ppNode(writer, c);
				ppNodeAttr(writer, c);
				writer.append(";\n");
			}

			for (final Object _c : graph.getConnections()) {
				final GraphConnection c = (GraphConnection) _c;

				ppNode(writer, c.getSource());
				ppEdge(writer, c);
				ppNode(writer, c.getDestination());
				ppEdgeAttr(writer, c);
				writer.append(";\n");
			}

			writer.append("}\n");

			writer.flush();
			writer.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private void ppNodeAttr(FileWriter sb, GraphNode c) throws IOException {
		final Color colb = c.getForegroundColor();

		sb.append(" [");
		sb.append("color=\"#");
		sb.append(rgbToHex(colb.getRGB()));
		sb.append("\"");

		if (c.getData() instanceof ElementNode) {
			final ElementNode en = (ElementNode) c.getData();
			if (en.getSize() != null) {
				sb.append(" width=");
				sb.append("" + en.getSize());
			}
		}
		sb.append("]");

	}

	private static String rgbToHex(RGB color) {
		return unsignedByteToString(color.red) + unsignedByteToString(color.green) + unsignedByteToString(color.blue);

	}

	private static String unsignedByteToString(int i) {
		return "" + Character.forDigit(i / 16, 16) //$NON-NLS-1$
				+ Character.forDigit(i % 16, 16);
	}

	private void ppEdge(FileWriter sb, GraphConnection conn) throws IOException {
		if ((conn.getConnectionStyle() & ZestStyles.CONNECTIONS_DIRECTED) == ZestStyles.CONNECTIONS_DIRECTED) {
			sb.append("->");
		} else {
			sb.append("--");
		}
	}

	private void ppEdgeAttr(FileWriter sb, GraphConnection conn) throws IOException {
		if (conn.getText() != null && conn.getText().length() > 0) {
			sb.append("[label=\"");
			sb.append(conn.getText());
			sb.append("\"]");
		}
	}

	private void ppNode(FileWriter sb, GraphNode node) throws IOException {
		sb.append('"');
		sb.append(node.getText());
		sb.append('"');
	}

	public void contributeUI() {
	}

	@Override
	public void createPartControl(Composite parent) {
		final ISelectionService selectionService = getSite().getWorkbenchWindow().getSelectionService();
		selectionService.addSelectionListener(SafecapConstants.NAVIGATOR_ID, this);

		parent.addControlListener(this);

		graph = new Graph(parent, SWT.NONE);
		graph.setNodeStyle(ZestStyles.NODES_NO_ANIMATION | ZestStyles.NODES_NO_LAYOUT_RESIZE);
		graph.setForeground(ColorConstants.black);
		graph.setLayoutAlgorithm(treeLayout, false);
		fillToolBar();
		contributeUI();
	}

	protected void fillToolBar() {
		zoom = new ZoomManager(graph.getRootLayer(), graph.getViewport());
		final ZoomContributionViewItemSpecial toolbarZoomContributionViewItem = new ZoomContributionViewItemSpecial(zoom);
		final IActionBars bars = getViewSite().getActionBars();
		bars.getMenuManager().add(toolbarZoomContributionViewItem);

		createActions();
		final IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
		// toolbarManager.removeAll();
		toolbarManager.add(export);

		final List<Action> actions = getContributedActions();
		if (actions != null) {
			for (final Action action : actions) {
				toolbarManager.add(action);
			}
		}
	}

	protected List<Action> getContributedActions() {
		return null;
	}

	protected void clearGraph() {

		// remove all the connections

		Object[] objects = graph.getConnections().toArray();

		for (int x = 0; x < objects.length; x++) {
			((GraphConnection) objects[x]).dispose();
		}

		// remove all the nodes

		objects = graph.getNodes().toArray();

		for (int x = 0; x < objects.length; x++) {
			((GraphNode) objects[x]).dispose();
		}

	}

	public LayoutAlgorithm getLayout() {
		return graph.getLayoutAlgorithm();
	}

	public void setLayout(LayoutAlgorithm currentLayout) {
		graph.setLayoutAlgorithm(currentLayout, false);
	}

	@Override
	public void controlResized(ControlEvent e) {
		graph.applyLayout();
	}

	@Override
	public void controlMoved(ControlEvent e) {
		// nothing
	}

	public abstract void setInput(Object input);

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection.isEmpty()) {
			return;
		}

		if (selection instanceof ITreeSelection) {
			final Object[] input = ((ITreeSelection) selection).toArray();
			if (input.length > 0) {
				setInput(input[0]);
			}
		}
	}

	@Override
	public void setFocus() {
		// nothing
	}

	public class TypedStringNode extends ElementNode {
		private final String type;

		public TypedStringNode(String s, String type) {
			super(s);
			this.type = type;
		}

		@Override
		public void setVisual(GraphNode node) {
			node.setBackgroundColor(ColorConstants.black);
			node.setForegroundColor(Colors[type.hashCode() % Colors.length]);
		}
	}

	public class StringNode extends ElementNode {
		public StringNode(String s) {
			super(s);
		}

		@Override
		public void setVisual(GraphNode node) {
			node.setBackgroundColor(ColorConstants.black);
			node.setForegroundColor(ColorConstants.white);
		}
	}
}
