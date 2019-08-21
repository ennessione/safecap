package safecap.diagram.edit.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.LabelDirectEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.diagram.ui.label.ILabelDelegate;
import org.eclipse.gmf.runtime.diagram.ui.label.WrappingLabelDelegate;
import org.eclipse.gmf.runtime.diagram.ui.render.editparts.RenderedDiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.tools.TextDirectEditManager;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.gmf.runtime.notation.FontStyle;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.directedit.TextDirectEditManager2;
import org.eclipse.gmf.tooling.runtime.draw2d.labels.SimpleLabelDelegate;
import org.eclipse.gmf.tooling.runtime.edit.policies.labels.IRefreshableFeedbackEditPolicy;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;

import safecap.diagram.edit.policies.SafecapTextSelectionEditPolicy;
import safecap.diagram.part.SafecapVisualIDRegistry;
import safecap.diagram.providers.SafecapElementTypes;
import safecap.diagram.providers.SafecapParserProvider;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackFonts;
import uk.ac.ncl.safecap.diagram.misc.visual.ZoomLevels;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;

/**
 * @generated
 */
public class LeftSignalLabelEditPart extends LabelEditPart implements ITextAwareEditPart, IBorderItemEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 5004;

	/**
	 * @generated
	 */
	private DirectEditManager manager;

	/**
	 * @generated
	 */
	private IParser parser;

	/**
	 * @generated
	 */
	private List<?> parserElements;

	/**
	 * @generated
	 */
	private String defaultText;

	/**
	 * @generated
	 */
	private ILabelDelegate labelDelegate;

	/**
	 * @generated
	 */
	static {
		registerSnapBackPosition(SafecapVisualIDRegistry.getType(safecap.diagram.edit.parts.LeftSignalLabelEditPart.VISUAL_ID),
				new Point(0, 0));
	}

	/**
	 * @generated
	 */
	public LeftSignalLabelEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new LabelDirectEditPolicy());
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new SafecapTextSelectionEditPolicy());
	}

	/**
	 * @generated
	 */
	@Override
	public IBorderItemLocator getBorderItemLocator() {
		final IFigure parentFigure = getFigure().getParent();
		if (parentFigure != null && parentFigure.getLayoutManager() != null) {
			final Object constraint = parentFigure.getLayoutManager().getConstraint(getFigure());
			return (IBorderItemLocator) constraint;
		}
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	public void refreshBounds() {
		final int x = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_X())).intValue();
		final int y = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_Y())).intValue();
		final int width = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Width())).intValue();
		final int height = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Height())).intValue();
		getBorderItemLocator().setConstraint(new Rectangle(x, y, width, height));
	}

	/**
	 * @generated
	 */
	protected String getLabelTextHelper(IFigure figure) {
		if (figure instanceof WrappingLabel) {
			return ((WrappingLabel) figure).getText();
		} else if (figure instanceof Label) {
			return ((Label) figure).getText();
		} else {
			return getLabelDelegate().getText();
		}
	}

	/**
	 * @generated
	 */
	protected void setLabelTextHelper(IFigure figure, String text) {
		if (figure instanceof WrappingLabel) {
			((WrappingLabel) figure).setText(text);
		} else if (figure instanceof Label) {
			((Label) figure).setText(text);
		} else {
			getLabelDelegate().setText(text);
		}
	}

	/**
	 * @generated
	 */
	protected Image getLabelIconHelper(IFigure figure) {
		if (figure instanceof WrappingLabel) {
			return ((WrappingLabel) figure).getIcon();
		} else if (figure instanceof Label) {
			return ((Label) figure).getIcon();
		} else {
			return getLabelDelegate().getIcon(0);
		}
	}

	/**
	 * @generated
	 */
	protected void setLabelIconHelper(IFigure figure, Image icon) {
		if (figure instanceof WrappingLabel) {
			((WrappingLabel) figure).setIcon(icon);
			return;
		} else if (figure instanceof Label) {
			((Label) figure).setIcon(icon);
			return;
		} else {
			getLabelDelegate().setIcon(icon, 0);
		}
	}

	/**
	 * @generated
	 */
	public void setLabel(IFigure figure) {
		unregisterVisuals();
		setFigure(figure);
		defaultText = getLabelTextHelper(figure);
		registerVisuals();
		refreshVisuals();
	}

	/**
	 * @generated
	 */
	@Override
	@SuppressWarnings("rawtypes")
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	@Override
	public IGraphicalEditPart getChildBySemanticHint(String semanticHint) {
		return null;
	}

	/**
	 * @generated
	 */
	protected EObject getParserElement() {
		return resolveSemanticElement();
	}

	/**
	 * @generated
	 */
	protected Image getLabelIcon() {
		return null;
	}

	/**
	 * @generated
	 */
	protected String getLabelText() {
		String text = null;
		final EObject parserElement = getParserElement();
		if (parserElement != null && getParser() != null) {
			text = getParser().getPrintString(new EObjectAdapter(parserElement), getParserOptions().intValue());
		}
		if (text == null || text.length() == 0) {
			text = defaultText;
		}
		return text;
	}

	/**
	 * @generated
	 */
	@Override
	public void setLabelText(String text) {
		setLabelTextHelper(getFigure(), text);
		refreshSelectionFeedback();
	}

	/**
	 * @generated
	 */
	@Override
	public String getEditText() {
		if (getParserElement() == null || getParser() == null) {
			return ""; //$NON-NLS-1$
		}
		return getParser().getEditString(new EObjectAdapter(getParserElement()), getParserOptions().intValue());
	}

	/**
	 * @generated
	 */
	protected boolean isEditable() {
		return getParser() != null;
	}

	/**
	 * @generated
	 */
	@Override
	public ICellEditorValidator getEditTextValidator() {
		return new ICellEditorValidator() {

			@Override
			public String isValid(final Object value) {
				if (value instanceof String) {
					final EObject element = getParserElement();
					final IParser parser = getParser();
					try {
						final IParserEditStatus valid = (IParserEditStatus) getEditingDomain()
								.runExclusive(new RunnableWithResult.Impl<IParserEditStatus>() {

									@Override
									public void run() {
										setResult(parser.isValidEditString(new EObjectAdapter(element), (String) value));
									}
								});
						return valid.getCode() == IParserEditStatus.EDITABLE ? null : valid.getMessage();
					} catch (final InterruptedException ie) {
						ie.printStackTrace();
					}
				}

				// shouldn't get here
				return null;
			}
		};
	}

	/**
	 * @generated
	 */
	@Override
	public IContentAssistProcessor getCompletionProcessor() {
		if (getParserElement() == null || getParser() == null) {
			return null;
		}
		return getParser().getCompletionProcessor(new EObjectAdapter(getParserElement()));
	}

	/**
	 * @generated
	 */
	@Override
	public ParserOptions getParserOptions() {
		return ParserOptions.NONE;
	}

	/**
	 * @generated
	 */
	@Override
	public IParser getParser() {
		if (parser == null) {
			parser = SafecapParserProvider.getParser(SafecapElementTypes.LeftSignal_2004, getParserElement(),
					SafecapVisualIDRegistry.getType(safecap.diagram.edit.parts.LeftSignalLabelEditPart.VISUAL_ID));
		}
		return parser;
	}

	/**
	 * @generated
	 */
	protected DirectEditManager getManager() {
		if (manager == null) {
			setManager(new TextDirectEditManager2(this, null, SafecapEditPartFactory.getTextCellEditorLocator(this)));
		}
		return manager;
	}

	/**
	 * @generated
	 */
	protected void setManager(DirectEditManager manager) {
		this.manager = manager;
	}

	/**
	 * @generated
	 */
	protected void performDirectEdit() {
		getManager().show();
	}

	/**
	 * @generated
	 */
	protected void performDirectEdit(Point eventLocation) {
		if (getManager().getClass() == TextDirectEditManager2.class) {
			((TextDirectEditManager2) getManager()).show(eventLocation.getSWTPoint());
		}
	}

	/**
	 * @generated
	 */
	private void performDirectEdit(char initialCharacter) {
		if (getManager() instanceof TextDirectEditManager) {
			((TextDirectEditManager) getManager()).show(initialCharacter);
		} else //
		if (getManager() instanceof TextDirectEditManager2) {
			((TextDirectEditManager2) getManager()).show(initialCharacter);
		} else //
		{
			performDirectEdit();
		}
	}

	/**
	 * @generated
	 */
	@Override
	protected void performDirectEditRequest(Request request) {
		final Request theRequest = request;
		try {
			getEditingDomain().runExclusive(new Runnable() {

				@Override
				public void run() {
					if (isActive() && isEditable()) {
						if (theRequest.getExtendedData()
								.get(RequestConstants.REQ_DIRECTEDIT_EXTENDEDDATA_INITIAL_CHAR) instanceof Character) {
							final Character initialChar = (Character) theRequest.getExtendedData()
									.get(RequestConstants.REQ_DIRECTEDIT_EXTENDEDDATA_INITIAL_CHAR);
							performDirectEdit(initialChar.charValue());
						} else if (theRequest instanceof DirectEditRequest && getEditText().equals(getLabelText())) {
							final DirectEditRequest editRequest = (DirectEditRequest) theRequest;
							performDirectEdit(editRequest.getLocation());
						} else {
							performDirectEdit();
						}
					}
				}
			});
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshLabel();
		refreshFont();
		refreshFontColor();
		refreshUnderline();
		refreshStrikeThrough();
	}

	/**
	 * @generated
	 */
	protected void refreshLabel() {
		setLabelTextHelper(getFigure(), getLabelText());
		setLabelIconHelper(getFigure(), getLabelIcon());
		refreshSelectionFeedback();
	}

	/**
	 * @generated
	 */
	protected void refreshUnderline() {
		final FontStyle style = (FontStyle) getFontStyleOwnerView().getStyle(NotationPackage.eINSTANCE.getFontStyle());
		if (style != null && getFigure() instanceof WrappingLabel) {
			((WrappingLabel) getFigure()).setTextUnderline(style.isUnderline());
		}
	}

	/**
	 * @generated
	 */
	protected void refreshStrikeThrough() {
		final FontStyle style = (FontStyle) getFontStyleOwnerView().getStyle(NotationPackage.eINSTANCE.getFontStyle());
		if (style != null && getFigure() instanceof WrappingLabel) {
			((WrappingLabel) getFigure()).setTextStrikeThrough(style.isStrikeThrough());
		}
	}

	/**
	 * @generated
	 */
	@Override
	protected void refreshFont() {
		final FontStyle style = (FontStyle) getFontStyleOwnerView().getStyle(NotationPackage.eINSTANCE.getFontStyle());
		if (style != null) {
			final FontData fontData = new FontData(style.getFontName(), style.getFontHeight(),
					(style.isBold() ? SWT.BOLD : SWT.NORMAL) | (style.isItalic() ? SWT.ITALIC : SWT.NORMAL));
			setFont(fontData);
		}
	}

	/**
	 * @generated
	 */
	private void refreshSelectionFeedback() {
		requestEditPolicyFeedbackRefresh(EditPolicy.PRIMARY_DRAG_ROLE);
		requestEditPolicyFeedbackRefresh(EditPolicy.SELECTION_FEEDBACK_ROLE);
	}

	/**
	 * @generated
	 */
	private void requestEditPolicyFeedbackRefresh(String editPolicyKey) {
		final Object editPolicy = getEditPolicy(editPolicyKey);
		if (editPolicy instanceof IRefreshableFeedbackEditPolicy) {
			((IRefreshableFeedbackEditPolicy) editPolicy).refreshFeedback();
		}
	}

	/**
	 * @generated
	 */
	@Override
	protected void setFontColor(Color color) {
		getFigure().setForegroundColor(color);
	}

	/**
	 * @generated
	 */
	@Override
	protected void addSemanticListeners() {
		if (getParser() instanceof ISemanticParser) {
			final EObject element = resolveSemanticElement();
			parserElements = ((ISemanticParser) getParser()).getSemanticElementsBeingParsed(element);
			for (int i = 0; i < parserElements.size(); i++) {
				addListenerFilter("SemanticModel" + i, this, (EObject) parserElements.get(i)); //$NON-NLS-1$
			}
		} else {
			super.addSemanticListeners();
		}
	}

	/**
	 * @generated
	 */
	@Override
	protected void removeSemanticListeners() {
		if (parserElements != null) {
			for (int i = 0; i < parserElements.size(); i++) {
				removeListenerFilter("SemanticModel" + i); //$NON-NLS-1$
			}
		} else {
			super.removeSemanticListeners();
		}
	}

	/**
	 * @generated
	 */
	@Override
	protected AccessibleEditPart getAccessibleEditPart() {
		if (accessibleEP == null) {
			accessibleEP = new AccessibleGraphicalEditPart() {

				@Override
				public void getName(AccessibleEvent e) {
					e.result = getLabelTextHelper(getFigure());
				}
			};
		}
		return accessibleEP;
	}

	/**
	 * @generated
	 */
	private View getFontStyleOwnerView() {
		return getPrimaryView();
	}

	/**
	 * @generated
	 */
	private ILabelDelegate getLabelDelegate() {
		if (labelDelegate == null) {
			final IFigure label = getFigure();
			if (label instanceof WrappingLabel) {
				labelDelegate = new WrappingLabelDelegate((WrappingLabel) label);
			} else {
				labelDelegate = new SimpleLabelDelegate((Label) label);
			}
		}
		return labelDelegate;
	}

	/**
	 * @generated
	 */
	@Override
	public Object getAdapter(Class key) {
		if (ILabelDelegate.class.equals(key)) {
			return getLabelDelegate();
		}
		return super.getAdapter(key);
	}

	/**
	 * @generated
	 */
	@Override
	protected void handleNotificationEvent(Notification event) {
		final Object feature = event.getFeature();
		if (NotationPackage.eINSTANCE.getFontStyle_FontColor().equals(feature)) {
			final Integer c = (Integer) event.getNewValue();
			setFontColor(DiagramColorRegistry.getInstance().getColor(c));
		} else if (NotationPackage.eINSTANCE.getFontStyle_Underline().equals(feature)) {
			refreshUnderline();
		} else if (NotationPackage.eINSTANCE.getFontStyle_StrikeThrough().equals(feature)) {
			refreshStrikeThrough();
		} else if (NotationPackage.eINSTANCE.getFontStyle_FontHeight().equals(feature)
				|| NotationPackage.eINSTANCE.getFontStyle_FontName().equals(feature)
				|| NotationPackage.eINSTANCE.getFontStyle_Bold().equals(feature)
				|| NotationPackage.eINSTANCE.getFontStyle_Italic().equals(feature)) {
			refreshFont();
		} else {
			if (getParser() != null && getParser().isAffectingEvent(event, getParserOptions().intValue())) {
				refreshLabel();
			}
			if (getParser() instanceof ISemanticParser) {
				final ISemanticParser modelParser = (ISemanticParser) getParser();
				if (modelParser.areSemanticElementsAffected(null, event)) {
					removeSemanticListeners();
					if (resolveSemanticElement() != null) {
						addSemanticListeners();
					}
					refreshLabel();
				}
			}
		}
		super.handleNotificationEvent(event);
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected IFigure createFigure() {
		final IFigure label = createFigurePrim();
		defaultText = getLabelTextHelper(label);
		label.setFont(TrackFonts.FONT_SMALLER);
		return label;
	}

	/**
	 * @generated
	 */
	protected IFigure createFigurePrim() {
		return new LineLabelDes();
	}

	/**
	 * @generated NOT
	 */
	public class LineLabelDes extends WrappingLabel {
		private ZoomManager zoomManager = null;
		private Signal signal = null;

		/**
		 * @generated NOT
		 */
		public LineLabelDes() {
			final EditPartViewer viewer = getViewer();
			final Node node = (Node) getModel();
			signal = (Signal) node.getElement();
			final RenderedDiagramRootEditPart root = (RenderedDiagramRootEditPart) viewer.getRootEditPart();
			zoomManager = root.getZoomManager();
			setText("");
		}

		/**
		 * @generated NOT
		 * @Override
		 */
		@Override
		public boolean isVisible() {

			return ZoomLevels.isNormal(zoomManager) && ExtensionUtil.isSignalsVisible(signal);
		}

	}

}
