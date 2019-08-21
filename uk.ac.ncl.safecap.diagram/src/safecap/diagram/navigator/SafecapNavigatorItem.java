package safecap.diagram.navigator;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class SafecapNavigatorItem extends SafecapAbstractNavigatorItem {

	/**
	 * @generated
	 */
	static {
		final Class[] supportedTypes = new Class[] { View.class, EObject.class };
		Platform.getAdapterManager().registerAdapters(new IAdapterFactory() {

			@Override
			public Object getAdapter(Object adaptableObject, Class adapterType) {
				if (adaptableObject instanceof safecap.diagram.navigator.SafecapNavigatorItem
						&& (adapterType == View.class || adapterType == EObject.class)) {
					return ((safecap.diagram.navigator.SafecapNavigatorItem) adaptableObject).getView();
				}
				return null;
			}

			@Override
			public Class[] getAdapterList() {
				return supportedTypes;
			}
		}, safecap.diagram.navigator.SafecapNavigatorItem.class);
	}

	/**
	 * @generated
	 */
	private final View myView;

	/**
	 * @generated
	 */
	private boolean myLeaf = false;

	/**
	 * @generated
	 */
	public SafecapNavigatorItem(View view, Object parent, boolean isLeaf) {
		super(parent);
		myView = view;
		myLeaf = isLeaf;
	}

	/**
	 * @generated
	 */
	public View getView() {
		return myView;
	}

	/**
	 * @generated
	 */
	public boolean isLeaf() {
		return myLeaf;
	}

	/**
	 * @generated
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof safecap.diagram.navigator.SafecapNavigatorItem) {
			return EcoreUtil.getURI(getView()).equals(EcoreUtil.getURI(((safecap.diagram.navigator.SafecapNavigatorItem) obj).getView()));
		}
		return super.equals(obj);
	}

	/**
	 * @generated
	 */
	@Override
	public int hashCode() {
		return EcoreUtil.getURI(getView()).hashCode();
	}

}
