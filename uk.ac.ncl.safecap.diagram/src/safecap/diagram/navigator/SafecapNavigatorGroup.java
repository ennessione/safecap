package safecap.diagram.navigator;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @generated
 */
public class SafecapNavigatorGroup extends SafecapAbstractNavigatorItem {

	/**
	 * @generated
	 */
	private final String myGroupName;

	/**
	 * @generated
	 */
	private final String myIcon;

	/**
	 * @generated
	 */
	private final Collection myChildren = new LinkedList();

	/**
	 * @generated
	 */
	SafecapNavigatorGroup(String groupName, String icon, Object parent) {
		super(parent);
		myGroupName = groupName;
		myIcon = icon;
	}

	/**
	 * @generated
	 */
	public String getGroupName() {
		return myGroupName;
	}

	/**
	 * @generated
	 */
	public String getIcon() {
		return myIcon;
	}

	/**
	 * @generated
	 */
	public Object[] getChildren() {
		return myChildren.toArray();
	}

	/**
	 * @generated
	 */
	public void addChildren(Collection children) {
		myChildren.addAll(children);
	}

	/**
	 * @generated
	 */
	public void addChild(Object child) {
		myChildren.add(child);
	}

	/**
	 * @generated
	 */
	public boolean isEmpty() {
		return myChildren.size() == 0;
	}

	/**
	 * @generated
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof safecap.diagram.navigator.SafecapNavigatorGroup) {
			final safecap.diagram.navigator.SafecapNavigatorGroup anotherGroup = (safecap.diagram.navigator.SafecapNavigatorGroup) obj;
			if (getGroupName().equals(anotherGroup.getGroupName())) {
				return getParent().equals(anotherGroup.getParent());
			}
		}
		return super.equals(obj);
	}

	/**
	 * @generated
	 */
	@Override
	public int hashCode() {
		return getGroupName().hashCode();
	}

}
