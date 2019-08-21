/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import safecap.Extensible;
import safecap.Labeled;
import safecap.TransientValues;
import safecap.Visual;
import safecap.model.Ambit;
import safecap.model.ControlLogic;
import safecap.model.Junction;
import safecap.model.Line;
import safecap.model.Model;
import safecap.model.ModelPackage;
import safecap.model.Point;
import safecap.model.Route;
import safecap.model.RouteStateRule;
import safecap.model.Rule;
import safecap.model.Section;
import safecap.model.TimedOccupationRule;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see safecap.model.ModelPackage
 * @generated
 */
public class ModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static ModelPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public ModelSwitch() {
		if (modelPackage == null) {
			modelPackage = ModelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a
	 * non null result; it yields that result. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case ModelPackage.MODEL: {
			final Model model = (Model) theEObject;
			T result = caseModel(model);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ModelPackage.LINE: {
			final Line line = (Line) theEObject;
			T result = caseLine(line);
			if (result == null) {
				result = caseLabeled(line);
			}
			if (result == null) {
				result = caseExtensible(line);
			}
			if (result == null) {
				result = caseVisual(line);
			}
			if (result == null) {
				result = caseTransientValues(line);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ModelPackage.ROUTE: {
			final Route route = (Route) theEObject;
			T result = caseRoute(route);
			if (result == null) {
				result = caseLabeled(route);
			}
			if (result == null) {
				result = caseExtensible(route);
			}
			if (result == null) {
				result = caseTransientValues(route);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ModelPackage.AMBIT: {
			final Ambit ambit = (Ambit) theEObject;
			T result = caseAmbit(ambit);
			if (result == null) {
				result = caseLabeled(ambit);
			}
			if (result == null) {
				result = caseExtensible(ambit);
			}
			if (result == null) {
				result = caseTransientValues(ambit);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ModelPackage.SECTION: {
			final Section section = (Section) theEObject;
			T result = caseSection(section);
			if (result == null) {
				result = caseAmbit(section);
			}
			if (result == null) {
				result = caseLabeled(section);
			}
			if (result == null) {
				result = caseExtensible(section);
			}
			if (result == null) {
				result = caseTransientValues(section);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ModelPackage.JUNCTION: {
			final Junction junction = (Junction) theEObject;
			T result = caseJunction(junction);
			if (result == null) {
				result = caseAmbit(junction);
			}
			if (result == null) {
				result = caseLabeled(junction);
			}
			if (result == null) {
				result = caseExtensible(junction);
			}
			if (result == null) {
				result = caseTransientValues(junction);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ModelPackage.POINT: {
			final Point point = (Point) theEObject;
			T result = casePoint(point);
			if (result == null) {
				result = caseExtensible(point);
			}
			if (result == null) {
				result = caseTransientValues(point);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ModelPackage.RULE: {
			final Rule rule = (Rule) theEObject;
			T result = caseRule(rule);
			if (result == null) {
				result = caseExtensible(rule);
			}
			if (result == null) {
				result = caseTransientValues(rule);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ModelPackage.TIMED_OCCUPATION_RULE: {
			final TimedOccupationRule timedOccupationRule = (TimedOccupationRule) theEObject;
			T result = caseTimedOccupationRule(timedOccupationRule);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ModelPackage.ROUTE_STATE_RULE: {
			final RouteStateRule routeStateRule = (RouteStateRule) theEObject;
			T result = caseRouteStateRule(routeStateRule);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ModelPackage.CONTROL_LOGIC: {
			final ControlLogic controlLogic = (ControlLogic) theEObject;
			T result = caseControlLogic(controlLogic);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Model</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModel(Model object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Line</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Line</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLine(Line object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Route</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Route</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRoute(Route object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Ambit</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Ambit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAmbit(Ambit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Section</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Section</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSection(Section object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Junction</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Junction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJunction(Junction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Point</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Point</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePoint(Point object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Rule</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRule(Rule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Timed
	 * Occupation Rule</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Timed
	 *         Occupation Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTimedOccupationRule(TimedOccupationRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Route
	 * State Rule</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Route
	 *         State Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRouteStateRule(RouteStateRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Control
	 * Logic</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Control
	 *         Logic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseControlLogic(ControlLogic object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Labeled</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Labeled</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLabeled(Labeled object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Transient Values</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Transient Values</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransientValues(TransientValues object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Extensible</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Extensible</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtensible(Extensible object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Visual</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Visual</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVisual(Visual object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>EObject</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last
	 * case anyway. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // ModelSwitch
