package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.annotations.Label;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

public interface FragmentCategory extends Element, ICommented, IFragmentCategory, INamed {

	ElementType TYPE = new ElementType(FragmentCategory.class);

	@Type(base = FragmentDefinition.class)
	@Label(standard = "fragment")
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "fragment", type = FragmentDefinition.class) })
	ListProperty PROP_FRAGMENTS = new ListProperty(TYPE, "Fragments");

	ElementList<FragmentDefinition> getFragments();
}
