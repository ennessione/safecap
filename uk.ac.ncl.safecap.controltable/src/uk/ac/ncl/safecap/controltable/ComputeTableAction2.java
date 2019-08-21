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
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
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
//import VerificationReport.VerEntry;
//import VerificationReport.VerificationLog;

public class ComputeTableAction2 implements IViewActionDelegate {
	private ISelection selection;

	@Override
	public void run(IAction action) {
		final Project project = getProject(action);

		if (project != null) {
			try {

				final InputDialog dialog = new InputDialog(Display.getCurrent().getActiveShell(), "Aspect number",
						"Enter the number of signal aspects", "4", null);

				if (dialog.open() != Window.OK) {
					return;
				}

				final String input = dialog.getValue();

				int aspect;
				try {
					aspect = Integer.parseInt(input);
					if (aspect < 2 || aspect > 10) {
						MessageDialog.openError(null, "Invalid input", "Invalid aspect number");
						return;
					}
				} catch (final Exception e) {
					MessageDialog.openError(null, "Invalid input", "Invalid aspect number");
					return;
				}

				final AbstractTransactionalCommand command = getBuildCommand(project, aspect);
				if (command != null) {
					OperationHistoryFactory.getOperationHistory().execute(command, null, null);
				} else {
					buildRules(project, aspect);
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

	private void buildRules(Project project, int aspects) {
		if (project != null) {
			for (final Route route : project.getRoutes()) {
				final ControlLogic logic = RuleUtil.getDefaultLogic(route, aspects);
				logic.setLine(null);
				logic.getRule().clear();
				if (logic.getAspects() != aspects) {
					logic.setAspects(aspects);
				}

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

	private AbstractTransactionalCommand getBuildCommand(final Project project, final int aspect) {

		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(project);
		if (domain == null) {
			return null;
		}

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "BuildTableCommand", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				buildRules(project, aspect);

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
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
			if (act.getId().equals("uk.ac.ncl.safecap.controltable.computeTableAction2")) {
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
