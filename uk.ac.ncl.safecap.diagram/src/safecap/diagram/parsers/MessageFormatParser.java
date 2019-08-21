package safecap.diagram.parsers;

import java.text.FieldPosition;
import java.text.MessageFormat;
import java.text.ParsePosition;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.tooling.runtime.parsers.AbstractAttributeParser;
import org.eclipse.osgi.util.NLS;

import safecap.diagram.part.Messages;
import safecap.diagram.part.SafecapDiagramEditorPlugin;

/**
 * @generated
 */
public class MessageFormatParser extends AbstractAttributeParser {

	/**
	 * @generated
	 */
	private String defaultPattern;

	/**
	 * @generated
	 */
	private String defaultEditablePattern;

	/**
	 * @generated
	 */
	private MessageFormat viewProcessor;

	/**
	 * @generated
	 */
	private MessageFormat editorProcessor;

	/**
	 * @generated
	 */
	private MessageFormat editProcessor;

	/**
	 * @generated
	 */
	public MessageFormatParser(EAttribute[] features) {
		super(features);
	}

	/**
	 * @generated
	 */
	public MessageFormatParser(EAttribute[] features, EAttribute[] editableFeatures) {
		super(features, editableFeatures);
	}

	/**
	 * @generated
	 */
	protected String getDefaultPattern() {
		if (defaultPattern == null) {
			final StringBuffer sb = new StringBuffer();
			for (int i = 0; i < features.length; i++) {
				if (i > 0) {
					sb.append(' ');
				}
				sb.append('{');
				sb.append(i);
				sb.append('}');
			}
			defaultPattern = sb.toString();
		}
		return defaultPattern;
	}

	/**
	 * @generated
	 */
	@Override
	public void setViewPattern(String viewPattern) {
		super.setViewPattern(viewPattern);
		viewProcessor = null;
	}

	/**
	 * @generated
	 */
	@Override
	public void setEditorPattern(String editorPattern) {
		super.setEditorPattern(editorPattern);
		editorProcessor = null;
	}

	/**
	 * @generated
	 */
	protected MessageFormat getViewProcessor() {
		if (viewProcessor == null) {
			viewProcessor = new MessageFormat(getViewPattern() == null ? getDefaultPattern() : getViewPattern());
		}
		return viewProcessor;
	}

	/**
	 * @generated
	 */
	protected MessageFormat getEditorProcessor() {
		if (editorProcessor == null) {
			editorProcessor = new MessageFormat(getEditorPattern() == null ? getDefaultEditablePattern() : getEditorPattern());
		}
		return editorProcessor;
	}

	/**
	 * @generated
	 */
	protected String getDefaultEditablePattern() {
		if (defaultEditablePattern == null) {
			final StringBuffer sb = new StringBuffer();
			for (int i = 0; i < editableFeatures.length; i++) {
				if (i > 0) {
					sb.append(' ');
				}
				sb.append('{');
				sb.append(i);
				sb.append('}');
			}
			defaultEditablePattern = sb.toString();
		}
		return defaultEditablePattern;
	}

	/**
	 * @generated
	 */
	@Override
	public void setEditPattern(String editPattern) {
		super.setEditPattern(editPattern);
		editProcessor = null;
	}

	/**
	 * @generated
	 */
	protected MessageFormat getEditProcessor() {
		if (editProcessor == null) {
			editProcessor = new MessageFormat(getEditPattern() == null ? getDefaultEditablePattern() : getEditPattern());
		}
		return editProcessor;
	}

	/**
	 * @generated
	 */
	@Override
	public String getEditString(IAdaptable adapter, int flags) {
		final EObject element = adapter.getAdapter(EObject.class);
		return getEditorProcessor().format(getEditableValues(element), new StringBuffer(), new FieldPosition(0)).toString();
	}

	/**
	 * @generated
	 */
	@Override
	public IParserEditStatus isValidEditString(IAdaptable adapter, String editString) {
		final ParsePosition pos = new ParsePosition(0);
		final Object[] values = getEditProcessor().parse(editString, pos);
		if (values == null) {
			return new ParserEditStatus(SafecapDiagramEditorPlugin.ID, IParserEditStatus.UNEDITABLE,
					NLS.bind(Messages.MessageFormatParser_InvalidInputError, new Integer(pos.getErrorIndex())));
		}
		return validateNewValues(values);
	}

	/**
	 * @generated
	 */
	@Override
	public ICommand getParseCommand(IAdaptable adapter, String newString, int flags) {
		final Object[] values = getEditProcessor().parse(newString, new ParsePosition(0));
		return getParseCommand(adapter, values, flags);
	}

	/**
	 * @generated
	 */
	@Override
	public String getPrintString(IAdaptable adapter, int flags) {
		final EObject element = adapter.getAdapter(EObject.class);
		return getViewProcessor().format(getValues(element), new StringBuffer(), new FieldPosition(0)).toString();
	}

}
