package uk.ac.ncl.safecap.controltable.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;

import safecap.Project;
import safecap.model.ControlLogic;
import safecap.model.Line;
import safecap.model.ModelFactory;
import safecap.model.ModelPackage;
import safecap.model.Route;
import safecap.model.RouteStateRule;
import safecap.model.Rule;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.RouteUtil;
import uk.ac.ncl.safecap.misc.core.RuleUtil;

public class RouteStateEditingSupport extends EditingSupport {

	private final TreeViewer viewer;

	public RouteStateEditingSupport(TreeViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor(viewer.getTree());
	}

	@Override
	protected boolean canEdit(Object element) {
		if (element instanceof RuleWrap) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected Object getValue(Object element) {
		if (element instanceof RuleWrap) {
			final Rule rule = ((RuleWrap) element).rule;
			return LabelProvider.routeListToString(rule);
		}

		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		final Rule rule = ((RuleWrap) element).rule;
		final Project project = EmfUtil.getProject(rule);
		final Line line = RuleUtil.getLineByRule(rule);
		final List<RouteStateRule> list = parseList(project, (String) value, line);
		if (list == null) {
			return;
		}

		if (!rule.getRouteState().equals(list)) {
			final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(rule);
			dom.getCommandStack().execute(new SetCommand(dom, rule, ModelPackage.eINSTANCE.getRule_RouteState(), list));
			EmfUtil.saveProject(project);
			VerificationProvider.invalidateVerificationStatus(rule);
		}

//		viewer.refresh();
	}

	private List<RouteStateRule> parseList(Project root, String text, Line line) {
		final List<RouteStateRule> result = new ArrayList<>();

		if (text == null) {
			return result;
		}

		text = text.trim();

		if (text.length() == 0) {
			return result;
		}

		final String[] items = text.split(",");

		for (final String item : items) {
			String label = item.trim();
			String aspect_label = null;
			int aspect = -1;

			if (label.contains(":")) {
				final String[] parts = label.split(":");
				if (parts.length != 2) {
					MessageDialog.openError(null, "Error parsing signal state expression", "Invalid expression: " + label);
					return null;
				}

				aspect_label = parts[1];
				label = parts[0];
			}

			final Route route = RouteUtil.getByLabel(root, label);
			if (route == null) {
				MessageDialog.openError(null, "Error parsing signal state expression", "No route with name " + label);
				return null;
			}

			if (aspect_label != null) {
				final ControlLogic logic = RuleUtil.getControlLogic(route, line);
				aspect = RouteUtil.parseAspectLabel(logic, aspect_label);

				if (aspect > logic.getAspects() - 1) {
					MessageDialog.openError(null, "Error parsing signal state expression",
							"Invalid route aspect " + RouteUtil.getAspectLabel(logic, aspect) + " (maximum aspect is "
									+ RouteUtil.getAspectLabel(logic, logic.getAspects() - 1) + ")");
					return null;
				}
			}
			final RouteStateRule rs = ModelFactory.eINSTANCE.createRouteStateRule();
			rs.setRoute(route);
			rs.setState(aspect);

			result.add(rs);
		}

		return result;
	}
}
