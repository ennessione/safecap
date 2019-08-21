package uk.ac.ncl.safecap.xdata.verification.console;

public class ConsoleNotebookEntry {
	private String entry;
	private String tag;
	private Object output;
	private String error;
	private String info;

	public ConsoleNotebookEntry(String entry) {
		super();
		this.entry = entry;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Object getOutput() {
		return output;
	}

	public void setOutput(Object output) {
		this.output = output;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (entry == null ? 0 : entry.hashCode());
		result = prime * result + (error == null ? 0 : error.hashCode());
		result = prime * result + (info == null ? 0 : info.hashCode());
		result = prime * result + (output == null ? 0 : output.hashCode());
		result = prime * result + (tag == null ? 0 : tag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ConsoleNotebookEntry other = (ConsoleNotebookEntry) obj;
		if (entry == null) {
			if (other.entry != null) {
				return false;
			}
		} else if (!entry.equals(other.entry)) {
			return false;
		}
		if (error == null) {
			if (other.error != null) {
				return false;
			}
		} else if (!error.equals(other.error)) {
			return false;
		}
		if (info == null) {
			if (other.info != null) {
				return false;
			}
		} else if (!info.equals(other.info)) {
			return false;
		}
		if (output == null) {
			if (other.output != null) {
				return false;
			}
		} else if (!output.equals(other.output)) {
			return false;
		}
		if (tag == null) {
			if (other.tag != null) {
				return false;
			}
		} else if (!tag.equals(other.tag)) {
			return false;
		}
		return true;
	}

}
