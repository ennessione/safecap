package uk.ac.ncl.safecap.navigator;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import safecap.Project;
import safecap.model.Ambit;
import safecap.model.Line;
import safecap.model.Route;
import uk.ac.ncl.safecap.common.resources.SafecapModelRegistry;
import uk.ac.ncl.safecap.diagram.misc.highlights.Highlight;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class SCNavigator extends CommonNavigator implements ISelectionChangedListener, ITabbedPropertySheetPageContributor {

	// @Override
	// protected Object getInitialInput() {
	// // TODO Auto-generated method stub
	// return ResourcesPlugin.getWorkspace().getRoot();
	// }

	@Override
	public void createPartControl(Composite aParent) {
		super.createPartControl(aParent);
		super.getCommonViewer().addSelectionChangedListener(this);
	}

//	@Override
//	protected void handleDoubleClick(DoubleClickEvent anEvent) {
//		IStructuredSelection selection = (IStructuredSelection) anEvent.getSelection();
//		Object element = selection.getFirstElement();
//
//		try {
//			if (element instanceof FileWrap) {
//				FileWrap fw = (FileWrap) element;
//				IFile file = fw.file;
//				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
//				IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
//				page.openEditor(new FileEditorInput(file), desc.getId(), true);
//				return;
//			}
//		} catch (PartInitException e) {
//			e.printStackTrace();
//		}
//
//
//		super.handleDoubleClick(anEvent);
//	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		final ISelection selection = event.getSelection();
		final IStructuredSelection sel = (IStructuredSelection) selection;
		Object element = sel.getFirstElement();

		if (element instanceof EObject) {
			final EObject ee = (EObject) element;
			if (ee.eResource() == null) {
				super.getCommonViewer().refresh();
				return;
			}

			final String fragment = ee.eResource().getURIFragment(ee);

			final IResource resource = EmfUtil.getPlatformResource(EmfUtil.getProject(ee));
			if (resource != null && resource instanceof IFile) {
				// Project x = EmfUtil.fromActiveEditor(resource.getName());
				final Resource res = SafecapModelRegistry.getResource((IFile) resource);
				final List<EObject> contents = res.getContents();
				Project x = null;
				for (final EObject root : contents) {
					if (root instanceof Project) {
						x = (Project) root;
					}
				}
				if (x != null) {
					final EObject zz = EmfUtil.getObjectFromDescriptor(x, fragment);
					element = zz;
					// System.out.println("Editor project:" + x);
					// System.out.println("Elememt:" + zz);
				}
			} else {
				super.getCommonViewer().refresh();
			}
		} else {
			return;
		}

		if (element instanceof Line) {
			final Line line = (Line) element;
			Highlight.line(line);
		} else if (element instanceof Route) {
			final Route route = (Route) element;
			// System.out.println("Route selection: " + sel.getFirstElement());
			Highlight.route(route);
		} else if (element instanceof Ambit) {
			final Ambit ambit = (Ambit) element;
			Highlight.ambit(ambit);
		} else {
			Highlight.reset();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getAdapter(Class key) {

		if (key == IPropertySheetPage.class) {
			return getPropertySheetPage();
		}

		return super.getAdapter(key);
	}

	public IPropertySheetPage getPropertySheetPage() {
		return new TabbedPropertySheetPage(this);
	}

	@Override
	public String getContributorId() {
		return getSite().getId();
	}
}
