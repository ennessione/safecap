package uk.ac.ncl.safecap.scitus.stat;

public class Run2DRecord {
	public enum XAXIS {
		TIME, DISTANCE, BOTH
	};

	public static final String MAIN = "main";
	public static final String MAINT = "main(t)";
	public static final String DIST = "dist";
	private static final int INITIAL_SIZE = 5000;
	private final String kind;
	private final String name;
	private XAXIS xaxis = XAXIS.DISTANCE;
	private double[] data;
	private int cursor = 0;
	private int max_size = INITIAL_SIZE;
	private int lineWidth = 2;
	private boolean dashed = false;
	private boolean show = true;

	public Run2DRecord(String kind, String name) {
		this.kind = kind;
		this.name = name;
		data = new double[INITIAL_SIZE];
	}

	public XAXIS getXAxis() {
		return xaxis;
	}

	public void setXAxis(XAXIS xaxis) {
		this.xaxis = xaxis;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public boolean isDashed() {
		return dashed;
	}

	public void setDashed(boolean dashed) {
		this.dashed = dashed;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public String getName() {
		return name;
	}

	public String getKind() {
		return kind;
	}

	public double[] getData() {
		return data;
	}

	public int getCursor() {
		return cursor;
	}

	public void insert(double value) {
		if (cursor == max_size) {
			max_size *= 2;
			data = (double[]) resizeArray(data, max_size);
		}
		data[cursor] = value;
		cursor++;
	}

	private static Object resizeArray(Object oldArray, int newSize) {
		final int oldSize = java.lang.reflect.Array.getLength(oldArray);
		final Class<?> elementType = oldArray.getClass().getComponentType();
		final Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);
		final int preserveLength = Math.min(oldSize, newSize);
		if (preserveLength > 0) {
			System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
		}
		return newArray;
	}

	public Run2DRecord truncate() {
		data = (double[]) resizeArray(data, cursor);
		return this;
	}

}
