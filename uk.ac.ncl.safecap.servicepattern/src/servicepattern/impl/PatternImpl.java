/**
 */
package servicepattern.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import servicepattern.Pattern;
import servicepattern.ServicepatternPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Pattern</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link servicepattern.impl.PatternImpl#getTrainName <em>Train
 * Name</em>}</li>
 * <li>{@link servicepattern.impl.PatternImpl#getTrainClass <em>Train
 * Class</em>}</li>
 * <li>{@link servicepattern.impl.PatternImpl#getLine <em>Line</em>}</li>
 * <li>{@link servicepattern.impl.PatternImpl#getDriver <em>Driver</em>}</li>
 * <li>{@link servicepattern.impl.PatternImpl#getStart <em>Start</em>}</li>
 * <li>{@link servicepattern.impl.PatternImpl#getReliability
 * <em>Reliability</em>}</li>
 * <li>{@link servicepattern.impl.PatternImpl#getColor <em>Color</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PatternImpl extends EObjectImpl implements Pattern {
	/**
	 * The default value of the '{@link #getTrainName() <em>Train Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTrainName()
	 * @generated
	 * @ordered
	 */
	protected static final String TRAIN_NAME_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getTrainName() <em>Train Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTrainName()
	 * @generated
	 * @ordered
	 */
	protected String trainName = TRAIN_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getTrainClass() <em>Train Class</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTrainClass()
	 * @generated
	 * @ordered
	 */
	protected static final String TRAIN_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTrainClass() <em>Train Class</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTrainClass()
	 * @generated
	 * @ordered
	 */
	protected String trainClass = TRAIN_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getLine() <em>Line</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLine()
	 * @generated
	 * @ordered
	 */
	protected static final String LINE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLine() <em>Line</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLine()
	 * @generated
	 * @ordered
	 */
	protected String line = LINE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDriver() <em>Driver</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDriver()
	 * @generated
	 * @ordered
	 */
	protected static final String DRIVER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDriver() <em>Driver</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDriver()
	 * @generated
	 * @ordered
	 */
	protected String driver = DRIVER_EDEFAULT;

	/**
	 * The default value of the '{@link #getStart() <em>Start</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStart()
	 * @generated
	 * @ordered
	 */
	protected static final int START_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStart() <em>Start</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStart()
	 * @generated
	 * @ordered
	 */
	protected int start = START_EDEFAULT;

	/**
	 * The default value of the '{@link #getReliability() <em>Reliability</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getReliability()
	 * @generated
	 * @ordered
	 */
	protected static final float RELIABILITY_EDEFAULT = 100.0F;

	/**
	 * The cached value of the '{@link #getReliability() <em>Reliability</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getReliability()
	 * @generated
	 * @ordered
	 */
	protected float reliability = RELIABILITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getColor() <em>Color</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected static final String COLOR_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getColor() <em>Color</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected String color = COLOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected PatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ServicepatternPackage.Literals.PATTERN;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getTrainName() {
		return trainName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTrainName(String newTrainName) {
		final String oldTrainName = trainName;
		trainName = newTrainName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ServicepatternPackage.PATTERN__TRAIN_NAME, oldTrainName, trainName));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getTrainClass() {
		return trainClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTrainClass(String newTrainClass) {
		final String oldTrainClass = trainClass;
		trainClass = newTrainClass;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ServicepatternPackage.PATTERN__TRAIN_CLASS, oldTrainClass, trainClass));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getLine() {
		return line;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLine(String newLine) {
		final String oldLine = line;
		line = newLine;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ServicepatternPackage.PATTERN__LINE, oldLine, line));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getDriver() {
		return driver;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDriver(String newDriver) {
		final String oldDriver = driver;
		driver = newDriver;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ServicepatternPackage.PATTERN__DRIVER, oldDriver, driver));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getStart() {
		return start;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setStart(int newStart) {
		final int oldStart = start;
		start = newStart;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ServicepatternPackage.PATTERN__START, oldStart, start));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getColor() {
		return color;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setColor(String newColor) {
		final String oldColor = color;
		color = newColor;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ServicepatternPackage.PATTERN__COLOR, oldColor, color));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public float getReliability() {
		return reliability;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setReliability(float newReliability) {
		final float oldReliability = reliability;
		reliability = newReliability;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ServicepatternPackage.PATTERN__RELIABILITY, oldReliability, reliability));
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
		case ServicepatternPackage.PATTERN__TRAIN_NAME:
			return getTrainName();
		case ServicepatternPackage.PATTERN__TRAIN_CLASS:
			return getTrainClass();
		case ServicepatternPackage.PATTERN__LINE:
			return getLine();
		case ServicepatternPackage.PATTERN__DRIVER:
			return getDriver();
		case ServicepatternPackage.PATTERN__START:
			return getStart();
		case ServicepatternPackage.PATTERN__RELIABILITY:
			return getReliability();
		case ServicepatternPackage.PATTERN__COLOR:
			return getColor();
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
		case ServicepatternPackage.PATTERN__TRAIN_NAME:
			setTrainName((String) newValue);
			return;
		case ServicepatternPackage.PATTERN__TRAIN_CLASS:
			setTrainClass((String) newValue);
			return;
		case ServicepatternPackage.PATTERN__LINE:
			setLine((String) newValue);
			return;
		case ServicepatternPackage.PATTERN__DRIVER:
			setDriver((String) newValue);
			return;
		case ServicepatternPackage.PATTERN__START:
			setStart((Integer) newValue);
			return;
		case ServicepatternPackage.PATTERN__RELIABILITY:
			setReliability((Float) newValue);
			return;
		case ServicepatternPackage.PATTERN__COLOR:
			setColor((String) newValue);
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
		case ServicepatternPackage.PATTERN__TRAIN_NAME:
			setTrainName(TRAIN_NAME_EDEFAULT);
			return;
		case ServicepatternPackage.PATTERN__TRAIN_CLASS:
			setTrainClass(TRAIN_CLASS_EDEFAULT);
			return;
		case ServicepatternPackage.PATTERN__LINE:
			setLine(LINE_EDEFAULT);
			return;
		case ServicepatternPackage.PATTERN__DRIVER:
			setDriver(DRIVER_EDEFAULT);
			return;
		case ServicepatternPackage.PATTERN__START:
			setStart(START_EDEFAULT);
			return;
		case ServicepatternPackage.PATTERN__RELIABILITY:
			setReliability(RELIABILITY_EDEFAULT);
			return;
		case ServicepatternPackage.PATTERN__COLOR:
			setColor(COLOR_EDEFAULT);
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
		case ServicepatternPackage.PATTERN__TRAIN_NAME:
			return TRAIN_NAME_EDEFAULT == null ? trainName != null : !TRAIN_NAME_EDEFAULT.equals(trainName);
		case ServicepatternPackage.PATTERN__TRAIN_CLASS:
			return TRAIN_CLASS_EDEFAULT == null ? trainClass != null : !TRAIN_CLASS_EDEFAULT.equals(trainClass);
		case ServicepatternPackage.PATTERN__LINE:
			return LINE_EDEFAULT == null ? line != null : !LINE_EDEFAULT.equals(line);
		case ServicepatternPackage.PATTERN__DRIVER:
			return DRIVER_EDEFAULT == null ? driver != null : !DRIVER_EDEFAULT.equals(driver);
		case ServicepatternPackage.PATTERN__START:
			return start != START_EDEFAULT;
		case ServicepatternPackage.PATTERN__RELIABILITY:
			return reliability != RELIABILITY_EDEFAULT;
		case ServicepatternPackage.PATTERN__COLOR:
			return COLOR_EDEFAULT == null ? color != null : !COLOR_EDEFAULT.equals(color);
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

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (trainName: ");
		result.append(trainName);
		result.append(", trainClass: ");
		result.append(trainClass);
		result.append(", line: ");
		result.append(line);
		result.append(", driver: ");
		result.append(driver);
		result.append(", start: ");
		result.append(start);
		result.append(", reliability: ");
		result.append(reliability);
		result.append(", color: ");
		result.append(color);
		result.append(')');
		return result.toString();
	}

} // PatternImpl
