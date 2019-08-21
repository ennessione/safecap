package uk.ac.ncl.safecap.xdata.verification.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.sapphire.modeling.Path;

import uk.ac.ncl.safecap.common.MD5Checksum;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.xdata.verification.DataReference;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionContainer;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.IConceptMap;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.base.ErrorLogStaged;
import uk.ac.ncl.safecap.xmldata.base.ISDADataProvider;
import uk.ac.ncl.safecap.xmldata.base.SDAAggregatingProvider;
import uk.ac.ncl.safecap.xmldata.base.SDAFileDataSource;
import uk.ac.ncl.safecap.xmldata.base.SDAImportException;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;

public class SDAContext extends ErrorLogStaged implements ISDADataProvider {
	public static final SDAContext EMPTY = new SDAContext();
	private final Map<DataReference, ISDADataProvider> dataCache;
	private final Map<DataReference, TransitionContainer> modelCache;
	private final SDAAggregatingProvider aggregator;
	private final SDARuntimeExecutionContext rootRuntimeContext;
	private final RootCatalog catalog;
	private String fingerprint;

	private SDAContext() {
		super();
		catalog = null;
		dataCache = new HashMap<>();
		modelCache = new HashMap<>();
		aggregator = new SDAAggregatingProvider();
		rootRuntimeContext = makeRuntimeModel(true);
	}

	public SDAContext(RootCatalog catalog) throws SDAImportException {
		super();
		// start("Data extraction");
		dataCache = new HashMap<>();
		modelCache = new HashMap<>();
		aggregator = new SDAAggregatingProvider();
		this.catalog = catalog;
		rootRuntimeContext = makeRuntimeModel(false);
		refreshDataReferences();
		rootRuntimeContext.build(this);
	}

	public SDAContext(ISDADataProvider base) {
		super();
		dataCache = new HashMap<>();
		modelCache = new HashMap<>();
		aggregator = new SDAAggregatingProvider();
		this.catalog = null;
		rootRuntimeContext = makeRuntimeModel(false);
		addDataReference(base);
		rootRuntimeContext.build(this);
	}	
	
	public SDAAggregatingProvider getAggregator() {
		return aggregator;
	}

	public String getFingerprint() {
		if (fingerprint != null) {
			return fingerprint;
		}

		final ISDADataProvider provider = getProvider("signalling");
		if (provider != null) {
			final XEnumType type = provider.getEnum("Route");
			if (type != null) {
				final StringBuilder sb = new StringBuilder();
				final List<String> names = new ArrayList<>(provider.getEnumMembers("Route"));
				Collections.sort(names);
				for (final String s : names) {
					sb.append(s);
				}
				fingerprint = MD5Checksum.getMD5TextChecksum(sb.toString());
				return fingerprint;
			}
		}

		return null;
	}

	public String getFingerprintName() {
		final ISDADataProvider provider = getProvider("signalling");
		if (provider instanceof SDAFileDataSource) {
			final SDAFileDataSource fs = (SDAFileDataSource) provider;
			return new File(fs.getSourceFile()).getName();
		} else if (provider instanceof DataContext) {
			final DataContext fs = (DataContext) provider;
			return new File(fs.getSourceFile()).getName();
		} else {
			return "unknown";
		}
	}

	public ISDADataProvider getProvider(String name) {
		for (final ISDADataProvider provider : aggregator.getProviders()) {
			if (name.equals(provider.getName())) {
				return provider;
			}
		}

		return null;
	}

	public ISDADataProvider getFunctionOrigin(String function) {
		return aggregator.getFunctionOrigin(function);
	}

	public ISDADataProvider getTypeOrigin(String type) {
		return aggregator.getTypeOrigin(type);
	}

	protected SDARuntimeExecutionContext makeRuntimeModel(boolean build) {
		return new SDARuntimeExecutionContext(this, build);
	}

	public Collection<TransitionContainer> getTransitionContainers() {
		return modelCache.values();
	}

	public Collection<DataReference> getTransitionReferences() {
		return modelCache.keySet();
	}

	public TransitionContainer getTransitionContainer(DataReference ref) {
		return modelCache.get(ref);
	}

	public SDARuntimeExecutionContext getRootRuntimeContext() {
		return rootRuntimeContext;
	}

	public void refreshDataReferences() throws SDAImportException {
		// long start = System.currentTimeMillis();
		fingerprint = null;
		for (final DataReference ref : catalog.getReferences()) {
			try {
				if (ref.getObject().empty()) {
					if (ref.getPath().empty()) {
						return;
					}
					if (isModelReference(ref.getPath().content())) {
						addModelReference(ref);
					} else {
						addDataReference(ref);
					}
				} else {
					final Object obj = ref.getObject().content();
					if (obj instanceof ISDADataProvider) {
						addDataReference(ref);
					} else if (obj instanceof TransitionContainer) {
						addModelReference(ref);
					}
				}
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}

		rootRuntimeContext.build(this);

		// System.out.println("SDAContext: refresh time on is " +
		// (System.currentTimeMillis() - start));

	}

	private boolean isModelReference(Path content) {
		return SafecapConstants.TRANSITIONS_EXTENSION.equals(content.getFileExtension());
	}

	private void addDataReference(DataReference reference) throws SDAImportException {
		synchronized (this) {
			// System.out.println("SDAContext: add " + reference.getId());
			if (dataCache.containsKey(reference)) {
				final ISDADataProvider source = dataCache.get(reference);
				aggregator.deleteSource(source);
				final ISDADataProvider newSource = getDataSourceFrom(reference);
				aggregator.insertSource(newSource, false);
				dataCache.put(reference, newSource);
			} else {
				final ISDADataProvider newSource = getDataSourceFrom(reference);
				if (newSource != null) {
					aggregator.insertSource(newSource, false);
					dataCache.put(reference, newSource);
				}
			}
		}
	}
	
	public void addDataReference(ISDADataProvider source) {
		aggregator.insertSource(source, false);
	}	

	private void addModelReference(DataReference reference) throws SDAImportException {
		synchronized (this) {
			final TransitionContainer newSource = getModelSourceFrom(reference);
			if (newSource != null) {
				modelCache.put(reference, newSource);
				if (newSource.getSDAContext() == null) {
					newSource.setSDAContext(catalog, this);
				}
			}

		}
	}

	private ISDADataProvider getDataSourceFrom(DataReference reference) throws SDAImportException {
		if (reference.getObject().empty() && !reference.getPath().empty()) {
			final File file = getFile(reference);
			if (file == null) {
				throw new SDAImportException("Invalid path in data source");
			}
			return new SDAFileDataSource(file);
		} else if (!reference.getObject().empty() && reference.getPath().empty()) {
			if (reference.getObject().content() instanceof ISDADataProvider) {
				return (ISDADataProvider) reference.getObject().content();
			}
			return null;
		} else {
			throw new SDAImportException("Unsupported data source kind");
		}
	}

	private TransitionContainer getModelSourceFrom(DataReference reference) throws SDAImportException {
		if (reference.getObject().empty() && !reference.getPath().empty()) {
			final File file = getFile(reference);
			if (file != null) {
				return VerificationBasePlugin.getTransitionCache().get(file);
			}
			return null;
		} else if (reference.getObject().empty() && !reference.getPath().empty()) {
			if (reference.getObject().content() instanceof TransitionContainer) {
				return (TransitionContainer) reference.getObject().content();
			}
			return null;
		} else {
			throw new SDAImportException("Unsupported data source kind");
		}
	}

	private File getFile(DataReference reference) {
		if (reference.getPath().content() == null) {
			return null;
		}
		final Path path = reference.getPath().content();
		File file = path.toFile();
		if (!file.exists()) {
			file = SDAUtils.resolveResource(file);
		}
		if (file == null || !file.exists()) {
			return null;
		}
		return file;
	}

	@Override
	public XEnumType getEnum(String id) {
		return aggregator.getEnum(id);
	}

	@Override
	public Set<String> getEnums() {
		return aggregator.getEnums();
	}

	@Override
	public Set<String> getFunctionIds() {
		return aggregator.getFunctionIds();
	}

	@Override
	public IXFunction getFunction(String name) {
		return aggregator.getFunction(name);
	}

	@Override
	public Set<String> getEnumMembers(XEnumType type) {
		return aggregator.getEnumMembers(type);
	}

	@Override
	public Set<String> getEnumMembers(String type) {
		return aggregator.getEnumMembers(type);
	}

	public RootCatalog getCatalog() {
		return catalog;
	}

	@Override
	public String getName() {
		return "sda";
	}

	@Override
	public String getConceptMapSource() {
		return aggregator.getConceptMapSource();
	}

	@Override
	public IConceptMap getConceptMap() {
		return aggregator.getConceptMap();
	}

	@Override
	public Collection<String> getExternalMap(String element) {
		return aggregator.getExternalMap(element);
	}

	@Override
	public Collection<String> getExternalFileLocations() {
		return aggregator.getExternalFileLocations();
	}

}
