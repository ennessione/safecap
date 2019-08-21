package uk.ac.ncl.safecap.capacity.blockdiagram;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.swt.graphics.Color;

import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;
import uk.ac.ncl.safecap.misc.core.RouteWrapper;
import uk.ac.ncl.safecap.scitus.base.Evolution;
import uk.ac.ncl.safecap.scitus.base.InitialRouteClaimInstanceEvent;
import uk.ac.ncl.safecap.scitus.base.InstanceEvent;
import uk.ac.ncl.safecap.scitus.base.InstanceProgression;
import uk.ac.ncl.safecap.scitus.base.Progression;
import uk.ac.ncl.safecap.scitus.base.RouteProgression;
import uk.ac.ncl.safecap.scitus.base.RouteSettingProgression;
import uk.ac.ncl.safecap.scitus.base.RouteUnsetInstanceEvent;

public class RouteFigure extends ColumnFigure {
	private final int _width;
	private final RouteWrapper _route;

	public RouteFigure(int width, IZoomInfo zoomer, RouteWrapper route, SimulationExperiment exp) {
		super(zoomer);

		_width = width;
		_route = route;
		setForegroundColor(ColorConstants.darkGray);
		setBorder(new LineBorder(1));
		// setAntialias(1);
		// useLocalCoordinates();

		double curTime = 0;
		commit(-1, 0);
		for (final Evolution ev : exp.getHistory()) {
			for (final Progression pr : ev.getProgressions()) {
				if (pr instanceof RouteSettingProgression) {
					final RouteSettingProgression pr1 = (RouteSettingProgression) pr;
					if (pr1.getRoutes().contains(route.getRoute())) {
						commit(0, curTime + ev.getExtent());
					}
				}

				if (pr instanceof RouteProgression) {
					final RouteProgression routeProgression = (RouteProgression) pr;
					if (routeProgression.getRoute().equals(_route)) {
						commit(routeProgression.getNewState(), curTime + ev.getExtent());
					}
				}
				if (pr instanceof InstanceProgression) {
					final InstanceProgression pr1 = (InstanceProgression) pr;
					for (final InstanceEvent event : pr1.getInstances()) {
						if (event instanceof InitialRouteClaimInstanceEvent) {
							final InitialRouteClaimInstanceEvent init = (InitialRouteClaimInstanceEvent) event;
							if (init.getRoute().equals(route)) {
								commit(0, curTime);
							}
						}

						if (event instanceof RouteUnsetInstanceEvent) {
							final RouteUnsetInstanceEvent ev1 = (RouteUnsetInstanceEvent) event;
							if (ev1.getRoute().equals(route)) {
								commit(0, curTime + pr1.getOffset());
							}
						}
					}
				}
			}
			curTime += ev.getExtent();
		}
		commit(-1, curTime);
	}

	@Override
	protected NamedBoxFigure createFigure(int state) {
		String name = "";
		if (state > 0 && state < _route.getAspects() - 1) {
			if (state == 1) {
				name = "Y";
			} else if (state == 2) {
				name = "YY";
			} else if (state == 3) {
				name = "YYY";
			} else {
				name = "Y" + state;
			}
		}
		return new NamedBoxFigure(name, getColor(state), false);
	}

	@Override
	public void myLayout() {
		layoutEvents(_width);
	}

	private Color getColor(int aspect) {
		if (aspect == -1) {
			return ColorConstants.gray;
		} else if (aspect == 0) {
			return ColorConstants.red;
		} else if (aspect == _route.getAspects() - 1) {
			return ColorConstants.lightGreen;
		} else {
			return ColorConstants.yellow;
		}
	}

	@Override
	public void paintLabel(Graphics graphics, int dx, int dy) {
		graphics.pushState();
		graphics.setFont(GraphFigure.labelFont);
		graphics.translate(getBounds().x + dx, getBounds().y + dy);
		graphics.rotate(-90);
		graphics.drawText(_route.getRoute().getLabel(), 0, 0);
		graphics.popState();
	}
}
