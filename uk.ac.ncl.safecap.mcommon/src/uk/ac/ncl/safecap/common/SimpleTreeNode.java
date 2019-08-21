package uk.ac.ncl.safecap.common;

import java.util.ArrayList;
import java.util.List;

public class SimpleTreeNode {
	private final SimpleTreeNode parent;
	private final String name;
	private final List<SimpleTreeNode> children;
	private Object data;

	public SimpleTreeNode(SimpleTreeNode parent, String name) throws SimpleTreeException {
		this.name = name;
		children = new ArrayList<>();
		assert parent != null;
		assert name != null;

		for (final SimpleTreeNode sibling : parent.children) {
			if (sibling.name.equals(name)) {
				throw new SimpleTreeException("Name conflict on name " + name);
			}
		}

		this.parent = parent;
		parent.children.add(this);
	}

	protected SimpleTreeNode(String name) throws SimpleTreeException {
		this.name = name;
		parent = null;
		children = new ArrayList<>();
		assert name != null;
	}

	public Object getData() {
		return data;
	}

	public SimpleTreeNode getChild(String name) {
		for (final SimpleTreeNode node : children) {
			if (node.name.equals(name)) {
				return node;
			}
		}

		return null;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public List<SimpleTreeNode> getChildren() {
		return children;
	}

	public SimpleTreeNode getParent() {
		return parent;
	}
}
