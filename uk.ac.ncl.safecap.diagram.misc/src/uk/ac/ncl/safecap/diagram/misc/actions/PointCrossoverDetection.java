package uk.ac.ncl.safecap.diagram.misc.actions;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RegistryToggleState;
import org.eclipse.ui.menus.UIElement;

public class PointCrossoverDetection extends AbstractHandler implements IElementUpdater {
	public static boolean getToggleState() {
		final ICommandService commandService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getService(ICommandService.class);
		final Command command = commandService.getCommand("uk.ac.ncl.safecap.diagram.misc.commandPointCrossDet");
		return ((Boolean) command.getState(RegistryToggleState.STATE_ID).getValue()).booleanValue();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Command command = event.getCommand();
		HandlerUtil.toggleCommandState(command);
		return null;
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		element.setChecked(getToggleState());
	}
}
