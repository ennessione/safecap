package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.DefaultValue;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

public interface PropertyReport extends BaseReport, IFormulaSource, IVerificationSource {
	ElementType TYPE = new ElementType(PropertyReport.class);

	@XmlBinding(path = "reportkind")
	@Type(base = ReportKind.class)
	@DefaultValue(text = "SINGLE")
	@Required
	ValueProperty PROP_REPORTKIND = new ValueProperty(TYPE, "ReportKind");

	Value<ReportKind> getReportKind();

	void setReportKind(ReportKind value);

	@XmlBinding(path = "positive")
	@Type(base = Boolean.class)
	@DefaultValue(text = "false")
	ValueProperty PROP_POSITIVE = new ValueProperty(TYPE, "Positive");

	Value<Boolean> isPositive();

	void setPositive(String value);

	void setPositive(Boolean value);
}
