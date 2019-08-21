/*******************************************************************************
 * Copyright (c) 2008-2011 SWTChart project. All rights reserved.
 * 
 * This code is distributed under the terms of the Eclipse Public License v1.0
 * which is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.swtchart.internal.compress;

/**
 * A Compressor.
 */
public interface ICompress {

	/**
	 * Gets the compressed X series
	 * 
	 * @return the compressed X series
	 */
	double[] getCompressedXSeries();

	/**
	 * Gets the compressed Y series
	 * 
	 * @return the compressed Y series
	 */
	double[] getCompressedYSeries();

	/**
	 * Gets the compressed series indexes
	 * 
	 * @return the compressed series indexes
	 */
	int[] getCompressedIndexes();

	/**
	 * Sets X series which have to be sorted.
	 * 
	 * @param xSeries the X series
	 */
	void setXSeries(double[] xSeries);

	/**
	 * sets the Y series
	 * 
	 * @param ySeries the Y series
	 */
	void setYSeries(double[] ySeries);

	/**
	 * Ignores the points which are in the same grid as the previous point.
	 * 
	 * @param config the configuration for compression
	 * @return true if the compression succeeds
	 */
	boolean compress(CompressConfig config);

}