package safecap.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;

import safecap.SafecapPackage;
import safecap.diagram.edit.parts.LeftSignalLabelEditPart;
import safecap.diagram.edit.parts.NodeLabelEditPart;
import safecap.diagram.edit.parts.RightSignalLabelEditPart;
import safecap.diagram.edit.parts.SegmentLabelEditPart;
import safecap.diagram.edit.parts.SubSchemaNodeLabelEditPart;
import safecap.diagram.parsers.MessageFormatParser;
import safecap.diagram.parsers.NativeParser;
import safecap.diagram.part.SafecapVisualIDRegistry;

/**
 * @generated
 */
public class SafecapParserProvider extends AbstractProvider implements IParserProvider {

	/**
	 * @generated
	 */
	private IParser nodeLabel_5003Parser;

	/**
	 * @generated
	 */
	private IParser getNodeLabel_5003Parser() {
		if (nodeLabel_5003Parser == null) {
			final EAttribute[] features = new EAttribute[] { SafecapPackage.eINSTANCE.getLabeled_Label() };
			final EAttribute[] editableFeatures = new EAttribute[] { SafecapPackage.eINSTANCE.getLabeled_Label() };
			final MessageFormatParser parser = new MessageFormatParser(features, editableFeatures);
			nodeLabel_5003Parser = parser;
		}
		return nodeLabel_5003Parser;
	}

	/**
	 * @generated
	 */
	private IParser leftSignalLabel_5004Parser;

	/**
	 * @generated
	 */
	private IParser getLeftSignalLabel_5004Parser() {
		if (leftSignalLabel_5004Parser == null) {
			final EAttribute[] features = new EAttribute[] { SafecapPackage.eINSTANCE.getLabeled_Label() };
			final EAttribute[] editableFeatures = new EAttribute[] { SafecapPackage.eINSTANCE.getLabeled_Label() };
			final NativeParser parser = new NativeParser(features, editableFeatures);
			leftSignalLabel_5004Parser = parser;
		}
		return leftSignalLabel_5004Parser;
	}

	/**
	 * @generated
	 */
	private IParser rightSignalLabel_5005Parser;

	/**
	 * @generated
	 */
	private IParser getRightSignalLabel_5005Parser() {
		if (rightSignalLabel_5005Parser == null) {
			final EAttribute[] features = new EAttribute[] { SafecapPackage.eINSTANCE.getLabeled_Label() };
			final EAttribute[] editableFeatures = new EAttribute[] { SafecapPackage.eINSTANCE.getLabeled_Label() };
			final NativeParser parser = new NativeParser(features, editableFeatures);
			rightSignalLabel_5005Parser = parser;
		}
		return rightSignalLabel_5005Parser;
	}

	/**
	 * @generated
	 */
	private IParser subSchemaNodeLabel_5007Parser;

	/**
	 * @generated
	 */
	private IParser getSubSchemaNodeLabel_5007Parser() {
		if (subSchemaNodeLabel_5007Parser == null) {
			final EAttribute[] features = new EAttribute[] { SafecapPackage.eINSTANCE.getLabeled_Label() };
			final EAttribute[] editableFeatures = new EAttribute[] { SafecapPackage.eINSTANCE.getLabeled_Label() };
			final NativeParser parser = new NativeParser(features, editableFeatures);
			subSchemaNodeLabel_5007Parser = parser;
		}
		return subSchemaNodeLabel_5007Parser;
	}

	/**
	 * @generated
	 */
	private IParser segmentLabel_6001Parser;

	/**
	 * @generated
	 */
	private IParser getSegmentLabel_6001Parser() {
		if (segmentLabel_6001Parser == null) {
			final EAttribute[] features = new EAttribute[] { SafecapPackage.eINSTANCE.getLabeled_Label() };
			final EAttribute[] editableFeatures = new EAttribute[] { SafecapPackage.eINSTANCE.getLabeled_Label() };
			final MessageFormatParser parser = new MessageFormatParser(features, editableFeatures);
			segmentLabel_6001Parser = parser;
		}
		return segmentLabel_6001Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case SubSchemaNodeLabelEditPart.VISUAL_ID:
			return getSubSchemaNodeLabel_5007Parser();
		case NodeLabelEditPart.VISUAL_ID:
			return getNodeLabel_5003Parser();
		case LeftSignalLabelEditPart.VISUAL_ID:
			return getLeftSignalLabel_5004Parser();
		case RightSignalLabelEditPart.VISUAL_ID:
			return getRightSignalLabel_5005Parser();
		case SegmentLabelEditPart.VISUAL_ID:
			return getSegmentLabel_6001Parser();
		}
		return null;
	}

	/**
	 * Utility method that consults ParserService
	 * 
	 * @generated
	 */
	public static IParser getParser(IElementType type, EObject object, String parserHint) {
		return ParserService.getInstance().getParser(new HintAdapter(type, object, parserHint));
	}

	/**
	 * @generated
	 */
	@Override
	public IParser getParser(IAdaptable hint) {
		final String vid = hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(SafecapVisualIDRegistry.getVisualID(vid));
		}
		final View view = hint.getAdapter(View.class);
		if (view != null) {
			return getParser(SafecapVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			final IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (SafecapElementTypes.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static class HintAdapter extends ParserHintAdapter {

		/**
		 * @generated
		 */
		private final IElementType elementType;

		/**
		 * @generated
		 */
		public HintAdapter(IElementType type, EObject object, String parserHint) {
			super(object, parserHint);
			assert type != null;
			elementType = type;
		}

		/**
		 * @generated
		 */
		@Override
		public Object getAdapter(Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}

}
