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

public class RouteSync extends AbstractHandler implements IElementUpdater {
	public static boolean getToggleState() {
		final ICommandService commandService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getService(ICommandService.class);
		final Command command = commandService.getCommand("uk.ac.ncl.safecap.diagram.misc.commandRouteSync");
		return ((Boolean) command.getState(RegistryToggleState.STATE_ID).getValue()).booleanValue();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Command command = event.getCommand();
		HandlerUtil.toggleCommandState(command);
		return null;
	}
//
//    private static Project getSelectedModel(IEditorPart editor)
//    {
//        IEditorInput obj = editor.getEditorInput();
//
//        if (obj instanceof FileEditorInput) {
//            FileEditorInput input = (FileEditorInput) obj;
//            return EmfUtil.fromFile(input.getFile());
//        }
//
//        return null;
//    }
//
//    private static DiagramEditPart getSelectedDiagram(IEditorPart editor)
//    {
//    	if (editor != null && editor instanceof DiagramDocumentEditor) {
//			DiagramDocumentEditor sc_editor = (DiagramDocumentEditor) editor;
//			DiagramEditPart diagram = (DiagramEditPart) sc_editor.getDiagramEditPart();
//			return diagram;
//    	}
//
//    	return null;
//    }

	@Override
	public void updateElement(UIElement element, Map parameters) {
		element.setChecked(getToggleState());
	}

}
