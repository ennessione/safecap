package uk.ac.ncl.safecap.xdata.base.steps;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import uk.ac.ncl.safecap.xmldata.DataConfigurationException;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataException;
import uk.ac.ncl.safecap.xmldata.DataUtils;
import uk.ac.ncl.safecap.xmldata.parsing.IDataParser;

public class DataInjectStep {

	public static void execute(DataContext data, String filename, IDataParser parser)
			throws DataException, DataConfigurationException, SAXException, IOException, ParserConfigurationException {

		if (filename == null || data == null) {
			return;
		}

		final File inFile = new File(filename);

		process(data, inFile, parser, 5);
	}

	private static void process(DataContext data, File inFile, IDataParser parser, int depth)
			throws DataException, DataConfigurationException, SAXException, IOException, ParserConfigurationException {
		try {
			if (inFile.isFile() && inFile.exists() && inFile.canRead()) {
				injectFile(data, inFile.getAbsolutePath(), parser);
			} else if (inFile.isDirectory() && depth > 0) {
				for (final File f : inFile.listFiles(XmlFilenameFilter.INSTANCE)) {
					process(data, f, parser, depth - 1);
				}
			} else {
				throw new DataException("Invalid file name: " + inFile);
			}
		} catch (final Exception e) {
			throw new DataException("Parse failed: " + e.getMessage());
		}
	}

	private static void injectFile(DataContext data, String filename, IDataParser parser) throws Exception {
		final DataContext inject = DataUtils.importDataContext(parser, filename);
		if (inject == null) {
			throw new DataException("XML parsing failed");
		}

		data.injectData(inject);

	}

	private static class XmlFilenameFilter implements FilenameFilter {
		static final XmlFilenameFilter INSTANCE = new XmlFilenameFilter();

		@Override
		public boolean accept(File arg0, String arg1) {
			return arg1 != null && arg1.endsWith(".xml");
		}

	}

}
