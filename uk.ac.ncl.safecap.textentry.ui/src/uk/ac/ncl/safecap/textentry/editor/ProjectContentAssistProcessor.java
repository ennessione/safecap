package uk.ac.ncl.safecap.textentry.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import uk.ac.ncl.safecap.textentry.core.TEPart;
import uk.ac.ncl.safecap.textentry.core.TEPlugin;
import uk.ac.ncl.safecap.textentry.core.TEUtil;
import uk.ac.ncl.safecap.textentry.types.ITEType;
import uk.ac.ncl.safecap.textentry.types.ITETypeInteractive;

public class ProjectContentAssistProcessor implements IContentAssistProcessor {

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		try {
			final List<ICompletionProposal> proposals = new ArrayList<>();

			final String text = viewer.getDocument().get();

			final char activationCharacter = text.charAt(offset > 0 ? offset - 1 : offset);

			if (activationCharacter == ':') { // entry completion
				final TEPart part = TEUtil.getEnclosingSchemaInfo(text, offset);
				if (part != null) { // have schema
					final String entryName = TEUtil.getEntryName(text, offset);
					if (entryName != null) {
						final String entryType = part.getString(entryName);
						if (entryType != null) {
							final ITEType typeInfo = TEPlugin.getTERegistry().getTypeInfo(entryType);
							if (typeInfo != null) {
								if (typeInfo instanceof ITETypeInteractive) {
									final ITETypeInteractive ti = (ITETypeInteractive) typeInfo;
									proposals.add(new InteractiveCompletionProposal(ti, offset));
								} else {
									for (final Object s : typeInfo.suggest(part, "")) {
										proposals.add(makeSimpleProposal(entryType, offset, s.toString()));
									}
								}
							}
						}
					}
				}
			}

			return proposals.toArray(new ICompletionProposal[proposals.size()]);
		} catch (final Throwable e) {
		}

		return new ICompletionProposal[0];
	}

	private static ICompletionProposal makeSimpleProposal(String type, int offset, String value) {
		return new CompletionProposal(value, offset, 0, 4);
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { '.', ':' }; // NON-NLS-1
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

}