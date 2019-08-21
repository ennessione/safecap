package uk.ac.ncl.safecap.xmldata.parsing;

import uk.ac.ncl.safecap.xmldata.DataContext;

public interface IDataParser {
	String getName();

	void process(DataContext context, String filename) throws Exception;
}
