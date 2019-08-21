/**
 */
package safecap.commentary;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Orientable Commentary</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.commentary.OrientableCommentary#getAngle
 * <em>Angle</em>}</li>
 * </ul>
 *
 * @see safecap.commentary.CommentaryPackage#getOrientableCommentary()
 * @model
 * @generated
 */
public interface OrientableCommentary extends Commentary {
	/**
	 * Returns the value of the '<em><b>Angle</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Angle</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Angle</em>' attribute.
	 * @see #setAngle(int)
	 * @see safecap.commentary.CommentaryPackage#getOrientableCommentary_Angle()
	 * @model
	 * @generated
	 */
	int getAngle();

	/**
	 * Sets the value of the
	 * '{@link safecap.commentary.OrientableCommentary#getAngle <em>Angle</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Angle</em>' attribute.
	 * @see #getAngle()
	 * @generated
	 */
	void setAngle(int value);

} // OrientableCommentary
