package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

public interface ITransitionCategory extends Element {

	ElementType TYPE = new ElementType(ITransitionCategory.class);

	@Type(base = TransitionCategory.class)
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "transitioncategory", type = TransitionCategory.class) })
	ListProperty PROP_TRANSITIONCATEGORIES = new ListProperty(TYPE, "TransitionCategories");

	ElementList<TransitionCategory> getTransitionCategories();
}
