/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import safecap.Extensible;
import safecap.Labeled;
import safecap.Provenance;
import safecap.TransientValues;
import safecap.Visual;
import safecap.trackside.Equipment;
import safecap.trackside.GenericLocatedEquipment;
import safecap.trackside.LeftSide;
import safecap.trackside.LeftSignal;
import safecap.trackside.LeftSpeedLimit;
import safecap.trackside.LeftStopAndGo;
import safecap.trackside.NodeWire;
import safecap.trackside.RedLeftSignal;
import safecap.trackside.RedRightSignal;
import safecap.trackside.RightSide;
import safecap.trackside.RightSignal;
import safecap.trackside.RightSpeedLimit;
import safecap.trackside.RightStopAndGo;
import safecap.trackside.Signal;
import safecap.trackside.SpeedLimit;
import safecap.trackside.StopAndGo;
import safecap.trackside.SubEquipment;
import safecap.trackside.SubWire;
import safecap.trackside.Trackside;
import safecap.trackside.TracksidePackage;
import safecap.trackside.Wire;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * 
 * @see safecap.trackside.TracksidePackage
 * @generated
 */
public class TracksideAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static TracksidePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public TracksideAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TracksidePackage.eINSTANCE;
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
	protected TracksideSwitch<Adapter> modelSwitch = new TracksideSwitch<Adapter>() {
		@Override
		public Adapter caseTrackside(Trackside object) {
			return createTracksideAdapter();
		}

		@Override
		public Adapter caseEquipment(Equipment object) {
			return createEquipmentAdapter();
		}

		@Override
		public Adapter caseSignal(Signal object) {
			return createSignalAdapter();
		}

		@Override
		public Adapter caseSpeedLimit(SpeedLimit object) {
			return createSpeedLimitAdapter();
		}

		@Override
		public Adapter caseLeftSignal(LeftSignal object) {
			return createLeftSignalAdapter();
		}

		@Override
		public Adapter caseRightSignal(RightSignal object) {
			return createRightSignalAdapter();
		}

		@Override
		public Adapter caseLeftSpeedLimit(LeftSpeedLimit object) {
			return createLeftSpeedLimitAdapter();
		}

		@Override
		public Adapter caseRightSpeedLimit(RightSpeedLimit object) {
			return createRightSpeedLimitAdapter();
		}

		@Override
		public Adapter caseWire(Wire object) {
			return createWireAdapter();
		}

		@Override
		public Adapter caseRedLeftSignal(RedLeftSignal object) {
			return createRedLeftSignalAdapter();
		}

		@Override
		public Adapter caseRedRightSignal(RedRightSignal object) {
			return createRedRightSignalAdapter();
		}

		@Override
		public Adapter caseLeftSide(LeftSide object) {
			return createLeftSideAdapter();
		}

		@Override
		public Adapter caseRightSide(RightSide object) {
			return createRightSideAdapter();
		}

		@Override
		public Adapter caseStopAndGo(StopAndGo object) {
			return createStopAndGoAdapter();
		}

		@Override
		public Adapter caseLeftStopAndGo(LeftStopAndGo object) {
			return createLeftStopAndGoAdapter();
		}

		@Override
		public Adapter caseRightStopAndGo(RightStopAndGo object) {
			return createRightStopAndGoAdapter();
		}

		@Override
		public Adapter caseSubEquipment(SubEquipment object) {
			return createSubEquipmentAdapter();
		}

		@Override
		public Adapter caseSubWire(SubWire object) {
			return createSubWireAdapter();
		}

		@Override
		public Adapter caseNodeWire(NodeWire object) {
			return createNodeWireAdapter();
		}

		@Override
		public Adapter caseGenericLocatedEquipment(GenericLocatedEquipment object) {
			return createGenericLocatedEquipmentAdapter();
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
		public Adapter caseProvenance(Provenance object) {
			return createProvenanceAdapter();
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
	 * '{@link safecap.trackside.Equipment <em>Equipment</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.Equipment
	 * @generated
	 */
	public Adapter createEquipmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.trackside.Signal
	 * <em>Signal</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.Signal
	 * @generated
	 */
	public Adapter createSignalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.SpeedLimit <em>Speed Limit</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.SpeedLimit
	 * @generated
	 */
	public Adapter createSpeedLimitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.LeftSignal <em>Left Signal</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.LeftSignal
	 * @generated
	 */
	public Adapter createLeftSignalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.RightSignal <em>Right Signal</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.RightSignal
	 * @generated
	 */
	public Adapter createRightSignalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.LeftSpeedLimit <em>Left Speed Limit</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.LeftSpeedLimit
	 * @generated
	 */
	public Adapter createLeftSpeedLimitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.RightSpeedLimit <em>Right Speed Limit</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.RightSpeedLimit
	 * @generated
	 */
	public Adapter createRightSpeedLimitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link safecap.trackside.Wire
	 * <em>Wire</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.Wire
	 * @generated
	 */
	public Adapter createWireAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.RedLeftSignal <em>Red Left Signal</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.RedLeftSignal
	 * @generated
	 */
	public Adapter createRedLeftSignalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.RedRightSignal <em>Red Right Signal</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.RedRightSignal
	 * @generated
	 */
	public Adapter createRedRightSignalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.LeftSide <em>Left Side</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.LeftSide
	 * @generated
	 */
	public Adapter createLeftSideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.RightSide <em>Right Side</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.RightSide
	 * @generated
	 */
	public Adapter createRightSideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.StopAndGo <em>Stop And Go</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.StopAndGo
	 * @generated
	 */
	public Adapter createStopAndGoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.LeftStopAndGo <em>Left Stop And Go</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.LeftStopAndGo
	 * @generated
	 */
	public Adapter createLeftStopAndGoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.RightStopAndGo <em>Right Stop And Go</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.RightStopAndGo
	 * @generated
	 */
	public Adapter createRightStopAndGoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.SubEquipment <em>Sub Equipment</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.SubEquipment
	 * @generated
	 */
	public Adapter createSubEquipmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.SubWire <em>Sub Wire</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.SubWire
	 * @generated
	 */
	public Adapter createSubWireAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.NodeWire <em>Node Wire</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.NodeWire
	 * @generated
	 */
	public Adapter createNodeWireAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link safecap.trackside.GenericLocatedEquipment <em>Generic Located
	 * Equipment</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see safecap.trackside.GenericLocatedEquipment
	 * @generated
	 */
	public Adapter createGenericLocatedEquipmentAdapter() {
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

} // TracksideAdapterFactory
