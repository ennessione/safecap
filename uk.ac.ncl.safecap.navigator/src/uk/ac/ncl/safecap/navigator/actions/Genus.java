package uk.ac.ncl.safecap.navigator.actions;

import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import uk.ac.ncl.safecap.diagram.misc.diagramactions.BuildGenus;

public class Genus implements IObjectActionDelegate {

	private ISelection selection;

	public Genus() {
		super();
	}

	@Override
	public void run(IAction action) {
		final DiagramEditPart diagram = getSelectedElement();
		if (diagram != null) {
			final BuildGenus g = new BuildGenus(diagram);
			g.run();
		}
	}

//	private void testEOL(Labeled e) {
//		URI rule = findRule();
//		System.out.println("Found rule: " + rule);
//		EGLManager manager = new EGLManager(rule);
//		try {
//			Object result = manager.execute(e);
//			System.out.println("Result:" + result);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//	}

//	private URI findRule()
//	{
//		try {
//			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
//			for(IProject p: projects) {
//					for(IResource resource : p.members()) {
//						if (resource.getFileExtension() != null && resource.getFileExtension().equals("egl")) {
//							URI uri = resource.getLocationURI();
//							return uri;
//						}
//					}
//			}
//		} catch (CoreException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	private DiagramEditPart getSelectedElement() {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			final Object element = ssel.getFirstElement();
			if (element instanceof DiagramEditPart) {
				return (DiagramEditPart) element;
			}
		}
		return null;
	}

}
