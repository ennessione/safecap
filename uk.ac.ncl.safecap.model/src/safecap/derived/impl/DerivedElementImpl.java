/**
 */
package safecap.derived.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.Extensible;
import safecap.SafecapPackage;
import safecap.TransientValues;
import safecap.derived.DerivedElement;
import safecap.derived.DerivedPackage;
import safecap.extension.ExtAttribute;
import safecap.impl.LabeledImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Element</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.derived.impl.DerivedElementImpl#getRuntimeAttributes
 * <em>Runtime Attributes</em>}</li>
 * <li>{@link safecap.derived.impl.DerivedElementImpl#getAttributes
 * <em>Attributes</em>}</li>
 * <li>{@link safecap.derived.impl.DerivedElementImpl#getCodes
 * <em>Codes</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DerivedElementImpl extends LabeledImpl implements DerivedElement {
	/**
	 * The cached value of the '{@link #getRuntimeAttributes() <em>Runtime
	 * Attributes</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRuntimeAttributes()
	 * @generated
	 * @ordered
	 */
	protected Map<String, Object> runtimeAttributes;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<ExtAttribute> attributes;

	/**
	 * The cached value of the '{@link #getCodes() <em>Codes</em>}' attribute list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCodes()
	 * @generated
	 * @ordered
	 */
	protected EList<String> codes;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected DerivedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DerivedPackage.Literals.DERIVED_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Map<String, Object> getRuntimeAttributes() {
		return runtimeAttributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setRuntimeAttributes(Map<String, Object> newRuntimeAttributes) {
		final Map<String, Object> oldRuntimeAttributes = runtimeAttributes;
		runtimeAttributes = newRuntimeAttributes;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DerivedPackage.DERIVED_ELEMENT__RUNTIME_ATTRIBUTES, oldRuntimeAttributes,
					runtimeAttributes));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<ExtAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList<>(ExtAttribute.class, this, DerivedPackage.DERIVED_ELEMENT__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<String> getCodes() {
		if (codes == null) {
			codes = new EDataTypeUniqueEList<>(String.class, this, DerivedPackage.DERIVED_ELEMENT__CODES);
		}
		return codes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DerivedPackage.DERIVED_ELEMENT__ATTRIBUTES:
			return ((InternalEList<?>) getAttributes()).basicRemove(otherEnd, msgs);
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
		case DerivedPackage.DERIVED_ELEMENT__RUNTIME_ATTRIBUTES:
			return getRuntimeAttributes();
		case DerivedPackage.DERIVED_ELEMENT__ATTRIBUTES:
			return getAttributes();
		case DerivedPackage.DERIVED_ELEMENT__CODES:
			return getCodes();
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
		case DerivedPackage.DERIVED_ELEMENT__RUNTIME_ATTRIBUTES:
			setRuntimeAttributes((Map<String, Object>) newValue);
			return;
		case DerivedPackage.DERIVED_ELEMENT__ATTRIBUTES:
			getAttributes().clear();
			getAttributes().addAll((Collection<? extends ExtAttribute>) newValue);
			return;
		case DerivedPackage.DERIVED_ELEMENT__CODES:
			getCodes().clear();
			getCodes().addAll((Collection<? extends String>) newValue);
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
		case DerivedPackage.DERIVED_ELEMENT__RUNTIME_ATTRIBUTES:
			setRuntimeAttributes((Map<String, Object>) null);
			return;
		case DerivedPackage.DERIVED_ELEMENT__ATTRIBUTES:
			getAttributes().clear();
			return;
		case DerivedPackage.DERIVED_ELEMENT__CODES:
			getCodes().clear();
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
		case DerivedPackage.DERIVED_ELEMENT__RUNTIME_ATTRIBUTES:
			return runtimeAttributes != null;
		case DerivedPackage.DERIVED_ELEMENT__ATTRIBUTES:
			return attributes != null && !attributes.isEmpty();
		case DerivedPackage.DERIVED_ELEMENT__CODES:
			return codes != null && !codes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == TransientValues.class) {
			switch (derivedFeatureID) {
			case DerivedPackage.DERIVED_ELEMENT__RUNTIME_ATTRIBUTES:
				return SafecapPackage.TRANSIENT_VALUES__RUNTIME_ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Extensible.class) {
			switch (derivedFeatureID) {
			case DerivedPackage.DERIVED_ELEMENT__ATTRIBUTES:
				return SafecapPackage.EXTENSIBLE__ATTRIBUTES;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == TransientValues.class) {
			switch (baseFeatureID) {
			case SafecapPackage.TRANSIENT_VALUES__RUNTIME_ATTRIBUTES:
				return DerivedPackage.DERIVED_ELEMENT__RUNTIME_ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Extensible.class) {
			switch (baseFeatureID) {
			case SafecapPackage.EXTENSIBLE__ATTRIBUTES:
				return DerivedPackage.DERIVED_ELEMENT__ATTRIBUTES;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (runtimeAttributes: ");
		result.append(runtimeAttributes);
		result.append(", codes: ");
		result.append(codes);
		result.append(')');
		return result.toString();
	}

} // DerivedElementImpl
