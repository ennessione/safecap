package uk.ac.ncl.safecap.gui.trainconfig;

import java.util.Map.Entry;
import java.util.TreeMap;

import traintable.TDTable;
//import uk.ac.ncl.safecap.capacity.views.GenericGraph;
import traintable.TDTableRow;

public class SparseApproxSurface implements IDataSurface {
	private final TreeMap<Double, TreeMap<Double, Double>> values;

	public SparseApproxSurface(TDTable table) throws Exception {
		values = new TreeMap<>();

		int rowsize = -1;
		for (int i = 0; i < table.getRows().size(); i++) {
			final TDTableRow r = table.getRows().get(i);
			if (rowsize == -1) {
				rowsize = r.getValues().size();
			} else if (rowsize != r.getValues().size()) {
				throw new Exception("Invalid size for row #" + i + "; expect " + rowsize + ", actual " + r.getValues().size());
			}
		}

		for (int row = 1; row < table.getRows().size(); row++) {
			final TDTableRow r = table.getRows().get(row);
			final TreeMap<Double, Double> map = new TreeMap<>();
			values.put(getValueAt(table, 0, row), map);
			for (int col = 1; col < r.getValues().size(); col++) {
				map.put(getValueAt(table, col, 0), getValueAt(table, col, row));
			}
		}
	}

	@Override
	public double value(double x, double y) {
		final Entry<Double, TreeMap<Double, Double>> f = values.floorEntry(x);
		final Entry<Double, TreeMap<Double, Double>> c = values.ceilingEntry(x);
		if (f == c) {
			return value(f.getValue(), y);
		}

		final double vf = value(f.getValue(), y);
		final double vc = value(c.getValue(), y);
		final double dx = (vc - vf) / (c.getKey() - f.getKey());
		return vf + (x - f.getKey()) * dx;
	}

	private double value(TreeMap<Double, Double> map, double y) {
		final Entry<Double, Double> f = map.floorEntry(y);
		final Entry<Double, Double> c = map.ceilingEntry(y);
		if (f == c) {
			return f.getValue();
		}

		final double dy = (c.getValue() - f.getValue()) / (c.getKey() - f.getKey());
		return f.getValue() + (y - f.getKey()) * dy;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (double b = 0; b < 160000; b += 10000.0) {
			for (double a = 0; a < 55; a += 3.0) {
				final double v = value(b, a);
				sb.append(v);
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private double getValueAt(TDTable table, int col, int row) {
		return getReal(table.getRows().get(row).getValues().get(col));
	}

	private double getReal(String value) {
		return Double.parseDouble(value);
	}
}
