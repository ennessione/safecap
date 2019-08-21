package uk.ac.ncl.safecap.xdata.verification.console;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

import safecap.Project;
import safecap.schema.Node;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.Overlap;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;

public class SchemaFixNodes extends SchemaBaseCommand {
	public static SchemaFixNodes INSTANCE = new SchemaFixNodes();

	private SchemaFixNodes() {
	}

	@Override
	public String getName() {
		return "unsafe:fixnodes";
	}

	@Override
	public int getArguments() {
		return 0;
	}

	@Override
	public void execute(ISafeCapConsole console, String[] arguments) {
		try {
			final Project root = console.getProject();
			final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);
			domain.getCommandStack().execute(new RecordingCommand(domain) {

				@Override
				protected void doExecute() {
					int index = 1;
					for(Node node: root.getNodes()) {
						String label = node.getLabel();
						if (label == null || label.length() < 2 || conflicting(label, node) || !label.startsWith("N")) {
							String newLabel = generateLabel(root, node, index);
							node.setLabel(newLabel);
							index++;
						}
					}
				}

				private String generateLabel(Project root, Node node, int index) {
					String candidate = "NN" + index;
					int subindex = 1;
					while (conflicting(candidate, node)) {
						subindex++;
						candidate = "NN" + index + "_" + subindex;
					}
					return candidate;
				}

				private boolean conflicting(String candidate, Node self) {
					for(Node node: root.getNodes()) 
						if (node != self && candidate.equals(node.getLabel()))
							return true;
					
					return false;
				}
			});			

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
