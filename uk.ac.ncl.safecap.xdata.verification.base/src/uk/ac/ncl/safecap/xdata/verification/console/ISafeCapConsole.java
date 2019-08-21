package uk.ac.ncl.safecap.xdata.verification.console;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;

import safecap.Project;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xmldata.DataContext;

public interface ISafeCapConsole {
	RootCatalog getRootCatalog();

	DataContext getDataContext();

	Project getProject();

	IGraphicalEditPart getGraphicalEditPart();

	void out(String message);

	void err(String message);
}
