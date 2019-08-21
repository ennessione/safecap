package uk.ac.ncl.safecap.capacity.blockdiagram;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class NamedBoxFigure extends RectangleFigure {
	// private String _name;
	private final Color _color;

	public NamedBoxFigure(String name, Color color, boolean border) {
		_color = color;
		// _name = name;
		setBorder(new LineBorder(1));
		if (border) {
			setForegroundColor(ColorConstants.darkGray);
		} else {
			setForegroundColor(color);
		}
		setBackgroundColor(color);
		setFill(true);
		setOpaque(true);
		useLocalCoordinates();

		// setBackgroundColor(ColorConstants.black);
		// setForegroundColor(ColorConstants.red);

		// final Label label = new Label(_name);
		// if (color == ColorConstants.yellow)
		// label.setForegroundColor(ColorConstants.black);
		// else
		// label.setForegroundColor(ColorConstants.white);
		// label.setLabelAlignment(PositionConstants.CENTER);
		// label.setBounds(new Rectangle(0,0,10,20));
		// label.setFont(GraphFigure.markFont);

		addFigureListener(new FigureListener() {
			@Override
			public void figureMoved(IFigure arg0) {
				final Rectangle rect = getBounds().getCopy();
				rect.height = 20;
				// label.setBounds(rect);
			}
		});

		// add(label);

	}

	public void setInitialColor() {
		setBackgroundColor(_color);
	}

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		// graphics.setForegroundColor(ColorConstants.black);
		// graphics.drawText(_name, this.);
	}

}
