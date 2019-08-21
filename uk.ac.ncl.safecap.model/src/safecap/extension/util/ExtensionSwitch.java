/**
 */
package safecap.extension.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import safecap.Extensible;
import safecap.Labeled;
import safecap.Provenance;
import safecap.TransientValues;
import safecap.Visual;
import safecap.extension.CustomColour;
import safecap.extension.CustomFigure;
import safecap.extension.CustomLabel;
import safecap.extension.CustomShape;
import safecap.extension.ExtAttribute;
import safecap.extension.ExtAttributeB;
import safecap.extension.ExtAttributeDbl;
import safecap.extension.ExtAttributeInt;
import safecap.extension.ExtAttributeStr;
import safecap.extension.ExtEquipment;
import safecap.extension.ExtVisual;
import safecap.extension.ExtensionPackage;
import safecap.extension.Point;
import safecap.trackside.Equipment;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see safecap.extension.ExtensionPackage
 * @generated
 */
public class ExtensionSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static ExtensionPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public ExtensionSwitch() {
		if (modelPackage == null) {
			modelPackage = ExtensionPackage.eINSTANCE;
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
		case ExtensionPackage.EXT_ATTRIBUTE: {
			final ExtAttribute extAttribute = (ExtAttribute) theEObject;
			T result = caseExtAttribute(extAttribute);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtensionPackage.EXT_ATTRIBUTE_INT: {
			final ExtAttributeInt extAttributeInt = (ExtAttributeInt) theEObject;
			T result = caseExtAttributeInt(extAttributeInt);
			if (result == null) {
				result = caseExtAttribute(extAttributeInt);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtensionPackage.EXT_ATTRIBUTE_DBL: {
			final ExtAttributeDbl extAttributeDbl = (ExtAttributeDbl) theEObject;
			T result = caseExtAttributeDbl(extAttributeDbl);
			if (result == null) {
				result = caseExtAttribute(extAttributeDbl);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtensionPackage.EXT_ATTRIBUTE_STR: {
			final ExtAttributeStr extAttributeStr = (ExtAttributeStr) theEObject;
			T result = caseExtAttributeStr(extAttributeStr);
			if (result == null) {
				result = caseExtAttribute(extAttributeStr);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtensionPackage.EXT_ATTRIBUTE_B: {
			final ExtAttributeB extAttributeB = (ExtAttributeB) theEObject;
			T result = caseExtAttributeB(extAttributeB);
			if (result == null) {
				result = caseExtAttribute(extAttributeB);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtensionPackage.CUSTOM_FIGURE: {
			final CustomFigure customFigure = (CustomFigure) theEObject;
			T result = caseCustomFigure(customFigure);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtensionPackage.CUSTOM_LABEL: {
			final CustomLabel customLabel = (CustomLabel) theEObject;
			T result = caseCustomLabel(customLabel);
			if (result == null) {
				result = caseCustomFigure(customLabel);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtensionPackage.POINT: {
			final Point point = (Point) theEObject;
			T result = casePoint(point);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtensionPackage.CUSTOM_COLOUR: {
			final CustomColour customColour = (CustomColour) theEObject;
			T result = caseCustomColour(customColour);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtensionPackage.CUSTOM_SHAPE: {
			final CustomShape customShape = (CustomShape) theEObject;
			T result = caseCustomShape(customShape);
			if (result == null) {
				result = caseCustomFigure(customShape);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtensionPackage.EXT_VISUAL: {
			final ExtVisual extVisual = (ExtVisual) theEObject;
			T result = caseExtVisual(extVisual);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ExtensionPackage.EXT_EQUIPMENT: {
			final ExtEquipment extEquipment = (ExtEquipment) theEObject;
			T result = caseExtEquipment(extEquipment);
			if (result == null) {
				result = caseEquipment(extEquipment);
			}
			if (result == null) {
				result = caseLabeled(extEquipment);
			}
			if (result == null) {
				result = caseVisual(extEquipment);
			}
			if (result == null) {
				result = caseProvenance(extEquipment);
			}
			if (result == null) {
				result = caseExtensible(extEquipment);
			}
			if (result == null) {
				result = caseTransientValues(extEquipment);
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
	 * Returns the result of interpreting the object as an instance of '<em>Ext
	 * Attribute</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ext
	 *         Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtAttribute(ExtAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ext
	 * Attribute Int</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ext
	 *         Attribute Int</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtAttributeInt(ExtAttributeInt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ext
	 * Attribute Dbl</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ext
	 *         Attribute Dbl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtAttributeDbl(ExtAttributeDbl object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ext
	 * Attribute Str</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ext
	 *         Attribute Str</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtAttributeStr(ExtAttributeStr object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ext
	 * Attribute B</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ext
	 *         Attribute B</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtAttributeB(ExtAttributeB object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Custom
	 * Figure</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Custom
	 *         Figure</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCustomFigure(CustomFigure object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Custom
	 * Label</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Custom
	 *         Label</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCustomLabel(CustomLabel object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Custom
	 * Colour</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Custom
	 *         Colour</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCustomColour(CustomColour object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Custom
	 * Shape</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Custom
	 *         Shape</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCustomShape(CustomShape object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ext
	 * Visual</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ext
	 *         Visual</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtVisual(ExtVisual object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ext
	 * Equipment</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ext
	 *         Equipment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtEquipment(ExtEquipment object) {
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

} // ExtensionSwitch
