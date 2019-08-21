package safecap.diagram.part;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.common.ui.URIEditorInput;

/**
 * @generated
 */
public class SafecapUriEditorInputTester extends PropertyTester {

	/**
	 * @generated
	 */
	@Override
	public boolean test(Object receiver, String method, Object[] args, Object expectedValue) {
		if (false == receiver instanceof URIEditorInput) {
			return false;
		}
		final URIEditorInput editorInput = (URIEditorInput) receiver;
		return "safecap".equals(editorInput.getURI().fileExtension()); //$NON-NLS-1$
	}

}
