package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.annotations.Label;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

public interface IdentifierCategory extends Element, ICommented, IIdentifierCategory, INamed {
	ElementType TYPE = new ElementType(IdentifierCategory.class);

	@Type(base = IdentifierDefinition.class)
	@Label(standard = "identifier")
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "identifier", type = IdentifierDefinition.class) })
	ListProperty PROP_IDENTIFIERS = new ListProperty(TYPE, "Identifiers");

	ElementList<IdentifierDefinition> getIdentifiers();

}
