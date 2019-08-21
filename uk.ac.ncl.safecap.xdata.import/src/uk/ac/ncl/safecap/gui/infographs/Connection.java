package uk.ac.ncl.safecap.gui.infographs;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.ZestStyles;

public class Connection {
	protected Object source;
	protected Object target;
	protected double weight;
	protected INodeMapper mapper;
	protected String label;
	protected Image image;
	protected boolean directed;

	public Connection(INodeMapper mapper, Object source, Object target) {
		this.source = source;
		this.target = target;
		weight = 1.0d;
		this.mapper = mapper;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Object getSource() {
		return source;
	}

	public Object getTarget() {
		return target;
	}

	public String getLabel() {
		return label;
	}

	public Image getImage() {
		return image;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Font getFont() {
		return ElementNode.standard_font;
	}

	public void setVisual(GraphConnection conn) {
		conn.setLineColor(ColorConstants.gray);
		conn.setCurveDepth(100);
	}

	public boolean isDirected() {
		return directed;
	}

	public void setDirected(boolean directed) {
		this.directed = directed;
	}

	public GraphConnection drawConnection(Graph g) {
		final GraphConnection c = new GraphConnection(g, SWT.NONE, mapper.getNodeFor(source), mapper.getNodeFor(target));
		c.setWeight(getWeight());
		final String text = getLabel();

		if (text != null) {
			c.setText(text);
		}

		final Image image = getImage();
		if (image != null) {
			c.setImage(image);
		}

		c.setData(this);
		c.setFont(getFont());
		setVisual(c);

		if (directed) {
			c.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		}

		return c;
	}

}
