package uk.ac.ncl.safecap.xdata.verification.registry;

import org.eclipse.sapphire.Element;

public interface ICatalogListener {
	void handleUpdate(Element updated);
}
