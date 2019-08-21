package uk.ac.ncl.safecap.xmldata.editors;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.gef.ui.internal.parts.WrapTextCellEditor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import uk.ac.ncl.safecap.misc.core.LocatedValue;
import uk.ac.ncl.safecap.xmldata.Block;
import uk.ac.ncl.safecap.xmldata.DataConfigurationException;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataNamespace;
import uk.ac.ncl.safecap.xmldata.DataUtils;
import uk.ac.ncl.safecap.xmldata.FileUtil;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.TypeDecomposition;
import uk.ac.ncl.safecap.xmldata.TypeDecompositionBuilder;
import uk.ac.ncl.safecap.xmldata.TypeDecompositionException;
import uk.ac.ncl.safecap.xmldata.ValueList;
import uk.ac.ncl.safecap.xmldata.editors.DataTreeContentProvider.Description;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.FDomain;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.FDomainType;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.FInfo;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.FRangeType;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.FunctionBundle;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;
import uk.ac.ncl.safecap.xmldata.types.XIntegerType;
import uk.ac.ncl.safecap.xmldata.types.XRealType;
import uk.ac.ncl.safecap.xmldata.ui.UIUtils;

@SuppressWarnings("restriction")
public class XmlDataEditor extends MultiPageEditorPart implements IResourceChangeListener {
	private static final String ID = "uk.ac.ncl.safecap.xmldata.editors.XmlDataEditor";

	/** The tree viewer for raw data. */
	private TreeViewer rawDataViewer;

	/** The tree viewer for functions. */
	TreeViewer functionViewer;

	private boolean isDirty = false;

	private boolean isHideEmpty = false;

	final Clipboard cb = new Clipboard(Display.getDefault());

	/**
	 * Creates a multi-page editor example.
	 */
	public XmlDataEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	public TreeViewer getRawDataViewer() {
		return rawDataViewer;
	}

	public TreeViewer getFunctionViewer() {
		return functionViewer;
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

	public boolean isHideEmpty() {
		return isHideEmpty;
	}

	public void setHideEmpty(boolean isHideEmpty) {
		this.isHideEmpty = isHideEmpty;
		functionViewer.refresh();
	}

	/**
	 * Creates page 0 of the multi-page editor, which contains a text editor.
	 */
	void createPage0() {

		try {
			// createImage();
			final Composite composite = new Composite(getContainer(), SWT.NONE);
			composite.setLayout(new FillLayout());
			rawDataViewer = new TreeViewer(composite, SWT.FULL_SELECTION | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
			rawDataViewer.getTree().setLayoutData(new FillLayout());
			rawDataViewer.setContentProvider(new DataTreeContentProvider());
			rawDataViewer.setLabelProvider(new DataTreeLabelProvider());
			rawDataViewer.setInput(getInput((FileEditorInput) getEditorInput()));
			rawDataViewer.setData("fileInput", getEditorInput());
			rawDataViewer.getTree().setFont(JFaceResources.getFont(JFaceResources.TEXT_FONT));
			final Tree tree = rawDataViewer.getTree();

			String name = ((FileEditorInput) getEditorInput()).getFile().getName();
			if (name.indexOf('.') != -1) {
				name = name.substring(0, name.indexOf('.'));
			}

			setPartName(name);

			final Menu menu = new Menu(tree);
			rawDataViewer.getControl().setMenu(menu);
			// getSite().registerContextMenu(menuMgr, viewer);

			final MenuItem report = new MenuItem(menu, SWT.PUSH);
			report.setText("Generate html template");
			report.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					final String path = UIUtils.openFileSaveDialog("*.html");
					if (path != null) {
						try {
							final File file = new File(path);
							final DataContext context = (DataContext) rawDataViewer.getInput();
							final ReportGenerator repg = new ReportGenerator(context);
							repg.saveReport(file);
						} catch (final Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			});

			final MenuItem delete = new MenuItem(menu, SWT.PUSH);
			delete.setText("Remove");
			delete.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					final List<DataNamespace> namespaces = new ArrayList<>();
					final List<Block> blocks = new ArrayList<>();
					for (final TreeItem item : tree.getSelection()) {
						if (item.getData() instanceof DataNamespace) {
							final DataNamespace d = (DataNamespace) item.getData();
							namespaces.add(d);
						} else if (item.getData() instanceof Block) {
							final Block b = (Block) item.getData();
							blocks.add(b);
						}
					}

					if (!namespaces.isEmpty() || !blocks.isEmpty()) {
						final boolean c = MessageDialog.openConfirm(rawDataViewer.getControl().getShell(), "Remove items",
								"Really remove " + (namespaces.size() + blocks.size()) + " item(s)?");

						if (c) {
							final DataContext context = (DataContext) rawDataViewer.getInput();
							synchronized (context) {
								context.getNamespaces().removeAll(namespaces);
								for (final DataNamespace nm : context.getNamespaces()) {
									nm.getBlocks().removeAll(blocks);
								}
							}

							// context.cookTypes();

							try {
								final IFile ifile = ((FileEditorInput) rawDataViewer.getData("fileInput")).getFile();
								DataUtils.clearCache(ifile);
								context.build();
								DataUtils.saveDataContextToFile(context, ifile);
							} catch (JAXBException | CoreException | IOException | DataConfigurationException ee) {
								ee.printStackTrace();
								MessageDialog.openError(functionViewer.getControl().getShell(), "Saving error", ee.getMessage());
							}
						}
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {

				}

			});

			menu.setEnabled(true);

			enableFunctionViewerEditing2();

			final int index = addPage(composite);
			setPageText(index, "Raw data view");
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	public static DataContext getInput(FileEditorInput input) {

		try {
			if (input.getFile().exists()) {
				return DataUtils.fetchDataContext(input.getFile());
			} else {
				return null;
			}
		} catch (final DataConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void setTitle(IEditorInput input) {
		String name = input.getName();
		String version = null;
		int i = name.indexOf(".xmldata");
		if (i > 0) {
			name = name.substring(0, i);
			i = name.indexOf("__");
			if (i > 0) {
				version = name.substring(i + 3);
				name = name.substring(0, i);
			}
		}

		if (version != null) {
			super.setPartName(name + " [" + version + "]");
		} else {
			super.setPartName(name);
		}
	}

	public DataContext getMyInput() {
		return getInput((FileEditorInput) getEditorInput());
	}

	/**
	 * Creates page 1 of the multi-page editor, which allows you to change the font
	 * used in page 2.
	 */
	void createPage1() {
		final Composite composite = new Composite(getContainer(), SWT.NONE);
		composite.setLayout(new FillLayout());
		functionViewer = new TreeViewer(composite, SWT.FULL_SELECTION | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		functionViewer.getTree().setLayoutData(new FillLayout());
		functionViewer.setContentProvider(new FunctionTreeContentProvider(this));
		functionViewer.setLabelProvider(new FunctionTreeLabelProvider());
		functionViewer.setInput(getInput((FileEditorInput) getEditorInput()));
		functionViewer.setData("fileInput", getEditorInput());
		functionViewer.setComparator(new MyComparator());
		functionViewer.getTree().setFont(JFaceResources.getFont(JFaceResources.TEXT_FONT));

		setTitle(getEditorInput());
		
		functionViewer.getTree().addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseUp(MouseEvent e) {
				if (e.button == 1 && (e.stateMask & SWT.CTRL) == SWT.CTRL) {
					final TreeItem item = functionViewer.getTree().getItem(new Point(e.x, e.y));
					final Object data = item.getData();

					if (data instanceof FDomain) {
						final FDomain fdomain = (FDomain) data;
						final Collection<Object> values = fdomain.function.get(fdomain.obj);
						final Set<LocatedValue> list = new HashSet<>();

						if (fdomain.obj instanceof LocatedValue) {
							list.add((LocatedValue) fdomain.obj);
						}

						for (final Object o : values) {
							walk(o, list);
						}

						if (!list.isEmpty()) {

							final Object[] loclist = list.toArray();

							final ListDialog dialog = new ListDialog(functionViewer.getControl().getShell());
							dialog.setTitle("Source link");
							dialog.setMessage("Select object");
							dialog.setInput(loclist);
							dialog.setContentProvider(new ArrayContentProvider());
							dialog.setLabelProvider(new LabelProvider() {
								@Override
								public String getText(Object element) {
									if (element instanceof LocatedValue) {
										final LocatedValue lv = (LocatedValue) element;
										final XFunctionBasic ff = (XFunctionBasic) fdomain.function;
										final String source = ff.getDataContext().getSourceFile();
										if (source != null) {
											lv.setFile(source);
										}
										return lv.getValue().toString() + " : " + source;
									} else {
										return "?";
									}
								}
							});
							dialog.setInitialSelections(new Object[] { loclist[0] });
							dialog.setAddCancelButton(true);
							dialog.setHelpAvailable(false);
							if (dialog.open() == Window.OK) {
								if (dialog.getResult().length > 0) {
									if (dialog.getResult()[0] instanceof LocatedValue) {
										final LocatedValue lv = (LocatedValue) dialog.getResult()[0];
										if (lv.getFile() != null) {
											String contents = null;
											try {
												contents = FileUtil.getFileContents(new File(lv.getFile()));
											} catch (final Exception e1) {
											}

											if (contents == null) {
												MessageDialog.openError(functionViewer.getControl().getShell(), "Read error",
														"Failed reading source file " + lv.getFile());
											} else {
												final XmlTextDialog tdialog = new XmlTextDialog(functionViewer.getControl().getShell(),
														lv.getFile());
												tdialog.addHighlight(lv);
												tdialog.open();
											}
										}
									}
								}
							}
						}
					}
				}

			}

			private void walk(Object o, Collection<LocatedValue> list) {
				if (o instanceof LocatedValue) {
					list.add((LocatedValue) o);
				} else if (o instanceof ValueList) {
					final ValueList vl = (ValueList) o;
					for (final Object x : vl.getValues()) {
						walk(x, list);
					}
				}
			}

			private String prettyFormatXML(String input, int indent) {
				try {
					final Source xmlInput = new StreamSource(new StringReader(input));
					final StringWriter stringWriter = new StringWriter();
					final StreamResult xmlOutput = new StreamResult(stringWriter);
					final TransformerFactory transformerFactory = TransformerFactory.newInstance();
					transformerFactory.setAttribute("indent-number", indent);
					final Transformer transformer = transformerFactory.newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
					transformer.transform(xmlInput, xmlOutput);
					return xmlOutput.getWriter().toString();
				} catch (final Exception e) {
					return "";
				}
			}

		});

		final Menu menu = new Menu(functionViewer.getTree());
		functionViewer.getControl().setMenu(menu);

		final MenuItem delete = new MenuItem(menu, SWT.PUSH);
		delete.setText("Remove");
		delete.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				final TreeSelection ts = (TreeSelection) functionViewer.getSelection();
				if (ts.size() == 1) {
					final Object object = ts.getFirstElement();
					if (object instanceof IXFunction) {
						removeFunction((IXFunction) object);
					} else if (object instanceof TypeDecomposition) {
						removeTypeDecomposition((TypeDecomposition) object);
					}
				}
			}

			private void removeTypeDecomposition(TypeDecomposition object) {
				final boolean c = MessageDialog.openConfirm(functionViewer.getControl().getShell(), "Remove items",
						"Really remove type decomposition " + object.getName() + "?");

				if (c) {
					final DataContext context = (DataContext) rawDataViewer.getInput();
					context.getTypeDecomposition().remove(object);
					final IFile ifile = ((FileEditorInput) rawDataViewer.getData("fileInput")).getFile();
					DataUtils.clearCache(ifile);
					rebuildContext(context);
				}
			}

			private void removeFunction(IXFunction function) {
				final boolean c = MessageDialog.openConfirm(functionViewer.getControl().getShell(), "Remove items",
						"Really remove function " + function.getName() + "?");

				if (c) {
					final DataContext context = (DataContext) rawDataViewer.getInput();
					context.getFunctions().remove(function);

					final IFile ifile = ((FileEditorInput) rawDataViewer.getData("fileInput")).getFile();
					DataUtils.clearCache(ifile);
					try {
						DataUtils.saveDataContextToFile(context, ifile);
					} catch (JAXBException | CoreException | IOException ee) {
						ee.printStackTrace();
						MessageDialog.openError(functionViewer.getControl().getShell(), "Saving error", ee.getMessage());
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}

		});

		final MenuItem copy = new MenuItem(menu, SWT.PUSH);
		copy.setText("Copy to Clipboard");
		copy.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final TreeSelection ts = (TreeSelection) functionViewer.getSelection();
				if (ts.size() == 1) {
					final Object object = ts.getFirstElement();
					if (object instanceof String) {
						final String cp = (String) object;
						if (cp.startsWith(FunctionTreeContentProvider.SCHEMA_PATH)) {
							final String text = cp.substring(FunctionTreeContentProvider.SCHEMA_PATH.length());
							cb.setContents(new Object[] { text }, new Transfer[] { TextTransfer.getInstance() });
						}
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}

		});

		functionViewer.getTree().setSortDirection(SWT.DOWN);
		// functionViewer.getTree().setSortColumn(functionViewer.getTree().getColumn(0));

		TreeViewerEditor.create(functionViewer, new ColumnViewerEditorActivationStrategy(functionViewer) {
			@Override
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION;
			}
		}, ColumnViewerEditor.DEFAULT);

		enableFunctionViewerEditing();

		final int index = addPage(composite);
		setPageText(index, "Function view");
	}

	class MyComparator extends ViewerComparator {
		@Override
		public int category(Object element) {
			if (element instanceof IXFunction) {
				return 100;

//				int dot = f.getName().indexOf('.');
//				if (dot > 0) {
//					String prefix = f.getName().substring(0, dot);
//					return 100 + Math.abs(prefix.hashCode() >> 1);
//				}
//				dot = f.getName().indexOf(':');
//				if (dot > 0) {
//					String prefix = f.getName().substring(0, dot);
//					return 100 + Math.abs(prefix.hashCode() >> 1);
//				}
//				return 50 + Math.abs(f.getName().hashCode());
			} else if (element instanceof FDomain) {
				final FDomain fdom = (FDomain) element;
				return 100 + Math.abs(fdom.obj.hashCode());
			} else {
				final String s = element.toString();
				if (s.startsWith("Total") || s.startsWith("*") || s.startsWith("Types")) {
					return 0;
				} else {
					return 10;
				}
			}
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			final int cat1 = category(e1);
			final int cat2 = category(e2);

			if (cat1 != cat2) {
				return cat1 - cat2;
			}

			final String name1 = getLabel(viewer, e1);
			final String name2 = getLabel(viewer, e2);

			// use the comparator to compare the strings
			return name1.compareTo(name2);
		}

		private String getLabel(Viewer viewer, Object e1) {
			if (e1 instanceof IXFunction) {
				final IXFunction f = (IXFunction) e1;
				return f.getName();
			} else if (e1 instanceof FDomain) {
				final FDomain fdom = (FDomain) e1;
				return fdom.obj.toString();
			} else if (e1 instanceof FunctionBundle) {
				final FunctionBundle fb = (FunctionBundle) e1;
				return fb.name;
			} else {
				return e1.toString();
			}
		}

		@Override
		public boolean isSorterProperty(Object element, String property) {
			return false;
		}
	}

	private void enableFunctionViewerEditing() {
		functionViewer.setColumnProperties(new String[] { "col1" });
		// Create text editor
		functionViewer.setCellEditors(new CellEditor[] {
				// new TextCellEditor(functionViewer.getTree())}
				new WrapTextCellEditor(functionViewer.getTree()) });
		functionViewer.setCellModifier(new ICellModifier() {

			@Override
			public void modify(Object _element, String property, Object value) {
				try {
					if (!(_element instanceof TreeItem) || value == null || value.toString().length() == 0) {
						return;
					}

					final TreeItem ti = (TreeItem) _element;
					final Object element = ti.getData();

					if (element instanceof TypeDecompositionTemplate) {
						final String newinfo = value.toString();
						final TypeDecompositionTemplate info = (TypeDecompositionTemplate) element;
						// todo
						try {
							new TypeDecompositionBuilder(info.context, info.type, newinfo);
							info.context.computeTypeDecompositions();
							;
						} catch (final TypeDecompositionException e) {
							MessageDialog.openError(functionViewer.getControl().getShell(), "Type decomposition", e.getMessage());
						}

						setDirty(true);
						functionViewer.refresh();
						return;
					} else if (element instanceof FInfo) {
						final String newinfo = value.toString();
						final FInfo info = (FInfo) element;
						info.function.setDescription(newinfo);
						setDirty(true);
						functionViewer.refresh();
						return;
					} else if (element instanceof FDomainType) {
						final FDomainType domt = (FDomainType) element;
						final XFunctionBasic fb = (XFunctionBasic) domt.function;
						final String newtype = value.toString();

						try {
							fb.getDataContext().setDomainTypeInfo(fb, newtype);
						} catch (final DataConfigurationException e1) {
							MessageDialog.openError(functionViewer.getControl().getShell(), "Domain type",
									"Failed casting type: " + e1.getMessage());
						}

						// saveChangesAndType(fb);
						fb.getDataContext().computeTypes();
						setDirty(true);
						functionViewer.refresh();
					} else if (element instanceof FRangeType) {
						final FRangeType rant = (FRangeType) element;
						final XFunctionBasic fb = (XFunctionBasic) rant.function;
						// TypeRegistry typeRegistry =
						// fb.getDataContext().getTypeRegistry();
						final String newtype = value.toString();
						try {
							fb.getDataContext().setRangeTypeInfo(fb, newtype);
						} catch (final DataConfigurationException e1) {
							MessageDialog.openError(functionViewer.getControl().getShell(), "Domain type",
									"Failed casting type: " + e1.getMessage());
						}

						// saveChangesAndType(fb);
						fb.getDataContext().computeTypes();
						setDirty(true);
						functionViewer.refresh();
					} else if (element instanceof Description) {
						final Description d = (Description) element;
						d.source.setDescription(value.toString());
						ti.setText(value.toString());
						XmlDataEditor.this.setDirty(true);
					}
				} catch (final Throwable e) {
					e.printStackTrace();
					MessageDialog.openError(functionViewer.getControl().getShell(), "Error", e.getMessage());
				}

			}

			@Override
			public Object getValue(Object element, String property) {
				if (element instanceof Description) {
					final Description d = (Description) element;
					return d.source.getDescription() != null ? d.source.getDescription() : "";
				}
				return "";
			}

			@Override
			public boolean canModify(Object element, String property) {
				return element instanceof FDomainType || element instanceof FRangeType || element instanceof Description
						|| element instanceof XIntegerType || element instanceof XRealType || element instanceof FInfo
						|| element instanceof TypeDecompositionTemplate;
			}
		});
	}

	private void enableFunctionViewerEditing2() {
		// You have to create identifier for tree columns
		rawDataViewer.setColumnProperties(new String[] { "col1" });
		// Create text editor
		rawDataViewer.setCellEditors(new CellEditor[] { new WrapTextCellEditor(rawDataViewer.getTree()) });
		rawDataViewer.setCellModifier(new ICellModifier() {

			@Override
			public void modify(Object _element, String property, Object value) {
				if (!(_element instanceof TreeItem)) {
					return;
				}

				final TreeItem ti = (TreeItem) _element;
				final Object element = ti.getData();

				if (element instanceof Description) {
					final Description d = (Description) element;
					d.source.setDescription(value.toString());
					ti.setText(value.toString());
					XmlDataEditor.this.setDirty(true);
				}
			}

			@Override
			public Object getValue(Object element, String property) {
				if (element instanceof Description) {
					final Description d = (Description) element;
					return d.source.getDescription() != null ? d.source.getDescription() : "";
				}
				return "";
			}

			@Override
			public boolean canModify(Object element, String property) {
				return element instanceof Description;
			}
		});
	}

	/**
	 * Creates the pages of the multi-page editor.
	 */
	@Override
	protected void createPages() {
		createPage0();
		createPage1();
	}

	/**
	 * The <code>MultiPageEditorPart</code> implementation of this
	 * <code>IWorkbenchPart</code> method disposes all nested editors. Subclasses
	 * may extend.
	 */
	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	/**
	 * Saves the multi-page editor's document.
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		// getEditor(0).doSave(monitor);

		// save
		try {
			final DataContext dataContext = (DataContext) rawDataViewer.getInput();
			final IFile ifile = ((FileEditorInput) rawDataViewer.getData("fileInput")).getFile();
			DataUtils.saveDataContextToFile(dataContext, ifile);
			setDirty(false);
		} catch (JAXBException | CoreException | IOException ee) {
			ee.printStackTrace();
			MessageDialog.openError(functionViewer.getControl().getShell(), "Saving error", ee.getMessage());
		}
	}

	@Override
	public void doSaveAs() {
		doSave(null);
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method checks
	 * that the input is an instance of <code>IFileEditorInput</code>.
	 */
	@Override
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput)) {
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		}
		super.init(site, editorInput);
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart.
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);

	}

	private void closeAllEditors(IFile ifile) {
		final IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
		for (int i = 0; i < pages.length; i++) {
			for (final IEditorReference z : pages[i].findEditors(null, ID, org.eclipse.ui.IWorkbenchPage.MATCH_ID)) {
				final IEditorPart part = z.getEditor(false);
				if (part != null) {
					final XmlDataEditor editor = (XmlDataEditor) part;
					final IFile other = ((FileEditorInput) editor.rawDataViewer.getData("fileInput")).getFile();
					if (ifile.equals(other)) {
						pages[i].closeEditor(editor, false);
					}
				}
			}
		}
	}

	private void refreshAllEditors(IFile ifile) {
		try {
			final Object input = DataUtils.fetchDataContext(ifile);

			final IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
			for (int i = 0; i < pages.length; i++) {
				for (final IEditorReference z : pages[i].findEditors(null, ID, org.eclipse.ui.IWorkbenchPage.MATCH_ID)) {
					final IEditorPart part = z.getEditor(false);
					if (part != null) {
						final XmlDataEditor editor = (XmlDataEditor) part;
						final IFile other = ((FileEditorInput) editor.rawDataViewer.getData("fileInput")).getFile();
						if (ifile.equals(other)) {
							editor.rawDataViewer.setInput(input);
							editor.functionViewer.setInput(input);
							editor.functionViewer.refresh();
						}
					}
				}
			}
		} catch (final DataConfigurationException e) {

			e.printStackTrace();
			MessageDialog.openError(functionViewer.getControl().getShell(), "Contract error", e.getMessage());
		}
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		final IFile ifile = ((FileEditorInput) rawDataViewer.getData("fileInput")).getFile();

		final IResourceDelta rootDelta = event.getDelta();

		// get the delta, if any, for the documentation directory
		final IResourceDelta docDelta = rootDelta.findMember(ifile.getFullPath());
		if (docDelta == null || isDirty) {
			return;
		}

		// clearCache(ifile);

		if (docDelta.getKind() == IResourceDelta.REMOVED) {
			final Display display = rawDataViewer.getControl().getDisplay();
			if (!display.isDisposed()) {
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						closeAllEditors(ifile);
					}
				});
			}
			return;
		}

		// we are only interested in POST_CHANGE events
		if (docDelta.getKind() == IResourceDelta.CHANGED || docDelta.getKind() == IResourceDelta.REPLACED) {
			final Display display = rawDataViewer.getControl().getDisplay();
			if (!display.isDisposed()) {
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						refreshAllEditors(ifile);
					}
				});
			}
		}
	}

	private void rebuildContext(DataContext context) {
		final IFile ifile = ((FileEditorInput) rawDataViewer.getData("fileInput")).getFile();
		try {
			DataUtils.saveDataContextToFile(context, ifile);
			context.build();
		} catch (JAXBException | CoreException | IOException | DataConfigurationException ee) {
			ee.printStackTrace();
			MessageDialog.openError(functionViewer.getControl().getShell(), "Saving error", ee.getMessage());
		}
	}

	// private void saveChangesAndType(XFunctionBasic fb) throws
	// DataConfigurationException {
	// try {
	// IFile ifile = ((FileEditorInput)
	// rawDataViewer.getData("fileInput")).getFile();
	// fb.getDataContext().computeTypes();
	// UIUtils.saveDataContextToFile(fb.getDataContext(), ifile);
	// } catch (JAXBException | CoreException | IOException e) {
	// e.printStackTrace();
	// MessageDialog.openError(functionViewer.getControl().getShell(),
	// "Saving error", e.getMessage());
	// }
	// }

}
