package uk.ac.ncl.safecap.common.report;

public class SRGrid extends SRContainer {
	public static final String PADDING = "padding";
	public static final String BORDER = "border";
	private final int columns;

	public SRGrid(int columns) {
		super();
		this.columns = columns;
	}

	public int getColumns() {
		return columns;
	}
}
