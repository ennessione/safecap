/**
 */
package traintable;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see traintable.TraintableFactory
 * @model kind="package"
 * @generated
 */
public interface TraintablePackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "traintable";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "uk.ac.ncl.safecap.traintable.emf";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "uk.ac.ncl.safecap.traintable.emf";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	TraintablePackage eINSTANCE = traintable.impl.TraintablePackageImpl.init();

	/**
	 * The meta object id for the '{@link traintable.impl.TDAttributeImpl <em>TD
	 * Attribute</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see traintable.impl.TDAttributeImpl
	 * @see traintable.impl.TraintablePackageImpl#getTDAttribute()
	 * @generated
	 */
	int TD_ATTRIBUTE = 0;

	/**
	 * The number of structural features of the '<em>TD Attribute</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TD_ATTRIBUTE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link traintable.impl.TrainTableImpl <em>Train
	 * Table</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see traintable.impl.TrainTableImpl
	 * @see traintable.impl.TraintablePackageImpl#getTrainTable()
	 * @generated
	 */
	int TRAIN_TABLE = 1;

	/**
	 * The feature id for the '<em><b>Trains</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRAIN_TABLE__TRAINS = 0;

	/**
	 * The number of structural features of the '<em>Train Table</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRAIN_TABLE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link traintable.impl.TrainImpl <em>Train</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see traintable.impl.TrainImpl
	 * @see traintable.impl.TraintablePackageImpl#getTrain()
	 * @generated
	 */
	int TRAIN = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRAIN__NAME = 0;

	/**
	 * The feature id for the '<em><b>Speed</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRAIN__SPEED = 1;

	/**
	 * The feature id for the '<em><b>Acceleration</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRAIN__ACCELERATION = 2;

	/**
	 * The feature id for the '<em><b>Deceleration</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRAIN__DECELERATION = 3;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRAIN__LENGTH = 4;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRAIN__ATTRIBUTES = 5;

	/**
	 * The number of structural features of the '<em>Train</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRAIN_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link traintable.impl.TDTableRowImpl <em>TD
	 * Table Row</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see traintable.impl.TDTableRowImpl
	 * @see traintable.impl.TraintablePackageImpl#getTDTableRow()
	 * @generated
	 */
	int TD_TABLE_ROW = 3;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TD_TABLE_ROW__VALUES = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TD_TABLE_ROW__LABEL = 1;

	/**
	 * The number of structural features of the '<em>TD Table Row</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TD_TABLE_ROW_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link traintable.impl.TDTableImpl <em>TD
	 * Table</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see traintable.impl.TDTableImpl
	 * @see traintable.impl.TraintablePackageImpl#getTDTable()
	 * @generated
	 */
	int TD_TABLE = 4;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TD_TABLE__LABEL = TD_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Column Labels</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TD_TABLE__COLUMN_LABELS = TD_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rows</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TD_TABLE__ROWS = TD_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>TD Table</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TD_TABLE_FEATURE_COUNT = TD_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link traintable.impl.TDValueImpl <em>TD
	 * Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see traintable.impl.TDValueImpl
	 * @see traintable.impl.TraintablePackageImpl#getTDValue()
	 * @generated
	 */
	int TD_VALUE = 5;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TD_VALUE__LABEL = TD_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TD_VALUE__VALUE = TD_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>TD Value</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TD_VALUE_FEATURE_COUNT = TD_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * Returns the meta object for class '{@link traintable.TDAttribute <em>TD
	 * Attribute</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>TD Attribute</em>'.
	 * @see traintable.TDAttribute
	 * @generated
	 */
	EClass getTDAttribute();

	/**
	 * Returns the meta object for class '{@link traintable.TrainTable <em>Train
	 * Table</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Train Table</em>'.
	 * @see traintable.TrainTable
	 * @generated
	 */
	EClass getTrainTable();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link traintable.TrainTable#getTrains <em>Trains</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Trains</em>'.
	 * @see traintable.TrainTable#getTrains()
	 * @see #getTrainTable()
	 * @generated
	 */
	EReference getTrainTable_Trains();

	/**
	 * Returns the meta object for class '{@link traintable.Train <em>Train</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Train</em>'.
	 * @see traintable.Train
	 * @generated
	 */
	EClass getTrain();

	/**
	 * Returns the meta object for the attribute '{@link traintable.Train#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see traintable.Train#getName()
	 * @see #getTrain()
	 * @generated
	 */
	EAttribute getTrain_Name();

	/**
	 * Returns the meta object for the attribute '{@link traintable.Train#getSpeed
	 * <em>Speed</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Speed</em>'.
	 * @see traintable.Train#getSpeed()
	 * @see #getTrain()
	 * @generated
	 */
	EAttribute getTrain_Speed();

	/**
	 * Returns the meta object for the attribute
	 * '{@link traintable.Train#getAcceleration <em>Acceleration</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Acceleration</em>'.
	 * @see traintable.Train#getAcceleration()
	 * @see #getTrain()
	 * @generated
	 */
	EAttribute getTrain_Acceleration();

	/**
	 * Returns the meta object for the attribute
	 * '{@link traintable.Train#getDeceleration <em>Deceleration</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Deceleration</em>'.
	 * @see traintable.Train#getDeceleration()
	 * @see #getTrain()
	 * @generated
	 */
	EAttribute getTrain_Deceleration();

	/**
	 * Returns the meta object for the attribute '{@link traintable.Train#getLength
	 * <em>Length</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see traintable.Train#getLength()
	 * @see #getTrain()
	 * @generated
	 */
	EAttribute getTrain_Length();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link traintable.Train#getAttributes <em>Attributes</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Attributes</em>'.
	 * @see traintable.Train#getAttributes()
	 * @see #getTrain()
	 * @generated
	 */
	EReference getTrain_Attributes();

	/**
	 * Returns the meta object for class '{@link traintable.TDTableRow <em>TD Table
	 * Row</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>TD Table Row</em>'.
	 * @see traintable.TDTableRow
	 * @generated
	 */
	EClass getTDTableRow();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link traintable.TDTableRow#getValues <em>Values</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Values</em>'.
	 * @see traintable.TDTableRow#getValues()
	 * @see #getTDTableRow()
	 * @generated
	 */
	EAttribute getTDTableRow_Values();

	/**
	 * Returns the meta object for the attribute
	 * '{@link traintable.TDTableRow#getLabel <em>Label</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see traintable.TDTableRow#getLabel()
	 * @see #getTDTableRow()
	 * @generated
	 */
	EAttribute getTDTableRow_Label();

	/**
	 * Returns the meta object for class '{@link traintable.TDTable <em>TD
	 * Table</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>TD Table</em>'.
	 * @see traintable.TDTable
	 * @generated
	 */
	EClass getTDTable();

	/**
	 * Returns the meta object for the attribute '{@link traintable.TDTable#getLabel
	 * <em>Label</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see traintable.TDTable#getLabel()
	 * @see #getTDTable()
	 * @generated
	 */
	EAttribute getTDTable_Label();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link traintable.TDTable#getColumnLabels <em>Column Labels</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Column Labels</em>'.
	 * @see traintable.TDTable#getColumnLabels()
	 * @see #getTDTable()
	 * @generated
	 */
	EAttribute getTDTable_ColumnLabels();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link traintable.TDTable#getRows <em>Rows</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Rows</em>'.
	 * @see traintable.TDTable#getRows()
	 * @see #getTDTable()
	 * @generated
	 */
	EReference getTDTable_Rows();

	/**
	 * Returns the meta object for class '{@link traintable.TDValue <em>TD
	 * Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>TD Value</em>'.
	 * @see traintable.TDValue
	 * @generated
	 */
	EClass getTDValue();

	/**
	 * Returns the meta object for the attribute '{@link traintable.TDValue#getLabel
	 * <em>Label</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see traintable.TDValue#getLabel()
	 * @see #getTDValue()
	 * @generated
	 */
	EAttribute getTDValue_Label();

	/**
	 * Returns the meta object for the attribute '{@link traintable.TDValue#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see traintable.TDValue#getValue()
	 * @see #getTDValue()
	 * @generated
	 */
	EAttribute getTDValue_Value();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TraintableFactory getTraintableFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link traintable.impl.TDAttributeImpl
		 * <em>TD Attribute</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see traintable.impl.TDAttributeImpl
		 * @see traintable.impl.TraintablePackageImpl#getTDAttribute()
		 * @generated
		 */
		EClass TD_ATTRIBUTE = eINSTANCE.getTDAttribute();

		/**
		 * The meta object literal for the '{@link traintable.impl.TrainTableImpl
		 * <em>Train Table</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see traintable.impl.TrainTableImpl
		 * @see traintable.impl.TraintablePackageImpl#getTrainTable()
		 * @generated
		 */
		EClass TRAIN_TABLE = eINSTANCE.getTrainTable();

		/**
		 * The meta object literal for the '<em><b>Trains</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRAIN_TABLE__TRAINS = eINSTANCE.getTrainTable_Trains();

		/**
		 * The meta object literal for the '{@link traintable.impl.TrainImpl
		 * <em>Train</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see traintable.impl.TrainImpl
		 * @see traintable.impl.TraintablePackageImpl#getTrain()
		 * @generated
		 */
		EClass TRAIN = eINSTANCE.getTrain();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRAIN__NAME = eINSTANCE.getTrain_Name();

		/**
		 * The meta object literal for the '<em><b>Speed</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRAIN__SPEED = eINSTANCE.getTrain_Speed();

		/**
		 * The meta object literal for the '<em><b>Acceleration</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRAIN__ACCELERATION = eINSTANCE.getTrain_Acceleration();

		/**
		 * The meta object literal for the '<em><b>Deceleration</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRAIN__DECELERATION = eINSTANCE.getTrain_Deceleration();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRAIN__LENGTH = eINSTANCE.getTrain_Length();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRAIN__ATTRIBUTES = eINSTANCE.getTrain_Attributes();

		/**
		 * The meta object literal for the '{@link traintable.impl.TDTableRowImpl <em>TD
		 * Table Row</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see traintable.impl.TDTableRowImpl
		 * @see traintable.impl.TraintablePackageImpl#getTDTableRow()
		 * @generated
		 */
		EClass TD_TABLE_ROW = eINSTANCE.getTDTableRow();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TD_TABLE_ROW__VALUES = eINSTANCE.getTDTableRow_Values();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TD_TABLE_ROW__LABEL = eINSTANCE.getTDTableRow_Label();

		/**
		 * The meta object literal for the '{@link traintable.impl.TDTableImpl <em>TD
		 * Table</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see traintable.impl.TDTableImpl
		 * @see traintable.impl.TraintablePackageImpl#getTDTable()
		 * @generated
		 */
		EClass TD_TABLE = eINSTANCE.getTDTable();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TD_TABLE__LABEL = eINSTANCE.getTDTable_Label();

		/**
		 * The meta object literal for the '<em><b>Column Labels</b></em>' attribute
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TD_TABLE__COLUMN_LABELS = eINSTANCE.getTDTable_ColumnLabels();

		/**
		 * The meta object literal for the '<em><b>Rows</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TD_TABLE__ROWS = eINSTANCE.getTDTable_Rows();

		/**
		 * The meta object literal for the '{@link traintable.impl.TDValueImpl <em>TD
		 * Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see traintable.impl.TDValueImpl
		 * @see traintable.impl.TraintablePackageImpl#getTDValue()
		 * @generated
		 */
		EClass TD_VALUE = eINSTANCE.getTDValue();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TD_VALUE__LABEL = eINSTANCE.getTDValue_Label();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TD_VALUE__VALUE = eINSTANCE.getTDValue_Value();

	}

} // TraintablePackage
