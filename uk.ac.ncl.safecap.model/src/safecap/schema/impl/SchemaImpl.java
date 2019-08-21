/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.schema.Node;
import safecap.schema.Schema;
import safecap.schema.SchemaPackage;
import safecap.schema.Segment;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Schema</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.impl.SchemaImpl#getNodes <em>Nodes</em>}</li>
 * <li>{@link safecap.schema.impl.SchemaImpl#getSegments <em>Segments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SchemaImpl extends EObjectImpl implements Schema {
	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> nodes;

	/**
	 * The cached value of the '{@link #getSegments() <em>Segments</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSegments()
	 * @generated
	 * @ordered
	 */
	protected EList<Segment> segments;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SchemaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SchemaPackage.Literals.SCHEMA;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Node> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList<>(Node.class, this, SchemaPackage.SCHEMA__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Segment> getSegments() {
		if (segments == null) {
			segments = new EObjectContainmentEList<>(Segment.class, this, SchemaPackage.SCHEMA__SEGMENTS);
		}
		return segments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case SchemaPackage.SCHEMA__NODES:
			return ((InternalEList<?>) getNodes()).basicRemove(otherEnd, msgs);
		case SchemaPackage.SCHEMA__SEGMENTS:
			return ((InternalEList<?>) getSegments()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case SchemaPackage.SCHEMA__NODES:
			return getNodes();
		case SchemaPackage.SCHEMA__SEGMENTS:
			return getSegments();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case SchemaPackage.SCHEMA__NODES:
			getNodes().clear();
			getNodes().addAll((Collection<? extends Node>) newValue);
			return;
		case SchemaPackage.SCHEMA__SEGMENTS:
			getSegments().clear();
			getSegments().addAll((Collection<? extends Segment>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case SchemaPackage.SCHEMA__NODES:
			getNodes().clear();
			return;
		case SchemaPackage.SCHEMA__SEGMENTS:
			getSegments().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case SchemaPackage.SCHEMA__NODES:
			return nodes != null && !nodes.isEmpty();
		case SchemaPackage.SCHEMA__SEGMENTS:
			return segments != null && !segments.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // SchemaImpl
