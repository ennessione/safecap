package uk.ac.ncl.safecap.xdata.verification.registry;

import org.eclipse.sapphire.Element;

public interface ICatalogCreationListener {
	void handleNew(Element updated);

	void handleClose(Element updated);
}
