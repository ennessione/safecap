/**
 */
package safecap.commentary;

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
 * @see safecap.commentary.CommentaryFactory
 * @model kind="package"
 * @generated
 */
public interface CommentaryPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "commentary";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "uk.ac.ncl.safecap.emf.commentary";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "uk.ac.ncl.safecap.emf.commentary";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	CommentaryPackage eINSTANCE = safecap.commentary.impl.CommentaryPackageImpl.init();

	/**
	 * The meta object id for the '{@link safecap.commentary.impl.CommentaryImpl
	 * <em>Commentary</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.commentary.impl.CommentaryImpl
	 * @see safecap.commentary.impl.CommentaryPackageImpl#getCommentary()
	 * @generated
	 */
	int COMMENTARY = 1;

	/**
	 * The meta object id for the
	 * '{@link safecap.commentary.impl.OrientableCommentaryImpl <em>Orientable
	 * Commentary</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.commentary.impl.OrientableCommentaryImpl
	 * @see safecap.commentary.impl.CommentaryPackageImpl#getOrientableCommentary()
	 * @generated
	 */
	int ORIENTABLE_COMMENTARY = 2;

	/**
	 * The meta object id for the '{@link safecap.commentary.impl.StationImpl
	 * <em>Station</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.commentary.impl.StationImpl
	 * @see safecap.commentary.impl.CommentaryPackageImpl#getStation()
	 * @generated
	 */
	int STATION = 3;

	/**
	 * The meta object id for the '{@link safecap.commentary.impl.BridgeImpl
	 * <em>Bridge</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.commentary.impl.BridgeImpl
	 * @see safecap.commentary.impl.CommentaryPackageImpl#getBridge()
	 * @generated
	 */
	int BRIDGE = 4;

	/**
	 * The meta object id for the '{@link safecap.commentary.impl.RoadImpl
	 * <em>Road</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.commentary.impl.RoadImpl
	 * @see safecap.commentary.impl.CommentaryPackageImpl#getRoad()
	 * @generated
	 */
	int ROAD = 5;

	/**
	 * The meta object id for the '{@link safecap.commentary.impl.TunnelImpl
	 * <em>Tunnel</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.commentary.impl.TunnelImpl
	 * @see safecap.commentary.impl.CommentaryPackageImpl#getTunnel()
	 * @generated
	 */
	int TUNNEL = 6;

	/**
	 * The meta object id for the '{@link safecap.commentary.impl.CommentsImpl
	 * <em>Comments</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see safecap.commentary.impl.CommentsImpl
	 * @see safecap.commentary.impl.CommentaryPackageImpl#getComments()
	 * @generated
	 */
	int COMMENTS = 0;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENTS__COMMENTS = 0;

	/**
	 * The number of structural features of the '<em>Comments</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENTS_FEATURE_COUNT = 1;

	/**
	 * The number of structural features of the '<em>Commentary</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENTARY_FEATURE_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Angle</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ORIENTABLE_COMMENTARY__ANGLE = COMMENTARY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Orientable Commentary</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ORIENTABLE_COMMENTARY_FEATURE_COUNT = COMMENTARY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATION__LABEL = SafecapPackage.LABELED__LABEL;

	/**
	 * The feature id for the '<em><b>Angle</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATION__ANGLE = SafecapPackage.LABELED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Station</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATION_FEATURE_COUNT = SafecapPackage.LABELED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Angle</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BRIDGE__ANGLE = ORIENTABLE_COMMENTARY__ANGLE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BRIDGE__LABEL = ORIENTABLE_COMMENTARY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Bridge</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BRIDGE_FEATURE_COUNT = ORIENTABLE_COMMENTARY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Angle</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROAD__ANGLE = ORIENTABLE_COMMENTARY__ANGLE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROAD__LABEL = ORIENTABLE_COMMENTARY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Road</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROAD_FEATURE_COUNT = ORIENTABLE_COMMENTARY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Angle</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TUNNEL__ANGLE = ORIENTABLE_COMMENTARY__ANGLE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TUNNEL__LABEL = ORIENTABLE_COMMENTARY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Tunnel</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TUNNEL_FEATURE_COUNT = ORIENTABLE_COMMENTARY_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link safecap.commentary.Commentary
	 * <em>Commentary</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Commentary</em>'.
	 * @see safecap.commentary.Commentary
	 * @generated
	 */
	EClass getCommentary();

	/**
	 * Returns the meta object for class
	 * '{@link safecap.commentary.OrientableCommentary <em>Orientable
	 * Commentary</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Orientable Commentary</em>'.
	 * @see safecap.commentary.OrientableCommentary
	 * @generated
	 */
	EClass getOrientableCommentary();

	/**
	 * Returns the meta object for the attribute
	 * '{@link safecap.commentary.OrientableCommentary#getAngle <em>Angle</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Angle</em>'.
	 * @see safecap.commentary.OrientableCommentary#getAngle()
	 * @see #getOrientableCommentary()
	 * @generated
	 */
	EAttribute getOrientableCommentary_Angle();

	/**
	 * Returns the meta object for class '{@link safecap.commentary.Station
	 * <em>Station</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Station</em>'.
	 * @see safecap.commentary.Station
	 * @generated
	 */
	EClass getStation();

	/**
	 * Returns the meta object for class '{@link safecap.commentary.Bridge
	 * <em>Bridge</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Bridge</em>'.
	 * @see safecap.commentary.Bridge
	 * @generated
	 */
	EClass getBridge();

	/**
	 * Returns the meta object for class '{@link safecap.commentary.Road
	 * <em>Road</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Road</em>'.
	 * @see safecap.commentary.Road
	 * @generated
	 */
	EClass getRoad();

	/**
	 * Returns the meta object for class '{@link safecap.commentary.Tunnel
	 * <em>Tunnel</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Tunnel</em>'.
	 * @see safecap.commentary.Tunnel
	 * @generated
	 */
	EClass getTunnel();

	/**
	 * Returns the meta object for class '{@link safecap.commentary.Comments
	 * <em>Comments</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Comments</em>'.
	 * @see safecap.commentary.Comments
	 * @generated
	 */
	EClass getComments();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link safecap.commentary.Comments#getComments <em>Comments</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Comments</em>'.
	 * @see safecap.commentary.Comments#getComments()
	 * @see #getComments()
	 * @generated
	 */
	EReference getComments_Comments();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CommentaryFactory getCommentaryFactory();

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
		 * '{@link safecap.commentary.impl.CommentaryImpl <em>Commentary</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.commentary.impl.CommentaryImpl
		 * @see safecap.commentary.impl.CommentaryPackageImpl#getCommentary()
		 * @generated
		 */
		EClass COMMENTARY = eINSTANCE.getCommentary();

		/**
		 * The meta object literal for the
		 * '{@link safecap.commentary.impl.OrientableCommentaryImpl <em>Orientable
		 * Commentary</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.commentary.impl.OrientableCommentaryImpl
		 * @see safecap.commentary.impl.CommentaryPackageImpl#getOrientableCommentary()
		 * @generated
		 */
		EClass ORIENTABLE_COMMENTARY = eINSTANCE.getOrientableCommentary();

		/**
		 * The meta object literal for the '<em><b>Angle</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ORIENTABLE_COMMENTARY__ANGLE = eINSTANCE.getOrientableCommentary_Angle();

		/**
		 * The meta object literal for the '{@link safecap.commentary.impl.StationImpl
		 * <em>Station</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.commentary.impl.StationImpl
		 * @see safecap.commentary.impl.CommentaryPackageImpl#getStation()
		 * @generated
		 */
		EClass STATION = eINSTANCE.getStation();

		/**
		 * The meta object literal for the '{@link safecap.commentary.impl.BridgeImpl
		 * <em>Bridge</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.commentary.impl.BridgeImpl
		 * @see safecap.commentary.impl.CommentaryPackageImpl#getBridge()
		 * @generated
		 */
		EClass BRIDGE = eINSTANCE.getBridge();

		/**
		 * The meta object literal for the '{@link safecap.commentary.impl.RoadImpl
		 * <em>Road</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.commentary.impl.RoadImpl
		 * @see safecap.commentary.impl.CommentaryPackageImpl#getRoad()
		 * @generated
		 */
		EClass ROAD = eINSTANCE.getRoad();

		/**
		 * The meta object literal for the '{@link safecap.commentary.impl.TunnelImpl
		 * <em>Tunnel</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.commentary.impl.TunnelImpl
		 * @see safecap.commentary.impl.CommentaryPackageImpl#getTunnel()
		 * @generated
		 */
		EClass TUNNEL = eINSTANCE.getTunnel();

		/**
		 * The meta object literal for the '{@link safecap.commentary.impl.CommentsImpl
		 * <em>Comments</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see safecap.commentary.impl.CommentsImpl
		 * @see safecap.commentary.impl.CommentaryPackageImpl#getComments()
		 * @generated
		 */
		EClass COMMENTS = eINSTANCE.getComments();

		/**
		 * The meta object literal for the '<em><b>Comments</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMMENTS__COMMENTS = eINSTANCE.getComments_Comments();

	}

} // CommentaryPackage
