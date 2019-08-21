package uk.ac.ncl.safecap.gui.verification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

public class VerificationActionContributionItem extends CompoundContributionItem {
	// public VerificationActionContributionItem(IAction action)
	// {
	// super();
	// // TODO Auto-generated constructor stub
	// }

	// @Override
	// public void fill(ToolBar parent, int index)
	// {
	// super.fill(parent, index);
	// }
	//
	// @Override
	// public void fill(Menu menu, int index) {
	// //Here you could get selection and decide what to do
	// //You can also simply return if you do not want to show a menu
	//
	// //create the menu item
	// MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
	// menuItem.setText("My menu item (" + new Date() + ")");
	// menuItem.addSelectionListener(new SelectionAdapter() {
	// public void widgetSelected(SelectionEvent e) {
	// //what to do when menu is subsequently selected.
	// System.err.println("Dynamic menu selected");
	// }
	// });
	// }

	@Override
	protected IContributionItem[] getContributionItems() {
		final VerificationProfileRegistry registry = VerificationProfileRegistry.getInstance();
		registry.loadFromPrefs();
		final List<CommandContributionItem> contributionItems = new ArrayList<>();

		for (final IVerificationProfile profile : registry.profiles) {
			final CommandContributionItemParameter parm = new CommandContributionItemParameter(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow(), "", "uk.ac.ncl.safecap.gui.verification.verificationCommand1",
					CommandContributionItem.STYLE_PUSH);
			final HashMap<String, String> cmdParms = new HashMap<>();
			cmdParms.put("uk.ac.ncl.safecap.gui.verification.commandParameter1", profile.getName());
			parm.parameters = cmdParms;
			parm.label = profile.getName();
			parm.visibleEnabled = true;
			final CommandContributionItem item = new CommandContributionItem(parm);
			item.setVisible(true);
			contributionItems.add(item);
		}

		return contributionItems.toArray(new CommandContributionItem[contributionItems.size()]);
	}
}
