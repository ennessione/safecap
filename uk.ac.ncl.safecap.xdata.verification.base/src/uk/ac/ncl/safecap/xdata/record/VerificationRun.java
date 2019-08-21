package uk.ac.ncl.safecap.xdata.record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class VerificationRun {
	private Date date;
	private String schemaId;
	private String schemaName;
	private String artefactId;
	private String artefactName;

	private List<VerificationEntry> entries;

	public VerificationRun() {
	}

	public VerificationRun(String schemaId, String schemaName, String artefactId, String artefactName) {
		date = new Date();
		this.schemaId = schemaId;
		this.schemaName = schemaName;
		this.artefactId = artefactId;
		this.artefactName = artefactName;
		entries = new ArrayList<>();
	}

	public void addEntry(VerificationEntry entry) {
		entries.add(entry);
	}

	@XmlElement(name = "d")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@XmlElement(name = "si")
	public String getSchemaId() {
		return schemaId;
	}

	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
	}

	@XmlElement(name = "sn")
	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	@XmlElement(name = "ai")
	public String getArtefactId() {
		return artefactId;
	}

	public void setArtefactId(String artefactId) {
		this.artefactId = artefactId;
	}

	@XmlElement(name = "an")
	public String getArtefactName() {
		return artefactName;
	}

	public void setArtefactName(String artefactName) {
		this.artefactName = artefactName;
	}

	@XmlElement(name = "ee")
	public List<VerificationEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<VerificationEntry> entries) {
		this.entries = entries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (artefactId == null ? 0 : artefactId.hashCode());
		result = prime * result + (entries == null ? 0 : entries.hashCode());
		result = prime * result + (schemaId == null ? 0 : schemaId.hashCode());
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
		final VerificationRun other = (VerificationRun) obj;
		if (artefactId == null) {
			if (other.artefactId != null) {
				return false;
			}
		} else if (!artefactId.equals(other.artefactId)) {
			return false;
		}
		if (entries == null) {
			if (other.entries != null) {
				return false;
			}
		} else if (!entries.equals(other.entries)) {
			return false;
		}
		if (schemaId == null) {
			if (other.schemaId != null) {
				return false;
			}
		} else if (!schemaId.equals(other.schemaId)) {
			return false;
		}
		return true;
	}

}
