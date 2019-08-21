package safecap.diagram.edit.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetViewMutabilityCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.update.UpdaterLinkDescriptor;

import safecap.commentary.CommentaryPackage;
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
import safecap.diagram.part.SafecapDiagramUpdater;
import safecap.diagram.part.SafecapLinkDescriptor;
import safecap.diagram.part.SafecapNodeDescriptor;
import safecap.diagram.part.SafecapVisualIDRegistry;
import safecap.schema.SchemaPackage;
import safecap.trackside.TracksidePackage;

/**
 * @generated
 */
public class ProjectCanonicalEditPolicy extends CanonicalEditPolicy {

	/**
	 * @generated
	 */
	private Set<EStructuralFeature> myFeaturesToSynchronize;

	/**
	 * @generated
	 */
	@Override
	protected void refreshOnActivate() {
		// Need to activate editpart children before invoking the canonical refresh for
		// EditParts to add event listeners
		final List<?> c = getHost().getChildren();
		for (int i = 0; i < c.size(); i++) {
			((EditPart) c.get(i)).activate();
		}
		super.refreshOnActivate();
	}

	/**
	 * @generated
	 */
	@Override
	protected Set getFeaturesToSynchronize() {
		if (myFeaturesToSynchronize == null) {
			myFeaturesToSynchronize = new HashSet<>();
			myFeaturesToSynchronize.add(SchemaPackage.eINSTANCE.getSchema_Nodes());
			myFeaturesToSynchronize.add(TracksidePackage.eINSTANCE.getTrackside_Equipment());
			myFeaturesToSynchronize.add(CommentaryPackage.eINSTANCE.getComments_Comments());
		}
		return myFeaturesToSynchronize;
	}

	/**
	 * @generated
	 */
	@Override
	@SuppressWarnings("rawtypes")
	protected List getSemanticChildrenList() {
		final View viewObject = (View) getHost().getModel();
		final LinkedList<EObject> result = new LinkedList<>();
		final List<SafecapNodeDescriptor> childDescriptors = SafecapDiagramUpdater.getProject_1000SemanticChildren(viewObject);
		for (final SafecapNodeDescriptor d : childDescriptors) {
			result.add(d.getModelElement());
		}
		return result;
	}

	/**
	 * @generated
	 */
	@Override
	protected boolean isOrphaned(Collection<EObject> semanticChildren, final View view) {
		return isMyDiagramElement(view) && !semanticChildren.contains(view.getElement());
	}

	/**
	 * @generated
	 */
	private boolean isMyDiagramElement(View view) {
		final int visualID = SafecapVisualIDRegistry.getVisualID(view);
		switch (visualID) {
		case SubSchemaNodeEditPart.VISUAL_ID:
		case NodeEditPart.VISUAL_ID:
		case LeftSignalEditPart.VISUAL_ID:
		case RightSignalEditPart.VISUAL_ID:
		case LeftSpeedLimitEditPart.VISUAL_ID:
		case RightSpeedLimitEditPart.VISUAL_ID:
		case RedLeftSignalEditPart.VISUAL_ID:
		case RedRightSignalEditPart.VISUAL_ID:
		case LeftStopAndGoEditPart.VISUAL_ID:
		case RightStopAndGoEditPart.VISUAL_ID:
		case StationEditPart.VISUAL_ID:
		case BridgeEditPart.VISUAL_ID:
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	@Override
	protected void refreshSemantic() {
		if (resolveSemanticElement() == null) {
			return;
		}
		final LinkedList<IAdaptable> createdViews = new LinkedList<>();
		final List<SafecapNodeDescriptor> childDescriptors = SafecapDiagramUpdater
				.getProject_1000SemanticChildren((View) getHost().getModel());
		final LinkedList<View> orphaned = new LinkedList<>();
		// we care to check only views we recognize as ours
		final LinkedList<View> knownViewChildren = new LinkedList<>();
		for (final View v : getViewChildren()) {
			if (isMyDiagramElement(v)) {
				knownViewChildren.add(v);
			}
		}
		// alternative to #cleanCanonicalSemanticChildren(getViewChildren(),
		// semanticChildren)
		//
		// iteration happens over list of desired semantic elements, trying to find best
		// matching View, while original CEP
		// iterates views, potentially losing view (size/bounds) information - i.e. if
		// there are few views to reference same EObject, only last one
		// to answer isOrphaned == true will be used for the domain element
		// representation, see #cleanCanonicalSemanticChildren()
		for (final Iterator<SafecapNodeDescriptor> descriptorsIterator = childDescriptors.iterator(); descriptorsIterator.hasNext();) {
			final SafecapNodeDescriptor next = descriptorsIterator.next();
			final String hint = SafecapVisualIDRegistry.getType(next.getVisualID());
			final LinkedList<View> perfectMatch = new LinkedList<>(); // both semanticElement and hint match that of NodeDescriptor
			for (final View childView : getViewChildren()) {
				final EObject semanticElement = childView.getElement();
				if (next.getModelElement().equals(semanticElement)) {
					if (hint.equals(childView.getType())) {
						perfectMatch.add(childView);
						// actually, can stop iteration over view children here, but
						// may want to use not the first view but last one as a 'real' match (the way
						// original CEP does
						// with its trick with viewToSemanticMap inside #cleanCanonicalSemanticChildren
					}
				}
			}
			if (perfectMatch.size() > 0) {
				descriptorsIterator.remove(); // precise match found no need to create anything for the NodeDescriptor
				// use only one view (first or last?), keep rest as orphaned for further
				// consideration
				knownViewChildren.remove(perfectMatch.getFirst());
			}
		}
		// those left in knownViewChildren are subject to removal - they are our diagram
		// elements we didn't find match to,
		// or those we have potential matches to, and thus need to be recreated,
		// preserving size/location information.
		orphaned.addAll(knownViewChildren);
		//
		final ArrayList<CreateViewRequest.ViewDescriptor> viewDescriptors = new ArrayList<>(
				childDescriptors.size());
		for (final SafecapNodeDescriptor next : childDescriptors) {
			final String hint = SafecapVisualIDRegistry.getType(next.getVisualID());
			final IAdaptable elementAdapter = new CanonicalElementAdapter(next.getModelElement(), hint);
			final CreateViewRequest.ViewDescriptor descriptor = new CreateViewRequest.ViewDescriptor(elementAdapter, Node.class, hint,
					ViewUtil.APPEND, false, host().getDiagramPreferencesHint());
			viewDescriptors.add(descriptor);
		}

		final boolean changed = deleteViews(orphaned.iterator());
		//
		final CreateViewRequest request = getCreateViewRequest(viewDescriptors);
		final Command cmd = getCreateViewCommand(request);
		if (cmd != null && cmd.canExecute()) {
			SetViewMutabilityCommand.makeMutable(new EObjectAdapter(host().getNotationView())).execute();
			executeCommand(cmd);
			@SuppressWarnings("unchecked")
			final List<IAdaptable> nl = (List<IAdaptable>) request.getNewObject();
			createdViews.addAll(nl);
		}
		if (changed || createdViews.size() > 0) {
			postProcessRefreshSemantic(createdViews);
		}

		final Collection<IAdaptable> createdConnectionViews = refreshConnections();

		if (createdViews.size() > 1) {
			// perform a layout of the container
			final DeferredLayoutCommand layoutCmd = new DeferredLayoutCommand(host().getEditingDomain(), createdViews, host());
			executeCommand(new ICommandProxy(layoutCmd));
		}

		createdViews.addAll(createdConnectionViews);

		makeViewsImmutable(createdViews);
	}

	/**
	 * @generated
	 */
	private Collection<IAdaptable> refreshConnections() {
		final Domain2Notation domain2NotationMap = new Domain2Notation();
		final Collection<SafecapLinkDescriptor> linkDescriptors = collectAllLinks(getDiagram(), domain2NotationMap);
		final Collection existingLinks = new LinkedList(getDiagram().getEdges());
		for (final Iterator linksIterator = existingLinks.iterator(); linksIterator.hasNext();) {
			final Edge nextDiagramLink = (Edge) linksIterator.next();
			final int diagramLinkVisualID = SafecapVisualIDRegistry.getVisualID(nextDiagramLink);
			if (diagramLinkVisualID == -1) {
				if (nextDiagramLink.getSource() != null && nextDiagramLink.getTarget() != null) {
					linksIterator.remove();
				}
				continue;
			}
			final EObject diagramLinkObject = nextDiagramLink.getElement();
			final EObject diagramLinkSrc = nextDiagramLink.getSource().getElement();
			final EObject diagramLinkDst = nextDiagramLink.getTarget().getElement();
			for (final Iterator<SafecapLinkDescriptor> linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator
					.hasNext();) {
				final SafecapLinkDescriptor nextLinkDescriptor = linkDescriptorsIterator.next();
				if (diagramLinkObject == nextLinkDescriptor.getModelElement() && diagramLinkSrc == nextLinkDescriptor.getSource()
						&& diagramLinkDst == nextLinkDescriptor.getDestination()
						&& diagramLinkVisualID == nextLinkDescriptor.getVisualID()) {
					linksIterator.remove();
					linkDescriptorsIterator.remove();
					break;
				}
			}
		}
		deleteViews(existingLinks.iterator());
		return createConnections(linkDescriptors, domain2NotationMap);
	}

	/**
	 * @generated
	 */
	private Collection<SafecapLinkDescriptor> collectAllLinks(View view, Domain2Notation domain2NotationMap) {
		if (!ProjectEditPart.MODEL_ID.equals(SafecapVisualIDRegistry.getModelID(view))) {
			return Collections.emptyList();
		}
		final LinkedList<SafecapLinkDescriptor> result = new LinkedList<>();
		switch (SafecapVisualIDRegistry.getVisualID(view)) {
		case ProjectEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getProject_1000ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case SubSchemaNodeEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getSubSchemaNode_2015ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case NodeEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getNode_2003ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case LeftSignalEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getLeftSignal_2004ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case RightSignalEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getRightSignal_2005ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case LeftSpeedLimitEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getLeftSpeedLimit_2006ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case RightSpeedLimitEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getRightSpeedLimit_2007ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case RedLeftSignalEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getRedLeftSignal_2008ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case RedRightSignalEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getRedRightSignal_2009ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case LeftStopAndGoEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getLeftStopAndGo_2010ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case RightStopAndGoEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getRightStopAndGo_2011ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case StationEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getStation_2012ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case BridgeEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getBridge_2013ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case SegmentEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getSegment_4001ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case WireEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getWire_4002ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		case NodeWireEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(SafecapDiagramUpdater.getNodeWire_4004ContainedLinks(view));
			}
			domain2NotationMap.putView(view.getElement(), view);
			break;
		}
		}
		for (final Iterator children = view.getChildren().iterator(); children.hasNext();) {
			result.addAll(collectAllLinks((View) children.next(), domain2NotationMap));
		}
		for (final Iterator edges = view.getSourceEdges().iterator(); edges.hasNext();) {
			result.addAll(collectAllLinks((View) edges.next(), domain2NotationMap));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection<IAdaptable> createConnections(Collection<SafecapLinkDescriptor> linkDescriptors,
			Domain2Notation domain2NotationMap) {
		final LinkedList<IAdaptable> adapters = new LinkedList<>();
		for (final SafecapLinkDescriptor nextLinkDescriptor : linkDescriptors) {
			final EditPart sourceEditPart = getSourceEditPart(nextLinkDescriptor, domain2NotationMap);
			final EditPart targetEditPart = getTargetEditPart(nextLinkDescriptor, domain2NotationMap);
			if (sourceEditPart == null || targetEditPart == null) {
				continue;
			}
			final CreateConnectionViewRequest.ConnectionViewDescriptor descriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor(
					nextLinkDescriptor.getSemanticAdapter(), SafecapVisualIDRegistry.getType(nextLinkDescriptor.getVisualID()),
					ViewUtil.APPEND, false, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint());
			final CreateConnectionViewRequest ccr = new CreateConnectionViewRequest(descriptor);
			ccr.setType(org.eclipse.gef.RequestConstants.REQ_CONNECTION_START);
			ccr.setSourceEditPart(sourceEditPart);
			sourceEditPart.getCommand(ccr);
			ccr.setTargetEditPart(targetEditPart);
			ccr.setType(org.eclipse.gef.RequestConstants.REQ_CONNECTION_END);
			final Command cmd = targetEditPart.getCommand(ccr);
			if (cmd != null && cmd.canExecute()) {
				executeCommand(cmd);
				final IAdaptable viewAdapter = (IAdaptable) ccr.getNewObject();
				if (viewAdapter != null) {
					adapters.add(viewAdapter);
				}
			}
		}
		return adapters;
	}

	/**
	 * @generated
	 */
	private EditPart getEditPart(EObject domainModelElement, Domain2Notation domain2NotationMap) {
		final View view = domain2NotationMap.get(domainModelElement);
		if (view != null) {
			return (EditPart) getHost().getViewer().getEditPartRegistry().get(view);
		}
		return null;
	}

	/**
	 * @generated
	 */
	private Diagram getDiagram() {
		return ((View) getHost().getModel()).getDiagram();
	}

	/**
	 * @generated
	 */
	private EditPart getSourceEditPart(UpdaterLinkDescriptor descriptor, Domain2Notation domain2NotationMap) {
		return getEditPart(descriptor.getSource(), domain2NotationMap);
	}

	/**
	 * @generated
	 */
	private EditPart getTargetEditPart(UpdaterLinkDescriptor descriptor, Domain2Notation domain2NotationMap) {
		return getEditPart(descriptor.getDestination(), domain2NotationMap);
	}

	/**
	 * @generated
	 */
	protected final EditPart getHintedEditPart(EObject domainModelElement, Domain2Notation domain2NotationMap, int hintVisualId) {
		final View view = domain2NotationMap.getHinted(domainModelElement, SafecapVisualIDRegistry.getType(hintVisualId));
		if (view != null) {
			return (EditPart) getHost().getViewer().getEditPartRegistry().get(view);
		}
		return null;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("serial")
	protected static class Domain2Notation extends HashMap<EObject, View> {
		/**
		 * @generated
		 */
		public boolean containsDomainElement(EObject domainElement) {
			return containsKey(domainElement);
		}

		/**
		 * @generated
		 */
		public View getHinted(EObject domainEObject, String hint) {
			return get(domainEObject);
		}

		/**
		 * @generated
		 */
		public void putView(EObject domainElement, View view) {
			if (!containsKey(view.getElement())) {
				put(domainElement, view);
			}
		}

	}
}
