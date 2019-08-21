package uk.ac.ncl.safecap.xdata.base.tablesmodel;

import java.util.ArrayList;
import java.util.List;

public class ColumnModel implements ITablePart {
	public enum ERROR_KIND {
		MUTATE, ADD, REMOVE, SWAP, EXCLUDE;
	};

	private String name;
	private final List<String> conceptPaths;
	private final List<ColumnModelInfo> info;
	private String reason;
	private String semantics;
	private boolean pseudo = false;
	private final TableModel parent;

	public boolean isPseudo() {
		return pseudo;
	}

	public void setPseudo(boolean pseudo) {
		this.pseudo = pseudo;
	}

	public ColumnModel(TableModel parent, String name) {
		this.parent = parent;
		this.name = name;
		conceptPaths = new ArrayList<>();
		info = new ArrayList<>();
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void addConceptPath(String path) {
		conceptPaths.add(path);
	}

	public List<String> getConceptPaths() {
		return conceptPaths;
	}

	public void addInfo(ERROR_KIND kind) {
		info.add(new ColumnModelInfo(this, kind));
	}

	public List<ColumnModelInfo> getInfo() {
		return info;
	}

	public ColumnModelInfo getInfoFor(ERROR_KIND kind) {
		for (final ColumnModelInfo cmi : info) {
			if (cmi.getKind() == kind) {
				return cmi;
			}
		}

		return null;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSemantics() {
		return semantics;
	}

	public void setSemantics(String semantics) {
		this.semantics = semantics;
	}

	@Override
	public TableModel getParent() {
		return parent;
	}

}
