/**
 */
package safecap.derived.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import safecap.Extensible;
import safecap.Labeled;
import safecap.TransientValues;
import safecap.derived.Derived;
import safecap.derived.DerivedElement;
import safecap.derived.DerivedPackage;
import safecap.derived.MergedAmbit;
import safecap.derived.MergedJunction;
import safecap.derived.MergedPoint;
import safecap.derived.MergedSection;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.model.Point;
import safecap.model.Section;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * 
 * @see safecap.derived.DerivedPackage
 * @generated
 */
public class DerivedAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static DerivedPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public DerivedAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DerivedPackage.eINSTANCE;
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
	protected DerivedSwitch<Adapter> modelSwitch = new DerivedSwitch<Adapter>() {
		@Override
		public Adapter caseDerived(Derived object) {
			return createDerivedAdapter();
		}

		@Override
		public Adapter caseDerivedElement(DerivedElement object) {
			return createDerivedElementAdapter();
		}

		@Override
		public Adapter caseMergedPoint(MergedPoint object) {
			return createMergedPointAdapter();
		}

		@Override
		public Adapter caseMergedAmbit(MergedAmbit object) {
			return createMergedAmbitAdapter();
		}

		@Override
		public Adapter caseMergedJunction(MergedJunction object) {
			return createMergedJunctionAdapter();
		}

		@Override
		public Adapter caseMergedSection(MergedSection object) {
			return createMergedSectionAdapter();
		}

		@Override
		public Adapter caseLabeled(Labeled object) {
			return createLabeledAdapter();
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
		public Adapter casePoint(Point object) {
			return createPointAdapter();
		}

		@Override
		public Adapter caseAmbit(Ambit object) {
			return createAmbitAdapter();
		}

		@Override
		public Adapter caseJunction(Junction object) {
			return createJunctionAdapter();
		}

		@Override
		public Adapter caseSection(Section object) {
			return createSectionAdapter();
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
	 * Creates a new adapter for an object of class
	 * '{@link safecap.derived.DerivedElement <em>Element</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.derived.DerivedElement
	 * @generated
	 */
	public Adapter createDerivedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.derived.MergedPoint <em>Merged Point</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.derived.MergedPoint
	 * @generated
	 */
	public Adapter createMergedPointAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.derived.MergedAmbit <em>Merged Ambit</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.derived.MergedAmbit
	 * @generated
	 */
	public Adapter createMergedAmbitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.derived.MergedJunction <em>Merged Junction</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.derived.MergedJunction
	 * @generated
	 */
	public Adapter createMergedJunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.derived.MergedSection <em>Merged Section</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.derived.MergedSection
	 * @generated
	 */
	public Adapter createMergedSectionAdapter() {
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
	 * Creates a new adapter for an object of class '{@link safecap.model.Point
	 * <em>Point</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.model.Point
	 * @generated
	 */
	public Adapter createPointAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.model.Ambit
	 * <em>Ambit</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.model.Ambit
	 * @generated
	 */
	public Adapter createAmbitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.model.Junction
	 * <em>Junction</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.model.Junction
	 * @generated
	 */
	public Adapter createJunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.model.Section
	 * <em>Section</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.model.Section
	 * @generated
	 */
	public Adapter createSectionAdapter() {
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

} // DerivedAdapterFactory
