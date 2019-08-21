package uk.ac.ncl.safecap.replay;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.EditorReference;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import safecap.Project;
import uk.ac.ncl.safecap.capacity.experiment.ExperimentRegistry;
import uk.ac.ncl.safecap.capacity.experiment.ExperimentRegistry.IExperimentListener;
import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.scitus.base.Evolution;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.scitus.base.Progression;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.base.TrainEvent;
import uk.ac.ncl.safecap.scitus.base.TrainProgression;
import uk.ac.ncl.safecap.scitus.base.World;
import uk.ac.ncl.safecap.scitus.stat.ITrainRun;
import uk.ac.ncl.safecap.scitus.stat.Run2DRecord;
import uk.ac.ncl.safecap.scitus.stat.SimHistoryRecord;

public class ReplayView extends ViewPart implements IExperimentListener, IPartListener2 {

	private class Updater implements Runnable {
		private final Runnable _updateAction = new Runnable() {
			@Override
			public void run() {
				final double dif = (System.nanoTime() - _startTime) / 1000000000.0;
				_time = _startPos + dif * _speed;
				updateTimeState();
			}
		};

		private long _startTime;
		private double _startPos;
		private final int _step;

		public Updater(int step) {
			_step = step;
		}

		@Override
		public void run() {
			_startPos = _time;
			_startTime = System.nanoTime();
			try {
				while (true) {
					if (Thread.currentThread().isInterrupted()) {
						return;
					}
//					long nextTime = _lastTime + _step;
//					long curTime = System.nanoTime();
//					long dt = (curTime - _lastTime) / 1000000;
//					long dif = _step - dt;
//					if (dif > 0)
//					{
//						_lastTime += _step*(long)1000000;
//						Thread.sleep(dif);
//					}
//					else
//						_lastTime = curTime;
					Thread.sleep(_step);
					Display.getDefault().asyncExec(_updateAction);
				}
			} catch (final InterruptedException e) {
			}
		}
	}

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "uk.ac.ncl.safecap.replay.ReplayView";
	private static final int COL_NAME = 0, COL_SPEED = 1, COL_MAX_SPEED = 2, COL_BD = 3, COL_MRT_TAIL = 4, COL_MRT_HEAD = 5,
			COL_MRT_DISTANCE_ORACLE = 6, COL_MRT_SPEED = 7, COL_MRT_SPEED_ORACLE = 8;

	private TreeViewer viewer;
	private Action action1;
	private Action action2;
	private static String[] speedTexts = new String[] { "1/4x", "1/2x", "1x", "2x", "3x", "5x", "10x", "20x" };
	private static double[] speeds = new double[] { 0.25, 0.5, 1, 2, 3, 5, 10, 20 };
	private Project _project;
	private SimulationExperiment _exp;

	private Combo comboSpeed;
	private Button btnPlay, btnPause, btnToBegin, btnToEnd, btnExport;
	private Scale progressTime;

	private boolean _playing = false;
	private double _time = 0, _speed = 1;

	private Thread _thread = null;
	private final Updater _updater = new Updater(100);
	private Label lblNewLabel;
	private Label lblTime;

	DecimalFormat format = new DecimalFormat("#.#");
	DecimalFormat format3 = new DecimalFormat("#.###");

	public static class ContentInput {
		public SimulationExperiment exp;
		public double time;

		public ContentInput(SimulationExperiment exp, double time) {
			super();
			this.exp = exp;
			this.time = time;
		}
	}

	public static class ChildParentPair<T, TParent> {
		public T node;
		public TParent parent;

		public ChildParentPair(T node, TParent parent) {
			super();
			this.node = node;
			this.parent = parent;
		}
	}

	public static class FirstLevelChild extends ChildParentPair<TrainProgression, ContentInput> {
		public FirstLevelChild(TrainProgression node, ContentInput parent) {
			super(node, parent);
		}
	}

	public static class SecondLevelChild extends ChildParentPair<TrainEvent, FirstLevelChild> {

		public SecondLevelChild(TrainEvent node, FirstLevelChild parent) {
			super(node, parent);
			// TODO Auto-generated constructor stub
		}
	}

	/*
	 * The content provider class is responsible for providing objects to the view.
	 * It can wrap existing objects in adapters or simply return objects as-is.
	 * These objects may be sensitive to the current input of the view, or ignore it
	 * and always show the same content (like Task List, for example).
	 */

	class ViewContentProvider implements ITreeContentProvider {

		private final Map<S1TrainActor, FirstLevelChild> _children = new HashMap<>();

		@Override
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
//			if (newInput != null)
//				exp = (SimulationExperiment) newInput;
//			else
//				exp = null;
			_children.clear();
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object parent) {
			return getChildren(parent);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof ContentInput) {
				final List<FirstLevelChild> progressions = new ArrayList<>();

				final ContentInput input = (ContentInput) parentElement;

				if (!(input.exp.getWorld() instanceof World)) {
					return progressions.toArray();
				}

				final Evolution ev = input.exp.getEvolution(input.time);

				for (final Progression progression : ev.getProgressions()) {
					if (progression instanceof TrainProgression) {
						final TrainProgression trainProgression = (TrainProgression) progression;
						final FirstLevelChild child = new FirstLevelChild(trainProgression, input);
						_children.put(trainProgression.getTrain(), child);
						progressions.add(child);
					}
				}
				return progressions.toArray();
			} else if (parentElement instanceof FirstLevelChild) {
				final FirstLevelChild parent = (FirstLevelChild) parentElement;
				final List<SecondLevelChild> mrts = new ArrayList<>();
				for (final TrainEvent event : parent.node.getOtherTargets()) {
					mrts.add(new SecondLevelChild(event, parent));
				}
				return mrts.toArray();
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof ChildParentPair) {
				final ChildParentPair p = (ChildParentPair) element;
				return p.parent;
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof ContentInput) {
				return true;
			} else if (element instanceof FirstLevelChild) {
				return true;
			}
			return false;
		}

		public void expandChildren(Object[] expanded) {
			for (final Object expandedObject : expanded) {
				if (expandedObject instanceof FirstLevelChild) {
					final FirstLevelChild child = (FirstLevelChild) expandedObject;
					if (_children.containsKey(child.node.getActor())) {
						viewer.setExpandedState(_children.get(child.node.getActor()), true);
					}
				}
			}
		}
	}

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {

		DecimalFormat format = new DecimalFormat("#.#");

		private double getCurrentSpeed(S1TrainActor train, SimulationExperiment exp, double time) {
			final ITrainRun data = exp.getRunData(train);
			final double[] speed = data.getSpeed();
			// double dt = data.getDeltaT();
			// int slotIndex = (int) Math.round(time / dt);
			int slotIndex = data.timeToIndex(time);
			if (slotIndex >= speed.length) {
				slotIndex = speed.length - 1;
			}
			if (slotIndex < 0) {
				slotIndex = 0;
			}
			return speed[slotIndex];
		}

		@Override
		public String getColumnText(Object obj, int index) {
			if (obj instanceof FirstLevelChild) {
				final FirstLevelChild child = (FirstLevelChild) obj;
				final S1TrainActor train = (S1TrainActor) child.node.getActor();
				final TrainProgression pr = child.node;
				final TrainEvent ev = pr.getMRT();
				switch (index) {
				case COL_NAME:
					return train.getName();
				case COL_SPEED:// speed
					final double speed = getCurrentSpeed(train, child.parent.exp, child.parent.time);
					return format.format(speed);
				case COL_MAX_SPEED:// max speed
					return format.format(pr.getMaxSpeed());
				case COL_BD:// braking distance
					final double currentSpeed = getCurrentSpeed(train, child.parent.exp, child.parent.time);
					return format.format(train.getDescriptor().getSafeBrakingDistance(currentSpeed));
				case COL_MRT_TAIL:// tail distance
				case COL_MRT_HEAD:// head distance
					final String s = format.format(ev.getDistance());
					if (index == COL_MRT_HEAD && ev.isForHead() || index == COL_MRT_TAIL && !ev.isForHead()) {
						return s;
					} else {
						return "";
					}
				case COL_MRT_DISTANCE_ORACLE:// target description
					return ev.getDistanceOracle();
				case COL_MRT_SPEED:// speed at target
					return format.format(ev.getSpeedAtEnd());
				case COL_MRT_SPEED_ORACLE:
					return ev.getSpeedOracle();
				default:
					return "";
				}
			} else if (obj instanceof SecondLevelChild) {
				final SecondLevelChild child = (SecondLevelChild) obj;
				final TrainEvent ev = child.node;
				switch (index) {
				case COL_MRT_TAIL:// tail distance
				case COL_MRT_HEAD:// head distance
					final String s = format.format(ev.getDistance());
					if (index == COL_MRT_HEAD && ev.isForHead() || index == COL_MRT_TAIL && !ev.isForHead()) {
						return s;
					} else {
						return "";
					}
				case COL_MRT_DISTANCE_ORACLE:// target description
					return ev.getDistanceOracle();
				case COL_MRT_SPEED:// speed at target
					return format.format(ev.getSpeedAtEnd());
				case COL_MRT_SPEED_ORACLE:
					return ev.getSpeedOracle();
				default:
					return "";
				}
			}
			return "";
		}

		@Override
		public Image getColumnImage(Object obj, int index) {
			return null;
		}

		@Override
		public Image getImage(Object obj) {
//			return PlatformUI.getWorkbench().
//					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
			return null;
		}
	}

	class NameSorter extends ViewerSorter {
	}

	private final List<IReplayListener> _listeners = new ArrayList<>();

	public void addListener(IReplayListener listener) {
		if (!_listeners.contains(listener)) {
			_listeners.add(listener);
		}
	}

	public void removeListener(IReplayListener listener) {
		_listeners.remove(listener);
	}

	/**
	 * The constructor.
	 */
	public ReplayView() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		final GridLayout gl_parent = new GridLayout(1, false);
		gl_parent.verticalSpacing = 0;
		gl_parent.marginWidth = 0;
		gl_parent.marginHeight = 0;
		gl_parent.horizontalSpacing = 0;
		parent.setLayout(gl_parent);

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1));
		composite.setLayout(new GridLayout(8, false));

		btnToBegin = new Button(composite, SWT.FLAT);
		btnToBegin.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.replay", "icons/toStart.gif"));
		btnToBegin.setBounds(0, 0, 75, 25);
		btnToBegin.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				toStart();
			}
		});

		btnPlay = new Button(composite, SWT.NONE);
		btnPlay.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.replay", "icons/play.gif"));
		btnPlay.setBounds(0, 0, 75, 25);
		btnPlay.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				play();
			}
		});

		btnPause = new Button(composite, SWT.NONE);
		btnPause.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.replay", "icons/pause.gif"));
		btnPause.setSize(75, 25);
		btnPause.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				pause();
			}
		});

		btnToEnd = new Button(composite, SWT.FLAT);
		btnToEnd.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.replay", "icons/toEnd.gif"));
		btnToEnd.setBounds(0, 0, 75, 25);
		btnToEnd.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				toEnd();
			}
		});

		comboSpeed = new Combo(composite, SWT.READ_ONLY);
		final GridData gd_comboSpeed = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_comboSpeed.widthHint = 69;
		comboSpeed.setLayoutData(gd_comboSpeed);
		comboSpeed.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		for (final String str : speedTexts) {
			comboSpeed.add(str);
		}
		comboSpeed.setText("1x");
		comboSpeed.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				speedChanged();
			}
		});

		lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setText("Time:");

		lblTime = new Label(composite, SWT.NONE);
		final GridData gd_lblTime = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblTime.widthHint = 60;
		lblTime.setLayoutData(gd_lblTime);
		lblTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTime.setText("0");

//		new Label(composite, SWT.NONE);
//		new Label(composite, SWT.NONE);
//		new Label(composite, SWT.NONE);
//		new Label(composite, SWT.NONE);

		btnExport = new Button(composite, SWT.FLAT);
		btnExport.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.replay", "icons/export.gif"));
		btnExport.setBounds(0, 0, 75, 25);
		btnExport.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				export();
			}
		});

		progressTime = new Scale(composite, SWT.NONE);
		progressTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		progressTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 7, 1));
		progressTime.setIncrement(1);
		progressTime.setMinimum(0);
		progressTime.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				timeSelect(progressTime.getSelection());
			}
		});
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		final Label label = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		final Tree tree = viewer.getTree();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		createTableViewerColumn("Train", 200, null);
		createTableViewerColumn("Speed", 60, null);
		createTableViewerColumn("Max Speed", 90, null);
		createTableViewerColumn("Braking distance", 120, null);
		createTableViewerColumn("MRT tail distance", 150, null);
		createTableViewerColumn("MRT head distance", 150, null);
		createTableViewerColumn("Distance oracle", 200, null);
		createTableViewerColumn("Speed at target", 100, null);
		createTableViewerColumn("Speed oracle", 200, null);

		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
//		viewer.setSorter(new NameSorter());
		viewer.setInput(null);
//		viewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);

		// Create the help context id for the viewer's control
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "uk.ac.ncl.safecap.replay.viewer");
//		makeActions();
//		hookContextMenu();
//		hookDoubleClickAction();
//		contributeToActionBars();

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().addPartListener(this);
			final IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (part != null) {
				final Project project = getSelectedModel(part.getEditorInput());
				setInput(project);
			}
		} catch (final NullPointerException e) {
//			setInput(null);
		}

		updateState();
	}

	protected void export() {
		final FileDialog dialog = new FileDialog(getSite().getShell(), SWT.SAVE);
		dialog.setText("Data export");
		dialog.setFileName("safecap_export.data");
		dialog.open();
		dialog.setOverwrite(true);
		if (dialog.getFileName() == null || dialog.getFileName().length() == 0) {
			return;
		}

		final String filename = dialog.getFilterPath() + File.separator + dialog.getFileName();

		FileWriter fstream = null;
		PrintWriter out = null;
		try {
			fstream = new FileWriter(filename, false);
			out = new PrintWriter(fstream);
			out.println("// File " + filename);
			out.println("// Produced by Safecap on " + new Date());

			for (final ITrain train : _exp.getTrains()) {
				final ITrainRun data = _exp.getRunData(train);
				out.println("// Train Class " + train.getDescriptor().getTrainName());
				out.println("// Train Name " + train.getDescriptor().getTrainClass());
				out.println("// Line Name " + train.getLine().getSchemaLine().getLabel());

				if (data instanceof SimHistoryRecord) {
					final SimHistoryRecord sh = (SimHistoryRecord) data;
					out.println("// Col. 0: Course-ID");
					out.println("// Col. 1: Time [s]");
					int i = 2;
					for (final String r : sh.getRecords()) {
						out.println("// Col. " + i + ": " + r);
						i++;
					}
					final int points = sh.getTime().length;
					final int skipLevel = points / 5000;
					for (int j = 0; j < points; j++) {
						if (skipLevel == 0 || j % skipLevel == 0) {
							out.print(train.getDescriptor().getTrainName());
							out.print("\t");
							out.print(format3.format(data.getTime()[j]));
							out.print("\t");
							for (final String r : sh.getRecords()) {
								final Run2DRecord rec = sh.getRecord(r);
								if (j < rec.getData().length) {
									out.print(format3.format(rec.getData()[j]));
								} else {
									out.print("\"missing\"");
								}
								out.print("\t");
							}
							out.print("\n");
						}
					}
				} else {
					out.println("// Col. 0: Course-ID");
					out.println("// Col. 1: Time [s]");
					out.println("// Col. 2: Distance [km]");
					out.println("// Col. 3: Speed [km/h]");
					final int points = data.getDistancePoints();
					final int skipLevel = points / 5000;
					for (int i = 0; i < points; i++) {
						if (skipLevel == 0 || i % skipLevel == 0) {
							out.print(train.getDescriptor().getTrainName());
							out.print("\t");
							out.print(format3.format(data.getTime()[i]));
							out.print("\t");
							out.print(format3.format(data.getPosition()[i] / 1000.0));
							out.print("\t");
							out.print(format3.format(data.getSpeed()[i] * 3.6));
							out.print("\n");
						}
					}
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (fstream != null) {
				try {
					fstream.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private TreeViewerColumn createTableViewerColumn(String title, int bound, EditingSupport editing) {
		final TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
		if (editing != null) {
			viewerColumn.setEditingSupport(editing);
		}

		final TreeColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(false);
		return viewerColumn;
	}

	private double getCurrentSpeed() {
		return getSpeedByText(comboSpeed.getText());
	}

	private double getSpeedByText(String str) {
		for (int i = 0; i < speedTexts.length; i++) {
			final String s = speedTexts[i];
			if (s.equals(str)) {
				return speeds[i];
			}
		}
		return 1;
	}

//	private void hookContextMenu() {
//		MenuManager menuMgr = new MenuManager("#PopupMenu");
//		menuMgr.setRemoveAllWhenShown(true);
//		menuMgr.addMenuListener(new IMenuListener() {
//			public void menuAboutToShow(IMenuManager manager) {
//				ReplayView.this.fillContextMenu(manager);
//			}
//		});
//		Menu menu = menuMgr.createContextMenu(viewer.getControl());
//		viewer.getControl().setMenu(menu);
//		getSite().registerContextMenu(menuMgr, viewer);
//	}

	private void contributeToActionBars() {
		final IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

//	private void makeActions() {
//		action1 = new Action() {
//			public void run() {
//				showMessage("Action 1 executed");
//			}
//		};
//		action1.setText("Action 1");
//		action1.setToolTipText("Action 1 tooltip");
//		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
//			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
//
//		action2 = new Action() {
//			public void run() {
//				showMessage("Action 2 executed");
//			}
//		};
//		action2.setText("Action 2");
//		action2.setToolTipText("Action 2 tooltip");
//		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
//				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
//		doubleClickAction = new Action() {
//			public void run() {
//				ISelection selection = viewer.getSelection();
//				Object obj = ((IStructuredSelection)selection).getFirstElement();
//				showMessage("Double-click detected on "+obj.toString());
//			}
//		};
//	}

//	private void hookDoubleClickAction() {
//		viewer.addDoubleClickListener(new IDoubleClickListener() {
//			public void doubleClick(DoubleClickEvent event) {
//				doubleClickAction.run();
//			}
//		});
//	}
//	private void showMessage(String message) {
//		MessageDialog.openInformation(
//			viewer.getControl().getShell(),
//			"Simulation Replay View",
//			message);
//	}

	private void play() {
		if (_thread != null && _thread.isAlive()) {
			_thread.interrupt();
		}
		_thread = new Thread(_updater);
		_thread.start();

		_playing = true;
		updateState();
	}

	private void pause() {
		if (_thread != null && _thread.isAlive()) {
			_thread.interrupt();
		}
		_thread = null;

		_playing = false;
		updateState();
	}

	private void toStart() {
		if (_thread != null && _thread.isAlive()) {
			_thread.interrupt();
		}
		_thread = null;

		_playing = false;
		_time = 0;
		updateState();
	}

	private void toEnd() {
		if (_thread != null && _thread.isAlive()) {
			_thread.interrupt();
		}
		_thread = null;

		_playing = false;
		if (_exp != null) {
			_time = _exp.getTotalTime();
		} else {
			_time = 0;
		}
		updateState();
	}

	private void timeSelect(int secs) {
		if (_thread != null && _thread.isAlive()) {
			_thread.interrupt();
			try {
				_thread.join();
			} catch (final InterruptedException e) {
			}
		}
		_time = secs;
		if (_playing) {
			_thread = new Thread(_updater);
			_thread.start();
		}
		updateState();
	}

	private void speedChanged() {
		if (_thread != null && _thread.isAlive()) {
			_thread.interrupt();
			try {
				_thread.join();
			} catch (final InterruptedException e) {
			}
		}
		_speed = getCurrentSpeed();
		if (_playing) {
			_thread = new Thread(_updater);
			_thread.start();
		}
		updateState();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().addPartListener(this);
			final IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (part != null) {
				final Project project = getSelectedModel(part.getEditorInput());
				setInput(project);
			}
		} catch (final NullPointerException e) {
//			setInput(null);
		}
//		viewer.getControl().setFocus();
		btnPlay.setFocus();
	}

	public void setInput(Project project) {
		if (project != _project) {
			if (_thread != null && _thread.isAlive()) {
				_thread.interrupt();
			}
			_thread = null;
			_exp = null;
			if (_project != null) {
				ExperimentRegistry.getInstance().removeListener(this);
				_project = null;
			}

			_project = project;

			if (project != null) {
				ExperimentRegistry.getInstance().addListener(this);
			}
			experimentUpdated(project);

			toStart();
		}
	}

	@Override
	public void experimentUpdated(Project project) {
		if (project == _project && _project != null) {
			_exp = ExperimentRegistry.getInstance().getExperiment(_project);
			if (_exp != null) {
				progressTime.setEnabled(true);
				progressTime.setMaximum((int) Math.floor(_exp.getTotalTime()));
			} else {
				progressTime.setMaximum(10);
				progressTime.setSelection(0);
				progressTime.setEnabled(false);
			}
			updateState();
		}
	}

	private void updateState() {
		if (_exp == null) {
			btnPlay.setEnabled(false);
			btnPause.setEnabled(false);
			btnToBegin.setEnabled(false);
			btnToEnd.setEnabled(false);
			btnExport.setEnabled(false);
		} else {
			if (_playing) {
				btnPause.setEnabled(true);
				btnPlay.setEnabled(false);
			} else {
				btnPause.setEnabled(false);
				btnPlay.setEnabled(true);
			}
			btnToBegin.setEnabled(true);
			btnToEnd.setEnabled(true);
			btnExport.setEnabled(true);
			updateTimeState();
		}
	}

	private void updateTimeState() {
		if (_time > _exp.getTotalTime()) {
			_time = _exp.getTotalTime();
			toEnd();
		}
//		System.out.println("Updating time: " + _time);
		int sec = (int) Math.floor(_time);
		if (sec > progressTime.getMaximum()) {
			sec = progressTime.getMaximum();
		}
		progressTime.setSelection(sec);
		lblTime.setText(format.format(_time));
		HighlightTrains.highlight(_project, _time, _exp);

		final Object[] expanded = viewer.getExpandedElements();
//		_expandedTrains.clear();
//		for (Object expandedObject: expanded)
//		{
//			if (expandedObject instanceof FirstLevelChild)
//			{
//				FirstLevelChild child = (FirstLevelChild) expandedObject;
//				_expandedTrains.add(child.node.getTrain());
//			}
//		}

//		Object[] expanded = viewer.getExpandedElements();
		viewer.getTree().setRedraw(false);
		viewer.setInput(new ContentInput(_exp, _time));
		((ViewContentProvider) viewer.getContentProvider()).expandChildren(expanded);
		viewer.getTree().setRedraw(true);

		for (final IReplayListener listener : _listeners) {
			listener.onReplayPositionChanged(_time, _exp);
		}
	}

	@Override
	public void dispose() {
		try {
			if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().removePartListener(this);
			}
		} catch (final NullPointerException e) {
		}
		super.dispose();
	}

	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
		if (partRef.getId().equals("safecap.diagram.part.SafecapDiagramEditorID") && partRef instanceof EditorReference) {
			Project project;
			try {
				project = getSelectedModel(((EditorReference) partRef).getEditorInput());
				if (project != null) {

					setInput(project);
				}
			} catch (final PartInitException e) {
				e.printStackTrace();
			}
//			System.out.println("Active: " + partRef.getTitle());
		}
	}

	private static Project getSelectedModel(IEditorInput obj) {
		if (obj instanceof FileEditorInput) {
			final FileEditorInput input = (FileEditorInput) obj;
			return EmfUtil.fromFile(input.getFile());
		}

		return null;
	}

	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partVisible(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub

	}
}