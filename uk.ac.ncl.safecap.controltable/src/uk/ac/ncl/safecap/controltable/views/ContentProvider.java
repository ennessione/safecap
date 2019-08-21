package uk.ac.ncl.safecap.controltable.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import safecap.Project;
import safecap.model.Line;
import safecap.model.Route;
import safecap.model.Rule;
//import uk.ac.ncl.safecap.common.RuleUtil;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.RouteUtil;
import uk.ac.ncl.safecap.misc.core.RuleUtil;

public class ContentProvider implements ITreeContentProvider, IResourceChangeListener {

	private final Adapter _listener = new AdapterImpl() {
		@Override
		public void notifyChanged(Notification msg) {
			super.notifyChanged(msg);
			viewer.refresh();
		}
	};

	private Viewer viewer;
	public boolean showRouteChildren = true;

	public ContentProvider() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		try {
			if (viewer != null) {
				viewer.getControl().getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						try {
							viewer.refresh();
							if (viewer instanceof TreeViewer) {
								final TreeViewer tv = (TreeViewer) viewer;
								tv.getTree().setRedraw(false);
								tv.expandAll();
								tv.getTree().setRedraw(true);
							}
						} catch (final Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		} catch (final Throwable e) {
			// suppress
		}
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
		if (oldInput != newInput) {
			if (oldInput != null && oldInput instanceof EObject) {
				final EObject oldObject = (EObject) oldInput;
				oldObject.eAdapters().remove(_listener);
			}
			if (newInput != null && newInput instanceof EObject) {
				((EObject) newInput).eAdapters().add(_listener);
			}
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
//		if (inputElement instanceof Project) {
//			Project project = (Project) inputElement;
//			List<Rule> rules = RuleUtil.getInitialisedRules(project);
//			return rules.toArray();
//		} else if (inputElement instanceof List<?>) {
//			List<?> list = (List<?>) inputElement;
//			return list.toArray();
//		} else if (inputElement instanceof Route) {
//			Route route = (Route) inputElement;
//			return new Object[]{route.getRule()};
//		}

		return getChildren(inputElement);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object[] getChildren(Object inputElement) {
		if (inputElement instanceof Project) {
			final Project project = (Project) inputElement;
			return getFirstLevelChildren(project).toArray();
		}
		if (inputElement instanceof Route) {
			final Route route = (Route) inputElement;
			if (!showRouteChildren) {
				if (RuleUtil.getDefaultLogic(route).getRule() != null) {
					final List<Rule> rules = RuleUtil.getRouteAspectRules((Route) inputElement);
					final List<RuleWrap> wraps = new ArrayList<>();
					for (final Rule rule : rules) {
						wraps.add(new RuleWrap(rule, route));
					}
					return wraps.toArray();
				} else {
					return new Object[0];
				}
			} else {
				final List list = new ArrayList();
				final List<Line> lines = RouteUtil.getAllContainingLines(route);
				for (final Line line : lines) {
					list.add(new RuleContainer(line, route));
				}
				return list.toArray();
			}
		}
		if (inputElement instanceof RuleContainer) {
			final RuleContainer container = (RuleContainer) inputElement;
			return container.getWraps().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Route) {
			if (!showRouteChildren) {
				return EmfUtil.getProject((Route) element);
			} else {
				return null;
			}
		}
		if (element instanceof RuleWrap) {
			final RuleWrap rule = (RuleWrap) element;
			return rule.container;
		}
		if (element instanceof RuleContainer) {
			final RuleContainer container = (RuleContainer) element;
			return container.route;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private List getFirstLevelChildren(Project project) {
		final List<Object> list = new ArrayList<>();
		final List<Rule> pointRules = RuleUtil.getAllPointRules(project);
		for (final Rule rule : pointRules) {
			list.add(new RuleWrap(rule, project));
		}
		list.addAll(project.getRoutes());
		return list;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Project) {
			return getFirstLevelChildren((Project) element).size() > 0;
		}
		if (element instanceof Route) {
			if (!showRouteChildren) {
				return RuleUtil.getRouteAspectRules((Route) element).size() > 0;
			} else {
				return true;
			}
		}
		if (element instanceof RuleContainer) {
			return true;
		}
		return false;
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);

		final Object oldInput = viewer.getInput();
		if (oldInput != null && oldInput instanceof EObject) {
			final EObject oldObject = (EObject) oldInput;
			oldObject.eAdapters().remove(_listener);
		}
	}
}
