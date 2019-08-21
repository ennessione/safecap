package uk.ac.ncl.safecap.capacity.blockdiagram;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import safecap.model.Ambit;
import safecap.model.Route;
import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.AmbitDeoccupationInstanceEvent;
import uk.ac.ncl.safecap.scitus.base.Evolution;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.scitus.base.InitialRouteClaimInstanceEvent;
import uk.ac.ncl.safecap.scitus.base.InstanceEvent;
import uk.ac.ncl.safecap.scitus.base.InstanceProgression;
import uk.ac.ncl.safecap.scitus.base.Progression;
import uk.ac.ncl.safecap.scitus.base.RouteSettingProgression;
import uk.ac.ncl.safecap.scitus.stat.ITrainRun;

public class BlockDiagramFigure extends CoordinateSystemFigure {
	private final TrainLine _line;
	private final SimulationExperiment _exp;
	private final Map<String, Color> _colors;
	private final Map<Ambit, AmbitFigure> _figs = new HashMap<>();
	private double _totalTime;

	public BlockDiagramFigure(IZoomInfo zoomer, TrainLine line, SimulationExperiment exp, Map<String, Color> colors) {
		super(zoomer);
		_line = line;
		_exp = exp;
		_colors = colors;
		setLayoutManager(new XYLayout());
		setForegroundColor(ColorConstants.black);

		_totalTime = 0;
		for (final Evolution ev : _exp.getHistory()) {
			_totalTime += ev.getExtent();
		}

		for (final Route route : _line.getSchemaLine().getRoutes()) {
			for (final Ambit ambit : route.getAmbits()) {
				final AmbitFigure fig = new AmbitFigure(zoomer, route, ambit, _exp, _line);
				add(fig);
				_figs.put(ambit, fig);
			}
		}

		// initialise ambits
		for (final Ambit ambit : _figs.keySet()) {
			final AmbitFigure fig = _figs.get(ambit);
			fig.commit(-1, 0);
		}

		double curTime = 0;
		for (final Evolution ev : _exp.getHistory()) {
			for (final Progression pr : ev.getProgressions()) {
				// if (pr instanceof RouteProgression)
				// {
				// RouteProgression routeProgression = (RouteProgression) pr;
				// for (Ambit ambit: routeProgression.getRoute().getAmbits())
				// {
				// AmbitFigure fig = _figs.get(ambit);
				// if (fig != null)
				// {
				// fig.commit(0, curTime);
				// }
				// }
				// }

				if (pr instanceof RouteSettingProgression) {
					final RouteSettingProgression pr1 = (RouteSettingProgression) pr;
					for (final Route route : pr1.getRoutes()) {
						for (final Ambit ambit : route.getAmbits()) {
							final AmbitFigure fig = _figs.get(ambit);
							if (fig != null) {
								fig.commit(0, curTime);
								System.out.println("Ambit " + ambit.getLabel() + " locked at " + curTime
										+ " due to RouteSettingProgression");
							}
						}
					}
				}

				if (pr instanceof InstanceProgression) {
					final InstanceProgression instanceProgression = (InstanceProgression) pr;
					for (final InstanceEvent event : instanceProgression.getInstances()) {
						if (event instanceof InitialRouteClaimInstanceEvent) {
							final InitialRouteClaimInstanceEvent init = (InitialRouteClaimInstanceEvent) event;
							final Route route = init.getRoute();
							for (final Ambit ambit : route.getAmbits()) {
								final AmbitFigure fig = _figs.get(ambit);
								if (fig != null) {
									fig.commit(0, curTime + instanceProgression.getOffset());
									System.out.println("Ambit " + ambit.getLabel() + " locked at "
											+ (curTime + instanceProgression.getOffset())
											+ " due to InitialRouteClaimInstanceEvent");
								}
							}
						}

						if (event instanceof AmbitDeoccupationInstanceEvent) {
							final AmbitDeoccupationInstanceEvent deoc = (AmbitDeoccupationInstanceEvent) event;
							final AmbitFigure fig = _figs.get(deoc.getAmbit());
							if (fig != null) {
								fig.commit(-1, curTime + ev.getExtent());
								System.out.println(
										"Ambit " + deoc.getAmbit().getLabel() + " deoccupied at " + (curTime + ev.getExtent()));
							}
						}

						// if (event instanceof RouteUnsetInstanceEvent)
						// {
						// RouteUnsetInstanceEvent ev1 =
						// (RouteUnsetInstanceEvent) event;
						// for (Ambit ambit: ev1.getRoute().getAmbits())
						// {
						// AmbitFigure fig = _figs.get(ambit);
						// if (fig != null)
						// {
						// fig.commit(-1, curTime);
						// }
						// }
						// }
					}
				}
			}
			curTime += ev.getExtent();
		}

		// finalize ambits
		for (final Ambit ambit : _figs.keySet()) {
			final AmbitFigure fig = _figs.get(ambit);
			fig.commit(-1, curTime);
		}
	}

	public void myLayout() {
		int maxX = 0, maxY = 0;

		final int height = toGraphY(_totalTime);

		final LayoutManager layoutMgr = getLayoutManager();
		int figPos = 0;
		for (final Route route : _line.getSchemaLine().getRoutes()) {
			for (final Ambit ambit : route.getAmbits()) {
				final AmbitFigure fig = _figs.get(ambit);
				final Rectangle bounds = new Rectangle(figPos, 0, toGraphX(_line.getAmbitLength(ambit)), height);
				fig.setSize(bounds.getSize());
				fig.setLocation(bounds.getLocation());
				// fig.setBounds(bounds);
				layoutMgr.setConstraint(fig, bounds);
				fig.myLayout();
				figPos += fig.getSize().width;
			}
		}

		// get maximum size for the figure
		for (final Object obj : getChildren()) {
			final IFigure fig = (IFigure) obj;
			if (fig.getBounds().getBottomRight().x > maxX) {
				maxX = fig.getBounds().getBottomRight().x;
			}
			if (fig.getBounds().getBottomRight().y > maxY) {
				maxY = fig.getBounds().getBottomRight().y;
			}
		}
		setSize(maxX, height);

		// UpdateManager updateMgr = getUpdateManager();
		// updateMgr.addDirtyRegion(this, getBounds());
	}

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);

		graphics.pushState();
		graphics.translate(getLocation());
		graphics.setAntialias(SWT.ON);

		for (final ITrain actor : _exp.getTrains()) {
			if (actor.getLine().equals(_line)) {
				final String trainName = actor.getDescriptor().getTrainName();
				Color color = _colors.get(trainName);
				if (color == null) {
					color = org.eclipse.draw2d.ColorConstants.black;
				}
				graphics.setForegroundColor(color);
				graphics.setLineWidth(1);
				final double len = actor.getDescriptor().getLength();
				final ITrainRun data = _exp.getRunData(actor);
				final double[] pos = data.getPosition();
				double lastPos = pos[0];
				// double dt = data.getDeltaT();
				double lastTime = 0;

				// double sumDt = dt;

				for (int i = 1; i < pos.length; i++) {
					final double newTime = data.getTime()[i];

					final int lastX = toGraphX(lastPos), lastY = toGraphY(lastTime), newX = toGraphX(pos[i]),
							newY = toGraphY(newTime);
					if (Math.abs(lastX - newX) + Math.abs(lastY - newY) > 0) {

						graphics.setForegroundColor(color);
						graphics.setLineWidth(2);
						graphics.drawLine(lastX, lastY, newX, newY);
						graphics.drawLine(toGraphX(lastPos - len), toGraphY(lastTime), toGraphX(pos[i] - len), toGraphY(newTime));
						// if (lastTime+dt - lastTimeDrawnTrain > 0.5)
						// {
						// graphics.setForegroundColor(ColorConstants.black);
						// graphics.setLineWidth(1);
						// graphics.drawLine(toGraphX(pos[i]-len),
						// toGraphY(lastTime+dt), toGraphX(pos[i]),
						// toGraphY(lastTime+dt));
						// lastTimeDrawnTrain = lastTime+dt;
						// }
						lastTime += newTime;
						lastPos = pos[i];
					}
				}
			}
		}

		graphics.popState();
	}

	public void paintLabels(Graphics graphics, int dy) {
		for (final Object obj : getChildren()) {
			if (obj instanceof ColumnFigure) {
				final ColumnFigure fig = (ColumnFigure) obj;
				fig.paintLabel(graphics, 5, dy);
			}
		}
		// graphics.pushState();
		// graphics.setFont(GraphFigure.labelFont);
		// graphics.translate(this.getBounds().x+dx, this.getBounds().y+dy);
		// graphics.rotate(-90);
		// graphics.drawText(_route.getLabel(), 0, 0);
		// graphics.popState();
	}
}
