package uk.ac.ncl.safecap.misc.core;

import org.eclipse.emf.common.util.EList;

import safecap.model.ControlLogic;
import safecap.model.Line;
import safecap.model.Point;
import safecap.model.Route;
import safecap.model.Rule;

public class RouteWrapper {
	private final Route _route;
	private final Line _line;
	private final ControlLogic _logic;

	public RouteWrapper(Route route, Line line) {
		_route = route;
		_line = line;
		_logic = RuleUtil.getControlLogic(route, line);
	}

	public Line getLine() {
		return _line;
	}

	public Route getRoute() {
		return _route;
	}

	public ControlLogic getLogic() {
		return _logic;
	}

	public int getAspects() {
		if (_logic != null) {
			return _logic.getAspects();
		} else {
			return 0;
		}
	}

	public EList<Point> getNormal() {
		if (_logic != null) {
			return _logic.getNormal();
		} else {
			return null;
		}
	}

	public EList<Point> getReverse() {
		if (_logic != null) {
			return _logic.getReverse();
		} else {
			return null;
		}
	}

	public EList<Rule> getRule() {
		if (_logic != null) {
			return _logic.getRule();
		} else {
			return null;
		}
	}

	// @Override
	// public String getLabel()
	// {
	// return _route.getLabel();
	// }
	//
	// @Override
	// public void setLabel(String value)
	// {
	// _route.setLabel(value);
	// }
	//
	// @Override
	// public EClass eClass()
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public Resource eResource()
	// {
	// return _route.eResource();
	// }
	//
	// @Override
	// public EObject eContainer()
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public EStructuralFeature eContainingFeature()
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public EReference eContainmentFeature()
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public EList<EObject> eContents()
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public TreeIterator<EObject> eAllContents()
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public boolean eIsProxy()
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public EList<EObject> eCrossReferences()
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public Object eGet(EStructuralFeature feature)
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public Object eGet(EStructuralFeature feature, boolean resolve)
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public void eSet(EStructuralFeature feature, Object newValue)
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public boolean eIsSet(EStructuralFeature feature)
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public void eUnset(EStructuralFeature feature)
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public Object eInvoke(EOperation operation, EList<?> arguments) throws
	// InvocationTargetException
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public EList<Adapter> eAdapters()
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public boolean eDeliver()
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public void eSetDeliver(boolean deliver)
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public void eNotify(Notification notification)
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public EList<ExtAttribute> getAttributes()
	// {
	// throw new RuntimeException();
	// }
	//
	// @Override
	// public EList<Ambit> getAmbits()
	// {
	// return _route.getAmbits();
	// }
	//
	// @Override
	// public EList<Segment> getSegments()
	// {
	// return _route.getSegments();
	// }
	//
	// @Override
	// public Orientation getOrientation()
	// {
	// return _route.getOrientation();
	// }
	//
	// @Override
	// public void setOrientation(Orientation value)
	// {
	// _route.setOrientation(value);
	// }
	//
	// @Override
	// public EList<ControlLogic> getLogic()
	// {
	// return _route.getLogic();
	// }
	//
	// @Override
	// public ControlLogic getDefaultlogic()
	// {
	// return _route.getDefaultlogic();
	// }
	//
	// @Override
	// public void setDefaultlogic(ControlLogic value)
	// {
	// _route.setDefaultlogic(value);
	// }
}
