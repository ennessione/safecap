/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import safecap.schema.HeightMap;
import safecap.schema.HeightPoint;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.NodeRole;
import safecap.schema.PointRole;
import safecap.schema.RouteSpeed;
import safecap.schema.Schema;
import safecap.schema.SchemaFactory;
import safecap.schema.SchemaPackage;
import safecap.schema.Segment;
import safecap.schema.SegmentRole;
import safecap.schema.SubSchemaNode;
import safecap.schema.SubSchemaPath;
import safecap.schema.TerminalNode;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class SchemaFactoryImpl extends EFactoryImpl implements SchemaFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static SchemaFactory init() {
		try {
			final SchemaFactory theSchemaFactory = (SchemaFactory) EPackage.Registry.INSTANCE.getEFactory(SchemaPackage.eNS_URI);
			if (theSchemaFactory != null) {
				return theSchemaFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SchemaFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public SchemaFactoryImpl() {
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
		case SchemaPackage.SCHEMA:
			return createSchema();
		case SchemaPackage.SEGMENT:
			return createSegment();
		case SchemaPackage.NODE:
			return createNode();
		case SchemaPackage.TERMINAL_NODE:
			return createTerminalNode();
		case SchemaPackage.HEIGHT_MAP:
			return createHeightMap();
		case SchemaPackage.HEIGHT_POINT:
			return createHeightPoint();
		case SchemaPackage.ROUTE_SPEED:
			return createRouteSpeed();
		case SchemaPackage.SUB_SCHEMA_NODE:
			return createSubSchemaNode();
		case SchemaPackage.SUB_SCHEMA_PATH:
			return createSubSchemaPath();
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
		case SchemaPackage.NODE_KIND:
			return createNodeKindFromString(eDataType, initialValue);
		case SchemaPackage.NODE_ROLE:
			return createNodeRoleFromString(eDataType, initialValue);
		case SchemaPackage.SEGMENT_ROLE:
			return createSegmentRoleFromString(eDataType, initialValue);
		case SchemaPackage.POINT_ROLE:
			return createPointRoleFromString(eDataType, initialValue);
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
		case SchemaPackage.NODE_KIND:
			return convertNodeKindToString(eDataType, instanceValue);
		case SchemaPackage.NODE_ROLE:
			return convertNodeRoleToString(eDataType, instanceValue);
		case SchemaPackage.SEGMENT_ROLE:
			return convertSegmentRoleToString(eDataType, instanceValue);
		case SchemaPackage.POINT_ROLE:
			return convertPointRoleToString(eDataType, instanceValue);
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
	public Schema createSchema() {
		final SchemaImpl schema = new SchemaImpl();
		return schema;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Segment createSegment() {
		final SegmentImpl segment = new SegmentImpl();
		return segment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Node createNode() {
		final NodeImpl node = new NodeImpl();
		return node;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TerminalNode createTerminalNode() {
		final TerminalNodeImpl terminalNode = new TerminalNodeImpl();
		return terminalNode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public HeightMap createHeightMap() {
		final HeightMapImpl heightMap = new HeightMapImpl();
		return heightMap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public HeightPoint createHeightPoint() {
		final HeightPointImpl heightPoint = new HeightPointImpl();
		return heightPoint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public RouteSpeed createRouteSpeed() {
		final RouteSpeedImpl routeSpeed = new RouteSpeedImpl();
		return routeSpeed;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SubSchemaNode createSubSchemaNode() {
		final SubSchemaNodeImpl subSchemaNode = new SubSchemaNodeImpl();
		return subSchemaNode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SubSchemaPath createSubSchemaPath() {
		final SubSchemaPathImpl subSchemaPath = new SubSchemaPathImpl();
		return subSchemaPath;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NodeKind createNodeKindFromString(EDataType eDataType, String initialValue) {
		final NodeKind result = NodeKind.get(initialValue);
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
	public String convertNodeKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NodeRole createNodeRoleFromString(EDataType eDataType, String initialValue) {
		final NodeRole result = NodeRole.get(initialValue);
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
	public String convertNodeRoleToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SegmentRole createSegmentRoleFromString(EDataType eDataType, String initialValue) {
		final SegmentRole result = SegmentRole.get(initialValue);
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
	public String convertSegmentRoleToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PointRole createPointRoleFromString(EDataType eDataType, String initialValue) {
		final PointRole result = PointRole.get(initialValue);
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
	public String convertPointRoleToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SchemaPackage getSchemaPackage() {
		return (SchemaPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SchemaPackage getPackage() {
		return SchemaPackage.eINSTANCE;
	}

} // SchemaFactoryImpl
