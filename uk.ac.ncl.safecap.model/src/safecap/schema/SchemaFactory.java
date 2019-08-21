/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see safecap.schema.SchemaPackage
 * @generated
 */
public interface SchemaFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	SchemaFactory eINSTANCE = safecap.schema.impl.SchemaFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Schema</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Schema</em>'.
	 * @generated
	 */
	Schema createSchema();

	/**
	 * Returns a new object of class '<em>Segment</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Segment</em>'.
	 * @generated
	 */
	Segment createSegment();

	/**
	 * Returns a new object of class '<em>Node</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Node</em>'.
	 * @generated
	 */
	Node createNode();

	/**
	 * Returns a new object of class '<em>Terminal Node</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Terminal Node</em>'.
	 * @generated
	 */
	TerminalNode createTerminalNode();

	/**
	 * Returns a new object of class '<em>Height Map</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Height Map</em>'.
	 * @generated
	 */
	HeightMap createHeightMap();

	/**
	 * Returns a new object of class '<em>Height Point</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Height Point</em>'.
	 * @generated
	 */
	HeightPoint createHeightPoint();

	/**
	 * Returns a new object of class '<em>Route Speed</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Route Speed</em>'.
	 * @generated
	 */
	RouteSpeed createRouteSpeed();

	/**
	 * Returns a new object of class '<em>Sub Schema Node</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Sub Schema Node</em>'.
	 * @generated
	 */
	SubSchemaNode createSubSchemaNode();

	/**
	 * Returns a new object of class '<em>Sub Schema Path</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Sub Schema Path</em>'.
	 * @generated
	 */
	SubSchemaPath createSubSchemaPath();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	SchemaPackage getSchemaPackage();

} // SchemaFactory
