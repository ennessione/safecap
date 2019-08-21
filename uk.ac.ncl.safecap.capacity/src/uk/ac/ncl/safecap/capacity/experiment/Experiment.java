package uk.ac.ncl.safecap.capacity.experiment;

import safecap.Project;

public abstract class Experiment {
	protected Project project;
	protected long timestamp;
	protected String kind;

	public Experiment(String kind, Project project, long timestamp) {
		this.project = project;
		this.timestamp = timestamp;
		this.kind = kind;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public Project getProject() {
		return project;
	}

	public long getTimestamp() {
		return timestamp;
	}

}
