package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.annotations.Label;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

public interface PredicateCategory extends Element, ICommented, IPredicateCategory, INamed, ITagSet {

	ElementType TYPE = new ElementType(PredicateCategory.class);

	@Type(base = PredicateDefinition.class)
	@Label(standard = "predicate")
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "predicate", type = PredicateDefinition.class) })
	ListProperty PROP_PREDICATES = new ListProperty(TYPE, "Predicates");

	ElementList<PredicateDefinition> getPredicates();
}
