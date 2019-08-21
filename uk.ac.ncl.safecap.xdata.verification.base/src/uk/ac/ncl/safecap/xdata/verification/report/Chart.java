package uk.ac.ncl.safecap.xdata.verification.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chart<T> {

	private final String valueAxis;
	private final String objectAxis;
	private final String[] legend;
	private List<T> order;
	private Map<T, double[]> data;

	public Chart(String valueAxis, String objectAxis, String... parts) {
		this.valueAxis = valueAxis;
		this.objectAxis = objectAxis;
		data = new HashMap<>();
		order = new ArrayList<>();
		legend = parts;
	}

	public void cutTailOff(double rate) {
		if (rate > 0 && rate < 0.9) {
			final int retain = (int) (order.size() * (1 - rate));
			for (int i = order.size() - 1; i > retain; i--) {
				data.remove(order.get(i));
				order.remove(i);
			}
		}
	}

	public String[] getLegend() {
		return legend;
	}

	public void compress(int points) {
		if (data.size() >= points * 2) {
			final int skip = data.size() / points + 1;
			if (skip > 1) {
				final Map<T, double[]> newdata = new HashMap<>();
				final List<T> neworder = new ArrayList<>();
				for (int i = 0; i < data.size(); i += skip) {
					newdata.put(order.get(i), data.get(order.get(i)));
					neworder.add(order.get(i));
				}
				data = newdata;
				order = neworder;
			}
		}
	}

	public double[] getDataPointsFor(int index) {
		final double[] result = new double[data.size()];
		int i = 0;
		for (final T k : order) {
			result[i++] = data.get(k)[index];
		}
		return result;
	}

	public List<T> getOrder() {
		return order;
	}

	public void set(T object, double... value) {
		if (!order.contains(object)) {
			assert value.length == legend.length;
			data.put(object, value);
			order.add(object);
		}
	}

	public void set(T object, String key, double value) {
		assert getLegendIndex(key) != -1;
		if (!order.contains(object)) {
			final double[] array = new double[legend.length];
			array[getLegendIndex(key)] = value;
			data.put(object, array);
			order.add(object);
		} else {
			final double[] array = data.get(object);
			array[getLegendIndex(key)] = value;
		}
	}

	public void add(T object, double... value) {
		if (!order.contains(object)) {
			set(object, value);
		} else {
			assert value.length == legend.length;
			final double[] old = data.get(object);
			for (int i = 0; i < legend.length; i++) {
				old[i] += value[i];
			}
			data.put(object, old);
		}
	}

	public double getMin() {
		double min = Double.MAX_VALUE;
		for (final double[] da : data.values()) {
			for (final double d : da) {
				if (d < min) {
					min = d;
				}
			}
		}

		return min;
	}

	public double getMax() {
		double max = Double.MIN_VALUE;
		for (final double[] da : data.values()) {
			for (final double d : da) {
				if (d > max) {
					max = d;
				}
			}
		}

		return max;
	}

	public double getMinSum() {
		double min = Double.MAX_VALUE;
		for (final double[] da : data.values()) {
			double sumda = 0;
			for (final double d : da) {
				sumda += d;
			}
			if (sumda < min) {
				min = sumda;
			}
		}
		return min;
	}

	public double getMaxSum() {
		double max = Double.MIN_VALUE;
		for (final double[] da : data.values()) {
			double sumda = 0;
			for (final double d : da) {
				sumda += d;
			}
			if (sumda > max) {
				max = sumda;
			}
		}
		return max;
	}

	public double getMinFor(int index) {
		double min = Double.MAX_VALUE;
		for (final double[] da : data.values()) {
			if (da[index] < min) {
				min = da[index];
			}
		}

		return min;
	}

	public double getMaxFor(int index) {
		double max = Double.MIN_VALUE;
		for (final double[] da : data.values()) {
			if (da[index] > max) {
				max = da[index];
			}
		}

		return max;
	}

	public double getSumAt(T key) {
		double sum = 0;
		for (final double d : data.get(key)) {
			sum += d;
		}
		return sum;
	}

	public double getSumFor(int index) {
		double sum = 0;
		for (final T k : order) {
			sum += data.get(k)[index];
		}
		return sum;
	}

	public double getMeanFor(int index) {
		return getSumFor(index) / order.size();
	}

	public double getStdDevFor(int index) {
		final double mean = getMeanFor(index);
		double sqrdiffsum = 0;
		for (final T k : order) {
			sqrdiffsum += (data.get(k)[index] - mean) * (data.get(k)[index] - mean);
		}
		return Math.sqrt(sqrdiffsum / (order.size() - 1));
	}

	public double[] getValue(T key) {
		if (data.containsKey(key)) {
			return data.get(key);
		} else {
			return null;
		}
	}

	public String getValueAxis() {
		return valueAxis;
	}

	public String getObjectAxis() {
		return objectAxis;
	}

	public int getLegendIndex(String value) {
		for (int i = 0; i < legend.length; i++) {
			if (legend[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

}
