package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

public interface IPredicateCategory extends Element {

	ElementType TYPE = new ElementType(IPredicateCategory.class);

	@Type(base = PredicateCategory.class)
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "predicatecategory", type = PredicateCategory.class) })
	ListProperty PROP_PREDICATECATEGORIES = new ListProperty(TYPE, "PredicateCategories");

	ElementList<PredicateCategory> getPredicateCategories();
}
