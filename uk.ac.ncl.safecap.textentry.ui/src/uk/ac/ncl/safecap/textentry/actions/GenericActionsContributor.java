package uk.ac.ncl.safecap.textentry.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

import uk.ac.ncl.safecap.textentry.core.TEPlugin;
import uk.ac.ncl.safecap.textentry.core.TERegistry;

public class GenericActionsContributor extends CompoundContributionItem {

	@Override
	protected IContributionItem[] getContributionItems() {
		final List<CommandContributionItem> contributionItems = new ArrayList<>();

		final TERegistry registry = TEPlugin.getTERegistry();

		for (final String s : registry.getActions()) {
			for (final ITEAction action : registry.getActionInfo(s)) {
				contributionItems.add(makeItem(s, action.getLabel()));
			}
		}

		return contributionItems.toArray(new CommandContributionItem[contributionItems.size()]);
	}

	private CommandContributionItem makeItem(String registry, String id) {
		final CommandContributionItemParameter parm = new CommandContributionItemParameter(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow(), "", GenericAction.COMMAND_ID, CommandContributionItem.STYLE_PUSH);

		final HashMap<String, String> cmdParms = new HashMap<>();
		cmdParms.put(GenericAction.ACTION_PARAM, registry + "///" + id);
		parm.parameters = cmdParms;
		parm.label = id;
		final CommandContributionItem item = new CommandContributionItem(parm);
		item.setVisible(true);
		return item;
	}

}
