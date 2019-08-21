/**
 */
package safecap.derived;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see safecap.derived.DerivedPackage
 * @generated
 */
public interface DerivedFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	DerivedFactory eINSTANCE = safecap.derived.impl.DerivedFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Merged Point</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Merged Point</em>'.
	 * @generated
	 */
	MergedPoint createMergedPoint();

	/**
	 * Returns a new object of class '<em>Merged Junction</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Merged Junction</em>'.
	 * @generated
	 */
	MergedJunction createMergedJunction();

	/**
	 * Returns a new object of class '<em>Merged Section</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Merged Section</em>'.
	 * @generated
	 */
	MergedSection createMergedSection();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	DerivedPackage getDerivedPackage();

} // DerivedFactory
