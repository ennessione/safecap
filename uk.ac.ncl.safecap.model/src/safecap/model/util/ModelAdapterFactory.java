/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.model.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import safecap.Extensible;
import safecap.Labeled;
import safecap.TransientValues;
import safecap.Visual;
import safecap.model.Ambit;
import safecap.model.ControlLogic;
import safecap.model.Junction;
import safecap.model.Line;
import safecap.model.Model;
import safecap.model.ModelPackage;
import safecap.model.Point;
import safecap.model.Route;
import safecap.model.RouteStateRule;
import safecap.model.Rule;
import safecap.model.Section;
import safecap.model.TimedOccupationRule;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * 
 * @see safecap.model.ModelPackage
 * @generated
 */
public class ModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static ModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ModelPackage.eINSTANCE;
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
	protected ModelSwitch<Adapter> modelSwitch = new ModelSwitch<Adapter>() {
		@Override
		public Adapter caseModel(Model object) {
			return createModelAdapter();
		}

		@Override
		public Adapter caseLine(Line object) {
			return createLineAdapter();
		}

		@Override
		public Adapter caseRoute(Route object) {
			return createRouteAdapter();
		}

		@Override
		public Adapter caseAmbit(Ambit object) {
			return createAmbitAdapter();
		}

		@Override
		public Adapter caseSection(Section object) {
			return createSectionAdapter();
		}

		@Override
		public Adapter caseJunction(Junction object) {
			return createJunctionAdapter();
		}

		@Override
		public Adapter casePoint(Point object) {
			return createPointAdapter();
		}

		@Override
		public Adapter caseRule(Rule object) {
			return createRuleAdapter();
		}

		@Override
		public Adapter caseTimedOccupationRule(TimedOccupationRule object) {
			return createTimedOccupationRuleAdapter();
		}

		@Override
		public Adapter caseRouteStateRule(RouteStateRule object) {
			return createRouteStateRuleAdapter();
		}

		@Override
		public Adapter caseControlLogic(ControlLogic object) {
			return createControlLogicAdapter();
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
		public Adapter caseVisual(Visual object) {
			return createVisualAdapter();
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
	 * Creates a new adapter for an object of class '{@link safecap.model.Line
	 * <em>Line</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.model.Line
	 * @generated
	 */
	public Adapter createLineAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.model.Route
	 * <em>Route</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.model.Route
	 * @generated
	 */
	public Adapter createRouteAdapter() {
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
	 * Creates a new adapter for an object of class '{@link safecap.model.Rule
	 * <em>Rule</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.model.Rule
	 * @generated
	 */
	public Adapter createRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.model.TimedOccupationRule <em>Timed Occupation Rule</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will
	 * catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.model.TimedOccupationRule
	 * @generated
	 */
	public Adapter createTimedOccupationRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.model.RouteStateRule <em>Route State Rule</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.model.RouteStateRule
	 * @generated
	 */
	public Adapter createRouteStateRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.model.ControlLogic <em>Control Logic</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.model.ControlLogic
	 * @generated
	 */
	public Adapter createControlLogicAdapter() {
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
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This
	 * default implementation returns null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // ModelAdapterFactory
