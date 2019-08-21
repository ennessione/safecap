/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see safecap.trackside.TracksidePackage
 * @generated
 */
public interface TracksideFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	TracksideFactory eINSTANCE = safecap.trackside.impl.TracksideFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Trackside</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Trackside</em>'.
	 * @generated
	 */
	Trackside createTrackside();

	/**
	 * Returns a new object of class '<em>Left Signal</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Left Signal</em>'.
	 * @generated
	 */
	LeftSignal createLeftSignal();

	/**
	 * Returns a new object of class '<em>Right Signal</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Right Signal</em>'.
	 * @generated
	 */
	RightSignal createRightSignal();

	/**
	 * Returns a new object of class '<em>Left Speed Limit</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Left Speed Limit</em>'.
	 * @generated
	 */
	LeftSpeedLimit createLeftSpeedLimit();

	/**
	 * Returns a new object of class '<em>Right Speed Limit</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Right Speed Limit</em>'.
	 * @generated
	 */
	RightSpeedLimit createRightSpeedLimit();

	/**
	 * Returns a new object of class '<em>Wire</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Wire</em>'.
	 * @generated
	 */
	Wire createWire();

	/**
	 * Returns a new object of class '<em>Red Left Signal</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Red Left Signal</em>'.
	 * @generated
	 */
	RedLeftSignal createRedLeftSignal();

	/**
	 * Returns a new object of class '<em>Red Right Signal</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Red Right Signal</em>'.
	 * @generated
	 */
	RedRightSignal createRedRightSignal();

	/**
	 * Returns a new object of class '<em>Stop And Go</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Stop And Go</em>'.
	 * @generated
	 */
	StopAndGo createStopAndGo();

	/**
	 * Returns a new object of class '<em>Left Stop And Go</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Left Stop And Go</em>'.
	 * @generated
	 */
	LeftStopAndGo createLeftStopAndGo();

	/**
	 * Returns a new object of class '<em>Right Stop And Go</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Right Stop And Go</em>'.
	 * @generated
	 */
	RightStopAndGo createRightStopAndGo();

	/**
	 * Returns a new object of class '<em>Sub Wire</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Sub Wire</em>'.
	 * @generated
	 */
	SubWire createSubWire();

	/**
	 * Returns a new object of class '<em>Node Wire</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Node Wire</em>'.
	 * @generated
	 */
	NodeWire createNodeWire();

	/**
	 * Returns a new object of class '<em>Generic Located Equipment</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Generic Located Equipment</em>'.
	 * @generated
	 */
	GenericLocatedEquipment createGenericLocatedEquipment();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	TracksidePackage getTracksidePackage();

} // TracksideFactory
