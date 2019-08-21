/**
 */
package safecap.commentary.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.commentary.Commentary;
import safecap.commentary.CommentaryPackage;
import safecap.commentary.Comments;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Comments</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.commentary.impl.CommentsImpl#getComments
 * <em>Comments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CommentsImpl extends EObjectImpl implements Comments {
	/**
	 * The cached value of the '{@link #getComments() <em>Comments</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getComments()
	 * @generated
	 * @ordered
	 */
	protected EList<Commentary> comments;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CommentsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CommentaryPackage.Literals.COMMENTS;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Commentary> getComments() {
		if (comments == null) {
			comments = new EObjectContainmentEList<>(Commentary.class, this, CommentaryPackage.COMMENTS__COMMENTS);
		}
		return comments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case CommentaryPackage.COMMENTS__COMMENTS:
			return ((InternalEList<?>) getComments()).basicRemove(otherEnd, msgs);
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
		case CommentaryPackage.COMMENTS__COMMENTS:
			return getComments();
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
		case CommentaryPackage.COMMENTS__COMMENTS:
			getComments().clear();
			getComments().addAll((Collection<? extends Commentary>) newValue);
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
		case CommentaryPackage.COMMENTS__COMMENTS:
			getComments().clear();
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
		case CommentaryPackage.COMMENTS__COMMENTS:
			return comments != null && !comments.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // CommentsImpl
