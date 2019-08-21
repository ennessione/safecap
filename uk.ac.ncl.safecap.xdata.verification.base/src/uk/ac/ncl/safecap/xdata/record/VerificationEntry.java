package uk.ac.ncl.safecap.xdata.record;

import javax.xml.bind.annotation.XmlElement;

import uk.ac.ncl.safecap.xdata.verification.core.InvariantInfo;

public class VerificationEntry {
	private int property;
	private int time;
	private int goals;
	private int violations;

	public VerificationEntry() {
	}

	public VerificationEntry(int property, int time, int goals, int violations) {
		super();
		this.property = property;
		this.time = time;
		this.goals = goals;
		this.violations = violations;
	}

	public VerificationEntry(InvariantInfo ii, int time, int goals, int violations) {
		super();
		property = ii.name.hashCode();
		this.time = time;
		this.goals = goals;
		this.violations = violations;
	}

	@XmlElement(name = "p")
	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

	@XmlElement(name = "t")
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@XmlElement(name = "g")
	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	@XmlElement(name = "v")
	public int getViolations() {
		return violations;
	}

	public void setViolations(int violations) {
		this.violations = violations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + goals;
		result = prime * result + property;
		result = prime * result + time;
		result = prime * result + violations;
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
		final VerificationEntry other = (VerificationEntry) obj;
		if (goals != other.goals) {
			return false;
		}
		if (property != other.property) {
			return false;
		}
		if (time != other.time) {
			return false;
		}
		if (violations != other.violations) {
			return false;
		}
		return true;
	}

}
