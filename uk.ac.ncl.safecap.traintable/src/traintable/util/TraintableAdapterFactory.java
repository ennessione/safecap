/**
 */
package traintable.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import traintable.TDAttribute;
import traintable.TDTable;
import traintable.TDTableRow;
import traintable.TDValue;
import traintable.Train;
import traintable.TrainTable;
import traintable.TraintablePackage;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * 
 * @see traintable.TraintablePackage
 * @generated
 */
public class TraintableAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static TraintablePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public TraintableAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TraintablePackage.eINSTANCE;
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
	protected TraintableSwitch<Adapter> modelSwitch = new TraintableSwitch<Adapter>() {
		@Override
		public Adapter caseTDAttribute(TDAttribute object) {
			return createTDAttributeAdapter();
		}

		@Override
		public Adapter caseTrainTable(TrainTable object) {
			return createTrainTableAdapter();
		}

		@Override
		public Adapter caseTrain(Train object) {
			return createTrainAdapter();
		}

		@Override
		public Adapter caseTDTableRow(TDTableRow object) {
			return createTDTableRowAdapter();
		}

		@Override
		public Adapter caseTDTable(TDTable object) {
			return createTDTableAdapter();
		}

		@Override
		public Adapter caseTDValue(TDValue object) {
			return createTDValueAdapter();
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
	 * Creates a new adapter for an object of class '{@link traintable.TDAttribute
	 * <em>TD Attribute</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see traintable.TDAttribute
	 * @generated
	 */
	public Adapter createTDAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link traintable.TrainTable
	 * <em>Train Table</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see traintable.TrainTable
	 * @generated
	 */
	public Adapter createTrainTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link traintable.Train
	 * <em>Train</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see traintable.Train
	 * @generated
	 */
	public Adapter createTrainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link traintable.TDTableRow
	 * <em>TD Table Row</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see traintable.TDTableRow
	 * @generated
	 */
	public Adapter createTDTableRowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link traintable.TDTable
	 * <em>TD Table</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see traintable.TDTable
	 * @generated
	 */
	public Adapter createTDTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link traintable.TDValue
	 * <em>TD Value</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see traintable.TDValue
	 * @generated
	 */
	public Adapter createTDValueAdapter() {
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

} // TraintableAdapterFactory
