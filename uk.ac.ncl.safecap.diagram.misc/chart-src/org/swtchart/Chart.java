/*******************************************************************************
 * Copyright (c) 2008-2011 SWTChart project. All rights reserved.
 * 
 * This code is distributed under the terms of the Eclipse Public License v1.0
 * which is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.swtchart;

import java.util.EventListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TypedListener;
import org.swtchart.internal.ChartLayout;
import org.swtchart.internal.ChartLayoutData;
import org.swtchart.internal.ChartTitle;
import org.swtchart.internal.Legend;
import org.swtchart.internal.PlotArea;
import org.swtchart.internal.Title;
import org.swtchart.internal.axis.AxisSet;
import org.swtchart.internal.series.SeriesSet;

/**
 * A chart which are composed of title, legend, axes and plot area.
 */
public class Chart extends Composite implements Listener {

	/** the title */
	private final Title title;

	/** the legend */
	private final Legend legend;

	/** the set of axes */
	private final AxisSet axisSet;

	/** the plot area */
	private final PlotArea plotArea;

	/** the orientation of chart which can be horizontal or vertical */
	private int orientation;

	/** the state indicating if compressing series is enabled */
	private boolean compressEnabled;

	/** the state indicating if the update of chart appearance is suspended */
	private boolean updateSuspended;

	/**
	 * Constructor.
	 * 
	 * @param parent the parent composite on which chart is placed
	 * @param style  the style of widget to construct
	 */
	public Chart(Composite parent, int style) {
		super(parent, style);

		orientation = SWT.HORIZONTAL;
		compressEnabled = true;
		updateSuspended = false;

		parent.layout();

		setLayout(new ChartLayout());

		title = new ChartTitle(this, SWT.NONE);
		title.setLayoutData(new ChartLayoutData(SWT.DEFAULT, 100));
		legend = new Legend(this, SWT.NONE);
		legend.setLayoutData(new ChartLayoutData(200, SWT.DEFAULT));
		plotArea = new PlotArea(this, SWT.NONE);
		axisSet = new AxisSet(this);

		updateLayout();

		addListener(SWT.Resize, this);
	}

	/**
	 * Gets the chart title.
	 * 
	 * @return the chart title
	 */
	public ITitle getTitle() {
		return title;
	}

	/**
	 * Gets the legend.
	 * 
	 * @return the legend
	 */
	public ILegend getLegend() {
		return legend;
	}

	/**
	 * Gets the set of axes.
	 * 
	 * @return the set of axes
	 */
	public IAxisSet getAxisSet() {
		return axisSet;
	}

	/**
	 * Gets the plot area.
	 * 
	 * @return the plot area
	 */
	public Composite getPlotArea() {
		return plotArea;
	}

	/**
	 * Gets the set of series.
	 * 
	 * @return the set of series
	 */
	public ISeriesSet getSeriesSet() {
		return plotArea.getSeriesSet();
	}

	/*
	 * @see Control#setBackground(Color)
	 */
	@Override
	public void setBackground(Color color) {
		super.setBackground(color);

		for (final Control child : getChildren()) {
			if (!(child instanceof PlotArea) && !(child instanceof Legend)) {
				child.setBackground(color);
			}
		}
	}

	/**
	 * Gets the background color in plot area. This method is identical with
	 * <tt>getPlotArea().getBackground()</tt>.
	 * 
	 * @return the background color in plot area
	 */
	public Color getBackgroundInPlotArea() {
		return plotArea.getBackground();
	}

	/**
	 * Sets the background color in plot area.
	 * 
	 * @param color the background color in plot area. If <tt>null</tt> is given,
	 *              default background color will be set.
	 * @exception IllegalArgumentException if given color is disposed
	 */
	public void setBackgroundInPlotArea(Color color) {
		if (color != null && color.isDisposed()) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		plotArea.setBackground(color);
	}

	/**
	 * Sets the state of chart orientation. The horizontal orientation means that X
	 * axis is horizontal as usual, while the vertical orientation means that Y axis
	 * is horizontal.
	 * 
	 * @param orientation the orientation which can be SWT.HORIZONTAL or
	 *                    SWT.VERTICAL
	 */
	@Override
	public void setOrientation(int orientation) {
		if (orientation == SWT.HORIZONTAL || orientation == SWT.VERTICAL) {
			this.orientation = orientation;
		}
		updateLayout();
	}

	/**
	 * Gets the state of chart orientation. The horizontal orientation means that X
	 * axis is horizontal as usual, while the vertical orientation means that Y axis
	 * is horizontal.
	 * 
	 * @return the orientation which can be SWT.HORIZONTAL or SWT.VERTICAL
	 */
	@Override
	public int getOrientation() {
		return orientation;
	}

	/**
	 * Enables compressing series. By default, compressing series is enabled, and
	 * normally there should be no usecase to disable it. However, if you suspect
	 * that something is wrong in compressing series, you can disable it to isolate
	 * the issue.
	 * 
	 * @param enabled true if enabling compressing series
	 */
	public void enableCompress(boolean enabled) {
		compressEnabled = enabled;
	}

	/**
	 * Gets the state indicating if compressing series is enabled.
	 * 
	 * @return true if compressing series is enabled
	 */
	public boolean isCompressEnabled() {
		return compressEnabled;
	}

	/**
	 * Suspends the update of chart appearance.
	 * 
	 * <p>
	 * By default, when the chart model is changed (e.g. adding new series or
	 * changing chart properties), the chart appearance is updated accordingly.
	 * <p>
	 * However, when doing a lot of changes in the chart model at once, it is
	 * inefficient that the update happens many times unnecessarily. In this case,
	 * you may want to defer the update until completing whole model changes in
	 * order to have better performance.
	 * <p>
	 * For example, suppose there is a chart having a large number of series, the
	 * following example code disables the update during changing the model.
	 * 
	 * <pre>
	 * try {
	 * 	// suspend update
	 * 	chart.suspendUpdate(true);
	 * 
	 * 	// do some changes for a large number of series
	 * 	for (ISeries series : chart.getSeriesSet().getSeries()) {
	 * 		series.enableStack(true);
	 * 	}
	 * } finally {
	 * 	// resume update
	 * 	chart.suspendUpdate(false);
	 * }
	 * </pre>
	 * 
	 * Note that the update has to be resumed right after completing the model
	 * changes in order to avoid showing an incompletely updated chart.
	 * 
	 * @param suspend true to suspend the update of chart appearance
	 */
	public void suspendUpdate(boolean suspend) {
		if (updateSuspended == suspend) {
			return;
		}
		updateSuspended = suspend;

		// make sure that chart is updated
		if (!suspend) {
			updateLayout();
			((SeriesSet) getSeriesSet()).updateStackAndRiserData();
		}
	}

	/**
	 * Gets the state indicating if the update of chart appearance is suspended.
	 * 
	 * @return true if the update of chart appearance is suspended
	 */
	public boolean isUpdateSuspended() {
		return updateSuspended;
	}

	/*
	 * @see Listener#handleEvent(Event)
	 */
	@Override
	public void handleEvent(Event event) {
		switch (event.type) {
		case SWT.Resize:
			updateLayout();
			redraw();
			break;
		default:
			break;
		}
	}

	/**
	 * Updates the layout of chart elements.
	 */
	public void updateLayout() {
		if (updateSuspended) {
			return;
		}

		if (legend != null) {
			legend.updateLayoutData();
		}

		if (title != null) {
			title.updateLayoutData();
		}

		if (axisSet != null) {
			axisSet.updateLayoutData();
		}

		layout();

		if (axisSet != null) {
			axisSet.refresh();
		}
	}

	/*
	 * @see Control#update()
	 */
	@Override
	public void update() {
		super.update();
		for (final Control child : getChildren()) {
			child.update();
		}
	}

	/*
	 * @see Widget#dispose()
	 */
	@Override
	public void dispose() {
		title.dispose();
		legend.dispose();
		axisSet.dispose();
		plotArea.dispose();
		super.dispose();
	}

	/*
	 * @see Control#redraw()
	 */
	@Override
	public void redraw() {
		super.redraw();
		for (final Control child : getChildren()) {
			child.redraw();
		}
	}

	/**
	 * Saves to file with given format.
	 * 
	 * @param filename the file name
	 * @param format   the format (SWT.IMAGE_*). The supported formats depend on OS.
	 */
	public void save(String filename, int format) {
		final Image image = new Image(Display.getDefault(), getBounds());

		renderOffscreenImage(image);

		final ImageData data = image.getImageData();
		final ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] { data };
		loader.save(filename, format);
		image.dispose();
	}

	/**
	 * Renders off-screen image.
	 * 
	 * @param image The image to render off-screen
	 */
	public void renderOffscreenImage(Image image) {
		final GC gc = new GC(image);

		gc.setFont(getFont());
		gc.setBackground(getBackground());
		gc.setForeground(getForeground());
		gc.fillRectangle(getBounds());

		paintControl(this, gc);

		for (final Control child : getChildren()) {
			final Rectangle r = child.getBounds();
			final Transform transform = new Transform(getDisplay());
			transform.translate(r.x, r.y);

			gc.setTransform(transform);
			gc.setFont(child.getFont());
			gc.setBackground(child.getBackground());
			gc.setForeground(child.getForeground());

			paintControl(child, gc);

			transform.dispose();
		}

		gc.dispose();
	}

	/**
	 * Paints with the given graphics context with the paint listeners registered in
	 * given control.
	 * 
	 * @param control The control
	 * @param gc      The graphics context
	 */
	private static void paintControl(Control control, GC gc) {
		for (final Listener listener : control.getListeners(SWT.Paint)) {
			if (listener instanceof TypedListener) {
				final EventListener eventListener = ((TypedListener) listener).getEventListener();
				if (eventListener instanceof PaintListener) {
					final Event e = new Event();
					e.type = SWT.Paint;
					e.display = control.getDisplay();
					e.widget = control;
					e.gc = gc;
					e.width = gc.getClipping().width;
					e.height = gc.getClipping().height;

					((PaintListener) eventListener).paintControl(new PaintEvent(e));
				}
			}
		}
	}
}
