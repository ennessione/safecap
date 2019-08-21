/**
 */
package safecap.config.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import safecap.config.ConfigPackage;
import safecap.config.NormalisationRule;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Normalisation Rule</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.config.impl.NormalisationRuleImpl#getPattern
 * <em>Pattern</em>}</li>
 * <li>{@link safecap.config.impl.NormalisationRuleImpl#getTemplate
 * <em>Template</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NormalisationRuleImpl extends EObjectImpl implements NormalisationRule {
	/**
	 * The default value of the '{@link #getPattern() <em>Pattern</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPattern()
	 * @generated
	 * @ordered
	 */
	protected static final String PATTERN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPattern() <em>Pattern</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPattern()
	 * @generated
	 * @ordered
	 */
	protected String pattern = PATTERN_EDEFAULT;

	/**
	 * The default value of the '{@link #getTemplate() <em>Template</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTemplate()
	 * @generated
	 * @ordered
	 */
	protected static final String TEMPLATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTemplate() <em>Template</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTemplate()
	 * @generated
	 * @ordered
	 */
	protected String template = TEMPLATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected NormalisationRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigPackage.Literals.NORMALISATION_RULE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getPattern() {
		return pattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setPattern(String newPattern) {
		final String oldPattern = pattern;
		pattern = newPattern;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.NORMALISATION_RULE__PATTERN, oldPattern, pattern));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getTemplate() {
		return template;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTemplate(String newTemplate) {
		final String oldTemplate = template;
		template = newTemplate;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.NORMALISATION_RULE__TEMPLATE, oldTemplate, template));
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
		case ConfigPackage.NORMALISATION_RULE__PATTERN:
			return getPattern();
		case ConfigPackage.NORMALISATION_RULE__TEMPLATE:
			return getTemplate();
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
		case ConfigPackage.NORMALISATION_RULE__PATTERN:
			setPattern((String) newValue);
			return;
		case ConfigPackage.NORMALISATION_RULE__TEMPLATE:
			setTemplate((String) newValue);
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
		case ConfigPackage.NORMALISATION_RULE__PATTERN:
			setPattern(PATTERN_EDEFAULT);
			return;
		case ConfigPackage.NORMALISATION_RULE__TEMPLATE:
			setTemplate(TEMPLATE_EDEFAULT);
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
		case ConfigPackage.NORMALISATION_RULE__PATTERN:
			return PATTERN_EDEFAULT == null ? pattern != null : !PATTERN_EDEFAULT.equals(pattern);
		case ConfigPackage.NORMALISATION_RULE__TEMPLATE:
			return TEMPLATE_EDEFAULT == null ? template != null : !TEMPLATE_EDEFAULT.equals(template);
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
		result.append(" (pattern: ");
		result.append(pattern);
		result.append(", template: ");
		result.append(template);
		result.append(')');
		return result.toString();
	}

} // NormalisationRuleImpl
