package uk.ac.ncl.safecap.xdata.verification.console;

import java.util.ArrayList;
import java.util.List;

import safecap.Project;
import safecap.schema.Node;
import uk.ac.ncl.safecap.mcommon.conf.SubOverlapBuilder;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.Overlap;
import uk.ac.ncl.safecap.misc.core.OverlapUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.misc.core.SubOverlap;
import uk.ac.ncl.safecap.misc.core.SubRoute;
import uk.ac.ncl.safecap.misc.core.SubRouteUtil;

public class SchemaFixOverlaps extends SchemaBaseCommand {
	public static SchemaFixOverlaps INSTANCE = new SchemaFixOverlaps();

	private SchemaFixOverlaps() {
	}

	@Override
	public String getName() {
		return "unsafe:fixoverlaps";
	}

	@Override
	public int getArguments() {
		return 0;
	}

	@Override
	public void execute(ISafeCapConsole console, String[] arguments) {
		try {
			final Project project = console.getProject();
			for (final Overlap ol : OverlapUtil.getAllOverlaps(project)) {
				final List<SubOverlap> so_list = OverlapUtil.getOverlapSubOverlaps(project, ol);
				if (so_list == null) {
					console.out("Overlap " + ol.getName() + " is broken");
				}

				final List<Node> path = getNodePath(console, ol);
				if (path == null || path.size() < 1) {
					console.out("\tCannot repair " + ol.getName() + ": no node path");
					continue;
				}
				final Node last = path.get(path.size() - 1);
				final List<SubRoute> newpath = SubRouteUtil.getSubRoutePath(ol.getSignal(), last);
				if (newpath == null || newpath.isEmpty()) {
					console.out("\tCannot find path from signal " + ol.getSignal() + " to node " + last);
					continue;
				}

				SubOverlapBuilder.deleteOverlapTransactionally(ol.getSignal(), ol.getName());
				SubOverlapBuilder.commitOverlapTransactionally(project, ol.getSignal(), last, ol.getKind());
				console.out("\tFixed overlap " + ol.getName());

			}

			for (final Overlap ol : OverlapUtil.getAllOverlaps(project)) {
				final List<SubOverlap> so_list = OverlapUtil.getOverlapSubOverlaps(project, ol);
				if (so_list == null) {
					console.out("Overlap " + ol.getName() + " is broken");
				} else {
					console.out("Overlap " + ol.getName() + " is ok");
				}
			}
		} catch (final Throwable e) {
			e.printStackTrace();
			console.err(e.getMessage());
		}
		return;
	}

	private static List<Node> getNodePath(ISafeCapConsole console, Overlap overlap) {
		final Project root = EmfUtil.getProject(overlap.getSignal());
		final String path = ExtensionUtil.getString(overlap.getSignal(), SafecapConstants.EXT_OVERLAP_PATH, overlap.getName());

		if (path != null) {
			final List<Node> result = new ArrayList<>();
			final String[] parts = path.split(";");
			for (final String p : parts) {
				Node n = NodeUtil.getByLabel(root, p);
				if (n == null) {
					n = NodeUtil.getByLabel(root, "P" + p);
				}
				if (n == null) {
					console.err("\tnode " + p + " cannot be found");
					return null;
				}
				result.add(n);
			}

			return result;
		}

		return null;
	}

}
