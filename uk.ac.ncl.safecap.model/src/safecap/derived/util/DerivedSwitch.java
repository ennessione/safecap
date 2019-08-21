/**
 */
package safecap.derived.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import safecap.Extensible;
import safecap.Labeled;
import safecap.TransientValues;
import safecap.derived.Derived;
import safecap.derived.DerivedElement;
import safecap.derived.DerivedPackage;
import safecap.derived.MergedAmbit;
import safecap.derived.MergedJunction;
import safecap.derived.MergedPoint;
import safecap.derived.MergedSection;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.model.Point;
import safecap.model.Section;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see safecap.derived.DerivedPackage
 * @generated
 */
public class DerivedSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static DerivedPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public DerivedSwitch() {
		if (modelPackage == null) {
			modelPackage = DerivedPackage.eINSTANCE;
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
		case DerivedPackage.DERIVED: {
			final Derived derived = (Derived) theEObject;
			T result = caseDerived(derived);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case DerivedPackage.DERIVED_ELEMENT: {
			final DerivedElement derivedElement = (DerivedElement) theEObject;
			T result = caseDerivedElement(derivedElement);
			if (result == null) {
				result = caseLabeled(derivedElement);
			}
			if (result == null) {
				result = caseExtensible(derivedElement);
			}
			if (result == null) {
				result = caseTransientValues(derivedElement);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case DerivedPackage.MERGED_POINT: {
			final MergedPoint mergedPoint = (MergedPoint) theEObject;
			T result = caseMergedPoint(mergedPoint);
			if (result == null) {
				result = caseDerivedElement(mergedPoint);
			}
			if (result == null) {
				result = casePoint(mergedPoint);
			}
			if (result == null) {
				result = caseLabeled(mergedPoint);
			}
			if (result == null) {
				result = caseExtensible(mergedPoint);
			}
			if (result == null) {
				result = caseTransientValues(mergedPoint);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case DerivedPackage.MERGED_AMBIT: {
			final MergedAmbit mergedAmbit = (MergedAmbit) theEObject;
			T result = caseMergedAmbit(mergedAmbit);
			if (result == null) {
				result = caseDerivedElement(mergedAmbit);
			}
			if (result == null) {
				result = caseAmbit(mergedAmbit);
			}
			if (result == null) {
				result = caseLabeled(mergedAmbit);
			}
			if (result == null) {
				result = caseExtensible(mergedAmbit);
			}
			if (result == null) {
				result = caseTransientValues(mergedAmbit);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case DerivedPackage.MERGED_JUNCTION: {
			final MergedJunction mergedJunction = (MergedJunction) theEObject;
			T result = caseMergedJunction(mergedJunction);
			if (result == null) {
				result = caseMergedAmbit(mergedJunction);
			}
			if (result == null) {
				result = caseJunction(mergedJunction);
			}
			if (result == null) {
				result = caseDerivedElement(mergedJunction);
			}
			if (result == null) {
				result = caseAmbit(mergedJunction);
			}
			if (result == null) {
				result = caseLabeled(mergedJunction);
			}
			if (result == null) {
				result = caseExtensible(mergedJunction);
			}
			if (result == null) {
				result = caseTransientValues(mergedJunction);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case DerivedPackage.MERGED_SECTION: {
			final MergedSection mergedSection = (MergedSection) theEObject;
			T result = caseMergedSection(mergedSection);
			if (result == null) {
				result = caseMergedAmbit(mergedSection);
			}
			if (result == null) {
				result = caseSection(mergedSection);
			}
			if (result == null) {
				result = caseDerivedElement(mergedSection);
			}
			if (result == null) {
				result = caseAmbit(mergedSection);
			}
			if (result == null) {
				result = caseLabeled(mergedSection);
			}
			if (result == null) {
				result = caseExtensible(mergedSection);
			}
			if (result == null) {
				result = caseTransientValues(mergedSection);
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
	 * '<em>Element</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDerivedElement(DerivedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Merged
	 * Point</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merged
	 *         Point</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergedPoint(MergedPoint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Merged
	 * Ambit</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merged
	 *         Ambit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergedAmbit(MergedAmbit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Merged
	 * Junction</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merged
	 *         Junction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergedJunction(MergedJunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Merged
	 * Section</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merged
	 *         Section</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergedSection(MergedSection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Derived</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Derived</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDerived(Derived object) {
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

} // DerivedSwitch
