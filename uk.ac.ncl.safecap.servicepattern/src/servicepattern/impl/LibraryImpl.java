/**
 */
package servicepattern.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import servicepattern.Library;
import servicepattern.Pattern;
import servicepattern.ServicepatternPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Library</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link servicepattern.impl.LibraryImpl#getPatterns
 * <em>Patterns</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LibraryImpl extends EObjectImpl implements Library {
	/**
	 * The cached value of the '{@link #getPatterns() <em>Patterns</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPatterns()
	 * @generated
	 * @ordered
	 */
	protected EList<Pattern> patterns;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected LibraryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ServicepatternPackage.Literals.LIBRARY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Pattern> getPatterns() {
		if (patterns == null) {
			patterns = new EObjectContainmentEList<>(Pattern.class, this, ServicepatternPackage.LIBRARY__PATTERNS);
		}
		return patterns;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ServicepatternPackage.LIBRARY__PATTERNS:
			return ((InternalEList<?>) getPatterns()).basicRemove(otherEnd, msgs);
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
		case ServicepatternPackage.LIBRARY__PATTERNS:
			return getPatterns();
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
		case ServicepatternPackage.LIBRARY__PATTERNS:
			getPatterns().clear();
			getPatterns().addAll((Collection<? extends Pattern>) newValue);
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
		case ServicepatternPackage.LIBRARY__PATTERNS:
			getPatterns().clear();
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
		case ServicepatternPackage.LIBRARY__PATTERNS:
			return patterns != null && !patterns.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // LibraryImpl
