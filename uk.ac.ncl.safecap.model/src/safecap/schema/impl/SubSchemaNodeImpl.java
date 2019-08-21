/**
 */
package safecap.schema.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.schema.SchemaPackage;
import safecap.schema.SubSchemaNode;
import safecap.schema.SubSchemaPath;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Sub
 * Schema Node</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.impl.SubSchemaNodeImpl#getPaths
 * <em>Paths</em>}</li>
 * <li>{@link safecap.schema.impl.SubSchemaNodeImpl#getChildren
 * <em>Children</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SubSchemaNodeImpl extends NodeImpl implements SubSchemaNode {
	/**
	 * The cached value of the '{@link #getPaths() <em>Paths</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPaths()
	 * @generated
	 * @ordered
	 */
	protected EList<SubSchemaPath> paths;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' attribute
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<String> children;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SubSchemaNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SchemaPackage.Literals.SUB_SCHEMA_NODE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<SubSchemaPath> getPaths() {
		if (paths == null) {
			paths = new EObjectContainmentEList<>(SubSchemaPath.class, this, SchemaPackage.SUB_SCHEMA_NODE__PATHS);
		}
		return paths;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<String> getChildren() {
		if (children == null) {
			children = new EDataTypeUniqueEList<>(String.class, this, SchemaPackage.SUB_SCHEMA_NODE__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case SchemaPackage.SUB_SCHEMA_NODE__PATHS:
			return ((InternalEList<?>) getPaths()).basicRemove(otherEnd, msgs);
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
		case SchemaPackage.SUB_SCHEMA_NODE__PATHS:
			return getPaths();
		case SchemaPackage.SUB_SCHEMA_NODE__CHILDREN:
			return getChildren();
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
		case SchemaPackage.SUB_SCHEMA_NODE__PATHS:
			getPaths().clear();
			getPaths().addAll((Collection<? extends SubSchemaPath>) newValue);
			return;
		case SchemaPackage.SUB_SCHEMA_NODE__CHILDREN:
			getChildren().clear();
			getChildren().addAll((Collection<? extends String>) newValue);
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
		case SchemaPackage.SUB_SCHEMA_NODE__PATHS:
			getPaths().clear();
			return;
		case SchemaPackage.SUB_SCHEMA_NODE__CHILDREN:
			getChildren().clear();
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
		case SchemaPackage.SUB_SCHEMA_NODE__PATHS:
			return paths != null && !paths.isEmpty();
		case SchemaPackage.SUB_SCHEMA_NODE__CHILDREN:
			return children != null && !children.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (children: ");
		result.append(children);
		result.append(')');
		return result.toString();
	}

} // SubSchemaNodeImpl
