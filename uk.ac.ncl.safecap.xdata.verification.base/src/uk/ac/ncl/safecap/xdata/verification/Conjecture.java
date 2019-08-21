package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.DefaultValue;
import org.eclipse.sapphire.modeling.annotations.Label;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

public interface Conjecture extends Verifiable, IVerificationSource, IDescriptionSource, ISemiFormal, IKeyCarrier {
	ElementType TYPE = new ElementType(Conjecture.class);

	@XmlBinding(path = "kind")
	@Type(base = ConjectureKind.class)
	@DefaultValue(text = "ERROR")
	@Required
	ValueProperty PROP_KIND = new ValueProperty(TYPE, "Kind");

	Value<ConjectureKind> getKind();

	void setKind(ConjectureKind value);

	@XmlBinding(path = "class")
	@Type(base = ConjectureClass.class)
	@DefaultValue(text = "NONE")
	@Required
	ValueProperty PROP_CLASSIFICATION = new ValueProperty(TYPE, "Classification");

	Value<ConjectureClass> getClassification();

	void setClassification(ConjectureClass value);

	@Type(base = PropertyReport.class)
	@Label(standard = "report")
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "report", type = PropertyReport.class) })
	ListProperty PROP_REPORTS = new ListProperty(TYPE, "Reports");

	ElementList<PropertyReport> getReports();

}
