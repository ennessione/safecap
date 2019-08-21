package uk.ac.ncl.safecap.navigator.schema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.Viewer;

import safecap.Extensible;
import safecap.Labeled;
import safecap.Orientation;
import safecap.Project;
import safecap.derived.MergedJunction;
import safecap.derived.MergedSection;
import safecap.extension.ExtAttribute;
import safecap.extension.ExtAttributeB;
import safecap.extension.ExtAttributeStr;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.model.Line;
import safecap.model.Route;
import safecap.schema.Segment;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.common.resources.SafecapResourceUtil;
import uk.ac.ncl.safecap.misc.Container;
import uk.ac.ncl.safecap.misc.ResourceContainer;
import uk.ac.ncl.safecap.misc.VersionFolder;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.LineUtil;
import uk.ac.ncl.safecap.misc.core.Overlap;
import uk.ac.ncl.safecap.misc.core.OverlapUtil;
import uk.ac.ncl.safecap.misc.core.RouteUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.misc.core.SegmentPath;
import uk.ac.ncl.safecap.misc.core.SubOverlapUtil;
import uk.ac.ncl.safecap.misc.core.SubRouteUtil;

public class ContentProvider implements org.eclipse.jface.viewers.ITreeContentProvider, IResourceChangeListener {
	private Viewer viewer;

	public ContentProvider() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		if (viewer != null) {
			viewer.getControl().getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					viewer.refresh();
				}
			});
		}

	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		try {
			// System.out.println("getChildren for element " + parentElement);
			// if (parentElement instanceof IWorkspaceRoot)
			// {
			// IWorkspaceRoot root = (IWorkspaceRoot) parentElement;
			// return root.getProjects();
			// }
			if (parentElement instanceof IProject) {
				final IProject project = (IProject) parentElement;
				// System.out.println("getChildren for project" + project.getName());
				final List<Object> children = new ArrayList<>();
				final Container layouts = buildCompartment(project, SafecapConstants.SAFECAP_FILE_EXTENSION, "Layouts");
				final Container datasets = buildCompartment(project, SafecapConstants.XMLDATA_EXTENSION, "Data sets");
				final Container properties = buildCompartment(project, SafecapConstants.VCATALOG_EXTENSION, "Properties");
				final Container configs = buildCompartment(project, SafecapConstants.CONFIGURATION_EXTENSION, "Configurations");
				final Container defs = buildCompartment(project, SafecapConstants.DEFINITION_EXTENSION, "Definitions");
				final Container contracts = buildCompartment(project, SafecapConstants.CONTRACT_EXTENSION, "Contracts");
				final Container transitions = buildCompartment(project, SafecapConstants.TRANSITIONS_EXTENSION, "Transitions");
				final Container extractions = buildCompartment(project, SafecapConstants.EXTRACTOR_EXTENSION, "Data transforms");

				if (!layouts.isEmpty()) {
					children.add(layouts);
				}
				if (!datasets.isEmpty()) {
					children.add(datasets);
				}
				if (!properties.isEmpty()) {
					children.add(properties);
				}
				if (!configs.isEmpty()) {
					children.add(configs);
				}
				if (!defs.isEmpty()) {
					children.add(defs);
				}
				if (!contracts.isEmpty()) {
					children.add(contracts);
				}
				if (!transitions.isEmpty()) {
					children.add(transitions);
				}
				if (!extractions.isEmpty()) {
					children.add(extractions);
				}

				return children.toArray();
			} else if (parentElement instanceof IFile) {
				final IFile _file = (IFile) parentElement;

				if (_file.exists() && _file.getFileExtension().equals(SafecapConstants.SAFECAP_FILE_EXTENSION)) {
					final Project project = getProject(_file);
					if (project != null) {
						final List<Container> result = new ArrayList<>();
						if (!project.getAmbits().isEmpty()) {
							result.add(new Container("Ambits", project.getAmbits()));
						}
						if (!project.getRoutes().isEmpty()) {
							result.add(new Container("Routes", project.getRoutes()));
						}
						if (!project.getLines().isEmpty()) {
							result.add(new Container("Lines", project.getLines()));
						}
						final Boolean _customBuild = ExtensionUtil.getBool(project, SafecapConstants.EXT_LDL_GEN, "customBuild");
						final Boolean _importedRoutes = ExtensionUtil.getBool(project, "uk.ac.ncl.safecap.importroutes", "importroutes");
						final Boolean customBuild = _customBuild == null ? false : _customBuild;
						final Boolean importedRoutes = _importedRoutes == null ? false : _importedRoutes;

						final List<String> debugvals = new ArrayList<>(10);
						debugvals.add("Custom build: " + customBuild.toString());
						debugvals.add("Imported routes: " + importedRoutes.toString());
						for (final String k : ExtensionUtil.getKeys(project, SafecapConstants.EXT_SCHEMA_FLAG)) {
							debugvals.add(k + ": " + ExtensionUtil.getFlag(project, k));
						}

						result.add(new Container("Debug", debugvals));

						if (!project.getDarkmatter().isEmpty()) {
							result.add(new Container("Virtual", project.getDarkmatter()));
						}
						return result.toArray();
					} else {
						return null;
					}
				} else {
					return null;
				}
			} else if (parentElement instanceof Container) {
				final Container cont = (Container) parentElement;
				final Object[] result = cont.getChildren().toArray();
				return result;
			} else if (parentElement instanceof RouteAmbits) {
				final RouteAmbits ra = (RouteAmbits) parentElement;
				return ra.route.getAmbits().toArray();
			} else if (parentElement instanceof RouteSegments) {
				final RouteSegments ra = (RouteSegments) parentElement;
				return ra.route.getSegments().toArray();
			} else if (parentElement instanceof Labeled) {
				if (parentElement instanceof Line) {
					final Line line = (Line) parentElement;
					final List<Object> parts = new ArrayList<>();
					parts.addAll(line.getRoutes());
					parts.add(new Attribute("length", LineUtil.getLength(line)));
					return parts.toArray();
				} else if (parentElement instanceof Route) {
					final Route route = (Route) parentElement;
					final List<Object> result = new ArrayList<>();
					result.add(new RouteAmbits(route));
					result.add(new RouteSegments(route));
					final Signal entry = RouteUtil.getEntrySignal(EmfUtil.getProject(route), route);
					final Signal exit = RouteUtil.getExitSignal(EmfUtil.getProject(route), route);
					result.add(new Attribute("entrance", entry == null ? "undefined" : entry));
					result.add(new Attribute("exit", exit == null ? "undefined" : exit));
					result.add(new Attribute("interlockings", RouteUtil.getInterlockings(route).toString()));

					try {
						final String _class = RouteUtil.getRouteClass(route);
						final String _subclass = RouteUtil.getRouteSubClass(route);
						final boolean _preset = RouteUtil.isRoutePreset(route);
						result.add(new Attribute("length", RouteUtil.getLength(route)));
						result.add(new Attribute("class", _class == null ? "undefined" : _class));
						result.add(new Attribute("subclass", _subclass == null ? "undefined" : _subclass));
						result.add(new Attribute("preset", "" + _preset));
					} catch (final Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					final Collection<Overlap> overlaps = RouteUtil.getRouteOverlaps(route);
					if (!overlaps.isEmpty()) {
						result.add(new RouteOverlaps(overlaps));
					}
					return result.toArray();
				} else if (parentElement instanceof Junction) {
					final Junction junction = (Junction) parentElement;
					if (junction instanceof MergedJunction) {
						final MergedJunction mj = (MergedJunction) junction;
						final List<Object> result = new ArrayList<>();
						result.addAll(mj.getAmbits());
						result.addAll(mj.getPoints());
						result.add(getAttributes(junction));
						return result.toArray();
					} else {
						return junction.getPoints().toArray();
					}
				} else if (parentElement instanceof Ambit) {
					final Ambit ambit = (Ambit) parentElement;
					final List<Object> result = new ArrayList<>(ambit.getSegments());
					if (ambit instanceof MergedSection) {
						final MergedSection ma = (MergedSection) ambit;
						result.addAll(ma.getAmbits());
					}

					result.addAll(SubRouteUtil.getSubRoutes(ambit));

					result.addAll(SubOverlapUtil.getSubOverlaps(ambit));

					result.addAll(ambit.getSegments());
					result.add(getAttributes(ambit));
					return result.toArray();
				} else if (parentElement instanceof Segment) {
					final Segment segment = (Segment) parentElement;
					final Attribute from = new Attribute("from", segment.getFrom());
					final Attribute to = new Attribute("to", segment.getTo());
					return new Object[] { from, to };
				} else {
					return null;
				}
			} else if (parentElement instanceof RouteOverlaps) {
				final RouteOverlaps ovl = (RouteOverlaps) parentElement;
				return ovl.overlaps.toArray();
			} else if (parentElement instanceof Overlap) {
				final Overlap ovl = (Overlap) parentElement;
				final SegmentPath sp = new SegmentPath(OverlapUtil.getNodePath(ovl), Orientation.ANY);
				return sp.getPath().toArray();
			} else {
				return null;
			}
		} catch (final Exception e) {
			return null;
		}
	}

	private static Container buildCompartment(IProject project, String extension, String label) {
		final Map<String, List<IFile>> layouts = SafecapResourceUtil.getResource(project, extension);
		final List<VersionFolder> versionFolders = new ArrayList<>();
		for (final String name : layouts.keySet()) {
			final VersionFolder folder = new VersionFolder(name, extension, layouts.get(name));
			versionFolders.add(folder);
		}

		return new ResourceContainer(label, extension, versionFolders);
	}

	private static Container getAttributes(Extensible element) {
		final List<String> attributes = new ArrayList<>();
		for (final ExtAttribute attr : element.getAttributes()) {
			String val = "?";
			if (attr instanceof ExtAttributeB) {
				final ExtAttributeB ab = (ExtAttributeB) attr;
				val = ab.isValue() ? "true" : "false";
			} else if (attr instanceof ExtAttributeStr) {
				final ExtAttributeStr ab = (ExtAttributeStr) attr;
				val = ab.getValue();
			}

			final String sattr = attr.getDomain() + "." + attr.getLabel() + ": " + val;
			attributes.add(sattr);
		}
		return new Container("Attributes", attributes);
	}

	static class RouteOverlaps {
		Collection<Overlap> overlaps;

		public RouteOverlaps(Collection<Overlap> overlaps) {
			this.overlaps = overlaps;
		}
	}

	static class RouteAmbits {
		Route route;

		public RouteAmbits(Route route) {
			this.route = route;
		}
	}

	static class RouteSegments {
		Route route;

		public RouteSegments(Route route) {
			this.route = route;
		}
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// System.out.println("hasChildren for element " + element);
		return element instanceof IWorkspaceRoot || element instanceof IProject || element instanceof IFile || element instanceof Container
				|| element instanceof Line || element instanceof Route || element instanceof Junction || element instanceof RouteAmbits
				|| element instanceof RouteSegments || element instanceof RouteOverlaps || element instanceof Overlap
				|| element instanceof Ambit;
	}

	private Project getProject(IFile file) {
		return EmfUtil.fromFile(file);
	}

}
