/**
 */
package traintable;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see traintable.TraintablePackage
 * @generated
 */
public interface TraintableFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	TraintableFactory eINSTANCE = traintable.impl.TraintableFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Train Table</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Train Table</em>'.
	 * @generated
	 */
	TrainTable createTrainTable();

	/**
	 * Returns a new object of class '<em>Train</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Train</em>'.
	 * @generated
	 */
	Train createTrain();

	/**
	 * Returns a new object of class '<em>TD Table Row</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>TD Table Row</em>'.
	 * @generated
	 */
	TDTableRow createTDTableRow();

	/**
	 * Returns a new object of class '<em>TD Table</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>TD Table</em>'.
	 * @generated
	 */
	TDTable createTDTable();

	/**
	 * Returns a new object of class '<em>TD Value</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>TD Value</em>'.
	 * @generated
	 */
	TDValue createTDValue();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	TraintablePackage getTraintablePackage();

} // TraintableFactory
