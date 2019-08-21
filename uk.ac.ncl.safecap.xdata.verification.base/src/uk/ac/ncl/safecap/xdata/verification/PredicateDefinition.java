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

import uk.ac.ncl.safecap.xdata.verification.rules.IProverFragments;

public interface PredicateDefinition extends ICommented, IVerificationSource, IFormulaSource, ITagSet, INamed, IDescriptionSource,
		ISemiFormal, IKeyCarrier, IProverFragments {
	ElementType TYPE = new ElementType(PredicateDefinition.class);

	@XmlBinding(path = "kind")
	@Type(base = PredicateKind.class)
	@DefaultValue(text = "axiom")
	@Required
	ValueProperty PROP_KIND = new ValueProperty(TYPE, "Kind");

	Value<PredicateKind> getKind();

	void setKind(PredicateKind value);

	@Type(base = InvariantReport.class)
	@Label(standard = "report")
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "report", type = InvariantReport.class) })
	ListProperty PROP_REPORTS = new ListProperty(TYPE, "Reports");

	ElementList<InvariantReport> getReports();
}
