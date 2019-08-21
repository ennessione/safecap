package uk.ac.ncl.safecap.controltable.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import safecap.Project;
import safecap.model.ControlLogic;
import safecap.model.ModelFactory;
import safecap.model.ModelPackage;
import safecap.model.Point;
import safecap.model.Route;
import safecap.model.Rule;
import safecap.schema.Node;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.diagram.misc.highlights.Highlight;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.RuleUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;

public class ControlTable extends ViewPart implements ISelectionListener {
	private TreeViewer viewer;
	private MenuManager _menuManager;
	private Project _project;
	private ContentProvider _contentProvider;
	private EObject _input = null;
	private static final String[] titles = { "Context", "Ambits Clear", "Points Normal", "Points Reverse", "Ambits Occupied",
			"Route states" };
	private static final int[] bounds = { 100, 200, 150, 150, 150, 150 };

	private final ISelectionListener _listener = new ISelectionListener() {
		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			if (selection instanceof IStructuredSelection) {
				final IStructuredSelection sel = (IStructuredSelection) selection;
				Object obj = sel.getFirstElement();

				if (obj instanceof Rule) {
					final Rule rule = (Rule) obj;
					obj = rule.eContainer();
				}

				if (obj instanceof Route) {
					final Route route = (Route) obj;
					Highlight.route(route);
				} else if (obj instanceof Point) {
					final Point point = (Point) obj;
					final List<Segment> list = new ArrayList<>();
					final Node node = point.getNode();
					list.addAll(NodeUtil.getIncomingSegments(node));
					list.addAll(NodeUtil.getOutgoingSegments(node));
					Highlight.segments(list);
				}
			}
		}
	};

	public ControlTable() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		createViewer(parent);

		final ISelectionService selectionService = getSite().getWorkbenchWindow().getSelectionService();
		selectionService.addSelectionListener(SafecapConstants.NAVIGATOR_ID, this);
		selectionService.addSelectionListener(this);
		selectionService.addSelectionListener("uk.ac.ncl.safecap.controltable.main", _listener);

		_menuManager = new MenuManager();
		_menuManager.setRemoveAllWhenShown(true);
		_menuManager.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}
		});
		final Menu menu = _menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
	}

	private ControlLogic getLogic(Object object) {
		ControlLogic logic;
		if (object instanceof Route) {
			logic = RuleUtil.getControlLogic((Route) object, null);
		} else {
			logic = RuleUtil.getControlLogic(((RuleContainer) object).route, ((RuleContainer) object).line);
		}
		return logic;
	}

	private void fillContextMenu(IMenuManager manager) {
		if (viewer.getSelection().isEmpty()) {
			return;
		}
		if (viewer.getSelection() instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
			final Object object = selection.getFirstElement();

			if (object instanceof RuleContainer) {
				final RuleContainer container = (RuleContainer) object;
				final Route route = container.route;
				final ControlLogic logic = RuleUtil.getControlLogic(route, container.line);
				if (logic == RuleUtil.getDefaultLogic(route)) {
					manager.add(new Action() {
						@Override
						public void run() {
							final ControlLogic l = ModelFactory.eINSTANCE.createControlLogic();
							l.setLine(container.line);
							l.setAspects(2);
							final Rule rule = ModelFactory.eINSTANCE.createRule();
							l.getRule().add(rule);

							final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(route);
							if (dom != null) {
								final Command command = new AddCommand(dom, route.getLogic(), l);
								dom.getCommandStack().execute(command);
								save(route.eResource());
							} else {
								route.getLogic().add(l);
								save(route.eResource());
							}
						}

						@Override
						public String getText() {
							return "Switch to custom";
						}
					});
				} else if (logic != null) {
					manager.add(new Action() {
						@Override
						public void run() {
							final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(route);
							if (dom != null) {
								final Command command = new RemoveCommand(dom, route.getLogic(), logic);
								dom.getCommandStack().execute(command);
								save(route.eResource());
							} else {
								route.getLogic().remove(logic);
								save(route.eResource());
							}
						}

						@Override
						public String getText() {
							return "Switch to default";
						}
					});
				}
			} else if (object instanceof Route) {
				final Route route = (Route) object;
				if (route.getLogic().size() > 0) {
					manager.add(new Action() {
						@Override
						public void run() {
							final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(route);
							if (dom != null) {
								final Command command = new RemoveCommand(dom, route.getLogic(), route.getLogic());
								dom.getCommandStack().execute(command);
								save(route.eResource());
							} else {
								route.getLogic().clear();
								save(route.eResource());
							}
						}

						@Override
						public String getText() {
							return "Switch all to default";
						}
					});
				}
			}

			if (object instanceof Route || object instanceof RuleContainer) {
				final ControlLogic l = getLogic(object);
				manager.add(new Action() {
					@Override
					public void run() {
						final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(l);
						if (dom != null) {
							dom.getCommandStack()
									.execute(new SetCommand(dom, l, ModelPackage.eINSTANCE.getControlLogic_Aspects(), l.getAspects() + 1));
//                            dom.getCommandStack().flush();
							save(l.eResource());
						} else {
							l.setAspects(l.getAspects() + 1);
							save(l.eResource());
						}
						viewer.refresh();
					}

					@Override
					public String getText() {
						return "Add aspect";
					}
				});
				if (l.getAspects() > 2) {
					manager.add(new Action() {
						@Override
						public void run() {
							final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(l);
							if (dom != null) {
								dom.getCommandStack().execute(
										new SetCommand(dom, l, ModelPackage.eINSTANCE.getControlLogic_Aspects(), l.getAspects() - 1));
								save(l.eResource());
							} else {
								l.setAspects(l.getAspects() - 1);
								save(l.eResource());
							}

							// Rule rule = ModelFactory.eINSTANCE.createRule();
							// if (dom != null)
							// dom.getCommandStack().execute(new AddCommand(dom,
							// route.getRule(), rule));
							// else
							// route.getRule().add(rule);
							viewer.refresh();
						}

						@Override
						public String getText() {
							return "Remove aspect";
						}
					});
				}
			} else if (object instanceof RuleWrap && ((RuleWrap) object).rule.eContainer() instanceof ControlLogic) {
				final Rule rule = ((RuleWrap) object).rule;
				final ControlLogic logic = (ControlLogic) rule.eContainer();
				if (logic.getRule() != null && logic.getRule().size() > 1) {
					manager.add(new Action() {
						@Override
						public void run() {
							final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(rule);
							if (dom != null) {
								dom.getCommandStack().execute(new RemoveCommand(dom, logic.getRule(), rule));
							} else {
								logic.getRule().remove(rule);
							}

							if (logic.getRule().size() < logic.getAspects() - 1 && logic.getAspects() > 2) {
								if (dom != null) {
									dom.getCommandStack().execute(new SetCommand(dom, logic,
											ModelPackage.eINSTANCE.getControlLogic_Aspects(), logic.getAspects() - 1));
								} else {
									logic.setAspects(logic.getAspects() - 1);
								}
							}

							save(logic.eResource());
							viewer.refresh();
						}

						@Override
						public String getText() {
							return "Remove";
						}
					});
				}
			}
		}
	}

	private static void save(Resource res) {
		try {
			res.save(null);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setFocus() {
//		viewer.getControl().setFocus();
//		viewer.refresh();
	}

	private void createViewer(Composite parent) {

		// Define the TableViewer
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		viewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
		viewer.refresh();

		// Create the columns
		createColumns(parent);

		// Make lines and make header visible
		final Tree tree = viewer.getTree();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		// Set the ContentProvider
		_contentProvider = new ContentProvider();
		viewer.setContentProvider(_contentProvider);
		viewer.setLabelProvider(new LabelProvider());

		// Get the content for the Viewer,
		// setInput will call getElements in the ContentProvider
		// viewer.setInput(ModelProvider.INSTANCE.getPersons());

		// Make the selection available to other Views
		getSite().setSelectionProvider(viewer);

//		// Layout the viewer
//		GridData gridData = new GridData();
//		gridData.verticalAlignment = GridData.FILL;
//		gridData.horizontalSpan = 2;
//		gridData.grabExcessHorizontalSpace = true;
//		gridData.grabExcessVerticalSpace = true;
//		gridData.horizontalAlignment = GridData.FILL;
//		viewer.getControl().setLayoutData(gridData);
	}

	// This will create the columns for the table
	private void createColumns(final Composite parent) {
		// First column is for the first name
		createTableViewerColumn(titles[0], bounds[0], null);
		createTableViewerColumn("", 40, new VerifyActionEditingSupport(viewer));
		createTableViewerColumn(titles[1], bounds[1], new AmbitEditingSupport(viewer));
		createTableViewerColumn(titles[2], bounds[2], new PointEditingSupport(viewer, PointEditingSupport.NORMAL));
		createTableViewerColumn(titles[3], bounds[3], new PointEditingSupport(viewer, PointEditingSupport.REVERSE));
		createTableViewerColumn(titles[4], bounds[4], new TimedOccupationEditingSupport(viewer));
		createTableViewerColumn(titles[5], bounds[5], new RouteStateEditingSupport(viewer));
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

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {

		if (part != this) {
			_selectionChanged(part, selection);
		}
	}

	public void _selectionChanged(IWorkbenchPart part, ISelection selection) {

		if (viewer == null || viewer.getControl() == null || viewer.getControl().isDisposed()) {
			return;
		}

		if (selection.isEmpty()) {
			return;
		}

		if (selection instanceof IStructuredSelection) {
			final Object[] input = ((IStructuredSelection) selection).toArray();
			if (input.length != 1) {
				return;
			}
			for (int i = 0; i < input.length; i++) {
				if (input[i] instanceof EditPart) {
					final EditPart editPart = (EditPart) input[i];
					Object obj = editPart.getModel();
					if (obj instanceof View) {
						final View view = (View) obj;
						obj = view.getDiagram().getElement();

					}
					input[i] = obj;
				}
			}

			if (input[0] instanceof Route) {
				_contentProvider.showRouteChildren = true;
				setInput((EObject) input[0]);
			} else if (input[0] instanceof IFile || input[0] instanceof EObject) {
				_contentProvider.showRouteChildren = false;
				Project project = null;
				if (input[0] instanceof IFile) {
					final IFile file = (IFile) input[0];
					project = EmfUtil.fromFile(file);
				} else if (input[0] instanceof EObject) {
					project = EmfUtil.getProject((EObject) input[0]);
				}

				setInput(project);
			} else {
				setInput(null);
			}
		}
	}

	private void setInput(EObject object) {
		if (_input != object) {
			viewer.setInput(object);
			_input = object;
			viewer.getTree().setRedraw(false);
			viewer.expandAll();
			viewer.getTree().setRedraw(true);
//			viewer.refresh();

			if (object == null) {
				_project = null;
			} else if (object instanceof Project) {
				_project = (Project) object;
			} else if (object instanceof EObject) {
				_project = EmfUtil.getProject(object);
			} else {
				_project = null;
			}
		}
	}

	public Project getProject() {
		return _project;
	}

	@Override
	public void dispose() {
		_project = null;
		final ISelectionService selectionService = getSite().getWorkbenchWindow().getSelectionService();
		selectionService.removeSelectionListener(this);
		selectionService.removeSelectionListener(_listener);
		super.dispose();
	}

}
