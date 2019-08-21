/**
 */
package safecap.derived.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import safecap.derived.DerivedPackage;
import safecap.derived.MergedAmbit;
import safecap.model.Ambit;
import safecap.model.ModelPackage;
import safecap.schema.Segment;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Merged
 * Ambit</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.derived.impl.MergedAmbitImpl#getSegments
 * <em>Segments</em>}</li>
 * <li>{@link safecap.derived.impl.MergedAmbitImpl#getAmbits
 * <em>Ambits</em>}</li>
 * <li>{@link safecap.derived.impl.MergedAmbitImpl#isComposed
 * <em>Composed</em>}</li>
 * <li>{@link safecap.derived.impl.MergedAmbitImpl#isDisjoint
 * <em>Disjoint</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class MergedAmbitImpl extends DerivedElementImpl implements MergedAmbit {
	/**
	 * The cached value of the '{@link #getSegments() <em>Segments</em>}' reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSegments()
	 * @generated
	 * @ordered
	 */
	protected EList<Segment> segments;

	/**
	 * The cached value of the '{@link #getAmbits() <em>Ambits</em>}' reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAmbits()
	 * @generated
	 * @ordered
	 */
	protected EList<Ambit> ambits;

	/**
	 * The default value of the '{@link #isComposed() <em>Composed</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isComposed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean COMPOSED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isComposed() <em>Composed</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isComposed()
	 * @generated
	 * @ordered
	 */
	protected boolean composed = COMPOSED_EDEFAULT;

	/**
	 * The default value of the '{@link #isDisjoint() <em>Disjoint</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isDisjoint()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DISJOINT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDisjoint() <em>Disjoint</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isDisjoint()
	 * @generated
	 * @ordered
	 */
	protected boolean disjoint = DISJOINT_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MergedAmbitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DerivedPackage.Literals.MERGED_AMBIT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Ambit> getAmbits() {
		if (ambits == null) {
			ambits = new EObjectResolvingEList<>(Ambit.class, this, DerivedPackage.MERGED_AMBIT__AMBITS);
		}
		return ambits;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isComposed() {
		return composed;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setComposed(boolean newComposed) {
		final boolean oldComposed = composed;
		composed = newComposed;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DerivedPackage.MERGED_AMBIT__COMPOSED, oldComposed, composed));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isDisjoint() {
		return disjoint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDisjoint(boolean newDisjoint) {
		final boolean oldDisjoint = disjoint;
		disjoint = newDisjoint;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DerivedPackage.MERGED_AMBIT__DISJOINT, oldDisjoint, disjoint));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Segment> getSegments() {
		if (segments == null) {
			segments = new EObjectResolvingEList<>(Segment.class, this, DerivedPackage.MERGED_AMBIT__SEGMENTS);
		}
		return segments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DerivedPackage.MERGED_AMBIT__SEGMENTS:
			return getSegments();
		case DerivedPackage.MERGED_AMBIT__AMBITS:
			return getAmbits();
		case DerivedPackage.MERGED_AMBIT__COMPOSED:
			return isComposed();
		case DerivedPackage.MERGED_AMBIT__DISJOINT:
			return isDisjoint();
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
		case DerivedPackage.MERGED_AMBIT__SEGMENTS:
			getSegments().clear();
			getSegments().addAll((Collection<? extends Segment>) newValue);
			return;
		case DerivedPackage.MERGED_AMBIT__AMBITS:
			getAmbits().clear();
			getAmbits().addAll((Collection<? extends Ambit>) newValue);
			return;
		case DerivedPackage.MERGED_AMBIT__COMPOSED:
			setComposed((Boolean) newValue);
			return;
		case DerivedPackage.MERGED_AMBIT__DISJOINT:
			setDisjoint((Boolean) newValue);
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
		case DerivedPackage.MERGED_AMBIT__SEGMENTS:
			getSegments().clear();
			return;
		case DerivedPackage.MERGED_AMBIT__AMBITS:
			getAmbits().clear();
			return;
		case DerivedPackage.MERGED_AMBIT__COMPOSED:
			setComposed(COMPOSED_EDEFAULT);
			return;
		case DerivedPackage.MERGED_AMBIT__DISJOINT:
			setDisjoint(DISJOINT_EDEFAULT);
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
		case DerivedPackage.MERGED_AMBIT__SEGMENTS:
			return segments != null && !segments.isEmpty();
		case DerivedPackage.MERGED_AMBIT__AMBITS:
			return ambits != null && !ambits.isEmpty();
		case DerivedPackage.MERGED_AMBIT__COMPOSED:
			return composed != COMPOSED_EDEFAULT;
		case DerivedPackage.MERGED_AMBIT__DISJOINT:
			return disjoint != DISJOINT_EDEFAULT;
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
		if (baseClass == Ambit.class) {
			switch (derivedFeatureID) {
			case DerivedPackage.MERGED_AMBIT__SEGMENTS:
				return ModelPackage.AMBIT__SEGMENTS;
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
		if (baseClass == Ambit.class) {
			switch (baseFeatureID) {
			case ModelPackage.AMBIT__SEGMENTS:
				return DerivedPackage.MERGED_AMBIT__SEGMENTS;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public String toString() {
		return getLabel();
	}

} // MergedAmbitImpl
