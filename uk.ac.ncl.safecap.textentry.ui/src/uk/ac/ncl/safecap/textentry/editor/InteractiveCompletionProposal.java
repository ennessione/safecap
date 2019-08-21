package uk.ac.ncl.safecap.textentry.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import uk.ac.ncl.safecap.textentry.types.ITETypeInteractive;

public class InteractiveCompletionProposal implements ICompletionProposal {
	private final int offset;
	private final ITETypeInteractive base;

	public InteractiveCompletionProposal(ITETypeInteractive base, int offset) {
		this.base = base;
		this.offset = offset;
	}

	@Override
	public void apply(final IDocument document) {
		Display.getCurrent().syncExec(new Runnable() {

			@Override
			public void run() {
				try {
					final String replacementString = (String) base.prompt();
					document.replace(offset, 0, replacementString);
				} catch (final Throwable e) {
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	public Point getSelection(IDocument document) {
		return new Point(offset, 0);
	}

	@Override
	public String getAdditionalProposalInfo() {
		return null;
	}

	@Override
	public String getDisplayString() {
		return "Interactive prompt";
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public IContextInformation getContextInformation() {
		return null;
	}

}
