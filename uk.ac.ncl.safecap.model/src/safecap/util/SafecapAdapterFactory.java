/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import safecap.Extensible;
import safecap.HighlightedInterval;
import safecap.Labeled;
import safecap.Project;
import safecap.Provenance;
import safecap.SafecapPackage;
import safecap.Style;
import safecap.TransientValues;
import safecap.Visual;
import safecap.VisualMarker;
import safecap.commentary.Comments;
import safecap.derived.Derived;
import safecap.model.Model;
import safecap.schema.Schema;
import safecap.trackside.Trackside;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * 
 * @see safecap.SafecapPackage
 * @generated
 */
public class SafecapAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static SafecapPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public SafecapAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SafecapPackage.eINSTANCE;
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
	protected SafecapSwitch<Adapter> modelSwitch = new SafecapSwitch<Adapter>() {
		@Override
		public Adapter caseLabeled(Labeled object) {
			return createLabeledAdapter();
		}

		@Override
		public Adapter caseProject(Project object) {
			return createProjectAdapter();
		}

		@Override
		public Adapter caseVisual(Visual object) {
			return createVisualAdapter();
		}

		@Override
		public Adapter caseStyle(Style object) {
			return createStyleAdapter();
		}

		@Override
		public Adapter caseProvenance(Provenance object) {
			return createProvenanceAdapter();
		}

		@Override
		public Adapter caseExtensible(Extensible object) {
			return createExtensibleAdapter();
		}

		@Override
		public Adapter caseHighlightedInterval(HighlightedInterval object) {
			return createHighlightedIntervalAdapter();
		}

		@Override
		public Adapter caseVisualMarker(VisualMarker object) {
			return createVisualMarkerAdapter();
		}

		@Override
		public Adapter caseTransientValues(TransientValues object) {
			return createTransientValuesAdapter();
		}

		@Override
		public Adapter caseSchema(Schema object) {
			return createSchemaAdapter();
		}

		@Override
		public Adapter caseModel(Model object) {
			return createModelAdapter();
		}

		@Override
		public Adapter caseTrackside(Trackside object) {
			return createTracksideAdapter();
		}

		@Override
		public Adapter caseComments(Comments object) {
			return createCommentsAdapter();
		}

		@Override
		public Adapter caseDerived(Derived object) {
			return createDerivedAdapter();
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
	 * Creates a new adapter for an object of class '{@link safecap.Project
	 * <em>Project</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.Project
	 * @generated
	 */
	public Adapter createProjectAdapter() {
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
	 * Creates a new adapter for an object of class '{@link safecap.Style
	 * <em>Style</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.Style
	 * @generated
	 */
	public Adapter createStyleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.Provenance
	 * <em>Provenance</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.Provenance
	 * @generated
	 */
	public Adapter createProvenanceAdapter() {
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
	 * Creates a new adapter for an object of class
	 * '{@link safecap.HighlightedInterval <em>Highlighted Interval</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.HighlightedInterval
	 * @generated
	 */
	public Adapter createHighlightedIntervalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.VisualMarker
	 * <em>Visual Marker</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.VisualMarker
	 * @generated
	 */
	public Adapter createVisualMarkerAdapter() {
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
	 * Creates a new adapter for an object of class '{@link safecap.model.Model
	 * <em>Model</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.model.Model
	 * @generated
	 */
	public Adapter createModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.Trackside <em>Trackside</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.Trackside
	 * @generated
	 */
	public Adapter createTracksideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.commentary.Comments <em>Comments</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.commentary.Comments
	 * @generated
	 */
	public Adapter createCommentsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.derived.Derived
	 * <em>Derived</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.derived.Derived
	 * @generated
	 */
	public Adapter createDerivedAdapter() {
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

} // SafecapAdapterFactory
