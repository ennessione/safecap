/**
 */
package safecap.commentary;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see safecap.commentary.CommentaryPackage
 * @generated
 */
public interface CommentaryFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	CommentaryFactory eINSTANCE = safecap.commentary.impl.CommentaryFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Orientable Commentary</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Orientable Commentary</em>'.
	 * @generated
	 */
	OrientableCommentary createOrientableCommentary();

	/**
	 * Returns a new object of class '<em>Station</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Station</em>'.
	 * @generated
	 */
	Station createStation();

	/**
	 * Returns a new object of class '<em>Bridge</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Bridge</em>'.
	 * @generated
	 */
	Bridge createBridge();

	/**
	 * Returns a new object of class '<em>Road</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Road</em>'.
	 * @generated
	 */
	Road createRoad();

	/**
	 * Returns a new object of class '<em>Tunnel</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Tunnel</em>'.
	 * @generated
	 */
	Tunnel createTunnel();

	/**
	 * Returns a new object of class '<em>Comments</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Comments</em>'.
	 * @generated
	 */
	Comments createComments();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	CommentaryPackage getCommentaryPackage();

} // CommentaryFactory
