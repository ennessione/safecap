/**
 */
package safecap.commentary.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import safecap.Labeled;
import safecap.commentary.Bridge;
import safecap.commentary.Commentary;
import safecap.commentary.CommentaryPackage;
import safecap.commentary.Comments;
import safecap.commentary.OrientableCommentary;
import safecap.commentary.Road;
import safecap.commentary.Station;
import safecap.commentary.Tunnel;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see safecap.commentary.CommentaryPackage
 * @generated
 */
public class CommentarySwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static CommentaryPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public CommentarySwitch() {
		if (modelPackage == null) {
			modelPackage = CommentaryPackage.eINSTANCE;
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
		case CommentaryPackage.COMMENTS: {
			final Comments comments = (Comments) theEObject;
			T result = caseComments(comments);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case CommentaryPackage.COMMENTARY: {
			final Commentary commentary = (Commentary) theEObject;
			T result = caseCommentary(commentary);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case CommentaryPackage.ORIENTABLE_COMMENTARY: {
			final OrientableCommentary orientableCommentary = (OrientableCommentary) theEObject;
			T result = caseOrientableCommentary(orientableCommentary);
			if (result == null) {
				result = caseCommentary(orientableCommentary);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case CommentaryPackage.STATION: {
			final Station station = (Station) theEObject;
			T result = caseStation(station);
			if (result == null) {
				result = caseLabeled(station);
			}
			if (result == null) {
				result = caseOrientableCommentary(station);
			}
			if (result == null) {
				result = caseCommentary(station);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case CommentaryPackage.BRIDGE: {
			final Bridge bridge = (Bridge) theEObject;
			T result = caseBridge(bridge);
			if (result == null) {
				result = caseOrientableCommentary(bridge);
			}
			if (result == null) {
				result = caseLabeled(bridge);
			}
			if (result == null) {
				result = caseCommentary(bridge);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case CommentaryPackage.ROAD: {
			final Road road = (Road) theEObject;
			T result = caseRoad(road);
			if (result == null) {
				result = caseOrientableCommentary(road);
			}
			if (result == null) {
				result = caseLabeled(road);
			}
			if (result == null) {
				result = caseCommentary(road);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case CommentaryPackage.TUNNEL: {
			final Tunnel tunnel = (Tunnel) theEObject;
			T result = caseTunnel(tunnel);
			if (result == null) {
				result = caseOrientableCommentary(tunnel);
			}
			if (result == null) {
				result = caseLabeled(tunnel);
			}
			if (result == null) {
				result = caseCommentary(tunnel);
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
	 * '<em>Commentary</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Commentary</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommentary(Commentary object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Orientable Commentary</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Orientable Commentary</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOrientableCommentary(OrientableCommentary object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Station</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Station</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStation(Station object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Bridge</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Bridge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBridge(Bridge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Road</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Road</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRoad(Road object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Tunnel</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Tunnel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTunnel(Tunnel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Comments</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Comments</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComments(Comments object) {
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

} // CommentarySwitch
