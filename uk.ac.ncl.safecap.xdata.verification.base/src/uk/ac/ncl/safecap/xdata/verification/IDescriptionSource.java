package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.annotations.Label;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

public interface IDescriptionSource extends Element {
	ElementType TYPE = new ElementType(IDescriptionSource.class);

	@Type(base = ConjectureDescription.class)
	@Label(standard = "description")
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "description", type = ConjectureDescription.class) })
	ListProperty PROP_DESCRIPTIONS = new ListProperty(TYPE, "Descriptions");

	ElementList<ConjectureDescription> getDescriptions();
}
