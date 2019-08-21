/**
 */
package servicepattern.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import servicepattern.Library;
import servicepattern.Pattern;
import servicepattern.ServicepatternFactory;
import servicepattern.ServicepatternPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class ServicepatternFactoryImpl extends EFactoryImpl implements ServicepatternFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static ServicepatternFactory init() {
		try {
			final ServicepatternFactory theServicepatternFactory = (ServicepatternFactory) EPackage.Registry.INSTANCE
					.getEFactory(ServicepatternPackage.eNS_URI);
			if (theServicepatternFactory != null) {
				return theServicepatternFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ServicepatternFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public ServicepatternFactoryImpl() {
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
		case ServicepatternPackage.LIBRARY:
			return createLibrary();
		case ServicepatternPackage.PATTERN:
			return createPattern();
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
	public Library createLibrary() {
		final LibraryImpl library = new LibraryImpl();
		return library;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Pattern createPattern() {
		final PatternImpl pattern = new PatternImpl();
		return pattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ServicepatternPackage getServicepatternPackage() {
		return (ServicepatternPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ServicepatternPackage getPackage() {
		return ServicepatternPackage.eINSTANCE;
	}

} // ServicepatternFactoryImpl
