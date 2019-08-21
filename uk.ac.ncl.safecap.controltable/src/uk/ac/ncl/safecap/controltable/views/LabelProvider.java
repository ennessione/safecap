package uk.ac.ncl.safecap.controltable.views;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import safecap.model.ControlLogic;
import safecap.model.Line;
import safecap.model.Point;
import safecap.model.Route;
import safecap.model.RouteStateRule;
import safecap.model.Rule;
import safecap.model.TimedOccupationRule;
import uk.ac.ncl.safecap.common.resources.ImageRegister;
import uk.ac.ncl.safecap.misc.core.RouteUtil;
import uk.ac.ncl.safecap.misc.core.RuleUtil;

public class LabelProvider implements ITableLabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0) {
			if (element instanceof Route) {
				return ImageRegister.getImage(ImageRegister.ICON_ROUTE);
			} else if (element instanceof RuleWrap) {
				final Rule rule = ((RuleWrap) element).rule;
				if (rule.eContainer() != null && rule.eContainer() instanceof Point) {
					return ImageRegister.getImage(ImageRegister.ICON_POINT);
				}
			}
		} else if (columnIndex == 1) {
			if (element instanceof Route) {
				return VerificationProvider.getVerificationStatusImage((Route) element);
			} else if (element instanceof RuleWrap) {
				final Rule rule = ((RuleWrap) element).rule;
				if (rule.eContainer() != null && rule.eContainer() instanceof Point) {
					return VerificationProvider.getVerificationStatusImage(rule.eContainer());
				}
			}
		}

//		if (element instanceof Rule) {
//			switch (columnIndex) {
//			case 0:
//				EObject container = ((Rule) element).eContainer();
//				if (container != null) {
////					if (container instanceof Route)
////						return ImageRegister.getImage(ImageRegister.ICON_ROUTE);
//					if (container instanceof Point)
//						return ImageRegister.getImage(ImageRegister.ICON_POINT);
//				}
//				return null;
//			case 1:
//				EObject container1 = ((Rule) element).eContainer();
//
////				return ImageRegister.getImage(ImageRegister.ICON_SECTION);
////			case 2:
////				return ImageRegister.getImage(ImageRegister.ICON_POINT);
////			case 3:
////				return ImageRegister.getImage(ImageRegister.ICON_POINT);
////			case 4:
////				return ImageRegister.getImage(ImageRegister.ICON_SECTION);
//			}
//		}
//		else if (element instanceof Route && columnIndex == 0)
//		    return ImageRegister.getImage(ImageRegister.ICON_ROUTE);
//		else if (element instanceof Route && columnIndex == 1)
//		{
//			return VerificationProvider.getVerificationStatusImage((Route) element);
//		}
//		else if (element instanceof Rule && ((Rule)element).eContainer() instanceof Point && columnIndex == 1)
//		{
//			return VerificationProvider.getVerificationStatusImage(((Rule)element).eContainer());
//		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof RuleWrap) {
			final Rule rule = ((RuleWrap) element).rule;
			switch (columnIndex) {
			case 1:
				return null;
			case 0:
				final EObject container = rule.eContainer();
				if (container != null) {
					if (container instanceof ControlLogic) {
						final ControlLogic logic = (ControlLogic) container;
						final int index = logic.getRule().indexOf(rule);
						return RouteUtil.getAspectLabel(logic, index + 1);
					} else if (container instanceof Point) {
						return ((Point) rule.eContainer()).getNode().getLabel();
					}
				}
				return null;
			case 2:
				return listToString(rule.getClear());
			case 3:
				return listToString(rule.getNormal());
			case 4:
				return listToString(rule.getReverse());
			case 5:
				return occlistToString(rule.getOccupied());
			case 6:
				return routeListToString(rule);
//			case 6:
//				return listToString(rule.getStop());
//			case 7:
//				return listToString(rule.getProceed());
			}
		} else if (element instanceof Route && columnIndex == 0) {
			return ((Route) element).getLabel();
		} else if (element instanceof RuleContainer && columnIndex == 0) {
			final RuleContainer container = (RuleContainer) element;
			if (RuleUtil.getControlLogic(container.route, container.line) == RuleUtil.getDefaultLogic(container.route)) {
				return ((RuleContainer) element).line.getLabel();
			} else {
				return "*" + ((RuleContainer) element).line.getLabel();
			}
		}

		return "";
	}

	public static String routeListToString(Rule rule) {

		final Line line = RuleUtil.getLineByRule(rule);
//		EObject container = rule.eContainer();
//		if (container instanceof ControlLogic)
//		{
//			ControlLogic logic = (ControlLogic) container;
//		    aspects = logic.getAspects();
//		}
//		else if (container instanceof Point)
//			aspects = 2;
//		else
//			return "";

		final EList<RouteStateRule> list = rule.getRouteState();

		final StringBuffer sb = new StringBuffer();

		for (int i = 0; i < list.size(); i++) {
			final RouteStateRule state = list.get(i);
			if (i > 0) {
				sb.append(", ");
			}

			sb.append(state.getRoute().getLabel());

			if (state.getState() >= 0) {
				sb.append(":");
				final int aspects = RuleUtil.getControlLogic(state.getRoute(), line).getAspects();
				sb.append(RouteUtil.getAspectLabel(aspects, state.getState()));
			}
		}

		return sb.toString();
	}

	public static String listToString(List<?> list) {
		final StringBuffer sb = new StringBuffer();

		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(list.get(i).toString());
		}

		return sb.toString();
	}

	public static String occlistToString(List<TimedOccupationRule> list) {
		final StringBuffer sb = new StringBuffer();

		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			final TimedOccupationRule to = list.get(i);

			if (to.getTime() <= 0) {
				sb.append(to.getAmbit().getLabel());
			} else {
				final double rt = Math.floor(to.getTime());
				if (rt == to.getTime()) {
					sb.append(to.getAmbit().getLabel() + ":" + (int) to.getTime());
				} else {
					sb.append(to.getAmbit().getLabel() + ":" + to.getTime());
				}
			}
		}

		return sb.toString();
	}
}
