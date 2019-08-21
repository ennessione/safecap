package uk.ac.ncl.safecap.textentry.editor;

import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;

import uk.ac.ncl.safecap.textentry.core.TEPart;
import uk.ac.ncl.safecap.textentry.core.TEPlugin;
import uk.ac.ncl.safecap.textentry.core.TEUtil;
import uk.ac.ncl.safecap.textentry.types.ITEType;

public class ProjectAutoEditStrategy implements IAutoEditStrategy {

	@Override
	public void customizeDocumentCommand(IDocument document, DocumentCommand command) {

		if ("{".equals(command.text)) { // open block
			final String type = TEUtil.getProtoSchemaType(document.get(), command.offset);
			if (type != null) {
				final TEPart info = TEPlugin.getTERegistry().getSchemaFor(type);
				if (info != null) {
					final StringBuilder sb = new StringBuilder();
					for (final Object key : info.getRecordKeys()) {
						sb.append("\n\t");
						sb.append(key.toString());
						sb.append(": ");
						final String typeName = info.getString(key.toString());
						if (typeName != null) {
							final ITEType entryTypeInfo = TEPlugin.getTERegistry().getTypeInfo(typeName);
							if (entryTypeInfo != null) {
								final String bestGuess = entryTypeInfo.bestGuess(null, key.toString());
								if (bestGuess != null) {
									sb.append(bestGuess);
								}
							}
						}
						sb.append("// value of type " + info.getString(key.toString()));
					}
					command.text += sb.toString() + "\n}";
				}

			}

			return;
		}
	}

}
