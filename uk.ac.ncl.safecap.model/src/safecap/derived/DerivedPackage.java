/**
 */
package safecap.derived;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import safecap.SafecapPackage;

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
 * @see safecap.derived.DerivedFactory
 * @model kind="package"
 * @generated
 */
public interface DerivedPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "derived";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "uk.ac.ncl.safecap.emf.derived";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "uk.ac.ncl.safecap.emf.derived";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	DerivedPackage eINSTANCE = safecap.derived.impl.DerivedPackageImpl.init();

	/**
	 * The meta object id for the '{@link safecap.derived.impl.DerivedElementImpl
	 * <em>Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.derived.impl.DerivedElementImpl
	 * @see safecap.derived.impl.DerivedPackageImpl#getDerivedElement()
	 * @generated
	 */
	int DERIVED_ELEMENT = 1;

	/**
	 * The meta object id for the '{@link safecap.derived.impl.MergedPointImpl
	 * <em>Merged Point</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.derived.impl.MergedPointImpl
	 * @see safecap.derived.impl.DerivedPackageImpl#getMergedPoint()
	 * @generated
	 */
	int MERGED_POINT = 2;

	/**
	 * The meta object id for the '{@link safecap.derived.impl.MergedAmbitImpl
	 * <em>Merged Ambit</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.derived.impl.MergedAmbitImpl
	 * @see safecap.derived.impl.DerivedPackageImpl#getMergedAmbit()
	 * @generated
	 */
	int MERGED_AMBIT = 3;

	/**
	 * The meta object id for the '{@link safecap.derived.impl.MergedJunctionImpl
	 * <em>Merged Junction</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.derived.impl.MergedJunctionImpl
	 * @see safecap.derived.impl.DerivedPackageImpl#getMergedJunction()
	 * @generated
	 */
	int MERGED_JUNCTION = 4;

	/**
	 * The meta object id for the '{@link safecap.derived.impl.MergedSectionImpl
	 * <em>Merged Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see safecap.derived.impl.MergedSectionImpl
	 * @see safecap.derived.impl.DerivedPackageImpl#getMergedSection()
	 * @generated
	 */
	int MERGED_SECTION = 5;

	/**
	 * The meta object id for the '{@link safecap.derived.impl.DerivedImpl
	 * <em>Derived</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.derived.impl.DerivedImpl
	 * @see safecap.derived.impl.DerivedPackageImpl#getDerived()
	 * @generated
	 */
	int DERIVED = 0;

	/**
	 * The feature id for the '<em><b>Mergedpoints</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DERIVED__MERGEDPOINTS = 0;

	/**
	 * The number of structural features of the '<em>Derived</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DERIVED_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DERIVED_ELEMENT__LABEL = SafecapPackage.LABELED__LABEL;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DERIVED_ELEMENT__RUNTIME_ATTRIBUTES = SafecapPackage.LABELED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DERIVED_ELEMENT__ATTRIBUTES = SafecapPackage.LABELED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Codes</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DERIVED_ELEMENT__CODES = SafecapPackage.LABELED_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Element</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DERIVED_ELEMENT_FEATURE_COUNT = SafecapPackage.LABELED_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_POINT__LABEL = DERIVED_ELEMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_POINT__RUNTIME_ATTRIBUTES = DERIVED_ELEMENT__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_POINT__ATTRIBUTES = DERIVED_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Codes</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_POINT__CODES = DERIVED_ELEMENT__CODES;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_POINT__NODE = DERIVED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_POINT__RULE = DERIVED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Points</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_POINT__POINTS = DERIVED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Merged Point</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_POINT_FEATURE_COUNT = DERIVED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_AMBIT__LABEL = DERIVED_ELEMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_AMBIT__RUNTIME_ATTRIBUTES = DERIVED_ELEMENT__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_AMBIT__ATTRIBUTES = DERIVED_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Codes</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_AMBIT__CODES = DERIVED_ELEMENT__CODES;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_AMBIT__SEGMENTS = DERIVED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ambits</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_AMBIT__AMBITS = DERIVED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Composed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_AMBIT__COMPOSED = DERIVED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Disjoint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_AMBIT__DISJOINT = DERIVED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Merged Ambit</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_AMBIT_FEATURE_COUNT = DERIVED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_JUNCTION__LABEL = MERGED_AMBIT__LABEL;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_JUNCTION__RUNTIME_ATTRIBUTES = MERGED_AMBIT__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_JUNCTION__ATTRIBUTES = MERGED_AMBIT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Codes</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_JUNCTION__CODES = MERGED_AMBIT__CODES;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_JUNCTION__SEGMENTS = MERGED_AMBIT__SEGMENTS;

	/**
	 * The feature id for the '<em><b>Ambits</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_JUNCTION__AMBITS = MERGED_AMBIT__AMBITS;

	/**
	 * The feature id for the '<em><b>Composed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_JUNCTION__COMPOSED = MERGED_AMBIT__COMPOSED;

	/**
	 * The feature id for the '<em><b>Disjoint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_JUNCTION__DISJOINT = MERGED_AMBIT__DISJOINT;

	/**
	 * The feature id for the '<em><b>Points</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_JUNCTION__POINTS = MERGED_AMBIT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Merged Junction</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_JUNCTION_FEATURE_COUNT = MERGED_AMBIT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_SECTION__LABEL = MERGED_AMBIT__LABEL;

	/**
	 * The feature id for the '<em><b>Runtime Attributes</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_SECTION__RUNTIME_ATTRIBUTES = MERGED_AMBIT__RUNTIME_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_SECTION__ATTRIBUTES = MERGED_AMBIT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Codes</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_SECTION__CODES = MERGED_AMBIT__CODES;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_SECTION__SEGMENTS = MERGED_AMBIT__SEGMENTS;

	/**
	 * The feature id for the '<em><b>Ambits</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_SECTION__AMBITS = MERGED_AMBIT__AMBITS;

	/**
	 * The feature id for the '<em><b>Composed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_SECTION__COMPOSED = MERGED_AMBIT__COMPOSED;

	/**
	 * The feature id for the '<em><b>Disjoint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_SECTION__DISJOINT = MERGED_AMBIT__DISJOINT;

	/**
	 * The number of structural features of the '<em>Merged Section</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERGED_SECTION_FEATURE_COUNT = MERGED_AMBIT_FEATURE_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link safecap.derived.DerivedElement
	 * <em>Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Element</em>'.
	 * @see safecap.derived.DerivedElement
	 * @generated
	 */
	EClass getDerivedElement();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link safecap.derived.DerivedElement#getCodes <em>Codes</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Codes</em>'.
	 * @see safecap.derived.DerivedElement#getCodes()
	 * @see #getDerivedElement()
	 * @generated
	 */
	EAttribute getDerivedElement_Codes();

	/**
	 * Returns the meta object for class '{@link safecap.derived.MergedPoint
	 * <em>Merged Point</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Merged Point</em>'.
	 * @see safecap.derived.MergedPoint
	 * @generated
	 */
	EClass getMergedPoint();

	/**
	 * Returns the meta object for the reference list
	 * '{@link safecap.derived.MergedPoint#getPoints <em>Points</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Points</em>'.
	 * @see safecap.derived.MergedPoint#getPoints()
	 * @see #getMergedPoint()
	 * @generated
	 */
	EReference getMergedPoint_Points();

	/**
	 * Returns the meta object for class '{@link safecap.derived.MergedAmbit
	 * <em>Merged Ambit</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Merged Ambit</em>'.
	 * @see safecap.derived.MergedAmbit
	 * @generated
	 */
	EClass getMergedAmbit();

	/**
	 * Returns the meta object for the reference list
	 * '{@link safecap.derived.MergedAmbit#getAmbits <em>Ambits</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Ambits</em>'.
	 * @see safecap.derived.MergedAmbit#getAmbits()
	 * @see #getMergedAmbit()
	 * @generated
	 */
	EReference getMergedAmbit_Ambits();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.derived.MergedAmbit#isComposed <em>Composed</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Composed</em>'.
	 * @see safecap.derived.MergedAmbit#isComposed()
	 * @see #getMergedAmbit()
	 * @generated
	 */
	EAttribute getMergedAmbit_Composed();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.derived.MergedAmbit#isDisjoint <em>Disjoint</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Disjoint</em>'.
	 * @see safecap.derived.MergedAmbit#isDisjoint()
	 * @see #getMergedAmbit()
	 * @generated
	 */
	EAttribute getMergedAmbit_Disjoint();

	/**
	 * Returns the meta object for class '{@link safecap.derived.MergedJunction
	 * <em>Merged Junction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Merged Junction</em>'.
	 * @see safecap.derived.MergedJunction
	 * @generated
	 */
	EClass getMergedJunction();

	/**
	 * Returns the meta object for class '{@link safecap.derived.MergedSection
	 * <em>Merged Section</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Merged Section</em>'.
	 * @see safecap.derived.MergedSection
	 * @generated
	 */
	EClass getMergedSection();

	/**
	 * Returns the meta object for class '{@link safecap.derived.Derived
	 * <em>Derived</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Derived</em>'.
	 * @see safecap.derived.Derived
	 * @generated
	 */
	EClass getDerived();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.derived.Derived#getMergedpoints <em>Mergedpoints</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Mergedpoints</em>'.
	 * @see safecap.derived.Derived#getMergedpoints()
	 * @see #getDerived()
	 * @generated
	 */
	EReference getDerived_Mergedpoints();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DerivedFactory getDerivedFactory();

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
		 * The meta object literal for the
		 * '{@link safecap.derived.impl.DerivedElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.derived.impl.DerivedElementImpl
		 * @see safecap.derived.impl.DerivedPackageImpl#getDerivedElement()
		 * @generated
		 */
		EClass DERIVED_ELEMENT = eINSTANCE.getDerivedElement();

		/**
		 * The meta object literal for the '<em><b>Codes</b></em>' attribute list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DERIVED_ELEMENT__CODES = eINSTANCE.getDerivedElement_Codes();

		/**
		 * The meta object literal for the '{@link safecap.derived.impl.MergedPointImpl
		 * <em>Merged Point</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.derived.impl.MergedPointImpl
		 * @see safecap.derived.impl.DerivedPackageImpl#getMergedPoint()
		 * @generated
		 */
		EClass MERGED_POINT = eINSTANCE.getMergedPoint();

		/**
		 * The meta object literal for the '<em><b>Points</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MERGED_POINT__POINTS = eINSTANCE.getMergedPoint_Points();

		/**
		 * The meta object literal for the '{@link safecap.derived.impl.MergedAmbitImpl
		 * <em>Merged Ambit</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.derived.impl.MergedAmbitImpl
		 * @see safecap.derived.impl.DerivedPackageImpl#getMergedAmbit()
		 * @generated
		 */
		EClass MERGED_AMBIT = eINSTANCE.getMergedAmbit();

		/**
		 * The meta object literal for the '<em><b>Ambits</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MERGED_AMBIT__AMBITS = eINSTANCE.getMergedAmbit_Ambits();

		/**
		 * The meta object literal for the '<em><b>Composed</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MERGED_AMBIT__COMPOSED = eINSTANCE.getMergedAmbit_Composed();

		/**
		 * The meta object literal for the '<em><b>Disjoint</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MERGED_AMBIT__DISJOINT = eINSTANCE.getMergedAmbit_Disjoint();

		/**
		 * The meta object literal for the
		 * '{@link safecap.derived.impl.MergedJunctionImpl <em>Merged Junction</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.derived.impl.MergedJunctionImpl
		 * @see safecap.derived.impl.DerivedPackageImpl#getMergedJunction()
		 * @generated
		 */
		EClass MERGED_JUNCTION = eINSTANCE.getMergedJunction();

		/**
		 * The meta object literal for the
		 * '{@link safecap.derived.impl.MergedSectionImpl <em>Merged Section</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.derived.impl.MergedSectionImpl
		 * @see safecap.derived.impl.DerivedPackageImpl#getMergedSection()
		 * @generated
		 */
		EClass MERGED_SECTION = eINSTANCE.getMergedSection();

		/**
		 * The meta object literal for the '{@link safecap.derived.impl.DerivedImpl
		 * <em>Derived</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.derived.impl.DerivedImpl
		 * @see safecap.derived.impl.DerivedPackageImpl#getDerived()
		 * @generated
		 */
		EClass DERIVED = eINSTANCE.getDerived();

		/**
		 * The meta object literal for the '<em><b>Mergedpoints</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference DERIVED__MERGEDPOINTS = eINSTANCE.getDerived_Mergedpoints();

	}

} // DerivedPackage
