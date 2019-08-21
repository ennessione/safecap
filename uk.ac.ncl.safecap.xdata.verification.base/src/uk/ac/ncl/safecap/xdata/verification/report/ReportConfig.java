package uk.ac.ncl.safecap.xdata.verification.report;

import java.util.Collection;
import java.util.Map;

import uk.ac.ncl.safecap.xdata.provers.VerificationResult;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;

public class ReportConfig implements java.io.Serializable {
	private static final long serialVersionUID = 7680282776611945504L;
	private boolean showInfo = true;
	private boolean showWarning = true;
	private boolean showSuccessful = true;
	private boolean showUnknown = false;
	private boolean showPredicate = true;
	private boolean showSolution = true;
	private boolean witnessLongForm = true;
	private boolean showRelated = true;
	private boolean showInvalid = true;
	private boolean proveDouble = true;
	private boolean enabledJustifications = true;
	private boolean enabledDetailedProperties = true;
	private boolean enabledTesting = false;
	private boolean showExtermalMapLinks = true;
	private boolean trapPointDetect = true;

	private double relatedThreshold = 0.1;
	private int relatedMax = 5;
	private int witnessMax = 10;
	private boolean showIllustration = true;
	private transient Map<Conjecture, VerificationResult.RESULT> selectedConjectures;
	private boolean showNotCoveredConcepts = true;
	private String predefinedInterlocking = "";

	private int servicePort = 8080;

	public ReportConfig() {
	}

	public ReportConfig(boolean showInfo, boolean showWarning, boolean showSuccessful, boolean showPredicate, boolean showSolution,
			boolean witnessLongForm, int witnessMax) {
		this.showInfo = showInfo;
		this.showWarning = showWarning;
		this.showSuccessful = showSuccessful;
		this.showPredicate = showPredicate;
		this.showSolution = showSolution;
		this.witnessLongForm = witnessLongForm;
		this.witnessMax = witnessMax;
	}

	public int getServicePort() {
		if (servicePort == 0) {
			return 8080;
		} else {
			return servicePort;
		}
	}

	public void setServicePort(int servicePort) {
		this.servicePort = servicePort;
	}

	public boolean isShowExtermalMapLinks() {
		return showExtermalMapLinks;
	}

	public void setShowExtermalMapLinks(boolean showExtermalMapLinks) {
		this.showExtermalMapLinks = showExtermalMapLinks;
	}

	public boolean isEnabledTesting() {
		return enabledTesting;
	}

	public void setEnabledTesting(boolean enabledTesting) {
		this.enabledTesting = enabledTesting;
	}

	public Map<Conjecture, VerificationResult.RESULT> getSelectedConjectures() {
		return selectedConjectures;
	}

	public Collection<Conjecture> getSelectedConjecturesSet() {
		if (selectedConjectures != null) {
			return selectedConjectures.keySet();
		} else {
			return null;
		}
	}

	public String getPredefinedInterlocking() {
		return predefinedInterlocking;
	}

	public void setPredefinedInterlocking(String predefinedInterlocking) {
		this.predefinedInterlocking = predefinedInterlocking;
	}

	public boolean isEnabledJustifications() {
		return enabledJustifications;
	}

	public void setEnabledJustifications(boolean enabledJustifications) {
		this.enabledJustifications = enabledJustifications;
	}

	public boolean isEnabledDetailedProperties() {
		return enabledDetailedProperties;
	}

	public void setEnabledDetailedProperties(boolean enabledDetailedProperties) {
		this.enabledDetailedProperties = enabledDetailedProperties;
	}

	public boolean isShowNotCoveredConcepts() {
		return showNotCoveredConcepts;
	}

	public void setShowNotCoveredConcepts(boolean showNotCoveredConcepts) {
		this.showNotCoveredConcepts = showNotCoveredConcepts;
	}

	public void setSelectedConjectures(Map<Conjecture, VerificationResult.RESULT> selectedConjectures) {
		this.selectedConjectures = selectedConjectures;
	}

	public boolean isShowIllustration() {
		return showIllustration;
	}

	public void setShowIllustration(boolean showIllustration) {
		this.showIllustration = showIllustration;
	}

	public boolean isProveDouble() {
		return proveDouble;
	}

	public void setProveDouble(boolean proveDouble) {
		this.proveDouble = proveDouble;
	}

	public boolean isShowInvalid() {
		return showInvalid;
	}

	public void setShowInvalid(boolean showInvalid) {
		this.showInvalid = showInvalid;
	}

	public int getRelatedMax() {
		return relatedMax;
	}

	public void setRelatedMax(int relatedMax) {
		this.relatedMax = relatedMax;
	}

	public boolean isShowRelated() {
		return showRelated;
	}

	public void setShowRelated(boolean showRelated) {
		this.showRelated = showRelated;
	}

	public double getRelatedThreshold() {
		return relatedThreshold;
	}

	public void setRelatedThreshold(double relatedThreshold) {
		this.relatedThreshold = relatedThreshold;
	}

	public boolean isShowUnknown() {
		return showUnknown;
	}

	public void setShowUnknown(boolean showUnknown) {
		this.showUnknown = showUnknown;
	}

	public boolean isShowInfo() {
		return showInfo;
	}

	public void setShowInfo(boolean showInfo) {
		this.showInfo = showInfo;
	}

	public boolean isShowWarning() {
		return showWarning;
	}

	public void setShowWarning(boolean showWarning) {
		this.showWarning = showWarning;
	}

	public boolean isShowSuccessful() {
		return showSuccessful;
	}

	public void setShowSuccessful(boolean showSuccessful) {
		this.showSuccessful = showSuccessful;
	}

	public boolean isShowPredicate() {
		return showPredicate;
	}

	public void setShowPredicate(boolean showPredicate) {
		this.showPredicate = showPredicate;
	}

	public boolean isShowSolution() {
		return showSolution;
	}

	public void setShowSolution(boolean showSolution) {
		this.showSolution = showSolution;
	}

	public boolean isWitnessLongForm() {
		return witnessLongForm;
	}

	public void setWitnessLongForm(boolean witnessLongForm) {
		this.witnessLongForm = witnessLongForm;
	}

	public int getWitnessMax() {
		return witnessMax;
	}

	public void setWitnessMax(int witnessMax) {
		this.witnessMax = witnessMax;
	}

	public void setTrapPointDetect(boolean trapPointDetect) {
		this.trapPointDetect = trapPointDetect;
	}

	public boolean isTrapPointDetect() {
		return trapPointDetect;
	}
}
