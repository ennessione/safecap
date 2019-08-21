package uk.ac.ncl.safecap.diagram.misc.propertypages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import safecap.Project;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public abstract class ExtensiblePropertySource implements IPropertySource {
	protected static final String[] yesno = new String[] { "Yes", "No" };

	TransactionalEditingDomain domain;

	public ExtensiblePropertySource(EObject object) {
		if (object != null) {
			final Project project = EmfUtil.getProject(object);
			if (project != null) {
				domain = TransactionUtil.getEditingDomain(EmfUtil.getProject(object));
			}
		}
	}

	@Override
	public abstract Object getEditableValue();

	public abstract void getDescriptors(List<IPropertyDescriptor> list);

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		final List<IPropertyDescriptor> allDescr = new ArrayList<>();

		if (domain != null) {
			getDescriptors(allDescr);
		}

		return allDescr.toArray(new IPropertyDescriptor[allDescr.size()]);
	}

	@Override
	public abstract Object getPropertyValue(Object id);

	@Override
	public boolean isPropertySet(Object id) {
		return true;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// do nothing
	}

	public abstract boolean setValueCommand(final Object id, final Object value);

	@Override
	public void setPropertyValue(final Object id, final Object value) {

		if (domain != null) {
			final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "setPropertyValue", null) {
				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

					setValueCommand(id, value);

					return CommandResult.newOKCommandResult();
				}
			};

			try {
				OperationHistoryFactory.getOperationHistory().execute(command, new NullProgressMonitor(), null);
			} catch (final ExecutionException e) {
				e.printStackTrace();
			}
		}

	}

}
