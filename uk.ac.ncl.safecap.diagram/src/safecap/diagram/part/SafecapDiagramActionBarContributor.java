package safecap.diagram.part;

import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramActionBarContributor;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPage;

/**
 * @generated
 */
public class SafecapDiagramActionBarContributor extends DiagramActionBarContributor {

	/**
	 * @generated
	 */
	@Override
	protected Class getEditorClass() {
		return SafecapDiagramEditor.class;
	}

	/**
	 * @generated
	 */
	@Override
	protected String getEditorId() {
		return SafecapDiagramEditor.ID;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);
		bars.getToolBarManager().removeAll();

		// IToolBarManager toolBarManager = bars.getToolBarManager();
		//
		// toolBarManager.remove("fontNameContributionItem");
		// toolBarManager.remove("fontSizeContributionItem");
		// toolBarManager.remove("fontBoldAction");
		// toolBarManager.remove("fontItalicAction");
		// toolBarManager.remove("toolbarFontGroup");
		// toolBarManager.remove("toolbarColorLineGroup");
		// toolBarManager.remove("fontColorContributionItem");
		// toolBarManager.remove("fillColorContributionItem");
		// toolBarManager.remove("lineColorContributionItem");
		// toolBarManager.remove("toolbarCopyAppearanceGroup");
		// toolBarManager.remove("copyAppearancePropertiesAction");
		// toolBarManager.remove("toolBarViewGroup");
		// toolBarManager.remove("toolbarEditGroup");
		// toolBarManager.remove("toolbarFormatGroup");
		// toolBarManager.remove("autoSizeAction");
		// toolBarManager.remove("toolbarFilterGroup");
		// toolBarManager.remove("toolbarNavigateGroup");
		// toolBarManager.remove("zoomContributionItem");
		// toolBarManager.remove("toolbarRestGroup");
		// toolBarManager.remove("toolbarAdditions");
		//
		// List<IContributionItem> toremove = new ArrayList<IContributionItem>(10);
		//
		// for (IContributionItem item : toolBarManager.getItems())
		// if (item.getClass().toString().contains("PluginMenuManager"))
		// toremove.add(item);
		//
		// for (IContributionItem item : toremove)
		// toolBarManager.remove(item);
		//
		// for (IContributionItem item : toolBarManager.getItems())
		// System.out.println("item:" + item);

		// // print preview
		// IMenuManager fileMenu = bars.getMenuManager().findMenuUsingPath(
		// IWorkbenchActionConstants.M_FILE);
		// assert fileMenu != null;
		// fileMenu.remove("pageSetupAction"); //$NON-NLS-1$
		// IMenuManager editMenu = bars.getMenuManager().findMenuUsingPath(
		// IWorkbenchActionConstants.M_EDIT);
		// assert editMenu != null;
		// if (editMenu.find("validationGroup") == null) { //$NON-NLS-1$
		// editMenu.add(new GroupMarker("validationGroup")); //$NON-NLS-1$
		// }
		// IAction validateAction = new ValidateAction(page);
		// editMenu.appendToGroup("validationGroup", validateAction); //$NON-NLS-1$
	}
}
