/**
 */
package safecap.commentary.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import safecap.commentary.Bridge;
import safecap.commentary.CommentaryFactory;
import safecap.commentary.CommentaryPackage;
import safecap.commentary.Comments;
import safecap.commentary.OrientableCommentary;
import safecap.commentary.Road;
import safecap.commentary.Station;
import safecap.commentary.Tunnel;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class CommentaryFactoryImpl extends EFactoryImpl implements CommentaryFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static CommentaryFactory init() {
		try {
			final CommentaryFactory theCommentaryFactory = (CommentaryFactory) EPackage.Registry.INSTANCE
					.getEFactory(CommentaryPackage.eNS_URI);
			if (theCommentaryFactory != null) {
				return theCommentaryFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CommentaryFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public CommentaryFactoryImpl() {
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
		case CommentaryPackage.COMMENTS:
			return createComments();
		case CommentaryPackage.ORIENTABLE_COMMENTARY:
			return createOrientableCommentary();
		case CommentaryPackage.STATION:
			return createStation();
		case CommentaryPackage.BRIDGE:
			return createBridge();
		case CommentaryPackage.ROAD:
			return createRoad();
		case CommentaryPackage.TUNNEL:
			return createTunnel();
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
	public OrientableCommentary createOrientableCommentary() {
		final OrientableCommentaryImpl orientableCommentary = new OrientableCommentaryImpl();
		return orientableCommentary;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Station createStation() {
		final StationImpl station = new StationImpl();
		return station;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Bridge createBridge() {
		final BridgeImpl bridge = new BridgeImpl();
		return bridge;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Road createRoad() {
		final RoadImpl road = new RoadImpl();
		return road;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Tunnel createTunnel() {
		final TunnelImpl tunnel = new TunnelImpl();
		return tunnel;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Comments createComments() {
		final CommentsImpl comments = new CommentsImpl();
		return comments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CommentaryPackage getCommentaryPackage() {
		return (CommentaryPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CommentaryPackage getPackage() {
		return CommentaryPackage.eINSTANCE;
	}

} // CommentaryFactoryImpl
