package uk.ac.ncl.safecap.common;

import java.util.ArrayList;
import java.util.List;

public class SimpleTree extends SimpleTreeNode {

	public SimpleTree(String name) throws SimpleTreeException {
		super(name);
	}

	public boolean addPath(String path) throws SimpleTreeException {
		final String parts[] = path.split("\\.");
		boolean isNew = false;
		SimpleTreeNode node = this;
		for (final String part : parts) {
			SimpleTreeNode subnode = node.getChild(part);
			if (subnode == null) {
				isNew = true;
				subnode = new SimpleTreeNode(node, part);
			}
			node = subnode;
		}

		return isNew;
	}

	private SimpleTreeNode getByPath(String[] path) throws SimpleTreeException {
		SimpleTreeNode node = this;
		for (final String part : path) {
			node = node.getChild(part);
			if (node == null) {
				return null;
			}
		}

		return node;
	}

	public String compactPath(String path, IUniqueNameChecker checker) throws SimpleTreeException {
		final String parts[] = path.split("\\.");
		final List<String> result = new ArrayList<>();

		final SimpleTreeNode leaf = getByPath(parts);
		if (leaf == null) {
			throw new SimpleTreeException("Cannot resolve tree path " + path);
		}

		SimpleTreeNode node = leaf;
		while (node != null && node.getParent() != null) {
			result.add(node.getName());
			node = closureParent(node);
		}

		String compacted = buildString(result);
		if (compacted.indexOf('.') == -1) {
			return path;
		}

		if (checker.isUnique(path, compacted)) {
			return compacted.toString();
		} else {
			// System.out.println("### stage 2 for " + path);
			result.clear();
			node = leaf;
			while (node != null && node.getParent() != null) {
				result.add(node.getName());
				node = closureParent2(node);
			}

			compacted = buildString(result);
			if (checker.isUnique(path, compacted)) {
				return compacted;
			} else {
				// System.out.println("### stage 3 for " + path);
				result.clear();
				node = leaf;
				while (node != null && node.getParent() != null) {
					result.add(node.getName());
					node = closureParent2(node);
				}

				return buildString(result);
			}
		}
	}

	private String buildString(List<String> result) {
		final StringBuilder compacted = new StringBuilder();
		for (int i = result.size() - 1; i >= 0; i--) {
			if (i < result.size() - 1) {
				compacted.append('.');
			}
			compacted.append(result.get(i));
		}
		return compacted.toString();
	}

	private SimpleTreeNode closureParent(SimpleTreeNode c) {
		while (c.getParent() != null && (c.getParent().getChildren().size() == 1
				|| c.getParent().getParent() != null && c.getParent().getParent().getChildren().size() == 1)) {
			c = c.getParent();
		}

		return c.getParent();
	}

	private SimpleTreeNode closureParent2(SimpleTreeNode c) {
		while (c.getParent() != null
				&& (c.getParent().getChildren().size() == 1
						|| c.getParent().getParent() != null && c.getParent().getParent().getChildren().size() == 1)
				&& c.getParent().getParent() != null && c.getParent().getParent().getParent() != null) {
			c = c.getParent();
		}

		return c.getParent();
	}
}
