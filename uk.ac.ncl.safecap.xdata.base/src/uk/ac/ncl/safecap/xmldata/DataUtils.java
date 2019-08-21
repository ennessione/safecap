package uk.ac.ncl.safecap.xmldata;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.xml.sax.SAXException;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

import uk.ac.ncl.safecap.misc.core.LocatedValue;
import uk.ac.ncl.safecap.xmldata.base.SDAImportException;
import uk.ac.ncl.safecap.xmldata.normalisation.Coercion;
import uk.ac.ncl.safecap.xmldata.normalisation.NormalisationPattern;
import uk.ac.ncl.safecap.xmldata.parsing.IDataParser;
import uk.ac.ncl.safecap.xmldata.parsing.SlidingParser;

public class DataUtils {
	public static JAXBContext jaxbContext;

	static {
		try {
			jaxbContext = JAXBContext.newInstance(DataContext.class, Pair.class, ValueList.class, LocatedValue.class);
		} catch (final JAXBException e) {
			e.printStackTrace();
			jaxbContext = null;
		}
	}

	private static Map<IFile, DataContext> dataContextCache = new HashMap<>(5);

	public static DataContext fetchDataContext(IFile file) throws DataConfigurationException {
		synchronized (dataContextCache) {
			if (dataContextCache.containsKey(file)) {
				return dataContextCache.get(file);
			} else {
				final DataContext dataContext = DataUtils.getDataContextFromFile(file.getLocation().toFile());
				if (dataContext != null) {
					// dataContext.cookTypes();
					dataContext.build();
					dataContextCache.put(file, dataContext);
				}
				return dataContext;
			}
		}
	}

	public static void clearCache(IFile file) {
		synchronized (dataContextCache) {
			dataContextCache.remove(file);
		}
	}

	public static DataContract readContract(Reader in, File file) throws DataConfigurationException, IOException {
		final DataContract contract = new DataContract();
		int line = 0;
		String tag = null;
		String[] parts;
		final CSVParser parser = new CSVParser(CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, '\0',
				CSVParser.DEFAULT_STRICT_QUOTES);
		final CSVReader reader = new CSVReader(in, 0, parser);
		try {
			while ((parts = reader.readNext()) != null) {
				if (parts.length > 0) {
					String kind = unquote(parts[0]).trim();
					if ("typedecomposition".equals(kind) && parts.length >= 5) {
						final String part4 = parts.length > 4 ? unquote(parts[4]) : null;
						contract.addTypeDecomposition(unquote(parts[1]), unquote(parts[2]), unquote(parts[3]), part4);
					} else if ("namespace".equals(kind) && parts.length >= 2) {
						contract.addNamespace(unquote(parts[1]));
					} else if ("tag".equals(kind) && parts.length >= 2) {
						tag = unquote(parts[1]);
					} else if ("genelement".equals(kind) && parts.length >= 6) {
						contract.addTemplateElement(unquote(parts[1]), unquote(parts[2]), unquote(parts[3]), unquote(parts[5]));
					} else if ("element".equals(kind) && parts.length >= 7) {
						contract.addConcreteElement(unquote(parts[6]), unquote(parts[1]), unquote(parts[2]), unquote(parts[3]),
								unquote(parts[4]), unquote(parts[5]),
								parts.length >= 8 && unquote(parts[7]).length() > 0 ? unquote(parts[7]) : tag);
					} else if ("date".equals(kind)) {

					} else if ("externalfileslocation".equals(kind)) {
						final String location = unquote(parts[1]);
						contract.addExternalMapLocation(location);
					} else if ("map".equals(kind) && parts.length >= 6) {
						final DataContractExternalMap mapElement = new DataContractExternalMap(unquote(parts[6]), unquote(parts[1]),
								unquote(parts[2]), unquote(parts[3]), unquote(parts[4]));
						contract.addExternalMapElement(mapElement);
					} else if ("name".equals(kind) && parts.length >= 2) {
						contract.setName(unquote(parts[1]));
					} else if ("conceptmap".equals(kind) && parts.length >= 2) {
						contract.setConceptMap(unquote(parts[1]));
					} else if ("versionmajor".equals(kind)) {

					} else if ("versionminor".equals(kind)) {

					} else if ("versionmicro".equals(kind)) {

					} else if ("comment".equals(kind)) {
					} else if ("enum".equals(kind) && parts.length >= 3) {
						final List<String> elements = new ArrayList<>();
						final String tname = unquote(parts[1]).trim();
						for (int i = 2; i < parts.length; i++) {
							final String el = unquote(parts[i]).trim();
							if (el.length() > 0) {
								elements.add(el);
							}
						}
						contract.addEnum(tname, elements);
					} else if ("split".equals(kind) && parts.length > 3) {
						final String base = unquote(parts[1]).trim();
						final String xpath = unquote(parts[2]).trim();

						if (base.length() == 0 || xpath.length() == 0) {
							throw new DataConfigurationException("Invalid split command in line " + line);
						}

						final List<String> subparts = new ArrayList<>(3);
						for (int i = 3; i < parts.length; i++) {
							final String v = unquote(parts[i]).trim();
							if (v.length() == 0) {
								break;
							}
							subparts.add(v);
						}

						if (subparts.size() < 2) {
							throw new DataConfigurationException("Must have at least two parts for split command in line " + line);
						}
						final DataContractSplit split = new DataContractSplit(xpath, base, subparts);
						contract.addDataContractSplit(split);
					} else if ("coerce".equals(kind) && parts.length >= 2) {
						final String type = unquote(parts[1]).trim();
						final Map<String, String> map = new HashMap<>();
						while ((parts = reader.readNext()) != null) {
							if (parts.length > 0) {
								kind = unquote(parts[0]).trim();
								if ("end-coerce".equals(kind)) {
									break;
								} else if ("map".equals(kind) && parts.length >= 3) {
									map.put(unquote(parts[1]).trim(), unquote(parts[2]).trim());
								} else {
									throw new DataConfigurationException("Syntax error in line " + line + ": expect end-coerce or map");
								}
							}
						}

						if (map.isEmpty()) {
							throw new DataConfigurationException("Empty coerce construct in line " + line);
						}

						final Coercion c = new Coercion(type, map);
						contract.addNormaliser(c);
					} else if ("patch".equals(kind) && parts.length >= 4) {
						final String type = unquote(parts[1]).trim();
						final String pattern = unquote(parts[2]).trim();
						final String template = unquote(parts[3]).trim();
						try {
							Pattern.compile(pattern);
						} catch (final Exception e) {
							throw new DataConfigurationException("Invalid pattern for patch in line " + line);
						}
						contract.addNormaliser(new NormalisationPattern(type, pattern, template));
					} else if ("persistent".equals(kind)) {
						if (file != null) {
							contract.setPersistContractFile(file);
						}
					} else if ("".equals(kind)) {
						// ignore empty line
					} else {
						throw new DataConfigurationException("Syntax error in line " + line + ": " + Arrays.toString(parts));
					}
				}
				line++;
			}
		} finally {
			reader.close();
		}

		return contract;
	}

	public static String unquote(String d) {
		if (d == null) {
			return null;
		} else if (d.startsWith("\"") && d.endsWith("\"")) {
			return d.substring(1, d.length() - 1).trim();
		} else {
			return d.trim();
		}
	}

	public static DataContext getDataContextFromFile(File file) {
		try {
			final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (DataContext) jaxbUnmarshaller.unmarshal(file);
		} catch (final JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static DataContext getDataContextFromFile2(File file) throws SDAImportException {
		try {
			final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (DataContext) jaxbUnmarshaller.unmarshal(file);
		} catch (final JAXBException e) {
			throw new SDAImportException(e.getMessage());
		}
	}

	public static DataContext getDataContextFromFile2(InputStream is) throws SDAImportException {
		try {
			final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (DataContext) jaxbUnmarshaller.unmarshal(is);
		} catch (final JAXBException e) {
			throw new SDAImportException(e.getMessage());
		}
	}

	public static void saveDataContextToFile(DataContext dataContext, IFile file) throws JAXBException, CoreException, IOException {
		final ByteArrayOutputStream os = new ByteArrayOutputStream(64192);

		saveDataContextToStream(dataContext, os);

		file.setContents(new ByteArrayInputStream(os.toByteArray()), IResource.FORCE, null);
	}
	
	public static String saveDataContextToString(DataContext dataContext)  {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			saveDataContextToStream(dataContext, out);
			return out.toString( StandardCharsets.UTF_8.name());
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}	

	public static void saveDataContextToStream(DataContext dataContext, OutputStream os) throws JAXBException, CoreException, IOException {
		final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://www.w3.org/2001/XMLSchema-instance");
		jaxbMarshaller.marshal(dataContext, os);
		os.flush();
	}

	public static DataContext importDataContextSlidingParser(String filename)
			throws SAXException, IOException, ParserConfigurationException {
		DataContext context;
		try {
			context = new DataContext();
			final SlidingParser parser = new SlidingParser(context);
			parser.parse(filename);
		} catch (final Throwable e1) {
			// e1.printStackTrace();
			throw new IOException("Failed parsing and processing xml file: " + e1.getMessage());
		}
		try {
			context.build();
		} catch (final DataConfigurationException e) {
			throw new IOException("Failed parsing and processing xml file: " + e.getMessage());
		}
		return context;
	}

	public static DataContext importDataContext(IDataParser parser, String filename) throws Exception {
		final DataContext context = new DataContext();
		parser.process(context, filename);
		try {
			context.build();
		} catch (final DataConfigurationException e) {
			// e.printStackTrace();
		}
		return context;
	}
//
//	public static DataContext importDataContext(String filename)
//			throws SAXException, IOException, ParserConfigurationException {
//		DataContext context;
//		try {
//			context = new DataContext();
//			ParserBase parser = new TwoLevelParser(context);
//			parser.parse(filename);
//		} catch (Exception e) {
//			// System.err.println("TwoLevelParser parser failed, trying SlidingParser");
//			try {
//				context = new DataContext();
//				SlidingParser parser = new SlidingParser(context);
//				parser.parse(filename);
//			} catch (Throwable e1) {
//				e1.printStackTrace();
//				throw new IOException("Failed parsing and processing xml file");
//			}
//		}
//		if (context != null) {
//			try {
//				context.build();
//			} catch (DataConfigurationException e) {
//				e.printStackTrace();
//			}
//		}
//		return context;
//	}
}
