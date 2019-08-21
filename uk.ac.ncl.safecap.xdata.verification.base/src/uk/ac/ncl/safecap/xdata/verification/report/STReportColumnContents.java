package uk.ac.ncl.safecap.xdata.verification.report;

import uk.ac.ncl.safecap.common.report.SRPart;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ColumnModel;

public abstract class STReportColumnContents {
	public SRPart getColumnBody(ColumnModel column, Object context) {
		return null;
	}

	public int getPartNumber(ColumnModel column, Object context) {
		return 1;
	}
	
	public SRPart getColumnHeader(ColumnModel column, Object context) {
		return null;
	}

	public SRPart getPartHeader(ColumnModel column, int index, Object context) {
		return null;
	}

	public SRPart getPartBody(ColumnModel column, int index, Object context) {
		return getColumnBody(column, context);
	}

	public SRPart getColumnFooter(ColumnModel column, Object context) {
		return null;
	}

}