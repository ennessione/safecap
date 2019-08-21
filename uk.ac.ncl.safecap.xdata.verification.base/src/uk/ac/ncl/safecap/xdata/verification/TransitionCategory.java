package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.annotations.Label;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

public interface TransitionCategory extends Element, ICommented, INamed {

	ElementType TYPE = new ElementType(TransitionCategory.class);

	@Type(base = TransitionDefinition.class)
	@Label(standard = "transition")
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "transition", type = TransitionDefinition.class) })
	ListProperty PROP_TRANSITIONS = new ListProperty(TYPE, "Transitions");

	ElementList<TransitionDefinition> getTransitions();

	@Type(base = TransitionCategory.class)
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "transitioncategory", type = TransitionCategory.class) })
	ListProperty PROP_TRANSITIONCATEGORIES = new ListProperty(TYPE, "TransitionCategories");

	ElementList<TransitionCategory> getTransitionCategories();
}
