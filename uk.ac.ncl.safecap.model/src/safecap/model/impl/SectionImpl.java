/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.model.impl;

import org.eclipse.emf.ecore.EClass;

import safecap.model.ModelPackage;
import safecap.model.Section;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Section</b></em>'. <!-- end-user-doc -->
 *
 * @generated
 */
public class SectionImpl extends AmbitImpl implements Section {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SECTION;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public String toString() {
		return getLabel();
	}

} // SectionImpl
