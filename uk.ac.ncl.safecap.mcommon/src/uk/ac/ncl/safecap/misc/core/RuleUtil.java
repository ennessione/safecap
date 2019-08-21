package uk.ac.ncl.safecap.misc.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

import safecap.Project;
import safecap.model.Ambit;
import safecap.model.ControlLogic;
import safecap.model.Junction;
import safecap.model.Line;
import safecap.model.ModelFactory;
import safecap.model.ModelPackage;
import safecap.model.Point;
import safecap.model.Route;
import safecap.model.Rule;

public class RuleUtil {

	public static ControlLogic getDefaultLogic(Route route) {
		ControlLogic logic = route.getDefaultlogic();
		if (logic == null) {
			logic = buildEmptyLogic(route, 2);
		}
		return logic;
	}

	public static ControlLogic getDefaultLogicSafe(Route route) {
		final ControlLogic logic = route.getDefaultlogic();
		return logic;
	}

	public static ControlLogic getDefaultLogic(Route route, int aspects) {
		ControlLogic logic = route.getDefaultlogic();
		if (logic == null) {
			logic = buildEmptyLogic(route, aspects);
		}
		return logic;
	}

	public static ControlLogic buildEmptyLogic(Route route, int aspects) {
		final ControlLogic logic = ModelFactory.eINSTANCE.createControlLogic();
		logic.setAspects(aspects);
		logic.getRule().add(ModelFactory.eINSTANCE.createRule());

		final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(route);
		if (dom != null) {
			dom.getCommandStack().execute(new SetCommand(dom, route, ModelPackage.eINSTANCE.getRoute_Defaultlogic(), logic));
		} else {
			route.setDefaultlogic(logic);
		}

		if (route.eResource() != null) {
			save(route.eResource());
		}

		return logic;
	}

	public static ControlLogic getControlLogic(Route route, Line line) {
		if (line == null) {
			return getDefaultLogic(route);
		}
		final List<ControlLogic> logics = route.getLogic();
		if (logics == null) {
			return getDefaultLogic(route);
		}
		for (final ControlLogic logic : logics) {
			if (logic.getLine() != null && logic.getLine() == line) {
				return logic;
			}
		}
		return getDefaultLogic(route);
	}

	public static Line getLineByRule(Rule rule) {
		if (rule.eContainer() instanceof ControlLogic) {
			final ControlLogic logic = (ControlLogic) rule.eContainer();
			final Route route = (Route) logic.eContainer();
			if (RuleUtil.getDefaultLogic(route) == logic) {
				return null;
			} else {
				return logic.getLine();
			}
		} else {
			return null;
		}
	}

	public static Line getLineByControlLogic(ControlLogic logic) {
		final Route route = (Route) logic.eContainer();
		if (RuleUtil.getDefaultLogic(route) == logic) {
			return null;
		} else {
			return logic.getLine();
		}
	}

	public static Rule buildInitialRule(Route route) {
		final Rule rule = ModelFactory.eINSTANCE.createRule();

		rule.getClear().addAll(route.getAmbits());

		final PointConf conf = new PointConf(route);
		rule.getNormal().addAll(conf.getNormal());
		rule.getReverse().addAll(conf.getReverse());

		return rule;
	}

	public static Rule buildInitialRule(Point point) {
		final Rule rule = ModelFactory.eINSTANCE.createRule();

		if (point.eContainer() instanceof Junction) {
			final Junction junction = (Junction) point.eContainer();
			rule.getClear().add(junction);
		}

		return rule;
	}

	public static List<Rule> getRouteAspectRules(ControlLogic logic) {
		final List<Rule> result = new ArrayList<>();
		final int aspects = logic.getAspects();
		if (logic.getRule().size() < aspects - 1) {
			final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(logic);
			final List<Rule> rules = new ArrayList<>();
			for (int i = logic.getRule().size(); i < aspects - 1; i++) {
				rules.add(ModelFactory.eINSTANCE.createRule());
			}
			if (dom != null) {
				dom.getCommandStack().execute(new AddCommand(dom, logic, ModelPackage.eINSTANCE.getControlLogic_Rule(), rules));
			} else {
				logic.getRule().addAll(rules);
				save(logic.eResource());
			}
		}
		result.addAll(logic.getRule());
		return result;
	}

	public static List<Rule> getRouteAspectRules(Route route) {
		return getRouteAspectRules(getDefaultLogic(route));
	}

	private static void save(Resource res) {
		try {
			res.save(null);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Rule> getAllPointRules(Project project) {
		final List<Rule> result = new ArrayList<>();
		for (final Ambit a : project.getAmbits()) {
			if (a instanceof Junction) {
				final Junction j = (Junction) a;
				for (final Point p : j.getPoints()) {
					if (p.getRule() != null) {
						result.add(p.getRule());
						// if (p.getRule() == null)
						// {
						// TransactionalEditingDomain dom =
						// TransactionUtil.getEditingDomain(project);
						// Rule rule = ModelFactory.eINSTANCE.createRule();
						// dom.getCommandStack().execute(new SetCommand(dom, p,
						// ModelPackage.eINSTANCE.getPoint_Rule(), rule));
						// }
						// result.add(p.getRule());
					}
				}
			}
		}
		return result;
	}

	// public static List<Rule> getAllRules(Project project)
	// {
	// List<Rule> result = new ArrayList<Rule>(project.getRoutes().size());
	//
	// for (Route r : project.getRoutes())
	// {
	// if (r.getRule() != null && r.getRule().size() > 0)
	// result.add(r.getRule().get(0));
	// }
	//
	// for (Ambit a : project.getAmbits())
	// {
	// if (a instanceof Junction)
	// {
	// Junction j = (Junction) a;
	// for (Point p : j.getPoints())
	// {
	// if (p.getRule() != null)
	// result.add(p.getRule());
	// }
	// }
	// }
	//
	// return result;
	// }

	// public static List<Rule> getInitialisedRules(final Project project)
	// {
	// List<Rule> result = new ArrayList<Rule>(project.getRoutes().size());
	//
	// // route rules
	// for (Route r : project.getRoutes())
	// {
	// result.add(getInitialisedRule(r));
	// }
	//
	// // point rules
	// for (Ambit a : project.getAmbits())
	// {
	// if (a instanceof Junction)
	// {
	// Junction j = (Junction) a;
	// for (Point p : j.getPoints())
	// {
	// result.add(getInitialisedRule(p));
	// }
	// }
	// }
	//
	// return result;
	// }

	// public static List<Rule> getInitialisedPointRules(final Project project)
	// {
	// List<Rule> result = new ArrayList<Rule>(project.getRoutes().size());
	// // point rules
	// for (Ambit a : project.getAmbits())
	// {
	// if (a instanceof Junction)
	// {
	// Junction j = (Junction) a;
	// for (Point p : j.getPoints())
	// {
	// result.add(getInitialisedRule(p));
	// }
	// }
	// }
	//
	// return result;
	// }

	// public static List<Rule> getInitialisedRules(final Route r)
	// {
	// if (r.getRule() != null && r.getRule().size() > 0)
	// return r.getRule();
	// List<Rule> result = new ArrayList<Rule>();
	// result.add(getInitialisedRule(r));
	// // add more rules here
	// return result;
	// }

	// public static Rule getInitialisedRule(final Route r)
	// {
	// Project project = EmfUtil.getProject(r);
	// TransactionalEditingDomain domain =
	// TransactionUtil.getEditingDomain(project);
	//
	// if (r.getRule() != null && r.getRule().size() > 0)
	// return r.getRule().get(0);
	// else
	// {
	// final Rule x = buildInitialRule(r);
	// if (domain == null)
	// {
	// if (r.getRule().size() > 0)
	// r.getRule().set(0, x);
	// else
	// r.getRule().add(x);
	// } else
	// {
	// try
	// {
	// getSetRuleCommand(domain, r, x).execute(null, null);
	// } catch (ExecutionException e)
	// {
	// e.printStackTrace();
	// }
	// }
	// return x;
	// }
	// }
	//
	// public static Rule getInitialisedRule(final Point p)
	// {
	// Project project = EmfUtil.getProject(p);
	// TransactionalEditingDomain domain =
	// TransactionUtil.getEditingDomain(project);
	//
	// if (p.getRule() != null)
	// return p.getRule();
	// else
	// {
	// final Rule x = buildInitialRule(p);
	// if (domain == null)
	// {
	// p.setRule(x);
	// } else
	// {
	// try
	// {
	// getSetRuleCommand(domain, p, x).execute(null, null);
	// } catch (ExecutionException e)
	// {
	// e.printStackTrace();
	// }
	// }
	// return x;
	// }
	// }

	// private static AbstractTransactionalCommand getSetRuleCommand(final
	// TransactionalEditingDomain domain, final Route route, final Rule rule)
	// {
	// AbstractTransactionalCommand command = new
	// AbstractTransactionalCommand(domain, "SetRuleCommand", null)
	// {
	//
	// @Override
	// protected CommandResult doExecuteWithResult(final IProgressMonitor
	// monitor, final IAdaptable info) throws ExecutionException
	// {
	//
	// if (route.getRule().size() > 0)
	// route.getRule().set(0, rule);
	// else
	// route.getRule().add(rule);
	//
	// return CommandResult.newOKCommandResult();
	// }
	// };
	//
	// return command;
	// }
	//
	// private static AbstractTransactionalCommand getSetRuleCommand(final
	// TransactionalEditingDomain domain, final Point point, final Rule rule)
	// {
	// AbstractTransactionalCommand command = new
	// AbstractTransactionalCommand(domain, "SetRuleCommand", null)
	// {
	//
	// @Override
	// protected CommandResult doExecuteWithResult(final IProgressMonitor
	// monitor, final IAdaptable info) throws ExecutionException
	// {
	//
	// point.setRule(rule);
	//
	// return CommandResult.newOKCommandResult();
	// }
	// };
	//
	// return command;
	// }
}
