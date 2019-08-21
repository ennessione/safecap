/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see safecap.SafecapPackage
 * @generated
 */
public interface SafecapFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	SafecapFactory eINSTANCE = safecap.impl.SafecapFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Project</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Project</em>'.
	 * @generated
	 */
	Project createProject();

	/**
	 * Returns a new object of class '<em>Style</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Style</em>'.
	 * @generated
	 */
	Style createStyle();

	/**
	 * Returns a new object of class '<em>Highlighted Interval</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Highlighted Interval</em>'.
	 * @generated
	 */
	HighlightedInterval createHighlightedInterval();

	/**
	 * Returns a new object of class '<em>Visual Marker</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Visual Marker</em>'.
	 * @generated
	 */
	VisualMarker createVisualMarker();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	SafecapPackage getSafecapPackage();

} // SafecapFactory
