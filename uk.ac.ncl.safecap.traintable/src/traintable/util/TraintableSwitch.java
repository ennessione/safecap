/**
 */
package traintable.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import traintable.TDAttribute;
import traintable.TDTable;
import traintable.TDTableRow;
import traintable.TDValue;
import traintable.Train;
import traintable.TrainTable;
import traintable.TraintablePackage;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see traintable.TraintablePackage
 * @generated
 */
public class TraintableSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static TraintablePackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public TraintableSwitch() {
		if (modelPackage == null) {
			modelPackage = TraintablePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @parameter ePackage the package in question.
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
		case TraintablePackage.TD_ATTRIBUTE: {
			final TDAttribute tdAttribute = (TDAttribute) theEObject;
			T result = caseTDAttribute(tdAttribute);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TraintablePackage.TRAIN_TABLE: {
			final TrainTable trainTable = (TrainTable) theEObject;
			T result = caseTrainTable(trainTable);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TraintablePackage.TRAIN: {
			final Train train = (Train) theEObject;
			T result = caseTrain(train);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TraintablePackage.TD_TABLE_ROW: {
			final TDTableRow tdTableRow = (TDTableRow) theEObject;
			T result = caseTDTableRow(tdTableRow);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TraintablePackage.TD_TABLE: {
			final TDTable tdTable = (TDTable) theEObject;
			T result = caseTDTable(tdTable);
			if (result == null) {
				result = caseTDAttribute(tdTable);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TraintablePackage.TD_VALUE: {
			final TDValue tdValue = (TDValue) theEObject;
			T result = caseTDValue(tdValue);
			if (result == null) {
				result = caseTDAttribute(tdValue);
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
	 * Returns the result of interpreting the object as an instance of '<em>TD
	 * Attribute</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TD
	 *         Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTDAttribute(TDAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Train
	 * Table</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Train
	 *         Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrainTable(TrainTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Train</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Train</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrain(Train object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TD Table
	 * Row</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TD Table
	 *         Row</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTDTableRow(TDTableRow object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TD
	 * Table</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TD
	 *         Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTDTable(TDTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TD
	 * Value</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TD
	 *         Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTDValue(TDValue object) {
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

} // TraintableSwitch
