package uk.ac.ncl.safecap.controltable;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ViewPluginAction;

import safecap.Project;
import safecap.model.Ambit;
import safecap.model.ControlLogic;
import safecap.model.Junction;
import safecap.model.Point;
import safecap.model.Route;
import safecap.model.Rule;
import uk.ac.ncl.safecap.controltable.views.ControlTable;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.RuleUtil;
//import uk.ac.ncl.safecap.reports.verification.VerificationUtils;

@SuppressWarnings("restriction")
public class ComputeTableAction implements IViewActionDelegate {

	@Override
	public void run(IAction action) {
		final Project project = getProject(action);

		if (project != null) {
			try {
				final AbstractTransactionalCommand command = getBuildCommand(project);
				if (command != null) {
					OperationHistoryFactory.getOperationHistory().execute(command, null, null);
				} else {
					buildRules(project);
				}

//				VerificationLog log = VerificationUtils.getVerificationLog(project);
//				if (log != null) {
//
//					for (Route r : project.getRoutes()) {
//						String uuid = EmfUtil.getElementDescriptor(r);
//						List<VerEntry> entries = VerificationUtils.findEntriesByKind(log, VerificationProvider.KIND, uuid);
//						log.getEntries().removeAll(entries);
//					}
//					VerificationUtils.saveLog(log);
//				}

				EmfUtil.saveProject(project);
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}
	}

	private void buildRules(Project project) {
		if (project != null) {
			for (final Route route : project.getRoutes()) {
				final ControlLogic logic = RuleUtil.getDefaultLogic(route);
				logic.getRule().clear();
				final Rule rule = RuleUtil.buildInitialRule(route);
				logic.getRule().add(rule);
			}

			for (final Ambit a : project.getAmbits()) {
				if (a instanceof Junction) {
					final Junction j = (Junction) a;
					for (final Point point : j.getPoints()) {
						point.setRule(RuleUtil.buildInitialRule(point));
					}
				}
			}
		}
	}

	private AbstractTransactionalCommand getBuildCommand(final Project project) {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(project);
		if (domain == null) {
			return null;
		}

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "BuildTableCommand", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				buildRules(project);

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

	@Override
	public void init(IViewPart view) {
		// TODO Auto-generated method stub

	}

	private Project getProject(IAction action) {
//		if (selection instanceof IStructuredSelection) {
//			final IStructuredSelection ssel = (IStructuredSelection) selection;
//			if (ssel.getFirstElement() instanceof EObject)
//				return EmfUtil.getProject((EObject) ssel.getFirstElement());
//		}

		if (action instanceof ViewPluginAction) {
			final ViewPluginAction act = (ViewPluginAction) action;
			if (act.getId().equals("uk.ac.ncl.safecap.controltable.computeTableAction")) {
				final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				final IViewPart view = page.findView("uk.ac.ncl.safecap.controltable.main");
				if (view != null && view instanceof ControlTable) {
					final ControlTable table = (ControlTable) view;
					return table.getProject();
				}
			}
		}
		return null;
	}

}
