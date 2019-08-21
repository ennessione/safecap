/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import safecap.Extensible;
import safecap.Labeled;
import safecap.TransientValues;
import safecap.Visual;
import safecap.schema.HeightMap;
import safecap.schema.HeightPoint;
import safecap.schema.Node;
import safecap.schema.RouteSpeed;
import safecap.schema.Schema;
import safecap.schema.SchemaPackage;
import safecap.schema.Segment;
import safecap.schema.SubSchemaNode;
import safecap.schema.SubSchemaPath;
import safecap.schema.TerminalNode;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see safecap.schema.SchemaPackage
 * @generated
 */
public class SchemaSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static SchemaPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public SchemaSwitch() {
		if (modelPackage == null) {
			modelPackage = SchemaPackage.eINSTANCE;
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
		case SchemaPackage.SCHEMA: {
			final Schema schema = (Schema) theEObject;
			T result = caseSchema(schema);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case SchemaPackage.SEGMENT: {
			final Segment segment = (Segment) theEObject;
			T result = caseSegment(segment);
			if (result == null) {
				result = caseLabeled(segment);
			}
			if (result == null) {
				result = caseVisual(segment);
			}
			if (result == null) {
				result = caseExtensible(segment);
			}
			if (result == null) {
				result = caseTransientValues(segment);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case SchemaPackage.NODE: {
			final Node node = (Node) theEObject;
			T result = caseNode(node);
			if (result == null) {
				result = caseLabeled(node);
			}
			if (result == null) {
				result = caseVisual(node);
			}
			if (result == null) {
				result = caseExtensible(node);
			}
			if (result == null) {
				result = caseTransientValues(node);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case SchemaPackage.TERMINAL_NODE: {
			final TerminalNode terminalNode = (TerminalNode) theEObject;
			T result = caseTerminalNode(terminalNode);
			if (result == null) {
				result = caseNode(terminalNode);
			}
			if (result == null) {
				result = caseLabeled(terminalNode);
			}
			if (result == null) {
				result = caseVisual(terminalNode);
			}
			if (result == null) {
				result = caseExtensible(terminalNode);
			}
			if (result == null) {
				result = caseTransientValues(terminalNode);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case SchemaPackage.HEIGHT_MAP: {
			final HeightMap heightMap = (HeightMap) theEObject;
			T result = caseHeightMap(heightMap);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case SchemaPackage.HEIGHT_POINT: {
			final HeightPoint heightPoint = (HeightPoint) theEObject;
			T result = caseHeightPoint(heightPoint);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case SchemaPackage.ROUTE_SPEED: {
			final RouteSpeed routeSpeed = (RouteSpeed) theEObject;
			T result = caseRouteSpeed(routeSpeed);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case SchemaPackage.SUB_SCHEMA_NODE: {
			final SubSchemaNode subSchemaNode = (SubSchemaNode) theEObject;
			T result = caseSubSchemaNode(subSchemaNode);
			if (result == null) {
				result = caseNode(subSchemaNode);
			}
			if (result == null) {
				result = caseLabeled(subSchemaNode);
			}
			if (result == null) {
				result = caseVisual(subSchemaNode);
			}
			if (result == null) {
				result = caseExtensible(subSchemaNode);
			}
			if (result == null) {
				result = caseTransientValues(subSchemaNode);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case SchemaPackage.SUB_SCHEMA_PATH: {
			final SubSchemaPath subSchemaPath = (SubSchemaPath) theEObject;
			T result = caseSubSchemaPath(subSchemaPath);
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
	 * '<em>Schema</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Schema</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSchema(Schema object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Segment</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Segment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSegment(Segment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Node</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNode(Node object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Terminal
	 * Node</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Terminal
	 *         Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTerminalNode(TerminalNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Height
	 * Map</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Height
	 *         Map</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHeightMap(HeightMap object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Height
	 * Point</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Height
	 *         Point</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHeightPoint(HeightPoint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Route
	 * Speed</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Route
	 *         Speed</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRouteSpeed(RouteSpeed object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sub
	 * Schema Node</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sub
	 *         Schema Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubSchemaNode(SubSchemaNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sub
	 * Schema Path</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sub
	 *         Schema Path</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubSchemaPath(SubSchemaPath object) {
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

} // SchemaSwitch
