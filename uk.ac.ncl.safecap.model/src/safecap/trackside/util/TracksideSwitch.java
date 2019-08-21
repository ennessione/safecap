/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import safecap.Extensible;
import safecap.Labeled;
import safecap.Provenance;
import safecap.TransientValues;
import safecap.Visual;
import safecap.trackside.Equipment;
import safecap.trackside.GenericLocatedEquipment;
import safecap.trackside.LeftSide;
import safecap.trackside.LeftSignal;
import safecap.trackside.LeftSpeedLimit;
import safecap.trackside.LeftStopAndGo;
import safecap.trackside.NodeWire;
import safecap.trackside.RedLeftSignal;
import safecap.trackside.RedRightSignal;
import safecap.trackside.RightSide;
import safecap.trackside.RightSignal;
import safecap.trackside.RightSpeedLimit;
import safecap.trackside.RightStopAndGo;
import safecap.trackside.Signal;
import safecap.trackside.SpeedLimit;
import safecap.trackside.StopAndGo;
import safecap.trackside.SubEquipment;
import safecap.trackside.SubWire;
import safecap.trackside.Trackside;
import safecap.trackside.TracksidePackage;
import safecap.trackside.Wire;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see safecap.trackside.TracksidePackage
 * @generated
 */
public class TracksideSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static TracksidePackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public TracksideSwitch() {
		if (modelPackage == null) {
			modelPackage = TracksidePackage.eINSTANCE;
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
		case TracksidePackage.TRACKSIDE: {
			final Trackside trackside = (Trackside) theEObject;
			T result = caseTrackside(trackside);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.EQUIPMENT: {
			final Equipment equipment = (Equipment) theEObject;
			T result = caseEquipment(equipment);
			if (result == null) {
				result = caseLabeled(equipment);
			}
			if (result == null) {
				result = caseVisual(equipment);
			}
			if (result == null) {
				result = caseProvenance(equipment);
			}
			if (result == null) {
				result = caseExtensible(equipment);
			}
			if (result == null) {
				result = caseTransientValues(equipment);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.SIGNAL: {
			final Signal signal = (Signal) theEObject;
			T result = caseSignal(signal);
			if (result == null) {
				result = caseEquipment(signal);
			}
			if (result == null) {
				result = caseLabeled(signal);
			}
			if (result == null) {
				result = caseVisual(signal);
			}
			if (result == null) {
				result = caseProvenance(signal);
			}
			if (result == null) {
				result = caseExtensible(signal);
			}
			if (result == null) {
				result = caseTransientValues(signal);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.SPEED_LIMIT: {
			final SpeedLimit speedLimit = (SpeedLimit) theEObject;
			T result = caseSpeedLimit(speedLimit);
			if (result == null) {
				result = caseEquipment(speedLimit);
			}
			if (result == null) {
				result = caseLabeled(speedLimit);
			}
			if (result == null) {
				result = caseVisual(speedLimit);
			}
			if (result == null) {
				result = caseProvenance(speedLimit);
			}
			if (result == null) {
				result = caseExtensible(speedLimit);
			}
			if (result == null) {
				result = caseTransientValues(speedLimit);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.LEFT_SIGNAL: {
			final LeftSignal leftSignal = (LeftSignal) theEObject;
			T result = caseLeftSignal(leftSignal);
			if (result == null) {
				result = caseSignal(leftSignal);
			}
			if (result == null) {
				result = caseLeftSide(leftSignal);
			}
			if (result == null) {
				result = caseEquipment(leftSignal);
			}
			if (result == null) {
				result = caseLabeled(leftSignal);
			}
			if (result == null) {
				result = caseVisual(leftSignal);
			}
			if (result == null) {
				result = caseProvenance(leftSignal);
			}
			if (result == null) {
				result = caseExtensible(leftSignal);
			}
			if (result == null) {
				result = caseTransientValues(leftSignal);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.RIGHT_SIGNAL: {
			final RightSignal rightSignal = (RightSignal) theEObject;
			T result = caseRightSignal(rightSignal);
			if (result == null) {
				result = caseSignal(rightSignal);
			}
			if (result == null) {
				result = caseRightSide(rightSignal);
			}
			if (result == null) {
				result = caseEquipment(rightSignal);
			}
			if (result == null) {
				result = caseLabeled(rightSignal);
			}
			if (result == null) {
				result = caseVisual(rightSignal);
			}
			if (result == null) {
				result = caseProvenance(rightSignal);
			}
			if (result == null) {
				result = caseExtensible(rightSignal);
			}
			if (result == null) {
				result = caseTransientValues(rightSignal);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.LEFT_SPEED_LIMIT: {
			final LeftSpeedLimit leftSpeedLimit = (LeftSpeedLimit) theEObject;
			T result = caseLeftSpeedLimit(leftSpeedLimit);
			if (result == null) {
				result = caseSpeedLimit(leftSpeedLimit);
			}
			if (result == null) {
				result = caseLeftSide(leftSpeedLimit);
			}
			if (result == null) {
				result = caseEquipment(leftSpeedLimit);
			}
			if (result == null) {
				result = caseLabeled(leftSpeedLimit);
			}
			if (result == null) {
				result = caseVisual(leftSpeedLimit);
			}
			if (result == null) {
				result = caseProvenance(leftSpeedLimit);
			}
			if (result == null) {
				result = caseExtensible(leftSpeedLimit);
			}
			if (result == null) {
				result = caseTransientValues(leftSpeedLimit);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.RIGHT_SPEED_LIMIT: {
			final RightSpeedLimit rightSpeedLimit = (RightSpeedLimit) theEObject;
			T result = caseRightSpeedLimit(rightSpeedLimit);
			if (result == null) {
				result = caseSpeedLimit(rightSpeedLimit);
			}
			if (result == null) {
				result = caseRightSide(rightSpeedLimit);
			}
			if (result == null) {
				result = caseEquipment(rightSpeedLimit);
			}
			if (result == null) {
				result = caseLabeled(rightSpeedLimit);
			}
			if (result == null) {
				result = caseVisual(rightSpeedLimit);
			}
			if (result == null) {
				result = caseProvenance(rightSpeedLimit);
			}
			if (result == null) {
				result = caseExtensible(rightSpeedLimit);
			}
			if (result == null) {
				result = caseTransientValues(rightSpeedLimit);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.WIRE: {
			final Wire wire = (Wire) theEObject;
			T result = caseWire(wire);
			if (result == null) {
				result = caseExtensible(wire);
			}
			if (result == null) {
				result = caseVisual(wire);
			}
			if (result == null) {
				result = caseTransientValues(wire);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.RED_LEFT_SIGNAL: {
			final RedLeftSignal redLeftSignal = (RedLeftSignal) theEObject;
			T result = caseRedLeftSignal(redLeftSignal);
			if (result == null) {
				result = caseSignal(redLeftSignal);
			}
			if (result == null) {
				result = caseLeftSide(redLeftSignal);
			}
			if (result == null) {
				result = caseEquipment(redLeftSignal);
			}
			if (result == null) {
				result = caseLabeled(redLeftSignal);
			}
			if (result == null) {
				result = caseVisual(redLeftSignal);
			}
			if (result == null) {
				result = caseProvenance(redLeftSignal);
			}
			if (result == null) {
				result = caseExtensible(redLeftSignal);
			}
			if (result == null) {
				result = caseTransientValues(redLeftSignal);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.RED_RIGHT_SIGNAL: {
			final RedRightSignal redRightSignal = (RedRightSignal) theEObject;
			T result = caseRedRightSignal(redRightSignal);
			if (result == null) {
				result = caseSignal(redRightSignal);
			}
			if (result == null) {
				result = caseRightSide(redRightSignal);
			}
			if (result == null) {
				result = caseEquipment(redRightSignal);
			}
			if (result == null) {
				result = caseLabeled(redRightSignal);
			}
			if (result == null) {
				result = caseVisual(redRightSignal);
			}
			if (result == null) {
				result = caseProvenance(redRightSignal);
			}
			if (result == null) {
				result = caseExtensible(redRightSignal);
			}
			if (result == null) {
				result = caseTransientValues(redRightSignal);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.LEFT_SIDE: {
			final LeftSide leftSide = (LeftSide) theEObject;
			T result = caseLeftSide(leftSide);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.RIGHT_SIDE: {
			final RightSide rightSide = (RightSide) theEObject;
			T result = caseRightSide(rightSide);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.STOP_AND_GO: {
			final StopAndGo stopAndGo = (StopAndGo) theEObject;
			T result = caseStopAndGo(stopAndGo);
			if (result == null) {
				result = caseEquipment(stopAndGo);
			}
			if (result == null) {
				result = caseLabeled(stopAndGo);
			}
			if (result == null) {
				result = caseVisual(stopAndGo);
			}
			if (result == null) {
				result = caseProvenance(stopAndGo);
			}
			if (result == null) {
				result = caseExtensible(stopAndGo);
			}
			if (result == null) {
				result = caseTransientValues(stopAndGo);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.LEFT_STOP_AND_GO: {
			final LeftStopAndGo leftStopAndGo = (LeftStopAndGo) theEObject;
			T result = caseLeftStopAndGo(leftStopAndGo);
			if (result == null) {
				result = caseStopAndGo(leftStopAndGo);
			}
			if (result == null) {
				result = caseLeftSide(leftStopAndGo);
			}
			if (result == null) {
				result = caseEquipment(leftStopAndGo);
			}
			if (result == null) {
				result = caseLabeled(leftStopAndGo);
			}
			if (result == null) {
				result = caseVisual(leftStopAndGo);
			}
			if (result == null) {
				result = caseProvenance(leftStopAndGo);
			}
			if (result == null) {
				result = caseExtensible(leftStopAndGo);
			}
			if (result == null) {
				result = caseTransientValues(leftStopAndGo);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.RIGHT_STOP_AND_GO: {
			final RightStopAndGo rightStopAndGo = (RightStopAndGo) theEObject;
			T result = caseRightStopAndGo(rightStopAndGo);
			if (result == null) {
				result = caseStopAndGo(rightStopAndGo);
			}
			if (result == null) {
				result = caseRightSide(rightStopAndGo);
			}
			if (result == null) {
				result = caseEquipment(rightStopAndGo);
			}
			if (result == null) {
				result = caseLabeled(rightStopAndGo);
			}
			if (result == null) {
				result = caseVisual(rightStopAndGo);
			}
			if (result == null) {
				result = caseProvenance(rightStopAndGo);
			}
			if (result == null) {
				result = caseExtensible(rightStopAndGo);
			}
			if (result == null) {
				result = caseTransientValues(rightStopAndGo);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.SUB_EQUIPMENT: {
			final SubEquipment subEquipment = (SubEquipment) theEObject;
			T result = caseSubEquipment(subEquipment);
			if (result == null) {
				result = caseEquipment(subEquipment);
			}
			if (result == null) {
				result = caseLabeled(subEquipment);
			}
			if (result == null) {
				result = caseVisual(subEquipment);
			}
			if (result == null) {
				result = caseProvenance(subEquipment);
			}
			if (result == null) {
				result = caseExtensible(subEquipment);
			}
			if (result == null) {
				result = caseTransientValues(subEquipment);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.SUB_WIRE: {
			final SubWire subWire = (SubWire) theEObject;
			T result = caseSubWire(subWire);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.NODE_WIRE: {
			final NodeWire nodeWire = (NodeWire) theEObject;
			T result = caseNodeWire(nodeWire);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT: {
			final GenericLocatedEquipment genericLocatedEquipment = (GenericLocatedEquipment) theEObject;
			T result = caseGenericLocatedEquipment(genericLocatedEquipment);
			if (result == null) {
				result = caseEquipment(genericLocatedEquipment);
			}
			if (result == null) {
				result = caseLabeled(genericLocatedEquipment);
			}
			if (result == null) {
				result = caseVisual(genericLocatedEquipment);
			}
			if (result == null) {
				result = caseProvenance(genericLocatedEquipment);
			}
			if (result == null) {
				result = caseExtensible(genericLocatedEquipment);
			}
			if (result == null) {
				result = caseTransientValues(genericLocatedEquipment);
			}
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
	 * '<em>Trackside</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Trackside</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrackside(Trackside object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Equipment</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Equipment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEquipment(Equipment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Signal</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Signal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSignal(Signal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Speed
	 * Limit</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Speed
	 *         Limit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSpeedLimit(SpeedLimit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Left
	 * Signal</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Left
	 *         Signal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLeftSignal(LeftSignal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Right
	 * Signal</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Right
	 *         Signal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRightSignal(RightSignal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Left
	 * Speed Limit</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Left
	 *         Speed Limit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLeftSpeedLimit(LeftSpeedLimit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Right
	 * Speed Limit</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Right
	 *         Speed Limit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRightSpeedLimit(RightSpeedLimit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Wire</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Wire</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWire(Wire object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Red Left
	 * Signal</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Red Left
	 *         Signal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRedLeftSignal(RedLeftSignal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Red
	 * Right Signal</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Red
	 *         Right Signal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRedRightSignal(RedRightSignal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Left
	 * Side</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Left
	 *         Side</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLeftSide(LeftSide object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Right
	 * Side</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Right
	 *         Side</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRightSide(RightSide object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stop And
	 * Go</em>'. <!-- begin-user-doc --> This implementation returns null; returning
	 * a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stop And
	 *         Go</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStopAndGo(StopAndGo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Left
	 * Stop And Go</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Left
	 *         Stop And Go</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLeftStopAndGo(LeftStopAndGo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Right
	 * Stop And Go</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Right
	 *         Stop And Go</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRightStopAndGo(RightStopAndGo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sub
	 * Equipment</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sub
	 *         Equipment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubEquipment(SubEquipment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sub
	 * Wire</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sub
	 *         Wire</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubWire(SubWire object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node
	 * Wire</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node
	 *         Wire</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNodeWire(NodeWire object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic
	 * Located Equipment</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic
	 *         Located Equipment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericLocatedEquipment(GenericLocatedEquipment object) {
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
	 * '<em>Provenance</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Provenance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProvenance(Provenance object) {
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

} // TracksideSwitch
