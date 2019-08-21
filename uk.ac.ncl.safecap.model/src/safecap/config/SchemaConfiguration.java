/**
 */
package safecap.config;

import uk.ac.ncl.safecap.model.misc.ESerializableObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Schema
 * Configuration</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link safecap.config.SchemaConfiguration#isWnSignal <em>Wn
 * Signal</em>}</li>
 * <li>{@link safecap.config.SchemaConfiguration#isWnTrack <em>Wn
 * Track</em>}</li>
 * <li>{@link safecap.config.SchemaConfiguration#getMergedambits
 * <em>Mergedambits</em>}</li>
 * <li>{@link safecap.config.SchemaConfiguration#getMergedpoints
 * <em>Mergedpoints</em>}</li>
 * <li>{@link safecap.config.SchemaConfiguration#getNormpoint
 * <em>Normpoint</em>}</li>
 * <li>{@link safecap.config.SchemaConfiguration#getNormtrack
 * <em>Normtrack</em>}</li>
 * <li>{@link safecap.config.SchemaConfiguration#getNormsignal
 * <em>Normsignal</em>}</li>
 * <li>{@link safecap.config.SchemaConfiguration#getOverlaps
 * <em>Overlaps</em>}</li>
 * </ul>
 *
 * @see safecap.config.ConfigPackage#getSchemaConfiguration()
 * @model
 * @extends ESerializableObject
 * @generated
 */
public interface SchemaConfiguration extends ESerializableObject {
	/**
	 * Returns the value of the '<em><b>Wn Signal</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wn Signal</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Wn Signal</em>' attribute.
	 * @see #setWnSignal(boolean)
	 * @see safecap.config.ConfigPackage#getSchemaConfiguration_WnSignal()
	 * @model
	 * @generated
	 */
	boolean isWnSignal();

	/**
	 * Sets the value of the '{@link safecap.config.SchemaConfiguration#isWnSignal
	 * <em>Wn Signal</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Wn Signal</em>' attribute.
	 * @see #isWnSignal()
	 * @generated
	 */
	void setWnSignal(boolean value);

	/**
	 * Returns the value of the '<em><b>Wn Track</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wn Track</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Wn Track</em>' attribute.
	 * @see #setWnTrack(boolean)
	 * @see safecap.config.ConfigPackage#getSchemaConfiguration_WnTrack()
	 * @model
	 * @generated
	 */
	boolean isWnTrack();

	/**
	 * Sets the value of the '{@link safecap.config.SchemaConfiguration#isWnTrack
	 * <em>Wn Track</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Wn Track</em>' attribute.
	 * @see #isWnTrack()
	 * @generated
	 */
	void setWnTrack(boolean value);

	/**
	 * Returns the value of the '<em><b>Mergedambits</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mergedambits</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Mergedambits</em>' reference.
	 * @see #setMergedambits(MergedAmbitsConfiguration)
	 * @see safecap.config.ConfigPackage#getSchemaConfiguration_Mergedambits()
	 * @model
	 * @generated
	 */
	MergedAmbitsConfiguration getMergedambits();

	/**
	 * Sets the value of the
	 * '{@link safecap.config.SchemaConfiguration#getMergedambits
	 * <em>Mergedambits</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Mergedambits</em>' reference.
	 * @see #getMergedambits()
	 * @generated
	 */
	void setMergedambits(MergedAmbitsConfiguration value);

	/**
	 * Returns the value of the '<em><b>Mergedpoints</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mergedpoints</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Mergedpoints</em>' reference.
	 * @see #setMergedpoints(MergedPointsConfiguration)
	 * @see safecap.config.ConfigPackage#getSchemaConfiguration_Mergedpoints()
	 * @model
	 * @generated
	 */
	MergedPointsConfiguration getMergedpoints();

	/**
	 * Sets the value of the
	 * '{@link safecap.config.SchemaConfiguration#getMergedpoints
	 * <em>Mergedpoints</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Mergedpoints</em>' reference.
	 * @see #getMergedpoints()
	 * @generated
	 */
	void setMergedpoints(MergedPointsConfiguration value);

	/**
	 * Returns the value of the '<em><b>Normpoint</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Normpoint</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Normpoint</em>' reference.
	 * @see #setNormpoint(NormalisationRule)
	 * @see safecap.config.ConfigPackage#getSchemaConfiguration_Normpoint()
	 * @model
	 * @generated
	 */
	NormalisationRule getNormpoint();

	/**
	 * Sets the value of the '{@link safecap.config.SchemaConfiguration#getNormpoint
	 * <em>Normpoint</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Normpoint</em>' reference.
	 * @see #getNormpoint()
	 * @generated
	 */
	void setNormpoint(NormalisationRule value);

	/**
	 * Returns the value of the '<em><b>Normtrack</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Normtrack</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Normtrack</em>' reference.
	 * @see #setNormtrack(NormalisationRule)
	 * @see safecap.config.ConfigPackage#getSchemaConfiguration_Normtrack()
	 * @model
	 * @generated
	 */
	NormalisationRule getNormtrack();

	/**
	 * Sets the value of the '{@link safecap.config.SchemaConfiguration#getNormtrack
	 * <em>Normtrack</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Normtrack</em>' reference.
	 * @see #getNormtrack()
	 * @generated
	 */
	void setNormtrack(NormalisationRule value);

	/**
	 * Returns the value of the '<em><b>Normsignal</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Normsignal</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Normsignal</em>' reference.
	 * @see #setNormsignal(NormalisationRule)
	 * @see safecap.config.ConfigPackage#getSchemaConfiguration_Normsignal()
	 * @model
	 * @generated
	 */
	NormalisationRule getNormsignal();

	/**
	 * Sets the value of the
	 * '{@link safecap.config.SchemaConfiguration#getNormsignal
	 * <em>Normsignal</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Normsignal</em>' reference.
	 * @see #getNormsignal()
	 * @generated
	 */
	void setNormsignal(NormalisationRule value);

	/**
	 * Returns the value of the '<em><b>Overlaps</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Overlaps</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Overlaps</em>' reference.
	 * @see #setOverlaps(AutoOverlapsConfiguration)
	 * @see safecap.config.ConfigPackage#getSchemaConfiguration_Overlaps()
	 * @model
	 * @generated
	 */
	AutoOverlapsConfiguration getOverlaps();

	/**
	 * Sets the value of the '{@link safecap.config.SchemaConfiguration#getOverlaps
	 * <em>Overlaps</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Overlaps</em>' reference.
	 * @see #getOverlaps()
	 * @generated
	 */
	void setOverlaps(AutoOverlapsConfiguration value);

} // SchemaConfiguration
