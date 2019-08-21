package uk.ac.ncl.safecap.diagram.heightmap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Edge;

import safecap.schema.Segment;

public class HeightmapSectionFilter implements org.eclipse.jface.viewers.IFilter {
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof EditPart) {
			final Object model = ((EditPart) toTest).getModel();
			if (model instanceof Edge) {
				final Edge edge = (Edge) model;
				final EObject obj = edge.getElement();
				if (obj instanceof Segment) {
					return true;
				}
			}
		}
		return false;
	}

}
