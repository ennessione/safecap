package uk.ac.ncl.safecap.gui.infographs;

import org.eclipse.emf.ecore.EObject;

public class SimpleConnection extends Connection {

	public SimpleConnection(INodeMapper mapper, EObject source, EObject target) {
		super(mapper, source, target);
	}
}
