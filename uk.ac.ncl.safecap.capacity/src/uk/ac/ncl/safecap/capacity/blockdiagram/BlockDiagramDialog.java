package uk.ac.ncl.safecap.capacity.blockdiagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;
import uk.ac.ncl.safecap.gui.trainconfig.ServicePatternUtil;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.ITrain;

public class BlockDiagramDialog extends Dialog {
	private LightweightSystem _draw2dSystem;
	private Canvas _canvas;
	private final SimulationExperiment _provider;
	// private Renderer _renderer;
	private GraphFigure _topLevelFigure;
	private Composite _labelsComposite;
	private final Map<String, Label> _trainLabels = new HashMap<>();
	private final Map<String, Label> _trainPositionLabels = new HashMap<>();
	private final Map<String, Color> _colors;
	private final List<TrainLine> _lines;
	private final Reporter _reporter = new Reporter();

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public BlockDiagramDialog(Shell parentShell, SimulationExperiment experiment) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.MAX | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);
		_provider = experiment;
		_colors = ServicePatternUtil.getColors(_provider.getProject().eResource());

		_lines = new ArrayList<>();
		for (final ITrain train : _provider.getTrains()) {
			final TrainLine line = train.getLine();
			if (!_lines.contains(line)) {
				_lines.add(line);
			}
		}
	}

	@Override
	protected void configureShell(Shell newShell) {
		newShell.setBackground(SWTResourceManager.getColor(255, 255, 255));
		newShell.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.capacity", "icons/blockdiagram.gif"));
		super.configureShell(newShell);
		newShell.setText("Block Diagram");
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));

		final Composite composite = new Composite(container, SWT.BORDER);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayout(new GridLayout(1, false));

		final Label lblLine = new Label(composite, SWT.NONE);
		lblLine.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblLine.setText("Line:");
		lblLine.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		final Label label_3 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final ComboViewer comboViewer = new ComboViewer(composite, SWT.NONE);
		final Combo comboLine = comboViewer.getCombo();
		comboLine.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboLine.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final int index = comboLine.getSelectionIndex();
				final TrainLine line = _lines.get(index);
				_topLevelFigure.setInput(line);
				if (!_trainLabels.isEmpty()) {
					for (final Label label : _trainLabels.values()) {
						label.dispose();
					}
					for (final Label label : _trainPositionLabels.values()) {
						label.dispose();
					}
					_trainLabels.clear();
					_trainPositionLabels.clear();
				}
				for (final ITrain train : _provider.getTrains()) {
					if (train.getLine() == line) {
						final Label trainLabel = new Label(_labelsComposite, SWT.NONE);
						trainLabel.setText(train.getDescriptor().getTrainName());
						trainLabel.setForeground(_colors.get(train.getDescriptor().getTrainName()));
						trainLabel.setBackground(ColorConstants.white);
						_trainLabels.put(train.getDescriptor().getTrainName(), trainLabel);

						final Label trainPositionLabel = new Label(_labelsComposite, SWT.NONE);
						trainPositionLabel.setBackground(ColorConstants.white);
						_trainPositionLabels.put(train.getDescriptor().getTrainName(), trainPositionLabel);
					}
				}
				_labelsComposite.layout();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		for (final TrainLine line : _lines) {
			comboLine.add(line.getSchemaLine().getLabel());
		}

		final ScrolledComposite sc1 = new ScrolledComposite(container, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		sc1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		sc1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4));

		// GLData data = new GLData();
		// _canvas = new GLCanvas(sc1, SWT.DOUBLE_BUFFERED|SWT.NO_FOCUS, data);
		_canvas = new Canvas(sc1, SWT.DOUBLE_BUFFERED);
		_canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		sc1.setContent(_canvas);
		_canvas.setBackground(ColorConstants.white);

		_draw2dSystem = new LightweightSystem(_canvas);
		_topLevelFigure = new GraphFigure(_canvas, _provider, _colors, _reporter);

		final Composite _zoomComposite = new Composite(container, SWT.NONE);
		_zoomComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		_zoomComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		_zoomComposite.setLayout(new GridLayout(2, false));

		final Button btnZoomInVer = new Button(_zoomComposite, SWT.FLAT);
		btnZoomInVer.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.capacity", "icons/zoom_in_ver.gif"));
		btnZoomInVer.setBounds(0, 0, 75, 25);
		btnZoomInVer.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				_topLevelFigure.zoom(true, true);
			}
		});

		final Button btnZoomOutVer = new Button(_zoomComposite, SWT.NONE);
		btnZoomOutVer.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.capacity", "icons/zoom_out_ver.gif"));
		btnZoomOutVer.setBounds(0, 0, 75, 25);
		btnZoomOutVer.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				_topLevelFigure.zoom(false, true);
			}
		});

		final Button btnZoomInHor = new Button(_zoomComposite, SWT.NONE);
		btnZoomInHor.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.capacity", "icons/zoom_in_hor.gif"));
		btnZoomInHor.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				_topLevelFigure.zoom(true, false);
			}
		});

		final Button btnZoomOutHor = new Button(_zoomComposite, SWT.NONE);
		btnZoomOutHor.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.capacity", "icons/zoom_out_hor.gif"));
		btnZoomOutHor.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				_topLevelFigure.zoom(false, false);
			}
		});

		_labelsComposite = new Composite(container, SWT.BORDER);
		_labelsComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		_labelsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		_labelsComposite.setLayout(new GridLayout(2, false));

		final Label lblTrains = new Label(_labelsComposite, SWT.NONE);
		lblTrains.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblTrains.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTrains.setText("Trains:");

		final Label label = new Label(_labelsComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		new Label(_labelsComposite, SWT.NONE);
		new Label(_labelsComposite, SWT.NONE);

		final Composite _statusComposite = new Composite(container, SWT.BORDER);
		_statusComposite.setLayout(new GridLayout(2, false));
		final GridData gd__statusComposite = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd__statusComposite.widthHint = 150;
		_statusComposite.setLayoutData(gd__statusComposite);
		_statusComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		final Label lblStatus = new Label(_statusComposite, SWT.NONE);
		lblStatus.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblStatus.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblStatus.setBounds(0, 0, 55, 15);
		lblStatus.setText("State:");

		final Label label_1 = new Label(_statusComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblNewLabel = new Label(_statusComposite, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setText("time: ");

		final Label _lblTime = new Label(_statusComposite, SWT.NONE);
		_lblTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		_lblTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		_lblTime.setText("0");
		_draw2dSystem.getRootFigure().setOpaque(false);
		_topLevelFigure.setOpaque(true);
		_draw2dSystem.setContents(_topLevelFigure);

		final Label lblPos = new Label(_statusComposite, SWT.NONE);
		lblPos.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblPos.setText("line pos:");

		final Label _lblLineCoord = new Label(_statusComposite, SWT.NONE);
		_lblLineCoord.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		_lblLineCoord.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		_lblLineCoord.setText("0");

		final Label lblRoute = new Label(_statusComposite, SWT.NONE);
		lblRoute.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblRoute.setText("route:");

		final Label _lblRoute = new Label(_statusComposite, SWT.NONE);
		_lblRoute.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		_lblRoute.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		_lblRoute.setText("<route>");

		final Label lblAmbit = new Label(_statusComposite, SWT.NONE);
		lblAmbit.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblAmbit.setText("ambit:");

		final Label _lblAmbit = new Label(_statusComposite, SWT.NONE);
		_lblAmbit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		_lblAmbit.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		_lblAmbit.setText("<ambit>");

		_reporter.setGui(_lblTime, _lblLineCoord, _lblRoute, _lblAmbit, _trainPositionLabels);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// createButton(parent, IDialogConstants.OK_ID,
		// IDialogConstants.OK_LABEL, true);
		// createButton(parent, IDialogConstants.CANCEL_ID,
		// IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(900, 700);
	}
}
