package uk.ac.ncl.safecap.xdata.record;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VerificationRecord {
	private List<VerificationRun> entries;

	public VerificationRecord() {

	}

	public VerificationRecord(int size) {
		entries = new ArrayList<>(size);
	}

	public void addVerificationRun(VerificationRun run) {
		if (entries.contains(run)) {
			entries.add(run);
		}
	}

	@XmlElement(name = "e")
	public List<VerificationRun> getEntries() {
		return entries;
	}

	public void setEntries(List<VerificationRun> entries) {
		this.entries = entries;
	}
}
