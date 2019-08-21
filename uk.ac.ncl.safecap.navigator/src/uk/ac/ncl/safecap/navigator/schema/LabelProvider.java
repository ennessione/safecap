package uk.ac.ncl.safecap.navigator.schema;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.navigator.IDescriptionProvider;

import safecap.Labeled;
import safecap.Orientation;
import safecap.derived.MergedPoint;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.model.Line;
import safecap.model.Point;
import safecap.model.Route;
import safecap.model.Section;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.common.resources.ImageRegister;
import uk.ac.ncl.safecap.common.resources.VersionUtil;
import uk.ac.ncl.safecap.misc.Container;
import uk.ac.ncl.safecap.misc.StyleColours;
import uk.ac.ncl.safecap.misc.VersionFolder;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.Overlap;
import uk.ac.ncl.safecap.misc.core.PointUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.misc.core.SubOverlap;
import uk.ac.ncl.safecap.misc.core.SubRoute;
import uk.ac.ncl.safecap.navigator.schema.ContentProvider.RouteAmbits;
import uk.ac.ncl.safecap.navigator.schema.ContentProvider.RouteOverlaps;
import uk.ac.ncl.safecap.navigator.schema.ContentProvider.RouteSegments;

public class LabelProvider implements ILabelProvider, IStyledLabelProvider, IDescriptionProvider {
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof IProject) {
			return ImageRegister.getImage(ImageRegister.ICON_PROJECT);
		} else if (element instanceof VersionFolder) {
			final VersionFolder vf = (VersionFolder) element;
			return getFileIcon(vf.getType());
		} else if (element instanceof IFile) {
			final IFile file = (IFile) element;
			if (file.exists()) {
				final String ext = file.getFileExtension();
				return getFileIcon(ext);
			} else {
				return null;
			}
		} else if (element instanceof Node) {
			return null;
		} else if (element instanceof Route) {
			final Route r = (Route) element;
			switch (r.getOrientation().getValue()) {
			case Orientation.LEFT_VALUE:
				return ImageRegister.getImage(ImageRegister.ICON_ROUTE_LEFT);
			case Orientation.RIGHT_VALUE:
				return ImageRegister.getImage(ImageRegister.ICON_ROUTE_RIGHT);
			default:
				return ImageRegister.getImage(ImageRegister.ICON_ROUTE);
			}

		} else if (element instanceof Point) {
			return ImageRegister.getImage(ImageRegister.ICON_POINT);
		} else if (element instanceof Line) {
			return ImageRegister.getImage(ImageRegister.ICON_LINE);
		} else if (element instanceof Section) {
			return ImageRegister.getImage(ImageRegister.ICON_SECTION);
		} else if (element instanceof Junction) {
			return ImageRegister.getImage(ImageRegister.ICON_JUNCTION);
		} else if (element instanceof Segment) {
			return ImageRegister.getImage(ImageRegister.ICON_BULLET_BLACK);
		} else if (element instanceof SubRoute) {
			return ImageRegister.getImage(ImageRegister.ICON_SUBROUTE);
		} else if (element instanceof SubOverlap) {
			return ImageRegister.getImage(ImageRegister.ICON_SUBOVERLAP);
		} else if (element instanceof Overlap) {
			return ImageRegister.getImage(ImageRegister.ICON_SUBOVERLAP);
		} else if (element instanceof Labeled) {
			return null;
		} else if (element instanceof Container) {
			return ImageRegister.getImage(ImageRegister.ICON_COLLECTION);
		} else if (element instanceof Attribute) {
			return null;
		} else {
			return null;
		}
	}

	private Image getFileIcon(String ext) {
		if (ext.equals(SafecapConstants.SAFECAP_FILE_EXTENSION)) {
			return ImageRegister.getImage(ImageRegister.ICON_SCHEMA);
		} else if (ext.equals(SafecapConstants.VCATALOG_EXTENSION)) {
			return ImageRegister.getImage(ImageRegister.ICON_VCATALOG);
		} else if (ext.equals(SafecapConstants.XMLDATA_EXTENSION)) {
			return ImageRegister.getImage(ImageRegister.ICON_XMLDATA);
		} else {
			return null;
		}
	}

	@Override
	public StyledString getStyledText(Object element) {
		final StyledString text = new StyledString();
		try {
			if (element instanceof IProject) {
				final IProject proj = (IProject) element;
				text.append(proj.getName());
			} else if (element instanceof VersionFolder) {
				final VersionFolder folder = (VersionFolder) element;
				text.append(folder.getLabel(), StyledString.DECORATIONS_STYLER);
			} else if (element instanceof IFile) {
				final IFile file = (IFile) element;
				final Date date = new Date(file.getLocalTimeStamp());
				text.append(VersionUtil.getVersionName(file.getFullPath().removeFileExtension().lastSegment()));
				text.append("  " + dateFormat.format(date), StyleColours.dateStyler);
				final IFileStore store = org.eclipse.core.filesystem.EFS.getStore(file.getLocationURI());
				if (store != null && store.fetchInfo().getLength() > 1000000) {
					text.append("  " + humanReadableByteCount(store.fetchInfo().getLength(), false), StyleColours.typeStyler);
				}

//				ResourceStatus status = DBUtils.getStatus(file);
//				if (status != null) {
//					text.append("  ");
//					if (status.isActual(file)) {
//						text.append("[" +status.getDescriptor().getDescription() + "]", StyleColours.resourceStyler);
//					}
//				}
			} else if (element instanceof Node) {
				final Node node = (Node) element;
				switch (node.getKind().getValue()) {
				case NodeKind.POINT_VALUE:
					text.append("Point " + node.getLabel());
				case NodeKind.CROSSOVER_VALUE:
					text.append("Crossover " + node.getLabel());
				case NodeKind.BOUNDARY_VALUE:
					text.append("(Boundary) " + node.getLabel());
				case NodeKind.TERMINAL_VALUE:
					text.append("(Terminal)  " + node.getLabel());
				default:
					text.append("Node " + node.getLabel());
				}
			} else if (element instanceof RouteAmbits) {
				text.append("Ambits");
			} else if (element instanceof RouteSegments) {
				text.append("Segments");
			} else if (element instanceof Line) {
				final Line l = (Line) element;
				text.append(l.getLabel());
				switch (l.getOrientation().getValue()) {
				case Orientation.LEFT_VALUE:
					text.append(" L", StyleColours.commentStyler);
				case Orientation.RIGHT_VALUE:
					text.append(" R", StyleColours.commentStyler);
				default:
					text.append(" A", StyleColours.commentStyler);
				}
			} else if (element instanceof Segment) {
				final Segment s = (Segment) element;
				text.append(s.getLabel());
				text.append(wn(s.getFrom().getLabel()), StyledString.COUNTER_STYLER);
				text.append(" - ", StyledString.COUNTER_STYLER);
				text.append(wn(s.getTo().getLabel()), StyledString.COUNTER_STYLER);
			} else if (element instanceof Ambit) {
				final Ambit a = (Ambit) element;
				text.append(a.getLabel());
				if (AmbitUtil.isComposed(a)) {
					text.append("  composed", StyleColours.commentStyler);
				} else if (AmbitUtil.isDisjoint(a)) {
					text.append("  merged/disjoint", StyleColours.commentStyler);
				} else if (AmbitUtil.isMerged(a)) {
					text.append("  merged", StyleColours.commentStyler);
				} else if (ExtensionUtil.isVirtual(a)) {
					text.append("  virtual", StyleColours.commentStyler);
				}
			} else if (element instanceof Labeled) {
				final Labeled l = (Labeled) element;
				text.append(l.getLabel());
			} else if (element instanceof Point) {
				final Point p = (Point) element;
				if (PointUtil.isMerged(p)) {
					final MergedPoint mp = (MergedPoint) p;
					text.append(mp.getLabel());
					text.append("  merged", StyleColours.commentStyler);
				} else {
					text.append(p.getNode().getLabel());
				}
			} else if (element instanceof RouteOverlaps) {
				text.append("Overlaps");
			} else if (element instanceof Overlap) {
				final Overlap ovl = (Overlap) element;
				text.append(ovl.describe());
			} else if (element instanceof Container) {
				final Container cont = (Container) element;
				text.append(cont.getLabel(), StyledString.COUNTER_STYLER);
			} else if (element instanceof Attribute) {
				final Attribute attr = (Attribute) element;
				text.append(attr.getAttribute() + ":" + attr.getValue());
			} else {
				text.append(element.toString());
			}
		} catch (final Exception e) {
			text.append("error: " + e.getMessage(), StyleColours.errorStyler);
		}

		return text;
	}

	private static String humanReadableByteCount(long bytes, boolean si) {
		final int unit = si ? 1000 : 1024;
		if (bytes < unit) {
			return bytes + " B";
		}
		final int exp = (int) (Math.log(bytes) / Math.log(unit));
		final String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	@Override
	public String getDescription(Object anElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText(Object element) {
		try {
			if (element instanceof IProject) {
				final IProject proj = (IProject) element;
				return proj.getName();
			} else if (element instanceof VersionFolder) {
				final VersionFolder folder = (VersionFolder) element;
				return folder.getLabel();
			} else if (element instanceof IFile) {
				final IFile file = (IFile) element;
				final Date date = new Date(file.getLocalTimeStamp());
				return VersionUtil.getVersionName(file.getFullPath().removeFileExtension().lastSegment()) + "  (" + dateFormat.format(date)
						+ ")";
			} else if (element instanceof Node) {
				final Node node = (Node) element;
				switch (node.getKind().getValue()) {
				case NodeKind.POINT_VALUE:
					return "Point " + node.getLabel();
				case NodeKind.CROSSOVER_VALUE:
					return "Crossover " + node.getLabel();
				case NodeKind.BOUNDARY_VALUE:
					return "(Boundary) " + node.getLabel();
				case NodeKind.TERMINAL_VALUE:
					return "(Terminal)  " + node.getLabel();
				default:
					return "Node " + node.getLabel();
				}
			} else if (element instanceof Route) {
				final Route r = (Route) element;
				return r.getLabel();
			} else if (element instanceof RouteAmbits) {
				return "Ambits";
			} else if (element instanceof RouteSegments) {
				return "Segments";
			} else if (element instanceof Line) {
				final Line l = (Line) element;
				switch (l.getOrientation().getValue()) {
				case Orientation.LEFT_VALUE:
					return l.getLabel() + " L";
				case Orientation.RIGHT_VALUE:
					return l.getLabel() + " R";
				default:
					return l.getLabel() + " A";
				}
			} else if (element instanceof Segment) {
				final Segment s = (Segment) element;
				return s.getLabel() + " " + wn(s.getFrom().getLabel()) + " - " + wn(s.getTo().getLabel());
			} else if (element instanceof Ambit) {
				final Ambit a = (Ambit) element;
				if (AmbitUtil.isComposed(a)) {
					return a.getLabel() + " (composed)";
				} else if (AmbitUtil.isDisjoint(a)) {
					return a.getLabel() + " (merged/disjoint)";
				} else if (AmbitUtil.isMerged(a)) {
					return a.getLabel() + " (merged)";
				} else if (ExtensionUtil.isVirtual(a)) {
					return a.getLabel() + " (virtual)";
				}

				return ((Labeled) element).getLabel();
			} else if (element instanceof Labeled) {
				return ((Labeled) element).getLabel();
			} else if (element instanceof Point) {
				final Point p = (Point) element;
				if (PointUtil.isMerged(p)) {
					final MergedPoint mp = (MergedPoint) p;
					return mp.getLabel() + " (merged)";
				} else {
					return p.getNode().getLabel();
				}
			} else if (element instanceof RouteOverlaps) {
				return "Overlaps";
			} else if (element instanceof Overlap) {
				final Overlap ovl = (Overlap) element;
				return ovl.describe();
			} else if (element instanceof Container) {
				final Container cont = (Container) element;
				return cont.getLabel();
			} else if (element instanceof Attribute) {
				final Attribute attr = (Attribute) element;
				return attr.getAttribute() + ":" + attr.getValue();
			} else {
				return element.toString();
			}
		} catch (final Exception e) {
			return "error: " + e.getMessage();
		}
	}

	private String wn(String label) {
		if (label == null) {
			return "?";
		} else {
			return label;
		}
	}

}
