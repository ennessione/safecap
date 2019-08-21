package uk.ac.ncl.eventb.why3.filter;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {
	private T data;
	private TreeNode<T> parent;
	private final List<TreeNode<T>> children;

	public TreeNode(T data) {
		this.data = data;
		this.children = new ArrayList<>(3);
	}

	public TreeNode<T> addChild(T child) {
		final TreeNode<T> childNode = new TreeNode<>(child);
		childNode.parent = this;
		children.add(childNode);
		return childNode;
	}

	public T getData() {
		return data;
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public List<TreeNode<T>> getChildren() {
		return children;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		if (data != null) {
			if (children.size() > 0) {
				return data.toString() + " " + children.toString();
			} else {
				return data.toString();
			}
		} else {
			return "?";
		}
	}
}