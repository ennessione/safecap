package uk.ac.ncl.safecap.analytics.ctextract.main;

import javax.xml.bind.annotation.XmlTransient;

import uk.ac.ncl.safecap.cteparser.TEContext;

public class CTRule {
	private String pattern = "?";
	private String template = "()";
	private boolean enabled = true;
	protected transient TEContext context;
	private transient CTRuleContainer container;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@XmlTransient
	public CTRuleContainer getContainer() {
		return container;
	}

	public void setContainer(CTRuleContainer container) {
		this.container = container;
	}

	@XmlTransient
	public TEContext getContext() {
		return context;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + (pattern == null ? 0 : pattern.hashCode());
		result = prime * result + (template == null ? 0 : template.hashCode());
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
		final CTRule other = (CTRule) obj;
		if (enabled != other.enabled) {
			return false;
		}
		if (pattern == null) {
			if (other.pattern != null) {
				return false;
			}
		} else if (!pattern.equals(other.pattern)) {
			return false;
		}
		if (template == null) {
			if (other.template != null) {
				return false;
			}
		} else if (!template.equals(other.template)) {
			return false;
		}
		return true;
	}

}
