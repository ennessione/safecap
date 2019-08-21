package uk.ac.ncl.safecap.xdata.dataframe;

import java.io.PrintStream;
import java.util.List;

import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xmldata.base.SDAImportException;

public class DataFrameRow {
	private Object domain;
	private List<Object> values;

	private SDAContext rowContext = null;
	
	public DataFrameRow(Object domain, List<Object> values) {
		super();
		this.domain = domain;
		this.values = values;
	}

	public SDAContext getRowContext(SDADataFrame frame) throws SDAImportException {
		if (rowContext == null) {
			rowContext = new SDAContext(frame.getContext());
			rowContext.getAggregator().insertSource(new RowDataProvider(frame, this), true);	
		}
		return rowContext;
	}	
	
	public Object getDomain() {
		return domain;
	}

	public List<Object> getValues() {
		return values;
	}
	
	public void addValue(Object value) {
		values.add(value);
	}
	
	public void pp(PrintStream ps) {
		ps.print(domain.toString());
		
		for(Object o: values) {
			ps.print(",");
			String s = o.toString();
			s = s.replaceAll("\\,", "_comma_");
			ps.print(s);
				
		}
		
		ps.println();
	}
}
