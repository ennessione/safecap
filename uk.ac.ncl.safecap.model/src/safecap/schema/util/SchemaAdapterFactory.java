/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import safecap.Extensible;
import safecap.Labeled;
import safecap.TransientValues;
import safecap.Visual;
import safecap.schema.HeightMap;
import safecap.schema.HeightPoint;
import safecap.schema.Node;
import safecap.schema.RouteSpeed;
import safecap.schema.Schema;
import safecap.schema.SchemaPackage;
import safecap.schema.Segment;
import safecap.schema.SubSchemaNode;
import safecap.schema.SubSchemaPath;
import safecap.schema.TerminalNode;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * 
 * @see safecap.schema.SchemaPackage
 * @generated
 */
public class SchemaAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static SchemaPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public SchemaAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SchemaPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object. <!--
	 * begin-user-doc --> This implementation returns <code>true</code> if the
	 * object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SchemaSwitch<Adapter> modelSwitch = new SchemaSwitch<Adapter>() {
		@Override
		public Adapter caseSchema(Schema object) {
			return createSchemaAdapter();
		}

		@Override
		public Adapter caseSegment(Segment object) {
			return createSegmentAdapter();
		}

		@Override
		public Adapter caseNode(Node object) {
			return createNodeAdapter();
		}

		@Override
		public Adapter caseTerminalNode(TerminalNode object) {
			return createTerminalNodeAdapter();
		}

		@Override
		public Adapter caseHeightMap(HeightMap object) {
			return createHeightMapAdapter();
		}

		@Override
		public Adapter caseHeightPoint(HeightPoint object) {
			return createHeightPointAdapter();
		}

		@Override
		public Adapter caseRouteSpeed(RouteSpeed object) {
			return createRouteSpeedAdapter();
		}

		@Override
		public Adapter caseSubSchemaNode(SubSchemaNode object) {
			return createSubSchemaNodeAdapter();
		}

		@Override
		public Adapter caseSubSchemaPath(SubSchemaPath object) {
			return createSubSchemaPathAdapter();
		}

		@Override
		public Adapter caseLabeled(Labeled object) {
			return createLabeledAdapter();
		}

		@Override
		public Adapter caseVisual(Visual object) {
			return createVisualAdapter();
		}

		@Override
		public Adapter caseTransientValues(TransientValues object) {
			return createTransientValuesAdapter();
		}

		@Override
		public Adapter caseExtensible(Extensible object) {
			return createExtensibleAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.schema.Schema
	 * <em>Schema</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.schema.Schema
	 * @generated
	 */
	public Adapter createSchemaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.schema.Segment
	 * <em>Segment</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.schema.Segment
	 * @generated
	 */
	public Adapter createSegmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.schema.Node
	 * <em>Node</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.schema.Node
	 * @generated
	 */
	public Adapter createNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.schema.TerminalNode <em>Terminal Node</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.schema.TerminalNode
	 * @generated
	 */
	public Adapter createTerminalNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.schema.HeightMap
	 * <em>Height Map</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.schema.HeightMap
	 * @generated
	 */
	public Adapter createHeightMapAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.schema.HeightPoint <em>Height Point</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.schema.HeightPoint
	 * @generated
	 */
	public Adapter createHeightPointAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.schema.RouteSpeed <em>Route Speed</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.schema.RouteSpeed
	 * @generated
	 */
	public Adapter createRouteSpeedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.schema.SubSchemaNode <em>Sub Schema Node</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.schema.SubSchemaNode
	 * @generated
	 */
	public Adapter createSubSchemaNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.schema.SubSchemaPath <em>Sub Schema Path</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.schema.SubSchemaPath
	 * @generated
	 */
	public Adapter createSubSchemaPathAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.Labeled
	 * <em>Labeled</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.Labeled
	 * @generated
	 */
	public Adapter createLabeledAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.Visual
	 * <em>Visual</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.Visual
	 * @generated
	 */
	public Adapter createVisualAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.TransientValues
	 * <em>Transient Values</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.TransientValues
	 * @generated
	 */
	public Adapter createTransientValuesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.Extensible
	 * <em>Extensible</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.Extensible
	 * @generated
	 */
	public Adapter createExtensibleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This
	 * default implementation returns null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // SchemaAdapterFactory
