package uk.ac.ncl.safecap.analytics.ctextract.main;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public abstract class CTRuleContainer {
	private List<CTRewriteRule> rewriteRules;
	private List<CTProductionRule> productionRules;

	public CTRuleContainer() {
		rewriteRules = new ArrayList<>();
		productionRules = new ArrayList<>();
	}

	public void addRewriteRule(CTRewriteRule rule) {
		rewriteRules.add(rule);
	}

	public void addProductionRule(CTProductionRule rule) {
		productionRules.add(rule);
	}

	@XmlElement(name = "production")
	public List<CTProductionRule> getProductionRules() {
		return productionRules;
	}

	public void setProductionRules(List<CTProductionRule> productionRules) {
		this.productionRules = productionRules;
	}

	@XmlElement(name = "rewrite")
	public List<CTRewriteRule> getRewriteRules() {
		return rewriteRules;
	}

	public void setRewriteRules(List<CTRewriteRule> rewriteRules) {
		this.rewriteRules = rewriteRules;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (productionRules == null ? 0 : productionRules.hashCode());
		result = prime * result + (rewriteRules == null ? 0 : rewriteRules.hashCode());
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
		final CTRuleContainer other = (CTRuleContainer) obj;
		if (productionRules == null) {
			if (other.productionRules != null) {
				return false;
			}
		} else if (!productionRules.equals(other.productionRules)) {
			return false;
		}
		if (rewriteRules == null) {
			if (other.rewriteRules != null) {
				return false;
			}
		} else if (!rewriteRules.equals(other.rewriteRules)) {
			return false;
		}
		return true;
	}
}
