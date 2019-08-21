package uk.ac.ncl.safecap.navigator.property;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import safecap.model.Line;
import traintable.Train;
import traintable.TrainTable;

public class LinePropertyComposite extends Composite {
	private static void save(Resource res) {
		try {
			res.save(null);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private static String arrToString(List<String> values) {
		final StringBuilder sb = new StringBuilder();
		for (final String str : values) {
			sb.append(str);
			if (values.indexOf(str) != values.size() - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	private static List<String> stringToArr(String value) {
		final String[] arr = value.split(",");
		final List<String> list = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i].trim());
		}
		return list;
	}

	private static List<String> getValidNames() {
		final List<String> names = new ArrayList<>();
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		final IProject verProject = ResourcesPlugin.getWorkspace().getRoot().getProject("Verification");
		if (verProject.exists()) {
			final IFile file = verProject.getFile("config.traintable");
			if (file.exists()) {
				try {
					// Create a resource set
					//
					final ResourceSet resourceSet = new ResourceSetImpl();
					// Get the URI of the model file.
					//
					final URI fileURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
					// Create a resource for this file.
					//
					final Resource resource = resourceSet.createResource(fileURI);
					// Add the initial model object to the contents.
					//
					final Map<Object, Object> options = new HashMap<>();
					options.put(XMLResource.OPTION_ENCODING, "UTF-8");
					resource.load(options);
					final TrainTable traintable = (TrainTable) resource.getContents().get(0);
					for (final Train train : traintable.getTrains()) {
						names.add(train.getName());
					}
					resource.unload();
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
		return names;
	}

	private final Adapter _listener = new AdapterImpl() {
		@Override
		public void notifyChanged(Notification msg) {
			super.notifyChanged(msg);
			updateValue();
		}
	};

	private final KeyListener _keyListener = new KeyListener() {
		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
//            System.out.println("Pressed: " + e.keyCode + " with " + ((e.stateMask&SWT.CTRL)==SWT.CTRL?"CTRL":""));
			if (e.keyCode == SWT.CR) {
				saveValue();
			}
		}
	};

	private final FocusListener _focusListener = new FocusListener() {

		@Override
		public void focusLost(FocusEvent e) {
			saveValue();
		}

		@Override
		public void focusGained(FocusEvent e) {
		}
	};

	private final Text txtTraffic;
	private Line _line;
	private final Label lblNewLabel;
	private final String _initialLabel;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public LinePropertyComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));

		lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Traffic Mix  ");
		_initialLabel = lblNewLabel.getText().trim();

		txtTraffic = new Text(this, SWT.BORDER);
		txtTraffic.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtTraffic.addKeyListener(_keyListener);
		txtTraffic.addFocusListener(_focusListener);

//        txtTraffic.addModifyListener(new ModifyListener()
//        {
//            @Override
//            public void modifyText(ModifyEvent e)
//            {
//                if (!stringToArr(txtTraffic.getText()).equals(_line.getTrafficmix()))
//                    lblNewLabel.setText(_initialLabel+"*");
//                else
//                    lblNewLabel.setText(_initialLabel);
////                lblNewLabel.setText(_initialLabel+"*");
//                lblNewLabel.redraw();
//            }
//        });
	}

	private void updateValue() {
		txtTraffic.setText(arrToString(_line.getTrafficmix()));
		lblNewLabel.setText(_initialLabel);
		lblNewLabel.redraw();
	}

	private void saveValue() {
		final List<String> arr = stringToArr(txtTraffic.getText());
		final List<String> trains = getValidNames();
		final List<String> valid = new ArrayList<>();
		for (final String name : arr) {
			if (trains.contains(name)) {
				valid.add(name);
			}
		}
		txtTraffic.setText(arrToString(valid));

		if (!valid.equals(_line.getTrafficmix())) {
			final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(_line);
			if (dom != null) {
				final List<Command> list = new ArrayList<>();
				list.add(new RemoveCommand(dom, _line.getTrafficmix(), _line.getTrafficmix()));
				list.add(new AddCommand(dom, _line.getTrafficmix(), valid));
				final CompoundCommand cmd = new CompoundCommand(list);
				dom.getCommandStack().execute(cmd);
				save(_line.eResource());
			} else {
				_line.getTrafficmix().clear();
				_line.getTrafficmix().addAll(valid);
				save(_line.eResource());
			}
		}

		lblNewLabel.setText(_initialLabel);
		lblNewLabel.redraw();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void dispose() {
		if (_line != null) {
			_line.eAdapters().remove(_listener);
		}
		txtTraffic.removeKeyListener(_keyListener);
		txtTraffic.removeFocusListener(_focusListener);
		super.dispose();
	}

	public void setInput(Line line) {
		if (_line != null) {
			_line.eAdapters().remove(_listener);
		}
		_line = line;
		_line.eAdapters().add(_listener);
		updateValue();
	}
}
