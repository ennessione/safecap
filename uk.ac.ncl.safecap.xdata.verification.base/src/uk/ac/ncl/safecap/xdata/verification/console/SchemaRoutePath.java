package uk.ac.ncl.safecap.xdata.verification.console;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;

import safecap.Project;
import uk.ac.ncl.safecap.mcommon.conf.RouteConfig;
import uk.ac.ncl.safecap.misc.core.SegmentPath;

public class SchemaRoutePath extends SchemaBaseCommand {
	public static SchemaRoutePath INSTANCE = new SchemaRoutePath();

	private SchemaRoutePath() {
	}

	@Override
	public String getName() {
		return "route:path";
	}

	@Override
	public int getArguments() {
		return -1;
	}

	@Override
	public void execute(ISafeCapConsole console, String[] arguments) {
		if (console.getGraphicalEditPart() == null) {
			console.err("No graphical edit part");
			return;
		}

		if (arguments.length < 2 || arguments.length > 4) {
			console.err("Invalid parameter number");
			return;
		}

		try {
			final Project project = console.getProject();
			final IGraphicalEditPart editPart = console.getGraphicalEditPart();
			final RouteConfig routeConfig = new RouteConfig(project, editPart);
			final Collection<String> ignoredSignals = new ArrayList<>();
			final Collection<String> pointStates = new ArrayList<>();
			if (arguments.length > 2) {
				final String[] parts = arguments[2].split(";");
				for (final String p : parts) {
					ignoredSignals.add(p.trim());
				}
			}

			if (arguments.length > 3) {
				final String[] parts = arguments[3].split(";");
				for (final String p : parts) {
					pointStates.add(p.trim());
				}
			}
			final SegmentPath path = routeConfig.routePath("xx", arguments[0].trim(), arguments[1].trim(), ignoredSignals, pointStates);
			if (path == null) {
				console.err("No path: " + routeConfig.getErrors());
				return;
			}

			console.out(path.toString());

		} catch (final Throwable e) {
			e.printStackTrace();
			console.err(e.getMessage());
		}
		return;
	}

}
