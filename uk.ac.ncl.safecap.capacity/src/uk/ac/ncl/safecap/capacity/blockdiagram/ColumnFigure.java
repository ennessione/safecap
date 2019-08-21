package uk.ac.ncl.safecap.capacity.blockdiagram;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public abstract class ColumnFigure extends CoordinateSystemFigure {
	public boolean debug = false;

	protected List<StateSetEvent> events = new ArrayList<>();

	public ColumnFigure(IZoomInfo zoomer) {
		super(zoomer);

		setLayoutManager(new XYLayout());
		setForegroundColor(ColorConstants.darkGray);
		// setBorder(new LineBorder(1));
		// setAntialias(1);
		// setFill(false);
		// setOpaque(true);
		// useLocalCoordinates();

		// NamedBoxFigure box = new NamedBoxFigure("", ColorConstants.blue,
		// true);
		// box.setBounds(new Rectangle(0, 0, 10, 100));
		// add(box);
		//
		// box.setBounds(new Rectangle(0, 0, 5, 50));
	}

	public void commit(int state, double time) {
		final StateSetEvent event = new StateSetEvent(time, state);
		if (events.size() > 0) {
			event.figure = createFigure(events.get(events.size() - 1).state);
			add(event.figure);
		}
		events.add(event);
	}

	abstract protected NamedBoxFigure createFigure(int state);

	protected void layoutEvents(int width) {
		// System.out.println("Column events :");
		// for (StateSetEvent event: events)
		// System.out.println(" " + event.time + " : " + event.state);

		if (events.size() == 0) {
			return;
		}

		final LayoutManager layoutMgr = getLayoutManager();
		int y = 0;
		for (int i = 1; i < events.size(); i++) {
			final StateSetEvent event = events.get(i);
			final NamedBoxFigure fig = event.figure;
			final int height = toGraphY(event.time) - y;
			final Rectangle bounds = new Rectangle(0, y, width, height);
			// Rectangle bounds = new Rectangle(0, 0, 100, 100);
			fig.setSize(width, height);
			fig.setLocation(new Point(0, y));
			// fig.setBounds(bounds);
			layoutMgr.setConstraint(fig, bounds);
			// UpdateManager updateMgr = getUpdateManager();
			// updateMgr.addDirtyRegion(this, bounds);
			// fig.invalidate();
			// fig.invalidateTree();
			y += height;
			// if (debug)
			// System.out.println("Box bounds: " + bounds);
		}

	}

	public abstract void myLayout();

	public abstract void paintLabel(Graphics graphics, int dx, int dy);
}
