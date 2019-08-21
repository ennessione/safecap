package uk.ac.ncl.safecap.capacity.views;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.swtchart.Chart;
import org.swtchart.IAxisSet;
import org.swtchart.ILineSeries;
import org.swtchart.ILineSeries.PlotSymbolType;
import org.swtchart.ISeries.SeriesType;
import org.swtchart.ISeriesSet;
import org.swtchart.LineStyle;

import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;
import uk.ac.ncl.safecap.capacity.experiment.TrainRun;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.scitus.stat.ITrainRun;
import uk.ac.ncl.safecap.scitus.stat.Run2DRecord;
import uk.ac.ncl.safecap.scitus.stat.SimHistoryRecord;

public class TrainRunReport {
	private static Color[] colours = new Color[] { ColorConstants.black, ColorConstants.blue, ColorConstants.red,
			ColorConstants.green, ColorConstants.gray, ColorConstants.lightBlue, ColorConstants.orange, ColorConstants.cyan };

	private static final int PLOT_HEIGHT = 400;

	public static void open(SimulationExperiment experiment) {
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		final Shell frame = createContents(shell, experiment);
		frame.open();
		frame.layout();
		// Display display = shell.getDisplay();
		// while (!frame.isDisposed()) {
		// if (!display.readAndDispatch()) {
		// display.sleep();
		// }
		// }
	}

	private static Shell createContents(Shell shell, SimulationExperiment experiment) {
		final Shell frame = new Shell(shell, SWT.BORDER | SWT.RESIZE | SWT.CLOSE);
		frame.setMinimumSize(150, 100);
		frame.setSize(800, 700);
		frame.setText("Simulation report");
		frame.setLayout(new FillLayout(SWT.VERTICAL));

		final ScrolledComposite sparent = new ScrolledComposite(frame, SWT.V_SCROLL | SWT.H_SCROLL);
		// sparent.setExpandHorizontal(true);
		// sparent.setExpandVertical(true);
		final int plots = getNumberOfPlots(experiment);
		sparent.setSize(800, PLOT_HEIGHT * plots);
		sparent.setAlwaysShowScrollBars(true);

		final Composite parent = new Composite(sparent, SWT.NONE);
		sparent.setContent(parent);

		frame.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event e) {
				final Rectangle rect = frame.getClientArea();
				parent.setSize(rect.width, PLOT_HEIGHT * plots);
				frame.layout(true, true);
			}
		});

		// sparent.addMouseWheelListener(new MouseWheelListener() {
		// public void mouseScrolled(MouseEvent e) {
		// if (e.count > 0) {
		// int width = (int) (1.2 * parent.getSize().x);
		// int height = (int) (1.2 * parent.getSize().x);
		// parent.setSize(width, height);
		// sparent.setMinSize(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		// frame.layout(true, true);
		// } else if (e.count < 0) {
		// int width = (int) ((double) parent.getSize().x / 1.2);
		// int height = (int) ((double) parent.getSize().x /1.2);
		// parent.setSize(width, height);
		// sparent.setMinSize(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		// frame.layout(true, true);
		// }
		//
		// }
		// });

		parent.setSize(800, PLOT_HEIGHT * plots);
		parent.setLayout(new FillLayout(SWT.VERTICAL));
		parent.setBackground(ColorConstants.black);

		for (final ITrain train : experiment.getTrains()) {
			makeTrainChart(parent, train, experiment);
		}

		return frame;
	}

	private static int getNumberOfPlots(SimulationExperiment experiment) {
		int i = 0;
		for (final ITrain train : experiment.getTrains()) {
			final ITrainRun run = experiment.getRunData(train);
			if (run instanceof SimHistoryRecord) {
				final SimHistoryRecord record = (SimHistoryRecord) run;
				final Set<String> kinds = getCategories(record);
				i += kinds.size();
			} else if (run instanceof TrainRun) {
				i++;
			}
		}

		return i;
	}

	private static Chart createChartControl(Composite parent) {
		final Chart chart = new Chart(parent, SWT.NONE);
		chart.setBackground(ColorConstants.white);
		chart.setBackgroundInPlotArea(ColorConstants.white);

		chart.getTitle().setVisible(false);
		chart.getLegend().setPosition(SWT.BOTTOM);
		// chart.getLegend().setFont(TrackFonts.FONT_TINY);
		chart.getLegend().setBackground(ColorConstants.white);

		// chart.enableCompress(false);

		final IAxisSet axisSet = chart.getAxisSet();
		axisSet.getXAxis(0).getTick().setForeground(ColorConstants.black);
		axisSet.getYAxis(0).getTick().setForeground(ColorConstants.black);

		axisSet.getXAxis(0).getTitle().setVisible(false);
		axisSet.getYAxis(0).getTitle().setVisible(false);

		return chart;

	}

	private static void makeTrainChart(Composite parent, ITrain train, SimulationExperiment experiment) {
		final ITrainRun run = experiment.getRunData(train);
		if (run instanceof SimHistoryRecord) {
			final SimHistoryRecord record = (SimHistoryRecord) run;
			final Set<String> kinds = getCategories(record);
			for (final String s : kinds) {
				final Chart chart = createChartControl(parent);
				chart.getTitle().setVisible(true);
				chart.getTitle().setText(s);
				final ISeriesSet seriesSet = chart.getSeriesSet();
				makeTrainChart(seriesSet, train, record, s);
				final IAxisSet axisSet = chart.getAxisSet();
				axisSet.adjustRange();
				chart.redraw();
			}
		} else if (run instanceof TrainRun) {
			final TrainRun record = (TrainRun) run;
			final Chart chart = createChartControl(parent);
			chart.getTitle().setVisible(true);
			chart.getTitle().setText(record.getTrain().getName());
			final ISeriesSet seriesSet = chart.getSeriesSet();
			makeTrainChart2(seriesSet, train, record, run.getPosition());
			final IAxisSet axisSet = chart.getAxisSet();
			axisSet.adjustRange();
			chart.redraw();
		}
	}

	private static void makeTrainChart2(ISeriesSet seriesSet, ITrain train, TrainRun record, double[] position) {
		// makePlot(seriesSet, train, "position", ColorConstants.black,
		// record.getTime(), record.getPosition());
		makePlot(seriesSet, train, "speed", ColorConstants.blue, record.getPosition(), record.getSpeed());
	}

	private static void makeTrainChart(ISeriesSet seriesSet, ITrain train, SimHistoryRecord record, String s) {
		// Color color = new Color(Display.getCurrent(),
		// train.getDescriptor().getRgb());
		int index = 0;
		for (final String r : record.getRecords()) {
			if (record.getRecord(r).getKind().equals(s) && record.getRecord(r).isShow()) {
				final Run2DRecord sprofile = record.getRecord(r);
				if (sprofile.getXAxis() == Run2DRecord.XAXIS.TIME) {
					makePlot(seriesSet, train, colours[index % colours.length], sprofile, record.getTime());
				} else {
					makePlot(seriesSet, train, colours[index % colours.length], sprofile, record.getPosition());
				}
				index++;
			}
		}
	}

	private static void makePlot(ISeriesSet seriesSet, ITrain train, Color color, Run2DRecord sprofile, double[] pos) {
		final ILineSeries trs = (ILineSeries) seriesSet.createSeries(SeriesType.LINE,
				train.getDescriptor().getTrainName() + "/" + sprofile.getName());
		trs.setLineColor(color);
		trs.setLineWidth(sprofile.getLineWidth() > 0 ? sprofile.getLineWidth() : 1);
		trs.setLineStyle(sprofile.isDashed() ? LineStyle.DASH : LineStyle.SOLID);
		trs.setSymbolType(PlotSymbolType.NONE);
		trs.setXSeries(pos);
		trs.setYSeries(sprofile.getData());
	}

	private static void makePlot(ISeriesSet seriesSet, ITrain train, String name, Color color, double[] x, double[] y) {
		final ILineSeries trs = (ILineSeries) seriesSet.createSeries(SeriesType.LINE,
				train.getDescriptor().getTrainName() + "/" + name);
		trs.setLineColor(color);
		trs.setSymbolType(PlotSymbolType.NONE);
		trs.setXSeries(x);
		trs.setYSeries(y);
	}

	private static Set<String> getCategories(SimHistoryRecord record) {
		final Set<String> result = new HashSet<>();
		for (final String r : record.getRecords()) {
			if (record.getRecord(r).isShow()) {
				result.add(record.getRecord(r).getKind());
			}
		}

		return result;
	}

}
