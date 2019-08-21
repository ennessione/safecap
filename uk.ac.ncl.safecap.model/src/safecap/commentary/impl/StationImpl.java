/**
 */
package safecap.commentary.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import safecap.commentary.Commentary;
import safecap.commentary.CommentaryPackage;
import safecap.commentary.OrientableCommentary;
import safecap.commentary.Station;
import safecap.impl.LabeledImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Station</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.commentary.impl.StationImpl#getAngle <em>Angle</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StationImpl extends LabeledImpl implements Station {
	/**
	 * The default value of the '{@link #getAngle() <em>Angle</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAngle()
	 * @generated
	 * @ordered
	 */
	protected static final int ANGLE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getAngle() <em>Angle</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAngle()
	 * @generated
	 * @ordered
	 */
	protected int angle = ANGLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected StationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CommentaryPackage.Literals.STATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getAngle() {
		return angle;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setAngle(int newAngle) {
		final int oldAngle = angle;
		angle = newAngle;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, CommentaryPackage.STATION__ANGLE, oldAngle, angle));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case CommentaryPackage.STATION__ANGLE:
			return getAngle();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case CommentaryPackage.STATION__ANGLE:
			setAngle((Integer) newValue);
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
		case CommentaryPackage.STATION__ANGLE:
			setAngle(ANGLE_EDEFAULT);
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
		case CommentaryPackage.STATION__ANGLE:
			return angle != ANGLE_EDEFAULT;
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
		if (baseClass == Commentary.class) {
			switch (derivedFeatureID) {
			default:
				return -1;
			}
		}
		if (baseClass == OrientableCommentary.class) {
			switch (derivedFeatureID) {
			case CommentaryPackage.STATION__ANGLE:
				return CommentaryPackage.ORIENTABLE_COMMENTARY__ANGLE;
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
		if (baseClass == Commentary.class) {
			switch (baseFeatureID) {
			default:
				return -1;
			}
		}
		if (baseClass == OrientableCommentary.class) {
			switch (baseFeatureID) {
			case CommentaryPackage.ORIENTABLE_COMMENTARY__ANGLE:
				return CommentaryPackage.STATION__ANGLE;
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
		result.append(" (angle: ");
		result.append(angle);
		result.append(')');
		return result.toString();
	}

} // StationImpl
