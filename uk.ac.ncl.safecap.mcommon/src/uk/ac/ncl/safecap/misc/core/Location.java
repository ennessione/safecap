package uk.ac.ncl.safecap.misc.core;

public class Location {
	private int line = -1;
	private int column = -1;
	private int offset = -1;
	private int length = -1;

	public Location() {
	}

	public Location(int line, int column, int offset, int length) {
		this.line = line;
		this.column = column;
		this.offset = offset;
		this.length = length;
	}

	public Location(int line, int column, int offset) {
		this.line = line;
		this.column = column;
		this.offset = offset;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "@" + offset + ":" + length;
	}

}
