package uk.ac.ncl.safecap.navigator.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import safecap.diagram.edit.parts.LeftSpeedLimitEditPart;
import safecap.diagram.edit.parts.LeftStopAndGoEditPart;
import safecap.diagram.edit.parts.RightSpeedLimitEditPart;
import safecap.diagram.edit.parts.RightStopAndGoEditPart;
import uk.ac.ncl.safecap.diagram.misc.diagramactions.FillLineAttachment;

public class FillSpeedLimitLineAttachment implements IObjectActionDelegate {

	private ISelection selection;

	public FillSpeedLimitLineAttachment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(IAction action) {
		final FillLineAttachment command = new FillLineAttachment(getSelectedElements());
		command.run();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		action.setEnabled(selectionContainsSpeedLimit());
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub

	}

	private boolean selectionContainsSpeedLimit() {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			for (final Iterator iter = ssel.iterator(); iter.hasNext();) {
				final Object element = iter.next();
				if (element instanceof ShapeNodeEditPart) {
					final ShapeNodeEditPart part = (ShapeNodeEditPart) element;
					if (part instanceof LeftSpeedLimitEditPart || part instanceof RightSpeedLimitEditPart
							|| part instanceof LeftStopAndGoEditPart || part instanceof RightStopAndGoEditPart) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private List<EObject> getSelectedElements() {
		final List<EObject> speedLimits = new ArrayList<>();
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			for (final Iterator iter = ssel.iterator(); iter.hasNext();) {
				final Object element = iter.next();
				if (element instanceof ShapeNodeEditPart) {
					final ShapeNodeEditPart part = (ShapeNodeEditPart) element;
					if (part instanceof LeftSpeedLimitEditPart || part instanceof RightSpeedLimitEditPart
							|| part instanceof LeftStopAndGoEditPart || part instanceof RightStopAndGoEditPart) {
						final Shape shape = (Shape) part.getModel();
						speedLimits.add(shape.getElement());
					}
				}
			}
		}
		return speedLimits;
	}

}
