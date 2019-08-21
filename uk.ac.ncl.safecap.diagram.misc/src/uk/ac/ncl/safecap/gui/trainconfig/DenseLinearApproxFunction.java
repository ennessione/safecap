package uk.ac.ncl.safecap.gui.trainconfig;

import traintable.TDTable;
import traintable.TDTableRow;
//import uk.ac.ncl.safecap.capacity.views.GenericGraph;

public class DenseLinearApproxFunction implements IDataFunction {

	private final double[] values;
	private double min, max;
	private final double delta;
	private final double fill;

	public DenseLinearApproxFunction(TDTable table, int size, double fill) throws Exception {
		this.fill = fill;
		min = Double.MAX_VALUE;
		max = Double.MIN_VALUE;

		int row = 1;
		for (final TDTableRow r : table.getRows()) {
			if (r.getValues().size() != 2) {
				throw new Exception("Invalid size for row #" + row + "; expect 2, actual " + r.getValues().size());
			}
			final double a = getReal(r.getValues().get(0));
			if (a > max) {
				max = a;
			}
			if (a < max) {
				min = a;
			}
			row++;
		}

		delta = (max - min) / size;

		values = new double[size + 1];
		TDTableRow r = table.getRows().get(0);
		double a_prev = getReal(r.getValues().get(0));
		double b_prev = getReal(r.getValues().get(1));
		int index_prev = (int) Math.round((a_prev - min) / delta);
		values[index_prev] = b_prev;
		for (int i = 1; i < table.getRows().size(); i++) {
			r = table.getRows().get(i);
			final double a = getReal(r.getValues().get(0));
			final double b = getReal(r.getValues().get(1));
			final int index = (int) Math.round((a - min) / delta);
			values[index] = b;
			final double k = (b - b_prev) / (a - a_prev);
			for (int j = index_prev + 1; j < index; j++) {
				final double x = delta * j;
				final double y = b_prev + k * (x - a_prev);
				values[j] = y;
			}

			a_prev = a;
			b_prev = b;
			index_prev = index;
		}

	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public double getDelta() {
		return delta;
	}

//	public void testGraph(String title, String xAxis, String yAxis) {
//		GenericGraph graph = new GenericGraph(title, values, min, max, delta);
//		graph.setxAxis(xAxis);
//		graph.setyAxis(yAxis);
//		graph.create();
//	}

	@Override
	public double value(double x) {
		if (x < min || x > max) {
			return fill;
		}

		final int index = (int) Math.round((x - min) / delta);
		return values[index];
	}

	private double getReal(String value) {
		return Double.parseDouble(value);
	}
}
