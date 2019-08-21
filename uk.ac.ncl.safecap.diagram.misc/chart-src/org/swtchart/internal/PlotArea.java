/*******************************************************************************
 * Copyright (c) 2008-2011 SWTChart project. All rights reserved.
 *
 * This code is distributed under the terms of the Eclipse Public License v1.0
 * which is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.swtchart.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.swtchart.Chart;
import org.swtchart.IAxis;
import org.swtchart.IBarSeries;
import org.swtchart.ICustomPaintListener;
import org.swtchart.ILineSeries;
import org.swtchart.IPlotArea;
import org.swtchart.ISeries;
import org.swtchart.ISeriesSet;
import org.swtchart.internal.series.Series;
import org.swtchart.internal.series.SeriesSet;

/**
 * Plot area to draw series and grids.
 */
public class PlotArea extends Composite implements PaintListener, IPlotArea {

	/** the chart */
	protected Chart chart;

	/** the set of plots */
	protected SeriesSet seriesSet;

	/** the image cache */
	private Image imageCache;

	/** the state indicating if image cache has to be updated */
	private boolean updateImageCache;

	/** the custom paint listeners */
	List<ICustomPaintListener> paintListeners;

	/** the default background color */
	private static final int DEFAULT_BACKGROUND = SWT.COLOR_WHITE;

	/**
	 * Constructor.
	 *
	 * @param chart the chart
	 * @param style the style
	 */
	public PlotArea(Chart chart, int style) {
		super(chart, style | SWT.NO_BACKGROUND);

		this.chart = chart;

		seriesSet = new SeriesSet(chart);
		updateImageCache = true;
		paintListeners = new ArrayList<>();

		setBackground(Display.getDefault().getSystemColor(DEFAULT_BACKGROUND));
		addPaintListener(this);
	}

	/**
	 * Gets the set of series.
	 *
	 * @return the set of series
	 */
	public ISeriesSet getSeriesSet() {
		return seriesSet;
	}

	/*
	 * @see Control#setBounds(int, int, int, int)
	 */
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		((SeriesSet) getSeriesSet()).compressAllSeries();
	}

	/*
	 * @see Control#setBackground(Color)
	 */
	@Override
	public void setBackground(Color color) {
		if (color == null) {
			super.setBackground(Display.getDefault().getSystemColor(DEFAULT_BACKGROUND));
		} else {
			super.setBackground(color);
		}
	}

	/*
	 * @see IPlotArea#addCustomPaintListener(ICustomPaintListener)
	 */
	@Override
	public void addCustomPaintListener(ICustomPaintListener listener) {
		paintListeners.add(listener);
	}

	/*
	 * @see IPlotArea#removeCustomPaintListener(ICustomPaintListener)
	 */
	@Override
	public void removeCustomPaintListener(ICustomPaintListener listener) {
		paintListeners.remove(listener);
	}

	/*
	 * @see PaintListener#paintControl(PaintEvent)
	 */
	@Override
	public void paintControl(PaintEvent e) {
		if (updateImageCache) {
			final Point p = getSize();
			if (imageCache != null && !imageCache.isDisposed()) {
				imageCache.dispose();
			}
			imageCache = new Image(Display.getCurrent(), p.x, p.y);
			final GC gc = new GC(imageCache);

			// draw the plot area background
			gc.setBackground(getBackground());
			gc.fillRectangle(0, 0, p.x, p.y);

			// draw grid
			for (final IAxis axis : chart.getAxisSet().getAxes()) {
				((Grid) axis.getGrid()).draw(gc, p.x, p.y);
			}

			// draw behind series
			final GC prevGC = e.gc;
			e.gc = gc;
			for (final ICustomPaintListener listener : paintListeners) {
				if (listener.drawBehindSeries()) {
					listener.paintControl(e);
				}
			}

			// draw series. The line series should be drawn on bar series.
			for (final ISeries series : chart.getSeriesSet().getSeries()) {
				if (series instanceof IBarSeries) {
					((Series) series).draw(gc, p.x, p.y);
				}
			}
			for (final ISeries series : chart.getSeriesSet().getSeries()) {
				if (series instanceof ILineSeries) {
					((Series) series).draw(gc, p.x, p.y);
				}
			}

			// draw over series
			for (final ICustomPaintListener listener : paintListeners) {
				if (!listener.drawBehindSeries()) {
					listener.paintControl(e);
				}
			}
			e.gc = prevGC;

			gc.dispose();
			updateImageCache = false;
		}
		e.gc.drawImage(imageCache, 0, 0);
	}

	/*
	 * @see Control#update()
	 */
	@Override
	public void update() {
		super.update();
		updateImageCache = true;
	}

	/*
	 * @see Control#redraw()
	 */
	@Override
	public void redraw() {
		super.redraw();
		updateImageCache = true;
	}

	/*
	 * @see Widget#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		seriesSet.dispose();
		if (imageCache != null && !imageCache.isDisposed()) {
			imageCache.dispose();
		}
	}
}
