package uk.ac.ncl.safecap.capacity.blockdiagram;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LineBorder;

import safecap.model.Point;
import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;
import uk.ac.ncl.safecap.scitus.base.Evolution;
import uk.ac.ncl.safecap.scitus.base.PointProgression;
import uk.ac.ncl.safecap.scitus.base.Progression;
import uk.ac.ncl.safecap.scitus.common.PointState;

public class PointFigure extends ColumnFigure {
	private final int _width;
	private final Point _point;
	SimulationExperiment _exp;

	public PointFigure(int width, IZoomInfo zoomer, Point point, SimulationExperiment exp) {
		super(zoomer);

		setForegroundColor(ColorConstants.darkGray);
		setBorder(new LineBorder(1));
		// setForegroundColor(ColorConstants.black);
		// setBackgroundColor(ColorConstants.blue);
		// setBorder(new LineBorder(1));
		// setAntialias(1);
		// useLocalCoordinates();
		// setOpaque(false);
		// setFill(false);

		_width = width;
		_exp = exp;
		_point = point;

		double curTime = 0;

		commit(0, 0);
		for (final Evolution ev : _exp.getHistory()) {
			curTime += ev.getExtent();
			for (final Progression pr : ev.getProgressions()) {
				if (pr instanceof PointProgression) {
					final PointProgression pointProgression = (PointProgression) pr;
					if (pointProgression.getPoint().equals(_point)) {
						commit(pointProgression.getNewState() == PointState.NORMAL ? 0 : 1, curTime);
					}
				}
			}
		}
		commit(0, curTime);
	}

	@Override
	protected NamedBoxFigure createFigure(int state) {
		return new NamedBoxFigure(state == 0 ? "N" : "R", state == 0 ? ColorConstants.darkGray : ColorConstants.lightGray, false);
	}

	@Override
	public void myLayout() {
		// debug = true;
		layoutEvents(_width);
	}

	@Override
	public void paintLabel(Graphics graphics, int dx, int dy) {
		graphics.pushState();
		graphics.setFont(GraphFigure.labelFont);
		graphics.translate(getBounds().x + dx, getBounds().y + dy);
		graphics.rotate(-90);
		graphics.drawText(_point.getNode().getLabel(), 0, 0);
		graphics.popState();
	}
}