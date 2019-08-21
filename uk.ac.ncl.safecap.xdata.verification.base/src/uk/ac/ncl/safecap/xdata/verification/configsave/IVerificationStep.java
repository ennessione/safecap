package uk.ac.ncl.safecap.xdata.verification.configsave;

import java.io.Serializable;
import java.util.Date;

import uk.ac.ncl.safecap.xmldata.DataContext;

public interface IVerificationStep extends Serializable {
	Date getDate();

	boolean hasSchema();

	boolean hasControlTable();

	boolean hasResult();

	boolean hasReport();

	DataContext getSchema();

	DataContext getControlTable();

	String getSchemaHash();

	String getControlTableHash();

	String getSchemaFile();

	String getControlTableFile();

	String getInterlocking();

	ReportResult getResult();

	byte[] getReport();

	void prepareToSave();
}
