/**
 */
package servicepattern;

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
 * @see servicepattern.ServicepatternFactory
 * @model kind="package"
 * @generated
 */
public interface ServicepatternPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "servicepattern";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "uk.ac.ncl.safecap.servicepattern.emf";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "uk.ac.ncl.safecap.servicepattern.emf";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	ServicepatternPackage eINSTANCE = servicepattern.impl.ServicepatternPackageImpl.init();

	/**
	 * The meta object id for the '{@link servicepattern.impl.LibraryImpl
	 * <em>Library</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see servicepattern.impl.LibraryImpl
	 * @see servicepattern.impl.ServicepatternPackageImpl#getLibrary()
	 * @generated
	 */
	int LIBRARY = 0;

	/**
	 * The feature id for the '<em><b>Patterns</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIBRARY__PATTERNS = 0;

	/**
	 * The number of structural features of the '<em>Library</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIBRARY_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link servicepattern.impl.PatternImpl
	 * <em>Pattern</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see servicepattern.impl.PatternImpl
	 * @see servicepattern.impl.ServicepatternPackageImpl#getPattern()
	 * @generated
	 */
	int PATTERN = 1;

	/**
	 * The feature id for the '<em><b>Train Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__TRAIN_NAME = 0;

	/**
	 * The feature id for the '<em><b>Train Class</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__TRAIN_CLASS = 1;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__LINE = 2;

	/**
	 * The feature id for the '<em><b>Driver</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__DRIVER = 3;

	/**
	 * The feature id for the '<em><b>Start</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__START = 4;

	/**
	 * The feature id for the '<em><b>Reliability</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__RELIABILITY = 5;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__COLOR = 6;

	/**
	 * The number of structural features of the '<em>Pattern</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_FEATURE_COUNT = 7;

	/**
	 * Returns the meta object for class '{@link servicepattern.Library
	 * <em>Library</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Library</em>'.
	 * @see servicepattern.Library
	 * @generated
	 */
	EClass getLibrary();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link servicepattern.Library#getPatterns <em>Patterns</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Patterns</em>'.
	 * @see servicepattern.Library#getPatterns()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_Patterns();

	/**
	 * Returns the meta object for class '{@link servicepattern.Pattern
	 * <em>Pattern</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Pattern</em>'.
	 * @see servicepattern.Pattern
	 * @generated
	 */
	EClass getPattern();

	/**
	 * Returns the meta object for the attribute
	 * '{@link servicepattern.Pattern#getTrainName <em>Train Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Train Name</em>'.
	 * @see servicepattern.Pattern#getTrainName()
	 * @see #getPattern()
	 * @generated
	 */
	EAttribute getPattern_TrainName();

	/**
	 * Returns the meta object for the attribute
	 * '{@link servicepattern.Pattern#getTrainClass <em>Train Class</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Train Class</em>'.
	 * @see servicepattern.Pattern#getTrainClass()
	 * @see #getPattern()
	 * @generated
	 */
	EAttribute getPattern_TrainClass();

	/**
	 * Returns the meta object for the attribute
	 * '{@link servicepattern.Pattern#getLine <em>Line</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Line</em>'.
	 * @see servicepattern.Pattern#getLine()
	 * @see #getPattern()
	 * @generated
	 */
	EAttribute getPattern_Line();

	/**
	 * Returns the meta object for the attribute
	 * '{@link servicepattern.Pattern#getDriver <em>Driver</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Driver</em>'.
	 * @see servicepattern.Pattern#getDriver()
	 * @see #getPattern()
	 * @generated
	 */
	EAttribute getPattern_Driver();

	/**
	 * Returns the meta object for the attribute
	 * '{@link servicepattern.Pattern#getStart <em>Start</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Start</em>'.
	 * @see servicepattern.Pattern#getStart()
	 * @see #getPattern()
	 * @generated
	 */
	EAttribute getPattern_Start();

	/**
	 * Returns the meta object for the attribute
	 * '{@link servicepattern.Pattern#getColor <em>Color</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Color</em>'.
	 * @see servicepattern.Pattern#getColor()
	 * @see #getPattern()
	 * @generated
	 */
	EAttribute getPattern_Color();

	/**
	 * Returns the meta object for the attribute
	 * '{@link servicepattern.Pattern#getReliability <em>Reliability</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Reliability</em>'.
	 * @see servicepattern.Pattern#getReliability()
	 * @see #getPattern()
	 * @generated
	 */
	EAttribute getPattern_Reliability();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ServicepatternFactory getServicepatternFactory();

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
		 * The meta object literal for the '{@link servicepattern.impl.LibraryImpl
		 * <em>Library</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see servicepattern.impl.LibraryImpl
		 * @see servicepattern.impl.ServicepatternPackageImpl#getLibrary()
		 * @generated
		 */
		EClass LIBRARY = eINSTANCE.getLibrary();

		/**
		 * The meta object literal for the '<em><b>Patterns</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LIBRARY__PATTERNS = eINSTANCE.getLibrary_Patterns();

		/**
		 * The meta object literal for the '{@link servicepattern.impl.PatternImpl
		 * <em>Pattern</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see servicepattern.impl.PatternImpl
		 * @see servicepattern.impl.ServicepatternPackageImpl#getPattern()
		 * @generated
		 */
		EClass PATTERN = eINSTANCE.getPattern();

		/**
		 * The meta object literal for the '<em><b>Train Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PATTERN__TRAIN_NAME = eINSTANCE.getPattern_TrainName();

		/**
		 * The meta object literal for the '<em><b>Train Class</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PATTERN__TRAIN_CLASS = eINSTANCE.getPattern_TrainClass();

		/**
		 * The meta object literal for the '<em><b>Line</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PATTERN__LINE = eINSTANCE.getPattern_Line();

		/**
		 * The meta object literal for the '<em><b>Driver</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PATTERN__DRIVER = eINSTANCE.getPattern_Driver();

		/**
		 * The meta object literal for the '<em><b>Start</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PATTERN__START = eINSTANCE.getPattern_Start();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PATTERN__COLOR = eINSTANCE.getPattern_Color();

		/**
		 * The meta object literal for the '<em><b>Reliability</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PATTERN__RELIABILITY = eINSTANCE.getPattern_Reliability();

	}

} // ServicepatternPackage
