package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

public interface IIdentifierCategory extends Element {
	ElementType TYPE = new ElementType(IIdentifierCategory.class);

	@Type(base = IdentifierCategory.class)
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "identifiercategory", type = IdentifierCategory.class) })
	ListProperty PROP_IDENTIFIERCATEGORIES = new ListProperty(TYPE, "IdentifierCategories");

	ElementList<IdentifierCategory> getIdentifierCategories();
}
