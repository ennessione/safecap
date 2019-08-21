/**
 */
package safecap.extension;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see safecap.extension.ExtensionPackage
 * @generated
 */
public interface ExtensionFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	ExtensionFactory eINSTANCE = safecap.extension.impl.ExtensionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Ext Attribute Int</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Ext Attribute Int</em>'.
	 * @generated
	 */
	ExtAttributeInt createExtAttributeInt();

	/**
	 * Returns a new object of class '<em>Ext Attribute Dbl</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Ext Attribute Dbl</em>'.
	 * @generated
	 */
	ExtAttributeDbl createExtAttributeDbl();

	/**
	 * Returns a new object of class '<em>Ext Attribute Str</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Ext Attribute Str</em>'.
	 * @generated
	 */
	ExtAttributeStr createExtAttributeStr();

	/**
	 * Returns a new object of class '<em>Ext Attribute B</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Ext Attribute B</em>'.
	 * @generated
	 */
	ExtAttributeB createExtAttributeB();

	/**
	 * Returns a new object of class '<em>Custom Label</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Custom Label</em>'.
	 * @generated
	 */
	CustomLabel createCustomLabel();

	/**
	 * Returns a new object of class '<em>Point</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Point</em>'.
	 * @generated
	 */
	Point createPoint();

	/**
	 * Returns a new object of class '<em>Custom Colour</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Custom Colour</em>'.
	 * @generated
	 */
	CustomColour createCustomColour();

	/**
	 * Returns a new object of class '<em>Custom Shape</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Custom Shape</em>'.
	 * @generated
	 */
	CustomShape createCustomShape();

	/**
	 * Returns a new object of class '<em>Ext Visual</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Ext Visual</em>'.
	 * @generated
	 */
	ExtVisual createExtVisual();

	/**
	 * Returns a new object of class '<em>Ext Equipment</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Ext Equipment</em>'.
	 * @generated
	 */
	ExtEquipment createExtEquipment();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	ExtensionPackage getExtensionPackage();

} // ExtensionFactory
