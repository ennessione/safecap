package uk.ac.ncl.safecap.xmldata.base;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataUtils;
import uk.ac.ncl.safecap.xmldata.IConceptMap;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;

public class SDAFileDataSource implements ISDADataProvider {
	private static Map<File, DataContext> cache = new HashMap<>();
	private DataContext context;
	private final File file;

	public SDAFileDataSource(File file) throws SDAImportException {
		this.file = file;
		if (cache.containsKey(file)) {
			context = cache.get(file);
		} else {
			getData(file);
			cache.put(file, context);
		}
	}

	public DataContext getContext() {
		return context;
	}

	private void getData(File file) throws SDAImportException {
		try {
			context = DataUtils.getDataContextFromFile2(file);
			// context.cookTypes();
			context.build();
		} catch (final Throwable e) {
			e.printStackTrace();
			throw new SDAImportException("Failed importing data from file " + file.getAbsolutePath() + ": " + e.getMessage());

		}
	}

	public String getSourceFile() {
		return context.getSourceFile();
	}

	@Override
	public XEnumType getEnum(String id) {
		return context.getTypeRegistry().getEnum(id);
	}

	@Override
	public Set<String> getEnums() {
		return context.getTypeRegistry().getEnums();
	}

	@Override
	public Set<String> getFunctionIds() {
		return context.getFunctionIds();
	}

	@Override
	public IXFunction getFunction(String name) {
		return context.getFunction(name);
	}

	@Override
	public Set<String> getEnumMembers(XEnumType type) {
		return context.getEnumMembers(type);
	}

	@Override
	public Set<String> getEnumMembers(String type) {
		return context.getEnumMembers(type);
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public String getConceptMapSource() {
		return context.getConceptMapSource();
	}

	@Override
	public IConceptMap getConceptMap() {
		return context.getConceptMap();
	}

	@Override
	public Collection<String> getExternalMap(String element) {
		return context.getExternalMap(element);
	}

	@Override
	public Collection<String> getExternalFileLocations() {
		return context.getExternalFileLocations();
	}
}
