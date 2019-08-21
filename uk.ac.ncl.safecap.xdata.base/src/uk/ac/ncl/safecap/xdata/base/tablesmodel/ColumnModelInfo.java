package uk.ac.ncl.safecap.xdata.base.tablesmodel;

import uk.ac.ncl.safecap.xdata.base.tablesmodel.ColumnModel.ERROR_KIND;

public class ColumnModelInfo {
	private final ColumnModel parent;
	private final ERROR_KIND kind;
	private String propertyName;
	private double weight = 1.0;
	private boolean isEnabled = false;;

	public ColumnModelInfo(ColumnModel parent, ERROR_KIND kind) {
		this.parent = parent;
		this.kind = kind;
		switch (kind) {
		case MUTATE:
			weight = 1.0;
			break;
		case ADD:
			weight = 0.2;
			break;
		case REMOVE:
			weight = 0.2;
			break;
		case SWAP:
			weight = 0.1;
			break;
		case EXCLUDE:
			weight = 0.1;
			break;
		}
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public ERROR_KIND getKind() {
		return kind;
	}

	public ColumnModel getParent() {
		return parent;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

}
