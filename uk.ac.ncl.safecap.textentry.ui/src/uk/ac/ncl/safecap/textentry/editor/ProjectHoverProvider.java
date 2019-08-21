package uk.ac.ncl.safecap.textentry.editor;

import org.eclipse.core.resources.IProjectNatureDescriptor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;

public class ProjectHoverProvider implements ITextHover {

	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		// TODO this is logic for .project file to show nature description on hover.
		// Replace with your language logic!
		final String contents = textViewer.getDocument().get();
		final int offset = hoverRegion.getOffset();
		final int endIndex = contents.indexOf("</nature>", offset);
		if (endIndex == -1) {
			return "";
		}
		final int startIndex = contents.substring(0, offset).lastIndexOf("<nature>");
		if (startIndex == -1) {
			return "";
		}
		final String selection = contents.substring(startIndex + "<nature>".length(), endIndex);

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProjectNatureDescriptor[] natureDescriptors = workspace.getNatureDescriptors();
		for (int i = 0; i < natureDescriptors.length; i++) {
			if (natureDescriptors[i].getNatureId().equals(selection)) {
				return natureDescriptors[i].getLabel();
			}
		}
		return "";
	}

	@Override
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return new Region(offset, 0);
	}
}