/**
 */
package traintable.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import traintable.TDTable;
import traintable.TDTableRow;
import traintable.TDValue;
import traintable.Train;
import traintable.TrainTable;
import traintable.TraintableFactory;
import traintable.TraintablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class TraintableFactoryImpl extends EFactoryImpl implements TraintableFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static TraintableFactory init() {
		try {
			final TraintableFactory theTraintableFactory = (TraintableFactory) EPackage.Registry.INSTANCE
					.getEFactory(TraintablePackage.eNS_URI);
			if (theTraintableFactory != null) {
				return theTraintableFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TraintableFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public TraintableFactoryImpl() {
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
		case TraintablePackage.TRAIN_TABLE:
			return createTrainTable();
		case TraintablePackage.TRAIN:
			return createTrain();
		case TraintablePackage.TD_TABLE_ROW:
			return createTDTableRow();
		case TraintablePackage.TD_TABLE:
			return createTDTable();
		case TraintablePackage.TD_VALUE:
			return createTDValue();
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
	public TrainTable createTrainTable() {
		final TrainTableImpl trainTable = new TrainTableImpl();
		return trainTable;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Train createTrain() {
		final TrainImpl train = new TrainImpl();
		return train;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TDTableRow createTDTableRow() {
		final TDTableRowImpl tdTableRow = new TDTableRowImpl();
		return tdTableRow;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TDTable createTDTable() {
		final TDTableImpl tdTable = new TDTableImpl();
		return tdTable;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TDValue createTDValue() {
		final TDValueImpl tdValue = new TDValueImpl();
		return tdValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TraintablePackage getTraintablePackage() {
		return (TraintablePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TraintablePackage getPackage() {
		return TraintablePackage.eINSTANCE;
	}

} // TraintableFactoryImpl
