package safecap.diagram.navigator;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITreePathLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

import safecap.Project;
import safecap.commentary.Bridge;
import safecap.commentary.Station;
import safecap.diagram.edit.parts.BridgeEditPart;
import safecap.diagram.edit.parts.LeftSignalEditPart;
import safecap.diagram.edit.parts.LeftSignalLabelEditPart;
import safecap.diagram.edit.parts.LeftSpeedLimitEditPart;
import safecap.diagram.edit.parts.LeftStopAndGoEditPart;
import safecap.diagram.edit.parts.NodeEditPart;
import safecap.diagram.edit.parts.NodeLabelEditPart;
import safecap.diagram.edit.parts.NodeWireEditPart;
import safecap.diagram.edit.parts.ProjectEditPart;
import safecap.diagram.edit.parts.RedLeftSignalEditPart;
import safecap.diagram.edit.parts.RedRightSignalEditPart;
import safecap.diagram.edit.parts.RightSignalEditPart;
import safecap.diagram.edit.parts.RightSignalLabelEditPart;
import safecap.diagram.edit.parts.RightSpeedLimitEditPart;
import safecap.diagram.edit.parts.RightStopAndGoEditPart;
import safecap.diagram.edit.parts.SegmentEditPart;
import safecap.diagram.edit.parts.SegmentLabelEditPart;
import safecap.diagram.edit.parts.StationEditPart;
import safecap.diagram.edit.parts.SubSchemaNodeEditPart;
import safecap.diagram.edit.parts.SubSchemaNodeLabelEditPart;
import safecap.diagram.edit.parts.WireEditPart;
import safecap.diagram.part.SafecapDiagramEditorPlugin;
import safecap.diagram.part.SafecapVisualIDRegistry;
import safecap.diagram.providers.SafecapElementTypes;
import safecap.diagram.providers.SafecapParserProvider;
import safecap.trackside.LeftSpeedLimit;
import safecap.trackside.LeftStopAndGo;
import safecap.trackside.RedLeftSignal;
import safecap.trackside.RedRightSignal;
import safecap.trackside.RightSpeedLimit;
import safecap.trackside.RightStopAndGo;
import safecap.trackside.Wire;

/**
 * @generated
 */
public class SafecapNavigatorLabelProvider extends LabelProvider implements ICommonLabelProvider, ITreePathLabelProvider {

	/**
	 * @generated
	 */
	static {
		SafecapDiagramEditorPlugin.getInstance().getImageRegistry().put("Navigator?UnknownElement", //$NON-NLS-1$
				ImageDescriptor.getMissingImageDescriptor());
		SafecapDiagramEditorPlugin.getInstance().getImageRegistry().put("Navigator?ImageNotFound", //$NON-NLS-1$
				ImageDescriptor.getMissingImageDescriptor());
	}

	/**
	 * @generated
	 */
	@Override
	public void updateLabel(ViewerLabel label, TreePath elementPath) {
		final Object element = elementPath.getLastSegment();
		if (element instanceof SafecapNavigatorItem && !isOwnView(((SafecapNavigatorItem) element).getView())) {
			return;
		}
		label.setText(getText(element));
		label.setImage(getImage(element));
	}

	/**
	 * @generated
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof SafecapNavigatorGroup) {
			final SafecapNavigatorGroup group = (SafecapNavigatorGroup) element;
			return SafecapDiagramEditorPlugin.getInstance().getBundledImage(group.getIcon());
		}

		if (element instanceof SafecapNavigatorItem) {
			final SafecapNavigatorItem navigatorItem = (SafecapNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return super.getImage(element);
			}
			return getImage(navigatorItem.getView());
		}

		return super.getImage(element);
	}

	/**
	 * @generated
	 */
	public Image getImage(View view) {
		switch (SafecapVisualIDRegistry.getVisualID(view)) {
		case ProjectEditPart.VISUAL_ID:
			return getImage("Navigator?Diagram?uk.ac.ncl.safecap.emf?Project", SafecapElementTypes.Project_1000); //$NON-NLS-1$
		case NodeEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?uk.ac.ncl.safecap.emf.schema?Node", SafecapElementTypes.Node_2003); //$NON-NLS-1$
		case LeftSignalEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?uk.ac.ncl.safecap.emf.trackside?LeftSignal", SafecapElementTypes.LeftSignal_2004); //$NON-NLS-1$
		case RightSignalEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?uk.ac.ncl.safecap.emf.trackside?RightSignal", SafecapElementTypes.RightSignal_2005); //$NON-NLS-1$
		case LeftSpeedLimitEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?uk.ac.ncl.safecap.emf.trackside?LeftSpeedLimit", //$NON-NLS-1$
					SafecapElementTypes.LeftSpeedLimit_2006);
		case RightSpeedLimitEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?uk.ac.ncl.safecap.emf.trackside?RightSpeedLimit", //$NON-NLS-1$
					SafecapElementTypes.RightSpeedLimit_2007);
		case RedLeftSignalEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?uk.ac.ncl.safecap.emf.trackside?RedLeftSignal", SafecapElementTypes.RedLeftSignal_2008); //$NON-NLS-1$
		case RedRightSignalEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?uk.ac.ncl.safecap.emf.trackside?RedRightSignal", //$NON-NLS-1$
					SafecapElementTypes.RedRightSignal_2009);
		case LeftStopAndGoEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?uk.ac.ncl.safecap.emf.trackside?LeftStopAndGo", SafecapElementTypes.LeftStopAndGo_2010); //$NON-NLS-1$
		case RightStopAndGoEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?uk.ac.ncl.safecap.emf.trackside?RightStopAndGo", //$NON-NLS-1$
					SafecapElementTypes.RightStopAndGo_2011);
		case StationEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?uk.ac.ncl.safecap.emf.commentary?Station", SafecapElementTypes.Station_2012); //$NON-NLS-1$
		case BridgeEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?uk.ac.ncl.safecap.emf.commentary?Bridge", SafecapElementTypes.Bridge_2013); //$NON-NLS-1$
		case SubSchemaNodeEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?uk.ac.ncl.safecap.emf.schema?SubSchemaNode", SafecapElementTypes.SubSchemaNode_2015); //$NON-NLS-1$
		case SegmentEditPart.VISUAL_ID:
			return getImage("Navigator?Link?uk.ac.ncl.safecap.emf.schema?Segment", SafecapElementTypes.Segment_4001); //$NON-NLS-1$
		case WireEditPart.VISUAL_ID:
			return getImage("Navigator?Link?uk.ac.ncl.safecap.emf.trackside?Wire", SafecapElementTypes.Wire_4002); //$NON-NLS-1$
		case NodeWireEditPart.VISUAL_ID:
			return getImage("Navigator?Link?uk.ac.ncl.safecap.emf.trackside?NodeWire", SafecapElementTypes.NodeWire_4004); //$NON-NLS-1$
		}
		return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private Image getImage(String key, IElementType elementType) {
		final ImageRegistry imageRegistry = SafecapDiagramEditorPlugin.getInstance().getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null && elementType != null && SafecapElementTypes.isKnownElementType(elementType)) {
			image = SafecapElementTypes.getImage(elementType);
			imageRegistry.put(key, image);
		}

		if (image == null) {
			image = imageRegistry.get("Navigator?ImageNotFound"); //$NON-NLS-1$
			imageRegistry.put(key, image);
		}
		return image;
	}

	/**
	 * @generated
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof SafecapNavigatorGroup) {
			final SafecapNavigatorGroup group = (SafecapNavigatorGroup) element;
			return group.getGroupName();
		}

		if (element instanceof SafecapNavigatorItem) {
			final SafecapNavigatorItem navigatorItem = (SafecapNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return null;
			}
			return getText(navigatorItem.getView());
		}

		return super.getText(element);
	}

	/**
	 * @generated
	 */
	public String getText(View view) {
		if (view.getElement() != null && view.getElement().eIsProxy()) {
			return getUnresolvedDomainElementProxyText(view);
		}
		switch (SafecapVisualIDRegistry.getVisualID(view)) {
		case ProjectEditPart.VISUAL_ID:
			return getProject_1000Text(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2003Text(view);
		case LeftSignalEditPart.VISUAL_ID:
			return getLeftSignal_2004Text(view);
		case RightSignalEditPart.VISUAL_ID:
			return getRightSignal_2005Text(view);
		case LeftSpeedLimitEditPart.VISUAL_ID:
			return getLeftSpeedLimit_2006Text(view);
		case RightSpeedLimitEditPart.VISUAL_ID:
			return getRightSpeedLimit_2007Text(view);
		case RedLeftSignalEditPart.VISUAL_ID:
			return getRedLeftSignal_2008Text(view);
		case RedRightSignalEditPart.VISUAL_ID:
			return getRedRightSignal_2009Text(view);
		case LeftStopAndGoEditPart.VISUAL_ID:
			return getLeftStopAndGo_2010Text(view);
		case RightStopAndGoEditPart.VISUAL_ID:
			return getRightStopAndGo_2011Text(view);
		case StationEditPart.VISUAL_ID:
			return getStation_2012Text(view);
		case BridgeEditPart.VISUAL_ID:
			return getBridge_2013Text(view);
		case SubSchemaNodeEditPart.VISUAL_ID:
			return getSubSchemaNode_2015Text(view);
		case SegmentEditPart.VISUAL_ID:
			return getSegment_4001Text(view);
		case WireEditPart.VISUAL_ID:
			return getWire_4002Text(view);
		case NodeWireEditPart.VISUAL_ID:
			return getNodeWire_4004Text(view);
		}
		return getUnknownElementText(view);
	}

	/**
	 * @generated
	 */
	private String getRightStopAndGo_2011Text(View view) {
		final RightStopAndGo domainModelElement = (RightStopAndGo) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getLabel();
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 2011); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getLeftSignal_2004Text(View view) {
		final IParser parser = SafecapParserProvider.getParser(SafecapElementTypes.LeftSignal_2004,
				view.getElement() != null ? view.getElement() : view, SafecapVisualIDRegistry.getType(LeftSignalLabelEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5004); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getSegment_4001Text(View view) {
		final IParser parser = SafecapParserProvider.getParser(SafecapElementTypes.Segment_4001,
				view.getElement() != null ? view.getElement() : view, SafecapVisualIDRegistry.getType(SegmentLabelEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 6001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getRedRightSignal_2009Text(View view) {
		final RedRightSignal domainModelElement = (RedRightSignal) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getLabel();
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 2009); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getWire_4002Text(View view) {
		final Wire domainModelElement = (Wire) view.getElement();
		if (domainModelElement != null) {
			return String.valueOf(domainModelElement.getRuntimeAttributes());
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 4002); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getStation_2012Text(View view) {
		final Station domainModelElement = (Station) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getLabel();
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 2012); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getRightSpeedLimit_2007Text(View view) {
		final RightSpeedLimit domainModelElement = (RightSpeedLimit) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getLabel();
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 2007); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getLeftSpeedLimit_2006Text(View view) {
		final LeftSpeedLimit domainModelElement = (LeftSpeedLimit) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getLabel();
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 2006); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getNode_2003Text(View view) {
		final IParser parser = SafecapParserProvider.getParser(SafecapElementTypes.Node_2003,
				view.getElement() != null ? view.getElement() : view, SafecapVisualIDRegistry.getType(NodeLabelEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5003); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getProject_1000Text(View view) {
		final Project domainModelElement = (Project) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getLabel();
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 1000); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getRedLeftSignal_2008Text(View view) {
		final RedLeftSignal domainModelElement = (RedLeftSignal) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getLabel();
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 2008); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getBridge_2013Text(View view) {
		final Bridge domainModelElement = (Bridge) view.getElement();
		if (domainModelElement != null) {
			return String.valueOf(domainModelElement.getAngle());
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 2013); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getSubSchemaNode_2015Text(View view) {
		final IParser parser = SafecapParserProvider.getParser(SafecapElementTypes.SubSchemaNode_2015,
				view.getElement() != null ? view.getElement() : view,
				SafecapVisualIDRegistry.getType(SubSchemaNodeLabelEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5007); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getNodeWire_4004Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getRightSignal_2005Text(View view) {
		final IParser parser = SafecapParserProvider.getParser(SafecapElementTypes.RightSignal_2005,
				view.getElement() != null ? view.getElement() : view, SafecapVisualIDRegistry.getType(RightSignalLabelEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5005); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getLeftStopAndGo_2010Text(View view) {
		final LeftStopAndGo domainModelElement = (LeftStopAndGo) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getLabel();
		} else {
			SafecapDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 2010); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getUnknownElementText(View view) {
		return "<UnknownElement Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @generated
	 */
	private String getUnresolvedDomainElementProxyText(View view) {
		return "<Unresolved domain element Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
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
	public String getDescription(Object anElement) {
		return null;
	}

	/**
	 * @generated
	 */
	private boolean isOwnView(View view) {
		return ProjectEditPart.MODEL_ID.equals(SafecapVisualIDRegistry.getModelID(view));
	}

}
