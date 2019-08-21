package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

public interface IFragmentCategory extends Element {

	ElementType TYPE = new ElementType(IFragmentCategory.class);

	@Type(base = FragmentCategory.class)
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "fragmentcategory", type = FragmentCategory.class) })
	ListProperty PROP_FRAGMENTCATEGORIES = new ListProperty(TYPE, "FragmentCategories");

	ElementList<FragmentCategory> getFragmentCategories();
}
