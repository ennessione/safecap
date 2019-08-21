package uk.ac.ncl.safecap.xdata.verification.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.modeling.ResourceStoreException;
import org.eclipse.sapphire.modeling.xml.RootXmlResource;
import org.eclipse.sapphire.modeling.xml.XmlResourceStore;

public class SapphireCatalogRegistry<T extends Element> implements IResourceChangeListener {
	private final String extension;
	private final ElementType type;
	private final Map<IFile, T> fileToCatalog;
	private final Map<String, T> nameToCatalog;
	private final Map<T, List<ICatalogListener>> listeners;
	private final List<ICatalogCreationListener> creationListeners;

	public SapphireCatalogRegistry(String extension, ElementType type) {
		fileToCatalog = new HashMap<>();
		nameToCatalog = new HashMap<>();
		listeners = new HashMap<>();
		creationListeners = new ArrayList<>();
		this.extension = extension;
		this.type = type;
	}

	public void start() {
		scan();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	public Collection<String> allModels() {
		return nameToCatalog.keySet();
	}

	public T resolve(IFile file) {
		T ct = fileToCatalog.get(file);
		if ((ct == null || ct.disposed()) && file.exists() && extension.equals(file.getFileExtension())) {
			refresh(file);
			ct = fileToCatalog.get(file);
		}

		return ct;
	}

	public String resolveByCatalog(T catalog) {
		for (final Entry<String, T> x : nameToCatalog.entrySet()) {
			if (x.getValue() == catalog) {
				return x.getKey();
			}
		}

		return null;
	}

	public IFile resolveByCatalogToFile(T catalog) {
		for (final Entry<IFile, T> x : fileToCatalog.entrySet()) {
			if (x.getValue() == catalog) {
				return x.getKey();
			}
		}

		return null;
	}

	public T resolveByName(String name) {
		return nameToCatalog.get(name);
	}

	private void scan() {
		for (final IProject p : org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			try {
				if (p.isOpen()) {
					openProject(p);
				}
			} catch (final CoreException e) {
				e.printStackTrace();
			}
		}
	}

	public void subscribe(T resource, ICatalogListener l) {
		List<ICatalogListener> lset = listeners.get(resource);
		if (lset == null) {
			lset = new ArrayList<>();
			listeners.put(resource, lset);
		}

		lset.add(l);
	}

	public void subscribe(ICatalogCreationListener l) {
		creationListeners.add(l);
	}

	public void unsubscribe(T resource, ICatalogListener l) {
		final List<ICatalogListener> lset = listeners.get(resource);
		if (lset != null) {
			lset.remove(l);
		}
	}

	public void unsubscribe(ICatalogCreationListener l) {
		creationListeners.remove(l);
	}

	public void notify(T resource) {
		final List<ICatalogListener> lset = listeners.get(resource);
		if (lset != null) {
			for (final ICatalogListener ll : lset) {
				ll.handleUpdate(resource);
			}
		}
	}

	// private void notifyNew(T resource) {
	// for (ICatalogCreationListener ll : creationListeners)
	// ll.handleNew(resource);
	// }
	//
	// private void notifyClose(T resource) {
	// for (ICatalogCreationListener ll : creationListeners)
	// ll.handleClose(resource);
	// }

	public void refresh(IFile file) {
		try {
			final XmlResourceStore store = new XmlResourceStore(file.getLocation().toFile());
			final RootXmlResource xmlres = new RootXmlResource(store);
			final T catalog = type.instantiate(xmlres);
			if (catalog != null) {
				// if (!fileToCatalog.containsKey(file))
				// notifyNew(catalog);
				fileToCatalog.put(file, catalog);
				nameToCatalog.put(file.getName(), catalog);
				// notify(catalog);
			}
		} catch (final ResourceStoreException e) {
			e.printStackTrace();
		}
	}

	public void inject(IFile file, T resource) {
		fileToCatalog.put(file, resource);
		nameToCatalog.put(file.getName(), resource);
		// notify(resource);
		// notifyNew(resource);
	}

	private void forget(IFile file) {
		final T catalog = fileToCatalog.get(file);
		if (catalog != null) {
			fileToCatalog.remove(file);
			nameToCatalog.remove(file.getName());
			// notifyClose(catalog);
		}
	}

	private void openProject(IProject project) throws CoreException {
		for (final IResource r : project.members()) {
			if (extension.equals(r.getFileExtension())) {
				if (!fileToCatalog.containsKey(r)) {
					refresh((IFile) r);
				}
			}
		}
	}

	private void closeProject(IProject project) throws CoreException {
		for (final IResource r : project.members()) {
			if (extension.equals(r.getFileExtension())) {
				forget((IFile) r);
			}
		}
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		final Set<IFile> fileToCatalogSet = new HashSet<>(fileToCatalog.keySet());
		for (final IFile f : fileToCatalogSet) {
			try {
				final IResourceDelta rd = event.getDelta().findMember(f.getFullPath());

				if (rd != null && rd.getResource() instanceof IFile && rd.getResource().getFileExtension().equals(extension)) {
					if (rd.getKind() == IResourceDelta.REMOVED) {
						forget((IFile) rd.getResource());
					} else if (rd.getKind() == IResourceDelta.CHANGED || rd.getKind() == IResourceDelta.REPLACED) {
						final T c = fileToCatalog.get(rd.getResource());
						notify(c);
						// refresh((IFile) rd.getResource());
					}
				}
			} catch (final Throwable e) {
				return;
			}
		}

		// project open
		try {
			event.getDelta().accept(new IResourceDeltaVisitor() {
				@Override
				public boolean visit(final IResourceDelta delta) throws CoreException {
					final IResource resource = delta.getResource();
					if ((resource.getType() & IResource.PROJECT) != 0 && resource.getProject().isOpen()
							&& delta.getKind() == IResourceDelta.CHANGED
							&& ((delta.getFlags() & IResourceDelta.OPEN) != 0 || (delta.getFlags() & IResourceDelta.ADDED) != 0)) {

						final IProject project = (IProject) resource;
						openProject(project);
					}
					return true;
				}
			});
		} catch (final CoreException e) {
			e.printStackTrace();
		}

		// project close
		try {
			event.getDelta().accept(new IResourceDeltaVisitor() {
				@Override
				public boolean visit(final IResourceDelta delta) throws CoreException {
					final IResource resource = delta.getResource();
					if ((resource.getType() & IResource.PROJECT) != 0 && resource.getProject().isOpen()
							&& delta.getKind() == IResourceDelta.CHANGED && (delta.getFlags() & IResourceDelta.REMOVED) != 0) {

						final IProject project = (IProject) resource;
						closeProject(project);
					}
					return true;
				}
			});
		} catch (final CoreException e) {
			e.printStackTrace();
		}
	}
}
