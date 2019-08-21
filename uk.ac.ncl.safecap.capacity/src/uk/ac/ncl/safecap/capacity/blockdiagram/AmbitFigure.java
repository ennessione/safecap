package uk.ac.ncl.safecap.capacity.blockdiagram;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.swt.graphics.Color;

import safecap.model.Ambit;
import safecap.model.Route;
import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;
import uk.ac.ncl.safecap.misc.core.TrainLine;

public class AmbitFigure extends ColumnFigure implements MouseMotionListener {
	private final Ambit _ambit;
	private final TrainLine _line;

	public AmbitFigure(IZoomInfo zoomer, Route route, Ambit ambit, SimulationExperiment exp, TrainLine line) {
		super(zoomer);

		_ambit = ambit;
		_line = line;
		// setForegroundColor(ColorConstants.white);
		// setBorder(new LineBorder(1));
		// setAntialias(1);
		// useLocalCoordinates();
	}

	public void createChildren() {
		// Collections.sort(_events);
		// List<AmbitSetEvent> eventsToDelete = new ArrayList<AmbitSetEvent>();
		// for (int i=1; i<_events.size(); i++)
		// {
		// AmbitSetEvent ev1 = _events.get(i-1);
		// AmbitSetEvent ev2 = _events.get(i);
		//// if (Math.abs(ev1.time-ev2.time) < 0.00001)
		//// eventsToDelete.add(ev2);
		// if (ev1.state == ev2.state)
		// eventsToDelete.add(ev2);
		// }
		// for (AmbitSetEvent event: eventsToDelete)
		// {
		// _events.remove(event);
		// }
		//
		// int state = -1;
		// int pos = 0;
		// for (AmbitSetEvent ev: _events)
		// {
		// IFigure fig = new NamedBoxFigure("", getColor(state),
		// state==-1?false:true);
		//// IFigure fig = new NamedBoxFigure("", getColor(state), false);
		//// fig.addMouseMotionListener(this);
		// int height = toGraphY(ev.time) - pos;
		// fig.setBounds(new Rectangle(0, pos, getWidth(), height));
		// this.add(fig);
		//
		// pos += height;
		// state = ev.state;
		// }
		//
		// setSize(getWidth(), getHeight());
	}

	@Override
	protected NamedBoxFigure createFigure(int state) {
		final NamedBoxFigure fig = new NamedBoxFigure("", getColor(state), true);
		// if (state == 0)
		// fig.addMouseMotionListener(this);
		return fig;
		// return new NamedBoxFigure("", getColor(state), state==-1?false:true);
	}

	@Override
	public void myLayout() {
		layoutEvents(toGraphX(_line.getAmbitLength(_ambit)));
	}

	private Color getColor(int state) {
		if (state == -1) {
			return ColorConstants.lightGray;
		} else {
			return ColorConstants.gray;
		}
	}

	@Override
	public void paintLabel(Graphics graphics, int dx, int dy) {
		graphics.pushState();
		graphics.setFont(GraphFigure.labelFont);
		graphics.translate(getBounds().x + dx, getBounds().y + dy);
		// graphics.rotate(-90);
		graphics.drawText(_ambit.getLabel(), 0, 0);
		graphics.popState();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// if (arg0.getSource() instanceof NamedBoxFigure)
		// {
		final NamedBoxFigure fig = (NamedBoxFigure) arg0.getSource();
		fig.setBackgroundColor(ColorConstants.darkGray);
		// }
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// if (arg0.getSource() instanceof NamedBoxFigure)
		// {
		final NamedBoxFigure fig = (NamedBoxFigure) arg0.getSource();
		fig.setInitialColor();
		// }
	}

	@Override
	public void mouseHover(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		getParent().handleMouseMoved(arg0);
	}
}
