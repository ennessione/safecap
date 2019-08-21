package uk.ac.ncl.safecap.textentry.core;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.textentry.actions.ITEAction;
import uk.ac.ncl.safecap.textentry.types.ITEType;

public class TERegistry {
	private final Map<String, ITEType> types;
	private final Map<String, TEPart> typeSchemas;
	private final Map<String, List<ITEAction>> actions;
	private final Map<String, List<TEPart>> library;

	public TERegistry() {
		types = new HashMap<>();
		typeSchemas = new HashMap<>();
		actions = new HashMap<>();
		library = new HashMap<>();

//		System.out.println("[TE] Known types " + types.keySet());
//		System.out.println("[TE] Library " + library.toString());
	}

	public void build() {
		try {
			buildExtensions();
			buildLibrary();
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	public TEPart getSchemaFor(String type) {
		return typeSchemas.get(type);
	}

	public List<TEPart> getLibraryElements(String type) {
		if (library.containsKey(type)) {
			return library.get(type);
		}

		return Collections.emptyList();
	}

	public List<TEPart> getAllElements(String type) {
		final List<TEPart> result = new ArrayList<>(getLibraryElements(type));
		for (final IFile file : getProjectTEFiles()) {
			try {
				final TEPart part = TEBuilder.build(file.getContents());
				if (part != null && type.equals(part.getType())) {
					result.add(part);
				}
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private void buildLibrary() {
		final List<URL> urls = geExtentionTEFiles();
		for (final URL url : urls) {
			try {
				// System.out.println("[TE] Processing library file " + url);
				final TEPart part = TEBuilder.build(url.openStream());
				if (part != null) {
					List<TEPart> list = library.get(part.getType());
					if (list == null) {
						list = new ArrayList<>();
						library.put(part.getType(), list);
					}
					list.add(part);
					// System.out.println("[TE] Added library item " + part.getType() + "::" +
					// part.getName());
					if ("schema".equals(part.getType())) {
						typeSchemas.put(part.getName(), part);
						// System.out.println("[TE] Added schema " + part.getName());
					}
				} else {
					System.err.println("[TE] Library file " + url + " is broken");
				}

			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}
	}

	private static List<URL> geExtentionTEFiles() {
		final List<URL> result = new ArrayList<>();
		final IExtensionRegistry reg = Platform.getExtensionRegistry();
		final IConfigurationElement[] elements = reg.getConfigurationElementsFor("uk.ac.ncl.safecap.te.lib");
		for (final IConfigurationElement zz : elements) {
			final String folder = zz.getAttribute("folder");
			final String plugin = zz.getContributor().getName();
			final Bundle bundle = Platform.getBundle(plugin);
			final Enumeration<String> enumerator = bundle.getEntryPaths(folder);
			// System.out.println("[TE] Processing library folder " + folder + " for " +
			// plugin);
			while (enumerator != null && enumerator.hasMoreElements()) {
				final String path = enumerator.nextElement();
				final URL entry = bundle.getEntry(path);
				final String pathString = entry.getPath();
				if (pathString.contains(".")) {
					final String extension = pathString.substring(pathString.lastIndexOf(".") + 1);
					if (isRelevant(extension)) {
						result.add(entry);
					}
				}
			}
		}

		return result;
	}

	private static List<IFile> getProjectTEFiles() {
		final List<IFile> result = new ArrayList<>();
		try {
			for (final IProject prj : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
				for (final IResource res : prj.members()) {
					if (res instanceof IFile) {
						final IFile file = (IFile) res;
						if (isRelevant(file)) {
							result.add(file);
						}
					}
				}
			}
		} catch (final Throwable e) {
		}

		return result;
	}

	private static boolean isRelevant(IFile file) {
		final String extension = file.getFileExtension();

		return isRelevant(extension);
	}

	private static boolean isRelevant(String extension) {
		return SafecapConstants.CONFIGURATION_EXTENSION.equals(extension) || SafecapConstants.CONTRACT_EXTENSION.equals(extension)
				|| SafecapConstants.DEFINITION_EXTENSION.equals(extension);
	}

	public Collection<String> getTypes() {
		return types.keySet();
	}

	public ITEType getTypeInfo(String type) {
		return types.get(type);
	}

	public List<ITEAction> getActionInfo(String type) {
		return actions.get(type);
	}

	public Collection<String> getActions() {
		return actions.keySet();
	}

	public void buildExtensions() {
		final IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = reg.getConfigurationElementsFor("uk.ac.ncl.safecap.te.type");
		for (final IConfigurationElement zz : elements) {
			try {
				final String name = zz.getAttribute("name");
				final Object o = zz.createExecutableExtension("class");
				if (name != null && o instanceof ITEType && !types.containsKey(name)) {
					final ITEType type = (ITEType) o;
					types.put(name, type);
				}
				final String schema = zz.getAttribute("schema");
				if (schema != null) {
					final String plugin = zz.getContributor().getName();
					final Bundle bundle = Platform.getBundle(plugin);
					final URL entry = bundle.getEntry(schema);
					final TEPart schemaPart = TEBuilder.build(entry.openStream());
					if (schemaPart != null) {
						typeSchemas.put(name, schemaPart);
					}
				}
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}

		elements = reg.getConfigurationElementsFor("uk.ac.ncl.safecap.te.action");
		for (final IConfigurationElement zz : elements) {
			try {
				final String type = zz.getAttribute("type");
				final Object o = zz.createExecutableExtension("class");
				if (type != null && o instanceof ITEAction) {
					final ITEAction action = (ITEAction) o;
					List<ITEAction> list = actions.get(type);
					if (list == null) {
						list = new ArrayList<>();
						actions.put(type, list);
					}
					list.add(action);
				}
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
