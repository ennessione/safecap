package uk.ac.ncl.safecap.xdata.verification.console;

import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;

import safecap.Labeled;
import safecap.Project;
import safecap.model.Ambit;
import safecap.schema.Segment;
import safecap.schema.SegmentRole;

public class ExportSchemaToCSV extends SchemaBaseCommand {

	public static ExportSchemaToCSV INSTANCE = new ExportSchemaToCSV();

	private ExportSchemaToCSV() {
	}

	@Override
	public String getName() {
		return "export:schema:cvs";
	}

	@Override
	public int getArguments() {
		return 0;
	}

	@Override
	public void execute(ISafeCapConsole console, String[] arguments) {
		final Project prj = console.getProject();
		buildSections(prj);
		buildEdges(prj);
		buildNodePositions(prj, console.getGraphicalEditPart());
	}

	private void buildNodePositions(Project prj, IGraphicalEditPart gep) {
		line("comment", "node id", "node type", "graphical x", "graphical y");				
		for(safecap.schema.Node node: prj.getNodes()) {
			final IGraphicalEditPart nodePart = (IGraphicalEditPart) gep.findEditPart(gep, node);
			final Bounds b = (Bounds) ((Node) nodePart.getModel()).getLayoutConstraint();
			line("node", node.getLabel(), node.getKind().getName(), b.getX(), b.getY());
		}
	}

	private void buildSections(Project prj) {
		line("comment", "section id", "list of segment ids");		
		for (final Ambit ambit : prj.getAmbits()) {
			line("section", ambit.getLabel(), ppSegmentList(ambit.getSegments(), ", "));
		}
	}

	public static String ppLabeledList(List<? extends Labeled> list, String separator) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				sb.append(separator);
			}
			sb.append(list.get(i).getLabel());
		}
		return sb.toString();
	}
	
	public static String ppSegmentList(List<Segment> list, String separator) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				sb.append(separator);
			}
			sb.append(list.get(i).hashCode());
		}
		return sb.toString();
	}	

	private void buildEdges(Project prj) {
		line("comment", "segment id", "point role", "crossover role", "from node", "to node", "length");
		for (final Segment s : prj.getSegments()) {
			line("segment", s.hashCode(), s.getPointrole().getName(), crossoverRole(s), s.getFrom().getLabel(), s.getTo().getLabel(), "" + s.getLength());
		}
	}
	

	private Object crossoverRole(Segment s) {
		if ((s.getRole() & SegmentRole.CROSS_A_VALUE) == SegmentRole.CROSS_A_VALUE) 
			return "A";
		else if ((s.getRole() & SegmentRole.CROSS_B_VALUE) == SegmentRole.CROSS_B_VALUE) 
			return "B";
		else
			return "none";
				
	}

	private void line(Object ... strings) {
		for (int i = 0; i < strings.length; i++) {
			if (i > 0) {
				write(", ");
			}
			write(strings[i].toString());
		}

		write("\n");
	}	
	
	private void write(String x) {
		System.out.print(x);
	}
}
