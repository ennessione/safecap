/**
 */
package safecap.derived.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import safecap.derived.DerivedFactory;
import safecap.derived.DerivedPackage;
import safecap.derived.MergedJunction;
import safecap.derived.MergedPoint;
import safecap.derived.MergedSection;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class DerivedFactoryImpl extends EFactoryImpl implements DerivedFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static DerivedFactory init() {
		try {
			final DerivedFactory theDerivedFactory = (DerivedFactory) EPackage.Registry.INSTANCE.getEFactory(DerivedPackage.eNS_URI);
			if (theDerivedFactory != null) {
				return theDerivedFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DerivedFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public DerivedFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case DerivedPackage.MERGED_POINT:
			return createMergedPoint();
		case DerivedPackage.MERGED_JUNCTION:
			return createMergedJunction();
		case DerivedPackage.MERGED_SECTION:
			return createMergedSection();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MergedPoint createMergedPoint() {
		final MergedPointImpl mergedPoint = new MergedPointImpl();
		return mergedPoint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MergedJunction createMergedJunction() {
		final MergedJunctionImpl mergedJunction = new MergedJunctionImpl();
		return mergedJunction;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MergedSection createMergedSection() {
		final MergedSectionImpl mergedSection = new MergedSectionImpl();
		return mergedSection;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public DerivedPackage getDerivedPackage() {
		return (DerivedPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DerivedPackage getPackage() {
		return DerivedPackage.eINSTANCE;
	}

} // DerivedFactoryImpl
