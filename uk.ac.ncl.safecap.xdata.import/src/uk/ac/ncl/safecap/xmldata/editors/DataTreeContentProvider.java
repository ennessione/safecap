package uk.ac.ncl.safecap.xmldata.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import uk.ac.ncl.safecap.xmldata.Block;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataNamespace;
import uk.ac.ncl.safecap.xmldata.IDescribed;

class DataTreeContentProvider implements ITreeContentProvider {
	private static final Object[] EMPTY_ARRAY = new Object[] {};

	@Override
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object[] getElements(Object input) {
		if (input instanceof DataContext) {
			final DataContext dataContext = (DataContext) input;
			return dataContext.getNamespaces().toArray();
		} else {
			return EMPTY_ARRAY;
		}
	}

	@Override
	public Object[] getChildren(Object parent) {
		if (parent instanceof DataContext) {
			final DataContext dataContext = (DataContext) parent;
			return dataContext.getNamespaces().toArray();
		} else if (parent instanceof DataNamespace) {
			final DataNamespace nmsp = (DataNamespace) parent;
			final List<Object> result = new ArrayList<>(nmsp.getBlocks().size() + 1);
			result.add(new Description(nmsp));
			result.addAll(nmsp.getBlocks());
			return result.toArray();
		} else if (parent instanceof Block) {
			final Block block = (Block) parent;
			final List<Object> result = new ArrayList<>(block.getValues().size() + 1);
			result.add(new Description(block));
			result.addAll(block.getValues());
			return result.toArray();
		} else {
			return EMPTY_ARRAY;
		}
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof DataContext) {
			final DataContext dataContext = (DataContext) element;
			return !dataContext.getNamespaces().isEmpty();
		} else if (element instanceof DataNamespace) {
			return true;
		} else if (element instanceof Block) {
			return true;
		} else {
			return false;
		}
	}

	static class Description {
		IDescribed source;

		public Description(IDescribed source) {
			super();
			this.source = source;
		}
	}

}