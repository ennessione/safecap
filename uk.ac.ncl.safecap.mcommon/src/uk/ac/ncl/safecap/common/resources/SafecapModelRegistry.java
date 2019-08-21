package uk.ac.ncl.safecap.common.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.DiagramEditingDomainFactory;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.internal.util.DiagramIOUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.ui.part.FileEditorInput;

public class SafecapModelRegistry implements IResourceChangeListener, IResourceDeltaVisitor {
	public static class ResourcePack {
		public Resource resource;
		public TransactionalEditingDomain domain;
		public Map<String, Object> attributes;
		private List<ISafecapSchemaListener> listeners;

		public ResourcePack(Resource resource, TransactionalEditingDomain domain) {
			super();
			this.resource = resource;
			this.domain = domain;
		}

		public void addListener(ISafecapSchemaListener listener) {
			if (listeners == null) {
				listeners = new ArrayList<>();
			}
			listeners.add(listener);
		}

		public void remListener(ISafecapSchemaListener listener) {
			if (listeners != null) {
				listeners.remove(listener);
			}
		}

		public void setAttribute(String key, Object value) {
			if (attributes == null) {
				attributes = new HashMap<>();
			}

			attributes.put(key, value);
		}

		public Object getAttribute(String key) {
			if (attributes != null) {
				return attributes.get(key);
			} else {
				return null;
			}
		}
	}

	private static SafecapModelRegistry _instance = new SafecapModelRegistry();

	private final Map<IFile, ResourcePack> _resources = new HashMap<>();

	public static Object getAttribute(IFile file, String key) {
		final ResourcePack pack = _instance.getResourcePack(file);
		if (pack != null) {
			return pack.getAttribute(key);
		} else {
			return null;
		}
	}

	public static void setAttribute(IFile file, String key, Object value) {
		final ResourcePack pack = _instance.getResourcePack(file);
		if (pack != null) {
			pack.setAttribute(key, value);
		}
	}

	public static void addListener(IFile file, ISafecapSchemaListener listener) {
		final ResourcePack pack = _instance.getResourcePack(file);
		if (pack != null) {
			pack.addListener(listener);
		}
	}

	public static void remListener(IFile file, ISafecapSchemaListener listener) {
		final ResourcePack pack = _instance.getResourcePack(file);
		if (pack != null) {
			pack.remListener(listener);
		}
	}

	public static Resource getResource(IFile file) {
		final ResourcePack pack = _instance.getResourcePack(file);
		return pack.resource;
	}

	public static TransactionalEditingDomain getDomain(IFile file) {
		final ResourcePack pack = _instance.getResourcePack(file);
		return pack.domain;
	}

	public ResourcePack getResourcePack(IFile file) {
		if (!_resources.containsKey(file)) {
			load(file);
		}
		return _resources.get(file);
	}

	@SuppressWarnings("restriction")
	private void load(IFile file) {
		if (!_resources.containsKey(file)) {
			try {
				final IStorage storage = new FileEditorInput(file).getStorage();

				final TransactionalEditingDomain domain = DiagramEditingDomainFactory.getInstance().createEditingDomain();
				domain.setID("uk.ac.ncl.safecap.diagram.EditingDomain"); //$NON-NLS-1$

				final Diagram diagram = DiagramIOUtil.load(domain, storage, true, new NullProgressMonitor());

				if (diagram != null)
					_resources.put(file, new ResourcePack(diagram.eResource(), domain));
//				Object contents = diagram.eResource().getContents().get(0);
//				if (contents instanceof Project) {
//					Project prj = (Project) contents;
//					//ProjectTransient.init(prj);
//				}

				ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
			} catch (final Throwable e) {
				e.printStackTrace();
				throw new RuntimeException("Could not load model: " + e.getMessage());
			}
		}
	}

	@Override
	public void resourceChanged(IResourceChangeEvent arg0) {
		try {
			arg0.getDelta().accept(this);
		} catch (final CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean visit(IResourceDelta delta) throws CoreException {
		final IResource res = delta.getResource();
		if (res instanceof IFile) {
			final IFile file = (IFile) res;
			if (_resources.containsKey(file)) {
				final ResourcePack pack = _resources.get(file);
				if (delta.getKind() == IResourceDelta.REMOVED) {
					pack.resource.unload();
					pack.domain.dispose();
					_resources.remove(file);
				} else {
					if (pack.listeners != null) {
						for (final ISafecapSchemaListener l : pack.listeners) {
							l.changed();
						}
					}
				}
			}
		}
		return true;
	}
}
