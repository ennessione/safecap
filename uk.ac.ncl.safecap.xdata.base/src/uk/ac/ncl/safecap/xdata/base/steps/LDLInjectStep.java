package uk.ac.ncl.safecap.xdata.base.steps;

import java.io.File;
import java.io.FileNotFoundException;

import uk.ac.ncl.safecap.xmldata.DataConfigurationException;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataException;
import uk.ac.ncl.safecap.xmldata.ldl.LdlImportException;
import uk.ac.ncl.safecap.xmldata.ldl.ParseLdl;

public class LDLInjectStep {

	public static void execute(DataContext data, String filename)
			throws FileNotFoundException, DataException, DataConfigurationException, LdlImportException {

		if (data == null || filename == null) {
			return;
		}

		final File inFile = new File(filename);

		process(data, inFile);

	}

	public static void process(DataContext data, File inFile)
			throws DataException, DataConfigurationException, FileNotFoundException, LdlImportException {
		if (inFile.isFile() && inFile.exists() && inFile.canRead()) {
			final DataContext newData = ParseLdl.INSTANCE.parseFile(inFile);
			data.injectData(newData);
		} else {
			throw new DataException("Invalid file name: " + inFile);
		}
	}

}
