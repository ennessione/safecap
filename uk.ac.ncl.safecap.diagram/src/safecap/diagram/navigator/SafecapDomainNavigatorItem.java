package safecap.diagram.navigator;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * @generated
 */
public class SafecapDomainNavigatorItem extends PlatformObject {

	/**
	 * @generated
	 */
	static {
		final Class[] supportedTypes = new Class[] { EObject.class, IPropertySource.class };
		Platform.getAdapterManager().registerAdapters(new IAdapterFactory() {

			@Override
			public Object getAdapter(Object adaptableObject, Class adapterType) {
				if (adaptableObject instanceof safecap.diagram.navigator.SafecapDomainNavigatorItem) {
					final safecap.diagram.navigator.SafecapDomainNavigatorItem domainNavigatorItem = (safecap.diagram.navigator.SafecapDomainNavigatorItem) adaptableObject;
					final EObject eObject = domainNavigatorItem.getEObject();
					if (adapterType == EObject.class) {
						return eObject;
					}
					if (adapterType == IPropertySource.class) {
						return domainNavigatorItem.getPropertySourceProvider().getPropertySource(eObject);
					}
				}

				return null;
			}

			@Override
			public Class[] getAdapterList() {
				return supportedTypes;
			}
		}, safecap.diagram.navigator.SafecapDomainNavigatorItem.class);
	}

	/**
	 * @generated
	 */
	private final Object myParent;

	/**
	 * @generated
	 */
	private final EObject myEObject;

	/**
	 * @generated
	 */
	private final IPropertySourceProvider myPropertySourceProvider;

	/**
	 * @generated
	 */
	public SafecapDomainNavigatorItem(EObject eObject, Object parent, IPropertySourceProvider propertySourceProvider) {
		myParent = parent;
		myEObject = eObject;
		myPropertySourceProvider = propertySourceProvider;
	}

	/**
	 * @generated
	 */
	public Object getParent() {
		return myParent;
	}

	/**
	 * @generated
	 */
	public EObject getEObject() {
		return myEObject;
	}

	/**
	 * @generated
	 */
	public IPropertySourceProvider getPropertySourceProvider() {
		return myPropertySourceProvider;
	}

	/**
	 * @generated
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof safecap.diagram.navigator.SafecapDomainNavigatorItem) {
			return EcoreUtil.getURI(getEObject())
					.equals(EcoreUtil.getURI(((safecap.diagram.navigator.SafecapDomainNavigatorItem) obj).getEObject()));
		}
		return super.equals(obj);
	}

	/**
	 * @generated
	 */
	@Override
	public int hashCode() {
		return EcoreUtil.getURI(getEObject()).hashCode();
	}

}
