package uk.ac.ncl.safecap.xdata.base.tablesmodel;

import java.util.ArrayList;
import java.util.List;

public class TableModel implements ITablePart {
	private final TableModel parent;
	private String name;
	private final List<ITablePart> parts;

	public TableModel(TableModel parent, String name) {
		this.parent = parent;
		this.name = name;
		parts = new ArrayList<>();
	}

	public TableModel copy(TableModel cparent) {
		final TableModel c = new TableModel(cparent, name);
		return c;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void remove(ITablePart part) {
		parts.remove(part);
	}

	public void add(ITablePart part) {
		parts.add(part);
	}

	public List<ITablePart> getParts() {
		return parts;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public TableModel getParent() {
		return parent;
	}

	public ColumnModel findColumnByPath(String path) {
		for (final ITablePart part : parts) {
			if (part instanceof ColumnModel) {
				final ColumnModel cm = (ColumnModel) part;
				if (cm.getConceptPaths().contains(path)) {
					return cm;
				}
			} else if (part instanceof TableModel) {
				final TableModel cm = (TableModel) part;
				final ColumnModel r = cm.findColumnByPath(path);
				if (r != null) {
					return r;
				}
			}
		}
		return null;
	}

}
