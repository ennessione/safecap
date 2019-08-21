package uk.ac.ncl.safecap.xdata.verification.console;

import java.util.List;

import safecap.Orientation;
import safecap.Project;
import safecap.schema.Node;
import safecap.trackside.LeftSignal;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.SignalUtil;
import uk.ac.ncl.safecap.misc.core.SubRoute;
import uk.ac.ncl.safecap.misc.core.SubRouteUtil;

public class SchemaSubRoutePath extends SchemaBaseCommand {
	public static SchemaSubRoutePath INSTANCE = new SchemaSubRoutePath();

	private SchemaSubRoutePath() {
	}

	@Override
	public String getName() {
		return "subroute:path";
	}

	@Override
	public int getArguments() {
		return 2;
	}

	@Override
	public void execute(ISafeCapConsole console, String[] arguments) {
		try {
			final Project project = console.getProject();
			final Signal signalFrom = SignalUtil.getByLabel(project, arguments[0]);
			final Signal signalTo = SignalUtil.getByLabel(project, arguments[1]);
			final Node nodeFrom = NodeUtil.getByLabel(project, arguments[0].trim());
			Node nodeTo = NodeUtil.getByLabel(project, arguments[1].trim());

			if (signalFrom != null && nodeTo != null) {
				final List<SubRoute> list = SubRouteUtil.getSubRoutePath(signalFrom, nodeTo);
				if (list != null) {
					console.out(list.toString());
				} else {
					console.out("none");
				}
			} else if (nodeFrom != null && signalTo != null) {
				nodeTo = SignalUtil.getSignalNode(signalTo);
				final List<SubRoute> list = SubRouteUtil.getSubRoutePath(nodeFrom, nodeTo,
						signalTo instanceof LeftSignal ? Orientation.LEFT : Orientation.RIGHT);
				if (list != null) {
					console.out(list.toString());
				} else {
					console.out("none");
				}
			}

		} catch (final Throwable e) {
			e.printStackTrace();
			console.err(e.getMessage());
		}
		return;
	}

}
