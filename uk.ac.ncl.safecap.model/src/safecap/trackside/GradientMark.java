/**
 */
package safecap.trackside;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Gradient
 * Mark</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link safecap.trackside.GradientMark#getBefore <em>Before</em>}</li>
 * <li>{@link safecap.trackside.GradientMark#getAfter <em>After</em>}</li>
 * </ul>
 * </p>
 *
 * @see safecap.trackside.TracksidePackage#getGradientMark()
 * @model
 * @generated
 */
public interface GradientMark extends Equipment {
	/**
	 * Returns the value of the '<em><b>Before</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Before</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Before</em>' attribute.
	 * @see #setBefore(double)
	 * @see safecap.trackside.TracksidePackage#getGradientMark_Before()
	 * @model
	 * @generated
	 */
	double getBefore();

	/**
	 * Sets the value of the '{@link safecap.trackside.GradientMark#getBefore
	 * <em>Before</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Before</em>' attribute.
	 * @see #getBefore()
	 * @generated
	 */
	void setBefore(double value);

	/**
	 * Returns the value of the '<em><b>After</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>After</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>After</em>' attribute.
	 * @see #setAfter(double)
	 * @see safecap.trackside.TracksidePackage#getGradientMark_After()
	 * @model
	 * @generated
	 */
	double getAfter();

	/**
	 * Sets the value of the '{@link safecap.trackside.GradientMark#getAfter
	 * <em>After</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>After</em>' attribute.
	 * @see #getAfter()
	 * @generated
	 */
	void setAfter(double value);

} // GradientMark
