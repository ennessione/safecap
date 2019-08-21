package uk.ac.ncl.safecap.analytics.ctextract.ui;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEToken;
import uk.ac.ncl.safecap.analytics.ctextract.core.CTETokenList;
import uk.ac.ncl.safecap.analytics.ctextract.core.IVisitor;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTBin;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTProductionRule;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTProject;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTProjectPart;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTRewriteRule;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTRule;
import uk.ac.ncl.safecap.analytics.ctextract.main.PatternInfo;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.Bins;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.ProductionRules;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.RewriteRules;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.RuleContainer;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.RulePattern;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.RuleTemplate;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ColumnModel;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ITablePart;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.TableModel;

public class ExtractionEditor extends EditorPart {
	private TreeViewer treeViewer;
	private boolean isDirty = false;

	public ExtractionEditor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		try {
			final CTProject prj = (CTProject) treeViewer.getInput();
			final IFile ifile = ((FileEditorInput) treeViewer.getData("fileInput")).getFile();
			CTUtils.saveProjectToFile(prj, ifile);
			setDirty(false);
		} catch (JAXBException | CoreException | IOException ee) {
			ee.printStackTrace();
			MessageDialog.openError(treeViewer.getControl().getShell(), "Saving error", ee.getMessage());
		}
	}

	@Override
	public void doSaveAs() {
		doSave(null);
	}

	public static CTProject getInput(FileEditorInput input) {

		try {
			if (input.getFile().exists()) {
				return CTUtils.getProjectFromStream(input.getFile().getContents());
			} else {
				return null;
			}
		} catch (final Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	public CTProject getFileInput() {
		return getInput((FileEditorInput) getEditorInput());
	}

	public CTProject getLiveInput() {
		return (CTProject) treeViewer.getInput();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setInput(input);
		setSite(site);

	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite container) {

		final Object inputElement = getInput((FileEditorInput) getEditorInput());
		if (!(inputElement instanceof CTProject)) {
			final Label l = new Label(container, SWT.NONE);
			l.setText("Invalid input");
			return;
		}

		final CTProject project = (CTProject) inputElement;

		final Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new FillLayout());
		treeViewer = new TreeViewer(composite, SWT.FULL_SELECTION | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		// transitionViewer.getTree().setLayoutData(new FillLayout());

		treeViewer.setContentProvider(new ExtractionContentProvider(project));
		treeViewer.setLabelProvider(new ExtractionLabelProvider());
		// treeViewer.setComparator(new MyComparator());
		treeViewer.getTree().setFont(JFaceResources.getFont(JFaceResources.TEXT_FONT));
		treeViewer.setInput(inputElement);
		treeViewer.setData("fileInput", getEditorInput());

		treeViewer.getTree().setSortDirection(SWT.DOWN);
		// functionViewer.getTree().setSortColumn(functionViewer.getTree().getColumn(0));

		TreeViewerEditor.create(treeViewer, new ColumnViewerEditorActivationStrategy(treeViewer) {
			@Override
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION;
			}
		}, ColumnViewerEditor.DEFAULT);

		enableFunctionViewerEditing();

		// context menu
		final MenuManager menuMgr = new MenuManager("#ViewerMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}
		});
		final Menu menu = menuMgr.createContextMenu(treeViewer.getTree());
		treeViewer.getTree().setMenu(menu);
	}

	protected void fillContextMenu(IMenuManager manager) {
		if (treeViewer.getSelection() instanceof TreeSelection) {
			final TreeSelection tsel = (TreeSelection) treeViewer.getSelection();
			if (tsel.getFirstElement() instanceof RewriteRules) {
				final RewriteRules rules = (RewriteRules) tsel.getFirstElement();
				manager.add(new AddRewriteRule(rules));
			} else if (tsel.getFirstElement() instanceof ProductionRules) {
				final ProductionRules rules = (ProductionRules) tsel.getFirstElement();
				manager.add(new AddProductionRule(rules));
			} else if (tsel.getFirstElement() instanceof CTRule) {
				final CTRule rule = (CTRule) tsel.getFirstElement();
				final TreePath path = tsel.getPathsFor(rule)[0];
				final RuleContainer container = (RuleContainer) path.getSegment(path.getSegmentCount() - 2);
				manager.add(new DisableRule(container, rule));
				manager.add(new DeleteRule(container, rule));
			} else if (tsel.getFirstElement() instanceof PatternInfo) {
				final PatternInfo rule = (PatternInfo) tsel.getFirstElement();
				final TreePath path = tsel.getPathsFor(rule)[0];
				final CTProjectPart container = (CTProjectPart) path.getSegment(path.getSegmentCount() - 2);
				manager.add(new SelectPatternRule(container, rule));
			} else if (tsel.getFirstElement() instanceof Bins) {
				final Bins bins = (Bins) tsel.getFirstElement();
				manager.add(new AddBin(bins));
			} else if (tsel.getFirstElement() instanceof CTBin) {
				final CTBin bin = (CTBin) tsel.getFirstElement();
				final TreePath path = tsel.getPathsFor(bin)[0];
				final Bins bins = (Bins) path.getSegment(path.getSegmentCount() - 2);
				defineBinColumn(manager, bins, bin);
				manager.add(new DeleteBin(bins, bin));
			}
		}
	}

	private void defineBinColumn(IMenuManager manager, Bins bins, CTBin bin) {
		final MenuManager subMenu = new MenuManager("Target column", null);
		manager.add(subMenu);
		if (bin.getPath() == null || !bin.getPath().equals(bins.container.getPath())) {
			subMenu.add(new Action("Current") {
				@Override
				public void run() {
					bin.setPath(bins.container.getPath());
					setDirty(true);
					treeViewer.refresh(bin);
				}
			});
		}

		final TableModel table = bins.container.getParent().getTable(bins.container.ownColumn());
		defineBinColumn(subMenu, bins, bin, table);
	}

	private void defineBinColumn(IMenuManager manager, Bins bins, CTBin bin, TableModel table) {
		final MenuManager subMenu = new MenuManager(table.getName(), null);
		manager.add(subMenu);
		for (final ITablePart part : table.getParts()) {
			if (part instanceof ColumnModel) {
				final ColumnModel tm = (ColumnModel) part;
				if (tm.getConceptPaths().size() > 0) {
					final MenuManager subSubMenu = new MenuManager(tm.getName(), null);
					subMenu.add(subSubMenu);
					for (final String path : tm.getConceptPaths()) {
						if (!path.equals(bin.getPath())) {
							addPathAction(bin, subSubMenu, path);
						}
					}

				} else if (tm.getConceptPaths().size() == 1 && !tm.getConceptPaths().get(0).equals(bin.getPath())) {
					addPathAction(bin, subMenu, tm.getConceptPaths().get(0));
				}
			} else if (part instanceof TableModel) {
				final TableModel subtable = (TableModel) part;
				defineBinColumn(subMenu, bins, bin, subtable);
			}
		}
	}

	private void addPathAction(CTBin bin, MenuManager subMenu, String path) {
		subMenu.add(new Action(path) {
			@Override
			public void run() {
				bin.setPath(path);
				setDirty(true);
				treeViewer.refresh(bin);
			}
		});
	}

//	private void defineBinAttributions(IMenuManager manager, CTProjectPart container, CTEPartBase pattern,
//			final Object parent) {
//		List<CTEToken> binless = pattern.filter(new ITokenFilter() {
//			@Override
//			public boolean accept(CTEToken token, Object data) {
//				return token.getKind() == KIND.ELEMENT && token.getBin() == null;
//			}
//
//		}, null);
//
//		for (final CTEToken t : binless) {
//			MenuManager subMenu = new MenuManager("Element index " + t.getIndex(), null);
//			for (CTBin bin : container.getBins()) {
//				subMenu.add(new Action("Goes into " + bin.getName()) {
//					@Override
//					public void run() {
//						t.setBin(bin.getName());
//						setDirty(true);
//						treeViewer.refresh(parent);
//					}
//				});
//			}
//			manager.add(subMenu);
//		}
//
//	}

	class AddBin extends Action {
		private final Bins bins;

		AddBin(Bins bins) {
			this.bins = bins;
			setText("Add new");
			setId("Add new");
		}

		@Override
		public void run() {
			try {
				final InputDialog dlg = new InputDialog(null, "", "New label", "", new PartLabelValidator());
				if (dlg.open() == Window.OK) {
					final CTBin bin = new CTBin(dlg.getValue(), bins.container.getPath());
					bins.container.getBins().add(bin);
					bin.setParent(bins.container);
					setDirty(true);
					treeViewer.refresh(bins);
				}
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}
	}

	class DeleteBin extends Action {
		private final Bins bins;
		private final CTBin bin;

		DeleteBin(Bins bins, CTBin bin) {
			this.bins = bins;
			this.bin = bin;
			setText("Delete");
			setId("Delete");
		}

		@Override
		public void run() {
			try {
				bins.container.getBins().remove(bin);
				setDirty(true);
				treeViewer.refresh(bins.container);
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}
	}

	class SelectPatternRule extends Action {
		private final CTProjectPart container;
		private final PatternInfo pattern;

		SelectPatternRule(CTProjectPart container, PatternInfo rule) {
			this.container = container;
			pattern = rule;
			setText("Select");
			setId("Select");
		}

		@Override
		public void run() {
			try {
				final CTProductionRule rule = new CTProductionRule();
				final StringBuilder sb = new StringBuilder();
				sb.append("S ");
				pattern.pattern.visit(new IVisitor() {
					@Override
					public void visit(CTEToken part) {
						switch (part.getKind()) {
						case NOISE:
							sb.append("N");
							break;
						case ELEMENT:
							sb.append("{?}");
							break;
						case OPERATOR:
							sb.append("O[" + part.getToken() + "]");
							break;
						case START:
							sb.append("S");
							break;
						case END:
							sb.append("X");
							break;
						}
					}

					@Override
					public void next() {
						sb.append(" ");
					}

					@Override
					public boolean enter(CTETokenList part) {
						sb.append("(");
						return true;
					}

					@Override
					public void leave(CTETokenList part) {
						sb.append(")");
					}

				}, null);
				sb.append("X ");
				rule.setContainer(container);
				rule.setPattern(sb.toString());
				rule.setTemplate("()");
				container.addProductionRule(rule);
				setDirty(true);
				treeViewer.refresh(container);
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}
	}

	class AddRewriteRule extends Action {
		private final RewriteRules container;

		AddRewriteRule(RewriteRules container) {
			this.container = container;
			setText("Add new");
			setId("Add new");
		}

		@Override
		public void run() {
			try {
				final CTRewriteRule rule = new CTRewriteRule();
				container.container.addRewriteRule(rule);
				setDirty(true);
				treeViewer.refresh(container);
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}
	}

	class AddProductionRule extends Action {
		private final ProductionRules container;

		AddProductionRule(ProductionRules container) {
			this.container = container;
			setText("Add new");
			setId("Add new");
		}

		@Override
		public void run() {
			try {
				final CTProductionRule rule = new CTProductionRule();
				rule.setContainer(container.container);
				container.container.addProductionRule(rule);
				setDirty(true);
				treeViewer.refresh(container);
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}
	}

	class DisableRule extends Action {
		private final RuleContainer container;
		private final CTRule rule;

		DisableRule(RuleContainer container, CTRule rule) {
			this.container = container;
			this.rule = rule;
			setText(rule.isEnabled() ? "Disable" : "Enable");
			setId("Disable");
		}

		@Override
		public void run() {
			try {
				rule.setEnabled(!rule.isEnabled());
				setDirty(true);
				treeViewer.refresh(container.container);
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}
	}

	class DeleteRule extends Action {
		private final RuleContainer container;
		private final CTRule rule;

		DeleteRule(RuleContainer container, CTRule rule) {
			this.container = container;
			this.rule = rule;
			setText("Delete");
			setId("Delete");
		}

		@Override
		public void run() {
			try {
				container.container.getRewriteRules().remove(rule);
				container.container.getProductionRules().remove(rule);
				setDirty(true);
				treeViewer.refresh(container.container);
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}
	}

	private void enableFunctionViewerEditing() {
		treeViewer.setColumnProperties(new String[] { "col1" });

		treeViewer.setCellEditors(new CellEditor[] { new TextCellEditor(treeViewer.getTree()) });
		treeViewer.setCellModifier(new ICellModifier() {

			@Override
			public void modify(Object _element, String property, Object value) {
				if (!(_element instanceof TreeItem) || value == null || value.toString().length() == 0) {
					return;
				}

				final TreeItem ti = (TreeItem) _element;
				final Object element = ti.getData();
				final String newinfo = value.toString();

				if (element instanceof RulePattern) {
					final RulePattern rp = (RulePattern) element;
					rp.rule.setPattern(newinfo);
					setDirty(true);
					treeViewer.refresh(rp.rule.getContainer());
					return;
				}

				if (element instanceof RuleTemplate) {
					final RuleTemplate rp = (RuleTemplate) element;
					rp.rule.setTemplate(newinfo);
					setDirty(true);
					treeViewer.refresh(rp.rule.getContainer());
					return;
				}

			}

			@Override
			public Object getValue(Object element, String property) {
				if (element instanceof RulePattern) {
					final RulePattern rp = (RulePattern) element;
					return rp.rule.getPattern();
				}

				if (element instanceof RuleTemplate) {
					final RuleTemplate rp = (RuleTemplate) element;
					return rp.rule.getTemplate();
				}

				return "";
			}

			@Override
			public boolean canModify(Object element, String property) {
				return element instanceof RulePattern || element instanceof RuleTemplate;
			}
		});
	}

	public final void setDirty(boolean isDirty) {
		if (this.isDirty == isDirty) {
			return;
		}

		this.isDirty = isDirty;
		firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
	}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void rebuild() {
		treeViewer.setAutoExpandLevel(3);
		treeViewer.refresh();

	}

	class PartLabelValidator implements IInputValidator {
		@Override
		public String isValid(String newText) {
			if (newText.length() == 0) {
				return "The name may not be empty";
			}
			if (!Character.isLetter(newText.charAt(0))) {
				return "The name may not start with a digit or a special character";
			}

			for (final char c : newText.toCharArray()) {
				if (!Character.isLetter(c) && !Character.isDigit(c) && c != '_' && c != '-') {
					return "Invalid character " + c + " - must be a letter or a digit or underscore or dash";
				}
			}

			return null;
		}
	}

}
