package safecap.diagram.navigator;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

/**
 * @generated
 */
public abstract class SafecapAbstractNavigatorItem extends PlatformObject {

	/**
	 * @generated
	 */
	static {
		final Class[] supportedTypes = new Class[] { ITabbedPropertySheetPageContributor.class };
		final ITabbedPropertySheetPageContributor propertySheetPageContributor = new ITabbedPropertySheetPageContributor() {
			@Override
			public String getContributorId() {
				return "uk.ac.ncl.safecap.diagram"; //$NON-NLS-1$
			}
		};
		Platform.getAdapterManager().registerAdapters(new IAdapterFactory() {

			@Override
			public Object getAdapter(Object adaptableObject, Class adapterType) {
				if (adaptableObject instanceof safecap.diagram.navigator.SafecapAbstractNavigatorItem
						&& adapterType == ITabbedPropertySheetPageContributor.class) {
					return propertySheetPageContributor;
				}
				return null;
			}

			@Override
			public Class[] getAdapterList() {
				return supportedTypes;
			}
		}, safecap.diagram.navigator.SafecapAbstractNavigatorItem.class);
	}

	/**
	 * @generated
	 */
	private final Object myParent;

	/**
	 * @generated
	 */
	protected SafecapAbstractNavigatorItem(Object parent) {
		myParent = parent;
	}

	/**
	 * @generated
	 */
	public Object getParent() {
		return myParent;
	}

}
