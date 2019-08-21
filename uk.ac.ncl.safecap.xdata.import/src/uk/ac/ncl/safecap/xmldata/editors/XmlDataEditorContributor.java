package uk.ac.ncl.safecap.xmldata.editors;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.wb.swt.ResourceManager;

import com.opencsv.CSVReader;

import uk.ac.ncl.safecap.xmldata.DataConfigurationException;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataContract;
import uk.ac.ncl.safecap.xmldata.DataNamespace;
import uk.ac.ncl.safecap.xmldata.DataUtils;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.TypeDecomposition;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;
import uk.ac.ncl.safecap.xmldata.ui.XmlDataPlugin;

public class XmlDataEditorContributor extends MultiPageEditorActionBarContributor {
	private XmlDataEditor activeEditorPart;
	private Action exportAction;
	private Action inferenceAction;
	private Action untypeAction;
	private Action contractAction;
	private Action hideEmptyAction;
	private Action detectFormulasAction;

	public XmlDataEditorContributor() {
		super();
		createActions();
	}

	// protected IAction getAction(ITextEditor editor, String actionID) {
	// return (editor == null ? null : editor.getAction(actionID));
	// }

	@Override
	public void setActivePage(IEditorPart part) {
		if (activeEditorPart == part) {
			return;
		}

		activeEditorPart = (XmlDataEditor) part;
		hideEmptyAction.setChecked(activeEditorPart.isHideEmpty());
	}

	private void createActions() {

		exportAction = new Action() {
			@Override
			public void run() {
				exportData(false);
			}
		};
		exportAction.setText("Export Descriptions");
		exportAction.setImageDescriptor(
				ResourceManager.getPluginImageDescriptor("uk.ac.ncl.safecap.xdata.verification", "icons/xml_exports.png"));
		exportAction.setToolTipText("Save block/function annotations to a .csv file");

		// importAction = new Action() {
		// @Override
		// public void run() {
		// importData();
		// }
		// };
		// importAction.setText("Import Descriptions");
		// importAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor("uk.ac.ncl.safecap.xdata.verification",
		// "icons/xml_exports.png"));
		// importAction.setToolTipText("Read in block/function annotations from a .csv
		// file");

		inferenceAction = new Action() {
			@Override
			public void run() {
				typeInference();
			}

		};
		inferenceAction.setText("Type inference");
		inferenceAction.setImageDescriptor(
				ResourceManager.getPluginImageDescriptor("uk.ac.ncl.safecap.xdata.verification", "icons/question_16.png"));
		inferenceAction.setToolTipText("Attempt to solve and unify types");

		untypeAction = new Action() {
			@Override
			public void run() {
				dropTypes();
			}
		};
		untypeAction.setText("Untype");
		untypeAction
				.setImageDescriptor(ResourceManager.getPluginImageDescriptor("uk.ac.ncl.safecap.xdata.verification", "icons/cross_16.png"));
		untypeAction.setToolTipText("Remove typing");

		contractAction = new Action() {
			@Override
			public void run() {
				insertContract();
			}
		};
		contractAction.setText("Impose contract");
		contractAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor("uk.ac.ncl.safecap.xdata.import", "icons/paper.png"));
		contractAction.setToolTipText("Insert contract information");

		hideEmptyAction = new Action("Hide empty", IAction.AS_CHECK_BOX) {
			@Override
			public void run() {

				if (getPage() != null && getPage().getActiveEditor() instanceof XmlDataEditor) {
					final XmlDataEditor x = (XmlDataEditor) getPage().getActiveEditor();
					x.setHideEmpty(!x.isHideEmpty());
					setChecked(x.isHideEmpty());
				}
			}
		};
		if (getPage() != null && getPage().getActiveEditor() instanceof XmlDataEditor) {
			final XmlDataEditor x = (XmlDataEditor) getPage().getActiveEditor();
			hideEmptyAction.setChecked(x.isHideEmpty());
		}

		hideEmptyAction
				.setImageDescriptor(ResourceManager.getPluginImageDescriptor("uk.ac.ncl.safecap.xdata.verification", "icons/pending.gif"));
		hideEmptyAction.setToolTipText("Hide empty elements");

		/*
		 * detectFormulasAction = new Action("Detect formulas") {
		 * 
		 * @Override public void run() {
		 * 
		 * if (getPage() != null && getPage().getActiveEditor() instanceof
		 * XmlDataEditor) { XmlDataEditor x = (XmlDataEditor)
		 * getPage().getActiveEditor(); DataContext ctx = x.getMyInput(); StringBuffer
		 * sb = new StringBuffer();
		 * 
		 * Map<String, List<String>> cases = new HashMap<String, List<String>>();
		 * 
		 * for (String f : ctx.getFunctionIds()) { XFunctionBasic ff = (XFunctionBasic)
		 * ctx.getFunction(f); for (Object d : ff.domain()) { for (Object r : ff.get(d))
		 * { if (looksLikeFormula(r.toString())) { String concept = null;
		 * 
		 * if (ctx.getConceptMap() != null) concept =
		 * ctx.getConceptMap().getConceptProvenance(ff.getCanonicalName());
		 * 
		 * if (concept == null) concept = ff.getCanonicalName(); List<String> info =
		 * cases.get(concept); if (info == null) { info = new ArrayList<String>();
		 * cases.put(concept, info); } info.add(d.toString() + ": " + r.toString()); } }
		 * } }
		 * 
		 * for (String s : cases.keySet()) { sb.append(s + "\n"); for (String d :
		 * cases.get(s)) { sb.append("\t\t"); sb.append(d); sb.append("\n"); }
		 * sb.append("\n"); }
		 * 
		 * if (sb.length() == 0) sb.append("No formulas found"); new
		 * TextDialog(Display.getDefault().getActiveShell(), sb.toString()).open(); } }
		 * 
		 * private boolean looksLikeFormula(String string) { String t = string.trim();
		 * if (t.indexOf("or") > 0) return true; for (char c : t.toCharArray()) { if
		 * (Character.isWhitespace(c)) return true; if (c == '$' || c == '#' || c == ','
		 * || c == '!') return true; if (c == '[' || c == ']') return true; if (c == '{'
		 * || c == '}') return true; } return false; } };
		 * detectFormulasAction.setImageDescriptor(ResourceManager.
		 * getPluginImageDescriptor("uk.ac.ncl.safecap.xdata.import",
		 * "icons/function.png")); detectFormulasAction.
		 * setToolTipText("Detect unparsed formulas in relation values");
		 */
	}

	protected void insertContract() {
		final XmlDataEditor editor = getEditor();

		if (editor != null) {
			final DataContext context = editor.getMyInput();
			if (context != null) {
				final FileDialog dialog = new FileDialog(editor.getSite().getShell(), SWT.OPEN);
				dialog.setText("Contract import");
				dialog.open();
				if (dialog.getFileName() == null || dialog.getFileName().length() == 0) {
					return;
				}

				final String filename = dialog.getFilterPath() + File.separator + dialog.getFileName();

				FileReader reader = null;
				try {
					reader = new FileReader(filename);
					importContract(reader, new File(filename), context);
				} catch (final IOException e) {
					e.printStackTrace();
					MessageDialog.openError(null, "Annotation import", "Import failed");
				} finally {
					try {
						if (reader != null) {
							reader.close();
						}
					} catch (final IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	private void importContract(Reader reader, File file, DataContext context) {
		DataContract contract = null;
		try {
			contract = DataUtils.readContract(reader, file);
		} catch (DataConfigurationException | IOException e) {
			e.printStackTrace();
			MessageDialog.openError(null, "Contract import error", e.getMessage());
			return;
		}

		context.setContract(contract);
		try {
			context.build();
		} catch (final DataConfigurationException e) {
			final boolean reply = MessageDialog.openQuestion(null, "Contract apply error",
					e.getMessage() + "\nTry with a forced contract?");
			if (reply) {
				try {
					context.buildContractForced();
				} catch (final DataConfigurationException e1) {
					MessageDialog.openError(null, "Contract apply error", e1.getMessage());
					e1.printStackTrace();
				}
			}
		}
		// context.typeInference();
		if (getPage() != null && getPage().getActiveEditor() instanceof XmlDataEditor) {
			final XmlDataEditor x = (XmlDataEditor) getPage().getActiveEditor();
			x.setDirty(true);
			x.functionViewer.refresh();
		}
	}

	protected void dropTypes() {
		try {
			final XmlDataEditor editor = getEditor();

			if (editor != null) {
				final DataContext context = editor.getMyInput();
				if (context != null) {
					context.dropTypes();
					context.build();
					editor.setDirty(true);
					editor.functionViewer.refresh();
				}
			}
		} catch (final DataConfigurationException e) {
			e.printStackTrace();
		}

	}

	protected void typeInference() {

		final XmlDataEditor editor = getEditor();

		if (editor != null) {
			final DataContext context = editor.getMyInput();
			if (context != null) {
				context.typeInference();
				editor.setDirty(true);
				editor.functionViewer.refresh();
			}
		}

	}

	@Override
	public void contributeToMenu(IMenuManager manager) {
		final IMenuManager menu = new MenuManager("Data &Model");
		manager.prependToGroup(IWorkbenchActionConstants.MB_ADDITIONS, menu);
		menu.add(hideEmptyAction);
		menu.add(exportAction);
		menu.add(untypeAction);
		menu.add(inferenceAction);
		menu.add(contractAction);
		// menu.add(detectFormulasAction);
	}

	private XmlDataEditor getEditor() {
		final IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (part instanceof XmlDataEditor) {
			return (XmlDataEditor) part;
		} else {
			for (final IEditorReference x : PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences()) {
				if (x.getEditor(false) instanceof XmlDataEditor) {
					return (XmlDataEditor) x.getEditor(false);
				}
			}
		}
		return null;
	}

	protected void exportData(boolean empty) {
		final XmlDataEditor editor = getEditor();

		if (editor != null) {
			final DataContext context = editor.getMyInput();
			if (context != null) {
				final FileDialog dialog = new FileDialog(editor.getSite().getShell(), SWT.SAVE);
				dialog.setText("Annotation export");
				dialog.setFileName(empty ? "empty_info.csv" : "data_info.csv");
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
					exportData(out, context, empty);
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
		}
	}

	protected void importData() {
		final XmlDataEditor editor = getEditor();

		if (editor != null) {
			final DataContext context = editor.getMyInput();
			if (context != null) {
				final FileDialog dialog = new FileDialog(editor.getSite().getShell(), SWT.OPEN);
				dialog.setText("Annotation import");
				dialog.open();
				if (dialog.getFileName() == null || dialog.getFileName().length() == 0) {
					return;
				}

				final String filename = dialog.getFilterPath() + File.separator + dialog.getFileName();

				FileReader fstream = null;
				try {
					fstream = new FileReader(filename);
					final int updated = importData(fstream, context);
					MessageDialog.openInformation(null, "Annotation import", "Updated " + updated + " annotation(s)");
					if (updated > 0) {
						editor.setDirty(true);
					}
				} catch (final IOException e) {
					e.printStackTrace();
					MessageDialog.openError(null, "Annotation import", "Import failed");
				} finally {
					try {
						if (fstream != null) {
							fstream.close();
						}
					} catch (final IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private int importData(FileReader in, DataContext context) throws IOException {
		String[] parts;
		int updated = 0;
		final CSVReader reader = new CSVReader(in);
		while ((parts = reader.readNext()) != null) {
			if (parts.length > 2) {
				final String kind = DataUtils.unquote(parts[0]);
				if ("namespace".equals(kind) && parts.length >= 3) {
					final DataNamespace namespace = context.getNamespaceById(DataUtils.unquote(parts[1]));
					if (namespace != null && parts[2].length() > 0 && !parts[2].equals(namespace.getDescription())) {
						namespace.setDescription(DataUtils.unquote(parts[2]));
					}
				} else if ("element".equals(kind) && parts.length >= 4) {
					final String funid = DataUtils.unquote(parts[1]);
					final IXFunction f = context.getFunction(funid);
					if (f != null && parts[3].length() > 0 && !parts[3].equals(f.getDescription())) {
						f.setDescription(DataUtils.unquote(parts[3]));
						updated++;
					}
				}
			}
		}

		reader.close();

		return updated;
	}

	private void exportData(PrintWriter out, DataContext context, boolean empty) {

		if (!empty) {
			out.print("comment,\"Header section\"\n");
			out.print("date," + new Date() + "\n");
			out.print("name," + XmlDataPlugin.getDefault().getBundle().getSymbolicName() + "\n");
			out.print("versionmajor," + XmlDataPlugin.getDefault().getBundle().getVersion().getMajor() + "\n");
			out.print("versionminor," + XmlDataPlugin.getDefault().getBundle().getVersion().getMinor() + "\n");
			out.print("versionmicro," + XmlDataPlugin.getDefault().getBundle().getVersion().getMicro() + "\n");

			out.print("comment\n");
			out.print("comment,\"Namespace definitions\"\n");
			out.print("comment,Name,Description\n");
			for (final DataNamespace namespace : context.getNamespaces()) {
				out.print("namespace,");
				out.print(namespace.getId());
				out.print(",");
				out.print(namespace.getDescription() == null ? "" : "\"" + namespace.getDescription() + "\"");
				out.print("\n");
			}

			out.print("comment\n");
			out.print("comment,\"Type decomposition definitions\"\n");
			out.print("comment,Type,Name,Pattern,,Description\n");

			final List<TypeDecomposition> decs = new ArrayList<>(context.getTypeDecomposition().size());
			decs.addAll(context.getTypeDecomposition());
			Collections.sort(decs);

			for (final TypeDecomposition tdec : decs) {
				out.print("typedecomposition,");
				out.print("\"" + tdec.getType() + "\"");
				out.print(",");
				out.print("\"" + tdec.getName() + "\"");
				out.print(",");
				out.print("\"" + tdec.getPattern() + "\"");
				out.print(",,");
				out.print("\"" + tdec.getDescription() + "\"");
				out.print("\n");
			}
		}

		out.print("comment\n");
		out.print("comment,\"Element definitions\"\n");
		out.print("comment,Name,Domain,Range,Kind,Description,Path\n");
		for (final String func : context.getFunctionIds()) {
			final IXFunction ixf = context.getFunction(func);
			if ((!empty || ixf.domain().isEmpty()) && ixf.getFunctionType() != null) {
				final String cn = ixf.getCanonicalName();
				final String name = context.getShortName().get(cn);
				final String domain = context.getFunctionDomainType().get(cn);
				final String range = context.getFunctionDomainType().get(cn);
				out.print("element,");
				out.print(name);
				out.print(",");
				final XRelationType type = (XRelationType) ixf.getFunctionType();
				if (domain != null && range != null) {
					out.print("\"" + domain + "\"");
					out.print(",");
					out.print("\"" + range + "\"");
					out.print(",");
					out.print("\"" + type.getKind().getLongName() + "\"");
				} else if (ixf.getFunctionType() != null && type.getDomain() != null && type.getRange() != null) {
					out.print("\"" + type.getDomain().toString() + "\"");
					out.print(",");
					out.print("\"" + type.getRange().toString() + "\"");
					out.print(",");
					out.print("\"" + type.getKind().getLongName() + "\"");
				} else {
					out.print(",,");
				}
				out.print(",");
				out.print(ixf.getDescription() == null ? "" : "\"" + ixf.getDescription() + "\"");
				out.print(",");
				out.print("\"" + ixf.getCanonicalName() + "\"");
				out.print("\n");
			}
		}
	}

	@Override
	public void contributeToToolBar(IToolBarManager manager) {
		manager.add(new Separator());
		manager.add(hideEmptyAction);
		manager.add(inferenceAction);
		manager.add(untypeAction);
		manager.add(new Separator());
		manager.add(exportAction);
		manager.add(contractAction);
		// manager.add(detectFormulasAction);

	}
}
