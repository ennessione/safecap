/**
 */
package servicepattern;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Pattern</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link servicepattern.Pattern#getTrainName <em>Train Name</em>}</li>
 * <li>{@link servicepattern.Pattern#getTrainClass <em>Train Class</em>}</li>
 * <li>{@link servicepattern.Pattern#getLine <em>Line</em>}</li>
 * <li>{@link servicepattern.Pattern#getDriver <em>Driver</em>}</li>
 * <li>{@link servicepattern.Pattern#getStart <em>Start</em>}</li>
 * <li>{@link servicepattern.Pattern#getReliability <em>Reliability</em>}</li>
 * <li>{@link servicepattern.Pattern#getColor <em>Color</em>}</li>
 * </ul>
 * </p>
 *
 * @see servicepattern.ServicepatternPackage#getPattern()
 * @model
 * @generated
 */
public interface Pattern extends EObject {
	/**
	 * Returns the value of the '<em><b>Train Name</b></em>' attribute. The default
	 * value is <code>""</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Train Name</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Train Name</em>' attribute.
	 * @see #setTrainName(String)
	 * @see servicepattern.ServicepatternPackage#getPattern_TrainName()
	 * @model default=""
	 * @generated
	 */
	String getTrainName();

	/**
	 * Sets the value of the '{@link servicepattern.Pattern#getTrainName <em>Train
	 * Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Train Name</em>' attribute.
	 * @see #getTrainName()
	 * @generated
	 */
	void setTrainName(String value);

	/**
	 * Returns the value of the '<em><b>Train Class</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Train Class</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Train Class</em>' attribute.
	 * @see #setTrainClass(String)
	 * @see servicepattern.ServicepatternPackage#getPattern_TrainClass()
	 * @model
	 * @generated
	 */
	String getTrainClass();

	/**
	 * Sets the value of the '{@link servicepattern.Pattern#getTrainClass <em>Train
	 * Class</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Train Class</em>' attribute.
	 * @see #getTrainClass()
	 * @generated
	 */
	void setTrainClass(String value);

	/**
	 * Returns the value of the '<em><b>Line</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Line</em>' attribute.
	 * @see #setLine(String)
	 * @see servicepattern.ServicepatternPackage#getPattern_Line()
	 * @model
	 * @generated
	 */
	String getLine();

	/**
	 * Sets the value of the '{@link servicepattern.Pattern#getLine <em>Line</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Line</em>' attribute.
	 * @see #getLine()
	 * @generated
	 */
	void setLine(String value);

	/**
	 * Returns the value of the '<em><b>Driver</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Driver</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Driver</em>' attribute.
	 * @see #setDriver(String)
	 * @see servicepattern.ServicepatternPackage#getPattern_Driver()
	 * @model
	 * @generated
	 */
	String getDriver();

	/**
	 * Sets the value of the '{@link servicepattern.Pattern#getDriver
	 * <em>Driver</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Driver</em>' attribute.
	 * @see #getDriver()
	 * @generated
	 */
	void setDriver(String value);

	/**
	 * Returns the value of the '<em><b>Start</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Start</em>' attribute.
	 * @see #setStart(int)
	 * @see servicepattern.ServicepatternPackage#getPattern_Start()
	 * @model
	 * @generated
	 */
	int getStart();

	/**
	 * Sets the value of the '{@link servicepattern.Pattern#getStart
	 * <em>Start</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Start</em>' attribute.
	 * @see #getStart()
	 * @generated
	 */
	void setStart(int value);

	/**
	 * Returns the value of the '<em><b>Color</b></em>' attribute. The default value
	 * is <code>""</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Color</em>' attribute.
	 * @see #setColor(String)
	 * @see servicepattern.ServicepatternPackage#getPattern_Color()
	 * @model default=""
	 * @generated
	 */
	String getColor();

	/**
	 * Sets the value of the '{@link servicepattern.Pattern#getColor
	 * <em>Color</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Color</em>' attribute.
	 * @see #getColor()
	 * @generated
	 */
	void setColor(String value);

	/**
	 * Returns the value of the '<em><b>Reliability</b></em>' attribute. The default
	 * value is <code>"100.0"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reliability</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Reliability</em>' attribute.
	 * @see #setReliability(float)
	 * @see servicepattern.ServicepatternPackage#getPattern_Reliability()
	 * @model default="100.0"
	 * @generated
	 */
	float getReliability();

	/**
	 * Sets the value of the '{@link servicepattern.Pattern#getReliability
	 * <em>Reliability</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Reliability</em>' attribute.
	 * @see #getReliability()
	 * @generated
	 */
	void setReliability(float value);

} // Pattern
