package uk.ac.ncl.safecap.diagram.misc.fa;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.TreeSearch;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;

public class FAEventFilteringNodeFigure extends DefaultSizeNodeFigure {

	public FAEventFilteringNodeFigure(int width, int height) {
		super(width, height);
	}

	@Override
	public IFigure findFigureAt(int x, int y, TreeSearch ss) {
		for (final Object f : getChildren()) {
			if (f instanceof IFigure) {
				final IFigure ff = (IFigure) f;
				if (ff.containsPoint(x, y)) {
					return ff;
				}
			}
		}
		return null;
	}

}
