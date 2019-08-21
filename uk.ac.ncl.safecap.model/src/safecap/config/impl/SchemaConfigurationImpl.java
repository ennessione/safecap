/**
 */
package safecap.config.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.config.AutoOverlapsConfiguration;
import safecap.config.ConfigPackage;
import safecap.config.MergedAmbitsConfiguration;
import safecap.config.MergedPointsConfiguration;
import safecap.config.NormalisationRule;
import safecap.config.SchemaConfiguration;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Schema
 * Configuration</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.config.impl.SchemaConfigurationImpl#isWnSignal <em>Wn
 * Signal</em>}</li>
 * <li>{@link safecap.config.impl.SchemaConfigurationImpl#isWnTrack <em>Wn
 * Track</em>}</li>
 * <li>{@link safecap.config.impl.SchemaConfigurationImpl#getMergedambits
 * <em>Mergedambits</em>}</li>
 * <li>{@link safecap.config.impl.SchemaConfigurationImpl#getMergedpoints
 * <em>Mergedpoints</em>}</li>
 * <li>{@link safecap.config.impl.SchemaConfigurationImpl#getNormpoint
 * <em>Normpoint</em>}</li>
 * <li>{@link safecap.config.impl.SchemaConfigurationImpl#getNormtrack
 * <em>Normtrack</em>}</li>
 * <li>{@link safecap.config.impl.SchemaConfigurationImpl#getNormsignal
 * <em>Normsignal</em>}</li>
 * <li>{@link safecap.config.impl.SchemaConfigurationImpl#getOverlaps
 * <em>Overlaps</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SchemaConfigurationImpl extends EObjectImpl implements SchemaConfiguration {
	/**
	 * The default value of the '{@link #isWnSignal() <em>Wn Signal</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isWnSignal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WN_SIGNAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWnSignal() <em>Wn Signal</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isWnSignal()
	 * @generated
	 * @ordered
	 */
	protected boolean wnSignal = WN_SIGNAL_EDEFAULT;

	/**
	 * The default value of the '{@link #isWnTrack() <em>Wn Track</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isWnTrack()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WN_TRACK_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWnTrack() <em>Wn Track</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isWnTrack()
	 * @generated
	 * @ordered
	 */
	protected boolean wnTrack = WN_TRACK_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMergedambits() <em>Mergedambits</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMergedambits()
	 * @generated
	 * @ordered
	 */
	protected MergedAmbitsConfiguration mergedambits;

	/**
	 * The cached value of the '{@link #getMergedpoints() <em>Mergedpoints</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMergedpoints()
	 * @generated
	 * @ordered
	 */
	protected MergedPointsConfiguration mergedpoints;

	/**
	 * The cached value of the '{@link #getNormpoint() <em>Normpoint</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNormpoint()
	 * @generated
	 * @ordered
	 */
	protected NormalisationRule normpoint;

	/**
	 * The cached value of the '{@link #getNormtrack() <em>Normtrack</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNormtrack()
	 * @generated
	 * @ordered
	 */
	protected NormalisationRule normtrack;

	/**
	 * The cached value of the '{@link #getNormsignal() <em>Normsignal</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNormsignal()
	 * @generated
	 * @ordered
	 */
	protected NormalisationRule normsignal;

	/**
	 * The cached value of the '{@link #getOverlaps() <em>Overlaps</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOverlaps()
	 * @generated
	 * @ordered
	 */
	protected AutoOverlapsConfiguration overlaps;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SchemaConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigPackage.Literals.SCHEMA_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isWnSignal() {
		return wnSignal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setWnSignal(boolean newWnSignal) {
		final boolean oldWnSignal = wnSignal;
		wnSignal = newWnSignal;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.SCHEMA_CONFIGURATION__WN_SIGNAL, oldWnSignal, wnSignal));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isWnTrack() {
		return wnTrack;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setWnTrack(boolean newWnTrack) {
		final boolean oldWnTrack = wnTrack;
		wnTrack = newWnTrack;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.SCHEMA_CONFIGURATION__WN_TRACK, oldWnTrack, wnTrack));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MergedAmbitsConfiguration getMergedambits() {
		if (mergedambits != null && ((EObject) mergedambits).eIsProxy()) {
			final InternalEObject oldMergedambits = (InternalEObject) mergedambits;
			mergedambits = (MergedAmbitsConfiguration) eResolveProxy(oldMergedambits);
			if (mergedambits != oldMergedambits) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConfigPackage.SCHEMA_CONFIGURATION__MERGEDAMBITS,
							oldMergedambits, mergedambits));
				}
			}
		}
		return mergedambits;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MergedAmbitsConfiguration basicGetMergedambits() {
		return mergedambits;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMergedambits(MergedAmbitsConfiguration newMergedambits) {
		final MergedAmbitsConfiguration oldMergedambits = mergedambits;
		mergedambits = newMergedambits;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.SCHEMA_CONFIGURATION__MERGEDAMBITS, oldMergedambits,
					mergedambits));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MergedPointsConfiguration getMergedpoints() {
		if (mergedpoints != null && ((EObject) mergedpoints).eIsProxy()) {
			final InternalEObject oldMergedpoints = (InternalEObject) mergedpoints;
			mergedpoints = (MergedPointsConfiguration) eResolveProxy(oldMergedpoints);
			if (mergedpoints != oldMergedpoints) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConfigPackage.SCHEMA_CONFIGURATION__MERGEDPOINTS,
							oldMergedpoints, mergedpoints));
				}
			}
		}
		return mergedpoints;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MergedPointsConfiguration basicGetMergedpoints() {
		return mergedpoints;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMergedpoints(MergedPointsConfiguration newMergedpoints) {
		final MergedPointsConfiguration oldMergedpoints = mergedpoints;
		mergedpoints = newMergedpoints;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.SCHEMA_CONFIGURATION__MERGEDPOINTS, oldMergedpoints,
					mergedpoints));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NormalisationRule getNormpoint() {
		if (normpoint != null && ((EObject) normpoint).eIsProxy()) {
			final InternalEObject oldNormpoint = (InternalEObject) normpoint;
			normpoint = (NormalisationRule) eResolveProxy(oldNormpoint);
			if (normpoint != oldNormpoint) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConfigPackage.SCHEMA_CONFIGURATION__NORMPOINT, oldNormpoint,
							normpoint));
				}
			}
		}
		return normpoint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NormalisationRule basicGetNormpoint() {
		return normpoint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setNormpoint(NormalisationRule newNormpoint) {
		final NormalisationRule oldNormpoint = normpoint;
		normpoint = newNormpoint;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.SCHEMA_CONFIGURATION__NORMPOINT, oldNormpoint, normpoint));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NormalisationRule getNormtrack() {
		if (normtrack != null && ((EObject) normtrack).eIsProxy()) {
			final InternalEObject oldNormtrack = (InternalEObject) normtrack;
			normtrack = (NormalisationRule) eResolveProxy(oldNormtrack);
			if (normtrack != oldNormtrack) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConfigPackage.SCHEMA_CONFIGURATION__NORMTRACK, oldNormtrack,
							normtrack));
				}
			}
		}
		return normtrack;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NormalisationRule basicGetNormtrack() {
		return normtrack;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setNormtrack(NormalisationRule newNormtrack) {
		final NormalisationRule oldNormtrack = normtrack;
		normtrack = newNormtrack;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.SCHEMA_CONFIGURATION__NORMTRACK, oldNormtrack, normtrack));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NormalisationRule getNormsignal() {
		if (normsignal != null && ((EObject) normsignal).eIsProxy()) {
			final InternalEObject oldNormsignal = (InternalEObject) normsignal;
			normsignal = (NormalisationRule) eResolveProxy(oldNormsignal);
			if (normsignal != oldNormsignal) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConfigPackage.SCHEMA_CONFIGURATION__NORMSIGNAL, oldNormsignal,
							normsignal));
				}
			}
		}
		return normsignal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NormalisationRule basicGetNormsignal() {
		return normsignal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setNormsignal(NormalisationRule newNormsignal) {
		final NormalisationRule oldNormsignal = normsignal;
		normsignal = newNormsignal;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.SCHEMA_CONFIGURATION__NORMSIGNAL, oldNormsignal,
					normsignal));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public AutoOverlapsConfiguration getOverlaps() {
		if (overlaps != null && ((EObject) overlaps).eIsProxy()) {
			final InternalEObject oldOverlaps = (InternalEObject) overlaps;
			overlaps = (AutoOverlapsConfiguration) eResolveProxy(oldOverlaps);
			if (overlaps != oldOverlaps) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConfigPackage.SCHEMA_CONFIGURATION__OVERLAPS, oldOverlaps,
							overlaps));
				}
			}
		}
		return overlaps;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AutoOverlapsConfiguration basicGetOverlaps() {
		return overlaps;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOverlaps(AutoOverlapsConfiguration newOverlaps) {
		final AutoOverlapsConfiguration oldOverlaps = overlaps;
		overlaps = newOverlaps;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.SCHEMA_CONFIGURATION__OVERLAPS, oldOverlaps, overlaps));
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
		case ConfigPackage.SCHEMA_CONFIGURATION__WN_SIGNAL:
			return isWnSignal();
		case ConfigPackage.SCHEMA_CONFIGURATION__WN_TRACK:
			return isWnTrack();
		case ConfigPackage.SCHEMA_CONFIGURATION__MERGEDAMBITS:
			if (resolve) {
				return getMergedambits();
			}
			return basicGetMergedambits();
		case ConfigPackage.SCHEMA_CONFIGURATION__MERGEDPOINTS:
			if (resolve) {
				return getMergedpoints();
			}
			return basicGetMergedpoints();
		case ConfigPackage.SCHEMA_CONFIGURATION__NORMPOINT:
			if (resolve) {
				return getNormpoint();
			}
			return basicGetNormpoint();
		case ConfigPackage.SCHEMA_CONFIGURATION__NORMTRACK:
			if (resolve) {
				return getNormtrack();
			}
			return basicGetNormtrack();
		case ConfigPackage.SCHEMA_CONFIGURATION__NORMSIGNAL:
			if (resolve) {
				return getNormsignal();
			}
			return basicGetNormsignal();
		case ConfigPackage.SCHEMA_CONFIGURATION__OVERLAPS:
			if (resolve) {
				return getOverlaps();
			}
			return basicGetOverlaps();
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
		case ConfigPackage.SCHEMA_CONFIGURATION__WN_SIGNAL:
			setWnSignal((Boolean) newValue);
			return;
		case ConfigPackage.SCHEMA_CONFIGURATION__WN_TRACK:
			setWnTrack((Boolean) newValue);
			return;
		case ConfigPackage.SCHEMA_CONFIGURATION__MERGEDAMBITS:
			setMergedambits((MergedAmbitsConfiguration) newValue);
			return;
		case ConfigPackage.SCHEMA_CONFIGURATION__MERGEDPOINTS:
			setMergedpoints((MergedPointsConfiguration) newValue);
			return;
		case ConfigPackage.SCHEMA_CONFIGURATION__NORMPOINT:
			setNormpoint((NormalisationRule) newValue);
			return;
		case ConfigPackage.SCHEMA_CONFIGURATION__NORMTRACK:
			setNormtrack((NormalisationRule) newValue);
			return;
		case ConfigPackage.SCHEMA_CONFIGURATION__NORMSIGNAL:
			setNormsignal((NormalisationRule) newValue);
			return;
		case ConfigPackage.SCHEMA_CONFIGURATION__OVERLAPS:
			setOverlaps((AutoOverlapsConfiguration) newValue);
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
		case ConfigPackage.SCHEMA_CONFIGURATION__WN_SIGNAL:
			setWnSignal(WN_SIGNAL_EDEFAULT);
			return;
		case ConfigPackage.SCHEMA_CONFIGURATION__WN_TRACK:
			setWnTrack(WN_TRACK_EDEFAULT);
			return;
		case ConfigPackage.SCHEMA_CONFIGURATION__MERGEDAMBITS:
			setMergedambits((MergedAmbitsConfiguration) null);
			return;
		case ConfigPackage.SCHEMA_CONFIGURATION__MERGEDPOINTS:
			setMergedpoints((MergedPointsConfiguration) null);
			return;
		case ConfigPackage.SCHEMA_CONFIGURATION__NORMPOINT:
			setNormpoint((NormalisationRule) null);
			return;
		case ConfigPackage.SCHEMA_CONFIGURATION__NORMTRACK:
			setNormtrack((NormalisationRule) null);
			return;
		case ConfigPackage.SCHEMA_CONFIGURATION__NORMSIGNAL:
			setNormsignal((NormalisationRule) null);
			return;
		case ConfigPackage.SCHEMA_CONFIGURATION__OVERLAPS:
			setOverlaps((AutoOverlapsConfiguration) null);
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
		case ConfigPackage.SCHEMA_CONFIGURATION__WN_SIGNAL:
			return wnSignal != WN_SIGNAL_EDEFAULT;
		case ConfigPackage.SCHEMA_CONFIGURATION__WN_TRACK:
			return wnTrack != WN_TRACK_EDEFAULT;
		case ConfigPackage.SCHEMA_CONFIGURATION__MERGEDAMBITS:
			return mergedambits != null;
		case ConfigPackage.SCHEMA_CONFIGURATION__MERGEDPOINTS:
			return mergedpoints != null;
		case ConfigPackage.SCHEMA_CONFIGURATION__NORMPOINT:
			return normpoint != null;
		case ConfigPackage.SCHEMA_CONFIGURATION__NORMTRACK:
			return normtrack != null;
		case ConfigPackage.SCHEMA_CONFIGURATION__NORMSIGNAL:
			return normsignal != null;
		case ConfigPackage.SCHEMA_CONFIGURATION__OVERLAPS:
			return overlaps != null;
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
		result.append(" (wnSignal: ");
		result.append(wnSignal);
		result.append(", wnTrack: ");
		result.append(wnTrack);
		result.append(')');
		return result.toString();
	}

} // SchemaConfigurationImpl
