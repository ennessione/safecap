package safecap.diagram.navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.WorkspaceEditingDomainFactory;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonContentProvider;

import safecap.diagram.edit.parts.BridgeEditPart;
import safecap.diagram.edit.parts.LeftSignalEditPart;
import safecap.diagram.edit.parts.LeftSpeedLimitEditPart;
import safecap.diagram.edit.parts.LeftStopAndGoEditPart;
import safecap.diagram.edit.parts.NodeEditPart;
import safecap.diagram.edit.parts.NodeWireEditPart;
import safecap.diagram.edit.parts.ProjectEditPart;
import safecap.diagram.edit.parts.RedLeftSignalEditPart;
import safecap.diagram.edit.parts.RedRightSignalEditPart;
import safecap.diagram.edit.parts.RightSignalEditPart;
import safecap.diagram.edit.parts.RightSpeedLimitEditPart;
import safecap.diagram.edit.parts.RightStopAndGoEditPart;
import safecap.diagram.edit.parts.SegmentEditPart;
import safecap.diagram.edit.parts.StationEditPart;
import safecap.diagram.edit.parts.SubSchemaNodeEditPart;
import safecap.diagram.edit.parts.WireEditPart;
import safecap.diagram.part.Messages;
import safecap.diagram.part.SafecapVisualIDRegistry;

/**
 * @generated
 */
public class SafecapNavigatorContentProvider implements ICommonContentProvider {

	/**
	 * @generated
	 */
	private static final Object[] EMPTY_ARRAY = new Object[0];

	/**
	 * @generated
	 */
	private Viewer myViewer;

	/**
	 * @generated
	 */
	private AdapterFactoryEditingDomain myEditingDomain;

	/**
	 * @generated
	 */
	private WorkspaceSynchronizer myWorkspaceSynchronizer;

	/**
	 * @generated
	 */
	private Runnable myViewerRefreshRunnable;

	/**
	 * @generated
	 */
	@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
	public SafecapNavigatorContentProvider() {
		final TransactionalEditingDomain editingDomain = WorkspaceEditingDomainFactory.INSTANCE.createEditingDomain();
		myEditingDomain = (AdapterFactoryEditingDomain) editingDomain;
		myEditingDomain.setResourceToReadOnlyMap(new HashMap() {
			@Override
			public Object get(Object key) {
				if (!containsKey(key)) {
					put(key, Boolean.TRUE);
				}
				return super.get(key);
			}
		});
		myViewerRefreshRunnable = new Runnable() {
			@Override
			public void run() {
				if (myViewer != null) {
					myViewer.refresh();
				}
			}
		};
		myWorkspaceSynchronizer = new WorkspaceSynchronizer(editingDomain, new WorkspaceSynchronizer.Delegate() {
			@Override
			public void dispose() {
			}

			@Override
			public boolean handleResourceChanged(final Resource resource) {
				unloadAllResources();
				asyncRefresh();
				return true;
			}

			@Override
			public boolean handleResourceDeleted(Resource resource) {
				unloadAllResources();
				asyncRefresh();
				return true;
			}

			@Override
			public boolean handleResourceMoved(Resource resource, final URI newURI) {
				unloadAllResources();
				asyncRefresh();
				return true;
			}
		});
	}

	/**
	 * @generated
	 */
	@Override
	public void dispose() {
		myWorkspaceSynchronizer.dispose();
		myWorkspaceSynchronizer = null;
		myViewerRefreshRunnable = null;
		myViewer = null;
		unloadAllResources();
		((TransactionalEditingDomain) myEditingDomain).dispose();
		myEditingDomain = null;
	}

	/**
	 * @generated
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		myViewer = viewer;
	}

	/**
	 * @generated
	 */
	void unloadAllResources() {
		for (final Resource nextResource : myEditingDomain.getResourceSet().getResources()) {
			nextResource.unload();
		}
	}

	/**
	 * @generated
	 */
	void asyncRefresh() {
		if (myViewer != null && !myViewer.getControl().isDisposed()) {
			myViewer.getControl().getDisplay().asyncExec(myViewerRefreshRunnable);
		}
	}

	/**
	 * @generated
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	/**
	 * @generated
	 */
	@Override
	public void restoreState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	@Override
	public void saveState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	@Override
	public void init(ICommonContentExtensionSite aConfig) {
	}

	/**
	 * @generated
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IFile) {
			final IFile file = (IFile) parentElement;
			final URI fileURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			final Resource resource = myEditingDomain.getResourceSet().getResource(fileURI, true);
			final ArrayList<SafecapNavigatorItem> result = new ArrayList<>();
			final ArrayList<View> topViews = new ArrayList<>(resource.getContents().size());
			for (final EObject o : resource.getContents()) {
				if (o instanceof View) {
					topViews.add((View) o);
				}
			}
			result.addAll(createNavigatorItems(selectViewsByType(topViews, ProjectEditPart.MODEL_ID), file, false));
			return result.toArray();
		}

		if (parentElement instanceof SafecapNavigatorGroup) {
			final SafecapNavigatorGroup group = (SafecapNavigatorGroup) parentElement;
			return group.getChildren();
		}

		if (parentElement instanceof SafecapNavigatorItem) {
			final SafecapNavigatorItem navigatorItem = (SafecapNavigatorItem) parentElement;
			if (navigatorItem.isLeaf() || !isOwnView(navigatorItem.getView())) {
				return EMPTY_ARRAY;
			}
			return getViewChildren(navigatorItem.getView(), parentElement);
		}

		return EMPTY_ARRAY;
	}

	/**
	 * @generated
	 */
	private Object[] getViewChildren(View view, Object parentElement) {
		switch (SafecapVisualIDRegistry.getVisualID(view)) {

		case ProjectEditPart.VISUAL_ID: {
			final LinkedList<SafecapAbstractNavigatorItem> result = new LinkedList<>();
			final Diagram sv = (Diagram) view;
			final SafecapNavigatorGroup links = new SafecapNavigatorGroup(Messages.NavigatorGroupName_Project_1000_links,
					"icons/linksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getChildrenByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(SubSchemaNodeEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement, false));
			connectedViews = getChildrenByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement, false));
			connectedViews = getChildrenByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(LeftSignalEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement, false));
			connectedViews = getChildrenByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(RightSignalEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement, false));
			connectedViews = getChildrenByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(LeftSpeedLimitEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement, false));
			connectedViews = getChildrenByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(RightSpeedLimitEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement, false));
			connectedViews = getChildrenByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(RedLeftSignalEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement, false));
			connectedViews = getChildrenByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(RedRightSignalEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement, false));
			connectedViews = getChildrenByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(LeftStopAndGoEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement, false));
			connectedViews = getChildrenByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(RightStopAndGoEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement, false));
			connectedViews = getChildrenByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(StationEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement, false));
			connectedViews = getChildrenByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(BridgeEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement, false));
			connectedViews = getDiagramLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(SegmentEditPart.VISUAL_ID));
			links.addChildren(createNavigatorItems(connectedViews, links, false));
			connectedViews = getDiagramLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(WireEditPart.VISUAL_ID));
			links.addChildren(createNavigatorItems(connectedViews, links, false));
			connectedViews = getDiagramLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeWireEditPart.VISUAL_ID));
			links.addChildren(createNavigatorItems(connectedViews, links, false));
			if (!links.isEmpty()) {
				result.add(links);
			}
			return result.toArray();
		}

		case NodeEditPart.VISUAL_ID: {
			final LinkedList<SafecapAbstractNavigatorItem> result = new LinkedList<>();
			final Node sv = (Node) view;
			final SafecapNavigatorGroup incominglinks = new SafecapNavigatorGroup(Messages.NavigatorGroupName_Node_2003_incominglinks,
					"icons/incomingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			final SafecapNavigatorGroup outgoinglinks = new SafecapNavigatorGroup(Messages.NavigatorGroupName_Node_2003_outgoinglinks,
					"icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getIncomingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(SegmentEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews, incominglinks, true));
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(SegmentEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			connectedViews = getIncomingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeWireEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews, incominglinks, true));
			if (!incominglinks.isEmpty()) {
				result.add(incominglinks);
			}
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case LeftSignalEditPart.VISUAL_ID: {
			final LinkedList<SafecapAbstractNavigatorItem> result = new LinkedList<>();
			final Node sv = (Node) view;
			final SafecapNavigatorGroup outgoinglinks = new SafecapNavigatorGroup(Messages.NavigatorGroupName_LeftSignal_2004_outgoinglinks,
					"icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(WireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeWireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case RightSignalEditPart.VISUAL_ID: {
			final LinkedList<SafecapAbstractNavigatorItem> result = new LinkedList<>();
			final Node sv = (Node) view;
			final SafecapNavigatorGroup outgoinglinks = new SafecapNavigatorGroup(
					Messages.NavigatorGroupName_RightSignal_2005_outgoinglinks, "icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(WireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeWireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case LeftSpeedLimitEditPart.VISUAL_ID: {
			final LinkedList<SafecapAbstractNavigatorItem> result = new LinkedList<>();
			final Node sv = (Node) view;
			final SafecapNavigatorGroup outgoinglinks = new SafecapNavigatorGroup(
					Messages.NavigatorGroupName_LeftSpeedLimit_2006_outgoinglinks, "icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(WireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeWireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case RightSpeedLimitEditPart.VISUAL_ID: {
			final LinkedList<SafecapAbstractNavigatorItem> result = new LinkedList<>();
			final Node sv = (Node) view;
			final SafecapNavigatorGroup outgoinglinks = new SafecapNavigatorGroup(
					Messages.NavigatorGroupName_RightSpeedLimit_2007_outgoinglinks, "icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(WireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeWireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case RedLeftSignalEditPart.VISUAL_ID: {
			final LinkedList<SafecapAbstractNavigatorItem> result = new LinkedList<>();
			final Node sv = (Node) view;
			final SafecapNavigatorGroup outgoinglinks = new SafecapNavigatorGroup(
					Messages.NavigatorGroupName_RedLeftSignal_2008_outgoinglinks, "icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(WireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeWireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case RedRightSignalEditPart.VISUAL_ID: {
			final LinkedList<SafecapAbstractNavigatorItem> result = new LinkedList<>();
			final Node sv = (Node) view;
			final SafecapNavigatorGroup outgoinglinks = new SafecapNavigatorGroup(
					Messages.NavigatorGroupName_RedRightSignal_2009_outgoinglinks, "icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(WireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeWireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case LeftStopAndGoEditPart.VISUAL_ID: {
			final LinkedList<SafecapAbstractNavigatorItem> result = new LinkedList<>();
			final Node sv = (Node) view;
			final SafecapNavigatorGroup outgoinglinks = new SafecapNavigatorGroup(
					Messages.NavigatorGroupName_LeftStopAndGo_2010_outgoinglinks, "icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(WireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeWireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case RightStopAndGoEditPart.VISUAL_ID: {
			final LinkedList<SafecapAbstractNavigatorItem> result = new LinkedList<>();
			final Node sv = (Node) view;
			final SafecapNavigatorGroup outgoinglinks = new SafecapNavigatorGroup(
					Messages.NavigatorGroupName_RightStopAndGo_2011_outgoinglinks, "icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(WireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeWireEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case SubSchemaNodeEditPart.VISUAL_ID: {
			final LinkedList<SafecapAbstractNavigatorItem> result = new LinkedList<>();
			final Node sv = (Node) view;
			final SafecapNavigatorGroup incominglinks = new SafecapNavigatorGroup(
					Messages.NavigatorGroupName_SubSchemaNode_2015_incominglinks, "icons/incomingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			final SafecapNavigatorGroup outgoinglinks = new SafecapNavigatorGroup(
					Messages.NavigatorGroupName_SubSchemaNode_2015_outgoinglinks, "icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getIncomingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(SegmentEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews, incominglinks, true));
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(SegmentEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews, outgoinglinks, true));
			connectedViews = getIncomingLinksByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeWireEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews, incominglinks, true));
			if (!incominglinks.isEmpty()) {
				result.add(incominglinks);
			}
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case SegmentEditPart.VISUAL_ID: {
			final LinkedList<SafecapAbstractNavigatorItem> result = new LinkedList<>();
			final Edge sv = (Edge) view;
			final SafecapNavigatorGroup target = new SafecapNavigatorGroup(Messages.NavigatorGroupName_Segment_4001_target,
					"icons/linkTargetNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			final SafecapNavigatorGroup source = new SafecapNavigatorGroup(Messages.NavigatorGroupName_Segment_4001_source,
					"icons/linkSourceNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			final SafecapNavigatorGroup incominglinks = new SafecapNavigatorGroup(Messages.NavigatorGroupName_Segment_4001_incominglinks,
					"icons/incomingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getLinksTargetByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(SubSchemaNodeEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target, true));
			connectedViews = getLinksTargetByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(SubSchemaNodeEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			if (!target.isEmpty()) {
				result.add(target);
			}
			if (!source.isEmpty()) {
				result.add(source);
			}
			if (!incominglinks.isEmpty()) {
				result.add(incominglinks);
			}
			return result.toArray();
		}

		case WireEditPart.VISUAL_ID: {
			final LinkedList<SafecapAbstractNavigatorItem> result = new LinkedList<>();
			final Edge sv = (Edge) view;
			final SafecapNavigatorGroup target = new SafecapNavigatorGroup(Messages.NavigatorGroupName_Wire_4002_target,
					"icons/linkTargetNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			final SafecapNavigatorGroup source = new SafecapNavigatorGroup(Messages.NavigatorGroupName_Wire_4002_source,
					"icons/linkSourceNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getLinksSourceByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(LeftSignalEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(RightSignalEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(LeftSpeedLimitEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(RightSpeedLimitEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(RedLeftSignalEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(RedRightSignalEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(LeftStopAndGoEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(RightStopAndGoEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			if (!target.isEmpty()) {
				result.add(target);
			}
			if (!source.isEmpty()) {
				result.add(source);
			}
			return result.toArray();
		}

		case NodeWireEditPart.VISUAL_ID: {
			final LinkedList<SafecapAbstractNavigatorItem> result = new LinkedList<>();
			final Edge sv = (Edge) view;
			final SafecapNavigatorGroup target = new SafecapNavigatorGroup(Messages.NavigatorGroupName_NodeWire_4004_target,
					"icons/linkTargetNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			final SafecapNavigatorGroup source = new SafecapNavigatorGroup(Messages.NavigatorGroupName_NodeWire_4004_source,
					"icons/linkSourceNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getLinksTargetByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(SubSchemaNodeEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target, true));
			connectedViews = getLinksTargetByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(NodeEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv), SafecapVisualIDRegistry.getType(LeftSignalEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(RightSignalEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(LeftSpeedLimitEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(RightSpeedLimitEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(RedLeftSignalEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(RedRightSignalEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(LeftStopAndGoEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SafecapVisualIDRegistry.getType(RightStopAndGoEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source, true));
			if (!target.isEmpty()) {
				result.add(target);
			}
			if (!source.isEmpty()) {
				result.add(source);
			}
			return result.toArray();
		}
		}
		return EMPTY_ARRAY;
	}

	/**
	 * @generated
	 */
	private Collection<View> getLinksSourceByType(Collection<Edge> edges, String type) {
		final LinkedList<View> result = new LinkedList<>();
		for (final Edge nextEdge : edges) {
			final View nextEdgeSource = nextEdge.getSource();
			if (type.equals(nextEdgeSource.getType()) && isOwnView(nextEdgeSource)) {
				result.add(nextEdgeSource);
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection<View> getLinksTargetByType(Collection<Edge> edges, String type) {
		final LinkedList<View> result = new LinkedList<>();
		for (final Edge nextEdge : edges) {
			final View nextEdgeTarget = nextEdge.getTarget();
			if (type.equals(nextEdgeTarget.getType()) && isOwnView(nextEdgeTarget)) {
				result.add(nextEdgeTarget);
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection<View> getOutgoingLinksByType(Collection<? extends View> nodes, String type) {
		final LinkedList<View> result = new LinkedList<>();
		for (final View nextNode : nodes) {
			result.addAll(selectViewsByType(nextNode.getSourceEdges(), type));
		}
		return result;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	private Collection<View> getIncomingLinksByType(Collection<? extends View> nodes, String type) {
		final LinkedList<View> result = new LinkedList<>();
		for (final View nextNode : nodes) {
			result.addAll(selectViewsByType(nextNode.getTargetEdges(), type));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection<View> getChildrenByType(Collection<? extends View> nodes, String type) {
		final LinkedList<View> result = new LinkedList<>();
		for (final View nextNode : nodes) {
			result.addAll(selectViewsByType(nextNode.getChildren(), type));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection<View> getDiagramLinksByType(Collection<Diagram> diagrams, String type) {
		final ArrayList<View> result = new ArrayList<>();
		for (final Diagram nextDiagram : diagrams) {
			result.addAll(selectViewsByType(nextDiagram.getEdges(), type));
		}
		return result;
	}

	// TODO refactor as static method
	/**
	 * @generated
	 */
	private Collection<View> selectViewsByType(Collection<View> views, String type) {
		final ArrayList<View> result = new ArrayList<>();
		for (final View nextView : views) {
			if (type.equals(nextView.getType()) && isOwnView(nextView)) {
				result.add(nextView);
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private boolean isOwnView(View view) {
		return ProjectEditPart.MODEL_ID.equals(SafecapVisualIDRegistry.getModelID(view));
	}

	/**
	 * @generated
	 */
	private Collection<SafecapNavigatorItem> createNavigatorItems(Collection<View> views, Object parent, boolean isLeafs) {
		final ArrayList<SafecapNavigatorItem> result = new ArrayList<>(views.size());
		for (final View nextView : views) {
			result.add(new SafecapNavigatorItem(nextView, parent, isLeafs));
		}
		return result;
	}

	/**
	 * @generated
	 */
	@Override
	public Object getParent(Object element) {
		if (element instanceof SafecapAbstractNavigatorItem) {
			final SafecapAbstractNavigatorItem abstractNavigatorItem = (SafecapAbstractNavigatorItem) element;
			return abstractNavigatorItem.getParent();
		}
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	public boolean hasChildren(Object element) {
		return element instanceof IFile || getChildren(element).length > 0;
	}

}
