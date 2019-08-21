package safecap.diagram.navigator;

import org.eclipse.jface.viewers.ViewerSorter;

import safecap.diagram.part.SafecapVisualIDRegistry;

/**
 * @generated
 */
public class SafecapNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 4006;

	/**
	 * @generated
	 */
	@Override
	public int category(Object element) {
		if (element instanceof SafecapNavigatorItem) {
			final SafecapNavigatorItem item = (SafecapNavigatorItem) element;
			return SafecapVisualIDRegistry.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
