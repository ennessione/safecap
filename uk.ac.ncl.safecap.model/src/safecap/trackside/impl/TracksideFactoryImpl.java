/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import safecap.trackside.GenericLocatedEquipment;
import safecap.trackside.LeftSignal;
import safecap.trackside.LeftSpeedLimit;
import safecap.trackside.LeftStopAndGo;
import safecap.trackside.NodeWire;
import safecap.trackside.RedLeftSignal;
import safecap.trackside.RedRightSignal;
import safecap.trackside.RightSignal;
import safecap.trackside.RightSpeedLimit;
import safecap.trackside.RightStopAndGo;
import safecap.trackside.SignalType;
import safecap.trackside.StopAndGo;
import safecap.trackside.SubWire;
import safecap.trackside.Trackside;
import safecap.trackside.TracksideFactory;
import safecap.trackside.TracksidePackage;
import safecap.trackside.Wire;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class TracksideFactoryImpl extends EFactoryImpl implements TracksideFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static TracksideFactory init() {
		try {
			final TracksideFactory theTracksideFactory = (TracksideFactory) EPackage.Registry.INSTANCE
					.getEFactory(TracksidePackage.eNS_URI);
			if (theTracksideFactory != null) {
				return theTracksideFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TracksideFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public TracksideFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case TracksidePackage.TRACKSIDE:
			return createTrackside();
		case TracksidePackage.LEFT_SIGNAL:
			return createLeftSignal();
		case TracksidePackage.RIGHT_SIGNAL:
			return createRightSignal();
		case TracksidePackage.LEFT_SPEED_LIMIT:
			return createLeftSpeedLimit();
		case TracksidePackage.RIGHT_SPEED_LIMIT:
			return createRightSpeedLimit();
		case TracksidePackage.WIRE:
			return createWire();
		case TracksidePackage.RED_LEFT_SIGNAL:
			return createRedLeftSignal();
		case TracksidePackage.RED_RIGHT_SIGNAL:
			return createRedRightSignal();
		case TracksidePackage.STOP_AND_GO:
			return createStopAndGo();
		case TracksidePackage.LEFT_STOP_AND_GO:
			return createLeftStopAndGo();
		case TracksidePackage.RIGHT_STOP_AND_GO:
			return createRightStopAndGo();
		case TracksidePackage.SUB_WIRE:
			return createSubWire();
		case TracksidePackage.NODE_WIRE:
			return createNodeWire();
		case TracksidePackage.GENERIC_LOCATED_EQUIPMENT:
			return createGenericLocatedEquipment();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case TracksidePackage.SIGNAL_TYPE:
			return createSignalTypeFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case TracksidePackage.SIGNAL_TYPE:
			return convertSignalTypeToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Trackside createTrackside() {
		final TracksideImpl trackside = new TracksideImpl();
		return trackside;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public LeftSignal createLeftSignal() {
		final LeftSignalImpl leftSignal = new LeftSignalImpl();
		return leftSignal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public RightSignal createRightSignal() {
		final RightSignalImpl rightSignal = new RightSignalImpl();
		return rightSignal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public LeftSpeedLimit createLeftSpeedLimit() {
		final LeftSpeedLimitImpl leftSpeedLimit = new LeftSpeedLimitImpl();
		return leftSpeedLimit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public RightSpeedLimit createRightSpeedLimit() {
		final RightSpeedLimitImpl rightSpeedLimit = new RightSpeedLimitImpl();
		return rightSpeedLimit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Wire createWire() {
		final WireImpl wire = new WireImpl();
		return wire;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public RedLeftSignal createRedLeftSignal() {
		final RedLeftSignalImpl redLeftSignal = new RedLeftSignalImpl();
		return redLeftSignal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public RedRightSignal createRedRightSignal() {
		final RedRightSignalImpl redRightSignal = new RedRightSignalImpl();
		return redRightSignal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public StopAndGo createStopAndGo() {
		final StopAndGoImpl stopAndGo = new StopAndGoImpl();
		return stopAndGo;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public LeftStopAndGo createLeftStopAndGo() {
		final LeftStopAndGoImpl leftStopAndGo = new LeftStopAndGoImpl();
		return leftStopAndGo;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public RightStopAndGo createRightStopAndGo() {
		final RightStopAndGoImpl rightStopAndGo = new RightStopAndGoImpl();
		return rightStopAndGo;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SubWire createSubWire() {
		final SubWireImpl subWire = new SubWireImpl();
		return subWire;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NodeWire createNodeWire() {
		final NodeWireImpl nodeWire = new NodeWireImpl();
		return nodeWire;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public GenericLocatedEquipment createGenericLocatedEquipment() {
		final GenericLocatedEquipmentImpl genericLocatedEquipment = new GenericLocatedEquipmentImpl();
		return genericLocatedEquipment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SignalType createSignalTypeFromString(EDataType eDataType, String initialValue) {
		final SignalType result = SignalType.get(initialValue);
		if (result == null) {
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertSignalTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TracksidePackage getTracksidePackage() {
		return (TracksidePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TracksidePackage getPackage() {
		return TracksidePackage.eINSTANCE;
	}

} // TracksideFactoryImpl
