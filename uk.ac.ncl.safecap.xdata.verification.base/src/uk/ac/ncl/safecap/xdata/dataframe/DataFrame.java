package uk.ac.ncl.safecap.xdata.dataframe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.safecap.misc.StringUtil;

public class DataFrame {
	private List<String> columnNames;
	private List<DataFrameRow> rows;
	
	public DataFrame() {
		columnNames = new ArrayList<>();
		columnNames.add("domain");
		rows = new ArrayList<>();
	}
	
	public int getColumnNumber() {
		return columnNames.size();
	}
	
	public int getRowNumber() {
		return rows.size();
	}	
	
	public void addColumn(String name) {
		if (name == null || columnNames.contains(name))
			throw new IllegalArgumentException("Invalid column name");
		columnNames.add(name);
	}
	
	public void addRow(DataFrameRow row) {
		rows.add(row);
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public List<DataFrameRow> getRows() {
		return rows;
	}
	
	public void toCSV(File file) throws FileNotFoundException {
		try (PrintStream ps = new PrintStream(file)) {
			ps.println(StringUtil.ppList(columnNames, ", "));
			for(DataFrameRow r: rows)
				r.pp(ps);
		}
	}
}
