package uk.ac.ncl.safecap.gui.verification;

import java.util.HashMap;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

public class CurrentProfileVerificationActionContributionItem extends CompoundContributionItem {
	@Override
	protected IContributionItem[] getContributionItems() {

		final String profName = VerificationAction.getActiveProfileName();
		if (profName != null && profName.length() > 0) {
			final CommandContributionItemParameter parm = new CommandContributionItemParameter(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow(), "", "uk.ac.ncl.safecap.gui.verification.verificationCommand1",
					CommandContributionItem.STYLE_PUSH);
			final HashMap<String, String> cmdParms = new HashMap<>();
			cmdParms.put("uk.ac.ncl.safecap.gui.verification.commandParameter1", profName);
			parm.parameters = cmdParms;
			parm.label = profName;
			final CommandContributionItem item = new CommandContributionItem(parm);
			item.setVisible(true);
			return new CommandContributionItem[] { item };
		}
		return new CommandContributionItem[0];
	}
}
