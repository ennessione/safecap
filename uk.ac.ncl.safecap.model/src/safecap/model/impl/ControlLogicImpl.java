package safecap.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.model.ControlLogic;
import safecap.model.Line;
import safecap.model.ModelPackage;
import safecap.model.Point;
import safecap.model.Rule;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Control
 * Logic</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.model.impl.ControlLogicImpl#getLine <em>Line</em>}</li>
 * <li>{@link safecap.model.impl.ControlLogicImpl#getAspects
 * <em>Aspects</em>}</li>
 * <li>{@link safecap.model.impl.ControlLogicImpl#getRule <em>Rule</em>}</li>
 * <li>{@link safecap.model.impl.ControlLogicImpl#getNormal
 * <em>Normal</em>}</li>
 * <li>{@link safecap.model.impl.ControlLogicImpl#getReverse
 * <em>Reverse</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ControlLogicImpl extends EObjectImpl implements ControlLogic {
	/**
	 * The cached value of the '{@link #getLine() <em>Line</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLine()
	 * @generated
	 * @ordered
	 */
	protected Line line;

	/**
	 * The default value of the '{@link #getAspects() <em>Aspects</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAspects()
	 * @generated
	 * @ordered
	 */
	protected static final int ASPECTS_EDEFAULT = 2;

	/**
	 * The cached value of the '{@link #getAspects() <em>Aspects</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAspects()
	 * @generated
	 * @ordered
	 */
	protected int aspects = ASPECTS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected EList<Rule> rule;

	/**
	 * The cached value of the '{@link #getNormal() <em>Normal</em>}' reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNormal()
	 * @generated
	 * @ordered
	 */
	protected EList<Point> normal;

	/**
	 * The cached value of the '{@link #getReverse() <em>Reverse</em>}' reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getReverse()
	 * @generated
	 * @ordered
	 */
	protected EList<Point> reverse;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ControlLogicImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.CONTROL_LOGIC;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Line getLine() {
		if (line != null && ((EObject) line).eIsProxy()) {
			final InternalEObject oldLine = (InternalEObject) line;
			line = (Line) eResolveProxy(oldLine);
			if (line != oldLine) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.CONTROL_LOGIC__LINE, oldLine, line));
				}
			}
		}
		return line;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Line basicGetLine() {
		return line;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLine(Line newLine) {
		final Line oldLine = line;
		line = newLine;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CONTROL_LOGIC__LINE, oldLine, line));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getAspects() {
		return aspects;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setAspects(int newAspects) {
		final int oldAspects = aspects;
		aspects = newAspects;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CONTROL_LOGIC__ASPECTS, oldAspects, aspects));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Rule> getRule() {
		if (rule == null) {
			rule = new EObjectContainmentEList<>(Rule.class, this, ModelPackage.CONTROL_LOGIC__RULE);
		}
		return rule;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Point> getNormal() {
		if (normal == null) {
			normal = new EObjectResolvingEList<>(Point.class, this, ModelPackage.CONTROL_LOGIC__NORMAL);
		}
		return normal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Point> getReverse() {
		if (reverse == null) {
			reverse = new EObjectResolvingEList<>(Point.class, this, ModelPackage.CONTROL_LOGIC__REVERSE);
		}
		return reverse;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.CONTROL_LOGIC__RULE:
			return ((InternalEList<?>) getRule()).basicRemove(otherEnd, msgs);
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
		case ModelPackage.CONTROL_LOGIC__LINE:
			if (resolve) {
				return getLine();
			}
			return basicGetLine();
		case ModelPackage.CONTROL_LOGIC__ASPECTS:
			return getAspects();
		case ModelPackage.CONTROL_LOGIC__RULE:
			return getRule();
		case ModelPackage.CONTROL_LOGIC__NORMAL:
			return getNormal();
		case ModelPackage.CONTROL_LOGIC__REVERSE:
			return getReverse();
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
		case ModelPackage.CONTROL_LOGIC__LINE:
			setLine((Line) newValue);
			return;
		case ModelPackage.CONTROL_LOGIC__ASPECTS:
			setAspects((Integer) newValue);
			return;
		case ModelPackage.CONTROL_LOGIC__RULE:
			getRule().clear();
			getRule().addAll((Collection<? extends Rule>) newValue);
			return;
		case ModelPackage.CONTROL_LOGIC__NORMAL:
			getNormal().clear();
			getNormal().addAll((Collection<? extends Point>) newValue);
			return;
		case ModelPackage.CONTROL_LOGIC__REVERSE:
			getReverse().clear();
			getReverse().addAll((Collection<? extends Point>) newValue);
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
		case ModelPackage.CONTROL_LOGIC__LINE:
			setLine((Line) null);
			return;
		case ModelPackage.CONTROL_LOGIC__ASPECTS:
			setAspects(ASPECTS_EDEFAULT);
			return;
		case ModelPackage.CONTROL_LOGIC__RULE:
			getRule().clear();
			return;
		case ModelPackage.CONTROL_LOGIC__NORMAL:
			getNormal().clear();
			return;
		case ModelPackage.CONTROL_LOGIC__REVERSE:
			getReverse().clear();
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
		case ModelPackage.CONTROL_LOGIC__LINE:
			return line != null;
		case ModelPackage.CONTROL_LOGIC__ASPECTS:
			return aspects != ASPECTS_EDEFAULT;
		case ModelPackage.CONTROL_LOGIC__RULE:
			return rule != null && !rule.isEmpty();
		case ModelPackage.CONTROL_LOGIC__NORMAL:
			return normal != null && !normal.isEmpty();
		case ModelPackage.CONTROL_LOGIC__REVERSE:
			return reverse != null && !reverse.isEmpty();
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
		result.append(" (aspects: ");
		result.append(aspects);
		result.append(')');
		return result.toString();
	}

} // ControlLogicImpl
