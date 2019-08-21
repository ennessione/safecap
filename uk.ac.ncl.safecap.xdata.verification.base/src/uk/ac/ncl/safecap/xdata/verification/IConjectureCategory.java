package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

public interface IConjectureCategory extends Element {

	ElementType TYPE = new ElementType(IConjectureCategory.class);

	@Type(base = ConjectureCategory.class)
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "conjecturecategory", type = ConjectureCategory.class) })
	ListProperty PROP_CONJECTURECATEGORIES = new ListProperty(TYPE, "ConjectureCategories");

	ElementList<ConjectureCategory> getConjectureCategories();
}
