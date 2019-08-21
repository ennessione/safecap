package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.annotations.Label;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

public interface IVerificationSource extends Element {

	ElementType TYPE = new ElementType(IVerificationSource.class);

	@Type(base = VerificationProperty.class)
	@Label(standard = "property")
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "property", type = VerificationProperty.class) })
	ListProperty PROP_PROPERTIES = new ListProperty(TYPE, "Properties");

	ElementList<VerificationProperty> getProperties();
}
