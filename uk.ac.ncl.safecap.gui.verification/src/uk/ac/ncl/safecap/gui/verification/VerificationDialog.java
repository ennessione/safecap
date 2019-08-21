package uk.ac.ncl.safecap.gui.verification;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import uk.ac.ncl.safecap.gui.verification.IVerificationTool.VerificationResult;

public class VerificationDialog extends Dialog {
	private class ToolUI {
		public IProgressMonitor monitor = new IProgressMonitor() {
			@Override
			public void worked(final int work) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						progress.setSelection(progress.getSelection() + work);
					}
				});
			}

			@Override
			public void subTask(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setTaskName(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setCanceled(boolean arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isCanceled() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void internalWorked(double arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void done() {
			}

			@Override
			public void beginTask(String arg0, final int totalWork) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						progress.setMinimum(0);
						progress.setMaximum(totalWork);
					}
				});
			}
		};

		Button report, stop;
		Label imgStatus, name;
		ProgressBar progress;
		IVerificationTool tool;
		VerificationResult result;
		VerificationStatusGui status;
		boolean stopped = false;

		private Thread _thread;

		public ToolUI(IVerificationTool tool, Composite container) {
			this.tool = tool;

			imgStatus = new Label(container, SWT.NONE);
			imgStatus.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/pending.gif"));

			name = new Label(container, SWT.NONE);
			name.setText(tool.getName());

			progress = new ProgressBar(container, SWT.NONE);
			progress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

			stop = new Button(container, SWT.NONE);
			stop.setToolTipText("Stop tool execution");
			stop.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/stop.gif"));
			stop.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					stop();
				}
			});

			report = new Button(container, SWT.FLAT);
			report.setToolTipText("Open report");
			report.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/report.gif"));
			report.setEnabled(false);
			report.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					result.showResultDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
				}
			});

			status = new VerificationStatusGui(tool, _lblStatus, monitor);
		}

		public void run(final EObject target, final VerificationContext context) {
			if (stopped) {
				return;
			}
			imgStatus.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/working.gif"));
			status.setStatusMessage("Working");
			_thread = new Thread(new Runnable() {
				@Override
				public void run() {
					int res = VerificationResult.RESULT_NONE;
					try {
						result = tool.run(target, context, status);
						res = result.result;
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								stop.setEnabled(false);
								progress.setSelection(progress.getMaximum());
								if (result.report) {
									report.setEnabled(true);
								}
								switch (result.result) {
								case VerificationResult.RESULT_OK:
									imgStatus.setImage(
											ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/success.gif"));
									break;
								case VerificationResult.RESULT_MAYBE:
									imgStatus.setImage(
											ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/possibly.gif"));
									break;
								case VerificationResult.RESULT_FAIL:
									imgStatus.setImage(
											ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/failed.gif"));
									break;
								case VerificationResult.RESULT_NONE:
									imgStatus.setImage(
											ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/stop.gif"));
									progress.setSelection(progress.getMinimum());
									stop.setEnabled(false);
									break;
								}
							}
						});
					} catch (final InterruptedException e) {
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								imgStatus.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/stop.gif"));
								progress.setSelection(progress.getMinimum());
								progress.setEnabled(false);
								stop.setEnabled(false);
							}
						});
					} finally {
						status.terminate();
						final int res1 = res;
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								onToolFinished(tool, res1);
							}
						});
					}
				}
			});
			_thread.start();
		}

		public void stop() {
			stopped = true;
			imgStatus.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/stop.gif"));
			progress.setSelection(progress.getMinimum());
			progress.setEnabled(false);
			stop.setEnabled(false);
			if (_thread.isAlive()) {
				_thread.interrupt();
			}
		}
	}

	private final EObject _target;
	private final IVerificationProfile _profile;
	private final List<IVerificationTool> _toolList;
	private final Map<IVerificationTool, ToolUI> _tools = new Hashtable<>();
	private Button _btnOk;
	private ToolUI _currentTool;
	private boolean _cancelled = false;
	private final VerificationContext _context = new VerificationContext();
	private Label _lblStatus;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public VerificationDialog(Shell parentShell, IVerificationProfile profile, EObject target) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);
		_profile = profile;
		_target = target;
		_toolList = _profile.getTools();
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite bigContainer = (Composite) super.createDialogArea(parent);
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.horizontalSpacing = 0;
		gl_container.marginTop = 0;
		gl_container.marginLeft = 0;
		bigContainer.setLayout(gl_container);

		final Composite container = new Composite(bigContainer, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		gl_container = new GridLayout(5, false);
		gl_container.horizontalSpacing = 7;
		gl_container.marginTop = 5;
		gl_container.marginLeft = 5;
		container.setLayout(gl_container);

//        Label lblNewLabel_1 = new Label(container, SWT.CENTER);
//        lblNewLabel_1.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/success.gif"));
//
//        Label lblNewLabel = new Label(container, SWT.LEFT);
//        lblNewLabel.setText("New Label");
//
//        ProgressBar progressBar = new ProgressBar(container, SWT.NONE);
//        progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
//
//        Button btnNewButton_1 = new Button(container, SWT.NONE);
//        btnNewButton_1.setToolTipText("Stop tool execution");
//        btnNewButton_1.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/stop.gif"));
//
//        Button btnNewButton = new Button(container, SWT.FLAT);
//        btnNewButton.setToolTipText("Open report");
//        btnNewButton.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/report.gif"));

		_lblStatus = new Label(bigContainer, SWT.WRAP);
		_lblStatus.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		_lblStatus.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		_lblStatus.setText("Status: Pending");

		populateTools(container);

		final List<IVerificationTool> toolList = _profile.getTools();
		if (toolList.size() > 0) {
			final IVerificationTool tool = toolList.get(0);
			final ToolUI ui = _tools.get(tool);
			ui.run(_target, _context);
			_currentTool = ui;
		}

		return container;
	}

	private void onToolFinished(IVerificationTool tool, int result) {
		_context.setToolResult(tool, result);
		if (!_cancelled) {
			for (int i = _toolList.indexOf(tool) + 1; i < _toolList.size(); i++) {
				final IVerificationTool nextTool = _toolList.get(i);
				final ToolUI ui = _tools.get(nextTool);
				if (!ui.stopped) {
					_lblStatus.setText("Status: Pending");
					ui.run(_target, _context);
					_currentTool = ui;
					return;
				}
			}
		}
		_currentTool = null;
		_btnOk.setEnabled(true);
		_lblStatus.setText("Status: Finished");
	}

	private void populateTools(Composite container) {
		final List<IVerificationTool> toolList = _profile.getTools();
		for (final IVerificationTool tool : toolList) {
			final ToolUI toolUI = new ToolUI(tool, container);
			_tools.put(tool, toolUI);
		}
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		final Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		button.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);

		_btnOk = button;
		if (_toolList.size() == 0) {
			_btnOk.setEnabled(true);
		}
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(402, 357);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.CANCEL_ID) {
			if (_currentTool != null) {
				_currentTool.stop();
			}
			_cancelled = true;
		}
		super.buttonPressed(buttonId);
	}
}
