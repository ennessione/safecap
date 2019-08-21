/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.trackside.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.trackside.Equipment;
import safecap.trackside.NodeWire;
import safecap.trackside.SubEquipment;
import safecap.trackside.SubWire;
import safecap.trackside.Trackside;
import safecap.trackside.TracksidePackage;
import safecap.trackside.Wire;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Trackside</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.trackside.impl.TracksideImpl#getEquipment
 * <em>Equipment</em>}</li>
 * <li>{@link safecap.trackside.impl.TracksideImpl#getWires <em>Wires</em>}</li>
 * <li>{@link safecap.trackside.impl.TracksideImpl#getNodewires
 * <em>Nodewires</em>}</li>
 * <li>{@link safecap.trackside.impl.TracksideImpl#getSubwires
 * <em>Subwires</em>}</li>
 * <li>{@link safecap.trackside.impl.TracksideImpl#getSubequipment
 * <em>Subequipment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TracksideImpl extends EObjectImpl implements Trackside {
	/**
	 * The cached value of the '{@link #getEquipment() <em>Equipment</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEquipment()
	 * @generated
	 * @ordered
	 */
	protected EList<Equipment> equipment;

	/**
	 * The cached value of the '{@link #getWires() <em>Wires</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getWires()
	 * @generated
	 * @ordered
	 */
	protected EList<Wire> wires;

	/**
	 * The cached value of the '{@link #getNodewires() <em>Nodewires</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNodewires()
	 * @generated
	 * @ordered
	 */
	protected EList<NodeWire> nodewires;

	/**
	 * The cached value of the '{@link #getSubwires() <em>Subwires</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSubwires()
	 * @generated
	 * @ordered
	 */
	protected EList<SubWire> subwires;

	/**
	 * The cached value of the '{@link #getSubequipment() <em>Subequipment</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSubequipment()
	 * @generated
	 * @ordered
	 */
	protected EList<SubEquipment> subequipment;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TracksideImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracksidePackage.Literals.TRACKSIDE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Equipment> getEquipment() {
		if (equipment == null) {
			equipment = new EObjectContainmentEList<>(Equipment.class, this, TracksidePackage.TRACKSIDE__EQUIPMENT);
		}
		return equipment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Wire> getWires() {
		if (wires == null) {
			wires = new EObjectContainmentEList<>(Wire.class, this, TracksidePackage.TRACKSIDE__WIRES);
		}
		return wires;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<NodeWire> getNodewires() {
		if (nodewires == null) {
			nodewires = new EObjectContainmentEList<>(NodeWire.class, this, TracksidePackage.TRACKSIDE__NODEWIRES);
		}
		return nodewires;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<SubWire> getSubwires() {
		if (subwires == null) {
			subwires = new EObjectContainmentEList<>(SubWire.class, this, TracksidePackage.TRACKSIDE__SUBWIRES);
		}
		return subwires;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<SubEquipment> getSubequipment() {
		if (subequipment == null) {
			subequipment = new EObjectContainmentEList<>(SubEquipment.class, this, TracksidePackage.TRACKSIDE__SUBEQUIPMENT);
		}
		return subequipment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case TracksidePackage.TRACKSIDE__EQUIPMENT:
			return ((InternalEList<?>) getEquipment()).basicRemove(otherEnd, msgs);
		case TracksidePackage.TRACKSIDE__WIRES:
			return ((InternalEList<?>) getWires()).basicRemove(otherEnd, msgs);
		case TracksidePackage.TRACKSIDE__NODEWIRES:
			return ((InternalEList<?>) getNodewires()).basicRemove(otherEnd, msgs);
		case TracksidePackage.TRACKSIDE__SUBWIRES:
			return ((InternalEList<?>) getSubwires()).basicRemove(otherEnd, msgs);
		case TracksidePackage.TRACKSIDE__SUBEQUIPMENT:
			return ((InternalEList<?>) getSubequipment()).basicRemove(otherEnd, msgs);
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
		case TracksidePackage.TRACKSIDE__EQUIPMENT:
			return getEquipment();
		case TracksidePackage.TRACKSIDE__WIRES:
			return getWires();
		case TracksidePackage.TRACKSIDE__NODEWIRES:
			return getNodewires();
		case TracksidePackage.TRACKSIDE__SUBWIRES:
			return getSubwires();
		case TracksidePackage.TRACKSIDE__SUBEQUIPMENT:
			return getSubequipment();
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
		case TracksidePackage.TRACKSIDE__EQUIPMENT:
			getEquipment().clear();
			getEquipment().addAll((Collection<? extends Equipment>) newValue);
			return;
		case TracksidePackage.TRACKSIDE__WIRES:
			getWires().clear();
			getWires().addAll((Collection<? extends Wire>) newValue);
			return;
		case TracksidePackage.TRACKSIDE__NODEWIRES:
			getNodewires().clear();
			getNodewires().addAll((Collection<? extends NodeWire>) newValue);
			return;
		case TracksidePackage.TRACKSIDE__SUBWIRES:
			getSubwires().clear();
			getSubwires().addAll((Collection<? extends SubWire>) newValue);
			return;
		case TracksidePackage.TRACKSIDE__SUBEQUIPMENT:
			getSubequipment().clear();
			getSubequipment().addAll((Collection<? extends SubEquipment>) newValue);
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
		case TracksidePackage.TRACKSIDE__EQUIPMENT:
			getEquipment().clear();
			return;
		case TracksidePackage.TRACKSIDE__WIRES:
			getWires().clear();
			return;
		case TracksidePackage.TRACKSIDE__NODEWIRES:
			getNodewires().clear();
			return;
		case TracksidePackage.TRACKSIDE__SUBWIRES:
			getSubwires().clear();
			return;
		case TracksidePackage.TRACKSIDE__SUBEQUIPMENT:
			getSubequipment().clear();
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
		case TracksidePackage.TRACKSIDE__EQUIPMENT:
			return equipment != null && !equipment.isEmpty();
		case TracksidePackage.TRACKSIDE__WIRES:
			return wires != null && !wires.isEmpty();
		case TracksidePackage.TRACKSIDE__NODEWIRES:
			return nodewires != null && !nodewires.isEmpty();
		case TracksidePackage.TRACKSIDE__SUBWIRES:
			return subwires != null && !subwires.isEmpty();
		case TracksidePackage.TRACKSIDE__SUBEQUIPMENT:
			return subequipment != null && !subequipment.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // TracksideImpl
